package src.com.dtdc.mdbDao.delivery;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.dtdc.domain.expense.MiscExpenseDO;
import com.dtdc.domain.master.ReasonDO;
import com.dtdc.domain.transaction.delivery.DeliveryDO;


// TODO: Auto-generated Javadoc
/**
 * The Interface DeliveryRunMDBDAO.
 */
public interface DeliveryRunMDBDAO {

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
	 * Update all delivery d os.
	 *
	 * @param deliveryDOs the delivery d os
	 * @param miscExpenseDos the misc expense dos
	 */
	void updateAllDeliveryDOs(List<DeliveryDO> deliveryDOs, List<MiscExpenseDO> miscExpenseDos);
	
	/**
	 * Gets the reason do by id.
	 *
	 * @param id the id
	 * @return the reason do by id
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	ReasonDO getReasonDOById(Integer id) throws CGSystemException,
	CGBusinessException;
	
	/**
	 * Save or update all delivery d os.
	 *
	 * @param deliveryRunDOs the delivery run d os
	 * @param miscExpenseDos the misc expense dos
	 * @param bdmFdmDos the bdm fdm dos
	 * @throws CGBusinessException the cG business exception
	 */
	void saveOrUpdateAllDeliveryDOs(List<? extends DeliveryDO> deliveryRunDOs, List<MiscExpenseDO> miscExpenseDos, List<? extends DeliveryDO> bdmFdmDos) throws CGBusinessException;

	/**
	 * Gets the fDM or bdm dtls.
	 *
	 * @param isBdm the is bdm
	 * @param consgNo the consg no
	 * @return the fDM or bdm dtls
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	List<DeliveryDO> getFDMOrBDMDtls(Boolean isBdm,List<String>consgNo)
			throws CGSystemException, CGBusinessException;
	
	/**
	 * Gets the delivery dtls.
	 *
	 * @param fdmNumber the fdm number
	 * @param runsheetNum the runsheet num
	 * @param cnNumber the cn number
	 * @return the delivery dtls
	 * @throws CGSystemException the cG system exception
	 */
	List<? extends DeliveryDO> getDeliveryDtls(String fdmNumber,String runsheetNum,String cnNumber)throws CGSystemException;
	
}
