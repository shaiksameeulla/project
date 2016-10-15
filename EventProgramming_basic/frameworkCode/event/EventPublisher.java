/**
 * 
 */
package com.swacorp.cargo.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swacorp.cargo.domain.BaseEntityDO;
import com.swacorp.cargo.event.impl.repo.GemFireEventRepository;

/**
 * The Class EventPublisher. This class is responsible for publishing events in
 * to the SWAEventsLog Repository.
 *
 * @author Swagat
 */
@Component("eventPublisher")
public class EventPublisher {

	/** The gem fire event repository. */
	@Autowired
	private GemFireEventRepository gemFireEventRepository;

	/**
	 * This method is used to publish Events.
	 *
	 * @param doList
	 *            the do list
	 */
	public void publishEvent(List<? extends BaseEntityDO> doList,
			DomainEventGenerator generator, String eventSubType) {
		// UseCase specific generator will be passed from BS Layer
		ArrayList<DomainEventDO> eventList = new ArrayList<>();
		for (BaseEntityDO entityDO : doList) {
			DomainEventDO domainEvent = generator.generateEvent(entityDO,
					eventSubType);
			eventList.add(domainEvent);
		}
		gemFireEventRepository.save(eventList);
	}

	/**
	 * This method is used to publish Events.
	 *
	 * @param doList
	 *            the do list
	 */
	public void publishEvent(List<? extends BaseEntityDO> doList,
			DomainEventGenerator generator, List<String> eventSubTypes) {
		// UseCase specific generator will be passed from BS Layer
		ArrayList<DomainEventDO> eventList = new ArrayList<>();
		for (BaseEntityDO entityDO : doList) {
			DomainEventDO domainEvent = generator.generateEvent(entityDO,
					eventSubTypes);
			eventList.add(domainEvent);
		}
		gemFireEventRepository.save(eventList);
	}

	/**
	 * This method is used to publish Events.
	 *
	 * @param entityDO
	 *            the entity do
	 */
	public void publishEvent(BaseEntityDO entityDO,
			DomainEventGenerator generator, String eventSubType) {
		DomainEventDO domainEvent = generator.generateEvent(entityDO,
				eventSubType);
		if (domainEvent != null) {
			gemFireEventRepository.save(domainEvent);
		}
	}

	/**
	 * This method is used to publish Events.
	 *
	 * @param entityDO
	 *            the entity do
	 */
	public void publishEvent(BaseEntityDO entityDO,
			DomainEventGenerator generator, List<String> eventSubTypes) {
		DomainEventDO domainEvent = generator.generateEvent(entityDO,
				eventSubTypes);
		if (domainEvent != null) {
			gemFireEventRepository.save(domainEvent);
		}
	}

	/**
	 * This method is used to publish Events with a particular business key.
	 *
	 * @param entityDO
	 *            the entity do
	 * @param generator
	 *            the generator
	 * @param eventSubType
	 *            the event sub type
	 * @param businessKey
	 *            the business key
	 */
	public void publishEvent(BaseEntityDO entityDO,
			DomainEventGenerator generator, List<String> eventSubTypes,
			Object businessKey) {
		DomainEventDO domainEvent = generator.generateEvent(entityDO,
				eventSubTypes, businessKey);
		if (domainEvent != null) {
			gemFireEventRepository.save(domainEvent);
		}
	}

	/**
	 * This method is used to publish Events having the old and updated entity
	 * DO in the payload of the Domain Event.
	 *
	 * @param entityMap
	 *            the entity map
	 * @param generator
	 *            the generator
	 * @param eventSubTypes
	 *            the event sub types
	 * @param businessKey
	 *            the business key
	 */
	public void publishEvent(HashMap<String, BaseEntityDO> entityMap,
			DomainEventGenerator generator, List<String> eventSubTypes,
			Object businessKey) {
		DomainEventDO domainEvent = generator.generateEvent(entityMap,
				eventSubTypes, businessKey);
		if (domainEvent != null) {
			gemFireEventRepository.save(domainEvent);
		}
	}
}
