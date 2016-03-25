package com.ff.admin.report.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.mec.common.constants.MECCommonConstants;
import com.ff.admin.report.dao.OutstandingReportDAO;
import com.ff.admin.report.to.OutstandingReportTO;
import com.ff.domain.mec.SAPOutstandingPaymentDO;
import com.ff.domain.mec.SAPReportDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.umc.UserInfoTO;
import com.ff.universe.organization.dao.OrganizationCommonDAO;

public class OutstandingReportServiceImpl implements OutstandingReportService {
	
	private final static Logger LOGGER = LoggerFactory
			.getLogger(OutstandingReportServiceImpl.class);
	
	private OutstandingReportDAO outstandingReportDAO;
	private OrganizationCommonDAO organizationCommonDAO;
	
	

	/**
	 * @param organizationCommonDAO the organizationCommonDAO to set
	 */
	public void setOrganizationCommonDAO(OrganizationCommonDAO organizationCommonDAO) {
		this.organizationCommonDAO = organizationCommonDAO;
	}

	public OutstandingReportDAO getOutstandingReportDAO() {
		return outstandingReportDAO;
	}

	public void setOutstandingReportDAO(OutstandingReportDAO outstandingReportDAO) {
		this.outstandingReportDAO = outstandingReportDAO;
	}

	@Override
	public Boolean saveReportData(OutstandingReportTO outstandingReportTO, UserInfoTO userInfoTO)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("in OutstandinfReportServiceImpl >> saveReportData");
		
		SAPOutstandingPaymentDO outstandingReportDO = getOutstandingReportDO(outstandingReportTO, userInfoTO);
		return outstandingReportDAO.saveReportData(outstandingReportDO);
	}
	
	
	
	
	public SAPOutstandingPaymentDO getOutstandingReportDO(OutstandingReportTO outstandingReportTO, UserInfoTO userInfoTO) throws CGBusinessException, CGSystemException{
		
		SAPOutstandingPaymentDO reportDO = new SAPOutstandingPaymentDO();
		OfficeDO ofcDO = null;
		try{
			reportDO.setBillUpto(DateUtil.slashDelimitedstringToDDMMYYYYFormat(outstandingReportTO.getBillUpto()));
		}catch(IllegalArgumentException e){
			LOGGER.error(MECCommonConstants.BILL_UPTO_ERROR);
			throw new CGBusinessException(MECCommonConstants.BILL_UPTO_ERROR);
		}
		try{
			reportDO.setPaymentUpto(DateUtil.slashDelimitedstringToDDMMYYYYFormat(outstandingReportTO.getPaymentUpto()));
		}catch(IllegalArgumentException e){
			LOGGER.error(MECCommonConstants.PAYMENT_UPTO_ERROR);
			throw new CGBusinessException(MECCommonConstants.PAYMENT_UPTO_ERROR);
		}
		
		if(MECCommonConstants.OUTSTANDING_FOR_CUSTOMER.trim().equalsIgnoreCase(outstandingReportTO.getOutStandingFor()) || MECCommonConstants.OUTSTANDING_FOR_PROFITCENTER.trim().equalsIgnoreCase(outstandingReportTO.getOutStandingFor()) ){
			reportDO.setOutStandingFor(outstandingReportTO.getOutStandingFor());
		}else{
			throw new CGBusinessException(MECCommonConstants.OUTSTANDING_FOR_ERROR);
		}
		
		
		if(MECCommonConstants.OUTSTANDING_FOR_CUSTOMER.equalsIgnoreCase(outstandingReportTO.getOutStandingFor())){
			if(userInfoTO.getEmpUserTo()!= null && userInfoTO.getEmpUserTo().getEmpTO() != null ){
				reportDO.setCustomerCode(outstandingReportTO.getCustomerCode());
			}
		}
		if(userInfoTO.getEmpUserTo() != null && userInfoTO.getEmpUserTo().getEmpTO() != null){
			reportDO.setEmpCode(userInfoTO.getEmpUserTo().getEmpTO().getEmpCode());
		}
		
		
		Integer officeId = userInfoTO.getEmpUserTo().getEmpTO().getOfficeId();
		String offCode = null;
		ofcDO = organizationCommonDAO.getOfficeByIdOrCode(officeId, offCode);
		if(!StringUtil.isNull(ofcDO)){
			reportDO.setOfficeCode(ofcDO.getOfficeCode());
		}
		reportDO.setCustomerCode(outstandingReportTO.getCustomerCode());
		reportDO.setCcemail(outstandingReportTO.getCcemail());
		reportDO.setReportName(outstandingReportTO.getReportFor());
		return reportDO;
		
	}
	

	@Override
	public List<SAPReportDO> getReportList() throws CGSystemException{
		return outstandingReportDAO.getReportList();
	}

}
