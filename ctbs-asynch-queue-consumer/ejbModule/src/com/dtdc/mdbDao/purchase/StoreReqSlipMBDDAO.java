/**
 * 
 */
package src.com.dtdc.mdbDao.purchase;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.dtdc.domain.purchase.StoreReqSlipDO;

// TODO: Auto-generated Javadoc
/**
 * The Interface StoreReqSlipMBDDAO.
 *
 * @author joaugust
 * This interface defines the methods for the Purchase related CTBS functions
 */
public interface StoreReqSlipMBDDAO {
	
	/**
	 * Save st odetails.
	 *
	 * @param storeReqSlipDOList the store req slip do list
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	public  Boolean saveSTOdetails(List<StoreReqSlipDO> storeReqSlipDOList) throws CGSystemException;
}
