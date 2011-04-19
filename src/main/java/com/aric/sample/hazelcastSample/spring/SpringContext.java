/**
 * 
 */
package com.aric.sample.hazelcastSample.spring;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aric.sample.hazelcastSample.spring.repository.CampaignRepository;
import com.aric.sample.hazelcastSample.spring.service.BulkCampaignService;
import com.aric.sample.hazelcastSample.spring.service.CampaignService;

/**
 * @author Dursun KOC
 * 
 */
public final class SpringContext {
	private static ApplicationContext context = new ClassPathXmlApplicationContext(
			"classpath:/META-INF/spring/app-context2.xml");

	/**
	 * @return
	 */
	public static BulkCampaignService getBulkCampaignService() {
		final BulkCampaignService bean = context.getBean(BulkCampaignService.class);
		return bean;
	}
	/**
	 * @return
	 */
	public static CampaignService getCampaignService() {
		final CampaignService bean = context.getBean(CampaignService.class);
		return bean;
	}

	/**
	 * @return
	 */
	public static CampaignRepository getCampaignParticipationRepository() {
		final CampaignRepository bean = context
				.getBean(CampaignRepository.class);
		return bean;
	}

	public static SessionFactory getSessionfactory() {
		final SessionFactory bean = context.getBean(SessionFactory.class);
		return bean;
	}
}
