/*
 * Copyright (c) 2014 Southwest Airlines, Co. 2702 Love Field Drive, Dallas, TX
 * 75235, U.S.A. All rights reserved.
 * 
 * This software is the confidential and proprietary information of Southwest
 * Airlines, Co.
 *
 */
package com.swacorp.cargo.event;

import java.util.HashMap;

import com.swacorp.cargo.domain.BaseEntityDO;
import com.swacorp.cargo.event.DomainEventDO;

/**
 * The Interface IEventGenerator.
 */
public interface IEventGenerator {

	/**
	 * Generates Domain Event with Application specific attributes in Domain
	 * Event Header passed from BS Layer.
	 *
	 * @param BaseEntity
	 *            the base entity
	 * @return the domain event
	 */
	public DomainEventDO generateEvent(BaseEntityDO baseEntity,
			DomainEventHeaderDO domainEventHeader);

	/**
	 * Generates Domain Event for entity DO changes having old and updated
	 * entity Dos in the payload.
	 *
	 * @param enitityMap
	 *            the enitity map
	 * @param domainEventHeader
	 *            the domain event header
	 * @param businessKey
	 *            the business key
	 * @return the domain event do
	 */
	public DomainEventDO generateEvent(
			HashMap<String, BaseEntityDO> enitityMap,
			DomainEventHeaderDO domainEventHeader, Object businessKey);

}
