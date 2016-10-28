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

	@Transactional
	@Override
	public void deleteById(Long id) {
		em.joinTransaction();
		CustomerAgreement customerAgreement = findById(id);
		em.remove(customerAgreement);
		em.flush();

	}

	@Transactional
	@Override
	public void createCustomerAgreement(CustomerAgreement customerAgreement) {
		em.joinTransaction();
		em.persist(customerAgreement);
		em.flush();

	}

	@Transactional
	@Override
	public void mergeCustomerAgreement(CustomerAgreement customerAgreement) {
		em.joinTransaction();
		CustomerAgreement existingCustomerAgreement = null;
		if (customerAgreement != null)
			existingCustomerAgreement = findById(customerAgreement.getId());
		if (existingCustomerAgreement != null)
			getMergedCustomerAgreement(existingCustomerAgreement,
					customerAgreement);

		em.merge(existingCustomerAgreement);
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

}
