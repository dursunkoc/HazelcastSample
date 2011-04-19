/**
 * 
 */
package com.aric.sample.hazelcastSample.exception;

/**
 * @author Dursun KOC
 * 
 */
public class CampaignLimitExceededException extends WriteResponseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6517406364099065877L;

	/**
	 * @param message
	 */
	public CampaignLimitExceededException(String message) {
		super(message);
	}

}
