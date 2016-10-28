/**
 * 
 */
package org.energyos.espi.common.repositories;

import java.util.List;




import org.energyos.espi.common.domain.ServiceSupplier;

/**
 * @author bosea
 *
 */
public interface ServiceSupplierRepository {
	ServiceSupplier findById(Long id);
	List<ServiceSupplier> findByCustomerIdAccountIdAgreementId(Long customerId,Long customerAccountId, Long customerAgreementId);
	 void deleteById(Long id);
	 void createServiceSupplier(ServiceSupplier serviceSupplier);
	 void mergeServiceSupplier(ServiceSupplier serviceSupplier);

}
