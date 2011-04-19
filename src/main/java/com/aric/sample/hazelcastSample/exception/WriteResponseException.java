/**
 * 
 */
package com.aric.sample.hazelcastSample.exception;
/**
 * @author Dursun KOC
 * 
 */
public class WriteResponseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -60626033054963281L;

	/**
	 * 
	 */
	public WriteResponseException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public WriteResponseException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public WriteResponseException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public WriteResponseException(Throwable cause) {
		super(cause);
	}

}
