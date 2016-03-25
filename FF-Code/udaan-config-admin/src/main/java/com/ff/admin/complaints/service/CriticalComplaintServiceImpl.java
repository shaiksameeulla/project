package com.ff.admin.complaints.service;

import java.util.Calendar;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.upload.FormFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.complaints.constants.ComplaintsCommonConstants;
import com.ff.admin.complaints.dao.CriticalComplaintDAO;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.complaints.CriticalComplaintTO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.complaints.ServiceRequestComplaintDO;
import com.ff.domain.complaints.ServiceRequestDO;
import com.ff.domain.complaints.ServiceRequestPapersDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.organization.OfficeTO;

/**
 * @author hkansagr
 * 
 */
public class CriticalComplaintServiceImpl implements CriticalComplaintService {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(CriticalComplaintServiceImpl.class);

	/** The criticalComplaintDAO. */
	private CriticalComplaintDAO criticalComplaintDAO;

	/** The ComplaintsCommonService. */
	private ComplaintsCommonService complaintsCommonService;

	/**
	 * @param criticalComplaintDAO
	 *            the criticalComplaintDAO to set
	 */
	public void setCriticalComplaintDAO(
			CriticalComplaintDAO criticalComplaintDAO) {
		this.criticalComplaintDAO = criticalComplaintDAO;
	}

	/**
	 * @param complaintsCommonService
	 *            the complaintsCommonService to set
	 */
	public void setComplaintsCommonService(
			ComplaintsCommonService complaintsCommonService) {
		this.complaintsCommonService = complaintsCommonService;
	}

