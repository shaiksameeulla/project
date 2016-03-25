package com.ff.admin.mec.pettycash.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.mec.common.constants.MECCommonConstants;
import com.ff.admin.mec.common.service.MECCommonService;
import com.ff.admin.mec.pettycash.dao.PettyCashReportDAO;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.common.ConfigurableParamsDO;
import com.ff.domain.geography.RegionDO;
import com.ff.domain.mec.collection.CollectionDO;
import com.ff.domain.mec.collection.CollectionDtlsDO;
import com.ff.domain.mec.pettycash.PettyCashReportDO;
import com.ff.domain.mec.pettycash.PettyCashReportWrapperDO;
import com.ff.to.mec.pettycash.PettyCashReportTO;
import com.ff.universe.global.dao.GlobalUniversalDAO;
import com.google.common.collect.Lists;

/**
 * @author hkansagr
 * 
 */
public class PettyCashReportServiceImpl implements PettyCashReportService {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(PettyCashReportServiceImpl.class);

	/** The pettyCashReportDAO. */
	private PettyCashReportDAO pettyCashReportDAO;

	/** The mecCommonService. */
	private MECCommonService mecCommonService;
	
	private Properties pettyCashRecalculationProperties;
	
	private List<PettyCashReportWrapperDO> pettyCashReportWrapperDoList = null;
	
	private GlobalUniversalDAO globalUniversalDAO;

	/**
	 * @param pettyCashReportDAO
	 *            the pettyCashReportDAO to set
	 */
	public void setPettyCashReportDAO(PettyCashReportDAO pettyCashReportDAO) {
		this.pettyCashReportDAO = pettyCashReportDAO;
	}

	/**
	 * @param mecCommonService
	 *            the mecCommonService to set
	 */
	public void setMecCommonService(MECCommonService mecCommonService) {
		this.mecCommonService = mecCommonService;
	}

	public void setPettyCashRecalculationProperties(
			Properties pettyCashRecalculationProperties) {
		this.pettyCashRecalculationProperties = pettyCashRecalculationProperties;
	}

	public void setGlobalUniversalDAO(GlobalUniversalDAO globalUniversalDAO) {
		this.globalUniversalDAO = globalUniversalDAO;
	}

	@Override
	public void executePettyCashScheduler() throws CGBusinessException,
			CGSystemException {
		LOGGER.warn("PettyCashReportServiceImpl :: executePettyCashScheduler() :: START");
		/* Calculate current date & use it for the current run of petty cash */ 
		String currentDateString = DateUtil.getCurrentDateInDDMMYYYY();
		
		/* Get the window size to be considered for considering late data sync
		 * For eg: if window size = 20, then the maximum period of late data sync allowed will be 20 days */ 
		String maximumAllowableDate = getMaximumAllowableDateForLateDataSync(currentDateString);
		
		/* To submit all saved collection Transaction(s) of last month from todays date */ 
		// 1) Auto-submit any unsaved collections
		autoSubmitCollectionDtls(currentDateString, maximumAllowableDate);

		// 2) Preparing hashmap for petty cash calculation
		Map<Integer,String> pettyCashCalculationHashMap = getOfficeIdAndStartDateHashMap(currentDateString, maximumAllowableDate);
		
		// 3) Prepare input for each thread, calculate the closing balance and save the data in the database
		calculateAndSavePettyCashDtls(pettyCashCalculationHashMap, currentDateString);
		LOGGER.warn("PettyCashReportServiceImpl :: executePettyCashScheduler() :: END");
	}
	
