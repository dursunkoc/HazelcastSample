/**
 * 
 */
package com.aric.sample.hazelcastSample.spring.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aric.sample.hazelcastSample.exception.CampaignLimitExceededException;
import com.aric.sample.hazelcastSample.exception.WriteResponseException;
import com.aric.sample.hazelcastSample.spring.model.WriteResponseParameter;

/**
 * @author Dursun KOC
 *
 */
@Service(value="bulkService")
public class BulkCampaignServiceImpl implements BulkCampaignService {
	private Logger logger = Logger.getLogger(this.getClass());
	private CampaignService campaignService;
	
	@Autowired
	public void setCampaignService(CampaignService campaignService) {
		this.campaignService = campaignService;
	}

	/* (non-Javadoc)
	 * @see com.aric.sample.hazelcastSample.spring.service.BulkCampaignService#writeBulkResponse(java.util.List)
	 */
	@Override
	public void writeBulkResponse(
			List<WriteResponseParameter> writeResponseParameters) throws WriteResponseException {
		logger.debug("Bulk Response started.");
		for (WriteResponseParameter writeResponseParameter : writeResponseParameters) {
			try {
				campaignService.writeResponse(writeResponseParameter);
			} catch (CampaignLimitExceededException e) {
				logger.debug("Exception Occured....."+e.getMessage());
			}
		}
		logger.debug("Bulk Response ended.");		
	}

}
