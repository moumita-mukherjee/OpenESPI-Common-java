/**
 * 
 */
package org.energyos.espi.common.repositories.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.energyos.espi.common.domain.ServiceSupplier;
import org.energyos.espi.common.repositories.ServiceSupplierRepository;
import org.springframework.transaction.annotation.Transactional;


public class ServiceSupplierRepositoryImpl implements ServiceSupplierRepository {
	@PersistenceContext(unitName = "pu-retailCustomer")
	protected EntityManager em;

	@Override
	public ServiceSupplier findById(Long id) {
		return (ServiceSupplier) em
				.createNamedQuery(ServiceSupplier.QUERY_FIND_BY_ID)
				.setParameter("id", id).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	
	public List<ServiceSupplier> findByCustomerIdAccountIdAgreementId(
			Long customerId, Long customerAccountId, Long customerAgreementId) {
		return (List<ServiceSupplier>) em
				.createNamedQuery(
						ServiceSupplier.QUERY_FIND_BY_CUSTOMER_ID_CUSTOMER_ACCOUNT_ID_CUSTOMER_AGREEMENT_ID)
				.setParameter("customerId", customerId)
				.setParameter("customerAccountId", customerAccountId)
				.setParameter("customerAgreementId", customerAgreementId)
				.getResultList();

	}

	@Transactional
	@Override
	public void deleteById(Long id) {
		em.joinTransaction();
		ServiceSupplier serviceSupplier = findById(id);
		em.remove(serviceSupplier);
		em.flush();

	}

	@Transactional
	@Override
	public void createServiceSupplier(ServiceSupplier serviceSupplier) {
		em.joinTransaction();
		em.persist(serviceSupplier);
		em.flush();

	}

	@Transactional
	@Override
	public void mergeServiceSupplier(ServiceSupplier serviceSupplier) {
		em.joinTransaction();
		ServiceSupplier existingServiceSupplier = null;
		if (serviceSupplier != null)
			existingServiceSupplier = findById(serviceSupplier.getId());
		if (existingServiceSupplier != null)
			getMergedServiceSupplier(existingServiceSupplier, serviceSupplier);

		em.merge(existingServiceSupplier);
		em.flush();

	}

	private void getMergedServiceSupplier(
			ServiceSupplier existingServiceSupplier,
			ServiceSupplier serviceSupplier) {

		if (serviceSupplier.getDescription() != null)
			existingServiceSupplier.setDescription(serviceSupplier
					.getDescription());
		if (serviceSupplier.getName() != null)
			existingServiceSupplier.setName(serviceSupplier.getName());
		existingServiceSupplier.setEnabled(serviceSupplier.isEnabled());
		existingServiceSupplier.setKind(serviceSupplier.getKind());
		existingServiceSupplier.setIssuerIdentificationNumber(serviceSupplier
				.getIssuerIdentificationNumber());

	}
}
