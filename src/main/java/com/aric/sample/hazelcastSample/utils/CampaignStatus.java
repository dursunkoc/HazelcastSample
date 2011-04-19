/**
 * 
 */
package com.aric.sample.hazelcastSample.utils;

/**
 * @author Dursun KOC
 * 
 */
public enum CampaignStatus {
	INITIAL("initial"), INPROGRESS("inprogress"), ERROR("error"), COMPLETED(
			"completed"), INVALID("invalid");

	private String name;

	/**
	 * @param name
	 */
	private CampaignStatus(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
