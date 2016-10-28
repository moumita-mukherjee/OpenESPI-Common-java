package org.energyos.espi.common.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "UsagePointDetail")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "services_v")
@NamedQueries(value = {
		@NamedQuery(name = UsagePointDetail.QUERY_FIND_ALL_BY_RETAIL_CUSTOMER_ID, query = "SELECT up FROM UsagePointDetail up WHERE up.retailCustomerId = :retailCustomerId"),
		@NamedQuery(name = UsagePointDetail.QUERY_FIND_BY_REF, query = "SELECT up FROM UsagePointDetail up WHERE up.retailCustomerId = :customerId AND  up.selfHref = :selfHref")

})
public class UsagePointDetail {

	public static final String QUERY_FIND_ALL_BY_RETAIL_CUSTOMER_ID = "UsagePointDetail.findUsagePointDetailByRetailCustomer";
	public static final String QUERY_FIND_BY_REF = "UsagePointDetail.findByRef";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlTransient
	protected Long id;

	@Column(name = "customer_id", insertable = false, updatable = false)
	private String retailCustomerId;

	public String getRetailCustomerId() {
		return retailCustomerId;
	}

	public void setRetailCustomerId(String retailCustomerId) {
		this.retailCustomerId = retailCustomerId;
	}

	@Column(name = "service_id", insertable = false, updatable = false)
	private String serviceId;
	@Column(name = "meter_number", insertable = false, updatable = false)
	private String meterNumber;

	@Column(name = "account_id", insertable = false, updatable = false)
	private String accountId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	@Column(name = "street_number", insertable = false, updatable = false)
	private String streetNumber;
	@Column(name = "street_name", insertable = false, updatable = false)
	private String streetName;
	@Column(name = "street_city", insertable = false, updatable = false)
	private String streetCity;
	@Column(name = "street_province", insertable = false, updatable = false)
	private String streetProvince;
	@Column(name = "postal_code", insertable = false, updatable = false)
	private String postalCode;
	@Column(name = "street_unit", insertable = false, updatable = false)
	private String streetUnit;

	@Column(name = "status", insertable = false, updatable = false)
	private String status;

	@Column(name = "end_date", insertable = false, updatable = false)
	private Date endDate;

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "self_href", insertable = false, updatable = false)
	private String selfHref;

	public String getSelfHref() {
		return selfHref;
	}

	public void setSelfHref(String selfHref) {
		this.selfHref = selfHref;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getMeterNumber() {
		return meterNumber;
	}

	public void setMeterNumber(String meterNumber) {
		this.meterNumber = meterNumber;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getStreetCity() {
		return streetCity;
	}

	public void setStreetCity(String streetCity) {
		this.streetCity = streetCity;
	}

	public String getStreetProvince() {
		return streetProvince;
	}

	public void setStreetProvince(String streetProvince) {
		this.streetProvince = streetProvince;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getStreetUnit() {
		return streetUnit;
	}

	public void setStreetUnit(String streetUnit) {
		this.streetUnit = streetUnit;
	}

	public String toString() {
		return selfHref;
	}

}
