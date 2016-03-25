package com.ff.admin.mec.pettycash.service;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.ff.admin.mec.common.service.MECCommonService;
import com.ff.admin.mec.pettycash.dao.PettyCashReportDAO;
import com.ff.domain.mec.pettycash.PettyCashReportDO;

public class PettyCashClosingBalanceParallelCalculation implements Callable<Map<Integer,String>>{
	private final static Logger LOGGER = LoggerFactory.getLogger(PettyCashClosingBalanceParallelCalculation.class);
	
	private MECCommonService mecCommonService;
	private Map<Integer,String> inputMap;
	private PettyCashReportService pettyCashReportService;
	private PettyCashReportDAO pettyCashReportDAO;
	private String currentDateString;
	
	// Default Constructor
	public PettyCashClosingBalanceParallelCalculation() {
		// TODO Auto-generated constructor stub
	}
	
	// Parameterized Constructor
	public PettyCashClosingBalanceParallelCalculation(Map<Integer,String> inputMap, MECCommonService mecCommonService, 
			PettyCashReportService pettyCashReportService, PettyCashReportDAO pettyCashReportDAO, String currentDateString) {
		this.inputMap = inputMap;
		this.mecCommonService = mecCommonService;
		this.pettyCashReportService = pettyCashReportService;
		this.pettyCashReportDAO = pettyCashReportDAO;
		this.currentDateString = currentDateString;
	}
	
	@Override
	public Map<Integer,String> call() throws Exception {
		Map<Integer,String> errorOfficesMap = new LinkedHashMap<>();
		calculateClosingBalanceFromStartDate(this.inputMap, errorOfficesMap, currentDateString);
		return errorOfficesMap;
	}
	
	
	private void calculateClosingBalanceFromStartDate(Map<Integer,String> recalculationOfficeInfoMap, 
			Map<Integer,String> errorOfficesMap, String currentDateString){
		/* Iterate the list of offices and calculate the date range for each office */
		if(recalculationOfficeInfoMap != null && !recalculationOfficeInfoMap.isEmpty()){
			for(Map.Entry<Integer, String> entry : recalculationOfficeInfoMap.entrySet()){
				Integer officeId = entry.getKey();
				String startDateString = entry.getValue();
				try{
					Calendar startDate = Calendar.getInstance();
					startDate.setTime(DateUtil.getDateFromString(startDateString, FrameworkConstants.DDMMYYYY_SLASH_FORMAT));
					Calendar currentDate = Calendar.getInstance();
					currentDate.setTime(DateUtil.getDateFromString(mecCommonService.decreaseDateByOne(currentDateString), FrameworkConstants.DDMMYYYY_SLASH_FORMAT));
					// currentDate.add(Calendar.DAY_OF_MONTH, -1);
					
					/* Date Loop will calculate the closing balance for a given office from startDate to today's date */
					while(!startDate.after(currentDate)){
						/* Re-Calculate Petty Cash Details */
						PettyCashReportDO pettyCashReportDo = 
								pettyCashReportService.calculatePettyCashReportDtls(officeId, startDateString, mecCommonService.increaseDateByDays(startDateString, 1));
						
						/* Save or update petty cash details */
					    pettyCashReportDAO.saveOrUpdatePettyCashReportDetails(pettyCashReportDo,pettyCashReportService.getStatusToUpdateInBookingTable());
						LOGGER.warn("PettyCashReportServiceImpl :: recalculateClosingBalanceForOffices() :: [" + officeId
								+ ", " + startDateString + ", " + pettyCashReportDo.getClosingBalance() + "]");
						
						/* Incrementing the start date by 1 */
						startDate.add(Calendar.DAY_OF_MONTH, 1);
						startDateString = DateUtil.getDDMMYYYYDateToString(startDate.getTime());
					}
				}
				catch(Exception e){
					LOGGER.error("PettyCashReportServiceImpl :: executePettyCashRecalculation() :: ERROR", e);
					errorOfficesMap.put(officeId, startDateString);
				}
			} // end of for office Id loop
		}
	}
}
