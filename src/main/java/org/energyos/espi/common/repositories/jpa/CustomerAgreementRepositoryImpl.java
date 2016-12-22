package org.energyos.espi.common.repositories.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.energyos.espi.common.domain.CustomerAgreement;
import org.energyos.espi.common.repositories.CustomerAgreementRepository;
import org.springframework.transaction.annotation.Transactional;

public class CustomerAgreementRepositoryImpl implements
		CustomerAgreementRepository {

	@PersistenceContext(unitName = "pu-retailCustomer")
	protected EntityManager em;

	@Override
	public CustomerAgreement findById(Long customerAgreementId) {
		return (CustomerAgreement) em
				.createNamedQuery(CustomerAgreement.QUERY_FIND_BY_ID)
				.setParameter("id", customerAgreementId).getSingleResult();
	}
	
	@Override
	public CustomerAgreement findByRetailCustomerIdCustomerIdAccountIdAgreementId(Long retailCustomerId, Long customerId, Long accountId, Long agreementId) throws Exception{
		return (CustomerAgreement) em
				.createNamedQuery(CustomerAgreement.QUERY_FIND_BY_RETAILCUSTOMER_ID_CUSTOMER_ID_ACCOUNT_ID_AGREEMENT_ID)
				.setParameter("retailCustomerId", retailCustomerId)
				.setParameter("customerId", customerId)
				.setParameter("customerAccountId", accountId)
				.setParameter("agreementId", agreementId)
				.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerAgreement> findByCustomerIdAccountId(Long customerId,
			Long customerAccountId) {
		return (List<CustomerAgreement>) em
				.createNamedQuery(
						CustomerAgreement.QUERY_FIND_BY_CUSTOMER_ID_ACCOUNT_ID)
				.setParameter("customerId", customerId)
				.setParameter("customerAccountId", customerAccountId)
				.getResultList();
	}
	
	
	@Override
	public List<CustomerAgreement> findByRetailCustomerIdCustomerIdAccountId(Long retailCustomerId, Long customerId,
			Long accountId) throws Exception{
		return (List<CustomerAgreement>) em
				.createNamedQuery(
						CustomerAgreement.QUERY_FIND_BY_RETAILCUSTOMER_ID_CUSTOMER_ID_ACCOUNT_ID)
				.setParameter("retailCustomerId", retailCustomerId)
				.setParameter("customerId", customerId)
				.setParameter("accountId", accountId)
				.getResultList();
	}

	@Transactional
	@Override
	public void deleteById(Long id) throws Exception{
		em.joinTransaction();
		CustomerAgreement customerAgreement = findById(id);
		em.remove(customerAgreement);
		em.flush();

	}

	@Transactional
	@Override
	public void createCustomerAgreement(CustomerAgreement customerAgreement) throws Exception{
		em.joinTransaction();
		em.persist(customerAgreement);
		em.flush();

	}

	@Transactional
	@Override
	public void mergeCustomerAgreement(CustomerAgreement customerAgreement, CustomerAgreement existingCustAgg) throws Exception{
		em.joinTransaction();
		
		if (existingCustAgg != null && customerAgreement!= null)
			getMergedCustomerAgreement(existingCustAgg,
					customerAgreement);

		em.merge(existingCustAgg);
		em.flush();

	}

	private void getMergedCustomerAgreement(
			CustomerAgreement existingCustomerAgreement,
			CustomerAgreement customerAgreement) {

		if (customerAgreement.getDescription() != null)
			existingCustomerAgreement.setDescription(customerAgreement
					.getDescription());
		if (customerAgreement.getName() != null)
			existingCustomerAgreement.setName(customerAgreement.getName());
		existingCustomerAgreement.setEnabled(customerAgreement.isEnabled());
		existingCustomerAgreement.setCreatedDateTime(customerAgreement
				.getCreatedDateTime());
		existingCustomerAgreement.setPricingStructures(customerAgreement
				.getPricingStructures());
		existingCustomerAgreement.setLoadMgmt(customerAgreement.getLoadMgmt());
		existingCustomerAgreement.setDemandResponsePrograms(customerAgreement
				.getDemandResponsePrograms());

	}

	@Override
	@Transactional
	public void delete(Long retailCustomerId, Long customerId, Long customerAccountId, Long customerAgreementId) throws Exception {
		em.joinTransaction();
		CustomerAgreement customerAgreement = findByRetailCustomerIdCustomerIdAccountIdAgreementId(retailCustomerId, customerId, customerAccountId, customerAgreementId);
		em.remove(customerAgreement);
		em.flush();
		
	}

}
