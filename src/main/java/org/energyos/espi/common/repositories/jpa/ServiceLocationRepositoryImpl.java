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

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceLocation> findByCustomerIdAccountIdAgreementId(
			Long customerId, Long customerAccountId, Long customerAgreementId) {
		return (List<ServiceLocation>) em
				.createNamedQuery(
						ServiceLocation.QUERY_FIND_BY_CUSTOMER_ID_CUSTOMER_ACCOUNT_ID_CUSTOMER_AGREEMENT_ID)
				.setParameter("customerId", customerId)
				.setParameter("customerAccountId", customerAccountId)
				.setParameter("customerAgreementId", customerAgreementId)
				.getResultList();
	}

	@Transactional
	@Override
	public void deleteById(Long id) {
		em.joinTransaction();
		ServiceLocation serviceLocation = findById(id);
		em.remove(serviceLocation);
		em.flush();

	}

	@Transactional
	@Override
	public void createServiceLocation(ServiceLocation serviceLocation) {
		em.joinTransaction();
		em.persist(serviceLocation);
		em.flush();

	}

	@Transactional
	@Override
	public void mergeServiceLocation(ServiceLocation serviceLocation) {
		em.joinTransaction();
		ServiceLocation existingServiceLocation = null;
		if (serviceLocation != null)
			existingServiceLocation = findById(serviceLocation.getId());
		if (existingServiceLocation != null)
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
}
