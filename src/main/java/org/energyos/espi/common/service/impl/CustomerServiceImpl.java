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
	public Customer findById(Long customerId) throws Exception {
		return customerRepository.findById(customerId);
	}

	@Override
	public List<Customer> findByRetailCustomerId(Long retailCustomerId) throws Exception{
		return customerRepository.findByRetailCustomerId(retailCustomerId);
	}
	
	
	
	@Override
	public Customer findByRetailCustomerIdCustomerId(Long retailCustomerId, Long customerId) throws Exception{
		return customerRepository.findByRetailCustomerIdCustomerId(retailCustomerId, customerId);
	}

	@Override
	public void deleteById(Long id) throws Exception {
		
		customerRepository.deleteById(id);
	}

	@Override
	public void createCustomer(Customer customer) throws Exception {
		customerRepository.createCustomer(customer);
		
	}

	@Override
	public void mergeCustomer(Customer customer, Customer existingCustomer) throws Exception {
		customerRepository.mergeCustomer(customer, existingCustomer);
		
	}
	
	@Override
	public void delete(Long retailCustomerId, Long customerId) throws Exception {
		
		customerRepository.delete(retailCustomerId, customerId);
	}

	
}
