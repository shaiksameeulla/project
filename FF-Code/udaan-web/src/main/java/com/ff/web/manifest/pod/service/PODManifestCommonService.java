/**
 * 
 */
package com.ff.web.manifest.pod.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.manifest.ConsignmentManifestDO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.manifest.pod.PODManifestDtlsTO;
import com.ff.manifest.pod.PODManifestTO;
import com.ff.organization.OfficeTO;
import com.ff.organization.OfficeTypeTO;
import com.ff.tracking.ProcessTO;

/**
 * @author nkattung
 * 
 */
public interface PODManifestCommonService {
	/**
	 * @param consignment
	 * @param officeId
	 * @param podManifestType
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public PODManifestDtlsTO getDeliverdConsgDtls(String consignment,
			Integer officeId, String podManifestType)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the all regions.
	 * 
	 * @return the all regions
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<RegionTO> getAllRegions() throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the cities by region.
	 * 
	 * @param regionTO
	 *            the region to
	 * @return the cities by region
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<CityTO> getCitiesByRegion(RegionTO regionTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the all offices by city.
	 * 
	 * @param cityId
	 *            the city id
	 * @return the all offices by city
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<OfficeTO> getAllOfficesByCity(Integer cityId)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the process.
	 * 
	 * @param process
	 *            the process
	 * @return the process
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public ProcessTO getProcess(ProcessTO process) throws CGSystemException,
			CGBusinessException;

	/**
	 * Checks if is manifest number used.
	 * 
	 * @param Manifest
	 *            Number
	 * @return true, Manifest Number used
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 * */
	public boolean isManifestExists(String manifestNo,
			String manifestDirection, String manifestType,
			String manifestPorcessCode) throws CGBusinessException,
			CGSystemException;

	/**
	 * Search POD Manifest Details
	 * 
	 * @param manifestNo
	 * @param manifestType
	 * @param processCode
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public PODManifestTO searchManifetsDtls(String manifestNo,
			String manifestType, String processCode, Integer orginOfficeId)
			throws CGBusinessException, CGSystemException;

	/**
	 * Check for is consignment is already manifested or not
	 * 
	 * @param consgNumber
	 * @param manifestDirection
	 * @param manifestType
	 * @param manifestPorcessCode
	 * @return boolean
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public boolean isConsgnNoManifested(String consgNumber,
			String manifestDirection, String manifestType,
			String manifestPorcessCode) throws CGSystemException,
			CGBusinessException;

	public OfficeTO getOfficeDetailsById(Integer officeId)
			throws CGBusinessException, CGSystemException;

	public PODManifestTO searchOutgoingPODManifetsDtls(String manifestNo,
			String podManifestOut, String processPod, Integer orginOfficeId)throws CGBusinessException, CGSystemException;

	public List<OfficeTO> getAllHubsByCity(Integer cityId,String officeTypeCode)
			throws CGBusinessException, CGSystemException;
	
	public PODManifestDtlsTO getDeliverdConsgDtlsForDestBranchToDestHub(String consignment,
			Integer officeId, String podManifestType,String loggdOffcType)
			throws CGBusinessException, CGSystemException;
	
	public List<CityTO> getCitiesForLoggdInBranchOffice(Integer loggdInbranchOfficeId)
			throws CGBusinessException, CGSystemException;
	
	public List<OfficeTypeTO> getOfficeTypeList(List<String> officeTypeCodes)
			throws CGBusinessException, CGSystemException; 
	
	public List<OfficeTO> getAllBranchesByCity(Integer cityId, Integer officeTypeId)
			throws CGBusinessException, CGSystemException ;
	
	public List<ConsignmentManifestDO> isConsgnNoManifestedToDestHub(String consignment,
			String podManifestType, String manifestProcessCode,Integer officeId) throws CGSystemException;
	
	public PODManifestDtlsTO getPODInManifstdConsingmentDtls(String consignmentNo) throws CGSystemException, CGBusinessException;
}
