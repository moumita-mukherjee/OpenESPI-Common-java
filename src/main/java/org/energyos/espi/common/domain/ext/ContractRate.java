package org.energyos.espi.common.domain.ext;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "contract_rates")
@NamedQueries(value = {
		@NamedQuery(name = ContractRate.QUERY_FIND_BY_SERVICE, query = "SELECT cr FROM ContractRate cr where cr.customerId =:customerId and cr.selfHref = :selfHref")		
})
public class ContractRate
{
	public static final String QUERY_FIND_BY_SERVICE = "ContractRate.findByService";
    public static final Set<String> RATES_FOR_CONSUMPTION =  new HashSet<String>(Arrays.asList(new String [] { 
            "ERES_DIST",
            "EG<50_DIST",
            "EG>50_DISN",
            "EG>50_DISI",
            "ELRG_DIST",
            "ESTL_DIST",
            "ESEN_DIST",
            "EUSL_DIST"
    }));
    public static final Set<String> RATES_FOR_DEMAND =  new HashSet<String>(Arrays.asList(new String [] { 
    		"EG>50_DISN",
            "EG>50_DISI",
            "ELRG_DIST"
    }));
    public static final Set<String> RATES_FOR_HDWM =  new HashSet<String>(Arrays.asList(new String [] { 
            "ERES_DIST",
            "EG<50_DIST",
            "EG>50_DISN",
            "EG>50_DISI",
            "ELRG_DIST"
    }));


    
    @Id
    @Column(name = "id", insertable = false, updatable = false)
    private String contractId;
    
    
    @Column(name = "customer_id", insertable = false, updatable = false)
    private String customerId;
    

	@Column(name = "self_href", insertable = false, updatable = false)
    private String selfHref;

    
    @Column(name = "account_id", insertable = false, updatable = false)
    private String accountId;
    
    
	@Column(name = "service_id", insertable = false, updatable = false)
    private String serviceId;
    
    @Column(name = "eff_date", insertable = false, updatable = false)
    private Date effDate;
    
    @Column(name = "end_date", insertable = false, updatable = false)
    private Date endDate;
    
    @Column(name = "supply_rate", insertable = false, updatable = false)
    private String supplyRate;
    
    @Column(name = "distribution_rate", insertable = false, updatable = false)
    private String distributionRate;
    
    public String getSupplyRate() { return supplyRate; } 
    public void setSupplyRate(String supplyRate) { this.supplyRate = supplyRate; } 
    public String getDistributionRate() { return distributionRate; }  
    public void setDistributionRate(String distributionRate) { this.distributionRate = distributionRate; }
    public String getContractId() { return contractId; } 
    public void setContractId(String contractId) { this.contractId = contractId; }
    public Date getEffDate() { return effDate; }    
    public void setEffDate(Date effDate) { this.effDate = effDate; }
    public Date getEndDate() { return endDate; }    
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public String getAccountId() {		return accountId;	}
	public void setAccountId(String accountId) {		this.accountId = accountId;	}
	public String getServiceId() {		return serviceId;	}
	public void setServiceId(String serviceId) {		this.serviceId = serviceId;	}  
    public String getCustomerId() {		return customerId;	}
	public void setCustomerId(String customerId) {		this.customerId = customerId;	}
	public String getSelfHref() {		return selfHref;	}
	public void setSelfHref(String selfHref) {		this.selfHref = selfHref;	}
	
    
    
    public boolean isDemand() { return distributionRate == null ? false : RATES_FOR_DEMAND.contains(distributionRate); } 
    
    
    public boolean isConsumption() { return distributionRate == null ? false : RATES_FOR_CONSUMPTION.contains(distributionRate); }
    
    
    public boolean isHdwm() { return distributionRate == null ? false : RATES_FOR_HDWM.contains(distributionRate); }
    
    
    public boolean isRPP() { return supplyRate == null ? false : isHdwm() && supplyRate.endsWith("RPP"); }
    
    
    public boolean isDCB() { return supplyRate == null ? false : supplyRate.endsWith("DCB"); }
    
    
    public boolean isSPOT() { return supplyRate == null ? false : supplyRate.endsWith("SPO"); }
    
    public String getSupplyRateSuffix() { return supplyRate == null || supplyRate.indexOf("_") == -1 ? "" :supplyRate.substring(supplyRate.indexOf("_") + 1); }
    
    public String toString() {
    	StringBuffer sb = new StringBuffer();
    	sb.append(" contractId"+contractId);
    	sb.append(" supplyRate"+supplyRate);
    	sb.append(" distributionRate"+distributionRate);
    	return sb.toString();
    }
    
}