/**
 * 
 */
package com.ff.admin.coloading.dao;

import java.util.List;
import java.util.Set;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.coloading.AirColoadingTO;
import com.ff.coloading.CdAwbModificationDetailsTO;
import com.ff.coloading.CdAwbModificationTO;
import com.ff.coloading.ColoadingVehicleContractTO;
import com.ff.coloading.ColoadingVendorTO;
import com.ff.coloading.FuelRateEntryTO;
import com.ff.coloading.TrainColoadingTO;
import com.ff.domain.coloading.ColoadingAirDO;
import com.ff.domain.coloading.ColoadingFuelRateEntryDO;
import com.ff.domain.coloading.ColoadingSurfaceRateEntryDO;
import com.ff.domain.coloading.ColoadingTrainContractDO;
import com.ff.domain.coloading.ColoadingVehicleContractDO;
import com.ff.domain.coloading.ColoadingVehicleServiceEntryDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;
import com.ff.domain.transport.FlightDO;
import com.ff.organization.OfficeTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface ColoadingDAO.
 *
 * @author isawarka
 */
public interface ColoadingDAO {

	/**
	 * Gets the stock std type.
	 *
	 * @param typeName the type name
	 * @return the stock std type
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public List<StockStandardTypeDO> getStockStdType(String typeName)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the vendors list.
	 *
	 * @param regionId the region id
	 * @param serviceType the service type
	 * @return the vendors list
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<ColoadingVendorTO> getVendorsList(Integer regionId, String serviceType)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the train data.
	 *
	 * @param coloadingTo the coloading to
	 * @return the train data
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	ColoadingTrainContractDO getTrainData(TrainColoadingTO coloadingTo)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the air data.
	 *
	 * @param airColoadingTo the air coloading to
	 * @return the air data
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	ColoadingAirDO getAirData(AirColoadingTO airColoadingTo)
			throws CGBusinessException, CGSystemException;
	
	/**
	 * Gets the train future data.
	 *
	 * @param coloadingTo the coloading to
	 * @return the train future data
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	ColoadingTrainContractDO getTrainFutureData(TrainColoadingTO coloadingTo)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the air future data.
	 *
	 * @param airColoadingTo the air coloading to
	 * @return the air future data
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	ColoadingAirDO getAirFutureData(AirColoadingTO airColoadingTo)
			throws CGBusinessException, CGSystemException;
	

	/**
	 * @param flightNo
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public String validateFlightNo(String flightNo) throws CGBusinessException, CGSystemException;
	
	/**
	 * Save coloading air.
	 *
	 * @param airDO the air do
	 * @param userId the user id
	 * @param renew the renew
	 * @return the coloading air do
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	ColoadingAirDO saveColoadingAir(ColoadingAirDO airDO, int userId,
			String renew) throws CGBusinessException, CGSystemException;

	/**
	 * Save coloading train.
	 *
	 * @param contractDO the contract do
	 * @param userId the user id
	 * @param renew the renew
	 * @return the coloading train contract do
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	ColoadingTrainContractDO saveColoadingTrain(
			ColoadingTrainContractDO contractDO, int userId, String renew)
			throws CGBusinessException, CGSystemException;
	
	/**
	 * Gets the vehicle list.
	 *
	 * @return the vehicle list
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public List<String> getVehicleList() throws CGBusinessException,
	CGSystemException;
	
	/**
	 * Search vehicle.
	 *
	 * @param vehicleContractTO the vehicle contract to
	 * @return the coloading vehicle contract do
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	ColoadingVehicleContractDO searchVehicle(ColoadingVehicleContractTO vehicleContractTO)
			throws CGBusinessException, CGSystemException;
	
	/**
	 * Save coloading vehicle.
	 *
	 * @param vehicleContractDO the vehicle contract do
	 * @param userId the user id
	 * @param renew the renew
	 * @return the coloading vehicle contract do
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	ColoadingVehicleContractDO saveColoadingVehicle(ColoadingVehicleContractDO vehicleContractDO, int userId,String renew, OfficeTO officeTo)
			throws CGBusinessException, CGSystemException;
	
	/**
	 * Gets the city do list.
	 *
	 * @return the city do list
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<CityDO> getCityDOList()
			throws CGBusinessException, CGSystemException;
	
	/**
	 * Save fuel rate entry.
	 *
	 * @param fuelRateEntryDO the fuel rate entry do
	 * @param userId the user id
	 * @param renew the renew
	 * @return the coloading fuel rate entry do
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	ColoadingFuelRateEntryDO saveFuelRateEntry(
			ColoadingFuelRateEntryDO fuelRateEntryDO , int userId,String renew)
			throws CGBusinessException, CGSystemException;
	
	/**
	 * Save vehicle service entry.
	 *
	 * @param vehicleServiceEntryDO the vehicle service entry do
	 * @param userId the user id
	 * @return the coloading vehicle service entry do
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	ColoadingVehicleServiceEntryDO saveVehicleServiceEntry(
			ColoadingVehicleServiceEntryDO vehicleServiceEntryDO)
			throws CGBusinessException, CGSystemException;
	
	/**
	 * Save surface rate entry.
	 *
	 * @param surfaceRateEntryDO the surface rate entry do
	 * @param userId the user id
	 * @return the coloading surface rate entry do
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	ColoadingSurfaceRateEntryDO saveSurfaceRateEntry(
			ColoadingSurfaceRateEntryDO surfaceRateEntryDO  , int userId)
			throws CGBusinessException, CGSystemException;
	
	/**
	 * Load fuel rate entry data.
	 *
	 * @param fuelRateEntryTO the fuel rate entry to
	 * @return the coloading fuel rate entry do
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	ColoadingFuelRateEntryDO loadFuelRateEntryData(FuelRateEntryTO fuelRateEntryTO)
			throws CGBusinessException, CGSystemException;
	
	/**
	 * Load future fuel rate entry data.
	 *
	 * @param fuelRateEntryTO the fuel rate entry to
	 * @return the coloading fuel rate entry do
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	ColoadingFuelRateEntryDO loadFutureFuelRateEntryData(FuelRateEntryTO fuelRateEntryTO)
			throws CGBusinessException, CGSystemException;
	
	/**
	 * Search vehicle future data.
	 *
	 * @param vehicleContractTO the vehicle contract to
	 * @return the coloading vehicle contract do
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	ColoadingVehicleContractDO searchVehicleFutureData(
			ColoadingVehicleContractTO vehicleContractTO) throws CGBusinessException,
			CGSystemException;
	
	/**
	 * Gets the vehicle contract do.
	 *
	 * @param date the date
	 * @param vehicleNumber the vehicle number
	 * @return the vehicle contract do
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	ColoadingVehicleContractDO getVehicleContractDO(String date,String vehicleNumber) throws CGBusinessException,
	CGSystemException;

	/**
	 * Gets the load connected d os4 cd awb modifaction.
	 *
	 * @param cdAwbModificationTO the cd awb modification to
	 * @return the load connected d os4 cd awb modifaction
	 * @throws CGSystemException the cG system exception
	 */
	public List<Object[]> getLoadConnectedDOs4CdAwbModifaction(
			CdAwbModificationTO cdAwbModificationTO) throws CGSystemException;

	/**
	 * Update cd awb details.
	 *
	 * @param cdAwbModificationDetailsTO the cd awb modification details to
	 * @throws CGSystemException the cG system exception
	 */
	public void updateCdAwbDetails(
			CdAwbModificationDetailsTO cdAwbModificationDetailsTO)
			throws CGSystemException;

	/**
	 * Update load movement db sync flags by ids.
	 *
	 * @param loadMovementIds the load movement ids
	 * @throws CGSystemException the cG system exception
	 */
	public void updateLoadMovementDbSyncFlagsByIds(Set<Integer> loadMovementIds)
			throws CGSystemException;
	
	ColoadingSurfaceRateEntryDO loadVendorSavedData(int vendorId)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the flight(s) mapped with particular route.
	 *
	 * @param airColoadingTo the air coloading to
	 * @return the flight data
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<FlightDO> getFlightList(AirColoadingTO coloadingTo);
	
}
