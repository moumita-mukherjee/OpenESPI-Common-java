package org.energyos.espi.common.utils;

import java.util.ArrayList;
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
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.service.ResourceService;
import org.springframework.dao.EmptyResultDataAccessException;

public class EntryTypeIterator {

    private EntryBuilder builder;

    private Iterator<Long> resourceIds;
    @SuppressWarnings("rawtypes")

	private Iterator<Pair<Long, Class>> childIds = new ArrayList<Pair<Long, Class>>().iterator();
    private ResourceService resourceService;
    
    @SuppressWarnings("rawtypes")
    // TODO: fix the EntryTypeIterator Typing System
	private Class rootClass;

    public EntryTypeIterator(ResourceService resourceService, List<Long> ids, EntryBuilder builder) {
        this.resourceService = resourceService;
        this.resourceIds = ids.iterator();
        this.builder = builder;
    }

    @SuppressWarnings("rawtypes")
    // TODO: fix the EntryTypeIterator Typing System
	public EntryTypeIterator(ResourceService resourceService, List<Long> ids, Class clazz) {
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
	public EntryType next()  {
        IdentifiedObject resource;

        
        if(childIds.hasNext()) {
            Pair<Long, Class> pair = childIds.next();
            resource = resourceService.findById(pair.getKey(), pair.getValue());
            System.out.println( " resource next 11111 "+resource);
        } else {
            resource = resourceService.findById(resourceIds.next(), rootClass);
            updateChildIds(resource.getId());
            System.out.println( " resource next 2222 "+resource);
        }
        System.out.println( " resource next "+resource);
        return builder.build(resource);
    }

    // For the RESTful interfaces, we don't want to build the whole child structure, 
    // only the 1 resource is exported.
    //
    @SuppressWarnings({ "unchecked", "rawtypes" })
    // TODO: fix the EntryTypeIterator Typing System
	public EntryType nextEntry(Class resourceClass)  {
        IdentifiedObject resource;
        resource = resourceService.findById(resourceIds.next(), resourceClass);
        return builder.buildEntry(resource);
    }

    private AtomPeriod fileterPeriod;
    
    public AtomPeriod getFileterPeriod() {
		return fileterPeriod;
	}

	public void setFileterPeriod(AtomPeriod fileterPeriod) {
		this.fileterPeriod = fileterPeriod;
	}

	@SuppressWarnings("rawtypes")
    // TODO: fix the EntryTypeIterator Typing System
	private void updateChildIds(Long usagePointId) {
    	// TODO: Deal with these Class warnings...
    	
        List<Pair<Long, Class>> pairs = new ArrayList<>();       
        try {
        	System.out.println(" Load  TimeConfiguration");
        for(Long id : resourceService.findAllIdsByUsagePointId(usagePointId, TimeConfiguration.class)) {
            pairs.add(new ImmutablePair<Long, Class>(id, TimeConfiguration.class));
        }
        }catch(EmptyResultDataAccessException ignore) {
        	
        }
        try {
        	System.out.println(" Load  MeterReading");
        for(Long id : resourceService.findAllIdsByUsagePointId(usagePointId, MeterReading.class)) {
            pairs.add(new ImmutablePair<Long, Class>(id, MeterReading.class));
        }
        }catch(EmptyResultDataAccessException ignore) {
        	
        }
        try {
        	System.out.println(usagePointId+ " Load  IntervalBlock " +fileterPeriod);
        if(fileterPeriod!=null) {
        for(Long id : resourceService.findAllIdsByUsagePointId(usagePointId, fileterPeriod,IntervalBlock.class)) {
            pairs.add(new ImmutablePair<Long, Class>(id, IntervalBlock.class));
        }
        }else {        	
        	for(Long id : resourceService.findAllIdsByUsagePointId(usagePointId, IntervalBlock.class)) {
                pairs.add(new ImmutablePair<Long, Class>(id, IntervalBlock.class));
            }	
        }
        }catch(EmptyResultDataAccessException ignore) {
        	
        }
        try {
        for(Long id : resourceService.findAllIdsByUsagePointId(usagePointId, ElectricPowerUsageSummary.class)) {
            pairs.add(new ImmutablePair<Long, Class>(id, ElectricPowerUsageSummary.class));
        }
        }catch(EmptyResultDataAccessException ignore) {
        	
        }
        try {
        for(Long id : resourceService.findAllIdsByUsagePointId(usagePointId, ElectricPowerQualitySummary.class)) {
            pairs.add(new ImmutablePair<Long, Class>(id, ElectricPowerQualitySummary.class));
        }
        }catch(EmptyResultDataAccessException ignore) {
        	
        }
        try {
        for(Long id : resourceService.findAllIdsByUsagePointId(usagePointId, ReadingType.class)) {
            pairs.add(new ImmutablePair<Long, Class>(id, ReadingType.class));
        }
        }catch(EmptyResultDataAccessException ignore) {
        	
        }        
        childIds = pairs.iterator();
    }

    public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
    }
}
