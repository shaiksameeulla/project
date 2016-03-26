package com.ff.web.pickup.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.cg.lbs.bcun.utility.TwoWayWriteProcessCall;
import com.ff.domain.pickup.PickupRunsheetHeaderDO;
import com.ff.domain.pickup.ReversePickupOrderBranchMappingDO;
import com.ff.domain.pickup.ReversePickupOrderDetailDO;
import com.ff.domain.pickup.RunsheetAssignmentHeaderDO;
import com.ff.pickup.PickupRunsheetTO;
import com.ff.pickup.PickupTwoWayWriteTO;

public class PickupUtils {
	private static void validateAndSetTwoWayWriteFlag(CGBaseDO cgBaseDO) {
		if(TwoWayWriteProcessCall.isTwoWayWriteEnabled()){
			cgBaseDO.setDtToCentral(CommonConstants.YES);
		}
	}
	
	public static void setUpdateFlag4DBSync(CGBaseDO cgBaseDO) {
		cgBaseDO.setDtUpdateToCentral(CommonConstants.YES);
		cgBaseDO.setDtToCentral(CommonConstants.NO);
		validateAndSetTwoWayWriteFlag(cgBaseDO);
	}
	
	/**
	 * Sets the save flag4 db sync.
	 *
	 * @param cgBaseDO the new save flag4 db sync
	 */
	public static void setSaveFlag4DBSync(CGBaseDO cgBaseDO) {
		cgBaseDO.setDtToCentral(CommonConstants.NO);
		validateAndSetTwoWayWriteFlag(cgBaseDO);
	}
	
	public static PickupTwoWayWriteTO setPkupRunsheetIds4TwoWayWrite(PickupRunsheetTO pkupRunsheetTO,
			List<PickupRunsheetHeaderDO> pickupRunsheetHeaderDOs) {
		ArrayList<Integer> runsheetHeaderIds = new ArrayList<>();
		ArrayList<String> headerProcessNames = new ArrayList<>();
		for (PickupRunsheetHeaderDO pickupRunsheetHeaderDO : pickupRunsheetHeaderDOs) {
			runsheetHeaderIds.add(pickupRunsheetHeaderDO.getRunsheetHeaderId());
			headerProcessNames.add(CommonConstants.TWO_WAY_WRITE_PROCESS_GENERATE_RUNSHEET);
		}			
		
		PickupTwoWayWriteTO pickupTwoWayWriteTO = new PickupTwoWayWriteTO();
		pickupTwoWayWriteTO.setHeaderDoIds(runsheetHeaderIds);
		pickupTwoWayWriteTO.setHeaderProcessNames(headerProcessNames);
		
		return pickupTwoWayWriteTO;
	}

	public static PickupTwoWayWriteTO setPickupOrderIds4TwoWayWrite(
			Set<ReversePickupOrderDetailDO> detailsDO) {
		ArrayList<Integer> runsheetHeaderIds = new ArrayList<>();
		ArrayList<String> headerProcessNames = new ArrayList<>();
		Set<ReversePickupOrderBranchMappingDO> revPickupOrderBranchMappingDOs = new HashSet<>();
		for (ReversePickupOrderDetailDO revPickupOrderDetailDO : detailsDO) {
			revPickupOrderBranchMappingDOs.addAll(revPickupOrderDetailDO.getBranchesAssignedDO());
		}
		for (ReversePickupOrderBranchMappingDO revPkupOrderBranchMappingDO : revPickupOrderBranchMappingDOs) {
			if(StringUtils.equalsIgnoreCase(CommonConstants.NO, revPkupOrderBranchMappingDO.getDtToCentral())){
				runsheetHeaderIds.add(revPkupOrderBranchMappingDO.getId());
				headerProcessNames.add(CommonConstants.TWO_WAY_WRITE_PROCESS_REVERSE_PICKUP_REQ);
			}			
		}
		
		PickupTwoWayWriteTO pickupTwoWayWriteTO = new PickupTwoWayWriteTO();
		pickupTwoWayWriteTO.setHeaderDoIds(runsheetHeaderIds);
		pickupTwoWayWriteTO.setHeaderProcessNames(headerProcessNames);
		
		return pickupTwoWayWriteTO;
	}

	public static PickupTwoWayWriteTO setPickupAssignmentIds4TwoWayWrite(
			RunsheetAssignmentHeaderDO runsheetAssignmentHeaderDO) {
		ArrayList<Integer> runsheetHeaderIds = new ArrayList<>();
		ArrayList<String> headerProcessNames = new ArrayList<>();
		
		runsheetHeaderIds.add(runsheetAssignmentHeaderDO.getAssignmentHeaderId());
		headerProcessNames.add(CommonConstants.TWO_WAY_WRITE_PROCESS_RUNSHEET_ASSIGNMENT);
		
		PickupTwoWayWriteTO pickupTwoWayWriteTO = new PickupTwoWayWriteTO();
		pickupTwoWayWriteTO.setHeaderDoIds(runsheetHeaderIds);
		pickupTwoWayWriteTO.setHeaderProcessNames(headerProcessNames);
		
		return pickupTwoWayWriteTO;
	}
	

}
