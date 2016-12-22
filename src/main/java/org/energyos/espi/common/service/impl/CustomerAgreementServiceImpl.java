package org.energyos.espi.common.service.impl;

import java.util.List;

import org.energyos.espi.common.domain.CustomerAgreement;
import org.energyos.espi.common.repositories.CustomerAgreementRepository;
import org.energyos.espi.common.service.CustomerAgreementService;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerAgreementServiceImpl implements CustomerAgreementService {

	
	@Autowired
	private CustomerAgreementRepository customerAgreementRepository;
	@Override
	public CustomerAgreement findById(Long customerAccountId) {
		return customerAgreementRepository.findById(customerAccountId);
	}

	@Override
	public List<CustomerAgreement> findByCustomerIdAccountId(Long customerId,
			Long customerAccountId) {
		return customerAgreementRepository.findByCustomerIdAccountId(customerId, customerAccountId);
	}
	
	@Override
	public List<CustomerAgreement> findByRetailCustomerIdCustomerIdAccountId(Long retailCustomerId,
			Long customerId, Long accountId) throws Exception{
		return customerAgreementRepository.findByRetailCustomerIdCustomerIdAccountId(retailCustomerId,customerId, accountId);
	}
	
	@Override
	public CustomerAgreement findByRetailCustomerIdCustomerIdAccountIdAgreementId(Long retailCustomerId, Long customerId, Long accountId, Long agreementId) throws Exception{
		CustomerAgreement ca = customerAgreementRepository.findByRetailCustomerIdCustomerIdAccountIdAgreementId(retailCustomerId,customerId,accountId,agreementId);
		//ca.setCustomerAccountId(ca.getCustomerAccount().getId());
		
		return ca;
	}

	@Override
	public void deleteById(Long id) throws Exception {
		customerAgreementRepository.deleteById(id);
	}

	@Override
	public void createCustomerAgreement(CustomerAgreement customerAgreement) throws Exception{
		customerAgreementRepository.createCustomerAgreement(customerAgreement);
	}

	

	@Override
	public void mergeCustomerAgreement(CustomerAgreement customerAgreement,
			CustomerAgreement existingCustAgg) throws Exception {
		customerAgreementRepository.mergeCustomerAgreement(customerAgreement,existingCustAgg);
		
	}

	@Override
	public void delete(Long retailCustomerId, Long customerId, Long customerAccountId, Long customerAgreementId) throws Exception {
		customerAgreementRepository.delete(retailCustomerId, customerId, customerAccountId, customerAgreementId);
		
	}
}