	@Override
	public void saveOrUpdateCriticalComplaint(CriticalComplaintTO to)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("CriticalComplaintServiceImpl :: saveOrUpdateCriticalComplaint() :: START");
		ServiceRequestComplaintDO domain = new ServiceRequestComplaintDO();
		complaintDomainObjConverter(to, domain);
		criticalComplaintDAO.saveOrUpdateCriticalComplaint(domain);
		if (!StringUtil.isEmptyInteger(domain.getServiceRequestComplaintId())) {
			to.setServiceRequestComplaintId(domain
					.getServiceRequestComplaintId());
			to.setMailerId(domain.getMailerPaperDO().getServiceRequestPaperId());
		} else {
			throw new CGBusinessException(
					ComplaintsCommonConstants.DTLS_NOT_SAVED_SUCCESSFULLY);
		}
		LOGGER.trace("CriticalComplaintServiceImpl :: saveOrUpdateCriticalComplaint() :: END");
	}

	/**
	 * To convert Critical Complaint domain from transfer object
	 * 
	 * @param to
	 * @param domain
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private void complaintDomainObjConverter(CriticalComplaintTO to,
			ServiceRequestComplaintDO domain) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("CriticalComplaintServiceImpl :: complaintDomainObjConverter() :: START");

		// Set primary key to null from 0 - zero
		if (StringUtil.isEmptyInteger(to.getServiceRequestComplaintId())) {
			to.setServiceRequestComplaintId(null);
		}

		try {
			// Copy common properties
			PropertyUtils.copyProperties(domain, to);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in CriticalComplaintServiceImpl :: complaintDomainObjConverter() :: ",
					e);
			throw new CGBusinessException(e);
		}

		// Set complaint creation date
		domain.setComplaintCreationDate(DateUtil
				.parseStringDateToDDMMYYYYHHMMFormat(to
						.getComplaintCreationDateStr()));

		// Set FIR date
		if (!StringUtil.isStringEmpty(to.getFirDateStr())) {
			domain.setFirDate(DateUtil.stringToDDMMYYYYFormat(to
					.getFirDateStr()));
		}

		// Set type date
		if (!StringUtil.isStringEmpty(to.getTypeDateStr())) {
			domain.setTypeDate(DateUtil.stringToDDMMYYYYFormat(to
					.getTypeDateStr()));
		}

		// Set LIR date
		if (!StringUtil.isStringEmpty(to.getLirDateStr())) {
			domain.setLirDate(DateUtil.stringToDDMMYYYYFormat(to
					.getLirDateStr()));
		}

		// Set COF date
		if (!StringUtil.isStringEmpty(to.getCofDateStr())) {
			domain.setCofDate(DateUtil.stringToDDMMYYYYFormat(to
					.getCofDateStr()));
		}

		// Set Lost Letter date
		if (!StringUtil.isStringEmpty(to.getLostLetterDateStr())) {
			domain.setLostLetterDate(DateUtil.stringToDDMMYYYYFormat(to
					.getLostLetterDateStr()));
		}

		// Set Complaint Id or Service request id.
		if (!StringUtil.isEmptyInteger(to.getComplaintId())) {
			ServiceRequestDO serviceRequestDO = new ServiceRequestDO();
			serviceRequestDO.setServiceRequestId(to.getComplaintId());
			domain.setServiceRequestDO(serviceRequestDO);
		}

		// Set Mailer Paper Work Details and Upload file
		ServiceRequestPapersDO mailerPaperDO = prepareMailerPaperDO(to);
		domain.setMailerPaperDO(mailerPaperDO);

		// Setting Created Date & Updated Date
		domain.setCreatedDate(Calendar.getInstance().getTime());
		domain.setUpdatedDate(Calendar.getInstance().getTime());

		LOGGER.trace("CriticalComplaintServiceImpl :: complaintDomainObjConverter() :: END");
	}

	/**
	 * To prepare mailer paper work details and save file
	 * 
	 * @param to
	 * @throws CGBusinessException
	 */
	private ServiceRequestPapersDO prepareMailerPaperDO(CriticalComplaintTO to)
			throws CGBusinessException {
		LOGGER.trace("CriticalComplaintServiceImpl :: prepareMailerPaperDO() :: START");
		ServiceRequestPapersDO mailerPaperDO = new ServiceRequestPapersDO();
		FormFile formFile = to.getMailerFile();

		// Uploading file to server
		if (!StringUtil.isNull(formFile)) {
			// Set File Name
			to.setMailerFileName(formFile.getFileName());
			// Set Mailer Created Date if Null.
			if (StringUtil.isStringEmpty(to.getMailerCreatedDateStr())) {
				to.setMailerCreatedDateStr(DateUtil
						.getDateInDDMMYYYYHHMMSlashFormat(Calendar
								.getInstance().getTime()));
			}
			// To convert file to data byte to save in database.
			byte[] file = null;
			try {
				file = formFile.getFileData();
				if (StringUtil.isNull(file)) {
					throw new CGBusinessException(
							ComplaintsCommonConstants.FILE_NOT_UPLOADED_SUCCESSFULLY);
				} else if (file.length > FrameworkConstants.MYSQL_MEDIUM_BLOB_SIZE) {
					throw new CGBusinessException(
							AdminErrorConstants.COMPLAINTS_FILE_UPLOAD_SIZE_EXCEEDED);
				} else if (file.length == 0) {
					throw new CGBusinessException(
							AdminErrorConstants.COMPLAINTS_FILE_UPLOAD_SIZE_EMPTY);
				}
			} catch (CGBusinessException e) {
				LOGGER.error(
						"Exception occurs in CriticalComplaintServiceImpl :: complaintTransferObjConverter() ::CGBusinessException ",
						e);
				throw e;
			} catch (Exception e) {
				LOGGER.error(
						"Exception occurs in CriticalComplaintServiceImpl :: complaintTransferObjConverter() :: ",
						e);
				throw new CGBusinessException(
						ComplaintsCommonConstants.FILE_NOT_UPLOADED_SUCCESSFULLY);
			}
			mailerPaperDO.setFile(file);
		}

		// Set mailer id - primary key.
		if (!StringUtil.isEmptyInteger(to.getMailerId())) {
			mailerPaperDO.setServiceRequestPaperId(to.getMailerId());
		}

		// Set service request id.
		mailerPaperDO.setServiceRequestId(to.getComplaintId());

		// Set Created By as logged in user id (it should updated by).
		mailerPaperDO.setCreatedBy(to.getUpdatedBy());

		// Set Mailer Created Date.
		mailerPaperDO.setCreatedDate(DateUtil
				.parseStringDateToDDMMYYYYHHMMFormat(to
						.getMailerCreatedDateStr()));

		// Set mailer copy - file name.
		mailerPaperDO.setFileName(to.getMailerFileName());
		mailerPaperDO.setFileDescription(ComplaintsCommonConstants.MAILER_FILE);
		LOGGER.trace("CriticalComplaintServiceImpl :: prepareMailerPaperDO() :: END");
		return mailerPaperDO;
	}

	@Override
	public CriticalComplaintTO getServiceRequestDtls(CriticalComplaintTO to)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("CriticalComplaintServiceImpl :: getServiceRequestDtls() :: START");
		ServiceRequestDO serviceRequestDO = criticalComplaintDAO
				.getServiceRequestDtls(to);
		prepareCriticalComplaintTO(serviceRequestDO, to);
		LOGGER.trace("CriticalComplaintServiceImpl :: getServiceRequestDtls() :: END");
		return to;
	}

	/**
	 * To prepare CriticalComplaintTO from ServiceRequestDO
	 * 
	 * @param serviceRequestDO
	 * @param to
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private void prepareCriticalComplaintTO(ServiceRequestDO serviceRequestDO,
			CriticalComplaintTO to) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("CriticalComplaintServiceImpl :: prepareCriticalComplaintTO() :: START");
		// Set Complaint No or Service Request No.
		to.setComplaintNo(serviceRequestDO.getServiceRequestNo());

		// Set Complaint Id or Service Request Id.
		to.setComplaintId(serviceRequestDO.getServiceRequestId());

		// Get Consignment details by Consg. No or Booking Reference No.
		ConsignmentDO consgDO = null;
		if (serviceRequestDO.getBookingNoType().equalsIgnoreCase(
				CommonConstants.BOOKING_NO_TYPE_CONSG_NO)) {
			consgDO = complaintsCommonService
					.getConsgDtlsByConsgNo(serviceRequestDO.getBookingNo());
		} else if (serviceRequestDO.getBookingNoType().equalsIgnoreCase(
				CommonConstants.BOOKING_NO_TYPE_CONSG_NO)) {
			consgDO = complaintsCommonService
					.getConsgDtlsByBookingRefNo(serviceRequestDO.getBookingNo());
		}

		// Set Consignment No.
		if (!StringUtil.isNull(consgDO)) {
			to.setConsignmentNumber(consgDO.getConsgNo());

			// Set Branch Name
			if (!StringUtil.isNull(consgDO)) {
				if (!StringUtil.isEmptyInteger(consgDO.getOrgOffId())) {
					OfficeTO orgOffTO = complaintsCommonService
							.getOfficeDetails(consgDO.getOrgOffId());
					to.setBranch(orgOffTO.getOfficeName());
				}
			}

			// Set Reason
			to.setReason(CommonConstants.EMPTY_STRING);

			// Set Declared Value
			to.setDeclaredValue(consgDO.getDeclaredValue());

			// Set Consignor Name
			if (!StringUtil.isNull(consgDO.getConsignor())) {
				StringBuffer consignerName = null;
				if (!StringUtil.isNull(consgDO.getConsignor().getFirstName())) {
					consignerName = new StringBuffer();
					consignerName.append(consgDO.getConsignor().getFirstName());
					if (!StringUtil
							.isNull(consgDO.getConsignor().getLastName())) {
						consignerName.append(CommonConstants.SPACE);
						consignerName.append(consgDO.getConsignor()
								.getLastName());
					}
					to.setConsignerName(consignerName.toString());
				}
			}

			// Set Customer Details
			if (!StringUtil.isEmptyInteger(consgDO.getCustomer())) {
				CustomerDO customer = complaintsCommonService
						.getCustomerDtlsByCustId(consgDO.getCustomer());

				// Set Customer Code
				if (!StringUtil.isStringEmpty(customer.getCustomerCode())) {
					to.setCustomerCode(customer.getCustomerCode());
				}

				// Set Address
				if (!StringUtil.isNull(customer.getAddressDO())) {
					to.setCustomerAddress(customer.getAddressDO().getAddress1()
							+ CommonConstants.SPACE
							+ customer.getAddressDO().getAddress2()
							+ CommonConstants.SPACE
							+ customer.getAddressDO().getAddress3());
				}

				// Set Phone
				if (!StringUtil.isStringEmpty(customer.getPhone())) {
					to.setCustomerPhone(customer.getPhone());
				}

				// Set Email
				if (!StringUtil.isStringEmpty(customer.getEmail())) {
					to.setCustomerEmail(customer.getEmail());
				}
			}
		}
		LOGGER.trace("CriticalComplaintServiceImpl :: prepareCriticalComplaintTO() :: END");
	}

	@Override
	public CriticalComplaintTO searchCriticalComplaint(CriticalComplaintTO to)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("CriticalComplaintServiceImpl :: searchCriticalComplaint() :: START");
		ServiceRequestComplaintDO domain = criticalComplaintDAO
				.searchCriticalComplaint(to);
		complaintTransferObjConverter(domain, to);
		LOGGER.trace("CriticalComplaintServiceImpl :: searchCriticalComplaint() :: END");
		return to;
	}

	/**
	 * To convert Critical Complaint transfer object from domain object
	 * 
	 * @param domain
	 * @param to
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private void complaintTransferObjConverter(
			ServiceRequestComplaintDO domain, CriticalComplaintTO to)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("CriticalComplaintServiceImpl :: complaintTransferObjConverter() :: START");
		try {
			// Copy common properties
			PropertyUtils.copyProperties(to, domain);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in CriticalComplaintServiceImpl :: complaintTransferObjConverter() :: ",
					e);
			throw new CGBusinessException(e);
		}
		// Set complaint creation date
		to.setComplaintCreationDateStr(DateUtil
				.getDateInDDMMYYYYHHMMSlashFormat(domain
						.getComplaintCreationDate()));

		// Set FIR date
		if (!StringUtil.isNull(domain.getFirDate())) {
			to.setFirDateStr(DateUtil.getDDMMYYYYDateToString(domain
					.getFirDate()));
		}

		// Set type date
		if (!StringUtil.isNull(domain.getTypeDate())) {
			to.setTypeDateStr(DateUtil.getDDMMYYYYDateToString(domain
					.getTypeDate()));
		}

		// Set LIR date
		if (!StringUtil.isNull(domain.getLirDate())) {
			to.setLirDateStr(DateUtil.getDDMMYYYYDateToString(domain
					.getLirDate()));
		}

		// Set COF date
		if (!StringUtil.isNull(domain.getCofDate())) {
			to.setCofDateStr(DateUtil.getDDMMYYYYDateToString(domain
					.getCofDate()));
		}

		// Set Lost Letter date
		if (!StringUtil.isNull(domain.getLostLetterDate())) {
			to.setLostLetterDateStr(DateUtil.getDDMMYYYYDateToString(domain
					.getLostLetterDate()));
		}

		// Set Complaint Id or Service request id & Complaint No.
		if (!StringUtil.isNull(domain.getServiceRequestDO())) {
			// Complaint Id.
			to.setComplaintId(domain.getServiceRequestDO()
					.getServiceRequestId());
			// Complaint No.
			to.setComplaintNo(domain.getServiceRequestDO()
					.getServiceRequestNo());
		}

		// Extract mailer paper details from domain object to transfer object
		extractMailerPaperDO(domain, to);

		LOGGER.trace("CriticalComplaintServiceImpl :: complaintTransferObjConverter() :: END");
	}

	/**
	 * To extract mailer paper details from domain object to transfer object
	 * 
	 * @param domain
	 * @param to
	 */
	private void extractMailerPaperDO(ServiceRequestComplaintDO domain,
			CriticalComplaintTO to) {
		LOGGER.trace("CriticalComplaintServiceImpl :: extractMailerPaperDO() :: START");
		ServiceRequestPapersDO mailerPaperDO = domain.getMailerPaperDO();
		if (!StringUtil.isNull(mailerPaperDO)) {
			to.setMailerId(mailerPaperDO.getServiceRequestPaperId());
			to.setMailerFileName(mailerPaperDO.getFileName());
			to.setMailerCreatedDateStr(DateUtil
					.getDateInDDMMYYYYHHMMSlashFormat(mailerPaperDO
							.getCreatedDate()));
		}
		LOGGER.trace("CriticalComplaintServiceImpl :: extractMailerPaperDO() :: END");
	}

	@Override
	public boolean isCriticalComplaintExist(String complaintNo)
			throws CGBusinessException, CGSystemException {
		return criticalComplaintDAO.isCriticalComplaintExist(complaintNo);
	}

}
