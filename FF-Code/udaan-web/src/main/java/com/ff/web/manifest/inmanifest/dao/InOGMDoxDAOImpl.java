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
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.ComailDO;
import com.ff.domain.manifest.ConsignmentManifestDO;
import com.ff.manifest.ManifestBaseTO;
import com.ff.manifest.inmanifest.InManifestOGMTO;
import com.ff.web.manifest.inmanifest.constants.InManifestConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class InOGMDoxDAOImpl.
 * 
 * @author uchauhan
 */
public class InOGMDoxDAOImpl extends CGBaseDAO implements InOGMDoxDAO {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(InOGMDoxDAOImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.inmanifest.dao.InOGMDoxDAO#saveOrUpdateConsignment
	 * (com.ff.domain.consignment.ConsignmentDO)
	 */
	@Override
	public ConsignmentDO saveOrUpdateConsignment(ConsignmentDO consignmentDO)
			throws CGSystemException {
		LOGGER.debug("InOGMDoxDAOImpl :: saveOrUpdateConsignment() :: Start --------> ::::::");
		Session session = null;
		try {
			/*Session session = getHibernateTemplate().getSessionFactory()
					.openSession();*/
			session = openTransactionalSession();
			if (consignmentDO.getConsgId() != null) {
				ConsignmentDO consignmentDO1 = (ConsignmentDO) session.get(
						ConsignmentDO.class, consignmentDO.getConsgId());
				consignmentDO1.setDestPincodeId(consignmentDO
						.getDestPincodeId());
				consignmentDO1.setFinalWeight(consignmentDO.getFinalWeight());
				consignmentDO1.setMobileNo(consignmentDO.getMobileNo());
			} else {
				getHibernateTemplate().saveOrUpdate(consignmentDO);
			}
			//session.flush();

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InOGMDoxDAOImpl::saveOrUpdateConsignment() :: "
					, e);
			throw new CGSystemException(e);
		} finally {
			closeTransactionalSession(session);
		}
		LOGGER.debug("InOGMDoxDAOImpl :: saveOrUpdateConsignment() :: End --------> ::::::");
		return consignmentDO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.inmanifest.dao.InOGMDoxDAO#getConsgManifestedDetails
	 * (com.ff.manifest.ManifestBaseTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ConsignmentDO> getConsgManifestedDetails(ManifestBaseTO baseTO)
			throws CGSystemException {
		LOGGER.trace("InOGMDoxDAOImpl::getConsgManifestedDetails::START------------>:::::::");
		Integer manifestId = null;
		List<ConsignmentDO> consignmentDOs = null;
		try {
			if (baseTO.getManifestId() != null) {
				manifestId = baseTO.getManifestId();
				consignmentDOs = getHibernateTemplate()
						.findByNamedQueryAndNamedParam(
								InManifestConstants.GET_CONSG_MANIFESTED_DETAILS,
								"manifestId", manifestId);
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InOGMDoxDAOImpl::getConsgManifestedDetails() :: " , e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("InOGMDoxDAOImpl::getConsgManifestedDetails::END------------>:::::::");
		return consignmentDOs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.inmanifest.dao.InOGMDoxDAO#saveOrUpdateConsignment
	 * (com.ff.domain.manifest.ComailDO)
	 */
	@Override
	public ComailDO saveOrUpdateComail(ComailDO comailDO)
			throws CGSystemException {
		LOGGER.debug("InOGMDoxDAOImpl :: saveOrUpdateComail() :: Start --------> ::::::");
		Session session = null;		
		try {
			/*Session session = getHibernateTemplate().getSessionFactory()
					.openSession();*/
			session = openTransactionalSession();
			if (comailDO.getCoMailId() != null) {
				ComailDO comailDO1 = (ComailDO) session.get(ComailDO.class,
						comailDO.getCoMailId());
				comailDO1.setCoMailNo(comailDO.getCoMailNo());
				// todo: destination office and origin office to be updated or
				// not ??
			} else {
				getHibernateTemplate().saveOrUpdate(comailDO);
			}
			//session.flush();

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InOGMDoxDAOImpl::saveOrUpdateComail() :: "
					, e);
			throw new CGSystemException(e);
		} finally {
			closeTransactionalSession(session);
		}
		LOGGER.debug("InOGMDoxDAOImpl :: saveOrUpdateComail() :: End --------> ::::::");
		return comailDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ConsignmentManifestDO isConsgManifested(InManifestOGMTO ogmTO)
			throws CGSystemException {
		LOGGER.trace("InOGMDoxDAOImpl::isConsgManifested::START------------>:::::::");
		String param[] = InManifestConstants.PARAMS.split(",");
		List<ConsignmentManifestDO> consgManifestDOs = null;
		ConsignmentManifestDO consgManifestDO = null;
		try {
			if (ogmTO.getConsgNumber() != null) {
				String consgNum = ogmTO.getConsgNumber();
				String processCode = ogmTO.getUpdateProcessCode();
				Object[] values = { consgNum, processCode };
				consgManifestDOs = getHibernateTemplate()
						.findByNamedQueryAndNamedParam(
								InManifestConstants.IS_CONSG_MANIFESTED, param,
								values);
				if (consgManifestDOs != null) {
					consgManifestDO = consgManifestDOs.get(0);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InOGMDoxDAOImpl::isConsgManifested() :: " , e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("InOGMDoxDAOImpl::isConsgManifested::END------------>:::::::");
		return consgManifestDO;
	}
}
