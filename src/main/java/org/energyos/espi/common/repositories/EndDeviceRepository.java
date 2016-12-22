/**
 * 
 */
package org.energyos.espi.common.repositories;

import java.util.List;

import org.energyos.espi.common.domain.CustomerAccount;
import org.energyos.espi.common.domain.EndDevice;

/**
 * @author royum
 *
 */
public interface EndDeviceRepository {
	EndDevice findById(Long id);
	List<EndDevice> findByCustDetails(Long customerId, Long customerAccountId, Long customerAgreementId, Long serviceLocationId);
	void deleteById(Long id) throws Exception;
	//void delete(EndDevice ed) throws Exception;
	void createEndDevice(EndDevice endDevice) throws Exception;
	void mergeEndDevice(EndDevice endDevice, EndDevice existingEndDevice)throws Exception;
	EndDevice findByRetailCustomerIdCustomerIdAccountIdAgreementIdServiceLocationIdEndDeviceId(
			Long retailCustomerId, Long customerId, Long accountId,
			Long agreementId, Long serviceLocationId, Long endDeviceId)
			throws Exception;
	List<EndDevice> findByRetailCustomerIdCustomerIdAccountIdAgreementIdServiceLocationId(
			Long retailCustomerId, Long customerId, Long accountId,
			Long agreementId, Long serviceLocationId) throws Exception;
	void delete(Long retailCustomerId, Long customerId, Long accountId,
			Long agreementId, Long serviceLocationId, Long endDeviceId)
			throws Exception;
}
