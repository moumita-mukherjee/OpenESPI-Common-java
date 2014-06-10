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

import org.energyos.espi.common.domain.RetailCustomer;
import org.springframework.security.core.userdetails.UserDetails;

public interface RetailCustomerRepository {

    public void deleteById(Long retailCustomerId);

    public List<RetailCustomer> findAll();

    public RetailCustomer findById(Long retailCustomerId);

    public RetailCustomer findById(String retailCustomerId);
    
    public RetailCustomer findByLink(String link);

    public UserDetails findByUsername(String userName);

    public void persist(RetailCustomer retailCustomer);

}
