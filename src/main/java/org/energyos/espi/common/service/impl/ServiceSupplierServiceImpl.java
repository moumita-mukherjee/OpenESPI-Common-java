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
	public ServiceSupplier findByRetailCustomerIdCustomerIdAccountIdAgreementIdServiceSupplierId(Long retailCustomerId,
			Long customerId, Long accountId, Long agreementId, Long serviceSupplierId) throws Exception{
		return serviceSupplierRepository.findByRetailCustomerIdCustomerIdAccountIdAgreementIdServiceSupplierId(retailCustomerId,
				customerId,accountId,agreementId,serviceSupplierId);
	}
	
	
	@Override
	public List<ServiceSupplier> findByRetailCustomerIdCustomerIdAccountIdAgreementId(Long retailCustomerId, Long customerId,
			Long accountId, Long agreementId) throws Exception{
		return serviceSupplierRepository.findByRetailCustomerIdCustomerIdAccountIdAgreementId(retailCustomerId,
				customerId,accountId,agreementId);
	}

	@Override
	public List<ServiceSupplier> findByCustomerIdAccountIdAgreementId(
			Long customerId, Long customerAccountId, Long customerAgreementId) {
		return serviceSupplierRepository.findByCustomerIdAccountIdAgreementId(
				customerId, customerAccountId, customerAgreementId);
	}

	@Override
	public void deleteById(Long id) throws Exception {
		serviceSupplierRepository.deleteById(id);

	}

	@Override
	public void createServiceSupplier(ServiceSupplier serviceSupplier) throws Exception {
		serviceSupplierRepository.createServiceSupplier(serviceSupplier);

	}

	@Override
	public void mergeServiceSupplier(ServiceSupplier serviceSupplier,ServiceSupplier existingServiceSupplier) throws Exception {
		serviceSupplierRepository.mergeServiceSupplier(serviceSupplier,existingServiceSupplier);

	}

	@Override
	public void delete(Long retailCustomerId, Long customerId, Long customerAccountId, Long customerAgreementId, Long serviceSupplierId) throws Exception {
		serviceSupplierRepository.delete(retailCustomerId, customerId, customerAccountId, customerAgreementId, serviceSupplierId);
		
	}

}
