/**
 * 
 */
package com.dtdc.centralserver.centraldao.dmc;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.dtdc.to.dmc.DMCFranchiseeDirectEmpTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface CentralDelvryMangmntCellDAO.
 *
 * @author mohammes
 */
public interface CentralDelvryMangmntCellDAO {
	
	/**
	 * Find fdm details.
	 *
	 * @param dmcTo the dmc to
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	List findFdmDetails(DMCFranchiseeDirectEmpTO dmcTo)
	throws CGSystemException;
	
	/**
	 * Gets the delivered info.
	 *
	 * @param dmcTo the dmc to
	 * @return the delivered info
	 * @throws CGSystemException the cG system exception
	 */
	List getDeliveredInfo(DMCFranchiseeDirectEmpTO dmcTo)throws CGSystemException;
	
	/**
	 * Gets the prepared info.
	 *
	 * @param dmcTo the dmc to
	 * @return the prepared info
	 * @throws CGSystemException the cG system exception
	 */
	List getPreparedInfo(DMCFranchiseeDirectEmpTO dmcTo)throws CGSystemException;
}
