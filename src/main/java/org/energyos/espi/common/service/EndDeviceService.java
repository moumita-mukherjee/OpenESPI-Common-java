package org.energyos.espi.common.service;

import java.util.List;

import org.energyos.espi.common.domain.EndDevice;

public interface EndDeviceService {

	EndDevice findById(Long id);
	 List<EndDevice> findByCustDetails(Long customerId, Long customerAccountId, Long customerAgreementId, Long serviceLocationId);
	 void deleteById(Long id);
	 void createEndDevice(EndDevice endDevice);
	 void mergeEndDevice(EndDevice endDevice);
	 
}
