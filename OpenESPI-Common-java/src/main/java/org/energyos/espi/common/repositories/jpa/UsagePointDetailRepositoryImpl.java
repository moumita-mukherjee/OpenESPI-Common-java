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

package org.energyos.espi.common.repositories.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.energyos.espi.common.domain.UsagePointDetail;
import org.energyos.espi.common.repositories.UsagePointDetailRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = { javax.xml.bind.JAXBException.class }, noRollbackFor = {
		javax.persistence.NoResultException.class,
		org.springframework.dao.EmptyResultDataAccessException.class })
public class UsagePointDetailRepositoryImpl implements
		UsagePointDetailRepository {

	@PersistenceContext(unitName = "energy")
	protected EntityManager em;

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@SuppressWarnings("unchecked")
	public List<UsagePointDetail> findAllByRetailCustomerId(String id) {
		return (List<UsagePointDetail>) this.em
				.createNamedQuery(
						UsagePointDetail.QUERY_FIND_ALL_BY_RETAIL_CUSTOMER_ID)
				.setParameter("retailCustomerId", id).getResultList();
	}

	@Override
	public UsagePointDetail findBySelfRef(String customerId, String selfHref) {
		return (UsagePointDetail) this.em
				.createNamedQuery(UsagePointDetail.QUERY_FIND_BY_REF)
				.setParameter("customerId", customerId)
				.setParameter("selfHref", selfHref).getSingleResult();

	}
}