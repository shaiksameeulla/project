package com.ff.admin.complaints.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.sms.SmsSenderUtil;
import com.capgemini.lbs.framework.to.MailSenderTO;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.to.SmsSenderTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.complaints.constants.ComplaintsCommonConstants;
import com.ff.admin.complaints.constants.ComplaintsServiceRequestConstants;
import com.ff.admin.complaints.converter.ComplaintsConverter;
import com.ff.admin.complaints.dao.ComplaintsCommonDAO;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.complaints.SearchServiceRequestGridTO;
import com.ff.complaints.SearchServiceRequestHeaderTO;
import com.ff.complaints.ServiceRequestFilters;
import com.ff.complaints.ServiceRequestQueryTypeTO;
import com.ff.complaints.ServiceRequestTO;
import com.ff.complaints.ServiceRequestValidationTO;
import com.ff.domain.complaints.ServiceRequestComplaintTypeDO;
import com.ff.domain.complaints.ServiceRequestCustTypeDO;
import com.ff.domain.complaints.ServiceRequestDO;
import com.ff.domain.complaints.ServiceRequestQueryTypeDO;
import com.ff.domain.complaints.ServiceRequestStatusDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.rate.RateCalculationInputTO;
import com.ff.to.rate.RateCalculationOutputTO;
import com.ff.to.rate.RateComponentTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;

/**
 * @author sdalli
 *
 */
