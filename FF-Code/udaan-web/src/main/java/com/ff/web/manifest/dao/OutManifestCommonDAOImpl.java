package com.ff.web.manifest.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.manifest.ComailDO;
import com.ff.domain.manifest.ConsignmentManifestDO;
import com.ff.domain.manifest.LoadLotDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.ratemanagement.masters.RateComponentDO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.ManifestProductMapTO;
import com.ff.manifest.OutManifestValidate;
import com.ff.universe.manifest.constant.ManifestUniversalConstants;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.inmanifest.constants.InManifestConstants;

/**
 * The Class OutManifestCommonDAOImpl.
 */
public class OutManifestCommonDAOImpl extends CGBaseDAO implements
		OutManifestCommonDAO {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(OutManifestCommonDAOImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.dao.OutManifestCommonDAO#getLoadNo()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LoadLotDO> getLoadNo() throws CGSystemException {
		List<LoadLotDO> loadLotDOList = null;
		try {
			loadLotDOList = getHibernateTemplate().findByNamedQuery(
					OutManifestConstants.QRY_GET_LOAD_NO);
		} catch (Exception e) {
			LOGGER.error("OutManifestCommonDAOImpl :: getLoadNo()::::::"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		return loadLotDOList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.dao.OutManifestCommonDAO#isConsgnNoManifested(com
	 * .ff.manifest.OutManifestValidate)
	 */
	@Override
	public boolean isConsgnNoManifested(OutManifestValidate manifestValidateTO)
			throws CGSystemException {
		boolean isCNManifested = Boolean.FALSE;
		try {
			String queryName = OutManifestConstants.QRY_IS_CONSGNMENT_NO_MANIFESTED;
			String[] params = { OutManifestConstants.CONSIGNMENT_NO,
					OutManifestConstants.MANIFEST_DIRECTION,
					OutManifestConstants.ALLOWED_CONSG_MANIFESTED_TYPE,
					OutManifestConstants.LOGIN_OFFICE_ID,
					OutManifestConstants.MANIFEST_NO };

			Object[] values = { manifestValidateTO.getConsgNumber(),
					manifestValidateTO.getManifestDirection(),
					manifestValidateTO.getAllowedConsgManifestedType(),
					manifestValidateTO.getOriginOffice().getOfficeId(),
					manifestValidateTO.getManifestNumber() };
			long count = 0;
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, params, values)
					.get(0);

			if (count > 0)
				isCNManifested = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error("OutManifestCommonDAOImpl :: isConsgnNoManifested()::::::"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		return isCNManifested;
	}

	@Override
	public List<ConsignmentManifestDO> isConsgnNoManifestedForThirdParty(OutManifestValidate manifestValidateTO)
			throws CGSystemException {
		
		List<ConsignmentManifestDO> consManifstList = null;
		try {
			String queryName = OutManifestConstants.QRY_IS_CONSGNMENT_NO_MANIFESTED_FOR_THIRDPARTY;
			String[] params = { OutManifestConstants.CONSIGNMENT_NO,
					OutManifestConstants.MANIFEST_DIRECTION,
					OutManifestConstants.ALLOWED_CONSG_MANIFESTED_TYPE,
					OutManifestConstants.LOGIN_OFFICE_ID,
					OutManifestConstants.MANIFEST_NO };

			Object[] values = { manifestValidateTO.getConsgNumber(),
					manifestValidateTO.getManifestDirection(),
					manifestValidateTO.getAllowedConsgManifestedType(),
					manifestValidateTO.getOriginOffice().getOfficeId(),
					manifestValidateTO.getManifestNumber() };
			
			consManifstList = (List<ConsignmentManifestDO>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, params, values);

			
		} catch (Exception e) {
			LOGGER.error("OutManifestCommonDAOImpl :: isConsgnNoManifestedForThirdParty()::::::"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		return consManifstList;
	}
	
	
	
	
	/**
	 * Checks if is consgn no manifested for branch manifest.
	 * 
	 * @param manifestValidateTO
	 *            the manifest validate to
	 * @return true, if is consgn no manifested for branch manifest
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public boolean isConsgnNoManifestedForBranchManifest(
			OutManifestValidate manifestValidateTO) throws CGSystemException {
		boolean isCNManifestedByRTO = Boolean.FALSE;
		try {
			String queryName = OutManifestConstants.QRY_IS_CONSGNMENT_NO_MANIFESTED_FOR_BRANCH;
			String[] params = { OutManifestConstants.CONSIGNMENT_NO,
					OutManifestConstants.MANIFEST_DIRECTION,
					OutManifestConstants.ALLOWED_CONSG_MANIFESTED_TYPE,
					OutManifestConstants.LOGIN_OFFICE_ID,
					OutManifestConstants.MANIFEST_NO,
					OutManifestConstants.MANIFEST_PROCESS_CODE_FOR_RTO };

			Object[] values = { manifestValidateTO.getConsgNumber(),
					manifestValidateTO.getManifestDirection(),
					manifestValidateTO.getAllowedConsgManifestedType(),
					manifestValidateTO.getOriginOffice().getOfficeId(),
					manifestValidateTO.getManifestNumber(), "R" };
			long count = 0;
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, params, values)
					.get(0);

			if (count > 0)
				isCNManifestedByRTO = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error("OutManifestCommonDAOImpl :: isConsgnNoManifestedForBranchManifest()::::::"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		return isCNManifestedByRTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.dao.OutManifestCommonDAO#isManifestEmbeddedIn(com
	 * .ff.manifest.ManifestInputs)
	 */
	public boolean isManifestEmbeddedIn(ManifestInputs manifestInputs)
			throws CGSystemException {
		boolean isManifestEmbeddedIn = Boolean.FALSE;
		try {
			long count = 0;
			String[] params = { OutManifestConstants.MANIFEST_ID,
					OutManifestConstants.OFFICE_ID,
					OutManifestConstants.MANIFEST_DIRECTION,
					OutManifestConstants.MANIFEST_NO };
			Object[] values = { manifestInputs.getManifestId(),
					manifestInputs.getLoginOfficeId(),
					CommonConstants.MANIFEST_TYPE_OUT,
					manifestInputs.getHeaderManifestNo() };
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							OutManifestConstants.QRY_IS_MANIFEST_EMBEDDED_IN,
							params, values).get(0);
			if (count > 0)
				isManifestEmbeddedIn = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error("OutManifestCommonDAOImpl :: isManifestEmbeddedIn()::::::"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		return isManifestEmbeddedIn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.dao.OutManifestCommonDAO#getManifestProductMapDtls
	 * (com.ff.manifest.ManifestProductMapTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getManifestProductMapDtls(
			ManifestProductMapTO mnfstProductMapTO) throws CGSystemException {
		List<Object[]> mnfstProdMap = null;
		try {
			String[] paramName = { OutManifestConstants.SCANNED_PRODUCT,
					OutManifestConstants.PROCESS_CODE,
					OutManifestConstants.CONSIGNMENT_TYPE,
					OutManifestConstants.MANIFEST_OPEN_TYPE,
					OutManifestConstants.OFFICE_TYPE };

			Object[] value = { mnfstProductMapTO.getScannedProduct(),
					mnfstProductMapTO.getManifestProcess(),
					mnfstProductMapTO.getConsignmentType(),
					mnfstProductMapTO.getManifestOpenType(),
					mnfstProductMapTO.getLoggedInOfficeType() };

			mnfstProdMap = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							OutManifestConstants.QRY_GET_MANIFEST_PRODUCT_MAP_DTLS,
							paramName, value);
		} catch (Exception e) {
			LOGGER.error("OutManifestCommonDAOImpl :: getManifestProductMapDtls()::::::"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		return mnfstProdMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.dao.OutManifestCommonDAO#isConsgnNoManifested(com
	 * .ff.manifest.OutManifestValidate)
	 */
	public boolean isConsgnNoInManifested(OutManifestValidate manifestValidateTO)
			throws CGSystemException {
		boolean isCNInManifested = Boolean.FALSE;
		try {
			String queryName = OutManifestConstants.QRY_IS_CONSGNMENT_NO_IN_MANIFESTED;
			String[] params = { OutManifestConstants.CONSIGNMENT_NO,
					OutManifestConstants.MANIFEST_TYPE,
					OutManifestConstants.LOGIN_OFFICE_ID };

			Object[] values = { manifestValidateTO.getConsgNumber(),
					ManifestConstants.MANIFEST_DIRECTION_IN,
					manifestValidateTO.getOriginOffice().getOfficeId() };

			long count = 0;
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, params, values)
					.get(0);

			if (count > 0)
				isCNInManifested = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error("OutManifestCommonDAOImpl :: isConsgnNoManifested()::::::"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		return isCNInManifested;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.dao.OutManifestCommonDAO#isManifestEmbeddedIn(com
	 * .ff.manifest.ManifestInputs)
	 */
	public boolean isEmbeddedTypeIsOfInManifest(Integer manifestEmbededId)
			throws CGSystemException {
		boolean isManifestEmbeddedOfTypeIn = Boolean.FALSE;
		try {
			long count = 0;
			String[] params = { OutManifestConstants.MANIFEST_EMBEDDED_ID,
					OutManifestConstants.MANIFEST_TYPE };
			Object[] values = { manifestEmbededId,
					ManifestConstants.MANIFEST_DIRECTION_IN };
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							OutManifestConstants.QRY_IS_MANIFEST_EMBEDDED_ID_OF_TYPE_IN,
							params, values).get(0);

			if (count > 0) {
				isManifestEmbeddedOfTypeIn = Boolean.TRUE;
			}

		} catch (Exception e) {
			LOGGER.error("OutManifestCommonDAOImpl :: isEmbeddedTypeIsOfInManifest()::::::"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		return isManifestEmbeddedOfTypeIn;
	}

	public ManifestDO getManifestById(Integer manifestId)
			throws CGSystemException {
		ManifestDO manifestDO = null;
		Session session = null;
		Criteria criteria = null;
		try {
			session = createSession();
			session.enableFetchProfile(InManifestConstants.Fetch_Profile_Manifest_Embedded);
			criteria = session.createCriteria(ManifestDO.class, "manifestDO");
			criteria.add(Restrictions.eq("manifestDO.manifestId", manifestId));
			manifestDO = (ManifestDO) criteria.uniqueResult();
			if (manifestDO != null) {
				Hibernate.initialize(manifestDO.getEmbeddedManifestDOs());
			}
		} catch (Exception e) {
			LOGGER.error("Error occured in OutManifestUniversalDAOImpl :: getConsDtls()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return manifestDO;
	}

	@Override
	public RateComponentDO getRateComponentByCode(String rateCompCode)
			throws CGSystemException {
		LOGGER.debug("OutManifestCommonServiceImpl :: getRateComponentByCode() :: START------------>:::::::");
		Session session = null;
		Criteria criteria = null;
		RateComponentDO componentDO = null;
		try {
			session = createSession();
			criteria = session.createCriteria(RateComponentDO.class,
					"rateComponentDO");
			criteria.add(Restrictions.eq("rateComponentDO.rateComponentCode",
					rateCompCode));
			componentDO = (RateComponentDO) criteria.uniqueResult();
		} catch (Exception e) {
			LOGGER.error("ERROR :: OutManifestCommonDAOImpl :: getRateComponentByCode() ::"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("OutManifestCommonServiceImpl :: getRateComponentByCode() :: END------------>:::::::");
		return componentDO;
	}

	@Override
	public ComailDO getComailsByComailNo(String comailNo)
			throws CGSystemException {
		LOGGER.debug("OutManifestCommonDAOImpl :: getComailsByComailNo() :: START------------>:::::::");
		Session session = null;
		Criteria criteria = null;
		ComailDO comailDO = null;
		try {
			session = createSession();
			criteria = session.createCriteria(ComailDO.class, "comailDO");
			criteria.add(Restrictions.eq("comailDO.coMailNo", comailNo));
			comailDO = (ComailDO) criteria.uniqueResult();
		} catch (Exception e) {
			LOGGER.error("ERROR :: OutManifestCommonDAOImpl :: getComailsByComailNo() ::"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("OutManifestCommonDAOImpl :: getComailsByComailNo() :: END------------>:::::::");
		return comailDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ManifestDO getUniqueManifest(ManifestDO manifestDO)
			throws CGSystemException {
		LOGGER.debug("OutManifestCommonDAOImpl :: getUniqueManifest() :: START------------>:::::::");
		try {
			/**
			 * MANIFEST_NO MANIFEST_TYPE MANIFEST_PROCESS_CODE OPERATING_OFFICE
			 */
			String[] params = { OutManifestConstants.MANIFEST_NO,
					OutManifestConstants.PARAM_MANIFEST_PROCESS_CODE,
					OutManifestConstants.MANIFEST_TYPE,
					OutManifestConstants.PARAM_OPERATING_OFFICE };
			Object[] values = { manifestDO.getManifestNo(),
					manifestDO.getManifestProcessCode(),
					manifestDO.getManifestType(),
					manifestDO.getOperatingOffice() };
			List<ManifestDO> manifestDOs = (List<ManifestDO>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							OutManifestConstants.QRY_GET_UNIQUE_MANIFEST,
							params, values);
			if (!StringUtil.isEmptyColletion(manifestDOs)
					&& manifestDOs.size() > 0) {
				manifestDO = manifestDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR :: OutManifestCommonDAOImpl :: getComailsByComailNo() ::"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("OutManifestCommonDAOImpl :: getUniqueManifest() :: END------------>:::::::");
		return manifestDO;
	}

	@Override
	public boolean isConsgnNoMisroute(OutManifestValidate manifestValidateTO)
			throws CGSystemException {
		LOGGER.trace("OutManifestCommonDAOImpl :: isConsgnNoMisroute() :: START");
		boolean result = Boolean.FALSE;
		try {
			String[] params = {
					OutManifestConstants.CONSIGNMENT_NO,
					OutManifestConstants.LOGIN_OFFICE_ID,
					OutManifestConstants.MANIFEST_TYPE
				};
			String[] manifestTypes = {
					CommonConstants.MANIFEST_TYPE_BRANCH_MISROUTE,
					CommonConstants.MANIFEST_TYPE_HUB_MISROUTE
				};
			Object[] values = {
					manifestValidateTO.getConsgNumber(),
					manifestValidateTO.getOriginOffice().getOfficeId(),
					manifestTypes
				};
			String queryName = OutManifestConstants.QRY_IS_CONSGNMENT_NO_MISROUTE;
			long count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, params, values)
					.get(0);
			if (count > 0)
				result = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in OutManifestCommonDAOImpl :: isConsgnNoMisroute() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("OutManifestCommonDAOImpl :: isConsgnNoMisroute() :: END");
		return result;
	}

	@Override
	public Integer getNoOfElementsFromIn(String manifestNo)
			throws CGSystemException {
		LOGGER.debug("OutManifestCommonDAOImpl :: getNoOfElementsFromIn() :: Start --------> ::::::");
		Integer noOfElement = null;
		Session session = null;
		try {

			session = createSession();
			Query qry = session
					.getNamedQuery(ManifestUniversalConstants.QRY_GET_NO_OF_ELEMENTS);
			qry.setString("manifestNo", manifestNo);

			qry.setMaxResults(1);
			List<Integer> noOfElements = qry.list();

			noOfElement = !StringUtil.isEmptyList(noOfElements) ? noOfElements
					.get(0) : null;

		} catch (Exception e) {
			LOGGER.error(
					"Exception Occured in::OutManifestCommonDAOImpl::getNoOfElementsFromIn() :: ",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("OutManifestCommonDAOImpl :: getNoOfElementsFromIn() :: End --------> ::::::");
		return noOfElement;
	}

	
	public Integer getManifestIdByNo(String manifestNo)
			throws CGSystemException{

		LOGGER.debug("OutManifestCommonDAOImpl :: getManifestIdByNo() :: Start --------> ::::::");
		Integer manifestId = null;
		Session session = null;
		try {

			session = createSession();
			Query qry = session
					.getNamedQuery(ManifestUniversalConstants.QRY_GET_MANIFEST_ID);
			qry.setString("manifestNo", manifestNo);

			qry.setMaxResults(1);
			List<Integer> manifestIds = qry.list();

			manifestId = !StringUtil.isEmptyList(manifestIds) ? manifestIds
					.get(0) : null;

		} catch (Exception e) {
			LOGGER.error(
					"Exception Occured in::OutManifestCommonDAOImpl::getManifestIdByNo() :: ",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("OutManifestCommonDAOImpl :: getManifestIdByNo() :: End --------> ::::::");
		return manifestId;
	
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object[] getManifestDtlsByConsgNoLoginOffice(
			OutManifestValidate manifestValidateTO) throws CGSystemException {
		LOGGER.debug("OutManifestCommonDAOImpl :: getManifestDtlsByConsgNoLoginOffice() :: Start --------> ::::::");
		Object manifest[] = null;
		Session session = null;
		try {
			session = createSession();
			Query qry = session
					.getNamedQuery("getManifestDtlsByConsgNoLoginOffice");
			qry.setString("consignmentNo", manifestValidateTO.getConsgNumber());
			qry.setInteger("operatingOffice", manifestValidateTO.getOriginOffice().getOfficeId());

			qry.setMaxResults(1);
			List<Object[]> manifestDtls = qry.list();


			manifest = !StringUtil.isEmptyList(manifestDtls) ? manifestDtls
					.get(0) : null;
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::OutManifestCommonDAOImpl::getManifestDtlsByConsgNoLoginOffice() :: "
					, e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("OutManifestCommonDAOImpl :: getManifestDtlsByConsgNoLoginOffice() :: End --------> ::::::");
		return manifest;
	}
}
