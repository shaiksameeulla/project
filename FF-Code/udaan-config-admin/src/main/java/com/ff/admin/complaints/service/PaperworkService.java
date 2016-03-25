package com.ff.admin.complaints.service;

import java.io.IOException;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.complaints.ComplaintsFileDetailsTO;
import com.ff.complaints.ServiceRequestPaperworkTO;

public interface PaperworkService {

	/**
	 * To save or update paper work
	 * 
	 * @param paperworkTO
	 * @return boolean value
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 * @throws IOException
	 */
	boolean saveOrUpdatePaperwork(ServiceRequestPaperworkTO paperworkTO)
			throws CGBusinessException, CGSystemException, IOException;

	/**
	 * To get paper work details
	 * 
	 * @param serviceRequestId
	 * @return paperworkTOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<ServiceRequestPaperworkTO> getPaperworkDetails(Integer serviceRequestId)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get complants file details
	 * 
	 * @param complaintId
	 * @return detailsTOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<ComplaintsFileDetailsTO> getComplaintFileDtls(Integer complaintId)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get paper work file - actual file contents
	 * 
	 * @param paperWorkId
	 * @return fileDetailsTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	ComplaintsFileDetailsTO getPaperworkFile(Integer paperWorkId)
			throws CGBusinessException, CGSystemException;

}
