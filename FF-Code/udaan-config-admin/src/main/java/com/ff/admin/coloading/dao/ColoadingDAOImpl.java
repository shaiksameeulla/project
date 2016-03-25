package com.ff.admin.coloading.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.ff.admin.coloading.constants.ColoadingConstants;
import com.ff.admin.mec.common.constants.MECCommonConstants;
import com.ff.coloading.AirColoadingTO;
import com.ff.coloading.CdAwbModificationDetailsTO;
import com.ff.coloading.CdAwbModificationTO;
import com.ff.coloading.ColoadingVehicleContractTO;
import com.ff.coloading.ColoadingVendorTO;
import com.ff.coloading.FuelRateEntryTO;
import com.ff.coloading.TrainColoadingTO;
import com.ff.domain.business.LoadMovementVendorDO;
import com.ff.domain.coloading.ColoadingAirAwbDO;
import com.ff.domain.coloading.ColoadingAirCdDO;
import com.ff.domain.coloading.ColoadingAirDO;
import com.ff.domain.coloading.ColoadingFuelRateEntryDO;
import com.ff.domain.coloading.ColoadingSurfaceRateEntryDO;
import com.ff.domain.coloading.ColoadingTrainContractDO;
import com.ff.domain.coloading.ColoadingTrainContractRateDetailsDO;
import com.ff.domain.coloading.ColoadingVehicleContractDO;
import com.ff.domain.coloading.ColoadingVehicleServiceEntryDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;
import com.ff.domain.transport.FlightDO;
import com.ff.domain.transport.VehicleDO;
import com.ff.organization.OfficeTO;
import com.ff.transport.FlightTO;

