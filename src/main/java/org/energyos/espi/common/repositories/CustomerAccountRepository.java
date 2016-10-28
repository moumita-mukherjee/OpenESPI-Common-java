package org.energyos.espi.common.repositories;

import java.util.List;

import org.energyos.espi.common.domain.CustomerAccount;

public interface CustomerAccountRepository {
	 CustomerAccount findById(Long id);
	 List<CustomerAccount> findByCustomerId(Long customerId);
	 void deleteById(Long id);
	 void createCustomerAccount(CustomerAccount customerAccount);
	 void mergeCustomerAccount(CustomerAccount customerAccount);
}
