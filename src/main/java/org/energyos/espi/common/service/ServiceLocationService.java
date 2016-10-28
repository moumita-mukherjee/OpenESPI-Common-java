package org.energyos.espi.common.service;

import java.util.List;

import org.energyos.espi.common.domain.CustomerAccount;
import org.energyos.espi.common.domain.ServiceLocation;

public interface ServiceLocationService {
	ServiceLocation findById(Long id);
	List<ServiceLocation> findByCustomerIdAccountIdAgreementId(Long customerId,Long customerAccountId, Long customerAgreementId);
	void deleteById(Long id);
	void createServiceLocation(ServiceLocation serviceLocation);
	void mergeServiceLocation(ServiceLocation serviceLocation);
}
