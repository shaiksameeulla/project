package com.cg.lbs.bcun.service.dataformater;

import java.util.List;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.constant.BcunDataFormaterConstants;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.ff.domain.routeserviced.TripDO;
import com.ff.domain.transport.TransportDO;

public class TripFormater extends AbstractDataFormater {

	@Override
	public CGBaseDO formatInsertData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) {
		TripDO tripDO = (TripDO) baseDO;
		tripDO.setTripId(null);
		
		setDefaultValueToTrip(tripDO);
		getAndSetTransportDO(tripDO, bcunService);
		
		return tripDO;
	}

	@SuppressWarnings("unchecked")
	private void getAndSetTransportDO(TripDO tripDO,
			BcunDatasyncService bcunService) {
		if(tripDO.getTransportDO()==null){
			return;
		}
		
		List<TransportDO> transportDOs = null;
		String transportModeCode = null;
		if(tripDO.getTransportDO().getTransportModeDO()!=null){
			transportModeCode = tripDO.getTransportDO().getTransportModeDO().getTransportModeCode();
		}
		
		if(tripDO.getTransportDO().getFlightDO()!=null){
			transportDOs = (List<TransportDO>) bcunService
					.getDataByNamedQueryAndNamedParam(
							BcunDataFormaterConstants.QRY_GET_TRANSPORT_DETAILS_BY_FLIGHT,
							new String[] {
									BcunDataFormaterConstants.PARAM_TRANSPORT_MODE_CODE,
									BcunDataFormaterConstants.PARAM_FLIGHT_NUMBER },
							new Object[] {
									transportModeCode,
									tripDO.getTransportDO().getFlightDO()
											.getFlightNumber() });
			
		}else if(tripDO.getTransportDO().getTrainDO()!=null){

			transportDOs = (List<TransportDO>) bcunService
					.getDataByNamedQueryAndNamedParam(
							BcunDataFormaterConstants.QRY_GET_TRANSPORT_DETAILS_BY_TRAIN,
							new String[] {
									BcunDataFormaterConstants.PARAM_TRANSPORT_MODE_CODE,
									BcunDataFormaterConstants.PARAM_TRAIN_NUMBER },
							new Object[] {
									transportModeCode,
									tripDO.getTransportDO().getTrainDO()
											.getTrainNumber() });
			
		}else if(tripDO.getTransportDO().getVehicleDO()!=null){

			transportDOs = (List<TransportDO>) bcunService
					.getDataByNamedQueryAndNamedParam(
							BcunDataFormaterConstants.QRY_GET_TRANSPORT_DETAILS_BY_ROAD,
							new String[] {
									BcunDataFormaterConstants.PARAM_TRANSPORT_MODE_CODE,
									BcunDataFormaterConstants.PARAM_REG_NUMBER },
							new Object[] {
									transportModeCode,
									tripDO.getTransportDO().getVehicleDO()
											.getRegNumber() });
		}
		
		if(!StringUtil.isEmptyColletion(transportDOs)){
			tripDO.setTransportDO(transportDOs.get(0));
		}else{
			tripDO.getTransportDO().setTransportId(null);
		}
	}

	private void setDefaultValueToTrip(TripDO tripDO) {
		tripDO.setOriginPortDO(null);
		tripDO.setDestPortDO(null);
	}

	@Override
	public CGBaseDO formatUpdateData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) {
		TripDO tripDO = (TripDO) baseDO;

		getAndSetTripId(tripDO, bcunService);
		setDefaultValueToTrip(tripDO);
		getAndSetTransportDO(tripDO, bcunService);
		
		return tripDO;
	}

	public static void getAndSetTripId(TripDO tripDO, BcunDatasyncService bcunService) {
		Integer tripId = null;
		if (tripDO.getRouteDO() == null || tripDO.getTransportDO() == null
				|| tripDO.getTransportDO().getTransportModeDO() == null) {
			tripDO.setTripId(tripId);
			return;
		}
		
		if (tripDO.getTransportDO().getFlightDO() != null) {
			tripId = bcunService
					.getUniqueId(
							BcunDataFormaterConstants.QRY_GET_TRIP_ID_BY_ROUTE_TRANSPORT_MODE_FLIGHT,
							new String[] {
									BcunDataFormaterConstants.PARAM_ROUTE_ID,
									BcunDataFormaterConstants.PARAM_ARRIVAL_TIME,
									BcunDataFormaterConstants.PARAM_DEPARTURE_TIME,
									BcunDataFormaterConstants.PARAM_TRANSPORT_MODE_ID,
									BcunDataFormaterConstants.PARAM_FLIGHT_ID },
							new Object[] {
									tripDO.getRouteDO().getRouteId(),
									tripDO.getArrivalTime(),
									tripDO.getDepartureTime(),
									tripDO.getTransportDO()
											.getTransportModeDO()
											.getTransportModeId(),
									tripDO.getTransportDO().getFlightDO()
											.getFlightId() });

		} else if (tripDO.getTransportDO().getTrainDO() != null) {
			tripId = bcunService
					.getUniqueId(
							BcunDataFormaterConstants.QRY_GET_TRIP_ID_BY_ROUTE_TRANSPORT_MODE_TRAIN,
							new String[] {
									BcunDataFormaterConstants.PARAM_ROUTE_ID,
									BcunDataFormaterConstants.PARAM_ARRIVAL_TIME,
									BcunDataFormaterConstants.PARAM_DEPARTURE_TIME,
									BcunDataFormaterConstants.PARAM_TRANSPORT_MODE_ID,
									BcunDataFormaterConstants.PARAM_TRAIN_ID },
							new Object[] {
									tripDO.getRouteDO().getRouteId(),
									tripDO.getArrivalTime(),
									tripDO.getDepartureTime(),
									tripDO.getTransportDO()
											.getTransportModeDO()
											.getTransportModeId(),
									tripDO.getTransportDO().getTrainDO()
											.getTrainId() });

		} else if (tripDO.getTransportDO().getVehicleDO() != null) {
			tripId = bcunService
					.getUniqueId(
							BcunDataFormaterConstants.QRY_GET_TRIP_ID_BY_ROUTE_TRANSPORT_MODE_VEHICLE,
							new String[] {
									BcunDataFormaterConstants.PARAM_ROUTE_ID,
									BcunDataFormaterConstants.PARAM_ARRIVAL_TIME,
									BcunDataFormaterConstants.PARAM_DEPARTURE_TIME,
									BcunDataFormaterConstants.PARAM_TRANSPORT_MODE_ID,
									BcunDataFormaterConstants.PARAM_VEHICLE_ID },
							new Object[] {
									tripDO.getRouteDO().getRouteId(),
									tripDO.getArrivalTime(),
									tripDO.getDepartureTime(),
									tripDO.getTransportDO()
											.getTransportModeDO()
											.getTransportModeId(),
									tripDO.getTransportDO().getVehicleDO()
											.getVehicleId() });

		}

		tripDO.setTripId(tripId);

	}

}
