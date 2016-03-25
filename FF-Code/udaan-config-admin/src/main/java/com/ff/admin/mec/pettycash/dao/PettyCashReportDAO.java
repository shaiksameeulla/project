package com.ff.admin.mec.pettycash.dao;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.mec.collection.CollectionDO;
import com.ff.domain.mec.pettycash.PettyCashReportDO;
import com.ff.to.mec.pettycash.PettyCashReportTO;

/**
 * @author hkansagr
 * 
 */
public interface PettyCashReportDAO {

	/**
	 * To save or update petty cash report using scheduler
	 * 
	 * @param pettyCashReportDOs
	 * @return boolean
	 * @throws CGSystemException
	 */
	boolean saveOrUpdatePettyCashReport(
			PettyCashReportDO pettyCashReportDO)
			throws CGSystemException;

	/**
	 * To get all collection offices of that day
	 * 
	 * @param prevDateStr
	 * @param currDateStr
	 * @param isUpdateReq
	 * @return list of office Ids - officeIds
	 * @throws CGSystemException
	 */
	List<Integer> getAllCollectionOfficesOfThatDay(String prevDateStr,
			String currDateStr, String isUpdateReq) throws CGSystemException;

	/**
	 * To get total expense of the office of that day
	 * 
	 * @param to
	 * @return totalExpense
	 * @throws CGSystemException
	 */
	Double getTotalExpenseOfOfficeOfThatDay(PettyCashReportTO to)
			throws CGSystemException;

	/**
	 * To get cash withdrawal bank amount of that day as collection (positive
	 * bank GL)
	 * 
	 * @param to
	 * @return cashWithdrawalBankAmt
	 * @throws CGSystemException
	 */
	Double getCashWithdrawalBankAmtOfThatDay(PettyCashReportTO to)
			throws CGSystemException;

	/**
	 * To get cash sales as collection - Positive
	 * 
	 * @param to
	 * @return cashSales
	 * @throws CGSystemException
	 */
	List<ConsignmentBillingRateDO> getCashSalesOfThatDay(PettyCashReportTO to) throws CGSystemException;

	/**
	 * To get collection amount of that day - Positive
	 * 
	 * @param to
	 * @return collectionAmt
	 * @throws CGSystemException
	 */
	Double getCollectionAmtOfThatDay(PettyCashReportTO to)
			throws CGSystemException;

	/**
	 * To get All GL description
	 * 
	 * @return list - expense GL description
	 * @throws CGSystemException
	 */
	List<String> getAllGLDesc() throws CGSystemException;

	/**
	 * To get opening balance of the day
	 * 
	 * @param to
	 * @return openingBalance
	 * @throws CGSystemException
	 */
	Double getOpeningBalanceOfThatDay(PettyCashReportTO to)
			throws CGSystemException;

	/**
	 * To get expense deduction amount of that day
	 * 
	 * @param to
	 * @return expenseDeductionAmt
	 * @throws CGSystemException
	 */
	Double getExpenseDeductionOfThatDay(PettyCashReportTO to)
			throws CGSystemException;

	/**
	 * To get debutors collection of that day
	 * 
	 * @param to
	 * @return debutorsCollectionAmt
	 * @throws CGSystemException
	 */
	Double getDebutorsCollectionOfThatDay(PettyCashReportTO to)
			throws CGSystemException;

	/**
	 * To auto submit collections details
	 * 
	 * @param collectionDOs
	 * @throws CGSystemException
	 */
	void autoSubmitCollectionDtls(List<CollectionDO> collectionDOs)
			throws CGSystemException;

	/**
	 * To get all saved only collections to submit them
	 * 
	 * @param to
	 * @return list of CollectionDO
	 * @throws CGSystemException
	 */
	List<CollectionDO> getSavedCollectionDtlsOfThatDay(PettyCashReportTO to)
			throws CGSystemException;

