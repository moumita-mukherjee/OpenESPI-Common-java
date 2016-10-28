package org.energyos.espi.common.service;

import java.util.List;

import org.energyos.espi.common.domain.CustomerAgreement;

public interface CustomerAgreementService {
	CustomerAgreement findById(Long customerAccountId);
	List<CustomerAgreement> findByCustomerIdAccountId(Long customerId, Long customerAccountId);
	void deleteById(Long id);
	void createCustomerAgreement(CustomerAgreement customerAgreement);
	void mergeCustomerAgreement(CustomerAgreement customerAgreement);
}
