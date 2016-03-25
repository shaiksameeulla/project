package com.ff.admin.master.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.geography.CityDO;
import com.ff.domain.geography.PincodeMasterDO;
import com.ff.domain.geography.StateDO;
import com.ff.domain.serviceOffering.CNPaperWorksDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.domain.serviceOffering.ProductGroupServiceabilityDO;

public interface PincodeMasterDao {

	List<StateDO> getStatesByRegionId(Integer regionId)
			throws CGSystemException;

	List<CityDO> getCitysByStateId(Integer stateId) throws CGSystemException;

	List<ProductDO> getAllProducts() throws CGSystemException;
	public List<CNPaperWorksDO> getAllPaperWorks() throws CGSystemException ;
	
	public ProductGroupServiceabilityDO getProductGroupServiceByGrupId(Integer productGrupId) throws CGSystemException;
	
	public CNPaperWorksDO getCnPaperWorksByPaperWorkId(Integer paperWorkId) throws CGSystemException ;
	
	public boolean saveOrUpdatePincode(PincodeMasterDO pincodeDO);
	
	public PincodeMasterDO searchPincodeDetls(String pincodeNO);
	
	List<CityDO> getCitysByStateIdAndRegionID(Integer stateId,Integer regionId) throws CGSystemException;

}
