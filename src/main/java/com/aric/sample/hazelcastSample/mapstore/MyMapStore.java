/**
 * 
 */
package com.aric.sample.hazelcastSample.mapstore;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.aric.sample.hazelcastSample.spring.SpringContext;
import com.aric.sample.hazelcastSample.spring.model.CampaignParticipation;
import com.aric.sample.hazelcastSample.spring.repository.CampaignRepository;
import com.hazelcast.core.MapStore;

/**
 * @author Dursun KOC
 * 
 */
public class MyMapStore implements MapStore<Long, Integer> {
	private Logger logger = Logger.getLogger(this.getClass());
	private CampaignRepository repository = SpringContext
			.getCampaignParticipationRepository();

	public MyMapStore() {
		logger.debug("MyMapStore is initialized.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hazelcast.core.MapLoader#load(java.lang.Object)
	 */
	@Override
	public Integer load(Long campaignId) {
		final CampaignParticipation readCampaignParticipation = repository
				.readCampaignParticipation(campaignId);
		if (readCampaignParticipation == null) {
			return null;
		}
		final Integer count = readCampaignParticipation.getCount();
		logger.debug("Load campaignId:" + campaignId + " with count:" + count);
		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hazelcast.core.MapLoader#loadAll(java.util.Collection)
	 */
	@Override
	public Map<Long, Integer> loadAll(Collection<Long> campaignIds) {
		Map<Long, Integer> campaignIdCountMap = new HashMap<Long, Integer>();
		for (Long campaignId : campaignIds) {
			final CampaignParticipation readCampaignParticipation = repository
					.readCampaignParticipation(campaignId);
			final Integer count = readCampaignParticipation.getCount();
			campaignIdCountMap.put(campaignId, count);
		}
		logger.debug("Load all => " + campaignIdCountMap);
		return campaignIdCountMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hazelcast.core.MapStore#delete(java.lang.Object)
	 */
	@Override
	public void delete(Long campaignId) {
		throw new RuntimeException("Cannot delete a campaign-count record!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hazelcast.core.MapStore#deleteAll(java.util.Collection)
	 */
	@Override
	public void deleteAll(Collection<Long> campaignIds) {
		throw new RuntimeException("Cannot delete campaign-count records!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hazelcast.core.MapStore#store(java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public void store(Long campaignId, Integer count) {
		CampaignParticipation participation = new CampaignParticipation();
		participation.setCampaignId(campaignId);
		participation.setCount(count);
		logger.debug("Saving campaignId:" + campaignId + " with count:" + count);
		try {
			repository.saveCampaignParticipation(participation);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("Failed to save campaignId:" + campaignId
					+ " with count:" + count);
		}
		logger.debug("Saved campaignId:" + campaignId + " with count:" + count);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hazelcast.core.MapStore#storeAll(java.util.Map)
	 */
	@Override
	public void storeAll(Map<Long, Integer> campaignIdCountMap) {
		final Set<Long> keySet = campaignIdCountMap.keySet();
		for (Long campaignId : keySet) {
			final Integer count = campaignIdCountMap.get(campaignId);
			store(campaignId, count);
		}
	}

}
