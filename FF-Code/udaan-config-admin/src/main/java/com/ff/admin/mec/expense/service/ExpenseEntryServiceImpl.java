package com.ff.admin.mec.expense.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.exception.MessageType;
import com.capgemini.lbs.framework.exception.MessageWrapper;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.mec.common.constants.MECCommonConstants;
import com.ff.admin.mec.common.service.MECCommonService;
import com.ff.admin.mec.expense.dao.ExpenseEntryDAO;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.mec.GLMasterDO;
import com.ff.domain.mec.expense.ExpenseDO;
import com.ff.domain.mec.expense.ExpenseEntriesDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.ratemanagement.masters.RateCustomerCategoryDO;
import com.ff.domain.serviceOffering.PaymentModeDO;
import com.ff.mec.collection.CNCollectionDtlsTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.mec.GLMasterTO;
import com.ff.to.mec.expense.ConsignmentExpenseDetailTO;
import com.ff.to.mec.expense.EmployeeExpenseDetailTO;
import com.ff.to.mec.expense.ExpenseTO;
import com.ff.to.rate.OctroiRateCalculationOutputTO;
import com.ff.universe.mec.constant.MECUniversalConstants;

/**
 * @author hkansagr
 */

public class ExpenseEntryServiceImpl implements ExpenseEntryService {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ExpenseEntryServiceImpl.class);

	/** The Expense Entry DAO */
	private ExpenseEntryDAO expenseEntryDAO;

	/** The MEC Common Service */
	private MECCommonService mecCommonService;

	/**
	 * @param mecCommonService
	 *            the mecCommonService to set
	 */
	public void setMecCommonService(MECCommonService mecCommonService) {
		this.mecCommonService = mecCommonService;
	}

	/**
	 * @param expenseEntryDAO
	 *            the expenseEntryDAO to set
	 */
	public void setExpenseEntryDAO(ExpenseEntryDAO expenseEntryDAO) {
		this.expenseEntryDAO = expenseEntryDAO;
	}

	@Override
	public boolean saveOrUpdateExpenseDtls(ExpenseTO to)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("ExpenseEntryServiceImpl::saveOrUpdateExpenseDtls::START");
		Boolean result = Boolean.FALSE;
		ExpenseDO domain = null;
		try {
			if (!StringUtil.isNull(to)) {
				/* Auto generates tx. number for expense */
				if (StringUtil.isEmptyLong(to.getExpenseId())
						&& StringUtil.isStringEmpty(to.getTxNumber())) {
					/*List<String> seqNOs = mecCommonService.generateSequenceNo(
							Integer.parseInt(MECCommonConstants.ONE),
							CommonConstants.GEN_MISC_EXP_TXN_NO);
					String txNumber = getTxNoForExpense(to, seqNOs);*/
					
					SequenceGeneratorConfigTO sequenceGeneratorConfigTO= new SequenceGeneratorConfigTO();
					sequenceGeneratorConfigTO.setPrefixCode(MECCommonConstants.TX_CODE_EX+to.getLoginOfficeCode());
					sequenceGeneratorConfigTO.setProcessRequesting(MECCommonConstants.TX_CODE_EX);
					sequenceGeneratorConfigTO.setRequestDate(new Date());
					sequenceGeneratorConfigTO.setRequestingBranchCode(to.getLoginOfficeCode());
					sequenceGeneratorConfigTO.setRequestingBranchId(to.getLoginOfficeId());
					sequenceGeneratorConfigTO.setSequenceRunningLength(CommonConstants.COLLECTION_RUNNING_NUMBER_LENGTH);
					
					List<String> seqNOs = mecCommonService.generateSequenceNo(sequenceGeneratorConfigTO);
					if(CGCollectionUtils.isEmpty(seqNOs)){
						throw new CGBusinessException(FrameworkConstants.SEQUENCE_NUMBER_NOT_GENERATED);
					}
					String txNumber = seqNOs.get(0);
					to.setTxNumber(txNumber);
				}
				/* To prepare header DO */
				domain = createExpenseDOFromTO(to);
				/* To prepare List for respective grid details DO(s) */
				Set<ExpenseEntriesDO> expenseEntriesDOs = null;
				if (!StringUtil.equals(to.getExpenseFor(),
						MECCommonConstants.EX_FOR_OFFICE)) {
					expenseEntriesDOs = createExpenseDtlsList(to, domain);
					domain.setExpenseEntries(expenseEntriesDOs);
				}

				/*
				 * If amount is zero (0.0) during expense validation then txn
				 * should not flow to SAP system so we are setting SAP flag to Y
				 * explicitly
				 */
				String sapStatus = MECCommonConstants.SAP_OUTBOUND_STATUS_NEW;
				if (domain.getStatus().equalsIgnoreCase(
						MECCommonConstants.STATUS_VALIDATED)
						&& StringUtil.isEmptyDouble(domain.getTotalExpense())) {
					domain.setSapStatus(MECCommonConstants.SAP_OUTBOUND_STATUS_CMPLT);
					if (!StringUtil.isEmptyLong(to.getOldExpId())) {
						sapStatus = MECCommonConstants.SAP_OUTBOUND_STATUS_CMPLT;
					}
				}

				// To set DT_TO_BRANCH flag for consignment expense for BCUN
				// purpose
				if (!domain.getExpenseFor().equalsIgnoreCase(
						MECCommonConstants.EX_FOR_CN)) {
					domain.setDtToBranch(CommonConstants.YES);
				} else {
					domain.setDtToBranch(CommonConstants.NO);
				}

				// Save or update expense details
				result = expenseEntryDAO.saveOrUpdateExpenseDtls(domain);

				/* During SUBMIT also save to collection entries */
				if (StringUtil.equals(to.getExpenseFor(),
						MECCommonConstants.EX_FOR_CN)
						&& StringUtil.equals(to.getExpenseStatus(),
								MECCommonConstants.STATUS_SUBMITTED)) {
					List<CNCollectionDtlsTO> cnCollectionTOs = null;
					cnCollectionTOs = convertExpDtlDOsToCollectionDtlTOs(domain
							.getExpenseEntries());
					boolean res = mecCommonService
							.saveOtherCollectionDtls(cnCollectionTOs);
					if (!res) {
						// LOGGER.info("Consignment Details not SAVED Properly in Collection Table");
						throw new CGBusinessException(
								MECCommonConstants.CN_EXP_DTLS_NOT_SAVE_COLLECTION_DTLS);
					}
					// Set Billing flags
					setBillingFlags(to);
				}
				/* To update old expense status - VALIDATION */
				if (result && !StringUtil.isEmptyLong(to.getOldExpId())) {
					boolean res = expenseEntryDAO.updateExpenseStatus(
							to.getOldExpId(),
							MECCommonConstants.VALIDATED_STATUS, sapStatus);
					if (!res)
						LOGGER.info("Expense not VALIDATED Properly");
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"Error Occurs in ExpenseEntryServiceImpl::saveOrUpdateExpenseDtls::",
					e);
			throw new CGBusinessException(
					AdminErrorConstants.DETAILS_CAN_NOT_SAVED, e);
		}
		LOGGER.trace("ExpenseEntryServiceImpl::saveOrUpdateExpenseDtls::END");
		return result;
	}

	/**
	 * To set billing flags for Octroi Expense
	 * 
	 * @param to
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private void setBillingFlags(ExpenseTO to) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("ExpenseEntryServiceImpl :: setBillingFlags() :: START");
		if (!StringUtil.isEmpty(to.getBillingFlags())) {
			int size = to.getBillingFlags().length;
			for (int i = 0; i < size; i++) {
				if (!StringUtil.isStringEmpty(to.getBillingFlags()[i])
						&& to.getBillingFlags()[i]
								.equalsIgnoreCase(CommonConstants.YES)) {
					ConsignmentDO consgDO = new ConsignmentDO();
					consgDO.setConsgNo(to.getConsgNos()[i]);
					mecCommonService.updateBillingFlagsInConsignment(consgDO,
							CommonConstants.UPDATE_BILLING_FOR_MEC);
				}
			}
		}
		LOGGER.trace("ExpenseEntryServiceImpl :: setBillingFlags() :: END");
	}

	/**
	 * To convert ExpenseTO 2 ExpenseDO
	 * 
	 * @param to
	 * @return domain
	 */
	private ExpenseDO createExpenseDOFromTO(ExpenseTO to) {
		LOGGER.trace("ExpenseEntryServiceImpl::createExpenseDOFromTO()::START");
		ExpenseDO domain = new ExpenseDO();
		domain.setExpenseId(!StringUtil.isEmptyLong(to.getExpenseId()) ? to
				.getExpenseId() : null);
		domain.setTxNumber(!StringUtil.isStringEmpty(to.getTxNumber()) ? to
				.getTxNumber() : null);
		domain.setPostingDate(DateUtil.parseStringDateToDDMMYYYYHHMMFormat(to
				.getPostingDate()));
		domain.setDocumentDate(DateUtil.stringToDDMMYYYYFormat(to
				.getDocumentDate()));
		domain.setExpenseFor(!StringUtil.isStringEmpty(to.getExpenseFor()) ? to
				.getExpenseFor() : null);
		if (!StringUtil.isEmptyInteger(to.getExpenseType())) {
			GLMasterDO glDO = new GLMasterDO();
			glDO.setGlId(to.getExpenseType());
			domain.setTypeOfExpense(glDO);
		}
		if (!StringUtil.isEmptyInteger(to.getExpenseMode())) {
			PaymentModeDO paymentMode = new PaymentModeDO();
			paymentMode.setPaymentId(to.getExpenseMode());
			domain.setModeOfExpense(paymentMode);
		}
		domain.setChequeNo(!StringUtil.isStringEmpty(to.getChequeNumber()) ? to
				.getChequeNumber() : null);
		domain.setChequeDate(DateUtil.stringToDDMMYYYYFormat(to.getChequeDate()));
		if (!StringUtil.isEmptyInteger(to.getBank())) {
			GLMasterDO bank = new GLMasterDO();
			bank.setGlId(to.getBank());
			domain.setBankDO(bank);
		}
		domain.setBranchName(!StringUtil.isStringEmpty(to.getBankBranchName()) ? to
				.getBankBranchName() : null);
		domain.setTotalExpense(!StringUtil.isStringEmpty(to.getFinalAmount()) ? Double
				.parseDouble(to.getFinalAmount()) : null);
		if (!StringUtil.isEmptyInteger(to.getLoginOfficeId())) {
			OfficeDO office = new OfficeDO();
			office.setOfficeId(to.getLoginOfficeId());
			domain.setExpenseOfficeId(office);
		}
		// Txn. Remarks
		domain.setRemarks(!StringUtil.isStringEmpty(to.getTxRemarks()) ? to
				.getTxRemarks() : null);
		domain.setExpenseOfficeRho(!StringUtil.isEmptyInteger(to
				.getExpenseOfficeRho()) ? to.getExpenseOfficeRho() : null);
		domain.setStatus(!StringUtil.isStringEmpty(to.getExpenseStatus()) ? to
				.getExpenseStatus() : null);
		domain.setCreatedBy(!StringUtil.isEmptyInteger(to.getCreatedBy()) ? to
				.getCreatedBy() : null);
		domain.setUpdatedBy(!StringUtil.isEmptyInteger(to.getUpdatedBy()) ? to
				.getUpdatedBy() : null);

		// Setting Created Date & Updated Date
		domain.setCreatedDate(Calendar.getInstance().getTime());
		domain.setUpdatedDate(Calendar.getInstance().getTime());

		LOGGER.trace("ExpenseEntryServiceImpl::createExpenseDOFromTO()::END");
		return domain;
	}

	@Override
	public ExpenseTO searchExpenseDtls(ExpenseTO to)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("ExpenseEntryServiceImpl::searchExpenseDtls()::START");
		ExpenseDO domain = null;
		ExpenseTO expenseTO = null;
		domain = expenseEntryDAO.searchExpenseDtls(to);
		if (!StringUtil.isNull(domain)) {
			expenseTO = createExpenseTOFromDO(domain);
			if (!StringUtil.isEmptyColletion(domain.getExpenseEntries())) {
				List<EmployeeExpenseDetailTO> empDtlsTOs = new ArrayList<EmployeeExpenseDetailTO>();
				List<ConsignmentExpenseDetailTO> consigDtlsTOs = new ArrayList<ConsignmentExpenseDetailTO>();
				for (ExpenseEntriesDO expenseEnteriesDO : domain
						.getExpenseEntries()) {
					if (StringUtil.equals(domain.getExpenseFor(),
							MECCommonConstants.EX_FOR_EMP)) {
						EmployeeExpenseDetailTO empDtlsTO = new EmployeeExpenseDetailTO();
						empDtlsTO = createEmpDtlsTOFromDO(expenseEnteriesDO);
						empDtlsTOs.add(empDtlsTO);
					} else if (StringUtil.equals(domain.getExpenseFor(),
							MECCommonConstants.EX_FOR_CN)) {
						ConsignmentExpenseDetailTO consigDtlsTO = new ConsignmentExpenseDetailTO();
						consigDtlsTO = createConsigDtlsTOFromDO(expenseEnteriesDO);
						consigDtlsTOs.add(consigDtlsTO);
					}
				}
				if (StringUtil.equals(domain.getExpenseFor(),
						MECCommonConstants.EX_FOR_EMP)) {
					Collections.sort(empDtlsTOs);
					expenseTO.setEmpDtlsTO(empDtlsTOs);
				} else if (StringUtil.equals(domain.getExpenseFor(),
						MECCommonConstants.EX_FOR_CN)) {
					Collections.sort(consigDtlsTOs);
					expenseTO.setConsgDtlsTO(consigDtlsTOs);
				}
			}
		} else {
			MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(
					AdminErrorConstants.NUMBER_DOES_NOT_EXIST_FOR_BRANCH,
					MessageType.Warning,
					MECCommonConstants.EXPENSE,
					new String[] { MECCommonConstants.TX_NUMBER,
							to.getTxNumber(), to.getLoginOfficeCode() });
			throw new CGBusinessException(msgWrapper);
		}
		LOGGER.trace("ExpenseEntryServiceImpl::searchExpenseDtls()::END");
		return expenseTO;
	}

	/**
	 * To convert ExpenseDO 2 ExpenseTO
	 * 
	 * @param domain
	 * @return to
	 */
	private ExpenseTO createExpenseTOFromDO(ExpenseDO domain) {
		LOGGER.trace("ExpenseEntryServiceImpl::createExpenseTOFromDO()::START");
		ExpenseTO to = new ExpenseTO();
		to.setExpenseId(domain.getExpenseId());
		to.setTxNumber(domain.getTxNumber());
		to.setPostingDate(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(domain
				.getPostingDate()));
		to.setDocumentDate(DateUtil.getDDMMYYYYDateToString(domain
				.getDocumentDate()));
		to.setExpenseFor(domain.getExpenseFor());
		if (!StringUtil.isNull(domain.getTypeOfExpense())) {
			to.setExpenseType(domain.getTypeOfExpense().getGlId());
			if (!StringUtil.isStringEmpty(domain.getTypeOfExpense()
					.getIsOctroiGL())) {
				to.setIsOctroiGL(domain.getTypeOfExpense().getIsOctroiGL());
			}
		}
		if (!StringUtil.isNull(domain.getModeOfExpense())) {
			to.setExpenseMode(domain.getModeOfExpense().getPaymentId());
		}
		to.setChequeNumber(domain.getChequeNo());
		to.setChequeDate(DateUtil.getDDMMYYYYDateToString(domain
				.getChequeDate()));

		if (!StringUtil.isNull(domain.getBankDO())) {
			to.setBank(domain.getBankDO().getGlId());
		}
		to.setBankBranchName(domain.getBranchName());

		if (!StringUtil.isEmptyDouble(domain.getTotalExpense())) {
			BigDecimal bd = new BigDecimal(domain.getTotalExpense(),
					MathContext.DECIMAL64);
			to.setFinalAmount(bd.toString());
		} else {
			to.setFinalAmount("0.0");
		}

		to.setExpenseStatus(domain.getStatus());
		if (!StringUtil.isNull(domain.getExpenseOfficeId())) {
			to.setLoginOfficeId(domain.getExpenseOfficeId().getOfficeId());
			to.setLoginOfficeCode(domain.getExpenseOfficeId().getOfficeCode());
			if (!StringUtil.isNull(domain.getExpenseOfficeId()
					.getMappedRegionDO())) {
				to.setRegionId(domain.getExpenseOfficeId().getMappedRegionDO()
						.getRegionId());
			}
		}
		// Txn. Remarks
		if (!StringUtil.isStringEmpty(domain.getRemarks())) {
			to.setTxRemarks(domain.getRemarks());
		}
		to.setExpenseOfficeRho(domain.getExpenseOfficeRho());
		LOGGER.trace("ExpenseEntryServiceImpl::createExpenseTOFromDO()::END");
		return to;
	}

	/**
	 * To get Tx No for Expense
	 * 
	 * @param to
	 * @param seqNOs
	 * @return txNo
	 */
	private String getTxNoForExpense(ExpenseTO to, List<String> seqNOs) {
		LOGGER.trace("ExpenseEntryServiceImpl::getTxNoForExpense()::START");
		String txNo = to.getLoginOfficeCode() + MECCommonConstants.TX_CODE_EX
				+ seqNOs.get(0);
		LOGGER.trace("ExpenseEntryServiceImpl::getTxNoForExpense()::END");
		return txNo;
	}

	/**
	 * To create expense details list
	 * 
	 * @param to
	 * @return expenseEntriesDOs
	 */
	private Set<ExpenseEntriesDO> createExpenseDtlsList(ExpenseTO to,
			ExpenseDO expenseDO) {
		LOGGER.trace("ExpenseEntryServiceImpl::createExpenseDtlsList()::START");
		Set<ExpenseEntriesDO> expenseEntriesDOs = null;
		ExpenseEntriesDO expenseEntriesDO = new ExpenseEntriesDO();
		int size = to.getPositions().length;
		expenseEntriesDOs = new HashSet<ExpenseEntriesDO>();
		for (int i = 0; i < size; i++) {
			if ((!StringUtil.isEmpty(to.getEmployeeIds()) && !StringUtil
					.isEmptyInteger(to.getEmployeeIds()[i]))
					|| (!StringUtil.isEmpty(to.getConsgIds()) && !StringUtil
							.isEmptyInteger(to.getConsgIds()[i]))) {
				expenseEntriesDO = createExpEntriesDOFromTO(to, i);
			}
			expenseEntriesDO.setExpenseDO(expenseDO);
			expenseEntriesDOs.add(expenseEntriesDO);
		}
		LOGGER.trace("ExpenseEntryServiceImpl::createExpenseDtlsList()::END");
		return expenseEntriesDOs;
	}

	/**
	 * To create ExpenseEntriesDO from ExpenseTO
	 * 
	 * @param to
	 * @param counter
	 * @return expEntriesDO
	 */
	private ExpenseEntriesDO createExpEntriesDOFromTO(ExpenseTO to, int counter) {
		LOGGER.trace("ExpenseEntryServiceImpl::createExpEntriesDOFromTO()::START");
		ExpenseEntriesDO expEntriesDO = new ExpenseEntriesDO();
		if (!StringUtil.isEmpty(to.getExpenseEntriesIds())
				&& !StringUtil.isEmptyLong(to.getExpenseEntriesIds()[counter])) {
			expEntriesDO.setExpenseEntryId(to.getExpenseEntriesIds()[counter]);
		}
		/* Employee */
		if (!StringUtil.isEmpty(to.getEmployeeIds())
				&& !StringUtil.isEmptyInteger(to.getEmployeeIds()[counter])) {
			EmployeeDO empDO = new EmployeeDO();
			empDO.setEmployeeId(to.getEmployeeIds()[counter]);
			expEntriesDO.setEmployeeDO(empDO);
		}
		/* Consignment */
		if (!StringUtil.isEmpty(to.getConsgIds())
				&& !StringUtil.isEmptyInteger(to.getConsgIds()[counter])) {
			ConsignmentDO consigDO = new ConsignmentDO();
			consigDO.setConsgId(to.getConsgIds()[counter]);
			expEntriesDO.setConsignmentDO(consigDO);
		}
		/* Amount */
		if (!StringUtil.isEmpty(to.getAmounts())
				&& !StringUtil.isEmptyDouble(to.getAmounts()[counter])) {
			expEntriesDO.setAmount(to.getAmounts()[counter]);
		}
		/* Service Charge(s) - Octroi */
		if (!StringUtil.isEmpty(to.getServiceCharges())
				&& !StringUtil.isEmptyDouble(to.getServiceCharges()[counter])) {
			expEntriesDO.setServiceCharge(to.getServiceCharges()[counter]);
		} else {
			expEntriesDO.setServiceCharge(Double
					.parseDouble(MECCommonConstants.DOUBLE_ZERO));
		}
		/* Service Tax - Octroi */
		if (!StringUtil.isEmpty(to.getServiceTaxs())
				&& !StringUtil.isEmptyDouble(to.getServiceTaxs()[counter])) {
			expEntriesDO.setServiceTax(to.getServiceTaxs()[counter]);
		} else {
			expEntriesDO.setServiceTax(Double
					.parseDouble(MECCommonConstants.DOUBLE_ZERO));
		}
		/* Education Cess Tax - Octroi */
		if (!StringUtil.isEmpty(to.getEduCesss())
				&& !StringUtil.isEmptyDouble(to.getEduCesss()[counter])) {
			expEntriesDO.setEducationCess(to.getEduCesss()[counter]);
		} else {
			expEntriesDO.setEducationCess(Double
					.parseDouble(MECCommonConstants.DOUBLE_ZERO));
		}
		/* Higher Education Cess Tax - Octroi */
		if (!StringUtil.isEmpty(to.getHigherEduCesss())
				&& !StringUtil.isEmptyDouble(to.getHigherEduCesss()[counter])) {
			expEntriesDO
					.setHigherEducationCess(to.getHigherEduCesss()[counter]);
		} else {
			expEntriesDO.setHigherEducationCess(Double
					.parseDouble(MECCommonConstants.DOUBLE_ZERO));
		}
		/* State Tax - Octroi */
		if (!StringUtil.isEmpty(to.getStateTaxs())
				&& !StringUtil.isEmptyDouble(to.getStateTaxs()[counter])) {
			expEntriesDO.setStateTax(to.getStateTaxs()[counter]);
		} else {
			expEntriesDO.setStateTax(Double
					.parseDouble(MECCommonConstants.DOUBLE_ZERO));
		}
		/* Surcharge On State Tax - Octroi */
		if (!StringUtil.isEmpty(to.getSurchargeOnStateTaxs())
				&& !StringUtil
						.isEmptyDouble(to.getSurchargeOnStateTaxs()[counter])) {
			expEntriesDO
					.setSurchargeOnStateTax(to.getSurchargeOnStateTaxs()[counter]);
		} else {
			expEntriesDO.setSurchargeOnStateTax(Double
					.parseDouble(MECCommonConstants.DOUBLE_ZERO));
		}
		/* Other Charge(s) */
		if (!StringUtil.isEmpty(to.getOtherCharges())
				&& !StringUtil.isEmptyDouble(to.getOtherCharges()[counter])) {
			expEntriesDO.setOtherCharges(to.getOtherCharges()[counter]);
		} else {
			expEntriesDO.setOtherCharges(Double
					.parseDouble(MECCommonConstants.DOUBLE_ZERO));
		}
		/* Total */
		if (!StringUtil.isEmpty(to.getTotals())
				&& !StringUtil.isEmptyDouble(to.getTotals()[counter])) {
			expEntriesDO.setTotalExpenseEntryAmt(to.getTotals()[counter]);
		}
		/* Remark(s) */
		if (!StringUtil.isEmpty(to.getRemarks())
				&& !StringUtil.isStringEmpty(to.getRemarks()[counter])) {
			expEntriesDO.setRemarks(to.getRemarks()[counter]);
		}
		/* Position in grid */
		if (!StringUtil.isEmpty(to.getPositions())
				&& !StringUtil.isEmptyInteger(to.getPositions()[counter])) {
			expEntriesDO.setPosition(to.getPositions()[counter]);
		}
		/* OctroiBorneBy */
		if (!StringUtil.isEmpty(to.getOctroiBourneBys())
				&& !StringUtil.isStringEmpty(to.getOctroiBourneBys()[counter])) {
			expEntriesDO.setOctroiBourneBy(to.getOctroiBourneBys()[counter]);
		} else {
			if (StringUtil.equals(to.getExpenseFor(),
					MECCommonConstants.EX_FOR_CN)) {
				expEntriesDO
						.setOctroiBourneBy(CommonConstants.OCTROI_BOURNE_BY_CONSIGNEE);
			}
		}
		/* Billing Flag */
		if (!StringUtil.isEmpty(to.getBillingFlags())
				&& !StringUtil.isStringEmpty(to.getBillingFlags()[counter])) {
			expEntriesDO.setBillingFlag(to.getBillingFlags()[counter]);
		} else {
			expEntriesDO.setBillingFlag(CommonConstants.NO);
		}

		// Setting Created Date & Updated Date
		expEntriesDO.setCreatedDate(Calendar.getInstance().getTime());
		expEntriesDO.setUpdatedDate(Calendar.getInstance().getTime());

		LOGGER.trace("ExpenseEntryServiceImpl::createExpEntriesDOFromTO()::END");
		return expEntriesDO;
	}

	/**
	 * To convert EmployeeDetailsTO from ExpenseEntriesDO
	 * 
	 * @param expenseEnteriesDO
	 * @return empDetailTO
	 */
	private EmployeeExpenseDetailTO createEmpDtlsTOFromDO(
			ExpenseEntriesDO expenseEnteriesDO) {
		LOGGER.trace("ExpenseEntryServiceImpl::createEmpDtlsTOFromDO()::START");
		EmployeeExpenseDetailTO empDetailTO = new EmployeeExpenseDetailTO();
		empDetailTO.setExpenseEntriesId(expenseEnteriesDO.getExpenseEntryId());
		if (!StringUtil.isNull(expenseEnteriesDO.getEmployeeDO())) {
			empDetailTO.setEmployeeId(expenseEnteriesDO.getEmployeeDO()
					.getEmployeeId());
		}
		empDetailTO.setAmount(!StringUtil.isEmptyDouble(expenseEnteriesDO
				.getAmount()) ? expenseEnteriesDO.getAmount() : null);
		empDetailTO.setRemark(!StringUtil.isStringEmpty(expenseEnteriesDO
				.getRemarks()) ? expenseEnteriesDO.getRemarks() : null);
		empDetailTO.setPosition(!StringUtil.isEmptyInteger(expenseEnteriesDO
				.getPosition()) ? expenseEnteriesDO.getPosition() : null);
		LOGGER.trace("ExpenseEntryServiceImpl::createEmpDtlsTOFromDO()::END");
		return empDetailTO;
	}

	/**
	 * To convert ConsignmentDetailsTO from ExpenseEntriesDO
	 * 
	 * @param expenseEnteriesDO
	 * @return consigDetailTO
	 */
	private ConsignmentExpenseDetailTO createConsigDtlsTOFromDO(
			ExpenseEntriesDO expenseEnteriesDO) {
		LOGGER.trace("ExpenseEntryServiceImpl::createConsigDtlsTOFromDO()::START");
		ConsignmentExpenseDetailTO consigDetailTO = new ConsignmentExpenseDetailTO();
		consigDetailTO.setExpenseEntriesId(expenseEnteriesDO
				.getExpenseEntryId());
		/* Consignment */
		if (!StringUtil.isNull(expenseEnteriesDO.getConsignmentDO())) {
			consigDetailTO.setConsgId(expenseEnteriesDO.getConsignmentDO()
					.getConsgId());
			consigDetailTO.setConsgNo(expenseEnteriesDO.getConsignmentDO()
					.getConsgNo());
		}
		/* Amount */
		consigDetailTO.setAmount(!StringUtil.isEmptyDouble(expenseEnteriesDO
				.getAmount()) ? expenseEnteriesDO.getAmount() : null);
		/* Service Charge - Octroi */
		consigDetailTO
				.setServiceCharge(!StringUtil.isEmptyDouble(expenseEnteriesDO
						.getServiceCharge()) ? expenseEnteriesDO
						.getServiceCharge() : Double
						.parseDouble(MECCommonConstants.DOUBLE_ZERO));
		/* Service Tax - Octroi */
		consigDetailTO
				.setServiceTax(!StringUtil.isEmptyDouble(expenseEnteriesDO
						.getServiceTax()) ? expenseEnteriesDO.getServiceTax()
						: Double.parseDouble(MECCommonConstants.DOUBLE_ZERO));
		/* EducationCess Tax - Octroi */
		consigDetailTO.setEduCess(!StringUtil.isEmptyDouble(expenseEnteriesDO
				.getEducationCess()) ? expenseEnteriesDO.getEducationCess()
				: Double.parseDouble(MECCommonConstants.DOUBLE_ZERO));
		/* Higher EducationCess Tax - Octroi */
		consigDetailTO
				.setHigherEduCess(!StringUtil.isEmptyDouble(expenseEnteriesDO
						.getHigherEducationCess()) ? expenseEnteriesDO
						.getHigherEducationCess() : Double
						.parseDouble(MECCommonConstants.DOUBLE_ZERO));
		/* Service Tax - Octroi */
		consigDetailTO
				.setServiceTax(!StringUtil.isEmptyDouble(expenseEnteriesDO
						.getServiceTax()) ? expenseEnteriesDO.getServiceTax()
						: Double.parseDouble(MECCommonConstants.DOUBLE_ZERO));
		/* SurCharge On State Tax - Octroi */
		consigDetailTO
				.setSurchargeOnStateTax(!StringUtil
						.isEmptyDouble(expenseEnteriesDO
								.getSurchargeOnStateTax()) ? expenseEnteriesDO
						.getSurchargeOnStateTax() : Double
						.parseDouble(MECCommonConstants.DOUBLE_ZERO));
		/* The OctroiBourneBy */
		consigDetailTO
				.setOctroiBourneBy(!StringUtil.isStringEmpty(expenseEnteriesDO
						.getOctroiBourneBy()) ? expenseEnteriesDO
						.getOctroiBourneBy() : CommonConstants.EMPTY_STRING);
		/* Other Charge */
		consigDetailTO
				.setOtherCharge(!StringUtil.isEmptyDouble(expenseEnteriesDO
						.getOtherCharges()) ? expenseEnteriesDO
						.getOtherCharges() : Double
						.parseDouble(MECCommonConstants.DOUBLE_ZERO));
		/* Total */
		consigDetailTO.setTotal(!StringUtil.isEmptyDouble(expenseEnteriesDO
				.getTotalExpenseEntryAmt()) ? expenseEnteriesDO
				.getTotalExpenseEntryAmt() : null);
		/* Remark(s) */
		consigDetailTO.setRemark(!StringUtil.isStringEmpty(expenseEnteriesDO
				.getRemarks()) ? expenseEnteriesDO.getRemarks() : null);
		/* Position in grid */
		consigDetailTO.setPosition(!StringUtil.isEmptyInteger(expenseEnteriesDO
				.getPosition()) ? expenseEnteriesDO.getPosition() : null);
		/* The Billing Flag */
		consigDetailTO.setBillingFlag(expenseEnteriesDO.getBillingFlag());
		LOGGER.trace("ExpenseEntryServiceImpl::createConsigDtlsTOFromDO()::END");
		return consigDetailTO;
	}

	@Override
	public ConsignmentTO getConsignmentDtls(String consgNo)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("ExpenseEntryServiceImpl::getConsignmentDtls::START");
		ConsignmentTO consgTO = mecCommonService.getConsignmentDtls(consgNo);
		if (!StringUtil.isNull(consgTO)
				&& !StringUtil.isStringEmpty(consgTO.getConsgStatus())
				&& StringUtil.equals(consgTO.getConsgStatus(),
						MECCommonConstants.DELIVERY_STATUS_DELIVERED)) {
			throw new CGBusinessException(
					AdminErrorConstants.CN_ALREADY_DELIVERED);
		}
		LOGGER.trace("ExpenseEntryServiceImpl::getConsignmentDtls::END");
		return consgTO;
	}

	/**
	 * To convert ExpenseEntriesDO List to CNCollectionDtlsTO List
	 * 
	 * @param expEntriesDOs
	 * @return cnCollectionDtlsTOs
	 */
	private List<CNCollectionDtlsTO> convertExpDtlDOsToCollectionDtlTOs(
			Set<ExpenseEntriesDO> expEntriesDOs) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("ExpenseEntryServiceImpl::convertExpDtlDOsToCollectionDtlTOs()::START");
		List<CNCollectionDtlsTO> cnCollectionDtlsTOs = new ArrayList<CNCollectionDtlsTO>();
		for (ExpenseEntriesDO expEntriesDO : expEntriesDOs) {
			GLMasterTO glMasterTO = mecCommonService.getGLDtlsById(expEntriesDO
					.getExpenseDO().getTypeOfExpense().getGlId());
			String collectionAgainst = CommonConstants.EMPTY_STRING;
			if (glMasterTO.getIsOctroiGL()
					.equalsIgnoreCase(CommonConstants.YES)) {
				if (!expEntriesDO.getOctroiBourneBy().equalsIgnoreCase(
						CommonConstants.OCTROI_BOURNE_BY_CONSIGNEE)) {
					continue;
				}
				collectionAgainst = MECUniversalConstants.COLL_AGAINST_OCTROI;
			}
			CNCollectionDtlsTO to = new CNCollectionDtlsTO();
			to.setAmount(expEntriesDO.getTotalExpenseEntryAmt());
			to.setConsgId(expEntriesDO.getConsignmentDO().getConsgId());
			to.setCollectionType(glMasterTO.getGlDesc());
			if (!StringUtil.isStringEmpty(collectionAgainst)) {
				to.setCollectionAgainst(collectionAgainst);
			}
			cnCollectionDtlsTOs.add(to);
		}
		LOGGER.trace("ExpenseEntryServiceImpl::convertExpDtlDOsToCollectionDtlTOs()::END");
		return cnCollectionDtlsTOs;
	}

	@Override
	public ConsignmentExpenseDetailTO calculateOCTROI(ConsignmentTO consgTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("ExpenseEntryServiceImpl :: calculateOCTROI() :: START");
		ConsignmentExpenseDetailTO cnExpenseDtlsTO = new ConsignmentExpenseDetailTO();
		OctroiRateCalculationOutputTO octroiRateTO = null;
		cnExpenseDtlsTO.setConsgTO(consgTO);

		/* Set Product Code */
		String cnSeries = CommonConstants.EMPTY_STRING;
		Character cnSeriesChar = consgTO.getConsgNo().substring(4, 5)
				.toCharArray()[0];
		if (Character.isDigit(cnSeriesChar)) {
			cnSeries = CommonConstants.PRODUCT_SERIES_NORMALCREDIT;
		} else {
			cnSeries = cnSeriesChar.toString();
		}
		ProductTO productTO = mecCommonService
				.getProductByConsgSeries(cnSeries);
		if (!StringUtil.isNull(productTO)) {
			consgTO.setProductTO(productTO);
		}

		String customerCategory = CommonConstants.EMPTY_STRING;
		// Set Rate Customer Category Code - CNPricingDetailsTO
		if (!StringUtil.isEmptyInteger(consgTO.getCustomer())) {
			RateCustomerCategoryDO rateCustCatDO = mecCommonService
					.getRateCustCategoryByCustId(consgTO.getCustomer());
			String rateCustCatCode = rateCustCatDO
					.getRateCustomerCategoryCode();
			customerCategory = rateCustCatCode;
			consgTO.setRateCustomerCatCode(rateCustCatCode);
			// Get and Set Rate Type
			String rateType = mecCommonService.getRateType(rateCustCatCode);
			if (!StringUtil.isNull(consgTO.getConsgPriceDtls())) {
				consgTO.getConsgPriceDtls().setRateType(rateType);
			} else {
				CNPricingDetailsTO consgPriceDtls = new CNPricingDetailsTO();
				consgPriceDtls.setRateType(rateType);
				consgTO.setConsgPriceDtls(consgPriceDtls);
			}
		} else {// If CASH Customer
			CNPricingDetailsTO consgPriceDtls = new CNPricingDetailsTO();
			String rateType = mecCommonService
					.getRateType(CommonConstants.RATE_CUSTOMER_CAT_CASH);
			consgPriceDtls.setRateType(rateType);
			consgTO.setConsgPriceDtls(consgPriceDtls);
		}

		/* Set Consignment.s booking details for rate calculation purpose */
		mecCommonService.getBookingDtls(consgTO);

		octroiRateTO = mecCommonService.calculateOCTROI(consgTO);

		/* Check for Billing flag */
		if (customerCategory
				.equalsIgnoreCase(CommonConstants.RATE_CUSTOMER_CAT_CRDT)
				&& octroiRateTO.getOctroiBourneBy().equalsIgnoreCase(
						CommonConstants.OCTROI_BOURNE_BY_CONSIGNOR)) {
			cnExpenseDtlsTO.setBillingFlag(CommonConstants.YES);
		} else {
			cnExpenseDtlsTO.setBillingFlag(CommonConstants.NO);
		}
		cnExpenseDtlsTO.setOctroiRateTO(octroiRateTO);
		LOGGER.trace("ExpenseEntryServiceImpl :: calculateOCTROI() :: END");
		return cnExpenseDtlsTO;
	}

}
