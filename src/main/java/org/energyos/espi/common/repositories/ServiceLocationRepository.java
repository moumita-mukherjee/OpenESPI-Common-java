package org.energyos.espi.common.repositories;

import java.util.List;

import org.energyos.espi.common.domain.ServiceLocation;

public interface ServiceLocationRepository {
	ServiceLocation findById(Long id);
	 List<ServiceLocation> findByCustomerIdAccountIdAgreementId(Long customerId,Long customerAccountId, Long customerAgreementId)
	 throws Exception;
	 void deleteById(Long id) throws Exception;
	 void delete(Long retailCustomerId, Long customerId, Long customerAccountId, Long customerAgreementId, Long serviceLocationId) throws Exception;
	 void createServiceLocation(ServiceLocation serviceLocation) throws Exception;
	 void mergeServiceLocation(ServiceLocation serviceLocation, ServiceLocation existingServiceLocation) throws Exception;
	ServiceLocation findByRetailCustomerIdCustomerIdAccountIdAgreementIdServiceLocationId(
			Long retailCustomerId, Long customerId, Long accountId,
			Long agreementId, Long serviceLocationId) throws Exception;
	List<ServiceLocation> findByRetailCustomerIdCustomerIdAccountIdAgreementId(
			Long retailCustomerId, Long customerId, Long accountId,
			Long agreementId) throws Exception;
}
