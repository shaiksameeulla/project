package com.ff.web.manifest.Utils;

import org.apache.commons.lang.StringUtils;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.ff.manifest.BPLBranchOutManifestDetailsTO;
import com.ff.manifest.BPLOutManifestDoxDetailsTO;
import com.ff.manifest.BPLOutManifestDoxTO;
import com.ff.manifest.BplBranchOutManifestTO;
import com.ff.manifest.BranchOutManifestDoxDetailsTO;
import com.ff.manifest.BranchOutManifestDoxTO;
import com.ff.manifest.BranchOutManifestParcelDetailsTO;
import com.ff.manifest.BranchOutManifestParcelTO;
import com.ff.manifest.MBPLOutManifestDetailsTO;
import com.ff.manifest.MBPLOutManifestTO;
import com.ff.manifest.ManifestFactoryTO;
import com.ff.manifest.OutManifestBaseTO;
import com.ff.manifest.OutManifestDetailBaseTO;
import com.ff.manifest.OutManifestDoxDetailsTO;
import com.ff.manifest.OutManifestDoxTO;
import com.ff.manifest.OutManifestParcelDetailsTO;
import com.ff.manifest.OutManifestParcelTO;
import com.ff.manifest.ThirdPartyBPLDetailsTO;
import com.ff.manifest.ThirdPartyBPLOutManifestTO;
import com.ff.manifest.ThirdPartyOutManifestDoxDetailsTO;
import com.ff.manifest.ThirdPartyOutManifestDoxTO;
import com.ff.web.manifest.constants.OutManifestConstants;

/**
 * A factory for creating OutManifestTO objects.
 */
public abstract class OutManifestTOFactory {
	
	/**
	 * Gets the out manifest base to.
	 *
	 * @param manifestFactoryTO the manifest factory to
	 * @return the out manifest base to
	 */
	public static OutManifestBaseTO getOutManifestBaseTO(
			ManifestFactoryTO manifestFactoryTO) {
		OutManifestBaseTO outManifestBaseTO = null;
		String manifestType = manifestFactoryTO.getManifestType();
		String consgType = manifestFactoryTO.getConsgType();
		if (StringUtils.equalsIgnoreCase(OutManifestConstants.OUT_MANIFEST,
				manifestType)) {
			if (StringUtils.equalsIgnoreCase(
					CommonConstants.CONSIGNMENT_TYPE_DOCUMENT, consgType))
				outManifestBaseTO = new OutManifestDoxTO();
			else if (StringUtils.equalsIgnoreCase(
					CommonConstants.CONSIGNMENT_TYPE_PARCEL, consgType))
				outManifestBaseTO = new OutManifestParcelTO();
		}
		else if (StringUtils.equalsIgnoreCase(OutManifestConstants.BPL_OUT_MANIFEST_TYPE_PURE,
				manifestType)) {
			if (StringUtils.equalsIgnoreCase(
					CommonConstants.CONSIGNMENT_TYPE_DOCUMENT, consgType))
				outManifestBaseTO = new BPLOutManifestDoxTO();
			else if (StringUtils.equalsIgnoreCase(
					CommonConstants.CONSIGNMENT_TYPE_PARCEL, consgType))
				outManifestBaseTO = new OutManifestParcelTO();
		}
		else if (StringUtils.equalsIgnoreCase(OutManifestConstants.BRANCH_MANIFEST,
				manifestType)) {
			if (StringUtils.equalsIgnoreCase(
					CommonConstants.CONSIGNMENT_TYPE_DOCUMENT, consgType))
				outManifestBaseTO = new BranchOutManifestDoxTO();
			else if (StringUtils.equalsIgnoreCase(
					CommonConstants.CONSIGNMENT_TYPE_PARCEL, consgType))
				outManifestBaseTO = new BranchOutManifestParcelTO();
			else if (StringUtils.equalsIgnoreCase(
					OutManifestConstants.BPL_MANIFEST, consgType))
				outManifestBaseTO = new BplBranchOutManifestTO();
		}
		
		else if (StringUtils.equalsIgnoreCase(
				OutManifestConstants.THIRD_PARTY_MANIFEST, manifestType)) {
			if (StringUtils.equalsIgnoreCase(
					CommonConstants.CONSIGNMENT_TYPE_DOCUMENT, consgType))
				outManifestBaseTO = new ThirdPartyOutManifestDoxTO();
			else if (StringUtils.equalsIgnoreCase(
					CommonConstants.CONSIGNMENT_TYPE_PARCEL, consgType))
				outManifestBaseTO = new ThirdPartyBPLOutManifestTO();
		}
		
		else if (StringUtils.equalsIgnoreCase(
				OutManifestConstants.MBPL_OUT_MANIFEST, manifestType)) {
				outManifestBaseTO = new MBPLOutManifestTO();
		}
		
	
		return outManifestBaseTO;
	}
	
