/**
 * 
 */
package com.dtdc.bodbadmin.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface BODBAdminRequestProcessorService.
 *
 * @author nisahoo
 */
public interface BODBAdminRequestProcessorService {

	/**
	 * Load data for branch.
	 *
	 * @param baseTO the base to
	 * @return the cG base to
	 * @throws CGBusinessException the cG business exception
	 */
	public CGBaseTO loadDataForBranch(CGBaseTO baseTO) throws CGBusinessException;
	
	/**
	 * Status update for branch.
	 *
	 * @param baseTO the base to
	 * @return the cG base to
	 * @throws CGBusinessException the cG business exception
	 */
	public CGBaseTO statusUpdateForBranch(CGBaseTO baseTO) throws CGBusinessException;
	
	/**
	 * Status restore for branch.
	 *
	 * @param baseTO the base to
	 * @return the cG base to
	 * @throws CGBusinessException the cG business exception
	 */
	CGBaseTO statusRestoreForBranch(CGBaseTO baseTO) throws CGBusinessException;

}
