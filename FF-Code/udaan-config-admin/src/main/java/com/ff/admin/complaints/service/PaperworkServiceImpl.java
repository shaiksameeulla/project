package com.ff.admin.complaints.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.struts.upload.FormFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.complaints.constants.ComplaintsCommonConstants;
import com.ff.admin.complaints.dao.ComplaintsCommonDAO;
import com.ff.admin.complaints.dao.PaperworkDAO;
import com.ff.complaints.ComplaintsFileDetailsTO;
import com.ff.complaints.ServiceRequestPaperworkTO;
import com.ff.domain.complaints.ServiceRequestPapersDO;
import com.ff.domain.umc.EmployeeUserDO;

/**
 * @author hkansagr
 * 
 */
public class PaperworkServiceImpl implements PaperworkService {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(PaperworkServiceImpl.class);

	/** The paperworkDAO. */
	private PaperworkDAO paperworkDAO;

	/** The complaints common dao. */
	private ComplaintsCommonDAO complaintsCommonDAO;

	/**
	 * @param paperworkDAO
	 *            the paperworkDAO to set
	 */
	public void setPaperworkDAO(PaperworkDAO paperworkDAO) {
		this.paperworkDAO = paperworkDAO;
	}

	/**
	 * @param complaintsCommonDAO
	 *            the complaintsCommonDAO to set
	 */
	public void setComplaintsCommonDAO(ComplaintsCommonDAO complaintsCommonDAO) {
		this.complaintsCommonDAO = complaintsCommonDAO;
	}

	@Override
	public boolean saveOrUpdatePaperwork(ServiceRequestPaperworkTO paperworkTO)
			throws CGBusinessException, CGSystemException, IOException {
		LOGGER.trace("PaperworkServiceImpl :: saveOrUpdatePaperwork :: START");
		List<ServiceRequestPapersDO> paperworkDOs = null;
		paperworkDOs = paperworkDAO.getPaperworkdetails(paperworkTO
				.getServiceRequestId());
		if (CGCollectionUtils.isEmpty(paperworkDOs)) {
			paperworkDOs = new ArrayList<ServiceRequestPapersDO>();
		}
		paperworkDOs = convertPaperworkTO2PaperworkDO(paperworkTO, paperworkDOs);
		if (!CGCollectionUtils.isEmpty(paperworkDOs)) {
			for (ServiceRequestPapersDO paperworkDO : paperworkDOs) {
				paperworkDAO.saveOrUpdatePaperwork(paperworkDO);
			}
			return true;
		}else if(CGCollectionUtils.isEmpty(paperworkDOs)){
			paperworkDAO.saveOrUpdatePaperwork(preparePaperworkDO(paperworkTO,null,null,null));
			return true;
		}
		LOGGER.trace("PaperworkServiceImpl :: saveOrUpdatePaperwork :: END");
		return false;
	}

	
	/**
	 * To convert paper work transfer object to domain object list
	 * 
	 * @param paperworkTO
	 * @param paperworkDOs
	 * @return paperworkDOList
	 * @throws CGBusinessException
	 * @throws IOException
	 */
	private List<ServiceRequestPapersDO> convertPaperworkTO2PaperworkDO(
			ServiceRequestPaperworkTO paperworkTO,
			List<ServiceRequestPapersDO> paperworkDOs)
			throws CGBusinessException, IOException {
		LOGGER.trace("PaperworkServiceImpl :: convertPaperworkTO2PaperworkDO :: START");
		List<ServiceRequestPapersDO> paperworkDOList = new ArrayList<ServiceRequestPapersDO>();
		FormFile formFile = null;
		if (!StringUtil.isNull(paperworkTO)) {

			ServiceRequestPapersDO paperworkDO = null;

			// 1. Complaint File
			
			formFile = paperworkTO.getComplaintFile();
			if ((!StringUtil.isNull(formFile)) && formFile.getFileSize() > 0) {
				paperworkTO.setServiceRequestPaperworkId(paperworkTO
						.getComplaintFileId());
				paperworkDO = preparePaperworkDO(paperworkTO, formFile,
						ComplaintsCommonConstants.COMPLAINT_LETTER,
						paperworkDOs);
				paperworkDOList.add(paperworkDO);
			}

			// 2. Consignor Copy
			formFile = paperworkTO.getConsignorCopyFile();
			if ((!StringUtil.isNull(formFile))  && formFile.getFileSize() > 0) {
				paperworkTO.setServiceRequestPaperworkId(paperworkTO
						.getConsignorCopyFileId());
				paperworkDO = preparePaperworkDO(paperworkTO, formFile,
						ComplaintsCommonConstants.CONSIGNOR_COPY, paperworkDOs);
				paperworkDOList.add(paperworkDO);
			}

			// 3. Mail Copy
			formFile = paperworkTO.getMailCopyFile();
			if ((!StringUtil.isNull(formFile))  && formFile.getFileSize() > 0) {
				paperworkTO.setServiceRequestPaperworkId(paperworkTO
						.getMailCopyFileId());
				paperworkDO = preparePaperworkDO(paperworkTO, formFile,
						ComplaintsCommonConstants.MAIL_COPY, paperworkDOs);
				paperworkDOList.add(paperworkDO);
			}

			// 4. Under taking file - Optional
			formFile = paperworkTO.getUndertakingFile();
			if ((!StringUtil.isEmptyInteger(paperworkTO.getUndertakingFileId())
					|| (!StringUtil.isNull(formFile) && !StringUtil
							.isStringEmpty(formFile.getFileName())))  && formFile.getFileSize() > 0) {
				paperworkTO.setServiceRequestPaperworkId(paperworkTO
						.getUndertakingFileId());
				paperworkDO = preparePaperworkDO(paperworkTO, formFile,
						ComplaintsCommonConstants.UNDERTAKING_LETTER,
						paperworkDOs);
				paperworkDOList.add(paperworkDO);
			}

			// 5. Invoice file - Optional
			formFile = paperworkTO.getInvoiceFile();
			if ((!StringUtil.isEmptyInteger(paperworkTO.getInvoiceFileId())
					|| (!StringUtil.isNull(formFile) && !StringUtil
							.isStringEmpty(formFile.getFileName())))  && formFile.getFileSize() > 0) {
				paperworkTO.setServiceRequestPaperworkId(paperworkTO
						.getInvoiceFileId());
				paperworkDO = preparePaperworkDO(paperworkTO, formFile,
						ComplaintsCommonConstants.INVOICE_COPY, paperworkDOs);
				paperworkDOList.add(paperworkDO);
			}
		}
		LOGGER.trace("PaperworkServiceImpl :: convertPaperworkTO2PaperworkDO :: END");
		return paperworkDOList;
	}

