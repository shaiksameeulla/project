package com.ff.sap.integration.schedular;

import java.io.IOException;
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
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.common.ConfigurableParamsDO;
import com.ff.domain.stockmanagement.operations.reduction.SAPStockConsolidationDO;
import com.ff.sap.integration.constant.SAPIntegrationConstants;
import com.ff.sap.integration.material.stock.StockSAPIntegrationService;
import com.ff.universe.stockmanagement.constant.StockUniveralConstants;
import com.firstflight.mm.csdtosap.stockconsumption.DTCSDStockConsumption;
import com.firstflight.mm.csdtosap.stockconsumption.SICSDStockConsumptionOut;

/**
 * @author hkansagr
 * 
 */
public class SAPStockConsumptionScheduler extends QuartzJobBean {

	/** The LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SAPStockConsumptionScheduler.class);

	/** The stockSAPIntegrationService. */
	public StockSAPIntegrationService stockConsumptionService;

	/** The SI CSD Stock Consumption Client. */
	private SICSDStockConsumptionOut client;

	/**
	 * @param stockConsumptionService
	 *            the stockConsumptionService to set
	 */
	public void setStockConsumptionService(
			StockSAPIntegrationService stockConsumptionService) {
		this.stockConsumptionService = stockConsumptionService;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(SICSDStockConsumptionOut client) {
		this.client = client;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		LOGGER.trace("STOCKCONSUMPTION :: SAPStockConsumptionScheduler :: Scheduler :: executeInternal() :: START");
		try {
			executeSAPStockConsumptionIntegrator();
		}catch (HttpException e) {
			LOGGER.error("SAPStockConsumptionScheduler::executeInternal::HttpException::",e);
		} catch (ClassNotFoundException e) {
			LOGGER.error("SAPStockConsumptionScheduler::executeInternal::ClassNotFoundException::" ,e);
		} catch (IOException e) {
			LOGGER.error("SAPStockConsumptionScheduler::executeInternal::IOException::" ,e);
		} catch(Exception e) {
			LOGGER.error("SAPStockConsumptionScheduler::executeInternal::Exception::" , e);
		}
		LOGGER.trace("SAPStockConsumptionScheduler :: Scheduler :: executeInternal() :: END");
	}

	/**
	 * To execute stock consumption scheduler to send data from CSD to SAP
	 * 
	 * @throws Exception
	 */
	private void executeSAPStockConsumptionIntegrator() throws Exception {
		LOGGER.trace("STOCKCONSUMPTION :: SAPStockConsumptionScheduler :: executeSAPStockConsumptionIntegrator() :: START");
		DTCSDStockConsumption dtcsdStockConsumption = null;
		List<DTCSDStockConsumption.StockConsumption> stockConsumptionList = null;
		try {
			// The max data count limit
			Long maxDataCountLimit = 0l;
			Long totalRecords = 0l;

			// To get max data count limit for SAP integration
			ConfigurableParamsDO configParamDO = stockConsumptionService
					.getMaxDataCount(SAPIntegrationConstants.MAX_CHECK);
			if (!StringUtil.isNull(configParamDO)) {
				maxDataCountLimit = Long.valueOf(configParamDO.getParamValue());
			}

			// To get data from CSD (To search stock consumption details)
			List<SAPStockConsolidationDO> sapStckConsolidationDOs = stockConsumptionService
					.getSAPStockConsolidationDtls();
			if (!CGCollectionUtils.isEmpty(sapStckConsolidationDOs)) {
				totalRecords = (long) sapStckConsolidationDOs.size();
				// To set CSD data to SAP stock consumption client list
				dtcsdStockConsumption = new DTCSDStockConsumption();
				stockConsumptionList = dtcsdStockConsumption
						.getStockConsumption();
				Long count = 0l;
				Long totalCount = 0l;
				for (SAPStockConsolidationDO sapStockDO : sapStckConsolidationDOs) {
					DTCSDStockConsumption.StockConsumption stockConsumption = new DTCSDStockConsumption.StockConsumption();

					// Setting booking date - transaction date
					GregorianCalendar gregCalenderDdate = new GregorianCalendar();
					gregCalenderDdate.setTime(sapStockDO.getTransactionDate());
					try {
						XMLGregorianCalendar xmlGregCalDate = DatatypeFactory
								.newInstance().newXMLGregorianCalendar(
										gregCalenderDdate);
						stockConsumption.setBookingDate(xmlGregCalDate);
					} catch (DatatypeConfigurationException e) {
						LOGGER.error(
								"Exception occurs in SAPStockConsumptionScheduler :: executeSAPStockConsumptionIntegrator() ::",
								e);
					}

					// Setting logged in plant code - transaction office code
					stockConsumption.setLoggedInPlant(sapStockDO
							.getTransactionCreatedOfficeDO().getOfficeCode());

					// Setting material code - item code
					stockConsumption.setMaterial(sapStockDO.getItemDO()
							.getItemCode());

					// Setting quantity - consumed stock quantity
					stockConsumption.setQuantity(String.valueOf(sapStockDO
							.getConsumedStockQuantity()));

					// Setting transaction number - generated transaction number
					stockConsumption.setTransactionNo(sapStockDO
							.getGeneratedTransactionNumber());

					// Setting sap transfer status. (T - Transferred)
					sapStockDO
							.setSapTransferStatus(StockUniveralConstants.SAP_TRANSFER_STATUS);

					stockConsumptionList.add(stockConsumption);

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
						// Calling SAP stock consumption client program
						client.siCSDStockConsumptionOut(dtcsdStockConsumption);

						// If max limit reached, then create next new data list
						dtcsdStockConsumption = new DTCSDStockConsumption();
						stockConsumptionList = dtcsdStockConsumption
								.getStockConsumption();
					}
				}

				// To save/update sap stock consolidation flag (save or update)
				for (SAPStockConsolidationDO sapStockDO : sapStckConsolidationDOs) {
					try {
						stockConsumptionService
								.saveStockConsolidationDtls(sapStockDO);
					} catch (Exception e) {
						LOGGER.error("Exception occurs in SAPStockConsumptionScheduler :: executeSAPStockConsumptionIntegrator() ::",e);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("STOCKCONSUMPTION :: SAPStockConsumptionScheduler :: executeSAPStockConsumptionIntegrator() :: ",e);
			throw new CGBusinessException(e);
		}
		LOGGER.trace("STOCKCONSUMPTION :: SAPStockConsumptionScheduler :: executeSAPStockConsumptionIntegrator() :: END");
	}

}