	private Map<Integer,String> getOfficeIdAndStartDateHashMap(String currentDateString, String maximumAllowableDate) 
			throws CGSystemException,CGBusinessException {
		LOGGER.debug("PettyCashReportServiceImpl :: getOfficeIdAndBookingDateHashMap() :: START");
		Map<Integer,String> pettyCashRecalculationHashMap = new LinkedHashMap<>();
		try{
			String currentDateMinusOne = mecCommonService.decreaseDateByOne(currentDateString);
			// 1) Logic to add all active office Ids in the map
			addAllActiveOfficesToHashMap(pettyCashRecalculationHashMap, currentDateMinusOne);
			
			// 2) Logic to add those office Ids whose booking data has synced in late
			addAllLateSyncedBookingOfficesToHashMap(pettyCashRecalculationHashMap, maximumAllowableDate, currentDateMinusOne);
			
			// 3) Logic to add those office Ids whose DRS data has synced in late
		    addAllLateSyncedDrsOfficesToHashMap(pettyCashRecalculationHashMap, maximumAllowableDate, currentDateMinusOne);
		}
		catch(Exception e) {
			LOGGER.debug("PettyCashReportServiceImpl :: getOfficeIdAndBookingDateHashMap() :: ERROR",e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("PettyCashReportServiceImpl :: getOfficeIdAndBookingDateHashMap() :: END");
		return pettyCashRecalculationHashMap;
	}
	
	private void addAllActiveOfficesToHashMap(Map<Integer,String> pettyCashRecalculationHashMap, String currentDateString) 
			throws CGSystemException, CGBusinessException{
		List<Integer> officeIds = getAllOfficesOfThatDay();
		if(!StringUtil.isEmptyColletion(officeIds)){
			for(Integer officeId : officeIds){
				pettyCashRecalculationHashMap.put(officeId, currentDateString);
			}
		}
	}
	
	private void addAllLateSyncedBookingOfficesToHashMap(Map<Integer,String> pettyCashRecalculationHashMap, String maximumAllowableDate,
			String currentDateString) throws CGSystemException {
		List<PettyCashReportWrapperDO> pettyCashReportWrapperDOList = getOfficeIdAndBookingDateForPettyCash(maximumAllowableDate, currentDateString);
		if(!StringUtil.isEmptyColletion(pettyCashReportWrapperDOList)){
			for(PettyCashReportWrapperDO pettyCashReportWrapperDO : pettyCashReportWrapperDOList){
				if(pettyCashRecalculationHashMap.containsKey(pettyCashReportWrapperDO.getOfficeId())){
					pettyCashRecalculationHashMap.put(pettyCashReportWrapperDO.getOfficeId(),
							getMinimumDateWithRespectToPettyCashWindow(DateUtil.getDDMMYYYYDateToString(pettyCashReportWrapperDO.getDate()), 
									maximumAllowableDate)); 
				}
			}
		}
	}
	
	private void addAllLateSyncedDrsOfficesToHashMap(Map<Integer,String> pettyCashRecalculationHashMap, String maximumAllowableDate, String currentDateMinusOne) 
			throws CGSystemException, CGBusinessException {
		/* Search collection details for petty cash recalculation */
		List<PettyCashReportWrapperDO> pettyCashReportWrapperDoList = mecCommonService.getCollectionIdAndCollectionDateForPettyCash(currentDateMinusOne); 
		if(!StringUtil.isEmptyColletion(pettyCashReportWrapperDoList)){
			getLateDrsOfficesHashMap(pettyCashReportWrapperDoList, pettyCashRecalculationHashMap, maximumAllowableDate);
			this.pettyCashReportWrapperDoList = pettyCashReportWrapperDoList;
		}
	}
	
	private void calculateAndSavePettyCashDtls(Map<Integer,String> pettyCashCalculationHashMap, String currentDateString)
		throws CGSystemException, CGBusinessException {
		LOGGER.warn("PettyCashReportServiceImpl :: calculateAndSavePettyCashDtls() :: START");
		
		Map<Integer,String> errorOfficesMap = new LinkedHashMap<>();
		int count = 0;
		do {
			count++;
			LOGGER.warn("PettyCashReportServiceImpl :: calculateAndSavePettyCashDtls() :: Iteration - [" + count + "]");
			LOGGER.warn("PettyCashReportServiceImpl :: calculateAndSavePettyCashDtls() :: Petty Cash Calculation Hashmap : ");
			for (Entry<Integer,String> mapEntry : pettyCashCalculationHashMap.entrySet()) {
				LOGGER.warn("[" + mapEntry.getKey() + ", " + mapEntry.getValue() + "]"); 
			}

			/** The "errorOfficeIds" consists of a list of offices for which the recalculation has failed **/
			if (!CollectionUtils.isEmpty(errorOfficesMap)) {
				errorOfficesMap.clear();
			}
			
			try {
				/** The maximum number of threads to be spawned by the petty cash scheduler **/
				Integer delta = getNumberOfThreadsForPettyCash(pettyCashCalculationHashMap.size());
				Integer maximumAllowableThreads = Integer.parseInt(pettyCashRecalculationProperties.getProperty("maximumAllowableThreads"));
				Integer numberOfThreads = null;
				if (pettyCashCalculationHashMap.size() <= maximumAllowableThreads) {
					numberOfThreads = delta;
				}
				else {
					numberOfThreads = (int)Math.ceil(pettyCashCalculationHashMap.size()/(delta * 1.0));
				}
				
				LOGGER.warn("PettyCashReportServiceImpl :: calculateAndSavePettyCashDtls() :: Size of petty cash hash map = [" + pettyCashCalculationHashMap.size() + "]");
				LOGGER.warn("PettyCashReportServiceImpl :: calculateAndSavePettyCashDtls() :: Number of threads = [" + numberOfThreads + "]");
				
				/** Creating a thread pool with "threadLimit" number of threads **/
		 		ExecutorService executorService = null;
				
				/** Creating a list of inputs for each thread **/
				List<PettyCashClosingBalanceParallelCalculation> threadInputs = new ArrayList<>();
				
				/** Preparing the input for each thread **/
				if(!CGCollectionUtils.isEmpty(pettyCashCalculationHashMap)) {
					List<Integer> integerList = new ArrayList<>();
					integerList.addAll(pettyCashCalculationHashMap.keySet());
					if (pettyCashCalculationHashMap.size() <= maximumAllowableThreads) {
						delta = 1;
					}
					
					List<List<Integer>> officeIdList = Lists.partition(integerList,delta.intValue());
					executorService = Executors.newFixedThreadPool(officeIdList.size());
					
					for(List<Integer> input : officeIdList){
						Map<Integer,String> inputMap = new LinkedHashMap<>();
						for(Integer officeId : input){
							inputMap.put(officeId, pettyCashCalculationHashMap.get(officeId));
						}
						threadInputs.add(new PettyCashClosingBalanceParallelCalculation(inputMap, mecCommonService, this, pettyCashReportDAO, currentDateString));
					}
					
					List<Future<Map<Integer,String>>>  returnList = executorService.invokeAll(threadInputs);
					executorService.shutdown();
					
					if(!StringUtil.isEmptyColletion(returnList)) {
						for(Future<Map<Integer,String>> futureObject : returnList) {
							Map<Integer,String> errorOfficesMapFromEachThread = futureObject.get();
							if(!CollectionUtils.isEmpty(errorOfficesMapFromEachThread)) {
								errorOfficesMap.putAll(errorOfficesMapFromEachThread);
							}
						}
					}
					
					// Print the office Ids for whom the closing balance was not calculated
					if(!CollectionUtils.isEmpty(errorOfficesMap)){
						pettyCashCalculationHashMap.clear();
						pettyCashCalculationHashMap.putAll(errorOfficesMap);
						LOGGER.error("PettyCashReportServiceImpl :: calculateAndSavePettyCashDtls() :: ERROR :: Calculation failed for the following offices");
						for (Entry<Integer,String> entrySet : errorOfficesMap.entrySet()) {
							LOGGER.error("[" + entrySet.getKey() + ", " + entrySet.getValue() + "]");
						}
					}
					
					/* Reset the recalculation flag in collection */
					List<Integer> collectionIds = getCollectionIdsForPettyCashCalculation(pettyCashReportWrapperDoList, errorOfficesMap);
					if (!StringUtil.isEmptyColletion(collectionIds)) {
						/** If at this point there is a communications link failure, then
						  * the entire petty cash closing balance calculation will be re-triggered **/
						updateCollectionRecalculationFlag(collectionIds);
					}
				}
			}
			catch(Exception e) {
				LOGGER.error("PettyCashReportServiceImpl :: calculateAndSavePettyCashDtls() :: END", e);
				throw new CGSystemException(e);
			}
		}
		while (!CollectionUtils.isEmpty(errorOfficesMap));  // end of do while loop
		LOGGER.warn("PettyCashReportServiceImpl :: calculateAndSavePettyCashDtls() :: END");
	}
	
	
	private List<Integer> getAllOfficesOfThatDay() throws CGSystemException{
		List<Integer> allOfficeIds = pettyCashReportDAO.getAllOfficesForClosingBalanceCalculation();
		return allOfficeIds;
	}
	

	/**
	 * To calculate closing balance of the day for particular office
	 * 
	 * @param to
	 * @return closingBalance
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private Double calculateClosingBalance(PettyCashReportTO to)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PettyCashReportServiceImpl :: calculateClosingBalance() :: START");
		Double closingBalance = 0.0;
		try {
			
			/**--------------------- ff_f_petty_cash table -------------------**/
			/* To get Opening balance of the day - Positive */
			Double openingBalance = pettyCashReportDAO.getOpeningBalanceOfThatDay(to);
			
			/**--------------------- ff_f_expense table -----------------------**/
			/* Calculating total expense of office of that day - Negative */
			Double allExpenseAmt = pettyCashReportDAO.getTotalExpenseOfOfficeOfThatDay(to);

			/* Calculating cash withdrawal bank amount - Positive */
			Double cashWithdrawalBankAmt = pettyCashReportDAO.getCashWithdrawalBankAmtOfThatDay(to);

			/* Cash Sales RHO */
			Double cashSalesRho = pettyCashReportDAO.getAllCashSalesOfRho(to);
			
			/**--------------------- ff_f_booking table -----------------------**/
			/* Calculating cash sales - Positive */
			Double cashSales = getAllCashSalesOfThatDay(to);
			
			/**--------------------- ff_f_collection table -----------------------**/
			/*
			 * Calculating collection for TOPAY, COD, LC, OCTROI, Misc. income - * Positive
			 */
			Double allCollectionAmt = getAllCollectionAmtOfThatDay(to); // refers to consignment delivery date

			/* Debtors Collection - Positive */
			Double debutorsCollectionAmt = pettyCashReportDAO.getDebutorsCollectionOfThatDay(to);
			
			/* UPS COP Collection*/
			Double CashSalesUpsCop = pettyCashReportDAO.getAllCashSalesOfUpsCop(to);
			
			/* Calculating income for miscellaneous income (Expense) */
			Double miscellaneousExpense = pettyCashReportDAO.getAllMiscellaneousExpense(to);
			
			/* Expense Deduction - Positive */
			Double expenseDeductionAmt = pettyCashReportDAO.getExpenseDeductionOfThatDay(to);

			/* Collection Deduction - Negative */
			Double collectionDeductionAmt = pettyCashReportDAO.getCollectionDeductionOfThatDay(to);
			
			/* Miscellaneous Deduction */
			Double miscellaneousDeduction = pettyCashReportDAO.getMiscellaneousDeductionOfThatDay(to);
			
			/* Calculating International Cash Sales made by the office in one day - Positive */
			Double internationalCashSales = pettyCashReportDAO.getAllInternationalCashSalesOfThatDay(to);

			/* Total collection - Positive */
			Double totalCollection = openingBalance + cashWithdrawalBankAmt
					+ cashSales + allCollectionAmt + debutorsCollectionAmt
					+ expenseDeductionAmt + internationalCashSales + cashSalesRho + miscellaneousExpense + CashSalesUpsCop;

			/* Total expense - Negative */
			Double totalExpense = allExpenseAmt + collectionDeductionAmt + miscellaneousDeduction;

			/* Final closing balance */
			closingBalance = totalCollection - totalExpense;
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in PettyCashReportServiceImpl :: calculateClosingBalance() :: ",
					e);
			throw new CGBusinessException(e);
		}
		LOGGER.trace("PettyCashReportServiceImpl :: calculateClosingBalance() :: END");
		return closingBalance;
	}

	/**
	 * To get all type of collection amount like TOPAY, COD, LC, OCTROI -
	 * Positive
	 * 
	 * @param to
	 * @return allCollectionAmt
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private Double getAllCollectionAmtOfThatDay(PettyCashReportTO to)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PettyCashReportServiceImpl :: getAllCollectionAmtOfThatDay() :: START");
		Double allCollectionAmt = 0.0;

		/* Calculating TOPAY */
		setCollectionType(to, MECCommonConstants.COLLECTION_TYPE_TOPAY);
		Double topayAmt = pettyCashReportDAO.getCollectionAmtOfThatDay(to);

		/* Calculating LC */
		setCollectionType(to, MECCommonConstants.COLLECTION_TYPE_LC);
		Double LCAmt = pettyCashReportDAO.getCollectionAmtOfThatDay(to);

		/* Calculating COD */
		setCollectionType(to, MECCommonConstants.COLLECTION_TYPE_COD);
		Double CODAmt = pettyCashReportDAO.getCollectionAmtOfThatDay(to);

		/* Calculating OCTROI And Misc. Expenses (Misc. incomes) */
		setCollectionType(to, MECCommonConstants.COLLECTION_TYPE_OCTROI);
		Double octroiAndMiscExpAmt = pettyCashReportDAO
				.getCollectionAmtOfThatDay(to);

		allCollectionAmt = topayAmt + LCAmt + CODAmt + octroiAndMiscExpAmt;
		LOGGER.trace("PettyCashReportServiceImpl :: getAllCollectionAmtOfThatDay() :: END");
		return allCollectionAmt;
	}

	/**
	 * To set collection type for TOPAY, COD, LC, OCTROI collection
	 * 
	 * @param to
	 * @param collectionType
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private void setCollectionType(PettyCashReportTO to, String collectionType)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PettyCashReportServiceImpl :: setCollectionType() :: START");
		String[] collectionTypes = {};
		switch (collectionType) {
		case MECCommonConstants.COLLECTION_TYPE_TOPAY:
			collectionTypes = new String[] { MECCommonConstants.COLLECTION_TYPE_TOPAY };
			break;
		case MECCommonConstants.COLLECTION_TYPE_LC:
			collectionTypes = new String[] { MECCommonConstants.COLLECTION_TYPE_LC };
			break;
		case MECCommonConstants.COLLECTION_TYPE_COD:
			collectionTypes = new String[] { MECCommonConstants.COLLECTION_TYPE_COD };
			break;
		case MECCommonConstants.COLLECTION_TYPE_OCTROI:
			/* Octroi and Misc. Expense (Misc. incomes) collection types */
			collectionTypes = getAllGLDesc();
			break;
		}
		to.setCollectionTypes(collectionTypes);
		LOGGER.trace("PettyCashReportServiceImpl :: setCollectionType() :: END");
	}

	/**
	 * To get GL Description as collection type
	 * 
	 * @return collectionTypes
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private String[] getAllGLDesc() throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("PettyCashReportServiceImpl :: getAllGLDesc() :: START");
		String[] collectionTypes = null;
		List<String> expGLDescList = pettyCashReportDAO.getAllGLDesc();
		if (!CGCollectionUtils.isEmpty(expGLDescList)) {
			collectionTypes = new String[expGLDescList.size()];
			int count = 0;
			for (String collectionType : expGLDescList) {
				collectionTypes[count++] = collectionType;
			}
		}
		LOGGER.trace("PettyCashReportServiceImpl :: getAllGLDesc() :: END");
		return collectionTypes;
	}

	/**
	 * To get all cash sales of office of that day as collection (Positive)
	 * 
	 * @param to
	 * @return cashSales
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private Double getAllCashSalesOfThatDay(PettyCashReportTO to)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PettyCashReportServiceImpl :: getAllCashSalesOfThatDay() :: START");
		Double cashSales = 0.0;
		List<ConsignmentBillingRateDO> consignmentBillingRateDOList = null;
		List<String> consignmentNos = new ArrayList<>();
		
		setPettyCashTOForCashSales(to);
		consignmentBillingRateDOList = pettyCashReportDAO.getCashSalesOfThatDay(to);
		
		/* Loop to calculate the cash sales of one day */
		if(!StringUtil.isEmptyColletion(consignmentBillingRateDOList)){
			for(ConsignmentBillingRateDO consignmentBillingRateDO : consignmentBillingRateDOList){
				cashSales = cashSales + consignmentBillingRateDO.getGrandTotalIncludingTax();
				consignmentNos.add(consignmentBillingRateDO.getConsignmentDO().getConsgNo());
			}
			
			/* Set the list of consignment numbers in PettyCashReportTO */
			to.setConsgNosConsideredForPettyCash(consignmentNos);
		}
		
		LOGGER.trace("PettyCashReportServiceImpl :: getAllCashSalesOfThatDay() :: END");
		return cashSales;
	}

	/**
	 * To set bookingType and consgSeries to petty cash report TO
	 * 
	 * @param to
	 * @param cashSales
	 */
	private void setPettyCashTOForCashSales(PettyCashReportTO to) {
		LOGGER.trace("PettyCashReportServiceImpl :: setPettyCashTOForCashSales() :: START");
		String[] bookingTypes = new String[] { 
										CommonConstants.CASH_BOOKING,
										CommonConstants.EMOTIONAL_BOND_BOOKING 
									};
		String[] consgSeries = new String[] { 
									  CommonConstants.PRODUCT_SERIES_CASH,
									  CommonConstants.PRODUCT_SERIES_EB,
									  CommonConstants.PRODUCT_SERIES_PRIORITY,
									  CommonConstants.PRODUCT_SERIES_AIR_CARGO,
									  CommonConstants.PRODUCT_SERIES_TRAIN_SURFACE
									};
		to.setBookingTypes(bookingTypes);
		to.setConsgSeries(consgSeries);
		LOGGER.trace("PettyCashReportServiceImpl :: setPettyCashTOForCashSales() :: END");
	}

	/**
	 * To prepare petty cash report TO
	 * 
	 * @param officeId
	 * @param prevDate
	 * @param currDate
	 * @return to PettyCashReportTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private PettyCashReportTO preparePettyCashReportTO(Integer officeId,
			String prevDate, String currDate) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("PettyCashReportServiceImpl :: preparePettyCashReportTO() :: START");
		PettyCashReportTO to = new PettyCashReportTO();
		RegionDO regionDO = mecCommonService.getRegionByOffice(officeId);
		to.setRegionId(regionDO.getRegionId());
		to.setLoggedInOfficeId(officeId);
		to.setClosingDate(prevDate);
		to.setCurrentDate(currDate);
		LOGGER.trace("PettyCashReportServiceImpl :: preparePettyCashReportTO() :: END");
		return to;
	}

	@Override
	public void autoSubmitCollectionDtls(String currentDateString, String maximumAllowableDate) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("PettyCashReportServiceImpl :: autoSubmitCollectionDtls() :: START");

		PettyCashReportTO to = new PettyCashReportTO();
		to.setCurrentDate(currentDateString);
		to.setClosingDate(maximumAllowableDate);

		/* To get all saved only collections to submit them. */
		List<CollectionDO> collectioDOs = pettyCashReportDAO.getSavedCollectionDtlsOfThatDay(to);

		if (!CGCollectionUtils.isEmpty(collectioDOs)) {
			for (CollectionDO collectionDO : collectioDOs) {
				collectionDO.setStatus(MECCommonConstants.SUBMITTED_STATUS);
				for (CollectionDtlsDO collectionDtlsDO : collectionDO.getCollectionDtls()) {
					/* Received Amount */
					if (StringUtil.isEmptyDouble(collectionDtlsDO.getRecvAmount())) {
						collectionDtlsDO.setRecvAmount(0.0);
					}
					/* TDS */
					if (StringUtil.isEmptyDouble(collectionDtlsDO.getTdsAmount())) {
						collectionDtlsDO.setTdsAmount(0.0);
					}
					/* Total */
					if (StringUtil.isEmptyDouble(collectionDtlsDO.getTotalBillAmount())) {
						collectionDtlsDO.setTotalBillAmount(0.0);
					}
					
					/* To set re-calculation flag */
					String cnDlvDt = DateUtil.getDDMMYYYYDateToString(collectionDtlsDO.getConsgDeliveryDate());
					String prevDt = mecCommonService.decreaseDateByOne(currentDateString);
					if (!cnDlvDt.equalsIgnoreCase(prevDt)) {
						collectionDO.setIsRecalculationReq(CommonConstants.YES);
					}
					collectionDtlsDO.setRemarks(MECCommonConstants.MSG_AUTO_SUBMIT);
				}
				/* Total */
				if (StringUtil.isEmptyDouble(collectionDO.getTotalAmount())) {
					collectionDO.setTotalAmount(0.0);
				}
			}

			/* To auto submit collections details */
			pettyCashReportDAO.autoSubmitCollectionDtls(collectioDOs);
		}
		LOGGER.trace("PettyCashReportServiceImpl :: autoSubmitCollectionDtls() :: END");
	}

	@Override
	public void executePettyCashRecalculation() throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("PettyCashReportServiceImpl :: executePettyCashRecalculation() :: START");
		/* Search collection details for petty cash recalculation */
		List<CollectionDO> collectionDoList = mecCommonService.getCollectionDtlsForRecalculation();
		
		Map<Integer,String> recalculationOfficeInfoMap = new LinkedHashMap<>();
		List<Integer> errorOfficeIds = new ArrayList<>();
		
		if (!CGCollectionUtils.isEmpty(collectionDoList)) {
			/* Get a hashmap in the form <office-id, date from where to start the recalculation> */
			recalculationOfficeInfoMap = getLateDrsOfficesHashMapForRecalculation(collectionDoList, recalculationOfficeInfoMap);
			
			/* Iterate the list of offices and calculate the date range for each office */
			if(recalculationOfficeInfoMap != null && !recalculationOfficeInfoMap.isEmpty()){
				for(Map.Entry<Integer, String> entry : recalculationOfficeInfoMap.entrySet()){
					Integer officeId = entry.getKey();
					String startDateString = entry.getValue();
					try{
						Calendar startDate = Calendar.getInstance();
						startDate.setTime(DateUtil.getDateFromString(startDateString, FrameworkConstants.DDMMYYYY_SLASH_FORMAT));
						Calendar currentDate = Calendar.getInstance();
						currentDate.add(Calendar.DAY_OF_MONTH, -1);
						
						/* Date Loop will calculate the closing balance for a given office from startDate to today's date */
						while(!startDate.after(currentDate)){
							/* Re-Calculate Petty Cash Details */
							PettyCashReportDO pettyCashReportDo = 
									recalculatePettyCashDtls(officeId, startDateString, mecCommonService.increaseDateByDays(startDateString, 1));
							
							/* Update Petty Cash Details accordingly. */
							updatePettyCashDtls(pettyCashReportDo);
							
							/* Incrementing the start date by 1 */
							startDate.add(Calendar.DAY_OF_MONTH, 1);
							startDateString = DateUtil.getDDMMYYYYDateToString(startDate.getTime());
						}
					}
					catch(Exception e){
						LOGGER.error("PettyCashReportServiceImpl :: executePettyCashRecalculation() :: ERROR", e);
						errorOfficeIds.add(officeId);
					}
				} // end of for office Id loop
				
				/* Reset the recalculation flag in collection */
				List<Integer> collectionIds = getCollectionIds(collectionDoList,errorOfficeIds);
				if(!StringUtil.isEmptyColletion(collectionIds)){
					mecCommonService.updateCollectionRecalcFlag(collectionIds);
				}
			}
		}
		LOGGER.trace("PettyCashReportServiceImpl :: executePettyCashRecalculation() :: END");
	}

	private Map<Integer,String> getLateDrsOfficesHashMap(List<PettyCashReportWrapperDO> pettyCashReportWrapperDoList, Map<Integer,String> recalculationOfficeInfoMap,
			String maximumAllowableDate){
		LOGGER.trace("PettyCashReportServiceImpl :: getPettyCashRecalculationHashMap() :: START");
		for(PettyCashReportWrapperDO pettyCashReportWrapperDo : pettyCashReportWrapperDoList){
			Integer collectionOfficeId = pettyCashReportWrapperDo.getOfficeId();
			// String consgDeliveryDate = getConsignmentDeliveryDate(collectionDo);
			String consgDeliveryDate = DateUtil.getDDMMYYYYDateToString(pettyCashReportWrapperDo.getDate());
			
			if(recalculationOfficeInfoMap.containsKey(collectionOfficeId)){
				String storedMapDate = recalculationOfficeInfoMap.get(collectionOfficeId);
				consgDeliveryDate = getMinimumDate(consgDeliveryDate,storedMapDate);
				recalculationOfficeInfoMap.put(collectionOfficeId, getMinimumDateWithRespectToPettyCashWindow(consgDeliveryDate, maximumAllowableDate));
			}
			else{
				recalculationOfficeInfoMap.put(collectionOfficeId, getMinimumDateWithRespectToPettyCashWindow(consgDeliveryDate, maximumAllowableDate));
			}
		} // end of CollectionDO loop
		LOGGER.trace("PettyCashReportServiceImpl :: getPettyCashRecalculationHashMap() :: END");
		return recalculationOfficeInfoMap;
	}
	
	private Map<Integer,String> getLateDrsOfficesHashMapForRecalculation(List<CollectionDO> collectionDoList, Map<Integer,String> recalculationOfficeInfoMap) {
		LOGGER.trace("PettyCashReportServiceImpl :: getPettyCashRecalculationHashMap() :: START");
		for(CollectionDO collectionDo : collectionDoList){
			Integer collectionOfficeId = collectionDo.getCollectionOfficeDO().getOfficeId();
			String consgDeliveryDate = getConsignmentDeliveryDate(collectionDo);
			
			if(recalculationOfficeInfoMap.containsKey(collectionOfficeId)){
				String storedMapDate = recalculationOfficeInfoMap.get(collectionOfficeId);
				consgDeliveryDate = getMinimumDate(consgDeliveryDate,storedMapDate);
				recalculationOfficeInfoMap.put(collectionOfficeId, consgDeliveryDate);
			}
			else{
				recalculationOfficeInfoMap.put(collectionOfficeId, consgDeliveryDate);
			}
		} // end of CollectionDO loop
		LOGGER.trace("PettyCashReportServiceImpl :: getPettyCashRecalculationHashMap() :: END");
		return recalculationOfficeInfoMap;
	}
	
	public PettyCashReportDO recalculatePettyCashDtls(Integer officeId, String prevDateStr, String currDateStr)
		throws CGSystemException{
		LOGGER.trace("PettyCashReportServiceImpl :: recalculatePettyCashDtls() :: START");
		PettyCashReportDO pettyCashReportDo = null;
		try{
			pettyCashReportDo = getPettyCashReportDO(officeId, prevDateStr);
			if(StringUtil.isNull(pettyCashReportDo)){
				pettyCashReportDo = new PettyCashReportDO();
				pettyCashReportDo.setPettyCashId(null);
				pettyCashReportDo.setCreatedDate(new Date(System.currentTimeMillis()));
			}
			PettyCashReportTO to = preparePettyCashReportTO(officeId, prevDateStr, currDateStr);
			Double closingBalance = calculateClosingBalance(to);
			pettyCashReportDo.setClosingBalance(closingBalance);
			pettyCashReportDo.setOfficeId(officeId);
			pettyCashReportDo.setUpdatedDate(new Date(System.currentTimeMillis()));
			pettyCashReportDo.setClosingDate(DateUtil.getDateFromString(prevDateStr, FrameworkConstants.DDMMYYYY_SLASH_FORMAT));
		}
		catch(Exception e){
			LOGGER.error("PettyCashReportServiceImpl :: recalculatePettyCashDtls() :: ERROR", e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("PettyCashReportServiceImpl :: recalculatePettyCashDtls() :: END");
		return pettyCashReportDo;
	}
	
	public PettyCashReportDO calculatePettyCashReportDtls(Integer officeId, String prevDateStr, String currDateStr)
			throws CGSystemException{
			LOGGER.trace("PettyCashReportServiceImpl :: recalculatePettyCashDtls() :: START");
			PettyCashReportDO pettyCashReportDo = null;
			try{
				pettyCashReportDo = getPettyCashReportDO(officeId, prevDateStr);
				if(StringUtil.isNull(pettyCashReportDo)){
					pettyCashReportDo = new PettyCashReportDO();
					pettyCashReportDo.setPettyCashId(null);
					pettyCashReportDo.setCreatedDate(new Date(System.currentTimeMillis()));
				}
				PettyCashReportTO to = preparePettyCashReportTO(officeId, prevDateStr, currDateStr);
				Double closingBalance = calculateClosingBalance(to);
				pettyCashReportDo.setClosingBalance(closingBalance);
				pettyCashReportDo.setOfficeId(officeId);
				pettyCashReportDo.setUpdatedDate(new Date(System.currentTimeMillis()));
				pettyCashReportDo.setClosingDate(DateUtil.getDateFromString(prevDateStr, FrameworkConstants.DDMMYYYY_SLASH_FORMAT));
				pettyCashReportDo.setConsgNosConsideredForPettyCash(to.getConsgNosConsideredForPettyCash());
			}
			catch(Exception e){
				LOGGER.error("PettyCashReportServiceImpl :: recalculatePettyCashDtls() :: ERROR", e);
				throw new CGSystemException(e);
			}
			LOGGER.trace("PettyCashReportServiceImpl :: recalculatePettyCashDtls() :: END");
			return pettyCashReportDo;
		}
	
	
	public PettyCashReportDO getPettyCashReportDO(Integer officeId, String prevDateStr)  
			throws CGSystemException{
		LOGGER.trace("PettyCashReportServiceImpl :: getPettyCashReportDO() :: START");
		List<PettyCashReportDO> pettyCashReportDOList = null;
		PettyCashReportDO pettyCashReportDO = null; 
		pettyCashReportDOList = pettyCashReportDAO.getPettyCashDtlsByDate(prevDateStr, officeId);
		if(!StringUtil.isEmptyColletion(pettyCashReportDOList)){
			pettyCashReportDO = pettyCashReportDOList.get(0);
		}
		LOGGER.trace("PettyCashReportServiceImpl :: getPettyCashReportDO() :: END");
		return pettyCashReportDO;
	}
	
	private String getConsignmentDeliveryDate(CollectionDO collectionDo){
		LOGGER.trace("PettyCashReportServiceImpl :: getConsignmentDeliveryDate() :: START");
		Set<String> dateSet = new TreeSet<>();
		String dateString = null;
		if(!StringUtil.isEmptyColletion(collectionDo.getCollectionDtls())){
			for(CollectionDtlsDO collectionDtlsDo : collectionDo.getCollectionDtls()){
				dateSet.add(DateUtil.getDDMMYYYYDateToString(collectionDtlsDo.getConsgDeliveryDate()));
			}
		}
		
		if(!StringUtil.isEmptyColletion(dateSet)){
			dateString = dateSet.iterator().next();
		}
		LOGGER.trace("PettyCashReportServiceImpl :: getConsignmentDeliveryDate() :: END");
		return dateString;
	}
	
	private String getMinimumDate(String consgDeliveryDateString, String storedMapDateString){
		Calendar consgDeliveryDate = Calendar.getInstance();
		consgDeliveryDate.setTime(DateUtil.getDateFromString(consgDeliveryDateString, FrameworkConstants.DDMMYYYY_SLASH_FORMAT));
		
		Calendar storedMapDate = Calendar.getInstance();
		storedMapDate.setTime(DateUtil.getDateFromString(storedMapDateString, FrameworkConstants.DDMMYYYY_SLASH_FORMAT));
		
		if(consgDeliveryDate.before(storedMapDate)){
			return consgDeliveryDateString;
		}
		else{
			return storedMapDateString;
		}
	}
	

	/**
	 * To update petty cash details accordingly
	 * 
	 * @param dateStr
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public void updatePettyCashDtls(PettyCashReportDO pettyCashReportDo) throws CGBusinessException,CGSystemException {
		LOGGER.trace("PettyCashReportServiceImpl :: updatePettyCashDtls() :: START");
		/* Save closing balance of previous day */
		boolean result = pettyCashReportDAO.updatePettyCashReportEntry(pettyCashReportDo);
		if(!result){
			LOGGER.error("PettyCashReportServiceImpl :: updatePettyCashDtls() :: ERROR :: Details not updated for [ " + pettyCashReportDo.getOfficeId() +
					", " + pettyCashReportDo.getClosingDate() + " ]");
			throw new CGBusinessException(MECCommonConstants.PETTY_CASH_REPORT_NOT_SAVED);
		}
		LOGGER.trace("PettyCashReportServiceImpl :: updatePettyCashDtls() :: END");
	}
	

	/**
	 * To get collection ids
	 * 
	 * @param collectionDOs
	 * @return collectionIds
	 */
	private List<Integer> getCollectionIds(List<CollectionDO> collectionDOs, List<Integer> errorOfficeIds) {
		LOGGER.trace("PettyCashReportServiceImpl :: getCollectionIds() :: START");
		List<Integer> collectionIds = new ArrayList<Integer>();
		/* If the errorOfficeIds list id empty, it means that there was no error in calculating & updating the closing balances */
		if(StringUtil.isEmptyColletion(errorOfficeIds)){
			for (PettyCashReportWrapperDO pettyCashReportWrapperDo : pettyCashReportWrapperDoList) {
				collectionIds.add(pettyCashReportWrapperDo.getCollectionId());
			}
		}
		else{
			for (CollectionDO collectionDO : collectionDOs) {
				if(!errorOfficeIds.contains(collectionDO.getCollectionOfficeDO().getOfficeId())){
					collectionIds.add(collectionDO.getCollectionId());
				}
			}
		}
		LOGGER.trace("PettyCashReportServiceImpl :: getCollectionIds() :: END");
		return collectionIds;
	}

	@Override
	public String decreaseDateByOne(String fromDt) throws CGBusinessException {
		return mecCommonService.decreaseDateByOne(fromDt);
	}
	
	@Override
	public void recalculateClosingBalanceForOffices() throws CGBusinessException, CGSystemException {
		LOGGER.trace("PettyCashReportServiceImpl :: recalculateClosingBalanceForOffices() :: START");
		try {
			/** Read input from file. The file should consist of <office_id,start_date> pairs.
			 * The scheduler will read the input from the file and then recalculate the closing balance
			 * for all the mentioned offices from mentioned "start date" to "current date" **/
			
			/** The "recalculationOfficeInfoMap" contains data in the form of <office_id,start_date> **/
			Map<Integer,List<String>> recalculationOfficeInfoMap = readFileAndGetPettyCashRecalculationInput();
			
			/** The "errorOfficeIdMap" consists of a map of offices for which the recalculation has failed **/
			Map<Integer,List<String>> errorOfficesInformationGlobalMap = new LinkedHashMap<>();
			int count = 0;
			
			do {
				count++;
				LOGGER.warn("PettyCashReportServiceImpl :: recalculateClosingBalanceForOffices() :: Iteration - [" + count + "]");
				LOGGER.warn("PettyCashReportServiceImpl :: recalculateClosingBalanceForOffices() :: Petty Cash Re-Calculation Hashmap : ");
				for (Entry<Integer,List<String>> mapEntry : recalculationOfficeInfoMap.entrySet()) {
					LOGGER.warn("[" + mapEntry.getKey() + ", " + mapEntry.getValue().get(0) + ", " + mapEntry.getValue().get(1) + "]"); 
				}
				
				/** The "errorOfficeIds" consists of a list of offices for which the recalculation has failed **/
				if (!CollectionUtils.isEmpty(errorOfficesInformationGlobalMap)) {
					errorOfficesInformationGlobalMap.clear();
				}
				
				/** The maximum number of threads to be spawned by the petty cash recalculation scheduler **/
				Integer maximumAllowableThreads = Integer.parseInt(pettyCashRecalculationProperties.getProperty("maximumAllowableThreads"));
				Integer delta = getNumberOfThreadsForPettyCash(recalculationOfficeInfoMap.size());
				Integer numberOfThreads = null;
				if (recalculationOfficeInfoMap.size() <= maximumAllowableThreads) {
					numberOfThreads = delta;
				}
				else {
					numberOfThreads = (int)Math.ceil(recalculationOfficeInfoMap.size()/(delta * 1.0));
				}
				
				LOGGER.warn("PettyCashReportServiceImpl :: recalculateClosingBalanceForOffices() :: Size of petty cash recalculation hash map = [" 
						+ recalculationOfficeInfoMap.size() + "]");
				LOGGER.warn("PettyCashReportServiceImpl :: recalculateClosingBalanceForOffices() :: Number of threads = [" + numberOfThreads + "]");
				
				/** Creating a thread pool with "threadLimit" number of threads **/
				ExecutorService executorService = null;
				
				/** Creating a list of inputs for each thread **/
				List<PettyCashParallelCalculation> threadInputs = new ArrayList<>();
				
				/** Preparing the input for each thread **/
				if(!CGCollectionUtils.isEmpty(recalculationOfficeInfoMap)){
					List<Integer> integerList = new ArrayList<>();
					integerList.addAll(recalculationOfficeInfoMap.keySet());
					if (recalculationOfficeInfoMap.size() <= maximumAllowableThreads) {
						delta = 1;
					}
					
					List<List<Integer>> officeIdList = null;
					officeIdList = Lists.partition(integerList,delta.intValue());
					
					executorService = Executors.newFixedThreadPool(officeIdList.size());
					
					for(List<Integer> input : officeIdList){
						Map<Integer,List<String>> inputMap = new LinkedHashMap<>();
						for(Integer officeId : input){
							inputMap.put(officeId, recalculationOfficeInfoMap.get(officeId));
						}
						threadInputs.add(new PettyCashParallelCalculation(inputMap, mecCommonService, this, pettyCashReportDAO));
					}
					List<Future<Map<Integer,List<String>>>> returnList = executorService.invokeAll(threadInputs);
					executorService.shutdown();
					
					for (Future<Map<Integer,List<String>>> futureObject : returnList) {
						Map<Integer,List<String>> errorOfficesInformationLocalMap = futureObject.get();
						if (!CollectionUtils.isEmpty(errorOfficesInformationLocalMap)) {
							errorOfficesInformationGlobalMap.putAll(errorOfficesInformationLocalMap);
						}
					}
					
					// Print the office Ids for whom the closing balance was not calculated
					if (!CollectionUtils.isEmpty(errorOfficesInformationGlobalMap)) {
						recalculationOfficeInfoMap.clear();
						recalculationOfficeInfoMap.putAll(errorOfficesInformationGlobalMap);
						LOGGER.error("PettyCashReportServiceImpl :: recalculateClosingBalanceForOffices() :: ERROR :: " +
								"Re-Calculation failed for the following offices : " + errorOfficesInformationGlobalMap.keySet());
					}
				}
			}
			while (!CollectionUtils.isEmpty(errorOfficesInformationGlobalMap));
		}
		catch (Exception e) {
			LOGGER.trace("PettyCashReportServiceImpl :: recalculateClosingBalanceForOffices() :: ERROR",e);
		}
		LOGGER.trace("PettyCashReportServiceImpl :: recalculateClosingBalanceForOffices() :: END");
	}
	
	private Map<Integer,List<String>> readFileAndGetPettyCashRecalculationInput(){
		BufferedReader in = null;
		String filePath = null;
		Map<Integer,List<String>> pettyCashRecalculationMap = new LinkedHashMap<>();
		try{
			if(isWindowsOS()){
				filePath = pettyCashRecalculationProperties.getProperty("windows.filepath") + "PettyCashInput.txt";
			}
			else{
				filePath = pettyCashRecalculationProperties.getProperty("linux.filepath") + "PettyCashInput.txt";
			}
			FileReader fr = new FileReader(filePath);
			in = new BufferedReader(fr); 
			String temp = null;
			
			while((temp = in.readLine()) != null){
				List<String> dateList = new ArrayList<>();
				String[] stringArray = temp.split(",");
				Integer officeId = Integer.parseInt(stringArray[0].trim());
				String officeStartDate = stringArray[1].trim();
				dateList.add(officeStartDate);
				if(stringArray.length == 3){
					String officeEndDate = stringArray[2].trim();
					dateList.add(officeEndDate);
				}
				pettyCashRecalculationMap.put(officeId, dateList);
			}
			in.close();
		}
		catch(Exception e){ 
			LOGGER.error("PettyCashReportServiceImpl :: readFileAndGetPettyCashRecalculationInput() :: ERROR",e);
			try{
				in.close();
			}
			catch(Exception ex){
				LOGGER.error("PettyCashReportServiceImpl :: readFileAndGetPettyCashRecalculationInput() :: ERROR",e);
			}
		}
		return pettyCashRecalculationMap;
	}
	
	private boolean isWindowsOS(){
		boolean isWindowsOs = false;
		String osName = System.getProperty("os.name");
		if(osName.toLowerCase().contains("windows")){
			isWindowsOs = true;
		}
		return isWindowsOs;
	}
	
	@Override
	public String getStatusToUpdateInBookingTable() {
		String updatedStatus = null;
		try{
			 updatedStatus = CommonConstants.YES + CommonConstants.SPACE + CommonConstants.HYPHEN + 
					CommonConstants.SPACE + DateUtil.getDateInDDMMYYYYHHMMSSSlashFormat();
		}catch(Exception e){
			LOGGER.error("PettyCashReportServiceImpl :: getStatusToUpdateInBookingTable() :: ERROR",e);
		}
		return updatedStatus;
	}
	
	@Override
	public List<PettyCashReportWrapperDO> getOfficeIdAndBookingDateForPettyCash(String maximumAllowableDate, String currentDateString)
			throws CGSystemException {
		List<PettyCashReportWrapperDO> pettyCashReportWrapperDOList = null;
		currentDateString = currentDateString + CommonConstants.SPACE + MECCommonConstants.MAX_HHMMSS;
		Date currentDateObject = DateUtil.getDateFromString(currentDateString, FrameworkConstants.DDMMYYYYHHMMSS_SLASH_FORMAT);
		List<Object[]> returnList = 
				pettyCashReportDAO.getOfficeIdAndBookingDateForPettyCash(DateUtil.getDateFromString(maximumAllowableDate, FrameworkConstants.DDMMYYYY_SLASH_FORMAT),
						currentDateObject); 
		
		if(!StringUtil.isEmptyColletion(returnList)){
			pettyCashReportWrapperDOList = new ArrayList<>();
			for(Object[] objArr : returnList){
				pettyCashReportWrapperDOList.add(new PettyCashReportWrapperDO((Integer)objArr[0], (Date)objArr[1]));
			}
		}
		return pettyCashReportWrapperDOList;
	}

	private String getMaximumAllowableDateForLateDataSync(String currentDateString) throws CGBusinessException, CGSystemException {
		LOGGER.debug("PettyCashReportServiceImpl :: getMaximumAllowableDateForLateDataSync() :: START");
		String maxAllowedNoOfDaysString = "";
		try {
			maxAllowedNoOfDaysString = pettyCashReportDAO.getMaximumAllowedNoOfDaysForLateDataSync();
			
			if (StringUtil.isStringEmpty(maxAllowedNoOfDaysString)) {
				maxAllowedNoOfDaysString = "20";
			}
		}
		catch (Exception e) {
			maxAllowedNoOfDaysString = "20";
			LOGGER.error("PettyCashReportServiceImpl :: getMaximumAllowableDateForLateDataSync() :: ERROR",e);
		}
		Integer maxAllowedOfDaysInteger = Integer.parseInt(maxAllowedNoOfDaysString);
		LOGGER.debug("PettyCashReportServiceImpl :: getMaximumAllowableDateForLateDataSync() :: END");
		return mecCommonService.decreaseDateByDays(currentDateString, maxAllowedOfDaysInteger);
	}
	
	private String getMinimumDateWithRespectToPettyCashWindow(String currentlyConsideredDateString, String maximumAllowableDateString){
		Calendar currentlyConsideredDate = Calendar.getInstance();
		currentlyConsideredDate.setTime(DateUtil.getDateFromString(currentlyConsideredDateString, FrameworkConstants.DDMMYYYY_SLASH_FORMAT));
		
		Calendar maximumAllowableDate = Calendar.getInstance();
		maximumAllowableDate.setTime(DateUtil.getDateFromString(maximumAllowableDateString, FrameworkConstants.DDMMYYYY_SLASH_FORMAT));
		
		if(currentlyConsideredDate.before(maximumAllowableDate)){
			return maximumAllowableDateString;
		}
		else{
			return currentlyConsideredDateString;
		}
	}
	
	/**
	 * To get collection ids
	 * 
	 * @param collectionDOs
	 * @return collectionIds
	 */
	private List<Integer> getCollectionIdsForPettyCashCalculation(List<PettyCashReportWrapperDO> pettyCashReportWrapperDoList, Map<Integer,String> errorOfficesMap) {
		LOGGER.trace("PettyCashReportServiceImpl :: getCollectionIds() :: START");
		List<Integer> collectionIds = new ArrayList<Integer>();
		if (StringUtil.isEmptyColletion(pettyCashReportWrapperDoList)) {
			return null;
		}
		
		/* If the errorOfficeIds list id empty, it means that there was no error in calculating & updating the closing balances */
		if(CollectionUtils.isEmpty(errorOfficesMap)){
			for (PettyCashReportWrapperDO pettyCashReportWrapperDo : pettyCashReportWrapperDoList) {
				collectionIds.add(pettyCashReportWrapperDo.getCollectionId());
			}
		}
		else {
			for (PettyCashReportWrapperDO pettyCashReportWrapperDo : pettyCashReportWrapperDoList) {
				if(!errorOfficesMap.containsKey(pettyCashReportWrapperDo.getOfficeId())){
					collectionIds.add(pettyCashReportWrapperDo.getCollectionId());
				}
			}
		}
		LOGGER.trace("PettyCashReportServiceImpl :: getCollectionIds() :: END");
		return collectionIds;
	}
	
	private Integer getNumberOfThreadsForPettyCash(Integer currentMapSize) {
		Integer maximumAllowableThreads = Integer.parseInt(pettyCashRecalculationProperties.getProperty("maximumAllowableThreads"));
		Integer numberOfThreads = null;
		
		if (currentMapSize <= maximumAllowableThreads) {
			numberOfThreads = currentMapSize;
		}
		else {
			numberOfThreads =  (int)Math.ceil(currentMapSize.doubleValue() / maximumAllowableThreads.doubleValue());
		}
		return numberOfThreads;
	}
	

	@Override
	public void executePettyCashAutoCorrection() throws CGBusinessException,
			CGSystemException {
		LOGGER.warn("PettyCashReportServiceImpl :: executePettyCashAutoCorrection() :: START");
		Map<Integer,String> pettyCashAutoCorrectionHashMap = new LinkedHashMap<>();
		List<Integer> officeIdList = new ArrayList<>();
		String startDateString = "";
		
		/* Get current system date */ 
		String endDateForAutoCorrectionAsString = mecCommonService.decreaseDateByOne(DateUtil.getCurrentDateInDDMMYYYY());
		
		/* Get list of all active offices */
		officeIdList = getAllOfficesOfThatDay();
		
		/* Get start date for petty cash auto correction */ 
		startDateString = getStartDateForPettyCashAutoCorrection(officeIdList.size(), endDateForAutoCorrectionAsString);
		
		if (!StringUtil.isStringEmpty(startDateString) && !(endDateForAutoCorrectionAsString.equals(startDateString))) {
			LOGGER.warn("PettyCashReportServiceImpl :: executePettyCashAutoCorrection() :: Server shutdown date identified as : ["  + startDateString + "]");
			for (Integer officeId : officeIdList) {
				pettyCashAutoCorrectionHashMap.put(officeId, startDateString);
			}
		    // Calculate closing balance for all the offices 
			calculateAndSavePettyCashDtls(pettyCashAutoCorrectionHashMap,endDateForAutoCorrectionAsString);
		}
		LOGGER.warn("PettyCashReportServiceImpl :: executePettyCashAutoCorrection() :: END");
	}
	
	/**
	 * This method determines the date on which the server was shutdown manually for maintenance activity. 
	 * Logic : The method starts checking the number of closing balances from [currentDate - 2].
	 * By making a comparison between the number of closing balances and the number of active offices, it is able to
	 * pinpoint the server shutdown date. Also, the condition where a new office has been added by FFCL is also taken
	 * into consideration in this method.
	 */
	private String getStartDateForPettyCashAutoCorrection(Integer numberOfActiveOffices, String endDateForAutoCorrectionAsString)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PettyCashReportServiceImpl :: getStartDateForPettyCashAutoCorrection() :: START");
		String startDateForPettyCashAutoCorrection = "";
		boolean loopTerminationFlag = true;
		String givenDate = endDateForAutoCorrectionAsString;
		
		/* Get the number of offices that are currently active. This value is stored as a parameter named :
		 * CURRENT_ACTIVE_OFFICES_COUNT_FOR_PETTY_CASH in ff_d_configurable_params table */
		Integer currentActiveOfficeCount = getCurrentActiveOfficeCountForPettyCash();
		
		/* Logic to decide the number of new offices added by FFCL */
		Integer numberOfOfficesNewlyAdded = 0;
		if (numberOfActiveOffices.intValue() > currentActiveOfficeCount.intValue()) {
			numberOfOfficesNewlyAdded = numberOfActiveOffices.intValue() - currentActiveOfficeCount.intValue();
		}
		
		while (loopTerminationFlag) {
		    givenDate = mecCommonService.decreaseDateByOne(givenDate);
			Long numberOfClosingBalances = getNumberOfClosingBalancesForGivenDate(givenDate);
			
			if (StringUtil.isEmptyLong(numberOfClosingBalances)) {
				continue;
			}
			/* If there is any discrepancy in the number of closing balances, then it needs to be decided whether
			 * a) The difference exists because of the addition of a new office OR
			 * b) The server was manually shutdown for maintenance activity */
			else if (numberOfActiveOffices.intValue() != numberOfClosingBalances.longValue()) {
				if (!StringUtil.isEmptyInteger(numberOfOfficesNewlyAdded)) {
					if ((numberOfActiveOffices.intValue() - numberOfOfficesNewlyAdded.intValue()) != numberOfClosingBalances.longValue()) {
						numberOfActiveOffices = currentActiveOfficeCount; 
					}
					else {
						loopTerminationFlag = false;
					}
				}
				else {
					continue;
				}
			}
			else if (numberOfActiveOffices.intValue() == numberOfClosingBalances.longValue()) {
				startDateForPettyCashAutoCorrection = mecCommonService.increaseDateByDays(givenDate, 1);
				loopTerminationFlag = false;
			}
			else {
				loopTerminationFlag = false;
			}
		}
		
		/* If any new offices have been added by FFCL, then the value of the stored parameter in
		 * ff_d_configurable_params needs to be updated */
		if (!StringUtil.isEmptyInteger(numberOfOfficesNewlyAdded)) {
			Integer latestActiveOfficeCount = currentActiveOfficeCount + numberOfOfficesNewlyAdded;
			updateCurrentActiveOfficeCountForPettyCash(latestActiveOfficeCount.toString());
		}
		LOGGER.trace("PettyCashReportServiceImpl :: getStartDateForPettyCashAutoCorrection() :: END");
		return startDateForPettyCashAutoCorrection;
	}
	
	@Override
	public Long getNumberOfClosingBalancesForGivenDate(String givenDateString)
			throws CGSystemException {
		Date givenDate = DateUtil.getDateFromString(givenDateString, FrameworkConstants.DDMMYYYY_SLASH_FORMAT);
		Long numberOfClosingBalances = pettyCashReportDAO.getNumberOfClosingBalancesForGivenDate(givenDate);
		return numberOfClosingBalances;
	}
	
	private void updateCollectionRecalculationFlag(List<Integer> collectionIds) throws InterruptedException {
		LOGGER.trace("PettyCashReportServiceImpl :: updateCollectionRecalculationFlag() :: START");
		try {
			mecCommonService.updateCollectionRecalcFlag(collectionIds);
		}
		catch (Exception e) {
			LOGGER.error("PettyCashReportServiceImpl :: updateCollectionRecalculationFlag() :: ERROR", e);
			LOGGER.error("PettyCashReportServiceImpl :: updateCollectionRecalculationFlag() :: Waiting for 5 minutes");
			Thread.sleep(300000); // Waiting period of 5 minutes
			LOGGER.error("PettyCashReportServiceImpl :: updateCollectionRecalculationFlag() :: Waiting period over. Executing the update method again");
			updateCollectionRecalculationFlag(collectionIds);
		}
		LOGGER.trace("PettyCashReportServiceImpl :: updateCollectionRecalculationFlag() :: END");
	}
	
	private Integer getCurrentActiveOfficeCountForPettyCash() throws CGSystemException {
		Integer currentActiveCount = null;
		List<ConfigurableParamsDO> configParamDoList = 
				globalUniversalDAO.getConfigParamValueByName(MECCommonConstants.PARAM_NAME_CURRENT_ACTIVE_OFFICES_COUNT_FOR_PETTY_CASH);
		if (!StringUtil.isEmptyColletion(configParamDoList)) {
			currentActiveCount = Integer.parseInt(configParamDoList.get(0).getParamValue());
		}
		return currentActiveCount;
	}
	
	private void updateCurrentActiveOfficeCountForPettyCash(String latestActiveOfficeCount) throws CGSystemException {
		globalUniversalDAO.updateConfigurableParamValueByParamName(MECCommonConstants.PARAM_NAME_CURRENT_ACTIVE_OFFICES_COUNT_FOR_PETTY_CASH, latestActiveOfficeCount);
	}
}
