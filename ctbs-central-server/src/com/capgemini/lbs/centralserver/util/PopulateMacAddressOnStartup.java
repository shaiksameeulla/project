/*
 * @author soagarwa
 */
package com.capgemini.lbs.centralserver.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.dtdc.centralserver.centraldao.PopulateMacAddressDAO;
import com.dtdc.domain.master.authorization.SystemAuthenticationDO;

// TODO: Auto-generated Javadoc
/**
 * The Class PopulateMacAddressOnStartup.
 */
@SuppressWarnings("unused")
public class PopulateMacAddressOnStartup {

	/** The mac address map. */
	private static Map<String, String> macAddressMap = null;
	
	/** The mac address dao. */
	private PopulateMacAddressDAO macAddressDao;

	/**
	 * This method will be called at server startup while bean is loadind . It will populate 
	 * a hash map with key as userId and value as mac address and store it at application level
	 * Populate mac address.
	 */
	
	private void populateMacAddress() {
		macAddressMap = new HashMap<String, String>();
		List<SystemAuthenticationDO> macList = macAddressDao
				.retrieveAllMacAddress();
		if(macList != null && macList.size()>0){
			Iterator<SystemAuthenticationDO> itr = macList.iterator();
			while(itr.hasNext()){
				SystemAuthenticationDO systemAuthDo = itr.next();
				macAddressMap.put(systemAuthDo.getAllocatedEmpCode(), systemAuthDo.getMacAddress());
			}
		}
	}
	
	/**
	 * Gets the mac address map.
	 *
	 * @return the macAddressMap
	 */
	public static Map<String, String> getMacAddressMap() {
		return macAddressMap;
	}

	/**
	 * Gets the mac address dao.
	 *
	 * @return the macAddressDao
	 */
	public PopulateMacAddressDAO getMacAddressDao() {
		return macAddressDao;
	}

	/**
	 * Sets the mac address dao.
	 *
	 * @param macAddressDao the macAddressDao to set
	 */
	public void setMacAddressDao(PopulateMacAddressDAO macAddressDao) {
		this.macAddressDao = macAddressDao;
	}

}
