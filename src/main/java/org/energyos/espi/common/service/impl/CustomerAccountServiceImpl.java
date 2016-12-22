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
	public List<CustomerAccount> findByCustomerId(Long customerId, Long retailCustomerId) throws Exception {
		return customerAccountRepository.findByCustomerId(customerId,retailCustomerId);
	}
	
	@Override
	public CustomerAccount findByRetailCustomerIdCustomerIdCustomerAccountId(Long retailCustomerId, Long customerId, Long accountId) throws Exception{
		return customerAccountRepository.findByRetailCustomerIdCustomerIdCustomerAccountId(retailCustomerId, customerId, accountId);
	}

	@Override
	public void deleteById(Long id) throws Exception{
		customerAccountRepository.deleteById(id);
		
	}

	@Override
	public void createCustomerAccount(CustomerAccount customerAccount) throws Exception{
		customerAccountRepository.createCustomerAccount(customerAccount);
		
	}

	@Override
	public void mergeCustomerAccount(CustomerAccount customerAccount, CustomerAccount existingCustomerAcc) throws Exception{
		customerAccountRepository.mergeCustomerAccount(customerAccount,existingCustomerAcc);
		
	}
	
	@Override
	public void delete(Long retailCustomerId, Long customerId, Long customerAccountId) throws Exception{
		customerAccountRepository.delete(retailCustomerId, customerId, customerAccountId);
		
	}
}
