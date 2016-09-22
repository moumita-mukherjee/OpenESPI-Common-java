package org.energyos.espi.common.domain;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "notificationQueue")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "notificationQueue")
@Entity
@Cacheable(false)
@Table(name = "notification_queue")
@NamedQueries(value = {
		@NamedQuery(name = NotificationQueue.QUERY_FIND_ALL, query = "SELECT que FROM NotificationQueue que"),
		@NamedQuery(name = NotificationQueue.QUERY_FIND_BY_ID, query = "SELECT que FROM NotificationQueue que WHERE que.id = :id"),
		@NamedQuery(name = NotificationQueue.QUERY_FIND_BY_APPLICATION_INFORMATION_ID, query = "SELECT que FROM NotificationQueue que where que.applicationInformationId= :applicationInformationId"),
		@NamedQuery(name = NotificationQueue.QUERY_FIND_BY_STATUS, query = "SELECT que FROM NotificationQueue que where que.status= :status"),
		@NamedQuery(name = NotificationQueue.QUERY_FIND_BY_STATUS_AND_APPLICATION_INFORMATION_ID, query = "SELECT que FROM NotificationQueue que WHERE que.status = :status and que.applicationInformationId = :applicationInformationId"),
		@NamedQuery(name = NotificationQueue.QUERY_UPDATE_BY_APPLICATION_INFORMATION_ID, query = "UPDATE NotificationQueue que SET que.status = 1 WHERE que.status=0 and que.applicationInformationId = :applicationInformationId")

})
public class NotificationQueue {

	public static final String QUERY_FIND_ALL = "NotificationQueue.findAll";
	public static final String QUERY_FIND_BY_ID = "NotificationQueue.findById";
	public static final String QUERY_FIND_BY_APPLICATION_INFORMATION_ID = "NotificationQueue.findByApplicationInformationId";
	public static final String QUERY_FIND_BY_STATUS = "NotificationQueue.findByStatus";
	public static final String QUERY_FIND_BY_STATUS_AND_APPLICATION_INFORMATION_ID = "NotificationQueue.findByStatusAndApplicationId";
	public static final String 	QUERY_UPDATE_BY_APPLICATION_INFORMATION_ID= "NotificationQueue.updateByApplicationId";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	
	protected Long id;
	
	@Column(name = "application_information_id")
	protected Long applicationInformationId;
	
	@Column(name = "created_Time")
	protected Date createdTime;
	
	@Column(name = "updated_Time")
	protected Date updatedTime;
	
	@Column(name = "status")
	protected int status;
	
	@Column(name = "resource_uri")
	protected String resourceUri;

	

	public Long getApplicationInformationId() {
		return applicationInformationId;
	}
	
	public void setApplicationInformationId(Long applicationInformationId) {
		this.applicationInformationId = applicationInformationId;
	}

	public Date getCreatedTime() {
		return createdTime;
	}
	
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}
	
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}

	public String getResourceUri() {
		return resourceUri;
	}
	
	public void setResourceUri(String resourceUri) {
		this.resourceUri = resourceUri;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	

}
