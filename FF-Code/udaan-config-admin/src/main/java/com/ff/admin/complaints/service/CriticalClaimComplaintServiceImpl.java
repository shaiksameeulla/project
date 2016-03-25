/**
 * 
 */
package com.ff.admin.complaints.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.complaints.dao.CriticalClaimComplaintDAO;
import com.ff.complaints.CriticalClaimComplaintTO;
import com.ff.domain.complaints.ServiceRequestComplaintDO;
import com.ff.domain.complaints.ServiceRequestCriticalComplaintClaimDO;

/**
 * @author cbhure
 *
 */
public class CriticalClaimComplaintServiceImpl implements
CriticalClaimComplaintService {
	
	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(CriticalClaimComplaintServiceImpl.class);
	
	private CriticalClaimComplaintDAO criticalClaimComplaintDAO;


	@Override
	public Boolean saveCriticalClaimComplaint(CriticalClaimComplaintTO criticalComplaintTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("CriticalClaimComplaintServiceImpl :: saveCriticalClaimComplaint :: START");
		Boolean isSave = false;
		ServiceRequestCriticalComplaintClaimDO criticalComplaintDO = null;
		if(!StringUtil.isNull(criticalComplaintTO)){
			criticalComplaintDO = convertCriticalComplaintTOtoDO(criticalComplaintTO);
			if(!StringUtil.isNull(criticalComplaintDO)){ 
				isSave = criticalClaimComplaintDAO.saveCriticalClaimComplaint(criticalComplaintDO);
			}
		}
		LOGGER.trace("CriticalClaimComplaintServiceImpl :: saveCriticalClaimComplaint :: END");
		return isSave;
	}

	private ServiceRequestCriticalComplaintClaimDO convertCriticalComplaintTOtoDO(
			CriticalClaimComplaintTO criticalComplaintTO) {
		
		LOGGER.trace("CriticalClaimComplaintServiceImpl :: convertCriticalComplaintTOtoDO :: Start");
		ServiceRequestCriticalComplaintClaimDO criticalComplaintDO = new ServiceRequestCriticalComplaintClaimDO();
		
		if(!StringUtil.isEmptyInteger(criticalComplaintTO.getServiceReqClaimId())){
			criticalComplaintDO.setServiceReqClaimId(criticalComplaintTO.getServiceReqClaimId());
		}
		
		if(!StringUtil.isEmptyInteger(criticalComplaintTO.getServiceRequestComplaintId())){
			ServiceRequestComplaintDO serviceRequestComplaintDO = new ServiceRequestComplaintDO();
			serviceRequestComplaintDO.setServiceRequestComplaintId(criticalComplaintTO.getServiceRequestComplaintId());
			criticalComplaintDO.setServiceRequestComplaintDO(serviceRequestComplaintDO);
		}
		
		if(!StringUtil.isNull(criticalComplaintTO.getIsActualClaim())){
			criticalComplaintDO.setActualClaim(criticalComplaintTO.getIsActualClaim());
		}
		
		if(!StringUtil.isStringEmpty(criticalComplaintTO.getActualClaimAmt())){
			criticalComplaintDO.setActualClaimAmt(Double.valueOf(criticalComplaintTO.getActualClaimAmt()));
		}
		
		if(!StringUtil.isNull(criticalComplaintTO.getIsNegotiableClaim())){
			criticalComplaintDO.setNegotiableClaim(criticalComplaintTO.getIsNegotiableClaim());
		}
		
		if(!StringUtil.isStringEmpty(criticalComplaintTO.getNegotiableClaimAmt())){
			criticalComplaintDO.setNegotiableClaimAmt(Double.valueOf(criticalComplaintTO.getNegotiableClaimAmt()));
		}
		
		// COF
		if (!StringUtil.isNull(criticalComplaintTO.getIsCof())) {
			criticalComplaintDO.setIsCof(criticalComplaintTO.getIsCof());
		}
		if (!StringUtil.isStringEmpty(criticalComplaintTO.getCofAmt())) {
			criticalComplaintDO.setCofAmt(Double.valueOf(criticalComplaintTO
					.getCofAmt()));
		}
		
		if(!StringUtil.isNull(criticalComplaintTO.getIsSettlement())){
			criticalComplaintDO.setSettlement(criticalComplaintTO.getIsSettlement());
		}
		
		if (!StringUtil.isStringEmpty(criticalComplaintTO.getIsSettled())) {
			criticalComplaintDO
					.setIsSettled(criticalComplaintTO.getIsSettled());
		}
		
		if(!StringUtil.isStringEmpty(criticalComplaintTO.getPaperWork())){
			criticalComplaintDO.setPaperWork(criticalComplaintTO.getPaperWork());
		}
		
		if(!StringUtil.isStringEmpty(criticalComplaintTO.getAccountability())){
			criticalComplaintDO.setAccountability(criticalComplaintTO.getAccountability());
		}
		
		if(!StringUtil.isStringEmpty(criticalComplaintTO.getClientPolicy())){
			criticalComplaintDO.setClientPolicy(criticalComplaintTO.getClientPolicy());
		}
		
		if(!StringUtil.isStringEmpty(criticalComplaintTO.getMissingCertificate())){
			criticalComplaintDO.setMissingCertificate(criticalComplaintTO.getMissingCertificate());
		}
		
		if(!StringUtil.isStringEmpty(criticalComplaintTO.getRemark())){
			criticalComplaintDO.setRemarks(criticalComplaintTO.getRemark());
		}
		
		if(!StringUtil.isStringEmpty(criticalComplaintTO.getSalesManagerFeedback())){
			criticalComplaintDO.setSalesManagerFeedback(criticalComplaintTO.getSalesManagerFeedback());
		}
		
		if(!StringUtil.isStringEmpty(criticalComplaintTO.getAgmFeedback())){
			criticalComplaintDO.setAgmFeedback(criticalComplaintTO.getAgmFeedback());
		}
		
		if(!StringUtil.isStringEmpty(criticalComplaintTO.getVpFeedback())){
			criticalComplaintDO.setVpFeedback(criticalComplaintTO.getVpFeedback());
		}
		
		if (!StringUtil.isStringEmpty(criticalComplaintTO.getCorporate())) {
			criticalComplaintDO
					.setCorporate(criticalComplaintTO.getCorporate());
		}
		
		if(!StringUtil.isStringEmpty(criticalComplaintTO.getCsManagerFeedback())){
			criticalComplaintDO.setCsManagerFeedback(criticalComplaintTO.getCsManagerFeedback());
		}
		
		if(!StringUtil.isNull(criticalComplaintTO.getAgmFeedbackDate())){
			criticalComplaintDO.setAgmFeedbackDate(DateUtil.stringToDDMMYYYYFormat(criticalComplaintTO.getAgmFeedbackDate()));
		}
		
		if(!StringUtil.isNull(criticalComplaintTO.getCsManagerFeedbackDate())){
			criticalComplaintDO.setCsManagerFeedbackDate(DateUtil.stringToDDMMYYYYFormat(criticalComplaintTO.getCsManagerFeedbackDate()));
		}
		
		if(!StringUtil.isNull(criticalComplaintTO.getVpFeedBackDate())){
			criticalComplaintDO.setVpFeedBackDate(DateUtil.stringToDDMMYYYYFormat(criticalComplaintTO.getVpFeedBackDate()));
		}
		
		if (!StringUtil.isStringEmpty(criticalComplaintTO.getCorporateDate())) {
			criticalComplaintDO.setCorporateDate(DateUtil
					.stringToDDMMYYYYFormat(criticalComplaintTO
							.getCorporateDate()));
		}
		
		if(!StringUtil.isNull(criticalComplaintTO.getSalesManagerFeedbackDate())){
			criticalComplaintDO.setSalesManagerFeedbackDate(DateUtil.stringToDDMMYYYYFormat(criticalComplaintTO.getSalesManagerFeedbackDate()));
		}
		
		if(!StringUtil.isEmptyInteger(criticalComplaintTO.getCreatedBy())){
			criticalComplaintDO.setCreatedBy(criticalComplaintTO.getCreatedBy());
		}
		
		if(!StringUtil.isEmptyInteger(criticalComplaintTO.getUpdatedBy())){
			criticalComplaintDO.setUpdatedBy(criticalComplaintTO.getUpdatedBy());
		}
		
		// Setting claim complaint status
		if (!StringUtil.isStringEmpty(criticalComplaintTO
				.getClaimComplaintStatus())) {
			criticalComplaintDO.setClaimComplaintStatus(criticalComplaintTO
					.getClaimComplaintStatus());
		}
		
		/*if(!StringUtil.isNull(criticalComplaintTO.getCreationDateStr())){
			criticalComplaintDO.setCreatedDate(criticalComplaintTO.getCreationDateStr());
		}*/
		
		LOGGER.trace("CriticalClaimComplaintServiceImpl :: convertCriticalComplaintTOtoDO :: End");
		return criticalComplaintDO;
	}

	/**
	 * @param criticalClaimComplaintDAO the criticalClaimComplaintDAO to set
	 */
	public void setCriticalClaimComplaintDAO(
			CriticalClaimComplaintDAO criticalClaimComplaintDAO) {
		this.criticalClaimComplaintDAO = criticalClaimComplaintDAO;
	}

	@Override
	public CriticalClaimComplaintTO getCriticalClaimComplaintDtls(
			CriticalClaimComplaintTO criticalComplaintTO) throws CGSystemException {
		LOGGER.trace("CriticalClaimComplaintServiceImpl :: getCriticalClaimComplaintDtls :: Start");
		CriticalClaimComplaintTO criticalClaimComplaintTO = null;
		ServiceRequestCriticalComplaintClaimDO criticalClaimComplaintDO = null;
			criticalClaimComplaintDO = criticalClaimComplaintDAO.getCriticalClaimComplaintDtls(criticalComplaintTO);
			if(!StringUtil.isNull(criticalClaimComplaintDO)){ 
				criticalClaimComplaintTO = convertCriticalClaimcomplaintDOtoTO(criticalClaimComplaintDO);
			}
		
		LOGGER.trace("CriticalClaimComplaintServiceImpl :: getCriticalClaimComplaintDtls :: End");
		return criticalClaimComplaintTO;
	}

	private CriticalClaimComplaintTO convertCriticalClaimcomplaintDOtoTO(
			ServiceRequestCriticalComplaintClaimDO criticalClaimComplaintDO) {
		
		LOGGER.trace("CriticalClaimComplaintServiceImpl :: convertCriticalClaimcomplaintDOtoTO :: Start");
		CriticalClaimComplaintTO  criticalComplaintTO = new CriticalClaimComplaintTO();
		
		if(!StringUtil.isEmptyInteger(criticalClaimComplaintDO.getServiceReqClaimId())){
			criticalComplaintTO.setServiceReqClaimId(criticalClaimComplaintDO.getServiceReqClaimId());
		}
		
		if(!StringUtil.isNull(criticalClaimComplaintDO.getServiceRequestComplaintDO())
				&& !StringUtil.isNull(criticalClaimComplaintDO.getServiceRequestComplaintDO().getServiceRequestComplaintId())){
			criticalComplaintTO.setServiceRequestComplaintId(criticalClaimComplaintDO.getServiceRequestComplaintDO().getServiceRequestComplaintId());
		}
		
		if(!StringUtil.isNull(criticalClaimComplaintDO.getActualClaim())){
			criticalComplaintTO.setIsActualClaim(criticalClaimComplaintDO.getActualClaim());
		}
		
		if(!StringUtil.isEmptyDouble(criticalClaimComplaintDO.getActualClaimAmt())){
			criticalComplaintTO.setActualClaimAmt(String.valueOf(criticalClaimComplaintDO.getActualClaimAmt()));
		}
		
		if(!StringUtil.isNull(criticalClaimComplaintDO.getNegotiableClaim())){
			criticalComplaintTO.setIsNegotiableClaim(criticalClaimComplaintDO.getNegotiableClaim());
		}
		
		if(!StringUtil.isEmptyDouble(criticalClaimComplaintDO.getNegotiableClaimAmt())){
			criticalComplaintTO.setNegotiableClaimAmt(String.valueOf(criticalClaimComplaintDO.getNegotiableClaimAmt()));
		}
		// COF
		if (!StringUtil.isNull(criticalClaimComplaintDO.getIsCof())) {
			criticalComplaintTO.setIsCof(criticalClaimComplaintDO.getIsCof());
		}
		if (!StringUtil.isEmptyDouble(criticalClaimComplaintDO.getCofAmt())) {
			criticalComplaintTO.setCofAmt(String
					.valueOf(criticalClaimComplaintDO.getCofAmt()));
		}
		
		if(!StringUtil.isNull(criticalClaimComplaintDO.getSettlement())){
			criticalComplaintTO.setIsSettlement(criticalClaimComplaintDO.getSettlement());
		}
		
		if (!StringUtil.isStringEmpty(criticalClaimComplaintDO.getIsSettled())) {
			criticalComplaintTO.setIsSettled(criticalClaimComplaintDO
					.getIsSettled());
		}
		
		if(!StringUtil.isStringEmpty(criticalClaimComplaintDO.getPaperWork())){
			criticalComplaintTO.setPaperWork(criticalClaimComplaintDO.getPaperWork());
		}
		
		if(!StringUtil.isStringEmpty(criticalClaimComplaintDO.getAccountability())){
			criticalComplaintTO.setAccountability(criticalClaimComplaintDO.getAccountability());
		}
		
		if(!StringUtil.isStringEmpty(criticalClaimComplaintDO.getClientPolicy())){
			criticalComplaintTO.setClientPolicy(criticalClaimComplaintDO.getClientPolicy());
		}
		
		if(!StringUtil.isStringEmpty(criticalClaimComplaintDO.getMissingCertificate())){
			criticalComplaintTO.setMissingCertificate(criticalClaimComplaintDO.getMissingCertificate());
		}
		
		if(!StringUtil.isStringEmpty(criticalClaimComplaintDO.getRemarks())){
			criticalComplaintTO.setRemark(criticalClaimComplaintDO.getRemarks());
		}
		
		if(!StringUtil.isStringEmpty(criticalClaimComplaintDO.getSalesManagerFeedback())){
			criticalComplaintTO.setSalesManagerFeedback(criticalClaimComplaintDO.getSalesManagerFeedback());
		}
		
		if(!StringUtil.isStringEmpty(criticalClaimComplaintDO.getAgmFeedback())){
			criticalComplaintTO.setAgmFeedback(criticalClaimComplaintDO.getAgmFeedback());
		}
		
		if(!StringUtil.isStringEmpty(criticalClaimComplaintDO.getVpFeedback())){
			criticalComplaintTO.setVpFeedback(criticalClaimComplaintDO.getVpFeedback());
		}
		
		if (!StringUtil.isStringEmpty(criticalClaimComplaintDO.getCorporate())) {
			criticalComplaintTO.setCorporate(criticalClaimComplaintDO
					.getCorporate());
		}
		
		if(!StringUtil.isStringEmpty(criticalClaimComplaintDO.getCsManagerFeedback())){
			criticalComplaintTO.setCsManagerFeedback(criticalClaimComplaintDO.getCsManagerFeedback());
		}
		
		if(!StringUtil.isNull(criticalClaimComplaintDO.getAgmFeedbackDate())){
			criticalComplaintTO.setAgmFeedbackDate(DateUtil.getDDMMYYYYDateString(criticalClaimComplaintDO.getAgmFeedbackDate()));
		}
		
		if(!StringUtil.isNull(criticalClaimComplaintDO.getCsManagerFeedbackDate())){
			criticalComplaintTO.setCsManagerFeedbackDate(DateUtil.getDDMMYYYYDateString(criticalClaimComplaintDO.getCsManagerFeedbackDate()));
		}
		
		if(!StringUtil.isNull(criticalClaimComplaintDO.getVpFeedBackDate())){
			criticalComplaintTO.setVpFeedBackDate(DateUtil.getDDMMYYYYDateString(criticalClaimComplaintDO.getVpFeedBackDate()));
		}
		
		if (!StringUtil.isNull(criticalClaimComplaintDO.getCorporateDate())) {
			criticalComplaintTO.setCorporateDate(DateUtil
					.getDDMMYYYYDateString(criticalClaimComplaintDO
							.getCorporateDate()));
		}
		
		if(!StringUtil.isNull(criticalClaimComplaintDO.getSalesManagerFeedbackDate())){
			criticalComplaintTO.setSalesManagerFeedbackDate(DateUtil.getDDMMYYYYDateString(criticalClaimComplaintDO.getSalesManagerFeedbackDate()));
		}
		
		/*if(!StringUtil.isEmptyInteger(criticalClaimComplaintDO.getCreatedBy())){
			criticalComplaintTO.setCreatedBy(criticalClaimComplaintDO.getCreatedBy());
		}
		
		if(!StringUtil.isEmptyInteger(criticalClaimComplaintDO.getUpdatedBy())){
			criticalComplaintTO.setUpdatedBy(criticalClaimComplaintDO.getUpdatedBy());
		}*/
		
		/*if(!StringUtil.isNull(criticalClaimComplaintDO.getCreationDateStr())){
			criticalComplaintTO.setCreatedDate(criticalClaimComplaintDO.getCreationDateStr());
		}*/
		
		if (!StringUtil.isStringEmpty(criticalClaimComplaintDO
				.getClaimComplaintStatus())) {
			criticalComplaintTO
					.setClaimComplaintStatus(criticalClaimComplaintDO
							.getClaimComplaintStatus());
		}
		
		LOGGER.trace("CriticalClaimComplaintServiceImpl :: convertCriticalClaimcomplaintDOtoTO :: End");
		
		return criticalComplaintTO;
		
	}

	@Override
	public CriticalClaimComplaintTO getCriticalComplaintDtls(String complaintNo) throws CGBusinessException,CGSystemException {
		LOGGER.trace("CriticalClaimComplaintServiceImpl :: getCriticalComplaintDtls :: Start");
		CriticalClaimComplaintTO criticalClaimComplaintTO = null;
		ServiceRequestComplaintDO criticalComplaintDO = null;
			criticalComplaintDO = criticalClaimComplaintDAO.getCriticalComplaintDtls(complaintNo);
			if(!StringUtil.isNull(criticalComplaintDO)){
				criticalClaimComplaintTO = new CriticalClaimComplaintTO();
				criticalClaimComplaintTO.setServiceRequestComplaintId(criticalComplaintDO.getServiceRequestComplaintId());
			}
		
		LOGGER.trace("CriticalClaimComplaintServiceImpl :: getCriticalComplaintDtls :: End");
		return criticalClaimComplaintTO;
	}
	

}
