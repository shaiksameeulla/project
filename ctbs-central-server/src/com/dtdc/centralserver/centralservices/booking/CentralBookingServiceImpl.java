/*
 * @author soagarwa
 */
package com.dtdc.centralserver.centralservices.booking;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.dtdc.centralserver.centraldao.booking.CentralBookingDAO;
import com.dtdc.to.booking.BookingTO;
import com.dtdc.to.delivery.DeliveryManifestTO;

// TODO: Auto-generated Javadoc
/**
 * The Class CentralBookingServiceImpl.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CentralBookingServiceImpl implements CentralBookingService {

	/** The central booking dao. */
	private CentralBookingDAO centralBookingDAO;

	/* (non-Javadoc)
	 * @see com.dtdc.centralserver.centralservices.booking.CentralBookingService#validateConsignment(com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO)
	 */
	public CGBaseTO validateConsignment(CGBaseTO baseTO)
			throws CGSystemException {

		List bookingToList = baseTO.getBaseList();
		boolean isCNBooked = false;
		if (bookingToList != null) {
			BookingTO bookingTo = (BookingTO) bookingToList.get(0);
			isCNBooked = centralBookingDAO.isCnAlreadyBooked(bookingTo
					.getConsignmentNumber());
			bookingTo.setValidConsignment(isCNBooked);
			bookingToList.add(bookingTo);
			baseTO.setBaseList(bookingToList);
		}
		return baseTO;

	}
	
	/* (non-Javadoc)
	 * @see com.dtdc.centralserver.centralservices.booking.CentralBookingService#getCNstatus(CGBaseTO)
	 */
	public CGBaseTO getCNstatus(CGBaseTO baseTO) throws CGSystemException{
		List deliveryToList = baseTO.getBaseList();
		String status;
		if (deliveryToList != null) {
			DeliveryManifestTO deliveryManifestTO = (DeliveryManifestTO) deliveryToList.get(0);
			status = centralBookingDAO.getCNstatus(deliveryManifestTO.getConNum());
			deliveryManifestTO.setDeliveryStatus(status);
			deliveryToList.add(deliveryManifestTO);
			baseTO.setBaseList(deliveryToList);
		}
		return baseTO;
	}

	/**
	 * Gets the central booking dao.
	 *
	 * @return the centralBookingDAO
	 */
	public CentralBookingDAO getCentralBookingDAO() {
		return centralBookingDAO;
	}

	/**
	 * Sets the central booking dao.
	 *
	 * @param centralBookingDAO the centralBookingDAO to set
	 */
	public void setCentralBookingDAO(CentralBookingDAO centralBookingDAO) {
		this.centralBookingDAO = centralBookingDAO;
	}
}
