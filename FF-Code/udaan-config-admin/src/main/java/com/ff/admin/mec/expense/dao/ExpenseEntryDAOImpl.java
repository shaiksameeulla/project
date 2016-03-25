package com.ff.admin.mec.expense.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.mec.common.constants.MECCommonConstants;
import com.ff.domain.mec.expense.ExpenseDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.to.mec.expense.ExpenseTO;

/**
 * @author hkansagr
 */

public class ExpenseEntryDAOImpl extends CGBaseDAO implements ExpenseEntryDAO {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(ExpenseEntryDAOImpl.class);

	@Override
	public boolean saveOrUpdateExpenseDtls(ExpenseDO domain)
			throws CGSystemException {
		LOGGER.trace("ExpenseEntryDAOImpl::saveOrUpdateExpenseDtls()::START");
		try {
			getHibernateTemplate().saveOrUpdate(domain);
		} catch (Exception e) {
			LOGGER.error(
					"ExpenseEntryDAOImpl :: saveOrUpdateExpenseDtls :: Error",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("ExpenseEntryDAOImpl::saveOrUpdateExpenseDtls()::End");
		return Boolean.TRUE;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ExpenseDO searchExpenseDtls(ExpenseTO expenseTO)
			throws CGSystemException {
		LOGGER.trace("ExpenseEntryDAOImpl::searchExpenseDtls()::START");
		List<ExpenseDO> expenseDOs = null;
		try {
			String[] params = { MECCommonConstants.PARAM_TX_NUMBER,
					MECCommonConstants.PARAM_OFFICE_ID };
			Object[] values = { expenseTO.getTxNumber(),
					expenseTO.getLoginOfficeId() };
			expenseDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					MECCommonConstants.QRY_GET_EXPENSE_DTLS_BY_TX_NO, params,
					values);
		} catch (Exception e) {
			LOGGER.error("ExpenseEntryDAOImpl :: searchExpenseDtls :: Error", e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("ExpenseEntryDAOImpl::searchExpenseDtls()::END");
		return !StringUtil.isEmptyList(expenseDOs) ? expenseDOs.get(0) : null;
	}

	@Override
	public boolean updateExpenseStatus(Long expenseId, String expenseStatus,
			String sapStatus) throws CGSystemException {
		LOGGER.trace("ExpenseEntryDAOImpl::updateExpenseStatus()::START");
		boolean result = Boolean.FALSE;
		Session session = null;
		Query query = null;
		try {
			session = createSession();
			query = session
					.getNamedQuery(MECCommonConstants.QRY_UPDATE_EXPENSE_STATUS_BY_EXPENSE_ID);
			query.setLong(MECCommonConstants.PARAM_EXPENSE_ID, expenseId);
			query.setString(MECCommonConstants.PARAM_STATUS, expenseStatus);
			query.setString(MECCommonConstants.PARAM_SAP_STATUS, sapStatus);
			int i = query.executeUpdate();
			if (i > 0)
				result = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ExpenseEntryDAOImpl::updateExpenseStatus()::",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("ExpenseEntryDAOImpl::updateExpenseStatus()::END");
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProductDO getProductDtls(Integer productId) throws CGSystemException {
		LOGGER.debug("ExpenseEntryDAOImpl::getProductDtls()::START");
		ProductDO productDO = null;
		Session session = null;
		try {
			session = createSession();
			Criteria cr = session.createCriteria(ProductDO.class, "product");
			cr.add(Restrictions.eq("product.productId", productId));
			List<ProductDO> list = cr.list();
			if (!StringUtil.isEmptyColletion(list))
				productDO = list.get(0);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ExpenseEntryDAOImpl::getProductDtls()::",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ExpenseEntryDAOImpl::getProductDtls()::END");
		return productDO;
	}

}
