package com.cg.lbs.opsmanintg.dao;

import java.util.List;

import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.cg.lbs.opsmanintg.constant.BcunConstant;

// TODO: Auto-generated Javadoc
/**
 * The Class BcunSystemAuthDAOImpl.
 */
public class BcunSystemAuthDAOImpl extends CGBaseDAO implements BcunSystemAuthDAO {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(BcunSystemAuthDAOImpl.class);
	
	
	/* (non-Javadoc)
	 * @see com.cg.lbs.opsmanintg.dao.BcunSystemAuthDAO#isAuthorizedSystemUser(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Boolean isAuthorizedSystemUser(String userCode,String branchCode) throws Exception{
		// TODO Auto-generated method stub
		LOGGER.debug("BcunSystemAuthDAOImpl::isAuthorizedSystemUser::start=====>");
		Boolean isAuthSysUser=false;
		List<Integer> systemAuthIds = null;
		
		String queryName=BcunConstant.IS_AUTH_SYSTEM_USER_QUERY;
		String params[]={BcunConstant.SYSTEM_USER_CODE,BcunConstant.AUTH_BRANCH_CODE};
		Object values[]={userCode,branchCode};
		
		systemAuthIds = getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, params, values);
		
		if(systemAuthIds!=null & !systemAuthIds.isEmpty()){
			isAuthSysUser=true;
		}
		LOGGER.debug("BcunSystemAuthDAOImpl::isAuthorizedSystemUser::end=====>");
		return isAuthSysUser;
	}
}
