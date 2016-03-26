package com.ff.web.global.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.geography.CityDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;
import com.ff.domain.umc.ApplScreenDO;
import com.ff.geography.CityTO;

public class GlobalServiceDAOImpl extends CGBaseDAO implements GlobalServiceDAO {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(GlobalServiceDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<StockStandardTypeDO> getAllStockStandardType(String typeName)
			throws CGSystemException {

		try {
			List<StockStandardTypeDO> stockStandardTypeDO = null;
			String queryName = "getStockStdTypeByTypeName";

			stockStandardTypeDO = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, "typeName",
							typeName);

			return stockStandardTypeDO;

		} catch (Exception e) {
			LOGGER.error(
					"ERROR : GlobalServiceDAOImpl.getAllStockStandardType", e);
			throw new CGSystemException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ApplScreenDO getScreenByCodeOrName(String screenCode,
			String screenName) throws CGSystemException {
		ApplScreenDO applScreenDO = null;
		Session session = null;
		Criteria criteria = null;
		try {
			session = createSession();
			criteria = session.createCriteria(ApplScreenDO.class,
					"applScreenDO");
			if (StringUtils.isNotEmpty(screenCode)) {
				criteria.add(Restrictions.eq("applScreenDO.screenCode",
						screenCode));
			}
			if (StringUtils.isNotEmpty(screenName)) {
				criteria.add(Restrictions.eq("applScreenDO.screenName",
						screenName));
			}
			applScreenDO = (ApplScreenDO) criteria.uniqueResult();

		} catch (Exception e) {
			LOGGER.error("ERROR : GeographyServiceDAOImpl.getCitiesByCity", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return applScreenDO;
	}

}
