package org.energyos.espi.common.service.impl;

import java.util.List;

import org.energyos.espi.common.domain.ServiceLocation;
import org.energyos.espi.common.repositories.ServiceLocationRepository;
import org.energyos.espi.common.service.ServiceLocationService;
import org.springframework.beans.factory.annotation.Autowired;

public class ServiceLocationServiceImpl implements ServiceLocationService {

	@Autowired
	private ServiceLocationRepository serviceLocationRepository;
	
	@Override
	public ServiceLocation findById(Long id) {
		return serviceLocationRepository.findById(id);
	}
	
	
	@Override
	public List<ServiceLocation> findByRetailCustomerIdCustomerIdAccountIdAgreementId(Long retailCustomerId,Long customerId, Long accountId, Long agreementId) throws Exception {
		return serviceLocationRepository.findByRetailCustomerIdCustomerIdAccountIdAgreementId(retailCustomerId,
				customerId, accountId, agreementId);
	}
	
	@Override
	public ServiceLocation findByRetailCustomerIdCustomerIdAccountIdAgreementIdServiceLocationId(Long retailCustomerId,
			Long customerId, Long accountId, Long agreementId, Long serviceLocationId) throws Exception{
		return serviceLocationRepository.findByRetailCustomerIdCustomerIdAccountIdAgreementIdServiceLocationId(retailCustomerId,
				customerId, accountId, agreementId, serviceLocationId);
	}

	@Override
	public List<ServiceLocation> findByCustomerIdAccountIdAgreementId (
			Long customerId, Long customerAccountId, Long customerAgreementId) throws Exception {
		return serviceLocationRepository.findByCustomerIdAccountIdAgreementId(customerId, customerAccountId, customerAgreementId);
	}

	@Override
	public void deleteById(Long id) throws Exception{
		serviceLocationRepository.deleteById(id);
		
	}

	@Override
	public void createServiceLocation(ServiceLocation serviceLocation) throws Exception{
		setUsagePointRef(serviceLocation);
		serviceLocationRepository.createServiceLocation(serviceLocation);
		
	}

	@Override
	public void mergeServiceLocation(ServiceLocation serviceLocation, ServiceLocation existingServiceLocation) throws Exception{
		serviceLocationRepository.mergeServiceLocation(serviceLocation, existingServiceLocation);
		
	}

	private void setUsagePointRef(ServiceLocation sl){
		try{
		//System.err.println("Entering setUsagePointRef");
		String usagePointRef = sl.getUsagePoints().getUsagePoint().get(0);
		sl.setUsagePointRef(Long.parseLong(usagePointRef.substring(usagePointRef.lastIndexOf("/")+1)));
		}catch(Exception e){
			e.printStackTrace(System.err);
			sl.setUsagePointRef(null);
		}
	}


	@Override
	
	public void delete(Long retailCustomerId, Long customerId, Long customerAccountId, Long customerAgreementId, Long serviceLocationId) throws Exception {
		
		serviceLocationRepository.delete(retailCustomerId, customerId, customerAccountId, customerAgreementId, serviceLocationId);
	}
}
