package com.ff.universe.tracking.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.tracking.ProcessMapDO;
import com.ff.organization.OfficeTO;
import com.ff.tracking.ProcessTO;

public interface TrackingUniversalService {

	/**
	 * @Desc:gets the process based on the process code
	 * @param process
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public ProcessTO getProcess(ProcessTO process) throws CGSystemException,
			CGBusinessException;

	/**
	 * generates the process Number with specific format
	 * 
	 * @param processTO
	 * @param officeTO
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public String createProcessNumber(ProcessTO processCode,OfficeTO officeCode)
			throws CGSystemException, CGBusinessException;

	/**
	 * gets the loggedIn Office details
	 * 
	 * @param office
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	OfficeTO getLoggedInOffice(OfficeTO office) throws CGSystemException,
			CGBusinessException;

	/**
	 * gets the Office details
	 * @param officeId
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public OfficeTO getReportingOffice(Integer officeId) throws CGSystemException, CGBusinessException;
	
	/**
	 * formats the template text to detailed tracking path
	 * @param processTO
	 * @param processMapTO
	 * @return string: tracking path
	 * @throws CGSystemException 
	 * @throws CGBusinessException 
	 * @throws NumberFormatException 
	 */
	public String formatArtifactPath(String template, ProcessMapDO processMapDO ) throws NumberFormatException, CGBusinessException, CGSystemException;
	
	public String formatArtifactPath(String template, ProcessMapDO processMapDO,String flag) throws NumberFormatException, CGBusinessException, CGSystemException;
	
	
	public List<Integer> getOffice(Integer officeId, Integer cityId) throws CGBusinessException;

	public OfficeTO getOfficeByOfficeId(Integer officeId) throws CGBusinessException,CGSystemException;
	
}
