package com.aric.sample.hazelcastSample;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.aric.sample.hazelcastSample.exception.WriteResponseException;
import com.aric.sample.hazelcastSample.model.KeyObject;
import com.aric.sample.hazelcastSample.spring.SpringContext;
import com.aric.sample.hazelcastSample.spring.model.CampaignParticipation;
import com.aric.sample.hazelcastSample.spring.model.WriteResponseParameter;
import com.aric.sample.hazelcastSample.spring.repository.CampaignRepository;
import com.aric.sample.hazelcastSample.spring.service.BulkCampaignService;
import com.aric.sample.hazelcastSample.spring.service.CampaignService;
import com.aric.sample.hazelcastSample.utils.CampaignResponse;
import com.hazelcast.core.DistributedTask;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.IMap;

/**
 * Hello world!
 * 
 */
public class App {
	public static final String TARGET_MAIN_META_INF_HAZELCAST_XML = "target/main/META-INF/hazelcast.xml";
	public static final String MAP_NAME = "myMap";
	public static final int CAMPAIGN_LIMIT = 10000;

	public static void main(String[] args) throws Exception {
		// System.setProperty("hazelcast.config",
		// TARGET_MAIN_META_INF_HAZELCAST_XML);
		main10();
	}

	public static void main11() throws Exception {
		final IMap<KeyObject, Long> map = Hazelcast.getMap("objectMap");
		Long id = 100L;
		String name = "Larry";
		String purpose = "Dominate the world";

		final KeyObject key1 = new KeyObject(id, name, purpose);
		final Long long1 = map.get(key1);
		System.out.println(long1);
	}

	public static void main10() throws Exception {
		final IMap<KeyObject, Long> map = Hazelcast.getMap("objectMap");
		Long id = 100L;
		String name = "Larry";
		String purpose = "Dominate the world";

		final KeyObject key1 = new KeyObject(id, name, purpose);
		System.out.println("Map Locked. @" + new Date());
		map.lock(key1);
		System.out.println("Put Ok. @" + new Date());
		final Long long1 = map.get(key1);
		map.put(key1, long1 + 10);
		Thread.sleep(20000);
		map.unlockMap();
		System.out.println("Map UnLocked. @" + new Date());
	}

	public static void main9() throws Exception {
		final IMap<KeyObject, Long> map = Hazelcast.getMap("objectMap");
		Long id = 100L;
		String name = "Larry";
		String purpose = "Dominate the world";

		final KeyObject key1 = new KeyObject(id, name, purpose);
		map.put(key1, 1000L);
		System.out.println("Put ok.");
	}

	public static void main8() throws Exception {
		final IMap<KeyObject, Long> map = Hazelcast.getMap("objectMap");
		Long id = null;
		String name = "Larry";
		String purpose = "Dominate the world";

		final KeyObject key1 = new KeyObject(id, name, purpose);
		map.put(key1, 1000L);

		final KeyObject key2 = new KeyObject(id, name, purpose);
		final Long long1 = map.get(key2);
		System.out.println(long1);

		KeyObject key3 = new KeyObject(id, name, purpose + ".");
		final Long long2 = map.get(key3);
		System.out.println(long2);

		key3 = new KeyObject(null, name.toLowerCase(), purpose);
		final Long long3 = map.get(key3);
		System.out.println(long3);

		KeyObject key4 = new KeyObject(1L, name.toLowerCase(), purpose);
		final Long long4 = map.get(key4);
		System.out.println(long4);
	}

	public static void main7() throws Exception {
		int port = Hazelcast.getCluster().getLocalMember()
				.getInetSocketAddress().getPort();
		String portlast = String.valueOf(port).substring(3);
		int rootMsisdn = Integer.parseInt(portlast);

		int threadCount = 1;
		Long campaignId = 4097L;
		int iterCount = 10000;
		Thread[] threads = new AnotherThread[threadCount];
		for (int i = 0; i < threads.length; i++) {
			String msisdn = "530" + rootMsisdn + "975600";
			threads[i] = new AnotherThread(campaignId, Long.parseLong(msisdn),
					iterCount);
		}
		for (int i = 0; i < threads.length; i++) {
			threads[i].start();
		}
	}

	private static class AnotherThread extends Thread {
		private final Long campaignId;
		private final long rootMsisdn;
		private final int iterCount;

		public AnotherThread(Long campaignId, Long rootMsisdn, int iterCount) {
			this.campaignId = campaignId;
			this.rootMsisdn = rootMsisdn;
			this.iterCount = iterCount;
		}

