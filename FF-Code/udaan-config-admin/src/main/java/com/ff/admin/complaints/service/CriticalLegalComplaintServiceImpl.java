package com.ff.admin.complaints.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.complaints.dao.ServiceRequestLegalComplaintDAO;
import com.ff.complaints.LegalComplaintTO;
import com.ff.domain.complaints.ServiceRequestLegalComplaintDO;
import com.ff.domain.complaints.ServiceRequestPapersDO;

public class CriticalLegalComplaintServiceImpl implements CriticalLegalComplaintService {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(CriticalLegalComplaintServiceImpl.class);	
	private ServiceRequestLegalComplaintDAO serviceRequstLegalComplaintDAO;
	
	/**
	 * @return the serviceRequstLegalComplaintDAO
	 */
	public ServiceRequestLegalComplaintDAO getServiceRequstLegalComplaintDAO() {
		return serviceRequstLegalComplaintDAO;
	}

	/**
	 * @param serviceRequstLegalComplaintDAO the serviceRequstLegalComplaintDAO to set
	 */
	public void setServiceRequstLegalComplaintDAO(
			ServiceRequestLegalComplaintDAO serviceRequstLegalComplaintDAO) {
		this.serviceRequstLegalComplaintDAO = serviceRequstLegalComplaintDAO;
	}



	@Override
	public boolean saveOrUpdateLegalComplaint(LegalComplaintTO legalComplainTO)
			throws CGBusinessException, CGSystemException, IOException {
		// TODO Auto-generated method stub
		
		boolean flag = Boolean.FALSE;
			
			ServiceRequestLegalComplaintDO serviceRequestLegalComplaintDO = new ServiceRequestLegalComplaintDO();
			ServiceRequestPapersDO serviceRequstpaperDO = new ServiceRequestPapersDO();
			//upload = CGExcelUploadUtil.uploadFile(legalComplainTO.getFilePath(), legalComplainTO.getFileName(), legalComplainTO.getFrmfile());
			
			//set the file and file name in ServiceRequestPaperDO
			serviceRequstpaperDO.setFile(legalComplainTO.getFrmfile().getFileData());
			serviceRequstpaperDO.setFileName(legalComplainTO.getFileName());
			serviceRequstpaperDO.setFileDescription("Advocate Notice Letter");
			serviceRequstpaperDO.setCreatedBy(legalComplainTO.getCreatedBy());
			serviceRequstpaperDO.setUpdatedBy(legalComplainTO.getUpdateby());
			serviceRequstpaperDO.setCreatedDate(DateUtil.stringToDDMMYYYYFormat(legalComplainTO.getCreatedDate()));
			serviceRequstpaperDO.setUpdatedDate(DateUtil.stringToDDMMYYYYFormat(legalComplainTO.getUpdateDate()));
			
			convertLegalcomplaintTO2LegalComplaintDO(legalComplainTO, serviceRequestLegalComplaintDO);
			
			if(!StringUtil.isNull(serviceRequestLegalComplaintDO)){
				
				serviceRequstpaperDO.setServiceRequestId(legalComplainTO.getServiceRequestComplaintId());
				//serviceRequstpaperDO.setAdvaocateNoticefileName(legalComplainTO.getServiceRequestComplaintId()+"_Advocate_Notice_"+legalComplainTO.getCreatedDate());//FIXME
				
				
				 flag = serviceRequstLegalComplaintDAO.saveOrUpdateLegalComplaint(serviceRequestLegalComplaintDO,serviceRequstpaperDO);
			}
			
			return flag;

}
	
