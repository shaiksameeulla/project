package com.ff.admin.complaints.converter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.business.ConsignorConsigneeTO;
import com.ff.complaints.ServiceRequestComplaintTypeTO;
import com.ff.complaints.ServiceRequestCustTypeTO;
import com.ff.complaints.ServiceRequestForConsignmentTO;
import com.ff.complaints.ServiceRequestForServiceTO;
import com.ff.complaints.ServiceRequestQueryTypeTO;
import com.ff.complaints.ServiceRequestStatusTO;
import com.ff.complaints.ServiceRequestTO;
import com.ff.complaints.ServiceRequestTransfertoTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.complaints.ServiceRelatedDetailsDO;
import com.ff.domain.complaints.ServiceRequestDO;
import com.ff.domain.complaints.ServiceRequestStatusDO;
import com.ff.domain.complaints.ServiceRequestTransfertoDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.domain.umc.EmployeeUserDO;
import com.ff.domain.umc.UserDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.rate.RateCalculationOutputTO;
import com.ff.to.rate.RateComponentTO;
import com.ff.umc.EmployeeUserTO;
import com.ff.umc.UserTO;

public  class ComplaintsConverter {
	private final static Logger LOGGER = LoggerFactory.getLogger(ComplaintsConverter.class);
	
	public static List<EmployeeUserTO> convertTosfromDomainObject(
			List<EmployeeUserDO> employeeUserDOs) throws CGSystemException,
			CGBusinessException {

		List<EmployeeUserTO> employeeUserTOs = null;
		EmployeeUserTO employeeUserTO= null;

		if(!StringUtil.isNull(employeeUserDOs)){
			employeeUserTOs = new ArrayList<>();
			for(EmployeeUserDO employeeUserDO : employeeUserDOs){
				employeeUserTO= new EmployeeUserTO();
				employeeUserTO.setEmpUserId(employeeUserDO.getEmpUserId());
				employeeUserTO.setUserId(employeeUserDO.getUserId());
				if (!StringUtil.isNull(employeeUserDO.getEmpDO())) {
					EmployeeTO employeeTO = new EmployeeTO();
					employeeTO = (EmployeeTO) CGObjectConverter
							.createToFromDomain(employeeUserDO.getEmpDO(), employeeTO);
					employeeUserTO.setEmpTO(employeeTO);
					
					
				}
				employeeUserTOs.add(employeeUserTO);
			}
		}
		return employeeUserTOs;
	}
	
