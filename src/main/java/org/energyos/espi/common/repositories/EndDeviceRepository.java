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
	void deleteById(Long id);
	void createEndDevice(EndDevice endDevice);
	void mergeEndDevice(EndDevice endDevice);
}
