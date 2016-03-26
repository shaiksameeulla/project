package com.ff.sap.integration.schedular;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.billing.SAPBillingConsignmentSummaryDO;
import com.ff.sap.integration.constant.SAPIntegrationConstants;
import com.ff.sap.integration.sd.service.SDSAPIntegrationService;
import com.ff.sap.integration.to.SAPBillingConsgSummaryTO;
import com.firstflight.sd.csdtosap.salesorder.DTCSDSalesOrder;
import com.firstflight.sd.csdtosap.salesorder.SICSDSalesOrderOut;

/**
 * @author CBHURE
 *
 */
public class SAPBillingConsgSummaryScheduler  extends QuartzJobBean {

	Logger logger = Logger.getLogger(SAPExpenseScheduler.class);
	private SICSDSalesOrderOut client; 
	public SDSAPIntegrationService sdSAPIntegrationServiceForBCS;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPBillingConsgSummaryScheduler :: executeInternal :: start=======>");
		Calendar calendar = Calendar.getInstance();
		DateFormat currentDate = new SimpleDateFormat("EE");
		String currentDay = currentDate.format(calendar.getTime());
		//System.out.println("Day Of Week : " + currentDay);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		//System.out.println(dayOfMonth);
		int BCS_SCHEDULER_START_DAY = 2;
		int BCS_SCHEDULER_START_DAY_FOR_BA = 14;
		Date startDate = null;
		Date endDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		/*if (currentDay.equalsIgnoreCase("SUN") && dayOfMonth >= BCS_SCHEDULER_START_DAY+4) {*/
		if (currentDay.equalsIgnoreCase("SUN")) {
			calendar.set(Calendar.DATE,
					calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
			Date currentMonthFirstDay = calendar.getTime();
			calendar.set(Calendar.DATE,
					calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			Date currentMonthLastDay = calendar.getTime();
			try {
				startDate =  sdf.parse(sdf.format(currentMonthFirstDay));
				endDate =  sdf.parse(sdf.format(currentMonthLastDay));
			} catch (ParseException e) {
				logger.error("BillingSummaryCreationScheduler::executeInternal::Parse Exception::" , e);
			}
		} else if (dayOfMonth == BCS_SCHEDULER_START_DAY){
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.DATE,
					calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
			Date lastMonthFirstDay = calendar.getTime();
			calendar.set(Calendar.DATE,
					calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			Date lastMonthLastDay = calendar.getTime();
			try {
				startDate =  sdf.parse(sdf.format(lastMonthFirstDay));
				endDate =  sdf.parse(sdf.format(lastMonthLastDay));
			} catch (ParseException e) {
				logger.error("BillingSummaryCreationScheduler::executeInternal::Parse Exception::" , e);
			}
		} /*else if (dayOfMonth == BCS_SCHEDULER_START_DAY_FOR_BA){
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.DATE,
					calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
			Date lastMonthFirstDay = calendar.getTime();
			calendar.set(Calendar.DATE,
					calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			Date lastMonthLastDay = calendar.getTime();
			try {
				startDate =  sdf.parse(sdf.format(lastMonthFirstDay));
				endDate =  sdf.parse(sdf.format(lastMonthLastDay));
			} catch (ParseException e) {
				logger.error("BillingSummaryCreationScheduler::executeInternal::Parse Exception::" , e);
			}
		}*/
		//System.out.println("first Date : " + startDate);
		//System.out.println("Last Date : " + endDate);
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BillingRateCalProcessScheduler::executeInternal::Start Date : -----> " + startDate + "  End Date : ----> " + endDate);
		
		SAPBillingConsgSummaryTO sapBillConsgSummaryTO = new SAPBillingConsgSummaryTO();
		sapBillConsgSummaryTO.setSapStatus(SAPIntegrationConstants.SAP_STATUS);
		sapBillConsgSummaryTO.setMaxCheck(SAPIntegrationConstants.MAX_CHECK);
	   /* if (!StringUtil.isNull(startDate) && !StringUtil.isNull(endDate)){*/
			try{
				long start1=System.currentTimeMillis();
				// sdSAPIntegrationServiceForBCS.getConsignmentsForRate();
				
				logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BillingRateCalProcessScheduler::executeInternal::Calling Billing Stored proc start----->");
				long start=System.currentTimeMillis();
			    //calling stored procedure
				sdSAPIntegrationServiceForBCS.billing_consolidation_Proc();
				
				sdSAPIntegrationServiceForBCS.billing_Stock_consolidation_Proc();
				long endTime=System.currentTimeMillis();
				long elapse=endTime-start;
				logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BillingRateCalProcessScheduler::executeInternal::Calling Billing Stored proc end with execution time----->"+elapse);
				long endTime1=System.currentTimeMillis();
				long elapse1=endTime1-start1;
				logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BillingRateCalProcessScheduler::executeInternal::Total Time required for Billing----->"+elapse1);
				
				/** Insert billing Summary Into Staging Table */
				sdSAPIntegrationServiceForBCS
						.insertBillingSummaryIntoSAPStagingTable(sapBillConsgSummaryTO);
				
				/** Send Billing Summary to SAP */
				Long maxDataCountLimit = null; 
				Long totalRecords = 0L;
				Long initialCount;
				//get max limit to send data to SAP
				maxDataCountLimit =  sdSAPIntegrationServiceForBCS.getMaxLimitToSendDataToSAP(sapBillConsgSummaryTO.getMaxCheck());
				/* Get Total billing summary count from staging*/
				totalRecords = sdSAPIntegrationServiceForBCS.getBCSDtlsCountFromStaging(sapBillConsgSummaryTO);
		
				/* Batch processing to sent data to SAP */
				 /**Send BCS to SAP */
				if (!StringUtil.isEmptyLong(totalRecords)) {
					logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPBillingConsgSummaryScheduler :: sendBCSDtlsToSAP :: START=======>");
					for (initialCount = 1l; initialCount <= totalRecords; initialCount = initialCount + maxDataCountLimit) {
						// Fetching BCS Dtls from Staging
						List<SAPBillingConsignmentSummaryDO> sapStkReqDOList =  sdSAPIntegrationServiceForBCS.findBCSDtlsFromStaging(sapBillConsgSummaryTO,maxDataCountLimit);
						// Send details To SAP.
						sendBCSDtlsToSAP(sapStkReqDOList);
					}
					logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPBillingConsgSummaryScheduler :: sendBCSDtlsToSAP :: END=======>");
				} 
			} catch(Exception e) {
				logger.error("BillingSummaryCreationScheduler::executeInternal::Exception::" , e);
			}
	//	}
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SAPBillingConsgSummaryScheduler :: executeInternal :: END=======>");
}

	private void sendBCSDtlsToSAP(
			List<SAPBillingConsignmentSummaryDO> sapBillingConsgSummaryDOList) {
		DTCSDSalesOrder salesOrder = null;
		DTCSDSalesOrder.SalesOrder so = null;
		salesOrder = new DTCSDSalesOrder();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<DTCSDSalesOrder.SalesOrder> soList = null;
		try {
			if (!CGCollectionUtils.isEmpty(sapBillingConsgSummaryDOList)) {
				for (SAPBillingConsignmentSummaryDO sapBillingConsgSummaryDO : sapBillingConsgSummaryDOList) {
					soList = salesOrder.getSalesOrder();
					so = new DTCSDSalesOrder.SalesOrder();

					if (!StringUtil.isNull(sapBillingConsgSummaryDO
							.getBookingDate())) {
						GregorianCalendar gregCalenderDdate = new GregorianCalendar();
						gregCalenderDdate.setTime(sapBillingConsgSummaryDO
								.getBookingDate());
						try {
							XMLGregorianCalendar xmlGregCalDate = DatatypeFactory
									.newInstance().newXMLGregorianCalendar(
											gregCalenderDdate);
							so.setBookingDate(xmlGregCalDate);
						} catch (DatatypeConfigurationException e) {
							logger.debug(
									"BillingSummaryCreationScheduler - for Summary scheduler :: XMLDateParse::sendBCSDtlsToSAP::XML Date Parse ::error::",
									e);
						}
					}
					logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BCS - Booking Date ---->"
							+ so.getBookingDate());

					if (!StringUtil.isStringEmpty(sapBillingConsgSummaryDO
							.getCustShipTo())) {
						so.setCustShipTo(sapBillingConsgSummaryDO
								.getCustShipTo());
					}
					logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BCS - Cust Ship TO ---->"
							+ so.getCustShipTo());

					if (!StringUtil.isStringEmpty(sapBillingConsgSummaryDO
							.getCustSoldTo())) {
						so.setCustSoldTo(sapBillingConsgSummaryDO
								.getCustSoldTo());
					}
					logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BCS - Cust Sold TO ---->"
							+ so.getCustSoldTo());

					if (!StringUtil.isStringEmpty(sapBillingConsgSummaryDO
							.getDistributionChannel())) {
						so.setDistributionChannel(sapBillingConsgSummaryDO
								.getDistributionChannel());
					}
					logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BCS - Dist Channel ---->"
							+ so.getDistributionChannel());

					// Custo Sold to ????????????????
					/*
					 * if(!StringUtil.isStringEmpty(sapBillingConsgSummaryDO.
					 * getDistributionChannel())){
					 * so.setDistributionChannel(sapBillingConsgSummaryDO
					 * .getDistributionChannel()); } logger.debug(
					 * "BillingSummaryCreationScheduler - for Summary scheduler :: BCS - Dist Channel ---->"
					 * +so.getDistributionChannel());
					 */

					if (!StringUtil.isStringEmpty(sapBillingConsgSummaryDO
							.getBookingOffice())) {
						so.setBookingOffice(sapBillingConsgSummaryDO
								.getBookingOffice());
					}
					logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BCS - Booking Ofc ---->"
							+ so.getBookingOffice());

					if (!StringUtil.isEmptyInteger(sapBillingConsgSummaryDO
							.getTransactionNumber())) {
						so.setTrnNo(String.valueOf(sapBillingConsgSummaryDO
								.getTransactionNumber()));
					}
					logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BCS - Transaction No Summary ID---->"
							+ so.getTrnNo());

