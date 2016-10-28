package org.energyos.espi.common.service.impl;

import java.util.List;

import org.energyos.espi.common.domain.ServiceSupplier;
import org.energyos.espi.common.repositories.ServiceSupplierRepository;
import org.energyos.espi.common.service.ServiceSupplierService;
import org.springframework.beans.factory.annotation.Autowired;

public class ServiceSupplierServiceImpl implements ServiceSupplierService {

	@Autowired
	private ServiceSupplierRepository serviceSupplierRepository;

	@Override
	public ServiceSupplier findById(Long id) {
		return serviceSupplierRepository.findById(id);

	}

	@Override
	public List<ServiceSupplier> findByCustomerIdAccountIdAgreementId(
			Long customerId, Long customerAccountId, Long customerAgreementId) {
		return serviceSupplierRepository.findByCustomerIdAccountIdAgreementId(
				customerId, customerAccountId, customerAgreementId);
	}

	@Override
	public void deleteById(Long id) {
		serviceSupplierRepository.deleteById(id);

	}

	@Override
	public void createServiceSupplier(ServiceSupplier serviceSupplier) {
		serviceSupplierRepository.createServiceSupplier(serviceSupplier);

	}

	@Override
	public void mergeServiceSupplier(ServiceSupplier serviceSupplier) {
		serviceSupplierRepository.mergeServiceSupplier(serviceSupplier);

	}

}
