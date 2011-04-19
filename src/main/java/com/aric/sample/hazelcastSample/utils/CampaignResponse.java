/**
 * 
 */
package com.aric.sample.hazelcastSample.utils;

/**
 * @author Dursun KOC
 * 
 */
public enum CampaignResponse implements Comparable<CampaignResponse> {
	ACCEPTED("acc", CampaignStatus.INITIAL), 
	REJECTED("rjc", CampaignStatus.INVALID), 
	INPROGRESS("ipr",CampaignStatus.INPROGRESS), 
	SUCCESSFUL("suc",CampaignStatus.COMPLETED), 
	ERROR("err", CampaignStatus.ERROR);

	private String response;
	private CampaignStatus status;

	/**
	 * @param response
	 */
	private CampaignResponse(String response, CampaignStatus status) {
		this.response = response;
		this.status = status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return this.response;
	}
	
	/**
	 * @return
	 */
	public CampaignStatus getStatus() {
		return status;
	}
}
