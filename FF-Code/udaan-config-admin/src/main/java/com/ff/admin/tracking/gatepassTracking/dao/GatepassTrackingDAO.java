package com.ff.admin.tracking.gatepassTracking.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.loadmanagement.LoadMovementDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;

public interface GatepassTrackingDAO {
	
	public List<LoadMovementDO> getGatePassDeatils(String number)throws CGSystemException;
	public List<LoadMovementDO> getCRAWBDeatils(String number)throws CGSystemException;
	public List<StockStandardTypeDO> getTypeName() throws CGSystemException;

}
