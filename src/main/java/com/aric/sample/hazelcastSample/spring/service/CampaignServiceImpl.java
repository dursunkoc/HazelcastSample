/**
 * 
 */
package com.aric.sample.hazelcastSample.spring.service;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aric.sample.hazelcastSample.App;
import com.aric.sample.hazelcastSample.exception.CampaignLimitExceededException;
import com.aric.sample.hazelcastSample.exception.WriteResponseException;
import com.aric.sample.hazelcastSample.spring.model.CampaignRequest;
import com.aric.sample.hazelcastSample.spring.model.ResponseHistory;
import com.aric.sample.hazelcastSample.spring.model.WriteResponseParameter;
import com.aric.sample.hazelcastSample.spring.repository.CampaignRepository;
import com.aric.sample.hazelcastSample.utils.CampaignResponse;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.IMap;

/**
 * @author Dursun KOC
 * 
 */
@Service(value = "service")
public class CampaignServiceImpl implements CampaignService {
	private CampaignRepository repository;
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * @param repository
	 */
	@Autowired
	public void setRepository(CampaignRepository repository) {
		this.repository = repository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aric.sample.hazelcastSample.spring.service.CampaignService#writeResponse
	 * (java.lang.String, java.lang.Long,
	 * com.aric.sample.hazelcastSample.spring.service.CampaignResponse,
	 * java.lang.Long)
	 */
	@Override
	public void writeResponse(WriteResponseParameter writeResponseParameter)
			throws WriteResponseException {
		final String msisdn = writeResponseParameter.getMsisdn();
		final CampaignResponse response = writeResponseParameter.getResponse();
		final Long campaignId = writeResponseParameter.getCampaignId();

		logger.debug("Writing Response for " + msisdn);
		checkResponse(response);

		if (isNewRequest(msisdn, campaignId,
				writeResponseParameter.getRequestId())) {
			createRequest(msisdn, campaignId, response);
		} else if (!isNewRequest(msisdn, campaignId,
				writeResponseParameter.getRequestId())) {
			reviseRequest(writeResponseParameter.getRequestId(), response);
		} else {
			throw new WriteResponseException("Inconsistent parameter set.");
		}
		logger.debug("Wrote Response for " + msisdn);
	}

	/**
	 * @param response
	 * @throws WriteResponseException
	 */
	private void checkResponse(CampaignResponse response)
			throws WriteResponseException {
		if (response == null) {
			throw new WriteResponseException("Response cannot be null!");
		}
	}

	/**
	 * @param msisdn
	 * @param campaignId
	 * @param requestId
	 * @return
	 */
	private boolean isNewRequest(String msisdn, Long campaignId, Long requestId) {
		return requestId == null && msisdn != null && campaignId != null;
	}

	/**
	 * @param requestId
	 * @param response
	 */
	private void reviseRequest(Long requestId, CampaignResponse response) {
		logger.debug("Revising Request for " + requestId);
		final CampaignRequest readCampaignRequest = repository
				.readCampaignRequest(requestId);

		readCampaignRequest.setStatus(response.getStatus());
		String msisdn = readCampaignRequest.getMsisdn();

		createResponse(msisdn, requestId, response, requestId);

		repository.saveCampaignRequest(readCampaignRequest);
		logger.debug("Revised Request for " + requestId);
	}

	/**
	 * @param msisdn
	 * @param campaignId
	 * @param response
	 * @param campaignRequestId
	 */
	private void createResponse(String msisdn, Long campaignId,
			CampaignResponse response, Long campaignRequestId) {
		logger.debug("Creating Response for " + msisdn);
		ResponseHistory responseHistory = new ResponseHistory();
		responseHistory.setCampaignId(campaignId);
		responseHistory.setCampaignRequestId(campaignRequestId);
		responseHistory.setDate(new Date());
		responseHistory.setMsisdn(msisdn);
		responseHistory.setResponseType(response);

		this.repository.saveResponseHistory(responseHistory);
		logger.debug("Created Response for " + msisdn);
	}

	/**
	 * @param msisdn
	 * @param campaignId
	 * @param response
	 * @return
	 * @throws WriteResponseException
	 */
	private Long createRequest(String msisdn, Long campaignId,
			CampaignResponse response) throws WriteResponseException {
		logger.debug("Creating Request for " + msisdn);

		final IMap<Long, Integer> map = Hazelcast.getMap(App.MAP_NAME);
		map.lock(campaignId);
		Integer currentCounter = map.get(campaignId);
		try {
			currentCounter = currentCounter == null ? 0 : currentCounter;
			logger.debug("currentCounter: "+currentCounter);

			if (currentCounter >= App.CAMPAIGN_LIMIT) {
				logger.debug("Limit exceeded!");
				throw new CampaignLimitExceededException("Limit exceeded.");
			}
			CampaignRequest campaignRequest = new CampaignRequest();
			campaignRequest.setCampaignId(campaignId);
			campaignRequest.setDate(new Date());
			campaignRequest.setMsisdn(msisdn);
			campaignRequest.setStatus(response.getStatus());

			map.put(campaignId, currentCounter + 1);

			this.repository.saveCampaignRequest(campaignRequest);
			final Long campaignRequestId = campaignRequest.getId();

			createResponse(msisdn, campaignId, response, campaignRequestId);

			logger.debug("Created Request for " + msisdn);
			return campaignRequestId;
		} catch (Exception e) {
			map.put(campaignId, currentCounter);
			throw new WriteResponseException(e);
		}finally {
			logger.debug("Unlocking... "+campaignId);
			map.unlock(campaignId);
		}
	}

}
