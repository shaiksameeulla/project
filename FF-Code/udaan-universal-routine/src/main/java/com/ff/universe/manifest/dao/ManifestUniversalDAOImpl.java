package com.ff.universe.manifest.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.manifest.ConsignmentManifestDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.manifest.ManifestBaseTO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.misroute.MisrouteDetailsTO;
import com.ff.universe.manifest.constant.ManifestUniversalConstants;

/**
 * The Class ManifestUniversalDAOImpl.
 */
public class ManifestUniversalDAOImpl extends CGBaseDAO implements
		ManifestUniversalDAO {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ManifestUniversalDAOImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.manifest.dao.ManifestUniversalDAO#saveOrUpdateManifest
	 * (java.util.List)
	 */
	public List<ManifestDO> saveOrUpdateManifest(List<ManifestDO> manifestDtls)
			throws CGSystemException {

		Session session = openTransactionalSession();
		try {
			// tx = session.beginTransaction();
			for (ManifestDO manifest : manifestDtls) {
				session.saveOrUpdate(manifest);
			}
			// tx.commit();
		} catch (Exception e) {
			// tx.rollback();
			LOGGER.error("Error occured in ManifestUniversalDAOImpl :: saveOrUpdateManifest()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeTransactionalSession(session);
		}
		return manifestDtls;
	}

	public void deleteFromComailManifest(Integer[] comailmanifstIdArray) {
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory()
					.openSession();
			Query qry = null;
			qry = session.getNamedQuery("deleteFromComailManifest");

			qry.setParameterList("comailmanifstIds", comailmanifstIdArray);

			qry.executeUpdate();
		} finally {
			closeSession(session);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.manifest.dao.ManifestUniversalDAO#saveOrUpdateTPManifest
	 * (com.ff.domain.manifest.ManifestDO)
	 */
	public ManifestDO saveOrUpdateTPManifest(ManifestDO manifestDtls)
			throws CGSystemException {
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(manifestDtls);

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			LOGGER.error("Error occured in ManifestUniversalDAOImpl :: saveOrUpdateManifestProcess()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			session.flush();
			session.close();
		}
		return manifestDtls;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.manifest.dao.ManifestUniversalDAO#saveOrUpdateManifestProcess
	 * (java.util.List)
	 */
	/*public List<ManifestProcessDO> saveOrUpdateManifestProcess(
			List<ManifestProcessDO> manifestProcessDOs)
			throws CGSystemException {
		Session session = openTransactionalSession();
		// Transaction tx = null;
		try {
			// tx = session.beginTransaction();
			for (ManifestProcessDO manifest : manifestProcessDOs) {
				session.saveOrUpdate(manifest);
			}
			// tx.commit();
		} catch (Exception e) {
			// tx.rollback();
			LOGGER.error("Error occured in ManifestUniversalDAOImpl :: saveOrUpdateManifestProcess()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeTransactionalSession(session);
		}
		return manifestProcessDOs;

	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.manifest.dao.ManifestUniversalDAO#getManifestDtls(com
	 * .ff.manifest.ManifestInputs)
	 */
	public ManifestDO getManifestDtls(ManifestInputs manifestTO)
			throws CGSystemException {
		ManifestDO manifestDO = null;
		Session session = null;
		Criteria criteria = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			criteria = session.createCriteria(ManifestDO.class);
			criteria.add(Restrictions.eq("manifestNo",
					manifestTO.getManifestNumber()));
			criteria.add(Restrictions.eq("manifestStatus",
					CommonConstants.MANIFEST_STATUS_CODE));

			// criteria.add(Restrictions.eq("manifestDirection",
			// CommonConstants.MANIFEST_TYPE_OUT));

			criteria.createAlias("originOffice", "origin");
			criteria.add(Restrictions.eq("origin.officeId",
					manifestTO.getLoginOfficeId()));

			if (StringUtils.isNotEmpty(manifestTO.getDocType())) {
				criteria.createAlias("manifestLoadContent", "loadContent");
				criteria.add(Restrictions.eq("loadContent.consignmentCode",
						manifestTO.getDocType()));
			}
			manifestDO = (ManifestDO) criteria.uniqueResult();
		} catch (Exception e) {
			LOGGER.error("Error occured in ManifestUniversalDAOImpl :: getManifestDtls()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}

		return manifestDO;
	}

	@SuppressWarnings("unchecked")
	public ManifestDO getBoutGridManifestDtls(ManifestInputs manifestTO)
			throws CGSystemException {
		ManifestDO manifestDO = null;
		Session session = null;
		Criteria criteria = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			criteria = session.createCriteria(ManifestDO.class);
			criteria.add(Restrictions.eq("manifestNo",
					manifestTO.getManifestNumber()));
			criteria.add(Restrictions.eq("manifestStatus",
					CommonConstants.MANIFEST_STATUS_CODE));
			criteria.add(Restrictions.eq("operatingOffice",
					manifestTO.getLoginOfficeId()));
			// criteria.add(Restrictions.eq("manifestDirection",
			// CommonConstants.MANIFEST_TYPE_OUT));

			List<ManifestDO> manifestDOs = criteria.list();

			manifestDO = !StringUtil.isEmptyList(manifestDOs) ? manifestDOs
					.get(0) : null;
			;
		} catch (Exception e) {
			LOGGER.error("Error occured in ManifestUniversalDAOImpl :: getManifestDtls()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}

		return manifestDO;
	}

	@SuppressWarnings("unchecked")
	public List<ManifestDO> getMisrouteManifestDtls(ManifestInputs manifestTO)
			throws CGSystemException {
		List<ManifestDO> manifestDO = null;
		Session session = null;
		Criteria criteria = null;
		try {

			session = getHibernateTemplate().getSessionFactory().openSession();
			criteria = session.createCriteria(ManifestDO.class);
			criteria.add(Restrictions.eq("manifestNo",
					manifestTO.getManifestNumber()));
			
			criteria.add(Restrictions.eq("operatingOffice",
					manifestTO.getLoginOfficeId()));
			
			/*criteria.add(Restrictions.eq("manifestType",
					CommonConstants.MANIFEST_TYPE_IN));

			*/
			criteria.add(Restrictions.eq("manifestStatus",
					CommonConstants.MANIFEST_STATUS_CODE));
			
			criteria.addOrder(Order.desc("manifestDirection"));

			manifestDO = (List<ManifestDO>) criteria.list();
		} catch (Exception e) {
			LOGGER.error("Error occured in ManifestUniversalDAOImpl :: getManifestDtls()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}

		return manifestDO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.manifest.dao.ManifestUniversalDAO#searchManifestDtls(
	 * com.ff.manifest.ManifestInputs)
	 */
	public ManifestDO searchManifestDtls(ManifestInputs manifestTO)
			throws CGSystemException {
		ManifestDO manifestDO = null;
		Session session = null;
		Criteria criteria = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			criteria = session.createCriteria(ManifestDO.class);
			criteria.createAlias("originOffice", "officeDO");
			criteria.add(Restrictions.eq("officeDO.officeId",
					manifestTO.getLoginOfficeId()));
			criteria.add(Restrictions.eq("manifestNo",
					manifestTO.getManifestNumber()));
			criteria.add(Restrictions.eq("manifestProcessCode",
					manifestTO.getManifestProcessCode()));
			if (StringUtils.isNotEmpty(manifestTO.getManifestType())) {
				criteria.add(Restrictions.eq("manifestType",
						manifestTO.getManifestType()));
			}
			if (StringUtils.isNotEmpty(manifestTO.getDocType())) {
				criteria.createAlias("manifestLoadContent", "loadContent");
				criteria.add(Restrictions.eq("loadContent.consignmentCode",
						manifestTO.getDocType()));
			}
			manifestDO = (ManifestDO) criteria.uniqueResult();

		} catch (Exception e) {
			LOGGER.error("ManifestUniversalDAOImpl :: searchManifestDtls()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return manifestDO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.manifest.dao.ManifestUniversalDAO#getManifestProcessDtls
	 * (com.ff.manifest.ManifestInputs)
	 */
	/*@SuppressWarnings("unchecked")
	@Override
	public List<ManifestProcessDO> getManifestProcessDtls(
			ManifestInputs manifestTO) throws CGSystemException {
		List<ManifestProcessDO> manifestProcessDOs = null;
		try {
			String[] paramNames = { ManifestUniversalConstants.MANIFEST_NO,
					ManifestUniversalConstants.ORIGIN_OFFICE_ID,
					ManifestUniversalConstants.MANIFEST_TYPE,
					// ManifestUniversalConstants.MANIFEST_PROCESS_CODE,
					ManifestUniversalConstants.MANIFEST_DIRECTION };
			Object[] values = { manifestTO.getManifestNumber(),
					manifestTO.getLoginOfficeId(),
					manifestTO.getManifestType(),
					// manifestTO.getManifestProcessCode(),
					manifestTO.getManifestDirection() };
			manifestProcessDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							ManifestUniversalConstants.QRY_GET_MANIFEST_PROCESS_DTLS,
							paramNames, values);
		} catch (Exception e) {
			LOGGER.error("ManifestUniversalDAOImpl :: getManifestProcessDtls()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}

		return manifestProcessDOs;
	}*/

	/*@SuppressWarnings("unchecked")
	@Override
	public ManifestProcessDO getManifestProcess(ManifestInputs manifestTO)
			throws CGSystemException {
		List<ManifestProcessDO> manifestProcessDOs = null;
		ManifestProcessDO manifestProcessDO = null;
		try {
			String[] paramNames = { ManifestUniversalConstants.MANIFEST_NO,
					ManifestUniversalConstants.ORIGIN_OFFICE_ID,
					ManifestUniversalConstants.MANIFEST_TYPE,
					// ManifestUniversalConstants.MANIFEST_PROCESS_CODE,
					ManifestUniversalConstants.MANIFEST_DIRECTION };
			Object[] values = { manifestTO.getManifestNumber(),
					manifestTO.getLoginOfficeId(),
					manifestTO.getManifestType(),
					// manifestTO.getManifestProcessCode(),
					manifestTO.getManifestDirection() };
			manifestProcessDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							ManifestUniversalConstants.QRY_GET_MANIFEST_PROCESS_DTLS,
							paramNames, values);

			if (!StringUtil.isEmptyColletion(manifestProcessDOs)
					&& manifestProcessDOs.size() > 0) {
				manifestProcessDO = manifestProcessDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("ManifestUniversalDAOImpl :: getManifestProcessDtls()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}

		return manifestProcessDO;
	}
*/
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.manifest.dao.ManifestUniversalDAO#isConsignmentClosed
	 * (java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isConsignmentClosed(String consgNumber,
			String manifestDirection, String manifestStatus)
			throws CGSystemException {
		/** Kindly Please don't modify this method without permission of manifest team*/
		boolean isConsignmentClosed = Boolean.FALSE;
		long count = 0;
		try {
			String[] params = { ManifestUniversalConstants.CONSIGNMENT_NO,
					ManifestUniversalConstants.MANIFEST_DIRECTION,
					ManifestUniversalConstants.MANIFEST_STATUS };
			Object[] values = { consgNumber, manifestDirection, manifestStatus };
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							ManifestUniversalConstants.QRY_IS_CONSIGNMENT_CLOSED_FOR_MANIFEST,
							params, values).get(0);
			isConsignmentClosed = (count > 0) ? Boolean.TRUE : Boolean.FALSE;
		} catch (Exception e) {
			LOGGER.error("ManifestUniversalDAOImpl :: isConsignmentClosed()::::::"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		return isConsignmentClosed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.manifest.dao.ManifestUniversalDAO#
	 * saveOrUpdateManifest4LoadMgmt(java.util.List)
	 */
	@Override
	public List<ManifestDO> saveOrUpdateManifest4LoadMgmt(
			List<ManifestDO> manifestDOList) throws CGSystemException {
		try {
			for (ManifestDO manifestDO : manifestDOList) {
				getHibernateTemplate().saveOrUpdate(manifestDO);
			}
		} catch (Exception e) {
			LOGGER.error("Error occured in ManifestUniversalDAOImpl :: saveOrUpdateManifest4LoadMgmt()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		return manifestDOList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.manifest.dao.ManifestUniversalDAO#isConsignmentsManifested
	 * (java.util.List, java.lang.String)
	 */
	@Override
	public boolean isConsignmentsManifested(List<String> consgnmentList,
			String manifestType) throws CGSystemException {
		boolean isAnyCNManifested = Boolean.FALSE;
		long count = 0;
		try {
			String[] params = { ManifestUniversalConstants.CONSIGNMENTS_LIST,
					ManifestUniversalConstants.MANIFEST_TYPE };
			Object[] values = { consgnmentList, manifestType };
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam("isConsignmentsManifested",
							params, values).get(0);
			isAnyCNManifested = (count > 0) ? Boolean.TRUE : Boolean.FALSE;
		} catch (Exception e) {
			LOGGER.error("ERROR :: ManifestUniversalDAOImpl :: isConsignmentsManifested()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		return isAnyCNManifested;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.manifest.dao.ManifestUniversalDAO#validateComail(java
	 * .lang.String)
	 */
	@Override
	public boolean isComailNumberUsed(String comailNo, Integer manifestId) throws CGSystemException {
		boolean isCmailNoUsed = Boolean.FALSE;
		long count = 0;
		try {
			String[] params = { "comailNo",
					ManifestUniversalConstants.MANIFEST_ID};
			Object[] values = { comailNo, manifestId};
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam("isComailNumberUsed",
							params, values).get(0);
			isCmailNoUsed = (count > 0) ? Boolean.TRUE : Boolean.FALSE;
		} catch (Exception e) {
			LOGGER.error("ERROR :: ManifestUniversalDAOImpl :: isComailNumberUsed()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		return isCmailNoUsed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.dao.ManifestUniversalDAOImpl#isManifestExists(java
	 * .lang.String)
	 */
	public boolean isManifestExists(String manifestNo,
			String manifestDirection, String manifestType,
			String manifestPorcessCode) throws CGSystemException {
		long count = 0;
		boolean isManifested = Boolean.FALSE;
		try {
			String queryName = "isManifestExists";
			String[] params = { "manifestNo",
					ManifestUniversalConstants.MANIFEST_PROCESS_CODE,
					ManifestUniversalConstants.MANIFEST_TYPE,
					"manifestDirection" };
			Object[] values = { manifestNo, manifestPorcessCode, manifestType,
					manifestDirection };
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, params, values)
					.get(0);
			if (count > 0)
				isManifested = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error("ManifestUniversalDAOImpl :: isManifestExists()::::::"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		return isManifested;
	}

	@Override
	public ConsignmentManifestDO getConsgManifest(
			MisrouteDetailsTO misrouteDetailsTO) throws CGSystemException {
		ConsignmentManifestDO consignmentManifestDO = null;
		Session session = null;
		Criteria criteria = null;
		try {

			session = getHibernateTemplate().getSessionFactory().openSession();
			criteria = session.createCriteria(ConsignmentManifestDO.class,
					"cons");
			criteria.add(Restrictions.eq("cons.consignment.consgId",
					misrouteDetailsTO.getScannedItemId()));

			consignmentManifestDO = (ConsignmentManifestDO) criteria
					.uniqueResult();
		} catch (Exception e) {
			LOGGER.error("Error occured in ManifestUniversalDAOImpl :: getConsgManifest()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}

		return consignmentManifestDO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.manifest.dao.ManifestUniversalDAO#
	 * getConsignmentManifestedDate(java.lang.String, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getConsignmentManifestedDate(String manifestType,
			Integer consignmentId) throws CGSystemException {
		List<Object[]> consgManfstInfoList = null;
		try {
			String queryName = "getManifestDate";
			String[] params = { ManifestUniversalConstants.MANIFEST_TYPE,
					ManifestUniversalConstants.CONSIGNMENT_ID };
			Object[] values = { manifestType, consignmentId };
			consgManfstInfoList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, params, values);
		} catch (Exception e) {
			LOGGER.error("ManifestUniversalDAOImpl :: getConsignmentManifestedDate()::"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		return consgManfstInfoList;
	}

	@Override
	public Long getComailCountByManifestNo(String manifestNo)
			throws CGSystemException {
		Long count = 0L;
		String[] params = { ManifestUniversalConstants.MANIFEST_NO,
				ManifestUniversalConstants.MANIFEST_PROCESS_CODE };
		List<String> processCodeList = new ArrayList<>();
		processCodeList.add(CommonConstants.PROCESS_OUT_MANIFEST_BAG_DOX);
		processCodeList.add(CommonConstants.PROCESS_BRANCH_OUT_MANIFEST);
		Object[] values = { manifestNo, processCodeList };
		try {
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							ManifestUniversalConstants.QRY_GET_COMAIL_COUNT_BY_MANIFESTNO,
							params, values).get(0);

		} catch (Exception e) {
			LOGGER.error("ERROR :: ManifestUniversalDAOImpl :: isComailNumberUsed()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		return count;
	}

	@Override
	public Long getComailCountByManifestId(Integer manifestIds)
			throws CGSystemException {
		Long count = 0L;
		/*
		 * String[] params = { ManifestUniversalConstants.MANIFEST_NO,
		 * ManifestUniversalConstants.MANIFEST_PROCESS_CODE }; List<String>
		 * processCodeList= new ArrayList<>();
		 * processCodeList.add(CommonConstants.PROCESS_OUT_MANIFEST_BAG_DOX);
		 * processCodeList.add(CommonConstants.PROCESS_BRANCH_OUT_MANIFEST );
		 * Object[] values = { manifestNo, processCodeList };
		 */
		try {
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							ManifestUniversalConstants.QRY_GET_COMAIL_COUNT_BY_MANIFESTID,
							ManifestUniversalConstants.MANIFEST_IDS,
							manifestIds).get(0);

		} catch (Exception e) {
			LOGGER.error("ERROR :: ManifestUniversalDAOImpl :: getComailCountByManifestId()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		return count;
	}

	@Override
	public Long getConsgCountByManifestId(Integer manifestIds)
			throws CGSystemException {
		Long count = 0L;
		try {
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							ManifestUniversalConstants.QRY_GET_CONSG_COUNT_BY_MANIFESTID,
							ManifestUniversalConstants.MANIFEST_IDS,
							manifestIds).get(0);

		} catch (Exception e) {
			LOGGER.error("ERROR :: ManifestUniversalDAOImpl :: getConsgCountByManifestId()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		return count;
	}

	public boolean isExistInComailTable(String comailNO) {

		boolean isCmailNoExist = Boolean.FALSE;
		long count = 0;
		try {
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam("isComailNumberExist",
							"comailNo", comailNO).get(0);
			isCmailNoExist = (count > 0) ? Boolean.TRUE : Boolean.FALSE;
		} catch (Exception e) {
			LOGGER.error("ERROR :: ManifestUniversalDAOImpl :: isComailNumberExist()..:"
					+ e.getMessage());
		}
		return isCmailNoExist;

	}

	public Integer getComailIdByComailNo(String comailNO) {
		Integer comailId = null;
		try {
			comailId = (Integer) getHibernateTemplate()
					.findByNamedQueryAndNamedParam("getComailIdByComailNo",
							"coMailNo", comailNO).get(0);
		} catch (Exception e) {
			return null;
		}

		return comailId;

	}

	@Override
	public boolean isValiedBagLockNo(String bagLockNo) throws CGSystemException {
		boolean isBagLockNoExist = Boolean.FALSE;
		long count = 0;
		try {
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							ManifestUniversalConstants.QRY_IS_VALIED_BAG_LOCK_NO,
							ManifestUniversalConstants.BAG_LOCK_NO, bagLockNo)
					.get(0);
			isBagLockNoExist = (count > 0) ? Boolean.TRUE : Boolean.FALSE;
		} catch (Exception e) {
			LOGGER.error("ERROR :: ManifestUniversalDAOImpl :: isValiedBagLockNo()..:"
					+ e.getMessage());
		}
		return isBagLockNoExist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ManifestDO getManifestDetailsWithFetchProfile(
			ManifestBaseTO manifestBaseTO) throws CGSystemException {
		LOGGER.trace("ManifestUniversalDAOImpl::getManifestDetailsWithFetchProfile::START------------>:::::::");
		Session session = null;
		ManifestDO manifestDO = null;
		Criteria criteria = null;
		try {
			session = createSession();
			enableFetchProfileInSession(manifestBaseTO, session);

			criteria = session.createCriteria(ManifestDO.class);
			criteria.createAlias("destOffice", "destOfficeDO");
			criteria.createAlias("updatingProcess", "updatingProcessDO");

			if (!StringUtil.isEmptyInteger(manifestBaseTO.getManifestId())) {
				criteria.add(Restrictions.eq("manifestId",
						manifestBaseTO.getManifestId()));
			}
			if (StringUtils.isNotBlank(manifestBaseTO.getManifestNumber())) {
				criteria.add(Restrictions.eq("manifestNo",
						manifestBaseTO.getManifestNumber()));
			}
			if (!StringUtil.isEmptyInteger(manifestBaseTO
					.getDestinationOfficeId())) {
				criteria.add(Restrictions.eq("destOffice.officeId",
						manifestBaseTO.getDestinationOfficeId()));
			}
			if (StringUtils.isNotBlank(manifestBaseTO.getProcessCode())) {
				criteria.add(Restrictions.in(
						"manifestProcessCode",
						manifestBaseTO.getProcessCode().split(
								CommonConstants.COMMA)));
			}

			if (StringUtils.isNotBlank(manifestBaseTO.getUpdateProcessCode())) {
				criteria.add(Restrictions.in(
						"updatingProcessDO.processCode",
						manifestBaseTO.getUpdateProcessCode().split(
								CommonConstants.COMMA)));
			}
			if (StringUtils.isNotBlank(manifestBaseTO.getManifestType())) {
				criteria.add(Restrictions.eq("manifestType",
						manifestBaseTO.getManifestType()));
			}
			criteria.setMaxResults(1);
			List<ManifestDO> manifestDOs = criteria.list();

			manifestDO = !StringUtil.isEmptyList(manifestDOs) ? manifestDOs
					.get(0) : null;
			// initializeEmbeddedManifestDOs(manifestBaseTO, manifestDO);
			initializeSetByHib(manifestBaseTO, manifestDO);

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::ManifestUniversalDAOImpl::getManifestDetailsWithFetchProfile() :: "
					+ e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}

		LOGGER.debug("ManifestUniversalDAOImpl :: getManifestDetailsWithFetchProfile() :: End --------> ::::::");
		return manifestDO;
	}

	private void enableFetchProfileInSession(ManifestBaseTO manifestBaseTO,
			Session session) {
		if (manifestBaseTO.getIsFetchProfileManifestEmbedded()) {
			session.enableFetchProfile(ManifestUniversalConstants.Fetch_Profile_Manifest_Embedded);
		}
		if (manifestBaseTO.getIsFetchProfileManifestParcel()) {
			session.enableFetchProfile(ManifestUniversalConstants.Fetch_Profile_Manifest_Parcel);
		}
		if (manifestBaseTO.getIsFetchProfileManifestDox()) {
			session.enableFetchProfile(ManifestUniversalConstants.Fetch_Profile_Manifest_Dox);
		}
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

}
