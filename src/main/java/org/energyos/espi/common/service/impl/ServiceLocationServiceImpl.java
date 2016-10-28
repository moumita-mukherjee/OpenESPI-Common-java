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
	public List<ServiceLocation> findByCustomerIdAccountIdAgreementId(
			Long customerId, Long customerAccountId, Long customerAgreementId) {
		return serviceLocationRepository.findByCustomerIdAccountIdAgreementId(customerId, customerAccountId, customerAgreementId);
	}

	@Override
	public void deleteById(Long id) {
		serviceLocationRepository.deleteById(id);
		
	}

	@Override
	public void createServiceLocation(ServiceLocation serviceLocation) {
		setUsagePointRef(serviceLocation);
		serviceLocationRepository.createServiceLocation(serviceLocation);
		
	}

	@Override
	public void mergeServiceLocation(ServiceLocation serviceLocation) {
		serviceLocationRepository.mergeServiceLocation(serviceLocation);
		
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
}
