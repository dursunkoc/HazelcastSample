/**
 *
 */
package com.aric.sample.hazelcastSample.spring.model;

import com.aric.sample.hazelcastSample.utils.CampaignResponse;

/**
 * @author Dursun KOC
 * 
 */
public class WriteResponseParameter {
	private String msisdn;
	private Long campaignId;
	private CampaignResponse response;
	private Long requestId;

	public WriteResponseParameter(String msisdn, Long campaignId,
			CampaignResponse response, Long requestId) {
		this.msisdn = msisdn;
		this.campaignId = campaignId;
		this.response = response;
		this.requestId = requestId;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public Long getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(Long campaignId) {
		this.campaignId = campaignId;
	}

	public CampaignResponse getResponse() {
		return response;
	}

	public void setResponse(CampaignResponse response) {
		this.response = response;
	}

	public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}
}