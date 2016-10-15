package com.swacorp.cargo.event;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * The Class DomainEventHeaderDO.
 */
public class DomainEventHeaderDO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The event domain. */
	private String eventDomain;

	/** The event type. */
	private String eventType;

	/** The event sub types. */
	private List<String> eventSubTypes;

	/** The event version. */
	private String eventVersion;

	/** The event id. */
	private String eventID;

	/** The event creation system. */
	private String eventCreationSystem;

	/** The event creation system instance id. */
	private Long eventCreationSystemInstanceId;

	/** The event creation host. */
	private String eventCreationHost;

	/** The event creating process id. */
	private Long eventCreatingProcessId;

	/** The event creation time. */
	private Date eventCreationTime;

	/** The event correlation id. */
	private Long eventCorrelationId;

	/** The entity update time stamp. */
	private Date entityUpdateTimeStamp;

	/** The entity keys. */
	private Map<String, Object> entityKeys;

	/** The entity attributes. */
	private List<StringAttributeDO> entityAttributes;

	/** The event attributes. */
	private List<StringAttributeDO> eventAttributes;

	/** The awb status changed to. */
	private String awbStatusChangedTo;

	/** The section number updated. */
	private List<String> sectionNumberUpdated;

	/** The req sections modified after man. */
	private boolean reqSectionsModifiedAfterMAN;

	/**
	 * Checks if is req sections modified after man.
	 *
	 * @return the reqSectionsModifiedAfterMAN
	 */
	public boolean isReqSectionsModifiedAfterMAN() {
		return reqSectionsModifiedAfterMAN;
	}

	/**
	 * Sets the req sections modified after man.
	 *
	 * @param reqSectionsModifiedAfterMAN
	 *            the reqSectionsModifiedAfterMAN to set
	 */
	public void setReqSectionsModifiedAfterMAN(
			boolean reqSectionsModifiedAfterMAN) {
		this.reqSectionsModifiedAfterMAN = reqSectionsModifiedAfterMAN;
	}

	/**
	 * Gets the event domain.
	 *
	 * @return the eventDomain
	 */
	public String getEventDomain() {
		return eventDomain;
	}

	/**
	 * Sets the event domain.
	 *
	 * @param eventDomain
	 *            the eventDomain to set
	 */
	public void setEventDomain(String eventDomain) {
		this.eventDomain = eventDomain;
	}

	/**
	 * Gets the event type.
	 *
	 * @return the eventType
	 */
	public String getEventType() {
		return eventType;
	}

	/**
	 * Sets the event type.
	 *
	 * @param eventType
	 *            the eventType to set
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	/**
	 * Gets the event sub types.
	 *
	 * @return the eventSubTypes
	 */
	public List<String> getEventSubTypes() {
		return eventSubTypes;
	}

	/**
	 * Sets the event sub types.
	 *
	 * @param eventSubTypes
	 *            the eventSubTypes to set
	 */
	public void setEventSubTypes(List<String> eventSubTypes) {
		this.eventSubTypes = eventSubTypes;
	}

	/**
	 * Gets the event version.
	 *
	 * @return the eventVersion
	 */
	public String getEventVersion() {
		return eventVersion;
	}

	/**
	 * Sets the event version.
	 *
	 * @param eventVersion
	 *            the eventVersion to set
	 */
	public void setEventVersion(String eventVersion) {
		this.eventVersion = eventVersion;
	}

	/**
	 * Gets the event id.
	 *
	 * @return the eventID
	 */
	public String getEventID() {
		return eventID;
	}

	/**
	 * Sets the event id.
	 *
	 * @param eventID
	 *            the eventID to set
	 */
	public void setEventID(String eventID) {
		this.eventID = eventID;
	}

	/**
	 * Gets the event creation system.
	 *
	 * @return the eventCreationSystem
	 */
	public String getEventCreationSystem() {
		return eventCreationSystem;
	}

	/**
	 * Sets the event creation system.
	 *
	 * @param eventCreationSystem
	 *            the eventCreationSystem to set
	 */
	public void setEventCreationSystem(String eventCreationSystem) {
		this.eventCreationSystem = eventCreationSystem;
	}

	/**
	 * Gets the event creation system instance id.
	 *
	 * @return the eventCreationSystemInstanceId
	 */
	public Long getEventCreationSystemInstanceId() {
		return eventCreationSystemInstanceId;
	}

	/**
	 * Sets the event creation system instance id.
	 *
	 * @param eventCreationSystemInstanceId
	 *            the eventCreationSystemInstanceId to set
	 */
	public void setEventCreationSystemInstanceId(
			Long eventCreationSystemInstanceId) {
		this.eventCreationSystemInstanceId = eventCreationSystemInstanceId;
	}

	/**
	 * Gets the event creation host.
	 *
	 * @return the eventCreationHost
	 */
	public String getEventCreationHost() {
		return eventCreationHost;
	}

	/**
	 * Sets the event creation host.
	 *
	 * @param eventCreationHost
	 *            the eventCreationHost to set
	 */
	public void setEventCreationHost(String eventCreationHost) {
		this.eventCreationHost = eventCreationHost;
	}

	/**
	 * Gets the event creating process id.
	 *
	 * @return the eventCreatingProcessId
	 */
	public Long getEventCreatingProcessId() {
		return eventCreatingProcessId;
	}

	/**
	 * Sets the event creating process id.
	 *
	 * @param eventCreatingProcessId
	 *            the eventCreatingProcessId to set
	 */
	public void setEventCreatingProcessId(Long eventCreatingProcessId) {
		this.eventCreatingProcessId = eventCreatingProcessId;
	}

	/**
	 * Gets the event creation time.
	 *
	 * @return the eventCreationTime
	 */
	public Date getEventCreationTime() {
		return eventCreationTime;
	}

	/**
	 * Sets the event creation time.
	 *
	 * @param eventCreationTime
	 *            the eventCreationTime to set
	 */
	public void setEventCreationTime(Date eventCreationTime) {
		this.eventCreationTime = eventCreationTime;
	}

	/**
	 * Gets the event correlation id.
	 *
	 * @return the eventCorrelationId
	 */
	public Long getEventCorrelationId() {
		return eventCorrelationId;
	}

	/**
	 * Sets the event correlation id.
	 *
	 * @param eventCorrelationId
	 *            the eventCorrelationId to set
	 */
	public void setEventCorrelationId(Long eventCorrelationId) {
		this.eventCorrelationId = eventCorrelationId;
	}

	/**
	 * Gets the entity update time stamp.
	 *
	 * @return the entityUpdateTimeStamp
	 */
	public Date getEntityUpdateTimeStamp() {
		return entityUpdateTimeStamp;
	}

	/**
	 * Sets the entity update time stamp.
	 *
	 * @param entityUpdateTimeStamp
	 *            the entityUpdateTimeStamp to set
	 */
	public void setEntityUpdateTimeStamp(Date entityUpdateTimeStamp) {
		this.entityUpdateTimeStamp = entityUpdateTimeStamp;
	}

	/**
	 * Gets the entity keys.
	 *
	 * @return the entityKeys
	 */
	public Map<String, Object> getEntityKeys() {
		return entityKeys;
	}

	/**
	 * Sets the entity keys.
	 *
	 * @param entityKeys
	 *            the entityKeys to set
	 */
	public void setEntityKeys(Map<String, Object> entityKeys) {
		this.entityKeys = entityKeys;
	}

	/**
	 * Gets the entity attributes.
	 *
	 * @return the entityAttributes
	 */
	public List<StringAttributeDO> getEntityAttributes() {
		return entityAttributes;
	}

	/**
	 * Sets the entity attributes.
	 *
	 * @param entityAttributes
	 *            the entityAttributes to set
	 */
	public void setEntityAttributes(List<StringAttributeDO> entityAttributes) {
		this.entityAttributes = entityAttributes;
	}

	/**
	 * Gets the event attributes.
	 *
	 * @return the eventAttributes
	 */
	public List<StringAttributeDO> getEventAttributes() {
		return eventAttributes;
	}

	/**
	 * Sets the event attributes.
	 *
	 * @param eventAttributes
	 *            the eventAttributes to set
	 */
	public void setEventAttributes(List<StringAttributeDO> eventAttributes) {
		this.eventAttributes = eventAttributes;
	}

	/**
	 * Gets the awb status changed to.
	 *
	 * @return the awbStatusChangedTo
	 */
	public String getAwbStatusChangedTo() {
		return awbStatusChangedTo;
	}

	/**
	 * Sets the awb status changed to.
	 *
	 * @param awbStatusChangedTo
	 *            the awbStatusChangedTo to set
	 */
	public void setAwbStatusChangedTo(String awbStatusChangedTo) {
		this.awbStatusChangedTo = awbStatusChangedTo;
	}

	/**
	 * Gets the section number updated.
	 *
	 * @return the sectionNumberUpdated
	 */
	public List<String> getSectionNumberUpdated() {
		return sectionNumberUpdated;
	}

	/**
	 * Sets the section number updated.
	 *
	 * @param sectionNumberUpdated
	 *            the sectionNumberUpdated to set
	 */
	public void setSectionNumberUpdated(List<String> sectionNumberUpdated) {
		this.sectionNumberUpdated = sectionNumberUpdated;
	}

}
