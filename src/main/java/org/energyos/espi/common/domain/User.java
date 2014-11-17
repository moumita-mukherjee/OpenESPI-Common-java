package org.energyos.espi.common.domain;

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

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
@Entity
@Table(name = "logins")
@NamedQueries(value = {
		@NamedQuery(name = User.QUERY_FIND_BY_ID, query = "SELECT user FROM User user WHERE user.id = :id"),
		@NamedQuery(name = User.QUERY_FIND_ALL, query = "SELECT user FROM User user"),
		@NamedQuery(name = User.QUERY_FIND_BY_USERNAME, query = "SELECT user FROM User user WHERE user.username = :username") })
public class User implements UserDetails, Principal {

	public final static String QUERY_FIND_BY_ID = "Login.findById";
	public final static String QUERY_FIND_ALL = "Login.findAll";
	public final static String QUERY_FIND_BY_USERNAME = "Login.findByUsername";

	public final static String ROLE_USER = "ROLE_USER";
	public final static String ROLE_CUSTODIAN = "ROLE_CUSTODIAN";

	public User() {
		super();
	}

	/*
	 * @Id
	 * 
	 * @GeneratedValue(strategy = GenerationType.IDENTITY)
	 * 
	 * @XmlTransient protected Long id;
	 */

	@Id
	@Column(name = "id")
	protected Long id;

	@Column(name = "username")
	@Size(min = 4, max = 30)
	protected String username;
	
	@Column(name = "username",insertable=false, updatable=false)
	@Size(min = 4, max = 30)
	protected String rawusername;

	@Column(name = "first_name")
	@NotEmpty
	@Size(max = 30)
	protected String firstName;

	@Column(name = "last_name")
	@NotEmpty
	@Size(max = 30)
	protected String lastName;

	@Column(name = "password")
	@Size(min = 5, max = 100)
	protected String password;

	@Column(name = "role")
	@NotEmpty
	protected String role = ROLE_USER;
	
	
	@Column(name = "salt")	
	protected String salt;


	@Column(name = "customer_id")
	private String customerId;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Column(name = "self_link_href")
	private String selfLink;

	public String getSelfLink() {
		return selfLink;
	}

	public void setSelfLink(String selfLink) {
		this.selfLink = selfLink;
	}
	//Not managed through JPA, as issue in reference embedable column
	@XmlTransient
	@Transient
	protected RetailCustomer retailCustomer;

	public RetailCustomer getRetailCustomer() {
		return retailCustomer;
	}

	public void setRetailCustomer(RetailCustomer retailCustomer) {
		this.retailCustomer = retailCustomer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}
	public String getRawusername() {
		return rawusername;
	}


	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public void setRawusername(String rawusername) {
		this.rawusername = rawusername;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSalt() {
		return this.salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role));
		return authorities;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getName() {
		return getUsername();
	}

	public String getHashedId() {
		return "" + getId();
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		User that = (User) o;

		if (id != null ? !id.equals(that.id) : that.id != null)
			return false;
		if (username != null ? !username.equals(that.username)
				: that.username != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (username != null ? username.hashCode() : 0);
		return result;
	}

	public void merge(User resource) {
		// TODO needs to inherit from Identified Object
		// super.merge(resource)
		this.firstName = resource.firstName;
		this.lastName = resource.lastName;
		this.password = resource.password;
		this.role = resource.role;
		this.username = resource.username;
		this.rawusername = resource.rawusername;
	}
}