	public static ConsignmentTO convertTosfromDomainObject(
			ConsignmentDO consignmentDO) throws CGSystemException,
			CGBusinessException {

		ConsignmentTO consignmentTO = null;

		if(!StringUtil.isNull(consignmentDO)){
			consignmentTO = new ConsignmentTO();
				if (!StringUtil.isNull(consignmentDO.getConsignor())) {
					ConsignorConsigneeTO consignorTO = new ConsignorConsigneeTO();
					consignorTO = (ConsignorConsigneeTO) CGObjectConverter
							.createToFromDomain(consignmentDO.getConsignor(), consignorTO);
					consignmentTO.setConsignorTO(consignorTO);
				}
				if (!StringUtil.isNull(consignmentDO.getConsignee())) {
					ConsignorConsigneeTO consigneeTO = new ConsignorConsigneeTO();
					consigneeTO = (ConsignorConsigneeTO) CGObjectConverter
							.createToFromDomain(consignmentDO.getConsignee(), consigneeTO);
					consignmentTO.setConsigneeTO(consigneeTO);
				}
			}
		return consignmentTO;
	}
	
	
public static ServiceRequestDO serviceReqDomainConverter(ServiceRequestForServiceTO serviceReqTO ){
		
		ServiceRequestDO serviceReqDO = new ServiceRequestDO();
	
		if(!StringUtil.isEmptyInteger(serviceReqTO.getServiceRequestId())){
			serviceReqDO.setServiceRequestId(serviceReqTO.getServiceRequestId());
		}
		if(!StringUtil.isStringEmpty(serviceReqTO.getReferenceNo())){
			serviceReqDO.setServiceRequestNo(serviceReqTO.getReferenceNo());
		}
		/*if(!StringUtil.isStringEmpty(serviceReqTO.getServiceRequestType())){
			serviceReqDO.setServiceRequestType(serviceReqTO.getServiceRequestType());
		}*/
		/*if(!StringUtil.isStringEmpty(serviceReqTO.getSearchType())){
			serviceReqDO.setServiceRequestType(serviceReqTO.getSearchType());
		}*/
		
		// private UserDO userDO;
		
		if(!StringUtil.isEmptyInteger(serviceReqTO.getLogginUserId())){
			//serviceReqDO.setCreatedBy(serviceReqTO.getLogginUserId());
			/*serviceReqDO.setUpdateBy(serviceReqTO.getLogginUserId());*/
			
			UserDO userDO = new UserDO();
			userDO.setUserId(serviceReqTO.getLogginUserId());
			/*serviceReqDO.setUserDO(userDO);*/
		}
		
		if(!StringUtil.isStringEmpty(serviceReqTO.getReferenceNo())){
			serviceReqDO.setReferenceNo(serviceReqTO.getReferenceNo());
		}
		/*if(!StringUtil.isNull(serviceReqTO.getConsignmentTO())){
			ConsignmentDO consgDO = new ConsignmentDO();
			consgDO.setConsgId(serviceReqTO.getConsignmentTO().getConsgId());
			serviceReqDO.setConsignmentDO(consgDO);
		}*/
		if(!StringUtil.isStringEmpty(serviceReqTO.getCallerName())){
			serviceReqDO.setCallerName(serviceReqTO.getCallerName());
		}
		if(!StringUtil.isStringEmpty(serviceReqTO.getCallerPhone())){
			serviceReqDO.setCallerPhone(serviceReqTO.getCallerPhone());
		}
		if(!StringUtil.isStringEmpty(serviceReqTO.getCallerEmail())){
			serviceReqDO.setCallerEmail(serviceReqTO.getCallerEmail());
		}
		/*if(!StringUtil.isStringEmpty(serviceReqTO.getCustCategoryType())){
			 custTypeCode= serviceReqTO.getCustCategoryType();	
		}*/
		
		
		/*if(!StringUtil.isStringEmpty(serviceReqTO.getCustomerType())){
			serviceReqDO.setCustomerType(serviceReqTO.getCustomerType());
		}
		if(!StringUtil.isStringEmpty(custTypeCode)){
			serviceReqDO.setCustomerType(custTypeCode);
		}
		if(!StringUtil.isStringEmpty(serviceReqTO.getServiceRelated())){
			serviceReqDO.setServiceRelated(serviceReqTO.getServiceRelated());	
		}
		if(!StringUtil.isStringEmpty(serviceReqTO.getSource())){
			serviceReqDO.setSource(serviceReqTO.getSource());
		}
		
		if(!StringUtil.isStringEmpty(serviceReqTO.getStatus())){
			serviceReqDO.setStatus(serviceReqTO.getStatus());
		}*/
		if(!StringUtil.isStringEmpty(serviceReqTO.getRemark())){
			serviceReqDO.setRemark(serviceReqTO.getRemark());
		}
		if(!StringUtil.isStringEmpty(serviceReqTO.getSmsToConsignee())){
			serviceReqDO.setSmsToConsignee(serviceReqTO.getSmsToConsignee());
		}
		if(!StringUtil.isStringEmpty(serviceReqTO.getSmsToConsignor())){
			serviceReqDO.setSmsToConsignor(serviceReqTO.getSmsToConsignor());
		}
		if(!StringUtil.isStringEmpty(serviceReqTO.getEmailToCaller())){
			serviceReqDO.setEmailToCaller(serviceReqTO.getEmailToCaller());
		}
		
		if(!StringUtil.isEmptyInteger(serviceReqTO.getLoginOfficeId())){
			OfficeDO officeDO = new OfficeDO();
			officeDO.setOfficeId(serviceReqTO.getLoginOfficeId());
			/*serviceReqDO.setComplaintOriginOfficeDO(officeDO);*/
		}
		/*if(!StringUtil.isStringEmpty(serviceReqTO.getCustCategory())){
			serviceReqDO.setCustCategory(serviceReqTO.getCustCategory());	
		}*/
		if(!StringUtil.isStringEmpty(serviceReqTO.getRemark())){
			serviceReqDO.setRemark(serviceReqTO.getRemark());
		}
	/*	if(!StringUtil.isStringEmpty(serviceReqTO.getResult())){
			serviceReqDO.setResult(serviceReqTO.getResult());
		}*/
		
		/// common user details.
		Date date=DateUtil.stringToDDMMYYYYFormat(serviceReqTO.getDate());
		if(!StringUtil.isNull(date)){
			serviceReqDO.setCreatedDate(date);
			serviceReqDO.setUpdateDate(date);
		}
		
		/*if(!StringUtil.isStringEmpty(serviceReqTO.getCreatedBy())){
			
		}*/
		
	/*	serviceReqDO.setCreatedByEmpDO(createdByEmpDO);
		serviceReqDO.setUpdateBy(updateBy);*/
		/*private Integer updateBy;
		private Date updateDate ;
		private Integer createdByEmpDO ;
		private Date createdDate;*/
		
		
		
		
		// Service Related Details
		
		ServiceRelatedDetailsDO serviceRelatedDetailsDO = new ServiceRelatedDetailsDO();
		
		if(!StringUtil.isEmptyInteger(serviceReqTO.getServiceRequestId())){
			serviceRelatedDetailsDO.setServiceRelatedId(serviceReqTO.getServiceRequestId());
		}
		/*if(!StringUtil.isEmptyDouble(serviceReqTO.getWeight())){
			serviceRelatedDetailsDO.setWeight(serviceReqTO.getWeight());	
		}*/
		
		if(!StringUtil.isStringEmpty(serviceReqTO.getEmpEmailId())){
			serviceRelatedDetailsDO.setEmpEmail(serviceReqTO.getEmpEmailId());	
		}
		
		if(!StringUtil.isStringEmpty(serviceReqTO.getEmpPhone())){
			serviceRelatedDetailsDO.setEmpPhone(serviceReqTO.getEmpPhone());
		}
		
		if(!StringUtil.isEmptyInteger(serviceReqTO.getProductId())){
			ProductDO productDO = new ProductDO();
			productDO.setProductId(serviceReqTO.getProductId());
			serviceRelatedDetailsDO.setProductDO(productDO);
		}
		
		if(!StringUtil.isEmptyInteger(serviceReqTO.getConsgTypeId())){
			ConsignmentTypeDO consignmentTypeDO = new ConsignmentTypeDO();
			consignmentTypeDO.setConsignmentId(serviceReqTO.getConsgTypeId());
			serviceRelatedDetailsDO.setConsgTypeDO(consignmentTypeDO);
		}
		/*if(!StringUtil.isEmptyInteger(serviceReqTO.getPaperwork())){
			CNPaperWorksDO cnPaperWrkDO = new CNPaperWorksDO();
			cnPaperWrkDO.setCnPaperWorkId(serviceReqTO.getPaperwork());
			serviceRelatedDetailsDO.setPaperworkDO(cnPaperWrkDO);
		}*/
		
		
		if(!StringUtil.isEmptyInteger(serviceReqTO.getPincodeId())){
			PincodeDO pincodeDO = new PincodeDO();
			pincodeDO.setPincodeId(serviceReqTO.getPincodeId());
			serviceRelatedDetailsDO.setPincodeDO(pincodeDO);
		}
		
		
		if(!StringUtil.isEmptyInteger(serviceReqTO.getOriginCityId())){
			CityDO cityDO = new CityDO();
			cityDO.setCityId(serviceReqTO.getOriginCityId());
			serviceRelatedDetailsDO.setCityDO(cityDO);
		}
		/*serviceReqDO.setServiceReqDtlsDO(serviceRelatedDetailsDO);*/
		
		return serviceReqDO;
		
	}

 
public static ServiceRequestDO serviceReqDomainConverter(ServiceRequestForConsignmentTO serviceReqTO ){
	
	ServiceRequestDO serviceReqDO = new ServiceRequestDO();

	if(!StringUtil.isEmptyInteger(serviceReqTO.getServiceRequestId())){
		serviceReqDO.setServiceRequestId(serviceReqTO.getServiceRequestId());
	}
	if(!StringUtil.isStringEmpty(serviceReqTO.getServiceRequestNo())){
		serviceReqDO.setServiceRequestNo(serviceReqTO.getServiceRequestNo());
	}
	/*serviceReqDO.setServiceRequestType(serviceReqTO.getServiceRequestType());*/
	if(!StringUtil.isStringEmpty(serviceReqTO.getReferenceNo())){
		serviceReqDO.setReferenceNo(serviceReqTO.getReferenceNo());
	}
	
	if(!StringUtil.isStringEmpty(serviceReqTO.getEmployeeDtls())){
		String employeeDtls=serviceReqTO.getEmployeeDtls().split("~")[0];
		Integer empId= Integer.valueOf(employeeDtls);
		if(!StringUtil.isEmptyInteger(empId)){
			EmployeeDO empDO = new EmployeeDO();
			empDO.setEmployeeId(empId);
			/*serviceReqDO.setEmployeeDO(empDO);*/
		}	
	}
	
	if(!StringUtil.isStringEmpty(serviceReqTO.getCallerName())){
		serviceReqDO.setCallerName(serviceReqTO.getCallerName());
	}
	if(!StringUtil.isStringEmpty(serviceReqTO.getCallerPhone())){
		serviceReqDO.setCallerPhone(serviceReqTO.getCallerPhone());
	}
	if(!StringUtil.isStringEmpty(serviceReqTO.getCallerEmail())){
		serviceReqDO.setCallerEmail(serviceReqTO.getCallerEmail());
	}
	
	/*if(!StringUtil.isStringEmpty(serviceReqTO.getCustomerType())){
		serviceReqDO.setCustomerType(serviceReqTO.getCustomerType());
	}
	if(!StringUtil.isStringEmpty(serviceReqTO.getServiceRelated())){
		serviceReqDO.setServiceRelated(serviceReqTO.getServiceRelated());
	}
	if(!StringUtil.isStringEmpty(serviceReqTO.getSource())){
		serviceReqDO.setSource(serviceReqTO.getSource());
	}
	
	if(!StringUtil.isStringEmpty(serviceReqTO.getStatus())){
		serviceReqDO.setStatus(serviceReqTO.getStatus());
	}*/
	if(!StringUtil.isStringEmpty(serviceReqTO.getRemark())){
		serviceReqDO.setRemark(serviceReqTO.getRemark());
	}
	if(!StringUtil.isStringEmpty(serviceReqTO.getSmsToConsignee())){
		serviceReqDO.setSmsToConsignee(serviceReqTO.getSmsToConsignee());
	}
	if(!StringUtil.isStringEmpty(serviceReqTO.getSmsToConsignor())){
		serviceReqDO.setSmsToConsignor(serviceReqTO.getSmsToConsignor());
	}
	if(!StringUtil.isStringEmpty(serviceReqTO.getEmailToCaller())){
		serviceReqDO.setEmailToCaller(serviceReqTO.getEmailToCaller());
	}
	
	if(!StringUtil.isEmptyInteger(serviceReqTO.getLoginOfficeId())){
		OfficeDO officeDO = new OfficeDO();
		officeDO.setOfficeId(serviceReqTO.getLoginOfficeId());
		/*serviceReqDO.setComplaintOriginOfficeDO(officeDO);*/
	}
	/*if(!StringUtil.isStringEmpty(serviceReqTO.getCustCategory())){
		serviceReqDO.setCustCategory(serviceReqTO.getCustCategory());	
	}*/
	
	// Service Related Details
	
	ServiceRelatedDetailsDO serviceRelatedDetailsDO = new ServiceRelatedDetailsDO();
	
	if(!StringUtil.isEmptyInteger(serviceReqTO.getServiceRequestId())){
		serviceRelatedDetailsDO.setServiceRelatedId(serviceReqTO.getServiceRequestId());
	}
	/*if(!StringUtil.isEmptyDouble(serviceReqTO.getWeight())){
		serviceRelatedDetailsDO.setWeight(serviceReqTO.getWeight());	
	}*/
	
	if(!StringUtil.isStringEmpty(serviceReqTO.getEmpEmailId())){
		serviceRelatedDetailsDO.setEmpEmail(serviceReqTO.getEmpEmailId());	
	}
	
	if(!StringUtil.isStringEmpty(serviceReqTO.getEmpPhone())){
		serviceRelatedDetailsDO.setEmpPhone(serviceReqTO.getEmpPhone());
	}
	
	/*private CNPaperWorksDO paperworkDO ;
	if(!StringUtil.isNull(serviceReqTO.getC
	CNPaperWorksDO cnPaperWrkDO = new CNPaperWorksDO();*/
	
	/*serviceReqDO.setServiceReqDtlsDO(serviceRelatedDetailsDO);*/
	
	return serviceReqDO;
	
}

// searching 
	public static ServiceRequestTO serviceReqDomainConverter(ServiceRequestDO serReqDO )throws CGBusinessException,
	CGSystemException   {
		ServiceRequestTO requestTO = new ServiceRequestTO();
		
		if(!StringUtil.isEmptyInteger(serReqDO.getServiceRequestId())){
			requestTO.setServiceRequestId(serReqDO.getServiceRequestId());
		}
		
		if(!StringUtil.isStringEmpty(serReqDO.getServiceRequestNo())){
			requestTO.setServiceRequestNo(serReqDO.getServiceRequestNo());
		}
		/*if(!StringUtil.isStringEmpty(serReqDO.getServiceRequestType())){
			requestTO.setServiceRequestType(serReqDO.getServiceRequestType());
		}
		ConsignmentDO consignmentDO= serReqDO.getConsignmentDO();*/
		/*if(!StringUtil.isNull(consignmentDO)){
			ConsignmentTO consig = ComplaintsConverter.setUpConsignmentDetails(consignmentDO);
			if(!StringUtil.isNull(consig)){
				requestTO.setConsignmentTO(consig);
			}
		}*/
		if(!StringUtil.isStringEmpty(serReqDO.getCallerName())){
			requestTO.setCallerName(serReqDO.getCallerName());
		}
		if(!StringUtil.isStringEmpty(serReqDO.getCallerPhone())){
			requestTO.setCallerPhone(serReqDO.getCallerPhone());
		}
		
		if(!StringUtil.isStringEmpty(serReqDO.getCallerEmail())){
			requestTO.setCallerEmail(serReqDO.getCallerEmail());
		}
		/*if(!StringUtil.isStringEmpty(serReqDO.getStatus())){
			requestTO.setStatus(serReqDO.getStatus());
		}*/
		if(!StringUtil.isStringEmpty(serReqDO.getRemark())){
			requestTO.setRemark(serReqDO.getRemark());
		}
		/*if(!StringUtil.isStringEmpty(serReqDO.getResult())){
			requestTO.setResult(serReqDO.getResult());
		}*/
		if(!StringUtil.isStringEmpty(serReqDO.getSmsToConsignor())){
			requestTO.setSmsToConsignor(serReqDO.getSmsToConsignor());
		}
		if(!StringUtil.isStringEmpty(serReqDO.getSmsToConsignee())){
			requestTO.setSmsToConsignee(serReqDO.getSmsToConsignee());
		}
		
		/*OfficeDO officeDO= serReqDO.getComplaintOriginOfficeDO();*/
		/*if(!StringUtil.isNull(officeDO)){
			OfficeTO officeTO = ComplaintsConverter.setUpOfficeDetails(officeDO);
			if(!StringUtil.isNull(officeTO)){
				requestTO.setComplaintOriginOfficeTO(officeTO);
			}
		}
		if(!StringUtil.isStringEmpty(serReqDO.getCustCategory())){
			requestTO.setCustomerType(serReqDO.getCustCategory());
		}
		
		if(!StringUtil.isStringEmpty(serReqDO.getSource())){
			requestTO.setSource(serReqDO.getSource());
		}
		
		
		// getting service related details
		ServiceRelatedDetailsDO serviceReqDtlsDO = serReqDO.getServiceReqDtlsDO();*/
	/*	if(!StringUtil.isNull(serviceReqDtlsDO)){
			ComplaintsConverter.convertDoToTO(requestTO, serviceReqDtlsDO);
		}*/
		
		
		return requestTO;
	}
	// searching service related
	public static ConsignmentTO setUpConsignmentDetails(
			ConsignmentDO consignmentDO) throws CGBusinessException,
			CGSystemException {
		ConsignmentTO consig = new ConsignmentTO();
		CGObjectConverter.createToFromDomain(consignmentDO, consig);
		return consig;
	}
	
