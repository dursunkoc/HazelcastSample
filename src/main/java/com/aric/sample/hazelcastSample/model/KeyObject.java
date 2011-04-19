/**
 * 
 */
package com.aric.sample.hazelcastSample.model;

import java.io.Serializable;

/**
 * @author Dursun KOC
 * 
 */
public class KeyObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6190866332302857064L;

	private Long id;
	private transient String name;
	private transient String purpose;

	/**
	 * @param id
	 * @param name
	 * @param purpose
	 */
	public KeyObject(Long id, String name, String purpose) {
		this.id = id;
		this.name = name;
		this.purpose = purpose;
	}

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the purpose
	 */
	public String getPurpose() {
		return purpose;
	}

	/**
	 * @param purpose
	 *            the purpose to set
	 */
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	@Override
	public boolean equals(Object obj) {
		System.out.println("Calling equal method for KeyObject.");
		if (!(obj instanceof KeyObject)) {
			return false;
		}
		KeyObject ko = (KeyObject) obj;
		return this.id.equals(ko.id) && this.name.equals(ko.name)
				&& this.purpose.equals(ko.purpose);
	}

	@Override
	public int hashCode() {
		System.out.println("Calling hashCode method for KeyObject.");
		return super.hashCode();
	}
	
	@Override
	public String toString() {
		System.out.println("Calling hashCode method for KeyObject.");
		return super.toString();
	}

}
