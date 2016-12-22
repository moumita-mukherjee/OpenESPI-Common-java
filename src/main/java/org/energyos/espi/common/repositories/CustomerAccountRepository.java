package org.energyos.espi.common.repositories;

import java.util.List;

import org.energyos.espi.common.domain.CustomerAccount;

public interface CustomerAccountRepository {
	 CustomerAccount findById(Long id);
	 List<CustomerAccount> findByCustomerId(Long customerId, Long retailCustomerId) throws Exception;
	 void deleteById(Long id) throws Exception;
	 void delete(Long retailCustomerId, Long customerId, Long customerAccountId) throws Exception;
	 void createCustomerAccount(CustomerAccount customerAccount) throws Exception;
	 void mergeCustomerAccount(CustomerAccount customerAccount, CustomerAccount existingCustomerAcc) throws Exception;
	CustomerAccount findByRetailCustomerIdCustomerIdCustomerAccountId(Long retailCustomerId, Long customerId, Long accountId) throws Exception;
}
