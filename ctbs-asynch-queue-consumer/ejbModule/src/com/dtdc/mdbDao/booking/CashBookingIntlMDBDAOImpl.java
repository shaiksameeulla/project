package src.com.dtdc.mdbDao.booking;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.dtdc.domain.booking.cashbooking.CashBookingDO;

// TODO: Auto-generated Javadoc
/**
 * The Class CashBookingIntlMDBDAOImpl.
 */
public class CashBookingIntlMDBDAOImpl extends HibernateDaoSupport implements CashBookingIntlMDBDAO {
	
	/** The logger. */
	private Logger logger = LoggerFactory
	.getLogger(CashBookingIntlMDBDAOImpl.class);

/* (non-Javadoc)
 * @see ejbModule.src.com.dtdc.mdbDao.booking.CashBookingIntlMDBDAO#saveCashBookingDetails(List)
 */
@Override
public boolean saveCashBookingDetails(
	List<CashBookingDO> cashBookingIntlDoList)
	throws CGBusinessException {
HibernateTemplate hibernateTemplate = getHibernateTemplate();
boolean bookingStatus = false;
try {
	Iterator<CashBookingDO> itr = cashBookingIntlDoList.iterator();
	while (itr.hasNext()) {
		CashBookingDO bookingIntlDO = itr.next();
		hibernateTemplate.saveOrUpdate(bookingIntlDO);
		hibernateTemplate.flush();
		// inserting vas products
	}
	bookingStatus = true;
} catch (Exception obj) {
	logger.error("CashBookingIntlMDBDAOImpl::saveCashBookingDetails::Exception occured:"
			+obj.getMessage());
	throw new CGBusinessException(obj);
}
return bookingStatus;
}

}
