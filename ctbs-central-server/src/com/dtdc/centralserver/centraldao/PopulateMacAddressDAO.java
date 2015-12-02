/*
 * @author soagarwa
 */
package com.dtdc.centralserver.centraldao;

import java.util.List;

import com.dtdc.domain.master.authorization.SystemAuthenticationDO;

// TODO: Auto-generated Javadoc
/**
 * The Interface PopulateMacAddressDAO.
 */
public interface PopulateMacAddressDAO {

	/**
	 * Retrieve all mac address.
	 *
	 * @return the list
	 */
	List<SystemAuthenticationDO> retrieveAllMacAddress();
	
	/**
	 * Gets the sys authentication do by mac.
	 *
	 * @param userInfo the user info
	 * @param mac the mac
	 * @return the sys authentication do by mac
	 */
	SystemAuthenticationDO getSysAuthenticationDOByMac(String userInfo, String mac);

}
