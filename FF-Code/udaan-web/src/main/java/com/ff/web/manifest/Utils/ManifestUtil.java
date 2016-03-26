package com.ff.web.manifest.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.utility.TwoWayWriteProcessCall;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.consignment.ConsignmentDOXDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.manifest.ComailDO;
import com.ff.domain.manifest.ComailManifestDO;
import com.ff.domain.manifest.ConsignmentManifestDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.manifest.ManifestMappedEmbeddedDO;
import com.ff.manifest.BranchOutManifestDoxDetailsTO;
import com.ff.manifest.BranchOutManifestDoxTO;
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
import com.ff.manifest.misroute.MisrouteDetailsTO;
import com.ff.manifest.misroute.MisrouteTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.web.manifest.constants.OutManifestConstants;

/**
 * The Class ManifestUtil.
 */
public abstract class ManifestUtil {
	// Set ManifestFactoryTO values for out manifest document

	/**
	 * Prepare factory inputs.
	 * 
	 * @param manifestType
	 *            the manifest type
	 * @param consgType
	 *            the consg type
	 * @return the manifest factory to
	 */
	public static ManifestFactoryTO prepareFactoryInputs(String manifestType,
			String consgType) {
		ManifestFactoryTO manifestFactoryTO = new ManifestFactoryTO();
		manifestFactoryTO.setManifestType(manifestType);
		manifestFactoryTO.setConsgType(consgType);
		return manifestFactoryTO;
	}

	// Set ManifestFactoryTO values for out manifest parcel
	/**
	 * Gets the out manifest parcel factory.
	 * 
	 * @return the out manifest parcel factory
	 */
	public static ManifestFactoryTO getOutManifestParcelFactory() {
		ManifestFactoryTO manifestFactoryTO = new ManifestFactoryTO();
		manifestFactoryTO.setManifestType(OutManifestConstants.OUT_MANIFEST);
		manifestFactoryTO.setConsgType(CommonConstants.CONSIGNMENT_TYPE_PARCEL);
		return manifestFactoryTO;
	}

	// Set ManifestFactoryTO values for out manifest parcel
	/**
	 * Gets the out manifest third party bpl factory.
	 * 
	 * @return the out manifest third party bpl factory
	 */
	public static ManifestFactoryTO getOutManifestThirdPartyBPLFactory() {
		ManifestFactoryTO manifestFactoryTO = new ManifestFactoryTO();
		manifestFactoryTO
				.setManifestType(OutManifestConstants.THIRD_PARTY_MANIFEST);
		manifestFactoryTO.setConsgType(CommonConstants.CONSIGNMENT_TYPE_PARCEL);
		return manifestFactoryTO;
	}

