package com.cg.lbs.bcun.service;

import java.util.List;

import com.ff.domain.bcun.SystemAuthDO;

// TODO: Auto-generated Javadoc
/**
 * The Interface BcunSystmeAuthService.
 */
public interface BcunSystemAuthService {

	/**
	 * Checks if is auth system user.
	 *
	 * @param userCode the user code
	 * @param branchCode the branch code
	 * @return the boolean
	 * @throws Exception the exception
	 */
	public Boolean isAuthSystemUser(String userCode,String branchCode) throws Exception;
	
	/**
	 * Gets the branch code.
	 *
	 * @return the branch code
	 * @throws Exception the exception
	 */
	public String getBranchCode() throws Exception;
	
	public List<SystemAuthDO> retrieveAllSysUserCodes() throws Exception;
	}
