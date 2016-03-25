package com.ff.admin.mec.expense.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.mec.expense.ExpenseDO;
import com.ff.to.mec.expense.ValidateExpenseTO;

public class ValidateExpenseDAOImpl extends CGBaseDAO implements ValidateExpenseDAO{

	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ValidateExpenseDAOImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpenseDO> searchValidateExpenseDtls(ValidateExpenseTO to)
			throws CGSystemException {
		LOGGER.debug("ValidateExpenseDAOImpl::searchValidateExpenseDtls()::START");
		List<ExpenseDO> expenseDOs = null;
		Session session = null;
		Criteria cr = null;
		try{
			session = createSession();
			cr = session.createCriteria(ExpenseDO.class, "exp");
			cr.add(Restrictions.eq("exp.expenseOfficeId.officeId", to.getOfficeId()));
			cr.add(Restrictions.between("exp.postingDate", to.getFromDt(), to.getNewToDt()));
			cr.add(Restrictions.eq("exp.status", to.getStatus()));
			if(!StringUtil.isStringEmpty(to.getTxNumber()))
				cr.add(Restrictions.eq("exp.txNumber", to.getTxNumber()));
			cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			expenseDOs = cr.list();
		} catch(Exception e) {
			LOGGER.error("Exception occurs in ValidateExpenseDAOImpl::searchValidateExpenseDtls()::",e);
			throw new CGSystemException(e); 
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ValidateExpenseDAOImpl::searchValidateExpenseDtls()::END");
		return expenseDOs;
	}

}
