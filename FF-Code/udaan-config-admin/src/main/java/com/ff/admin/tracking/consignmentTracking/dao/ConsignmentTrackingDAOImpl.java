/**
 * 
 */
package com.ff.admin.tracking.consignmentTracking.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.domain.tracking.ProcessMapDO;
import com.ff.domain.umc.UserDO;
import com.ff.umc.constants.UmcConstants;

/**
 * @author uchauhan
 * 
 */
public class ConsignmentTrackingDAOImpl extends CGBaseDAO implements
		ConsignmentTrackingDAO {

	
	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ConsignmentTrackingDAOImpl.class);

	@Override
	public List<BookingDO> viewTrackInformation(String consgNum, String refNum)
			throws CGSystemException {
		LOGGER.trace("ConsignmentTrackingDAOImpl::viewTrackInformation()::START");
		Session session = null;
		Criteria criteria = null;
		List<BookingDO> bookingList=null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			criteria = session.createCriteria(BookingDO.class);
			if (consgNum != null) {
				criteria.add(Restrictions.eq("consgNumber", consgNum));
			} else if (refNum != null) {
				criteria.add(Restrictions.eq("refNo", refNum));
				criteria.setMaxResults(2);
			}
			
			bookingList= criteria.list();
		} catch (Exception e) {
			LOGGER.error("Error occured in ConsignmentTrackingDAOImpl::viewTrackInformation()::" + e);
			throw new CGSystemException(e);
		} finally {
			session.flush();
			session.close();
		}
		LOGGER.trace("ConsignmentTrackingDAOImpl::viewTrackInformation()::END");
		return bookingList;
	}

	@Override
	public List<ConsignmentDO> getConsignmentDtls(String consgNum, String refNum)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("ConsignmentTrackingDAOImpl::getConsignmentDtls()::START");
		Session session = null;
		Criteria criteria = null;
		List<ConsignmentDO> consgList=null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			criteria = session.createCriteria(ConsignmentDO.class);
			if (consgNum != null) {
				criteria.add(Restrictions.eq("consgNo", consgNum));
//				commented since limit applied on child consignments also and getting only one child CN.
//				criteria.setMaxResults(1);
			} else if (refNum != null) {
				criteria.add(Restrictions.eq("refNo", refNum));
				criteria.setMaxResults(2);
			}			
			consgList = criteria.list();
		} catch (Exception e) {
			LOGGER.error("Error occured in ConsignmentTrackingDAOImpl::getConsignmentDtls()::" + e);
			throw new CGSystemException(e);
		} finally {
			session.flush();
			session.close();
		}
		LOGGER.trace("ConsignmentTrackingDAOImpl::getConsignmentDtls()::END");
		return consgList;
	}

	@SuppressWarnings(CommonConstants.UNCHECKED)
	@Override
	public ConsignmentDO getChildConsgDetails(String conNumber)
			throws CGSystemException {
		LOGGER.trace("ConsignmentTrackingDAOImpl::getChildConsgDetails()::START");
		List<ConsignmentDO> consignmentDOs = null;
		ConsignmentDO consgDO = null;
		consignmentDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
				AdminSpringConstants.GET_CHILD_CN_DETAILS,
				AdminSpringConstants.CONSG_NO, conNumber);
		if (consignmentDOs != null && !consignmentDOs.isEmpty()) {
			consgDO = consignmentDOs.get(0);
		}
		LOGGER.trace("ConsignmentTrackingDAOImpl::getChildConsgDetails()::END");
		return consgDO;
	}

	@SuppressWarnings(CommonConstants.UNCHECKED)
	@Override
	public List<ProcessMapDO> getDetailedTrackingInformation(String consgNum)
			throws CGSystemException {
		LOGGER.trace("ConsignmentTrackingDAOImpl::getDetailedTrackingInformation()::START");
		List<ProcessMapDO> processMapDOs = null;
		processMapDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
				AdminSpringConstants.GET_PROCESS_MAP_DETAILS,
				AdminSpringConstants.CONSG_NO, consgNum);
		LOGGER.trace("ConsignmentTrackingDAOImpl::getDetailedTrackingInformation()::END");
		return processMapDOs;
	}

	@SuppressWarnings(CommonConstants.UNCHECKED)
	@Override
	public List<ProcessDO> getProcessDetails() throws CGSystemException {
		LOGGER.trace("ConsignmentTrackingDAOImpl::getProcessDetails()::START");
		List<ProcessDO> processDOs = null;
		processDOs = getHibernateTemplate().findByNamedQuery(
				AdminSpringConstants.GET_PROCESS_DETAILS);
		LOGGER.trace("ConsignmentTrackingDAOImpl::getProcessDetails()::END");
		return processDOs;
	}

	@SuppressWarnings(CommonConstants.UNCHECKED)
	public List<StockStandardTypeDO> getTypeName() throws CGSystemException {
		LOGGER.trace("ConsignmentTrackingDAOImpl::getTypeName()::START");
		List<StockStandardTypeDO> stockTypeDOList = null;
		try {
			String type = "TRACKING";
			stockTypeDOList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							AdminSpringConstants.QRY_GET_Type_Name, "type",
							type);
		} catch (Exception e) {
			LOGGER.error("ERROR :: ConsignmentTrackingDAOImpl :: getTypeName()::::::"
					,e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("ConsignmentTrackingDAOImpl::getTypeName()::END");
		return stockTypeDOList;
	}

	@SuppressWarnings(CommonConstants.UNCHECKED)
	@Override
	public UserDO getCreatedByDeatils(Integer userId) throws CGSystemException {
		LOGGER.trace("ConsignmentTrackingDAOImpl::getCreatedByDeatils()::START");
		List<UserDO> result = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						UmcConstants.QRY_GET_USER_BY_USERID,
						UmcConstants.USER_ID, userId);

		LOGGER.trace("ConsignmentTrackingDAOImpl::getCreatedByDeatils()::END");
		if (result.isEmpty()){
			return null;
		}else if (result.size() > 1){
			return null;// TODO throw Exceptions
		}else{
			return result.get(0);
		}
	}

}
