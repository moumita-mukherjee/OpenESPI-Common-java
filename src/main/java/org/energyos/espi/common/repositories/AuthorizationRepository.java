/*
 * Copyright 2013, 2014 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.energyos.espi.common.repositories;

import java.util.List;
import java.util.UUID;

import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.IdentifiedObject;

public interface AuthorizationRepository {

    void persist(Authorization authorization);

    List<Authorization> findAllByRetailCustomerId(Long retailCustomerId);
    
    List<IdentifiedObject> findAllIdsByApplicationInformationId(Long applicationInformationId);

    Authorization findByState(String state);
    
    Authorization findByScope(String scope, Long retailCustomerId);

    void merge(Authorization authorization);

    Authorization findById(Long authorizationId);

    List<IdentifiedObject> findAllIds(Long retailCustomerId);

    Authorization findByUUID(UUID uuid);

    List<IdentifiedObject> findAllIds();
    
    void deleteById(Long id);

    void createOrReplaceByUUID(Authorization authorization);
    
    Authorization findByAccessToken(String accessToken);

	Authorization findByRefreshToken(String refreshToken);
	
	public List<Long> findAllIdsByBulkId(String thirdParty, Long bulkId);
	
	/* LH customization starts here */
	Authorization findByApplicationInformationId(Long applicationInformationId,String scope);
	
    List<Authorization> findAllActiveByRetailCustomerId(Long retailCustomerId);
	
    void delete(Authorization authorization);    
	
	Authorization findByScope(Long retailCustomerId,Long applicationInformationId, String scope );
	
	public List<Authorization> findByStatus(Long retailCustomerId,String applicationInformationId,String status);
}
