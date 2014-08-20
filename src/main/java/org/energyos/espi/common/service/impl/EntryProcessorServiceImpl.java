/*
22 * Copyright 2013, 2014 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.energyos.espi.common.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.persistence.NoResultException;

import org.energyos.espi.common.domain.ApplicationInformation;
import org.energyos.espi.common.domain.ElectricPowerQualitySummary;
import org.energyos.espi.common.domain.ElectricPowerUsageSummary;
import org.energyos.espi.common.domain.IdentifiedObject;
import org.energyos.espi.common.domain.IntervalBlock;
import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.domain.ReadingType;
import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.TimeConfiguration;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.models.atom.LinkType;
import org.energyos.espi.common.service.EntryProcessorService;
import org.energyos.espi.common.service.IntervalBlockService;
import org.energyos.espi.common.service.MeterReadingService;
import org.energyos.espi.common.service.ResourceService;
import org.energyos.espi.common.service.RetailCustomerService;
import org.energyos.espi.common.utils.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = { javax.xml.bind.JAXBException.class }, noRollbackFor = {
		javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })
@Service
public class EntryProcessorServiceImpl implements EntryProcessorService {
	private Logger log = LoggerFactory.getLogger(EntryProcessorServiceImpl.class);
	@Autowired
	private ResourceService resourceService;

	@Autowired
	private IntervalBlockService intervalBlockService;

	@Autowired
	private MeterReadingService meterReadingService;

	@Autowired
	private RetailCustomerService retailCustomerService;

	public RetailCustomerService getRetailCustomerService() {
		return retailCustomerService;
	}

	public void setRetailCustomerService(RetailCustomerService retailCustomerService) {
		this.retailCustomerService = retailCustomerService;
	}

	public void setMeterReadingService(MeterReadingService meterReadingService) {
		this.meterReadingService = meterReadingService;
	}

	public void setIntervalBlockService(IntervalBlockService intervalBlockService) {
		this.intervalBlockService = intervalBlockService;
	}

	public EntryType process(EntryType entry) {
		log.debug("process ***************************************** entry " + entry);
		convert(entry);
		for (IdentifiedObject resource : entry.getContent().getResources()) {

			try {

				IdentifiedObject existingResource = null;

				if (resource instanceof IntervalBlock) {

					System.err.println(resource.getSelfLink().getHref()+ " interval block uuid  ..." + resource.getUUID());

					existingResource = intervalBlockService.findByUUID(resource.getUUID());

					IntervalBlock ib = (IntervalBlock) existingResource;

					System.err.println("block existingResource.getId() ..." + existingResource.getId());
					System.err.println("block existingResource.meter reading id ..." + ib.getMeterReadingId());

					ib.setMeterReading(meterReadingService.findById(ib.getMeterReadingId()));


					// merge the new into the old and throw the new away
					//
					existingResource.merge(resource);

					existingResource = intervalBlockService.merge((IntervalBlock) existingResource);

					if (ib.getMeterReading() != null) {
						meterReadingService.persist(ib.getMeterReading());
					}

				} else {
					existingResource = resourceService.findByUUID(resource.getUUID(), resource.getClass());
					existingResource = resourceService.merge(existingResource);

					// merge the new into the old and throw the new away
					//
					existingResource.merge(resource);
				}

				// now put the existing resource back into the structure so it
				// will
				// be available for later processing

				if (existingResource instanceof UsagePoint) {
					entry.getContent().setUsagePoint((UsagePoint) existingResource);
				}

				if (existingResource instanceof MeterReading) {
					entry.getContent().setMeterReading((MeterReading) existingResource);
				}

				if (existingResource instanceof IntervalBlock) {
					// System.out.printf("*****We have an existing IntervalBlock??: %s\n",
					// existingResource.toString());
					List<IntervalBlock> intervalBlocks = entry.getContent().getIntervalBlocks();
					List<IntervalBlock> newList = new ArrayList<IntervalBlock>();
					Iterator<IntervalBlock> blocks = intervalBlocks.iterator();
					/*
					 * DJ while (blocks.hasNext()) { IntervalBlock bl =
					 * blocks.next(); if
					 * (bl.getUUID().equals(existingResource.getUUID())) {
					 * existingResource.merge(bl); newList.add((IntervalBlock)
					 * existingResource); } else { newList.add(bl); } }
					 */
				}

				if (existingResource instanceof ReadingType) {

					entry.getContent().setReadingType((ReadingType) existingResource);
				}

				if (existingResource instanceof TimeConfiguration) {

					entry.getContent().setLocalTimeParameters((TimeConfiguration) existingResource);
				}

				if (existingResource instanceof ElectricPowerUsageSummary) {

					entry.getContent().setElectricPowerUsageSummary((ElectricPowerUsageSummary) existingResource);
				}

				if (existingResource instanceof ElectricPowerQualitySummary) {

					entry.getContent().setElectricPowerQualitySummary((ElectricPowerQualitySummary) existingResource);
				}

				if (existingResource instanceof ApplicationInformation) {

					entry.getContent().setApplicationInformation((ApplicationInformation) existingResource);
				}
				if (existingResource instanceof RetailCustomer) {
					entry.getContent().setRetailCustomer((RetailCustomer) existingResource);
				}

				link(existingResource);

			} catch (EmptyResultDataAccessException x) {
				if (resource instanceof IntervalBlock) {
					IntervalBlock ib = (IntervalBlock) resource;
					String uplink = ib.getUpLink().getHref();
					String[] tokens = uplink.split("/IntervalBlock");
					String link = tokens[0];
					try {
						UUID uuid = UUIDUtil.uuid(link);
						System.err.println(link+ " meter reading uuid ->" + uuid);
						MeterReading meterReading = meterReadingService.findByUUID(uuid);

						if (meterReading != null) {
							log.info(" Associating with meter reading " + meterReading.getUUID());
							// load the usage point as well
							meterReading.getUsagePoint();
							ib.setMeterReading(meterReading);
							ib.setMeterReadingId(meterReading.getId());
							ib.setMeterRedingDate();
						}
					} catch (EmptyResultDataAccessException ignore) {
						System.out.println("Exception *** " + ignore.getMessage());
					} catch (NoResultException ignore) {
						System.out.println("Exception *** " + ignore.getMessage());
					} catch (Exception ignore) {
						System.err.println("Exception *** " + ignore.getMessage());
					}

					intervalBlockService.persist((IntervalBlock) resource);
					link(resource);
					// intervalBlockService.flush();

				} else if (resource instanceof UsagePoint) {
					UsagePoint usagePoint = (UsagePoint) resource;
					// if related link present for customer
					for (String link : usagePoint.getRelatedLinkHrefs()) {
						if (link.contains("/espi/1_1/resource/RetailCustomer/")) {
							if (link.indexOf("/", "/espi/1_1/resource/RetailCustomer/".length()) < 0) {
								UUID uuid = UUIDUtil.uuid(link);
								log.debug(" Associating with RetailCustomer " + uuid);
								RetailCustomer retailCustomer = null;
								try {
									retailCustomer = retailCustomerService.findByUUID(uuid);
								} catch (EmptyResultDataAccessException ignore) {
									retailCustomer = new RetailCustomer();
									retailCustomer.setUUID(uuid);
									retailCustomer.setSelfLink(new LinkType("self", link));
									retailCustomer.setUpLink(new LinkType("up", "/espi/1_1/resource/RetailCustomer"));
									retailCustomer.setFirstName("unknown");
									retailCustomer.setLastName("unknown");
									retailCustomer.setPassword("a@12561b");
									resourceService.persist(retailCustomer);
								} catch (NoResultException ignore) {
									retailCustomer = new RetailCustomer();
									retailCustomer.setUUID(uuid);
									retailCustomer.setSelfLink(new LinkType("self", link));
									retailCustomer.setUpLink(new LinkType("up", "/espi/1_1/resource/RetailCustomer"));
									retailCustomer.setFirstName("unknown");
									retailCustomer.setLastName("unknown");
									retailCustomer.setPassword("a@12561b");
									resourceService.persist(retailCustomer);
								}
								usagePoint.setRetailCustomer(retailCustomer);
							}

							// break;
						}
					}
					resourceService.persist(resource);
					link(resource);
				} else {
					resourceService.persist(resource);
					link(resource);
					resourceService.flush();
				}
			}

		}

		return entry;
	}

	// the private functions that do the work
	//
	private void link(IdentifiedObject resource) {
		linkUp(resource);
		linkUpMember(resource);
		linkRelatedCollection(resource);
	}

	// Copy the entry attributes into the resource attributes
	//
	public void convert(EntryType entry) {
		for (IdentifiedObject resource : entry.getContent().getResources()) {
			resource.setMRID(entry.getId());
			for (LinkType link : entry.getLinks()) {
				if (link.getRel().equals(LinkType.SELF)) resource.setSelfLink(link);
				if (link.getRel().equals(LinkType.UP)) resource.setUpLink(link);
				if (link.getRel().equals(LinkType.RELATED)) resource.getRelatedLinks().add(link);
			}
			resource.setDescription(entry.getTitle());
			resource.setPublished(entry.getPublished().getValue().toGregorianCalendar());
			resource.setUpdated(entry.getUpdated().getValue().toGregorianCalendar());
		}

	}

	// Establish the rel="up" links into the parent collections
	//
	private void linkUp(IdentifiedObject resource) {
		if (resource.getUpLink() != null) {
			List<IdentifiedObject> parents = resourceService.findByAllParentsHref(resource.getUpLink().getHref(),
					resource);
			for (IdentifiedObject parent : parents) {
				// add the parent to the transaction
				System.err.println(resource + " Link up " + parent);
				resourceService.merge(parent);
				// add the resource to the parent collection
				resource.setUpResource(parent);
			}
		}
	}

	// Establish the rel="related" links to the associated resources
	//
	private void linkUpMember(IdentifiedObject resource) {

		if (resource.getSelfLink() != null) {
			List<IdentifiedObject> parents = resourceService.findByAllParentsHref(resource.getSelfLink().getHref(),
					resource);
			for (IdentifiedObject parent : parents) {
				// put the existing resource in the transaction
				resourceService.merge(parent);

				// Based on the kind of resource, do the appropriate fixup
				if (resource instanceof TimeConfiguration) {
					UsagePoint usagePoint = (UsagePoint) parent;

					if (usagePoint.getLocalTimeParameters() == null) {

						usagePoint.setLocalTimeParameters((TimeConfiguration) resource);

					}
				}

				if (resource instanceof ReadingType) {

					MeterReading meterReading = (MeterReading) parent;
					if (meterReading.getReadingType() == null) {
						meterReading.setReadingType((ReadingType) resource);
					}
				}
			}
		}
	}

	private void linkRelatedCollection(IdentifiedObject resource) {

		List<IdentifiedObject> relatedResources = resourceService.findAllRelated(resource);

		for (IdentifiedObject relatedResource : relatedResources) {
			// Put the relatedResource in the Transaction
			resourceService.merge(relatedResource);

			if (resource instanceof UsagePoint) {
				if (relatedResource instanceof TimeConfiguration) {
					((UsagePoint) resource).setLocalTimeParameters((TimeConfiguration) relatedResource);
				}

				if (relatedResource instanceof MeterReading) {
					((UsagePoint) resource).addMeterReading((MeterReading) relatedResource);
				}

				if (relatedResource instanceof ElectricPowerUsageSummary) {
					((UsagePoint) resource).addElectricPowerUsageSummary((ElectricPowerUsageSummary) relatedResource);
				}

				if (relatedResource instanceof ElectricPowerQualitySummary) {
					((UsagePoint) resource)
							.addElectricPowerQualitySummary((ElectricPowerQualitySummary) relatedResource);
				}
			}
			if (resource instanceof MeterReading) {

				if (relatedResource instanceof ReadingType) {
					((MeterReading) resource).setReadingType((ReadingType) relatedResource);
				}

				if (relatedResource instanceof IntervalBlock) {
					((MeterReading) resource).addIntervalBlock((IntervalBlock) relatedResource);
				}
			}

		}

	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public ResourceService getResourceService(ResourceService resourceService) {
		return resourceService;
	}
}