	public static OfficeTO setUpOfficeDetails(
			OfficeDO officeDO) throws CGBusinessException,
			CGSystemException {
		OfficeTO officeTO = new OfficeTO();
		CGObjectConverter.createToFromDomain(officeDO, officeTO);
		return officeTO;
	}
	
	public static ServiceRequestTO ConvertTOFromDO(ServiceRequestDO serviceRequestDO) throws CGBusinessException{
		LOGGER.debug("ComplaintsConverter::ConvertTOFromDO::START----->");
		ServiceRequestTO serviceRequestTO = new ServiceRequestTO();
		
		CGObjectConverter.createToFromDomain(serviceRequestDO, serviceRequestTO);
		
		ServiceRequestCustTypeTO serviceRequestCustTypeTO =null;
		if(!StringUtil.isNull(serviceRequestDO.getServiceRequestCustTypeDO())){
			serviceRequestCustTypeTO = new ServiceRequestCustTypeTO();
			CGObjectConverter.createToFromDomain(serviceRequestDO.getServiceRequestCustTypeDO(),serviceRequestCustTypeTO);
			serviceRequestTO.setServiceRequestCustTypeTO(serviceRequestCustTypeTO);
		}
		
		ServiceRequestQueryTypeTO serviceRequestQueryTypeTO = null;
		if(!StringUtil.isNull(serviceRequestDO.getServiceRequestQueryTypeDO())){
			serviceRequestQueryTypeTO = new ServiceRequestQueryTypeTO();
			CGObjectConverter.createToFromDomain(serviceRequestDO.getServiceRequestQueryTypeDO(),serviceRequestQueryTypeTO);
			serviceRequestTO.setServiceRequestQueryTypeTO(serviceRequestQueryTypeTO);
		}
		
		ServiceRequestComplaintTypeTO serviceRequestComplaintTypeTO = null;
		if(!StringUtil.isNull(serviceRequestDO.getServiceRequestComplaintTypeDO())){
			serviceRequestComplaintTypeTO = new ServiceRequestComplaintTypeTO();
			CGObjectConverter.createToFromDomain(serviceRequestDO.getServiceRequestComplaintTypeDO(),serviceRequestComplaintTypeTO);
			serviceRequestTO.setServiceRequestComplaintTypeTO(serviceRequestComplaintTypeTO);
		}
		
		ServiceRequestStatusTO serviceRequestStatusTO = null;
		if(!StringUtil.isNull(serviceRequestDO.getServiceRequestStatusDO())){
			serviceRequestStatusTO = new ServiceRequestStatusTO();
			CGObjectConverter.createToFromDomain(serviceRequestDO.getServiceRequestStatusDO(),serviceRequestStatusTO);
			serviceRequestTO.setServiceRequestStatusTO(serviceRequestStatusTO);
		}
		
		UserTO createdByUserTO =  null;
		if(!StringUtil.isNull(serviceRequestDO.getCreatedByUserDO())){
			createdByUserTO =  new UserTO();
			CGObjectConverter.createToFromDomain(serviceRequestDO.getCreatedByUserDO(),createdByUserTO);
			serviceRequestTO.setCreatedByUserTO(createdByUserTO);
		}
		
		if(serviceRequestDO.getCreatedDate()!=null){
			serviceRequestTO.setCreatedDateStr(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(serviceRequestDO.getCreatedDate()));
		}
		
		UserTO updatedByUserTO = null;
		if(!StringUtil.isNull(serviceRequestDO.getUpdateByUserDO())){
			updatedByUserTO = new UserTO();
			CGObjectConverter.createToFromDomain(serviceRequestDO.getUpdateByUserDO(),updatedByUserTO);
			serviceRequestTO.setUpdateByUserTO(updatedByUserTO);
		}
		
		if(serviceRequestDO.getUpdateDate()!=null){
			serviceRequestTO.setUpdateDateStr(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(serviceRequestDO.getUpdateDate()));
		}
		
		CityTO cityTO = null;
		if(!StringUtil.isNull(serviceRequestDO.getOriginCity())){
			cityTO = new CityTO();
			CGObjectConverter.createToFromDomain(serviceRequestDO.getOriginCity(),cityTO);
			serviceRequestTO.setOriginCityTO(cityTO);
		}
		
		ProductTO productTO =null;
		if(!StringUtil.isNull(serviceRequestDO.getProductType())){
			productTO = new ProductTO();
			CGObjectConverter.createToFromDomain(serviceRequestDO.getProductType(),productTO);
			serviceRequestTO.setProductTypeTO(productTO);
		}
		
		PincodeTO pincodeTO =null;
		if(!StringUtil.isNull(serviceRequestDO.getDestPincode())){
			pincodeTO = new PincodeTO();
			CGObjectConverter.createToFromDomain(serviceRequestDO.getDestPincode(),pincodeTO);
			serviceRequestTO.setDestPincodeTO(pincodeTO);
		}
		
		EmployeeTO employeeTO = null;
		if(!StringUtil.isNull(serviceRequestDO.getAssignedTo())){
			employeeTO = new EmployeeTO();
			CGObjectConverter.createToFromDomain(serviceRequestDO.getAssignedTo(),employeeTO);
			serviceRequestTO.setAssignedToEmpTO(employeeTO);
		}
		
		ServiceRequestTransfertoTO serviceRequestTransfertoTO =null;
		if(!StringUtil.isNull(serviceRequestDO.getServiceRequestTransferToDO())){
			serviceRequestTransfertoTO = new ServiceRequestTransfertoTO();
			CGObjectConverter.createToFromDomain(serviceRequestDO.getServiceRequestTransferToDO(),serviceRequestTransfertoTO);
			serviceRequestTO.setServiceRequestTranferTO(serviceRequestTransfertoTO);
		}
		
		LOGGER.debug("ComplaintsConverter::ConvertTOFromDO::END----->");
		return serviceRequestTO;
		
	}
	 
