package org.energyos.espi.common.service;

import java.util.List;

import org.energyos.espi.common.domain.CustomerAgreement;

public interface CustomerAgreementService {
	CustomerAgreement findById(Long customerAccountId);
	List<CustomerAgreement> findByCustomerIdAccountId(Long customerId, Long customerAccountId);
	void deleteById(Long id) throws Exception;
	void delete(Long retailCustomerId, Long customerId, Long customerAccountId, Long customerAgreementId) throws Exception;
	void createCustomerAgreement(CustomerAgreement customerAgreement) throws Exception;
	void mergeCustomerAgreement(CustomerAgreement customerAgreement, CustomerAgreement existingCustAgg) throws Exception;
	CustomerAgreement findByRetailCustomerIdCustomerIdAccountIdAgreementId(
			Long retailCustomerId, Long customerId, Long accountId,
			Long agreementId) throws Exception;
	List<CustomerAgreement> findByRetailCustomerIdCustomerIdAccountId(
			Long retailCustomerId, Long customerId, Long accountId)
			throws Exception;
}
