package src.com.dtdc.mdbServices.purchase.requistion;

import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface PurchaseRequisitionMBDService.
 */
public interface PurchaseRequisitionMBDService {
		
		/**
		 * Save requisition.
		 *
		 * @param purchaseTo the purchase to
		 * @return the boolean
		 * @throws Exception the exception
		 */
		public Boolean  saveRequisition(CGBaseTO purchaseTo) throws Exception;
		
		/**
		 * Save requisition.
		 *
		 * @param purchaseTo the purchase to
		 * @return the boolean
		 * @throws Exception the exception
		 */
		public  Boolean saveRequestFranchisee(CGBaseTO purchaseTo) throws Exception;
}
