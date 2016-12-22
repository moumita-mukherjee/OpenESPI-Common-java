package org.energyos.espi.common.service;

import java.util.List;






import org.energyos.espi.common.domain.ServiceSupplier;

public interface ServiceSupplierService {
	ServiceSupplier findById(Long id);
	List<ServiceSupplier> findByCustomerIdAccountIdAgreementId(Long customerId, Long customerAccountId, Long customerAgreementId);
	void deleteById(Long id) throws Exception;
	void delete(Long retailCustomerId, Long customerId, Long customerAccountId, Long customerAgreementId, Long serviceSupplierId) throws Exception;
	void createServiceSupplier(ServiceSupplier serviceSupplier) throws Exception;
	void mergeServiceSupplier(ServiceSupplier serviceSupplier, ServiceSupplier existingServiceSupplier) throws Exception;
	ServiceSupplier findByRetailCustomerIdCustomerIdAccountIdAgreementIdServiceSupplierId(
			Long retailCustomerId, Long customerId, Long accountId,
			Long agreementId, Long supplierId) throws Exception;
	List<ServiceSupplier> findByRetailCustomerIdCustomerIdAccountIdAgreementId(
			Long retailCustomerId, Long customerId, Long accountId,
			Long agreementId) throws Exception;
}
