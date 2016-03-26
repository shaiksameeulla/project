package com.ff.web.manifest.dao;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.BookingConsignmentDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.pickup.PickupRunsheetHeaderDO;
import com.ff.manifest.ManifestBaseTO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.OutManifestValidate;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.inmanifest.constants.InManifestConstants;

/**
 * @author hkansagr
 * 
 */
public class ManifestCommonDAOImpl extends CGBaseDAO implements
		ManifestCommonDAO {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ManifestCommonDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public ManifestDO getManifest(ManifestDO manifestDO)
			throws CGSystemException {
		LOGGER.debug("ManifestCommonDAOImpl :: getManifest() :: START");
		try {
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
			LOGGER.error("ERROR :: ManifestCommonDAOImpl :: getManifest() ::",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ManifestCommonDAOImpl :: getManifest() :: END");
		return manifestDO;
	}

	@Override
	public ManifestDO getManifestDtlsByFetchProfile(ManifestDO manifestDO,
			String fetchProfile) throws CGSystemException {
		LOGGER.debug("ManifestCommonDAOImpl :: getManifestDtlsByFetchProfile() :: START");
		Session session = null;
		try {
			session = createSession();
			session.enableFetchProfile(fetchProfile);
			manifestDO = (ManifestDO) session
					.createCriteria(ManifestDO.class)
					.add(Restrictions.eq(OutManifestConstants.MANIFEST_NO,
							manifestDO.getManifestNo().toUpperCase()))
					.add(Restrictions.eq(
							OutManifestConstants.PARAM_MANIFEST_PROCESS_CODE,
							manifestDO.getManifestProcessCode()))
					.add(Restrictions.eq(OutManifestConstants.MANIFEST_TYPE,
							manifestDO.getManifestType()))
					.add(Restrictions.eq(
							OutManifestConstants.PARAM_OPERATING_OFFICE,
							manifestDO.getOperatingOffice())).uniqueResult();
			if (manifestDO != null
					&& (fetchProfile
							.equals(ManifestConstants.FETCH_PROFILE_MANIFEST_EMBEDDED_IN) || fetchProfile
							.equals(ManifestConstants.FETCH_PROFILE_PARCEL_EMBEDDED_IN_MANIFEST))) {
				Hibernate.initialize(manifestDO.getEmbeddedManifestDOs());
			}
			if (manifestDO != null)
				Hibernate.initialize(manifestDO.getConsignments());
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: ManifestCommonDAOImpl :: getManifestDtlsByFetchProfile() ::",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ManifestCommonDAOImpl :: getManifestDtlsByFetchProfile() :: END");
		return manifestDO;
	}

	@Override
	public ManifestDO getManifestDtlsByFetchProfileForOutDOX(
			ManifestDO manifestDO, String fetchProfile)
			throws CGSystemException {
		LOGGER.debug("ManifestCommonDAOImpl :: getManifestDtlsByFetchProfileForOutDOX() :: START");
		Session session = null;
		try {
			session = createSession();
			session.enableFetchProfile(fetchProfile);
			manifestDO = (ManifestDO) session
					.createCriteria(ManifestDO.class)
					.add(Restrictions.eq(OutManifestConstants.MANIFEST_NO,
							manifestDO.getManifestNo()))
					.add(Restrictions.eq(
							OutManifestConstants.PARAM_MANIFEST_PROCESS_CODE,
							manifestDO.getManifestProcessCode()))
					.add(Restrictions.eq(OutManifestConstants.MANIFEST_TYPE,
							manifestDO.getManifestType()))
					.add(Restrictions.eq(
							OutManifestConstants.PARAM_OPERATING_OFFICE,
							manifestDO.getOperatingOffice())).uniqueResult();
			if (manifestDO != null) {
				Hibernate.initialize(manifestDO.getConsignments());
				Hibernate.initialize(manifestDO.getComails());
			}

		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: ManifestCommonDAOImpl :: getManifestDtlsByFetchProfileForOutDOX() ::",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ManifestCommonDAOImpl :: getManifestDtlsByFetchProfileForOutDOX() :: END");
		return manifestDO;
	}

	@Override
	public boolean saveOrUpdateManifest(ManifestDO manifestDO)
			throws CGSystemException {
		LOGGER.debug("ManifestCommonDAOImpl :: saveOrUpdateManifest() :: START");
		boolean result = Boolean.FALSE;
		try {
			getHibernateTemplate().merge(manifestDO);
			result = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: ManifestCommonDAOImpl :: saveOrUpdateManifest() ::",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ManifestCommonDAOImpl :: saveOrUpdateManifest() :: END");
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsignmentDO> getConsignments(List<String> consgNos)
			throws CGSystemException {
		LOGGER.debug("ManifestCommonDAOImpl :: getConsignments() :: START");
		List<ConsignmentDO> consgDOs = null;
		try {
			String params[] = { ManifestConstants.PARAM_CONSG_NO };
			Object[] values = new Object[] { consgNos };
			consgDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					ManifestConstants.QRY_GET_CONSIGNMENT_BY_CONSG_NO, params,
					values);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: ManifestCommonDAOImpl :: getConsignments() ::", e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ManifestCommonDAOImpl :: getConsignments() :: END");
		return consgDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ManifestDO> getManifests(List<String> manifestNos,
			Integer officeId) throws CGSystemException {
		LOGGER.debug("ManifestCommonDAOImpl :: getManifests() :: START");
		List<ManifestDO> manifestDOs = null;
		try {
			String params[] = { ManifestConstants.PARAM_MANIFEST_NOS,
					ManifestConstants.PARAM_OFFICE_ID,
					ManifestConstants.PARAM_MANIFEST_STATUS };
			Object[] values = new Object[] { manifestNos, officeId,
					OutManifestConstants.CLOSE };
			manifestDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					ManifestConstants.QRY_GET_MANIFESTS_BY_MANIFEST_NOS,
					params, values);
		} catch (Exception e) {
			LOGGER.error("ERROR :: ManifestCommonDAOImpl :: getManifests() ::",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ManifestCommonDAOImpl :: getManifests() :: END");
		return manifestDOs;
	}

	/*@Override
	public boolean saveOrUpdateManifestProcess(
			ManifestProcessDO manifestProcessDO) throws CGSystemException {
		LOGGER.debug("ManifestCommonDAOImpl :: saveOrUpdateManifestProcess() :: START");
		boolean result = Boolean.FALSE;
		try {
			getHibernateTemplate().merge(manifestProcessDO);
			result = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: ManifestCommonDAOImpl :: saveOrUpdateManifestProcess() ::",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ManifestCommonDAOImpl :: saveOrUpdateManifestProcess() :: END");
		return result;
	}*/

	@SuppressWarnings("unchecked")
	@Override
	public List<BookingConsignmentDO> getBookingConsignmentDO(
			List<String> consgNos) throws CGSystemException {
		LOGGER.debug("ManifestCommonDAOImpl :: getBookingConsignmentDO() :: START");
		List<BookingConsignmentDO> bookingConsignmentDOs = null;
		try {
			String params[] = { ManifestConstants.PARAM_CONSG_NOS };
			Object[] values = new Object[] { consgNos };
			bookingConsignmentDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							ManifestConstants.QRY_GET_BOOKING_CONSIGNMENT,
							params, values);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: ManifestCommonDAOImpl :: getBookingConsignmentDO() ::",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ManifestCommonDAOImpl :: getBookingConsignmentDO() :: END");
		return bookingConsignmentDOs;
	}

	@Override
	public ConsignmentDO getConsignment(String consgNo)
			throws CGSystemException {
		LOGGER.debug("ManifestCommonDAOImpl :: getConsignment() :: START");
		Session session = null;
		ConsignmentDO consignmentDO = null;
		try {
			session = createSession();
			consignmentDO = (ConsignmentDO) session
					.createCriteria(ConsignmentDO.class)
					.add(Restrictions.eq("consgNo", consgNo)).uniqueResult();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: ManifestCommonDAOImpl :: getConsignment() ::", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ManifestCommonDAOImpl :: getConsignment() :: END");
		return consignmentDO;
	}

	@Override
	public BookingDO getBookingConsignment(String consgNo)
			throws CGSystemException {
		LOGGER.debug("ManifestCommonDAOImpl :: getBookingConsignment() :: START");
		Session session = null;
		BookingDO bookingDO = null;
		try {
			session = createSession();
			bookingDO = (BookingDO) session.createCriteria(BookingDO.class)
					.add(Restrictions.eq("consgNumber", consgNo))
					.uniqueResult();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: ManifestCommonDAOImpl :: getBookingConsignment() ::",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ManifestCommonDAOImpl :: getBookingConsignment() :: END");
		return bookingDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ManifestDO> getMisrouteManifests(List<String> manifestNOList,
			Integer loginOfficeId) throws CGSystemException {
		LOGGER.debug("ManifestCommonDAOImpl :: getManifests() :: START");
		List<ManifestDO> manifestDOs = null;
		try {
			String params[] = { ManifestConstants.PARAM_MANIFEST_NOS,
					ManifestConstants.PARAM_OFFICE_ID,
					ManifestConstants.PARAM_MANIFEST_STATUS,
					ManifestConstants.PARAM_MANIFEST_DIRECTION };
			Object[] values = new Object[] { manifestNOList, loginOfficeId,
					OutManifestConstants.CLOSE,
					CommonConstants.MANIFEST_TYPE_IN };
			manifestDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							ManifestConstants.QRY_GET_MISROUTE_MANIFESTS_BY_MANIFEST_NOS,
							params, values);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: ManifestCommonDAOImpl :: getMisrouteManifests() ::",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ManifestCommonDAOImpl :: getMisrouteManifests() :: END");
		return manifestDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ManifestDO> getManifestForCreation(ManifestDO manifestDO)
			throws CGSystemException {
		List<ManifestDO> manifestDOs = null;
		LOGGER.debug("ManifestCommonDAOImpl :: getManifestForCreation() :: START");
		try {
			String[] params = { OutManifestConstants.MANIFEST_NO,
					OutManifestConstants.PARAM_OPERATING_OFFICE };
			Object[] values = { manifestDO.getManifestNo(),
					manifestDO.getOperatingOffice() };
			manifestDOs = (List<ManifestDO>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							OutManifestConstants.QRY_GET_UNIQUE_MANIFEST_FOR_CREATION,
							params, values);

		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: ManifestCommonDAOImpl :: getManifestForCreation() ::",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ManifestCommonDAOImpl :: getManifestForCreation() :: END");
		return manifestDOs;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ManifestDO> getManifestDtls(ManifestInputs manifestTO)
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

			manifestDO = (List<ManifestDO>) criteria.list();
		} catch (Exception e) {
			LOGGER.error("Error occured in ManifestCommonDAOImpl :: getManifestDtls()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}

		return manifestDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BookingDO> getBookings(List<String> consgNos)
			throws CGSystemException {
		LOGGER.debug("ManifestCommonDAOImpl :: getBookings() :: START");
		List<BookingDO> bookings = null;
		try {
			String params[] = { ManifestConstants.PARAM_CONSG_NOS };
			Object[] values = new Object[] { consgNos };
			bookings = getHibernateTemplate().findByNamedQueryAndNamedParam(
					ManifestConstants.QRY_GET_BOOKING_CONSIGNMENT, params,
					values);
			for (BookingDO booking : bookings) {
				getHibernateTemplate().evict(booking);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR :: ManifestCommonDAOImpl :: getBookings() ::",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ManifestCommonDAOImpl :: getBookings() :: END");
		return bookings;
	}

	@Override
	public void saveOrUpdateBookings(List<BookingDO> allBooking,
			List<ConsignmentDO> bookingConsignment) throws CGSystemException {
		LOGGER.debug("ManifestCommonDAOImpl :: saveOrUpdateBookings() :: START");
		Session session = null;
		try {
			session = openTransactionalSession();
			getHibernateTemplate().saveOrUpdateAll(allBooking);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: ManifestCommonDAOImpl :: saveOrUpdateBookings() ::",
					e);
			throw new CGSystemException(e);
		} finally {
			closeTransactionalSession(session);
		}
		LOGGER.debug("ManifestCommonDAOImpl :: saveOrUpdateBookings() :: END");
	}

	@SuppressWarnings("unchecked")
	@Override
	public ManifestDO isOutManifestExistByFetchProfile(ManifestDO manifestDO,
			String fetchProfile) throws CGSystemException {
		LOGGER.debug("ManifestCommonDAOImpl :: isOutManifestExistByFetchProfile() :: START");
		Session session = null;
		try {
			session = createSession();
			session.enableFetchProfile(fetchProfile);
			List<ManifestDO> manifestDOList = null;
			manifestDOList = session
					.createCriteria(ManifestDO.class)
					.add(Restrictions.eq(OutManifestConstants.MANIFEST_NO,
							manifestDO.getManifestNo()))
					.add(Restrictions.eq(
							OutManifestConstants.MANIFEST_DIRECTION,
							manifestDO.getManifestDirection()))
					.add(Restrictions.eq(
							OutManifestConstants.PARAM_OPERATING_OFFICE,
							manifestDO.getOperatingOffice())).list();
			if (!CGCollectionUtils.isEmpty(manifestDOList)){
				manifestDO = manifestDOList.get(0);
			}
			if (manifestDO != null
					&& (fetchProfile
							.equals(ManifestConstants.FETCH_PROFILE_MANIFEST_EMBEDDED_IN) || fetchProfile
							.equals(ManifestConstants.FETCH_PROFILE_PARCEL_EMBEDDED_IN_MANIFEST))) {
				Hibernate.initialize(manifestDO.getEmbeddedManifestDOs());
			}
			if (manifestDO != null)
				Hibernate.initialize(manifestDO.getConsignments());
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: ManifestCommonDAOImpl :: isOutManifestExistByFetchProfile() ::",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ManifestCommonDAOImpl :: isOutManifestExistByFetchProfile() :: END");
		return manifestDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsignmentBillingRateDO> searchConsgBillingRateDtls(
			List<String> pickupConsgNos) throws CGSystemException {
		LOGGER.debug("ManifestCommonDAOImpl :: searchConsgBillingRateDtls() :: START");
		List<ConsignmentBillingRateDO> consRateDOs = null;
		Session session = null;
		Criteria cr = null;
		try {
			session = createSession();
			cr = session.createCriteria(ConsignmentBillingRateDO.class,
					"consgRate");
			cr.add(Restrictions.eq("consgRate.rateCalculatedFor", "B"));
			cr.createAlias("consgRate.consignmentDO", "consg");
			cr.add(Restrictions.in("consg.consgNo", pickupConsgNos));
			consRateDOs = (List<ConsignmentBillingRateDO>) cr.list();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: ManifestCommonDAOImpl :: searchConsgBillingRateDtls() ::",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ManifestCommonDAOImpl :: searchConsgBillingRateDtls() :: END");
		return consRateDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsignmentDO> getConsignmentsAndEvictFromSession(
			List<String> consgNos) throws CGSystemException {
		LOGGER.debug("ManifestCommonDAOImpl :: getConsignments() :: START");
		List<ConsignmentDO> consgDOs = null;
		try {
			String params[] = { ManifestConstants.PARAM_CONSG_NO };
			Object[] values = new Object[] { consgNos };
			consgDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					ManifestConstants.QRY_GET_CONSIGNMENT_BY_CONSG_NO, params,
					values);
			for (ConsignmentDO cnObj : consgDOs) {
				getHibernateTemplate().evict(cnObj);
			}

		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: ManifestCommonDAOImpl :: getConsignments() ::", e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ManifestCommonDAOImpl :: getConsignments() :: END");
		return consgDOs;
	}

	@Override
	public boolean saveOrUpdateBookingCNs(List<BookingDO> allBooking)
			throws CGSystemException {
		LOGGER.debug("ManifestCommonDAOImpl :: saveOrUpdateBookingCNs() :: START");
		boolean result = Boolean.FALSE;
		try {
			getHibernateTemplate().saveOrUpdateAll(allBooking);
			result = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: ManifestCommonDAOImpl :: saveOrUpdateBookingCNs() ::",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ManifestCommonDAOImpl :: saveOrUpdateBookingCNs() :: END");
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ManifestDO getManifestDetailsWithFetchProfile(
			ManifestBaseTO manifestBaseTO) throws CGSystemException {
		LOGGER.trace("ManifestCommonDAOImpl::getManifestDetailsWithFetchProfile::START------------>:::::::");
		Session session = null;
		ManifestDO manifestDO = null;
		Criteria criteria = null;
		try {
			session = createSession();
			enableFetchProfileInSession(manifestBaseTO, session);

			criteria = session.createCriteria(ManifestDO.class);
			// criteria.createAlias("destOffice", "destOfficeDO");
			// criteria.createAlias("updatingProcess", "updatingProcessDO");

			if (!StringUtil.isEmptyInteger(manifestBaseTO.getManifestId())) {
				criteria.add(Restrictions.eq("manifestId",
						manifestBaseTO.getManifestId()));
			}
			if (StringUtils.isNotBlank(manifestBaseTO.getManifestNumber())) {
				criteria.add(Restrictions.eq("manifestNo",
						manifestBaseTO.getManifestNumber()));
			}
			if (!StringUtil
					.isEmptyInteger(manifestBaseTO.getLoggedInOfficeId())) {
				criteria.add(Restrictions.eq("operatingOffice",
						manifestBaseTO.getLoggedInOfficeId()));
			}
			/*
			 * if (!StringUtil.isEmptyInteger(manifestBaseTO
			 * .getDestinationOfficeId())) {
			 * criteria.add(Restrictions.eq("destOffice.officeId",
			 * manifestBaseTO.getDestinationOfficeId())); }
			 */
			if (StringUtils.isNotBlank(manifestBaseTO.getProcessCode())) {
				criteria.add(Restrictions.in(
						"manifestProcessCode",
						manifestBaseTO.getProcessCode().split(
								CommonConstants.COMMA)));
			}
			/*
			 * if
			 * (StringUtils.isNotBlank(manifestBaseTO.getUpdateProcessCode())) {
			 * criteria.add(Restrictions.in( "updatingProcessDO.processCode",
			 * manifestBaseTO.getUpdateProcessCode().split(
			 * CommonConstants.COMMA))); }
			 */
			if (StringUtils.isNotBlank(manifestBaseTO.getManifestType())) {
				if (manifestBaseTO.getIsExcludeManifestType()) {
					criteria.add(Restrictions.ne("manifestType",
							manifestBaseTO.getManifestType()));
				} else {
					criteria.add(Restrictions.eq("manifestType",
							manifestBaseTO.getManifestType()));
				}
			}
			criteria.addOrder(Order.desc("manifestDate"));
			criteria.setMaxResults(1);
			List<ManifestDO> manifestDOs = criteria.list();

			manifestDO = !StringUtil.isEmptyList(manifestDOs) ? manifestDOs
					.get(0) : null;
			initializeSetByHib(manifestBaseTO, manifestDO);

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::ManifestCommonDAOImpl::getManifestDetailsWithFetchProfile() :: "
					+ e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}

		LOGGER.debug("ManifestCommonDAOImpl :: getManifestDetailsWithFetchProfile() :: End --------> ::::::");
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
	@Override
	public boolean isValidateScanedManifestNo(OutManifestValidate cnValidateTO)
			throws CGSystemException {
		LOGGER.debug("ManifestCommonDAOImpl :: isValidateScanedManifestNo() :: START");
		boolean result = Boolean.TRUE;
		try {
			String[] params = { OutManifestConstants.MANIFEST_NO,
					OutManifestConstants.MANIFEST_DIRECTION };
			Object[] values = { cnValidateTO.getManifestNumber(),
					ManifestConstants.MANIFEST_DIRECTION_OUT };
			List<Long> counts = (List<Long>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							OutManifestConstants.QRY_IS_VALIDATE_SCANED_MANIFEST_NO,
							params, values);
			if (!CGCollectionUtils.isEmpty(counts)) {
				if (!StringUtil.isEmptyLong(counts.get(0))) {
					result = Boolean.FALSE;
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: ManifestCommonDAOImpl :: isValidateScanedManifestNo() ::",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ManifestCommonDAOImpl :: isValidateScanedManifestNo() :: END");
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PickupRunsheetHeaderDO getPickupRunsheetHeaderByConsignmentNo(
			String consgNo) throws CGSystemException {
		LOGGER.debug("ManifestCommonDAOImpl :: getPickupRunsheetHeaderByConsignmentNo() :: START");
		List<PickupRunsheetHeaderDO> pickupRunsheetHeaderDOs = null;
		PickupRunsheetHeaderDO pickupRunsheetHeaderDO = null;
		try {
			String params[] = { ManifestConstants.PARAM_CONSG_NO };
			Object[] values = new Object[] { consgNo };
			pickupRunsheetHeaderDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							ManifestConstants.QRY_GET_PICKUP_RUNSHEET_HEADER_BY_CONSIGNMENT,
							params, values);
			if (!StringUtil.isEmptyColletion(pickupRunsheetHeaderDOs)
					&& pickupRunsheetHeaderDOs.size() > 0) {
				pickupRunsheetHeaderDO = pickupRunsheetHeaderDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: ManifestCommonDAOImpl :: getPickupRunsheetHeaderByConsignmentNo() ::",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ManifestCommonDAOImpl :: getPickupRunsheetHeaderByConsignmentNo() :: END");
		return pickupRunsheetHeaderDO;
	}

	@Override
	public boolean saveOrUpdatePickupRunsheetHeaderDetails(
			Set<PickupRunsheetHeaderDO> pickupRunsheetHeader)
			throws CGSystemException {
		LOGGER.debug("ManifestCommonDAOImpl :: saveOrUpdatePickupRunsheetHeaderDetails() :: START");
		boolean result = Boolean.FALSE;
		try {
			getHibernateTemplate().saveOrUpdateAll(pickupRunsheetHeader);
			result = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: ManifestCommonDAOImpl :: saveOrUpdatePickupRunsheetHeaderDetails() ::",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ManifestCommonDAOImpl :: saveOrUpdatePickupRunsheetHeaderDetails() :: END");
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ManifestDO> getManifestDetails(ManifestInputs manifestInputs)
			throws CGSystemException {
		LOGGER.debug("ManifestCommonDAOImpl :: getManifestDetails() :: Start --------> ::::::");
		List<ManifestDO> manifestDOs = null;
		try {
			manifestDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					"getManifestForOut",
					new String[] { "manifestNo", "operatingOffice", },
					new Object[] { manifestInputs.getManifestNumber(),
							manifestInputs.getLoginOfficeId() });

		} catch (Exception e) {
			LOGGER.error(
					"Exception Occured in::ManifestCommonDAOImpl::getManifestDetails() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ManifestCommonDAOImpl :: getManifestDetails() :: End --------> ::::::");
		return manifestDOs;
	}

}
