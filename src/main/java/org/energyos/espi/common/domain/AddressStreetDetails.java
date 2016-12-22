package org.energyos.espi.common.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AddressStreetDetails {

	@Column(name = "street_number")
	private String number;

	@Column(name = "street_name")
	private String name;

	@Column(name = "street_suffix")
	private String suffix;

	@Column(name = "street_prefix")
	private String prefix;

	@Column(name = "street_type")
	private String type;

	@Column(name = "street_code")
	private String code;

	@Column(name = "street_building_name")
	private String buildingName;

	@Column(name = "street_suite_number")
	private String suiteNumber;

	@Column(name = "street_address_general")
	private String addressGeneral;

	@Column(name = "street_within_town_limits")
	private boolean withinTownLimits;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getSuiteNumber() {
		return suiteNumber;
	}

	public void setSuiteNumber(String suiteNumber) {
		this.suiteNumber = suiteNumber;
	}

	public String getAddressGeneral() {
		return addressGeneral;
	}

	public void setAddressGeneral(String addressGeneral) {
		this.addressGeneral = addressGeneral;
	}

	public boolean isWithinTownLimits() {
		return withinTownLimits;
	}

	public void setWithinTownLimits(boolean withinTownLimits) {
		this.withinTownLimits = withinTownLimits;
	}

}