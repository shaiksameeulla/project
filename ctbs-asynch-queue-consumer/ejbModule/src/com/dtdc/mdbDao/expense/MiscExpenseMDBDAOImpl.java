package src.com.dtdc.mdbDao.expense;


import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dtdc.domain.expense.MiscExpenseDO;

// TODO: Auto-generated Javadoc
/**
 * The Class MiscExpenseMDBDAOImpl.
 */
public class MiscExpenseMDBDAOImpl extends HibernateDaoSupport implements MiscExpenseMDBDAO {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = Logger.getLogger(MiscExpenseMDBDAOImpl.class);
	
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.expense.MiscExpenseMDBDAO#saveExpenses(MiscExpenseDO)
	 */
	@Override
	public void saveExpenses(MiscExpenseDO expenseDao) {
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		tx.begin();
		session.save(expenseDao);
		tx.commit();
		session.flush();
		session.close();
	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.expense.MiscExpenseMDBDAO#updateExpenses(MiscExpenseDO)
	 */
	@Override
	public void updateExpenses(MiscExpenseDO expenseDao) {
		//String hql = prepareUpdateQuery(expenseDao);
		String hql = prepareReadQuery(expenseDao);
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		//session.getc
		Query query = session.createQuery(hql);
		Transaction tx = session.beginTransaction();
		List<MiscExpenseDO> expnces = query.list();
		if(expnces != null && !expnces.isEmpty()) {
			MiscExpenseDO exp = expnces.get(0);
			exp.setConsignmentNumber(expenseDao.getConsignmentNumber());
			exp.setExpenditureType(expenseDao.getExpenditureType());
			exp.setExpenditureAmount(expenseDao.getExpenditureAmount());
			exp.setEnterebBy(expenseDao.getEnterebBy());
			exp.setRemark(expenseDao.getRemark());
			exp.setAuthorizer(expenseDao.getAuthorizer());
			exp.setExpenditureDate(expenseDao.getExpenditureDate());
			exp.setVoucherDate(expenseDao.getVoucherDate());
			exp.setApproved(expenseDao.getApproved());
		}
		tx.commit();
		session.flush();
		session.close();
	}
	
	/**
	 * Prepare update query.
	 *
	 * @param expenseDao the expense dao
	 * @return the string
	 */
	private String prepareUpdateQuery(MiscExpenseDO expenseDao) {
		StringBuffer queryBuffer = new StringBuffer();
		queryBuffer.append("update com.dtdc.domain.expense.MiscExpenseDO v set");
		queryBuffer.append(" v.consignmentNumber='" +expenseDao.getConsignmentNumber()+"'");
		queryBuffer.append(", v.expenditureType.expndTypeId=" +expenseDao.getExpenditureType().getExpndTypeId());
		queryBuffer.append(", v.expenditureAmount=" +expenseDao.getExpenditureAmount());
		queryBuffer.append(", v.enterebBy.employeeId=" +expenseDao.getEnterebBy().getEmployeeId());
		queryBuffer.append(", v.remark='" +expenseDao.getRemark()+"'");
		queryBuffer.append(", v.authorizer.employeeId=" +expenseDao.getAuthorizer().getEmployeeId());
		new SimpleDateFormat("yyyy-MM-dd");
		//queryBuffer.append(", v.expenditureDate='" + formater.format(expenseDao.getExpenditureDate()) + "'");
		//queryBuffer.append(", v.voucherDate='" + formater.format(expenseDao.getVoucherDate()) + "'");
		queryBuffer.append(", v.approved='" + expenseDao.getApproved()+"'");
		queryBuffer.append(" where v.voucherNumber='"+expenseDao.getVoucherNumber()+"'");
		return queryBuffer.toString();
	}
	
	/**
	 * Prepare read query.
	 *
	 * @param expenseDao the expense dao
	 * @return the string
	 */
	private String prepareReadQuery(MiscExpenseDO expenseDao) {
		StringBuffer queryBuffer = new StringBuffer();
		queryBuffer.append("FROM com.dtdc.domain.expense.MiscExpenseDO e");
				queryBuffer.append(" where e.voucherNumber='"+expenseDao.getVoucherNumber()+"'");
		return queryBuffer.toString();
	}
}
