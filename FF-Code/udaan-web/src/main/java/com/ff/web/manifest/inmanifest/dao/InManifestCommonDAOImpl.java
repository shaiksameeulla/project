/**
 * 
 */
package com.ff.web.manifest.inmanifest.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.ConsignmentManifestDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.manifest.ManifestMappedEmbeddedDO;
import com.ff.loadmanagement.ManifestTO;
import com.ff.manifest.ManifestBaseTO;
import com.ff.manifest.inmanifest.InBagManifestTO;
import com.ff.manifest.inmanifest.InManifestTO;
import com.ff.manifest.inmanifest.InManifestValidationTO;
import com.ff.manifest.inmanifest.InMasterBagManifestTO;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.inmanifest.constants.InManifestConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class InManifestCommonDAOImpl.
 * 
 * @author narmdr
 */
public class InManifestCommonDAOImpl extends CGBaseDAO implements
		InManifestCommonDAO {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(InManifestCommonDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public ConsignmentManifestDO getInManifestDtls(ManifestBaseTO manifestBaseTO)
			throws CGSystemException {
		LOGGER.trace("InManifestCommonDAOImpl::getInManifestDtls::START------------>:::::::");
		ConsignmentManifestDO consgManifestDO = null;
		List<ConsignmentManifestDO> consgManifestDOs = null;
		Object values[] = { manifestBaseTO.getManifestNumber(),
				manifestBaseTO.getProcessCode().split(","),
				manifestBaseTO.getUpdateProcessCode().split(",") };
		try {
			consgManifestDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							ManifestConstants.QRY_MANIFEST_DETAILS,
							ManifestConstants.params, values);
			consgManifestDO = !StringUtil.isEmptyList(consgManifestDOs) ? consgManifestDOs
					.get(0) : null;

		} catch (Exception e) {
			LOGGER.error("Error occured in InManifestCommonDAOImpl :: getManifestDtls()..:"
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("InManifestCommonDAOImpl::getInManifestDtls::END------------>:::::::");
		return consgManifestDO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.inmanifest.dao.InManifestCommonDAO#getManifestDtls
	 * (com.ff.manifest.ManifestBaseTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ManifestDO getManifestDtls(ManifestBaseTO manifestBaseTO)
			throws CGSystemException {
		LOGGER.trace("InManifestCommonDAOImpl::getManifestDtls::START------------>:::::::");
		ManifestDO manifestDO = null;
		List<ManifestDO> manifestDOs = null;
		Object values[] = { manifestBaseTO.getManifestNumber(),
				manifestBaseTO.getProcessCode().split(","),
				manifestBaseTO.getUpdateProcessCode().split(",") };
		try {
			manifestDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					ManifestConstants.QRY_IN_MANIFEST_DETAILS,
					ManifestConstants.params, values);
			manifestDO = !StringUtil.isEmptyList(manifestDOs) ? manifestDOs
					.get(0) : null;

			initializeSetByHib(manifestBaseTO, manifestDO);

		} catch (Exception e) {
			LOGGER.error("Error occured in InManifestCommonDAOImpl :: getManifestDtls()..:"
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("InManifestCommonDAOImpl::getManifestDtls::END------------>:::::::");
		return manifestDO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.inmanifest.dao.InManifestCommonDAO#saveOrUpdateManifest
	 * (com.ff.domain.manifest.ManifestDO)
	 */
	@Override
	public ManifestDO saveOrUpdateManifest(ManifestDO manifestDO)
			throws CGSystemException {
		LOGGER.debug("InManifestCommonDAOImpl :: saveOrUpdateManifest() :: Start --------> ::::::");
		try {
			getHibernateTemplate().saveOrUpdate(manifestDO);
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::saveOrUpdateManifest() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("InManifestCommonDAOImpl :: saveOrUpdateManifest() :: End --------> ::::::");
		return manifestDO;
	}

	/* (non-Javadoc)
	 * @see com.ff.web.manifest.inmanifest.dao.InManifestCommonDAO#saveOrMergeManifest(com.ff.domain.manifest.ManifestDO)
	 */
	@Override
	public ManifestDO saveOrMergeManifest(ManifestDO manifestDO)
			throws CGSystemException {
		LOGGER.debug("InManifestCommonDAOImpl :: saveOrMergeManifest() :: Start --------> ::::::");
		try {
			getHibernateTemplate().merge(manifestDO);
			
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::saveOrMergeManifest() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("InManifestCommonDAOImpl :: saveOrMergeManifest() :: End --------> ::::::");
		return manifestDO;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.dao.InManifestCommonDAO#
	 * saveOrUpdateManifestList(java.util.List)
	 */
	@Override
	public List<ManifestDO> saveOrUpdateManifestList(
			List<ManifestDO> manifestDOs) throws CGSystemException {
		LOGGER.debug("InManifestCommonDAOImpl :: saveOrUpdateManifestList() :: Start --------> ::::::");
		try {
			for (ManifestDO manifestDO : manifestDOs) {
				getHibernateTemplate().saveOrUpdate(manifestDO);
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::saveOrUpdateManifestList() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("InManifestCommonDAOImpl :: saveOrUpdateManifestList() :: End --------> ::::::");
		return manifestDOs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.dao.InManifestCommonDAO#
	 * saveOrUpdateManifestProcess(java.util.List)
	 */
/*	@Override
	public boolean saveOrUpdateManifestProcess(
			List<ManifestProcessDO> manifestProcessDOs)
			throws CGSystemException {
		LOGGER.debug("InManifestCommonDAOImpl :: saveOrUpdateManifestProcess() :: Start --------> ::::::");
		boolean isSaved = Boolean.FALSE;
		try {
			for (ManifestProcessDO manifestProcessDO : manifestProcessDOs) {
				getHibernateTemplate().saveOrUpdate(manifestProcessDO);
			}
			isSaved = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::saveOrUpdateManifestProcess() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("InManifestCommonDAOImpl :: saveOrUpdateManifestProcess() :: End --------> ::::::");
		return isSaved;
	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.dao.InManifestCommonDAO#
	 * getManifestByNoProcessConsgType
	 * (com.ff.manifest.inmanifest.InBagManifestTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ManifestDO getManifestByNoProcessConsgType(
			InBagManifestTO manifestBaseTO) throws CGSystemException {
		LOGGER.debug("InManifestCommonDAOImpl :: getManifestByNoProcessConsgType() :: Start --------> ::::::");
		ManifestDO manifestDO = null;
		try {
			List<ManifestDO> manifestDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							ManifestConstants.QRY_GET_MANIFEST_BY_NO_PROCESS_CONSG_TYPE,
							new String[] { "manifestNumber", "destOfficeId",
									"processCode", "updateProcessCode",
									"consignmentCode", "manifestType" },
							new Object[] {
									manifestBaseTO.getManifestNumber(),
									manifestBaseTO.getDestinationOfficeId(),
									manifestBaseTO.getProcessCode(),
									manifestBaseTO.getUpdateProcessCode(),
									manifestBaseTO.getConsignmentTypeTO()
											.getConsignmentCode(),
									manifestBaseTO.getManifestType() });
			manifestDO = !StringUtil.isEmptyList(manifestDOs) ? manifestDOs
					.get(0) : null;

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::getManifestByNoProcessConsgType() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("InManifestCommonDAOImpl :: getManifestByNoProcessConsgType() :: End --------> ::::::");
		return manifestDO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.dao.InManifestCommonDAO#
	 * getEmbeddedManifestDtlsByEmbeddedId(com.ff.manifest.ManifestBaseTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ManifestDO> getEmbeddedManifestDtlsByEmbeddedId(
			ManifestBaseTO manifestBaseTO) throws CGSystemException {
		LOGGER.debug("InManifestCommonDAOImpl :: getEmbeddedManifestDtlsByEmbeddedId() :: Start --------> ::::::");
		List<ManifestDO> manifestDOs = null;
		try {
			manifestDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							ManifestConstants.QRY_GET_EMBEDDED_MANIFEST_DTLS_BY_EMBEDDED_ID,
							"manifestEmbeddedId",
							manifestBaseTO.getManifestId());

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::getEmbeddedManifestDtlsByEmbeddedId() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("InManifestCommonDAOImpl :: getEmbeddedManifestDtlsByEmbeddedId() :: End --------> ::::::");
		return manifestDOs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.inmanifest.dao.InManifestCommonDAO#reportLessBaggage
	 * (com.ff.manifest.inmanifest.InMasterBagManifestTO)
	 */
	@SuppressWarnings("unchecked")
	public List<String> reportLessBaggage(
			InMasterBagManifestTO inMasterBagManifestTO)
			throws CGSystemException {
		LOGGER.trace("InManifestCommonDAOImpl::reportLessBaggage::START------------>:::::::");
		ManifestDO manifestDO = null;
		List<ManifestDO> manifestDOs = null;
		Object values[] = { inMasterBagManifestTO.getManifestNumber(),
				CommonConstants.PROCESS_OUT_MANIFEST_MATER_BAG,
				InManifestConstants.UPDATED_PROCESS_CODE };
		Session session = null;
		/*
		 * session = getHibernateTemplate().getSessionFactory() .openSession();
		 */

		Transaction tx = null;
		Integer manifestId = null;
		List<String> manifestNumbers = null;
		try {
			session = createSession();
			tx = session.beginTransaction();
			manifestDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					ManifestConstants.QRY_IN_MANIFEST_DETAILS,
					ManifestConstants.params, values);
			manifestDO = !StringUtil.isEmptyList(manifestDOs) ? manifestDOs
					.get(0) : null;
			manifestId = manifestDO.getManifestId();
			Object values1[] = { inMasterBagManifestTO.getBplNumbers(),
					manifestId, CommonConstants.PROCESS_OUT_MANIFEST_MATER_BAG,
					InManifestConstants.UPDATED_PROCESS_CODE };
			manifestNumbers = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							InManifestConstants.QRY_LESS_MANIFEST_NUMBERS,
							InManifestConstants.params, values1);

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			LOGGER.error("Error occured in InManifestCommonDAOImpl :: reportLessBaggage()..:"
					, e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("InManifestCommonDAOImpl::reportLessBaggage::END------------>:::::::");
		return manifestNumbers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.inmanifest.dao.InManifestCommonDAO#getManifestId(
	 * com.ff.manifest.inmanifest.InManifestTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer getManifestId(InManifestTO inManifestTO)
			throws CGSystemException {
		LOGGER.debug("InManifestCommonDAOImpl :: getManifestId() :: Start --------> ::::::");
		Integer manifestId = null;
		try {
			List<Integer> manifestIds = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							ManifestConstants.QRY_GET_MANIFEST_ID,
							new String[] { "manifestNumber", "processCode",
									"updateProcessCode" },
							new Object[] {
									inManifestTO.getManifestNumber(),
									inManifestTO.getProcessCode().split(
											CommonConstants.COMMA),
									inManifestTO.getUpdateProcessCode().split(
											CommonConstants.COMMA) });
			manifestId = !StringUtil.isEmptyList(manifestIds) ? manifestIds
					.get(0) : null;

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::getManifestId() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("InManifestCommonDAOImpl :: getManifestId() :: End --------> ::::::");
		return manifestId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.inmanifest.dao.InManifestCommonDAO#getLessManifestNumbers
	 * (com.ff.manifest.inmanifest.InManifestTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getLessManifestNumbers(InManifestTO inManifestTO)
			throws CGSystemException {
		LOGGER.debug("InManifestCommonDAOImpl :: getLessManifestNumbers() :: Start --------> ::::::");
		List<String> manifestNos = null;
		try {
			manifestNos = getHibernateTemplate().findByNamedQueryAndNamedParam(
					ManifestConstants.QRY_GET_LESS_MANIFEST_NUMBERS,
					new String[] { "manifestNumbers", "manifestEmbeddedId", },
					new Object[] { inManifestTO.getManifestNoList(),
							inManifestTO.getManifestId() });
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::getLessManifestNumbers() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("InManifestCommonDAOImpl :: getLessManifestNumbers() :: End --------> ::::::");
		return manifestNos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.dao.InManifestCommonDAO#
	 * getManifestIdByEmbeddedIdAndMfNo(com.ff.manifest.inmanifest.InManifestTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer getManifestIdByEmbeddedIdAndMfNo(InManifestTO inManifestTO)
			throws CGSystemException {
		LOGGER.debug("InManifestCommonDAOImpl :: getManifestIdByEmbeddedIdAndMfNo() :: Start --------> ::::::");
		Integer manifestId = null;
		try {
			List<Integer> manifestIds = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							ManifestConstants.QRY_GET_MANIFEST_ID_BY_EMBEDDED_ID_AND_MF_NO,
							new String[] { "manifestNumber",
									"manifestEmbeddedId" },
							new Object[] { inManifestTO.getManifestNumber(),
									inManifestTO.getManifestId() });
			manifestId = !StringUtil.isEmptyList(manifestIds) ? manifestIds
					.get(0) : null;

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::getManifestIdByEmbeddedIdAndMfNo() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("InManifestCommonDAOImpl :: getManifestIdByEmbeddedIdAndMfNo() :: End --------> ::::::");
		return manifestId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.inmanifest.dao.InManifestCommonDAO#getConsgDetails
	 * (java.lang.String)
	 */
	@Override
	public ConsignmentManifestDO getConsgDetails(String consgNum,
			String processCode, String manifestNum) throws CGSystemException {
		LOGGER.trace("InManifestCommonDAOImpl::getConsgDetails::START------------>:::::::");
		Session session = null;
		ConsignmentManifestDO consgManifestDO = null;
		Criteria criteria = null;
		try {
			// session =
			// getHibernateTemplate().getSessionFactory().openSession();
			session = createSession();
			criteria = session.createCriteria(ConsignmentManifestDO.class)
					.createAlias("consignment", "consignmentDO");
			criteria = criteria.createAlias("manifest", "manifestDO");
			criteria = criteria.add(
					Restrictions.eq("consignmentDO.consgNo", consgNum)).add(
					Restrictions.eq("manifestDO.manifestProcessCode",
							processCode));
			if (manifestNum != null) {
				criteria = criteria.add(Restrictions.eq(
						"manifestDO.manifestNo", manifestNum));
			}
			consgManifestDO = (ConsignmentManifestDO) criteria.uniqueResult();
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::getConsgDetails() :: "
					, e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}

		LOGGER.debug("InManifestCommonDAOImpl :: getConsgDetails() :: End --------> ::::::");
		return consgManifestDO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.dao.InManifestCommonDAO#
	 * getManifestConsignmentDtlsByManifestId(com.ff.manifest.ManifestBaseTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ConsignmentManifestDO> getManifestConsignmentDtlsByManifestId(
			ManifestBaseTO manifestBaseTO) throws CGSystemException {
		LOGGER.debug("InManifestCommonDAOImpl :: getManifestConsignmentDtlsByManifestId() :: Start --------> ::::::");
		List<ConsignmentManifestDO> ConsignmentManifestDOs = null;
		try {
			ConsignmentManifestDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							InManifestConstants.QRY_GET_MANIFEST_CONSIGNMENT_DTLS_BY_MANIFEST_ID,
							"manifestId", manifestBaseTO.getManifestId());

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::getManifestConsignmentDtlsByManifestId() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("InManifestCommonDAOImpl :: getManifestConsignmentDtlsByManifestId() :: End --------> ::::::");
		return ConsignmentManifestDOs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.dao.InManifestCommonDAO#
	 * saveOrUpdateConsignmentList(java.util.List)
	 */
	@Override
	public List<ConsignmentDO> saveOrUpdateConsignmentList(
			List<ConsignmentDO> consignmentDOs) throws CGSystemException {
		LOGGER.debug("InManifestCommonDAOImpl :: saveOrUpdateConsignmentList() :: Start --------> ::::::");
		try {
			for (ConsignmentDO consignmentDO : consignmentDOs) {
				getHibernateTemplate().saveOrUpdate(consignmentDO);
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::saveOrUpdateConsignmentList() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("InManifestCommonDAOImpl :: saveOrUpdateConsignmentList() :: End --------> ::::::");
		return consignmentDOs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.dao.InManifestCommonDAO#
	 * saveOrUpdateConsignmentManifestList(java.util.List)
	 */
	@Override
	public List<ConsignmentManifestDO> saveOrUpdateConsignmentManifestList(
			List<ConsignmentManifestDO> consignmentManifestDOs)
			throws CGSystemException {
		LOGGER.debug("InManifestCommonDAOImpl :: saveOrUpdateConsignmentManifestList() :: Start --------> ::::::");
		try {
			for (ConsignmentManifestDO consignmentManifestDO : consignmentManifestDOs) {
				getHibernateTemplate().saveOrUpdate(consignmentManifestDO);
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::saveOrUpdateConsignmentManifestList() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("InManifestCommonDAOImpl :: saveOrUpdateConsignmentManifestList() :: End --------> ::::::");
		return consignmentManifestDOs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.dao.InManifestCommonDAO#
	 * saveOrUpdateConsignment(com.ff.domain.consignment.ConsignmentDO)
	 */
	@Override
	public ConsignmentDO saveOrUpdateConsignment(ConsignmentDO consignmentDO)
			throws CGSystemException {
		LOGGER.debug("InManifestCommonDAOImpl :: saveOrUpdateConsignment() :: Start --------> ::::::");
		try {
			getHibernateTemplate().saveOrUpdate(consignmentDO);
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::saveOrUpdateConsignment() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("InManifestCommonDAOImpl :: saveOrUpdateConsignment() :: End --------> ::::::");
		return consignmentDO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.dao.InManifestCommonDAO#
	 * getCNPricingDetailsByConsgId(java.lang.Integer)
	 */
	/*
	 * @Override public CNPricingDetailsDO getCNPricingDetailsByConsgId(Integer
	 * consgId) throws CGSystemException { LOGGER.debug(
	 * "InManifestCommonDAOImpl :: getCNPricingDetailsByConsgId() :: Start --------> ::::::"
	 * ); CNPricingDetailsDO cnPricingDetailsDO = null; Session session = null;
	 * try { // session = //
	 * getHibernateTemplate().getSessionFactory().openSession(); session =
	 * createSession(); cnPricingDetailsDO = (CNPricingDetailsDO) session
	 * .createCriteria(CNPricingDetailsDO.class)
	 * .add(Restrictions.eq("consignment.consgId", consgId)) .uniqueResult(); }
	 * catch (Exception e) { LOGGER.error(
	 * "Exception Occured in::InManifestCommonDAOImpl::getCNPricingDetailsByConsgId() :: "
	 * , e); throw new CGSystemException(e); } finally { closeSession(session);
	 * } LOGGER.debug(
	 * "InManifestCommonDAOImpl :: getCNPricingDetailsByConsgId() :: End --------> ::::::"
	 * ); return cnPricingDetailsDO; }
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.inmanifest.dao.InManifestCommonDAO#getLessConsgNumbers
	 * (com.ff.manifest.inmanifest.InManifestTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getLessConsgNumbers(InManifestTO inManifestTO)
			throws CGSystemException {
		LOGGER.debug("InManifestCommonDAOImpl :: getLessConsgNumbers() :: Start --------> ::::::");
		List<String> consgNos = null;
		try {
			consgNos = getHibernateTemplate().findByNamedQueryAndNamedParam(
					InManifestConstants.QRY_GET_LESS_CONSG_NUMBERS,
					new String[] { "consgNumbers", "manifestId", },
					new Object[] { inManifestTO.getConsgNoList(),
							inManifestTO.getManifestId() });
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::getLessConsgNumbers() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("InManifestCommonDAOImpl :: getLessConsgNumbers() :: End --------> ::::::");
		return consgNos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.dao.InManifestCommonDAO#
	 * getConsgManifestIdByManifestIdAndConsgNo
	 * (com.ff.manifest.inmanifest.InManifestTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer getConsgManifestIdByManifestIdAndConsgNo(
			InManifestTO inManifestTO) throws CGSystemException {
		LOGGER.debug("InManifestCommonDAOImpl :: getConsgManifestIdByManifestIdAndConsgNo() :: Start --------> ::::::");
		Integer consgManifestId = null;
		try {
			List<Integer> consgManifestIds = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							InManifestConstants.QRY_GET_CONSG_MANIFEST_ID_BY_MANIFEST_ID_AND_CONSG_NO,
							new String[] { "consgNumber", "manifestId" },
							new Object[] { inManifestTO.getConsgNumber(),
									inManifestTO.getManifestId() });
			consgManifestId = !StringUtil.isEmptyList(consgManifestIds) ? consgManifestIds
					.get(0) : null;

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::getConsgManifestIdByManifestIdAndConsgNo() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("InManifestCommonDAOImpl :: getConsgManifestIdByManifestIdAndConsgNo() :: End --------> ::::::");
		return consgManifestId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.dao.InManifestCommonDAO#
	 * saveOrUpdateCnPricingDetails(com.ff.domain.booking.CNPricingDetailsDO)
	 */
	@Override
	/*
	 * public CNPricingDetailsDO saveOrUpdateCnPricingDetails(
	 * CNPricingDetailsDO cnPricingDetailsDO) throws CGSystemException {
	 * LOGGER.debug(
	 * "InManifestCommonDAOImpl :: saveOrUpdateCnPricingDetails() :: Start --------> ::::::"
	 * ); try { getHibernateTemplate().saveOrUpdate(cnPricingDetailsDO); } catch
	 * (Exception e) { LOGGER.error(
	 * "Exception Occured in::InManifestCommonDAOImpl::saveOrUpdateCnPricingDetails() :: "
	 * , e); throw new CGSystemException(e); } LOGGER.debug(
	 * "InManifestCommonDAOImpl :: saveOrUpdateCnPricingDetails() :: End --------> ::::::"
	 * ); return cnPricingDetailsDO; }
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.inmanifest.dao.InManifestCommonDAO#updateCnPricingDetails
	 * (com.ff.booking.CNPricingDetailsTO)
	 */
	public boolean updateCnPricingDetails(CNPricingDetailsTO cnPricingDetailsTO)
			throws CGSystemException {
		LOGGER.debug("InManifestCommonDAOImpl :: updateCnPricingDetails() :: Start --------> ::::::");
		Boolean isUpdated = Boolean.FALSE;
		Session session = null;
		try {
			/*
			 * session = getHibernateTemplate().getSessionFactory()
			 * .openSession();
			 */

			session = openTransactionalSession();
			Query query = session
					.getNamedQuery(InManifestConstants.QRY_UPDATE_CN_PRICING_DETAILS);
			query.setDouble(InManifestConstants.PARAM_DECLARED_VALUE,
					cnPricingDetailsTO.getDeclaredvalue());
			query.setDouble(InManifestConstants.PARAM_COD_AMT,
					cnPricingDetailsTO.getCodAmt());
			query.setDouble(InManifestConstants.PARAM_TO_PAY_CHG,
					cnPricingDetailsTO.getTopayChg());
			query.setInteger(InManifestConstants.PARAM_PRICE_ID,
					cnPricingDetailsTO.getPriceId());
			query.executeUpdate();
			isUpdated = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::updateCnPricingDetails() :: "
					, e);
			throw new CGSystemException(e);
		} finally {
			// closeSession(session);
			closeTransactionalSession(session);
		}
		LOGGER.debug("InManifestCommonDAOImpl :: updateCnPricingDetails() :: End --------> ::::::");
		return isUpdated;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.inmanifest.dao.InManifestCommonDAO#getConsignmentDetails
	 * (com.ff.manifest.inmanifest.InManifestTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ConsignmentDO getConsignmentDetails(ConsignmentTO consignmentTO)
			throws CGSystemException {
		LOGGER.debug("InManifestCommonDAOImpl :: getConsignmentDetails() :: Start --------> ::::::");
		ConsignmentDO consignmentDO = null;
		try {
			List<ConsignmentDO> consignmentDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							InManifestConstants.QRY_GET_CONSIGNMENT_DETAILS,
							new String[] { "consgNumber", "consgTypeCode", },
							new Object[] {
									consignmentTO.getConsgNo(),
									consignmentTO.getTypeTO()
											.getConsignmentCode() });
			if (!StringUtil.isEmptyColletion(consignmentDOs)) {
				consignmentDO = consignmentDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::getConsignmentDetails() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("InManifestCommonDAOImpl :: getConsignmentDetails() :: End --------> ::::::");
		return consignmentDO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.dao.InManifestCommonDAO#
	 * getConsignmentIdByConsgNo(com.ff.manifest.inmanifest.InManifestTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer getConsignmentIdByConsgNo(InManifestTO inManifestTO)
			throws CGSystemException {
		LOGGER.debug("InManifestCommonDAOImpl :: getConsignmentIdByConsgNo() :: Start --------> ::::::");
		Integer consgId = null;
		try {
			List<Integer> consgIds = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							InManifestConstants.QRY_GET_CONSIGNMENT_ID_BY_CONSG_NO,
							"consgNumber", inManifestTO.getConsgNumber());
			consgId = !StringUtil.isEmptyList(consgIds) ? consgIds.get(0)
					: null;

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::getConsignmentIdByConsgNo() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("InManifestCommonDAOImpl :: getConsignmentIdByConsgNo() :: End --------> ::::::");
		return consgId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.dao.InManifestCommonDAO#
	 * getManifestHeaderDetails
	 * (com.ff.manifest.inmanifest.InManifestValidationTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ManifestDO> getManifestHeaderDetails(
			InManifestValidationTO inManifestValidationTO)
			throws CGSystemException {
		LOGGER.debug("InManifestCommonDAOImpl :: getManifestHeaderDetails() :: Start --------> ::::::");
		List<ManifestDO> manifestDOs = null;
		try {
			manifestDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					InManifestConstants.QRY_GET_MANIFEST_HEADER,
					new String[] { "manifestType", "processCode",
							"updateProcessCode", "todayDate", "prevDate" },
					new Object[] { inManifestValidationTO.getManifestType(),
							inManifestValidationTO.getProcessCode(),
							inManifestValidationTO.getUpdateProcessCode(),
							inManifestValidationTO.getManifestDate(),
							inManifestValidationTO.getPreviousDate() });

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::getManifestHeaderDetails() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("InManifestCommonDAOImpl :: getManifestHeaderDetails() :: End --------> ::::::");
		return manifestDOs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.dao.InManifestCommonDAO#
	 * getManifestNumbersByEmbeddedId(com.ff.manifest.inmanifest.InManifestTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getManifestNumbersByEmbeddedId(InManifestTO inManifestTO)
			throws CGSystemException {
		LOGGER.debug("InManifestCommonDAOImpl :: getManifestNumbersByEmbeddedId() :: Start --------> ::::::");
		List<String> manifestNos = null;
		try {
			manifestNos = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							InManifestConstants.QRY_GET_MANIFEST_NUMBERS_BY_EMBEDDED_ID,
							"manifestEmbeddedId", inManifestTO.getManifestId());
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::getManifestNumbersByEmbeddedId() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("InManifestCommonDAOImpl :: getManifestNumbersByEmbeddedId() :: End --------> ::::::");
		return manifestNos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.dao.InManifestCommonDAO#
	 * getConsgNumbersByManifestId(com.ff.manifest.inmanifest.InManifestTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getConsgNumbersByManifestId(InManifestTO inManifestTO)
			throws CGSystemException {
		LOGGER.debug("InManifestCommonDAOImpl :: getConsgNumbersByManifestId() :: Start --------> ::::::");
		List<String> consgNos = null;
		try {
			consgNos = getHibernateTemplate().findByNamedQueryAndNamedParam(
					InManifestConstants.QRY_GET_CONSG_NUMBERS_BY_MANIFEST_ID,
					"manifestId", inManifestTO.getManifestId());
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::getConsgNumbersByManifestId() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("InManifestCommonDAOImpl :: getConsgNumbersByManifestId() :: End --------> ::::::");
		return consgNos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.dao.InManifestCommonDAO#
	 * getManifestIdByManifestNoOperatingOffice
	 * (com.ff.loadmanagement.ManifestTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer getManifestIdByManifestNoOperatingOffice(
			ManifestTO manifestTO) throws CGSystemException {
		LOGGER.debug("InManifestCommonDAOImpl :: getManifestIdByManifestNoOperatingOffice() :: Start --------> ::::::");
		Integer manifestId = null;
		try {
			List<Integer> manifestIds = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							InManifestConstants.QRY_GET_MANIFEST_ID_BY_MANIFEST_NO_OPERATING_OFFICE,
							new String[] { "manifestNumber", "operatingOffice",
									"manifestType" },
							new Object[] { manifestTO.getManifestNumber(),
									manifestTO.getLoginOfficeId(),
									manifestTO.getManifestType() });
			manifestId = !StringUtil.isEmptyList(manifestIds) ? manifestIds
					.get(0) : null;

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::getManifestIdByManifestNoOperatingOffice() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("InManifestCommonDAOImpl :: getManifestIdByManifestNoOperatingOffice() :: End --------> ::::::");
		return manifestId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.inmanifest.dao.InManifestCommonDAO#getInComailDetails
	 * (com.ff.manifest.ManifestBaseTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ManifestDO getInComailDetails(ManifestBaseTO manifestBaseTO)
			throws CGSystemException {

		LOGGER.trace("InManifestCommonDAOImpl::getInComailDetails::START------------>:::::::");
		ManifestDO manifestDO = null;
		List<ManifestDO> manifestDOs = null;
		Object values[] = { manifestBaseTO.getManifestNumber(),
				manifestBaseTO.getProcessCode().split(","),
				manifestBaseTO.getUpdateProcessCode().split(",") };
		try {
			manifestDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					ManifestConstants.QRY_COMAIL_DETAILS,
					ManifestConstants.params, values);
			manifestDO = !StringUtil.isEmptyList(manifestDOs) ? manifestDOs
					.get(0) : null;

		} catch (Exception e) {
			LOGGER.error("Error occured in InManifestCommonDAOImpl :: getInComailDetails()..:"
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("InManifestCommonDAOImpl::getInComailDetails::END------------>:::::::");
		return manifestDO;
	}

	/**
	 * Checks if is consg no in manifested.
	 * 
	 * @param inBagManifestParcelTO
	 *            the in bag manifest parcel to
	 * @return true, if is consg no in manifested
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	@Override
	public boolean isConsgNoInManifested(InBagManifestTO inBagManifestParcelTO)
			throws CGSystemException {
		LOGGER.debug("InManifestCommonDAOImpl :: isConsgNoInManifested() :: Start --------> ::::::");

		boolean isCNManifested = Boolean.FALSE;
		try {
			long count = (long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							InManifestConstants.QRY_IS_CONSG_NUM_MANIFESTED,
							new String[] { "consignmentNo", "operatingOffice",
									"processCode", "updateProcessCode",
									"manifestType" },
							new Object[] {
									inBagManifestParcelTO.getConsgNumber(),
									inBagManifestParcelTO
											.getLoggedInOfficeId(),
									inBagManifestParcelTO.getProcessCode(),
									inBagManifestParcelTO
											.getUpdateProcessCode(),
									inBagManifestParcelTO.getManifestType() })
					.get(0);

			if (count > 0)
				isCNManifested = Boolean.TRUE;

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::isConsgNoInManifested() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("InManifestCommonDAOImpl :: isConsgNoInManifested() :: End --------> ::::::");
		return isCNManifested;

	}

	@Override
	public boolean isManifestNumInManifested(InManifestTO inManifestTO)
			throws CGSystemException {
		LOGGER.debug("InManifestCommonDAOImpl :: isManifestNumInManifested() :: Start --------> ::::::");

		boolean isManifested = Boolean.FALSE;
		try {
			/*
			 * long count = (long) getHibernateTemplate()
			 * .findByNamedQueryAndNamedParam(
			 * InManifestConstants.QRY_IS_MANIFEST_NUM_IN_MANIFESTED, new
			 * String[] { "manifestNumber", "destOfficeId", }, new Object[] {
			 * inManifestTO.getManifestNumber(),
			 * inManifestTO.getDestinationOfficeTO() .getOfficeId(), }).get(0);
			 */
			long count = (long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							InManifestConstants.QRY_IS_MANIFEST_NUM_IN_MANIFESTED,
							new String[] { "manifestNumber", "operatingOffice",
									"processCode", "updateProcessCode" },
							new Object[] {
									inManifestTO.getManifestNumber(),
									inManifestTO.getLoggedInOfficeId(),
									inManifestTO.getProcessCode().split(
											CommonConstants.COMMA),
									inManifestTO.getUpdateProcessCode().split(
											CommonConstants.COMMA) }).get(0);
			if (count > 0) {
				isManifested = Boolean.TRUE;
			}

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::isManifestNumInManifested() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("InManifestCommonDAOImpl :: isManifestNumInManifested() :: End --------> ::::::");
		return isManifested;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.dao.InManifestCommonDAO#
	 * saveOrUpdateManifestMappedEmbeddedDOs(java.util.List)
	 */
	@Override
	public void saveOrUpdateManifestMappedEmbeddedDOs(
			List<ManifestMappedEmbeddedDO> manifestMappedEmbeddedDOs)
			throws CGSystemException {
		LOGGER.debug("InManifestCommonDAOImpl :: saveOrUpdateManifestMappedEmbeddedDOs() :: Start --------> ::::::");
		try {
			for (ManifestMappedEmbeddedDO manifestMappedEmbeddedDO : manifestMappedEmbeddedDOs) {
				getHibernateTemplate().saveOrUpdate(manifestMappedEmbeddedDO);
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::saveOrUpdateManifestMappedEmbeddedDOs() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("InManifestCommonDAOImpl :: saveOrUpdateManifestMappedEmbeddedDOs() :: End --------> ::::::");
	}

	@Override
	public void updateManifestRemarks(ManifestDO manifestDO)
			throws CGSystemException {
		LOGGER.debug("InManifestCommonDAOImpl :: updateManifestRemarks() :: Start --------> ::::::");
		Session session = null;
		try {
			/*
			 * session = getHibernateTemplate().getSessionFactory()
			 * .openSession();
			 */
			session = openTransactionalSession();
			Query query = session
					.getNamedQuery(InManifestConstants.QRY_UPDATE_MANIFEST_REMARKS_BY_ID);
			query.setInteger(InManifestConstants.PARAM_MANIFEST_ID,
					manifestDO.getManifestId());
			query.setString(InManifestConstants.PARAM_REMARKS,
					manifestDO.getRemarks());
			query.executeUpdate();
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::updateManifestRemarks() :: "
					, e);
			throw new CGSystemException(e);
		} finally {
			// closeSession(session);
			closeTransactionalSession(session);
		}
		LOGGER.debug("InManifestCommonDAOImpl :: updateManifestRemarks() :: End --------> ::::::");
	}

	@SuppressWarnings("unchecked")
	@Override
	public ManifestDO getManifestByNoProcessType(ManifestBaseTO manifestBaseTO)
			throws CGSystemException {

		LOGGER.debug("InManifestCommonDAOImpl :: getManifestByNoProcessType() :: Start --------> ::::::");
		ManifestDO manifestDO = null;
		Session session = null;
		try {
			session = createSession();
			// session.enableFetchProfile("manifest-embedded-in");
			Query qry = session
					.getNamedQuery(ManifestConstants.QRY_GET_MANIFEST_BY_NO_PROCESS_TYPE);
			qry.setString("manifestNumber", manifestBaseTO.getManifestNumber());
			qry.setInteger("destOfficeId",
					manifestBaseTO.getDestinationOfficeId());
			qry.setString("processCode", manifestBaseTO.getProcessCode());
			qry.setString("updateProcessCode",
					manifestBaseTO.getUpdateProcessCode());
			qry.setString("manifestType", manifestBaseTO.getManifestType());

			qry.setMaxResults(1);
			List<ManifestDO> manifestDOs = qry.list();

			/*
			 * List<ManifestDO> manifestDOs = getHibernateTemplate()
			 * .findByNamedQueryAndNamedParam(
			 * ManifestConstants.QRY_GET_MANIFEST_BY_NO_PROCESS_TYPE, new
			 * String[] { "manifestNumber", "destOfficeId", "processCode",
			 * "updateProcessCode", "manifestType" }, new Object[] {
			 * manifestBaseTO.getManifestNumber(),
			 * manifestBaseTO.getDestinationOfficeTO() .getOfficeId(),
			 * manifestBaseTO.getProcessCode(),
			 * manifestBaseTO.getUpdateProcessCode(),
			 * manifestBaseTO.getManifestType() });
			 */

			manifestDO = !StringUtil.isEmptyList(manifestDOs) ? manifestDOs
					.get(0) : null;
			initializeSetByHib(manifestBaseTO, manifestDO);

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::getManifestByNoProcessType() :: "
					, e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("InManifestCommonDAOImpl :: getManifestByNoProcessType() :: End --------> ::::::");
		return manifestDO;

	}

	@Override
	public boolean isCoMailNumInManifested(InManifestTO inManifestTO)
			throws CGSystemException {
		LOGGER.debug("InManifestCommonDAOImpl :: isCoMailNumInManifested() :: Start --------> ::::::");

		boolean isCNManifested = Boolean.FALSE;
		try {
			long count = (long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							InManifestConstants.QRY_IS_COMAIL_NUM_MANIFESTED,
							new String[] { "coMailNo", "operatingOffice",
									"processCode", "updateProcessCode",
									"manifestType" },
							new Object[] { inManifestTO.getCoMailNo(),
									inManifestTO.getLoggedInOfficeId(),
									inManifestTO.getProcessCode(),
									inManifestTO.getUpdateProcessCode(),
									inManifestTO.getManifestType() }).get(0);

			if (count > 0) {
				isCNManifested = Boolean.TRUE;
			}

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::isCoMailNumInManifested() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("InManifestCommonDAOImpl :: isCoMailNumInManifested() :: End --------> ::::::");
		return isCNManifested;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getCoMailIdByNo(String coMailNo) throws CGSystemException {
		LOGGER.debug("InManifestCommonDAOImpl :: getCoMailIdByNo() :: Start --------> ::::::");
		Integer coMailId = null;
		try {
			List<Integer> coMailIds = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							InManifestConstants.QRY_GET_COMAIL_ID_BY_NO,
							"coMailNo", coMailNo);
			coMailId = !StringUtil.isEmptyList(coMailIds) ? coMailIds.get(0)
					: null;

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::getCoMailIdByNo() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("InManifestCommonDAOImpl :: getCoMailIdByNo() :: End --------> ::::::");
		return coMailId;
	}

	@Override
	public boolean isManifestHeaderInManifested(ManifestBaseTO inManifestTO)
			throws CGSystemException {
		LOGGER.debug("InManifestCommonDAOImpl :: isManifestHeaderInManifested() :: Start --------> ::::::");

		boolean isManifested = Boolean.FALSE;
		try {
			long count = (long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							InManifestConstants.QRY_IS_MANIFEST_HEADER_IN_MANIFESTED,
							new String[] { "manifestNumber", "operatingOffice",
									"manifestType" },
							new Object[] { inManifestTO.getManifestNumber(),
									inManifestTO.getLoggedInOfficeId(),
									inManifestTO.getManifestType() }).get(0);

			if (count > 0) {
				isManifested = Boolean.TRUE;
			}

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::isManifestHeaderInManifested() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("InManifestCommonDAOImpl :: isManifestHeaderInManifested() :: End --------> ::::::");
		return isManifested;

	}

	@SuppressWarnings("unchecked")
	@Override
	public ManifestDO getManifestDetailsWithFetchProfile(
			ManifestBaseTO manifestBaseTO) throws CGSystemException {
		LOGGER.trace("InManifestCommonDAOImpl::getManifestDetailsWithFetchProfile::START------------>:::::::");
		Session session = null;
		ManifestDO manifestDO = null;
		Criteria criteria = null;
		try {
			session = createSession();
			enableFetchProfileInSession(manifestBaseTO, session);

			criteria = session.createCriteria(ManifestDO.class);
			//criteria.createAlias("destOffice", "destOfficeDO");
			//criteria.createAlias("updatingProcess", "updatingProcessDO");

			if (!StringUtil.isEmptyInteger(manifestBaseTO.getManifestId())) {
				criteria.add(Restrictions.eq("manifestId",
						manifestBaseTO.getManifestId()));
			}
			if (StringUtils.isNotBlank(manifestBaseTO.getManifestNumber())) {
				criteria.add(Restrictions.eq("manifestNo",
						manifestBaseTO.getManifestNumber()));
			}
			if (!StringUtil.isEmptyInteger(manifestBaseTO
					.getLoggedInOfficeId())) {
				criteria.add(Restrictions.eq("operatingOffice",
						manifestBaseTO.getLoggedInOfficeId()));
			}
			/*if (!StringUtil.isEmptyInteger(manifestBaseTO
					.getDestinationOfficeId())) {
				criteria.add(Restrictions.eq("destOffice.officeId",
						manifestBaseTO.getDestinationOfficeId()));
			}*/
			if (StringUtils.isNotBlank(manifestBaseTO.getProcessCode())) {
				criteria.add(Restrictions.in(
						"manifestProcessCode",
						manifestBaseTO.getProcessCode().split(
								CommonConstants.COMMA)));
			}
			/*if (StringUtils.isNotBlank(manifestBaseTO.getUpdateProcessCode())) {
				criteria.add(Restrictions.in(
						"updatingProcessDO.processCode",
						manifestBaseTO.getUpdateProcessCode().split(
								CommonConstants.COMMA)));
			}*/
			if (StringUtils.isNotBlank(manifestBaseTO.getManifestType())) {
				if (manifestBaseTO.getIsExcludeManifestType()) {
					criteria.add(Restrictions.ne("manifestType",
							manifestBaseTO.getManifestType()));
				} else {
					criteria.add(Restrictions.eq("manifestType",
							manifestBaseTO.getManifestType()));
				}
			}
			if (manifestBaseTO.getIsNoOfElementNotNull()) {
				criteria.add(Restrictions.isNotNull("noOfElements"));
			} 
			criteria.addOrder(Order.desc("manifestDate"));
			criteria.setMaxResults(1);
			List<ManifestDO> manifestDOs = criteria.list();

			manifestDO = !StringUtil.isEmptyList(manifestDOs) ? manifestDOs
					.get(0) : null;
			initializeSetByHib(manifestBaseTO, manifestDO);

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::getManifestDetailsWithFetchProfile() :: "
					, e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}

		LOGGER.debug("InManifestCommonDAOImpl :: getManifestDetailsWithFetchProfile() :: End --------> ::::::");
		return manifestDO;
	}

	private void initializeSetByHib(ManifestBaseTO manifestBaseTO,
			ManifestDO manifestDO) {
		if (manifestDO != null) {
			if (manifestBaseTO.getIsFetchProfileManifestEmbedded()) {
				Hibernate.initialize(manifestDO.getEmbeddedManifestDOs());
			}
			if (manifestBaseTO.getIsFetchProfileManifestParcel()) {
				Hibernate.initialize(manifestDO.getConsignments());
			}
			if (manifestBaseTO.getIsFetchProfileManifestDox()) {
				Hibernate.initialize(manifestDO.getConsignments());
				Hibernate.initialize(manifestDO.getComails());
			}
		}
	}

	private void enableFetchProfileInSession(ManifestBaseTO manifestBaseTO,
			Session session) {
		if (manifestBaseTO.getIsFetchProfileManifestEmbedded()) {
			session.enableFetchProfile(InManifestConstants.Fetch_Profile_Manifest_Embedded);
		}
		if (manifestBaseTO.getIsFetchProfileManifestParcel()) {
			session.enableFetchProfile(InManifestConstants.Fetch_Profile_Manifest_Parcel);
		}
		if (manifestBaseTO.getIsFetchProfileManifestDox()) {
			session.enableFetchProfile(InManifestConstants.Fetch_Profile_Manifest_Dox);
		}
	}

	@SuppressWarnings("unchecked")
	public Integer getManifestIdByManifestNoAndTypeAndOrigin(
			ManifestBaseTO baseTO) throws CGSystemException {
		LOGGER.debug("InManifestCommonDAOImpl :: getManifestIdByManifestNoAndTypeAndOrigin() :: Start --------> ::::::");
		Integer manifestId = null;
		try {

			List<Integer> manifestIds = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							InManifestConstants.QRY_GET_MANIFEST_ID_BY_MANIFEST_NO_ORIGIN_TYPE,
							new String[] { "manifestNumber", "OriginOfficeId",
									"manifestType" },
							new Object[] { baseTO.getManifestNumber(),
									baseTO.getOriginOfficeId(),
									baseTO.getManifestType() });
			manifestId = !StringUtil.isEmptyList(manifestIds) ? manifestIds
					.get(0) : null;

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::getManifestIdByManifestNoAndTypeAndOrigin() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("InManifestCommonDAOImpl :: getManifestIdByManifestNoAndTypeAndOrigin() :: End --------> ::::::");
		return manifestId;
	}

	@Override
	public boolean isBplDoxParcel(ManifestBaseTO manifestBaseTO)
			throws CGSystemException {
		LOGGER.debug("InManifestCommonDAOImpl :: isBplDoxParcel() :: Start --------> ::::::");

		boolean isBplDoxParcel = Boolean.FALSE;
		try {
			long count = (long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							InManifestConstants.QRY_IS_BPL_DOX_PARCEL,
							new String[] { "manifestNumber", "processCode",
									"consignmentCode" },
							new Object[] {
									manifestBaseTO.getManifestNumber(),
									manifestBaseTO.getProcessCode().split(
											CommonConstants.COMMA),
									manifestBaseTO.getConsignmentTypeTO()
											.getConsignmentCode() }).get(0);
			if (count > 0) {
				isBplDoxParcel = Boolean.TRUE;
			}

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::isBplDoxParcel() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("InManifestCommonDAOImpl :: isBplDoxParcel() :: End --------> ::::::");
		return isBplDoxParcel;

	}

	/* (non-Javadoc)
	 * @see com.ff.web.manifest.inmanifest.dao.InManifestCommonDAO#getManifestIdByManifest(com.ff.manifest.ManifestBaseTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer getManifestIdByManifest(ManifestBaseTO manifestBaseTO)
			throws CGSystemException {
		LOGGER.trace("InManifestCommonDAOImpl::getManifestIdByManifest::START------------>:::::::");
		Session session = null;
		Criteria criteria = null;
		Integer manifestId = null;
		try {
			session = createSession();
			criteria = session.createCriteria(ManifestDO.class, "manifestDO");

			if (StringUtils.isNotBlank(manifestBaseTO.getManifestNumber())) {
				criteria.add(Restrictions.eq("manifestNo",
						manifestBaseTO.getManifestNumber()));
			}
			if (StringUtils.isNotBlank(manifestBaseTO.getManifestType())) {
				if (manifestBaseTO.getIsExcludeManifestType()) {
					criteria.add(Restrictions.ne("manifestType",
							manifestBaseTO.getManifestType()));
				} else {
					criteria.add(Restrictions.eq("manifestType",
							manifestBaseTO.getManifestType()));
				}
			}
			if (manifestBaseTO.getIsNoOfElementNotNull()) {
				criteria.add(Restrictions.isNotNull("noOfElements"));
			}
			criteria.addOrder(Order.desc("manifestDate"));
			criteria.setProjection(Projections
					.property("manifestDO.manifestId"));
			criteria.setMaxResults(1);
			List<Integer> manifestIds = criteria.list();

			manifestId = !StringUtil.isEmptyList(manifestIds) ? manifestIds
					.get(0) : null;

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::getManifestIdByManifest() :: "
					, e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}

		LOGGER.debug("InManifestCommonDAOImpl :: getManifestIdByManifest() :: End --------> ::::::");
		return manifestId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getManifestIdFromCnManifest(InManifestTO inManifestTO)
			throws CGSystemException {		
		LOGGER.trace("InManifestCommonDAOImpl::getManifestIdFromCnManifest::START------------>:::::::");
		Session session = null;
		Criteria criteria = null;
		Integer manifestId = null;
		try {
			session = createSession();
			criteria = session.createCriteria(ConsignmentManifestDO.class, "consignmentManifestDO");
			criteria.createAlias("manifest", "manifestDO");
			criteria.createAlias("consignment", "consignmentDO");
			
			if (StringUtils.isNotBlank(inManifestTO.getManifestNumber())) {
				criteria.add(Restrictions.eq("manifestDO.manifestNo",
						inManifestTO.getManifestNumber()));
			}
			if (StringUtils.isNotBlank(inManifestTO.getManifestType())) {
				if (inManifestTO.getIsExcludeManifestType()) {
					criteria.add(Restrictions.ne("manifestDO.manifestType",
							inManifestTO.getManifestType()));
				} else {
					criteria.add(Restrictions.eq("manifestDO.manifestType",
							inManifestTO.getManifestType()));
				}
			}
			if (!StringUtil.isEmptyInteger(inManifestTO
					.getLoggedInOfficeId())) {
				criteria.add(Restrictions.eq("manifestDO.operatingOffice",
						inManifestTO.getLoggedInOfficeId()));
			}
			if (inManifestTO.getIsNoOfElementNotNull()) {
				criteria.add(Restrictions.isNotNull("manifestDO.noOfElements"));
			}
			if (StringUtils.isNotBlank(inManifestTO.getConsgNumber())) {
				criteria.add(Restrictions.eq("consignmentDO.consgNo",
						inManifestTO.getConsgNumber()));
			}
			criteria.addOrder(Order.desc("manifestDO.manifestDate"));
			criteria.setProjection(Projections
					.property("manifestDO.manifestId"));
			criteria.setMaxResults(1);
			List<Integer> manifestIds = criteria.list();

			manifestId = !StringUtil.isEmptyList(manifestIds) ? manifestIds
					.get(0) : null;

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::getManifestIdFromCnManifest() :: "
					, e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}

		LOGGER.debug("InManifestCommonDAOImpl :: getManifestIdFromCnManifest() :: End --------> ::::::");
		return manifestId;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object[] getManifestDtlsByConsgNoOperatingOffice(
			InManifestTO inManifestTO) throws CGSystemException {
		LOGGER.debug("InManifestCommonDAOImpl :: getManifestDtlsByConsgNoOperatingOffice() :: Start --------> ::::::");
		Object manifest[] = null;
		Session session = null;
		try {
			session = createSession();
			Query qry = session
					.getNamedQuery(InManifestConstants.QRY_GET_MANIFEST_DTLS_BY_CONSG_NO_OPERATING_OFFICE);
			qry.setString("consignmentNo", inManifestTO.getConsgNumber());
			qry.setInteger("operatingOffice", inManifestTO.getLoggedInOfficeId());

			qry.setMaxResults(1);
			List<Object[]> manifestDtls = qry.list();


			manifest = !StringUtil.isEmptyList(manifestDtls) ? manifestDtls
					.get(0) : null;
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::getManifestDtlsByConsgNoOperatingOffice() :: "
					, e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("InManifestCommonDAOImpl :: getManifestDtlsByConsgNoOperatingOffice() :: End --------> ::::::");
		return manifest;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getBookingOfficeIdByConsgNo(String consgNo)
			throws CGSystemException {
		LOGGER.debug("InManifestCommonDAOImpl :: getBookingOfficeIdByConsgNo() :: Start --------> ::::::");
		Integer bookingOfficeId = null;
		Session session = null;
		try {
			session = createSession();
			Query qry = session
					.getNamedQuery(InManifestConstants.QRY_GET_BOOKING_OFFICE_ID);
			qry.setString("consgNumber", consgNo);

			qry.setMaxResults(1);
			List<Integer> bookingOfficeIds = qry.list();
			
			bookingOfficeId = !StringUtil.isEmptyList(bookingOfficeIds) ? bookingOfficeIds
					.get(0) : null;
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::getBookingOfficeIdByConsgNo() :: "
					, e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("InManifestCommonDAOImpl :: getBookingOfficeIdByConsgNo() :: End --------> ::::::");
		return bookingOfficeId;
	}

	@Override
	public boolean isManifestOutManifested(ManifestDO manifestDO)
			throws CGSystemException {
		LOGGER.debug("InManifestCommonDAOImpl :: isManifestOutManifested() :: Start --------> ::::::");

		boolean isOutManifested = Boolean.FALSE;
		try {
			long count = (long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							InManifestConstants.QRY_IS_MANIFEST_OUT_MANIFESTED,
							new String[] { "manifestNumber", "operatingOffice" },
							new Object[] { manifestDO.getManifestNo(),
									manifestDO.getOperatingOffice() }).get(0);

			if (count > 0) {
				isOutManifested = Boolean.TRUE;
			}

		} catch (Exception e) {
			LOGGER.error(
					"Exception Occured in::InManifestCommonDAOImpl::isManifestOutManifested() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("InManifestCommonDAOImpl :: isManifestOutManifested() :: End --------> ::::::");
		return isOutManifested;

	}
	@Override
	public List<?> getMnfstOpenTypeAndBplMnfstType(ManifestDO inManifestDO)
			throws CGSystemException {
		LOGGER.debug("InManifestCommonDAOImpl :: getMnfstOpenTypeAndBplMnfstType() :: Start --------> ::::::");

		List<?> manifestTypes=null;
		Session session=null;
		try {
			
			session = createSession();
			Query qry = session
					.getNamedQuery(InManifestConstants.QRY_GET_MNFST_OPEN_TYPE_AND_BPL_MNFST_TYPE);
			qry.setString("manifestNumber", inManifestDO.getManifestNo());
			qry.setString("manifestType", CommonConstants.MANIFEST_TYPE_OUT);
			
			qry.setMaxResults(1);
			manifestTypes=qry.list();

		} catch (Exception e) {
			LOGGER.error(
					"Exception Occured in::getMnfstOpenTypeAndBplMnfstType::isManifestOutManifested() :: ",
					e);
			throw new CGSystemException(e);
		}  finally {
			closeSession(session);
		}
		LOGGER.debug("InManifestCommonDAOImpl :: getMnfstOpenTypeAndBplMnfstType() :: End --------> ::::::");
		return manifestTypes;

	}
	
	
}
