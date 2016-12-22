package org.energyos.espi.common.repositories.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.energyos.espi.common.domain.Customer;
import org.energyos.espi.common.repositories.CustomerRepository;
import org.springframework.transaction.annotation.Transactional;

public class CustomerRepositoryImpl implements CustomerRepository {

	@PersistenceContext(unitName = "pu-retailCustomer")
	protected EntityManager em;

	@Override
	public Customer findById(Long id) throws Exception {
		return (Customer) em.createNamedQuery(Customer.QUERY_FIND_BY_ID)
				.setParameter("id", id).getSingleResult();
	}

	
	@Override
	public Customer findByRetailCustomerIdCustomerId(Long retailCustomerId, Long id) throws Exception{
		return (Customer) em.createNamedQuery(Customer.QUERY_FIND_BY_RETAIL_CUSTOMER_ID_CUSTOMER_ID)
				.setParameter("retailCustomerId", retailCustomerId)
				.setParameter("id", id)
				.getSingleResult();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> findByRetailCustomerId(Long retailCustomerId) throws Exception {

		return (List<Customer>) em
				.createNamedQuery(Customer.QUERY_FIND_ALL_BY_RETAIL_CUSTOMER_ID)
				.setParameter("retailCustomerId", retailCustomerId)
				.getResultList();

	}

	@Transactional
	@Override
	public void deleteById(Long id) throws Exception {
		em.joinTransaction();
		Customer customer = findById(id);
		em.remove(customer);
		em.flush();

	}

	@Transactional
	@Override
	public void createCustomer(Customer customer) throws Exception {
		em.joinTransaction();
		em.persist(customer);
		em.flush();

	}

	@Transactional
	@Override
	public void mergeCustomer(Customer customer, Customer existingCustomer) throws Exception {
		
		if (existingCustomer != null && customer!=null )
			getMergedCustomer(existingCustomer, customer);
		em.joinTransaction();
		em.merge(existingCustomer);
		em.flush();

	}

	private void getMergedCustomer(Customer existingCustomer, Customer customer) {

		if (customer.getDescription() != null)
			existingCustomer.setDescription(customer.getDescription());
		if (customer.getName() != null)
			existingCustomer.setName(customer.getName());
		existingCustomer.setEnabled(customer.getEnabled());
		existingCustomer.setSupplierId(customer.getSupplierId());
		existingCustomer.setKind(customer.getKind());
		existingCustomer.setVip(customer.getVip());
		existingCustomer.setPucNumber(customer.getPucNumber());
		existingCustomer.setStatus(customer.getStatus());
		existingCustomer.setPriority(customer.getPriority());
		existingCustomer.setSpecialNeed(customer.getSpecialNeed());

	}
	
	@Transactional
	@Override
	public void delete(Long retailCustomerId, Long customerId) throws Exception {
		em.joinTransaction();
		Customer customer = findByRetailCustomerIdCustomerId(retailCustomerId, customerId);
		em.remove(customer);
		em.flush();

	}


}
