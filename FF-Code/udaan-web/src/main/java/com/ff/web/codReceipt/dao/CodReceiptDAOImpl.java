package com.ff.web.codReceipt.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.to.codreceipt.ExpenseAliasTO;
import com.ff.universe.booking.constant.UniversalBookingConstants;
import com.ff.web.codReceipt.constants.CodReceiptConstants;

public class CodReceiptDAOImpl extends CGBaseDAO implements CodReceiptDAO  {

	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(CodReceiptDAOImpl.class);
	
	public ConsignmentDO getConsingmentDtlsForCodRecpt(String consgNumner)
			throws CGSystemException {
		ConsignmentDO consg = null;
		Session session = null;
		try {
			session = createSession();
			consg = (ConsignmentDO) session
					.createCriteria(ConsignmentDO.class)
					.add(Restrictions.eq(
							UniversalBookingConstants.QRY_PARAM_CONSG2,
							consgNumner)).uniqueResult();
		} catch (Exception e) {
			LOGGER.error("Error occured in CodReceiptDAOImpl :: getConsingmentDtlsForCodRecpt()..:"
					+ e.getMessage());
			throw new CGSystemException(e);

		} finally {
			closeSession(session);
		}
		return consg;
	}	
	
	public BookingDO getConsgBookingDtsForCodRcpt(String consgNo) throws CGSystemException{
		BookingDO bookingDO = null;
		Session session = null;
		Criteria cr = null;
		try {
			session =createSession();
			cr = session.createCriteria(BookingDO.class, "booking");
			cr.add(Restrictions.eq("booking.consgNumber", consgNo));
			@SuppressWarnings("unchecked")
			List<BookingDO> bookingDOs = (List<BookingDO>)cr.list();
			if(!StringUtil.isEmptyColletion(bookingDOs)){
				bookingDO = bookingDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("CodReceiptDAOImpl :: getConsgBookingDtsForCodRcpt()::::::"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally{
			closeSession(session);
		}
		 LOGGER .debug("CodReceiptDAOImpl: getConsgBookingDtsForCodRcpt(): END");
		return bookingDO;
	
      }
	
	public ExpenseAliasTO getExpenseDetails(Integer consgId)throws CGSystemException{
		LOGGER.debug("CodReceiptDAOImpl :: getExpenseDetails() :: Start --------> ::::::");
		ExpenseAliasTO expenseAliasTO = null;
		Session session = null;
		String query = null;
		try {
		
		    session = getHibernateTemplate().getSessionFactory().openSession();
		    query = CodReceiptConstants.GET_EXPENSE_DETAILS;
		    expenseAliasTO = (ExpenseAliasTO) session
			    .createSQLQuery(query)
			    .addScalar("glDescription")
			    .addScalar("amount")
			    .addScalar("seviceCharge")
			    .addScalar("serviceTax")
			    .addScalar("educationCess")
			    .addScalar("higherEduCess")
			    .addScalar("otherCharges")
			    .addScalar("totalExpenseAmt")
			    .setInteger("consgId", consgId)
			    .setResultTransformer(
				    Transformers.aliasToBean(ExpenseAliasTO.class)).uniqueResult();
		/*    if(!StringUtil.isEmptyColletion(expenseAliasTOs)){
		    	expenseAliasTO = expenseAliasTOs.get(0);
			}*/
		} catch (Exception e) {
		    LOGGER.error("ERROR : CodReceiptDAOImpl.getExpenseDetails",
			    e);
		    throw new CGSystemException(e);
		} finally {
		    session.close();
		    session = null;
		}

		LOGGER.debug("CodReceiptDAOImpl :: getExpenseDetails() :: End --------> ::::::");
		return expenseAliasTO;
	 
	}
}
