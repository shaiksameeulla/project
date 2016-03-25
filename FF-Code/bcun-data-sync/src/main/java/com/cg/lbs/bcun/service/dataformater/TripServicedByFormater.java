package com.cg.lbs.bcun.service.dataformater;

import java.util.List;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.constant.BcunDataFormaterConstants;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.ff.domain.routeserviced.BcunServicedByDO;
import com.ff.domain.routeserviced.BcunTripServicedByDO;
import com.ff.domain.routeserviced.ServicedByDO;
import com.ff.domain.routeserviced.TripDO;

public class TripServicedByFormater extends AbstractDataFormater {

	@Override
	public CGBaseDO formatInsertData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) {
		BcunTripServicedByDO tripServicedByDO = (BcunTripServicedByDO) baseDO;
		tripServicedByDO.setTripServicedById(null);

		getAndSetServicedByDO(tripServicedByDO, bcunService);
		getAndSetTripDO(tripServicedByDO, bcunService);
		
		return tripServicedByDO;
	}

	private void getAndSetTripDO(BcunTripServicedByDO tripServicedByDO,
			BcunDatasyncService bcunService) {
		//TripDO tripDO = tripServicedByDO.getTripDO();
		TripDO tripDO = null;
		if(tripDO!=null){
			TripFormater.getAndSetTripId(tripDO, bcunService);
			if(StringUtil.isEmptyInteger(tripServicedByDO.getTripDO().getTripId())){
				tripServicedByDO.setTripDO(null);
			}
		}
	}

	private void getAndSetServicedByDO(BcunTripServicedByDO tripServicedByDO, BcunDatasyncService bcunService) {
		if(tripServicedByDO.getServicedByDO()==null){
			return;
		}
		
		BcunServicedByDO servicedByDO = getServicedByDO4TripServicedBy(tripServicedByDO, bcunService);
		if(servicedByDO!=null){
			tripServicedByDO.setServicedByDO(servicedByDO);
		} else {
			tripServicedByDO.getServicedByDO().setServicedById(null);
		}		
	}

	@SuppressWarnings("unchecked")
	private BcunServicedByDO getServicedByDO4TripServicedBy(
			BcunTripServicedByDO tripServicedByDO, BcunDatasyncService bcunService) {

		BcunServicedByDO servicedByDO = null;
		List<BcunServicedByDO> servicedByDOs = null;
		Integer serviceByTypeId = null;
		if(tripServicedByDO.getServicedByDO().getServiceByTypeDO()!=null){
			serviceByTypeId = tripServicedByDO.getServicedByDO().getServiceByTypeDO().getServiceByTypeId();
		}
		
		if (tripServicedByDO.getServicedByDO().getLoadMovementVendorDO() != null) {
			servicedByDOs = (List<BcunServicedByDO>) bcunService
					.getDataByNamedQueryAndNamedParam(
							BcunDataFormaterConstants.QRY_GET_SERVICED_BY_DETAILS_BY_VENDOR,
							new String[] {
									BcunDataFormaterConstants.PARAM_SERVICE_BY_TYPE_ID,
									BcunDataFormaterConstants.PARAM_VENDOR_ID },
							new Object[] {
									serviceByTypeId,
									tripServicedByDO.getServicedByDO()
											.getLoadMovementVendorDO()
											.getVendorId() });
			
		} else if (tripServicedByDO.getServicedByDO().getEmployeeDO() != null) {
			servicedByDOs = (List<BcunServicedByDO>) bcunService
					.getDataByNamedQueryAndNamedParam(
							BcunDataFormaterConstants.QRY_GET_SERVICED_BY_DETAILS_BY_EMPLOYEE,
							new String[] {
									BcunDataFormaterConstants.PARAM_SERVICE_BY_TYPE_ID,
									BcunDataFormaterConstants.PARAM_EMPLOYEE_ID },
							new Object[] {
									serviceByTypeId,
									tripServicedByDO.getServicedByDO()
											.getEmployeeDO()
											.getEmployeeId() });
		}
		
		if (!StringUtil.isEmptyColletion(servicedByDOs)) {
			servicedByDO = servicedByDOs.get(0);
		}
		return servicedByDO;
	}

	@Override
	public CGBaseDO formatUpdateData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) {
		BcunTripServicedByDO tripServicedByDO = (BcunTripServicedByDO) baseDO;

		getAndSetServicedByDO(tripServicedByDO, bcunService);
		getAndSetTripDO(tripServicedByDO, bcunService);
		
		Integer tripServicedById = bcunService.getUniqueId(
				BcunDataFormaterConstants.QRY_GET_TRIP_SERVICED_BY_ID_BY_TRIP_SERVICED_BY,
				new String[] {
						BcunDataFormaterConstants.PARAM_TRIP_ID,
						BcunDataFormaterConstants.PARAM_SERVICE_BY_ID,
						BcunDataFormaterConstants.PARAM_TRANSPORT_MODE_ID,
						BcunDataFormaterConstants.PARAM_OPERATION_DAYS,
						BcunDataFormaterConstants.PARAM_EFFECTIVE_FROM,
						BcunDataFormaterConstants.PARAM_EFFECTIVE_TO },
				new Object[] {
						tripServicedByDO.getTripDO().getTripId(),
						tripServicedByDO.getServicedByDO().getServicedById(),
						tripServicedByDO.getTransportModeDO().getTransportModeId(),
						tripServicedByDO.getOperationDays(),
						tripServicedByDO.getEffectiveFrom(),
						tripServicedByDO.getEffectiveTo()});
		
		tripServicedByDO.setTripServicedById(tripServicedById);
		
		return tripServicedByDO;
	}

}