public class ColoadingDAOImpl extends CGBaseDAO implements ColoadingDAO {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ColoadingDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<StockStandardTypeDO> getStockStdType(String typeName)
			throws CGSystemException {
		LOGGER.debug("ColoadingDAOImpl::getStockStdType()::START");
		List<StockStandardTypeDO> typesList = null;
		try {
			String params[] = { MECCommonConstants.PARAM_TYPE_NAME };
			Object[] values = new Object[] { typeName };
			typesList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					ColoadingConstants.STD_TYPE_QUERY, params, values);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ColoadingDAOImpl::getStockStdType()::",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ColoadingDAOImpl :: getStockStdType() :: END");
		return typesList;
	}
	
	@Override
	public List<FlightDO> getFlightList(AirColoadingTO coloadingTo) {
		String params[] = { ColoadingConstants.ORIGIN_CITY_ID, ColoadingConstants.DESTINATION_CITY_ID};
		
		Object[] values = new Object[] {coloadingTo.getOrigionCityID(), coloadingTo.getDestinationCityID()};
		
		List<FlightDO> flightList = (List<FlightDO>)getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						ColoadingConstants.GET_FLIGHT_LIST,
						params, values);
		
		return flightList;
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ColoadingVendorTO> getVendorsList(Integer regionId, String serviceType) throws CGBusinessException, CGSystemException {
		LOGGER.debug("ColoadingDAOImpl::getVendorsList()::START");
		
		String params[] = {ColoadingConstants.TRANSPORT_MODE};
		
		int transportMode = 0;
		if(serviceType.equalsIgnoreCase(ColoadingConstants.AIR)) {
			transportMode = 1;
		} else if(serviceType.equalsIgnoreCase(ColoadingConstants.TRAIN)) {
			transportMode = 2;
		} else if(serviceType.equalsIgnoreCase(ColoadingConstants.ROAD)){
			transportMode = 3;
		}
		
		Object[] values = new Object[] {transportMode};
		
		List<LoadMovementVendorDO> loadMovementVendorDOList = (List<LoadMovementVendorDO>)getHibernateTemplate()
				.findByNamedQueryAndNamedParam(ColoadingConstants.GET_VENDOR_LIST_BY_TRANSPORT_MODE, params, values);
		
		List<ColoadingVendorTO> coloadingVendorTOs = null;
		try {
			if (!CGCollectionUtils.isEmpty(loadMovementVendorDOList)) {
				coloadingVendorTOs = new ArrayList<>(loadMovementVendorDOList.size());
				ColoadingVendorTO to = null;
				for (LoadMovementVendorDO movementVendorDO : loadMovementVendorDOList) {
					to = new ColoadingVendorTO();
					to.setVendorId(movementVendorDO.getVendorId());
					to.setVendorCode(movementVendorDO.getVendorCode());
					to.setBusinessName(movementVendorDO.getBusinessName());
					coloadingVendorTOs.add(to);
				}
			} else {
				throw new CGBusinessException(ColoadingConstants.CL0001);
			}
			loadMovementVendorDOList = null;

		} catch (CGBusinessException e) {
			LOGGER.error("ERROR : ColoadingDAOImpl::getVendorsList()", e);
			throw e;
		} catch (Exception e) {
			LOGGER.error("ERROR : ColoadingDAOImpl::getVendorsList()", e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ColoadingDAOImpl::getVendorsList()::END");
		return coloadingVendorTOs;
	}

	@SuppressWarnings("unchecked")
	public ColoadingTrainContractDO getTrainData(TrainColoadingTO coloadingTo)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("ColoadingDAOImpl::getTrainList()::START");
		ColoadingTrainContractDO coloadingTrainContractDO = null;
		Session session = null;
		try {
			session = createSession();
			if (session != null) {
				Criteria criteria = session
						.createCriteria(ColoadingTrainContractDO.class);
				criteria.add(Restrictions.eq("originCityId",
						coloadingTo.getOrigionCityID()));
				criteria.add(Restrictions.eq("destCityId",
						coloadingTo.getDestinationCityID()));
				criteria.add(Restrictions.eq("vendorId",
						coloadingTo.getVendorId()));
				criteria.add(Restrictions.eq("effectiveFrom", DateUtil
						.slashDelimitedstringToDDMMYYYYFormat(coloadingTo
								.getEffectiveFrom())));
				criteria.add(Restrictions.ne("storeStatus",
						ColoadingConstants.INACTIVE));
				coloadingTrainContractDO = (ColoadingTrainContractDO) criteria
						.uniqueResult();

				if (coloadingTrainContractDO == null) {
					criteria = session
							.createCriteria(ColoadingTrainContractDO.class);
					criteria.add(Restrictions.eq("originCityId",
							coloadingTo.getOrigionCityID()));
					criteria.add(Restrictions.eq("destCityId",
							coloadingTo.getDestinationCityID()));
					criteria.add(Restrictions.eq("vendorId",
							coloadingTo.getVendorId()));
					criteria.add(Restrictions.ne("storeStatus",
							ColoadingConstants.INACTIVE));
					criteria.add(Restrictions.isNull("effectiveTill"));
					coloadingTrainContractDO = (ColoadingTrainContractDO) criteria
							.uniqueResult();
					if (coloadingTrainContractDO == null) {
						List<String> trainList = getTrainList(coloadingTo);
						if (!CGCollectionUtils.isEmpty(trainList)) {
							coloadingTrainContractDO = new ColoadingTrainContractDO();
							Set<ColoadingTrainContractRateDetailsDO> detailsDOs = new HashSet<>(
									trainList.size());
							ColoadingTrainContractRateDetailsDO detailsDO = null;
							for (String trainNumber : trainList) {
								detailsDO = new ColoadingTrainContractRateDetailsDO();
								detailsDO.setTrainNo(trainNumber);
								detailsDOs.add(detailsDO);
							}
							coloadingTrainContractDO
									.setColoadingTrainContractRateDtls(detailsDOs);
						}
					}else{
						if(ColoadingConstants.RENEW_R.equals(coloadingTo.getRenewFlag()) || ColoadingConstants.SAVE_STRING_T.equals(coloadingTrainContractDO.getStoreStatus())){
							List<String> trainList = getTrainList(coloadingTo);
							if (!CGCollectionUtils.isEmpty(trainList)) {
								Set<ColoadingTrainContractRateDetailsDO> detailsDOs = new HashSet<>(
										trainList.size());
								ColoadingTrainContractRateDetailsDO detailsDO = null;
								for (String trainNumber : trainList) {
									detailsDO = new ColoadingTrainContractRateDetailsDO();
									detailsDO.setTrainNo(trainNumber);
									detailsDOs.add(detailsDO);
								}
								coloadingTrainContractDO.getColoadingTrainContractRateDtls().addAll(detailsDOs);
								detailsDOs = null;
							}
						}
					}
				}else{
					if(ColoadingConstants.RENEW_R.equals(coloadingTo.getRenewFlag()) || ColoadingConstants.SAVE_CHAR == coloadingTrainContractDO.getStoreStatus()){
						List<String> trainList = getTrainList(coloadingTo);
						if (!CGCollectionUtils.isEmpty(trainList)) {
							Set<ColoadingTrainContractRateDetailsDO> detailsDOs = new HashSet<>(
									trainList.size());
							ColoadingTrainContractRateDetailsDO detailsDO = null;
							for (String trainNumber : trainList) {
								detailsDO = new ColoadingTrainContractRateDetailsDO();
								detailsDO.setTrainNo(trainNumber);
								detailsDOs.add(detailsDO);
							}
							coloadingTrainContractDO.getColoadingTrainContractRateDtls().addAll(detailsDOs);
							detailsDOs = null;
						}
					}
				}
				if (coloadingTrainContractDO != null && coloadingTrainContractDO
						.getEffectiveFrom() != null) {
					Date seletedDate = DateUtil
							.slashDelimitedstringToDDMMYYYYFormat(coloadingTo
									.getEffectiveFrom());
					if (seletedDate.before(coloadingTrainContractDO
							.getEffectiveFrom())) {
						Date previusDate = DateUtil
								.getPreviousDateFromGivenDate(coloadingTrainContractDO
										.getEffectiveFrom());
						criteria = session
								.createCriteria(ColoadingTrainContractDO.class);
						criteria.add(Restrictions.eq("originCityId",
								coloadingTo.getOrigionCityID()));
						criteria.add(Restrictions.eq("destCityId",
								coloadingTo.getDestinationCityID()));
						criteria.add(Restrictions.eq("vendorId",
								coloadingTo.getVendorId()));
						criteria.add(Restrictions.eq("storeStatus",
								ColoadingConstants.RENEW));
						criteria.add(Restrictions.eq("effectiveTill",
								previusDate));
						coloadingTrainContractDO = (ColoadingTrainContractDO) criteria
								.uniqueResult();
						if(coloadingTrainContractDO != null){
							coloadingTrainContractDO.setStoreStatus('C');
						}
						
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ColoadingDAOImpl::getTrainList()", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ColoadingDAOImpl :: getTrainList() :: END");
		return coloadingTrainContractDO;
	}

	private List<String> getTrainList(TrainColoadingTO coloadingTo) {
		String params[] = { ColoadingConstants.ORIGIN_CITY_ID,
				ColoadingConstants.DESTINATION_CITY_ID };
		Object[] values = new Object[] {
				coloadingTo.getOrigionCityID(),
				coloadingTo.getDestinationCityID() };

		@SuppressWarnings("unchecked")
		List<String> trainList = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						ColoadingConstants.GET_TRAIN_LIST,
						params, values);
		return trainList;
	}

	@Override
	public ColoadingAirDO getAirData(AirColoadingTO airColoadingTo)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("ColoadingDAOImpl::getAirData()::START");
		ColoadingAirDO airDO = null;
		Session session = null;
		try {
			session = createSession();
			if (session != null) {
				Criteria criteria = session
						.createCriteria(ColoadingAirDO.class);
				criteria.add(Restrictions.eq("vendorId",
						airColoadingTo.getVendorId()));
				criteria.add(Restrictions.eq("effectiveFrom", DateUtil
						.slashDelimitedstringToDDMMYYYYFormat(airColoadingTo
								.getEffectiveFrom())));
				criteria.add(Restrictions.eq("cdType",
						airColoadingTo.getCdType()));
				criteria.add(Restrictions.ne("storeStatus",
						ColoadingConstants.INACTIVE));
				criteria.add(Restrictions.eq("originCityId",
						airColoadingTo.getOrigionCityID()));
				criteria.add(Restrictions.eq("destinationCityId",
						airColoadingTo.getDestinationCityID()));
				airDO = (ColoadingAirDO) criteria.uniqueResult();
				if (airDO == null) {
					criteria = session.createCriteria(ColoadingAirDO.class);
					criteria.add(Restrictions.eq("vendorId",
							airColoadingTo.getVendorId()));
					criteria.add(Restrictions.eq("cdType",
							airColoadingTo.getCdType()));
					criteria.add(Restrictions.ne("storeStatus",
							ColoadingConstants.INACTIVE));
					criteria.add(Restrictions.isNull("effectiveTill"));
					criteria.add(Restrictions.eq("originCityId",
							airColoadingTo.getOrigionCityID()));
					criteria.add(Restrictions.eq("destinationCityId",
							airColoadingTo.getDestinationCityID()));
					airDO = (ColoadingAirDO) criteria.uniqueResult();
					if (airDO != null) {
						Date seletedDate = DateUtil
								.slashDelimitedstringToDDMMYYYYFormat(airColoadingTo
										.getEffectiveFrom());
						if (seletedDate.before(airDO
								.getEffectiveFrom())) {
							Date previusDate = DateUtil
									.getPreviousDateFromGivenDate(airDO
											.getEffectiveFrom());
							criteria = session.createCriteria(ColoadingAirDO.class);
							criteria.add(Restrictions.eq("vendorId",
									airColoadingTo.getVendorId()));
							criteria.add(Restrictions.eq("cdType",
									airColoadingTo.getCdType()));
							criteria.add(Restrictions.ne("storeStatus",
									ColoadingConstants.INACTIVE));
							criteria.add(Restrictions.eq("effectiveTill",
									previusDate));
							criteria.add(Restrictions.eq("originCityId",
									airColoadingTo.getOrigionCityID()));
							criteria.add(Restrictions.eq("destinationCityId",
									airColoadingTo.getDestinationCityID()));
							ColoadingAirDO currentAirDo = (ColoadingAirDO) criteria
									.uniqueResult();
							if (currentAirDo != null) {
								airDO = currentAirDo;
							}
						}
						
					}
				}

			}
		} catch (Exception e) {
			LOGGER.error("Exception occurs in getAirData::saveColoading()::", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ColoadingDAOImpl :: getAirData() :: END");
		return airDO;
	}

	public ColoadingTrainContractDO getTrainFutureData(
			TrainColoadingTO coloadingTo) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("ColoadingDAOImpl::getTrainFutureData()::START");
		ColoadingTrainContractDO coloadingTrainContractDO = null;
		Session session = null;
		try {
			session = createSession();
			if (session != null) {
				Criteria criteria = session
						.createCriteria(ColoadingTrainContractDO.class);
				criteria.add(Restrictions.eq("originCityId",
						coloadingTo.getOrigionCityID()));
				criteria.add(Restrictions.eq("destCityId",
						coloadingTo.getDestinationCityID()));
				criteria.add(Restrictions.eq("vendorId",
						coloadingTo.getVendorId()));
				criteria.add(Restrictions.isNull("effectiveTill"));
				criteria.add(Restrictions.ne("storeStatus",
						ColoadingConstants.INACTIVE));
				coloadingTrainContractDO = (ColoadingTrainContractDO) criteria
						.uniqueResult();

			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ColoadingDAOImpl :: getTrainFutureData()",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ColoadingDAOImpl :: getTrainFutureData() :: END");
		return coloadingTrainContractDO;
	}

	public ColoadingVehicleContractDO searchVehicleFutureData(
			ColoadingVehicleContractTO vehicleContractTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("ColoadingDAOImpl::searchVehicleFutureData()::START");
		ColoadingVehicleContractDO vehicleContractDO = null;
		Session session = null;
		try {
			session = createSession();
			if (session != null) {
				Criteria criteria = session
						.createCriteria(ColoadingVehicleContractDO.class);
				criteria.add(Restrictions.eq("effectiveFrom", DateUtil
						.slashDelimitedstringToDDMMYYYYFormat(vehicleContractTO
								.getEffectiveFrom())));
				criteria.add(Restrictions.eq("vehicleNo",
						vehicleContractTO.getVehicleNo()));
				/*criteria.add(Restrictions.eq("vendorId",
						vehicleContractTO.getVendorId()));*/
				criteria.add(Restrictions.ne("storeStatus",
						ColoadingConstants.INACTIVE));
				criteria.add(Restrictions.isNull("effectiveTill"));
				vehicleContractDO = (ColoadingVehicleContractDO) criteria
						.uniqueResult();
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ColoadingDAOImpl::searchVehicleFutureData()::",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ColoadingDAOImpl :: searchVehicleFutureData() :: END");
		return vehicleContractDO;
	}

	@Override
	public ColoadingAirDO getAirFutureData(AirColoadingTO airColoadingTo)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("ColoadingDAOImpl::getAirFutureData()::START");
		ColoadingAirDO airDO = null;
		Session session = null;
		try {
			session = createSession();
			if (session != null) {
				Criteria criteria = session
						.createCriteria(ColoadingAirDO.class);
				criteria.add(Restrictions.eq("vendorId",
						airColoadingTo.getVendorId()));
				criteria.add(Restrictions.eq("cdType",
						airColoadingTo.getCdType()));
				criteria.add(Restrictions.eq("originCityId",
						airColoadingTo.getOrigionCityID()));
				criteria.add(Restrictions.eq("destinationCityId",
						airColoadingTo.getDestinationCityID()));
				criteria.add(Restrictions.isNull("effectiveTill"));
				airDO = (ColoadingAirDO) criteria.uniqueResult();
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ColoadingDAOImpl :: getAirFutureData()",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ColoadingDAOImpl :: getAirFutureData() :: END");
		return airDO;
	}

	@Override
	public ColoadingAirDO saveColoadingAir(ColoadingAirDO airDO, int userId, String renew) throws CGBusinessException, CGSystemException {
		LOGGER.debug("ColoadingDAOImpl::saveColoadingAir()::START");
		Session session = null;
		try {
			session = openTransactionalSession();
			if (session != null) {
				if (renew != null && ColoadingConstants.RENEW_R.equals(renew)) {
					Criteria criteria = session.createCriteria(ColoadingAirDO.class);
					criteria.add(Restrictions.isNull("effectiveTill"));
					criteria.add(Restrictions.eq("vendorId", airDO.getVendorId()));
					criteria.add(Restrictions.eq("originCityId", airDO.getOriginCityId()));
					criteria.add(Restrictions.eq("destinationCityId", airDO.getDestinationCityId()));
					criteria.add(Restrictions.eq("cdType", airDO.getCdType()));
					criteria.add(Restrictions.or(Restrictions.eq("storeStatus", ColoadingConstants.SAVE_CHAR), Restrictions.eq("storeStatus", ColoadingConstants.SUBMIT_CHAR)));
					ColoadingAirDO coloadingAirDO = (ColoadingAirDO) criteria.uniqueResult();
					if (coloadingAirDO != null) {
						if (coloadingAirDO.getEffectiveFrom().equals(airDO.getEffectiveFrom())) {
							coloadingAirDO.setStoreStatus(ColoadingConstants.INACTIVE);
							coloadingAirDO.setEffectiveTill(coloadingAirDO.getEffectiveFrom());
						} else {
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(airDO.getEffectiveFrom());
							calendar.add(Calendar.DATE, -1);
							coloadingAirDO.setEffectiveTill(calendar.getTime());
							coloadingAirDO.setStoreStatus(ColoadingConstants.RENEW_R.charAt(0));
						}
						//session.saveOrUpdate(coloadingAirDO);
						session.update(coloadingAirDO);
					}
					
					if(airDO.getStoreStatus() == (ColoadingConstants.SAVE_CHAR)) {						
						airDO.setId(null);
					}
				}

				airDO.setCreatedBy(userId);
				airDO.setUpdatedBy(userId);
				airDO.setUpdatedDate(Calendar.getInstance().getTime());
				airDO.setCreatedDate(Calendar.getInstance().getTime());
				
				if (ColoadingConstants.CD.equals(airDO.getCdType())) {
					Set<ColoadingAirCdDO> airCdDOs = airDO.getColoadingAirCds();
					if (!CGCollectionUtils.isEmpty(airCdDOs)) {
						for (ColoadingAirCdDO airCdDO : airCdDOs) {
							airCdDO.setCreatedBy(userId);
							airCdDO.setUpdatedBy(userId);				
							airCdDO.setUpdatedDate(Calendar.getInstance().getTime());
							airCdDO.setCreatedDate(Calendar.getInstance().getTime());
						}
					}					
				} else if (ColoadingConstants.AWB.equals(airDO.getCdType())) {
					Set<ColoadingAirAwbDO> airAwbDOs = airDO.getColoadingAirAwbs();
					if (!CGCollectionUtils.isEmpty(airAwbDOs)) {
						for (ColoadingAirAwbDO airAwbDO : airAwbDOs) {
							airAwbDO.setCreatedBy(userId);
							airAwbDO.setUpdatedBy(userId);
							airAwbDO.setUpdatedDate(Calendar.getInstance().getTime());
							airAwbDO.setCreatedDate(Calendar.getInstance().getTime());
						}
					}
				}
				
				session.merge(airDO);
				
				return airDO;
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ColoadingDAOImpl::saveColoadingAir()",
					e);
			throw new CGSystemException(e);
		} finally {
//			closeSession(session);
			closeTransactionalSession(session);
		}
		LOGGER.debug("ColoadingDAOImpl :: saveColoadingAir() :: END");
		return airDO;
	}

	@Override
	public ColoadingTrainContractDO saveColoadingTrain(
			ColoadingTrainContractDO trainContractDO, int userId, String renew)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("ColoadingDAOImpl::saveColoadingTrain()::START");
		Session session = null;
		try {
			session = createSession();
			if (session != null) {
				if (renew != null
						&& ColoadingConstants.RENEW_R.equals(renew)
						&& ColoadingConstants.SAVE_STRING_T.equals(String
								.valueOf(trainContractDO.getStoreStatus()))) {
					Criteria criteria = session
							.createCriteria(ColoadingTrainContractDO.class);
					criteria.add(Restrictions.isNull("effectiveTill"));
					criteria.add(Restrictions.eq("vendorId",
							trainContractDO.getVendorId()));
					criteria.add(Restrictions.eq("storeStatus",
							ColoadingConstants.SUBMIT_CHAR));
					criteria.add(Restrictions.eq("originCityId",
							trainContractDO.getOriginCityId()));
					criteria.add(Restrictions.eq("destCityId",
							trainContractDO.getDestCityId()));
					ColoadingTrainContractDO coloadingTrainContractDO = (ColoadingTrainContractDO) criteria
							.uniqueResult();
					if (coloadingTrainContractDO != null) {
						if (coloadingTrainContractDO.getEffectiveFrom().equals(
								trainContractDO.getEffectiveFrom())) {
							coloadingTrainContractDO
									.setStoreStatus(ColoadingConstants.INACTIVE);
							coloadingTrainContractDO
									.setEffectiveTill(coloadingTrainContractDO
											.getEffectiveFrom());
						} else {
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(trainContractDO.getEffectiveFrom());
							calendar.add(Calendar.DATE, -1);
							coloadingTrainContractDO.setEffectiveTill(calendar
									.getTime());
							coloadingTrainContractDO
									.setStoreStatus(ColoadingConstants.RENEW_R
											.charAt(0));
						}
						session.update(coloadingTrainContractDO);
						session.flush();
						trainContractDO.setId(null);
					}
					
				}
				if (trainContractDO.getId() != null) {
					trainContractDO.setUpdatedBy(userId);
					trainContractDO.setUpdatedDate(Calendar.getInstance()
							.getTime());
					session.update(trainContractDO);
					session.flush();
				} else {
					trainContractDO.setCreatedBy(userId);
					trainContractDO.setUpdatedBy(userId);
					trainContractDO.setUpdatedDate(Calendar.getInstance()
							.getTime());
					trainContractDO.setCreatedDate(Calendar.getInstance()
							.getTime());
					session.save(trainContractDO);
				}

				Set<ColoadingTrainContractRateDetailsDO> detailsDOs = trainContractDO
						.getColoadingTrainContractRateDtls();
				if (!CGCollectionUtils.isEmpty(detailsDOs)) {
					for (ColoadingTrainContractRateDetailsDO detailsDO : detailsDOs) {
						if (ColoadingConstants.RENEW_R.equals(renew)
								&& "T".equals(String.valueOf(trainContractDO
										.getStoreStatus()))) {
							detailsDO.setId(null);
						}
						if (detailsDO.getId() != null) {
							detailsDO.setUpdatedBy(userId);
							detailsDO.setUpdatedDate(Calendar.getInstance()
									.getTime());
							session.update(detailsDO);
							session.flush();
						} else {
							detailsDO.setCreatedBy(userId);
							detailsDO.setUpdatedBy(userId);
							detailsDO.setUpdatedDate(Calendar.getInstance()
									.getTime());
							detailsDO.setCreatedDate(Calendar.getInstance()
									.getTime());
							session.save(detailsDO);
						}
					}
				}
				return trainContractDO;
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ColoadingDAOImpl::saveColoadingTrain()::",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ColoadingDAOImpl :: saveColoadingTrain() :: END");
		return trainContractDO;
	}

	@Override
	public ColoadingVehicleContractDO saveColoadingVehicle(
			ColoadingVehicleContractDO vehicleContractDO, int userId,
			String renew, OfficeTO officeTo) throws CGBusinessException, CGSystemException {
		LOGGER.debug("ColoadingDAOImpl::saveColoadingVehicle()::START");
		Session session = null;
		try {
			session = createSession();
			if (session != null) {
				Criteria criteria = session
						.createCriteria(VehicleDO.class);
				
				criteria.createCriteria("regionalOfficeDO", "ro");
				criteria.add(Restrictions.eq("ro.officeId", officeTo.getOfficeId()));
				
				criteria.add(Restrictions.eq("regNumber",
						vehicleContractDO.getVehicleNo()));
				
				VehicleDO vehicleDO = (VehicleDO)criteria.uniqueResult();
				if(vehicleDO != null){
					// Upadate GPSEnabled column in ff_d_vehicle table
					vehicleDO.setIsGpsEnabled(vehicleContractDO.getGpsEnabled());
					vehicleDO.setUpdatedBy(userId);
					vehicleDO.setUpdatedDate(Calendar.getInstance().getTime());					
					
				/*if (renew != null && ColoadingConstants.RENEW_R.equals(renew)) {*/
					criteria = session
							.createCriteria(ColoadingVehicleContractDO.class);
					/*criteria.add(Restrictions.isNull("effectiveTill"));
					criteria.add(Restrictions.eq("vendorId",
							vehicleContractDO.getVendorId()));*/
					criteria.add(Restrictions.eq("vehicleNo",
							vehicleContractDO.getVehicleNo()));
					criteria.add(Restrictions.isNull("effectiveTill"));
					ColoadingVehicleContractDO contractDO = (ColoadingVehicleContractDO) criteria
							.uniqueResult();
					if (contractDO != null) {

						if(contractDO.getVendorId() != null && contractDO.getVendorId().equals(vehicleContractDO.getVendorId())){
							Date currentDate = DateUtil.getCurrentDate();
							if (contractDO.getEffectiveFrom().after(currentDate)) {
								contractDO.setStoreStatus('F');
								vehicleContractDO = contractDO;
								return vehicleContractDO;
							}
						}
						
						/*if(contractDO.getVendorId() == vehicleContractDO.getVendorId()){
						*/	if (contractDO.getEffectiveFrom().equals(
									vehicleContractDO.getEffectiveFrom())) {
								contractDO
										.setStoreStatus(ColoadingConstants.INACTIVE);
								contractDO.setEffectiveTill(contractDO
										.getEffectiveFrom());
							} else {
								Calendar calendar = Calendar.getInstance();
								calendar.setTime(vehicleContractDO
										.getEffectiveFrom());
								calendar.add(Calendar.DATE, -1);
								contractDO.setEffectiveTill(calendar.getTime());
								contractDO
										.setStoreStatus(ColoadingConstants.RENEW);
							}
						/*}else{
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(vehicleContractDO
									.getEffectiveFrom());
							calendar.add(Calendar.DATE, -1);
							contractDO.setEffectiveTill(calendar.getTime());
							contractDO
									.setStoreStatus(ColoadingConstants.INACTIVE);
						}*/
						session.update(contractDO);
						
						session.update(vehicleDO);
						session.flush();
					}

					
				vehicleContractDO.setCreatedBy(userId);
				vehicleContractDO.setUpdatedBy(userId);
				vehicleContractDO.setUpdatedDate(Calendar.getInstance()
						.getTime());
				vehicleContractDO.setCreatedDate(Calendar.getInstance()
						.getTime());
				session.save(vehicleContractDO);
				session.flush();
				return vehicleContractDO;
			}
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ColoadingDAOImpl::saveColoadingVehicle()",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ColoadingDAOImpl :: saveColoadingVehicle() :: END");
		return null;
	}

	@Override
	public List<CityDO> getCityDOList() throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("ColoadingDAOImpl::getCityDOList()::START");
		List<CityDO> cityDOs = null;
		Session session = null;
		try {
			session = createSession();
			if (session != null) {
				Criteria criteria = session.createCriteria(CityDO.class);
				criteria.addOrder(Order.asc("cityName"));
				criteria.setProjection(
						Projections
								.projectionList()
								.add(Projections.property("cityId"), "cityId")
								.add(Projections.property("cityName"),
										"cityName")).setResultTransformer(
						Transformers.aliasToBean(CityDO.class));
				cityDOs = criteria.list();

			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ColoadingDAOImpl::getCityDOList()::",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ColoadingDAOImpl :: getCityDOList() :: END");
		return cityDOs;
	}

	@Override
	public ColoadingFuelRateEntryDO saveFuelRateEntry(
			ColoadingFuelRateEntryDO fuelRateEntryDO, int userId, String renew)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("ColoadingDAOImpl::saveFuelRateEntry()::START");
		Session session = null;
		try {
			session = createSession();
			if (session != null) {
				if (renew != null && ColoadingConstants.RENEW_R.equals(renew)) {
					Criteria criteria = session
							.createCriteria(ColoadingFuelRateEntryDO.class);
					criteria.add(Restrictions.isNull("effectiveTill"));
					criteria.add(Restrictions.eq("cityId",
							fuelRateEntryDO.getCityId()));
					ColoadingFuelRateEntryDO entryDO = (ColoadingFuelRateEntryDO) criteria
							.uniqueResult();
					if (entryDO != null) {
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(fuelRateEntryDO.getEffectiveFrom());
						calendar.add(Calendar.DATE, -1);
						entryDO.setEffectiveTill(calendar.getTime());
						entryDO.setStoreStatus(ColoadingConstants.RENEW_R
								.charAt(0));
						session.update(entryDO);
						session.flush();
					}
				}

				fuelRateEntryDO.setCreatedBy(userId);
				fuelRateEntryDO.setUpdatedBy(userId);
				fuelRateEntryDO
						.setUpdatedDate(Calendar.getInstance().getTime());
				fuelRateEntryDO
						.setCreatedDate(Calendar.getInstance().getTime());
				session.save(fuelRateEntryDO);
				session.flush();
				return fuelRateEntryDO;
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ColoadingDAOImpl::saveFuelRateEntry()::",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ColoadingDAOImpl :: saveFuelRateEntry() :: END");
		return null;
	}

	@Override
	public ColoadingVehicleServiceEntryDO saveVehicleServiceEntry(
			ColoadingVehicleServiceEntryDO vehicleServiceEntryDO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("ColoadingDAOImpl::saveVehicleServiceEntry()::START");
		Session session = null;
		try {
			session = createSession();
			if (session != null) {
				vehicleServiceEntryDO.setUpdatedDate(Calendar.getInstance()
						.getTime());
				vehicleServiceEntryDO.setCreatedDate(Calendar.getInstance()
						.getTime());
				session.save(vehicleServiceEntryDO);
				session.flush();
				return vehicleServiceEntryDO;
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ColoadingDAOImpl::saveVehicleServiceEntry()::",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ColoadingDAOImpl :: saveVehicleServiceEntry() :: END");
		return null;
	}

	@Override
	public ColoadingSurfaceRateEntryDO saveSurfaceRateEntry(
			ColoadingSurfaceRateEntryDO surfaceRateEntryDO, int userId)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("ColoadingDAOImpl::saveSurfaceRateEntry()::START");
		Session session = null;
		try {
			session = createSession();
			if (session != null) {
				Criteria criteria = session.createCriteria(ColoadingSurfaceRateEntryDO.class).setProjection(Projections.property("id"));
				criteria.add(Restrictions.eq("vendorId", surfaceRateEntryDO.getVendorId()));
				Integer id = (Integer)criteria.uniqueResult();
				if(id != null) {
					surfaceRateEntryDO.setId(id);
					id = null;
				}
				surfaceRateEntryDO.setCreatedBy(userId);
				surfaceRateEntryDO.setUpdatedBy(userId);
				surfaceRateEntryDO.setUpdatedDate(Calendar.getInstance()
						.getTime());
				surfaceRateEntryDO.setCreatedDate(Calendar.getInstance()
						.getTime());
				session.saveOrUpdate(surfaceRateEntryDO);
				session.flush();
				
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ColoadingDAOImpl::saveSurfaceRateEntry()::",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ColoadingDAOImpl :: saveSurfaceRateEntry() :: END");
		return surfaceRateEntryDO;
	}

	@Override
	public ColoadingFuelRateEntryDO loadFuelRateEntryData(
			FuelRateEntryTO fuelRateEntryTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("ColoadingDAOImpl::loadFuelRateEntryData()::START");
		ColoadingFuelRateEntryDO fuelRateEntryDO = null;
		Session session = null;
		try {
			session = createSession();
			if (session != null) {
				Criteria criteria = session
						.createCriteria(ColoadingFuelRateEntryDO.class);
				criteria.add(Restrictions.eq("effectiveFrom", DateUtil
						.slashDelimitedstringToDDMMYYYYFormat(fuelRateEntryTO
								.getEffectiveFrom())));
				criteria.add(Restrictions.eq("cityId",
						fuelRateEntryTO.getCityId()));
				criteria.add(Restrictions.ne("storeStatus",
						ColoadingConstants.INACTIVE));
				fuelRateEntryDO = (ColoadingFuelRateEntryDO) criteria
						.uniqueResult();
				if (fuelRateEntryDO == null) {
					criteria = session
							.createCriteria(ColoadingFuelRateEntryDO.class);
					criteria.add(Restrictions.ne("storeStatus",
							ColoadingConstants.INACTIVE));
					criteria.add(Restrictions.eq("cityId",
							fuelRateEntryTO.getCityId()));
					criteria.add(Restrictions.isNull("effectiveTill"));
					fuelRateEntryDO = (ColoadingFuelRateEntryDO) criteria
							.uniqueResult();
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ColoadingDAOImpl::loadFuelRateEntryData()::",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ColoadingDAOImpl :: loadFuelRateEntryData() :: END");
		return fuelRateEntryDO;
	}

	@Override
	public ColoadingFuelRateEntryDO loadFutureFuelRateEntryData(
			FuelRateEntryTO fuelRateEntryTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("ColoadingDAOImpl::loadFutureFuelRateEntryData()::START");
		ColoadingFuelRateEntryDO fuelRateEntryDO = null;
		Session session = null;
		try {
			session = createSession();
			if (session != null) {
				Criteria criteria = session
						.createCriteria(ColoadingFuelRateEntryDO.class);
				criteria.add(Restrictions.isNull("effectiveTill"));
				criteria.add(Restrictions.ne("storeStatus",
						ColoadingConstants.INACTIVE));
				criteria.add(Restrictions.eq("cityId",
						fuelRateEntryTO.getCityId()));
				criteria.add(Restrictions.ne("storeStatus",
						ColoadingConstants.INACTIVE));
				fuelRateEntryDO = (ColoadingFuelRateEntryDO) criteria
						.uniqueResult();
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ColoadingDAOImpl::loadFutureFuelRateEntryData()",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ColoadingDAOImpl :: loadFutureFuelRateEntryData() :: END");
		return fuelRateEntryDO;
	}

	@Override
	public List<String> getVehicleList() throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("ColoadingDAOImpl::getVehicleList()::START");
		List<String> vehicleCode = null;
		Session session = null;

		try {
			session = createSession();
			if (session != null) {
				Criteria criteria = session.createCriteria(VehicleDO.class);
				ProjectionList projList = Projections.projectionList();
				projList.add(Projections.property("regNumber"));
				criteria.setProjection(Projections.distinct(projList));
				criteria.add(Restrictions.or(
						Restrictions.eq("isGpsEnabled", false),
						Restrictions.isNull("isGpsEnabled")));

				vehicleCode = criteria.list();
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ColoadingDAOImpl::getVehicleList()::",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ColoadingDAOImpl :: getVehicleList() :: END");
		return vehicleCode;
	}

	@Override
	public ColoadingVehicleContractDO searchVehicle(
			ColoadingVehicleContractTO vehicleContractTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("ColoadingDAOImpl::searchVehicle()::START");
		ColoadingVehicleContractDO vehicleContractDO = null;
		Session session = null;
		try {
			session = createSession();
			if (session != null) {
				Criteria vehicleCriteria = session.createCriteria(VehicleDO.class);				
				vehicleCriteria.add(Restrictions.eq("regNumber", vehicleContractTO.getVehicleNo()));				
				VehicleDO vehicleDO = (VehicleDO)vehicleCriteria.uniqueResult();
				
							
				Criteria criteria = session
						.createCriteria(ColoadingVehicleContractDO.class);
				criteria.add(Restrictions.eq("effectiveFrom", DateUtil
						.slashDelimitedstringToDDMMYYYYFormat(vehicleContractTO
								.getEffectiveFrom())));
				criteria.add(Restrictions.eq("vehicleNo",
						vehicleContractTO.getVehicleNo()));
				/*criteria.add(Restrictions.eq("vendorId",
						vehicleContractTO.getVendorId()));*/
				criteria.add(Restrictions.ne("storeStatus",
						ColoadingConstants.INACTIVE));
				vehicleContractDO = (ColoadingVehicleContractDO) criteria
						.uniqueResult();
				if (vehicleContractDO == null) {
					criteria = session
							.createCriteria(ColoadingVehicleContractDO.class);
					criteria.add(Restrictions.ne("storeStatus",
							ColoadingConstants.INACTIVE));
					criteria.add(Restrictions.eq("vehicleNo",
							vehicleContractTO.getVehicleNo()));
					/*
					 * criteria.add(Restrictions.eq("vendorId",
					 * vehicleContractTO.getVendorId()));
					 */
					criteria.add(Restrictions.isNull("effectiveTill"));
					vehicleContractDO = (ColoadingVehicleContractDO) criteria
							.uniqueResult();
					if (vehicleContractDO != null) {
						Date seletedDate = DateUtil
								.slashDelimitedstringToDDMMYYYYFormat(vehicleContractTO
										.getEffectiveFrom());
						if (seletedDate.before(vehicleContractDO
								.getEffectiveFrom())) {
							criteria = session
									.createCriteria(ColoadingVehicleContractDO.class);
							criteria.add(Restrictions.eq("storeStatus",
									ColoadingConstants.RENEW));
							criteria.add(Restrictions.eq("vehicleNo",
									vehicleContractTO.getVehicleNo()));
							Date previusDate = DateUtil
									.getPreviousDateFromGivenDate(vehicleContractDO
											.getEffectiveFrom());
							criteria.add(Restrictions.eq("effectiveTill",
									previusDate));
							vehicleContractDO = (ColoadingVehicleContractDO) criteria
									.uniqueResult();
						}
					}
				}
				
				if(vehicleDO != null) {
					vehicleContractDO.setGpsEnabled(vehicleDO.getIsGpsEnabled());
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ColoadingDAOImpl::searchVehicle()", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ColoadingDAOImpl :: searchVehicle() :: END");
		return vehicleContractDO;
	}

	@Override
	public String validateFlightNo(String flightNo)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("ColoadingDAOImpl::validateFlightNos()::START");
		Session session = null;
		String fn = null;
		try {
			session = createSession();
			if (session != null) {
				Criteria criteria = session.createCriteria(FlightDO.class).setProjection(Projections.property("airlineName"));
				criteria.add(Restrictions.eq("flightNumber", flightNo.toUpperCase()));
				List<String> flightDO = criteria.list();
				if(!CGCollectionUtils.isEmpty(flightDO)){
					fn = flightDO.get(0);
					if(fn == null){
						fn = "NA";
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ColoadingDAOImpl::validateFlightNos()::",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ColoadingDAOImpl :: validateFlightNos() :: END");
		return fn;
	}

	@Override
	public ColoadingVehicleContractDO getVehicleContractDO(String date,
			String vehicleNumber) throws CGBusinessException, CGSystemException {

		LOGGER.debug("ColoadingDAOImpl::getVehicleContractDO()::START");
		ColoadingVehicleContractDO vehicleContractDO = null;
		Session session = null;
		try {
			session = createSession();
			if (session != null) {
				Criteria criteria = session
						.createCriteria(ColoadingVehicleContractDO.class);
				criteria.add(Restrictions.le("effectiveFrom", DateUtil
						.slashDelimitedstringToDDMMYYYYFormat(date)));
				criteria.add(Restrictions.isNull("effectiveTill"));
				criteria.add(Restrictions.eq("vehicleNo",
						vehicleNumber));
				criteria.add(Restrictions.eq("storeStatus",
						ColoadingConstants.SUBMIT_CHAR));
				vehicleContractDO = (ColoadingVehicleContractDO) criteria
						.uniqueResult();
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ColoadingDAOImpl::getVehicleContractDO()::",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ColoadingDAOImpl :: getVehicleContractDO() :: END");
		return vehicleContractDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getLoadConnectedDOs4CdAwbModifaction(
			CdAwbModificationTO cdAwbModificationTO) throws CGSystemException {
		LOGGER.debug("ColoadingDAOImpl :: getLoadConnectedDOs4CdAwbModifaction() :: Start --------> ::::::");
		List<Object[]> loadConnectedDOs = null;
		try {
			loadConnectedDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							ColoadingConstants.QRY_GET_LOAD_CONNECTED_4_CD_AWB_MODIFACTION,
							new String[] { "regionId", "fromDate", "toDate" },
							new Object[] {
									cdAwbModificationTO.getRegionTO()
											.getRegionId(),
									DateUtil.stringToDDMMYYYYFormat(cdAwbModificationTO
											.getFromDate()),
									DateUtil.getIncrementDate(DateUtil.stringToDDMMYYYYFormat(cdAwbModificationTO
											.getToDate()), 1), });

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::ColoadingDAOImpl::getLoadConnectedDOs4CdAwbModifaction() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ColoadingDAOImpl :: getLoadConnectedDOs4CdAwbModifaction() :: End --------> ::::::");
		return loadConnectedDOs;
	}

	@Override
	public void updateCdAwbDetails(
			CdAwbModificationDetailsTO cdAwbModificationDetailsTO)
			throws CGSystemException {
		LOGGER.debug("ColoadingDAOImpl :: updateCdAwbDetails() :: Start --------> ::::::");
		Session session = null;
		try {
			/*
			 * session = getHibernateTemplate().getSessionFactory()
			 * .openSession();
			 */
			session = openTransactionalSession();
			Query query = session
					.getNamedQuery(ColoadingConstants.QRY_UPDATE_CD_AWB_DETAILS_BY_LOAD_MOVEMENT_ID);
			query.setInteger("loadMovementId",
					cdAwbModificationDetailsTO.getLoadMovementId());
			query.setString("dispatchedUsing",
					cdAwbModificationDetailsTO.getDispatchedUsing());
			query.setString("dispatchedType",
					cdAwbModificationDetailsTO.getDispatchedType());

			query.executeUpdate();
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::ColoadingDAOImpl::updateCdAwbDetails() :: "
					, e);
			throw new CGSystemException(e);
		} finally {
			// closeSession(session);
			closeTransactionalSession(session);
		}
		LOGGER.debug("ColoadingDAOImpl :: updateCdAwbDetails() :: End --------> ::::::");
	}

	@Override
	public void updateLoadMovementDbSyncFlagsByIds(Set<Integer> loadMovementIds)
			throws CGSystemException {
		LOGGER.debug("ColoadingDAOImpl :: updateLoadMovementDbSyncFlagsByIds() :: Start --------> ::::::");
		Session session = null;
		try {
			/*
			 * session = getHibernateTemplate().getSessionFactory()
			 * .openSession();
			 */
			session = openTransactionalSession();
			Query query = session
					.getNamedQuery(ColoadingConstants.QRY_UPDATE_LOAD_MOVEMENT_DT_TO_CENTRAL);
			query.setParameterList("loadMovementId", loadMovementIds);
			query.executeUpdate();
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::ColoadingDAOImpl::updateLoadMovementDbSyncFlagsByIds() :: "
					, e);
			throw new CGSystemException(e);
		} finally {
			// closeSession(session);
			closeTransactionalSession(session);
		}
		LOGGER.debug("ColoadingDAOImpl :: updateLoadMovementDbSyncFlagsByIds() :: End --------> ::::::");
	}

	@Override
	public ColoadingSurfaceRateEntryDO loadVendorSavedData(int vendorId)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("ColoadingDAOImpl :: loadVendorSavedData() :: Start");
		Session session = null;
		ColoadingSurfaceRateEntryDO coloadingSurfaceRateEntryDO = null;
		try {
			session = createSession();
			if (session != null) {
				Criteria criteria = session.createCriteria(ColoadingSurfaceRateEntryDO.class);
				criteria.add(Restrictions.eq("vendorId", vendorId));
				coloadingSurfaceRateEntryDO = (ColoadingSurfaceRateEntryDO)criteria.uniqueResult();
				
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ColoadingDAOImpl::validateFlightNos()::",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ColoadingDAOImpl :: loadVendorSavedData() :: End");
		return coloadingSurfaceRateEntryDO;
	}
}
