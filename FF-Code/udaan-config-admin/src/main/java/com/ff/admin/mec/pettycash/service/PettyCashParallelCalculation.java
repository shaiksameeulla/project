package com.ff.admin.mec.pettycash.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.ff.admin.mec.common.service.MECCommonService;
import com.ff.admin.mec.pettycash.dao.PettyCashReportDAO;
import com.ff.domain.mec.pettycash.PettyCashReportDO;

public class PettyCashParallelCalculation implements Callable<Map<Integer,List<String>>>{
	private final static Logger LOGGER = LoggerFactory.getLogger(PettyCashParallelCalculation.class);
	
	private MECCommonService mecCommonService;
	private Map<Integer,List<String>> inputMap;
	private PettyCashReportService pettyCashReportService;
	private PettyCashReportDAO pettyCashReportDAO;
	
	// Default Constructor
	public PettyCashParallelCalculation() {
		// TODO Auto-generated constructor stub
	}
	
	// Parameterized Constructor
	public PettyCashParallelCalculation(Map<Integer,List<String>> inputMap, MECCommonService mecCommonService, 
			PettyCashReportService pettyCashReportService, PettyCashReportDAO pettyCashReportDAO) {
		this.inputMap = inputMap;
		this.mecCommonService = mecCommonService;
		this.pettyCashReportService = pettyCashReportService;
		this.pettyCashReportDAO = pettyCashReportDAO;
	}
	
	@Override
	public Map<Integer,List<String>> call() throws Exception {
		Map<Integer,List<String>> errorOfficesInformationLocalMap = new LinkedHashMap<>();
		errorOfficesInformationLocalMap = calculateClosingBalanceFromStartDate(this.inputMap, errorOfficesInformationLocalMap);
		return errorOfficesInformationLocalMap;
	}
	
	private Map<Integer,List<String>> calculateClosingBalanceFromStartDate(Map<Integer,List<String>> recalculationOfficeInfoMap, 
			Map<Integer,List<String>> errorOfficesInformationLocalMap) {
		LOGGER.debug("PettyCashParallelCalculation :: calculateClosingBalanceFromStartDate() :: START");
		/* Iterate the list of offices and calculate the date range for each office */
		if(!CollectionUtils.isEmpty(recalculationOfficeInfoMap)) {
			for(Map.Entry<Integer, List<String>> entry : recalculationOfficeInfoMap.entrySet()) {
				Integer officeId = entry.getKey();
				List<String> dateStringList = entry.getValue();
				String startDateString = dateStringList.get(0);
				String endDateString = dateStringList.get(1);
				
				try{
					Calendar startDate = Calendar.getInstance();
					startDate.setTime(DateUtil.getDateFromString(startDateString, FrameworkConstants.DDMMYYYY_SLASH_FORMAT));
					Calendar endDate = Calendar.getInstance();
					endDate.setTime(DateUtil.getDateFromString(endDateString, FrameworkConstants.DDMMYYYY_SLASH_FORMAT));
					endDate.add(Calendar.DAY_OF_MONTH, -1);
					
					/* Date Loop will calculate the closing balance for a given office from startDate to today's date */
					while(!startDate.after(endDate)){
						/* Re-Calculate Petty Cash Details */ 
						PettyCashReportDO pettyCashReportDo = 
								pettyCashReportService.recalculatePettyCashDtls(officeId, startDateString, mecCommonService.increaseDateByDays(startDateString, 1));
						
						/* Save or update petty cash details */
						pettyCashReportDAO.saveOrUpdatePettyCashReportDO(pettyCashReportDo);
						LOGGER.warn("PettyCashReportServiceImpl :: recalculateClosingBalanceForOffices() :: [" + officeId
								+ ", " + startDateString + ", " + pettyCashReportDo.getClosingBalance() + "]");
						
						/* Incrementing the start date by 1 */
						startDate.add(Calendar.DAY_OF_MONTH, 1);
						startDateString = DateUtil.getDDMMYYYYDateToString(startDate.getTime());
					}
				}
				catch(Exception e){
					LOGGER.error("PettyCashParallelCalculation :: calculateClosingBalanceFromStartDate() :: ERROR_OFFICE_ID [" + officeId + 
							", " + startDateString + "]",e);
					List<String> errorDateStringList = new ArrayList<>();
					errorDateStringList.add(startDateString);
					errorDateStringList.add(endDateString);
					errorOfficesInformationLocalMap.put(officeId, errorDateStringList);
				}
			} // end of for loop
		}
		LOGGER.debug("PettyCashParallelCalculation :: calculateClosingBalanceFromStartDate() :: END");
		return errorOfficesInformationLocalMap;
	} // end of method
}
