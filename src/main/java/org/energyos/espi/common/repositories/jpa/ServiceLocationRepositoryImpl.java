package org.energyos.espi.common.repositories.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.energyos.espi.common.domain.ServiceLocation;
import org.energyos.espi.common.repositories.ServiceLocationRepository;
import org.springframework.transaction.annotation.Transactional;

public class ServiceLocationRepositoryImpl implements ServiceLocationRepository {

	@PersistenceContext(unitName = "pu-retailCustomer")
	protected EntityManager em;

	@Override
	public ServiceLocation findById(Long id) {
		return (ServiceLocation) em
				.createNamedQuery(ServiceLocation.QUERY_FIND_BY_ID)
				.setParameter("id", id).getSingleResult();
	}
	
	@Override
	public ServiceLocation findByRetailCustomerIdCustomerIdAccountIdAgreementIdServiceLocationId(Long retailCustomerId,
			Long customerId, Long accountId, Long agreementId, Long serviceLocationId) throws Exception{
		return (ServiceLocation) em
				.createNamedQuery(ServiceLocation.QUERY_FIND_BY_RETAILCUSTOMER_ID_CUSTOMER_ID_ACCOUNT_ID_AGREEMENT_ID_LOCATION_ID)
				.setParameter("retailCustomerId", retailCustomerId)
				.setParameter("customerId", customerId)
				.setParameter("accountId", accountId)
				.setParameter("agreementId", agreementId)
				.setParameter("serviceLocationId", serviceLocationId)
				.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceLocation> findByCustomerIdAccountIdAgreementId (
			Long customerId, Long customerAccountId, Long customerAgreementId) throws Exception{
		return (List<ServiceLocation>) em
				.createNamedQuery(
						ServiceLocation.QUERY_FIND_BY_CUSTOMER_ID_CUSTOMER_ACCOUNT_ID_CUSTOMER_AGREEMENT_ID)
				.setParameter("customerId", customerId)
				.setParameter("customerAccountId", customerAccountId)
				.setParameter("customerAgreementId", customerAgreementId)
				.getResultList();
	}
	
	
	@Override
	public List<ServiceLocation> findByRetailCustomerIdCustomerIdAccountIdAgreementId(Long retailCustomerId,
			Long customerId, Long accountId, Long agreementId) throws Exception{
		return (List<ServiceLocation>) em
				.createNamedQuery(
						ServiceLocation.QUERY_FIND_BY_RETAILCUSTOMER_ID_CUSTOMER_ID_ACCOUNT_ID_AGREEMENT_ID)
				.setParameter("retailCustomerId", retailCustomerId)
				.setParameter("customerId", customerId)
				.setParameter("accountId", accountId)
				.setParameter("agreementId", agreementId)
				.getResultList();
	}

	@Transactional
	@Override
	public void deleteById(Long id) throws Exception{
		em.joinTransaction();
		ServiceLocation serviceLocation = findById(id);
		em.remove(serviceLocation);
		em.flush();

	}

	@Transactional
	@Override
	public void createServiceLocation(ServiceLocation serviceLocation) throws Exception{
		em.joinTransaction();
		try{
		em.persist(serviceLocation);
		}catch(Exception e){
			e.printStackTrace(System.err);
		}
		em.flush();

	}

	@Transactional
	@Override
	public void mergeServiceLocation(ServiceLocation serviceLocation, ServiceLocation existingServiceLocation) throws Exception{
		em.joinTransaction();
		
		if (existingServiceLocation != null && serviceLocation!= null)
			getMergedServiceLocation(existingServiceLocation, serviceLocation);
		
		em.merge(existingServiceLocation);
		em.flush();

	}

	private void getMergedServiceLocation(
			ServiceLocation existingServiceLocation,
			ServiceLocation serviceLocation) {
		System.err.println("Entering getMergedServiceLocation>>");
		
		if(serviceLocation.getUsagePoints()!=null && serviceLocation.getUsagePoints().getUsagePoint()!=null && serviceLocation.getUsagePoints().getUsagePoint().size()>0){
			System.err.println("Entering 1st if inside getMergedServiceLocation");
			String usagePointRef = serviceLocation.getUsagePoints().getUsagePoint().get(0);
			existingServiceLocation.setUsagePointRef(Long.parseLong(usagePointRef.substring(usagePointRef.lastIndexOf("/")+1)));
			}

		if (serviceLocation.getDescription() != null)
			existingServiceLocation.setDescription(serviceLocation
					.getDescription());
		if (serviceLocation.getName() != null)
			existingServiceLocation.setName(serviceLocation.getName());
		existingServiceLocation.setEnabled(serviceLocation.isEnabled());
		existingServiceLocation.setType(serviceLocation.getType());

	}

	@Override
	@Transactional
	public void delete(Long retailCustomerId, Long customerId, Long customerAccountId, Long customerAgreementId, Long serviceLocationId) throws Exception {
		
		em.joinTransaction();
		ServiceLocation serviceLocation = findByRetailCustomerIdCustomerIdAccountIdAgreementIdServiceLocationId(retailCustomerId, customerId, customerAccountId, customerAgreementId, serviceLocationId);
		em.remove(serviceLocation);
		em.flush();
		
	}
}
