package com.cg.lbs.bcun.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cg.lbs.bcun.dao.BcunPopulateSysUserCodesDAO;
import com.cg.lbs.bcun.dao.BcunSystemAuthDAO;
// TODO: Auto-generated Javadoc
import com.ff.domain.bcun.SystemAuthDO;

/**
 * The Class BcunSystmeAuthServiceImpl.
 */
public class BcunSystemAuthServiceImpl implements BcunSystemAuthService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BcunSystemAuthServiceImpl.class);
	
	/** The bcun system auth dao. */
	private BcunSystemAuthDAO bcunSystemAuthDao;
	
	private BcunPopulateSysUserCodesDAO bcunPopulateSysUserCodesDAO;
	
	/** The request branch code. */
	private String requestBranchCode;
	
	/**
	 * Gets the bcun system auth dao.
	 *
	 * @return the bcun system auth dao
	 */
	public BcunSystemAuthDAO getBcunSystemAuthDao() {
		return bcunSystemAuthDao;
	}

	/**
	 * Sets the bcun system auth dao.
	 *
	 * @param bcunSystemAuthDao the new bcun system auth dao
	 */
	public void setBcunSystemAuthDao(BcunSystemAuthDAO bcunSystemAuthDao) {
		this.bcunSystemAuthDao = bcunSystemAuthDao;
	}

	/**
	 * Gets the request branch code.
	 *
	 * @return the request branch code
	 */
	public String getRequestBranchCode() {
		return requestBranchCode;
	}

	/**
	 * Sets the request branch code.
	 *
	 * @param requestBranchCode the new request branch code
	 */
	public void setRequestBranchCode(String requestBranchCode) {
		this.requestBranchCode = requestBranchCode;
	}

	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.service.BcunSystmeAuthService#isAuthSystemUser(java.lang.String, java.lang.String)
	 */
	@Override
	public Boolean isAuthSystemUser(String userCode,String branchCode) throws Exception{
		Boolean isAuthSysUser=bcunSystemAuthDao.isAuthorizedSystemUser(userCode, branchCode);
		return isAuthSysUser;
	}

	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.service.BcunSystmeAuthService#getBranchCode()
	 */
	@Override
	public String getBranchCode() throws Exception{
		// TODO Auto-generated method stub
		return this.requestBranchCode;
	}

	@Override
	public List<SystemAuthDO> retrieveAllSysUserCodes() throws Exception {
		return bcunPopulateSysUserCodesDAO.retrieveAllSysUserCodes();
	}
	
	public BcunPopulateSysUserCodesDAO getBcunPopulateSysUserCodesDAO() {
		return bcunPopulateSysUserCodesDAO;
	}

	public void setBcunPopulateSysUserCodesDAO(
			BcunPopulateSysUserCodesDAO bcunPopulateSysUserCodesDAO) {
		this.bcunPopulateSysUserCodesDAO = bcunPopulateSysUserCodesDAO;
	}


}
