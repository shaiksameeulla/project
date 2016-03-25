package com.ff.universe.tracking.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.domain.umc.UserDO;
import com.ff.organization.OfficeTO;
import com.ff.tracking.ProcessTO;

/**
 * @author nkattung
 * 
 */
public interface TrackingUniversalDAO {
	
	
	/**@Desc:gets the process based on the process code
	 * @param process
	 * @return ProcessDO
	 * @throws CGSystemException
	 */
	public ProcessDO getProcess(ProcessTO process) throws CGSystemException;

	/**
	 * gets the office for given officeId
	 * @param officeId
	 * @return OfficeDO 
	 * @throws CGSystemException
	 */
	public OfficeDO getOfficeByOfficeId(Integer officeId) throws CGSystemException;

	/**
	 * gets the loggedInOfficeDetails
	 * @param office
	 * @return
	 * @throws CGSystemException
	 */
	public OfficeDO getLoggedInOffice(OfficeTO office) throws CGSystemException;
	
	/**
	 *gets the office Details
	 * @param officeId
	 * @param cityId
	 * @return OfficeDO
	 */
	public List<OfficeDO> getOfficeByDestCityId(Integer officeId, Integer cityId) throws CGSystemException;
	public UserDO getCreatedByDeatils(Integer userId) throws CGSystemException;
}
