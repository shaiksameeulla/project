package com.ff.admin.master.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.business.LoadMovementVendorDO;
import com.ff.domain.business.VendorOfficeMapDO;
import com.ff.domain.organization.OfficeDO;

public interface VendorMappingDAO {

	public LoadMovementVendorDO getVendorDetails(String vendorName) throws CGSystemException;
	public boolean saveOrUpdateVendor(LoadMovementVendorDO vendorDO)throws CGSystemException;
	public boolean saveOrUpdateVendorOffice( List<VendorOfficeMapDO> vendorOfficeDOList)throws CGSystemException;
	public List<VendorOfficeMapDO> alreadtExistVedorOffice(List<VendorOfficeMapDO> vendorOfficeDOList )throws CGSystemException;
	
	/**
	 * Gets all vendors from the DB
	 * 
	 * @return
	 * @throws CGSystemException
	 */
	public List<String> getAllVendorsList()throws CGSystemException;
	
	/**
	 * getting All selected offices against city of that vendor
	 * 
	 * @param regionId
	 * @param cityId
	 * @param vendorId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<OfficeDO> getSelectedOffices(Integer regionId,Integer cityId,Integer vendorId)throws CGBusinessException, CGSystemException;
	
	public void deleteVendorOfficeMap(Integer regionMappedId, List<Integer> branchIds)throws CGBusinessException, CGSystemException;
}
