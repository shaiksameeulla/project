package com.ff.web.codReceipt.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.bs.sequence.SequenceGeneratorService;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.to.codreceipt.CodReceiptDetailsTO;
import com.ff.to.codreceipt.CodReceiptTO;
import com.ff.to.codreceipt.ExpenseAliasTO;
import com.ff.web.codReceipt.constants.CodReceiptConstants;
import com.ff.web.codReceipt.dao.CodReceiptDAO;
import com.ff.web.util.UdaanWebErrorConstants;

public class CodReceiptServiceImpl implements CodReceiptService {
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(CodReceiptServiceImpl.class);
	private CodReceiptDAO codReceiptDAO;
	/** The sequence generator service. */
	private transient SequenceGeneratorService sequenceGeneratorService;
	
	public void setCodReceiptDAO(CodReceiptDAO codReceiptDAO) {
		this.codReceiptDAO = codReceiptDAO;
	}


  public void setSequenceGeneratorService(
			SequenceGeneratorService sequenceGeneratorService) {
		this.sequenceGeneratorService = sequenceGeneratorService;
	}



public CodReceiptTO getConsgDetails(String consgNo)throws CGBusinessException,
    CGSystemException {
	 LOGGER.debug("CodReceiptServiceImpl::getConsgDetails::START----->");	
	 ConsignmentDO consg=null;
	 BookingDO bookingDO = null;
	 CodReceiptTO codReceiptTO=new CodReceiptTO();
	 List<CodReceiptDetailsTO> codDetailTOs=new ArrayList<CodReceiptDetailsTO>();
	 CodReceiptDetailsTO codDetails=null;
	 double grandTotal=0.0;
     consg=codReceiptDAO.getConsingmentDtlsForCodRecpt(consgNo);
     if(!StringUtil.isNull(consg)){
      bookingDO=codReceiptDAO.getConsgBookingDtsForCodRcpt(consgNo);
      if(!StringUtil.isNull(bookingDO)){
      if(!StringUtil.isEmptyDouble(consg.getLcAmount())){
       codDetails=new CodReceiptDetailsTO();
       /*codDetails.setLcAmt(consg.getLcAmount());
       codDetailTOs.add(codDetails);*/
       codDetails.setExpenseDescription("LC Amount");
       codDetails.setExpenseTotalAmount(consg.getLcAmount());
       codDetailTOs.add(codDetails);
       grandTotal+=consg.getLcAmount();
      }
     
      if(!StringUtil.isEmptyDouble(consg.getTopayAmt())){
         codDetails=new CodReceiptDetailsTO();
         codDetails.setExpenseDescription("To-Pay Amount");
         codDetails.setExpenseTotalAmount(consg.getTopayAmt());
         codDetailTOs.add(codDetails);
         grandTotal+=consg.getTopayAmt();
       }
    
     if(!StringUtil.isEmptyDouble(consg.getCodAmt())){
         codDetails=new CodReceiptDetailsTO();
         codDetails.setExpenseDescription("COD Amount");
         codDetails.setExpenseTotalAmount(consg.getCodAmt());
         codDetailTOs.add(codDetails);
         grandTotal+=consg.getCodAmt();
      }
     ExpenseAliasTO expenseDetlsTO=codReceiptDAO.getExpenseDetails(consg.getConsgId());
     if(!StringUtil.isNull(expenseDetlsTO)){
    	 if(!StringUtil.isEmptyDouble(expenseDetlsTO.getTotalExpenseAmt())){
             codDetails=new CodReceiptDetailsTO();
             codDetails.setExpenseDescription(expenseDetlsTO.getGlDescription());
             codDetails.setExpenseTotalAmount(expenseDetlsTO.getTotalExpenseAmt());
             codDetailTOs.add(codDetails);
             grandTotal+=expenseDetlsTO.getTotalExpenseAmt();
          } 
    	/* if(!StringUtil.isEmptyDouble(expenseDetlsTO.getServiceTax())){
             codDetails=new CodReceiptDetailsTO();
             codDetails.setExpenseDescription(expenseDetlsTO.getGlDescription());
             codDetails.setExpenseTotalAmount(expenseDetlsTO.getServiceTax());
             codDetailTOs.add(codDetails);
          } 
    	 if(!StringUtil.isEmptyDouble(expenseDetlsTO.getSeviceCharge())){
             codDetails=new CodReceiptDetailsTO();
             codDetails.setExpenseDescription(expenseDetlsTO.getGlDescription());
             codDetails.setExpenseTotalAmount(expenseDetlsTO.getSeviceCharge());
             codDetailTOs.add(codDetails);
          } 
    	 if(!StringUtil.isEmptyDouble(expenseDetlsTO.getAmount())){
             codDetails=new CodReceiptDetailsTO();
             codDetails.setExpenseDescription(expenseDetlsTO.getGlDescription());
             codDetails.setExpenseTotalAmount(expenseDetlsTO.getAmount());
             codDetailTOs.add(codDetails);
          } 
    	 if(!StringUtil.isEmptyDouble(expenseDetlsTO.getEducationCess())){
             codDetails=new CodReceiptDetailsTO();
             codDetails.setExpenseDescription(expenseDetlsTO.getGlDescription());
             codDetails.setExpenseTotalAmount(expenseDetlsTO.getEducationCess());
             codDetailTOs.add(codDetails);
          } 
    	 
    	 if(!StringUtil.isEmptyDouble(expenseDetlsTO.getHigherEduCess())){
             codDetails=new CodReceiptDetailsTO();
             codDetails.setExpenseDescription(expenseDetlsTO.getGlDescription());
             codDetails.setExpenseTotalAmount(expenseDetlsTO.getHigherEduCess());
             codDetailTOs.add(codDetails);
          } 
    	 
    	 if(!StringUtil.isEmptyDouble(expenseDetlsTO.getOtherCharges())){
             codDetails=new CodReceiptDetailsTO();
             codDetails.setExpenseDescription(expenseDetlsTO.getGlDescription());
             codDetails.setExpenseTotalAmount(expenseDetlsTO.getOtherCharges());
             codDetailTOs.add(codDetails);
          } */
      }
       codReceiptTO.setConsgNo(consg.getConsgNo());
       codReceiptTO.setBookDate(bookingDO.getBookingDate().toString());
       if(!CGCollectionUtils.isEmpty(codDetailTOs)){
         codReceiptTO.setCodReceiptDetailsTOs(codDetailTOs);
         codReceiptTO.setGrandTotal(grandTotal);
       }
     }
      else{
    	  throw new CGBusinessException(UdaanWebErrorConstants.NO_BOOKING_DETAILS_FOUND);
      }
   }  
    else{
    	 throw new CGBusinessException(UdaanWebErrorConstants.NO_CN_DETAILS_FOUND);
     }
     LOGGER.debug("CodReceiptServiceImpl::getConsgDetails::END----->");
	 return codReceiptTO;
	}


