package com.ff.web.manifest.converter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.booking.ConsignmentModificationTO;
import com.ff.booking.CreditCustomerBookingParcelTO;
import com.ff.business.ConsignorConsigneeTO;
import com.ff.consignment.ChildConsignmentTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.consignment.ChildConsignmentDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.ConsignmentManifestDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.organization.OfficeTypeDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.geography.RegionTO;
import com.ff.manifest.CreditCustomerBookingConsignmentTO;
import com.ff.manifest.OutManifestParcelDetailsTO;
import com.ff.manifest.OutManifestParcelTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.serviceOfferring.VolumetricWeightTO;
import com.ff.tracking.ProcessTO;
import com.ff.universe.constant.UniversalErrorConstants;
import com.ff.web.booking.utils.BookingUtils;
import com.ff.web.manifest.Utils.ManifestUtil;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.service.OutManifestCommonService;

/**
 * The Class OutManifestParcelConverter.
 */
public class OutManifestParcelConverter extends OutManifestBaseConverter {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(OutManifestParcelConverter.class);
	/** The out manifest common service. */
	private static OutManifestCommonService outManifestCommonService;

	/**
	 * Sets the out manifest common service.
	 * 
	 * @param outManifestCommonService
	 *            the new out manifest common service
	 */
	public static void setOutManifestCommonService(
			OutManifestCommonService outManifestCommonService) {
		OutManifestParcelConverter.outManifestCommonService = outManifestCommonService;
	}

	/**
	 * Sets the booking dtls to out manifest parcel dtl.
	 * 
	 * @param cnModificationTO
	 *            the cn modification to
	 * @return the out manifest parcel details to
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public static OutManifestParcelDetailsTO setBookingDtlsToOutManifestParcelDtl(
			ConsignmentModificationTO cnModificationTO)
			throws CGBusinessException {
		OutManifestParcelDetailsTO outManifestParcelDtlTO = null;

		try {
			// Setting Common attributes
			outManifestParcelDtlTO = (OutManifestParcelDetailsTO) cnDtlsToOutMnfstDtlBaseConverter(
					cnModificationTO, ManifestUtil.prepareFactoryInputs(
							OutManifestConstants.OUT_MANIFEST,
							CommonConstants.CONSIGNMENT_TYPE_PARCEL));
			// Setting specific attributes
			outManifestParcelDtlTO.setBookingType(cnModificationTO
					.getBookingType());
			ConsignmentTO consignmentTO = cnModificationTO.getConsigmentTO();
			if (!StringUtil.isNull(consignmentTO)) {
				if (!StringUtil.isNull(consignmentTO)) {
					// outManifestParcelDtlTO.setConsgAction("U");
					outManifestParcelDtlTO.setNoOfPcs(consignmentTO
							.getNoOfPcs());
					outManifestParcelDtlTO.setActWeight(consignmentTO
							.getActualWeight());
					if (!StringUtil.isNull(consignmentTO.getVolWightDtls())) {
						outManifestParcelDtlTO
								.setVolumetricWeight(consignmentTO
										.getVolWightDtls().getVolWeight());
						outManifestParcelDtlTO.setLength(consignmentTO
								.getVolWightDtls().getLength());
						outManifestParcelDtlTO.setBreadth(consignmentTO
								.getVolWightDtls().getBreadth());
						outManifestParcelDtlTO.setHeight(consignmentTO
								.getVolWightDtls().getHeight());
					}
					if (StringUtils.isNotEmpty(consignmentTO.getChildCNsDtls())) {
						outManifestParcelDtlTO.setChildCn(consignmentTO
								.getChildCNsDtls());
					}
					if (!StringUtil.isNull(consignmentTO.getCnContents())) {
						outManifestParcelDtlTO.setCnContentId(consignmentTO
								.getCnContents().getCnContentId());
						outManifestParcelDtlTO.setCnContentCode(consignmentTO
								.getCnContents().getCnContentCode());
						outManifestParcelDtlTO.setCnContent(consignmentTO
								.getCnContents().getCnContentName());
						outManifestParcelDtlTO.setOtherCNContent(consignmentTO
								.getCnContents().getOtherContent());
					}
					if (!StringUtil.isNull(consignmentTO.getCnPaperWorks())) {
						outManifestParcelDtlTO.setPaperWorkId(consignmentTO
								.getCnPaperWorks().getCnPaperWorkId());
						outManifestParcelDtlTO.setPaperWorkCode(consignmentTO
								.getCnPaperWorks().getCnPaperWorkCode());
						outManifestParcelDtlTO.setPaperWork(consignmentTO
								.getCnPaperWorks().getCnPaperWorkName());
						outManifestParcelDtlTO.setPaperRefNum(consignmentTO
								.getCnPaperWorks().getPaperWorkRefNum());
					}
					if (!StringUtil.isNull(consignmentTO.getInsuredByTO())) {
						outManifestParcelDtlTO.setInsuredById(consignmentTO
								.getInsuredByTO().getInsuredById());
					}
					// set Policy Number
					outManifestParcelDtlTO.setPolicyNo(consignmentTO
							.getInsurencePolicyNo());
					// Set Rate Details
					// Need to set in consg TO

					/*
					 * CNPricingDetailsTO rateDetails = outManifestCommonService
					 * .getConsgPrincingDtls(consignmentTO.getConsgNo());
					 */
					CNPricingDetailsTO rateDetails = consignmentTO
							.getConsgPriceDtls();
					if (!StringUtil.isNull(rateDetails)) {
						outManifestParcelDtlTO.setDeclaredValue(rateDetails
								.getDeclaredvalue());
						outManifestParcelDtlTO.setToPayAmt(rateDetails
								.getTopayChg());
						outManifestParcelDtlTO.setCodAmt(rateDetails
								.getCodAmt());
						outManifestParcelDtlTO.setLcAmt(consignmentTO
								.getLcAmount());
						outManifestParcelDtlTO.setLcBankName(consignmentTO
								.getLcBankName());
					}

