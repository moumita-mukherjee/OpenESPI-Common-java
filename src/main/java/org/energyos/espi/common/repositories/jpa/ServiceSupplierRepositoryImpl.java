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
	
	@Override
	public ServiceSupplier findByRetailCustomerIdCustomerIdAccountIdAgreementIdServiceSupplierId(Long retailCustomerId,
			Long customerId, Long accountId, Long agreementId, Long serviceSupplierId) throws Exception{
		return (ServiceSupplier) em
				.createNamedQuery(ServiceSupplier.QUERY_FIND_BY_RETAILCUSTOMER_ID_CUSTOMER_ID_ACCOUNT_ID_AGREEMENT_ID_SUPPLIER_ID)
				.setParameter("retailCustomerId", retailCustomerId)
				.setParameter("customerId", customerId)
				.setParameter("accountId", accountId)
				.setParameter("agreementId", agreementId)
				.setParameter("serviceSupplierId", serviceSupplierId)
				.getSingleResult();
	}
	
	
	
    @Override
	
	public List<ServiceSupplier> findByRetailCustomerIdCustomerIdAccountIdAgreementId(Long retailCustomerId,
			Long customerId, Long accountId, Long agreementId) throws Exception {
		return (List<ServiceSupplier>) em
				.createNamedQuery(
						ServiceSupplier.QUERY_FIND_BY_RETAILCUSTOMER_ID_CUSTOMER_ID_ACCOUNT_ID_AGREEMENT_ID)
				.setParameter("retailCustomerId", retailCustomerId)
				.setParameter("customerId", customerId)
				.setParameter("accountId", accountId)
				.setParameter("agreementId", agreementId)
				.getResultList();

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
	public void deleteById(Long id) throws Exception {
		em.joinTransaction();
		ServiceSupplier serviceSupplier = findById(id);
		em.remove(serviceSupplier);
		em.flush();

	}

	@Transactional
	@Override
	public void createServiceSupplier(ServiceSupplier serviceSupplier) throws Exception {
		em.joinTransaction();
		em.persist(serviceSupplier);
		em.flush();

	}

	@Transactional
	@Override
	public void mergeServiceSupplier(ServiceSupplier serviceSupplier,ServiceSupplier existingServiceSupplier) throws Exception {
		em.joinTransaction();
		
		
		if (existingServiceSupplier != null && serviceSupplier!=null)
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

	@Override
	@Transactional
	public void delete(Long retailCustomerId, Long customerId, Long customerAccountId, Long customerAgreementId, Long serviceSupplierId) throws Exception {
		em.joinTransaction();
		ServiceSupplier sup = findByRetailCustomerIdCustomerIdAccountIdAgreementIdServiceSupplierId(retailCustomerId, customerId, customerAccountId, customerAgreementId, serviceSupplierId);
		em.remove(sup);
		em.flush();
		
	}
}
