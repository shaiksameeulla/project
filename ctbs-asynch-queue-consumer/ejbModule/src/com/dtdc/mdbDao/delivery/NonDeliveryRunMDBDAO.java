package src.com.dtdc.mdbDao.delivery;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.dtdc.domain.expense.MiscExpenseDO;
import com.dtdc.domain.master.ReasonDO;
import com.dtdc.domain.transaction.delivery.DeliveryDO;
import com.dtdc.domain.transaction.delivery.NonDeliveryRunDO;
import com.dtdc.to.delivery.NonDeliveryRunDisplyTO;
import com.dtdc.to.delivery.NonDeliveryRunTO;
import com.dtdc.to.franchisee.FranchiseeTO;

// TODO: Auto-generated Javadoc
/**
 * UC 069-70 Non Delivery Run Sheet
 * NonDeliveryRunDAO - responsible for making calls to appropriate DB methods 
 * as per the service requested.
 * @author cjaganna
 * 
 */
public interface NonDeliveryRunMDBDAO {

	/**
	 * getBranchName - Helps in getting the branch Name for the branch Code.
	 *
	 * @param brCode the br code
	 * @return the branch name
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	String getBranchName(String brCode) throws CGSystemException, CGBusinessException;
	
	/**
	 * getDeliveryConsignmentDetails -Gets the details for a particular.
	 *
	 * @param aDRSNumber the a drs number
	 * @param branchId the branch id
	 * @return the delivery do by drs no for branch
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException DeliveryDO getDeliveryConsignmentDetails(CGBaseTO to, String consignmentNo,String runSheetNumber)throws CGSystemException, CGBusinessException;
	 */
	/**
	 * 
	 * @param aConsgNumber
	 * @param runSheetNum
	 * @param headerFileName
	 * @return DeliveryDO
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 
	public List<DeliveryDO> getDeliveryDO(String aConsgNumber, String branchId, String drsNo, String headerFile, String fdmNo)throws CGSystemException, CGBusinessException;
*/
	/**
	 * 
	 * @param aDRSNumber
	 * @param officeId
	 * @param isBranch
	 * @return boolean
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	List<DeliveryDO> getDeliveryDOByDrsNoForBranch(String aDRSNumber,Integer branchId) throws CGSystemException, CGBusinessException;
	
	/**
	 * Gets the delivery do by drs no for franchisee.
	 *
	 * @param aDRSNumber the a drs number
	 * @param branchId the branch id
	 * @param franchiseeId the franchisee id
	 * @return boolean
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	List<DeliveryDO> getDeliveryDOByDRSNoForFranchisee(String aDRSNumber,Integer branchId, Integer franchiseeId) throws CGSystemException, CGBusinessException; 
	
	/**
	 * Inquire drs.
	 *
	 * @param nonDeliveryRunTO the non delivery run to
	 * @return List<NonDeliveryRunDisplyTO>
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	List<NonDeliveryRunDisplyTO> inquireDRS(NonDeliveryRunTO nonDeliveryRunTO) throws CGSystemException, CGBusinessException;

	/**
	 * getDeliveryConsignmentDetails -Gets the details for a particular.
	 *
	 * @param to the to
	 * @param consignmentNo the consignment no
	 * @return the delivery consignment details for branch
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	List<DeliveryDO> getDeliveryConsignmentDetailsForBranch(CGBaseTO to, String consignmentNo)throws CGSystemException, CGBusinessException;
	
	

	/**
	 * Gets the delivery d os.
	 *
	 * @param consgNo the consg no
	 * @return the delivery d os
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	List<DeliveryDO> getDeliveryDOs(String...consgNo) throws CGSystemException, CGBusinessException;
	
	/**
	 * Gets the reason do by id.
	 *
	 * @param id the id
	 * @return the reason do by id
	 * @throws CGBusinessException the cG business exception
	 */
	ReasonDO getReasonDOById(Integer id) throws CGBusinessException;
	
	/**
	 * Update all delivery d os.
	 *
	 * @param deliveryDOs the delivery d os
	 * @param miscExpenseDos the misc expense dos
	 * @param bdmFdmDOs the bdm fdm d os
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	void updateAllDeliveryDOs(List<? extends DeliveryDO> deliveryDOs, List<MiscExpenseDO> miscExpenseDos, List<? extends DeliveryDO> bdmFdmDOs) throws CGSystemException, CGBusinessException;
	
	/**
	 * getFranchiseeCodeValuesByApexBranch - Only Apex branch offices are considered for all FR list to pulled.
	 *
	 * @param branchId the branch id
	 * @return the franchisee code values by apex branch
	 * @throws CGSystemException the cG system exception
	 */
	List<FranchiseeTO> getFranchiseeCodeValuesByApexBranch(
			Integer branchId) throws CGSystemException;

	/**
	 * Checks if is duplicate drs no.
	 *
	 * @param aDRSNumber the a drs number
	 * @param branchId the branch id
	 * @param franchiseeCode the franchisee code
	 * @param headerFileName the header file name
	 * @return true, if is duplicate drs no
	 * @throws CGBusinessException the cG business exception
	 */
	boolean isDuplicateDrsNo(String aDRSNumber, String branchId, String franchiseeCode, String headerFileName) throws CGBusinessException;
	
	
	

	/**
	 * Gets the non delivery reasons.
	 *
	 * @return the non delivery reasons
	 * @throws CGSystemException the cG system exception
	 */
	List<ReasonDO> getNonDeliveryReasons()throws CGSystemException;

	/**
	 * Checks if is drs no augogenerated for branch.
	 *
	 * @param aDRSNumber the a drs number
	 * @param branchId the branch id
	 * @return true, if is drs no augogenerated for branch
	 * @throws CGSystemException the cG system exception
	 */
	boolean isDrsNoAugogeneratedForBranch(String aDRSNumber, Integer branchId)throws CGSystemException;

	/**
	 * Checks if is drs no augogenerated for franchisee.
	 *
	 * @param aDRSNumber the a drs number
	 * @param franchiseeId the franchisee id
	 * @return true, if is drs no augogenerated for franchisee
	 * @throws CGSystemException the cG system exception
	 */
	boolean isDrsNoAugogeneratedForFranchisee(String aDRSNumber,
			Integer franchiseeId)throws CGSystemException;

	/**
	 * Gets the ndrs.
	 *
	 * @param to the to
	 * @return the ndrs
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<NonDeliveryRunDO> getNDRS(NonDeliveryRunTO to)throws CGBusinessException, CGSystemException;

	/**
	 * Gets the previous reason.
	 *
	 * @param to the to
	 * @param aConsgNumber the a consg number
	 * @return the previous reason
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	ReasonDO getPreviousReason(CGBaseTO to, String aConsgNumber) throws CGBusinessException, CGSystemException;
	
	/**
	 * Gets the bdmless than delivery date.
	 *
	 * @param deliveryDates the delivery dates
	 * @param consgNo the consg no
	 * @return the bdmless than delivery date
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<DeliveryDO> getBdmlessThanDeliveryDate(String[] deliveryDates, String[] consgNo) throws CGBusinessException, CGSystemException;

	/**
	 * Gets the reason do by code.
	 *
	 * @param reasonCode the reason code
	 * @return the reason do by code
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	 
	ReasonDO getReasonDOByCode(String reasonCode)throws CGBusinessException, CGSystemException;

	/**
	 * Save or update all delivery d os.
	 *
	 * @param deliveryDOs the delivery d os
	 * @throws CGBusinessException the cG business exception
	 */
	void saveOrUpdateAllDeliveryDOs(List<? extends DeliveryDO> deliveryDOs)
			throws CGBusinessException;
}
