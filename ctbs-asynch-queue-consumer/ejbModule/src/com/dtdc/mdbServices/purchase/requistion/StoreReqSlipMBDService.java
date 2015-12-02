package src.com.dtdc.mdbServices.purchase.requistion;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface PurchaseRequisitionService.
 */
public interface StoreReqSlipMBDService{

	/**
	 * Save sto details.
	 *
	 * @param stoRequstTO the sto requst to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 */
	public  Boolean saveSTODetails(CGBaseTO stoRequstTO) throws CGBusinessException; 
	
	
}
