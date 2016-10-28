package org.energyos.espi.common.service;

import java.util.List;

import org.energyos.espi.common.domain.Customer;

public interface CustomerService {

	
	Customer findById(Long customerId);
	List<Customer> findByRetailCustomerId(Long retailCustomerId);
	void deleteById(Long id);
	void createCustomer(Customer customer);
	void mergeCustomer(Customer customer);
	
}
