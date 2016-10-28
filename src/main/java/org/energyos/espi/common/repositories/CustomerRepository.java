package org.energyos.espi.common.repositories;

import java.util.List;

import org.energyos.espi.common.domain.Customer;

public interface CustomerRepository {
	 Customer findById(Long id);
	 List<Customer> findByRetailCustomerId(Long retailCustomerId);
	void deleteById(Long id);

	void createCustomer(Customer customer);

	void mergeCustomer(Customer customer);
}
