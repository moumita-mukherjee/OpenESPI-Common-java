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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.energyos.espi.common.domain.IdentifiedObject;
import org.energyos.espi.common.domain.IntervalBlock;
import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.models.atom.ContentType;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.IntervalBlockRepository;
import org.energyos.espi.common.service.ImportService;
import org.energyos.espi.common.service.IntervalBlockService;
import org.energyos.espi.common.service.ResourceService;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.energyos.espi.common.utils.ExportFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IntervalBlockServiceImpl implements IntervalBlockService {
	
	private Logger log = LoggerFactory.getLogger(IntervalBlockServiceImpl.class);

	@Autowired
	protected IntervalBlockRepository intervalBlockRepository;

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private ImportService importService;

	@Override
	public List<IntervalBlock> findAllByMeterReadingId(Long meterReadingId) {
		return intervalBlockRepository.findAllByMeterReadingId(meterReadingId);
	}

	public void setRepository(IntervalBlockRepository repository) {
		this.intervalBlockRepository = repository;
	}

	@Override
	public String feedFor(List<IntervalBlock> intervalBlocks) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IntervalBlock findById(long retailCustomerId, long usagePointId, long meterReadingId, long intervalBlockId) {
		return intervalBlockRepository.findById(intervalBlockId);
	}

	@Override
	public String entryFor(IntervalBlock intervalBlock) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void associateByUUID(MeterReading meterReading, UUID uuid) {
		// TODO Auto-generated method stub
		intervalBlockRepository.associateByUUID(meterReading, uuid);
	}

	@Override
	public void delete(IntervalBlock intervalBlock) {
		intervalBlockRepository.deleteById(intervalBlock.getId());
	}

	@Override
	public List<IntervalBlock> findAllByMeterReading(MeterReading meterReading) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IntervalBlock findByURI(String uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void persist(IntervalBlock intervalBlock) {
		intervalBlockRepository.persist(intervalBlock);
	}

	@Override
	public EntryTypeIterator findEntryTypeIterator(Long retailCustomerId, Long usagePointId,
			Long meterReadingId) {
		EntryTypeIterator result = null;
		try {
			// TODO - this is sub-optimal (but defers the need to understand creation of an EntryType
			List<IdentifiedObject> temp = new ArrayList<IdentifiedObject>();
			temp = resourceService.findAllIds(IntervalBlock.class);
			result = (new EntryTypeIterator(resourceService, temp, IntervalBlock.class));
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if
			// it's not found
			result = null;
		}
		return result;
	}

	@Override
	public EntryType findEntryType(Long retailCustomerId, Long usagePointId,
			Long meterReadingId, Long intervalBlockId) {
		EntryType result = null;
		try {
			// TODO - this is sub-optimal (but defers the need to understan creation of an EntryType
			List<IdentifiedObject> temp = new ArrayList<IdentifiedObject>();
			temp.add(new IdentifiedObject(intervalBlockId));
			result = (new EntryTypeIterator(resourceService, temp, IntervalBlock.class)).nextEntry(IntervalBlock.class);
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if
			// it's not found
			result = null;
		}
		return result;
	}

	@Override
	public void add(IntervalBlock intervalBlock) {
		// TODO Auto-generated method stub

	}

	@Override
	public IntervalBlock importResource(InputStream stream) throws Exception{		
			importService.importData(stream, null);
			List<EntryType> entries=importService.getEntries();
			if(entries!=null && entries.size()>0 ) {			
				EntryType entry = entries.get(0);
				List<IntervalBlock> intervalBlocks = entry.getContent().getIntervalBlocks();
				if(intervalBlocks==null ||intervalBlocks.isEmpty()) {
					log.info("IntervalBlocks is empty");
				}else {
					return intervalBlocks.get(0);
				}
			}		
		return null;
	}

	@Override
	public IntervalBlock findById(long intervalBlockId) {
		intervalBlockRepository.findById(intervalBlockId);
		return null;
	}
   
	public void setIntervalBlockRepository(IntervalBlockRepository intervalBlockRepository) {
        this.intervalBlockRepository = intervalBlockRepository;
   }

   public IntervalBlockRepository getIntervalBlockRepository () {
        return this.intervalBlockRepository;
   }
   public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
   }

   public ResourceService getResourceService () {
        return this.resourceService;
   }
   public void setImportService(ImportService importService) {
        this.importService = importService;
   }

	public ImportService getImportService() {
		return this.importService;
	}
	/* LH customization starts here */
	@Override
	public IntervalBlock merge(IntervalBlock intervalBlock) {
		return intervalBlockRepository.merge(intervalBlock);
	}
		@Override
	public List<IntervalBlock> findIntervalBlocksByPeriod(Long meterReadingId, ExportFilter ap) {
		return intervalBlockRepository.findIntervalBlocksByPeriod(meterReadingId, ap);
	}

	@Override
	public void flush() {

		intervalBlockRepository.flush();
	}

	@Override
	public IntervalBlock findByUUID(UUID uuid) {
		System.out.println("service findByUUID ..."+intervalBlockRepository);
		return intervalBlockRepository.findByUUID(uuid);
	}

}
