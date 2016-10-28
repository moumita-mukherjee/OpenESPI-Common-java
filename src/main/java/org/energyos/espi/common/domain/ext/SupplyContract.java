package org.energyos.espi.common.domain.ext;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "supply_contracts")
@NamedQueries(value = { @NamedQuery(name = SupplyContract.QUERY_FIND_BY_SERVICE, query = "SELECT sc FROM SupplyContract sc where sc.customerId =:customerId and sc.selfHref = :selfHref") })
public class SupplyContract {

	public static final String QUERY_FIND_BY_SERVICE = "SupplyContract.findByService";
	
    @Id
    @Column(name = "id", insertable = false, updatable = false)
    private String contractId;
    
    
    @Column(name = "customer_id", insertable = false, updatable = false)
    private String customerId;
    


	@Column(name = "self_href", insertable = false, updatable = false)
    private String selfHref;
	
	@Column(name = "eff_date", insertable = false, updatable = false)
	private Date effDate;

	@Column(name = "end_date", insertable = false, updatable = false)
	private Date endDate;

	@Column(name = "supplier_id", insertable = false, updatable = false)
	private String name;

	public Date getEffDate() {
		return effDate;
	}

	public void setEffDate(Date effDate) {
		this.effDate = effDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getSelfHref() {
		return selfHref;
	}

	public void setSelfHref(String selfHref) {
		this.selfHref = selfHref;
	}
	public boolean isLhSupply () {
		return "LH_SUPPLY".equals(name);
	}
    public String toString() {
    	StringBuffer sb = new StringBuffer();
    	sb.append(" contractId"+contractId);
    	sb.append(" name"+name);
    	sb.append(" selfHref"+selfHref);
    	sb.append(" effDate"+effDate);
    	sb.append(" endDate"+endDate);
    	return sb.toString();
    }


}