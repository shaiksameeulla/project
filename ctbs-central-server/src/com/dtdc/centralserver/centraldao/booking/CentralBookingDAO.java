/*
 * @author soagarwa
 */
package com.dtdc.centralserver.centraldao.booking;

import com.capgemini.lbs.framework.exception.CGSystemException;

// TODO: Auto-generated Javadoc
/**
 * The Interface CentralBookingDAO.
 */
public interface CentralBookingDAO {

	/**
	 * Checks if is cn already booked.
	 *
	 * @param consignmentNumber the consignment number
	 * @return true, if is cn already booked
	 * @throws CGSystemException the cG system exception
	 */
	boolean isCnAlreadyBooked(String consignmentNumber)
			throws CGSystemException;

	/**
	 * Gets the c nstatus.
	 *
	 * @param ConNo the con no
	 * @return the c nstatus
	 * @throws CGSystemException the cG system exception
	 */
	public String getCNstatus(String ConNo) throws CGSystemException;
}
