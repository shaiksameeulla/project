/**
 * 
 */
package com.ff.web.manifest.pod.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.manifest.ConsignmentManifestDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.universe.manifest.constant.ManifestUniversalConstants;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.pod.constants.PODManifestConstants;

/**
 * @author nkattung
 * 
 */
public class PODManifestCommonDAOImpl extends CGBaseDAO implements
		PODManifestCommonDAO {
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(PODManifestCommonDAOImpl.class);

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
		LOGGER.trace("PODManifestCommonDAOImpl :: saveOrUpdateManifest() :: Start --------> ::::::");
		try {
			
			/* Setting Created and updated date in manifest*/
			manifestDO.setCreatedDate(DateUtil.getCurrentDate());
			manifestDO.setUpdatedDate(DateUtil.getCurrentDate());
			
			getHibernateTemplate().merge(manifestDO);
			
			
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::PODManifestCommonDAOImpl::saveOrUpdateManifest() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("PODManifestCommonDAOImpl :: saveOrUpdateManifest() :: End --------> ::::::");
		return manifestDO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.dao.InManifestCommonDAO#
	 * saveOrUpdateManifestProcess(java.util.List)
	 */
	/*@Override
	public boolean saveOrUpdateManifestProcess(
			List<ManifestProcessDO> manifestProcessDOs)
			throws CGSystemException {
		LOGGER.debug("PODManifestCommonDAOImpl :: saveOrUpdateManifestProcess() :: Start --------> ::::::");
		boolean isSaved = Boolean.FALSE;
		try {
			for (ManifestProcessDO manifestProcessDO : manifestProcessDOs) {
				
				 Setting Created and updated date in manifestProcess
				manifestProcessDO.setCreatedDate(DateUtil.getCurrentDate());
				manifestProcessDO.setUpdatedDate(DateUtil.getCurrentDate());
				getHibernateTemplate().saveOrUpdate(manifestProcessDO);
			}
			isSaved = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::PODManifestCommonDAOImpl::saveOrUpdateManifestProcess() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("PODManifestCommonDAOImpl :: saveOrUpdateManifestProcess() :: End --------> ::::::");
		return isSaved;
	}
*/
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.manifest.dao.ManifestUniversalDAO#searchManifestDtls(
	 * com.ff.manifest.ManifestInputs)
	 */
	@SuppressWarnings("unchecked")
	public ManifestDO searchManifestDtls(String manifestNo,
			String manifestDirection, String processCode, Integer orgOfficeId)
			throws CGSystemException {
		LOGGER.debug("PODManifestCommonDAOImpl :: searchManifestDtls() :: Start --------> ::::::");
		ManifestDO manifestDO = null;
		List<ManifestDO> manifestDOs = null;
		Session session = null;
		Query query = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			if(StringUtils.equalsIgnoreCase("I", manifestDirection)) {
				query = session
					.getNamedQuery(ManifestUniversalConstants.QRY_GET_MANIFEST_DTLS_IN);
			}else{
				query = session
						.getNamedQuery(ManifestUniversalConstants.QRY_GET_OUTGOIN_POD_MANIFEST_DTLS);
			}
			query.setParameter(ManifestUniversalConstants.MANIFEST_NO,
					manifestNo);
			query.setParameter(ManifestUniversalConstants.MANIFEST_TYPE,
					PODManifestConstants.POD_MANIFEST);
			query.setParameter(
					ManifestUniversalConstants.MANIFEST_PROCESS_CODE,
					processCode);
			query.setParameter(ManifestUniversalConstants.MANIFEST_DIRECTION,
					manifestDirection);
			query.setParameter(ManifestUniversalConstants.ORIGIN_OFFICE_ID,
					orgOfficeId);
			manifestDOs = query.list();
			manifestDO = !StringUtil.isEmptyList(manifestDOs) ? manifestDOs
					.get(0) : null;
			if (manifestDO != null) {
				Hibernate.initialize(manifestDO.getConsignments());
			}

		} catch (Exception e) {
			LOGGER.error("PODManifestCommonDAOImpl :: searchManifestDtls()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			 session.close();
		}
		LOGGER.debug("PODManifestCommonDAOImpl :: searchManifestDtls() :: END --------> ::::::");
		return manifestDO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.dao.OutManifestCommonDAO#isConsgnNoManifested(com
	 * .ff.manifest.OutManifestValidate)
	 */
	@Override
	public boolean isConsgnNoManifested(String consgNumber,
			String manifestDirection, String manifestType,
			String manifestPorcessCode) throws CGSystemException {
		LOGGER.debug("PODManifestCommonDAOImpl :: isConsgnNoManifested() :: Start --------> ::::::");
		boolean isCNManifested = Boolean.FALSE;
		try {
			String queryName = PODManifestConstants.QRY_IS_CONSG_MANIFESTED;
			String[] params = { OutManifestConstants.CONSIGNMENT_NO,
					OutManifestConstants.MANIFEST_DIRECTION,
					ManifestUniversalConstants.MANIFEST_TYPE,
					ManifestUniversalConstants.MANIFEST_PROCESS_CODE };

			Object[] values = { consgNumber, manifestDirection, manifestType,
					manifestPorcessCode };
			long count = 0;
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, params, values)
					.get(0);
			if (count > 0)
				isCNManifested = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error("PODManifestCommonDAOImpl :: isConsgnNoManifested()::::::"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("PODManifestCommonDAOImpl :: isConsgnNoManifested() :: Start --------> ::::::");
		return isCNManifested;
	}

	public boolean isConsgBelongsToManifest(String manifestNo, String consgNo)
			throws CGSystemException {
		boolean isBelongsTO = Boolean.FALSE;
		return isBelongsTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ManifestDO searchOutgoingPODManifetsDtls(String manifestNo,
			String manifestDirection, String processCode, Integer orgOfficeId)
			throws CGSystemException {
		LOGGER.debug("PODManifestCommonDAOImpl :: searchManifestDtls() :: Start --------> ::::::");
		ManifestDO manifestDO = null;
		List<ManifestDO> manifestDOs = null;
		Session session = null;
		Query query = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			query = session
					.getNamedQuery(ManifestUniversalConstants.QRY_GET_MANIFEST_DTLS);
			query.setParameter(ManifestUniversalConstants.MANIFEST_NO,
					manifestNo);
			query.setParameter(ManifestUniversalConstants.MANIFEST_TYPE,
					PODManifestConstants.POD_MANIFEST);
			query.setParameter(
					ManifestUniversalConstants.MANIFEST_PROCESS_CODE,
					processCode);
			query.setParameter(ManifestUniversalConstants.MANIFEST_DIRECTION,
					manifestDirection);
			query.setParameter(ManifestUniversalConstants.ORIGIN_OFFICE_ID,
					orgOfficeId);
			manifestDOs = query.list();
			manifestDO = !StringUtil.isEmptyList(manifestDOs) ? manifestDOs
					.get(0) : null;
			if (manifestDO != null) {
				Hibernate.initialize(manifestDO.getConsignments());
			}

		} catch (Exception e) {
			LOGGER.error("PODManifestCommonDAOImpl :: searchManifestDtls()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			 session.close();
		}
		LOGGER.debug("PODManifestCommonDAOImpl :: searchManifestDtls() :: END --------> ::::::");
		return manifestDO;
	}
		
			@Override
			public DeliveryDetailsDO getDeliverdConsgDtlsForDestBranchToDestHub(String consignment,
					Integer orgOfficeId) throws CGSystemException {
				DeliveryDetailsDO deliveryDtls = null;
				Session session = null;
				List<DeliveryDetailsDO> deliveryDtlsDOs = null;
				try {
					String[] params = { UniversalDeliveryContants.QRY_PARAM_CONSG,
							UniversalDeliveryContants.QRY_PARAM_DELIVERY_STATUS,
							UniversalDeliveryContants.CONSG_ORIGIN_OFF_ID };
					Object[] values = { consignment,
							UniversalDeliveryContants.DELIVERY_STATUS_DELIVERED,
							orgOfficeId };
					deliveryDtlsDOs = getHibernateTemplate()
							.findByNamedQueryAndNamedParam(
									PODManifestConstants.QRY_GET_CONSG_DETAILS_FOR_DESTBRANCH_TO_DESTHUB,
									params, values);
					deliveryDtls = !StringUtil.isEmptyList(deliveryDtlsDOs) ? deliveryDtlsDOs
							.get(0) : null;
				} catch (Exception e) {
					LOGGER.error("Error occured in DeliveryUniversalDAOImpl :: getDeliverdConsgDtls()..:"
							+ e.getMessage());
				} finally {
					closeSession(session);
				}
				return deliveryDtls;
			}
			
			
			
			public List<ConsignmentManifestDO> isConsgnNoManifestedToDestHub(String consignment,
					String podManifestType, String manifestProcessCode,Integer officeId) throws CGSystemException{
				List<ConsignmentManifestDO> manifestCosgDtlsDOs = null;
				try {
					String queryName = PODManifestConstants.QRY_GET_OUTGOING_POD_CONSG_DETAILS;
					String[] params = { OutManifestConstants.CONSIGNMENT_NO,
							OutManifestConstants.MANIFEST_DIRECTION,
							ManifestUniversalConstants.MANIFEST_TYPE,
							ManifestUniversalConstants.MANIFEST_PROCESS_CODE,
							PODManifestConstants.PARAM_DEST_OFF_ID };
					Object[] values = { consignment, "O", podManifestType,
							manifestProcessCode, officeId };
					manifestCosgDtlsDOs = getHibernateTemplate()
							.findByNamedQueryAndNamedParam(queryName, params, values);
					if (!StringUtil.isEmptyList(manifestCosgDtlsDOs))
						return manifestCosgDtlsDOs;
				}catch (Exception e) {
						LOGGER.error("PODManifestCommonDAOImpl :: getOutgoingPODConsgDtls()::::::"
								+ e.getMessage());
						throw new CGSystemException(e);
					}
					LOGGER.trace("PODManifestCommonDAOImpl :: getOutgoingPODConsgDtls():::: END");
					return manifestCosgDtlsDOs;
			}
			
			
			
			
			public List<ConsignmentManifestDO> isConsgnNoInManifestedAtLoggdInOffc(String consignment,
					String podManifestType, String manifestProcessCode,Integer officeId) throws CGSystemException{
				List<ConsignmentManifestDO> manifestCosgDtlsDOs = null;
				try {
					String queryName = PODManifestConstants.QRY_IS_CONS_IN_POD_MANIFESTD;
					String[] params = { OutManifestConstants.CONSIGNMENT_NO,
							OutManifestConstants.MANIFEST_DIRECTION,
							ManifestUniversalConstants.MANIFEST_TYPE,
							ManifestUniversalConstants.MANIFEST_PROCESS_CODE,
							PODManifestConstants.PARAM_ORG_OFF_ID };
					Object[] values = { consignment, "I", podManifestType,
							manifestProcessCode, officeId };
					manifestCosgDtlsDOs = getHibernateTemplate()
							.findByNamedQueryAndNamedParam(queryName, params, values);
					if (!StringUtil.isEmptyList(manifestCosgDtlsDOs))
						return manifestCosgDtlsDOs;
				}catch (Exception e) {
						LOGGER.error("PODManifestCommonDAOImpl :: getOutgoingPODConsgDtls()::::::"
								+ e.getMessage());
						throw new CGSystemException(e);
					}
					LOGGER.trace("PODManifestCommonDAOImpl :: getOutgoingPODConsgDtls():::: END");
					return manifestCosgDtlsDOs;
			}

			@SuppressWarnings("unchecked")
			@Override
			public DeliveryDetailsDO getConsignmentDetailsForParentCN(
					String consignment, Integer orgOfficeId)
					throws CGSystemException {
				DeliveryDetailsDO deliveryDtls = null;
				Session session = null;
				List<DeliveryDetailsDO> deliveryDtlsDOs = null;
				try {
					String[] params = { UniversalDeliveryContants.QRY_PARAM_CONSG,
							UniversalDeliveryContants.QRY_PARAM_DELIVERY_STATUS,
							UniversalDeliveryContants.CONSG_ORIGIN_OFF_ID };
					Object[] values = { consignment,
							UniversalDeliveryContants.DELIVERY_STATUS_DELIVERED,
							orgOfficeId };
					deliveryDtlsDOs = getHibernateTemplate()
							.findByNamedQueryAndNamedParam(
									PODManifestConstants.QRY_GET_CONSG_DETAILS_FOR_DESTBRANCH_TO_DESTHUB_FOR_PARENT_CN,
									params, values);
					deliveryDtls = !StringUtil.isEmptyList(deliveryDtlsDOs) ? deliveryDtlsDOs
							.get(0) : null;
				} catch (Exception e) {
					LOGGER.error("Error occured in DeliveryUniversalDAOImpl :: getDeliverdConsgDtls()..:"
							+ e.getMessage());
				} finally {
					closeSession(session);
				}
				return deliveryDtls;
			}
			

}