	/**
	 * To get expense deduction amount of that day
	 * 
	 * @param to
	 * @return collectionDeductionAmt
	 * @throws CGSystemException
	 */
	Double getCollectionDeductionOfThatDay(PettyCashReportTO to)
			throws CGSystemException;

	/**
	 * To get petty cash details by given dates i.e. IF date = 16/1/2014 THEN
	 * result should all petty cash details after 16/1/2014 date
	 * 
	 * @param dateStr
	 * @param officeId
	 * @return pettyCashReportDOs
	 * @throws CGSystemException
	 */
	List<PettyCashReportDO> getPettyCashDtlsByDate(String dateStr,
			Integer officeId) throws CGSystemException;

	/**
	 * To get offices for updation
	 * 
	 * @param dateStr
	 * @return officeIds
	 * @throws CGSystemException
	 */
	List<Integer> getOfficesForUpdation(String dateStr)
			throws CGSystemException;

	/**
	 * To get all booking offices of that day
	 * 
	 * @param prevDateStr
	 * @param currDateStr
	 * @return officeIds
	 * @throws CGSystemException
	 */
	List<Integer> getAllBookingOfficesOfThatDay(String prevDateStr,
			String currDateStr) throws CGSystemException;

	/**
	 * To get all expense offices of that day
	 * 
	 * @param prevDateStr
	 * @param currDateStr
	 * @return officeIds
	 * @throws CGSystemException
	 */
	List<Integer> getAllExpenseOfficesOfThatDay(String prevDateStr,
			String currDateStr) throws CGSystemException;

	/**
	 * To get all previous day office whose closing balance need to calculated
	 * again for next days opening balance
	 * 
	 * @param decreaseDateByOne
	 * @return officeIds
	 * @throws CGSystemException
	 */
	List<Integer> getAllPreviosDayOffices(String decreaseDateByOne)
			throws CGSystemException;
	
	/**
	 * To get all international cash sales of that day
	 * 
	 * @param prevDateStr
	 * @param currDateStr
	 * @return officeIds
	 * @throws CGSystemException
	 */
	public Double getAllInternationalCashSalesOfThatDay(PettyCashReportTO to) throws CGSystemException;
	
	
	/**
	 * To get all offices for calculating closing balance
	 * 
	 * @return list of office Ids - officeIds
	 * @throws CGSystemException
	 */
	List<Integer> getAllOfficesForClosingBalanceCalculation() throws CGSystemException;
	
	/**
	 * To save or update petty cash report using scheduler
	 * 
	 * @param pettyCashReportDOs
	 * @return boolean
	 * @throws CGSystemException
	 */
	boolean updatePettyCashReportEntry(PettyCashReportDO pettyCashReportDo) throws CGSystemException;

	public Double getAllCashSalesOfRho(PettyCashReportTO to) throws CGSystemException;

	public Double getAllMiscellaneousExpense(PettyCashReportTO to) throws CGSystemException;
	
	public Double getAllCashSalesOfUpsCop(PettyCashReportTO to) throws CGSystemException;

	public Double getMiscellaneousDeductionOfThatDay(PettyCashReportTO to) throws CGSystemException;
	
	public void saveOrUpdatePettyCashReportDO(PettyCashReportDO pettyCashReportDO) throws CGSystemException;
	
	public void saveOrUpdatePettyCashReportDetails(PettyCashReportDO pettyCashReportDO, String updatedStatus) throws CGSystemException;
	
	public void saveOrUpdatePettyCashReportDOList(List<PettyCashReportDO> pettyCashReportDOList) throws CGSystemException;
	
	public List<Object[]> getOfficeIdAndBookingDateForPettyCash(Date maximumAllowableDate, Date currentDate) throws CGSystemException;
	
	public String getMaximumAllowedNoOfDaysForLateDataSync() throws CGSystemException;
	
	public Long getNumberOfClosingBalancesForGivenDate(Date givenDate) throws CGSystemException;

}
