/**
 * 
 */
package com.capgemini.lbs.framework.dao.sms;

import java.util.List;

import com.capgemini.lbs.framework.domain.SmsDO;
import com.capgemini.lbs.framework.exception.CGSystemException;

// TODO: Auto-generated Javadoc
/**
 * The Interface SmsSenderDAO.
 *
 * @author narmdr
 */
public interface SmsSenderDAO {

	/**
	 * Gets the configurable param map by names.
	 *
	 * @param paramNames the param names
	 * @return the configurable param map by names
	 * @throws CGSystemException the cG system exception
	 */
	List<?> getConfigurableParamMapByNames(List<String> paramNames) throws CGSystemException;

	/**
	 * Save sms details.
	 *
	 * @param smsDO the sms do
	 * @return 
	 * @throws CGSystemException the cG system exception
	 */
	SmsDO saveSmsDetails(SmsDO smsDO) throws CGSystemException;	
	
}
