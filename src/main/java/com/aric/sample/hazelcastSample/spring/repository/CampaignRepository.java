/**
 * 
 */
package com.aric.sample.hazelcastSample.spring.repository;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aric.sample.hazelcastSample.spring.model.CampaignParticipation;
import com.aric.sample.hazelcastSample.spring.model.CampaignRequest;
import com.aric.sample.hazelcastSample.spring.model.ResponseHistory;

/**
 * @author Dursun KOC
 * 
 */
public interface CampaignRepository {
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveCampaignParticipation(CampaignParticipation participation);

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public CampaignParticipation readCampaignParticipation(Long campaignId);

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveCampaignRequest(CampaignRequest campaignRequest);

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public CampaignRequest readCampaignRequest(Long campaignRequestId);

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<CampaignRequest> readCampaignRequests(Long campaignId,
			String status);

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveResponseHistory(ResponseHistory responseHistory);

}
