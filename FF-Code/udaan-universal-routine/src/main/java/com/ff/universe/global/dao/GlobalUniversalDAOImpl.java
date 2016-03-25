package com.ff.universe.global.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.common.ConfigurableParamsDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;
import com.ff.universe.booking.constant.UniversalBookingConstants;
import com.ff.universe.mec.constant.MECUniversalConstants;

public class GlobalUniversalDAOImpl extends CGBaseDAO implements
		GlobalUniversalDAO {
	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(GlobalUniversalDAOImpl.class);

	@Override
	public List<ConfigurableParamsDO> getConfigurabParams()
			throws CGSystemException {
		
		List<ConfigurableParamsDO> configurableParamsDOList = null;
		String queryName = "from com.ff.domain.common.ConfigurableParamsDO confgparam where confgparam.paramName is not null";
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		try {
			configurableParamsDOList = hibernateTemplate.find(queryName);
		} catch (Exception e) {
			LOGGER.error("Exception occurred while retrieving static configured params", e);
			throw new CGSystemException(e);
		}
		return configurableParamsDOList;
	}
	@Override
	public List<ConfigurableParamsDO> getConfigParamValueByName(String paramName)
			throws CGSystemException {
		 return getHibernateTemplate().findByNamedQueryAndNamedParam(CommonConstants.CONFIGURABLE_PARAM_QUERRY, CommonConstants.PARAM_NAME, paramName);
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.global.dao.GlobalUniversalDAO#getStandardTypesByTypeName(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StockStandardTypeDO> getStandardTypesByTypeName(String typeName)
			throws CGSystemException {
		List<StockStandardTypeDO> stockStandardTypeDOs = null;	
		try {
			String queryName = "getStockStdTypeByTypeName";			
			stockStandardTypeDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, "typeName",
							typeName);
		} catch (Exception e) {
			LOGGER.error("ERROR : GlobalUniversalDAOImpl.getStandardTypesByTypeName", e);
			throw new CGSystemException(e);
		}
		return stockStandardTypeDOs;
	}

	@Override
	public void updateConfigurableParamValueByParamName(String paramName,
			String paramValue) throws CGSystemException {
		LOGGER.trace("GlobalUniversalDAOImpl :: updateConfigurableParamValueByParamName :: START");
		Session session = null;
		try {
			session = createSession();
			Query query = session.getNamedQuery(UniversalBookingConstants.QRY_UPDATE_CONFIG_PARAM_VALUE_BY_PARAM_NAME);
			query.setParameter(MECUniversalConstants.PARAM_NAME, paramName);
			query.setParameter(MECUniversalConstants.PARAM_VALUE, paramValue);
			query.executeUpdate();
		}
		catch (Exception e) {
			LOGGER.error("GlobalUniversalDAOImpl :: updateConfigurableParamValueByParamName :: ERROR",e);
			throw new CGSystemException(e);
		}
		finally {
			closeSession(session);
		}
		LOGGER.trace("GlobalUniversalDAOImpl :: updateConfigurableParamValueByParamName :: END");
	}
	
}
