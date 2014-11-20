/*
 * Copyright 2013 EnergyOS.org
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
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.energyos.espi.common.models.atom.LinkType;
import org.energyos.espi.common.models.atom.adapters.MeterReadingAdapter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


/**
 * Set of values obtained from the meter.
 * <p/>
 * <p>Java class for MeterReading complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="MeterReading">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi}IdentifiedObject">
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlRootElement(name="MeterReading")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MeterReading")
@Entity
@Table(name = "meter_readings", uniqueConstraints = {@UniqueConstraint(columnNames={"uuid"})})
@XmlJavaTypeAdapter(MeterReadingAdapter.class)
@NamedQueries(value = {
        @NamedQuery(name = MeterReading.QUERY_FIND_BY_ID,
                query = "SELECT reading FROM MeterReading reading WHERE reading.id = :id"),
        @NamedQuery(name = MeterReading.QUERY_FIND_BY_LINK, query = "SELECT reading FROM MeterReading reading WHERE reading.selfLink.href = :link"),
        @NamedQuery(name = MeterReading.QUERY_FIND_BY_UUID,
                query = "SELECT meterReading FROM MeterReading meterReading WHERE meterReading.uuid = :uuid"),
        @NamedQuery(name = MeterReading.QUERY_FIND_BY_RELATED_HREF,
                query = "SELECT reading FROM MeterReading reading join reading.relatedLinks link WHERE link.href = :href"),
        @NamedQuery(name = MeterReading.QUERY_FIND_ALL_RELATED,
                query = "SELECT readingType FROM ReadingType readingType WHERE readingType.selfLink.href in (:relatedLinkHrefs)"),
        @NamedQuery(name = MeterReading.QUERY_FIND_ALL_IDS_BY_USAGE_POINT_ID,
                query = "SELECT reading.id FROM MeterReading reading WHERE reading.usagePoint.id = :usagePointId"),
        @NamedQuery(name = MeterReading.QUERY_FIND_ALL_IDS,
                query = "SELECT meterReading.id FROM MeterReading meterReading"),
        @NamedQuery(name = MeterReading.QUERY_FIND_ALL_IDS_BY_XPATH_2, query = "SELECT DISTINCT m.id FROM UsagePoint u, MeterReading m WHERE u.retailCustomer.id = :o1Id AND m.usagePoint.id = :o2Id"),
        @NamedQuery(name = MeterReading.QUERY_FIND_ID_BY_XPATH, query = "SELECT DISTINCT m.id FROM  UsagePoint u, MeterReading m WHERE u.retailCustomer.id = :o1Id AND m.usagePoint.id = :o2Id AND m.id = :o3Id")

})

public class MeterReading extends IdentifiedObject
{
    public static final String QUERY_FIND_BY_UUID = "MeterReading.findByUUID";
    public static final String QUERY_FIND_BY_ID = "MeterReading.findById";
    public static final String QUERY_FIND_BY_LINK = "MeterReading.findByLink";
    public static final String QUERY_FIND_BY_RELATED_HREF = "MeterReading.findByAllParentsHref";
    public static final String QUERY_FIND_ALL_RELATED = "MeterReading.findAllRelated";
    public static final String QUERY_FIND_ALL_IDS_BY_USAGE_POINT_ID = "MeterReading.findAllIdsByUsagePointId";
	public static final String QUERY_FIND_ALL_IDS = "MeterReading.findAllIds";
    public static final String QUERY_FIND_ALL_IDS_BY_XPATH_2 = "MeterReading.findAllIdsByXpath2";
    public static final String QUERY_FIND_ID_BY_XPATH = "MeterReading.findIdByXpath";
    
    //DJ
    @XmlTransient        
    //@OneToMany(mappedBy = "meterReading", cascade = {CascadeType.ALL}, orphanRemoval=true)
    //@LazyCollection(LazyCollectionOption.TRUE)
    @Transient
    private List<IntervalBlock> intervalBlocks = new ArrayList<>();

    @XmlTransient
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name="meter_reading_related_links", joinColumns=@JoinColumn(name="meter_reading_id"))
    private List<LinkType> relatedLinks = new ArrayList<>();

    @XmlTransient
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "usage_point_id")
    private UsagePoint usagePoint;

    @XmlTransient
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "reading_type_id")
    private ReadingType readingType;
    
    @XmlTransient
    @Column(name = "interval_reading_from")
	protected Date intervalReadingFrom =null;
    

    @XmlTransient
    @Column(name = "interval_reading_till")
    protected Date intervalReadingTill =null;

    public Date getIntervalReadingFrom() {
		return intervalReadingFrom;
	}

	public void setIntervalReadingFrom(Date intervalReadingFrom) {
		this.intervalReadingFrom = intervalReadingFrom;
	}

	public Date getIntervalReadingTill() {
		return intervalReadingTill;
	}

	public void setIntervalReadingTill(Date intervalReadingTill) {
		this.intervalReadingTill = intervalReadingTill;
	}

	public UsagePoint getUsagePoint() {
        return usagePoint;
    }

    public void setUsagePoint(UsagePoint usagePoint) {
        this.usagePoint = usagePoint;
    }

    public void addIntervalBlock(IntervalBlock intervalBlock) {
        intervalBlock.setMeterReading(this);
        //DJ
        intervalBlock.setMeterReadingId(getId());
        intervalBlocks.add(intervalBlock);
    }

    public void removeIntervalBlock(IntervalBlock intervalBlock) {
    	intervalBlock.setMeterReading(null);
    	//DJ
    	intervalBlock.setMeterReadingId(null);
    	intervalBlocks.remove(intervalBlock);
    }
    
    public ReadingType getReadingType() {
        return readingType;
    }

    public void setReadingType(ReadingType readingType) {
        this.readingType = readingType;
    }

    public void removeReadingType(ReadingType readingType) {
    	this.readingType = null;
    }
    
    public List<IntervalBlock> getIntervalBlocks() {
          return intervalBlocks;
    }

    public void setIntervalBlocks(List<IntervalBlock> intervalBlocks) {
        this.intervalBlocks = intervalBlocks;
    }

    @Override
    public void setUpResource(IdentifiedObject resource) {
        UsagePoint usagePoint = (UsagePoint)resource;
        usagePoint.addMeterReading(this);
    }

    @Override
    public String getParentQuery() {
        return UsagePoint.QUERY_FIND_BY_RELATED_HREF;
    }

    @Override
    public String getAllRelatedQuery() {
        return MeterReading.QUERY_FIND_ALL_RELATED;
    }

    public List<LinkType> getRelatedLinks() {
        return relatedLinks;
    }

    public void setRelatedLinks(List<LinkType> relatedLinks) {
        this.relatedLinks = relatedLinks;
    }
    
    //DJ @Transactional
    @Override 
    public void merge(IdentifiedObject resource) {
    	
    	super.merge(resource);
        
    	/*DJ
    	for (IntervalBlock bl : ((MeterReading)resource).getIntervalBlocks()) {
    		
    		// TODO: Validate that the UUIDs are equal!!
    		if (this.intervalBlocks.contains(bl)) {
    			this.intervalBlocks.remove(bl);
    		}    	
    		this.intervalBlocks.add(bl);
    	}
		*/
    	if (((MeterReading)resource).readingType != null) {
    		this.readingType = ((MeterReading)resource).readingType;
    	}
    	if (((MeterReading)resource).usagePoint != null) {
    		this.usagePoint = ((MeterReading)resource).usagePoint;
    	}
    }
    
	@Override
	public void unlink() {
        super.unlink();
        
		getIntervalBlocks().clear();
		getRelatedLinks().clear();
		setReadingType(null);
		setUsagePoint(null);

	}
    
}
