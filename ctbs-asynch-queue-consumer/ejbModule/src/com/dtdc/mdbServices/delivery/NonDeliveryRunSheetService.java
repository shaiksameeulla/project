package src.com.dtdc.mdbServices.delivery;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.dtdc.to.delivery.NonDeliveryRunTO;

// TODO: Auto-generated Javadoc
/**
 * UC 069-70 Non Delivery Run Sheet
 * NonDeliveryRunSheetService - It is the service list of functions which helps 
 * to make call to proper DAO layer, for each functional request.
 * @author cjaganna
 *
 */
public interface NonDeliveryRunSheetService{
	
	/**
	 * Update ndrs.
	 *
	 * @param cgBaseTO the cg base to
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	void updateNDRS(CGBaseTO cgBaseTO)	throws CGSystemException, CGBusinessException;
	
	/**
	 * updateNDRS - Helps in updating the list of NDRS Consignment details
	 * given by the user for at the Branch level and Franchisee level in the NDRS.
	 *
	 * @param nondeliveryRunSheetTO the nondelivery run sheet to
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	void updateNDRS(NonDeliveryRunTO nondeliveryRunSheetTO)	throws CGSystemException, CGBusinessException;
	
	
	
}
