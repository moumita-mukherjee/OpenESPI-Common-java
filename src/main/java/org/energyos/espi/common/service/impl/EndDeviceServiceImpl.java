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
	public EndDevice findByRetailCustomerIdCustomerIdAccountIdAgreementIdServiceLocationIdEndDeviceId(Long retailCustomerId,
			Long customerId, Long accountId, Long agreementId, Long serviceLocationId, Long endDeviceId) throws Exception {
		return endDeviceRepository.findByRetailCustomerIdCustomerIdAccountIdAgreementIdServiceLocationIdEndDeviceId(retailCustomerId,
				customerId,accountId,agreementId,serviceLocationId,endDeviceId);
	}
	
	@Override
	public List<EndDevice> findByRetailCustomerIdCustomerIdAccountIdAgreementIdServiceLocationId(Long retailCustomerId,
			Long customerId, Long accountId, Long agreementId, Long serviceLocationId) throws Exception {
		return endDeviceRepository.findByRetailCustomerIdCustomerIdAccountIdAgreementIdServiceLocationId(retailCustomerId,
				customerId,accountId,agreementId,serviceLocationId);
	}

	@Override
	public List<EndDevice> findByCustDetails(Long customerId,
			Long customerAccountId, Long customerAgreementId,
			Long serviceLocationId) {
		return endDeviceRepository.findByCustDetails(customerId, customerAccountId, customerAgreementId, serviceLocationId);
	}

	@Override
	@Transactional
	public void deleteById(Long id) throws Exception {
		
		endDeviceRepository.deleteById(id);
	}
	
	@Override
	public void delete(Long retailCustomerId, Long customerId, Long accountId, Long agreementId, Long serviceLocationId, Long endDeviceId) throws Exception {
		
		endDeviceRepository.delete(retailCustomerId, customerId, accountId, agreementId, serviceLocationId, endDeviceId);
	}

	@Override
	public void createEndDevice(EndDevice endDevice) throws Exception {
		endDeviceRepository.createEndDevice(endDevice);
		
	}

	@Override
	public void mergeEndDevice(EndDevice endDevice, EndDevice existingEndDevice) throws Exception {
		endDeviceRepository.mergeEndDevice(endDevice,existingEndDevice);
		
	}
}
