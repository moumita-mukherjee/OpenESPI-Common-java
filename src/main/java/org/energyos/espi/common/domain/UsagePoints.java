package org.energyos.espi.common.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.energyos.espi.common.models.atom.adapters.CustomerAdapter;
@XmlRootElement(name="UsagePoints", namespace="http://naesb.org/espi/cust")
@XmlJavaTypeAdapter(CustomerAdapter.class)
@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UsagePoints")
public class UsagePoints {
	public List<String> UsagePoint=new ArrayList<String>();

	public List<String> getUsagePoint() {
		return UsagePoint;
	}


}
