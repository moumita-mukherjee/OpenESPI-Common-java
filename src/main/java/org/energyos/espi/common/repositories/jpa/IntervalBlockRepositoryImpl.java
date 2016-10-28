/*
 * Copyright 2013 EnergyOS.org
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

package org.energyos.espi.common.repositories.jpa;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.eclipse.jetty.util.log.Log;
import org.energyos.espi.common.domain.AtomPeriod;
import org.energyos.espi.common.domain.IntervalBlock;
import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.repositories.IntervalBlockRepository;
import org.energyos.espi.common.utils.AtomMarshallerListener;
import org.energyos.espi.common.utils.ExportFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional (rollbackFor= {javax.xml.bind.JAXBException.class}, 
                noRollbackFor = {javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })
public class IntervalBlockRepositoryImpl implements IntervalBlockRepository {
	
	private Logger log = LoggerFactory.getLogger(IntervalBlockRepositoryImpl.class);

	@PersistenceContext(unitName = "pu-usage")
	protected EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
    public List<IntervalBlock> findAllByMeterReadingId(Long meterReadingId) {
        return (List<IntervalBlock>)this.em.createNamedQuery(IntervalBlock.QUERY_ALL_BY_METER_READING_ID)
                .setParameter("meterReadingId", meterReadingId).getResultList();
    }

	@Override
	public IntervalBlock findById(Long intervalBlockId) {
		return em.find(IntervalBlock.class, intervalBlockId);
	}

	@Override
        @Transactional (rollbackFor= {javax.xml.bind.JAXBException.class}, 
                noRollbackFor = {javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })

	public void persist(IntervalBlock intervalBlock) {
		em.persist(intervalBlock);

	}

	@Override
	public IntervalBlock findByUUID(UUID uuid) {
		return (IntervalBlock) em.createNamedQuery(IntervalBlock.QUERY_FIND_BY_UUID)
                .setParameter("uuid", uuid.toString().toUpperCase())
                .getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> findAllIds() {
		List<Long> temp;
    	temp = (List<Long>)this.em.createNamedQuery(IntervalBlock.QUERY_FIND_ALL_IDS)
        .getResultList();
		return temp;
	}

	@Override
	@Transactional (rollbackFor= {javax.xml.bind.JAXBException.class}, 
                noRollbackFor = {javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })

	public void deleteById(Long id) {
		IntervalBlock ib = findById(id);
		MeterReading mr = ib.getMeterReading();
		if (mr != null) {
			mr.removeIntervalBlock(ib);
			em.persist(em.contains(mr) ? mr : em.merge(mr));
		}
		em.remove(em.contains(ib) ? ib : em.merge(ib));
	}

	@Override
	public void createOrReplaceByUUID(IntervalBlock intervalBlock) {
		// TODO Auto-generated method stub

	}

	/* LH customization starts here */
	@Override
	@Transactional(rollbackFor = { javax.xml.bind.JAXBException.class }, noRollbackFor = {
			javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })
	public void associateByUUID(MeterReading meterReading, UUID uuid) {
		Log.info("Entering associateByUUID method>>");
		try {
			IntervalBlock existingIntervalBlock = findByUUID(uuid);
			existingIntervalBlock.setMeterReading(meterReading);
			// DJ
			if (meterReading != null) {
				existingIntervalBlock.setMeterReadingId(meterReading.getId());
			}
			persist(existingIntervalBlock);
		} catch (NoResultException e) {
			e.printStackTrace();
			
		}
	}

	@Override
	public void flush() {
		em.flush();
	}

	public List<IntervalBlock> findIntervalBlocksByUsagePoint(Long usagePointId, ExportFilter ap) {
		return null;
	}
		@SuppressWarnings("unchecked")
	@Override
	public List<IntervalBlock> findIntervalBlocksByPeriod(Long meterReadingId, ExportFilter ap) {
		return (List<IntervalBlock>) this.em.createNamedQuery(IntervalBlock.QUERY_FIND_BY_PERIOD)
				.setParameter("meterReadingId", meterReadingId).setParameter("publishedMin", ap.getFilterPeriod().getPublishedMin())
				.setParameter("publishedMax", ap.getFilterPeriod().getPublishedMax()).setParameter("updatedMin", ap.getFilterPeriod().getUpdatedMin())
				.setParameter("updatedMax", ap.getFilterPeriod().getUpdatedMax()).setParameter("usageMin", ap.getFilterPeriod().getUsageMin())
				.setParameter("usageMax", ap.getFilterPeriod().getUsageMax()).getResultList();

	}
		@Override
	@Transactional(rollbackFor = { javax.xml.bind.JAXBException.class }, noRollbackFor = {
			javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })
	public IntervalBlock merge(IntervalBlock intervalBlock) {
		return em.merge(intervalBlock);

	}

}
