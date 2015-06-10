package org.energyos.espi.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.energyos.espi.common.domain.AtomPeriod;
import org.energyos.espi.common.domain.ElectricPowerQualitySummary;
import org.energyos.espi.common.domain.ElectricPowerUsageSummary;
import org.energyos.espi.common.domain.IdentifiedObject;
import org.energyos.espi.common.domain.IntervalBlock;
import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.domain.ReadingType;
import org.energyos.espi.common.domain.TimeConfiguration;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;

public class EntryTypeIterator {
	private Logger log = LoggerFactory.getLogger(EntryTypeIterator.class);
	private EntryBuilder builder;

    private Iterator<IdentifiedObject> resourceIds;
    @SuppressWarnings("rawtypes")

	private Iterator<Pair<Long, Class>> childIds = new ArrayList<Pair<Long, Class>>().iterator();
	private ResourceService resourceService;

	@SuppressWarnings("rawtypes")
	// TODO: fix the EntryTypeIterator Typing System
	private Class rootClass;
    
    private Long subscriptionId;

	public EntryTypeIterator(ResourceService resourceService, List<IdentifiedObject> ids, EntryBuilder builder) {
		this.resourceService = resourceService;
		this.resourceIds = ids.iterator();
		this.builder = builder;
	}

	@SuppressWarnings("rawtypes")
	// TODO: fix the EntryTypeIterator Typing System
	public EntryTypeIterator(ResourceService resourceService, List<IdentifiedObject> ids, Class clazz) {
		System.err.println(" EntryTypeIterator "+clazz );
		this.resourceService = resourceService;
		this.resourceIds = ids.iterator();
		builder = new EntryBuilder();
		rootClass = clazz;
	}

	public boolean hasNext() {
		return childIds.hasNext() || resourceIds.hasNext();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	// TODO: fix the EntryTypeIterator Typing System
	public EntryType next() {
		IdentifiedObject resource = null;
		if (childIds.hasNext()) {
			Pair<Long, Class> pair = childIds.next();
			log.debug(pair.getValue().getName() + " resource pair 11111 " + pair.getKey());
			// interval blocks are pre-loaded and keep in map,to work with
			// EntryTypeIterator all ids are added in childIds
			if (pair.getValue().getName().contains("IntervalBlock")) {
				if (blockcache.containsKey(pair.getKey())) {
					resource = blockcache.get(pair.getKey());
					blockcache.remove(pair.getKey());
				}
			} else {
				resource = resourceService.findById(pair.getKey(), pair.getValue());
			}
		} else {
			log.warn("Root class " + rootClass);
			resource = resourceService.findById(resourceIds.next().getId(), rootClass);

			updateChildIds(resource.getId());
		}
		return builder.buildEntry(resource);
	}

    // For the RESTful interfaces, we don't want to build the whole child structure, 
    // only the 1 resource is exported.
    //
    @SuppressWarnings({ "unchecked", "rawtypes" })
    // TODO: fix the EntryTypeIterator Typing System
	public EntryType nextEntry(Class resourceClass)  {
    	
        IdentifiedObject resource=resourceIds.next();
        //if uuid is not null then object already been loaded in current request...so do not need reloading
        if(resource.getUUID() ==null) {
        	resource = resourceService.findById(resource.getId(), resourceClass);
        }
        return builder.buildEntry(resource);
    }

    @SuppressWarnings("rawtypes")
    // TODO: fix the EntryTypeIterator Typing System
	private void updateChildIds(Long usagePointId) {
		// TODO: Deal with these Class warnings...

		List<Pair<Long, Class>> pairs = new ArrayList<>();
		try {

			for (IdentifiedObject id : resourceService.findAllIdsByUsagePointId(usagePointId, TimeConfiguration.class)) {
				pairs.add(new ImmutablePair<Long, Class>(id.getId(), TimeConfiguration.class));
			}

		} catch (EmptyResultDataAccessException ignore) {

		}
		try {

			for (IdentifiedObject id : resourceService.findAllIdsByUsagePointId(usagePointId, MeterReading.class)) {
				pairs.add(new ImmutablePair<Long, Class>(id.getId(), MeterReading.class));
			}

		} catch (EmptyResultDataAccessException ignore) {

		}
		blockcache.clear();
		try {
			log.debug(usagePointId + " Load  IntervalBlock " + exportFilter);
			HashMap<Long, MeterReading> lomcalmrmap = new HashMap<Long, MeterReading>();

			List<IntervalBlock> blocks = resourceService.findAllByUsagePointId(usagePointId, exportFilter);
			
			if(blocks!=null) {
				for (IntervalBlock block : blocks) {
					blockcache.put(block.getId(), block);
					if (block.getMeterReading() == null) {
						// associate meter reading
						MeterReading mr = null;
						if (lomcalmrmap.containsKey(block.getMeterReadingId())) {
							mr = lomcalmrmap.get(block.getMeterReadingId());
						} else {
							try {
								mr = resourceService.findById(block.getMeterReadingId(), MeterReading.class);
								lomcalmrmap.put(mr.getId(), mr);
							} catch (EmptyResultDataAccessException ignore) {
	
							}
						}
						block.setMeterReading(mr);
					} else {
						lomcalmrmap.put(block.getMeterReadingId(), block.getMeterReading());
					}
					pairs.add(new ImmutablePair<Long, Class>(block.getId(), IntervalBlock.class));
				}
			}
		} catch (EmptyResultDataAccessException ignore) {
			log.warn("Exception ", ignore);
			ignore.printStackTrace(System.err);
		} catch (Exception ignore) {
			log.warn("Exception ", ignore);
			ignore.printStackTrace(System.err);

		}

		try {
			for (IdentifiedObject id : resourceService.findAllIdsByUsagePointId(usagePointId, ElectricPowerUsageSummary.class)) {
				pairs.add(new ImmutablePair<Long, Class>(id.getId(), ElectricPowerUsageSummary.class));
			}
		} catch (EmptyResultDataAccessException ignore) {

		}
		try {
			for (IdentifiedObject id : resourceService.findAllIdsByUsagePointId(usagePointId, ElectricPowerQualitySummary.class)) {
				pairs.add(new ImmutablePair<Long, Class>(id.getId(), ElectricPowerQualitySummary.class));
			}

		} catch (EmptyResultDataAccessException ignore) {

		}
		try {
			for (IdentifiedObject id : resourceService.findAllIdsByUsagePointId(usagePointId, ReadingType.class)) {
				pairs.add(new ImmutablePair<Long, Class>(id.getId(), ReadingType.class));
			}

		} catch (EmptyResultDataAccessException ignore) {

		}
		childIds = pairs.iterator();
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public Long getSubscriptionId() {
		return this.subscriptionId;
	}
	/* LH customization starts here */
	private ExportFilter exportFilter;

	public ExportFilter getExportFilter() {
		return exportFilter;
	}

	public void setExportFilter(ExportFilter fileterPeriod) {
		this.exportFilter = fileterPeriod;
	}

	private HashMap<Long, IntervalBlock> blockcache = new HashMap<Long, IntervalBlock>();	
}
