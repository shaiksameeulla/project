package com.ff.web.manifest.inmanifest.utils;

import java.util.Calendar;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.domain.CGFactDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.manifest.ManifestDO;
import com.ff.manifest.ManifestBaseTO;
import com.ff.manifest.inmanifest.InManifestTO;
import com.ff.universe.util.UdaanContextService;

/**
 * The Class InManifestUtils.
 * 
 * @author narmdr
 */
public class InManifestUtils {

	private static UdaanContextService udaanContextService;
	
	/**
	 * @param udaanContextService the udaanContextService to set
	 */
	public static void setUdaanContextService(
			UdaanContextService udaanContextService) {
		InManifestUtils.udaanContextService = udaanContextService;
	}

	/**
	 * Reset fetch profile.
	 *
	 * @param manifestBaseTO the manifest base to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public static void resetFetchProfile(ManifestBaseTO manifestBaseTO)
			throws CGBusinessException, CGSystemException {
		if(manifestBaseTO!=null){
			manifestBaseTO.setIsFetchProfileManifestEmbedded(Boolean.FALSE);
			manifestBaseTO.setIsFetchProfileManifestParcel(Boolean.FALSE);
			manifestBaseTO.setIsFetchProfileManifestDox(Boolean.FALSE);
		}		
	}
	
	/**
	 * Sets the header manifest info to child manifest.
	 *
	 * @param headerManifestDO the header manifest do
	 * @param manifestDO the manifest do
	 */
	public static void setHeaderManifestInfoToChildManifest(
			ManifestDO headerManifestDO, ManifestDO manifestDO) {
		
		if(manifestDO.getOriginOffice()==null){
			manifestDO.setOriginOffice(headerManifestDO.getOriginOffice());
		}
		//No need to set header office to childs as its casing problem in tracking
		/*if(manifestDO.getDestOffice()==null){
			manifestDO.setDestOffice(headerManifestDO.getDestOffice());
		}*/
		if(manifestDO.getDestinationCity()==null){
			manifestDO.setDestinationCity(headerManifestDO.getDestinationCity());
		}
		//manifestDO.setManifestDate(headerManifestDO.getManifestDate());
		manifestDO.setManifestDate(DateUtil.getCurrentDate());
		manifestDO.setOperatingOffice(headerManifestDO.getOperatingOffice());
	}

	public static void setEmbeddedInRemarksPositionToDO(InManifestTO inManifestTO,
			ManifestDO manifestDO) {
		manifestDO.setRemarks(inManifestTO.getHeaderRemarks());
		if(!StringUtil.isEmptyInteger(inManifestTO.getManifestEmbeddedIn())){
			manifestDO.setManifestEmbeddedIn(inManifestTO.getManifestEmbeddedIn());
		}
		if(!StringUtil.isEmptyInteger(inManifestTO.getPosition())){
			manifestDO.setPosition(inManifestTO.getPosition());
		}
	}

	/**
	 * Sets the default value to manifest.
	 *
	 * @param manifestDO the new default value to manifest
	 */
	public static void setDefaultValueToManifest(ManifestDO manifestDO) {		
		manifestDO.setManifestStatus(CommonConstants.MANIFEST_STATUS_CLOSED);
		manifestDO.setManifestDirection(CommonConstants.DIRECTION_IN);
		setCreatedByUpdatedBy(manifestDO);
	}

	/**
	 * Sets the created by updated by.
	 *
	 * @param cgFactDO the new created by updated by
	 */
	public static void setCreatedByUpdatedBy(CGFactDO cgFactDO) {
		if (udaanContextService.getUserInfoTO() != null
				&& udaanContextService.getUserInfoTO().getUserto() != null) {
			final Integer userId = udaanContextService.getUserInfoTO()
					.getUserto().getUserId();
			if(StringUtil.isEmptyInteger(cgFactDO.getCreatedBy())){
				cgFactDO.setCreatedBy(userId);
			}
			cgFactDO.setUpdatedBy(userId);
		}
		setCreatedAndUpdatedDate(cgFactDO);
	}

	/**
	 * Sets the created and updated date.
	 *
	 * @param cgFactDO the new created and updated date
	 */
	public static void setCreatedAndUpdatedDate(CGFactDO cgFactDO) {
		if(cgFactDO.getCreatedDate()==null){
			cgFactDO.setCreatedDate(Calendar.getInstance().getTime());
		}
		cgFactDO.setUpdatedDate(Calendar.getInstance().getTime());
	}

	/**
	 * Sets the embedded in remarks position to to.
	 *
	 * @param manifestDO the manifest do
	 * @param inManifestTO the in manifest to
	 */
	public static void setEmbeddedInRemarksPositionToTO(ManifestDO manifestDO,
			InManifestTO inManifestTO) {
		inManifestTO.setHeaderRemarks(manifestDO.getRemarks());
		inManifestTO.setManifestEmbeddedIn(manifestDO.getManifestEmbeddedIn());
		inManifestTO.setPosition(manifestDO.getPosition());
	}
	
	/**
	 * Sets the transgerred flag4 db sync.
	 *
	 * @param cgBaseDO the new transgerred flag4 db sync
	 */
	public static void setTransferredFlag4DBSync(CGBaseDO cgBaseDO) {
		//cgBaseDO.setDtUpdateToCentral(CommonConstants.YES);
		cgBaseDO.setDtToCentral(CommonConstants.YES);
		//cgBaseDO.setDtToBranch(CommonConstants.YES);
	}

	/**
	 * Sets the dest office and city to manifest process.
	 *
	 * @param manifestDO the manifest do
	 * @param manifestProcessDOs the manifest process d os
	 */
/*	public static void setDestOfficeAndCityToManifestProcess(
			ManifestDO manifestDO, List<ManifestProcessDO> manifestProcessDOs) {
		if (!StringUtil.isEmptyList(manifestProcessDOs)) {
			for (ManifestProcessDO manifestProcessDO : manifestProcessDOs) {
				if (manifestDO.getDestOffice() != null
						&& StringUtil.isEmptyInteger(manifestProcessDO
								.getDestOfficeId())) {
					manifestProcessDO.setDestOfficeId(manifestDO
							.getDestOffice().getOfficeId());
				}
				if (manifestDO.getDestinationCity() != null
						&& StringUtil.isEmptyInteger(manifestProcessDO
								.getDestCityId())) {
					manifestProcessDO.setDestCityId(manifestDO
							.getDestinationCity().getCityId());
				}
			}
		}
	}*/

	/**
	 * Sets the manifest process id.
	 *
	 * @param inManifestTO the in manifest to
	 * @param manifestProcessDO the manifest process do
	 */
/*	public static void setManifestProcessId(InManifestTO inManifestTO,
			ManifestProcessDO manifestProcessDO) {
		inManifestTO.setManifestProcessId(manifestProcessDO
				.getManifestProcessId());
	}
*/
	/**
	 * Sets the manifest id.
	 *
	 * @param inManifestTO the in manifest to
	 * @param manifestDO the manifest do
	 */
	public static void setManifestId(InManifestTO inManifestTO,
			ManifestDO manifestDO) {
		inManifestTO.setTwoWayManifestId(manifestDO.getManifestId());
	}

}
