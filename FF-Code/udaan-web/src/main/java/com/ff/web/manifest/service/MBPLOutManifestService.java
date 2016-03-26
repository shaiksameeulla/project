package com.ff.web.manifest.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.manifest.MBPLOutManifestDetailsTO;
import com.ff.manifest.MBPLOutManifestTO;
import com.ff.manifest.ManifestInputs;
import com.ff.routeserviced.TransshipmentRouteTO;

/**
 * @author preegupt
 * 
 */
public interface MBPLOutManifestService {

	/**
	 * @Desc For searching the master bag and its details
	 * @param manifestTO
	 * @return MBPLOutManifestTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public MBPLOutManifestTO searchManifestDtls(ManifestInputs manifestTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * @Desc : For getting the details of BPL No entered in grid
	 * @param manifestTOs
	 * @return MBPLOutManifestTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public MBPLOutManifestTO getManifestDtls(ManifestInputs manifestTOs)
			throws CGBusinessException, CGSystemException;

	/**
	 * @Desc : For saving and closing the master bag
	 * @param mbplOutManifestTO
	 * @return MBPLOutManifestTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public Boolean saveOrUpdateOutManifestMBPL(
			MBPLOutManifestTO mbplOutManifestTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * @Desc : For getting the serviced city in Grid
	 * @param transshipmentTO
	 * @return Boolean
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public Integer getRouteIdByOriginCityIdAndDestCityId(Integer originCityId,
			Integer destCityId) throws CGBusinessException, CGSystemException;

	public MBPLOutManifestTO getTotalConsignmentCount(
			List<MBPLOutManifestDetailsTO> mbplOutManifestDetailsTOsList)
			throws CGBusinessException, CGSystemException;

	public Boolean getRouteServicibility(
			TransshipmentRouteTO transshipmentRouteTO)
			throws CGBusinessException, CGSystemException;

}
