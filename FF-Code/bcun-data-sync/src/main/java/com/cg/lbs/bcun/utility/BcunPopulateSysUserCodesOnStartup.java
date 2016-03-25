package com.cg.lbs.bcun.utility;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cg.lbs.bcun.dao.BcunPopulateSysUserCodesDAO;
import com.ff.domain.bcun.SystemAuthDO;


// TODO: Auto-generated Javadoc
/**
 * The Class BcunPopulateSysUserCodesOnStartup.
 */
@SuppressWarnings("unused")
public class BcunPopulateSysUserCodesOnStartup {

	
	/** The system user codes map. */
	private static Map<String, String> systemUserCodesMap = null;
	
	/** The bcun populate sys user codes dao. */
	private BcunPopulateSysUserCodesDAO bcunPopulateSysUserCodesDAO;
	
	/**
	 * This method will be called at server startup while bean is loading . It will populate 
	 * a hash map with key as userId and value as system user code and store it at application level
	 * Populate system user codes map.
	 */
	
	private void populateSystemUserCodes() {
		systemUserCodesMap = new HashMap<String, String>();
		List<SystemAuthDO> sysUserCodesList = bcunPopulateSysUserCodesDAO.retrieveAllSysUserCodes();
		
		if(sysUserCodesList!= null && sysUserCodesList.size()>0){
			Iterator<SystemAuthDO> itr = sysUserCodesList.iterator();
			
			while(itr.hasNext()){
				SystemAuthDO systemAuthDo = itr.next();
				systemUserCodesMap.put(systemAuthDo.getSystemUserCode(), systemAuthDo.getAuthBranchCode());
			}
		}
	}	
	
	/**
	 * Gets the bcun populate sys user codes dao.
	 *
	 * @return the bcun populate sys user codes dao
	 */
	public BcunPopulateSysUserCodesDAO getBcunPopulateSysUserCodesDAO() {
		return bcunPopulateSysUserCodesDAO;
	}

	/**
	 * Sets the bcun populate sys user codes dao.
	 *
	 * @param bcunPopulateSysUserCodesDAO the new bcun populate sys user codes dao
	 */
	public void setBcunPopulateSysUserCodesDAO(
			BcunPopulateSysUserCodesDAO bcunPopulateSysUserCodesDAO) {
		this.bcunPopulateSysUserCodesDAO = bcunPopulateSysUserCodesDAO;
	}

	/**
	 * Gets the system user codes map.
	 *
	 * @return the system user codes map
	 */
	public static Map<String, String> getSystemUserCodesMap() {
		return systemUserCodesMap;
	}
}