	/**
	 * Gets the out manifest detail base to.
	 *
	 * @param manifestFactoryTO the manifest factory to
	 * @return the out manifest detail base to
	 */
	public static OutManifestDetailBaseTO getOutManifestDetailBaseTO(
			ManifestFactoryTO manifestFactoryTO) {
		OutManifestDetailBaseTO outManifestDetailBaseTO = null;
		String manifestType = manifestFactoryTO.getManifestType();
		String consgType = manifestFactoryTO.getConsgType();
		if (StringUtils.equalsIgnoreCase(OutManifestConstants.OUT_MANIFEST,
				manifestType)) {
			if (StringUtils.equalsIgnoreCase(
					CommonConstants.CONSIGNMENT_TYPE_DOCUMENT, consgType))
				outManifestDetailBaseTO = new OutManifestDoxDetailsTO();	
			else if (StringUtils.equalsIgnoreCase(
					CommonConstants.CONSIGNMENT_TYPE_PARCEL, consgType))
				outManifestDetailBaseTO = new OutManifestParcelDetailsTO();
		} else if (StringUtils.equalsIgnoreCase(OutManifestConstants.BPL_OUT_MANIFEST_TYPE_PURE,
				manifestType)) {
			if (StringUtils.equalsIgnoreCase(
					CommonConstants.CONSIGNMENT_TYPE_DOCUMENT, consgType))
				outManifestDetailBaseTO = new BPLOutManifestDoxDetailsTO();
		} else if (StringUtils.equalsIgnoreCase(OutManifestConstants.BRANCH_MANIFEST,
				manifestType)) {
			if (StringUtils.equalsIgnoreCase(
					CommonConstants.CONSIGNMENT_TYPE_DOCUMENT, consgType))
				outManifestDetailBaseTO = new BranchOutManifestDoxDetailsTO();
			else if (StringUtils.equalsIgnoreCase(
					CommonConstants.CONSIGNMENT_TYPE_PARCEL, consgType))
				outManifestDetailBaseTO = new BranchOutManifestParcelDetailsTO();
			else if (StringUtils.equalsIgnoreCase(
					OutManifestConstants.BPL_MANIFEST, consgType))
				outManifestDetailBaseTO = new BPLBranchOutManifestDetailsTO();
		} else if (StringUtils.equalsIgnoreCase(
				OutManifestConstants.THIRD_PARTY_MANIFEST, manifestType)) {
			if (StringUtils.equalsIgnoreCase(
					CommonConstants.CONSIGNMENT_TYPE_DOCUMENT, consgType))
				outManifestDetailBaseTO = new ThirdPartyOutManifestDoxDetailsTO();
			else if (StringUtils.equalsIgnoreCase(
					CommonConstants.CONSIGNMENT_TYPE_PARCEL, consgType))
				outManifestDetailBaseTO = new ThirdPartyBPLDetailsTO();
		}
				
		if (StringUtils.equalsIgnoreCase(OutManifestConstants.MBPL_OUT_MANIFEST,
				manifestType)) {
			
				outManifestDetailBaseTO = new MBPLOutManifestDetailsTO();
			
		}	
		
		/*if (StringUtils.equalsIgnoreCase(OutManifestConstants.BRANCH_MANIFEST,
				manifestType)) {
			
				outManifestDetailBaseTO = new BPLBranchOutManifestDetailsTO();
			
		}*/
		
		if (StringUtils.equalsIgnoreCase(OutManifestConstants.BPL_OUT_MANIFEST,
				manifestType)) {
			
				outManifestDetailBaseTO = new BPLOutManifestDoxDetailsTO();
			
		}
		return outManifestDetailBaseTO;
	}
	
	
}
