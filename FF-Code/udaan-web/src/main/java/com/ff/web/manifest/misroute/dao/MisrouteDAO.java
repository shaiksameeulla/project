package com.ff.web.manifest.misroute.dao;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;

public interface MisrouteDAO {

	

	/**
	 * ** @Desc:gets the remarks from consignment manifested
	 * 
	 * @param manifestId
	 * @param consgId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	String getRemarks(Integer manifestId, Integer consgId)
			throws CGBusinessException, CGSystemException;

}