	/**
	 * If file present then save convert if to paper work DO
	 * 
	 * @param paperworkTO
	 * @param formFile
	 * @param fileDesc
	 * @param paperworkDOs
	 * @return paperworkDO
	 * @throws CGBusinessException
	 */
	private ServiceRequestPapersDO preparePaperworkDO(
			ServiceRequestPaperworkTO paperworkTO, FormFile formFile,
			String fileDesc, List<ServiceRequestPapersDO> paperworkDOs)
			throws CGBusinessException {
		LOGGER.trace("PaperworkServiceImpl :: preparePaperworkDO :: START");
		ServiceRequestPapersDO paperworkDO = new ServiceRequestPapersDO();
		if (!StringUtil.isEmptyInteger(paperworkTO
				.getServiceRequestPaperworkId())) {
			paperworkDO.setServiceRequestPaperId(paperworkTO
					.getServiceRequestPaperworkId());
		}
		paperworkDO.setServiceRequestId(paperworkTO.getServiceRequestId());
		// Feedback
		if (!StringUtil.isStringEmpty(paperworkTO.getFeedback())) {
			paperworkDO.setFeedback(paperworkTO.getFeedback());
		}
		// Client Meet
		if (!StringUtil.isStringEmpty(paperworkTO.getClientMeet())) {
			paperworkDO.setClientMeet(paperworkTO.getClientMeet());
		}
		paperworkDO.setCreatedDate(DateUtil.stringToDDMMYYYYFormat(paperworkTO
				.getServiceRequestPaperworkDateStr()));
		if (StringUtil.isNull(paperworkDO.getCreatedBy())) {
			paperworkDO.setCreatedBy(paperworkTO.getCreatedBy());
		}
		// Transfer to ICC
		if (!StringUtil.isEmptyInteger(paperworkTO.getTransferIcc())) {
			paperworkDO.setTransferIcc(paperworkTO.getTransferIcc());
		}

		// To convert file to data byte to save in database.
		if (!StringUtil.isNull(formFile)
				&& !StringUtil.isStringEmpty(formFile.getFileName())) {
			byte[] file = null;
			try {
				file = formFile.getFileData();
				if (StringUtil.isNull(file)) {
					throw new CGBusinessException(
							ComplaintsCommonConstants.FILE_NOT_UPLOADED_SUCCESSFULLY);
				}
			} catch (Exception e) {
				LOGGER.error(
						"Exception occurs in CriticalComplaintServiceImpl :: complaintTransferObjConverter() :: ",
						e);
				throw new CGBusinessException(
						ComplaintsCommonConstants.FILE_NOT_UPLOADED_SUCCESSFULLY);
			}
			paperworkDO.setFile(file);

			// File name
			paperworkDO.setFileName(formFile.getFileName());
		} else {
			// if already exist and update other paper work details from screen
			if(!StringUtil.isEmptyList(paperworkDOs)){
			ServiceRequestPapersDO paperWork = getSavedPaperWorkDO(
					paperworkDOs, paperworkTO.getServiceRequestPaperworkId());
			if(!StringUtil.isNull(paperWork)){
			paperworkDO.setFile(paperWork.getFile());
			paperworkDO.setFileName(paperWork.getFileName());
				}
			}
		}
		// File Description
		paperworkDO.setFileDescription(fileDesc);

		paperworkDO.setUpdatedBy(paperworkTO.getUpdateBy());
		paperworkDO.setUpdatedDate(Calendar.getInstance().getTime());
		LOGGER.trace("PaperworkServiceImpl :: preparePaperworkDO :: END");
		return paperworkDO;
	}