					if (!StringUtil.isNull(consignmentTO.getConsigneeTO())) {
						outManifestParcelDtlTO.setMobileNo(consignmentTO
								.getConsigneeTO().getMobile());
						outManifestParcelDtlTO.setConsigneeId(consignmentTO
								.getConsigneeTO().getPartyId());
					}
				}

				outManifestParcelDtlTO
						.setCustRefNo(cnModificationTO.getRefNo());
			}
		} catch (Exception e) {
			throw new CGBusinessException(UniversalErrorConstants.ERROR);
		}

		return outManifestParcelDtlTO;
	}

	/**
	 * Prepare out manifest bpl dtls.
	 * 
	 * @param outmanifestParcelTO
	 *            the outmanifest parcel to
	 * @return the list
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static List<OutManifestParcelDetailsTO> prepareOutManifestBplDtls(
			OutManifestParcelTO outmanifestParcelTO)
			throws CGBusinessException, CGSystemException {
		List<OutManifestParcelDetailsTO> outmanifestBplDtlsList = null;
		int cnPosition = 1;
		int noOfElements = 0;

		if (!StringUtil.isNull(outmanifestParcelTO)) {
			outmanifestBplDtlsList = new ArrayList<>(
					outmanifestParcelTO.getConsgNos().length);
			if (outmanifestParcelTO.getConsgNos() != null
					&& outmanifestParcelTO.getConsgNos().length > 0) {

				// Populate the consignments from input grid
				noOfElements += putInConsignments(outmanifestParcelTO,
						cnPosition);
				outmanifestParcelTO.setNoOfElements(noOfElements);

			}
		}
		return outmanifestBplDtlsList;
	}

	/**
	 * Prepare manifest do list.
	 * 
	 * @param outmanifestParcelTO
	 *            the outmanifest parcel to
	 * @return the list
	 * @throws CGBusinessException
	 */
	// Ami commented on 2309
	/*
	 * public static List<ManifestDO> prepareManifestDOList( OutManifestParcelTO
	 * outmanifestParcelTO) throws CGBusinessException { List<ManifestDO>
	 * manifestDOs = new ArrayList<>(); ManifestDO manifestDO = null;
	 * 
	 * // Setting Common attributes manifestDO =
	 * outManifestTransferObjConverter(outmanifestParcelTO); // Specific to Out
	 * manifest parcel String manifestStatus = CommonConstants.EMPTY_STRING; int
	 * noOfElements = CommonConstants.ZERO;
	 * 
	 * //this is done by preeti if
	 * (StringUtils.isEmpty(outmanifestParcelTO.getDestOfficeType()) ||
	 * (outmanifestParcelTO.getDestOfficeType() != null)){ OfficeTypeDO
	 * officeTypeDO=new OfficeTypeDO();
	 * officeTypeDO.setOffcTypeId(Integer.parseInt
	 * (outmanifestParcelTO.getDestOfficeType()));
	 * manifestDO.setOfficeType(officeTypeDO); } if
	 * (StringUtils.equalsIgnoreCase( outmanifestParcelTO.getBplManifestType(),
	 * OutManifestConstants.MANIFEST_TYPE_PURE)) {
	 * manifestDO.setBplManifestType(ManifestConstants.PURE); } else if
	 * (StringUtils.equalsIgnoreCase( outmanifestParcelTO.getBplManifestType(),
	 * OutManifestConstants.MANIFEST_TYPE_TRANSHIPMENT_CODE)) {
	 * manifestDO.setBplManifestType(ManifestConstants.TRANS); } // Set
	 * ConsignmentManifestDO Set<ConsignmentManifestDO> cnManifestSet = null; if
	 * (outmanifestParcelTO.getConsgIds() != null &&
	 * outmanifestParcelTO.getConsgIds().length > 0) { cnManifestSet =
	 * ManifestUtil.setConsignmentManifestDtls(outmanifestParcelTO.
	 * getOutManifestParcelDetailsList()); noOfElements += cnManifestSet.size();
	 * } Double manifestWeight=0.0; for (OutManifestParcelDetailsTO
	 * parcelDetailsTO : outmanifestParcelTO.getOutManifestParcelDetailsList())
	 * { if(!StringUtil.isEmptyDouble(parcelDetailsTO.getWeight()))
	 * manifestWeight = manifestWeight + parcelDetailsTO.getWeight(); }
	 * outmanifestParcelTO.setFinalWeight(manifestWeight);
	 * manifestDO.setManifestWeight(manifestWeight);
	 * 
	 * manifestDO.setManifestConsgDtls(cnManifestSet); manifestStatus =
	 * outmanifestParcelTO.getManifestStatus(); Double
	 * maxAllowedWeightWithTolerance = 0.00; Double maxWtAllowed =
	 * Double.parseDouble(outmanifestParcelTO.getMaxWeightAllowed()); Double
	 * maxTolleranceAllowed =
	 * Double.parseDouble(outmanifestParcelTO.getMaxTolerenceAllowed());
	 * maxAllowedWeightWithTolerance = maxWtAllowed + ((maxWtAllowed *
	 * maxTolleranceAllowed) / 100); int maxCNsAllowed =
	 * Integer.parseInt(outmanifestParcelTO.getMaxCNsAllowed()); int
	 * noofCns=cnManifestSet.size(); if (noofCns == maxCNsAllowed ||
	 * (manifestDO.getManifestWeight().doubleValue() >=
	 * maxAllowedWeightWithTolerance.doubleValue())) { manifestStatus =
	 * OutManifestConstants.CLOSE; }
	 * 
	 * manifestDO.setManifestStatus(manifestStatus);
	 * outmanifestParcelTO.setManifestStatus(manifestStatus);
	 * outmanifestParcelTO.setNoOfElements(noOfElements); if
	 * (!StringUtil.isEmptyInteger(noOfElements))
	 * manifestDO.setNoOfElements(noOfElements); else throw new
	 * CGBusinessException
	 * (ManifestErrorCodesConstants.PACKET_PARCEL_CONSIGNMENT_EXIST);
	 * 
	 * manifestDOs.add(manifestDO);
	 * 
	 * return manifestDOs; }
	 */

	/**
	 * Prepare manifest process do list.
	 * 
	 * @param outmanifestParcelTO
	 *            the out manifest parcel to
	 * @return the list
	 */
	/*public static List<ManifestProcessDO> prepareManifestProcessDOList(
			OutManifestParcelTO outmanifestParcelTO) {
		List<ManifestProcessDO> ManifestProcessDOs = new ArrayList<>();
		ManifestProcessDO manifestProcessDO = null;
		// Setting Common attributes
		manifestProcessDO = outManifestBaseTransferObjConverter(outmanifestParcelTO);
		// Specific to Out manifest parcel
		manifestProcessDO
				.setNoOfElements(outmanifestParcelTO.getNoOfElements());
		manifestProcessDO.setManifestStatus(outmanifestParcelTO
				.getManifestStatus());
		if (StringUtils.equalsIgnoreCase(
				outmanifestParcelTO.getBplManifestType(),
				OutManifestConstants.MANIFEST_TYPE_PURE)) {
			manifestProcessDO.setBplManifestType(ManifestConstants.PURE);
		} else if (StringUtils.equalsIgnoreCase(
				outmanifestParcelTO.getBplManifestType(),
				OutManifestConstants.MANIFEST_TYPE_TRANSHIPMENT_CODE)) {
			manifestProcessDO.setBplManifestType(ManifestConstants.TRANS);
		}

		ManifestProcessDOs.add(manifestProcessDO);
		return ManifestProcessDOs;
	}
*/
	/**
	 * Out manifest parcel domain converter.
	 * 
	 * @param manifestDO
	 *            the manifest do
	 * @return the out manifest parcel to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static OutManifestParcelTO outManifestParcelDomainConverter(
			ManifestDO manifestDO) throws CGBusinessException,
			CGSystemException {
		// Set the common attributes for the header
		OutManifestParcelTO outManifestParcelTO = (OutManifestParcelTO) outManifestDomainConverter(
				manifestDO, ManifestUtil.prepareFactoryInputs(
						OutManifestConstants.OUT_MANIFEST,
						CommonConstants.CONSIGNMENT_TYPE_PARCEL));
		Double decVal = null;
		// Set the specific attributes for header
		outManifestParcelTO.setBagLockNo(manifestDO.getBagLockNo());
		// outManifestParcelTO.setRfidNo(manifestDO.getBagRFID());

		/*
		 * Commentted By HIMAL on 01/10/2013
		 * 
		 * OfficeTO regionOfficeTO = outManifestCommonService
		 * .getOfficeDetails(outManifestParcelTO.getRegionId()); String
		 * regionName = regionOfficeTO.getOfficeName(); String loginRegionOffice
		 * = regionName + CommonConstants.HYPHEN +
		 * outManifestParcelTO.getLoginOfficeName();
		 * outManifestParcelTO.setLoginOfficeName(loginRegionOffice);
		 */

		if (!StringUtil.isNull(manifestDO.getDestOffice())
				&& !StringUtil.isNull(manifestDO.getDestOffice()
						.getOfficeTypeDO()))
			outManifestParcelTO.setDestOfficeType(manifestDO.getDestOffice()
					.getOfficeTypeDO().getOffcTypeId().toString());

		// this is done by preeti
		if (!StringUtil.isNull(manifestDO.getOfficeType())) {
			OfficeTypeDO officeTypeDO = manifestDO.getOfficeType();
			outManifestParcelTO.setDestOfficeType(officeTypeDO.getOffcTypeId()
					+ "");
		}

		/*
		 * CityTO cityTO = new CityTO();
		 * cityTO.setCityId(outManifestParcelTO.getDestinationCityTO
		 * ().getCityId()); List<CityTO> cityTOs =
		 * outManifestCommonService.getCitiesByCity(cityTO);
		 */
		if (!StringUtil.isNull(manifestDO.getDestinationCity())) {
			RegionTO destRegionTO = new RegionTO();
			// destRegionTO.setRegionId(cityTOs.get(0).getRegion());
			destRegionTO.setRegionId(manifestDO.getDestinationCity()
					.getRegion());
			outManifestParcelTO.setDestRegionTO(destRegionTO);
		}
		// added for BCUN purpose
		if (StringUtils.isNotEmpty(manifestDO.getBplManifestType())) {
			if (StringUtils.equalsIgnoreCase(manifestDO.getBplManifestType(),
					ManifestConstants.PURE)) {
				outManifestParcelTO
						.setBplManifestType(OutManifestConstants.MANIFEST_TYPE_PURE);
			} else {
				outManifestParcelTO
						.setBplManifestType(OutManifestConstants.MANIFEST_TYPE_TRANSHIPMENT_CODE);
			}
		}
		// set the attributes for detailTO
		List<OutManifestParcelDetailsTO> outManifestParcelDetailTOs = new ArrayList<>(
				manifestDO.getNoOfElements());

		OutManifestParcelDetailsTO outManifestpParcelDtlTO = null;
		int count = 0;
		double totalWt = 0.0;
		int totalNoPcs =0;
		// Ami replaced consignment manifested set to consignment set on
		// 23092013
		Set<ConsignmentDO> consignments = manifestDO.getConsignments();
		if (!StringUtil.isEmptyColletion(consignments)) {
			for (ConsignmentDO consignnmentDO : consignments) {
				outManifestpParcelDtlTO = (OutManifestParcelDetailsTO) ManifestUtil
						.consignmentDomainConverter(
								consignnmentDO,
								ManifestUtil
										.prepareFactoryInputs(
												OutManifestConstants.OUT_MANIFEST,
												CommonConstants.CONSIGNMENT_TYPE_PARCEL));

				if (!StringUtil.isNull(outManifestpParcelDtlTO)) {
					// outManifestpParcelDtlTO.setConsgManifestedId(manifestCN.getConsignmentManifestId());
					CityTO city = outManifestCommonService
							.getCity(outManifestpParcelDtlTO.getPincode());
					if (city != null) {
						outManifestpParcelDtlTO.setDestCityId(city.getCityId());
						outManifestpParcelDtlTO.setDestCity(city.getCityName());
					}
					// Ami modified 2309 : removed db cal to get
					// consignee/consignor details
					if (!StringUtil.isNull(consignnmentDO)) {
						// Set Process
						if (!StringUtil.isNull(consignnmentDO
								.getUpdatedProcess())) {
							outManifestpParcelDtlTO.setProcessId(consignnmentDO
									.getUpdatedProcess().getProcessId());
							if (StringUtil.equals(consignnmentDO
									.getUpdatedProcess().getProcessCode(),
									ManifestConstants.PICKUP_PROCESS_CODE)) {
								outManifestpParcelDtlTO
										.setIsPickupCN(CommonConstants.YES);
							} else {
								outManifestpParcelDtlTO
										.setIsPickupCN(CommonConstants.NO);
							}
						}
						// Set Consignee
						if (!StringUtil.isNull(consignnmentDO.getConsignee())) {
							outManifestpParcelDtlTO.setMobileNo(consignnmentDO
									.getConsignee().getMobile());
							outManifestpParcelDtlTO
									.setConsigneeId(consignnmentDO
											.getConsignee().getPartyId());
						}
						// Set Consignor
						if (!StringUtil.isNull(consignnmentDO.getConsignor())) {
							outManifestpParcelDtlTO
									.setConsigneeId(consignnmentDO
											.getConsignor().getPartyId());
						}
						outManifestpParcelDtlTO.setIsCN(CommonConstants.YES);
						// outManifestpParcelDtlTO.setBookingType("");
						outManifestpParcelDtlTO.setNoOfPcs(consignnmentDO
								.getNoOfPcs());
						outManifestpParcelDtlTO.setWeight(consignnmentDO
								.getFinalWeight());
						outManifestpParcelDtlTO.setActWeight(consignnmentDO
								.getActualWeight());
						outManifestpParcelDtlTO
								.setVolumetricWeight(consignnmentDO
										.getVolWeight());
						outManifestpParcelDtlTO.setLength(consignnmentDO
								.getLength());
						outManifestpParcelDtlTO.setBreadth(consignnmentDO
								.getBreath());
						outManifestpParcelDtlTO.setHeight(consignnmentDO
								.getHeight());
						if (!StringUtil.isEmptyColletion(consignnmentDO
								.getChildCNs())) {
							StringBuilder chindCNDtls = new StringBuilder();
							for (ChildConsignmentDO childCN : consignnmentDO
									.getChildCNs()) {
								chindCNDtls.append(childCN
										.getChildConsgNumber());
								chindCNDtls.append(CommonConstants.COMMA);
								chindCNDtls.append(childCN
										.getChildConsgWeight());
								chindCNDtls.append(CommonConstants.HASH);
							}
							outManifestpParcelDtlTO.setChildCn(chindCNDtls
									.toString());
						}
						if (!StringUtil.isNull(consignnmentDO.getCnContentId())) {
							outManifestpParcelDtlTO
									.setCnContentId(consignnmentDO
											.getCnContentId().getCnContentId());
							outManifestpParcelDtlTO
									.setCnContentCode(consignnmentDO
											.getCnContentId()
											.getCnContentCode());
							outManifestpParcelDtlTO.setCnContent(consignnmentDO
									.getCnContentId().getCnContentName());
							outManifestpParcelDtlTO
									.setOtherCNContent(consignnmentDO
											.getOtherCNContent());
						} else {
							outManifestpParcelDtlTO
									.setOtherCNContent(consignnmentDO
											.getOtherCNContent());
						}

						if (!StringUtil.isNull(consignnmentDO
								.getCnPaperWorkId())) {
							outManifestpParcelDtlTO
									.setPaperWorkId(consignnmentDO
											.getCnPaperWorkId()
											.getCnPaperWorkId());
							outManifestpParcelDtlTO
									.setPaperWorkCode(consignnmentDO
											.getCnPaperWorkId()
											.getCnPaperWorkCode());
							outManifestpParcelDtlTO.setPaperWork(consignnmentDO
									.getCnPaperWorkId().getCnPaperWorkName());
						}
						outManifestpParcelDtlTO.setPaperRefNum(consignnmentDO
								.getPaperWorkRefNo());
						if (!StringUtil.isNull(consignnmentDO.getInsuredBy()))
							outManifestpParcelDtlTO
									.setInsuredById(consignnmentDO
											.getInsuredBy().getInsuredById());
						outManifestpParcelDtlTO.setPolicyNo(consignnmentDO
								.getInsurencePolicyNo());
						outManifestpParcelDtlTO.setCustRefNo(consignnmentDO
								.getRefNo());
						// outManifestpParcelDtlTO.setPosition(manifestCN.getPosition());

						/*
						 * CNPricingDetailsTO CNPricingDtlsTO =
						 * outManifestCommonService
						 * .getConsgPrincingDtls(consignnmentDO .getConsgNo());
						 */
						outManifestpParcelDtlTO.setDeclaredValue(consignnmentDO
								.getDeclaredValue());
						outManifestpParcelDtlTO.setToPayAmt(consignnmentDO
								.getTopayAmt());
						outManifestpParcelDtlTO.setCodAmt(consignnmentDO
								.getCodAmt());
						outManifestpParcelDtlTO.setLcAmt(consignnmentDO
								.getLcAmount());
						outManifestpParcelDtlTO.setLcBankName(consignnmentDO
								.getLcBankName());

						CNPaperWorksTO paperWorkValidationTO = new CNPaperWorksTO();
						if (!StringUtil.isNull(outManifestpParcelDtlTO
								.getPincode())) {
							paperWorkValidationTO
									.setPincode(outManifestpParcelDtlTO
											.getPincode());
						}
						paperWorkValidationTO
								.setDocType(CommonConstants.CONSIGNMENT_TYPE_PARCEL);
						if (!StringUtil.isNull(decVal)) {
							paperWorkValidationTO.setDeclatedValue(decVal);
						}

						consignnmentDO.getCnPaperWorkId();
						List<CNPaperWorksTO> cnPaperWorksTOs = outManifestCommonService
								.getPaperWorks(paperWorkValidationTO);
						if (!CGCollectionUtils.isEmpty(cnPaperWorksTOs)) {
							outManifestpParcelDtlTO
									.setCnPaperWorksTOList(cnPaperWorksTOs);
						}

					}

					// for print
					count++;
					if (!StringUtil.isEmptyDouble(consignnmentDO
							.getActualWeight())) {
						totalWt += consignnmentDO.getActualWeight();
					}
					if (!StringUtil.isEmptyInteger(consignnmentDO.getNoOfPcs())) {
						totalNoPcs += consignnmentDO.getNoOfPcs();
					}

				}
				outManifestParcelDetailTOs.add(outManifestpParcelDtlTO);
			}
		}
		// Collections.sort(outManifestParcelDetailTOs);
		outManifestParcelTO
				.setOutManifestParcelDetailsList(outManifestParcelDetailTOs);
		// for print
		outManifestParcelTO.setTotalConsg(count);
		outManifestParcelTO.setTotalWt(Double.parseDouble(new DecimalFormat(
				"##.###").format(totalWt)));
		outManifestParcelTO.setTotalNoPcs(totalNoPcs);
		outManifestParcelTO.setOriginOfficeName(manifestDO.getOriginOffice().getOfficeName());
		return outManifestParcelTO;
	}

	public static CreditCustomerBookingParcelTO prepareCreditCustomerBookingParcelTO(
			OutManifestParcelDetailsTO bplParcelDtlsTO,
			CreditCustomerBookingParcelTO ccBookingTO)
			throws CGSystemException, CGBusinessException {
		ccBookingTO.setBookingId(bplParcelDtlsTO.getBookingId());
		ccBookingTO.setConsgNumber(bplParcelDtlsTO.getConsgNo());
		ccBookingTO.setNoOfPieces(bplParcelDtlsTO.getNoOfPcs());
		ccBookingTO.setPincodeId(bplParcelDtlsTO.getPincodeId());
		ccBookingTO.setCityId(bplParcelDtlsTO.getDestCityId());
		ccBookingTO.setFinalWeight(bplParcelDtlsTO.getWeight());
		ccBookingTO.setActualWeight(bplParcelDtlsTO.getWeight());
		ccBookingTO.setBookingTypeId(bplParcelDtlsTO.getBookingTypeId());
		ccBookingTO.setCustomerId(bplParcelDtlsTO.getCustomerId());
		ccBookingTO.setPickupRunsheetNo(bplParcelDtlsTO.getRunsheetNo());
		// Child Cns
		if (!StringUtils.isNotEmpty(bplParcelDtlsTO.getChildCn()))
			ccBookingTO.setChildCNsDtls(bplParcelDtlsTO.getChildCn());
		// Volumetric weight
		if (!StringUtil.isEmptyDouble(bplParcelDtlsTO.getVolumetricWeight())) {
			ccBookingTO.setVolWeight(bplParcelDtlsTO.getVolumetricWeight());
			ccBookingTO.setHeight(bplParcelDtlsTO.getHeight());
			ccBookingTO.setLength(bplParcelDtlsTO.getLength());
			ccBookingTO.setBreath(bplParcelDtlsTO.getBreadth());
		}
		// Content desc
		if (!StringUtil.isEmptyInteger(bplParcelDtlsTO.getCnContentId())) {
			ccBookingTO.setCnContentId(bplParcelDtlsTO.getCnContentId());
		} else {
			ccBookingTO.setOtherCNContent(bplParcelDtlsTO.getOtherCNContent());
		}
		// Paper work details
		ccBookingTO.setCnPaperworkId(bplParcelDtlsTO.getPaperWorkId());
		ccBookingTO.setPaperWorkRefNo(bplParcelDtlsTO.getPaperRefNum());
		// insurance details
		ccBookingTO.setInsuredById(bplParcelDtlsTO.getInsuredById());
		ccBookingTO.setPolicyNo(bplParcelDtlsTO.getPolicyNo());
		// TODO:Setting COD Amt and TOPay amt and declared value..since design
		// is getting changed need to set after finalization
		// If we are using partial booking record, need to re calculate the rate
		// also.
		CNPricingDetailsTO pricingDetailsTO = new CNPricingDetailsTO();
		pricingDetailsTO.setDeclaredvalue(bplParcelDtlsTO.getDeclaredValue());
		pricingDetailsTO.setReCalcRateReq(true);
		ccBookingTO.setCnPricingDtls(pricingDetailsTO);
		ccBookingTO.setRefNo(bplParcelDtlsTO.getCustRefNo());
		ccBookingTO.setReCalcRateReq(Boolean.TRUE);
		ConsignorConsigneeTO consignorTO = new ConsignorConsigneeTO();
		consignorTO.setPartyId(bplParcelDtlsTO.getConsignorId());
		// consignorTO.setMobile(bplParcelDtlsTO.getMobileNo());
		ccBookingTO.setConsignor(consignorTO);
		if (!StringUtil.isEmptyInteger(bplParcelDtlsTO.getConsigneeId())) {
			ConsignorConsigneeTO consigneeTO = outManifestCommonService
					.getConsigneeConsignorDtls(bplParcelDtlsTO.getConsgNo(),
							CommonConstants.PARTY_TYPE_CONSIGNEE);
			if (!StringUtil.isNull(consigneeTO)) {
				ccBookingTO.setConsignee(consigneeTO);
			}
		}
		return ccBookingTO;
	}

	/**
	 * Prepare consignment to.
	 * 
	 * @param bplParcelDtlsTO
	 *            the bpl parcel dtls to
	 * @param consignmentTO
	 *            the consignment to
	 * @return the consignment to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static ConsignmentTO prepareConsignmentTO(
			OutManifestParcelDetailsTO bplParcelDtlsTO,
			ConsignmentTO consignmentTO) throws CGBusinessException,
			CGSystemException {
		consignmentTO.setConsgId(bplParcelDtlsTO.getConsgId());
		boolean isDataMismatch = Boolean.FALSE;
		if (bplParcelDtlsTO.getWeight().doubleValue() > bplParcelDtlsTO
				.getBkgWeight().doubleValue()) {
			consignmentTO.setFinalWeight(bplParcelDtlsTO.getWeight()
					.doubleValue());
			isDataMismatch = Boolean.TRUE;
		} else
			consignmentTO.setFinalWeight(bplParcelDtlsTO.getBkgWeight()
					.doubleValue());

		// if (bplParcelDtlsTO.getPincodeId().intValue() !=
		// bplParcelDtlsTO.getBkgPincodeId().intValue()) {
		PincodeTO pincodeTO = new PincodeTO();
		pincodeTO.setPincodeId(bplParcelDtlsTO.getBkgPincodeId());
		consignmentTO.setDestPincode(pincodeTO);
		// }
		int noOfChildCns = bplParcelDtlsTO.getNoOfPcs();
		consignmentTO.setNoOfPcs(noOfChildCns);
		if (noOfChildCns > 1) {
			Set<ChildConsignmentTO> childCNsSet = BookingUtils
					.setUpChildConsignmentTOs(bplParcelDtlsTO.getChildCn());
			consignmentTO.setChildTOSet(childCNsSet);
		}

		// setting content Id
		CNContentTO cnContents = new CNContentTO();
		if (!StringUtil.isEmptyInteger(bplParcelDtlsTO.getCnContentId())) {
			cnContents.setCnContentId(bplParcelDtlsTO.getCnContentId());
			consignmentTO.setCnContents(cnContents);
		} else if (StringUtils.isNotEmpty(bplParcelDtlsTO.getOtherCNContent())) {
			cnContents.setOtherContent(bplParcelDtlsTO.getOtherCNContent());
			consignmentTO.setCnContents(cnContents);
		}
		// declared value
		if (!StringUtil.isEmptyInteger(bplParcelDtlsTO.getPaperWorkId())) {
			CNPaperWorksTO cnPaperWorks = new CNPaperWorksTO();
			cnPaperWorks.setPaperWorkRefNum(bplParcelDtlsTO.getPaperRefNum());
			cnPaperWorks.setCnPaperWorkId(bplParcelDtlsTO.getPaperWorkId());
			cnPaperWorks.setCnPaperWorkName(bplParcelDtlsTO.getPaperWork());
			consignmentTO.setCnPaperWorks(cnPaperWorks);
		}
		// setting paper work Id
		if (!StringUtil.isEmptyInteger(bplParcelDtlsTO.getPaperWorkId())) {
			CNPaperWorksTO cnPaperWorks = new CNPaperWorksTO();
			cnPaperWorks.setCnPaperWorkId(bplParcelDtlsTO.getPaperWorkId());
			consignmentTO.setCnPaperWorks(cnPaperWorks);
		}
		// Insured By
		if (!StringUtil.isEmptyInteger(bplParcelDtlsTO.getInsuredById())) {
			InsuredByTO insuredByTO = new InsuredByTO();
			insuredByTO.setInsuredById(bplParcelDtlsTO.getInsuredById());
			// Policy No
			insuredByTO.setPolicyNo(bplParcelDtlsTO.getPolicyNo());
			consignmentTO.setInsuredByTO(insuredByTO);
		}
		if (!StringUtil.isEmptyDouble(bplParcelDtlsTO.getVolumetricWeight())) {
			VolumetricWeightTO volWeightDtls = new VolumetricWeightTO();
			volWeightDtls.setVolWeight(bplParcelDtlsTO.getVolumetricWeight());
			volWeightDtls.setHeight(bplParcelDtlsTO.getHeight());
			volWeightDtls.setLength(bplParcelDtlsTO.getLength());
			volWeightDtls.setBreadth(bplParcelDtlsTO.getBreadth());
			consignmentTO.setVolWightDtls(volWeightDtls);
		}

		consignmentTO.setMobileNo(bplParcelDtlsTO.getMobileNo());

		// if Consignment data is updating then need to set the operating level
		if (isDataMismatch) {
			consignmentTO.setOrgOffId(bplParcelDtlsTO
					.getManifestLoggedInOffId());
			consignmentTO.setDestOffice(null);
			if (bplParcelDtlsTO.getDestOfficeId() != null) {
				OfficeTO destoffTO = new OfficeTO();
				destoffTO.setOfficeId(bplParcelDtlsTO.getDestOfficeId());
				consignmentTO.setDestOffice(destoffTO);
			}
			// if(outManifestDoxDetailsTO.getDestCityId()!=null){
			CityTO destCityTO = new CityTO();
			destCityTO.setCityId(bplParcelDtlsTO.getDestCityId());
			consignmentTO.setDestCity(destCityTO);
			// }
			OfficeTO offTO = new OfficeTO();
			offTO.setOfficeId(bplParcelDtlsTO.getManifestLoggedInOffId());
			Integer operatingLevel = outManifestCommonService
					.getConsgOperatingLevel(consignmentTO, offTO);
			consignmentTO.setOperatingLevel(operatingLevel);

			// Setting manifest login office in consignment table
			consignmentTO.setOperatingOffice(bplParcelDtlsTO
					.getManifestLoggedInOffId());
		}

		// Added by ami 0709
		CNPricingDetailsTO consgPriceDtls = new CNPricingDetailsTO();
		consgPriceDtls.setDeclaredvalue(bplParcelDtlsTO.getDeclaredValue());
		consgPriceDtls.setTopayChg(bplParcelDtlsTO.getToPayAmt());
		consgPriceDtls.setCodAmt(bplParcelDtlsTO.getCodAmt());
		consignmentTO.setConsgPriceDtls(consgPriceDtls);
		// Rate Details

		/*
		 * Set<ConsignmentRateTO> rateDetails =
		 * consignmentTO.getConsgRateDetails(); setRateDetails(bplParcelDtlsTO,
		 * rateDetails);
		 */

		return consignmentTO;
	}

	/*
	 * private static void setRateDetails( OutManifestParcelDetailsTO
	 * bplParcelDtlsTO, Set<ConsignmentRateTO> rateDetails) throws
	 * CGBusinessException, CGSystemException { boolean isDecValueCompExists =
	 * Boolean.FALSE; boolean isTopayValueCompExists = Boolean.FALSE; boolean
	 * isCODValueCompExists = Boolean.FALSE;
	 * 
	 * for (ConsignmentRateTO consignmentRateTO : rateDetails) { RateComponentTO
	 * componentTO=consignmentRateTO.getRateComponent();
	 * if(StringUtils.equalsIgnoreCase
	 * (RateCommonConstants.RATE_COMPONENT_TYPE_DECLARED_VALUE
	 * ,componentTO.getRateComponentCode())){
	 * consignmentRateTO.setCalculatedValue(bplParcelDtlsTO.getDeclaredValue());
	 * isDecValueCompExists = Boolean.TRUE; }
	 * if(StringUtils.equalsIgnoreCase(RateCommonConstants
	 * .RATE_COMPONENT_TYPE_TO_PAY_CHARGES,componentTO.getRateComponentCode())){
	 * consignmentRateTO.setCalculatedValue(bplParcelDtlsTO.getToPayAmt());
	 * isTopayValueCompExists = Boolean.TRUE; }
	 * if(StringUtils.equalsIgnoreCase(RateCommonConstants
	 * .RATE_COMPONENT_TYPE_COD,componentTO.getRateComponentCode())){
	 * consignmentRateTO.setCalculatedValue(bplParcelDtlsTO.getCodAmt());
	 * isCODValueCompExists = Boolean.TRUE; } }
	 * 
	 * if (!isDecValueCompExists &&
	 * !StringUtil.isEmptyDouble(bplParcelDtlsTO.getDeclaredValue())) {
	 * ConsignmentRateTO decRateTO = new ConsignmentRateTO();
	 * decRateTO.setCalculatedValue(bplParcelDtlsTO.getDeclaredValue());
	 * RateComponentTO decRateCompoment = new RateComponentTO(); Integer
	 * rateCompId
	 * =outManifestCommonService.getRateComponentIdByCode(RateCommonConstants
	 * .RATE_COMPONENT_TYPE_DECLARED_VALUE);
	 * decRateCompoment.setRateComponentId(rateCompId);
	 * decRateTO.setRateComponent(decRateCompoment); rateDetails.add(decRateTO);
	 * } if (!isTopayValueCompExists &&
	 * !StringUtil.isEmptyDouble(bplParcelDtlsTO.getToPayAmt())) {
	 * ConsignmentRateTO topayRateTO = new ConsignmentRateTO();
	 * topayRateTO.setCalculatedValue(bplParcelDtlsTO.getToPayAmt());
	 * RateComponentTO decRateCompoment = new RateComponentTO(); Integer
	 * rateCompId
	 * =outManifestCommonService.getRateComponentIdByCode(RateCommonConstants
	 * .RATE_COMPONENT_TYPE_TO_PAY_CHARGES);
	 * decRateCompoment.setRateComponentId(rateCompId);
	 * topayRateTO.setRateComponent(decRateCompoment);
	 * rateDetails.add(topayRateTO); } if (!isCODValueCompExists &&
	 * !StringUtil.isEmptyDouble(bplParcelDtlsTO.getCodAmt())) {
	 * ConsignmentRateTO codRateTO = new ConsignmentRateTO();
	 * codRateTO.setCalculatedValue(bplParcelDtlsTO.getCodAmt());
	 * RateComponentTO decRateCompoment = new RateComponentTO(); Integer
	 * rateCompId
	 * =outManifestCommonService.getRateComponentIdByCode(RateCommonConstants
	 * .RATE_COMPONENT_TYPE_COD);
	 * decRateCompoment.setRateComponentId(rateCompId);
	 * codRateTO.setRateComponent(decRateCompoment); rateDetails.add(codRateTO);
	 * } }
	 */
	/**
	 * Out manifest ppx grid details for in manif consg.
	 * 
	 * @param consTO
	 *            the cons to
	 * @return the out manifest parcel details to
	 */
	public static OutManifestParcelDetailsTO outManifestPpxGridDetailsForInManifConsg(
			ConsignmentTO consTO) {
		OutManifestParcelDetailsTO outManifestPpxDetailsTO = new OutManifestParcelDetailsTO();

		if (!StringUtil.isNull(consTO)) {
			outManifestPpxDetailsTO.setConsgNo(consTO.getConsgNo());
			outManifestPpxDetailsTO.setConsgId(consTO.getConsgId());
		}

		if (!StringUtil.isNull(consTO.getDestPincode())) {
			// outManifestDoxDetailsTO.setPincode(consTO.getDestPincode().getPincode());
			outManifestPpxDetailsTO.setPincodeId(consTO.getDestPincode()
					.getPincodeId());
		}

		if (!StringUtil.isNull(consTO.getDestCity())) {
			outManifestPpxDetailsTO.setDestCityId(consTO.getDestCity()
					.getCityId());
			outManifestPpxDetailsTO.setDestCity(consTO.getDestCity()
					.getCityName());
		}

		if (!StringUtil.isNull(consTO.getFinalWeight())) {
			outManifestPpxDetailsTO.setWeight(consTO.getFinalWeight());
			outManifestPpxDetailsTO.setBkgWeight(consTO.getFinalWeight());
		}

		if (!StringUtil.isNull(consTO.getNoOfPcs())) {
			outManifestPpxDetailsTO.setNoOfPcs(consTO.getNoOfPcs());
		}

		if (!StringUtil.isNull(consTO.getFinalWeight())) {
			outManifestPpxDetailsTO.setActWeight(consTO.getActualWeight());
		}
		if (!StringUtil.isNull(consTO.getVolWightDtls())) {
			outManifestPpxDetailsTO.setVolumetricWeight(consTO
					.getVolWightDtls().getVolWeight());
			outManifestPpxDetailsTO.setLength(consTO.getVolWightDtls()
					.getLength());
			outManifestPpxDetailsTO.setBreadth(consTO.getVolWightDtls()
					.getBreadth());
			outManifestPpxDetailsTO.setHeight(consTO.getVolWightDtls()
					.getHeight());
		}

		if (StringUtils.isNotEmpty(consTO.getChildCNsDtls())) {
			outManifestPpxDetailsTO.setChildCn(consTO.getChildCNsDtls());
		}
		if (!StringUtil.isNull(consTO.getCnContents())) {
			outManifestPpxDetailsTO.setCnContentId(consTO.getCnContents()
					.getCnContentId());
			outManifestPpxDetailsTO.setCnContentCode(consTO.getCnContents()
					.getCnContentCode());
			outManifestPpxDetailsTO.setCnContent(consTO.getCnContents()
					.getCnContentName());
			outManifestPpxDetailsTO.setOtherCNContent(consTO.getCnContents()
					.getOtherContent());
		}
		if (!StringUtil.isNull(consTO.getCnPaperWorks())) {
			outManifestPpxDetailsTO.setPaperWorkId(consTO.getCnPaperWorks()
					.getCnPaperWorkId());
			outManifestPpxDetailsTO.setPaperWorkCode(consTO.getCnPaperWorks()
					.getCnPaperWorkCode());
			outManifestPpxDetailsTO.setPaperWork(consTO.getCnPaperWorks()
					.getCnPaperWorkName());
			outManifestPpxDetailsTO.setPaperRefNum(consTO.getCnPaperWorks()
					.getPaperWorkRefNum());
		}
		if (!StringUtil.isNull(consTO.getInsuredByTO())) {
			outManifestPpxDetailsTO.setInsuredById(consTO.getInsuredByTO()
					.getInsuredById());
		}
		// Set Policy Number
		outManifestPpxDetailsTO.setPolicyNo(consTO.getInsurencePolicyNo());

		if (!StringUtil.isNull(consTO.getConsigneeTO())) {
			outManifestPpxDetailsTO.setMobileNo(consTO.getConsigneeTO()
					.getMobile());
			outManifestPpxDetailsTO.setConsigneeId(consTO.getConsigneeTO()
					.getPartyId());
		}
		/*
		 * if (!StringUtil.isNull(consTO.getC)) {
		 * outManifestParcelDtlTO.setToPayAmt(consTO
		 * .getCnPricingDtls().getTopayChg());
		 * outManifestParcelDtlTO.setCodAmt(consTO
		 * .getCnPricingDtls().getCodAmt());
		 * outManifestParcelDtlTO.setDeclaredValue(consTO
		 * .getCnPricingDtls().getDeclaredvalue()); }
		 */
		outManifestPpxDetailsTO.setCustRefNo(consTO.getRefNo());

		return outManifestPpxDetailsTO;
	}

	/**
	 * Prepare manifest do list.
	 * 
	 * @param outmanifestParcelTO
	 * @param manifestDO2
	 * @return the list
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static ManifestDO prepareManifestDO(
			OutManifestParcelTO outmanifestParcelTO, ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("OutManifestParcelConverter :: prepareManifestDO() :: START------------>:::::::");

		// Setting HEADER attributes
		manifestDO = outManifestTransferObjConverter(outmanifestParcelTO,
				manifestDO);

		String manifestStatus = CommonConstants.EMPTY_STRING;

		// Added from original method starts
		if (StringUtils.isEmpty(outmanifestParcelTO.getDestOfficeType())
				|| (outmanifestParcelTO.getDestOfficeType() != null)) {
			OfficeTypeDO officeTypeDO = new OfficeTypeDO();
			officeTypeDO.setOffcTypeId(Integer.parseInt(outmanifestParcelTO
					.getDestOfficeType()));
			manifestDO.setOfficeType(officeTypeDO);
		}
		if (StringUtils.equalsIgnoreCase(
				outmanifestParcelTO.getBplManifestType(),
				OutManifestConstants.MANIFEST_TYPE_PURE)) {
			manifestDO.setBplManifestType(ManifestConstants.PURE);
		} else if (StringUtils.equalsIgnoreCase(
				outmanifestParcelTO.getBplManifestType(),
				OutManifestConstants.MANIFEST_TYPE_TRANSHIPMENT_CODE)) {
			manifestDO.setBplManifestType(ManifestConstants.TRANS);
		}
		// Added from original method ends

		int noOfElements = CommonConstants.ZERO;
		Set<ConsignmentManifestDO> cnManifestSet = null;
		cnManifestSet = ManifestUtil
				.setConsignmentManifestDtls(outmanifestParcelTO);
		noOfElements += cnManifestSet.size();
		// manifestDO.setManifestConsgDtls(cnManifestSet);
		manifestStatus = outmanifestParcelTO.getManifestStatus();

		Double manifestWeight = 0.0;

		/*
		 * for (OutManifestParcelDetailsTO parcelDetailsTO : outmanifestParcelTO
		 * .getOutManifestParcelDetailsList()) { if
		 * (!StringUtil.isEmptyDouble(parcelDetailsTO.getWeight()))
		 * manifestWeight = manifestWeight + parcelDetailsTO.getWeight(); }
		 */
		for (int i = 0; i < outmanifestParcelTO.getConsgNos().length; i++) {
			if (!StringUtil.isStringEmpty(outmanifestParcelTO.getConsgNos()[i])) {
				manifestWeight = manifestWeight
						+ outmanifestParcelTO.getActWeights()[i];
			}
		}
		outmanifestParcelTO.setFinalWeight(manifestWeight);
		manifestDO.setManifestWeight(manifestWeight);
		// MAX CN
		if (StringUtils.isNotEmpty(outmanifestParcelTO.getMaxCNsAllowed())) {
			int maxCNsAllowed = Integer.parseInt(outmanifestParcelTO
					.getMaxCNsAllowed());

			if (cnManifestSet.size() == maxCNsAllowed) {
				manifestStatus = OutManifestConstants.CLOSE;
			}
		}

		/* Max weight tolerance */
		double maxWeightAllowed = Double.parseDouble(outmanifestParcelTO
				.getMaxWeightAllowed());
		double maxTolerenceAllowed = Double.parseDouble(outmanifestParcelTO
				.getMaxTolerenceAllowed());
		double maxWtTolerence = maxWeightAllowed
				+ (maxWeightAllowed * maxTolerenceAllowed / 100);
		if (outmanifestParcelTO.getFinalWeight().doubleValue() >= maxWtTolerence) {
			manifestStatus = OutManifestConstants.CLOSE;
		}

		manifestDO.setManifestStatus(manifestStatus);
		//manifestDO.setNoOfElements(noOfElements);
		//outmanifestParcelTO.setNoOfElements(noOfElements);
		outmanifestParcelTO.setManifestStatus(manifestStatus);
		LOGGER.trace("OutManifestParcelConverter :: prepareManifestDO() :: END------------>:::::::");
		return manifestDO;
	}

	/*
	 * // Setting Pickedup consignment Ids private static void
	 * setPickedupConsignmentIds( OutManifestParcelTO outmanifestParcelTO,
	 * List<ConsignmentDO> consignmentDOList) { LOGGER.trace(
	 * "OutManifestParcelConverter :: setPickedupConsignmentIds() :: START------------>:::::::"
	 * ); List<OutManifestParcelDetailsTO> outManifestParcelDetailsTOList =
	 * outmanifestParcelTO.getOutManifestParcelDetailsList(); for
	 * (OutManifestParcelDetailsTO outManifestParcelDetailsTO :
	 * outManifestParcelDetailsTOList) { for (ConsignmentDO consignmentDO :
	 * consignmentDOList) { if
	 * (StringUtils.equalsIgnoreCase(consignmentDO.getConsgNo(),
	 * outManifestParcelDetailsTO.getConsgNo())) {
	 * outManifestParcelDetailsTO.setConsgId(consignmentDO .getConsgId()); } } }
	 * LOGGER.trace(
	 * "OutManifestParcelConverter :: setPickedupConsignmentIds() :: END------------>:::::::"
	 * ); }
	 */

	private static int putInConsignments(OutManifestParcelTO outManifestPPXTO,
			int cnPosition) throws CGBusinessException, CGSystemException {
		LOGGER.trace("OutManifestParcelConverter :: putInConsignments() :: START------------>:::::::");
		int noOfElements = 0;
		OutManifestParcelDetailsTO outManifestPPXDetailsTO = null;
		List<OutManifestParcelDetailsTO> outPPXDetailsList = new ArrayList<>();
		CreditCustomerBookingConsignmentTO creditCustomerBookingConsignmentTO = null;
		ConsignmentTO consignmentTO = null;
		// ComailTO comailTO = null;
		// Grid item as Consignments
		for (int rowCount = 0; rowCount < outManifestPPXTO.getConsgNos().length; rowCount++) {

			if (StringUtils
					.isNotEmpty(outManifestPPXTO.getConsgNos()[rowCount])) {
				noOfElements++;
				outManifestPPXDetailsTO = new OutManifestParcelDetailsTO();
				// Setting the common grid level attributes
				outManifestPPXDetailsTO = (OutManifestParcelDetailsTO) setUpManifestDtlsTOs(
						outManifestPPXTO, rowCount);
				outPPXDetailsList.add(outManifestPPXDetailsTO);
				outManifestPPXTO
						.setOutManifestParcelDetailsList(outPPXDetailsList);
				// Setting the data to indicate whether the consignment is
				// changed
				outManifestPPXDetailsTO.setIsDataMismatch(outManifestPPXTO
						.getIsDataMismatched()[rowCount]);

				if (!StringUtil.isStringEmpty(outManifestPPXTO
						.getIsCNProcessedFromPickup()[rowCount])
						&& StringUtil
								.equals(outManifestPPXTO
										.getIsCNProcessedFromPickup()[rowCount],
										CommonConstants.YES)) {
					// Consignments created from PICKUP process
					outManifestPPXDetailsTO.setGridItemType("P");
					creditCustomerBookingConsignmentTO = prepareCreditCustomerBookingPPXTO(
							outManifestPPXTO, rowCount);
					creditCustomerBookingConsignmentTO
							.setConsignmentTO(consignmentTO);
					outManifestPPXTO.getCreditCustomerBookingDoxTOList().add(
							creditCustomerBookingConsignmentTO
									.getCreditCustomerBookingDoxTO());
				} else {
					if (CommonConstants.YES
							.compareToIgnoreCase(outManifestPPXDetailsTO
									.getIsDataMismatch()) == 0) {
						// Consignments created from BOOKING process
						consignmentTO = prepareConsignmentTO(outManifestPPXTO,
								rowCount);

						outManifestPPXDetailsTO.setGridItemType("C");
						outManifestPPXTO.getConsignmentstoBeUpdatedList().add(
								consignmentTO);
					}
				}
				/*
				 * outManifestPPXDetailsTO.setConsgManifestedId(outManifestPPXTO
				 * .getConsgManifestedIds()[rowCount]);
				 */

				outManifestPPXDetailsTO.setBkgWeight(outManifestPPXTO
						.getBookingWeights()[rowCount]);
				
				outManifestPPXDetailsTO.setActWeight(outManifestPPXTO
						.getActWeights()[rowCount]);

				outManifestPPXDetailsTO.setIsDataMismatch(outManifestPPXTO
						.getIsDataMismatched()[rowCount]);
				// outManifestPPXDetailsTO.setLcDtls(outManifestPPXTO.getLcDetails()[rowCount]);
				outManifestPPXDetailsTO.setMobileNo(outManifestPPXTO
						.getMobileNos()[rowCount]);
				outManifestPPXDetailsTO.setConsigneeId(outManifestPPXTO
						.getConsigneeIds()[rowCount]);

				outManifestPPXDetailsTO.setPosition(cnPosition++);
				outManifestPPXTO.getOutManifestParcelDetailsList().add(
						outManifestPPXDetailsTO);
			}/* END of IF */
		}/* END of FOR LOOP */
		LOGGER.trace("OutManifestParcelConverter :: putInConsignments() :: END------------>:::::::");
		return noOfElements;
	}

	private static CreditCustomerBookingConsignmentTO prepareCreditCustomerBookingPPXTO(
			OutManifestParcelTO outManifestTO, Integer rowcount)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("OutManifestParcelConverter :: prepareCreditCustomerBookingDoxTO() :: START------------>:::::::");
		CreditCustomerBookingConsignmentTO creditCustomerBookingConsignmentTO = new CreditCustomerBookingConsignmentTO();

		CreditCustomerBookingParcelTO creditCustomerBookingTO = new CreditCustomerBookingParcelTO();
		ConsignmentTO consignmentTO = null;

		creditCustomerBookingTO.setBookingDate(outManifestTO.getManifestDate());
		creditCustomerBookingTO.setConsgTypeId(outManifestTO
				.getConsignmentTypeTO().getConsignmentId());
		creditCustomerBookingTO.setBookingOfficeId(outManifestTO
				.getLoginOfficeId());
		creditCustomerBookingTO
				.setBookingId(outManifestTO.getBookingIds()[rowcount]);
		creditCustomerBookingTO
				.setConsgNumber(outManifestTO.getConsgNos()[rowcount]);
		creditCustomerBookingTO
				.setPincodeId(outManifestTO.getPincodeIds()[rowcount]);
		/*
		 * creditCustomerBookingTO
		 * .setCityId(outManifestTO.getDestCityIds()[rowcount]);
		 */
		creditCustomerBookingTO
				.setFinalWeight(outManifestTO.getWeights()[rowcount]);
		creditCustomerBookingTO
				.setActualWeight(outManifestTO.getWeights()[rowcount]);
		creditCustomerBookingTO.setBookingTypeId(outManifestTO
				.getBookingTypeIds()[rowcount]);
		creditCustomerBookingTO
				.setCustomerId(outManifestTO.getCustomerIds()[rowcount]);
		creditCustomerBookingTO.setPickupRunsheetNo(outManifestTO
				.getRunsheetNos()[rowcount]);
		creditCustomerBookingTO
				.setVolWeight(outManifestTO.getVolWeight()[rowcount]);
		creditCustomerBookingTO.setReCalcRateReq(Boolean.TRUE);
		ConsignorConsigneeTO consignorTO = new ConsignorConsigneeTO();
		consignorTO.setPartyId(outManifestTO.getConsignorIds()[rowcount]);
		creditCustomerBookingTO.setConsignor(consignorTO);

		consignmentTO = prepareConsignmentTO(outManifestTO, rowcount);

		creditCustomerBookingConsignmentTO
				.setCreditCustomerBookingPPXTO(creditCustomerBookingTO);
		creditCustomerBookingConsignmentTO.setConsignmentTO(consignmentTO);
		LOGGER.trace("OutManifestParcelConverter :: prepareCreditCustomerBookingDoxTO() :: END------------>:::::::");
		return creditCustomerBookingConsignmentTO;
	}

	private static ConsignmentTO prepareConsignmentTO(
			OutManifestParcelTO outManifestTO, Integer rowcount)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("OutManifestParcelConverter :: prepareConsignmentTO() :: START------------>:::::::");
		ConsignmentTO consignmentTO = new ConsignmentTO();
		consignmentTO.setConsgNo(outManifestTO.getConsgNos()[rowcount]);
		ProcessTO processTO = new ProcessTO();
		processTO.setProcessId(outManifestTO.getProcessId());
		consignmentTO.setUpdatedProcessFrom(processTO);

		boolean isDataMismatch = Boolean.FALSE;
		if (outManifestTO.getWeights()[rowcount].doubleValue() > outManifestTO
				.getBookingWeights()[rowcount].doubleValue()) {
			consignmentTO.setFinalWeight(outManifestTO.getWeights()[rowcount]
					.doubleValue());
			isDataMismatch = Boolean.TRUE;
		} else
			consignmentTO
					.setFinalWeight(outManifestTO.getBookingWeights()[rowcount]
							.doubleValue());
		PincodeTO pincodeTO = new PincodeTO();
		pincodeTO.setPincodeId(outManifestTO.getPincodeIds()[rowcount]);
		consignmentTO.setDestPincode(pincodeTO);
		// ////////////////////Carried prev code ////////////
		List<OutManifestParcelDetailsTO> bplParcelDtlsList = outManifestTO
				.getOutManifestParcelDetailsList();
		OutManifestParcelDetailsTO bplParcelDtlsTO = bplParcelDtlsList.get(0);
		int noOfChildCns = outManifestTO.getNoOfPcs()[rowcount];
		consignmentTO.setNoOfPcs(noOfChildCns);
		if (noOfChildCns > 1) {
			Set<ChildConsignmentTO> childCNsSet = BookingUtils
					.setUpChildConsignmentTOs(bplParcelDtlsTO.getChildCn());
			consignmentTO.setChildTOSet(childCNsSet);
		}

		// setting content Id
		CNContentTO cnContents = new CNContentTO();
		if (!StringUtil
				.isEmptyInteger(outManifestTO.getCnContentIds()[rowcount])) {
			cnContents
					.setCnContentId(outManifestTO.getCnContentIds()[rowcount]);
			consignmentTO.setCnContents(cnContents);
		} else if (StringUtils.isNotEmpty(bplParcelDtlsTO.getOtherCNContent())) {
			cnContents.setOtherContent(bplParcelDtlsTO.getOtherCNContent());
			consignmentTO.setCnContents(cnContents);
		}
		// declared value
		/*
		 * if
		 * (!StringUtil.isEmptyDouble(outManifestTO.getDeclaredValues()[rowcount
		 * ]) {
		 * 
		 * consignmentTO.set }
		 */
		// setting paper work Id
		if (!StringUtil
				.isEmptyInteger(outManifestTO.getPaperWorkIds()[rowcount])) {
			CNPaperWorksTO cnPaperWorks = new CNPaperWorksTO();
			cnPaperWorks
					.setCnPaperWorkId(outManifestTO.getPaperWorkIds()[rowcount]);
			consignmentTO.setCnPaperWorks(cnPaperWorks);
		}
		// Insured By
		if (!StringUtil
				.isEmptyInteger(outManifestTO.getInsuredByIds()[rowcount])) {
			InsuredByTO insuredByTO = new InsuredByTO();
			insuredByTO
					.setInsuredById(outManifestTO.getInsuredByIds()[rowcount]);
			// Policy No
			insuredByTO.setPolicyNo(outManifestTO.getPolicyNos()[rowcount]);
			consignmentTO.setInsuredByTO(insuredByTO);
		}
		if (!StringUtil.isEmptyDouble(outManifestTO.getVolWeight()[rowcount])) {
			VolumetricWeightTO volWeightDtls = new VolumetricWeightTO();
			volWeightDtls.setVolWeight(outManifestTO.getVolWeight()[rowcount]);
			volWeightDtls.setHeight(outManifestTO.getHeights()[rowcount]);
			volWeightDtls.setLength(outManifestTO.getLengths()[rowcount]);
			volWeightDtls.setBreadth(outManifestTO.getBreadths()[rowcount]);
			consignmentTO.setVolWightDtls(volWeightDtls);
		}

		consignmentTO.setMobileNo(outManifestTO.getMobileNos()[rowcount]);

		// operating level needs to be set in case of weight/destination changes
		if (isDataMismatch) {
			consignmentTO.setOrgOffId(outManifestTO.getLoginOfficeId());
			consignmentTO.setDestOffice(null);
			if (outManifestTO.getDestinationOfficeId() != null) {
				OfficeTO destoffTO = new OfficeTO();
				destoffTO.setOfficeId(outManifestTO.getDestinationOfficeId());
				consignmentTO.setDestOffice(destoffTO);
			}

			// if(outManifestDoxDetailsTO.getDestCityId()!=null){
			CityTO destCityTO = new CityTO();
			destCityTO.setCityId(bplParcelDtlsTO.getDestCityId());
			consignmentTO.setDestCity(destCityTO);
			/*
			 * CityTO destCityTO = new CityTO();
			 * destCityTO.setCityId(outManifestTO.getDestCityIds()[rowcount]);
			 * consignmentTO.setDestCity(destCityTO);
			 */

			OfficeTO offTO = new OfficeTO();
			offTO.setOfficeId(outManifestTO.getLoginOfficeId());
			Integer operatingLevel = outManifestCommonService
					.getConsgOperatingLevel(consignmentTO, offTO);
			consignmentTO.setOperatingLevel(operatingLevel);
			// Setting manifest login office in consignment table
			consignmentTO.setOperatingOffice(outManifestTO.getLoginOfficeId());
		}
		ConsignorConsigneeTO consigneeTO = new ConsignorConsigneeTO();
		consigneeTO.setPartyId(outManifestTO.getConsigneeIds()[rowcount]);
		consigneeTO.setMobile(outManifestTO.getMobileNos()[rowcount]);
		consignmentTO.setConsigneeTO(consigneeTO);

		// if Consignment data is updating then need to set the operating level
		// Duplicate code - Commentted by Himal 04102013
		/*
		 * if (isDataMismatch) {
		 * consignmentTO.setOrgOffId(outManifestTO.getLoginOfficeId());
		 * consignmentTO.setDestOffice(null); if
		 * (bplParcelDtlsTO.getDestOfficeId() != null) { OfficeTO destoffTO =
		 * new OfficeTO();
		 * destoffTO.setOfficeId(bplParcelDtlsTO.getDestOfficeId());
		 * consignmentTO.setDestOffice(destoffTO); } //
		 * if(outManifestDoxDetailsTO.getDestCityId()!=null){ CityTO destCityTO
		 * = new CityTO();
		 * destCityTO.setCityId(bplParcelDtlsTO.getDestCityId());
		 * consignmentTO.setDestCity(destCityTO); // } OfficeTO offTO = new
		 * OfficeTO(); offTO.setOfficeId(outManifestTO.getLoginOfficeId());
		 * Integer operatingLevel = outManifestCommonService
		 * .getConsgOperatingLevel(consignmentTO, offTO);
		 * consignmentTO.setOperatingLevel(operatingLevel);
		 * 
		 * // Setting manifest login office in consignment table
		 * consignmentTO.setOperatingOffice(bplParcelDtlsTO
		 * .getManifestLoggedInOffId()); }
		 */

		// Added by ami 0709 Rate Details
		CNPricingDetailsTO consgPriceDtls = new CNPricingDetailsTO();
		consgPriceDtls.setDeclaredvalue(bplParcelDtlsTO.getDeclaredValue());
		consgPriceDtls.setTopayChg(bplParcelDtlsTO.getToPayAmt());
		consgPriceDtls.setCodAmt(bplParcelDtlsTO.getCodAmt());
		consignmentTO.setConsgPriceDtls(consgPriceDtls);

		LOGGER.trace("OutManifestParcelConverter :: prepareConsignmentTO() :: END------------>:::::::");
		return consignmentTO;
	}
}
