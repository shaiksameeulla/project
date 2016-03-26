package com.cg.lbs.opsmanintg.dao;

public interface BcunSystemAuthDAO {
	
		public Boolean isAuthorizedSystemUser(String userCode,String branchCode) throws Exception;
}
