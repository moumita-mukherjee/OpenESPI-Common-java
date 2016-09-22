package org.energyos.espi.common.repositories.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.energyos.espi.common.domain.NotificationQueue;
import org.energyos.espi.common.repositories.NotificationQueueRepository;
import org.springframework.transaction.annotation.Transactional;

public class NotificationQueueRepositoryImpl implements	NotificationQueueRepository {

	@PersistenceContext(unitName = "pu-energy")
	protected EntityManager em;

	@Override
	public List<NotificationQueue> findByApplicationInformationId(
			Long applicationInformationId) {
		return (List<NotificationQueue>) em
				.createNamedQuery(
						NotificationQueue.QUERY_FIND_BY_APPLICATION_INFORMATION_ID)
				.setParameter("applicationInformationId",
						applicationInformationId).getResultList();
	}

	@Override
	public List<NotificationQueue> findAll() {
		return (List<NotificationQueue>) em.createNamedQuery(
				NotificationQueue.QUERY_FIND_ALL).getResultList();

	}

	@Override
	public List<NotificationQueue> findByStatus(int status) {
		return (List<NotificationQueue>) em
				.createNamedQuery(
						NotificationQueue.QUERY_FIND_BY_APPLICATION_INFORMATION_ID)
				.setParameter("status", status).getResultList();
	}

	@Override
	public NotificationQueue findById(Long id) {
		return (NotificationQueue) em
				.createNamedQuery(NotificationQueue.QUERY_FIND_BY_ID)
				.setParameter("id", id).getSingleResult();
	}

	@Transactional
	@Override
	public void createNotificationQueue(NotificationQueue notificationQueue) {
		em.joinTransaction();
		em.persist(notificationQueue);
		em.flush();

	}

	@Transactional
	@Override
	public void mergeNotificationQueue(NotificationQueue notificationQueue) {
		NotificationQueue existingNotificationQueue = null;
		if (notificationQueue != null)
			existingNotificationQueue = findById(notificationQueue.getId());
		System.err.println(":::notification:::"
				+ notificationQueue.getResourceUri());
		if (existingNotificationQueue != null)
			updateExistingNotificationQueue(existingNotificationQueue,
					notificationQueue);
		em.joinTransaction();
		em.merge(existingNotificationQueue);
		em.flush();

	}

	private void updateExistingNotificationQueue(
			NotificationQueue existingNotificationQueue,
			NotificationQueue notificationQueue) {

		existingNotificationQueue.setStatus(notificationQueue.getStatus());
		existingNotificationQueue.setResourceUri(notificationQueue
				.getResourceUri());
	}

	@Override
	public void updateNotificationQueueStatusByAppInfoId(
			List<NotificationQueue> notifications) {
		for (NotificationQueue notify : notifications) {
			try {
				em.merge(notify);
				em.flush();
			} catch (Exception e) {

				System.err
						.println(" :::: update error is :::: " + e.getCause());
				System.err.println(" :::: update error is :::: "
						+ e.getMessage());
			}
		}

	}

	@Override
	public List<NotificationQueue> findByStatusAndApplicationId(int status,
			Long applicationInformationId) {
		List<NotificationQueue> returnList = new ArrayList<NotificationQueue>();

		System.err.println(" :::: Application ID :::: "
				+ applicationInformationId + " :::: Status :::: " + status);
		try {
			returnList = (List<NotificationQueue>) em
					.createNamedQuery(
							NotificationQueue.QUERY_FIND_BY_STATUS_AND_APPLICATION_INFORMATION_ID)
					.setParameter("status", status)
					.setParameter("applicationInformationId",
							applicationInformationId).getResultList();
		} catch (Exception ex) {
			System.err.println(" :::: query error :::: " + ex.getCause());
			System.err.println(" :::: query error :::: " + ex.getMessage());
		}

		return returnList;

	}

	@Override
	@Transactional
	public void updateByApplicationId(Long applicationInformationId) {

		System.err.println(" :::: Application ID :::: "
				+ applicationInformationId);
		try {
			em.joinTransaction();
			em.createNamedQuery(
					NotificationQueue.QUERY_UPDATE_BY_APPLICATION_INFORMATION_ID)
					.setParameter("applicationInformationId",
							applicationInformationId).executeUpdate();
		} catch (Exception ex) {
			System.err.println(" :::: query error :::: " + ex.getCause());
			System.err.println(" :::: query error :::: " + ex.getMessage());
		}

	}

}
