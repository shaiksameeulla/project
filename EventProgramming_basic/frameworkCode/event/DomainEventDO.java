package com.swacorp.cargo.event;

import java.io.Serializable;
import java.util.Map;

import org.springframework.data.annotation.Id;

import com.swacorp.cargo.domain.BaseEntityDO;

/**
 * The Class DomainEventDO.
 *
 * @author x213445
 */
public class DomainEventDO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3182653634036889991L;

	/** The header. */
	private DomainEventHeaderDO header;

	/** The payload. */
	private Map<String, BaseEntityDO> payload;

	/** The entity sequence number. */
	@Id
	private String entitySequenceNumber;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((entitySequenceNumber == null) ? 0 : entitySequenceNumber
						.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		DomainEventDO other = (DomainEventDO) obj;
		if (entitySequenceNumber == null) {
			if (other.entitySequenceNumber != null) {
				return false;
			}
		} else if (!entitySequenceNumber.equals(other.entitySequenceNumber)) {
			return false;
		}
		return true;
	}

	/**
	 * Gets the entity sequence number.
	 *
	 * @return the entitySequenceNumber
	 */
	public String getEntitySequenceNumber() {
		return entitySequenceNumber;
	}

	/**
	 * Sets the entity sequence number.
	 *
	 * @param entitySequenceNumber
	 *            the entitySequenceNumber to set
	 */
	public void setEntitySequenceNumber(String entitySequenceNumber) {
		this.entitySequenceNumber = entitySequenceNumber;
	}

	/**
	 * Gets the payload.
	 *
	 * @return the payload
	 */
	public Map<String, BaseEntityDO> getPayload() {
		return payload;
	}

	/**
	 * Sets the payload.
	 *
	 * @param payload
	 *            the payload to set
	 */
	public void setPayload(Map<String, BaseEntityDO> payload) {
		this.payload = payload;
	}

	/**
	 * Gets the header.
	 *
	 * @return the header
	 */
	public DomainEventHeaderDO getHeader() {
		return header;
	}

	/**
	 * Sets the header.
	 *
	 * @param header
	 *            the header to set
	 */
	public void setHeader(DomainEventHeaderDO header) {
		this.header = header;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DomainEventDO [header=" + header + ", payload=" + payload
				+ ", entitySequenceNumber=" + entitySequenceNumber + "]";
	}

}
