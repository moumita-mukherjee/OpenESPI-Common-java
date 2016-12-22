package org.energyos.espi.common.repositories;

import java.util.List;

import org.energyos.espi.common.domain.Customer;

public interface CustomerRepository {
	 Customer findById(Long id) throws Exception;
	 List<Customer> findByRetailCustomerId(Long retailCustomerId) throws Exception;
	void deleteById(Long id) throws Exception;

	void createCustomer(Customer customer) throws Exception;

	Customer findByRetailCustomerIdCustomerId(Long retailCustomerId, Long id) throws Exception;
	void delete(Long retailCustomerId, Long customerId) throws Exception;
	void mergeCustomer(Customer customer, Customer existingCustomer)
			throws Exception;
}
