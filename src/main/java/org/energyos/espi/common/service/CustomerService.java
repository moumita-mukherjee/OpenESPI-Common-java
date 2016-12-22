package org.energyos.espi.common.service;

import java.util.List;

import org.energyos.espi.common.domain.Customer;

public interface CustomerService {

	
	Customer findById(Long customerId) throws Exception;
	List<Customer> findByRetailCustomerId(Long retailCustomerId) throws Exception;
	void deleteById(Long id) throws Exception;
	void createCustomer(Customer customer) throws Exception;
	void delete(Long retailCustomerId, Long customerId) throws Exception;
	Customer findByRetailCustomerIdCustomerId(Long retailCustomerId,
			Long customerId) throws Exception;
	void mergeCustomer(Customer customer, Customer existingCustomer)
			throws Exception;
	
	
}
