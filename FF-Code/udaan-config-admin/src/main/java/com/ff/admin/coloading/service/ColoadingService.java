/**
 * 
 */
package com.ff.admin.coloading.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.coloading.AirColoadingTO;
import com.ff.coloading.CdAwbModificationTO;
import com.ff.coloading.ColoadingVehicleContractTO;
import com.ff.coloading.ColoadingVendorTO;
import com.ff.coloading.FuelRateEntryTO;
import com.ff.coloading.SurfaceRateEntryTO;
import com.ff.coloading.TrainColoadingTO;
import com.ff.coloading.VehicleServiceEntryTO;
import com.ff.domain.coloading.ColoadingAirDO;
import com.ff.domain.coloading.ColoadingFuelRateEntryDO;
import com.ff.domain.coloading.ColoadingSurfaceRateEntryDO;
import com.ff.domain.coloading.ColoadingTrainContractDO;
import com.ff.domain.coloading.ColoadingVehicleContractDO;
import com.ff.domain.coloading.ColoadingVehicleServiceEntryDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.transport.FlightDO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.organization.OfficeTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;

/**
 * @author isawarka
 * 
 */
public interface ColoadingService {
	List<RegionTO> getRegions() throws CGBusinessException, CGSystemException;

	List<CityTO> getCitiesByRegionId(final Integer regionId)
			throws CGBusinessException, CGSystemException;

	List<ColoadingVendorTO> getVendorsList(Integer regionId, String serviceType)
			throws CGBusinessException, CGSystemException;

	List<StockStandardTypeTO> getStockStdType(String typeName)
			throws CGBusinessException, CGSystemException;

	ColoadingTrainContractDO getTrainData(TrainColoadingTO coloadingTo)
			throws CGBusinessException, CGSystemException;

	ColoadingAirDO getAirData(AirColoadingTO airColoadingTo)
			throws CGBusinessException, CGSystemException;

	ColoadingTrainContractDO getTrainFutureData(TrainColoadingTO coloadingTo)
			throws CGBusinessException, CGSystemException;

	ColoadingAirDO getAirFutureData(AirColoadingTO airColoadingTo)
			throws CGBusinessException, CGSystemException;

	String validateFlightNo(String flightNo) throws CGBusinessException, CGSystemException;
	
	ColoadingAirDO saveColoadingAir(AirColoadingTO airColoadingTo, int userId) throws CGBusinessException, CGSystemException;

	ColoadingTrainContractDO saveColoadingTrain(
			TrainColoadingTO trainColoadingTo, int userId)
			throws CGBusinessException, CGSystemException;

	public List<String> getVehicleList() throws CGBusinessException,
	CGSystemException;
	
	ColoadingVehicleContractDO searchVehicle(ColoadingVehicleContractTO vehicleContractTO)
			throws CGBusinessException, CGSystemException;
	
	ColoadingVehicleContractDO saveColoadingVehicle(
			ColoadingVehicleContractTO vehicleContractTO , int userId, OfficeTO officeTo)
			throws CGBusinessException, CGSystemException;
	
	ColoadingFuelRateEntryDO saveFuelRateEntry(
			FuelRateEntryTO fuelRateEntryTO , int userId)
			throws CGBusinessException, CGSystemException;
	
	ColoadingFuelRateEntryDO loadFuelRateEntryData(FuelRateEntryTO fuelRateEntryTO)
			throws CGBusinessException, CGSystemException;
	
	ColoadingFuelRateEntryDO loadFutureFuelRateEntryData(FuelRateEntryTO fuelRateEntryTO)
			throws CGBusinessException, CGSystemException;
	
	List<CityDO> getCityDOList()
			throws CGBusinessException, CGSystemException;
	
	ColoadingVehicleServiceEntryDO saveVehicleServiceEntry(
			VehicleServiceEntryTO serviceEntryTO , int userId , int officeId)
			throws CGBusinessException, CGSystemException;
	
	ColoadingSurfaceRateEntryDO loadVendorSavedData(int vendorId)
			throws CGBusinessException, CGSystemException;
	
	ColoadingSurfaceRateEntryDO saveSurfaceRateEntry(
			SurfaceRateEntryTO surfaceRateEntryTO  , int userId)
			throws CGBusinessException, CGSystemException;
	ColoadingVehicleContractDO searchVehicleFutureData(
			ColoadingVehicleContractTO vehicleContractTO) throws CGBusinessException,
			CGSystemException;
	ColoadingVehicleContractDO getVehicleContractDO(String date,String vehicleNumber) throws CGBusinessException,
			CGSystemException;
	
	int getUserID(HttpServletRequest request);
	int getOfficeID(HttpServletRequest request);

	/**
	 * Find cd awb details.
	 *
	 * @param cdAwbModificationTO the cd awb modification to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	void findCdAwbDetails(CdAwbModificationTO cdAwbModificationTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Update cd awb details.
	 *
	 * @param cdAwbModificationTO the cd awb modification to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	void updateCdAwbDetails(CdAwbModificationTO cdAwbModificationTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Get all the flights mapped to a particular route
	 * @param coloadingTo the air coloading to
	 * @return flights mapped to a particular route
	 */
	List<FlightDO> getFlightData(AirColoadingTO coloadingTo);
}
