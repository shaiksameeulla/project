/**
 * 
 */
package com.swacorp.cargo.event;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.util.StringUtils;

import com.swacorp.cargo.common.util.CargoUtil;
import com.swacorp.cargo.domain.BaseEntityDO;

/**
 * The Class DomainEventGenerator. This Class is responsible for generating the
 * Domain Event DO based on the application Domain, type and sub types.
 *
 * @author Swagat
 */
public abstract class DomainEventGenerator implements IEventGenerator {

	/**
	 * Generates Domain Event without Application specific attributes in Domain
	 * Event Header.
	 *
	 * @param baseEntity
	 *            the base entity
	 * @param eventSubType
	 *            the event sub type
	 * @return the domain event do
	 */
	public DomainEventDO generateEvent(BaseEntityDO baseEntity,
			String eventSubType) {
		List<String> eventSubTypes = Arrays.asList(eventSubType);
		return generateEvent(baseEntity, eventSubTypes);
	}

	/**
	 * Generates Domain Event without Application specific attributes in Domain
	 * Event Header having a business key.
	 *
	 * @param baseEntity
	 *            the base entity
	 * @param eventSubType
	 *            the event sub type
	 * @param businessKey
	 *            the business key
	 * @return the domain event do
	 */
	public DomainEventDO generateEvent(BaseEntityDO baseEntity,
			String eventSubType, Object businessKey) {
		List<String> eventSubTypes = Arrays.asList(eventSubType);
		return generateEvent(baseEntity, eventSubTypes, businessKey);
	}

	/**
	 * Generates Domain Event without Application specific attributes in Domain
	 * Event Header.
	 *
	 * @param baseEntity
	 *            the base entity
	 * @param eventSubTypes
	 *            the event sub types
	 * @return the domain event do
	 */
	public DomainEventDO generateEvent(BaseEntityDO baseEntity,
			List<String> eventSubTypes) {
		DomainEventHeaderDO domainEventHeader = new DomainEventHeaderDO();
		domainEventHeader.setEventSubTypes(eventSubTypes);
		return generateEvent(baseEntity, domainEventHeader);
	}

	/**
	 * Generates Domain Event without Application specific attributes in Domain
	 * Event Header having a business key.
	 *
	 * @param baseEntity
	 *            the base entity
	 * @param eventSubTypes
	 *            the event sub types
	 * @param businessKey
	 *            the business key
	 * @return the domain event do
	 */
	public DomainEventDO generateEvent(BaseEntityDO baseEntity,
			List<String> eventSubTypes, Object businessKey) {
		DomainEventHeaderDO domainEventHeader = new DomainEventHeaderDO();
		domainEventHeader.setEventSubTypes(eventSubTypes);
		return generateEvent(baseEntity, domainEventHeader, businessKey);
	}

	/**
	 * Generates Domain Event with Application specific attributes in Domain
	 * Event Header passed from BS Layer.
	 *
	 * @param baseEntity
	 *            the base entity
	 * @param domainEventHeader
	 *            the domain event header
	 * @return the domain event do
	 */
	@Override
	public DomainEventDO generateEvent(BaseEntityDO baseEntity,
			DomainEventHeaderDO domainEventHeader) {
		// Set common attributes of domainEventHeader here

		String entitySequenceNumber = populateCommonHeader(domainEventHeader);
		// set the entityUpdateTimestamp from the payload
		domainEventHeader.setEntityUpdateTimeStamp(baseEntity
				.getLastUpdatedDate());

		/** create DomainEvent */
		DomainEventDO domainEvent = new DomainEventDO();
		domainEvent.setHeader(domainEventHeader);
		domainEvent.setPayload(new HashMap<String, BaseEntityDO>());
		domainEvent.getPayload().put(baseEntity.getClass().getName(),
				baseEntity);
		domainEvent.setEntitySequenceNumber(entitySequenceNumber);
		return domainEvent;
	}

	/**
	 * Generates Domain Event with Application specific attributes in Domain
	 * Event Header passed from BS Layer having a business Key.
	 *
	 * @param baseEntity
	 *            the base entity
	 * @param domainEventHeader
	 *            the domain event header
	 * @param businesskey
	 *            the businessKey
	 * @return the domain event do
	 */
	protected DomainEventDO generateEvent(BaseEntityDO baseEntity,
			DomainEventHeaderDO domainEventHeader, Object businessKey) {
		return generateEvent(baseEntity, domainEventHeader);
	}

	/**
	 * Generates Domain Event for entity DO changes having old and updated
	 * entity Dos in the payload.
	 *
	 * @param entityMap
	 *            the entity map
	 * @param eventSubTypes
	 *            the event sub types
	 * @param businessKey
	 *            the business key
	 * @return the domain event do
	 */
	public DomainEventDO generateEvent(HashMap<String, BaseEntityDO> entityMap,
			List<String> eventSubTypes, Object businessKey) {
		DomainEventHeaderDO domainEventHeader = new DomainEventHeaderDO();
		domainEventHeader.setEventSubTypes(eventSubTypes);
		return generateEvent(entityMap, domainEventHeader, businessKey);
	}

	/**
	 * Generates Domain Event for entity DO changes having old and updated
	 * entity Dos in the payload.
	 *
	 * @param entityMap
	 *            the entity map
	 * @param domainEventHeader
	 *            the domain event header
	 * @return the domain event do
	 */
	@Override
	public DomainEventDO generateEvent(HashMap<String, BaseEntityDO> entityMap,
			DomainEventHeaderDO domainEventHeader, Object businessKey) {
		// Set common attributes of domainEventHeader here

		String entitySequenceNumber = populateCommonHeader(domainEventHeader);
		// set the entityUpdateTimestamp from the payload
		domainEventHeader.setEntityUpdateTimeStamp(new Date());

		/** create DomainEvent */
		DomainEventDO domainEvent = new DomainEventDO();
		domainEvent.setHeader(domainEventHeader);
		domainEvent.setPayload(entityMap);
		domainEvent.setEntitySequenceNumber(entitySequenceNumber);
		return domainEvent;
	}

	/**
	 * Populate common header. This method populates the common attributes into
	 * the DomainEventHeader and returns the generated entitySequenceNumber.
	 *
	 * @param domainEventHeader
	 *            the domain event header
	 * @return the string
	 */
	private String populateCommonHeader(DomainEventHeaderDO domainEventHeader) {
		// since Events are immutable, set eventCreationTime at only one place
		// here
		if (domainEventHeader.getEventCreationTime() == null) {
			domainEventHeader.setEventCreationTime(new Date());
		}

		/**
		 * EventID in the format
		 * EventDomain.EventType.EventSubType.CreationDatTime.Identifier
		 */
		String entitySequenceNumber = CargoUtil.getUUIDValue();
		if (domainEventHeader.getEventID() == null
				&& StringUtils.isEmpty(domainEventHeader.getEventID())) {
			StringBuffer eventIDTemp = new StringBuffer();
			eventIDTemp.append(domainEventHeader.getEventDomain());
			eventIDTemp.append(".");
			eventIDTemp.append(domainEventHeader.getEventType());
			for (String eventSubType : domainEventHeader.getEventSubTypes()) {
				eventIDTemp.append(".");
				eventIDTemp.append(eventSubType);
			}
			eventIDTemp.append(".");
			eventIDTemp.append(Long.toString(domainEventHeader
					.getEventCreationTime().getTime()));
			eventIDTemp.append(".");
			eventIDTemp.append(entitySequenceNumber);
			String eventID = eventIDTemp.toString();
			domainEventHeader.setEventID(eventID);
		}
		return entitySequenceNumber;
	}

}