   public String generateCodReceiptNumber(String officeCode)
		throws CGBusinessException, CGSystemException {
		
		LOGGER.debug("BillingCommonServiceImpl::generateInvoiceRunsheetNumber::START----->");
		//I+branch code+7 digit
		StringBuilder codReceiptNo = new StringBuilder();
		String runningNumber = null;
		SequenceGeneratorConfigTO sequenceGeneratorConfigTO = new SequenceGeneratorConfigTO();
		sequenceGeneratorConfigTO.setProcessRequesting(CodReceiptConstants.COD_RECEIPT_NO);
		sequenceGeneratorConfigTO.setNoOfSequencesToBegenerated(1);
		sequenceGeneratorConfigTO.setRequestDate(new Date());
		sequenceGeneratorConfigTO = sequenceGeneratorService.getGeneratedSequence(sequenceGeneratorConfigTO);
		sequenceGeneratorConfigTO.getGeneratedSequences();
		
		if(sequenceGeneratorConfigTO.getGeneratedSequences()!=null && 
				sequenceGeneratorConfigTO.getGeneratedSequences().size()>0){
			runningNumber = sequenceGeneratorConfigTO.getGeneratedSequences().get(0);
		}
		
		codReceiptNo
				.append(officeCode).append(runningNumber);
		LOGGER.debug("BillingCommonServiceImpl::generateInvoiceRunsheetNumber::END----->");
		return codReceiptNo.toString();
	}
}
