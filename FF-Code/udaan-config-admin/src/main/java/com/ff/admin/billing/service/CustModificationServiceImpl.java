package com.ff.admin.billing.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.billing.constants.BillingConstants;
import com.ff.admin.billing.dao.CnModificationCommonDAO;
import com.ff.admin.billing.dao.CustModificationDAO;
import com.ff.business.CustomerTO;
import com.ff.business.CustomerTypeTO;
import com.ff.domain.business.CustomerBillingDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.geography.RegionDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.to.billing.CustModificationAliasTO;
import com.ff.to.billing.CustModificationTO;
import com.ff.universe.constant.UniversalErrorConstants;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.serviceOffering.service.ServiceOfferingCommonService;

public class CustModificationServiceImpl implements CustModificationService {

	private final static Logger LOGGER = LoggerFactory.getLogger(CustModificationServiceImpl.class);
	
	private CustModificationDAO custModificationDAO;
	private CnModificationCommonDAO cnModificationCommonDAO;
	private CnModificationCommonService cnModificationCommonService;
	private GeographyCommonService geographyCommonService;
	private ServiceOfferingCommonService serviceOfferingCommonService;

	public void setCustModificationDAO(CustModificationDAO custModificationDAO) {
		this.custModificationDAO = custModificationDAO;
	}
	
	public void setCnModificationCommonDAO(
			CnModificationCommonDAO cnModificationCommonDAO) {
		this.cnModificationCommonDAO = cnModificationCommonDAO;
	}
		
