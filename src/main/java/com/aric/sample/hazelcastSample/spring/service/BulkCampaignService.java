/**
 * 
 */
package com.aric.sample.hazelcastSample.spring.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aric.sample.hazelcastSample.exception.WriteResponseException;
import com.aric.sample.hazelcastSample.spring.model.WriteResponseParameter;

/**
 * @author Dursun KOC
 * 
 */
public interface BulkCampaignService {
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = WriteResponseException.class)
	public void writeBulkResponse(
			List<WriteResponseParameter> writeResponseParameters)
			throws WriteResponseException;

}
