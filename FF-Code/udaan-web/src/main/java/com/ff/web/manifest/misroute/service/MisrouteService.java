package com.ff.web.manifest.misroute.service;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.ManifestRegionTO;
import com.ff.manifest.ManifestStockIssueInputs;
import com.ff.manifest.OutManifestValidate;
import com.ff.manifest.misroute.MisrouteDetailsTO;
import com.ff.manifest.misroute.MisrouteTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.tracking.ProcessTO;

public interface MisrouteService {

	/**
	 * @Desc:getting all regions
	 * @return List<RegionTO>
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<RegionTO> getAllRegions() throws CGBusinessException,
			CGSystemException;

	/**
	 * @Desc:gettinf all offices
	 * @param regionId
	 * @return List<OfficeTO>
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<OfficeTO> getAllOffices(Integer cityId)
			throws CGBusinessException, CGSystemException;

	/**
	 * @Desc:getting office details based on region id
	 * @param regionId
	 * @return OfficeTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public OfficeTO getOfficeDetails(Integer regionId)
			throws CGBusinessException, CGSystemException;

	/**
	 * @Desc:getting consignment type(i.e Dox or PPX)
	 * @return List<ConsignmentTypeTO>
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<ConsignmentTypeTO> getConsignmentType()
			throws CGBusinessException, CGSystemException;

	/**
	 * @Desc:getting the consignment details in grid
	 * @param cnValidateTO
	 * @return MisrouteDetailsTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public MisrouteDetailsTO getInManifestConsgDtls(
			OutManifestValidate cnValidateTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * @Desc:gettimg the misroute details in grid
	 * @param manifestInputTO
	 * @return MisrouteDetailsTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public MisrouteDetailsTO getMisrouteManifestDtls(
			ManifestInputs manifestInputTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * @Desc:saving the misroute manifest no
	 * @param misrouteTO
	 * @return MisrouteTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public Boolean saveOrUpdateOutMisrouteManifest(MisrouteTO misrouteTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * @Desc:Search the details of misroute manifest no
	 * @param manifestTO
	 * @return MisrouteTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public MisrouteTO searchManifestDtls(ManifestInputs manifestTO)
			throws CGBusinessException, CGSystemException;

	/**
	 ** @Desc:gets the cityname by the login office id
	 * @param cityTO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<CityTO> getCitiesByRegion(ManifestRegionTO manifestRegionTO)
			throws CGBusinessException, CGSystemException;

	public String createProcessNumber(ProcessTO processTO, OfficeTO officeTO)
			throws CGBusinessException, CGSystemException;

	public CityTO getCityByCityId(CityTO cityTo) throws CGBusinessException,
			CGSystemException;

	/**
	 * @Desc:getting region details based on region id
	 * @param regionId
	 * @return RegionTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public RegionTO getRegionDetailsByRegionId(Integer regionId)
			throws CGBusinessException, CGSystemException;

	public OutManifestValidate validateConsignment(
			OutManifestValidate cnValidateTO) throws CGBusinessException,
			CGSystemException;

	public MisrouteTO getTotalConsignmentCountForMasterBag(MisrouteTO misrouteTO)
			throws CGBusinessException, CGSystemException;

	public MisrouteTO getTotalConsignmentCountForPacket(
			List<MisrouteDetailsTO> misrouteDetailsTO)
			throws CGBusinessException, CGSystemException;

	public MisrouteTO getTotalConsignmentCountForBag(
			List<MisrouteDetailsTO> misrouteDetailsTO)
			throws CGBusinessException, CGSystemException;

	public ManifestStockIssueInputs validateManifestNo(
			ManifestStockIssueInputs stockIssueInputs, String loginOfficeId)
			throws CGBusinessException, CGSystemException;

	public void isManifestExist(ManifestInputs manifestTO)
			throws CGBusinessException, CGSystemException;

	public Boolean isValiedBagLockNo(String bagLockNo)
			throws CGBusinessException, CGSystemException;

	public void twoWayWriteProcess(ArrayList<Integer> ids,
			ArrayList<String> processNames) throws CGBusinessException,
			CGSystemException;
}
