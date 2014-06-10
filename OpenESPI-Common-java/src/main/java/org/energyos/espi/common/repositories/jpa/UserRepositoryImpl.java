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

import org.energyos.espi.common.domain.User;
import org.energyos.espi.common.repositories.RetailCustomerRepository;
import org.energyos.espi.common.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = { javax.xml.bind.JAXBException.class }, noRollbackFor = {
		javax.persistence.NoResultException.class,
		org.springframework.dao.EmptyResultDataAccessException.class })
public class UserRepositoryImpl implements UserRepository {

	@PersistenceContext(unitName = "energy")
	protected EntityManager em;

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Autowired
	RetailCustomerRepository retailCustomerRepository;

	public RetailCustomerRepository getRetailCustomerRepository() {
		return retailCustomerRepository;
	}

	public void setRetailCustomerRepository(
			RetailCustomerRepository retailCustomerRepository) {
		this.retailCustomerRepository = retailCustomerRepository;
	}

	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		return (List<User>) this.em.createNamedQuery(User.QUERY_FIND_ALL)
				.getResultList();
	}

	@Transactional(rollbackFor = { javax.xml.bind.JAXBException.class }, noRollbackFor = {
			javax.persistence.NoResultException.class,
			org.springframework.dao.EmptyResultDataAccessException.class })
	public void persist(User customer) {
		this.em.persist(customer);
	}

	@Transactional(rollbackFor = { javax.xml.bind.JAXBException.class }, noRollbackFor = {
			javax.persistence.NoResultException.class,
			org.springframework.dao.EmptyResultDataAccessException.class })
	public void merge(User customer) {
		this.em.merge(customer);
	}

	@Override
	public User findById(Long id) {
		return this.em.find(User.class, id);
	}

	@Override
	public User findByUsername(String username) {
		User user = (User) this.em
				.createNamedQuery(User.QUERY_FIND_BY_USERNAME)
				.setParameter("username", username).getSingleResult();
		try {
			if (user != null) {
				user.setRetailCustomer(retailCustomerRepository.findByLink(user
						.getSelfLink()));
			}
		} catch (EmptyResultDataAccessException ignore) {

		}
		return user;

	}

	@Override
	public User findById(String id) {
		return em.find(User.class, id);
	}

	@Transactional(rollbackFor = { javax.xml.bind.JAXBException.class }, noRollbackFor = {
			javax.persistence.NoResultException.class,
			org.springframework.dao.EmptyResultDataAccessException.class })
	@Override
	public void deleteById(Long id) {
		User rc = findById(id);
		em.remove(em.contains(rc) ? rc : em.merge(rc));
	}
}
