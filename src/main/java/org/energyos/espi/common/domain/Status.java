
package org.energyos.espi.common.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.joda.time.DateTime;

@XmlAccessorType(XmlAccessType.FIELD)
@Embeddable
public class Status {

    @Column(name = "status_value")
    protected String statusvalue;
    @Column(name = "status_dateTime")
    protected DateTime statusDateTime;
    @Column(name = "status_remark")
    protected String statusremark;
    @Column(name = "status_reason")
    protected String statusreason;

  
    public String getStatusValue() {
        return statusvalue;
    }

   
    public void setStatusValue(String statusvalue) {
        this.statusvalue = statusvalue;
    }

    public String getStatusRemark() {
        return statusremark;
    }

   
    public void setStatusRemark(String statusremark) {
        this.statusremark = statusremark;
    }
    
    
    public String getStatusReason() {
        return statusreason;
    }

   
    public void setStatusReason(String statusreason) {
        this.statusreason = statusreason;
    }
  
    
    public DateTime getStatusDateTime() {
        return statusDateTime;
    }

 
    public void setStatusDateTime(DateTime statusDateTime) {
        this.statusDateTime = statusDateTime;
    }

}