	public void setCnModificationCommonService(
			CnModificationCommonService cnModificationCommonService) {
		this.cnModificationCommonService = cnModificationCommonService;
	}

	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}
	
	public void setServiceOfferingCommonService(
			ServiceOfferingCommonService serviceOfferingCommonService) {
		this.serviceOfferingCommonService = serviceOfferingCommonService;
	}

	public CustModificationTO getConsignmentDetails(String consgNo)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("CustModificationServiceImpl: getConsignmentDetails(): START");
		CustModificationAliasTO custModificationAliasTO = cnModificationCommonService.validateConsignment2Modify(consgNo);
		
		ConsignmentDO consgDo = cnModificationCommonDAO.getConsignmentDetails(consgNo);
		CustModificationTO custModificationTO = setCustomerModificationTO(custModificationAliasTO, consgDo);
		
		LOGGER.debug("CustModificationServiceImpl: getConsgAndBookDetails(): END");
		
		return custModificationTO;
	}
	
	private CustModificationTO setCustomerModificationTO(CustModificationAliasTO custModificationAliasTO, ConsignmentDO consgDo) 
			throws CGBusinessException, CGSystemException {
		String consgNo = consgDo.getConsgNo();
		
		CustModificationTO custModificationTO = new CustModificationTO();
		custModificationTO.setCongNo(consgNo);
		custModificationTO.setExBookingDate(DateUtil.getDDMMYYYYDateToString(custModificationAliasTO.getBkgDate()));
		CustomerTO bkgCustTO = new CustomerTO();
		if (!StringUtil.isNull(custModificationAliasTO.getShipToCode())) {			
			bkgCustTO.setShippedToCode(custModificationAliasTO.getShipToCode());			
		}
		bkgCustTO.setCustomerId(consgDo.getCustomer());
		bkgCustTO.setCustomerCode(custModificationAliasTO.getCustCode());
		if (!StringUtil.isNull(custModificationAliasTO.getCustName())) {
			bkgCustTO.setBusinessName(custModificationAliasTO.getCustName());
		}
		if(!StringUtil.isNull(custModificationAliasTO.getCustTypeCode())){
			CustomerTypeTO customerTypeTO = new CustomerTypeTO();
			customerTypeTO.setCustomerTypeCode(custModificationAliasTO.getCustTypeCode());
			bkgCustTO.setCustomerTypeTO(customerTypeTO);
		}
		
		custModificationTO.setBkgCustTO(bkgCustTO);
		custModificationTO.setNewCustTO(bkgCustTO);
		if(!StringUtil.isEmptyDouble(custModificationAliasTO.getTotalConsignmentWeight())){
			String finalCnWeight = custModificationAliasTO.getTotalConsignmentWeight().toString();
			int count = finalCnWeight.indexOf('.');
			String wtkgStr = finalCnWeight.substring(0, count);
			String wtgmStr = finalCnWeight.substring(count + 1);
			custModificationTO.setBkgFinalWeight(custModificationAliasTO.getTotalConsignmentWeight());
			custModificationTO.setBkgWtKg(wtkgStr);
			custModificationTO.setBkgWtGm(wtgmStr);
		}
		ConsignmentTypeDO consignmentTypeDO = null;
		consignmentTypeDO = consgDo.getConsgType();
		if(!StringUtil.isNull(consignmentTypeDO)){
			custModificationTO.setBkgCnType(consignmentTypeDO.getConsignmentName());
			ConsignmentTypeTO cnTypeTO = new ConsignmentTypeTO();
			CGObjectConverter.createToFromDomain(consignmentTypeDO, cnTypeTO);
			custModificationTO.setCnTypeTO(cnTypeTO);
		}
		
		if(!StringUtil.isEmptyDouble(consgDo.getDeclaredValue())){
			custModificationTO.setBkgDeclaredValue(consgDo.getDeclaredValue());
		}
		if(!StringUtil.isEmptyInteger(custModificationAliasTO.getOfficeId())){
			custModificationTO.setCnOrgOffice(custModificationAliasTO.getOfficeId());
		}
		if(!StringUtil.isEmptyInteger(custModificationAliasTO.getCityId())){
			custModificationTO.setCityId(custModificationAliasTO.getCityId());
		}
		if(StringUtils.equalsIgnoreCase(consgDo.getIsExcessConsg(), CommonConstants.YES)){
			custModificationTO.setIsExcessConsg(consgDo.getIsExcessConsg());
			String regionCode = consgNo.substring(0, 1);
			List<RegionTO> regionTOList = getRegionNameByRegionCode(regionCode.trim());
			for (RegionTO bkgRegion : regionTOList) {
				custModificationTO.setBkgRegionTO(bkgRegion);
			}
		}
		if(StringUtils.equalsIgnoreCase(custModificationAliasTO.getIsCnTypeEditable(), CommonConstants.YES)){
			if(!StringUtil.isNull(consgDo.getConsgType()) 
					&& StringUtils.equalsIgnoreCase(consgDo.getConsgType().getConsignmentCode(), CommonConstants.CONSIGNMENT_TYPE_DOCUMENT_CODE)){
				custModificationTO.setIsDecValEditable(CommonConstants.NO);
			}
		}else{
			custModificationTO.setIsDecValEditable(CommonConstants.NO);
		}
		custModificationTO.setIsCustEditable(custModificationAliasTO.getIsCustEditable());
		custModificationTO.setIsWeightEditable(custModificationAliasTO.getIsWeightEditable());
		custModificationTO.setIsCnTypeEditable(custModificationAliasTO.getIsCnTypeEditable());
		
		return custModificationTO;
	}
	
	@Override
	public List<CustomerTO> getCustomerDetails(Integer officeId,Integer cityId)
			throws CGBusinessException, CGSystemException {
		List<CustomerTO> customerTOs = null;
		List<CustomerBillingDO> customerBillingDos = custModificationDAO.getCustomerDetails(cityId,officeId);
		if(!StringUtil.isEmptyColletion(customerBillingDos)){
			customerTOs = new ArrayList<CustomerTO>();
			for (CustomerBillingDO customerBillingDO : customerBillingDos) {
				CustomerTO customerTO = new CustomerTO();
				customerTO.setCustomerId(customerBillingDO.getCustomerId());
				customerTO.setCustomerCode(customerBillingDO.getCustomerCode());
				customerTO.setBusinessName(customerBillingDO.getBusinessName());
				customerTO.setShippedToCode(customerBillingDO.getShippedToCode());
				customerTOs.add(customerTO);
			}
		}else{
			throw new CGBusinessException(UniversalErrorConstants.NO_CUSTOMERS_FOUND);
		}
		return customerTOs;
	}
	
	@Override
	public List<CityTO> getCitysByRegion(Integer regionId) throws CGBusinessException, CGSystemException {
		return geographyCommonService.getCitiesByRegion(regionId);
	}
	
	@SuppressWarnings("unchecked")
	private List<RegionTO> getRegionNameByRegionCode(String regionCode)
			throws CGBusinessException, CGSystemException {
		List<RegionTO> regionTO = null;
		try{
			List<RegionDO> regionDO = custModificationDAO.getRegionByRegionCode(regionCode);

			if (!CGCollectionUtils.isEmpty(regionDO)) {

				regionTO = (List<RegionTO>) CGObjectConverter
						.createTOListFromDomainList(regionDO, RegionTO.class);
			} else {
				ExceptionUtil
				.prepareBusinessException(BillingConstants.CITIES_NOT_FOUND);
			}
		} catch (Exception ex) {
			LOGGER.error("ERROR : CustModificationServiceImpl::getCitysByStateId",
					ex);
			throw ex;
		}
		LOGGER.debug("CustModificationServiceImpl::getCitysByStateId::END----->");
		return regionTO;
	}
	
	@Override
	public List<ConsignmentTypeTO> getConsignmentType() throws CGBusinessException, CGSystemException {
		return serviceOfferingCommonService.getConsignmentType();
	}
}