	/**
	 * To get already saved paper work details
	 * 
	 * @param paperworkDOs
	 * @param serviceRequestPaperworkId
	 * @return paperWork
	 */
	private ServiceRequestPapersDO getSavedPaperWorkDO(
			List<ServiceRequestPapersDO> paperworkDOs,
			Integer serviceRequestPaperworkId) {
		LOGGER.trace("PaperworkServiceImpl :: getSavedPaperWorkDO :: START");
		ServiceRequestPapersDO paperWork = null;
		for (ServiceRequestPapersDO paperworkDO : paperworkDOs) {
			if (paperworkDO.getServiceRequestPaperId().intValue() == serviceRequestPaperworkId
					.intValue()) {
				paperWork = paperworkDO;
				break;
			}
		}
		LOGGER.trace("PaperworkServiceImpl :: getSavedPaperWorkDO :: END");
		return paperWork;
	}

	@Override
	public List<ServiceRequestPaperworkTO> getPaperworkDetails(
			Integer serviceRequestId) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("PaperworkServiceImpl :: getPaperworkDetails :: START");
		List<ServiceRequestPaperworkTO> paperworkTOs = null;
		List<ServiceRequestPapersDO> paperworkDOs = null;
		if (!StringUtil.isEmptyInteger(serviceRequestId)) {
			paperworkDOs = paperworkDAO.getPaperworkdetails(serviceRequestId);
			paperworkTOs = new ArrayList<ServiceRequestPaperworkTO>();
			for (ServiceRequestPapersDO paperworkDO : paperworkDOs)
				if (!StringUtil.isNull(paperworkDO)) {
					ServiceRequestPaperworkTO paperworkTO = new ServiceRequestPaperworkTO();
					paperworkTO = (ServiceRequestPaperworkTO) CGObjectConverter
							.createToFromDomain(paperworkDO, paperworkTO);
					paperworkTO.setServiceRequestPaperworkDateStr(DateUtil
							.getDDMMYYYYDateToString(paperworkDO
									.getCreatedDate()));
					setServiceReqPaperworkId(paperworkTO, paperworkDO);
					paperworkTOs.add(paperworkTO);
				}
		}
		LOGGER.trace("PaperworkServiceImpl :: getPaperworkDetails :: END");
		return paperworkTOs;
	}

	/**
	 * To set respective primary key - id as per file description
	 * 
	 * @param paperworkTO
	 * @param paperworkDO
	 */
	private void setServiceReqPaperworkId(
			ServiceRequestPaperworkTO paperworkTO,
			ServiceRequestPapersDO paperworkDO) {
		LOGGER.trace("PaperworkServiceImpl :: setServiceReqPaperworkId :: START");
		if (!StringUtil.isStringEmpty(paperworkDO.getFileDescription())) {
			switch (paperworkDO.getFileDescription()) {
			case ComplaintsCommonConstants.COMPLAINT_LETTER:
				paperworkTO.setComplaintFileId(paperworkDO
						.getServiceRequestPaperId());
				break;
			case ComplaintsCommonConstants.MAIL_COPY:
				paperworkTO.setMailCopyFileId(paperworkDO
						.getServiceRequestPaperId());
				break;
			case ComplaintsCommonConstants.CONSIGNOR_COPY:
				paperworkTO.setConsignorCopyFileId(paperworkDO
						.getServiceRequestPaperId());
				break;
			case ComplaintsCommonConstants.UNDERTAKING_LETTER:
				paperworkTO.setUndertakingFileId(paperworkDO
						.getServiceRequestPaperId());
				break;
			case ComplaintsCommonConstants.INVOICE_COPY:
				paperworkTO.setInvoiceFileId(paperworkDO
						.getServiceRequestPaperId());
				break;
			}
		}
		LOGGER.trace("PaperworkServiceImpl :: setServiceReqPaperworkId :: END");
	}

	@Override
	public List<ComplaintsFileDetailsTO> getComplaintFileDtls(
			Integer complaintId) throws CGBusinessException, CGSystemException {
		LOGGER.trace("PaperworkServiceImpl :: getComplaintFileDtls :: START");
		List<ComplaintsFileDetailsTO> detailsTOs = null;
		List<ServiceRequestPapersDO> paperworkDOs = paperworkDAO
				.getPaperworkdetails(complaintId);
		if (!CGCollectionUtils.isEmpty(paperworkDOs)) {
			detailsTOs = prepareComplaintFileTOList(paperworkDOs);
		}
		LOGGER.trace("PaperworkServiceImpl :: getComplaintFileDtls :: END");
		return detailsTOs;
	}

	/**
	 * To prepare ComplaintsFileDetailsTO from paperWorkDOs
	 * 
	 * @param paperworkDOs
	 * @return detailsTOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private List<ComplaintsFileDetailsTO> prepareComplaintFileTOList(
			List<ServiceRequestPapersDO> paperworkDOs)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PaperworkServiceImpl :: prepareComplaintFileTOList :: START");
		List<ComplaintsFileDetailsTO> detailsTOs = new ArrayList<ComplaintsFileDetailsTO>(
				paperworkDOs.size());
		for (ServiceRequestPapersDO paperworkDO : paperworkDOs) {
			ComplaintsFileDetailsTO to = new ComplaintsFileDetailsTO();
			to.setPaperWorkId(paperworkDO.getServiceRequestPaperId());
			to.setFileName(paperworkDO.getFileName());
			to.setFileDescrition(paperworkDO.getFileDescription());

			// To get employee name by user id.
			EmployeeUserDO empUserDO = complaintsCommonDAO
					.getEmployeeUser(paperworkDO.getCreatedBy());
			// To set upload by user name = employee first name + last name
			String uploadByUser = empUserDO.getEmpDO().getFirstName()
					+ CommonConstants.SPACE
					+ empUserDO.getEmpDO().getLastName();
			to.setUploadedBy(uploadByUser);

			to.setUploadedDate(DateUtil.getDDMMYYYYDateToString(paperworkDO
					.getCreatedDate()));
			detailsTOs.add(to);
		}
		LOGGER.trace("PaperworkServiceImpl :: prepareComplaintFileTOList :: END");
		return detailsTOs;
	}

	@Override
	public ComplaintsFileDetailsTO getPaperworkFile(Integer paperWorkId)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PaperworkDAOImpl :: getPaperworkdetails() :: START");
		ComplaintsFileDetailsTO fileDetailsTO = null;
		ServiceRequestPapersDO paperWorkDO = paperworkDAO
				.getPaperworkFile(paperWorkId);
		if (!StringUtil.isNull(paperWorkDO)) {
			fileDetailsTO = new ComplaintsFileDetailsTO();
			byte[] fileData = paperWorkDO.getFile();
			fileDetailsTO.setFileData(fileData);
			fileDetailsTO.setFileName(paperWorkDO.getFileName());
		}
		LOGGER.trace("PaperworkDAOImpl :: getPaperworkdetails() :: END");
		return fileDetailsTO;
	}

}
