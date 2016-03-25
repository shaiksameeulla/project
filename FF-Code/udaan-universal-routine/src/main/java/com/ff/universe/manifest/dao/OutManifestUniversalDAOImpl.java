package com.ff.universe.manifest.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.loadmanagement.ManifestTO;
import com.ff.manifest.ManifestUpdateInput;
import com.ff.manifest.OutManifestDetailBaseTO;
import com.ff.universe.manifest.constant.ManifestUniversalConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class OutManifestUniversalDAOImpl.
 */
public class OutManifestUniversalDAOImpl extends CGBaseDAO implements
		OutManifestUniversalDAO {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(OutManifestUniversalDAOImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.manifest.dao.OutManifestUniversalDAO#
	 * getManifestDtlsByManifestNo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	// Load Dispatch Dependency services with Manifest
	public List<ManifestDO> getManifestDtlsByManifestNo(String manifestNumber)
			throws CGSystemException {
		List<ManifestDO> manifestDOList = null;
		try {
			manifestDOList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							ManifestUniversalConstants.QRY_GET_MANIFEST_DTLS_BY_MANIFEST_NO,
							ManifestUniversalConstants.MANIFEST_NO,
							manifestNumber);
		} catch (Exception e) {
			LOGGER.error("Error occured in OutManifestUniversalDAOImpl :: getManifestDtlsByManifestNo()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		return manifestDOList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.manifest.dao.OutManifestUniversalDAO#updateManifestWeight
	 * (com.ff.loadmanagement.ManifestTO)
	 */
	@Override
	// Load Dispatch Dependency services with Manifest
	public boolean updateManifestWeight(ManifestTO manifestTO)
			throws CGSystemException {
		Boolean isUpdated = Boolean.FALSE;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(ManifestUniversalConstants.QRY_UPDATE_MANIFEST_WEIGHT);
			query.setDouble(ManifestUniversalConstants.MANIFEST_WEIGHT,
					manifestTO.getManifestWeight());
			query.setInteger(ManifestUniversalConstants.MANIFEST_ID,
					manifestTO.getManifestId());
			/*
			 * query.setInteger(ManifestUniversalConstants.OPERATING_LEVEL,
			 * manifestTO.getOperatingLevel());
			 */
			query.executeUpdate();
			isUpdated = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error("Error occured in OutManifestUniversalDAOImpl :: updateManifestWeight()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return isUpdated;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.manifest.dao.OutManifestUniversalDAO#updateEmbeddedInDtls
	 * (com.ff.manifest.ManifestUpdateInput)
	 */
	@Override
	// Updating the Manifest_Weight and Manifest_Embedded_In with Manifest_id
	// @Reddy
	public boolean updateProcessIdDetails(ManifestUpdateInput manifestInput)
			throws CGSystemException {
		Boolean isUpdated = Boolean.FALSE;
		Session session = null;
		try {
			session = openTransactionalSession();
			Query query = session
					.getNamedQuery(ManifestUniversalConstants.QRY_UPDATE_PROCESS_ID);

			query.setInteger(ManifestUniversalConstants.UPDATED_PROCESS_ID,
					manifestInput.getUpdatedProcessId());
			query.setParameterList(ManifestUniversalConstants.MANIFEST_IDS,
					manifestInput.getManifestIds());
			query.executeUpdate();
			isUpdated = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error("Error occured in OutManifestUniversalDAOImpl :: updateManifestData()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeTransactionalSession(session);
		}
		return isUpdated;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.manifest.dao.OutManifestUniversalDAO#getBagRfIdByRfIdNO
	 * (java.lang.String)
	 */
	@Override
	public Integer getBagRfIdByRfIdNO(String rfIdNo) throws CGSystemException {
		Integer rfid = null;
		try {
			@SuppressWarnings("unchecked")
			List<Integer> rfids = (List<Integer>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							ManifestUniversalConstants.QRY_GET_RFID_BY_RFNO,
							ManifestUniversalConstants.RADIO_FREQUENCY_NO,
							rfIdNo);
			if (rfids != null && rfids.size() > 0) {
				rfid = rfids.get(0);
			}

		} catch (Exception e) {
			LOGGER.error("Error occured in OutManifestUniversalDAOImpl :: getBagRfIdByRfIdNO()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		return rfid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.manifest.dao.OutManifestUniversalDAO#getBagRfNoByRfId
	 * (java.lang.Integer)
	 */
	@Override
	// Getting BagRFNo by sending BagRfId...@ Reddy
	public String getBagRfNoByRfId(Integer rfId) throws CGSystemException {
		String rfNo = null;
		try {
			@SuppressWarnings("unchecked")
			List<String> rfNos = (List<String>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							ManifestUniversalConstants.QRY_GET_RFNO_BY_RFID,
							ManifestUniversalConstants.RADIO_FREQUENCY_ID, rfId);
			if (rfNos != null && rfNos.size() > 0) {
				rfNo = rfNos.get(0);
			}

		} catch (Exception e) {
			LOGGER.error("Error occured in OutManifestUniversalDAOImpl :: getBagRfNoByRfId()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		return rfNo;
	}

	@Override
	public boolean updateRemarksOfMisroute(int manifestId, String remarks,
			String manifestType, int updateProcessId) throws CGSystemException {
		Boolean isUpdated = Boolean.FALSE;
		Session session = null;
		try {
			session = openTransactionalSession();
			Query query = session
					.getNamedQuery(ManifestUniversalConstants.QRY_UPDATE_REMARKS_MISROUTE);

			query.setInteger(ManifestUniversalConstants.MANIFEST_ID, manifestId);
			query.setString(ManifestUniversalConstants.REMARKS, remarks);
			query.setString(ManifestUniversalConstants.MANIFEST_TYPE,
					manifestType);
			query.setInteger(ManifestUniversalConstants.UPDATED_PROCESS_ID,
					updateProcessId);
			query.executeUpdate();
			isUpdated = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error("Error occured in OutManifestUniversalDAOImpl :: updateManifestData()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeTransactionalSession(session);
		}
		return isUpdated;
	}

	@Override
	public boolean updatePositionByManifestId(ManifestUpdateInput manifestInput)
			throws CGSystemException {
		Boolean isUpdated = Boolean.FALSE;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(ManifestUniversalConstants.QRY_UPDATE_POSITION_BY_MANIFEST_ID);
			int size = manifestInput.getManifestIds().size();
			for (int i = 0; i < size; i++) {
				query.setInteger(ManifestUniversalConstants.MANIFEST_ID,
						manifestInput.getManifestIds().get(i).intValue());
				query.setInteger(ManifestUniversalConstants.POSITION,
						manifestInput.getPositions().get(i).intValue());
				query.executeUpdate();
			}
			isUpdated = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error("Error occured in OutManifestUniversalDAOImpl :: updatePositionByManifestId()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return isUpdated;
	}

	@Override
	public boolean updateConsgWeight(OutManifestDetailBaseTO to,
			Integer operatingLevel) throws CGSystemException {
		Boolean isUpdated = Boolean.FALSE;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory()
					.openSession();
			Query query = session
					.getNamedQuery(ManifestUniversalConstants.QRY_UPDATE_CONSIGNMENT_WEIGHT);
			query.setDouble(ManifestUniversalConstants.PARAM_FINAL_WT,
					to.getWeight());
			query.setInteger(ManifestUniversalConstants.CONSIGNMENT_ID,
					to.getConsgId());
			query.setInteger(ManifestUniversalConstants.PROCESS_ID,
					to.getProcessId());
			/*query.setInteger(ManifestUniversalConstants.OPERATING_LEVEL,
					operatingLevel);*/
			query.executeUpdate();
			isUpdated = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error("Error occured in OutManifestUniversalDAOImpl::updateConsgWeight()::"
					+ e.getMessage());
			throw new CGSystemException(e);
		}  finally{
			closeSession(session);
		}
		return isUpdated;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.manifest.dao.OutManifestUniversalDAO#
	 * getManifestDtlsByManifestNoOriginOffId(com.ff.loadmanagement.ManifestTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ManifestDO> getManifestDtlsByManifestNoOriginOffId(
			ManifestTO manifestTO) throws CGSystemException {
		List<ManifestDO> manifestDOList = null;
		try {
			manifestDOList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							ManifestUniversalConstants.QRY_GET_MANIFEST_DTLS_BY_MANIFEST_NO_OPERATING_OFFICE,
							new String[] {
									ManifestUniversalConstants.MANIFEST_NO,
									ManifestUniversalConstants.OPERATING_OFFICE },
							new Object[] {
									manifestTO.getManifestNumber(),
									manifestTO.getOriginOfficeTO()
											.getOfficeId() });

		} catch (Exception e) {
			LOGGER.error("Error occured in OutManifestUniversalDAOImpl :: getManifestDtlsByManifestNoOriginOffId()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		return manifestDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isPartyShiftedConsg(Integer consgId)
			throws CGBusinessException, CGSystemException {
		boolean isPartyShifted = Boolean.TRUE;
		List<DeliveryDetailsDO> drsDetailList = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						ManifestUniversalConstants.QRY_IS_PARTY_SHIFTED_CONSG,
						new String[] {
								ManifestUniversalConstants.PARAM_REASON_CODE,
								ManifestUniversalConstants.CONSIGNMENT_ID },
						new Object[] {
								ManifestUniversalConstants.PARTY_SHIFTED_REASON_CODE,
								consgId });

		if (drsDetailList != null && drsDetailList.size() > 0)
			isPartyShifted = Boolean.FALSE;

		return isPartyShifted;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.manifest.dao.OutManifestUniversalDAO#getConsDtls(java
	 * .util.List, java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<ConsignmentDO> getConsDtls(List<String> consgNumbers,
			String consgType) throws CGSystemException {
		List<ConsignmentDO> consList = null;
		Session session = null;
		Criteria criteria = null;
		try {
			session = createSession();
			criteria = session.createCriteria(ConsignmentDO.class).createAlias(
					"consgType", "consTypeDO");
			criteria.add(Restrictions.in("consgNo", consgNumbers));
			if (StringUtils.isNotEmpty(consgType)) {
				criteria.add(Restrictions.eq("consTypeDO.consignmentName",
						consgType));
			}

			consList = (List<ConsignmentDO>) criteria.list();
		} catch (Exception e) {
			LOGGER.error("Error occured in OutManifestUniversalDAOImpl :: getConsDtls()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return consList;

	}

	@Override
	public ProcessDO getProcess(String processCode) throws CGSystemException {
		LOGGER.trace("OutManifestUniversalDAOImpl::getProcess()::START");
		ProcessDO processDO = null;
		Session session = null;
		Criteria criteria = null;
		try {
			session = createSession();
			criteria = session.createCriteria(ProcessDO.class);
			criteria.add(Restrictions.eq("processCode", processCode));
			processDO = (ProcessDO) criteria.uniqueResult();
		} catch (Exception e) {
			LOGGER.error("Error occured in OutManifestUniversalDAOImpl :: getProcess()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("OutManifestUniversalDAOImpl::getProcess()::END");
		return processDO;
	}

	@Override
	public boolean saveOrUpdateConsignmentBillingRates(
			List<ConsignmentBillingRateDO> consignmentBillingRateDOs)
			throws CGSystemException {
		LOGGER.trace("OutManifestUniversalDAOImpl :: saveOrUpdateConsignmentBillingRates() :: START --------> ::::::");
		boolean result = Boolean.FALSE;
		try {
			for (ConsignmentBillingRateDO consgRateDO : consignmentBillingRateDOs) {
				getHibernateTemplate().merge(consgRateDO);
			}
			result = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error(
					"Exception occured in :: OutManifestUniversalDAOImpl :: saveOrUpdateConsignmentBillingRates() ::",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("OutManifestUniversalDAOImpl :: saveOrUpdateConsignmentBillingRates() :: END --------> ::::::");
		return result;
	}

}
