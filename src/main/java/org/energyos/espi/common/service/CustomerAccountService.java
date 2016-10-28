package org.energyos.espi.common.service;

import java.util.List;

import org.energyos.espi.common.domain.CustomerAccount;

public interface CustomerAccountService {

	
	CustomerAccount findById(Long customerAccountId);
	List<CustomerAccount> findByCustomerId(Long customerId);
	void deleteById(Long id);
	void createCustomerAccount(CustomerAccount customerAccount);
	void mergeCustomerAccount(CustomerAccount customerAccount);
}
