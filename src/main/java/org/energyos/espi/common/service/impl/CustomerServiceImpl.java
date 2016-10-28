package org.energyos.espi.common.service.impl;

import java.util.List;

import org.energyos.espi.common.domain.Customer;
import org.energyos.espi.common.repositories.CustomerRepository;
import org.energyos.espi.common.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public Customer findById(Long customerId) {
		return customerRepository.findById(customerId);
	}

	@Override
	public List<Customer> findByRetailCustomerId(Long retailCustomerId) {
		return customerRepository.findByRetailCustomerId(retailCustomerId);
	}

	@Override
	public void deleteById(Long id) {
		
		customerRepository.deleteById(id);
	}

	@Override
	public void createCustomer(Customer customer) {
		customerRepository.createCustomer(customer);
		
	}

	@Override
	public void mergeCustomer(Customer customer) {
		customerRepository.mergeCustomer(customer);
		
	}
}
