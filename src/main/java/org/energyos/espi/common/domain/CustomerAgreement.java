package org.energyos.espi.common.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.energyos.espi.common.models.atom.adapters.CustomerAdapter;

@SuppressWarnings("serial")
@XmlRootElement(name = "CustomerAgreement", namespace = "http://naesb.org/espi")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerAgreement")
@XmlJavaTypeAdapter(CustomerAdapter.class)
@Entity
@Table(name = "customer_agreement")
@NamedQueries(value = {
		@NamedQuery(name = CustomerAgreement.QUERY_FIND_ALL_IDS, query = "SELECT customeragreement.id FROM CustomerAgreement customeragreement"),
		@NamedQuery(name = CustomerAgreement.QUERY_FIND_BY_ID, query = "SELECT customeragreement FROM CustomerAgreement customeragreement WHERE customeragreement.id = :id"),
		
		@NamedQuery(name = CustomerAgreement.QUERY_FIND_BY_RETAILCUSTOMER_ID_CUSTOMER_ID_ACCOUNT_ID_AGREEMENT_ID, query = "SELECT customeragreement FROM CustomerAgreement customeragreement WHERE customeragreement.customerAccount.customer.retailCustomerId = :retailCustomerId and customeragreement.customerAccount.customer.id = :customerId and customeragreement.customerAccount.id = :customerAccountId and customeragreement.id = :agreementId"),
		
		@NamedQuery(name = CustomerAgreement.QUERY_FIND_BY_RETAILCUSTOMER_ID_CUSTOMER_ID_ACCOUNT_ID, query = "SELECT customeragreement FROM CustomerAgreement customeragreement WHERE customeragreement.customerAccount.customer.retailCustomerId = :retailCustomerId and customeragreement.customerAccount.customer.id = :customerId and customeragreement.customerAccount.id = :accountId"),
		
		@NamedQuery(name = CustomerAgreement.QUERY_FIND_BY_CUSTOMER_ID_ACCOUNT_ID, query = "SELECT customeragreement FROM CustomerAgreement customeragreement WHERE customeragreement.customerAccount.customer.id = :customerId and customeragreement.customerAccount.id = :customerAccountId "),

		
})
public class CustomerAgreement extends IdentifiedObject {

	public static final String QUERY_FIND_ALL_IDS = "CustomerAgreement.findAllIds";
	public static final String QUERY_FIND_BY_ID = "CustomerAgreement.findById";
	public static final String QUERY_FIND_BY_RETAILCUSTOMER_ID_CUSTOMER_ID_ACCOUNT_ID_AGREEMENT_ID = "CustomerAgreement.findByRetailCustomerIdCustomerIdAccountIdAgreementId";
	public static final String QUERY_FIND_BY_RETAILCUSTOMER_ID_CUSTOMER_ID_ACCOUNT_ID = "CustomerAgreement.findByRetailCustomerIdCustomerIdAccountId";
	
	public static final String QUERY_FIND_BY_CUSTOMER_ID_ACCOUNT_ID = "CustomerAgreement.findByCustomerIdAccountId";

	@Column(name = "name")
	protected String name;

	@Column(name = "enabled")
	protected boolean enabled = true;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	@Embedded
	protected Status status;

	@Column(name = "created")
	protected Date createdDateTime;
	
	@Column(name = "revision_number")
	protected String revisionNumber;

	
	
	@Column(name = "sign_date")
	protected Date signDate;
	
	@Column(name = "validity_interval")
	protected Date validityInterval;


	@Column(name = "load_mgmt")
	private String loadMgmt;

	@Column(name = "pricing_structures")
	private String pricingStructures;

	@Column(name = "demand_response_programs")
	private String demandResponsePrograms;
	
	/*@Transient
	private Long customerAccountId;*/

	@XmlTransient
	@Transient
	private String link;

	public String getLink() {
		if(customerAccount!=null)
			return this.customerAccount.getLink() + "/CustomerAccount/"
				+ this.customerAccount.getId();
		else
			return null;
	}

	public void setLink(Long customerId, Long accountId) {
		this.link = "/Customer/" + customerId + "/CustomerAccount/" + accountId;
	}

	@XmlTransient
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_account_id")
	private CustomerAccount customerAccount;

	public CustomerAgreement() {
		super();
	}

	public CustomerAccount getCustomerAccount() {
		return customerAccount;
	}

	public void setCustomerAccount(CustomerAccount customerAccount) {
		this.customerAccount = customerAccount;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "customerAgreement", cascade = CascadeType.REMOVE)
	private Set<ServiceLocation> serviceLocations;

	@XmlTransient
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "customerAgreement", cascade = CascadeType.REMOVE)
	private Set<ServiceSupplier> serviceSuppliers;

	/**
	 * Gets the value of the status property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getName() {
		return name;
	}

	public Set<ServiceLocation> getServiceLocations() {
		return serviceLocations;
	}

	public void setServiceLocations(Set<ServiceLocation> serviceLocations) {
		this.serviceLocations = serviceLocations;
	}

	public Set<ServiceSupplier> getServiceSuppliers() {
		return serviceSuppliers;
	}

	public void setServiceSuppliers(Set<ServiceSupplier> serviceSuppliers) {
		this.serviceSuppliers = serviceSuppliers;
	}

	/**
	 * Sets the value of the status property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	/**
	 * Gets the value of the status property.
	 * 
	 * @return possible object is {@link DateTimeInterval }
	 * 
	 */
	public String getLoadMgmt() {
		return loadMgmt;
	}

	/**
	 * Sets the value of the status property.
	 * 
	 * @param value
	 *            allowed object is {@link DateTimeInterval }
	 * 
	 */
	public void setLoadMgmt(String loadMgmt) {
		this.loadMgmt = loadMgmt;
	}

	/**
	 * Gets the value of the status property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPricingStructures() {
		return pricingStructures;
	}

	/**
	 * Sets the value of the status property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPricingStructures(String pricingStructures) {
		this.pricingStructures = pricingStructures;
	}

	/**
	 * Gets the value of the status property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDemandResponsePrograms() {
		return demandResponsePrograms;
	}

	/**
	 * Sets the value of the status property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDemandResponsePrograms(String demandResponsePrograms) {
		this.demandResponsePrograms = demandResponsePrograms;
	}

	@Override
	public void merge(IdentifiedObject resource) {

		super.merge(resource);
	}

	@Override
	public String toString() {
		return "CustomerAgreement [name=" + name + "]";
	}

	public String getRevisionNumber() {
		return revisionNumber;
	}

	public void setRevisionNumber(String revisionNumber) {
		this.revisionNumber = revisionNumber;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public Date getValidityInterval() {
		return validityInterval;
	}

	public void setValidityInterval(Date validityInterval) {
		this.validityInterval = validityInterval;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	/*public Long getCustomerAccountId() {
		return customerAccountId;
	}

	public void setCustomerAccountId(Long customerAccountId) {
		this.customerAccountId = customerAccountId;
	}*/

	

}