					if (!StringUtil.isStringEmpty(sapBillingConsgSummaryDO
							.getProductCode())) {
						so.setMaterial(sapBillingConsgSummaryDO
								.getProductCode());
					}
					logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BCS - Material ---->"
							+ so.getMaterial());

					if (!StringUtil.isEmptyInteger(sapBillingConsgSummaryDO
							.getQuantity())) {
						so.setQuantity(sapBillingConsgSummaryDO.getQuantity()
								.toString());
					}
					logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BCS - Quantity ---->"
							+ so.getQuantity());

					if (!StringUtil.isNull(sapBillingConsgSummaryDO
							.getFreight())) {
						so.setFright(BigDecimal.valueOf(
								sapBillingConsgSummaryDO.getFreight())
								.toPlainString());
					}
					logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BCS - Freight ---->"
							+ so.getFright());

					if (!StringUtil.isNull(sapBillingConsgSummaryDO
							.getFuelSurcharge())) {
						so.setFuelSurcharge(BigDecimal.valueOf(
								sapBillingConsgSummaryDO.getFuelSurcharge())
								.toPlainString());
					}
					logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BCS - Fuel Surcharge ---->"
							+ so.getFuelSurcharge());

					if (!StringUtil.isNull(sapBillingConsgSummaryDO
							.getRiskSurcharge())) {
						so.setRiskSurcharge(BigDecimal.valueOf(
								sapBillingConsgSummaryDO.getRiskSurcharge())
								.toPlainString());
					}
					logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BCS - Risk Surcharge ---->"
							+ so.getFuelSurcharge());

					if (!StringUtil.isNull(sapBillingConsgSummaryDO
							.getParcelHandlingCharges())) {
						so.setParcelHandlingCharges(BigDecimal.valueOf(
								sapBillingConsgSummaryDO
										.getParcelHandlingCharges())
								.toPlainString());
					}
					logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BCS - Parcel Handling charge ---->"
							+ so.getParcelHandlingCharges());

					if (!StringUtil.isNull(sapBillingConsgSummaryDO
							.getAirportHandlingCharges())) {
						so.setAirportHandlingCharges(BigDecimal.valueOf(
								sapBillingConsgSummaryDO
										.getAirportHandlingCharges())
								.toPlainString());
					}
					logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BCS - Air Port charge ---->"
							+ so.getAirportHandlingCharges());

					if (!StringUtil.isNull(sapBillingConsgSummaryDO
							.getDocumentHandlingCharges())) {
						so.setDocumentHandlingCharges(BigDecimal.valueOf(
								sapBillingConsgSummaryDO
										.getDocumentHandlingCharges())
								.toPlainString());
					}
					logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BCS - Doc handling chargess ---->"
							+ so.getDocumentHandlingCharges());

					if (!StringUtil.isNull(sapBillingConsgSummaryDO
							.getValueOfMaterial())) {
						so.setValueofMaterial(BigDecimal.valueOf(
								sapBillingConsgSummaryDO.getValueOfMaterial())
								.toPlainString());
					}
					logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BCS - Value Of Material ---->"
							+ so.getValueofMaterial());

					if (!StringUtil.isNull(sapBillingConsgSummaryDO
							.getCodCharges())) {
						so.setCODCharges(BigDecimal.valueOf(
								sapBillingConsgSummaryDO.getCodCharges())
								.toPlainString());
					}
					logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BCS - COD charge ---->"
							+ so.getCODCharges());

					if (!StringUtil.isNull(sapBillingConsgSummaryDO
							.getToPayCharges())) {
						so.setToPayCharges(BigDecimal.valueOf(
								sapBillingConsgSummaryDO.getToPayCharges())
								.toPlainString());
					}
					logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BCS - To Pay Charge ---->"
							+ so.getToPayCharges());

					if (!StringUtil.isNull(sapBillingConsgSummaryDO
							.getLcCharges())) {
						so.setLCCharges(BigDecimal.valueOf(
								sapBillingConsgSummaryDO.getLcCharges())
								.toPlainString());
					}
					logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BCS - LC charge ---->"
							+ so.getLCCharges());

					if (!StringUtil
							.isNull(sapBillingConsgSummaryDO.getOthers())) {
						so.setOthers(BigDecimal.valueOf(
								sapBillingConsgSummaryDO.getOthers())
								.toPlainString());
					}
					logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BCS - Others Charge ---->"
							+ so.getOthers());

					if (!StringUtil.isNull(sapBillingConsgSummaryDO
							.getServiceTax())) {
						so.setServiceTax(BigDecimal.valueOf(
								sapBillingConsgSummaryDO.getServiceTax())
								.toPlainString());
					}
					logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BCS - Service Tax ---->"
							+ so.getServiceTax());

					if (!StringUtil.isNull(sapBillingConsgSummaryDO
							.getEducationCess())) {
						so.setEducationCess(BigDecimal.valueOf(
								sapBillingConsgSummaryDO.getEducationCess())
								.toPlainString());
					}
					logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BCS - Edu Cess ---->"
							+ so.getEducationCess());

					if (!StringUtil.isNull(sapBillingConsgSummaryDO
							.getSecHighEduCess())) {
						so.setHighEduCess(BigDecimal.valueOf(
								sapBillingConsgSummaryDO.getSecHighEduCess())
								.toPlainString());
					}
					logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BCS - High Edu charge ---->"
							+ so.getHighEduCess());

					if (!StringUtil.isNull(sapBillingConsgSummaryDO
							.getStateTax())) {
						so.setStateTax(BigDecimal.valueOf(
								sapBillingConsgSummaryDO.getStateTax())
								.toPlainString());
					}
					logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BCS - State Tax ---->"
							+ so.getStateTax());

					if (!StringUtil.isNull(sapBillingConsgSummaryDO
							.getSurchargeOnStateTax())) {
						so.setSurchargeonStateTax(BigDecimal.valueOf(
								sapBillingConsgSummaryDO
										.getSurchargeOnStateTax())
								.toPlainString());
					}
					logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BCS - State Tax ---->"
							+ so.getSurchargeonStateTax());

					/*
					 * if(!StringUtil.isNull(sapBillingConsgSummaryDO.
					 * getSurchargeOnStateTax())){
					 * so.setSurchargeonStateTax(sapBillingConsgSummaryDO
					 * .getSurchargeOnStateTax().toString()); } logger.debug(
					 * "BillingSummaryCreationScheduler - for Summary scheduler :: BCS - State Tax ---->"
					 * +so.getSurchargeonStateTax());
					 */

					if (!StringUtil.isNull(sapBillingConsgSummaryDO
							.getGrandTotal())) {
						so.setGrandTotal(BigDecimal.valueOf(
								sapBillingConsgSummaryDO.getGrandTotal())
								.toPlainString());
					}
					logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BCS - Grand Toatal ---->"
							+ so.getGrandTotal());

					if (!StringUtil.isStringEmpty(sapBillingConsgSummaryDO
							.getSummaryCategory())) {
						so.setRTO(sapBillingConsgSummaryDO.getSummaryCategory());
					}
					logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BCS - Sum Category RTO ---->"
							+ so.getRTO());

					if (!StringUtil.isStringEmpty(sapBillingConsgSummaryDO
							.getDestinationOfc())) {
						so.setDestinationBranch(sapBillingConsgSummaryDO
								.getDestinationOfc());
					}
					logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BCS - Dest Branch ---->"
							+ so.getDestinationBranch());

					Date today = Calendar.getInstance().getTime();
					String dateStamp = df.format(today);
					so.setTimestamp(dateStamp);
					soList.add(so);
				} // End of for
			}// End of if
		} catch (Exception e) {
			logger.error(
					"BillingSummaryCreationScheduler::executeInternal::Exception::",
					e);
		}
		if (!StringUtil.isEmptyList(salesOrder.getSalesOrder())) {
			String sapStatus = null;
			String exception = null;
			try {
				client.siCSDSalesOrderOut(salesOrder);
				sapStatus = "C";
			} catch (Exception e) {
				sapStatus = "N";
				exception = e.getMessage();
				logger.debug(
						"BillingSummaryCreationScheduler - for Summary scheduler :: Error is ",
						e);
			} finally {
				try {
					sdSAPIntegrationServiceForBCS.updateBCSStagingStatusFlag(
							sapStatus, sapBillingConsgSummaryDOList, exception);
				} catch (CGSystemException e) {
					logger.error(
							"BillingSummaryCreationScheduler - for Summary scheduler :: Error is ",
							e);
				}
			}
		}
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(SICSDSalesOrderOut client) {
		this.client = client;
	}

	/**
	 * @param sdSAPIntegrationServiceForBCS the sdSAPIntegrationServiceForBCS to set
	 */
	public void setSdSAPIntegrationServiceForBCS(
			SDSAPIntegrationService sdSAPIntegrationServiceForBCS) {
		this.sdSAPIntegrationServiceForBCS = sdSAPIntegrationServiceForBCS;
	}
	
}
