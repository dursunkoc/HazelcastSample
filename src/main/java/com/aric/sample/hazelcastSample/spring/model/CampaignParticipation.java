/**
 * 
 */
package com.aric.sample.hazelcastSample.spring.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author Dursun KOC
 * 
 */
@Entity
@Table
@Cache(usage=CacheConcurrencyStrategy.TRANSACTIONAL)
public class CampaignParticipation implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2267375171326127111L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id;
	@Column(unique = true)
	private Long campaignId;
	@Column
	private Integer count;

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
	 * @return the count
	 */
	public Integer getCount() {
		return count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * @param i
	 */
	public void increaseCount(int i) {
		this.count = this.count + i;
	}
	
	

}
