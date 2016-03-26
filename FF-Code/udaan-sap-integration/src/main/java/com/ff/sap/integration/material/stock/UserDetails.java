/**
 * 
 */
package com.ff.sap.integration.material.stock;

import org.springframework.beans.factory.InitializingBean;

import com.ff.universe.mec.dao.MECUniversalDAO;

/**
 * @author cbhure
 *
 */
public class UserDetails implements InitializingBean{

	private MECUniversalDAO mecUniversalDAO;

	public void setMecUniversalDAO(MECUniversalDAO mecUniversalDAO) {
		this.mecUniversalDAO = mecUniversalDAO;
	}
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		String SAP_USER;
		//mecUniversalDAO.getSAPUserDetails();
		
		
	}

	
	
}
