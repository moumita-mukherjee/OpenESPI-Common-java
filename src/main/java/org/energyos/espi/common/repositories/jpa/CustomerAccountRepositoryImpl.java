package org.energyos.espi.common.repositories.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.energyos.espi.common.domain.Customer;
import org.energyos.espi.common.domain.CustomerAccount;
import org.energyos.espi.common.repositories.CustomerAccountRepository;
import org.springframework.transaction.annotation.Transactional;

public class CustomerAccountRepositoryImpl implements CustomerAccountRepository {

	@PersistenceContext(unitName = "pu-retailCustomer")
	protected EntityManager em;

	@Override
	public CustomerAccount findById(Long id) {
		return (CustomerAccount) em
				.createNamedQuery(CustomerAccount.QUERY_FIND_BY_ID)
				.setParameter("id", id).getSingleResult();
	}
	
	@Override
	public CustomerAccount findByRetailCustomerIdCustomerIdCustomerAccountId(Long retailCustomerId, Long customerId, Long accountId) throws Exception{
		
	
		return 
				
				(CustomerAccount) em
				.createNamedQuery(CustomerAccount.QUERY_FIND_BY_RETAILCUSTOMER_ID_CUSTOMER_ID_ACCOUNT_ID)
				.setParameter("retailCustomerId", retailCustomerId)
				.setParameter("customerId", customerId)
				.setParameter("accountId", accountId)
				.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerAccount> findByCustomerId(Long customerId, Long retailCustomerId) throws Exception{
		return (List<CustomerAccount>) em
				.createNamedQuery(CustomerAccount.QUERY_FIND_BY_CUSTOMER_ID)
				.setParameter("customerId", customerId)
				.setParameter("retailCustomerId", retailCustomerId)
				.getResultList();
		
	}

	@Transactional
	@Override
	public void deleteById(Long id) throws Exception{
		em.joinTransaction();
		CustomerAccount customerAccount = findById(id);
		em.remove(customerAccount);
		em.flush();

	}

	@Transactional
	@Override
	public void createCustomerAccount(CustomerAccount customerAccount) throws Exception{
		em.joinTransaction();
		try{em.persist(customerAccount);}catch(Exception e){e.printStackTrace(System.err);}
		em.flush();

	}

	@Transactional
	@Override
	public void mergeCustomerAccount(CustomerAccount customerAccount, CustomerAccount existingCustomerAccount) throws Exception{
		em.joinTransaction();
		
		if (existingCustomerAccount != null && customerAccount!= null)
			getMergedCustomerAccount(existingCustomerAccount, customerAccount);

		em.merge(existingCustomerAccount);
		em.flush();

	}

	private void getMergedCustomerAccount(
			CustomerAccount existingCustomerAccount,
			CustomerAccount customerAccount) {

		if (customerAccount.getDescription() != null)
			existingCustomerAccount.setDescription(customerAccount
					.getDescription());
		// existingCustomerAccount.setDescription(customerAccount.getDescription());
		if (customerAccount.getName() != null)
			existingCustomerAccount.setName(customerAccount.getName());
		existingCustomerAccount.setBudgetBill(customerAccount.getBudgetBill());
		existingCustomerAccount.setBillingCycle(customerAccount
				.getBillingCycle());
		existingCustomerAccount.setEnabled(customerAccount.isEnabled());
		existingCustomerAccount.setRevisionNumber(customerAccount
				.getRevisionNumber());
		existingCustomerAccount.setCreatedDateTime(customerAccount
				.getCreatedDateTime());
		existingCustomerAccount.setLastModifiedDateTime(customerAccount
				.getLastModifiedDateTime());
	}
	
	
	@Transactional
	@Override
	public void delete(Long retailCustomerId, Long customerId, Long customerAccountId) throws Exception{
		
		em.joinTransaction();
		CustomerAccount customerAccount = findByRetailCustomerIdCustomerIdCustomerAccountId(retailCustomerId, customerId, customerAccountId);
		em.remove(customerAccount);
		em.flush();

	}
}
