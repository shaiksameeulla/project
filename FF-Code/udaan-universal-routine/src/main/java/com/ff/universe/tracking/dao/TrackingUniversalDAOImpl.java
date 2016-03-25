package com.ff.universe.tracking.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.domain.umc.UserDO;
import com.ff.organization.OfficeTO;
import com.ff.tracking.ProcessTO;
import com.ff.universe.tracking.constant.UniversalTrackingConstants;


/**
 * @author nkattung
 * 
 */
public class TrackingUniversalDAOImpl extends CGBaseDAO implements
		TrackingUniversalDAO {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(TrackingUniversalDAOImpl.class);

	@Override
	public ProcessDO getProcess(ProcessTO process) throws CGSystemException {
		LOGGER.trace("TrackingUniversalDAOImpl::getProcess()::START");
		Session session = null;
		Criteria criteria = null;
		ProcessDO processDO = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			criteria = session.createCriteria(ProcessDO.class);
			if (!StringUtil.isEmptyInteger(process.getProcessId()))
				criteria.add(Restrictions.eq(
						UniversalTrackingConstants.PARAM_PROCESS_ID,
						process.getProcessId()));
			if (StringUtils.isNotEmpty(process.getProcessCode()))
				criteria.add(Restrictions.eq(
						UniversalTrackingConstants.PARAM_PROCESS_CODE,
						process.getProcessCode()));
			processDO = (ProcessDO) criteria.uniqueResult();
		} catch (Exception e) {
			LOGGER.error("Error occures in TrackingUniversalDAOImpl::getProcess()::" 
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			session.flush();
			session.close();
		}
		LOGGER.trace("TrackingUniversalDAOImpl::getProcess()::END");
		return processDO;
	}

	@SuppressWarnings(CommonConstants.UNCHECKED)
	@Override
	public OfficeDO getOfficeByOfficeId(Integer officeId) throws CGSystemException {
		LOGGER.trace("TrackingUniversalDAOImpl::getOfficeByOfficeId()::START");
		List<OfficeDO> officeDOs = null;
		OfficeDO officeDO = null;
		officeDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
				UniversalTrackingConstants.QRY_GETOFFICE_BY_OFFICEID, UniversalTrackingConstants.OFFICE_ID,
				officeId);
		if(!officeDOs.isEmpty() && officeDOs!=null){
			officeDO = officeDOs.get(0);
		}
		LOGGER.trace("TrackingUniversalDAOImpl::getOfficeByOfficeId()::END");
		 return officeDO;
		
	}

	@Override
	public OfficeDO getLoggedInOffice(OfficeTO office) throws CGSystemException {
		LOGGER.trace("TrackingUniversalDAOImpl::getLoggedInOffice()::START");
		Session session = null;
		Criteria criteria = null;
		OfficeDO officeDO = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			criteria = session.createCriteria(OfficeDO.class);
			if (!StringUtil.isEmptyInteger(office.getOfficeId()))
				criteria.add(Restrictions.eq(
						UniversalTrackingConstants.OFFICE_ID,
						office.getOfficeId()));
			if (StringUtils.isNotEmpty(office.getOfficeCode()))
				criteria.add(Restrictions.eq(
						UniversalTrackingConstants.PARAM_OFFICE_CODE,
						office.getOfficeCode()));
			officeDO = (OfficeDO) criteria.uniqueResult();
		} catch (Exception e) {
			LOGGER.error("Error occured in getLoggedInOffice()..:" + e.getMessage());
			throw new CGSystemException(e);
		} finally {
			session.flush();
			session.close();
		}
		LOGGER.trace("TrackingUniversalDAOImpl::getLoggedInOffice()::END");
		return officeDO;
	}

	@SuppressWarnings(CommonConstants.UNCHECKED)
	@Override
	public List<OfficeDO> getOfficeByDestCityId(Integer officeId, Integer cityId)
			throws CGSystemException {
		LOGGER.trace("TrackingUniversalDAOImpl::getOfficeByDestCityId()::START");
		List<OfficeDO> officeDOs = null;
		String param[] = UniversalTrackingConstants.PARAMS.split(",");
		Object[] values = { officeId, cityId };
		officeDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
				UniversalTrackingConstants.QRY_GETOFFICE_BY_DESTCITYID,param,values);
		LOGGER.trace("TrackingUniversalDAOImpl::getOfficeByDestCityId()::END");
		return officeDOs;
	}
	@SuppressWarnings(CommonConstants.UNCHECKED)
	@Override
	public UserDO getCreatedByDeatils(Integer userId) throws CGSystemException {
		LOGGER.trace("ConsignmentTrackingDAOImpl::getCreatedByDeatils()::START");
		List<UserDO> result = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						UniversalTrackingConstants.QRY_GET_USER_BY_USERID,
						UniversalTrackingConstants.USER_ID, userId);

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
