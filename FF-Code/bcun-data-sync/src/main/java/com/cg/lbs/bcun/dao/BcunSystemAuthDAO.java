package com.cg.lbs.bcun.dao;

public interface BcunSystemAuthDAO {
	
		public Boolean isAuthorizedSystemUser(String userCode,String branchCode) throws Exception;
}
