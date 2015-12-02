/**
 * 
 */
package src.com.dtdc.mdbServices.dmc;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.dtdc.to.dmc.DMCFranchiseeDirectEmpTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface DmcReportMDBService.
 *
 * @author mohammes
 */
public interface DmcReportMDBService {
	
	/**
	 * Save deliverymanagement info.
	 *
	 * @param dmcTo the dmc to
	 * @return true, if successful
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public boolean saveDeliverymanagementInfo(
			CGBaseTO dmcTo)
			throws CGBusinessException, CGSystemException;
	
	/**
	 * Save deliverymanagement info.
	 *
	 * @param dMCyesterDayInfo the d m cyester day info
	 * @return true, if successful
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public boolean saveDeliverymanagementInfo(
			List<DMCFranchiseeDirectEmpTO> dMCyesterDayInfo)
			throws CGBusinessException, CGSystemException;

}
