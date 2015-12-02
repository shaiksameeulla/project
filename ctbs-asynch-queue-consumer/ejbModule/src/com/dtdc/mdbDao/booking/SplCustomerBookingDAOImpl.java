package src.com.dtdc.mdbDao.booking;

/*
 * @author rajmanda
 */


import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseDAO.CGBaseDAO;
import com.capgemini.lbs.framework.utils.EmailUtility;
import com.dtdc.domain.booking.specialcustomer.SplCustomerBookingDO;

// TODO: Auto-generated Javadoc
/**
 * The Class SplCustomerBookingDAOImpl.
 */
@SuppressWarnings("unchecked")
public class SplCustomerBookingDAOImpl extends CGBaseDAO implements
SplCustomerBookingDAO {

	/** logger. */
	private final static Logger LOGGER = LoggerFactory
	.getLogger(SplCustomerBookingDAOImpl.class);

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.booking.SplCustomerBookingDAO#saveSplCustBookingDetails(List)
	 */
	@Override
	public boolean saveSplCustBookingDetails(
			List<SplCustomerBookingDO> splCustomerBookingDOList)
	throws CGSystemException {
		boolean bookingStatus = false;
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		StringBuilder errorMessages=new StringBuilder();
		StringBuilder errorCNNumbers=new StringBuilder();
		try {
			if(splCustomerBookingDOList == null || splCustomerBookingDOList.isEmpty()){
				return false;
			}else{
				Iterator<SplCustomerBookingDO> itr = splCustomerBookingDOList.iterator();
				while (itr.hasNext()) {
					SplCustomerBookingDO bookingDO = itr.next();
					try{
						hibernateTemplate.saveOrUpdate(bookingDO);
					}catch(Exception e){
						logger.error(bookingDO.getConsignmentNumber()+"#################Unable Book in Central DB.####################");
						logger.error("SplCustomerBookingDAOImpl::saveSplCustBookingDetails occured:"
								+e.getMessage());
						errorMessages.append(e.getMessage());
						errorMessages.append("\n");
						errorCNNumbers.append(bookingDO.getConsignmentNumber()+",");
					}
				}
				bookingStatus = true;
			}
		} catch (Exception obj) {
			logger.error("SplCustomerBookingDAOImpl::saveSplCustBookingDetails occured:"
					+obj.getMessage());	
//				throw new CGBusinessException(obj);
		EmailUtility.sendEmail(
				new String[] { "ctbsaqc@dtdc.com" },"Unable Book in Central DB",
				"Unable Book in Central DB"+
				"CNNo:"+ errorCNNumbers +
				"ErrorLogs"+errorMessages); 
		}
		finally{
			hibernateTemplate.flush();
		}
		return bookingStatus;
	}

}
