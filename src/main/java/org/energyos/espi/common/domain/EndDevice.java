/*
 * Copyright 2013 EnergyOS.org
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

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.08.27 at 01:43:57 PM EDT 
//

package org.energyos.espi.common.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.energyos.espi.common.models.atom.adapters.CustomerAdapter;

@XmlRootElement(name = "EndDevice", namespace = "http://naesb.org/espi/cust")
@XmlJavaTypeAdapter(CustomerAdapter.class)
@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EndDevice")
@Entity
@Table(name = "end_device")
@NamedQueries(value = {
		@NamedQuery(name = EndDevice.QUERY_FIND_ALL_IDS, query = "SELECT endDevice.id FROM EndDevice endDevice "),
		@NamedQuery(name = EndDevice.QUERY_FIND_BY_ID, query = "SELECT endDevice FROM EndDevice endDevice WHERE endDevice.id = :id"),
		@NamedQuery(name = EndDevice.QUERY_FIND_BY_CUSTOMER_CUSTOMERACCOUNT_CUSTOMERAGREEMENT_SERVICELOCATION_IDS, query = "SELECT endDevice FROM EndDevice endDevice WHERE endDevice.serviceLocation.customerAgreement.customerAccount.customer.id = :customerId and endDevice.serviceLocation.customerAgreement.customerAccount.id =:customerAccountId and endDevice.serviceLocation.customerAgreement.id=:customerAgreementId and endDevice.serviceLocation.id =:serviceLocationId"),

})
public class EndDevice extends IdentifiedObject {

	public static final String QUERY_FIND_ALL_IDS = "EndDevice.findAllIds";
	public static final String QUERY_FIND_BY_ID = "EndDevice.findById";
	public static final String QUERY_FIND_BY_CUSTOMER_CUSTOMERACCOUNT_CUSTOMERAGREEMENT_SERVICELOCATION_IDS = "EndDevice.findByCustomerIdCustomerAccountIdCustomerAgreementIdServiceLocationId";

	@Column(name = "name")
	protected String name;

	@Column(name = "type")
	protected String type;

	@Column(name = "utcNumber")
	protected String utcNumber;

	@Column(name = "enabled")
	protected Boolean enabled = true;

	@XmlTransient
	@Transient
	private String link;

	public String getLink() {
		return this.serviceLocation.getLink() + "/ServiceLocation/"
				+ this.serviceLocation.getId();
	}

	public void setLink(Long customerId, Long accountId, Long agreementId,
			Long serviceLocationId) {
		this.link = "/Customer/" + customerId + "/CustomerAccount/" + accountId
				+ "/CustomerAgreement/" + agreementId + "/ServiceLocation/"
				+ serviceLocationId;
	}

	@XmlTransient
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "service_location_id")
	private ServiceLocation serviceLocation;

	public EndDevice() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ServiceLocation getServiceLocation() {
		return serviceLocation;
	}

	public void setServiceLocation(ServiceLocation serviceLocation) {
		this.serviceLocation = serviceLocation;
	}

	public String getUtcNumber() {
		return utcNumber;
	}

	public void setUtcNumber(String utcNumber) {
		this.utcNumber = utcNumber;
	}

	@Override
	public String toString() {
		return "EndDevice [name=" + name + "]";
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}
