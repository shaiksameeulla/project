/**
 * Author: Narasimha Rao Kattunga 
 * OutgoingManifestServiceImpl
 */
package src.com.dtdc.mdbDao.manifest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;

import src.com.dtdc.constants.CommonConstants;
import src.com.dtdc.constants.DeliveryManifestConstants;
import src.com.dtdc.constants.ManifestConstant;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseDAO.CGBaseDAO;
import com.capgemini.lbs.framework.frameworkbaseDO.CGBaseEntity;
import com.dtdc.domain.booking.BookingLogDO;
import com.dtdc.domain.expense.MiscExpenseDO;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.manifest.ManifestTypeDO;
import com.dtdc.domain.manifest.RtnToOrgDO;
import com.dtdc.domain.master.document.DocumentDO;
import com.dtdc.domain.master.geography.PincodeDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.transaction.delivery.DeliveryDO;
import com.dtdc.domain.transaction.heldup.HeldUpReleaseItemsDtlsDO;
import com.dtdc.domain.transaction.manifest.ManifestBookingDO;

// TODO: Auto-generated Javadoc
/**
 * The Class OutgoingManifestMDBDAOImpl.
 */
public class OutgoingManifestMDBDAOImpl extends CGBaseDAO implements
OutgoingManifestMDBDAO {
	/** logger. */
	private final static Logger LOGGER = Logger
	.getLogger(OutgoingManifestMDBDAOImpl.class);

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.OutgoingManifestMDBDAO#saveOrUpdateManifest(List)
	 */
	@Override
	public String saveOrUpdateManifest(List<ManifestDO> manifestDOList)
	throws CGSystemException {
		LOGGER.trace("*********OutgoingManifestMDBDAOImpl::saveOrUpdateManifest*******: Start:Save"
				+ System.currentTimeMillis());
		String manifestNum = "";
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		StringBuffer manifestDetails = new StringBuffer();
		String message = "";
		try {
			for (ManifestDO manifestDO : manifestDOList) {
				if (StringUtils.isEmpty(manifestNum)) {
					manifestNum = manifestDO.getManifestNumber();
				}
				LOGGER.info("*********OutgoingManifestMDBDAOImpl::saveOrUpdateManifest*******: Start:Before Save:"
						+ System.currentTimeMillis());
				hibernateTemplate.saveOrUpdate(manifestDO);
				hibernateTemplate.flush();
				LOGGER.info("*********OutgoingManifestMDBDAOImpl::saveOrUpdateManifest*******CN No:" +manifestDO.getConsgNumber()+" is Saved sucessfully:"
						+ System.currentTimeMillis());
				LOGGER.info("*********OutgoingManifestMDBDAOImpl::saveOrUpdateManifest*******: End: After Save:"
						+ System.currentTimeMillis());
			}
			manifestDetails.append(ManifestConstant.SUCCESS_MSG);
			manifestDetails.append(ManifestConstant.COMMA);
			manifestDetails.append(manifestNum);
			message = manifestDetails.toString();
		} catch (Exception ex) {
			logger.error("OutgoingManifestMDBDAOImpl::saveOrUpdateManifest::Exception occured:"
					+ex.getMessage());
			LOGGER.error("*********OutgoingManifestMDBDAOImpl::saveOrUpdateManifest*******: Error occurred:Cause:"
					+ ex.getClass());
			throw new CGSystemException("  " + ex.getMessage(), ex);
		}
		LOGGER.trace("*********OutgoingManifestMDBDAOImpl::saveOrUpdateManifest*******: End:Save"
				+ System.currentTimeMillis());
		return message;

	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.OutgoingManifestMDBDAO#getManifestType(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ManifestTypeDO getManifestType(String manifestType)
	throws CGSystemException {

		List<ManifestTypeDO> manifestTypeDOList = null;
		String paramNames = ManifestConstant.MANIFEST_CODE;
		Object values = manifestType;
		ManifestTypeDO manifestTypeObject = null;
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		try {
			manifestTypeDOList = new ArrayList<ManifestTypeDO>();
			manifestTypeDOList = hibernateTemplate
			.findByNamedQueryAndNamedParam(
					ManifestConstant.GET_MANIFEST_TYPE_QUERY,
					paramNames, values);
			for (ManifestTypeDO manifestTypeDO : manifestTypeDOList) {

				manifestTypeObject = manifestTypeDO;
			}

		} catch (Exception ex) {
			logger.error("OutgoingManifestMDBDAOImpl::getManifestType::Exception occured:"
					+ex.getMessage());
			throw new CGSystemException("  " + ex.getMessage(), ex);
		} finally {
			hibernateTemplate = null;
		}

		return manifestTypeObject;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.OutgoingManifestMDBDAO#getBookingDetailsByConNum(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ManifestBookingDO getBookingDetailsByConNum(String conNum)
	throws CGSystemException {
		// TODO Auto-generated method stub
		ManifestBookingDO booking = null;
		Session session = null;
		// For Held up
		Object[] values = { ManifestConstant.BOOKING_STATUS, "H" };
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			booking = (ManifestBookingDO) session
			.createCriteria(ManifestBookingDO.class)
			.add(Restrictions.eq(ManifestConstant.CONSIGNMENT_NUMBER,
					conNum))
					.add(Restrictions.in(ManifestConstant.CONSIGNMENT_STATUS,
							values)).uniqueResult();
		} catch (Exception ex) {
			logger.error("OutgoingManifestMDBDAOImpl::getBookingDetailsByConNum::Exception occured:"
					+ex.getMessage());
			throw new CGSystemException(
					"Error occured while getting Booking details. "
					+ ex.getMessage(), ex);
		} finally {
			session.flush();
			session.close();
		}
		return booking;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.OutgoingManifestMDBDAO#getDuplicateConsignment(String, String, int)
	 */
	@Override
	public int getDuplicateConsignment(String consgNum, String manifestType,
			int mnfstTypeId) throws CGSystemException {
		int count = 0;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			count = session
			.createCriteria(ManifestDO.class)
			.add(Restrictions.eq(ManifestConstant.CONSG_NUMBER,
					consgNum))
					// NO need to check the mnfstTypeId for duplicate check
					// (allowing same RTO in master bag)
					// But this fix may not allow the mis route consignment
					.add(Restrictions.eq("mnsftTypes.mnfstTypeId", mnfstTypeId))
					.add(Restrictions.eq(
							ManifestConstant.OUTGOING_MANIFEST_TYPE,
							manifestType)).list().size();

		} catch (Exception ex) {
			logger.error("OutgoingManifestMDBDAOImpl::getDuplicateConsignment::Exception occured:"
					+ex.getMessage());
			throw new CGSystemException("  " + ex.getMessage(), ex);
		} finally {
			session.flush();
			session.close();
		}
		return count;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.OutgoingManifestMDBDAO#checkDuplicateManifestNumber(String)
	 */
	@Override
	public int checkDuplicateManifestNumber(String manifestNum)
	throws CGSystemException {
		int count = 0;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			count = session
			.createCriteria(ManifestDO.class)
			.add(Restrictions.eq(ManifestConstant.MANIFEST_NUMBER,
					manifestNum)).list().size();

		} catch (Exception ex) {
			logger.error("OutgoingManifestMDBDAOImpl::checkDuplicateManifestNumber::Exception occured:"
					+ex.getMessage());
			throw new CGSystemException("  " + ex.getMessage(), ex);
		} finally {
			session.flush();
			session.close();
		}
		return count;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.OutgoingManifestMDBDAO#findByManifestNumber(String, String)
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public List<ManifestDO> findByManifestNumber(String manifestNumber,
			String manifestCode) throws CGSystemException {
		List<ManifestDO> packetManifestList = null;
		Session session = null;
		try {
			if ((StringUtils.isNotEmpty(manifestNumber))) {
				ManifestTypeDO manifestTypeDO = getManifestType(manifestCode);
				packetManifestList = new ArrayList<ManifestDO>();
				session = getHibernateTemplate().getSessionFactory()
				.openSession();
				packetManifestList = session
				.createCriteria(ManifestDO.class)
				.add(Restrictions.eq(ManifestConstant.MANIFEST_NUMBER,
						manifestNumber))
						.add(Restrictions
								.eq(ManifestConstant.OUTGOING_MANIFEST_TYPE,
										ManifestConstant.MANIFEST_TYPE_AGAINST_OUTGOING))
										.add(Restrictions
												.eq(ManifestConstant.MANIFEST_TYPES_MANIFEST_TYPE_ID,
														manifestTypeDO.getMnfstTypeId()))
														.list();
				// TODO for MNP type id is 7 : need to remove hard coding : bag
				// non dox can be MNP or BMN
				if (packetManifestList.size() == 0
						&& manifestCode.equalsIgnoreCase("BMN")) {
					packetManifestList = session
					.createCriteria(ManifestDO.class)
					.add(Restrictions.eq(
							ManifestConstant.MANIFEST_NUMBER,
							manifestNumber))
							.add(Restrictions
									.eq(ManifestConstant.OUTGOING_MANIFEST_TYPE,
											ManifestConstant.MANIFEST_TYPE_AGAINST_OUTGOING))
											.add(Restrictions
													.eq(ManifestConstant.MANIFEST_TYPES_MANIFEST_TYPE_ID,
															7)).list();
				}

			}
		} catch (Exception ex) {
			logger.error("OutgoingManifestMDBDAOImpl::findByManifestNumber::Exception occured:"
					+ex.getMessage());
			throw new CGSystemException("  " + ex.getMessage(), ex);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return packetManifestList;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.OutgoingManifestMDBDAO#getDocumentID(String)
	 */
	@Override
	public DocumentDO getDocumentID(String docCode) throws CGSystemException {
		DocumentDO document = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			document = (DocumentDO) session
			.createCriteria(DocumentDO.class)
			.add(Restrictions.eq(ManifestConstant.DOCUMENT_TYPE,
					docCode)).list().get(0);
		} catch (Exception ex) {
			logger.error("OutgoingManifestMDBDAOImpl::getDocumentID::Exception occured:"
					+ex.getMessage());
			throw new CGSystemException("  " + ex.getMessage(), ex);
		} finally {
			session.flush();
			session.close();
		}
		return document;
	}

	/*
	 * Validating manifest by manifest type and returning manifest details
	 */
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.OutgoingManifestMDBDAO#getManifestByType(String, String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<ManifestDO> getManifestByType(String manifestNumber,
			String manifestType) throws CGSystemException {
		List<ManifestDO> manifestDetailsList = null;
		manifestDetailsList = new ArrayList<ManifestDO>();
		manifestDetailsList = getHibernateTemplate()
		.findByNamedQueryAndNamedParam(
				ManifestConstant.GET_MANIFEST_BY_NO,
				ManifestConstant.MANIFEST_NUMBER, manifestNumber);
		return manifestDetailsList;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.OutgoingManifestMDBDAO#isConsignmentDelivered(String, List)
	 */
	@Override
	public boolean isConsignmentDelivered(String consgNum,
			List<String> consgStatuses) throws CGSystemException {
		boolean isDelivered = Boolean.FALSE;
		Session session = null;
		int count = 0;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			count = session
			.createCriteria(DeliveryDO.class)
			.add(Restrictions.eq(
					ManifestConstant.DELIVERY_CONSG_NUMBER, consgNum))
					.add(Restrictions.in(ManifestConstant.DELIVERY_STATUS,
							consgStatuses)).list().size();
			if (count > 0) {
				isDelivered = Boolean.TRUE;
			}
		} catch (Exception ex) {
			logger.error("OutgoingManifestMDBDAOImpl::isConsignmentDelivered::Exception occured:"
					+ex.getMessage());
			throw new CGSystemException("  " + ex.getMessage(), ex);
		} finally {
			session.flush();
			session.close();
		}
		return isDelivered;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.OutgoingManifestMDBDAO#getRTOManifest(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RtnToOrgDO> getRTOManifest(String manifestNumber)
	throws CGSystemException {
		List<RtnToOrgDO> rtoManifestList = null;
		Session session = null;
		try {
			if ((StringUtils.isNotEmpty(manifestNumber))) {
				rtoManifestList = new ArrayList<RtnToOrgDO>();
				session = getHibernateTemplate().getSessionFactory()
				.openSession();
				rtoManifestList = session
				.createCriteria(RtnToOrgDO.class)
				.add(Restrictions.eq(ManifestConstant.MANIFEST_NUMBER,
						manifestNumber)).list();
			}
		} catch (Exception ex) {
			logger.error("OutgoingManifestMDBDAOImpl::getRTOManifest::Exception occured:"
					+ex.getMessage());
			throw new CGSystemException("  " + ex.getMessage(), ex);
		} finally {
			session.flush();
			session.close();
		}
		return rtoManifestList;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.OutgoingManifestMDBDAO#updateBookedWeight(String, Double, Double, String)
	 */
	@Override
	public void updateBookedWeight(String consgNumber, Double finalWeight,
			Double rateAmount, String updatedfProcessFrom)
	throws CGSystemException {
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
			.getNamedQuery(ManifestConstant.UPDATE_QUERY_FOR_BOOKING_WEIGHT);
			query.setDouble(ManifestConstant.FINAL_WEIGHT, finalWeight);
			query.setDouble(ManifestConstant.RATE_AMOUNT, rateAmount);
			query.setString(DeliveryManifestConstants.UPDATE_PROCESS,
					updatedfProcessFrom);
			query.setString(ManifestConstant.BOOKING_CONSG_NUMBER, consgNumber);
			query.executeUpdate();
		} catch (Exception ex) {
			logger.error("OutgoingManifestMDBDAOImpl::updateBookedWeight::Exception occured:"
					+ex.getMessage());
			throw new CGSystemException("  " + ex.getMessage(), ex);
		} finally {
			session.flush();
			session.close();
		}
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.OutgoingManifestMDBDAO#getHeldUpByManifest(String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public int getHeldUpByManifest(String manifestNumber)
	throws CGSystemException {
		List<HeldUpReleaseItemsDtlsDO> heldUpList = null;
		Session session = null;
		int heldUpcount = 0;
		try {
			if ((StringUtils.isNotEmpty(manifestNumber))) {
				heldUpList = new ArrayList<HeldUpReleaseItemsDtlsDO>();
				session = getHibernateTemplate().getSessionFactory()
				.openSession();
				heldUpList = session
				.createCriteria(HeldUpReleaseItemsDtlsDO.class)
				.add(Restrictions.eq(
						ManifestConstant.MANIFEST_NUMBER_QUERY_PARAM,
						manifestNumber)).list();
				if (heldUpList != null && !heldUpList.isEmpty()) {
					heldUpcount = heldUpList.size();
				}
			}
		} catch (Exception ex) {
			logger.error("OutgoingManifestMDBDAOImpl::getHeldUpByManifest::Exception occured:"
					+ex.getMessage());
			throw new CGSystemException("  " + ex.getMessage(), ex);
		} finally {
			session.flush();
			session.close();
		}
		return heldUpcount;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.OutgoingManifestMDBDAO#findByManifestNumber(String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<ManifestDO> findByManifestNumber(String manifestNumber)
	throws CGSystemException {
		List<ManifestDO> packetManifestList = null;
		Session session = null;
		try {
			if ((StringUtils.isNotEmpty(manifestNumber))) {
				packetManifestList = new ArrayList<ManifestDO>();
				session = getHibernateTemplate().getSessionFactory()
				.openSession();
				packetManifestList = session
				.createCriteria(ManifestDO.class)
				.add(Restrictions.eq(
						ManifestConstant.MANIFEST_NUMBER_QUERY_PARAM,
						manifestNumber))
						.add(Restrictions
								.eq(ManifestConstant.OUTGOING_MANIFEST_TYPE,
										ManifestConstant.MANIFEST_TYPE_AGAINST_OUTGOING))
										.list();
			}
		} catch (Exception ex) {
			logger.error("OutgoingManifestMDBDAOImpl::findByManifestNumber::Exception occured:"
					+ex.getMessage());
			throw new CGSystemException("  " + ex.getMessage(), ex);
		} finally {
			session.flush();
			session.close();
		}
		return packetManifestList;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.OutgoingManifestMDBDAO#findByMasterBag(String)
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public List<ManifestDO> findByMasterBag(String manifestNumber)
	throws CGSystemException {
		List<ManifestDO> packetManifestList = null;
		Session session = null;
		try {
			if ((StringUtils.isNotEmpty(manifestNumber))) {
				ManifestTypeDO manifestTypeDO = getManifestType(ManifestConstant.MANIFEST_TYPE_MASTER_BAG);
				packetManifestList = new ArrayList<ManifestDO>();
				session = getHibernateTemplate().getSessionFactory()
				.openSession();
				packetManifestList = session
				.createCriteria(ManifestDO.class)
				.add(Restrictions.eq(ManifestConstant.CONSG_NUMBER,
						manifestNumber))
						.add(Restrictions
								.eq(ManifestConstant.OUTGOING_MANIFEST_TYPE,
										ManifestConstant.MANIFEST_TYPE_AGAINST_OUTGOING))
										.add(Restrictions
												.eq(ManifestConstant.MANIFEST_TYPES_MANIFEST_TYPE_ID,
														manifestTypeDO.getMnfstTypeId()))
														.list();
			}
		} catch (Exception ex) {
			logger.error("OutgoingManifestMDBDAOImpl::findByMasterBag::Exception occured:"
					+ex.getMessage());
			throw new CGSystemException("  " + ex.getMessage(), ex);
		} finally {
			session.flush();
			session.close();
		}
		return packetManifestList;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.OutgoingManifestMDBDAO#updateSAPIntgConsgStatus(String, String)
	 */
	@Override
	public void updateSAPIntgConsgStatus(String consgNumber,
			String billingStatus) throws CGSystemException {
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
			.getNamedQuery(ManifestConstant.UPDATE_TO_BILLING_IN_SAP_INTEGRATION);
			query.setString(ManifestConstant.BILLING_STATUS, billingStatus);
			query.setString(ManifestConstant.CONSG_NUMBER, consgNumber);
			query.executeUpdate();
		} catch (Exception ex) {
			logger.error("OutgoingManifestMDBDAOImpl::updateSAPIntgConsgStatus::Exception occured:"
					+ex.getMessage());
			throw new CGSystemException("  " + ex.getMessage(), ex);
		} finally {
			session.flush();
			session.close();
		}

	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.OutgoingManifestMDBDAO#validatePinCode(String, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PincodeDO validatePinCode(String consgPinCode, int officeID)
	throws CGSystemException {
		// TODO Auto-generated method stub
		PincodeDO pincode = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			pincode = (PincodeDO) session
			.createCriteria(PincodeDO.class)
			.add(Restrictions.eq(CommonConstants.PINCODE, consgPinCode))
			.add(Restrictions.eq("office.officeId", officeID))
			.uniqueResult();
		} catch (Exception e) {
			logger.error("OutgoingManifestMDBDAOImpl::validatePinCode::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		} finally {
			session.flush();
			session.close();
		}
		return pincode;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.OutgoingManifestMDBDAO#getBooking(String, CGBaseEntity)
	 */
	@Override
	public CGBaseEntity getBooking(String consgNumber, CGBaseEntity entity)
	throws CGSystemException {
		Session session = null;
		CGBaseEntity booking = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			booking = (CGBaseEntity) session
			.createCriteria(entity.getClass())
			.add(Restrictions.eq(ManifestConstant.CONSIGNMENT_NUMBER,
					consgNumber)).uniqueResult();
		} catch (Exception ex) {
			logger.error("OutgoingManifestMDBDAOImpl::getBooking::Exception occured:"
					+ex.getMessage());
			throw new CGSystemException("  " + ex.getMessage(), ex);
		} finally {
			session.flush();
			session.close();
		}
		return booking;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.OutgoingManifestMDBDAO#insertBookingLog(BookingLogDO)
	 */
	@Override
	public void insertBookingLog(BookingLogDO logDo) throws CGSystemException {
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		try {
			hibernateTemplate.save(logDo);
		} catch (Exception e) {
			logger.error("OutgoingManifestMDBDAOImpl::insertBookingLog::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		} finally {
			hibernateTemplate.flush();
		}
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.OutgoingManifestMDBDAO#getFinalWeight(int)
	 */
	@Override
	public Double getFinalWeight(int bookingId) throws CGSystemException {
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		Double changeWeight = 0.0;
		List weightList = null;
		try {
			weightList = hibernateTemplate.findByNamedQueryAndNamedParam(
					ManifestConstant.getFinalWeight,
					ManifestConstant.BOOKING_ID, bookingId);
			if (weightList != null && !weightList.isEmpty()) {
				Object[] obj = (Object[]) weightList.get(0);
				changeWeight = Double.parseDouble(obj[0].toString());
			}
		} catch (Exception ex) {
			logger.error("OutgoingManifestMDBDAOImpl::getFinalWeight::Exception occured:"
					+ex.getMessage());
			throw new CGSystemException("  " + ex.getMessage(), ex);
		} finally {
			hibernateTemplate.flush();
		}
		return changeWeight;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.OutgoingManifestMDBDAO#isMisrouted(String, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean isMisrouted(String consgNumber, int destOffId)
	throws CGSystemException {
		long count = 0;
		boolean isMisrouted = Boolean.FALSE;
		int manifestTypeId = 0;
		try {
			ManifestTypeDO manifestTypeDO = getManifestType(ManifestConstant.MANIFEST_TYPE_REROUTE_PACKET);
			manifestTypeId = manifestTypeDO.getMnfstTypeId();
			String[] params = { "consgNumber", "brOffId", "manifestTypeId" };
			Object[] values = { consgNumber, destOffId, manifestTypeId };
			count = (Long) getHibernateTemplate()
			.findByNamedQueryAndNamedParam(
					ManifestConstant.isMisrouted, params, values)
					.get(0);
			if (count >= 1) {
				isMisrouted = Boolean.TRUE;
			}
		} catch (Exception ex) {
			logger.error("OutgoingManifestMDBDAOImpl::isMisrouted::Exception occured:"
					+ex.getMessage());
			throw new CGSystemException("  " + ex.getMessage(), ex);
		}
		return isMisrouted;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.OutgoingManifestMDBDAO#getIncmgMnfst(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ManifestDO getIncmgMnfst(String consgNumber)
	throws CGSystemException {
		ManifestDO manifest = null;
		List<Integer> manifestTypeIds = null;
		List<String> manifestCodes = null;
		List<ManifestDO> manifestList = null;
		try {
			manifestCodes = new ArrayList<String>();
			manifestCodes.add(ManifestConstant.MANIFEST_TYPE_PACKET);
			manifestCodes.add(ManifestConstant.MANIFEST_TYPE_BAG_NONDOX);
			manifestTypeIds = getManifestTypeIds(manifestCodes);
			String[] params = { "consgNumber", "manifestTypeId" };
			Object[] values = { consgNumber, manifestTypeIds };
			manifestList = getHibernateTemplate()
			.findByNamedQueryAndNamedParam("getIncmgManifest", params,
					values);
			if (manifestList != null && manifestList.size() > 0) {
				manifest = manifestList.get(0);
			}
		} catch (Exception ex) {
			logger.error("OutgoingManifestMDBDAOImpl::getIncmgMnfst::Exception occured:"
					+ex.getMessage());
			throw new CGSystemException("  " + ex.getMessage(), ex);
		}
		return manifest;
	}

	/**
	 * Gets the manifest type ids.
	 *
	 * @param manifestCodes the manifest codes
	 * @return the manifest type ids
	 * @throws CGSystemException the cG system exception
	 */
	@SuppressWarnings("unchecked")
	List<Integer> getManifestTypeIds(List<String> manifestCodes)
	throws CGSystemException {
		Session session = null;
		List<ManifestTypeDO> manifestTypes = null;
		List<Integer> manifestTypeIds = null;
		try {
			manifestTypeIds = new ArrayList<Integer>();
			session = getHibernateTemplate().getSessionFactory().openSession();
			manifestTypes = session
			.createCriteria(ManifestTypeDO.class)
			.add(Restrictions.in("mnfstCode", manifestCodes)).list();
			if (manifestTypes != null && manifestTypes.size() > 0) {
				for (ManifestTypeDO manifestType : manifestTypes) {
					manifestTypeIds.add(manifestType.getMnfstTypeId());
				}
			}
		} catch (Exception ex) {
			logger.error("OutgoingManifestMDBDAOImpl::getManifestTypeIds::Exception occured:"
					+ex.getMessage());
			throw new CGSystemException("  " + ex.getMessage(), ex);
		} finally {
			session.flush();
			session.close();
		}
		return manifestTypeIds;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.OutgoingManifestMDBDAO#saveMiscExpForRelease(List)
	 */
	@Override
	public String saveMiscExpForRelease(List<MiscExpenseDO> miscExpenseDoList)
	throws CGSystemException {
		// TODO Auto-generated method stub
		String message = "Failure";
		try {
			HibernateTemplate hibernateTemplate = getHibernateTemplate();
			if (miscExpenseDoList != null) {
				hibernateTemplate.saveOrUpdateAll(miscExpenseDoList);
				message = "SUCCESS";
			}
		} catch (Exception ex) {
			logger.error("OutgoingManifestMDBDAOImpl::saveMiscExpForRelease::Exception occured:"
					+ex.getMessage());
			throw new CGSystemException("  " + ex.getMessage(), ex);
		}

		return message;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.OutgoingManifestMDBDAO#pendingManifestCount(Date)
	 */
	@Override
	public long pendingManifestCount(Date manifestDate)
	throws CGSystemException {
		long countManfest = 0;
		long countFDMDelivery = 0;
		long countRTO = 0;
		long totalCount = 0;
		List<Integer> manifestTypeIds = null;
		List<String> manifestCodes = null;
		List<Integer> bagManifestTypeId = null;
		List<String> bagManifestCode = null;

		try {
			manifestCodes = new ArrayList<String>();
			manifestCodes.add(ManifestConstant.MANIFEST_TYPE_PACKET);
			manifestCodes.add(ManifestConstant.MANIFEST_TYPE_REROUTE_PACKET);
			manifestCodes.add(ManifestConstant.ROBO_MANIFEST_TYPE);
			manifestTypeIds = getManifestTypeIds(manifestCodes);
			bagManifestCode = new ArrayList<String>();
			bagManifestCode.add(ManifestConstant.MANIFEST_TYPE_BAG_DOX);
			bagManifestTypeId = getManifestTypeIds(bagManifestCode);

			String[] params = { "mnfstDate", "manifestTypeIds",
			"bagMnfstTypeId" };
			Object[] values = { manifestDate, manifestTypeIds,
					bagManifestTypeId };
			// Getting ManifestCount
			countManfest = getHibernateTemplate()
			.findByNamedQueryAndNamedParam(
					ManifestConstant.pendingManifestOnDay, params,
					values).size();
			// Getting RTO Manifest Count
			String[] params1 = { "mnfstDate", "bagMnfstTypeId" };
			Object[] values1 = { manifestDate, bagManifestTypeId };
			countRTO = getHibernateTemplate().findByNamedQueryAndNamedParam(
					ManifestConstant.pendingRTOManifestOnDay, params1, values1)
					.size();
			// Getting FDM Manifest Count
			String[] params2 = { "mnfstDate", "bagMnfstTypeId" };
			Object[] values2 = { manifestDate, bagManifestTypeId };
			countFDMDelivery = getHibernateTemplate()
			.findByNamedQueryAndNamedParam(
					ManifestConstant.pendingFDMManifestOnDay, params2,
					values2).size();
			totalCount = countManfest + countRTO + countFDMDelivery;

		} catch (Exception ex) {
			logger.error("OutgoingManifestMDBDAOImpl::pendingManifestCount::Exception occured:"
					+ex.getMessage());
			throw new CGSystemException("  " + ex.getMessage(), ex);
		}
		return totalCount;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.OutgoingManifestMDBDAO#getOrgDestOffIdsByManifest(String, String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public String getOrgDestOffIdsByManifest(String manifestNumber,
			String manifestCode) throws CGSystemException {
		Session session = null;
		List<Object[]> results = null;
		StringBuffer offIds = new StringBuffer();
		List<String> list = null;
		try {
			ManifestTypeDO manifestTypeDO = getManifestType(manifestCode);
			session = getHibernateTemplate().getSessionFactory().openSession();
			Criteria criteria = session.createCriteria(ManifestDO.class);
			criteria.add(Restrictions.eq(ManifestConstant.MANIFEST_NUMBER,
					manifestNumber));
			criteria.add(Restrictions.eq(
					ManifestConstant.OUTGOING_MANIFEST_TYPE,
					ManifestConstant.MANIFEST_TYPE_AGAINST_OUTGOING));
			criteria.add(Restrictions.eq(
					ManifestConstant.MANIFEST_TYPES_MANIFEST_TYPE_ID,
					manifestTypeDO.getMnfstTypeId()));
			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.property("originBranch.officeId"));
			projList.add(Projections.property("destBranch.officeId"));
			criteria.setProjection(projList);
			results = criteria.list();
			if (results != null && !results.isEmpty()) {
				Object[] objArray = results.get(0);
				offIds.append(objArray[0]);
				offIds.append(CommonConstants.COMMA);
				offIds.append(objArray[1]);
			}
			// setting is bag maniefsted
			if (StringUtils.equals(manifestCode,
					ManifestConstant.MANIFEST_TYPE_PACKET)) {
				int bagManifestTypeId = 0;
				bagManifestTypeId = getManifestTypeId(ManifestConstant.MANIFEST_TYPE_BAG_DOX);
				String[] params = { "manifstNumner", "manifestTypeId" };
				Object[] values = { manifestNumber, bagManifestTypeId };
				// Getting ManifestCount
				list = getHibernateTemplate()
				.findByNamedQueryAndNamedParam("isBagManifested",
						params, values);
				if (list != null && list.size() > 0) {
					offIds.append(CommonConstants.COMMA);
					offIds.append("PACKETISINBAG");
				} else {
					offIds.append(CommonConstants.COMMA);
					offIds.append(CommonConstants.EMPTY_STRING);
				}
			} else if (StringUtils.equals(manifestCode,
					ManifestConstant.MANIFEST_TYPE_BAG_DOX)
					|| StringUtils.equals(manifestCode,
							ManifestConstant.MANIFEST_TYPE_BAG_NONDOX)) {
				int bagManifestTypeId = 0;
				bagManifestTypeId = getManifestTypeId(ManifestConstant.MANIFEST_TYPE_MASTER_BAG);
				String[] params = { "manifstNumner", "manifestTypeId" };
				Object[] values = { manifestNumber, bagManifestTypeId };
				// Getting ManifestCount
				list = getHibernateTemplate()
				.findByNamedQueryAndNamedParam("isBagManifested",
						params, values);
				if (list != null && list.size() > 0) {
					offIds.append(CommonConstants.COMMA);
					offIds.append("BAGINMASTERBAG");
				} else {
					offIds.append(CommonConstants.COMMA);
					offIds.append(CommonConstants.EMPTY_STRING);
				}

			}
		} catch (Exception e) {
			logger.error("OutgoingManifestMDBDAOImpl::getOrgDestOffIdsByManifest::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		}
		return offIds.toString();
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.OutgoingManifestMDBDAO#getWeightByConsg(String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Double getWeightByConsg(String consgNumber) throws CGSystemException {
		Session session = null;
		List<Object[]> results = null;
		Double weightOnBooking = 0.0;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Criteria criteria = session.createCriteria(ManifestBookingDO.class);
			criteria.add(Restrictions.eq(ManifestConstant.CONSIGNMENT_NUMBER,
					consgNumber));
			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.property("bookedWeight"));
			criteria.setProjection(projList);
			results = criteria.list();
			if (results != null && !results.isEmpty()) {
				Object object = results.get(0);
				weightOnBooking = (Double) object;
			}
		} catch (Exception e) {
			logger.error("OutgoingManifestMDBDAOImpl::getWeightByConsg::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		}
		return weightOnBooking;
	}

	/**
	 * Gets the manifest type id.
	 *
	 * @param manifestCode the manifest code
	 * @return the manifest type id
	 * @throws CGSystemException the cG system exception
	 */
	@SuppressWarnings("unchecked")
	public int getManifestTypeId(String manifestCode) throws CGSystemException {
		Session session = null;
		List<Object[]> results = null;
		int mnaifestTypeId = 0;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Criteria criteria = session.createCriteria(ManifestTypeDO.class);
			criteria.add(Restrictions.eq(ManifestConstant.MANIFEST_CODE,
					manifestCode));
			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.property("mnfstTypeId"));
			criteria.setProjection(projList);
			results = criteria.list();
			if (results != null && !results.isEmpty()) {
				Object object = results.get(0);
				mnaifestTypeId = (Integer) object;
			}
		} catch (Exception ex) {
			logger.error("OutgoingManifestMDBDAOImpl::getManifestTypeId::Exception occured:"
					+ex.getMessage());
			throw new CGSystemException("  " + ex.getMessage(), ex);
		} finally {
			session.flush();
			session.close();
		}
		return mnaifestTypeId;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.OutgoingManifestMDBDAO#pendingMNPBookings(Date)
	 */
	@Override
	public long pendingMNPBookings(Date manifestDate) throws CGSystemException {
		long countMNPBookings = 0;
		int mnpMnfstTypeId = 0;

		try {
			mnpMnfstTypeId = getManifestTypeId(ManifestConstant.MANIFEST_TYPE_MNPBAG);
			String[] params = { "mnfstDate", "mnpMnfstTypeId" };
			Object[] values = { manifestDate, mnpMnfstTypeId };
			// Getting ManifestCount
			countMNPBookings = getHibernateTemplate()
			.findByNamedQueryAndNamedParam(
					ManifestConstant.pendingMNPBookingOnDay, params,
					values).size();

		} catch (Exception ex) {
			logger.error("OutgoingManifestMDBDAOImpl::pendingMNPBookings::Exception occured:"
					+ex.getMessage());
			throw new CGSystemException("  " + ex.getMessage(), ex);
		}
		return countMNPBookings;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.OutgoingManifestMDBDAO#validateApexForBranch(String, String)
	 */
	@Override
	public boolean validateApexForBranch(String apexOfficeCode,
			String branchOfficeCode) {

		boolean validApex = false;

		String hql = "validateApexForBranch";
		Object[] values = { apexOfficeCode, branchOfficeCode };
		String[] params = { "apexOfficeCode", "officeCode" };
		List<OfficeDO> apexes = getHibernateTemplate()
		.findByNamedQueryAndNamedParam(hql, params, values);
		if (apexes != null && !apexes.isEmpty()) {
			validApex = true;
		}

		return validApex;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.OutgoingManifestMDBDAO#getManifestId(String, String, String, Integer)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Integer getManifestId(String mnfstNumber, String consgNum,
			String mnfstType, Integer mnfstTypeId) throws CGSystemException {
		Integer manifestId = 0;
		List<Integer> mnfstTypeIds = null;
		LOGGER.trace("LocalDBDataPersistDAOImpl : getManifestId() : START");
		String query = ManifestConstant.GET_MANIFEST_ID;
		String params[] = { ManifestConstant.MANIFEST_NUMBER,
				ManifestConstant.CONSG_NUMBER,
				ManifestConstant.MANIFEST_TYPE_ID,
				ManifestConstant.MANIFESTTYPE };
		Object values[] = { mnfstNumber, consgNum, mnfstTypeId, mnfstType };
		mnfstTypeIds = getHibernateTemplate()
		.findByNamedQueryAndNamedParam(query, params, values);
		if (mnfstTypeIds != null && mnfstTypeIds.size() > 0) {
			manifestId = mnfstTypeIds.get(0);
		}
		return manifestId;
	}

}
