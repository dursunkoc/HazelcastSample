/**
 * 
 */
package com.aric.sample.experience;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Dursun KOC
 * 
 */
public class CampaignCounter implements Map<Long, AtomicInteger> {
	private Map<Long, AtomicInteger> campaignCounter = new ConcurrentHashMap<Long, AtomicInteger>();
	
	@Override
	public int size() {
		return campaignCounter.size();
	}

	@Override
	public boolean isEmpty() {
		return campaignCounter.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return campaignCounter.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return campaignCounter.containsValue(value);
	}

	@Override
	public AtomicInteger get(Object key) {
		return campaignCounter.get(key);
	}

	@Override
	public AtomicInteger put(Long key, AtomicInteger value) {
		return campaignCounter.put(key, value);
	}

	@Override
	public AtomicInteger remove(Object key) {
		return campaignCounter.remove(key);
	}

	@Override
	public void putAll(Map<? extends Long, ? extends AtomicInteger> m) {
		campaignCounter.putAll(m);
	}

	@Override
	public void clear() {
		campaignCounter.clear();
	}

	@Override
	public Set<Long> keySet() {
		return campaignCounter.keySet();
	}

	@Override
	public Collection<AtomicInteger> values() {
		return campaignCounter.values();
	}

	@Override
	public Set<java.util.Map.Entry<Long, AtomicInteger>> entrySet() {
		return campaignCounter.entrySet();
	}

}
