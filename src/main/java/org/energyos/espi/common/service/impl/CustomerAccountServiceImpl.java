package org.energyos.espi.common.service.impl;

import java.util.List;

import org.energyos.espi.common.domain.CustomerAccount;
import org.energyos.espi.common.repositories.CustomerAccountRepository;
import org.energyos.espi.common.service.CustomerAccountService;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerAccountServiceImpl implements CustomerAccountService {
	
	@Autowired
	private CustomerAccountRepository customerAccountRepository;
	
	@Override
	public CustomerAccount findById(Long customerAccountId) {
		return customerAccountRepository.findById(customerAccountId);
	}

	@Override
	public List<CustomerAccount> findByCustomerId(Long customerId) {
		return customerAccountRepository.findByCustomerId(customerId);
	}

	@Override
	public void deleteById(Long id) {
		customerAccountRepository.deleteById(id);
		
	}

	@Override
	public void createCustomerAccount(CustomerAccount customerAccount) {
		customerAccountRepository.createCustomerAccount(customerAccount);
		
	}

	@Override
	public void mergeCustomerAccount(CustomerAccount customerAccount) {
		customerAccountRepository.mergeCustomerAccount(customerAccount);
		
	}
}
