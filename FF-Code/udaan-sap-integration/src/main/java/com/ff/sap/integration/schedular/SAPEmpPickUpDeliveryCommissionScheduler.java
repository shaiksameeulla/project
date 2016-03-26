/**
 * SAP Employee PickUp and Delivery Commission Calculation Count Scheduler.
 */
package com.ff.sap.integration.schedular;

import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.httpclient.HttpException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.common.ConfigurableParamsDO;
import com.ff.domain.pndcommission.SAPDeliveryCommissionCalcDO;
import com.ff.sap.integration.constant.SAPIntegrationConstants;
import com.ff.sap.integration.material.stock.StockSAPIntegrationService;
import com.ff.sap.integration.to.SAPErrorTO;
import com.firstflight.hr.csdtosap.emppickupdelivery.DTCSDEmpPickUpDelivery;
import com.firstflight.hr.csdtosap.emppickupdelivery.SICSDEmpPickUpDeliveryOut;

/**
 * @author hkansagr
 */
public class SAPEmpPickUpDeliveryCommissionScheduler extends QuartzJobBean {

	/** The LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SAPEmpPickUpDeliveryCommissionScheduler.class);

	/** The integrationService. */
	private StockSAPIntegrationService integrationService;

	/** The client. */
	private SICSDEmpPickUpDeliveryOut client;

	/**
	 * @param integrationService
	 *            the integrationService to set
	 */
	public void setIntegrationService(
			StockSAPIntegrationService integrationService) {
		this.integrationService = integrationService;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(SICSDEmpPickUpDeliveryOut client) {
		this.client = client;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		LOGGER.trace("SAPEmpPickUpDeliveryCommissionScheduler :: Scheduler :: executeInternal() :: START");
		try {
			executePndCommission();
		}catch (HttpException e) {
			LOGGER.error("SAPEmpPickUpDeliveryCommissionScheduler::executeInternal::HttpException::" ,e);
		} catch (ClassNotFoundException e) {
			LOGGER.error("SAPEmpPickUpDeliveryCommissionScheduler::executeInternal::ClassNotFoundException::",e);
		} catch (IOException e) {
			LOGGER.error("SAPEmpPickUpDeliveryCommissionScheduler::executeInternal::IOException::" ,e);
		} catch(Exception e) {
			LOGGER.error("SAPEmpPickUpDeliveryCommissionScheduler::executeInternal::Exception::" , e);
		}
		LOGGER.trace("SAPEmpPickUpDeliveryCommissionScheduler :: Scheduler :: executeInternal() :: END");
	}

	/**
	 * To execute PnD Commission scheduler to send data to CSD to SAP.
	 * 
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private void executePndCommission() throws CGBusinessException,
			CGSystemException,HttpException, ClassNotFoundException, IOException {
		LOGGER.trace("SAPEmpPickUpDeliveryCommissionScheduler :: Scheduler :: executePndCommission() :: START");
		DTCSDEmpPickUpDelivery dtcsdEmpPickUpDelivery = null;
		List<DTCSDEmpPickUpDelivery.EmpPickUpDelivery> empPnDList = null;
		try {
			// The max data count limit
			Long maxDataCountLimit = 0l;
			Long totalRecords = 0l;

			// To get max data count limit for SAP integration
			ConfigurableParamsDO configParamDO = integrationService
					.getMaxDataCount(SAPIntegrationConstants.MAX_CHECK);
			if (!StringUtil.isNull(configParamDO)) {
				maxDataCountLimit = Long.valueOf(configParamDO.getParamValue());
			}

			// To get data from CSD (To search delivery commission)
			List<SAPDeliveryCommissionCalcDO> sapDlvCommDOs = integrationService
					.getSAPDlvCommissionDtls();

			if (!CGCollectionUtils.isEmpty(sapDlvCommDOs)) {
				totalRecords = (long) sapDlvCommDOs.size();
				Long count = 0l;
				Long totalCount = 0l;

				// To set CSD data to SAP employee pickup delivery client list
				dtcsdEmpPickUpDelivery = new DTCSDEmpPickUpDelivery();
				empPnDList = dtcsdEmpPickUpDelivery.getEmpPickUpDelivery();

				for (SAPDeliveryCommissionCalcDO sapDlvCommDO : sapDlvCommDOs) {

					DTCSDEmpPickUpDelivery.EmpPickUpDelivery empPnD = new DTCSDEmpPickUpDelivery.EmpPickUpDelivery();

					// To set SAP delivery commission details to client object
					empPnD.setEMPLOYEECODE(sapDlvCommDO.getEmployeeCode());
					empPnD.setPRODUCTGROUP(sapDlvCommDO.getProductGroup());
					// CALCULATED FOR PERIOD - To convert CSD date format to SAP
					// date format.
					GregorianCalendar gregCalenderDdate = new GregorianCalendar();
					gregCalenderDdate.setTime(sapDlvCommDO
							.getCalculatedForPeriod());
					try {
						XMLGregorianCalendar xmlGregCalDate = DatatypeFactory
								.newInstance().newXMLGregorianCalendar(
										gregCalenderDdate);
						empPnD.setCALCULATEDFORPERIOD(xmlGregCalDate);
					} catch (DatatypeConfigurationException e) {
						LOGGER.error(
								"Exception occurs in SAPEmpPickUpDeliveryCommissionScheduler :: executePndCommission() :: DATE-CONVERSION",
								e);
					}
					empPnD.setDELIVEREDCOUNT(sapDlvCommDO.getDeliveredCount()
							.toString());
					empPnD.setDELIVEREDDAY1(sapDlvCommDO.getDlvDay1()
							.toString());
					empPnD.setDELIVEREDDAY2(sapDlvCommDO.getDlvDay2()
							.toString());
					empPnD.setDELIVEREDDAY3(sapDlvCommDO.getDlvDay3()
							.toString());
					empPnD.setDELIVEREDDAY4BEYOND(sapDlvCommDO
							.getDlvDay4Beyond().toString());
					empPnDList.add(empPnD);

					// Setting Flag and time stamp
					sapDlvCommDO
							.setSapStatus(SAPIntegrationConstants.SAP_STATUS_C);
					sapDlvCommDO.setSapTimestamp(DateUtil.getCurrentDate());

					/*
					 * If max limit reached, then send through web service
					 * client
					 */
					count++;
					totalCount++;
					if (maxDataCountLimit.longValue() == count.longValue()
							|| totalRecords.longValue() == totalCount
									.longValue()) {
						count = 0l;

						// Calling SAP employee pickup delivery client program
						client.siCSDEmpPickUpDeliveryOut(dtcsdEmpPickUpDelivery);

						// If max limit reached, then create next new data list
						dtcsdEmpPickUpDelivery = new DTCSDEmpPickUpDelivery();
						empPnDList = dtcsdEmpPickUpDelivery
								.getEmpPickUpDelivery();

					}
				} // END of FOR LOOP 1