		@Override
		public void run() {
			try {
				final BulkCampaignService bulkCampaignService = SpringContext
						.getBulkCampaignService();
				List<WriteResponseParameter> parameters = new LinkedList<WriteResponseParameter>();
				for (long i = 0; i < iterCount; i++) {
					parameters.add(new WriteResponseParameter((rootMsisdn + i)
							+ "", campaignId, CampaignResponse.ACCEPTED, null));
				}
				bulkCampaignService.writeBulkResponse(parameters);
				System.out.println(SpringContext.getSessionfactory()
						.getStatistics());
			} catch (WriteResponseException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main6() throws Exception {
		final long campaignId = 4097L;
		final BulkCampaignService bulkCampaignService = SpringContext
				.getBulkCampaignService();
		List<WriteResponseParameter> parameters = new LinkedList<WriteResponseParameter>();
		for (long i = 0; i < 1000; i++) {
			parameters.add(new WriteResponseParameter((5304975600l + i) + "",
					campaignId, CampaignResponse.ACCEPTED, null));
		}
		bulkCampaignService.writeBulkResponse(parameters);
		System.out.println(SpringContext.getSessionfactory().getStatistics());
	}

	public static void main5() throws Exception {
		final CampaignService campaignService = SpringContext
				.getCampaignService();
		for (long i = 0; i < 100; i++) {
			Long requestId = null;
			final WriteResponseParameter writeResponseParameter = new WriteResponseParameter(
					(5304975600l + i) + "", 4097L, CampaignResponse.ACCEPTED,
					requestId);
			campaignService.writeResponse(writeResponseParameter);
		}
	}

	public static void main4() {
		final CampaignRepository camParRep = SpringContext
				.getCampaignParticipationRepository();

		final CampaignParticipation readCampaignParticipation = camParRep
				.readCampaignParticipation(1000L);
		incrAndSave(camParRep, readCampaignParticipation);

		final CampaignParticipation readCampaignParticipation2 = camParRep
				.readCampaignParticipation(1000L);
		incrAndSave(camParRep, readCampaignParticipation2);

		final CampaignParticipation readCampaignParticipation3 = camParRep
				.readCampaignParticipation(1000L);
		incrAndSave(camParRep, readCampaignParticipation3);

		System.out.println(readCampaignParticipation.getCount());
		System.out.println(readCampaignParticipation2.getCount());
		System.out.println(readCampaignParticipation3.getCount());
		System.out.println(readCampaignParticipation);
		System.out.println(readCampaignParticipation2);
		System.out.println(readCampaignParticipation3);
		if (readCampaignParticipation.equals(readCampaignParticipation2)) {
			System.out.println("They are equal!");
		}
		System.out.println(SpringContext.getSessionfactory().getStatistics());
	}

	private static void incrAndSave(final CampaignRepository camParRep,
			final CampaignParticipation readCampaignParticipation) {
		readCampaignParticipation
				.setCount(readCampaignParticipation.getCount() + 1);
		camParRep.saveCampaignParticipation(readCampaignParticipation);
	}

	public static void main3() {
		final int threadCount = 9;
		final int trial = 1000;
		final long campaignId = 1000L;
		final IMap<Long, Integer> map = Hazelcast.getMap("myMap");
		map.put(1000L, 1000);

		Thread[] threads = new Thread[threadCount];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new MyThread(trial, campaignId, "Thread " + (i + 1));
		}
		for (int i = 0; i < threads.length; i++) {
			threads[i].start();
		}
		System.out.println("Threads fired.");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static class MyThread extends Thread {
		private int trial;
		private Long campaignId;

		/**
		 * @param trial
		 * @param campaignId
		 */
		public MyThread(int trial, Long campaignId, String name) {
			super(name);
			this.trial = trial;
			this.campaignId = campaignId;
		}

		public void run() {
			System.out.println(Thread.currentThread().getName() + " started.");
			List<DistributedTask> myList = new ArrayList<DistributedTask>();
			for (int i = 0; i < trial; i++) {
				final DistributedTask command = new DistributedTask(
						new CampaignCounterIncrementTask(campaignId),
						campaignId);
				myList.add(command);
				Hazelcast.getExecutorService().execute(command);
			}
			// for (DistributedTask distributedTask : myList) {
			// try {distributedTask.get();} catch (Exception e) {}
			// }
			final IMap<Long, Integer> map = Hazelcast.getMap("myMap");
			Integer counter = map.get(campaignId);
			System.out.println(Thread.currentThread().getName()
					+ " completed, with " + counter);
		}
	}

	public static void main2() {
		final IMap<Long, Integer> map = Hazelcast.getMap("myMap");
		final Integer integer = map.get(1000L);
		System.out.println(integer);
	}

	public static void main1() {
		final IMap<Long, Integer> map = Hazelcast.getMap("myMap");
		final long campaignId = 1000L;
		final int initValue = 1000;
		final int incr = 100;
		map.put(campaignId, initValue);
		Thread[] threads = new Thread[] { new Thread() {
			public void run() {
				for (int i = 0; i < incr; i++) {
					map.put(campaignId, map.get(campaignId) + 1);
				}
				System.out
						.println("finished." + Thread.currentThread().getId());
			};
		}, new Thread() {
			public void run() {
				for (int i = 0; i < incr; i++) {
					map.put(campaignId, map.get(campaignId) + 1);
				}
				System.out
						.println("finished." + Thread.currentThread().getId());
			};
		}, new Thread() {
			public void run() {
				for (int i = 0; i < incr; i++) {
					map.put(campaignId, map.get(campaignId) + 1);
				}
				System.out
						.println("finished." + Thread.currentThread().getId());
			};
		}, new Thread() {
			public void run() {
				for (int i = 0; i < incr; i++) {
					map.put(campaignId, map.get(campaignId) + 1);
				}
				System.out
						.println("finished." + Thread.currentThread().getId());
			};
		}, new Thread() {
			public void run() {
				for (int i = 0; i < incr; i++) {
					map.put(campaignId, map.get(campaignId) + 1);
				}
				System.out
						.println("finished." + Thread.currentThread().getId());
			};
		}, new Thread() {
			public void run() {
				for (int i = 0; i < incr; i++) {
					map.put(campaignId, map.get(campaignId) + 1);
				}
				System.out
						.println("finished." + Thread.currentThread().getId());
			};
		}, new Thread() {
			public void run() {
				for (int i = 0; i < incr; i++) {
					map.put(campaignId, map.get(campaignId) + 1);
				}
				System.out
						.println("finished." + Thread.currentThread().getId());
			};
		}, };
		for (Thread thread : threads) {
			thread.start();
		}
		System.out.println("All threads fired.");
	}
}
