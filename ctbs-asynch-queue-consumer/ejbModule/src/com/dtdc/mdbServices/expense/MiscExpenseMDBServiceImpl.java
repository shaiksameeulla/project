package src.com.dtdc.mdbServices.expense;

import java.util.List;

import org.apache.log4j.Logger;

import src.com.dtdc.mdbDao.expense.MiscExpenseMDBDAO;

import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.dtdc.domain.expense.ExpenditureTypeDO;
import com.dtdc.domain.expense.MiscExpenseDO;
import com.dtdc.domain.master.employee.EmployeeDO;
import com.dtdc.to.expense.CnMiscExpenseTO;

// TODO: Auto-generated Javadoc
/**
 * The Class MiscExpenseMDBServiceImpl.
 */
public class MiscExpenseMDBServiceImpl implements MiscExpenseMDBService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger
			.getLogger(MiscExpenseMDBServiceImpl.class);
	
	/** The misc expence dao. */
	private MiscExpenseMDBDAO miscExpenceDao;

	
	/**
	 * Sets the misc expence dao.
	 *
	 * @param miscExpenceDao the miscExpenceDao to set
	 */
	public void setMiscExpenceDao(MiscExpenseMDBDAO miscExpenceDao) {
		this.miscExpenceDao = miscExpenceDao;
	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.expense.MiscExpenseMDBService#saveMiscExpenses(CnMiscExpenseTO)
	 */
	@Override
	public void saveMiscExpenses(final CnMiscExpenseTO cnMiscExpenseTo) {
		miscExpenceDao.saveExpenses(getMiscExpenseDoFromTo(cnMiscExpenseTo));
	}
	
	/**
	 * Gets the misc expense do from to.
	 *
	 * @param teo the teo
	 * @return the misc expense do from to
	 */
	private MiscExpenseDO getMiscExpenseDoFromTo( CnMiscExpenseTO teo) {
		LOGGER.debug("MiscExpenseMDBServiceImpl::getMiscExpenseDoFromTo::start");
		MiscExpenseDO dao = new MiscExpenseDO();
		dao.setConsignmentNumber("");
		dao.setExpenditureId(teo.getExpndTypeId());
		ExpenditureTypeDO expType = new ExpenditureTypeDO();
		expType.setExpndTypeId(teo.getExpenditureType());
		dao.setExpenditureType(expType);
		
		dao.setExpenditureAmount(teo.getExpenditureAmount());
		EmployeeDO auth = new EmployeeDO();
		auth.setEmployeeId(teo.getEmpId());
		dao.setAuthorizer(auth);
		dao.setRemark(teo.getRemark());
		String expenseDate = teo.getExpenditureDate();
		LOGGER.debug("$$$$$$$$$$$$$$$$$$$$$MiscExpenseMDBServiceImpl::getMiscExpenseDoFromTo::expenseDate from TO" + expenseDate);
		dao.setExpenditureDate(DateFormatterUtil.stringToDDMMYYYYFormat(expenseDate));
		dao.setVoucherNumber(teo.getVoucherNumber());
		String voucherDate = teo.getVoucherDate();
		LOGGER.debug("$$$$$$$$$$$$$$$$$$$$$MiscExpenseMDBServiceImpl::getMiscExpenseDoFromTo::voucherDate from TO" + voucherDate);
		dao.setVoucherDate(DateFormatterUtil.stringToDDMMYYYYFormat(voucherDate));
		
		dao.setConsignmentNumber(teo.getCnNumber());
		dao.setApproved("Y");
		EmployeeDO enteredBy = new EmployeeDO();
		enteredBy.setEmployeeId(Integer.parseInt(teo.getEnteredBy()));
		dao.setEnterebBy(enteredBy);
		LOGGER.debug("MiscExpenseMDBServiceImpl::getMiscExpenseDoFromTo::end");
		return dao;
	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.expense.MiscExpenseMDBService#saveMiscExpenses(CGBaseTO)
	 */
	@Override
	public void saveMiscExpenses(CGBaseTO baseTo) {
		List<CnMiscExpenseTO> expenseList = (List<CnMiscExpenseTO>) baseTo.getBaseList();
		LOGGER.debug("MiscExpenseMDBServiceImpl::saveMiscExpenses::base list is: " + expenseList);
		if(expenseList != null && !expenseList.isEmpty()) {
			for(final CnMiscExpenseTO espense : expenseList) {
				try {
					saveMiscExpenses(espense);
				} catch (Exception ex) {
					LOGGER.error("MiscExpenseMDBServiceImpl::saveMiscExpenses::Exception::unable to process for voucher[" + espense.getVoucherNumber() + "] of consignment number [" + espense.getCnNumber() + "]");
				}
			}
		} else {
			LOGGER.debug("MiscExpenseMDBServiceImpl::saveMiscExpenses::base list is empty or null");
		}
		
		
	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.expense.MiscExpenseMDBService#updateExpenses(CnMiscExpenseTO)
	 */
	@Override
	public void updateExpenses(CnMiscExpenseTO miscExpenseTo) {
		miscExpenceDao.updateExpenses(getMiscExpenseDoFromTo(miscExpenseTo));
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.expense.MiscExpenseMDBService#updateExpenses(CGBaseTO)
	 */
	@Override
	public void updateExpenses(CGBaseTO baseTo) {
		final CnMiscExpenseTO miscExpenseTo = (CnMiscExpenseTO)baseTo.getBaseList().get(0);
		updateExpenses(miscExpenseTo);
	}
}
