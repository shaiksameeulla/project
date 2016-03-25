package com.ff.admin.complaints.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.complaints.constants.ComplaintsCommonConstants;
import com.ff.admin.complaints.converter.ComplaintsConverter;
import com.ff.admin.complaints.dao.ComplaintsCommonDAO;
import com.ff.admin.complaints.dao.ServiceRequestForConsignmentDAO;
import com.ff.admin.tracking.consignmentTracking.service.ConsignmentTrackingService;
import com.ff.complaints.ServiceRequestForConsignmentTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.complaints.ServiceRequestDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.umc.EmployeeUserDO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.tracking.TrackingConsignmentTO;
import com.ff.umc.EmployeeUserTO;
import com.ff.universe.mec.dao.MECUniversalDAO;
import com.ff.universe.util.SMSSenderService;
//import com.ff.universe.util.EmailSenderService;

public class ServiceRequestForConsignmentImpl implements
		ServiceRequestForConsignment {
	
	private ComplaintsCommonService complaintsCommonService ;
	//private ServiceOfferingCommonService serviceOfferingCommonService;
	private ComplaintsCommonDAO complaintsCommonDAO;
	private ServiceRequestForConsignmentDAO serviceRequestForConsignmentDAO;
	//private EmailSenderService emailSenderService;
	private SMSSenderService smsSenderService;
	private MECUniversalDAO mecUniversalDAO;
	private ConsignmentTrackingService consignmentTrackingService;
	
	/**
	 * @param mecUniversalDAO the mecUniversalDAO to set
	 */
	public void setMecUniversalDAO(MECUniversalDAO mecUniversalDAO) {
		this.mecUniversalDAO = mecUniversalDAO;
	}

	/**
	 * @param smsSenderService the smsSenderService to set
	 */
	public void setSmsSenderService(SMSSenderService smsSenderService) {
		this.smsSenderService = smsSenderService;
	}

	/**
	 * @param emailSenderService the emailSenderService to set
	 */
	/*public void setEmailSenderService(EmailSenderService emailSenderService) {
		this.emailSenderService = emailSenderService;
	}*/

	/*public void setServiceOfferingCommonService(
			ServiceOfferingCommonService serviceOfferingCommonService) {
		this.serviceOfferingCommonService = serviceOfferingCommonService;
	}*/

	public void setComplaintsCommonService(
			ComplaintsCommonService complaintsCommonService) {
		this.complaintsCommonService = complaintsCommonService;
	}

	public void setComplaintsCommonDAO(ComplaintsCommonDAO complaintsCommonDAO) {
		this.complaintsCommonDAO = complaintsCommonDAO;
	}
	
	public void setServiceRequestForConsignmentDAO(
			ServiceRequestForConsignmentDAO serviceRequestForConsignmentDAO) {
		this.serviceRequestForConsignmentDAO = serviceRequestForConsignmentDAO;
	}

	@Override
	public List<StockStandardTypeTO> getSearchCategoryList()
			throws CGSystemException, CGBusinessException {
		// TODO Auto-generated method stub
		// return complaintsCommonService.getStandardTypesByTypeName(ComplaintsCommonConstants.COMPLAINTS_SEARCH); COMPLAINTS_TRACKING
		return complaintsCommonService.getStandardTypesByTypeName(ComplaintsCommonConstants.COMPLAINTS_TRACKING);
	}

	@Override
	public List<StockStandardTypeTO> getStatusbyType()
			throws CGSystemException, CGBusinessException {
		// TODO Auto-generated method stub
		return complaintsCommonService.getStandardTypesByTypeName(ComplaintsCommonConstants.COMPLAINTS_STATUS);
	}


	@Override
	public String generateReferenceNumber(String loginOfficeCode)
			throws CGBusinessException, CGSystemException {
		return complaintsCommonService.generateReferenceNumber(loginOfficeCode);
	}

	public void setConsignmentTrackingService(
			ConsignmentTrackingService consignmentTrackingService) {
		this.consignmentTrackingService = consignmentTrackingService;
	}

	@Override
	public void saveOrUpdateServiceConsigDtls(
			ServiceRequestForConsignmentTO serviceTO) throws CGBusinessException, CGSystemException {
		List<ServiceRequestDO> serviceRequestDOs= null;
		
		if(!StringUtil.isNull(serviceTO)){
			serviceRequestDOs= new ArrayList<>();
				ServiceRequestDO serviceRequestDO = ComplaintsConverter.serviceReqDomainConverter(serviceTO);
				if(!StringUtil.isNull(serviceRequestDO)){
				serviceRequestDOs.add(serviceRequestDO);
				}
				complaintsCommonDAO.saveOrUpdateComplaints(serviceRequestDOs);
		}
	}

	@Override
	public List<EmployeeUserTO> getBackLineEmpList(Integer officeId,
			String designation) throws CGBusinessException, CGSystemException {
		List<EmployeeUserTO> employeeTOs= null;
		
		List<EmployeeUserDO> employeeUserDOs = serviceRequestForConsignmentDAO
				.getBackLineEmpList(officeId,designation);
		
		if(!StringUtil.isNull(employeeUserDOs)){
			 employeeTOs=ComplaintsConverter.convertTosfromDomainObject(employeeUserDOs);
		}
		return employeeTOs;
}

	@Override
	public Boolean sendEmailByPlainText(String to) 
			throws CGBusinessException, CGSystemException {
		// TODO Auto-generated method stub
		boolean mailSent = false;
		//mailSent = emailSenderService.sendEmailByPlainText("test_user1@testfirstflight.com", to, subject, body);
		return mailSent;
	}
	@Override
	public void sendSMS(String num,HttpServletResponse response)
			throws CGBusinessException, CGSystemException {
		// TODO Auto-generated method stub
		String msg = "Complaints module testing";
		smsSenderService.sendSMS(num, msg, response);
	}
	@Override
	public ConsignmentTO getConsignmentDtls(String consgNo)
			throws CGBusinessException, CGSystemException {
		ConsignmentTO consignmentTO = new ConsignmentTO();
		ConsignmentDO consignmentDO = mecUniversalDAO.getConsignmentDtls(consgNo);
		if(!StringUtil.isNull(consignmentDO)){
			//consignmentTO =  (ConsignmentTO) CGObjectConverter.createToFromDomain(consignmentDO, consignmentTO);
			consignmentTO = ComplaintsConverter.convertTosfromDomainObject(consignmentDO);
		}
		return consignmentTO;
	}

	@Override
	public TrackingConsignmentTO viewTrackInformation(String consgNum,
			String refNum, String loginUserType) throws CGSystemException, CGBusinessException {
		return consignmentTrackingService.viewTrackInformation(consgNum, refNum, loginUserType);
	}
	

	

}
