package com.ff.sap.integration.schedular;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.mec.collection.SAPCollectionDO;
import com.ff.sap.integration.constant.SAPIntegrationConstants;
import com.ff.sap.integration.miscellaneous.service.MiscellaneousSAPIntegrationService;
import com.ff.sap.integration.to.SAPCollectionTO;
import com.firstflight.fi.csdtosap.collectionentry.DTCSDCollectionEntries;
import com.firstflight.fi.csdtosap.collectionentry.SICSDCollectionEntriesOut;

/**
 * @author CBHURE
 *
 */
public class SAPCollectionScheduler  extends QuartzJobBean {

	Logger logger = Logger.getLogger(SAPCollectionScheduler.class);
	private SICSDCollectionEntriesOut client; 
	public MiscellaneousSAPIntegrationService miscSAPService;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		logger.debug("COLLECTION :: SAPCollectionScheduler::executeInternal::start=======>");
		
		DTCSDCollectionEntries collectionEntries = null;
		DTCSDCollectionEntries.CollectionEntries ce = null;
		DTCSDCollectionEntries.CollectionEntries.ItemDetails ced = null;
		
		SAPCollectionTO collectionTO = new SAPCollectionTO();
		collectionTO.setSapStatus(SAPIntegrationConstants.SAP_STATUS);
		collectionTO.setStatus(SAPIntegrationConstants.COLLN_STATUS);
		collectionTO.setMaxCheck(SAPIntegrationConstants.MAX_CHECK);
		List<DTCSDCollectionEntries.CollectionEntries> elements =  null;
		List<DTCSDCollectionEntries.CollectionEntries.ItemDetails> cedList = null;
		List<SAPCollectionDO> sapCollectionDOList = null;
		
		List<DTCSDCollectionEntries.CollectionEntries> ceList = null;
		
