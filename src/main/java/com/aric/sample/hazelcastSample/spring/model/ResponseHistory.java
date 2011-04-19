/**
 * 
 */
package com.aric.sample.hazelcastSample.spring.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.aric.sample.hazelcastSample.utils.CampaignResponse;

/**
 * @author Dursun KOC
 * 
 */
@Entity
@Table
public class ResponseHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1958467902067438261L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id;
	@Column(nullable = false)
	private Long campaignId;
	@Column(nullable = false)
	private Long campaignRequestId;
	@Column(nullable = false)
	private String msisdn;
	@Column(nullable = false)
	private Date date;
	@Column(nullable = false)
	private String responseType;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the campaignId
	 */
	public Long getCampaignId() {
		return campaignId;
	}

	/**
	 * @param campaignId
	 *            the campaignId to set
	 */
	public void setCampaignId(Long campaignId) {
		this.campaignId = campaignId;
	}

	/**
	 * @return the campaignRequestId
	 */
	public Long getCampaignRequestId() {
		return campaignRequestId;
	}

	/**
	 * @param campaignRequestId
	 *            the campaignRequestId to set
	 */
	public void setCampaignRequestId(Long campaignRequestId) {
		this.campaignRequestId = campaignRequestId;
	}

	/**
	 * @return the msisdn
	 */
	public String getMsisdn() {
		return msisdn;
	}

	/**
	 * @param msisdn
	 *            the msisdn to set
	 */
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the responseType
	 */
	public String getResponseType() {
		return responseType;
	}

	/**
	 * @param responseType
	 *            the responseType to set
	 */
	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	/**
	 * @param responseType
	 *            the responseType to set
	 */
	public void setResponseType(CampaignResponse responseType) {
		this.responseType = responseType.toString();
	}

}
