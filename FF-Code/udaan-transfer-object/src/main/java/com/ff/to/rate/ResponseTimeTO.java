/**
 * 
 */
package com.ff.to.rate;

/**
 * @author prmeher
 *
 */
@Deprecated
public class ResponseTimeTO {

	private String eventName;
	private long timeTaken;
	
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public long getTimeTaken() {
		return timeTaken;
	}
	public void setTimeTaken(long timeTaken) {
		this.timeTaken = timeTaken;
	}
}
