package org.energyos.espi.common.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class LifecycleDate {

	@Column(name = "lifecycle_installation_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date installationDate;

	@Column(name = "lifecycle_manufactured_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date manufacturedDate;

	@Column(name = "lifecycle_purchase_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date purchaseDate;

	@Column(name = "lifecycle_received_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date receivedDate;

	@Column(name = "lifecycle_removal_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date removalDate;

	@Column(name = "lifecycle_retire_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date retiredDate;

	public Date getInstallationDate() {
		return installationDate;
	}

	public void setInstallationDate(Date installationDate) {
		this.installationDate = installationDate;
	}

	public Date getManufacturedDate() {
		return manufacturedDate;
	}

	public void setManufacturedDate(Date manufacturedDate) {
		this.manufacturedDate = manufacturedDate;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public Date getRemovalDate() {
		return removalDate;
	}

	public void setRemovalDate(Date removalDate) {
		this.removalDate = removalDate;
	}

	public Date getRetiredDate() {
		return retiredDate;
	}

	public void setRetiredDate(Date retiredDate) {
		this.retiredDate = retiredDate;
	}

}