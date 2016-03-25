package com.ff.admin.complaints.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.complaints.ServiceRequestPapersDO;

public interface PaperworkDAO {

	boolean saveOrUpdatePaperwork(ServiceRequestPapersDO paperworkDO)
			throws CGSystemException;

	List<ServiceRequestPapersDO> getPaperworkdetails(Integer serviceRequestId)
			throws CGSystemException;

	/**
	 * To get paper work file.
	 * 
	 * @param paperWorkId
	 * @return papersDO
	 * @throws CGSystemException
	 */
	ServiceRequestPapersDO getPaperworkFile(Integer paperWorkId)
			throws CGSystemException;

}
