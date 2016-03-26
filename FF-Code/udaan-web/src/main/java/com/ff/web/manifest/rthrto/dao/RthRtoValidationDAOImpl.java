package com.ff.web.manifest.rthrto.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.manifest.ConsignmentReturnDO;
import com.ff.manifest.rthrto.RthRtoValidationTO;
import com.ff.web.manifest.rthrto.constants.RthRtoManifestConstatnts;

// TODO: Auto-generated Javadoc
/**
 * The Class RthRtoValidationDAOImpl.
 */
public class RthRtoValidationDAOImpl extends CGBaseDAO implements RthRtoValidationDAO {


	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(RthRtoValidationDAOImpl.class);	

	/* (non-Javadoc)
	 * @see com.ff.web.manifest.rthrto.dao.RthRtoValidationDAO#saveOrUpdateConsignmentReturn(com.ff.domain.manifest.ConsignmentReturnDO)
	 */
	@Override
	public void saveOrUpdateConsignmentReturn(ConsignmentReturnDO consignmentReturnDO)
			throws CGSystemException {
		LOGGER.debug("RthRtoValidationDAOImpl :: saveOrUpdateConsignmentReturn() :: Start --------> ::::::");
		try{
			getHibernateTemplate().saveOrUpdate(consignmentReturnDO);
		}
		catch(Exception e){
			LOGGER.error("Exception Occured in::RthRtoValidationDAOImpl::saveOrUpdateConsignmentReturn() :: " , e);
			throw new CGSystemException(e);
		}		
		LOGGER.debug("RthRtoValidationDAOImpl :: saveOrUpdateConsignmentReturn() :: End --------> ::::::");
	}


	/* (non-Javadoc)
	 * @see com.ff.web.manifest.rthrto.dao.RthRtoValidationDAO#getDataListByNamedQueryAndNamedParam(java.lang.String, java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CGBaseDO> getDataListByNamedQueryAndNamedParam(
			String namedQuery, String param, Object value)
			throws CGSystemException {
		return getHibernateTemplate().findByNamedQueryAndNamedParam(namedQuery,
				param, value);
	}

	/* (non-Javadoc)
	 * @see com.ff.web.manifest.rthrto.dao.RthRtoValidationDAO#getDataListByNamedQueryAndNamedParam(java.lang.String, java.lang.String[], java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CGBaseDO> getDataListByNamedQueryAndNamedParam(
			String namedQuery, String[] params, Object[] values)
			throws CGSystemException {
		return getHibernateTemplate().findByNamedQueryAndNamedParam(namedQuery,
				params, values);
	}

	/* (non-Javadoc)
	 * @see com.ff.web.manifest.rthrto.dao.RthRtoValidationDAO#getDataByNamedQueryAndNamedParam(java.lang.String, java.lang.String[], java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public CGBaseDO getDataByNamedQueryAndNamedParam(String namedQuery,
			String[] params, Object[] values) {
		LOGGER.trace("RthRtoValidationDAOImpl::getDataByNamedQueryAndNamedParam::START------------>:::::::");
		CGBaseDO cgBaseDO = null;
		List<CGBaseDO> cgBaseDOs =  getHibernateTemplate().findByNamedQueryAndNamedParam(namedQuery,
				params, values);
		if(!StringUtil.isEmptyColletion(cgBaseDOs)){
			cgBaseDO = cgBaseDOs.get(0);
		}
		LOGGER.trace("RthRtoValidationDAOImpl::getDataByNamedQueryAndNamedParam::END------------>:::::::");
		return cgBaseDO;
	}

	/* (non-Javadoc)
	 * @see com.ff.web.manifest.rthrto.dao.RthRtoValidationDAO#getDataByNamedQueryAndNamedParam(java.lang.String, java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public CGBaseDO getDataByNamedQueryAndNamedParam(String namedQuery,
			String param, Object value) {
		LOGGER.trace("RthRtoValidationDAOImpl::getDataByNamedQueryAndNamedParam::START------------>:::::::");
		CGBaseDO cgBaseDO = null;
		List<CGBaseDO> cgBaseDOs =  getHibernateTemplate().findByNamedQueryAndNamedParam(namedQuery,
				param, param);
		if(!StringUtil.isEmptyColletion(cgBaseDOs)){
			cgBaseDO = cgBaseDOs.get(0);
		}
		LOGGER.trace("RthRtoValidationDAOImpl::getDataByNamedQueryAndNamedParam::END------------>:::::::");
		return cgBaseDO;
	}


	/* (non-Javadoc)
	 * @see com.ff.web.manifest.rthrto.dao.RthRtoValidationDAO#updateOnHoldConsgEmailStatus(java.util.List)
	 */
	@Override
	public void updateOnHoldConsgEmailStatus(List<Integer> consignmentReturnIds)
			throws CGSystemException {
		LOGGER.debug("RthRtoValidationDAOImpl :: updateOnHoldConsgEmailStatus() :: Start --------> ::::::");
		Session session = null;
		try {
			session = createSession();
			Query query = session.getNamedQuery("updateRtoEmailValidated");
			query.setString("isEmailValidated", CommonConstants.YES);
			query.setParameterList("consignmentReturnIds", consignmentReturnIds);
			query.executeUpdate();
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::RthRtoValidationDAOImpl::updateOnHoldConsgEmailStatus() :: "
					, e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("RthRtoValidationDAOImpl :: updateOnHoldConsgEmailStatus() :: End --------> ::::::");
	}


	/* (non-Javadoc)
	 * @see com.ff.web.manifest.rthrto.dao.RthRtoValidationDAO#getConsignmentReturnIds(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getConsignmentReturnIds(String reasonCode,
			String returnType) throws CGSystemException {
		LOGGER.debug("RthRtoValidationDAOImpl :: getConsignmentReturnIds() :: Start --------> ::::::");
		List<Integer> consignmentReturnIds = null;
		try {
			consignmentReturnIds = getHibernateTemplate().findByNamedQueryAndNamedParam(
					"getConsignmentReturnIds",
					new String[] {RthRtoManifestConstatnts.PARAM_RETURN_TYPE, RthRtoManifestConstatnts.PARAM_REASON_CODE },
					new Object[] { returnType, reasonCode});
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::RthRtoValidationDAOImpl::getConsignmentReturnIds() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("RthRtoValidationDAOImpl :: getConsignmentReturnIds() :: End --------> ::::::");
		return consignmentReturnIds;
	}


	@Override
	public boolean isConsgManifested(RthRtoValidationTO rthRtoValidationTO)
			throws CGSystemException {
		LOGGER.debug("RthRtoValidationDAOImpl :: isConsgManifested() :: Start --------> ::::::");

		boolean isCNManifested = Boolean.FALSE;
		try {
			long count = (long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							"isConsgManifested4RthValidation",
							new String[] { "consignmentNo", "operatingOffice",
									"manifestType" },
							new Object[] {
									rthRtoValidationTO.getConsignmentNumber(),
									rthRtoValidationTO.getOfficeTO()
											.getOfficeId(),
									CommonConstants.MANIFEST_TYPE_IN }).get(0);

			if (count > 0){
				isCNManifested = Boolean.TRUE;				
			}

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::RthRtoValidationDAOImpl::isConsgManifested() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("RthRtoValidationDAOImpl :: isConsgManifested() :: End --------> ::::::");
		return isCNManifested;

	}
}