		try {
			collectionEntries = new DTCSDCollectionEntries();
			sapCollectionDOList = miscSAPService.findCollectionDtlsForSAPIntegration(collectionTO);
			List<DTCSDCollectionEntries.CollectionEntries.ItemDetails>[] cedAry = new ArrayList[sapCollectionDOList.size()]; 
			String[] txnNo = new String[sapCollectionDOList.size()];
			//List<SAPCollectionDO>[] SAPColAry = new ArrayList[sapCollectionDOList.size()]; 
			boolean flag  = false; 
			int j = -1, k=0;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DecimalFormat decimalFormat = new DecimalFormat("#.##");
			for(SAPCollectionDO sapCollnDO: sapCollectionDOList){
				ceList = collectionEntries.getCollectionEntries();
				flag = false;
				for(int i =0;i<txnNo.length;i++){
					if(txnNo[i] != null && txnNo[i].equals(sapCollnDO.getTxNumber())){
						flag = true;
						if(cedAry[j].size()!=0){
							//SAPColAry[j].add(scDO);
							ced = new DTCSDCollectionEntries.CollectionEntries.ItemDetails();
							//Child Elements
							if((!StringUtil.isStringEmpty(sapCollnDO.getCollectionAgainst()))
									&& !sapCollnDO.getCollectionAgainst().equalsIgnoreCase("O")){
								ced.setCollectionAgainst(sapCollnDO.getCollectionAgainst());
							}else{
								ced.setCollectionAgainst("OA");
							}
							ced.setConsignmentNo(sapCollnDO.getConsgNo());
							ced.setBillNo(sapCollnDO.getBillNo());
							ced.setRemarks(sapCollnDO.getRemarks());
							ced.setReasonCode(String.valueOf(sapCollnDO.getReasonCode()));
							
							// TDS Amount
							if(!StringUtil.isEmptyDouble(sapCollnDO.getTdsAmt())){
								ced.setTDSAmount(decimalFormat.format(sapCollnDO.getTdsAmt().doubleValue()));
							}
							else{
								ced.setTDSAmount("0.00");
							}
							
							// Bill Amount
							if(!StringUtil.isEmptyDouble(sapCollnDO.getBilledAmt())){
								ced.setBilledAmount(decimalFormat.format(sapCollnDO.getBilledAmt().doubleValue()));
							}
							else{
								ced.setBilledAmount("0.00");
							}
							
							// Deduction Amount
							if(!StringUtil.isEmptyDouble(sapCollnDO.getDeduction())){
								ced.setDeduction(decimalFormat.format(sapCollnDO.getDeduction().doubleValue()));
							}
							else{
								ced.setDeduction("0.00");
							}
							
							// Received Amount
							if(!StringUtil.isEmptyDouble(sapCollnDO.getReceivedAmt())){
								ced.setReceivedAmount(decimalFormat.format(sapCollnDO.getReceivedAmt().doubleValue()));
							}
							else{
								ced.setReceivedAmount("0.00");
							}
							cedAry[j].add(ced);
						}
						break;
					}
				}
				if(!flag){
					txnNo[k] = sapCollnDO.getTxNumber();
						j++;
						cedAry[j] = new ArrayList<DTCSDCollectionEntries.CollectionEntries.ItemDetails>();
						
						ce = new DTCSDCollectionEntries.CollectionEntries();
						ced = new DTCSDCollectionEntries.CollectionEntries.ItemDetails();
						//Header Elements
						ce.setBankGLCode(sapCollnDO.getBankGLCode());
						ce.setChequeBank(sapCollnDO.getBankName());
						if(!StringUtil.isNull(sapCollnDO.getChequeDate())){
							GregorianCalendar gregCalenderDdate = new GregorianCalendar();
							gregCalenderDdate.setTime(sapCollnDO.getChequeDate());
							try {
								XMLGregorianCalendar xmlGregCalDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregCalenderDdate);
								ce.setChequeDate(xmlGregCalDate);
							} catch (DatatypeConfigurationException e) {
								logger.error("COLLECTION :: SAPCollectionScheduler::executeInternal::error",e);
							}
						}
						ce.setChequeNo(sapCollnDO.getChequeNo());
						if(!StringUtil.isNull(sapCollnDO.getCollectionDate())){
							GregorianCalendar gregCalenderDdate = new GregorianCalendar();
							gregCalenderDdate.setTime(sapCollnDO.getCollectionDate());
							try {
								XMLGregorianCalendar xmlGregCalDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregCalenderDdate);
								ce.setCreatedDate(xmlGregCalDate);
							} catch (DatatypeConfigurationException e) {
								logger.error("COLLECTION :: SAPCollectionScheduler::executeInternal::error",e);
							}
						}
						ce.setCustomerCode(sapCollnDO.getCustCode());
						if((!StringUtil.isStringEmpty(sapCollnDO.getCollectionAgainst()))
								&& sapCollnDO.getCollectionAgainst().equalsIgnoreCase("W")){
							ce.setModeOfPayment("Cash");
						}else {
							ce.setModeOfPayment(sapCollnDO.getModeOfPayment());
						}
						ce.setTransactionNo(sapCollnDO.getTxNumber());
						ce.setBranch(sapCollnDO.getOfficeCode());
						Date today = Calendar.getInstance().getTime();        
						String dateStamp = df.format(today);
						ce.setTimestamp(dateStamp);
						
						//Child Elements
						if((!StringUtil.isStringEmpty(sapCollnDO.getCollectionAgainst()))
								&& !sapCollnDO.getCollectionAgainst().equalsIgnoreCase("O")){
							ced.setCollectionAgainst(sapCollnDO.getCollectionAgainst());
						}else{
							ced.setCollectionAgainst("OA");
						}
						ced.setConsignmentNo(sapCollnDO.getConsgNo());
						ced.setBillNo(sapCollnDO.getBillNo());
						ced.setRemarks(sapCollnDO.getRemarks());
						ced.setReasonCode(String.valueOf(sapCollnDO.getReasonCode()));
						
						// TDS Amount
						if(!StringUtil.isEmptyDouble(sapCollnDO.getTdsAmt())){
							ced.setTDSAmount(decimalFormat.format(sapCollnDO.getTdsAmt().doubleValue()));
						}
						else{
							ced.setTDSAmount("0.00");
						}
						
						// Bill Amount
						if(!StringUtil.isEmptyDouble(sapCollnDO.getBilledAmt())){
							ced.setBilledAmount(decimalFormat.format(sapCollnDO.getBilledAmt().doubleValue()));
						}
						else{
							ced.setBilledAmount("0.00");
						}
						
						// Deduction Amount
						if(!StringUtil.isEmptyDouble(sapCollnDO.getDeduction())){
							ced.setDeduction(decimalFormat.format(sapCollnDO.getDeduction().doubleValue()));
						}
						else{
							ced.setDeduction("0.00");
						}
						
						// Received Amount
						if(!StringUtil.isEmptyDouble(sapCollnDO.getReceivedAmt())){
							ced.setReceivedAmount(decimalFormat.format(sapCollnDO.getReceivedAmt().doubleValue()));
						}
						else{
							ced.setReceivedAmount("0.00");
						}
						cedAry[j].add(ced);
						ce.itemDetails = cedAry[j];
						ceList.add(ce);
					k++;
				}
			}
		} catch (CGSystemException | CGBusinessException e) {
			logger.error("COLLECTION :: SAPCollectionScheduler..",e);
		}
		if(!StringUtil.isEmptyList(collectionEntries.getCollectionEntries())){
			String sapStatus = null;
			String exception = null;
			try{
				client.siCSDCollectionEntriesOut(collectionEntries);
				sapStatus = SAPIntegrationConstants.SAP_STATUS_C; 
			}catch(Exception e){
				sapStatus = SAPIntegrationConstants.SAP_STATUS;
				exception = e.getMessage();
				logger.debug("COLLECTION :: Error is "+e);
			}
			finally{
				try {
					miscSAPService.updateCollnStagingStatusFlag(sapCollectionDOList,sapStatus,exception);
				} catch (CGSystemException e) {
					logger.error("COLLECTION :: SAPCollectionScheduler :",e); 
				}
			}
		}
		logger.debug("COLLECTION :: SAPCollectionScheduler::executeInternal::after webservice call=======>");
		logger.debug("COLLECTION :: SAPCollectionScheduler::executeInternal::end=======>");
	}

	/**
	 * @param miscSAPService the miscSAPService to set
	 */
	public void setMiscSAPService(MiscellaneousSAPIntegrationService miscSAPService) {
		this.miscSAPService = miscSAPService;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(SICSDCollectionEntriesOut client) {
		this.client = client;
	}
	
}

