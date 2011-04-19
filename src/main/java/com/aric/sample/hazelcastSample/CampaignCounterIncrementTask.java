/**
 * 
 */
package com.aric.sample.hazelcastSample;

import java.io.Serializable;
import java.util.concurrent.Callable;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.IMap;

/**
 * @author Dursun KOC
 * 
 */
public class CampaignCounterIncrementTask implements Callable<Integer>,
		Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6627408527065976052L;
	private Long campaignId;

	/**
	 * @param campaignParticipation
	 */
	public CampaignCounterIncrementTask(Long campaignId) {
		this.campaignId = campaignId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public Integer call() throws Exception {
		final IMap<Long, Integer> map = Hazelcast.getMap("myMap");
		map.lock(campaignId);
		Integer count = map.get(campaignId) + 1;
		map.put(campaignId, count);
		map.unlock(campaignId);
		if (count % 1000 == 0) {
			String prt = Hazelcast.getCluster().getLocalMember() + "-"
					+ count;
			System.out.println(prt);
		}
		return count;
	}

}