	// Set ManifestFactoryTO values for Third Party Out Manifest
	/**
	 * Gets the out manifest third party dox factory.
	 * 
	 * @return the out manifest third party dox factory
	 */
	public static ManifestFactoryTO getOutManifestThirdPartyDoxFactory() {
		ManifestFactoryTO manifestFactoryTO = new ManifestFactoryTO();
		manifestFactoryTO
				.setManifestType(OutManifestConstants.THIRD_PARTY_MANIFEST);
		manifestFactoryTO
				.setConsgType(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
		return manifestFactoryTO;
	}

	// Set ManifestFactoryTO values for MBPL Out Manifest
	/**
	 * Gets the mBPL out manifest dox factory.
	 * 
	 * @return the mBPL out manifest dox factory
	 */
	public static ManifestFactoryTO getMBPLOutManifestDoxFactory() {
		ManifestFactoryTO manifestFactoryTO = new ManifestFactoryTO();
		manifestFactoryTO
				.setManifestType(OutManifestConstants.MBPL_OUT_MANIFEST);
		return manifestFactoryTO;
	}

	// Set ManifestFactoryTO values for Branch out manifest Dox
	/**
	 * Gets the branch out manifest dox factory.
	 * 
	 * @return the branch out manifest dox factory
	 */
	public static ManifestFactoryTO getBranchOutManifestDoxFactory() {
		ManifestFactoryTO manifestFactoryTO = new ManifestFactoryTO();
		manifestFactoryTO.setManifestType(OutManifestConstants.BRANCH_MANIFEST);
		manifestFactoryTO
				.setConsgType(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
		return manifestFactoryTO;
	}

	// Set ManifestFactoryTO values for Branch out manifest Dox
	/**
	 * Gets the bpl out manifest dox factory.
	 * 
	 * @return the bpl out manifest dox factory
	 */
	public static ManifestFactoryTO getBplOutManifestDoxFactory() {
		ManifestFactoryTO manifestFactoryTO = new ManifestFactoryTO();
		manifestFactoryTO
				.setManifestType(OutManifestConstants.BPL_OUT_MANIFEST_TYPE_PURE);
		manifestFactoryTO
				.setConsgType(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
		return manifestFactoryTO;
	}

	// Set ManifestFactoryTO values for BPL Branch out manifest
	/**
	 * Gets the bPL branch out manifest factory.
	 * 
	 * @return the bPL branch out manifest factory
	 */
	public static ManifestFactoryTO getBPLBranchOutManifestFactory() {
		ManifestFactoryTO manifestFactoryTO = new ManifestFactoryTO();
		manifestFactoryTO.setManifestType(OutManifestConstants.BRANCH_MANIFEST);
		manifestFactoryTO.setConsgType(OutManifestConstants.BPL_MANIFEST);
		return manifestFactoryTO;
	}

	// Set ManifestFactoryTO values for Branch out manifest Parcel
	/**
	 * Gets the branch out manifest parcel factory.
	 * 
	 * @return the branch out manifest parcel factory
	 */
	public static ManifestFactoryTO getBranchOutManifestParcelFactory() {
		ManifestFactoryTO manifestFactoryTO = new ManifestFactoryTO();
		manifestFactoryTO.setManifestType(OutManifestConstants.BRANCH_MANIFEST);
		manifestFactoryTO.setConsgType(CommonConstants.CONSIGNMENT_TYPE_PARCEL);
		return manifestFactoryTO;
	}

	// Setting ConsignmentManifestDO
	/**
	 * Sets the consignment manifest dtls.
	 * 
	 * @param outmanifestTOs
	 *            the outmanifest t os
	 * @return the sets the
	 */
	public static Set<ConsignmentManifestDO> setConsignmentManifestDtls(
			List<? extends OutManifestDetailBaseTO> outmanifestTOs) {
		Set<ConsignmentManifestDO> cnManifestSet = new HashSet<>(
				outmanifestTOs.size());
		for (OutManifestDetailBaseTO outmanifestDtlTO : outmanifestTOs) {
			if (!StringUtil.isNull(outmanifestDtlTO)) {
				if (StringUtils.isNotEmpty(outmanifestDtlTO.getConsgNo())) {
					ConsignmentManifestDO cnManifestedDO = new ConsignmentManifestDO();
					if (!StringUtil.isEmptyInteger(outmanifestDtlTO
							.getConsgManifestedId())) {
						cnManifestedDO
								.setConsignmentManifestId(outmanifestDtlTO
										.getConsgManifestedId());
					}
					ConsignmentDO cnDO = new ConsignmentDO();
					cnDO.setConsgId(outmanifestDtlTO.getConsgId());
					cnManifestedDO.setConsignment(cnDO);
					if (!StringUtil.isNull(outmanifestDtlTO.getManifestId())) {
						ManifestDO manifest = new ManifestDO();
						manifest.setManifestId(outmanifestDtlTO.getManifestId());
						cnManifestedDO.setManifest(manifest);
					}
					//cnManifestedDO.setPosition(outmanifestDtlTO.getPosition());
					cnManifestSet.add(cnManifestedDO);
				}
			}
		}
		return cnManifestSet;
	}

	/**
	 * 
	 * @param outManifestDoxTO
	 * @return
	 */
	public static Set<ConsignmentManifestDO> setConsignmentManifestDtls(
			OutManifestDoxTO outManifestDoxTO) {
		Set<ConsignmentManifestDO> cnManifestSet = new HashSet<>(
				outManifestDoxTO.getOutManifestDoxDetailTOs().size());
		for (OutManifestDoxDetailsTO outManifestDoxDetailsTO : outManifestDoxTO
				.getOutManifestDoxDetailTOs()) {
			if (StringUtils.isNotEmpty(outManifestDoxDetailsTO.getConsgNo())) {
				ConsignmentManifestDO cnManifestedDO = new ConsignmentManifestDO();
				if (!StringUtil.isEmptyInteger(outManifestDoxDetailsTO
						.getConsgManifestedId())) {
					cnManifestedDO
							.setConsignmentManifestId(outManifestDoxDetailsTO
									.getConsgManifestedId());
				}
				ConsignmentDO cnDO = new ConsignmentDO();
				cnDO.setConsgId(outManifestDoxDetailsTO.getConsgId());
				cnManifestedDO.setConsignment(cnDO);
				if (!StringUtil
						.isEmptyInteger(outManifestDoxTO.getManifestId())) {
					ManifestDO manifest = new ManifestDO();
					manifest.setManifestId(outManifestDoxTO.getManifestId());
					cnManifestedDO.setManifest(manifest);
				}
				cnManifestSet.add(cnManifestedDO);
			}
		}
		return cnManifestSet;
	}

	/*
	 * public static Set<ConsignmentDO> setConsignmentDtls( OutManifestDoxTO
	 * outManifestDoxTO, Set<ConsignmentDO> consignments) { Set<ConsignmentDO>
	 * consigSet = new HashSet<ConsignmentDO>(
	 * outManifestDoxTO.getOutManifestDoxDetailTOs().size()); for
	 * (OutManifestDoxDetailsTO outManifestDoxDetailsTO : outManifestDoxTO
	 * .getOutManifestDoxDetailTOs()) { for (ConsignmentDO consgDO :
	 * consignments) { if (StringUtil.equals(outManifestDoxTO.getStatus(), "U")
	 * && StringUtil.equals( outManifestDoxDetailsTO.getConsgNo(),
	 * consgDO.getConsgNo())) { consigSet.add(consgDO); } } } return consigSet;
	 * }
	 */

	// Setting ConsignmentManifestDO FOr Third Party Out Manifest Dox
	/**
	 * Sets the consignment manifest dtls for tpdx.
	 * 
	 * @param thirdPartyOutManifestDoxTO
	 *            the third party out manifest dox to
	 * @return the sets the
	 */
	public static Set<ConsignmentManifestDO> setConsignmentManifestDtlsForTPDX(
			ThirdPartyOutManifestDoxTO thirdPartyOutManifestDoxTO) {
		Set<ConsignmentManifestDO> cnManifestSet = null;
		if (thirdPartyOutManifestDoxTO.getConsgIds() != null
				&& thirdPartyOutManifestDoxTO.getConsgIds().length > 0) {
			cnManifestSet = new HashSet<>(thirdPartyOutManifestDoxTO
					.getThirdPartyOutManifestDoxDetailsToList().size());
			for (ThirdPartyOutManifestDoxDetailsTO thirdPartyOutManifestDoxDetailsTO : thirdPartyOutManifestDoxTO
					.getThirdPartyOutManifestDoxDetailsToList()) {
				if (!StringUtil
						.isEmptyInteger(thirdPartyOutManifestDoxDetailsTO
								.getConsgId())) {
					ConsignmentManifestDO cnManifestedDO = new ConsignmentManifestDO();
					if (!StringUtil
							.isEmptyInteger(thirdPartyOutManifestDoxDetailsTO
									.getConsgManifestedId())) {
						cnManifestedDO
								.setConsignmentManifestId(thirdPartyOutManifestDoxDetailsTO
										.getConsgManifestedId());
					}
					ConsignmentDO cnDO = new ConsignmentDO();
					cnDO.setConsgId(thirdPartyOutManifestDoxDetailsTO
							.getConsgId());
					cnManifestedDO.setConsignment(cnDO);
					cnManifestSet.add(cnManifestedDO);
				}
			}
		}
		return cnManifestSet;
	}

	// Setting ConsignmentManifestDO FOr Third Party Out Manifest BPL
	/**
	 * Sets the consignment manifest dtls for tpbp.
	 * 
	 * @param thirdPartyOutManifestBPLTO
	 *            the third party out manifest bplto
	 * @return the list
	 */
	public static List<ConsignmentManifestDO> setConsignmentManifestDtlsForTPBP(
			ThirdPartyBPLOutManifestTO thirdPartyOutManifestBPLTO) {
		List<ConsignmentManifestDO> cnManifestList = null;
		if (thirdPartyOutManifestBPLTO.getConsgIds() != null
				&& thirdPartyOutManifestBPLTO.getConsgIds().length > 0) {
			cnManifestList = new ArrayList<>(
					thirdPartyOutManifestBPLTO.getConsgNos().length);
			for (int i = 0; i < thirdPartyOutManifestBPLTO.getConsgNos().length; i++) {
				ThirdPartyBPLDetailsTO thirdPartyBPLDetailsTO = thirdPartyOutManifestBPLTO
						.getThirdPartyBPLDetailsListTO().get(i);
				if (!StringUtil.isEmptyInteger(thirdPartyBPLDetailsTO
						.getConsgId())) {
					ConsignmentManifestDO cnManifestedDO = new ConsignmentManifestDO();
					if (!StringUtil.isEmptyInteger(thirdPartyBPLDetailsTO
							.getConsgManifestedId())) {
						cnManifestedDO
								.setConsignmentManifestId(thirdPartyBPLDetailsTO
										.getConsgManifestedId());
					}
					ConsignmentDO cnDO = new ConsignmentDO();
					cnDO.setConsgId(thirdPartyBPLDetailsTO.getConsgId());
					cnManifestedDO.setConsignment(cnDO);
					ManifestDO manifestDo = new ManifestDO();
					manifestDo.setManifestId(thirdPartyOutManifestBPLTO
							.getManifestId());
					if (manifestDo != null) {
						cnManifestedDO.setManifest(manifestDo);
					}
					// Added on 3008 for delete consg issue
					if (!StringUtil.isEmptyInteger(thirdPartyBPLDetailsTO
							.getConsgManifestedId())) {
						cnManifestedDO
								.setConsignmentManifestId(thirdPartyBPLDetailsTO
										.getConsgManifestedId());
					}
					cnManifestList.add(cnManifestedDO);
				}
			}
		}
		return cnManifestList;
	}

	// Setting ComailManifestDO
	/**
	 * Sets the co mail manifest dtls.
	 * 
	 * @param baseTO
	 *            the base to
	 * @param outmanifestTOs
	 *            the outmanifest t os
	 * @return the sets the
	 */
	public static Set<ComailManifestDO> setCoMailManifestDtls(
			OutManifestBaseTO baseTO,
			List<? extends OutManifestDetailBaseTO> outmanifestTOs) {
		Set<ComailManifestDO> comailManifestSet = new HashSet<>(
				outmanifestTOs.size());
		Integer originOfficeId = baseTO.getLoginOfficeId();
		Integer destOfficeId = baseTO.getDestinationOfficeId();
		for (OutManifestDetailBaseTO outmanifestDtlTO : outmanifestTOs) {
			if (StringUtils.isNotEmpty(outmanifestDtlTO.getComailNo())) {
				ComailManifestDO comailMnfstdDO = new ComailManifestDO();
				if (!StringUtil.isEmptyInteger(outmanifestDtlTO
						.getComailManifestedId())) {
					comailMnfstdDO.setCoMailManifestId(outmanifestDtlTO
							.getComailManifestedId());
				}
				ComailDO comailDO = new ComailDO();
				if (!StringUtil.isEmptyInteger(outmanifestDtlTO.getComailId())) {
					comailDO.setCoMailId(outmanifestDtlTO.getComailId());
				}
				comailDO.setCoMailNo(outmanifestDtlTO.getComailNo());
				comailDO.setOriginOffice(originOfficeId);
				if (!StringUtil.isEmptyInteger(destOfficeId))
					comailDO.setDestinationOffice(destOfficeId);
				comailMnfstdDO.setComailDO(comailDO);
				comailManifestSet.add(comailMnfstdDO);
			}
		}

		return comailManifestSet;
	}

	// Setting Consignment Domain Converter
	/**
	 * Consignment domain converter.
	 * 
	 * @param consignmentDO
	 *            the consignment do
	 * @param manifestFactoryTO
	 *            the manifest factory to
	 * @return the out manifest detail base to
	 */
	public static OutManifestDetailBaseTO consignmentDomainConverter(
			ConsignmentDO consignmentDO, ManifestFactoryTO manifestFactoryTO) {
		OutManifestDetailBaseTO outManifestDtlTO = null;
		if (!StringUtil.isNull(consignmentDO)) {
			outManifestDtlTO = OutManifestTOFactory
					.getOutManifestDetailBaseTO(manifestFactoryTO);
			outManifestDtlTO.setConsgId(consignmentDO.getConsgId());
			outManifestDtlTO.setConsgNo(consignmentDO.getConsgNo());
			outManifestDtlTO.setWeight(consignmentDO.getFinalWeight());
			outManifestDtlTO.setBkgWeight(consignmentDO.getFinalWeight());
			PincodeDO pincodeDO = consignmentDO.getDestPincodeId();
			if (!StringUtil.isNull(pincodeDO)) {
				outManifestDtlTO.setPincode(pincodeDO.getPincode());
				outManifestDtlTO.setPincodeId(pincodeDO.getPincodeId());
			}
		}
		return outManifestDtlTO;
	}

	public static OutManifestDetailBaseTO consignmentDomainConverter(
			ConsignmentDOXDO consignmentDO, ManifestFactoryTO manifestFactoryTO) {
		OutManifestDetailBaseTO outManifestDtlTO = null;
		if (!StringUtil.isNull(consignmentDO)) {
			outManifestDtlTO = OutManifestTOFactory
					.getOutManifestDetailBaseTO(manifestFactoryTO);
			outManifestDtlTO.setConsgId(consignmentDO.getConsgId());
			outManifestDtlTO.setConsgNo(consignmentDO.getConsgNo());
			outManifestDtlTO.setWeight(consignmentDO.getFinalWeight());
			outManifestDtlTO.setBkgWeight(consignmentDO.getFinalWeight());
			PincodeDO pincodeDO = consignmentDO.getDestPincodeId();
			if (!StringUtil.isNull(pincodeDO)) {
				outManifestDtlTO.setPincode(pincodeDO.getPincode());
				outManifestDtlTO.setPincodeId(pincodeDO.getPincodeId());
			}
		}
		return outManifestDtlTO;
	}
	
	/**
	 * Comail domain converter.
	 * 
	 * @param comailDO
	 *            the comail do
	 * @param manifestFactoryTO
	 *            the manifest factory to
	 * @return the out manifest detail base to
	 */
	public static OutManifestDetailBaseTO comailDomainConverter(
			ComailDO comailDO, ManifestFactoryTO manifestFactoryTO) {
		OutManifestDetailBaseTO outManifestDtlTO = null;
		if (!StringUtil.isNull(comailDO)) {
			outManifestDtlTO = OutManifestTOFactory
					.getOutManifestDetailBaseTO(manifestFactoryTO);
			outManifestDtlTO.setComailId(comailDO.getCoMailId());
			outManifestDtlTO.setComailNo(comailDO.getCoMailNo());
			// outManifestDtlTO.setWeight(comailDO.getManifestWeight());
		}
		return outManifestDtlTO;
	}

	// Setting ConsignmentManifestDO FOr Branch Out Manifest Dox
	/**
	 * Sets the consignment manifest dtls for bout.
	 * 
	 * @param branchOutManifestDoxTO
	 *            the branch out manifest dox to
	 * @return the sets the
	 */
	public static Set<ConsignmentManifestDO> setConsignmentManifestDtlsForBOUT(
			BranchOutManifestDoxTO branchOutManifestDoxTO) {
		Set<ConsignmentManifestDO> cnManifestSet = null;
		if (branchOutManifestDoxTO.getConsgIds() != null
				&& branchOutManifestDoxTO.getConsgIds().length > 0) {
			cnManifestSet = new HashSet<>(branchOutManifestDoxTO
					.getBranchOutManifestDoxDetailsTOList().size());
			for (BranchOutManifestDoxDetailsTO branchOutManifestDoxDetailsTO : branchOutManifestDoxTO
					.getBranchOutManifestDoxDetailsTOList()) {
				if (!StringUtil.isNull(branchOutManifestDoxDetailsTO)) {
					if (!StringUtil
							.isEmptyInteger(branchOutManifestDoxDetailsTO
									.getConsgId())) {
						ConsignmentManifestDO cnManifestedDO = new ConsignmentManifestDO();
						ConsignmentDO cnDO = new ConsignmentDO();
						cnDO.setConsgId(branchOutManifestDoxDetailsTO
								.getConsgId());
						cnManifestedDO.setConsignment(cnDO);
						if (!StringUtil
								.isEmptyInteger(branchOutManifestDoxDetailsTO
										.getConsgManifestedId())) {
							cnManifestedDO
									.setConsignmentManifestId(branchOutManifestDoxDetailsTO
											.getConsgManifestedId());
						}
						cnManifestSet.add(cnManifestedDO);
					}
				}
			}
		}
		return cnManifestSet;
	}

	public static List<ConsignmentManifestDO> setConsignmentManifestDtlsForMisroute(
			MisrouteTO misrouteTO) {
		List<ConsignmentManifestDO> cnManifestList = null;
		if (misrouteTO.getScannedItemIds() != null
				&& misrouteTO.getScannedItemIds().length > 0) {
			cnManifestList = new ArrayList<>(
					misrouteTO.getScannedItemNos().length);
			for (int i = 0; i < misrouteTO.getScannedItemNos().length; i++) {
				MisrouteDetailsTO misrouteDetailsTO = misrouteTO
						.getMisrouteDetailsTO().get(i);
				if (!StringUtil.isEmptyInteger(misrouteDetailsTO
						.getScannedItemId())) {
					ConsignmentManifestDO cnManifestedDO = new ConsignmentManifestDO();
					ConsignmentDO cnDO = new ConsignmentDO();
					cnDO.setConsgId(misrouteDetailsTO.getScannedItemId());
					cnManifestedDO.setConsignment(cnDO);
					/*cnManifestedDO.setRemarks(misrouteDetailsTO.getRemarks());
					cnManifestedDO.setPosition(misrouteDetailsTO.getPosition());*/
					ManifestDO manifestDo = new ManifestDO();
					manifestDo.setManifestId(misrouteTO.getMisrouteId());
					if (!StringUtil.isEmptyInteger(misrouteTO
							.getConsgManifestedIds()[i])) {
						cnManifestedDO.setConsignmentManifestId(misrouteTO
								.getConsgManifestedIds()[i]);
					}
					if (manifestDo != null) {
						cnManifestedDO.setManifest(manifestDo);
					}
					cnManifestList.add(cnManifestedDO);
				}
			}
		}
		return cnManifestList;
	}

	// Setting ManifestMappedEmbeddedDO
	/**
	 * Sets the embedded manifest dtls.
	 * 
	 * @param outmanifestTOs
	 *            the outmanifest t os
	 * @return the sets the
	 */
	/*
	 * public static Set<ManifestMappedEmbeddedDO> setEmbeddedManifestDtls(
	 * OutManifestBaseTO outmanifestTO) { Set<ManifestMappedEmbeddedDO>
	 * embeddedManifestDOList = null; if (outmanifestTO.getManifestIds() !=
	 * null&& outmanifestTO.getManifestIds().length > 0) {
	 * embeddedManifestDOList = new
	 * HashSet<>(outmanifestTO.getManifestIds().length); for (int
	 * i=0;i<outmanifestTO.getManifestIds().length;i++) {
	 * 
	 * if (!StringUtil.isEmptyInteger(outmanifestTO.getManifestId())) {
	 * 
	 * ManifestMappedEmbeddedDO embeddedManifestDO = new
	 * ManifestMappedEmbeddedDO(); ManifestDO manifestDO = new ManifestDO();
	 * manifestDO.setManifestId(outmanifestTO.getManifestId());
	 * embeddedManifestDO.setEmbeddedIn(manifestDO); if(manifestDO!=null){
	 * embeddedManifestDO.setManifestId(outmanifestTO.getManifestIds()[i]);}
	 * embeddedManifestDO.setPosition(outmanifestTO.getPosition()[i]);
	 * embeddedManifestDOList.add(embeddedManifestDO); } } } return
	 * embeddedManifestDOList; }
	 */

	public static List<ManifestMappedEmbeddedDO> setEmbeddedManifestDtls(
			OutManifestBaseTO outmanifestTO) {
		List<ManifestMappedEmbeddedDO> embeddedManifestDOList = null;
		if (outmanifestTO.getManifestIds() != null
				&& outmanifestTO.getManifestIds().length > 0) {
			embeddedManifestDOList = new ArrayList(
					outmanifestTO.getManifestIds().length);
			for (int i = 0; i < outmanifestTO.getManifestIds().length; i++) {

				if (!StringUtil
						.isEmptyInteger(outmanifestTO.getManifestIds()[i])) {

					ManifestMappedEmbeddedDO embeddedManifestDO = new ManifestMappedEmbeddedDO();
					ManifestDO manifestDO = new ManifestDO();
					manifestDO.setManifestId(outmanifestTO.getManifestId());
					embeddedManifestDO.setEmbeddedIn(manifestDO);
					embeddedManifestDO.setManifestId(outmanifestTO
							.getManifestIds()[i]);
					/*
					 * if(manifestDO!=null){ }
					 */
					embeddedManifestDO
							.setPosition(outmanifestTO.getPosition()[i]);
					if (!StringUtil.isEmptyInteger(outmanifestTO
							.getManifestMappedEmbeddeId()[i])) {
						embeddedManifestDO.setMapId(outmanifestTO
								.getManifestMappedEmbeddeId()[i]);
					}
					embeddedManifestDOList.add(embeddedManifestDO);
				}
			}
		}
		return embeddedManifestDOList;
	}

	public static List<ManifestMappedEmbeddedDO> setEmbeddedManifestDtlsForMisroute(
			MisrouteTO misrouteTO) {
		List<ManifestMappedEmbeddedDO> embeddedManifestDOList = null;
		if (misrouteTO.getScannedItemIds() != null
				&& misrouteTO.getScannedItemIds().length > 0) {
			embeddedManifestDOList = new ArrayList(
					misrouteTO.getScannedItemIds().length);
			for (int i = 0; i < misrouteTO.getScannedItemIds().length; i++) {

				if (!StringUtil
						.isEmptyInteger(misrouteTO.getScannedItemIds()[i])) {

					ManifestMappedEmbeddedDO embeddedManifestDO = new ManifestMappedEmbeddedDO();
					ManifestDO manifestDO = new ManifestDO();
					manifestDO.setManifestId(misrouteTO.getMisrouteId());
					embeddedManifestDO.setEmbeddedIn(manifestDO);
					embeddedManifestDO.setManifestId(misrouteTO
							.getScannedItemIds()[i]);
					embeddedManifestDO
							.setPosition(misrouteTO.getPositions()[i]);
					if (!StringUtil.isEmptyInteger(misrouteTO
							.getManifestMappedEmbeddeId()[i])) {
						embeddedManifestDO.setMapId(misrouteTO
								.getManifestMappedEmbeddeId()[i]);
					}
					embeddedManifestDOList.add(embeddedManifestDO);
				}
			}

		}

		return embeddedManifestDOList;
	}
	
	
	
	/**
	 * Ami Added on 2009 starts
	 * @param outmanifestParcelTO
	 * @return
	 */
	public static Set<ConsignmentManifestDO> setConsignmentManifestDtls(
			OutManifestParcelTO outmanifestParcelTO) {
		Set<ConsignmentManifestDO> cnManifestSet = new HashSet<>(
				outmanifestParcelTO.getOutManifestParcelDetailsList().size());
		for (OutManifestParcelDetailsTO outManifestParcelDetailsTO : outmanifestParcelTO.getOutManifestParcelDetailsList()) {
			if (StringUtils.isNotEmpty(outManifestParcelDetailsTO.getConsgNo())) {
				ConsignmentManifestDO cnManifestedDO = new ConsignmentManifestDO();
				/*if (!StringUtil.isEmptyInteger(outManifestDoxDetailsTO
						.getConsgManifestedId())) {
					cnManifestedDO
							.setConsignmentManifestId(outManifestDoxDetailsTO
									.getConsgManifestedId());
				}*/
				ConsignmentDO cnDO = new ConsignmentDO();
				cnDO.setConsgId(outManifestParcelDetailsTO.getConsgId());
				cnManifestedDO.setConsignment(cnDO);
				if (!StringUtil
						.isEmptyInteger(outmanifestParcelTO.getManifestId())) {
					ManifestDO manifest = new ManifestDO();
					manifest.setManifestId(outmanifestParcelTO.getManifestId());
					cnManifestedDO.setManifest(manifest);
				}
				/*cnManifestedDO.setPosition(outManifestParcelDetailsTO
						.getPosition());*/
				cnManifestSet.add(cnManifestedDO);
			}
		}
		return cnManifestSet;
	}
	
	/**
	 * To convert consignment rate component to CNPricing details
	 * 
	 * @param rateOutput
	 * @return consgRateDtls
	 */
	public static CNPricingDetailsTO setUpRateCompoments(
			ConsignmentRateCalculationOutputTO rateOutput) {
		CNPricingDetailsTO consgRateDtls = new CNPricingDetailsTO();
		if (!StringUtil.isNull(rateOutput)) {
			consgRateDtls.setAirportHandlingChg(rateOutput
					.getAirportHandlingCharge());
			consgRateDtls.setFinalPrice(rateOutput.getGrandTotalIncludingTax());
			consgRateDtls.setCodAmt(rateOutput.getCodAmount());
			consgRateDtls.setFuelChg(rateOutput.getFuelSurcharge());
			consgRateDtls.setServiceTax(rateOutput.getServiceTax());
			consgRateDtls.setRiskSurChg(rateOutput.getRiskSurcharge());
			consgRateDtls.setTopayChg(rateOutput.getGrandTotalIncludingTax());
			consgRateDtls.setEduCessChg(rateOutput.getEducationCess());
			consgRateDtls.setHigherEduCessChg(rateOutput
					.getHigherEducationCess());
			consgRateDtls.setFreightChg(rateOutput.getSlabRate());
		}
		return consgRateDtls;
	}
	
	public static void validateAndSetTwoWayWriteFlag(CGBaseDO cgBaseDO) {
		if(TwoWayWriteProcessCall.isTwoWayWriteEnabled()){
			cgBaseDO.setDtToCentral(CommonConstants.YES);
		}
	}
	

}
