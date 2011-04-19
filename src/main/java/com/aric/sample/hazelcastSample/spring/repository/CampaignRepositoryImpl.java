/**
 * 
 */
package com.aric.sample.hazelcastSample.spring.repository;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.aric.sample.hazelcastSample.spring.model.CampaignParticipation;
import com.aric.sample.hazelcastSample.spring.model.CampaignRequest;
import com.aric.sample.hazelcastSample.spring.model.ResponseHistory;

/**
 * @author Dursun KOC
 * 
 */
@Repository(value = "repository")
public class CampaignRepositoryImpl implements CampaignRepository {

	private HibernateTemplate ht;

	/**
	 * @param sessionFactory
	 */
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.ht = new HibernateTemplate(sessionFactory);
		ht.setCacheQueries(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aric.sample.hazelcastSample.repository.CampaignParticipationRepository
	 * #saveCampaignParticipation(com.aric.sample.hazelcastSample.model.
	 * CampaignParticipation)
	 */
	@Override
	public void saveCampaignParticipation(CampaignParticipation participation) {
		final CampaignParticipation readCampaignParticipation = this
				.readCampaignParticipation(participation.getCampaignId());
		if (readCampaignParticipation != null) {
			readCampaignParticipation.setCount(participation.getCount());
			ht.update(readCampaignParticipation);
		} else {
			ht.save(participation);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aric.sample.hazelcastSample.repository.CampaignParticipationRepository
	 * #readCampaignParticipation(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public CampaignParticipation readCampaignParticipation(Long campaignId) {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(CampaignParticipation.class);
		criteria.add(Property.forName("campaignId").eq(campaignId));
		List<CampaignParticipation> campaignParticipations = ht
				.findByCriteria(criteria);
		final String message = "Too Many CampaignParticipation found for campaignId='"
				+ campaignId + "'.";
		return selectOneRecord(campaignParticipations, message);
	}

	/**
	 * @param <T>
	 * @param records
	 * @param message
	 * @return
	 */
	private <T> T selectOneRecord(List<T> records, String message) {
		if (records == null || records.isEmpty()) {
			return null;
		} else if (records.size() == 1) {
			return records.get(0);
		} else {
			throw new RuntimeException(message);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aric.sample.hazelcastSample.spring.repository.CampaignRepository#
	 * saveCampaignRequest
	 * (com.aric.sample.hazelcastSample.spring.model.CampaignRequest)
	 */
	@Override
	public void saveCampaignRequest(CampaignRequest campaignRequest) {
		ht.save(campaignRequest);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aric.sample.hazelcastSample.spring.repository.CampaignRepository#
	 * readCampaignRequest(java.lang.Long)
	 */
	@Override
	public CampaignRequest readCampaignRequest(Long campaignRequestId) {
		final CampaignRequest load = ht.load(CampaignRequest.class,
				campaignRequestId);
		return load;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aric.sample.hazelcastSample.spring.repository.CampaignRepository#
	 * readCampaignRequests(java.lang.Long, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CampaignRequest> readCampaignRequests(Long campaignId,
			String status) {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(CampaignRequest.class);
		criteria.add(Property.forName("campaignId").eq(campaignId));
		criteria.add(Property.forName("status").eq(status));
		return ht.findByCriteria(criteria);
	}

	@Override
	public void saveResponseHistory(ResponseHistory responseHistory) {
		ht.save(responseHistory);
	}

}
