package org.energyos.espi.common.repositories.jpa;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.IdentifiedObject;
import org.energyos.espi.common.repositories.AuthorizationRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional (rollbackFor= {javax.xml.bind.JAXBException.class}, 
                noRollbackFor = {javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })

public class AuthorizationRepositoryImpl implements AuthorizationRepository {

	@PersistenceContext(unitName = "pu-energy")
	protected EntityManager em;

	@Override
    @Transactional (rollbackFor= {javax.xml.bind.JAXBException.class}, 
                noRollbackFor = {javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })

    public void persist(Authorization authorization) {
        em.persist(authorization);
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<IdentifiedObject> findAllIdsByApplicationInformationId(Long applicationInformationId) {
      	return em.createNamedQuery(Authorization.QUERY_FIND_ALL_IDS_BY_APPLICATION_INFORMATION_ID)
      			.setParameter("applicationInformationId", applicationInformationId)
      			.getResultList();
    } 
	@SuppressWarnings("unchecked")
	@Override
    public List<Authorization> findAllByRetailCustomerId(Long retailCustomerId) {
      return em.createNamedQuery(Authorization.QUERY_FIND_BY_RETAIL_CUSTOMER_ID)
              .setParameter("retailCustomerId", retailCustomerId).getResultList();
    }   


	@Override
	public Authorization findByState(String state) {
      return (Authorization)em.createNamedQuery(Authorization.QUERY_FIND_BY_STATE)
              .setParameter("state", state).getSingleResult();
	}

	@Override
	public Authorization findByScope(String scope, Long retailCustomerId) {
		if(retailCustomerId==null) {
				return (Authorization) em.createNamedQuery(Authorization.QUERY_FIND_BY_SCOPE2).setParameter("scope", scope).getSingleResult();			
		}else {
			return (Authorization) em.createNamedQuery(Authorization.QUERY_FIND_BY_SCOPE).setParameter("scope", scope)
					.setParameter("retailCustomerId", retailCustomerId).getSingleResult();
		}
    }

    @Override
    @Transactional (rollbackFor= {javax.xml.bind.JAXBException.class}, 
                noRollbackFor = {javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })

    public void merge(Authorization authorization) {
        em.merge(authorization);
    }

	@Override
	public Authorization findById(Long authorizationId) {
        return (Authorization)em.createNamedQuery(Authorization.QUERY_FIND_BY_ID)
                .setParameter("id", authorizationId).getSingleResult();
	}

	@Override
	public List<IdentifiedObject> findAllIds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IdentifiedObject> findAllIds(Long retailCustomerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Authorization findByUUID(UUID uuid) {
		return (Authorization) em.createNamedQuery(Authorization.QUERY_FIND_BY_UUID)
                .setParameter("uuid", uuid.toString().toUpperCase())
                .getSingleResult();
	}

	@Override
    @Transactional (rollbackFor= {javax.xml.bind.JAXBException.class}, 
                noRollbackFor = {javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })

	public void deleteById(Long id) {
		em.remove(findById(id));
	}

	@Override
	public void createOrReplaceByUUID(Authorization authorization) {
		// TODO Auto-generated method stub

	}

	@Override
	public Authorization findByAccessToken(String accessToken) {
		return (Authorization) em.createNamedQuery(Authorization.QUERY_FIND_BY_ACCESS_TOKEN)
				.setParameter("accessToken", accessToken)
				.getSingleResult();
	}

	@Override
	public Authorization findByRefreshToken(String refreshToken) {
		return (Authorization) em.createNamedQuery(Authorization.QUERY_FIND_BY_REFRESH_TOKEN)
				.setParameter("refreshToken", refreshToken)
				.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> findAllIdsByBulkId(String thirdParty, Long bulkId) {
		return em.createNamedQuery(Authorization.QUERY_FIND_ALL_IDS_BY_BULK_ID)
				.setParameter("thirdParty", thirdParty)
				.setParameter("bulkId", "%BR=" + bulkId + "%")
				.getResultList();
	}
	/* LH customization starts here */
	@SuppressWarnings("unchecked")
	@Override
	public Authorization findByApplicationInformationId(Long applicationInformationId,String scope){
		return (Authorization)em.createNamedQuery(Authorization.QUERY_FIND_BY_APPLICATION_INFORMATION_ID)
				.setParameter("applicationInformationId", applicationInformationId).setParameter("scope", scope).setFirstResult(0).setMaxResults(1).getSingleResult();		
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Authorization> findAllActiveByRetailCustomerId(Long retailCustomerId) {
		return em.createNamedQuery(Authorization.QUERY_FIND_ACTIVE_BY_RETAIL_CUSTOMER_ID)
				.setParameter("retailCustomerId", retailCustomerId).getResultList();
	}
		
	@Override
	@Transactional(rollbackFor = { javax.xml.bind.JAXBException.class }, noRollbackFor = {
			javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })
	public void delete(Authorization authorization) {
		em.remove(authorization);
	}
	
	@Override
	public Authorization findByScope(Long retailCustomerId,Long applicationInformationId, String scope ) {		
			return (Authorization) em.createNamedQuery(Authorization.QUERY_FIND_BY_SCOPE3).setParameter("scope", scope)
					.setParameter("retailCustomerId", retailCustomerId).setParameter("applicationInformationId", applicationInformationId).getSingleResult();		
	}
	@Override
	public List<Authorization> findByStatus(Long retailCustomerId,String thirdparty,String status) {
		return em.createNamedQuery(Authorization.QUERY_FIND_BY_STATUS)
				.setParameter("retailCustomerId", retailCustomerId)
				.setParameter("status", status)
				.setParameter("thirdParty", thirdparty)
				.getResultList();
	}

}
