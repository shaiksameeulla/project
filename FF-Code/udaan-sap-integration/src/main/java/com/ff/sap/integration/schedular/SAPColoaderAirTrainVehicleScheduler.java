package com.ff.sap.integration.schedular;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.coloading.SAPColoaderRatesDO;
import com.ff.sap.integration.constant.SAPIntegrationConstants;
import com.ff.sap.integration.material.stock.StockSAPIntegrationService;
import com.ff.sap.integration.to.SAPColoaderTO;
import com.firstflight.mm.csdtosap.serviceentrycreation.DTCSDServiceEntryCreation;
import com.firstflight.mm.csdtosap.serviceentrycreation.SICSDServiceEntryCreationOut;

/**
 * @author cbhure
 *
 */
public class SAPColoaderAirTrainVehicleScheduler  extends QuartzJobBean {

	Logger logger = Logger.getLogger(SAPColoaderAirTrainVehicleScheduler.class);
	//private SICSDPurchaseRequisitionOut client; 
	public StockSAPIntegrationService coloaderSAPService;
	private SICSDServiceEntryCreationOut client;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		logger.debug("COLOADER :: SAPColoaderAirTrainVehicleScheduler :: executeInternal :: Start");
		String sapStatus = null;
		String exception = null;
		DTCSDServiceEntryCreation coloader = null;
		DTCSDServiceEntryCreation.ServiceEntryCreation se = null;
		
		
		SAPColoaderTO sapColoaderTO = new SAPColoaderTO();
		sapColoaderTO.setSapStatus(SAPIntegrationConstants.SAP_STATUS);
		sapColoaderTO.setMaxCheck(SAPIntegrationConstants.MAX_CHECK);
		List<DTCSDServiceEntryCreation.ServiceEntryCreation> elements =  null;
		
