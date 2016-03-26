/**
 * 
 */
package com.ff.web.drs.common.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.ff.domain.delivery.DeliveryDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.delivery.DeliveryNavigatorDO;
import com.ff.to.drs.AbstractDeliveryTO;

/**
 * @author mohammes
 *
 */
public interface DeliveryCommonDAO {

	/**
	 * Gets the max number from process.
	 *
	 * @param seqConfigTo the seq config to
	 * @param queryName the query name
	 * @return the max number from process
	 * @throws CGSystemException the cG system exception
	 */
	String getMaxNumberFromProcess(SequenceGeneratorConfigTO seqConfigTo,
			String queryName) throws CGSystemException;

	Boolean discardGeneratedDrs(DeliveryDO deliveryDO) throws CGSystemException;

	/**
	 * Gets the drs details by drs number.
	 *
	 * @param inputTo the input to
	 * @return the drs details by drs number
	 * @throws CGSystemException the cG system exception
	 */
	DeliveryDO getDrsDetailsByDrsNumber(AbstractDeliveryTO inputTo)
			throws CGSystemException;

	/**
	 * Gets the drs navigation details.
	 *
	 * @param drsNumber the drs number
	 * @param drsCode the drs code
	 * @return the drs navigation details
	 * @throws CGSystemException the cG system exception
	 */
	DeliveryNavigatorDO getDrsNavigationDetails(String drsNumber, String drsCode)
			throws CGSystemException;

	/**
	 * Gets the drs navigation details.
	 *
	 * @param drsNumber the drs number
	 * @param drsCode the drs code
	 * @return the drs navigation details
	 * @throws CGSystemException the cG system exception
	 */
	List<DeliveryNavigatorDO> getDrsNavigationDetails(List<String> drsNumber,
			String drsCode) throws CGSystemException;

	/**
	 * Modify drs.Modifies only DRS-FOR & its code if and only if it's in open status
	 *
	 * @param deliveryDO the delivery do
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean modifyDrs(DeliveryDO deliveryDO) throws CGSystemException;

	/**
	 * Save prepare drs.
	 *
	 * @param deliveryDO the delivery do
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean savePrepareDrs(DeliveryDO deliveryDO) throws CGSystemException;

	/**
	 * Gets the drs details by consg and drs number.
	 *
	 * @param inputTo the input to
	 * @return the drs details by consg and drs number
	 * @throws CGSystemException the cG system exception
	 */
	DeliveryDetailsDO getDrsDetailsByConsgAndDrsNumber(
			AbstractDeliveryTO inputTo) throws CGSystemException;

	/**
	 * Gets the drs details by drs number For Print.
	 *
	 * @param drsNumber the drs number
	 * @return the drs details by drs number
	 * @throws CGSystemException the cG system exception
	 */
	DeliveryDO getDrsDetailsByDrsNumber(String drsNumber)
			throws CGSystemException;

	/**
	 * Gets the drs number status.
	 *
	 * @param drsHeaderTO the drs header to
	 * @return the drs number status
	 * @throws CGSystemException the cG system exception
	 */
	String getDrsStatusByDrsNumber(AbstractDeliveryTO drsHeaderTO)
			throws CGSystemException;

	/**
	 * Checks if is payment captured for cn.
	 *
	 * @param consgId the consg id
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean isPaymentCapturedForCn(Integer consgId) throws CGSystemException;

	 String getOutManifestedConsignmentNumberByOfficeForTPManifestForManualDrsForParentCn(
				AbstractDeliveryTO deliveryTO) throws CGSystemException;

		String getOutManifestedConsignmentNumberByOfficeForTPManifestForManualDrsForChildCn(
				AbstractDeliveryTO deliveryTO) throws CGSystemException;

		String checkifConsIsDelivered(String query, String consNO,
				String deliverystatus);

		Boolean updateManualDrs(DeliveryDO deliveryDO) throws CGSystemException;
	
}
