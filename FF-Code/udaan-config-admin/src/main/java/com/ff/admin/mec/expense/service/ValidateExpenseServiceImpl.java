package com.ff.admin.mec.expense.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.exception.MessageType;
import com.capgemini.lbs.framework.exception.MessageWrapper;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.mec.common.constants.MECCommonConstants;
import com.ff.admin.mec.expense.dao.ValidateExpenseDAO;
import com.ff.domain.mec.expense.ExpenseDO;
import com.ff.to.mec.expense.ValidateExpenseDetailTO;
import com.ff.to.mec.expense.ValidateExpenseTO;

public class ValidateExpenseServiceImpl implements ValidateExpenseService{

	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ValidateExpenseServiceImpl.class);
	
	/** The Validate Expense DAO. */
	private ValidateExpenseDAO validateExpenseDAO;
	
	/**
	 * @param validateExpenseDAO the validateExpenseDAO to set
	 */
	public void setValidateExpenseDAO(ValidateExpenseDAO validateExpenseDAO) {
		this.validateExpenseDAO = validateExpenseDAO;
	}

	@Override
	public ValidateExpenseTO searchValidateExpenseDtls(ValidateExpenseTO to)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("ValidateExpenseServiceImpl::searchValidateExpenseDtls()::START");
		ValidateExpenseTO validateExpTO = to;
		List<ExpenseDO> expenseDOs = null;
		List<ValidateExpenseDetailTO> validateExpDtlsTOs = null;
		/* To convert date from String to Date data type */
		validateExpTO.setFromDt(DateUtil.parseStringDateToDDMMYYYYHHMMFormat(
				to.getFromDate()+" 00:00"));
		
		/* To increase date by 1 day */
		String dt = to.getToDate();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(dt));
		} catch (ParseException e) {
			LOGGER.error("Exception occurs in ValidateExpenseServiceImpl::searchValidateExpenseDtls()::"
				+ e.getMessage());
		}
		c.add(Calendar.DATE, 1);//Number of days to add
		dt = sdf.format(c.getTime());// dt is now the new date
		validateExpTO.setNewToDt(DateUtil.parseStringDateToDDMMYYYYHHMMFormat(
				dt+" 00:00"));
		
		validateExpTO.setToDt(DateUtil.parseStringDateToDDMMYYYYHHMMFormat(
				to.getToDate()+" 00:00"));
		
		expenseDOs = validateExpenseDAO.searchValidateExpenseDtls(validateExpTO);
		if(!StringUtil.isEmptyColletion(expenseDOs)){
			validateExpDtlsTOs = convertExpDOListToValidateExpTOList(expenseDOs);
			validateExpTO.setValidateExpDtlsTOs(validateExpDtlsTOs);
		} else {
			MessageWrapper msgWrapper = ExceptionUtil
					.getMessageWrapper(MECCommonConstants.NO_TX_NO_FOUND_FOR_BRANCH, MessageType.Warning);
			throw new CGBusinessException(msgWrapper);
		}
		LOGGER.debug("ValidateExpenseServiceImpl::searchValidateExpenseDtls()::END");
		return validateExpTO;
	}
	
	/**
	 * To convert ExpenseDO list to ValidateExpenseTO list
	 * 
	 * @param expenseDOs
	 * @return validateExpenseDetailTOs
	 */
	private List<ValidateExpenseDetailTO> convertExpDOListToValidateExpTOList(
			List<ExpenseDO> expenseDOs){
		LOGGER.debug("ValidateExpenseServiceImpl::convertExpDOListToValidateExpTOList()::START");
		List<ValidateExpenseDetailTO> validateExpenseDetailTOs = new 
				ArrayList<ValidateExpenseDetailTO>();
		for(ExpenseDO domain : expenseDOs){
			ValidateExpenseDetailTO validateExpTO = new ValidateExpenseDetailTO();
			
			/* PRIMARY KEY */
			validateExpTO.setExpenseId(domain.getExpenseId());
			
			/* FORMAT: DD/MM/YYYY */
			validateExpTO.setTxDate(DateUtil.getDDMMYYYYDateString(domain.getPostingDate()));
			
			/* i.e. MUMBEX123456 */
			validateExpTO.setTxNumber(domain.getTxNumber());
			
			/* TOTAL AMOUNT OF EXPENSE */
			validateExpTO.setAmount(domain.getTotalExpense());
			
			/* EXPENSE FOR */
			if(StringUtil.equals(domain.getExpenseFor(),
					MECCommonConstants.EX_FOR_OFFICE)){
				validateExpTO.setExpenseFor(domain.getExpenseFor());
				validateExpTO.setExpenseForDesc(MECCommonConstants.OFFICE.toUpperCase());	
			} else if(StringUtil.equals(domain.getExpenseFor(),
					MECCommonConstants.EX_FOR_EMP)){
				validateExpTO.setExpenseFor(domain.getExpenseFor());
				validateExpTO.setExpenseForDesc(MECCommonConstants.EMPLOYEE.toUpperCase());	
			} else if(StringUtil.equals(domain.getExpenseFor(),
					MECCommonConstants.EX_FOR_CN)){
				validateExpTO.setExpenseFor(domain.getExpenseFor());
				validateExpTO.setExpenseForDesc(MECCommonConstants.CONSIGNMENT.toUpperCase());	
			}
			
			/* TYPE OF EXPENSE */
			if(!StringUtil.isNull(domain.getTypeOfExpense())){
				validateExpTO.setExpenseType(domain.getTypeOfExpense().getGlDesc());
				validateExpTO.setExpenseTypeId(domain.getTypeOfExpense().getGlId());	
			}
			
			/* STATUS */
			if(StringUtil.equals(domain.getStatus(),
					MECCommonConstants.STATUS_SUBMITTED)){
				validateExpTO.setExpenseStatus(domain.getStatus());
				validateExpTO.setStatus(MECCommonConstants.SUBMITTED.toUpperCase());	
			} else if(StringUtil.equals(domain.getStatus(),
					MECCommonConstants.STATUS_VALIDATED)){
				validateExpTO.setExpenseStatus(domain.getStatus());
				validateExpTO.setStatus(MECCommonConstants.VALIDATED.toUpperCase());
			}
			
			/* EXPENSE OFFICE ID */
			if(!StringUtil.isNull(domain.getExpenseOfficeId())){
				validateExpTO.setExpenseOfficeId(domain
						.getExpenseOfficeId().getOfficeId());
				if(!StringUtil.isNull(domain.getExpenseOfficeId().getMappedRegionDO())){
					validateExpTO.setExpenseRegionId(domain.getExpenseOfficeId()
							.getMappedRegionDO().getRegionId());
				}
			}
			
			validateExpenseDetailTOs.add(validateExpTO);
		}/* End of For Loop */
		LOGGER.debug("ValidateExpenseServiceImpl::convertExpDOListToValidateExpTOList()::END");
		return validateExpenseDetailTOs;
	}

}
