package org.energyos.espi.common.domain;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@XmlRootElement(name = "Customer", namespace = "http://naesb.org/espi")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Customer")
@Entity
@Table(name = "customer")
@NamedQueries(value = {
		@NamedQuery(name = Customer.QUERY_FIND_ALL_IDS, query = "SELECT customer.id FROM Customer customer"),
		@NamedQuery(name = Customer.QUERY_FIND_BY_ID, query = "SELECT customer FROM Customer customer where customer.id = :id"),
		@NamedQuery(name = Customer.QUERY_FIND_ALL_BY_RETAIL_CUSTOMER_ID, query = "SELECT customer FROM Customer customer where customer.retailCustomerId=:retailCustomerId"),
		@NamedQuery(name = Customer.QUERY_FIND_BY_RETAIL_CUSTOMER_ID_CUSTOMER_ID, query = "SELECT customer FROM Customer customer where customer.retailCustomerId=:retailCustomerId and customer.id=:id"),
		@NamedQuery(name = Customer.QUERY_FIND_ID_BY_XPATH, query = "SELECT DISTINCT r FROM Customer r WHERE r.id = :o1Id") })
public class Customer extends IdentifiedObject {

	public static final String QUERY_FIND_ALL_IDS = "Customer.findAllIds";
	public static final String QUERY_FIND_BY_ID = "Customer.findById";
	public static final String QUERY_FIND_ALL_BY_RETAIL_CUSTOMER_ID = "Customer.findByRetailCustomerId";
	public static final String QUERY_FIND_BY_RETAIL_CUSTOMER_ID_CUSTOMER_ID = "Customer.findByRetailCustomerIdCustomerId";
	public static final String QUERY_FIND_ID_BY_XPATH = "Customer.findIdsByXpath";

	@Column(name = "enabled")
	protected boolean enabled = true;

	@Column(name = "name")
	protected String name;
	
	
	@Column(name = "supplierId")
	protected Long supplierId;

	@Column(name = "kind")
	protected String kind;

	//@XmlTransient
	@Column(name = "specialNeed")
	private String specialNeed;

	//@XmlTransient
	@Column(name = "vip")
	private Boolean vip;

	//@XmlTransient
	@Column(name = "pucNumber")
	private String pucNumber;

	@Embedded
	protected Status status;

	//@XmlTransient
	@Column(name = "priority")
	private String priority;

	//@XmlTransient
	@Column(name = "locale")
	private String locale;

	@XmlTransient
	@Column(name = "retail_customer_id")
	private Long retailCustomerId;

	

	public Long getRetailCustomerId() {
		return retailCustomerId;
	}

	public void setRetailCustomerId(Long retailCustomerId) {
		this.retailCustomerId = retailCustomerId;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "customer", cascade = CascadeType.REMOVE)
	private Set<CustomerAccount> customerAccounts;

	public Set<CustomerAccount> getCustomerAccounts() {
		return customerAccounts;
	}

	public void setCustomerAccounts(Set<CustomerAccount> customerAccounts) {
		this.customerAccounts = customerAccounts;
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

	/**
	 * Gets the value of the status property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getKind() {
		return kind;
	}

	/**
	 * Sets the value of the status property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setKind(String kind) {
		this.kind = kind;
	}

	/**
	 * Gets the value of the status property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSpecialNeed() {
		return specialNeed;
	}

	/**
	 * Sets the value of the status property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setSpecialNeed(String specialNeed) {
		this.specialNeed = specialNeed;
	}

	/**
	 * Gets the value of the status property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public Boolean getVip() {
		return vip;
	}

	/**
	 * Sets the value of the status property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setVip(Boolean vip) {
		this.vip = vip;
	}

	/**
	 * Gets the value of the status property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPucNumber() {
		return pucNumber;
	}

	/**
	 * Sets the value of the status property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPucNumber(String pucNumber) {
		this.pucNumber = pucNumber;
	}

	/**
	 * Gets the value of the status property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPriority() {
		return priority;
	}

	/**
	 * Sets the value of the status property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}

	/**
	 * Gets the value of the status property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * Sets the value of the status property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	/*public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		title = "Customer";
		this.title = title;
	}*/

	public void merge(Customer resource) {
		this.kind = ((Customer) resource).kind;
		this.name = ((Customer) resource).name;

	}

	@Override
	public String toString() {
		return "Customer [name=" + name + "]";
	}

}
