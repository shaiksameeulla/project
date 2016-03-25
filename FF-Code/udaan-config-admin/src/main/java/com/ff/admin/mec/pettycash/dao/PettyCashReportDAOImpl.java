package com.ff.admin.mec.pettycash.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.mec.common.constants.MECCommonConstants;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.common.ConfigurableParamsDO;
import com.ff.domain.mec.collection.CollectionDO;
import com.ff.domain.mec.pettycash.PettyCashReportDO;
import com.ff.to.mec.pettycash.PettyCashReportTO;

/**
 * @author hkansagr
 * 
 */
public class PettyCashReportDAOImpl extends CGBaseDAO implements
PettyCashReportDAO {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(PettyCashReportDAOImpl.class);

	@Override
	public boolean saveOrUpdatePettyCashReport(
			PettyCashReportDO pettyCashReportDO)
					throws CGSystemException {
		LOGGER.trace("PettyCashReportDAOImpl :: saveOrUpdatePettyCashReport() :: START");
		boolean result = Boolean.FALSE;
		try {
			getHibernateTemplate().saveOrUpdate(pettyCashReportDO);
			result = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in PettyCashReportDAOImpl :: saveOrUpdatePettyCashReport() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("PettyCashReportDAOImpl :: saveOrUpdatePettyCashReport() :: END");
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getAllPreviosDayOffices(String prevDateStr)
			throws CGSystemException {
		LOGGER.trace("PettyCashReportDAOImpl :: getAllPreviosDayOffices() :: START");
		List<Integer> officeIds = null;
		try {
			String[] paramNames = { MECCommonConstants.PREV_DATE };
			Object[] values = { DateUtil.stringToDDMMYYYYFormat(prevDateStr) };
			String queryName = MECCommonConstants.QRY_GET_ALL_PREV_DAY_OFFICES;

			officeIds = (List<Integer>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, paramNames,
							values);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in PettyCashReportDAOImpl :: getAllPreviosDayOffices() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("PettyCashReportDAOImpl :: getAllPreviosDayOffices() :: END");
		return officeIds;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getAllCollectionOfficesOfThatDay(String prevDateStr,
			String currDateStr, String isUpdateReq) throws CGSystemException {
		LOGGER.trace("PettyCashReportDAOImpl :: getAllCollectionOfficesOfThatDay() :: START");
		List<Integer> officeIds = null;
		try {
			String[] paramNames = { MECCommonConstants.PARAM_STATUS,
					MECCommonConstants.PREV_DATE,
					MECCommonConstants.PARAM_IS_UPDATE_REQ };

			String[] paramStatus = { MECCommonConstants.STATUS_SAVE };
			Date prevDate = DateUtil.stringToDDMMYYYYFormat(prevDateStr);

			Object[] values = { paramStatus, prevDate, isUpdateReq };

			String queryName = MECCommonConstants.QRY_GET_ALL_COLLECTION_OFFICES_OF_THAT_DAY;
			officeIds = (List<Integer>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, paramNames,
							values);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in PettyCashReportDAOImpl :: getAllCollectionOfficesOfThatDay() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("PettyCashReportDAOImpl :: getAllCollectionOfficesOfThatDay() :: END");
		return officeIds;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getAllExpenseOfficesOfThatDay(String prevDateStr,
			String currDateStr) throws CGSystemException {
		LOGGER.trace("PettyCashReportDAOImpl :: getAllExpenseOfficesOfThatDay() :: START");
		List<Integer> officeIds = null;
		try {
			String[] paramNames = { MECCommonConstants.PARAM_STATUS,
					MECCommonConstants.PREV_DATE,
					MECCommonConstants.CURRENT_DATE };

			String[] paramStatus = { MECCommonConstants.STATUS_SAVE };
			Date prevDate = DateUtil.stringToDDMMYYYYFormat(prevDateStr);
			Date currDate = DateUtil.stringToDDMMYYYYFormat(currDateStr);

			Object[] values = { paramStatus, prevDate, currDate };

			String queryName = MECCommonConstants.QRY_GET_ALL_EXPENSE_OFFICES_OF_THAT_DAY;
			officeIds = (List<Integer>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, paramNames,
							values);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in PettyCashReportDAOImpl :: getAllExpenseOfficesOfThatDay() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("PettyCashReportDAOImpl :: getAllExpenseOfficesOfThatDay() :: END");
		return officeIds;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getAllBookingOfficesOfThatDay(String prevDateStr,
			String currDateStr) throws CGSystemException {
		LOGGER.trace("PettyCashReportDAOImpl :: getAllBookingOfficesOfThatDay() :: START");
		List<Integer> officeIds = null;
		try {
			String[] paramNames = { MECCommonConstants.PREV_DATE,
					MECCommonConstants.CURRENT_DATE };

			Date prevDate = DateUtil.stringToDDMMYYYYFormat(prevDateStr);
			Date currDate = DateUtil.stringToDDMMYYYYFormat(currDateStr);

			Object[] values = { prevDate, currDate };

			String queryName = MECCommonConstants.QRY_GET_ALL_BOOKING_OFFICES_OF_THAT_DAY;
			officeIds = (List<Integer>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, paramNames,
							values);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in PettyCashReportDAOImpl :: getAllBookingOfficesOfThatDay() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("PettyCashReportDAOImpl :: getAllBookingOfficesOfThatDay() :: END");
		return officeIds;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Double getTotalExpenseOfOfficeOfThatDay(PettyCashReportTO to)
			throws CGSystemException {
		LOGGER.trace("PettyCashReportDAOImpl :: getTotalExpenseOfOfficeOfThatDay() :: START");
		Double totalExpense = 0.0;
		try {
			String[] paramNames = { MECCommonConstants.PREV_DATE,
					MECCommonConstants.CURRENT_DATE,
					MECCommonConstants.OFFICE_ID,
					MECCommonConstants.PARAM_STATUS,
					MECCommonConstants.PARAM_PAYMENT_MODE,
					MECCommonConstants.PARAM_REGION_ID,
					MECCommonConstants.PARAM_NATURE,
					//MECCommonConstants.PARAM_TXN_LENGTH 
			};

			Date prevDate = DateUtil
					.stringToDDMMYYYYFormat(to.getClosingDate());
			Date currentDate = DateUtil.stringToDDMMYYYYFormat(to
					.getCurrentDate());

			Object[] values = { prevDate, currentDate,
					to.getLoggedInOfficeId(), MECCommonConstants.STATUS_SAVE,
					CommonConstants.PAYMENT_MODE_CODE_CASH, to.getRegionId(),
					MECCommonConstants.NEGATIVE_GL_NATURE,
					//MECCommonConstants.TXN_LENGTH 
			};

			String queryName = MECCommonConstants.QRY_GET_TOTAL_EXPENSE_OF_OFFICE_OF_THAT_DAY;
			List<Double> totalExpenses = (List<Double>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, paramNames,
							values);
			if (!CGCollectionUtils.isEmpty(totalExpenses)
					&& !StringUtil.isEmptyDouble(totalExpenses.get(0))) {
				totalExpense = totalExpenses.get(0);
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in PettyCashReportDAOImpl :: getAllCollectionOfficesOfThatDay() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("PettyCashReportDAOImpl :: getTotalExpenseOfOfficeOfThatDay() :: END");
		return totalExpense;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Double getCashWithdrawalBankAmtOfThatDay(PettyCashReportTO to)
			throws CGSystemException {
		LOGGER.trace("PettyCashReportDAOImpl :: getCashWithdrawalBankAmtOfThatDay() :: START");
		Double cashWithdrawalBankAmt = 0.0;
		try {
			String[] paramNames = { MECCommonConstants.PREV_DATE,
					MECCommonConstants.CURRENT_DATE,
					MECCommonConstants.OFFICE_ID,
					MECCommonConstants.PARAM_STATUS,
					MECCommonConstants.PARAM_PAYMENT_MODE,
					MECCommonConstants.PARAM_REGION_ID,
					MECCommonConstants.PARAM_NATURE,
					//MECCommonConstants.PARAM_TXN_LENGTH 
			};

			Date prevDate = DateUtil
					.stringToDDMMYYYYFormat(to.getClosingDate());
			Date currentDate = DateUtil.stringToDDMMYYYYFormat(to
					.getCurrentDate());

			Object[] values = { prevDate, currentDate,
					to.getLoggedInOfficeId(), MECCommonConstants.STATUS_SAVE,
					CommonConstants.PAYMENT_MODE_CODE_CASH, to.getRegionId(),
					MECCommonConstants.POSITIVE_GL_NATURE,
					//MECCommonConstants.TXN_LENGTH
			};

			String queryName = MECCommonConstants.QRY_GET_CASH_WITHDRAWAL_BANK_AMT_OF_THAT_DAY;
			List<Double> cashWithdrawalBankAmts = (List<Double>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, paramNames,
							values);
			if (!CGCollectionUtils.isEmpty(cashWithdrawalBankAmts)
					&& !StringUtil.isEmptyDouble(cashWithdrawalBankAmts.get(0))) {
				cashWithdrawalBankAmt = cashWithdrawalBankAmts.get(0);
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in PettyCashReportDAOImpl :: getCashWithdrawalBankAmtOfThatDay() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("PettyCashReportDAOImpl :: getCashWithdrawalBankAmtOfThatDay() :: END");
		return cashWithdrawalBankAmt;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsignmentBillingRateDO> getCashSalesOfThatDay(PettyCashReportTO to)
			throws CGSystemException {
		LOGGER.trace("PettyCashReportDAOImpl :: getCashSalesOfThatDay() :: START");
		List<ConsignmentBillingRateDO> consignmentBillingRateDOList = null;
		try {
			String[] paramNames = 
				{   MECCommonConstants.PREV_DATE,
					MECCommonConstants.CURRENT_DATE,
					MECCommonConstants.OFFICE_ID
				};

			Date prevDate = DateUtil.stringToDDMMYYYYFormat(to.getClosingDate());
			Date currentDate = DateUtil.stringToDDMMYYYYFormat(to.getCurrentDate());

			Object[] values = 
				{ prevDate, 
					currentDate,
					to.getLoggedInOfficeId(),
				};

			String queryName = MECCommonConstants.QRY_GET_CASH_SALES_OF_THAT_DAY;
			consignmentBillingRateDOList = 
					(List<ConsignmentBillingRateDO>) getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, paramNames, values);

		} catch (Exception e) {
			LOGGER.error("PettyCashReportDAOImpl :: getCashSalesOfThatDay() :: ERROR",e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("PettyCashReportDAOImpl :: getCashSalesOfThatDay() :: END");
		return consignmentBillingRateDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Double getCollectionAmtOfThatDay(PettyCashReportTO to)
			throws CGSystemException {
		LOGGER.trace("PettyCashReportDAOImpl :: getCollectionAmtOfThatDay() :: START");
		Double collectionAmt = 0.0;
		try {
			String[] paramNames = { MECCommonConstants.PREV_DATE,
					MECCommonConstants.CURRENT_DATE,
					MECCommonConstants.OFFICE_ID,
					MECCommonConstants.PARAM_STATUS,
					MECCommonConstants.PARAM_PAYMENT_MODE,
					MECCommonConstants.PARAM_COLLECTION_TYPE,
					//MECCommonConstants.PARAM_TXN_LENGTH 
			};

			Date prevDate = DateUtil
					.stringToDDMMYYYYFormat(to.getClosingDate());
			Date currentDate = DateUtil.stringToDDMMYYYYFormat(to
					.getCurrentDate());

			Object[] values = { prevDate, currentDate,
					to.getLoggedInOfficeId(), MECCommonConstants.STATUS_SAVE,
					CommonConstants.PAYMENT_MODE_CODE_CASH,
					to.getCollectionTypes(), 
					//MECCommonConstants.TXN_LENGTH 
			};

			String queryName = MECCommonConstants.QRY_GET_COLLECTION_AMT_OF_THAT_DAY;
			List<Double> collectionAmts = (List<Double>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, paramNames,
							values);
			if (!CGCollectionUtils.isEmpty(collectionAmts)
					&& !StringUtil.isEmptyDouble(collectionAmts.get(0))) {
				collectionAmt = collectionAmts.get(0);
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in PettyCashReportDAOImpl :: getCollectionAmtOfThatDay() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("PettyCashReportDAOImpl :: getCollectionAmtOfThatDay() :: END");
		return collectionAmt;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllGLDesc() throws CGSystemException {
		LOGGER.trace("PettyCashReportDAOImpl :: getAllGLDesc() :: START");
		List<String> expGLDescList = null;
		try {
			expGLDescList = (List<String>) getHibernateTemplate()
					.findByNamedQuery(MECCommonConstants.QRY_GET_ALL_GL_DESC);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in PettyCashReportDAOImpl :: getAllGLDesc() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("PettyCashReportDAOImpl :: getAllGLDesc() :: END");
		return (!CGCollectionUtils.isEmpty(expGLDescList)) ? expGLDescList
				: null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Double getOpeningBalanceOfThatDay(PettyCashReportTO to)
			throws CGSystemException {
		LOGGER.trace("PettyCashReportDAOImpl :: getOpeningBalanceOfThatDay() :: START");
		Double openingBalance = 0.0;
		try {
			String[] paramNames = { MECCommonConstants.PREV_DATE,
					MECCommonConstants.OFFICE_ID };
			Date prevDate = DateUtil
					.stringToDDMMYYYYFormat(decreaseDateByOne(to
							.getClosingDate()));
			Object[] values = { prevDate, to.getLoggedInOfficeId() };

			String queryName = MECCommonConstants.QRY_GET_OPENING_BALANCE_OF_THAT_DAY;
			List<Double> openingBalances = (List<Double>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, paramNames,
							values);
			if (!CGCollectionUtils.isEmpty(openingBalances)
					&& !StringUtil.isEmptyDouble(openingBalances.get(0))) {
				openingBalance = openingBalances.get(0);
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in PettyCashReportDAOImpl :: getOpeningBalanceOfThatDay() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("PettyCashReportDAOImpl :: getOpeningBalanceOfThatDay() :: END");
		return openingBalance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Double getExpenseDeductionOfThatDay(PettyCashReportTO to)
			throws CGSystemException {
		LOGGER.trace("PettyCashReportDAOImpl :: getExpenseDeductionOfThatDay() :: START");
		Double expenseDeductionAmt = 0.0;
		try {
			String[] paramNames = { MECCommonConstants.PREV_DATE,
					MECCommonConstants.CURRENT_DATE,
					MECCommonConstants.OFFICE_ID,
					MECCommonConstants.PARAM_STATUS,
					MECCommonConstants.PARAM_PAYMENT_MODE,
					//MECCommonConstants.PARAM_TXN_LENGTH
			};

			Date prevDate = DateUtil
					.stringToDDMMYYYYFormat(to.getClosingDate());
			Date currentDate = DateUtil.stringToDDMMYYYYFormat(to
					.getCurrentDate());

			Object[] values = { prevDate, currentDate,
					to.getLoggedInOfficeId(), MECCommonConstants.STATUS_SAVE,
					CommonConstants.PAYMENT_MODE_CODE_CASH,
					//MECCommonConstants.TXN_LENGTH 
			};
			String[] queryNames = {
					MECCommonConstants.QRY_GET_EXPENSE_DEDUCTION_OF_THAT_DAY_FOR_EXPENSE,
					MECCommonConstants.QRY_GET_EXPENSE_DEDUCTION_OF_THAT_DAY_FOR_COLLECTION };
			for (String queryName : queryNames) {
				List<Double> expenseDeductionAmts = (List<Double>) getHibernateTemplate()
						.findByNamedQueryAndNamedParam(queryName, paramNames,
								values);
				if (!CGCollectionUtils.isEmpty(expenseDeductionAmts)
						&& !StringUtil.isEmptyDouble(expenseDeductionAmts
								.get(0))) {
					for (Double expenseDeduction : expenseDeductionAmts) {
						expenseDeductionAmt += expenseDeduction;
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in PettyCashReportDAOImpl :: getExpenseDeductionOfThatDay() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("PettyCashReportDAOImpl :: getExpenseDeductionOfThatDay() :: END");
		return expenseDeductionAmt;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Double getCollectionDeductionOfThatDay(PettyCashReportTO to)
			throws CGSystemException {
		LOGGER.trace("PettyCashReportDAOImpl :: getCollectionDeductionOfThatDay() :: START");
		Double collectionDeductionAmt = 0.0;
		try {
			String[] paramNames = { MECCommonConstants.PREV_DATE,
					MECCommonConstants.CURRENT_DATE,
					MECCommonConstants.OFFICE_ID,
					MECCommonConstants.PARAM_STATUS,
					MECCommonConstants.PARAM_PAYMENT_MODE,
					//MECCommonConstants.PARAM_TXN_LENGTH
			};

			Date prevDate = DateUtil
					.stringToDDMMYYYYFormat(to.getClosingDate());
			Date currentDate = DateUtil.stringToDDMMYYYYFormat(to
					.getCurrentDate());

			Object[] values = { prevDate, currentDate,
					to.getLoggedInOfficeId(), MECCommonConstants.STATUS_SAVE,
					CommonConstants.PAYMENT_MODE_CODE_CASH,
					//MECCommonConstants.TXN_LENGTH 
			};

			String[] queryNames = {
					MECCommonConstants.QRY_GET_COLLECTION_DEDUCTION_OF_THAT_DAY_FOR_EXPENSE,
					MECCommonConstants.QRY_GET_COLLECTION_DEDUCTION_OF_THAT_DAY_FOR_COLLECTION };
			for (String queryName : queryNames) {
				List<Double> collectionDeductionAmts = (List<Double>) getHibernateTemplate()
						.findByNamedQueryAndNamedParam(queryName, paramNames,
								values);
				if (!CGCollectionUtils.isEmpty(collectionDeductionAmts)
						&& !StringUtil.isEmptyDouble(collectionDeductionAmts
								.get(0))) {
					for (Double expenseDeduction : collectionDeductionAmts) {
						collectionDeductionAmt += expenseDeduction;
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in PettyCashReportDAOImpl :: getCollectionDeductionOfThatDay() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("PettyCashReportDAOImpl :: getCollectionDeductionOfThatDay() :: END");
		return collectionDeductionAmt;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Double getDebutorsCollectionOfThatDay(PettyCashReportTO to)
			throws CGSystemException {
		LOGGER.trace("PettyCashReportDAOImpl :: getDebutorsCollectionOfThatDay() :: START");
		Double debutorsCollectionAmt = 0.0;
		try {
			String[] paramNames = { MECCommonConstants.PREV_DATE,
					MECCommonConstants.OFFICE_ID,
					MECCommonConstants.PARAM_STATUS,
					MECCommonConstants.PARAM_PAYMENT_MODE,
					//MECCommonConstants.PARAM_TXN_LENGTH,
					MECCommonConstants.PARAM_CLC_AGAINST};

			Date prevDate = DateUtil
					.stringToDDMMYYYYFormat(to.getClosingDate());

			Object[] values = { prevDate, to.getLoggedInOfficeId(),
					MECCommonConstants.STATUS_SAVE,
					CommonConstants.PAYMENT_MODE_CODE_CASH,
					//MECCommonConstants.TXN_LENGTH,
					MECCommonConstants.CLC_AGAINST_B};
			String queryName = MECCommonConstants.QRY_GET_DEBUTORS_COLLECTION_OF_THAT_DAY;
			List<Double> debutorsCollectionAmts = (List<Double>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, paramNames,
							values);
			if (!CGCollectionUtils.isEmpty(debutorsCollectionAmts)
					&& !StringUtil.isEmptyDouble(debutorsCollectionAmts.get(0))) {
				debutorsCollectionAmt = debutorsCollectionAmts.get(0);
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in PettyCashReportDAOImpl :: getDebutorsCollectionOfThatDay() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("PettyCashReportDAOImpl :: getDebutorsCollectionOfThatDay() :: END");
		return debutorsCollectionAmt;
	}

	@Override
	public void autoSubmitCollectionDtls(List<CollectionDO> collectionDOs)
			throws CGSystemException {
		LOGGER.trace("PettyCashReportDAOImpl :: autoSubmitCollectionDtls() :: START");
		Session session = null;
		Transaction tx = null;
		try {
			session = createSession();
			for (CollectionDO collectionDO : collectionDOs) {
				try {
					tx = session.beginTransaction();
					session.merge(collectionDO);
					tx.commit();
				}
				catch (Exception e) {
					LOGGER.error(
							"Exception Occured in PettyCashReportDAOImpl :: autoSubmitCollectionDtls() :: ",
							e);
					tx.rollback();
				}
			}
		} 
		catch (Exception e) {
			LOGGER.error(
					"Exception Occured in PettyCashReportDAOImpl :: autoSubmitCollectionDtls() :: ",
					e);
			throw new CGSystemException(e);
		}
		finally {
			closeSession(session);
		}
		LOGGER.trace("PettyCashReportDAOImpl :: autoSubmitCollectionDtls() :: END");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CollectionDO> getSavedCollectionDtlsOfThatDay(PettyCashReportTO to) throws CGSystemException {
		LOGGER.trace("PettyCashReportDAOImpl :: getSavedCollectionDtlsOfThatDay() :: START");
		List<CollectionDO> collectionDOs = null;
		try {
			String[] params = { MECCommonConstants.PARAM_STATUS,
					MECCommonConstants.PREV_DATE,
					MECCommonConstants.CURRENT_DATE };

			Date prevDate = DateUtil.stringToDDMMYYYYFormat(to.getClosingDate());
			Date currentDate = DateUtil.stringToDDMMYYYYFormat(to.getCurrentDate());

			Object[] values = { MECCommonConstants.SAVED_STATUS, prevDate, currentDate };
			String queryName = MECCommonConstants.QRY_GET_SAVED_COLLECTION_DTLS_OF_THAT_DAY;
			collectionDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, params, values);
		} catch (Exception e) {
			LOGGER.error("PettyCashReportDAOImpl :: getSavedCollectionDtlsOfThatDay() :: ERROR", e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("PettyCashReportDAOImpl :: getSavedCollectionDtlsOfThatDay() :: END");
		return (!CGCollectionUtils.isEmpty(collectionDOs)) ? collectionDOs : null;
	}

	private String decreaseDateByOne(String fromDt) throws CGSystemException {
		LOGGER.trace("PettyCashReportDAOImpl :: decreaseDateByOne() :: START");
		String toDtStr = CommonConstants.EMPTY_STRING;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(
					FrameworkConstants.DDMMYYYY_SLASH_FORMAT);
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(fromDt));
			// Number of days to reduce
			c.add(Calendar.DATE, -1);
			// toDtStr is now the new date, 1 day before
			toDtStr = sdf.format(c.getTime());
		} catch (ParseException e) {
			LOGGER.error(
					"Exception occurs in PettyCashReportDAOImpl :: decreaseDateByOne() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("PettyCashReportDAOImpl :: decreaseDateByOne() :: END");
		return toDtStr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PettyCashReportDO> getPettyCashDtlsByDate(String dateStr,
			Integer officeId) throws CGSystemException {
		LOGGER.trace("PettyCashReportDAOImpl :: getPettyCashDtlsByDate() :: START");
		List<PettyCashReportDO> pettyCashReportDOs = null;
		try {
			String[] params = { MECCommonConstants.QRY_PARAM_DATE_OBJ,
					MECCommonConstants.OFFICE_ID };
			Object[] values = { DateUtil.stringToDDMMYYYYFormat(dateStr),
					officeId };
			String queryName = MECCommonConstants.QRY_GET_PETTY_CASH_DTLS_BY_DATE;
			pettyCashReportDOs = (List<PettyCashReportDO>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, params, values);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in PettyCashReportDAOImpl :: getPettyCashDtlsByDate() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("PettyCashReportDAOImpl :: getPettyCashDtlsByDate() :: END");
		return pettyCashReportDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getOfficesForUpdation(String dateStr)
			throws CGSystemException {
		LOGGER.trace("PettyCashReportDAOImpl :: getOfficesForUpdation() :: START");
		List<Integer> officeIds = null;
		try {
			String[] params = { MECCommonConstants.QRY_PARAM_DATE_OBJ };
			Object[] values = { DateUtil.stringToDDMMYYYYFormat(dateStr) };
			String queryName = MECCommonConstants.QRY_GET_OFFICES_FOR_UPDATION;
			officeIds = (List<Integer>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, params, values);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in PettyCashReportDAOImpl :: getOfficesForUpdation() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("PettyCashReportDAOImpl :: getOfficesForUpdation() :: END");
		return officeIds;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Double getAllInternationalCashSalesOfThatDay(PettyCashReportTO to) throws CGSystemException {
		LOGGER.trace("PettyCashReportDAOImpl :: getAllInternationalCashSalesOfThatDay() :: START");
		Double totalExpense = 0.0;
		try {
			String[] paramNames = { 
					MECCommonConstants.PREV_DATE,
					MECCommonConstants.CURRENT_DATE,
					MECCommonConstants.OFFICE_ID,
					MECCommonConstants.PARAM_PAYMENT_MODE,
					MECCommonConstants.PARAM_GL_CODE};

			Date prevDate = DateUtil.stringToDDMMYYYYFormat(to.getClosingDate());
			Date currentDate = DateUtil.stringToDDMMYYYYFormat(to.getCurrentDate());

			Object[] values = { 
					prevDate, 
					currentDate,
					to.getLoggedInOfficeId(),
					CommonConstants.PAYMENT_MODE_CODE_CASH, 
					MECCommonConstants.GL_CODE_FOR_INTERNATIONAL_CASH_SALES};

			String queryName = MECCommonConstants.QRY_GET_INTERNATIONAL_CASH_SALES_OF_THAT_DAY;
			List<Double> totalExpenses = (List<Double>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, paramNames,
							values);
			if (!CGCollectionUtils.isEmpty(totalExpenses)
					&& !StringUtil.isEmptyDouble(totalExpenses.get(0))) {
				totalExpense = totalExpenses.get(0);
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in PettyCashReportDAOImpl :: getAllInternationalCashSalesOfThatDay() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("PettyCashReportDAOImpl :: getAllInternationalCashSalesOfThatDay() :: END");
		return totalExpense;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getAllOfficesForClosingBalanceCalculation()
			throws CGSystemException {
		LOGGER.trace("PettyCashReportDAOImpl :: getAllOfficesForClosingBalanceCalculation() :: START");
		List<Integer> officeIds = null;
		try {
			String queryName = MECCommonConstants.QRY_GET_ALL_BRANCH_AND_HUB_OFFICES;
			officeIds = (List<Integer>) getHibernateTemplate().findByNamedQuery(queryName);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in PettyCashReportDAOImpl :: getAllOfficesForClosingBalanceCalculation() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("PettyCashReportDAOImpl :: getAllOfficesForClosingBalanceCalculation() :: END");
		return officeIds;
	}

	@Override
	public boolean updatePettyCashReportEntry(PettyCashReportDO pettyCashReportDo)
			throws CGSystemException {
		LOGGER.trace("PettyCashReportDAOImpl :: updatePettyCashReportEntry() :: START");
		boolean result = Boolean.FALSE;
		Session session = null;
		try {
			session = createSession();
			Query qry = session.getNamedQuery(MECCommonConstants.QRY_UPDATE_PETTY_CASH_REPORT_ENTRY);
			qry.setParameter(MECCommonConstants.PARAM_CLOSING_BALANCE, pettyCashReportDo.getClosingBalance());
			qry.setParameter(MECCommonConstants.PARAM_UPDATED_DATE, pettyCashReportDo.getUpdatedDate());
			qry.setParameter(MECCommonConstants.OFFICE_ID, pettyCashReportDo.getOfficeId());
			qry.setParameter(MECCommonConstants.PARAM_CLOSING_DATE, pettyCashReportDo.getClosingDate());
			int count = qry.executeUpdate();
			if(count > 0){
				result = Boolean.TRUE;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occurs in PettyCashReportDAOImpl :: updatePettyCashReportEntry() :: ",e);
			throw new CGSystemException(e);
		}
		finally {
			closeSession(session);
		}
		LOGGER.trace("PettyCashReportDAOImpl :: updatePettyCashReportEntry() :: END");
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Double getAllCashSalesOfRho(PettyCashReportTO to)
			throws CGSystemException {
		LOGGER.trace("PettyCashReportDAOImpl :: getAllCashSalesOfRho() :: START");
		Double totalExpense = 0.0;
		try {
			String[] paramNames = { 
					MECCommonConstants.PREV_DATE,
					MECCommonConstants.CURRENT_DATE,
					MECCommonConstants.OFFICE_ID,
					MECCommonConstants.PARAM_PAYMENT_MODE,
					MECCommonConstants.PARAM_GL_CODE};

			Date prevDate = DateUtil.stringToDDMMYYYYFormat(to.getClosingDate());
			Date currentDate = DateUtil.stringToDDMMYYYYFormat(to.getCurrentDate());

			Object[] values = { 
					prevDate, 
					currentDate,
					to.getLoggedInOfficeId(),
					CommonConstants.PAYMENT_MODE_CODE_CASH, 
					MECCommonConstants.GL_CODE_FOR_RHO_COLLECTION};

			String queryName = MECCommonConstants.QRY_GET_RHO_CASH_SALES_OF_THAT_DAY;
			List<Double> totalExpenses = (List<Double>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, paramNames,
							values);
			if (!CGCollectionUtils.isEmpty(totalExpenses)
					&& !StringUtil.isEmptyDouble(totalExpenses.get(0))) {
				totalExpense = totalExpenses.get(0);
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in PettyCashReportDAOImpl :: getAllCashSalesOfRho() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("PettyCashReportDAOImpl :: getAllCashSalesOfRho() :: END");
		return totalExpense;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Double getAllMiscellaneousExpense(PettyCashReportTO to)
			throws CGSystemException {
		LOGGER.trace("PettyCashReportDAOImpl :: getAllMiscellaneousExpense() :: START");
		Double totalExpense = 0.0;
		try
		{
			String[] paramNames = { 
					MECCommonConstants.PREV_DATE,
					MECCommonConstants.CURRENT_DATE,
					MECCommonConstants.OFFICE_ID,
					MECCommonConstants.PARAM_PAYMENT_MODE};

			Date prevDate = DateUtil.stringToDDMMYYYYFormat(to.getClosingDate());
			Date currentDate = DateUtil.stringToDDMMYYYYFormat(to.getCurrentDate());
			Object[] values = { 
					prevDate, 
					currentDate,
					to.getLoggedInOfficeId(),
					CommonConstants.PAYMENT_MODE_CODE_CASH};

			String queryName = MECCommonConstants.QRY_GET_MISC_EXPENSE_OF_THAT_DAY;
			List<Double> totalExpenses = (List<Double>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, paramNames,
							values);
			if (!CGCollectionUtils.isEmpty(totalExpenses)
					&& !StringUtil.isEmptyDouble(totalExpenses.get(0))) {
				totalExpense = totalExpenses.get(0);
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in PettyCashReportDAOImpl :: getAllMiscellaneousExpense() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("PettyCashReportDAOImpl :: getAllMiscellaneousExpense() :: END");
		return totalExpense;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Double getAllCashSalesOfUpsCop(PettyCashReportTO to)
			throws CGSystemException {
		LOGGER.trace("PettyCashReportDAOImpl :: getAllCashSalesOfRho() :: START");
		Double totalExpense = 0.0;
		try {
			String[] paramNames = { 
					MECCommonConstants.PREV_DATE,
					MECCommonConstants.CURRENT_DATE,
					MECCommonConstants.OFFICE_ID,
					MECCommonConstants.PARAM_PAYMENT_MODE,
					MECCommonConstants.PARAM_GL_CODE};

			Date prevDate = DateUtil.stringToDDMMYYYYFormat(to.getClosingDate());
			Date currentDate = DateUtil.stringToDDMMYYYYFormat(to.getCurrentDate());

			Object[] values = { 
					prevDate, 
					currentDate,
					to.getLoggedInOfficeId(),
					CommonConstants.PAYMENT_MODE_CODE_CASH, 
					MECCommonConstants.GL_CODE_FOR_UPS_COP_COLLECTION};

			String queryName = MECCommonConstants.QRY_GET_UPS_COP_CASH_SALES_OF_THAT_DAY;
			List<Double> totalExpenses = (List<Double>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, paramNames,
							values);
			if (!CGCollectionUtils.isEmpty(totalExpenses)
					&& !StringUtil.isEmptyDouble(totalExpenses.get(0))) {
				totalExpense = totalExpenses.get(0);
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in PettyCashReportDAOImpl :: getAllCashSalesOfRho() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("PettyCashReportDAOImpl :: getAllCashSalesOfRho() :: END");
		return totalExpense;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Double getMiscellaneousDeductionOfThatDay(PettyCashReportTO to)
			throws CGSystemException {
		LOGGER.trace("PettyCashReportDAOImpl :: getAllMiscellaneousExpense() :: START");
		Double totalExpense = 0.0;
		try
		{
			String[] paramNames = { 
					MECCommonConstants.PREV_DATE,
					MECCommonConstants.CURRENT_DATE,
					MECCommonConstants.OFFICE_ID,
					MECCommonConstants.PARAM_PAYMENT_MODE};

			Date prevDate = DateUtil.stringToDDMMYYYYFormat(to.getClosingDate());
			Date currentDate = DateUtil.stringToDDMMYYYYFormat(to.getCurrentDate());
			Object[] values = { 
					prevDate, 
					currentDate,
					to.getLoggedInOfficeId(),
					CommonConstants.PAYMENT_MODE_CODE_CASH};

			String queryName = MECCommonConstants.QRY_GET_MISC_DEDUCTION_OF_THAT_DAY;
			List<Double> totalExpenses = (List<Double>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, paramNames,
							values);
			if (!CGCollectionUtils.isEmpty(totalExpenses)
					&& !StringUtil.isEmptyDouble(totalExpenses.get(0))) {
				totalExpense = totalExpenses.get(0);
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in PettyCashReportDAOImpl :: getAllMiscellaneousExpense() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("PettyCashReportDAOImpl :: getAllMiscellaneousExpense() :: END");
		return totalExpense;
	}

	@Override
	public void saveOrUpdatePettyCashReportDO(PettyCashReportDO pettyCashReportDO)
			throws CGSystemException {
		LOGGER.trace("PettyCashReportDAOImpl :: saveOrUpdatePettyCashReportDO() :: START");
		try{
			if(!StringUtil.isNull(pettyCashReportDO)){
				getHibernateTemplate().saveOrUpdate(pettyCashReportDO);
			}else{
				LOGGER.warn("PettyCashReportDAOImpl :: saveOrUpdatePettyCashReportDO() :: WARN :: DO is empty. No data to save");
			}
		}
		catch(Exception e){
			LOGGER.trace("PettyCashReportDAOImpl :: saveOrUpdatePettyCashReportDO() :: ERROR",e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("PettyCashReportDAOImpl :: saveOrUpdatePettyCashReportDO() :: END");
	}

	@Override
	public void saveOrUpdatePettyCashReportDetails(PettyCashReportDO pettyCashReportDO, String updatedStatus)
			throws CGSystemException {
		LOGGER.trace("PettyCashReportDAOImpl :: saveOrUpdatePettyCashReportDO() :: START");
		Session session = null;
		Transaction tx = null;
		try{
			session = createSession();
			tx = session.beginTransaction();
			if(!StringUtil.isNull(pettyCashReportDO)){

				// Save PettyCashReportDO
				session.saveOrUpdate(pettyCashReportDO);

				// Update consignments considered by petty cash
				if(!StringUtil.isEmptyColletion(pettyCashReportDO.getConsgNosConsideredForPettyCash())){
					Query qry = session.getNamedQuery(MECCommonConstants.QRY_UPDATE_CONSGS_CONSIDERED_FOR_PETTY_CASH);
					qry.setParameter(MECCommonConstants.PARAM_UPDATED_STATUS, updatedStatus);
					qry.setParameterList(MECCommonConstants.PARAM_CONSG_NO_LIST, pettyCashReportDO.getConsgNosConsideredForPettyCash());
					qry.executeUpdate();
				}
				tx.commit();
			}
			else {
				LOGGER.warn("PettyCashReportDAOImpl :: saveOrUpdatePettyCashReportDO() :: WARN :: DO is empty. No data to save");
			}
		}
		catch(Exception e){
			tx.rollback();
			LOGGER.trace("PettyCashReportDAOImpl :: saveOrUpdatePettyCashReportDO() :: ERROR",e);
			throw new CGSystemException(e);
		}
		finally{
			closeSession(session);
		}
		LOGGER.trace("PettyCashReportDAOImpl :: saveOrUpdatePettyCashReportDO() :: END");
	}

	@Override
	public void saveOrUpdatePettyCashReportDOList(List<PettyCashReportDO> pettyCashReportDOList)
			throws CGSystemException {
		LOGGER.trace("PettyCashReportDAOImpl :: saveOrUpdatePettyCashReportDOList() :: START");
		try{
			if(!StringUtil.isEmptyColletion(pettyCashReportDOList)){
				getHibernateTemplate().saveOrUpdateAll(pettyCashReportDOList);
			}else{
				LOGGER.warn("PettyCashReportDAOImpl :: saveOrUpdatePettyCashReportDOList() :: WARN :: List is empty. No data to save");
			}
		}
		catch(Exception e){
			LOGGER.trace("PettyCashReportDAOImpl :: saveOrUpdatePettyCashReportDOList() :: ERROR",e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("PettyCashReportDAOImpl :: saveOrUpdatePettyCashReportDOList() :: END");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getOfficeIdAndBookingDateForPettyCash(Date maximumAllowableDate, Date currentDate)
			throws CGSystemException {
		LOGGER.debug("PettyCashReportDAOImpl :: getOfficeIdAndBookingDateForPettyCash() :: START");
		Session session = null;
		Query qry = null;
		List<Object[]> returnList = null;
		try{
			session = createSession();
			qry = session.getNamedQuery(MECCommonConstants.QRY_GET_BOOKING_OFF_ID_AND_BOOKING_DATE_FOR_PETTY_CASH);
			qry.setParameter(MECCommonConstants.PARAM_MAXIMUM_ALLOWABLE_DATE, maximumAllowableDate);
			qry.setParameter(MECCommonConstants.PARAM_CURR_DATE_TIME, currentDate);
			returnList  = (List<Object[]>)qry.list();
		}
		catch(Exception e){
			LOGGER.error("PettyCashReportDAOImpl :: saveOrUpdatePettyCashReportDOList() :: ERROR",e);
			throw new CGSystemException(e);
		}
		finally {
			closeSession(session);
		}
		LOGGER.debug("PettyCashReportDAOImpl :: getOfficeIdAndBookingDateForPettyCash() :: END");
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getMaximumAllowedNoOfDaysForLateDataSync() throws CGSystemException {
		LOGGER.debug("PettyCashReportDAOImpl :: getMaximumAllowedNoOfDaysForLateDataSync() :: START");
		List<ConfigurableParamsDO> resultList = null;
		ConfigurableParamsDO configParamDo = null;
		String maxAllowedNoOfDays = "";
		try {
			resultList = (List<ConfigurableParamsDO>)getHibernateTemplate().findByNamedQueryAndNamedParam(CommonConstants.CONFIGURABLE_PARAM_QUERRY, 
					CommonConstants.PARAM_NAME, MECCommonConstants.PARAM_WINDOW_SIZE_FOR_PETTY_CASH);
			
			if (!StringUtil.isEmptyColletion(resultList)) {
				configParamDo = resultList.get(0);
				maxAllowedNoOfDays = configParamDo.getParamValue();
			}
		}
		catch (Exception e) {
			LOGGER.error("PettyCashReportDAOImpl :: getMaximumAllowedNoOfDaysForLateDataSync() :: ERROR",e);
		}
		LOGGER.debug("PettyCashReportDAOImpl :: getMaximumAllowedNoOfDaysForLateDataSync() :: END");
		return maxAllowedNoOfDays;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public Long getNumberOfClosingBalancesForGivenDate(Date givenDate)
			throws CGSystemException {
		LOGGER.debug("PettyCashReportDAOImpl :: getNumberOfClosingBalancesForGivenDate() :: START");
		List<Long> cosingBalancesList = null;
		Long numberOfClosingBalances = null;
		try {
			cosingBalancesList = (List<Long>)getHibernateTemplate().findByNamedQueryAndNamedParam(MECCommonConstants.QRY_GET_NUMBER_OF_CLOSING_BALANCES_FOR_GIVEN_DATE, 
					MECCommonConstants.PARAM_CLOSING_DATE, givenDate);
			if (!StringUtil.isEmptyColletion(cosingBalancesList)) {
				numberOfClosingBalances = cosingBalancesList.get(0);
			}
		}
		catch (Exception e) {
			LOGGER.error("PettyCashReportDAOImpl :: getNumberOfClosingBalancesForGivenDate() :: ERROR",e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("PettyCashReportDAOImpl :: getNumberOfClosingBalancesForGivenDate() :: END");
		return numberOfClosingBalances;
	}
}
