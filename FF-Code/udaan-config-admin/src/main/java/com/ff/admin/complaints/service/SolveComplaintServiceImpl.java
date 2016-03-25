/**
 * 
 */
package com.ff.admin.complaints.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.MailSenderTO;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.complaints.constants.ComplaintsCommonConstants;
import com.ff.admin.complaints.converter.ComplaintsConverter;
import com.ff.admin.complaints.dao.SolveComplaintDAO;
import com.ff.complaints.ServiceRequestStatusTO;
import com.ff.complaints.ServiceRequestTO;
import com.ff.complaints.ServiceRequestTransfertoTO;
import com.ff.domain.complaints.ServiceRequestDO;

/**
 * @author abarudwa
 * 
 */
public class SolveComplaintServiceImpl implements SolveComplaintService {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(SolveComplaintServiceImpl.class);
	private ComplaintsCommonService complaintsCommonService;
	private SolveComplaintDAO solveComplaintDAO;

	public void setComplaintsCommonService(
			ComplaintsCommonService complaintsCommonService) {
		this.complaintsCommonService = complaintsCommonService;
	}

	/**
	 * @param solveComplaintDAO
	 *            the solveComplaintDAO to set
	 */
	public void setSolveComplaintDAO(SolveComplaintDAO solveComplaintDAO) {
		this.solveComplaintDAO = solveComplaintDAO;
	}

	@Override
	public List<ServiceRequestStatusTO> getServiceRequestStatus()
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("SolveComplaintServiceImpl::getServiceRequestStatus::START------------>:::::::");
		List<ServiceRequestStatusTO> statusTOs = null;
		statusTOs = complaintsCommonService.getServiceRequestStatus();
		if (StringUtil.isEmptyList(statusTOs)) {
			throw new CGBusinessException(
					ComplaintsCommonConstants.ERROR_IN_GETTING_BACKLINE_SUMMARY_STATUS);
		}
		LOGGER.debug("SolveComplaintServiceImpl::getServiceRequestStatus::END------------>:::::::");
		return statusTOs;
	}

	@Override
	public List<ServiceRequestTransfertoTO> getServiceRequestTransferList()
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("SolveComplaintServiceImpl::getServiceRequestTransferList::START------------>:::::::");
		List<ServiceRequestTransfertoTO> serviceRequestTranferTOs = null;
		serviceRequestTranferTOs = complaintsCommonService.getTransfettoDetails();
		if (StringUtil.isEmptyList(serviceRequestTranferTOs)) {
			throw new CGBusinessException(
					ComplaintsCommonConstants.ERROR_IN_GETTING_SERVICE_REQUEST_TRANSFER_LIST);
		}
		LOGGER.debug("SolveComplaintServiceImpl::getServiceRequestTransferList::END------------>:::::::");
		return serviceRequestTranferTOs;
	}
	
	@Override
	public ServiceRequestTO getComplaintDetailsByServiceRequestNo(
			String serviceRequestNo) throws CGSystemException,CGBusinessException {
		LOGGER.trace("SolveComplaintServiceImpl::getComplaintDetailsByServiceRequestNo::START------------>:::::::");
		ServiceRequestDO serviceRequestDO = null;
		ServiceRequestTO serviceRequestTO = null;
		serviceRequestDO = solveComplaintDAO.getComplaintDetailsByServiceRequestNo(serviceRequestNo);
		if(!StringUtil.isNull(serviceRequestDO)){
			serviceRequestTO = ComplaintsConverter.ConvertTOFromDO(serviceRequestDO);
			serviceRequestTO.setUpdateDateStr(DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
		}
		LOGGER.trace("SolveComplaintServiceImpl::getComplaintDetailsByServiceRequestNo::END------------>:::::::");
		return serviceRequestTO;
	}

	@Override
	public ServiceRequestTO saveServiceRequestDetails(
			ServiceRequestTO serviceRequestTO) throws CGSystemException,
			CGBusinessException {
		LOGGER.trace("SolveComplaintServiceImpl::saveServiceRequestDetails::START------------>:::::::");
		ServiceRequestDO serviceRequestDO = new ServiceRequestDO();
		if(!StringUtil.isEmptyInteger(serviceRequestTO.getServiceRequestId())){
		serviceRequestDO = solveComplaintDAO.getComplaintDetailsByServiceRequestId(serviceRequestTO.getServiceRequestId());
		}
		serviceRequestDO = ComplaintsConverter.ConvertDOFromTO(serviceRequestTO,serviceRequestDO);
		serviceRequestDO = solveComplaintDAO.saveServiceRequestDetails(serviceRequestDO);
		if(!StringUtil.isNull(serviceRequestDO)){
			/** Send Email to caller*/
			MailSenderTO senderTO=new MailSenderTO();
			String subject = "Complaint number : "+ serviceRequestDO.getServiceRequestNo() + " has been resolved";
			senderTO.setTo(new String[]{serviceRequestTO.getCallerEmail()});
			senderTO.setMailSubject(subject);
			StringBuilder plainMailBody = getMailBody(serviceRequestDO.getServiceRequestNo(), serviceRequestDO.getBookingNo());
			senderTO.setPlainMailBody(plainMailBody.toString());
			complaintsCommonService.sendEmail(senderTO);
			serviceRequestTO = ComplaintsConverter.ConvertTOFromDO(serviceRequestDO);
		}
		LOGGER.trace("SolveComplaintServiceImpl::saveServiceRequestDetails::END------------>:::::::");
		return serviceRequestTO;
	}

	private StringBuilder getMailBody(String serviceRequestNo, String consgNo) {
		StringBuilder plainMailBody=new StringBuilder();
		plainMailBody.append("<html><body> Dear Sir/madam");
		plainMailBody.append("<BR> Your complaint with reference no: " + serviceRequestNo + " related to " +consgNo + " has been resolved.");
		plainMailBody.append("<BR><BR> Regarads,<BR> First Flight Customer Service.");
		return plainMailBody;
	}

	@Override
	public Date getConsignmentDeliveryDate(String consigNo)
			throws CGSystemException, CGBusinessException {
		return complaintsCommonService.getConsignmentDeliveryDate(consigNo);
	}
}
