package org.energyos.espi.common.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AddressTownDetails {

	@Column(name = "town_code")
	private String code;

	@Column(name = "town_section")
	private String section;

	@Column(name = "town_name")
	private String name;

	@Column(name = "town_state_or_province")
	private String stateOrProvince;

	@Column(name = "town_country")
	private String country;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStateOrProvince() {
		return stateOrProvince;
	}

	public void setStateOrProvince(String stateOrProvince) {
		this.stateOrProvince = stateOrProvince;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