	public static ServiceRequestDO ConvertDOFromTO(ServiceRequestTO serviceRequestTO,ServiceRequestDO serviceRequestDO) throws CGBusinessException{
		LOGGER.debug("ComplaintsConverter::ConvertDOFromTO::START----->");
		
		
		ServiceRequestStatusDO serviceRequestStatusDO = new ServiceRequestStatusDO();
		if(!StringUtil.isNull(serviceRequestTO.getServiceRequestStatusTO())){
			CGObjectConverter.createDomainFromTo(serviceRequestTO.getServiceRequestStatusTO(), serviceRequestStatusDO);
			serviceRequestDO.setServiceRequestStatusDO(serviceRequestStatusDO);
		}
		
		UserDO updatedByUserDO = new UserDO();
		if(!StringUtil.isNull(serviceRequestTO.getUpdateByUserTO())){
			CGObjectConverter.createDomainFromTo(serviceRequestTO.getUpdateByUserTO(), updatedByUserDO);
			serviceRequestDO.setUpdateByUserDO(updatedByUserDO);
		}
		serviceRequestDO.setUpdateDate(Calendar.getInstance().getTime());
		
		ServiceRequestTransfertoDO serviceRequestTransfertoDO = new ServiceRequestTransfertoDO();
		if(!StringUtil.isNull(serviceRequestTO.getServiceRequestTranferTO())){
			CGObjectConverter.createDomainFromTo(serviceRequestTO.getServiceRequestTranferTO(), serviceRequestTransfertoDO);
			serviceRequestDO.setServiceRequestTransferToDO(serviceRequestTransfertoDO);
		}
		
		if(!StringUtil.isStringEmpty(serviceRequestTO.getRemark())){
			serviceRequestDO.setRemark(serviceRequestTO.getRemark());
		}
		if (!StringUtil.isStringEmpty(serviceRequestTO.getCallerEmail())){
			serviceRequestDO.setCallerEmail(serviceRequestTO.getCallerEmail());
		}
		
		LOGGER.debug("ComplaintsConverter::ConvertDOFromTO::END----->");
		return serviceRequestDO;
		
	}
	public static Double getFinalRateComponent(RateCalculationOutputTO outputTo) {
		Double grandTotalIncludingTax=null;
		if(outputTo!=null && !CGCollectionUtils.isEmpty(outputTo.getComponents())){
			for(RateComponentTO rateTO:outputTo.getComponents()){
				if(rateTO.getRateComponentCode().equalsIgnoreCase(RateCommonConstants.RATE_COMPONENT_GRAND_TOTAL_INCLUDING_TAX)){
					grandTotalIncludingTax=rateTO.getCalculatedValue();
					break;
				}
			}
		}
		return grandTotalIncludingTax;
	}
}
