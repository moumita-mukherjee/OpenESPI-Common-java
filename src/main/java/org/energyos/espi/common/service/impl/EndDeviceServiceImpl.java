package org.energyos.espi.common.service.impl;

import java.util.List;

import org.energyos.espi.common.domain.EndDevice;
import org.energyos.espi.common.repositories.EndDeviceRepository;
import org.energyos.espi.common.service.EndDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class EndDeviceServiceImpl implements EndDeviceService {

	@Autowired
	private EndDeviceRepository endDeviceRepository;
	
	@Override
	
	public EndDevice findById(Long id) {
		return endDeviceRepository.findById(id);
	}

	@Override
	public List<EndDevice> findByCustDetails(Long customerId,
			Long customerAccountId, Long customerAgreementId,
			Long serviceLocationId) {
		return endDeviceRepository.findByCustDetails(customerId, customerAccountId, customerAgreementId, serviceLocationId);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		
		endDeviceRepository.deleteById(id);
	}

	@Override
	public void createEndDevice(EndDevice endDevice) {
		endDeviceRepository.createEndDevice(endDevice);
		
	}

	@Override
	public void mergeEndDevice(EndDevice endDevice) {
		endDeviceRepository.mergeEndDevice(endDevice);
		
	}
}
