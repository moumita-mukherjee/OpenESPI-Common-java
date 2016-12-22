package org.energyos.espi.common.repositories.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.energyos.espi.common.domain.EndDevice;
import org.energyos.espi.common.repositories.EndDeviceRepository;
import org.springframework.transaction.annotation.Transactional;

public class EndDeviceRepositoryImpl implements EndDeviceRepository {

	@PersistenceContext(unitName = "pu-retailCustomer")
	protected EntityManager em;

	@Override
	public EndDevice findById(Long id){
		return (EndDevice) em.createNamedQuery(EndDevice.QUERY_FIND_BY_ID)
				.setParameter("id", id).getSingleResult();
	}
	
	@Override
	public EndDevice findByRetailCustomerIdCustomerIdAccountIdAgreementIdServiceLocationIdEndDeviceId(Long retailCustomerId,
			Long customerId, Long accountId, Long agreementId, Long serviceLocationId, Long endDeviceId) throws Exception{
		return (EndDevice) em.createNamedQuery(EndDevice.QUERY_FIND_BY_RETAILCUSTOMER_ID_CUSTOMER_ID_ACCOUNT_ID_AGREEMENT_ID_LOCATION_ID_ENDDEVICE_ID)
				.setParameter("retailCustomerId", retailCustomerId)
				.setParameter("customerId", customerId)
				.setParameter("accountId", accountId)
				.setParameter("agreementId", agreementId)
				.setParameter("serviceLocationId", serviceLocationId)
				.setParameter("endDeviceId", endDeviceId)
				.getSingleResult();
	}
	
	
	
	@Override
	public List<EndDevice> findByRetailCustomerIdCustomerIdAccountIdAgreementIdServiceLocationId(Long retailCustomerId,
			Long customerId, Long accountId, Long agreementId, Long serviceLocationId) throws Exception{

		return (List<EndDevice>) em
				.createNamedQuery(
						EndDevice.QUERY_FIND_BY_RETAILCUSTOMER_ID_CUSTOMER_ID_ACCOUNT_ID_AGREEMENT_ID_LOCATION_ID)
				.setParameter("retailCustomerId", retailCustomerId)
				.setParameter("customerId", customerId)
				.setParameter("accountId", accountId)
				.setParameter("agreementId", agreementId)
				.setParameter("serviceLocationId", serviceLocationId)
				.getResultList();
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EndDevice> findByCustDetails(Long customerId,
			Long customerAccountId, Long customerAgreementId,
			Long serviceLocationId) {

		return (List<EndDevice>) em
				.createNamedQuery(
						EndDevice.QUERY_FIND_BY_CUSTOMER_CUSTOMERACCOUNT_CUSTOMERAGREEMENT_SERVICELOCATION_IDS)
				.setParameter("customerId", customerId)
				.setParameter("customerAccountId", customerAccountId)
				.setParameter("customerAgreementId", customerAgreementId)
				.setParameter("serviceLocationId", serviceLocationId)
				.getResultList();
	}

	@Transactional
	@Override
	public void deleteById(Long id) throws Exception{
		em.joinTransaction();
		EndDevice ed = findById(id);
		em.remove(ed);
		em.flush();
	}

	@Transactional
	@Override
	public void createEndDevice(EndDevice endDevice) throws Exception{
		em.joinTransaction();
		em.persist(endDevice);
		em.flush();

	}

	@Transactional
	@Override
	public void mergeEndDevice(EndDevice endDevice, EndDevice existingEndDevice) throws Exception {
		em.joinTransaction();
		if (existingEndDevice != null && endDevice != null )
			getMergedEndDevice(existingEndDevice, endDevice);
		em.merge(existingEndDevice);
		em.flush();

	}

	private void getMergedEndDevice(EndDevice existingEndDevice,
			EndDevice endDevice) {

		if (endDevice.getDescription() != null)
			existingEndDevice.setDescription(endDevice.getDescription());
		if (endDevice.getName() != null)
			existingEndDevice.setName(endDevice.getName());
		existingEndDevice.setEnabled(endDevice.isEnabled());
		existingEndDevice.setType(endDevice.getType());

	}

	@Transactional
	@Override
	public void delete(Long retailCustomerId, Long customerId, Long accountId, Long agreementId, Long serviceLocationId, Long endDeviceId) throws Exception {
		
		em.joinTransaction();
		EndDevice end = findByRetailCustomerIdCustomerIdAccountIdAgreementIdServiceLocationIdEndDeviceId(retailCustomerId, customerId, accountId, agreementId, serviceLocationId, endDeviceId);
		em.remove(end);
		em.flush();
		}

}
