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
	public EndDevice findById(Long id) {
		return (EndDevice) em.createNamedQuery(EndDevice.QUERY_FIND_BY_ID)
				.setParameter("id", id).getSingleResult();
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
	public void deleteById(Long id) {
		em.joinTransaction();
		EndDevice ed = findById(id);
		em.remove(ed);
		em.flush();
	}

	@Transactional
	@Override
	public void createEndDevice(EndDevice endDevice) {
		em.joinTransaction();
		em.persist(endDevice);
		em.flush();

	}

	@Transactional
	@Override
	public void mergeEndDevice(EndDevice endDevice) {
		em.joinTransaction();
		EndDevice existingeEndDevice = null;
		if (endDevice != null)
			existingeEndDevice = findById(endDevice.getId());
		if (existingeEndDevice != null)
			getMergedEndDevice(existingeEndDevice, endDevice);

		em.merge(existingeEndDevice);
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

}
