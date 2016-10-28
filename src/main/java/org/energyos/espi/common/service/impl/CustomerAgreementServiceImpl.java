package org.energyos.espi.common.service.impl;

import java.util.List;

import org.energyos.espi.common.domain.CustomerAgreement;
import org.energyos.espi.common.repositories.CustomerAgreementRepository;
import org.energyos.espi.common.service.CustomerAgreementService;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerAgreementServiceImpl implements CustomerAgreementService {

	
	@Autowired
	private CustomerAgreementRepository customerAgreementRepository;
	@Override
	public CustomerAgreement findById(Long customerAccountId) {
		return customerAgreementRepository.findById(customerAccountId);
	}

	@Override
	public List<CustomerAgreement> findByCustomerIdAccountId(Long customerId,
			Long customerAccountId) {
		return customerAgreementRepository.findByCustomerIdAccountId(customerId, customerAccountId);
	}

	@Override
	public void deleteById(Long id) {
		customerAgreementRepository.deleteById(id);
	}

	@Override
	public void createCustomerAgreement(CustomerAgreement customerAgreement) {
		customerAgreementRepository.createCustomerAgreement(customerAgreement);
	}

	@Override
	public void mergeCustomerAgreement(CustomerAgreement customerAgreement) {
		customerAgreementRepository.mergeCustomerAgreement(customerAgreement);
	}
}
