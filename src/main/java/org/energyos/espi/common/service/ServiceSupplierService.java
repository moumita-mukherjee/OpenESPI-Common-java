package org.energyos.espi.common.service;

import java.util.List;



import org.energyos.espi.common.domain.ServiceSupplier;

public interface ServiceSupplierService {
	ServiceSupplier findById(Long id);
	List<ServiceSupplier> findByCustomerIdAccountIdAgreementId(Long customerId, Long customerAccountId, Long customerAgreementId);
	void deleteById(Long id);
	void createServiceSupplier(ServiceSupplier serviceSupplier);
	void mergeServiceSupplier(ServiceSupplier serviceSupplier);
}
