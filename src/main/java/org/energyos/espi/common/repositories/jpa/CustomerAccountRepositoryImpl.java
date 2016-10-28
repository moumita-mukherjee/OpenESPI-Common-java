package org.energyos.espi.common.repositories.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerAccount> findByCustomerId(Long customerId) {
		return (List<CustomerAccount>) em
				.createNamedQuery(CustomerAccount.QUERY_FIND_BY_CUSTOMER_ID)
				.setParameter("customerId", customerId).getResultList();
	}

	@Transactional
	@Override
	public void deleteById(Long id) {
		em.joinTransaction();
		CustomerAccount customerAccount = findById(id);
		em.remove(customerAccount);
		em.flush();

	}

	@Transactional
	@Override
	public void createCustomerAccount(CustomerAccount customerAccount) {
		em.joinTransaction();
		em.persist(customerAccount);
		em.flush();

	}

	@Transactional
	@Override
	public void mergeCustomerAccount(CustomerAccount customerAccount) {
		em.joinTransaction();
		CustomerAccount existingCustomerAccount = null;
		if (customerAccount != null)
			existingCustomerAccount = findById(customerAccount.getId());
		if (existingCustomerAccount != null)
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
}
