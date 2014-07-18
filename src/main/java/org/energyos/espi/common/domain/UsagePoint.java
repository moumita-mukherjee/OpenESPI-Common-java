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

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2013.08.27 at 01:43:57 PM EDT
//

package org.energyos.espi.common.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.energyos.espi.common.models.atom.LinkType;
import org.energyos.espi.common.models.atom.adapters.UsagePointAdapter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


/**
 * Logical point on a network at which consumption or production is either physically measured (e.g., metered) or estimated (e.g., unmetered street lights).
 * <p/>
 * <p>Java class for UsagePoint complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="UsagePoint">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi}IdentifiedObject">
 *       &lt;sequence>
 *         &lt;element name="roleFlags" type="{http://naesb.org/espi}HexBinary16" minOccurs="0"/>
 *         &lt;element name="ServiceCategory" type="{http://naesb.org/espi}ServiceCategory" minOccurs="0"/>
 *         &lt;element name="status" type="{http://naesb.org/espi}UInt8" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlRootElement(name="UsagePoint")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UsagePoint", propOrder = {
        "roleFlags",
        "serviceCategory",
        "status",
        "serviceDeliveryPoint"
})
@Entity
@Table(name = "usage_points", uniqueConstraints = {@UniqueConstraint(columnNames={"uuid"})})
@NamedQueries(value = {
        @NamedQuery(name = UsagePoint.QUERY_FIND_ALL_BY_RETAIL_CUSTOMER_ID,
                query = "SELECT point FROM UsagePoint point WHERE point.retailCustomer.id = :retailCustomerId"),
        @NamedQuery(name = UsagePoint.QUERY_FIND_BY_UUID,
                query = "SELECT point FROM UsagePoint point WHERE point.uuid = :uuid"),
        @NamedQuery(name = UsagePoint.QUERY_FIND_BY_ID,
                query = "SELECT point FROM UsagePoint point WHERE point.id = :id"),
        @NamedQuery(name = UsagePoint.QUERY_FIND_ALL_UPDATED_FOR,
                query = "SELECT point FROM UsagePoint point WHERE point.updated > :lastUpdate"),
        @NamedQuery(name = UsagePoint.QUERY_FIND_BY_RELATED_HREF,
                query = "SELECT point FROM UsagePoint point join point.relatedLinks link WHERE link.href = :href"),
        @NamedQuery(name = UsagePoint.QUERY_FIND_ALL_RELATED,
                query = "SELECT timeConfiguration FROM TimeConfiguration timeConfiguration WHERE timeConfiguration.selfLink.href in (:relatedLinkHrefs)"),
        @NamedQuery(name = UsagePoint.QUERY_FIND_BY_URI,
                query = "SELECT point FROM UsagePoint point WHERE point.uri = :uri"),
        @NamedQuery(name = UsagePoint.QUERY_FIND_ALL_IDS_FOR_RETAIL_CUSTOMER,
                query = "SELECT point.id from UsagePoint point where point.retailCustomer.id = :retailCustomerId"),
        @NamedQuery(name = UsagePoint.QUERY_FIND_ALL_IDS, query = "SELECT point.id from UsagePoint point"),
        @NamedQuery(name = UsagePoint.QUERY_FIND_ALL_IDS_BY_XPATH_1, query = "SELECT DISTINCT u.id FROM UsagePoint u WHERE u.retailCustomer.id = :o1Id"),
        @NamedQuery(name = UsagePoint.QUERY_FIND_ID_BY_XPATH, query = "SELECT DISTINCT u.id FROM UsagePoint u WHERE u.retailCustomer.id = :o1Id AND u.id = :o2Id")

})

@XmlJavaTypeAdapter(UsagePointAdapter.class)
public class UsagePoint
        extends IdentifiedObject
{
    public static final String QUERY_FIND_ALL_BY_RETAIL_CUSTOMER_ID = "UsagePoint.findUsagePointsByRetailCustomer";
    public static final String QUERY_FIND_BY_UUID = "UsagePoint.findByUUID";
    public static final String QUERY_FIND_BY_ID = "UsagePoint.findById";
    public static final String QUERY_FIND_ALL_UPDATED_FOR = "UsagePoint.findAllUpdatedFor";
    public static final String QUERY_FIND_BY_RELATED_HREF = "UsagePoint.findByAllParentsHref";
    public static final String QUERY_FIND_ALL_RELATED = "UsagePoint.findAllRelated";
    public static final String QUERY_FIND_BY_URI = "UsagePoint.findByURI";
    public static final String QUERY_FIND_ALL_IDS_FOR_RETAIL_CUSTOMER = "UsagePoint.findAllIdsForRetailCustomer";
    public static final String QUERY_FIND_ALL_IDS = "UsagePoint.findAllIds";
    public static final String QUERY_FIND_ALL_IDS_BY_XPATH_1 = "UsagePoint.findAllIdsByXpath1";
    public static final String QUERY_FIND_ID_BY_XPATH = "UsagePoint.findIdByXpath";

    
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    protected byte[] roleFlags;

    @XmlElement(name = "ServiceCategory")
    @NotNull
    //@ManyToOne (cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    protected ServiceCategory serviceCategory;

    @XmlElement(name = "ServiceDeliveryPoint")
    @OneToOne(cascade = {CascadeType.ALL})
    protected ServiceDeliveryPoint serviceDeliveryPoint;
    
    
    @XmlTransient    
    @Transient
 
    protected UsagePointDetail usagePointDetail;
    

    public UsagePointDetail getUsagePointDetail() {
		return usagePointDetail;
	}

	public void setUsagePointDetail(UsagePointDetail usagePointDetail) {
		this.usagePointDetail = usagePointDetail;
	}

	protected Short status;

    @XmlTransient
    @OneToMany(mappedBy = "usagePoint", cascade = {CascadeType.ALL}, orphanRemoval=true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @OrderBy ("readingType.id asc")
    private List<MeterReading> meterReadings = new ArrayList<>();

    @XmlTransient
    @OneToMany(mappedBy = "usagePoint", cascade = {CascadeType.ALL}, orphanRemoval=true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<ElectricPowerUsageSummary> electricPowerUsageSummaries = new ArrayList<>();

    @XmlTransient
    @OneToMany(mappedBy = "usagePoint", cascade = {CascadeType.ALL}, orphanRemoval=true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<ElectricPowerQualitySummary> electricPowerQualitySummaries = new ArrayList<>();

    @XmlTransient
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    //@JoinColumn(name = "local_time_parameters_id")
    private TimeConfiguration localTimeParameters;

    @XmlTransient
    @ManyToMany(mappedBy = "usagePoints")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Subscription> subscriptions = new HashSet<>();

    @XmlTransient
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name="usage_point_related_links", joinColumns=@JoinColumn(name="usage_point_id"))
    private List<LinkType> relatedLinks = new ArrayList<>();

    @XmlTransient
    private String uri;

    @XmlTransient
    @OneToOne
    private Subscription subscription;

    public void addMeterReading(MeterReading meterReading)
    {
        meterReading.setUsagePoint(this);
        meterReadings.add(meterReading);
    }

    public void removeMeterReading(MeterReading meterReading)
    {
        meterReading.setUsagePoint(null);
        meterReadings.remove(meterReading);
    }
    
    @XmlTransient
    @ManyToOne
    @JoinColumn(name="retail_customer_id")
    protected RetailCustomer retailCustomer;

    public String getSelfHref() {
        return getUpHref() + "/" + getHashedId();
    }

    public String getUpHref() {
        if (getRetailCustomer() != null) {
            return "RetailCustomer/" + getRetailCustomer().getHashedId() + "/UsagePoint";
        }
        return null;
    }

    /**
     * Gets the value of the roleFlags property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public byte[] getRoleFlags() {
        return roleFlags;
    }

    /**
     * Sets the value of the roleFlags property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setRoleFlags(byte[] value) {
        this.roleFlags = value;
    }

    /**
     * Gets the value of the serviceCategory property.
     *
     * @return
     *     possible object is
     *     {@link ServiceCategory }
     *
     */
    public ServiceCategory getServiceCategory() {
        return serviceCategory;
    }

    /**
     * Sets the value of the serviceCategory property.
     *
     * @param value
     *     allowed object is
     *     {@link ServiceCategory }
     *
     */
    public void setServiceCategory(ServiceCategory value) {
        this.serviceCategory = value;
    }

    /**
     * Gets the value of the status property.
     *
     * @return
     *     possible object is
     *     {@link Short }
     *
     */
    public Short getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     *
     * @param value
     *     allowed object is
     *     {@link Short }
     *
     */
    public void setStatus(Short value) {
        this.status = value;
    }

    public List<MeterReading> getMeterReadings() {
        return meterReadings;
    }

    public void setMeterReadings(List<MeterReading> meterReadings) {
        this.meterReadings = meterReadings;
    }

    public RetailCustomer getRetailCustomer() {
        return retailCustomer;
    }

    public void setRetailCustomer(RetailCustomer retailCustomer) {
        this.retailCustomer = retailCustomer;
    }

    public List<ElectricPowerUsageSummary> getElectricPowerUsageSummaries() {
        return electricPowerUsageSummaries;
    }

    public void addElectricPowerUsageSummary(ElectricPowerUsageSummary electricPowerUsageSummary) {
        //electricPowerUsageSummary.setUsagePoint(this);
        electricPowerUsageSummaries.add(electricPowerUsageSummary);
    }

    public void removeElectricPowerUsageSummary(ElectricPowerUsageSummary electricPowerUsageSummary) {
    	//electricPowerUsageSummary.setUsagePoint(null);
    	electricPowerUsageSummaries.remove(electricPowerUsageSummary);
    }
    
    public List<ElectricPowerQualitySummary> getElectricPowerQualitySummaries() {
        return electricPowerQualitySummaries;
    }

    public void removeElectricPowerQualitySummary(ElectricPowerQualitySummary electricPowerQualitySummary) {
    	//electricPowerQualitySummary.setUsagePoint(null);
    	electricPowerQualitySummaries.remove(electricPowerQualitySummary);
    }
    
    public void setElectricPowerQualitySummaries(List<ElectricPowerQualitySummary> electricPowerQualitySummaries) {
        this.electricPowerQualitySummaries = electricPowerQualitySummaries;
    }

    public void addElectricPowerQualitySummary(ElectricPowerQualitySummary electricPowerQualitySummary) {
        //electricPowerQualitySummary.setUsagePoint(this);
        electricPowerQualitySummaries.add(electricPowerQualitySummary);
    }

    public TimeConfiguration getLocalTimeParameters() {
        return localTimeParameters;
    }

    public void setLocalTimeParameters(TimeConfiguration localTimeParameters) {
        this.localTimeParameters = localTimeParameters;
    }

    public ServiceDeliveryPoint getServiceDeliveryPoint() {
        return serviceDeliveryPoint;
    }

    public void setServiceDeliveryPoint(ServiceDeliveryPoint serviceDeliveryPoint) {
        this.serviceDeliveryPoint = serviceDeliveryPoint;
    }

    public Set<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Set<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public void addSubscription(Subscription subscription){
    	this.subscriptions.add(subscription);
    }
    
    public void removeSubscription(Subscription subscription) {
    	this.subscriptions.remove(subscription);
    	subscription.removeUsagePoint(this);
    }
    
    public void setRelatedLinks(List<LinkType> relatedLinks) {
        this.relatedLinks = relatedLinks;
    }

    public List<LinkType> getRelatedLinks() {
        return relatedLinks;
    }

    @Override
    public void setUpResource(IdentifiedObject resource) {
    }

    @Override
    public String getParentQuery() {
        return QUERY_FIND_BY_RELATED_HREF;
    }

    @Override
    public String getAllRelatedQuery() {
        return QUERY_FIND_ALL_RELATED;
    }

    @Override
    public void merge(IdentifiedObject resource) {
    	super.merge(resource);
        this.setRelatedLinks(resource.getRelatedLinks());
        this.setServiceCategory(((UsagePoint)resource).getServiceCategory());
    }

	@Override
	public void unlink() {
		super.unlink();

		getRelatedLinks().clear();
		getElectricPowerQualitySummaries().clear();
		getElectricPowerUsageSummaries().clear();
		getMeterReadings().clear();

		setRetailCustomer(null);
		getSubscriptions().clear();

	}
    
    public String getURI() {
        return uri;
    }

    public void setURI(String URI) {
        this.uri = URI;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }
    public boolean equals (UsagePoint up) {
    	return (this.getId().equals(up.getId()));
    }
	
}