				// To save or update sap delivery commission flags and
				// time stamp.
				List<SAPErrorTO> sapErroTOlist = new ArrayList<SAPErrorTO>();
				for (SAPDeliveryCommissionCalcDO sapDlvCommDO : sapDlvCommDOs) {
					try {
						integrationService
								.saveOrUpdateSAPDlvCommissionDtls(sapDlvCommDO);
					} catch (Exception e) {
						LOGGER.error(
								"Exception occurs in SAPEmpPickUpDeliveryCommissionScheduler :: executePndCommission() :: INNER-TRY-CATCH :: ",
								e);
						sapErroTOlist
								.add(errorEmailTriggering(sapDlvCommDO, e));
					}
				} // END of FOR LOOP 2

				if (!CGCollectionUtils.isEmpty(sapErroTOlist)) {
					try {
						integrationService
								.errorEmailTriggering(
										sapErroTOlist,
										SAPIntegrationConstants.STK_CANCELLATION_EMAIL_TEMPLATE_NAME,
										"Employee PickUp Delivery Commission Error Records");
					} catch (Exception e) {
						LOGGER.error(
								"Exception occurs in SAPEmpPickUpDeliveryCommissionScheduler :: executePndCommission() :: EMAIL-TRIGGERING :: ",
								e);
					}
				}

			}// END of IF

		} catch (Exception e) {
			LOGGER.error(
					"SAPEmpPickUpDeliveryCommissionScheduler :: executePndCommission() :: ",
					e);
			throw new CGBusinessException(e);
		}
		LOGGER.trace("SAPEmpPickUpDeliveryCommissionScheduler :: Scheduler :: executePndCommission() :: END");
	}

	private SAPErrorTO errorEmailTriggering(
			SAPDeliveryCommissionCalcDO sapDlvCommDO, Exception e) {
		SAPErrorTO errorTO = new SAPErrorTO();
		if (!StringUtil.isStringEmpty(e.getCause().getCause().getMessage())) {
			errorTO.setErrorMessage(e.getCause().getCause().getMessage());
		}
		if (!StringUtil.isStringEmpty(sapDlvCommDO.getEmployeeCode())) {
			errorTO.setTransactionNo(sapDlvCommDO.getEmployeeCode());
		}
		return errorTO;
	}

}
