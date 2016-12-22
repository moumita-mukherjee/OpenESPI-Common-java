package org.energyos.espi.common.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ElectronicAddress {
	
	@Column(name = "electronicaddress_lan")
	String lan;
	
	@Column(name = "electronicaddress_mac")
	String mac;
	
	@Column(name = "electronicaddress_email1")
	String email1;
	
	@Column(name = "electronicaddress_email2")
	String email2;
	
	@Column(name = "electronicaddress_web")
	String web;
	
	@Column(name = "electronicaddress_radio")
	String radio;
	
	@Column(name = "electronicaddress_userId")
	String userId;
	
	@Column(name = "electronicaddress_password")
	String password;

	public String getLan() {
		return lan;
	}

	public void setLan(String lan) {
		this.lan = lan;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getRadio() {
		return radio;
	}

	public void setRadio(String radio) {
		this.radio = radio;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}