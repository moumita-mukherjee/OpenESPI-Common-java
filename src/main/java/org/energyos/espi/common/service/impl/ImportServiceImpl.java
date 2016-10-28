/*
 * Copyright 2013, 2014 EnergyOS.org
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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.energyos.espi.common.domain.ApplicationInformation;
import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.Subscription;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.service.AuthorizationService;
import org.energyos.espi.common.service.EntryProcessorService;
import org.energyos.espi.common.service.ImportService;
import org.energyos.espi.common.service.IntervalBlockService;
import org.energyos.espi.common.service.MeterReadingService;
import org.energyos.espi.common.service.ResourceService;
import org.energyos.espi.common.service.RetailCustomerService;
import org.energyos.espi.common.service.SubscriptionService;
import org.energyos.espi.common.service.UsagePointService;
import org.energyos.espi.common.utils.ATOMContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.energyos.espi.common.utils.EntryProcessor;
//import org.energyos.espi.common.utils.ResourceConverter;
//import org.energyos.espi.common.utils.ResourceLinker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

@Service
public class ImportServiceImpl implements ImportService {	
	private Logger log = LoggerFactory.getLogger(ImportServiceImpl.class);
    @Autowired
    @Qualifier("atomMarshaller")
    private Jaxb2Marshaller marshaller;
    
    @Autowired
    private AuthorizationService authorizationService; 
    
    @Autowired
    private SubscriptionService subscriptionService;
    
    @Autowired
    private UsagePointService usagePointService;
    
    @Autowired
    private RetailCustomerService retailCustomerService;
    
    @Autowired
    private ResourceService resourceService;
    
    @Autowired
    private EntryProcessorService entryProcessorService;
    
    // this is a list of the UsagePointIds referenced during
    // this import
    private List<EntryType> entries;
    
    // Min Updated <== used on time scoping the subscriptions
    //
    private XMLGregorianCalendar minUpdated = null;
    
    // Max Updated <== used on time scoping the subscriptions
    //
    private XMLGregorianCalendar maxUpdated = null;
    
    @Override
    public List <EntryType> getEntries() {
    	List<EntryType> result = entries;
		//DJ entries = null;
		return result;
	}

	@Override
	public XMLGregorianCalendar getMinUpdated() {
		return this.minUpdated;
	}

	@Override
	public XMLGregorianCalendar getMaxUpdated() {
		return this.maxUpdated;
	}

	// DJ
	@Transactional(rollbackFor = { javax.xml.bind.JAXBException.class }, noRollbackFor = {
			javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })
	@Override
	public void importData(InputStream stream, Long retailCustomerId) throws IOException, SAXException,
			ParserConfigurationException {
		log.debug("************importData************************************** retailCustomerId "+ retailCustomerId);
		try {
			JAXBContext context = marshaller.getJaxbContext();
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XMLReader reader = factory.newSAXParser().getXMLReader();
			
			ATOMContentHandler atomContentHandler = new ATOMContentHandler(context, entryProcessorService);
			reader.setContentHandler(atomContentHandler);

			try {
				reader.parse(new InputSource(stream));
			} catch (Exception e1) {
				log.warn("Exception in import data :"+e1.getMessage());
				log.debug("Exception in import data :",e1);	
				
			}
			entries = atomContentHandler.getEntries();
			minUpdated = atomContentHandler.getMinUpdated();
			maxUpdated = atomContentHandler.getMaxUpdated();
			
			List<UsagePoint> usagePointList = new ArrayList<UsagePoint>();
			Iterator<EntryType> its = entries.iterator();
			while (its.hasNext()) {
				EntryType entry = its.next();
				if (entry.getContent() != null && entry.getContent().getResource() instanceof RetailCustomer) {
					return;
				}
			}
			RetailCustomer retailCustomer = null;

			if (retailCustomerId != null) {
				retailCustomer = retailCustomerService.findById(retailCustomerId);
			}
			its = entries.iterator();
			while (its.hasNext()) {
				EntryType entry = its.next();
				UsagePoint usagePoint = entry.getContent().getUsagePoint();
				if (usagePoint != null) {
					RetailCustomer tempRc = usagePoint.getRetailCustomer();					
					if (tempRc != null) {
						if (!(tempRc.equals(retailCustomer))) {
							// we have a conflict in association meaning to
							// Retail Customers
							// TODO: resolve how to handle the conflict
							// mentioned above.
						}
					} else {
						// associate the usagePoint with the Retail Customer
						log.info("getRelatedLinkHrefs retailCustomer retailCustomer " + retailCustomer);
						if (retailCustomer != null) {
							usagePointService.associateByUUID(retailCustomer, usagePoint.getUUID());
						}

					}
					usagePointList.add(usagePoint);
				}
			}

			if (retailCustomer != null && false) {

				Subscription subscription = null;

				// find and iterate across all relevant authorizations
				//
				List<Authorization> authorizationList = authorizationService.findAllByRetailCustomerId(retailCustomer
						.getId());
				for (Authorization authorization : authorizationList) {

					try {
						subscription = subscriptionService.findByAuthorizationId(authorization.getId());
					} catch (Exception e) {
						// an Authorization w/o an associated subscription
						// breaks
						// the propagation chain
						System.out.printf("**** End of Notification Propgation Chain\n");
					}
					if (subscription != null) {
						String resourceUri = authorization.getResourceURI();
						// this is the first time this authorization has been in
						// effect. We must set up the appropriate resource links
						if (resourceUri == null) {
							ApplicationInformation applicationInformation = authorization.getApplicationInformation();
							resourceUri = applicationInformation.getDataCustodianResourceEndpoint();
							resourceUri = resourceUri + "/Batch/Subscription/" + subscription.getId();
							authorization.setResourceURI(resourceUri);

							resourceService.merge(authorization);
						}

						// make sure the UsagePoint(s) we just imported are
						// linked up
						// with
						// the Subscription

						for (UsagePoint usagePoint : usagePointList) {
							boolean addNew = false;
							for (UsagePoint up : subscription.getUsagePoints()) {
								if (up.equals(usagePoint)) addNew = true;
							}

							if (addNew) subscriptionService.addUsagePoint(subscription, usagePoint);

						}
					}
				}
			}

		} catch (Exception ex) {
			log.warn("Exception in import data :"+ex.getMessage());
			log.debug("Exception in import data :",ex);			
		}
    }
        
    public void setJaxb2Marshaller(Jaxb2Marshaller marshaller) {
        this.marshaller = marshaller;
   }

   public Jaxb2Marshaller getJaxb2Marshaller () {
       return this.marshaller;
   }
   public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService; 
   }

	public AuthorizationService getAuthorizationService() {
		return this.authorizationService;
	}

	public void setSubscriptionService(SubscriptionService subscriptionService) {
		this.subscriptionService = subscriptionService;
	}

	public SubscriptionService getSubscriptionService() {
		return this.subscriptionService;
	}

	public void setUsagePointService(UsagePointService usagePointService) {
		this.usagePointService = usagePointService;
	}

	public UsagePointService getUsagePointService() {
		return this.usagePointService;
	}

	public void setRetailCustomerService(RetailCustomerService retailCustomerService) {
		this.retailCustomerService = retailCustomerService;
	}

	public RetailCustomerService getRetailCustomerService() {
		return this.retailCustomerService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public ResourceService getResourceService() {
		return this.resourceService;
	}

	public void setEntryProcessorService(EntryProcessorService entryProcessorService) {
		this.entryProcessorService = entryProcessorService;
	}

	public EntryProcessorService getEntryProcessorService() {
		return this.entryProcessorService;
	}

	/* LH customization starts here */	
	@Autowired
	private MeterReadingService meterReadingService;

	@Autowired
	private IntervalBlockService intervalBlockService;
	
		public void setMeterReadingService(MeterReadingService meterReadingService) {
		this.meterReadingService = meterReadingService;
	}

	public void setIntervalBlockService(IntervalBlockService intervalBlockService) {
		this.intervalBlockService = intervalBlockService;
	}


}