	private void convertLegalcomplaintTO2LegalComplaintDO(LegalComplaintTO legalComplainTO,
			ServiceRequestLegalComplaintDO serviceRequestLegalComplaintDO) throws CGBusinessException{
	
		
		
		if(!StringUtil.isNull(legalComplainTO)){
			//paperworkDO = new ServiceRequestPapersDO();			
			
			serviceRequestLegalComplaintDO.setInvestigationFeedback(legalComplainTO.getInvestigFeedback());
			serviceRequestLegalComplaintDO.setForwardedToFfclLawyer(legalComplainTO.getForwardedToFFclLawyer());
			serviceRequestLegalComplaintDO.setForwardedToFfclLawyerDate(DateUtil.stringToDDMMYYYYFormat(legalComplainTO.getDate()));
			serviceRequestLegalComplaintDO.setRemarks(legalComplainTO.getRemarks());			
			serviceRequestLegalComplaintDO.setLawyerFees(legalComplainTO.getLawyerFees());	
			
			serviceRequestLegalComplaintDO.setHearing1(legalComplainTO.getHearing1());
			serviceRequestLegalComplaintDO.setHearing2(legalComplainTO.getHearing2());
			serviceRequestLegalComplaintDO.setHearing3(legalComplainTO.getHearing3());
			serviceRequestLegalComplaintDO.setHearing4(legalComplainTO.getHearing4());
			serviceRequestLegalComplaintDO.setHearing5(legalComplainTO.getHearing5());
			serviceRequestLegalComplaintDO.setHearing6(legalComplainTO.getHearing6());
			
			//setting the new added dates for hearing 1 to hearing 6 and advocate notice Date
			serviceRequestLegalComplaintDO.setHearing1_date(DateUtil.stringToDDMMYYYYFormat(legalComplainTO.getHearing1_date()));
			serviceRequestLegalComplaintDO.setHearing2_date(DateUtil.stringToDDMMYYYYFormat(legalComplainTO.getHearing1_date()));
			serviceRequestLegalComplaintDO.setHearing3_date(DateUtil.stringToDDMMYYYYFormat(legalComplainTO.getHearing1_date()));
			serviceRequestLegalComplaintDO.setHearing4_date(DateUtil.stringToDDMMYYYYFormat(legalComplainTO.getHearing1_date()));
			serviceRequestLegalComplaintDO.setHearing5_date(DateUtil.stringToDDMMYYYYFormat(legalComplainTO.getHearing1_date()));
			serviceRequestLegalComplaintDO.setHearing6_date(DateUtil.stringToDDMMYYYYFormat(legalComplainTO.getHearing1_date()));
			serviceRequestLegalComplaintDO.setAdvocateNoticeFileDate(DateUtil.stringToDDMMYYYYFormat(legalComplainTO.getAdvocateNoticeFileDate()));
			
			
			serviceRequestLegalComplaintDO.setCreatedBy(legalComplainTO.getCreatedBy());
			serviceRequestLegalComplaintDO.setCreatedDate(DateUtil.stringToDDMMYYYYFormat(legalComplainTO.getCreatedDate()));
			serviceRequestLegalComplaintDO.setUpdatedDate(DateUtil.stringToDDMMYYYYFormat(legalComplainTO.getUpdateDate()));
			serviceRequestLegalComplaintDO.setUpdatedBy(legalComplainTO.getUpdateby());
			
			// Setting legal complaint status
			if (!StringUtil.isStringEmpty(legalComplainTO
					.getLegalComplaintStatus())) {
				serviceRequestLegalComplaintDO
						.setLegalComplaintStatus(legalComplainTO
								.getLegalComplaintStatus());
			}
		}
	}
	
	@Override
	public LegalComplaintTO searchLegalComplaint(LegalComplaintTO legalcomplntTO)
			throws CGBusinessException, CGSystemException {
		ServiceRequestLegalComplaintDO legalComplantDO = serviceRequstLegalComplaintDAO.searchLegalComplaint(legalcomplntTO);
	
		if(!StringUtil.isNull(legalComplantDO)){
			legalComplaintTransferObjConverter(legalComplantDO, legalcomplntTO);
		}
		
		/*if(!StringUtil.isNull(legalComplantDO)){
			 serviceRequstPaperDO= serviceRequstLegalComplaintDAO.getPaperWorkDO(legalcomplntTO);
			 if(!StringUtil.isNull(serviceRequstPaperDO)){
				 //legalcomplntTO.setFileName(serviceRequstPaperDO.getAdvaocateNoticefileName());//FIXME
			 }
		}*/
		
		return legalcomplntTO;
	}
	
	private void legalComplaintTransferObjConverter(
			ServiceRequestLegalComplaintDO domain, LegalComplaintTO to)
			throws CGBusinessException, CGSystemException {
		
		try {
			to.setInvestigFeedback(domain.getInvestigationFeedback());
			to.setForwardedToFFclLawyer(domain.getForwardedToFfclLawyer());
			to.setDate(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(domain.getForwardedToFfclLawyerDate()));
			to.setRemarks(domain.getRemarks());			
			to.setLawyerFees(domain.getLawyerFees());			
			to.setHearing1(domain.getHearing1());
			to.setHearing2(domain.getHearing2());
			to.setHearing3(domain.getHearing3());
			to.setHearing4(domain.getHearing4());
			to.setHearing5(domain.getHearing5());
			to.setHearing6(domain.getHearing6());
			
			to.setAdvocateNoticeFileDate(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(domain.getAdvocateNoticeFileDate()));
			to.setHearing1_date(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(domain.getHearing1_date()));
			to.setHearing2_date(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(domain.getHearing2_date()));
			to.setHearing3_date(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(domain.getHearing3_date()));
			to.setHearing4_date(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(domain.getHearing4_date()));
			to.setHearing5_date(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(domain.getHearing5_date()));
			to.setHearing6_date(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(domain.getHearing6_date()));
			
			
			
			to.setCreatedBy(domain.getCreatedBy());
			to.setCreatedDate(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(domain.getCreatedDate()));
			to.setUpdateDate(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(domain.getUpdatedDate()));
			to.setUpdateby(domain.getUpdatedBy());

			if (!StringUtil.isStringEmpty(domain.getLegalComplaintStatus())) {
				to.setLegalComplaintStatus(domain.getLegalComplaintStatus());
			}
			
		} catch (Exception e) {
			LOGGER.error("CriticalLegalComplaintServiceImpl::searchLegalComplaint::legalComplaintTransferObjConverter",e);
			throw new CGBusinessException(e);
		}
		
	}
	
}