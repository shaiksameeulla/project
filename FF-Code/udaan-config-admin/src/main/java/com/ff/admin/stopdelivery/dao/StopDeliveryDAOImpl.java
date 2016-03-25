package com.ff.admin.stopdelivery.dao;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.stopdelivery.constants.StopDeliveryConstants;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.serviceOffering.ReasonDO;
import com.ff.organization.OfficeTO;
import com.ff.umc.UserTO;

public class StopDeliveryDAOImpl extends CGBaseDAO  implements StopDeliveryDAO {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(StopDeliveryDAOImpl.class);
	
	public BookingDO getConsgBookingDeatils(String consgNo) throws CGSystemException{
		
		
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
				LOGGER.error("StopDeliveryDAOImpl :: getConsgBookingDeatils()::::::"
						+ e.getMessage());
				throw new CGSystemException(e);
			} finally{
				closeSession(session);
			}
			 LOGGER .debug("StopDeliveryDAOImpl: getConsgBookingDeatils(): END");
			return bookingDO;
		
	}
	
	
	public OfficeDO checkOriginOffBelongsToRHO(Integer rhoId,Integer branchOffId) throws CGSystemException{
		
		OfficeDO bookingDO=null;
		try {
			@SuppressWarnings("unchecked")
			List<OfficeDO> officeDos = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							StopDeliveryConstants.QRY_CHECK_RHOCONTAINS_ORIGIN_OFF,
							new String[] {
									StopDeliveryConstants.PARAM_RHOID,
									StopDeliveryConstants.PARAM_BRANCHOFFID },
							new Object[] { rhoId, branchOffId });
			if(!StringUtil.isEmptyColletion(officeDos)){
				bookingDO = officeDos.get(0);
			}
			
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::StopDeliveryDAOImpl::checkOriginOffBelongsToRHO() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("StopDeliveryDAOImpl :: checkOriginOffBelongsToRHO() :: End --------> ::::::");
		return bookingDO;
	}
	
	public OfficeDO checkOriginOffBelongsToHO(Integer hubOffId,Integer branchOffId)throws CGSystemException{
		OfficeDO bookingDO=null;
		try {
			@SuppressWarnings("unchecked")
			List<OfficeDO> officeDos = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							StopDeliveryConstants.QRY_CHECK_HOCONTAINS_ORIGIN_OFF,
							new String[] {
									StopDeliveryConstants.PARAM_HOID,
									StopDeliveryConstants.PARAM_BRANCHOFFID },
							new Object[] { hubOffId, branchOffId });
			if(!StringUtil.isEmptyColletion(officeDos)){
				bookingDO = officeDos.get(0);
			}
			
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::StopDeliveryDAOImpl::checkOriginOffBelongsToHO() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("StopDeliveryDAOImpl :: checkOriginOffBelongsToHO() :: End --------> ::::::");
		return bookingDO;
	}
	
	public boolean updateStopDeliveryFlag(String consgNo,Integer reasonId,String remark, UserTO userTO,OfficeTO loggedInOfficeTO)throws CGSystemException{
		LOGGER.debug("BillingCommonDAOImpl :: updateStopDeliveryFlag() :: Start --------> ::::::");
		boolean result = Boolean.FALSE;
		Session session = null;
		//Session session = openTransactionalSession();
		Query query = null;
		int i;
		try{
			if(StringUtils.isEmpty(remark)){
	            ReasonDO reason=new ReasonDO();
	            reason.setReasonId(reasonId);
				session = createSession();
				query = session.getNamedQuery(StopDeliveryConstants.QRY_UPDATE_STOPDELIVERY_FLAG);
				query.setString(StopDeliveryConstants.STOPDELIVERY_FLAG, StopDeliveryConstants.STOPDELIVERY_STATUS);
				query.setString(StopDeliveryConstants.CONSIGNMENT_NO, consgNo);
				query.setEntity(StopDeliveryConstants.STOPDELIVERY_REASON,reason);
				query.setTimestamp(StopDeliveryConstants.STOPDELIVERY_DATE, Calendar.getInstance().getTime());
				query.setTimestamp(StopDeliveryConstants.UPDATED_DATE, Calendar.getInstance().getTime());
				query.setInteger(StopDeliveryConstants.UPDATED_BY, userTO.getUserId());
				query.setInteger(StopDeliveryConstants.STOP_DELIVERY_OFF, loggedInOfficeTO.getOfficeId());
				 i = query.executeUpdate();
		     } 
			else{
				    ReasonDO reason=new ReasonDO();
		            reason.setReasonId(reasonId);
					session = createSession();
					query = session.getNamedQuery(StopDeliveryConstants.QRY_UPDATE_STOPDELIVERY_FLAG_WITH_REMARK);
					query.setString(StopDeliveryConstants.STOPDELIVERY_FLAG, StopDeliveryConstants.STOPDELIVERY_STATUS);
					query.setString(StopDeliveryConstants.CONSIGNMENT_NO, consgNo);
					query.setString(StopDeliveryConstants.REMARK, remark);
					query.setEntity(StopDeliveryConstants.STOPDELIVERY_REASON,reason);
					query.setTimestamp(StopDeliveryConstants.STOPDELIVERY_DATE, Calendar.getInstance().getTime());
					query.setTimestamp(StopDeliveryConstants.UPDATED_DATE, Calendar.getInstance().getTime());
					query.setInteger(StopDeliveryConstants.UPDATED_BY, userTO.getUserId());
					query.setInteger(StopDeliveryConstants.STOP_DELIVERY_OFF, loggedInOfficeTO.getOfficeId());
					 i = query.executeUpdate();
			 }
			 
			if(i>0)

				result = Boolean.TRUE;

		} catch(Exception e) {
			LOGGER.error("StopDeliveryDAOImpl :: updateStopDeliveryFlag()::::::"
					+ e);
			throw new CGSystemException(e);

		}finally{
            closeSession(session);
			//closeTransactionalSession(session);

		}
		 LOGGER .debug("StopDeliveryDAOImpl: updateStopDeliveryFlag(): END");
		return result;
	}
	
	public ManifestDO getOfficeEmailIdsByTravelPath(ConsignmentTO consignmentTO)throws CGSystemException{
		ManifestDO manifestDO=null;
		try{
			
			@SuppressWarnings("unchecked")
			List<ManifestDO> manifestDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							StopDeliveryConstants.QRY_GETCN_TRAVELPATH_OFFICEEMAIL,
							new String[] {
									StopDeliveryConstants.CONSIGNMENT_NO,
									StopDeliveryConstants.PARAM_OFFICED },
							new Object[] { consignmentTO.getConsgNo(), consignmentTO.getOrgOffId() });
			if(!StringUtil.isEmptyColletion(manifestDOs)){
				manifestDO = manifestDOs.get(0);
			}
			
		}catch(Exception e) {
			LOGGER.error("StopDeliveryDAOImpl :: getOfficeEmailIdsByTravelPath()::::::"
					+ e);
			throw new CGSystemException(e);
	   }
		
		return manifestDO;
	}
	
	@SuppressWarnings("unchecked")
	public List<OfficeDO> getAllHubOff(OfficeTO officeTO)throws CGSystemException{
		 List<OfficeDO> officeDOs=null;
		try{
			
			officeDOs=getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							StopDeliveryConstants.QRY_GET_ALL_HUBOFFCE,
							new String[] { StopDeliveryConstants.PARAM_RHOID },
							new Object[] { officeTO.getReportingRHO() });
			
		}catch(Exception e) {
			LOGGER.error("StopDeliveryDAOImpl :: getAllHubOff()::::::"
					+ e);
			throw new CGSystemException(e);
	   }
		return officeDOs;
	}
}
