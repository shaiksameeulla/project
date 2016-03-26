/**
 * 
 */
package com.ff.web.manifest.inmanifest.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.manifest.ManifestDO;
import com.ff.manifest.inmanifest.InMasterBagManifestTO;
import com.ff.web.manifest.inmanifest.constants.InManifestConstants;

/**
 * The Class InMasterBagManifestDAOImpl.
 * 
 * @author nkattung
 */
public class InMasterBagManifestDAOImpl extends CGBaseDAO implements
		InMasterBagManifestDAO {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(InMasterBagManifestDAOImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.inmanifest.dao.InMasterBagManifestDAO#searchManifestDtls
	 * (com.ff.manifest.inmanifest.InMasterBagManifestTO)
	 */
	@Override
	public ManifestDO searchManifestDtls(InMasterBagManifestTO manifestTO)
			throws CGSystemException {
		LOGGER.trace("InMasterBagManifestDAOImpl::searchManifestDtls::START------------>:::::::");
		ManifestDO manifestDO = null;
		Session session = null;
		Criteria criteria = null;
		try {
			session = createSession();
			Integer officeId = manifestTO.getLoggedInOfficeId();
			criteria = session.createCriteria(ManifestDO.class, "manifest");
			criteria.createAlias("destOffice", "officeDO");
			if (officeId != null) {
				criteria.add(Restrictions.eq("officeDO.officeId", officeId));
			}
			criteria.add(Restrictions.eq("manifestNo",
					manifestTO.getManifestNumber()));
			criteria.add(Restrictions.eq("manifestProcessCode",
					manifestTO.getProcessCode()));
			criteria.createAlias("updatingProcess", "update");
			criteria.add(Restrictions.in("update.processCode", manifestTO
					.getUpdateProcessCode().split(",")));
			criteria.add(Restrictions.eq("manifestType",
					manifestTO.getManifestType()));
			manifestDO = (ManifestDO) criteria.uniqueResult();

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InMasterBagManifestDAOImpl::searchManifestDtls() :: " , e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("InMasterBagManifestDAOImpl::searchManifestDtls::END------------>:::::::");
		return manifestDO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.dao.InMasterBagManifestDAO#
	 * getEmbeddedManifestDtls(com.ff.manifest.inmanifest.InMasterBagManifestTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ManifestDO> getEmbeddedManifestDtls(
			InMasterBagManifestTO manifestInputTO) throws CGSystemException {

		LOGGER.trace("InMasterBagManifestDAOImpl::getEmbeddedManifestDtls::START------------>:::::::");
		List<ManifestDO> manifestDOs = null;
		try {
			String[] paramNames = { InManifestConstants.MANIFEST_NO,
					InManifestConstants.MANIFEST_TYPE,
					InManifestConstants.MANIFEST_PROCESS_CODE };
			Object[] values = { manifestInputTO.getManifestNumber(),
					manifestInputTO.getManifestType(),
					manifestInputTO.getProcessCode() };
			manifestDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					InManifestConstants.QRY_GET_IN_MANIFEST_ENBEDDED_DTLS,
					paramNames, values);
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InMasterBagManifestDAOImpl::getEmbeddedManifestDtls() :: " , e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("InMasterBagManifestDAOImpl::getEmbeddedManifestDtls::END------------>:::::::");
		return manifestDOs;
	}

}
