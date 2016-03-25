package com.ff.admin.master.dao;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.ff.admin.master.constants.HolidayConstants;
import com.ff.domain.geography.StateDO;
import com.ff.domain.masters.HolidayDO;
import com.ff.domain.masters.HolidayNameDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.master.HolidayTO;

public class HolidayDAOImpl extends CGBaseDAO implements HolidayDAO {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(HolidayDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<HolidayNameDO> getHolidayNameList() throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("HolidayDAOImpl::getHolidayNameList()::START");
		 List<HolidayNameDO> holidayNameDOs = null;
		Session session = null;
		Criteria criteria = null;
		try {
			session = createSession();
			criteria = session.createCriteria(HolidayNameDO.class);
			criteria.addOrder(Order.asc("holidayName"));
			holidayNameDOs = criteria.list();
		} catch (Exception e) {
			LOGGER.error("ERROR : HolidayDAOImpl::getHolidayNameList()", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("HolidayDAOImpl::getHolidayNameList()::END");
		return holidayNameDOs;
	}

	@Override
	public HolidayDO saveHoliday(HolidayDO holidayDO)
			throws CGBusinessException, CGSystemException {

		LOGGER.debug("HolidayDAOImpl::saveHoliday()::START");
		Session session = null;
		try {
			session = createSession();
			if (session != null) {
				session.saveOrUpdate(holidayDO);
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in HolidayDAOImpl::saveHoliday()",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("HolidayDAOImpl :: saveHoliday() :: END");
		return holidayDO;
	
	}

	@Override
	public List<HolidayDO> getHoliday(HolidayTO holidayTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("HolidayDAOImpl::getHoliday()::START");
		Session session = null;
		List<HolidayDO> holidayList = null;
		try {
			session = createSession();
			if (session != null) {
				Criteria criteria = session.createCriteria(HolidayDO.class);
				criteria.add(Restrictions.eq("date", DateUtil.slashDelimitedstringToDDMMYYYYFormat(holidayTO.getDate())));
				criteria.add(Restrictions.eq("status", HolidayConstants.ACTIVE));
				holidayList =criteria.list();
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in HolidayDAOImpl::getHoliday()",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("HolidayDAOImpl :: getHoliday() :: END");
		return holidayList;
	}

	@Override
	public void updateHoliday(HolidayDO holidayDO) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("HolidayDAOImpl::updateHoliday()::START");
		Session session = null;
		try {
			session = createSession();
			if (session != null) {
				session.update(holidayDO);
				session.flush();
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in HolidayDAOImpl::updateHoliday()",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("HolidayDAOImpl :: updateHoliday() :: END");
	}

	@Override
	public void updateHoliday(Integer regionId)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("HolidayDAOImpl::updateHoliday()::START");
		Session session = null;
		try {
			session = createSession();
			Query query = session
					.getNamedQuery(HolidayConstants.UPDATE_HOLIDAY_FOR_REGION);
			query.setParameter("regionId", regionId);
			query.executeUpdate();
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in HolidayDAOImpl::updateHoliday()",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("HolidayDAOImpl :: updateHoliday() :: END");
		
	}
	@Override
	public void updateHoliday(Integer regionId,Integer stateId)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("HolidayDAOImpl::updateHoliday()::START");
		Session session = null;
		try {
			session = createSession();
			Query query = session
					.getNamedQuery(HolidayConstants.UPDATE_HOLIDAY_FOR_STATE);
			query.setParameter("regionId", regionId);
			query.setParameter("stateId", stateId);
			query.executeUpdate();
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in HolidayDAOImpl::updateHoliday()",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("HolidayDAOImpl :: updateHoliday() :: END");
		
	}
	@Override
	public void updateHoliday(Integer regionId,Integer stateId,Integer cityId)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("HolidayDAOImpl::updateHoliday()::START");
		Session session = null;
		try {
			session = createSession();
			Query query = session
					.getNamedQuery(HolidayConstants.UPDATE_HOLIDAY_FOR_CITY);
			query.setParameter("regionId", regionId);
			query.setParameter("stateId", stateId);
			query.setParameter("cityId", cityId);
			query.executeUpdate();
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in HolidayDAOImpl::updateHoliday()",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("HolidayDAOImpl :: updateHoliday() :: END");
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<HolidayDO> searchHoliday(HolidayTO holidayTO) throws CGBusinessException, CGSystemException {
		LOGGER.debug("HolidayDAOImpl::searchHoliday::START::" + System.currentTimeMillis());
		Session session = null;
		List<HolidayDO> holidayList = null;
		try {
			session = createSession();
			if(session.isOpen()) {
				Criteria criteria = session.createCriteria(HolidayDO.class);
				
				
				//criteria.add(Restrictions.ge("date",DateUtil.slashDelimitedstringToDDMMYYYYFormat(holidayTO.getFromDate())));
				//criteria.add(Restrictions.lt("date",DateUtil.slashDelimitedstringToDDMMYYYYFormat(holidayTO.getToDate())));
				criteria.add(Restrictions.between("date", DateUtil.slashDelimitedstringToDDMMYYYYFormat(holidayTO.getFromDate()), DateUtil.slashDelimitedstringToDDMMYYYYFormat(holidayTO.getToDate())));
				criteria.add(Restrictions.eq("status", HolidayConstants.ACTIVE));
				
				if(holidayTO.getRegionId() != null && holidayTO.getRegionId() != 0) {
					criteria.add(Restrictions.in("regionId", Arrays.asList(holidayTO.getRegionId(), null)));
				}
				if(holidayTO.getStateId() != null && holidayTO.getStateId() != 0) {
					criteria.add(Restrictions.eq("stateId", holidayTO.getStateId()));
				}
				if(holidayTO.getCityId() != null && holidayTO.getCityId() != 0) {
					criteria.add(Restrictions.eq("cityId", holidayTO.getCityId()));
				}
				if(holidayTO.getBranchId() != null && holidayTO.getBranchId() != 0) {
					criteria.add(Restrictions.eq("branchId", holidayTO.getBranchId()));
				}
				/*if(holidayTO.getHolidayNameId() != null && holidayTO.getHolidayNameId() != 0){
					criteria.add(Restrictions.eq("holidayNameId", holidayTO.getHolidayNameId()));
				}*/
				
				holidayList = criteria.list();
				
				LOGGER.debug("HolidayDAOImpl::searchHoliday::DEBUG::--> Holiday List Size - " + holidayList.size());
			}
		} catch (Exception exception) {
			LOGGER.error("HolidayDAOImpl::searchHoliday::ERROR", exception);
			throw new CGSystemException(exception);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("HolidayDAOImpl::searchHoliday::END::" + System.currentTimeMillis());
		return holidayList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public StateDO getStateById(Integer stateId) throws CGSystemException {
		LOGGER.debug("HolidayDAOImpl::getStateById::START::"
				+ System.currentTimeMillis());
		Session session = null;
		StateDO stateDO = null;
		List<StateDO> stateDOs = null;
		try {
			session = createSession();
			if (session.isOpen()) {
				Criteria criteria = session.createCriteria(StateDO.class);
				criteria.add(Restrictions.eq("stateId", stateId));

				stateDOs = criteria.list();
				stateDO = !CGCollectionUtils.isEmpty(stateDOs) ? (StateDO) stateDOs
						.get(0) : null;

			}
		} catch (Exception exception) {
			LOGGER.error("HolidayDAOImpl::getStateById::ERROR", exception);
			throw new CGSystemException(exception);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("HolidayDAOImpl::getStateById::END::"
				+ System.currentTimeMillis());
		return stateDO;
	}

	@Override
	public OfficeDO getOfficeByOfficeId(Integer branchId) throws CGSystemException {
		LOGGER.debug("HolidayDAOImpl::getOfficeByOfficeId::START::"
				+ System.currentTimeMillis());
		Session session = null;
		OfficeDO officeDO = null;
		List<OfficeDO> officeDOs = null;
		try {
			session = createSession();
			if (session.isOpen()) {
				Criteria criteria = session.createCriteria(OfficeDO.class);
				criteria.add(Restrictions.eq("officeId", branchId));

				officeDOs = criteria.list();
				officeDO = !CGCollectionUtils.isEmpty(officeDOs) ? (OfficeDO) officeDOs
						.get(0) : null;

			}
		} catch (Exception exception) {
			LOGGER.error("HolidayDAOImpl::getOfficeByOfficeId::ERROR", exception);
			throw new CGSystemException(exception);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("HolidayDAOImpl::getOfficeByOfficeId::END::"
				+ System.currentTimeMillis());
		return officeDO;
	}

	@Override
	public HolidayNameDO getHolidayNameByHolidayId(Integer holidayNameId)
			throws CGSystemException {
		LOGGER.debug("HolidayDAOImpl::getHolidayNameByHolidayId::START::"
				+ System.currentTimeMillis());
		Session session = null;
		HolidayNameDO holidayNameDO = null;
		List<HolidayNameDO> HolidayNameDOs = null;
		try {
			session = createSession();
			if (session.isOpen()) {
				Criteria criteria = session.createCriteria(HolidayNameDO.class);
				criteria.add(Restrictions.eq("id", holidayNameId));

				HolidayNameDOs = criteria.list();
				holidayNameDO = !CGCollectionUtils.isEmpty(HolidayNameDOs) ? (HolidayNameDO) HolidayNameDOs
						.get(0) : null;

			}
		} catch (Exception exception) {
			LOGGER.error("HolidayDAOImpl::getHolidayNameByHolidayId::ERROR", exception);
			throw new CGSystemException(exception);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("HolidayDAOImpl::getHolidayNameByHolidayId::END::"
				+ System.currentTimeMillis());
		return holidayNameDO;
		
	}

	@Override
	public Boolean editExistingHoliday(HolidayTO holidayTO)
			throws CGSystemException {
		Session session = null;
		Boolean flag = Boolean.FALSE;
		try {
			session = createSession();
			if (session.isOpen()) {
				getHibernateTemplate().merge(holidayTO);
				flag = Boolean.TRUE;
			}
		} catch (Exception exception) {
			LOGGER.error("HolidayDAOImpl::getHolidayNameByHolidayId::ERROR",
					exception);
			throw new CGSystemException(exception);
		} finally {
			closeSession(session);
		}
		return flag;
	}
	
}
