/**
 * 
 */
package com.ff.web.drs.updation.dao;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.booking.BulkBookingVendorDtlsDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.delivery.DeliveryDO;


/**
 * @author mohammes
 *
 */
public interface RtoCodDrsDAO {

	/**
	 * Update delivered drs for dox/ppx.
	 *
	 * @param deliveryDo the delivery do
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Boolean updateDeliveredDrs(DeliveryDO deliveryDo)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the manifested consg details.
	 *
	 * @param consgNumber the consg number
	 * @param manifestStatus the manifest status
	 * @param originCityId the origin city id
	 * @return the manifested consg details
	 * @throws CGSystemException 
	 */
	ConsignmentDO getManifestedConsgDetails(String consgNumber,
			String manifestStatus, Integer originCityId) throws CGSystemException;

	/**
	 * Gets the manifested child consg details.
	 *
	 * @param consgNumber the consg number
	 * @param manifestStatus the manifest status
	 * @param originCityId the origin city id
	 * @return the manifested child consg details
	 * @throws CGSystemException 
	 */
	String getManifestedChildConsgDetails(String consgNumber,
			String manifestStatus, Integer originCityId) throws CGSystemException;

	/**
	 * Gets the customer dtls by consg num frm bkng.
	 *
	 * @param consgNumber the consg number
	 * @return the customer dtls by consg num frm bkng
	 * @throws CGSystemException the cG system exception
	 */
	CustomerDO getCustomerDtlsByConsgNumFrmBkng(String consgNumber)
			throws CGSystemException;

	/**
	 * Gets the venodr dtls by consg num frm bkng.
	 *
	 * @param consgNumber the consg number
	 * @return the venodr dtls by consg num frm bkng
	 * @throws CGSystemException the cG system exception
	 */
	BulkBookingVendorDtlsDO getVendorDtlsByConsgNumFrmBkng(String consgNumber)
			throws CGSystemException;

	
}