		List<SAPColoaderRatesDO> sapColoaderList = null;
		try {
			coloader = new DTCSDServiceEntryCreation();
			sapColoaderList = coloaderSAPService.findColoaderAirTrainVehicleDtls(sapColoaderTO);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			for(SAPColoaderRatesDO sapColoaderDO : sapColoaderList){
				elements =  coloader.getServiceEntryCreation();
				se = new DTCSDServiceEntryCreation.ServiceEntryCreation();
				if(!StringUtil.isNull(sapColoaderDO)){ 
					
					if(!StringUtil.isNull(sapColoaderDO.getDispatchDate())){
						se.setTripSheetDate(DateUtil.getDDMMYYYYDateToString(sapColoaderDO.getDispatchDate()));
					}
					logger.debug("Trip Sheet Date--------------->"+se.getTripSheetDate());
					
					if(!StringUtil.isStringEmpty(sapColoaderDO.getTripShitNumber())){
						se.setTripSheetNo(sapColoaderDO.getTripShitNumber());
					}
					logger.debug("getTripShitNumber -------------->"+se.getTripSheetNo());
					
					if(!StringUtil.isStringEmpty(sapColoaderDO.getVendorName())){
						se.setNameOfVendor(sapColoaderDO.getVendorName());
					}
					logger.debug("Name Of Vendor--------------->"+se.getNameOfVendor());
					
					if(!StringUtil.isStringEmpty(sapColoaderDO.getSubRateType())){
						se.setSubServiceType(sapColoaderDO.getSubRateType());
					}
					logger.debug("Sub Service Type --------------->"+se.getSubServiceType());
					
					if(!StringUtil.isStringEmpty(sapColoaderDO.getAwbCDRRNumber())){
						se.setWayBill(sapColoaderDO.getAwbCDRRNumber());
						se.setRRNo(sapColoaderDO.getAwbCDRRNumber());
					}
					logger.debug("WAy Bill/CD/RR Number --------------->"+se.getWayBill());
					
					if(!StringUtil.isStringEmpty(sapColoaderDO.getTransportRefNumber())){
						se.setFlightNo(sapColoaderDO.getTransportRefNumber());
						//se.setDestination(sapColoaderDO.getTransportRefNumber());
					}
					logger.debug("Flight No or Vehicle No--------------->"+se.getFlightNo());
					
					if(!StringUtil.isStringEmpty(sapColoaderDO.getDestinationCity())){
						se.setDestination(sapColoaderDO.getDestinationCity());
					}
					logger.debug("Destinattion----->"+se.getDestination());
					
					if(!StringUtil.isEmptyDouble(sapColoaderDO.getBasic())){
						//se.setGrossPrice(String.valueOf(sapColoaderDO.getBasic()));
						se.setRent(String.valueOf(sapColoaderDO.getBasic()));
					} 
					logger.debug("Gross Price and Rent -------------->"+se.getGrossPrice());
					
					if(!StringUtil.isEmptyDouble(sapColoaderDO.getGrossTotal())){
						se.setTotalPrice(String.valueOf(sapColoaderDO.getGrossTotal()));
						se.setGrossPrice(String.valueOf(sapColoaderDO.getGrossTotal()));
					}
					logger.debug("Total Price & Gross Price--------------->"+se.getTotalPrice());
					
					if(!StringUtil.isStringEmpty(sapColoaderDO.getServiceType())){
						se.setServiceType(sapColoaderDO.getServiceType());
					}
					logger.debug("Ser Type--------------->"+se.getServiceType());
					
					if(!StringUtil.isStringEmpty(sapColoaderDO.getUom())){
						se.setUOM(sapColoaderDO.getUom());
					}
					logger.debug("UOM--------------->"+se.getUOM());
					
					if(!StringUtil.isNull(sapColoaderDO.getQty())){
						se.setQuantity(String.valueOf(sapColoaderDO.getQty()));
					}
					logger.debug("QTY ---------->"+se.getQuantity());
					
					
					if(!StringUtil.isStringEmpty(sapColoaderDO.getOffCode())){
						se.setPlant(sapColoaderDO.getOffCode());
					}
					logger.debug("Ofc Code --------------->"+se.getPlant());
					
					
					if(!StringUtil.isEmptyInteger(sapColoaderDO.getTransactionNumber())){
						se.setTranscationNo(String.valueOf(sapColoaderDO.getTransactionNumber()));
					}
					logger.debug("Transaction No --------------->"+se.getTranscationNo());
					
					if(!StringUtil.isEmptyDouble(sapColoaderDO.getOtherCharges())){
						se.setPriceOthers(String.valueOf(sapColoaderDO.getOtherCharges()));
					}
					logger.debug("Price Others ---------->"+se.getPriceOthers());
					
					//Total,SAP timestamp and Trip Ship date is not ib xmlGrogorian format Field is missing in WSDL
					if(!StringUtil.isEmptyDouble(sapColoaderDO.getTotal())){
						se.setTotalPrice(String.valueOf(sapColoaderDO.getTotal()));
					}
					logger.debug("Total   Price ---------->"+se.getPriceOthers());
					
					/*if(!StringUtil.isNull(sapColoaderDO.getTransactionCreateDate())){
						GregorianCalendar gregCalenderDdate = new GregorianCalendar();
						gregCalenderDdate.setTime(sapColoaderDO.getTransactionCreateDate());
						try {
							XMLGregorianCalendar xmlGregCalDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregCalenderDdate);
							se.setREQCREATEDDATETIME(xmlGregCalDate);
						} catch (DatatypeConfigurationException e) {
						}
					}*/
					
					//Date today = Calendar.getInstance().getTime();        
					//String dateStamp = df.format(today);
					//se.setTimestamp(dateStamp);
					elements.add(se);
				}
			}
		}catch (CGBusinessException e) {
			logger.error("COLOADER :: Error In :: SAPColoaderAirTrainVehicleScheduler :: executeInternal",e);
		}
		if(!StringUtil.isEmptyList(coloader.getServiceEntryCreation())){
			try{
				client.siCSDServiceEntryCreationOut(coloader);
				sapStatus = "C"; 
			}catch(Exception e){
				sapStatus = "N";
				exception = e.getMessage();
				logger.error("COLOADER :: Error In :: SAPColoaderAirTrainVehicleScheduler :: executeInternal",e);
			}
			finally{
				try {
					coloaderSAPService.updateColoaderStagingStatusFlag(sapStatus,sapColoaderList,exception);
				} catch (CGSystemException e) {
					logger.error("COLOADER :: Error In :: updateColoaderStagingStatusFlag :: executeInternal",e);
				}
			}
		}
		logger.debug("COLOADER :: SAPColoaderAirTrainVehicleScheduler :: executeInternal :: After webservice call");
		logger.debug("COLOADER :: SAPColoaderAirTrainVehicleScheduler :: executeInternal :: End");
	}
	
	

	/**
	 * @param coloaderSAPService the coloaderSAPService to set
	 */
	public void setColoaderSAPService(StockSAPIntegrationService coloaderSAPService) {
		this.coloaderSAPService = coloaderSAPService;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(SICSDServiceEntryCreationOut client) {
		this.client = client;
	}
	
	
}

