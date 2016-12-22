package org.energyos.espi.common.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "address")
@SuppressWarnings("serial")
@XmlRootElement(name = "Address", namespace = "http://naesb.org/espi")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Address")
public class Address {

	@Id
	@GeneratedValue
//	@XmlTransient
	@Column(name = "id")
	private Long id;

	@Embedded
	private AddressStreetDetails street;

	@Embedded
	private AddressTownDetails town;

	@Embedded
	private Status status;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AddressStreetDetails getStreet() {
		return street;
	}

	public void setStreet(AddressStreetDetails street) {
		this.street = street;
	}

	public AddressTownDetails getTown() {
		return town;
	}

	public void setTown(AddressTownDetails town) {
		this.town = town;
	}
}