public class ServiceRequestForServiceReqServiceImpl implements
ServiceRequestForServiceReqService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ServiceRequestForServiceReqServiceImpl.class);
	private ComplaintsCommonService complaintsCommonService ;
	private ComplaintsCommonDAO complaintsCommonDAO;






	public void setComplaintsCommonService(
			ComplaintsCommonService complaintsCommonService) {
		this.complaintsCommonService = complaintsCommonService;
	}



	public void setComplaintsCommonDAO(ComplaintsCommonDAO complaintsCommonDAO) {
		this.complaintsCommonDAO = complaintsCommonDAO;
	}



	@Override
	public List<StockStandardTypeTO> getSearchCategoryList()
			throws CGSystemException, CGBusinessException {
		return complaintsCommonService.getStandardTypesByTypeName(ComplaintsCommonConstants.COMPLAINTS_SEARCH);
	}



	@Override
	public List<StockStandardTypeTO> getQueryType() throws CGSystemException,
	CGBusinessException {
		return complaintsCommonService.getStandardTypesByTypeName(ComplaintsCommonConstants.COMPLAINTS_QUERY_TYPE);
	}



	@Override
	public List<StockStandardTypeTO> getServiceRelatedbyType()
			throws CGSystemException, CGBusinessException {
		return complaintsCommonService.getStandardTypesByTypeName(ComplaintsCommonConstants.COMPLAINTS_SERVICE_QRY);
	}



	@Override
	public List<StockStandardTypeTO> getStatusbyType()
			throws CGSystemException, CGBusinessException {
		return complaintsCommonService.getStandardTypesByTypeName(ComplaintsCommonConstants.COMPLAINTS_STATUS);
	}







	@Override
	public CityTO getCity(final CityTO cityTO) throws CGBusinessException,
	CGSystemException {
		return complaintsCommonService.getCity(cityTO);
	}



	@Override
	public List<EmployeeTO> getEmployeeDetailsByDesignationType(
			final String employeeDesignationType) throws CGBusinessException,
			CGSystemException {
		return  complaintsCommonService.getEmployeeDetailsByDesignationType(employeeDesignationType);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeTO> getEmployeeDetailsByUserRoleAndOffice(final String roleName,final Integer officeId)
			throws CGSystemException, CGBusinessException {
		return complaintsCommonService.getEmployeeDetailsByUserRoleAndOffice(roleName, officeId);
	}
	@Override
	public void saveOrUpdateServiceReqDtls(final ServiceRequestTO serviceRequestTO)
			throws CGBusinessException, CGSystemException {
		Boolean flag=Boolean.FALSE;
		ServiceRequestDO servicerequestDo=null;
		if(!StringUtil.isNull(serviceRequestTO)){
			servicerequestDo= new ServiceRequestDO();


			servicerequestDo.setTransactionOfficeId(serviceRequestTO.getLoginOfficeId());
			servicerequestDo.setCreatedDate(DateUtil.getCurrentDate());
			if(!StringUtil.isStringEmpty(serviceRequestTO.getLinkedServiceReqNo())){
				servicerequestDo.setLinkedServiceReqNo(serviceRequestTO.getLinkedServiceReqNo());
			}

			servicerequestDo.setCallerEmail(serviceRequestTO.getCallerEmail());
			servicerequestDo.setCallerName(serviceRequestTO.getCallerName());
			servicerequestDo.setCallerPhone(serviceRequestTO.getCallerPhone());
			servicerequestDo.setServiceResult(serviceRequestTO.getServiceResult());
			servicerequestDo.setCreatedBy(serviceRequestTO.getLogginUserId());
			servicerequestDo.setUpdatedBy(serviceRequestTO.getLogginUserId());
			servicerequestDo.setCreatedDate(DateUtil.getCurrentDate());
			servicerequestDo.setUpdateDate(DateUtil.getCurrentDate());

			if(!StringUtil.isStringEmpty(serviceRequestTO.getServiceType())){
				switch(serviceRequestTO.getServiceType()){
				case ComplaintsCommonConstants.SERVICE_REQUEST_TYPE_FOR_SERVICE:
					servicerequestDo.setBookingNoType(null);
					servicerequestDo.setBookingNo(null);
					break;
				case ComplaintsCommonConstants.SERVICE_REQUEST_TYPE_FOR_BOOKING_REF:
					servicerequestDo.setBookingNoType(ComplaintsCommonConstants.COMPLAINT_BOOKING_NO_TYPE_REF);
					servicerequestDo.setBookingNo(serviceRequestTO.getBookingNo());
					break;

				case ComplaintsCommonConstants.SERVICE_REQUEST_TYPE_FOR_CONSG:
					servicerequestDo.setBookingNoType(ComplaintsCommonConstants.COMPLAINT_BOOKING_NO_TYPE_CN);
					servicerequestDo.setBookingNo(serviceRequestTO.getBookingNo());
					break;
				case ComplaintsCommonConstants.SERVICE_REQUEST_TYPE_FOR_CONTACT_NO:
					//throw Business Exception
					break;
				}
			}

			if(!StringUtil.isStringEmpty(serviceRequestTO.getCustomerType())){
				String custType[]=serviceRequestTO.getCustomerType().split(FrameworkConstants.CHARACTER_TILDE);
				ServiceRequestCustTypeDO customerType= new ServiceRequestCustTypeDO();
				customerType.setServiceRequestCustomerTypeId(StringUtil.parseInteger(custType[0]));
				servicerequestDo.setServiceRequestCustTypeDO(customerType);

			}
			if(!StringUtil.isStringEmpty(serviceRequestTO.getConsignmentType())){
				//String consgTypes[]=serviceRequestTO.getConsignmentType().split(FrameworkConstants.CHARACTER_TILDE);
				servicerequestDo.setConsignmentType(StringUtil.trimAllWhitespace(serviceRequestTO.getConsignmentType()));
			}
			if(!StringUtil.isEmptyInteger(serviceRequestTO.getOriginCityId())){
				CityDO cityDo= new CityDO();
				cityDo.setCityId(serviceRequestTO.getOriginCityId());
				servicerequestDo.setOriginCity(cityDo);
			}
			if(!StringUtil.isEmptyInteger(serviceRequestTO.getProductId())){
				ProductDO product= new ProductDO();
				product.setProductId(serviceRequestTO.getProductId());
				servicerequestDo.setProductType(product);
			}
			if(!StringUtil.isEmptyInteger(serviceRequestTO.getPincodeId())){
				PincodeDO pincode= new PincodeDO();
				pincode.setPincodeId(serviceRequestTO.getPincodeId());
				servicerequestDo.setDestPincode(pincode);
			}
			if(!StringUtil.isStringEmpty(serviceRequestTO.getWeightKgs()) && !StringUtil.isStringEmpty(serviceRequestTO.getWeightGrm())){
				String weighInDouble=serviceRequestTO.getWeightKgs()+FrameworkConstants.CHARACTER_DOT+serviceRequestTO.getWeightGrm();
				servicerequestDo.setWeight(new BigDecimal(weighInDouble));
			}
			if(!StringUtil.isStringEmpty(serviceRequestTO.getSourceOfQuery())){
				servicerequestDo.setSourceOfQuery(StringUtil.trimAllWhitespace(serviceRequestTO.getSourceOfQuery()));
			}
			if(!StringUtil.isEmptyInteger(serviceRequestTO.getEmployeeId())){
				EmployeeDO assignedTo= new EmployeeDO();
				assignedTo.setEmployeeId(serviceRequestTO.getEmployeeId());
				assignedTo.setEmailId(serviceRequestTO.getEmpEmailId());
				assignedTo.setEmpPhone(serviceRequestTO.getEmpPhone());
				servicerequestDo.setAssignedTo(assignedTo);
			}
			if(!StringUtil.isStringEmpty(serviceRequestTO.getRemark())){
				if(serviceRequestTO.getRemark().length()>250){
					servicerequestDo.setRemark(serviceRequestTO.getRemark().substring(0, 250));
				}else{
					servicerequestDo.setRemark(serviceRequestTO.getRemark());
				}
			}
			if(!StringUtil.isStringEmpty(serviceRequestTO.getIndustryType())){
				servicerequestDo.setIndustryType(serviceRequestTO.getIndustryType());
			}
			//servicerequestDo.setServiceRequestType(serviceRequestTO.getServiceType());

			if(!StringUtil.isStringEmpty(serviceRequestTO.getComplaintCategory())){
				String compCotgry[]=serviceRequestTO.getComplaintCategory().split(FrameworkConstants.CHARACTER_TILDE);
				ServiceRequestComplaintTypeDO serviceRequestComplaintTypeDO= new ServiceRequestComplaintTypeDO();
				serviceRequestComplaintTypeDO.setServiceRequestComplaintTypeId(StringUtil.parseInteger(compCotgry[0]));
				servicerequestDo.setServiceRequestComplaintTypeDO(serviceRequestComplaintTypeDO);
			}else{
				//FIXME throw Exception
				//ExceptionUtil.prepareBusinessException(AdminErrorConstants.COMPLAINTS_CAN_NOT_BE_EMPTY,new String[]{ComplaintsServiceRequestConstants.COMPLAINT_CATEGORY_ERROR_KEY});
			}
			if(!StringUtil.isStringEmpty(serviceRequestTO.getServiceRelated())){
				String qryType[]=serviceRequestTO.getServiceRelated().split(FrameworkConstants.CHARACTER_TILDE);
				ServiceRequestQueryTypeDO serviceRequestQueryTypeDO= new ServiceRequestQueryTypeDO();
				serviceRequestQueryTypeDO.setServiceRequestQueryTypeId(StringUtil.parseInteger(qryType[0]));
				servicerequestDo.setServiceRequestQueryTypeDO(serviceRequestQueryTypeDO);
				
				String qryTypeCode=qryType[1];
				if(!StringUtil.isStringEmpty(qryTypeCode)){
					if(qryTypeCode.equalsIgnoreCase(ComplaintsServiceRequestConstants.SERVICE_REQUEST_SERVICE_QUERY_TYPE_LEAD_CALL)|| qryTypeCode.equalsIgnoreCase(ComplaintsServiceRequestConstants.SERVICE_REQUEST_SERVICE_QUERY_TYPE_PICKUP_CALL)){
						servicerequestDo.setOriginBranchId(serviceRequestTO.getOriginCityId());
						servicerequestDo.setOriginCity(null);
					}
				}
			}else{
				//FIXME throw Exception
				ExceptionUtil.prepareBusinessException(AdminErrorConstants.COMPLAINTS_CAN_NOT_BE_EMPTY,new String[]{ComplaintsServiceRequestConstants.COMPLAINT_SERVICE_RELATED_ERROR_KEY});
			}
			if(!StringUtil.isStringEmpty(serviceRequestTO.getStatus())){
				ServiceRequestStatusDO statusDO= new ServiceRequestStatusDO();
				String status[]=serviceRequestTO.getStatus().split(FrameworkConstants.CHARACTER_TILDE);
				statusDO.setServiceRequestStatusId(StringUtil.parseInteger(status[0]));
				statusDO.setStatusCode(status[1]);
				servicerequestDo.setServiceRequestStatusDO(statusDO);
			}else{
				//FIXME throw Exception
				ExceptionUtil.prepareBusinessException(AdminErrorConstants.COMPLAINTS_CAN_NOT_BE_EMPTY,new String[]{ComplaintsServiceRequestConstants.COMPLAINT_STATUS_ERROR_KEY});
			}
			if(!StringUtil.isStringEmpty(serviceRequestTO.getEmailToCaller())){
				servicerequestDo.setEmailToCaller(serviceRequestTO.getEmailToCaller());
			}
			if(!StringUtil.isStringEmpty(serviceRequestTO.getSmsToConsignee())){
				servicerequestDo.setSmsToConsignee(serviceRequestTO.getSmsToConsignee());
			}

			if(!StringUtil.isStringEmpty(serviceRequestTO.getSmsToConsignor())){
				servicerequestDo.setSmsToConsignor(serviceRequestTO.getSmsToConsignor());
			}
			String complaintNumber=null;
			if(StringUtil.isEmptyInteger(serviceRequestTO.getServiceRequestId())){
				complaintNumber = getServiceRequestNumber(serviceRequestTO);
				servicerequestDo.setIsNewServiceRequest(true);
				LOGGER.debug("ServiceRequestForServiceReqServiceImpl ::saveOrUpdateServiceReqDtls ::complaintNumber"+complaintNumber);
				//complaintNumber= StringUtil.generateDDMMYYHHMMSSRamdomNumber();
			}else{
				servicerequestDo.setIsNewServiceRequest(false);
				complaintNumber=serviceRequestTO.getServiceRequestNo();
				servicerequestDo.setServiceRequestId(serviceRequestTO.getServiceRequestId());
			}
			servicerequestDo.setServiceRequestNo(complaintNumber);
			serviceRequestTO.setServiceRequestNo(complaintNumber);
			if(StringUtil.isEmptyInteger(servicerequestDo.getServiceRequestId())){
				flag=complaintsCommonDAO.saveOrUpdateComplaints(servicerequestDo);
			}else{
				flag=complaintsCommonDAO.updateServiceRequest(servicerequestDo);
			}

			if(flag){
				servicerequestDo.setFromEmailId(serviceRequestTO.getFromEmailId());
				sendMail(servicerequestDo);
			}
		}

	}



	private void sendMail(ServiceRequestDO servicerequestDo)
			throws CGBusinessException, CGSystemException {
		String subject=null;
		StringBuilder plainMailBody=null;
		try {
			String statusCode=servicerequestDo.getServiceRequestStatusDO().getStatusCode();
			if(!StringUtil.isStringEmpty(statusCode)){
				List<MailSenderTO> mailerList=new ArrayList<>(2);

				subject="Your complaint with reference number "+servicerequestDo.getServiceRequestNo();
				if(statusCode.equalsIgnoreCase(ComplaintsServiceRequestConstants.COMPLAINT_STATUS_RESOLVED)){
					subject=subject+" has been Resolved";
				}else if(servicerequestDo.getIsNewServiceRequest()){
					subject=subject+" is Registered";
				}else{
					subject=subject+" is In-progress";
				}
				if(!StringUtil.isStringEmpty(servicerequestDo.getBookingNo())){
					subject=subject+" for consignment/Booking Ref no: "+servicerequestDo.getBookingNo();
					plainMailBody=getMailBodyForConsignemnt(servicerequestDo.getBookingNo(), servicerequestDo.getServiceRequestNo());
				}else{
					plainMailBody=getMailBodyForServiceRequest(servicerequestDo.getServiceRequestNo());
				}
					prepareCallerMailAddress(servicerequestDo, mailerList, subject,
							plainMailBody);
				prepareExecutiveMail(servicerequestDo, statusCode, mailerList);
				for(MailSenderTO senderTO:mailerList){
					senderTO.setFrom(servicerequestDo.getFromEmailId());
					complaintsCommonService.sendEmail(senderTO);
				}
			}
		} catch (Exception e) {
			LOGGER.error("ServiceRequestForServiceReqServiceImpl::saveOrUpdateServiceReqDtls ::sendMail",e);
		}
		
		if(!StringUtil.isStringEmpty(servicerequestDo.getSmsToConsignor()) && servicerequestDo.getSmsToConsignor().equalsIgnoreCase(FrameworkConstants.ENUM_YES)){
		try {
			String consinor=complaintsCommonService.getConsignorMobileNumberByConsgNo(servicerequestDo.getBookingNo(), servicerequestDo.getBookingNo());
			if(!StringUtil.isStringEmpty(consinor)){
			SmsSenderTO consinorSmsTo= null;
			consinorSmsTo = prepareSmsTO(servicerequestDo, subject);
			consinorSmsTo.setContactNumber(consinor);
			SmsSenderUtil.sendSms(consinorSmsTo);
			}
		} catch (Exception e) {
			LOGGER.error("ServiceRequestForServiceReqServiceImpl::saveOrUpdateServiceReqDtls ::Send SMS to Consinor",e);
		}
		}
		if(!StringUtil.isStringEmpty(servicerequestDo.getSmsToConsignee()) && servicerequestDo.getSmsToConsignee().equalsIgnoreCase(FrameworkConstants.ENUM_YES)){

			try {
				String consinee=complaintsCommonService.getConsigneeMobileNumberByConsgNo(servicerequestDo.getBookingNo(), servicerequestDo.getBookingNo());
				if(!StringUtil.isStringEmpty(consinee)){
					SmsSenderTO consineeSmsTo= null;
					consineeSmsTo = prepareSmsTO(servicerequestDo, subject);
					consineeSmsTo.setContactNumber(consinee);
					SmsSenderUtil.sendSms(consineeSmsTo);
				}
			} catch (Exception e) {
				LOGGER.error("ServiceRequestForServiceReqServiceImpl::saveOrUpdateServiceReqDtls ::Send SMS to Consinee",e);
			}

		}
	}



	private SmsSenderTO prepareSmsTO(ServiceRequestDO servicerequestDo,
			String subject) {
		SmsSenderTO smsTo;
		smsTo= new  SmsSenderTO();
		smsTo.setModuleName(CommonConstants.COMPAINTS_MODULE);
		smsTo.setUserId(servicerequestDo.getCreatedBy());
		smsTo.setImmediateSend(true);
		smsTo.setMessage(subject);
		return smsTo;
	}



	private void prepareCallerMailAddress(ServiceRequestDO servicerequestDo,
			List<MailSenderTO> mailerList, String subject,
			StringBuilder plainMailBody) {
		if(!StringUtil.isStringEmpty(servicerequestDo.getCallerEmail())){
			MailSenderTO callerSenderTO=new MailSenderTO();
			callerSenderTO.setTo(new String[]{servicerequestDo.getCallerEmail()});
			callerSenderTO.setMailSubject(subject);
			callerSenderTO.setPlainMailBody(plainMailBody.toString());
			mailerList.add(callerSenderTO);
		}
	}



	private void prepareExecutiveMail(ServiceRequestDO servicerequestDo,
			String statusCode, List<MailSenderTO> mailerList) {
		if(servicerequestDo.getAssignedTo()!=null && !StringUtil.isStringEmpty(servicerequestDo.getAssignedTo().getEmailId())){
			if(statusCode.equalsIgnoreCase(ComplaintsServiceRequestConstants.COMPLAINT_STATUS_BACKLINE) || statusCode.equalsIgnoreCase(ComplaintsServiceRequestConstants.COMPLAINT_STATUS_FOLLOWUP)){
				MailSenderTO exucutive=new MailSenderTO();
				exucutive.setTo(new String[]{servicerequestDo.getAssignedTo().getEmailId()});
				exucutive.setMailSubject("Complaint No:"+servicerequestDo.getServiceRequestNo()+" assigned to you");
				exucutive.setPlainMailBody(getMailBody(exucutive.getMailSubject()).toString());
				mailerList.add(exucutive);
			}

		}
	}



	private StringBuilder getMailBody(String subject) {
		StringBuilder plainMailBody=new StringBuilder();
		plainMailBody.append("<html><body> Dear Sir/madam");
		plainMailBody.append(subject);
		plainMailBody.append("<BR><BR> Regarads,<BR> FFCL IT support");
		return plainMailBody;
	}
	private StringBuilder getMailBodyForConsignemnt(String consgNumber,String complaintNumber) {
		StringBuilder plainMailBody=new StringBuilder();
		plainMailBody.append("<html><body> Dear Sir/madam");
		plainMailBody.append("<BR>Thank you for calling First flight. Your complaint for Consignment number <b>"+consgNumber+"</b> has been registered at our end . Please note the reference number :<b>"+complaintNumber+" </b> for your future correspondences on the same issue.");

		plainMailBody.append("<BR><BR> Thanks & Regarads,<BR> FFCL Customer Service");
		return plainMailBody;
	}
	
	private StringBuilder getMailBodyForServiceRequest(String complaintNumber) {
		StringBuilder plainMailBody=new StringBuilder();
		plainMailBody.append("<html><body> Dear Customer");
		plainMailBody.append("<BR>Thank you for calling First Flight. Hope our Customer Service Executive has attended to your query successfully.");
		plainMailBody.append("The Service Request number for your query is "+complaintNumber+".<br>");
		plainMailBody.append("Your feedback is valuable to us..Please send your feedback to ffcl@firstflight.net.");
		plainMailBody.append("<BR><BR>Thanks & Regarads,<BR> FFCL Customer Service");
		return plainMailBody;
	}


	private String getServiceRequestNumber(
			final ServiceRequestTO serviceRequestTO)
					throws CGSystemException, CGBusinessException {
		String complaintNumber=null;
		//Office code+DDMM+4 digit
		SequenceGeneratorConfigTO numberGenerator=new SequenceGeneratorConfigTO();
		numberGenerator.setProcessRequesting(serviceRequestTO.getLoginOfficeCode());
		//numberGenerator.setRequestingBranchCode(serviceRequestTO.getLoginOfficeCode());
		numberGenerator.setRequestingBranchCode(DateUtil.getDDMMStringFromDate(new Date()));
		numberGenerator.setRequestingBranchId(serviceRequestTO.getLoginOfficeId());
		numberGenerator.setLengthOfNumber(ComplaintsCommonConstants.SERVICE_REQUEST_NUMBER_LENGTH);
		numberGenerator.setSequenceRunningLength(ComplaintsCommonConstants.SERVICE_REQUEST_NUMBER_RUNNING_LENGTH);
		try {
			complaintNumber=complaintsCommonService.complaintProcessNumberGenerator(numberGenerator);
		} catch (Exception e) {
			LOGGER.error("ServiceRequestForServiceReqServiceImpl ::getServiceRequestNumber ::EXCEPTION",e);
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.PROBLEM_NUMBER_GENERATION, new String[]{ComplaintsCommonConstants.COMPLAINT_NO});
		}
		return complaintNumber;
	}





	@Override
	public String generateReferenceNumber(final String loginOfficeCode)
			throws CGBusinessException, CGSystemException {
		return complaintsCommonService.generateReferenceNumber(loginOfficeCode);
	}



	@Override
	public List<ProductTO> getProductList() throws CGBusinessException,
	CGSystemException {
		return complaintsCommonService.getProductList();
	}



	@Override
	public ServiceRequestTO searchServiceReq(ServiceRequestFilters serviceRequestFilters)
			throws CGBusinessException, CGSystemException {
		List<ServiceRequestDO> serviceRequestDOs= complaintsCommonDAO.getServiceRequestDetails(serviceRequestFilters);
		ServiceRequestTO serviceRequestTO= null;
		if(!StringUtil.isNull(serviceRequestDOs)){
			for(ServiceRequestDO serviceRequestDO :serviceRequestDOs ){
				serviceRequestTO = ComplaintsConverter.serviceReqDomainConverter(serviceRequestDO);
			}
		}
		return serviceRequestTO;
	}


	@Override
	public List<ServiceRequestQueryTypeTO> getServiceRequestQueryTypeByServiceType(String serviceType)
			throws CGBusinessException, CGSystemException {
		ServiceRequestQueryTypeTO serviceTypeTO=prepareServiceRequestQueryTypeByServiceType(serviceType);
		return complaintsCommonService.getServiceRequestQueryTypeDetails(serviceTypeTO);
	}
	private ServiceRequestQueryTypeTO prepareServiceRequestQueryTypeByServiceType(String serviceType)
			throws CGBusinessException, CGSystemException {
		ServiceRequestQueryTypeTO serviceTypeTO=null;
		if(!StringUtil.isStringEmpty(serviceType)){
			serviceTypeTO= new ServiceRequestQueryTypeTO();
			switch(serviceType){
			case ComplaintsCommonConstants.SERVICE_REQUEST_TYPE_FOR_SERVICE:
				serviceTypeTO.setQueryType(ComplaintsCommonConstants.SERVICE_REQUEST_QUERY_TYPE_SR);

				break;
			case ComplaintsCommonConstants.SERVICE_REQUEST_TYPE_FOR_BOOKING_REF:
				serviceTypeTO.setQueryType(ComplaintsCommonConstants.SERVICE_REQUEST_QUERY_TYPE_CN);
				break;

			case ComplaintsCommonConstants.SERVICE_REQUEST_TYPE_FOR_CONSG:
				serviceTypeTO.setQueryType(ComplaintsCommonConstants.SERVICE_REQUEST_QUERY_TYPE_CN);
				break;
			}
			if(!StringUtil.isStringEmpty(serviceTypeTO.getQueryType())){
				serviceTypeTO.setQueryType(serviceTypeTO.getQueryType()+FrameworkConstants.CHARACTER_COMMA+ComplaintsCommonConstants.SERVICE_REQUEST_QUERY_TYPE_BOTH_CN_AND_SR);
			}else{
				serviceTypeTO.setQueryType(ComplaintsCommonConstants.SERVICE_REQUEST_QUERY_TYPE_BOTH_CN_AND_SR);
			}
		}
		return serviceTypeTO;
	}

	@Override
	public List<ServiceRequestTO> searchServiceRequestDtls(ServiceRequestTO serviceRequestTO)
			throws CGBusinessException, CGSystemException {
		String bookingNumber=serviceRequestTO.getBookingNo();
		String serviceType=serviceRequestTO.getServiceType();
		ServiceRequestFilters serviceRequestFilters = prepareServiceRequestFilter(
				bookingNumber, serviceType);

		List<ServiceRequestTO> serviceRequestTOs= complaintsCommonService.searchServiceRequestDetails(serviceRequestFilters);
		if(CGCollectionUtils.isEmpty(serviceRequestTOs)){
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.SEARCH_DETAILS_DOES_NOT_EXIST);
		}
		return serviceRequestTOs;
	}



	private ServiceRequestFilters prepareServiceRequestFilter(
			String bookingNumber, String serviceType)
					throws CGBusinessException {
		ServiceRequestFilters serviceRequestFilters=new ServiceRequestFilters();
		if(!StringUtil.isStringEmpty(serviceType)){
			if(StringUtil.isStringEmpty(bookingNumber)){
				ExceptionUtil.prepareBusinessException(AdminErrorConstants.COMPLAINTS_INVALID_SEARCH_TYPE_NUMBER);
			}
			switch(serviceType){
			case ComplaintsCommonConstants.SERVICE_REQUEST_TYPE_FOR_SERVICE:
				serviceRequestFilters.setServiceRequestNumber(bookingNumber);
				break;
			case ComplaintsCommonConstants.SERVICE_REQUEST_TYPE_FOR_BOOKING_REF:
				serviceRequestFilters.setBookingReferenceNumber(bookingNumber);
				break;

			case ComplaintsCommonConstants.SERVICE_REQUEST_TYPE_FOR_CONSG:
				serviceRequestFilters.setConsignmentNo(bookingNumber);
				break;
			case ComplaintsCommonConstants.SERVICE_REQUEST_TYPE_FOR_CONTACT_NO:
				serviceRequestFilters.setCallerPhone(bookingNumber);
				break;
			}

		}else{
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.COMPLAINTS_INVALID_SEARCH_TYPE);
		}
		return serviceRequestFilters;
	}

	@Override
	public ServiceRequestValidationTO serviceEquiryValidation(ServiceRequestValidationTO validationTO)throws CGBusinessException,CGSystemException{
		String queryType=validationTO.getServiceQueryType();
		String result=null;
		if(!StringUtil.isStringEmpty(queryType)){
			populateProductInformation(validationTO);
			switch(queryType){
			case ComplaintsServiceRequestConstants.SERVICE_REQUEST_SERVICE_QUERY_TYPE_PAPERWORK:
				result=getPaperworkDetails(validationTO);
				break;
			case ComplaintsServiceRequestConstants.SERVICE_REQUEST_SERVICE_QUERY_TYPE_TARIFF_ENQUIRY:
				result=getRateTariffDetails(validationTO);
				break;
			case ComplaintsServiceRequestConstants.SERVICE_REQUEST_SERVICE_QUERY_TYPE_EMOTIONAL_BOND:
				result=getServiceCheckDetails(validationTO);
				break;
			case ComplaintsServiceRequestConstants.SERVICE_REQUEST_SERVICE_QUERY_TYPE_SERVICE_CHECK:
				result=getServiceCheckDetails(validationTO);
				break;
			}
		}else{
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.COMPLAINTS_QUERY_TYPE_EMPTY);
		}
		validationTO.setQueryResult(result);//FIXME
		return validationTO;
	}



	private void populateProductInformation(
			ServiceRequestValidationTO validationTO) throws CGSystemException,
			CGBusinessException {
		ProductTO productTO=null;
		if(!StringUtil.isEmptyInteger(validationTO.getProductId())){
			productTO= new ProductTO();
			productTO.setProductId(validationTO.getProductId());
		}else if(validationTO.getServiceQueryType().equalsIgnoreCase(ComplaintsServiceRequestConstants.SERVICE_REQUEST_SERVICE_QUERY_TYPE_EMOTIONAL_BOND)){
			productTO= new ProductTO();
			productTO.setConsgSeries(CommonConstants.PRODUCT_SERIES_EB);
		}
		if(productTO!=null){
			List<ProductTO> productList=complaintsCommonService.getProductByProductTO(productTO);
			if(!CGCollectionUtils.isEmpty(productList)){
				productTO=productList.get(0);
				validationTO.setProductTO(productTO);
				validationTO.setProductId(productTO.getProductId());
			}else{
				//throw Exception
			}
		}
	}

	public String getRateTariffDetails(ServiceRequestValidationTO validationTO) throws CGBusinessException, CGSystemException{
		String tariffResult=null;
		ProductTO product=validationTO.getProductTO();
		PincodeTO destPincodeTo=complaintsCommonService.getPincodeByIdOrCode(validationTO.getPincodeId(), null);
		populateCityTO(validationTO);
		boolean isValidInput = validateInputForRate(validationTO);
		if(isValidInput){
			RateCalculationInputTO rateInputTo=null;

			if(validationTO.getProductTO()!=null && !StringUtil.isStringEmpty(product.getProductCode())){
				rateInputTo=new RateCalculationInputTO();
				rateInputTo.setProductCode(product.getProductCode());
				rateInputTo.setOriginCityCode(validationTO.getCityTO().getCityCode());
				rateInputTo.setConsignmentType(validationTO.getConsignmentType());
				rateInputTo.setWeight(validationTO.getWeight());
				rateInputTo.setDestinationPincode(destPincodeTo.getPincode());
				rateInputTo.setRateType(RateCommonConstants.RATE_TYPE_CASH);
				rateInputTo.setCalculationRequestDate(DateUtil.todayDate());
				StringBuffer rateResult=new StringBuffer();
				if(product.getConsgSeries().equalsIgnoreCase(CommonConstants.PRODUCT_SERIES_PRIORITY)){

					for(String serviceOn:ComplaintsServiceRequestConstants.RATE_SERVICE_ON.split(CommonConstants.COMMA)){
						rateInputTo.setServiceOn(serviceOn);
						String result=getRateResult(product, rateInputTo, serviceOn);
						if(!StringUtil.isStringEmpty(result)){
							rateResult.append(result);
						}
					}
				}else{
					String result=getRateResult(product, rateInputTo, null);
					if(!StringUtil.isStringEmpty(result)){
						rateResult.append(result);
					}
				}
				tariffResult=rateResult.toString();

			}

		}else{
			//throw Exception
		}
		return tariffResult;

	}



	private void populateCityTO(ServiceRequestValidationTO validationTO)
			throws CGBusinessException, CGSystemException {
		CityTO cityTo= new CityTO();
		cityTo.setCityId(validationTO.getCityId());
		cityTo=complaintsCommonService.getCity(cityTo);
		validationTO.setCityTO(cityTo);
	}



	private String getRateResult(ProductTO product,
			RateCalculationInputTO rateInputTo, String serviceOn)
					throws CGBusinessException, CGSystemException {
		StringBuilder result=new StringBuilder();
		RateCalculationOutputTO rateOutputTO=complaintsCommonService.calculateRate(rateInputTo);
		if(!StringUtil.isNull(rateOutputTO)){
			result.append("Rate for ");
			result.append(product.getProductDesc());
			result.append("\n");
			if(!StringUtil.isStringEmpty(serviceOn)){
				result.append(" Service ON ");
				switch(serviceOn){
				case "A":
					result.append(" After 14 hours");
					break;
				case "B":
					result.append(" Before 14 hours");
					break;
				case "S":
					result.append(" Sunday ");
					break;

				}
			}
			result.append(" Rate Components :");
			/*BigDecimal finalRate = BigDecimal.valueOf(finalValue );
			finalRate = finalRate.setScale(2, BigDecimal.ROUND_HALF_UP);
			result.append(finalRate.doubleValue());*/
			if(rateOutputTO!=null && !CGCollectionUtils.isEmpty(rateOutputTO.getComponents())){
				Map<Integer,String> components=new TreeMap();
				Double serviceOrStTax=0.0d;
				/**
				 * serviceOrStTax= (Service Tax+HigherEDu+edu) or(state Tax + surcharge on ST)
				 */
				for(RateComponentTO rateTO:rateOutputTO.getComponents()){

					switch(rateTO.getRateComponentCode()){
					case RateCommonConstants.SLAB_RATE_CODE:
						components.put(1,"\n \t Freight Charges(Slab charge) :Rs"+(StringUtil.isNull(rateTO.getCalculatedValue())?0.0:rateTO.getCalculatedValue()));
						break;
					case RateCommonConstants.RATE_COMPONENT_TYPE_FUEL_SURCHARGE:
						components.put(2,"\n \t FSC Charges:Rs "+(StringUtil.isNull(rateTO.getCalculatedValue())?0.0:rateTO.getCalculatedValue()));
						break;
					case RateCommonConstants.RATE_COMPONENT_TYPE_RISK_SURCHARGE://check for null
						components.put(3,"\n \t Risk Surcharge: Rs"+(StringUtil.isNull(rateTO.getCalculatedValue())?0.0:rateTO.getCalculatedValue()));
						break;
					case RateCommonConstants.RATE_COMPONENT_TYPE_SERVICE_TAX:
						//components.put(4,"\n \t Service Tax: Rs "+(StringUtil.isNull(rateTO.getCalculatedValue())?0.0:rateTO.getCalculatedValue()));
						serviceOrStTax=serviceOrStTax+(StringUtil.isNull(rateTO.getCalculatedValue())?0.0:rateTO.getCalculatedValue());
						break;

					case RateCommonConstants.RATE_COMPONENT_TYPE_HIGHER_EDUCATION_CESS:
						//components.put(4,"\n \t Service Tax: Rs "+(StringUtil.isNull(rateTO.getCalculatedValue())?0.0:rateTO.getCalculatedValue()));
						serviceOrStTax=serviceOrStTax+(StringUtil.isNull(rateTO.getCalculatedValue())?0.0:rateTO.getCalculatedValue());
						break;
					case RateCommonConstants.RATE_COMPONENT_TYPE_EDUCATION_CESS:
						//components.put(4,"\n \t Service Tax: Rs "+(StringUtil.isNull(rateTO.getCalculatedValue())?0.0:rateTO.getCalculatedValue()));
						serviceOrStTax=serviceOrStTax+(StringUtil.isNull(rateTO.getCalculatedValue())?0.0:rateTO.getCalculatedValue());
						break;
					case RateCommonConstants.RATE_COMPONENT_TYPE_STATE_TAX:
						//	components.put(5,"\n \t State Tax:Rs "+(StringUtil.isNull(rateTO.getCalculatedValue())?0.0:rateTO.getCalculatedValue()));
						serviceOrStTax=serviceOrStTax+(StringUtil.isNull(rateTO.getCalculatedValue())?0.0:rateTO.getCalculatedValue());
						break;
					case RateCommonConstants.RATE_COMPONENT_TYPE_SURCHARGE_ON_ST:
						//components.put(4,"\n \t Service Tax: Rs "+(StringUtil.isNull(rateTO.getCalculatedValue())?0.0:rateTO.getCalculatedValue()));
						serviceOrStTax=serviceOrStTax+(StringUtil.isNull(rateTO.getCalculatedValue())?0.0:rateTO.getCalculatedValue());
						break;
					case RateCommonConstants.RATE_COMPONENT_TYPE_AIRPORT_HANDLING_CAHRGES:
						components.put(6,"\n \t Airport Handling Charges: Rs "+(StringUtil.isNull(rateTO.getCalculatedValue())?0.0:rateTO.getCalculatedValue()));
						break;
					case RateCommonConstants.RATE_COMPONENT_TYPE_PARCEL_HANDLING_CHARGES://check for  null
						components.put(7,"\n \t Parcel Handling Charges:Rs "+(StringUtil.isNull(rateTO.getCalculatedValue())?0.0:rateTO.getCalculatedValue()));
						break;
					case RateCommonConstants.RATE_COMPONENT_GRAND_TOTAL_INCLUDING_TAX:
						components.put(8, "\n \t Final rate (including Tax): Rs"+rateTO.getCalculatedValue());
						break;

					}//end of Switch
				}//END of For loop
				BigDecimal finalRate = BigDecimal.valueOf(serviceOrStTax );
				finalRate = finalRate.setScale(2, BigDecimal.ROUND_HALF_UP);
				components.put(4,"\n \t Service/State Tax: Rs "+finalRate.doubleValue());
				for(Map.Entry<Integer, String> entry:components.entrySet()){
					result.append(entry.getValue());
				}
			}
			
			result.append(" ]");
		}
		return result.toString();
	}




	public String getServiceCheckDetails(ServiceRequestValidationTO validationTO) throws CGBusinessException, CGSystemException{
		String tariffResult=null;
		boolean isValidInput = validateInputForServiceCheck(validationTO);
		if(isValidInput){
			StringBuilder result= new StringBuilder();
			isValidInput = complaintsCommonService.isPincodeServiceableByProductSeries(validationTO.getPincodeId(), validationTO.getProductId());
			if(isValidInput){
				List<OfficeTO> officeToList=complaintsCommonService.getBranchDtlsForPincodeServiceByPincode(validationTO.getPincodeId());
				if(!CGCollectionUtils.isEmpty(officeToList)){
					 result= new StringBuilder("Pincode serviced by Branch :\n");
					/*OfficeTO officeTo=officeToList.get(0);
					result.append("Office Code-Name : ");
					result.append(officeTo.getOfficeCode());
					result.append(" - ");
					result.append(officeTo.getOfficeName());
					if(officeToList.size()==1){

						if(!StringUtil.isStringEmpty(officeTo.getBuildingName())){
							result.append("Building Name : ");
							result.append(officeTo.getBuildingName());
						}
						if(!StringUtil.isStringEmpty(officeTo.getAddress1())){
							result.append("\nAddress:  ");
							result.append(officeTo.getAddress1());
						}
						if(!StringUtil.isStringEmpty(officeTo.getAddress2())){
							result.append(FrameworkConstants.CHARACTER_COMMA);
							result.append("\n"+officeTo.getAddress2());
						}
						if(!StringUtil.isStringEmpty(officeTo.getAddress2())){
							result.append(FrameworkConstants.CHARACTER_COMMA);
							result.append("\n"+officeTo.getAddress3());
						}
					}*/
					boolean isNumberRequired=true;
					if(officeToList.size()==1){
						isNumberRequired=false;
					}
					int counter=1;
					for(OfficeTO officeTo :officeToList){
						if(counter>1){
							result.append(" \n ");
						}
						if(isNumberRequired){
							result.append(counter+". ");
						}
						result.append(officeTo.getOfficeCode());
						result.append(" - ");
						result.append(officeTo.getOfficeName());
						if(!StringUtil.isStringEmpty(officeTo.getAddress1())){
							result.append("\nAddress:  ");
							result.append(officeTo.getAddress1());
							if(!StringUtil.isStringEmpty(officeTo.getAddress2())){
								result.append(FrameworkConstants.CHARACTER_COMMA);
								result.append("\n    ");
								result.append(officeTo.getAddress2());
							}
							if(!StringUtil.isStringEmpty(officeTo.getAddress2())){
								result.append(FrameworkConstants.CHARACTER_COMMA);
								result.append(officeTo.getAddress3());
							}
						}
						if(!StringUtil.isStringEmpty(officeTo.getPhone())){
							result.append("\nContact number :  ");
							result.append(officeTo.getPhone());

						}else if(!StringUtil.isStringEmpty(officeTo.getMobileNo())){
							result.append("\nContact number :  ");
							result.append(officeTo.getMobileNo());
						}
						++counter;
					}

				}else{
					result.append("this pincode is not serviceable by any brach");
				}
			}else{

				result.append("This product/pincode is not serviceable");

			}
			tariffResult=result.toString();
		}else{
			//throw Exception
		}

		return tariffResult;

	}

	public String getPaperworkDetails(ServiceRequestValidationTO validationTO) throws CGSystemException, CGBusinessException{
		String tariffResult=null;
		//product and pincode
		boolean isValidInputs = validateInputsForPaperwork(validationTO);

		if(isValidInputs){
			tariffResult=complaintsCommonService.getPaperWorksDetailsForComplaints(validationTO.getPincodeId(), validationTO.getConsignmentType());
		}else{
			//throw Exception
		}
		return tariffResult;

	}


	private boolean validateInputForRate(ServiceRequestValidationTO validationTO) {
		boolean isValidInput = validateInputForServiceCheck(validationTO);
		if(isValidInput){
			if(StringUtil.isStringEmpty(validationTO.getConsignmentType())){
				isValidInput=false;
			}else if(StringUtil.isEmptyDouble(validationTO.getWeight())){
				isValidInput=false;
			}
		}
		return isValidInput;
	}





	private boolean validateInputsForPaperwork(
			ServiceRequestValidationTO validationTO) {
		boolean isValidInputs=true;
		/*if(StringUtil.isEmptyInteger(validationTO.getProductId())){
			isValidInputs=false;
		}else*/ if(StringUtil.isEmptyInteger(validationTO.getPincodeId())){
			isValidInputs=false;
		}/*else if(StringUtil.isStringEmpty(validationTO.getConsignmentType())){
			isValidInputs=false;
		}*/
		return isValidInputs;
	}


	private boolean validateInputForServiceCheck(
			ServiceRequestValidationTO validationTO) {
		boolean isValidInput=true;
		if(StringUtil.isEmptyInteger(validationTO.getCityId())){
			isValidInput=false;
		}else if(StringUtil.isEmptyInteger(validationTO.getProductId())){
			isValidInput=false;
		}else	if(StringUtil.isEmptyInteger(validationTO.getPincodeId())){
			isValidInput=false;
		}
		return isValidInput;
	}

	@Override
	public SearchServiceRequestHeaderTO searchServiceRequestDtls(SearchServiceRequestHeaderTO serviceRequestTO)
			throws CGBusinessException, CGSystemException {
		String bookingNumber=serviceRequestTO.getSearchNumber();
		String serviceType=serviceRequestTO.getSearchType();
		ServiceRequestFilters serviceRequestFilters = prepareServiceRequestFilter(
				bookingNumber, serviceType);
		serviceRequestFilters.setIsProjectionRequired(true);
		List<ServiceRequestTO> serviceRequestTOs= complaintsCommonService.searchServiceRequestDetails(serviceRequestFilters);
		if(CGCollectionUtils.isEmpty(serviceRequestTOs)){
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.SEARCH_DETAILS_DOES_NOT_EXIST);
		}else{
			List<SearchServiceRequestGridTO> gridDtls= new ArrayList<>(serviceRequestTOs.size());
			for(ServiceRequestTO serviceRequest:serviceRequestTOs){
				SearchServiceRequestGridTO gridTo= new SearchServiceRequestGridTO();
				gridTo.setServiceRequestNo(serviceRequest.getServiceRequestNo());
				gridTo.setLinkedServiceReqNo(serviceRequest.getLinkedServiceReqNo());
				gridTo.setCallerPhone(serviceRequest.getCallerPhone());
				if(serviceRequest.getServiceType().equalsIgnoreCase(ComplaintsCommonConstants.SERVICE_REQUEST_TYPE_FOR_CONSG)){
					gridTo.setConsignmentNumber(serviceRequest.getBookingNo());
				}else if(serviceRequest.getServiceType().equalsIgnoreCase(ComplaintsCommonConstants.SERVICE_REQUEST_TYPE_FOR_BOOKING_REF)){
					gridTo.setBookingReferenceNo(serviceRequest.getBookingNo());
				}
				if(!StringUtil.isNull(serviceRequest.getServiceRequestStatusTO())){
					gridTo.setStatus(serviceRequest.getServiceRequestStatusTO().getStatusDescription());
				}
				gridDtls.add(gridTo);
			}
			Collections.sort(gridDtls);
			serviceRequestTO.setGridDtls(gridDtls);
		}

		return serviceRequestTO;
	}
	
	@Override
	public Boolean updateServiceTransferDetails(final ServiceRequestTO serviceRequestTO)throws CGBusinessException, CGSystemException {
		Boolean result=false;
		ServiceRequestDO servicerequestDo=null;
			
		if(serviceRequestTO!=null && !StringUtil.isEmptyInteger(serviceRequestTO.getServiceRequestId())){
			if(!StringUtil.isEmptyInteger(serviceRequestTO.getEmployeeId())){
				servicerequestDo= new ServiceRequestDO();
				servicerequestDo.setServiceRequestId(serviceRequestTO.getServiceRequestId());
				servicerequestDo.setServiceRequestNo(serviceRequestTO.getServiceRequestNo());
				EmployeeDO  assignedTo=new EmployeeDO();
				assignedTo.setEmployeeId(serviceRequestTO.getEmployeeId());
				assignedTo.setEmailId(serviceRequestTO.getEmpEmailId());
				assignedTo.setEmpPhone(serviceRequestTO.getEmpPhone());
				servicerequestDo.setAssignedTo(assignedTo);
				servicerequestDo.setForTransferFunctionality(true);
				result=complaintsCommonDAO.updateServiceRequest(servicerequestDo);
			if(result){
				sendMail(servicerequestDo);
			}
			}
		}else{
			//FIXME invalid
		}
		
		return result;
		
	}
}
