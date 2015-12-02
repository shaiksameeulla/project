/**
 * 
 */
package src.com.dtdc.mdbServices.vehicletrip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import src.com.dtdc.mdbDao.vehicletrip.VehicleTripSheetMDBDAO;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.master.vehicle.VehicleDO;
import com.dtdc.domain.transaction.vehicletrip.VehicleTripSheetDO;
import com.dtdc.to.vehicletrip.VehicleTripSheetTO;


// TODO: Auto-generated Javadoc
/**
 * The Class VehicleTripSheetMDBServiceImpl.
 */
public class VehicleTripSheetMDBServiceImpl implements
VehicleTripSheetMDBService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
	.getLogger(VehicleTripSheetMDBServiceImpl.class);

	/** The vehicle trip sheet mdbdao. */
	private VehicleTripSheetMDBDAO vehicleTripSheetMDBDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dtdc.ng.bs.booking.cashbooking.CashBookingService#saveCashBookingDetails
	 * (com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO)
	 */
	@Override
	public Boolean saveVehicleTripSheet(CGBaseTO tripSheetTO) throws Exception {

		LOGGER.debug("*********************** In Spring Bean Class *********************"
				+ tripSheetTO.getBaseList());
		VehicleTripSheetTO vehicleTripSheetTO = (VehicleTripSheetTO) tripSheetTO
		.getBaseList().get(0);
		VehicleTripSheetDO tripSheetDO = new VehicleTripSheetDO();

		// FIXME Creating the Office Objects
		OfficeDO location = new OfficeDO();
		OfficeDO startLocation = new OfficeDO();
		OfficeDO viaLocation = new OfficeDO();
		OfficeDO endLocation = new OfficeDO();
		location.setOfficeId(vehicleTripSheetTO.getLocationID());
		startLocation.setOfficeId(vehicleTripSheetTO.getStartLocationID());
		if (vehicleTripSheetTO.getViaLocationID() == 0) {
			viaLocation = null;
		} else {
			viaLocation.setOfficeId(vehicleTripSheetTO.getViaLocationID());
		}
		endLocation.setOfficeId(vehicleTripSheetTO.getEndLocationID());

		VehicleDO vehicle = new VehicleDO();
		vehicle.setVehicleId(Integer.parseInt(vehicleTripSheetTO
				.getVehicleRegstn()));

		vehicleTripSheetTO.setTripDate(DateFormatterUtil
				.getDateFromStringDDMMYYY(vehicleTripSheetTO.getTripdateStr()));

		CGObjectConverter.createDomainFromTo(vehicleTripSheetTO, tripSheetDO);
		tripSheetDO.setLocation(location);
		tripSheetDO.setStartLocation(startLocation);
		tripSheetDO.setViaLocation(viaLocation);
		tripSheetDO.setEndLocation(endLocation);
		tripSheetDO.setVehicleId(vehicle);
		tripSheetDO.setActive("Y");

		boolean vehStatus = vehicleTripSheetMDBDAO
		.saveVehicleTripSheet(tripSheetDO);

		/*
		 * To throw the exception explicitly so that request can be written into
		 * ERROR Q
		 */
		if (!vehStatus) {
			throw new CGBusinessException("Error occurred while saving the data");
		}

		return vehStatus;
	}

	/**
	 * Gets the vehicle trip sheet mdbdao.
	 *
	 * @return the vehicleTripSheetMDBDAO
	 */
	public VehicleTripSheetMDBDAO getVehicleTripSheetMDBDAO() {
		return vehicleTripSheetMDBDAO;
	}

	/**
	 * Sets the vehicle trip sheet mdbdao.
	 *
	 * @param vehicleTripSheetMDBDAO the vehicleTripSheetMDBDAO to set
	 */
	public void setVehicleTripSheetMDBDAO(
			VehicleTripSheetMDBDAO vehicleTripSheetMDBDAO) {
		this.vehicleTripSheetMDBDAO = vehicleTripSheetMDBDAO;
	}

}
