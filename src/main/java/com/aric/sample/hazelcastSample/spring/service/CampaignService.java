/**
 * 
 */
package com.aric.sample.hazelcastSample.spring.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aric.sample.hazelcastSample.exception.CampaignLimitExceededException;
import com.aric.sample.hazelcastSample.exception.WriteResponseException;
import com.aric.sample.hazelcastSample.spring.model.WriteResponseParameter;

/**
 * @author Dursun KOC
 * 
 */
public interface CampaignService {
	/**
	 * @param writeResponseParameter
	 *            TODO
	 * @throws WriteResponseException
	 */
	@Transactional(readOnly = false, propagation = Propagation.NESTED, noRollbackFor = CampaignLimitExceededException.class, rollbackFor = WriteResponseException.class)
	public void writeResponse(WriteResponseParameter writeResponseParameter)
			throws WriteResponseException;

}
