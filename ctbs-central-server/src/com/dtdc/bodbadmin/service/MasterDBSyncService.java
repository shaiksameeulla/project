/**
 * 
 */
package com.dtdc.bodbadmin.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;

/**
 * @author nisahoo
 *
 */
public interface MasterDBSyncService {
	
	public void extractDataForAllBranches()throws CGBusinessException;

	public void extractDataForAllFranchisees() throws CGBusinessException;

}
