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

@SuppressWarnings("serial")
@XmlRootElement(name = "CustomerAccount", namespace = "http://naesb.org/espi")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerAccount")
@Entity
@Table(name = "customer_account")
@NamedQueries(value = {
		
		@NamedQuery(name = CustomerAccount.QUERY_FIND_ALL_IDS, query = "SELECT customeraccount.id FROM CustomerAccount customeraccount"),
		
		@NamedQuery(name = CustomerAccount.QUERY_FIND_BY_ID, query = "SELECT customeraccount FROM CustomerAccount customeraccount WHERE customeraccount.id = :id"),
		
		@NamedQuery(name = CustomerAccount.QUERY_FIND_BY_CUSTOMER_ID_ACCOUNT_ID, query = "SELECT customeraccount FROM CustomerAccount customerAccount where  customeraccount.id = :id and customerAccount.customer.id = :customerId"),
		
		@NamedQuery(name = CustomerAccount.QUERY_FIND_BY_RETAILCUSTOMER_ID_CUSTOMER_ID_ACCOUNT_ID, query = "SELECT customeraccount FROM CustomerAccount customeraccount where customerAccount.customer.retailCustomerId = :retailCustomerId and customerAccount.customer.id= :customerId and customerAccount.id= :accountId "),
		
		@NamedQuery(name = CustomerAccount.QUERY_FIND_BY_CUSTOMER_ID, query = "SELECT customeraccount FROM CustomerAccount customerAccount where  customerAccount.customer.id= :customerId and customerAccount.customer.retailCustomerId = :retailCustomerId")
		
		
})
public class CustomerAccount extends IdentifiedObject {

	public static final String QUERY_FIND_ALL_IDS = "CustomerAccount.findAllIds";
	public static final String QUERY_FIND_BY_ID = "CustomerAccount.findById";
	public static final String QUERY_FIND_BY_CUSTOMER_ID_ACCOUNT_ID = "CustomerAccount.findByCustomerIdCustomerAccountId";
	public static final String QUERY_FIND_BY_RETAILCUSTOMER_ID_CUSTOMER_ID_ACCOUNT_ID = "CustomerAccount.findByRetailCustomerIdCustomerIdCustomerAccountId";
	public static final String QUERY_FIND_BY_CUSTOMER_ID = "CustomerAccount.findByCustomerId";

	@Column(name = "name")
	protected String name;

	@Column(name = "enabled")
	protected boolean enabled;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	@Embedded
	protected Status status;
	

	@Column(name = "createdDateTime")
	protected Date createdDateTime;

	@Column(name = "lastModifiedDateTime")
	protected Date lastModifiedDateTime;

	@Column(name = "revisionNumber")
	protected String revisionNumber;

	@Column(name = "billingCycle")
	private String billingCycle;

	@Column(name = "budgetBill")
	private String budgetBill;

	@XmlTransient
	@Transient
	private String link;
	


	@XmlTransient
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id")
	private Customer customer;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getLink() {
		if(customer!=null)
			return "/Customer/" + this.customer.id;
		else 
			return null;
	}

	public void setLink(Long customerId) {
		this.link = "/Customer/" + customerId;
	}

	public CustomerAccount() {
		super();
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "customerAccount", cascade = CascadeType.REMOVE)
	private Set<CustomerAgreement> customerAgreements;

	public Set<CustomerAgreement> getCustomerAgreements() {
		return customerAgreements;
	}

	public void setCustomerAgreements(Set<CustomerAgreement> customerAgreements) {
		this.customerAgreements = customerAgreements;
	}

	/**
	 * Gets the value of the status property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getName() {
		return name;
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
	 * @return possible object is {@link String }
	 * 
	 */
	public String getBudgetBill() {
		return budgetBill;
	}

	/**
	 * Sets the value of the status property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setBudgetBill(String budgetBill) {
		this.budgetBill = budgetBill;
	}

	/**
	 * Gets the value of the status property.
	 * 
	 * @return possible object is {@link Date }
	 * 
	 */
	public Date getLastModifiedDateTime() {
		return lastModifiedDateTime;
	}

	/**
	 * Sets the value of the status property.
	 * 
	 * @param value
	 *            allowed object is {@link Date }
	 * 
	 */
	public void setLastModifiedDateTime(Date lastModifiedDateTime) {
		this.lastModifiedDateTime = lastModifiedDateTime;
	}

	/**
	 * Gets the value of the status property.
	 * 
	 * @return possible object is {@link Date }
	 * 
	 */
	/*
    
   
   
    *//**
	 * Gets the value of the status property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRevisionNumber() {
		return revisionNumber;
	}

	/**
	 * Sets the value of the status property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setRevisionNumber(String revisionNumber) {
		this.revisionNumber = revisionNumber;
	}

	/**
	 * Gets the value of the status property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getBillingCycle() {
		return billingCycle;
	}

	/**
	 * Sets the value of the status property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setBillingCycle(String billingCycle) {
		this.billingCycle = billingCycle;
	}

	@Override
	public void merge(IdentifiedObject resource) {
		super.merge(resource);
	}

	@Override
	public String toString() {
		return "CustomerAccount [name=" + name + "]";
	}

	
	

}
