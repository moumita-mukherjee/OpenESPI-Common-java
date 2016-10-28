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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.energyos.espi.common.domain.AtomPeriod;
import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.ElectricPowerQualitySummary;
import org.energyos.espi.common.domain.ElectricPowerUsageSummary;
import org.energyos.espi.common.domain.IdentifiedObject;
import org.energyos.espi.common.domain.IntervalBlock;
import org.energyos.espi.common.domain.Linkable;
import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.domain.ReadingType;
import org.energyos.espi.common.domain.Subscription;
import org.energyos.espi.common.domain.TimeConfiguration;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.ResourceRepository;
import org.energyos.espi.common.service.ResourceService;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.energyos.espi.common.utils.ExportFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResourceServiceImpl implements ResourceService {
		
    @Autowired
    private ResourceRepository repository;
    
    /**
     * A private Map of Key:Value strings to hold the
     * dynamic configuration.
     * 
     * SFTPCacheRoot: "./pendingdelivery/"
     * AutomaticNotificationPropogation: [true | false]
     * 
     */
    private Map<String, String> params;

	@Override
	public void persist(IdentifiedObject resource) {
		repository.persist(resource);
	}

	@Override
	public List<IdentifiedObject> findByAllParentsHref(String relatedHref, Linkable linkable) {
		try {

			if (linkable instanceof UsagePoint) {
				return new ArrayList<>();
			} else {
				return repository.findAllParentsByRelatedHref(relatedHref, linkable);
			}
		} catch (Exception e) {

			return new ArrayList<>();
		}
	}

	@Override
	public List<IdentifiedObject> findAllRelated(Linkable linkable) {
		List<IdentifiedObject> temp = repository.findAllRelated(linkable);
		return temp;
	}

    /**
     * Returns an list of the resources contained in the primary children collection
     * of the a parent resource. The resources are returned lazily within a transactional context
     * allowing for persistent mutation.
     *
     * @param  Long parentResourceID used to retrieve the Resource
     * @param  Class the class of the parent resource
     * @return      List<Resources> of class appropriate to the children of the parent resource
     * @see         IdentifiedObject and Resource
     */
    public <T extends IdentifiedObject> List<IdentifiedObject> findAllContainedChildren(Long id, Class <T> parentClass) {
    	List<IdentifiedObject> result = new ArrayList<IdentifiedObject>();
        
        if (UsagePoint.class.equals(parentClass)) {
        	// the children are the MeterReadings
        	UsagePoint p = findById(id, UsagePoint.class);
        	for (MeterReading o : p.getMeterReadings()) {
        		result.add((IdentifiedObject) o);
        	}
        }
        
        if (MeterReading.class.equals(parentClass)) {
        	// the children are the MeterReadings
        	MeterReading p = findById(id, MeterReading.class);
        	for (IntervalBlock o : p.getIntervalBlocks()) {
        		result.add((IdentifiedObject) o);
        	}
        }
        
        if (ReadingType.class.equals(parentClass)) {
           // ReadingType doesn't have any	
        }
        
        if (IntervalBlock.class.equals(parentClass)) {
        	// IntervalBlock doesn't have any IdentfiedObject children
        	// but we may want to return the IntervalReadings
        }
        
        if (ElectricPowerQualitySummary.class.equals(parentClass)) {
        	// ElectricPowerQualitySummary doesn't have any	
        }
        
        if (ElectricPowerUsageSummary.class.equals(parentClass)) {
        	// ElectricPowerUsageSummary doesn't have any	
        }
        
        
        if (TimeConfiguration.class.equals(parentClass)) {
        	// the children are the UsagePoints
        	// TODO: find all usage points in this local time
        }
        
        if (Subscription.class.equals(parentClass)) {
        	// the children are the Subscription
        	Subscription p = findById(id, Subscription.class);
        	for (UsagePoint o : p.getUsagePoints()) {
        		result.add((IdentifiedObject) o);
        	}
        }
        
        if (Authorization.class.equals(parentClass)) {
        	// Authorizations have no children (yet)
        }
        
        if (MeterReading.class.equals(parentClass)) {
        	
        }
        
    	return result;
    	
    }
    
    @Override
    public <T> T findByUUID(UUID uuid, Class<T> clazz) {
        return repository.findByUUID(uuid, clazz);
    }

	@Override
	@Transactional(readOnly = true)
	public <T extends IdentifiedObject> T testById(Long id, Class<T> clazz) {
		return repository.findById(id, clazz);
	}

	@Override
	public <T extends IdentifiedObject> T findById(Long id, Class<T> clazz) {
		return repository.findById(id, clazz);
	}

	@Override
	public <T extends IdentifiedObject> List<IdentifiedObject> findAllIds(Class<T> clazz) {
		return repository.findAllIds(clazz);
	}
	@Override
	public <T extends IdentifiedObject> List<IdentifiedObject> findAllIds(Class<T> clazz,ExportFilter exportFilter) {
		return repository.findAllIds(clazz,exportFilter);
	}


    @Override
    public <T extends IdentifiedObject> List<IdentifiedObject> findAllIdsByUsagePointId(Long id, Class<T> clazz) {
        return repository.findAllIdsByUsagePointId(id, clazz);
    }

    // XPath Accessors
    //
    
	@Override
	public <T extends IdentifiedObject> List<IdentifiedObject> findAllIdsByXPath(Class<T> clazz) {
		return repository.findAllIdsByXPath(clazz);
	}

	@Override
	public <T extends IdentifiedObject> List<IdentifiedObject> findAllIdsByXPath(Long id1,
			Class<T> clazz) {
		return repository.findAllIdsByXPath(id1, clazz);
	}

	@Override
	public <T extends IdentifiedObject> List<IdentifiedObject> findAllIdsByXPath(Long id1,
			Long id2, Class<T> clazz) {
		return repository.findAllIdsByXPath(id1, id2, clazz);
	}

	@Override
	public <T extends IdentifiedObject> List<IdentifiedObject> findAllIdsByXPath(Long id1,
			Long id2, Long id3, Class<T> clazz,ExportFilter exportFilter) {
		return repository.findAllIdsByXPath(id1, id2, id3, clazz,exportFilter);
	}

	@Override
	public <T extends IdentifiedObject> IdentifiedObject findIdByXPath(Long id1,
			Class<T> clazz) {
		return repository.findIdByXPath(id1, clazz);
	}

	@Override
	public <T extends IdentifiedObject> IdentifiedObject findIdByXPath(Long id1, Long id2,
			Class<T> clazz) {
		return repository.findIdByXPath(id1, id2, clazz);
	}

	@Override
	public <T extends IdentifiedObject> IdentifiedObject findIdByXPath(Long id1, Long id2,
			Long id3, Class<T> clazz) {
		return repository.findIdByXPath(id1, id2, id3, clazz);
	}

	@Override
	public <T extends IdentifiedObject> IdentifiedObject findIdByXPath(Long id1, Long id2,
			Long id3, Long id4, Class<T> clazz) {
		return repository.findIdByXPath(id1, id2, id3, id4, clazz);
	}

	public void setRepository(ResourceRepository repository) {
		this.repository = repository;
	}
	@Override
	public <T extends IdentifiedObject> EntryTypeIterator findEntryTypeIterator(Class<T> clazz) {
		List<IdentifiedObject> idList = repository.findAllIds(clazz);
		return findEntryTypeIterator(idList, clazz);
	}


	@Override
	public <T extends IdentifiedObject> EntryTypeIterator findEntryTypeIterator(Class<T> clazz,ExportFilter exportFilter) {
		List<IdentifiedObject> idList = repository.findAllIds(clazz,exportFilter);
		return findEntryTypeIterator(idList, clazz);
	}

	@Override
	public <T extends IdentifiedObject> EntryTypeIterator findEntryTypeIterator(List<IdentifiedObject> ids, Class<T> clazz) {
		List<IdentifiedObject> idList = ids;
		EntryTypeIterator result = null;
		try {
			result = (new EntryTypeIterator(this, idList, clazz));
		} catch (Exception e) {
			result = null;
		}
		return result;
	}

	@Override
	public <T extends IdentifiedObject> EntryType findEntryType(long id1, Class<T> clazz) {
		EntryType result = null;
		try {
			List<IdentifiedObject> temp = new ArrayList<IdentifiedObject>();
			temp.add(new IdentifiedObject(id1));
			result = (new EntryTypeIterator(this, temp, clazz)).nextEntry(clazz);
		} catch (Exception e) {
			result = null;
		}
		return result;
	}

	@Override
	public <T extends IdentifiedObject> T findByResourceUri(String uri,
			Class<T> clazz) {
		return repository.findByResourceUri(uri, clazz);
	}

	@Override
	public void flush() {

		repository.flush();
	}

	@Transactional
	@Override
	public <T extends IdentifiedObject> void deleteById(Long id, Class<T> clazz) {

		repository.deleteById(id, clazz);
	}

	@Transactional
	@Override
	public <T extends IdentifiedObject> void deleteByXPathId(Long id1,
			Long id2, Class<T> clazz) {
		repository.deleteByXPathId(id1, id2, clazz);

	}

	@Transactional
	@Override
	public <T extends IdentifiedObject> void deleteByXPathId(Long id1,
			Long id2, Long id3, Class<T> clazz) {
		repository.deleteByXPathId(id1, id2, id3, clazz);

	}

	@Transactional
	@Override
	public <T extends IdentifiedObject> void deleteByXPathId(Long id1,
			Long id2, Long id3, Long id4, Class<T> clazz) {
		repository.deleteByXPathId(id1, id2, id3, id4, clazz);

	}

	@Transactional
	@Override
	public <T extends IdentifiedObject> T merge(IdentifiedObject resource) {
		return repository.merge(resource);

	}
	
    public void setResourceRepository(ResourceRepository repository) {
        this.repository = repository;     
   }

   public ResourceRepository getResourceRepository () {
        return this.repository;
   }

	@Override
	public void setConfigurations(Map<String, String> params) {
		this.params = params;
	}

	@Override
	public String getConfiguration(String key) {
		return this.params.get(key);
	}

	@Override
	public void setConfiguration(String key, String value) {
		this.params.put(key, value);
	}

	@Override
	public Map<String, String> getConfigurations()  {
		return this.params;
	}

	/* LH customization starts here */
	@Override
	public <T extends IdentifiedObject> List<IdentifiedObject> findAllIdsByUsagePointId(Long id,AtomPeriod ap, Class<T> clazz) {
		return repository.findAllIdsByUsagePointId(id, clazz);
	}
		
	@Override
	public List<IntervalBlock> findAllByMeterReadingId(Long id, AtomPeriod ap) {
		// not implemented
		return null;
	}

	@Override
	public List<IntervalBlock> findAllByUsagePointId(Long id, ExportFilter ap) {
		// not implemented
		return null;

	}

	@Override
	public void remove(IdentifiedObject entity) {
		this.repository.remove(entity);
	}

}
