package org.energyos.espi.common.repositories;

import java.util.List;

import org.energyos.espi.common.domain.NotificationQueue;



public interface NotificationQueueRepository {
	 NotificationQueue findById(Long id);
	 List<NotificationQueue> findByApplicationInformationId(Long applicationInformationId);
	 List<NotificationQueue> findAll();
	 List<NotificationQueue> findByStatus(int status);
	 List<NotificationQueue> findByStatusAndApplicationId(int status,Long applicationId);
	 void createNotificationQueue(NotificationQueue NotificationQueue);
	 void mergeNotificationQueue(NotificationQueue notificationQueue);
	 void updateNotificationQueueStatusByAppInfoId(List<NotificationQueue> notificationList);
	 void updateByApplicationId(Long applicationInformationId);
}
