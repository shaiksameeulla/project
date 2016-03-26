package com.ff.web.manifest.converter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.geography.CityDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.manifest.ManifestMappedEmbeddedDO;
import com.ff.domain.manifest.OutManifestDestinationDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.organization.OfficeTypeDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.manifest.BPLOutManifestDoxDetailsTO;
import com.ff.manifest.BPLOutManifestDoxTO;
import com.ff.manifest.ManifestInputs;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.web.manifest.Utils.ManifestUtil;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.constants.ManifestErrorCodesConstants;
import com.ff.web.manifest.service.OutManifestCommonService;

/**
 * The Class BPLOutManifestDoxConverter.
 */
public class BPLOutManifestDoxConverter extends OutManifestBaseConverter {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BPLOutManifestDoxConverter.class);
	private static OutManifestCommonService outManifestCommonService;

	public void setOutManifestCommonService(
			OutManifestCommonService outManifestCommonService) {
		this.outManifestCommonService = outManifestCommonService;
	}

	/**
	 * Bpl dox manifest domain converter.
	 * 
	 * @param bplDoxManifestTO
	 *            the bpl dox manifest to
	 * @return the list
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static List<ManifestDO> bplDoxManifestDomainConverter(
			BPLOutManifestDoxTO bplDoxManifestTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("BPLOutManifestDoxConverter :: bplDoxManifestDomainConverter() :: Start --------> ::::::");
		List<ManifestDO> bplDoxManifestDOs = null;
		ManifestDO outManifestDO = null;
		if (!StringUtil.isEmptyList(bplDoxManifestTO
				.getBplOutManifestDoxDetailsTOList())) {
			bplDoxManifestDOs = new ArrayList(bplDoxManifestTO
					.getBplOutManifestDoxDetailsTOList().size());
			// Setting Header values
			outManifestDO = headerDomainManifestConverter(bplDoxManifestTO);
			bplDoxManifestDOs.add(outManifestDO);
			for (BPLOutManifestDoxDetailsTO bplManifestDoxDtlsTO : bplDoxManifestTO
					.getBplOutManifestDoxDetailsTOList()) {
				outManifestDO = new ManifestDO();
				bplDoxManifestDOs.add(outManifestDO);
			}
		}
		LOGGER.trace("BPLOutManifestDoxConverter :: bplDoxManifestDomainConverter() :: End --------> ::::::");
		return bplDoxManifestDOs;
	}

	// Constructing DOs Bases on TOs for ManifestProcess

	/**
	 * Bpl dox manifest process domain converter.
	 * 
	 * @param bplDoxManifestTO
	 *            the bpl dox manifest to
	 * @return the list
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	/*
	 * public static List<ManifestProcessDO>
	 * bplDoxManifestProcessDomainConverter( BPLOutManifestDoxTO
	 * bplDoxManifestTO) throws CGBusinessException, CGSystemException {
	 * LOGGER.trace(
	 * "BPLOutManifestDoxConverter :: bplDoxManifestProcessDomainConverter() :: Start --------> ::::::"
	 * ); List<ManifestProcessDO> bplDoxManifestDOs = null; ManifestProcessDO
	 * outManifestDO = null; if (!StringUtil.isEmptyList(bplDoxManifestTO
	 * .getBplOutManifestDoxDetailsTOList())) { bplDoxManifestDOs = new
	 * ArrayList(bplDoxManifestTO .getBplOutManifestDoxDetailsTOList().size());
	 * // Setting Header values outManifestDO =
	 * headerDomainManifestProcessConverter(bplDoxManifestTO);
	 * bplDoxManifestDOs.add(outManifestDO); for (BPLOutManifestDoxDetailsTO
	 * bplManifestDoxDtlsTO : bplDoxManifestTO
	 * .getBplOutManifestDoxDetailsTOList()) { outManifestDO = new
	 * ManifestProcessDO();
	 * 
	 * bplDoxManifestDOs.add(outManifestDO); } } LOGGER.trace(
	 * "BPLOutManifestDoxConverter :: bplDoxManifestProcessDomainConverter() :: End --------> ::::::"
	 * ); return bplDoxManifestDOs; }
	 */

	/**
	 * Header domain manifest converter.
	 * 
	 * @param bplDoxManifestTO
	 *            the bpl dox manifest to
	 * @return the manifest do
	 */
	private static ManifestDO headerDomainManifestConverter(
			BPLOutManifestDoxTO bplDoxManifestTO) {
		LOGGER.trace("BPLOutManifestDoxConverter :: headerDomainManifestConverter() :: Start --------> ::::::");
		ManifestDO outManifestDO = new ManifestDO();
		Date manifestDate = DateUtil
				.slashDelimitedstringToDDMMYYYYFormat(bplDoxManifestTO
						.getManifestDate());
		outManifestDO.setManifestNo(bplDoxManifestTO.getManifestNo());
		outManifestDO.setManifestWeight(bplDoxManifestTO.getFinalWeight());
//		outManifestDO.setManifestDate(manifestDate);
		/*Submit or Close date and time to be populated in Manifest date and Time (In other words final updated date & time)*/
		outManifestDO.setManifestDate(Calendar.getInstance().getTime());
		outManifestDO.setManifestType(bplDoxManifestTO.getManifestType());
		outManifestDO
				.setDestOfficeId(bplDoxManifestTO.getDestinationOfficeId());
		outManifestDO.setOriginOfficeId(bplDoxManifestTO.getLoginOfficeId());
		if (bplDoxManifestTO.getBagLockNo() != null) {
			outManifestDO.setBagLockNo(bplDoxManifestTO.getBagLockNo());
		}
		if (bplDoxManifestTO.getBagRFID() != null) {
			outManifestDO.setBagRFID(bplDoxManifestTO.getBagRFID());
		}
		if (bplDoxManifestTO.getDestinationCityId() != null) {
			CityDO cityDO = new CityDO();
			cityDO.setCityId(bplDoxManifestTO.getDestinationCityId());
			outManifestDO.setDestinationCity(cityDO);
		}
		if (bplDoxManifestTO.getManifestStatus() != null) {
			outManifestDO.setManifestStatus(bplDoxManifestTO
					.getManifestStatus());
		}
		if (bplDoxManifestTO.getManifestType() != null) {
			outManifestDO.setManifestType(ManifestConstants.MANIFEST_TYPE_OUT);
		} else {
			outManifestDO.setManifestType(ManifestConstants.MANIFEST_TYPE_OUT);
		}

		if (bplDoxManifestTO.getProcessId() != null) {
			/*
			 * ProductDO productDO = new ProductDO();
			 * productDO.setProductId(bplDoxManifestTO.getProcessId());
			 */

			ProcessDO processDO = new ProcessDO();
			processDO.setProcessId(bplDoxManifestTO.getProcessId());
			outManifestDO.setUpdatingProcess(processDO);

		}
		LOGGER.trace("BPLOutManifestDoxConverter :: headerDomainManifestConverter() :: End --------> ::::::");
		return outManifestDO;
	}

	/**
	 * Header domain manifest process converter.
	 * 
	 * @param bplDoxManifestTO
	 *            the bpl dox manifest to
	 * @return the manifest process do
	 */
	/*
	 * private static ManifestProcessDO headerDomainManifestProcessConverter(
	 * BPLOutManifestDoxTO bplDoxManifestTO) { LOGGER.trace(
	 * "BPLOutManifestDoxConverter :: headerDomainManifestProcessConverter() :: Start --------> ::::::"
	 * ); ManifestProcessDO outManifestProcessDO = new ManifestProcessDO(); Date
	 * manifestDate = DateUtil
	 * .slashDelimitedstringToDDMMYYYYFormat(bplDoxManifestTO
	 * .getManifestDate()); outManifestProcessDO.setManifestDate(manifestDate);
	 * 
	 * outManifestProcessDO.setBplManifestType(bplDoxManifestTO
	 * .getManifestType());
	 * outManifestProcessDO.setBagLockNo(bplDoxManifestTO.getBagLockNo()); //
	 * outManifestProcessDO.setBagRFID(bplDoxManifestTO.getRfidNo());
	 * outManifestProcessDO.setDestOfficeId(bplDoxManifestTO
	 * .getDestinationCityId());
	 * outManifestProcessDO.setOriginOfficeId(bplDoxManifestTO
	 * .getLoginOfficeId());
	 * 
	 * 
	 * outManifestProcessDO.setDestinationOfficeId(bplDoxManifestTO.
	 * getDestinationOfficeId()); CityDO cityDO = new CityDO();
	 * cityDO.setCityName(bplDoxManifestTO.getDestinationOfficeName());
	 * outManifestProcessDO.setDestinationCity(cityDO);
	 * 
	 * LOGGER.trace(
	 * "BPLOutManifestDoxConverter :: headerDomainManifestProcessConverter() :: End --------> ::::::"
	 * ); return outManifestProcessDO; }
	 */

	// Constructing TOs based on Array's
	/**
	 * Bpl dox manifest dtls converter.
	 * 
	 * @param bplDoxManifestTO
	 *            the bpl dox manifest to
	 * @return the list
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static List<BPLOutManifestDoxDetailsTO> bplDoxManifestDtlsConverter(
			BPLOutManifestDoxTO bplDoxManifestTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("BPLOutManifestDoxConverter :: bplDoxManifestDtlsConverter() :: Start --------> ::::::");
		BPLOutManifestDoxDetailsTO bplOutManifestDoxDetailsTO = null;
		List<BPLOutManifestDoxDetailsTO> bplOutManifestDoxDetailsTOs = null;
		if (!StringUtil.isNull(bplDoxManifestTO)) {
			bplOutManifestDoxDetailsTOs = new ArrayList<>();
			if (bplDoxManifestTO.getManifestNos() != null
					&& bplDoxManifestTO.getManifestNos().length > 0) {
				for (int rowCount = 0; rowCount < bplDoxManifestTO
						.getManifestNos().length; rowCount++) {
					if (StringUtils.isNotEmpty(bplDoxManifestTO
							.getManifestNos()[rowCount])) {
						// Setting the common grid level attributes
						bplOutManifestDoxDetailsTO = (BPLOutManifestDoxDetailsTO) setUpManifestDtlsTOs(
								bplDoxManifestTO, rowCount);
						// Spefific to BPL Outmanifest Dox
						bplOutManifestDoxDetailsTOs
								.add(bplOutManifestDoxDetailsTO);
					}
				}
			}
		}
		LOGGER.trace("BPLOutManifestDoxConverter :: bplDoxManifestDtlsConverter() :: End --------> ::::::");
		return bplOutManifestDoxDetailsTOs;
	}

	//

	// Converter
	/**
	 * Out manifest dox domain converter.
	 * 
	 * @param manifestDO
	 *            the manifest do
	 * @return the bPL out manifest dox to
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public static BPLOutManifestDoxTO outManifestDoxDomainConverter(
			ManifestDO manifestDO) throws CGBusinessException {
		LOGGER.trace("BPLOutManifestDoxConverter :: outManifestDoxDomainConverter() :: Start --------> ::::::");
		// Set the common attributes for the header
		BPLOutManifestDoxTO bplOutManifestDoxTo = (BPLOutManifestDoxTO) outManifestDomainConverter(
				manifestDO, ManifestUtil.getBplOutManifestDoxFactory());
		// Set the specific attributes for header
		bplOutManifestDoxTo.setManifestType(manifestDO.getManifestType());
		bplOutManifestDoxTo.setManifestStatus(manifestDO.getManifestStatus());

		if (!StringUtil.isNull(manifestDO.getDestinationCity())) {
			bplOutManifestDoxTo.setDestinationRegionId(manifestDO
					.getDestinationCity().getRegion());
		}

		if (manifestDO.getMultipleDestinations() != null
				&& manifestDO.getMultipleDestinations().size() > 0) {

			Set<OutManifestDestinationDO> outManifestDestinationDOs = manifestDO
					.getMultipleDestinations();
			StringBuilder multiDestString = new StringBuilder();
			for (OutManifestDestinationDO outManifestDestinationDO : outManifestDestinationDOs) {

				if (outManifestDestinationDO.getOffice() != null) {
					bplOutManifestDoxTo
							.setDestinationRegionId(outManifestDestinationDO
									.getOffice().getOfficeId());
					if (StringUtils.isBlank(multiDestString.toString())) {
						multiDestString.append(outManifestDestinationDO
								.getOffice().getOfficeId());
					} else {
						multiDestString.append(",");
						multiDestString.append(outManifestDestinationDO
								.getOffice().getOfficeId());
					}
				}

				if (outManifestDestinationDO.getOffice().getOfficeTypeDO() != null) {
					bplOutManifestDoxTo
							.setOfficeTypeId(outManifestDestinationDO
									.getOffice().getOfficeTypeDO()
									.getOffcTypeId());
				}
			}
			bplOutManifestDoxTo
					.setMultiDestinations(multiDestString.toString());
			// added by niharika

			if (manifestDO.getDestOffice() == null) {
				bplOutManifestDoxTo.setIsMulDestination("Y");
			}

		}

		if (!StringUtil.isNull(manifestDO.getOfficeType())) {
			OfficeTypeDO officeTypeDO = manifestDO.getOfficeType();
			bplOutManifestDoxTo.setDestOfficeType(officeTypeDO.getOffcTypeId()
					+ "~" + officeTypeDO.getOffcTypeCode());
		}

		if (!StringUtil.isNull(manifestDO.getDestinationCity())) {
			CityDO cityDO = manifestDO.getDestinationCity();
			Integer regionId = cityDO.getRegion();
			RegionTO regionto = new RegionTO();
			regionto.setRegionId(regionId);
			bplOutManifestDoxTo.setDestRegionTO(regionto);
		}

		LOGGER.trace("BPLOutManifestDoxConverter :: outManifestDoxDomainConverter() :: End --------> ::::::");
		return bplOutManifestDoxTo;
	}

	/**
	 * Prepare bpl out manifest dtl list.
	 * 
	 * @param bplOutManifestTO
	 *            the bpl out manifest to
	 * @return the list
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static List<BPLOutManifestDoxDetailsTO> prepareBPLOutManifestDtlList(
			BPLOutManifestDoxTO bplOutManifestTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("BPLOutManifestDoxConverter :: prepareBPLOutManifestDtlList() :: Start --------> ::::::");
		BPLOutManifestDoxDetailsTO bplOutManifestDetailsTO = null;
		List<BPLOutManifestDoxDetailsTO> bplOutManifestDetailsTOs = null;
		int noOfElements = 0;

		if (!StringUtil.isNull(bplOutManifestTO)) {
			bplOutManifestDetailsTOs = new ArrayList<>(
					bplOutManifestTO.getManifestNos().length);
			for (int rowCount = 0; rowCount < bplOutManifestTO.getManifestNos().length; rowCount++) {
				if (!StringUtil
						.isStringEmpty(bplOutManifestTO.getManifestNos()[rowCount])) {
					noOfElements++;

				}

			}
			bplOutManifestTO.setRowCount(noOfElements);
			if (bplOutManifestTO.getManifestNos() != null
					&& bplOutManifestTO.getManifestNos().length > 0) {
				for (int rowCount = 0; rowCount < bplOutManifestTO
						.getManifestNos().length; rowCount++) {
					if (!StringUtil.isStringEmpty(bplOutManifestTO
							.getManifestNos()[rowCount])) {
						// Setting the common grid level attributes
						bplOutManifestDetailsTO = (BPLOutManifestDoxDetailsTO) setUpManifestDtlsTOs(
								bplOutManifestTO, rowCount);
						bplOutManifestDetailsTO.setPosition(bplOutManifestTO
								.getPosition()[rowCount]);
						bplOutManifestDetailsTOs.add(bplOutManifestDetailsTO);
					}

				}
			}
		}
		LOGGER.trace("BPLOutManifestDoxConverter :: prepareBPLOutManifestDtlList() :: End --------> ::::::");
		return bplOutManifestDetailsTOs;
	}

	/*
	 * public static ManifestProcessDO prepareManifestProcessDOList(
	 * BPLOutManifestDoxTO bplOutManifestTO) throws CGBusinessException,
	 * CGSystemException { LOGGER.trace(
	 * "BPLOutManifestDoxConverter :: prepareManifestProcessDOList() :: Start --------> ::::::"
	 * ); ManifestProcessDO manifestProcessDO = null;
	 * 
	 * // Setting Common attributes manifestProcessDO = OutManifestBaseConverter
	 * .outManifestBaseTransferObjConverter(bplOutManifestTO);
	 * 
	 * if (StringUtils.isNotBlank(bplOutManifestTO.getDestinationOffcId()) &&
	 * !(bplOutManifestTO.getDestinationOffcId().split(
	 * CommonConstants.TILD)[0]).equals("0")) {
	 * 
	 * manifestProcessDO.setDestOfficeId(Integer.valueOf(bplOutManifestTO
	 * .getDestinationOffcId().split(CommonConstants.TILD)[0])); } // Specific
	 * to MBPL Out manifest if
	 * (bplOutManifestTO.getBplManifestType().equals("PURE")) {
	 * manifestProcessDO.setBplManifestType(ManifestConstants.PURE); } else {
	 * manifestProcessDO.setBplManifestType(ManifestConstants.TRANS); }
	 * 
	 * manifestProcessDO.setManifestProcessId(bplOutManifestTO.getManifestProcessId
	 * ()); LOGGER.trace(
	 * "BPLOutManifestDoxConverter :: prepareManifestProcessDOList() :: End --------> ::::::"
	 * ); return manifestProcessDO; }
	 */
	// //////////////////////// converter for embidded in manifest id setting
	// /////////////////////
	/**
	 * @param bplOutManifestDoxTO
	 * @return
	 */
	public static List<ManifestMappedEmbeddedDO> domainConverterList4ManifestMappedEmbedded(
			BPLOutManifestDoxTO bplOutManifestDoxTO,
			Integer ManifestEmbeddedInId) {
		LOGGER.trace("BPLOutManifestDoxConverter :: domainConverterList4ManifestMappedEmbedded() :: Start --------> ::::::");

		List<ManifestMappedEmbeddedDO> manifestMappedEmbeddedDOs = new ArrayList<>();

		int noOfGridManifest = bplOutManifestDoxTO
				.getBplOutManifestDoxDetailsTOList().size();
		for (int i = 0; i < noOfGridManifest; i++) {

			ManifestMappedEmbeddedDO manifestMappedEmbeddedDO = new ManifestMappedEmbeddedDO();
			ManifestDO manifestDO = new ManifestDO();
			manifestDO.setManifestId(ManifestEmbeddedInId);
			manifestMappedEmbeddedDO
					.setManifestId(bplOutManifestDoxTO
							.getBplOutManifestDoxDetailsTOList().get(i)
							.getManifestId());
			manifestMappedEmbeddedDO.setPosition(bplOutManifestDoxTO
					.getBplOutManifestDoxDetailsTOList().get(i).getPosition());
			manifestMappedEmbeddedDO.setEmbeddedIn(manifestDO);
			manifestMappedEmbeddedDOs.add(manifestMappedEmbeddedDO);
		}
		LOGGER.trace("BPLOutManifestDoxConverter :: domainConverterList4ManifestMappedEmbedded() :: End --------> ::::::");
		return manifestMappedEmbeddedDOs;
	}

	/**
	 * @param manifestDO
	 * @return
	 */
	public static BPLOutManifestDoxTO convertToFromDO(ManifestDO manifestDO,
			ManifestInputs manifestInputsTO) throws CGBusinessException,
			CGSystemException {
		BPLOutManifestDoxTO bplOutManifestDoxTO = new BPLOutManifestDoxTO();
		if (!StringUtil.isNull(manifestDO)) {
			LOGGER.trace("BPLOutManifestDoxConverter :: convertToFromDO() :: Start --------> ::::::");

			// InManifest Integration starts

			ManifestInputs manifestInputsTOs = new ManifestInputs();
			manifestInputsTOs.setManifestNumber(manifestDO.getManifestNo());
			manifestInputsTOs.setManifestType(manifestDO.getManifestType());
			manifestInputsTOs
					.setManifestDirection(ManifestConstants.MANIFEST_TYPE_OUT);
			manifestInputsTOs.setLoginOfficeId(manifestDO.getOriginOffice()
					.getOfficeId());
			manifestInputsTOs.setManifestId(manifestDO.getManifestId());
			manifestInputsTOs.setHeaderManifestNo(manifestInputsTO
					.getHeaderManifestNo());
			if (!manifestDO.getManifestType().equalsIgnoreCase(
					ManifestConstants.MANIFEST_DIRECTION_IN)) {
				if (outManifestCommonService
						.isManifestEmbeddedIn(manifestInputsTOs)) {
					ExceptionUtil
							.prepareBusinessException(ManifestErrorCodesConstants.MANIFEST_ALREADY_EMBEDDED);

				}
			}

			bplOutManifestDoxTO.setFinalWeight(manifestDO.getManifestWeight());
			bplOutManifestDoxTO.setManifestId(manifestDO.getManifestId());
			bplOutManifestDoxTO.setManifestStatus(manifestDO
					.getManifestStatus());
			if (manifestDO.getDestinationCity() != null) {
				CityTO cityTo = new CityTO();
				cityTo.setCityName((manifestDO.getDestinationCity()
						.getCityName()));
				cityTo.setCityId((manifestDO.getDestinationCity().getCityId()));
				bplOutManifestDoxTO.setDestinationCityTO(cityTo);

			}
			if (manifestDO.getDestOffice() != null) {
				OfficeTO officeTo = new OfficeTO();
				officeTo.setOfficeId(manifestDO.getDestOffice().getOfficeId());
				officeTo.setOfficeName(manifestDO.getDestOffice()
						.getOfficeName());
				officeTo.setOfficeCode(manifestDO.getDestOffice()
						.getOfficeCode());
				bplOutManifestDoxTO.setDestinationOfficeTO(officeTo);
			}

			bplOutManifestDoxTO.setOperatingOffice(manifestDO
					.getOperatingOffice());
			bplOutManifestDoxTO.setReceivedStatus(manifestDO
					.getReceivedStatus());
			bplOutManifestDoxTO.setNoOfElements(manifestDO.getNoOfElements());
			bplOutManifestDoxTO.setBplManifestType(manifestDO.getBplManifestType());
			bplOutManifestDoxTO.setManifestOpenType(manifestDO.getManifestOpenType());
						
			if (!StringUtil.isNull(manifestDO.getOriginOffice())) {
				bplOutManifestDoxTO.setOriginOfficeId(manifestDO
						.getOriginOffice().getOfficeId());
			}
			ProductDO prodDo = manifestDO.getManifestedProductSeries();

			if (prodDo != null) {
				ProductTO productTO = new ProductTO();
				productTO.setProductId(prodDo.getProductId());
				productTO.setConsgSeries(prodDo.getConsgSeries());
				bplOutManifestDoxTO.setProduct(productTO);
			}

		}

		LOGGER.trace("BPLOutManifestDoxConverter :: convertToFromDO() :: End --------> ::::::");
		return bplOutManifestDoxTO;
	}

	public static List<ManifestMappedEmbeddedDO> setEmbeddedManifestDetails(
			BPLOutManifestDoxTO bplOutManifestDoxTO) {
		LOGGER.trace("BPLOutManifestDoxConverter :: setEmbeddedManifestDetails() :: Start --------> ::::::");
		List<ManifestMappedEmbeddedDO> embeddedManifestDOs = new ArrayList<>();
		// Set ConsignmentManifestDO
		if (bplOutManifestDoxTO.getManifestIds() != null
				&& bplOutManifestDoxTO.getManifestIds().length > 0) {
			embeddedManifestDOs = ManifestUtil
					.setEmbeddedManifestDtls(bplOutManifestDoxTO);
		}
		LOGGER.trace("BPLOutManifestDoxConverter :: setEmbeddedManifestDetails() :: End --------> ::::::");
		return embeddedManifestDOs;

	}

	public static List<BPLOutManifestDoxDetailsTO> bplOutManifestDomainConvertorForEmbeddedIn(
			ManifestDO manifestDOHeader) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("BPLOutManifestDoxConverter :: bplOutManifestDomainConvertorForEmbeddedIn() :: Start --------> ::::::");
		List<BPLOutManifestDoxDetailsTO> detailsTOs = null;
		if (!StringUtil.isEmptyColletion(manifestDOHeader
				.getEmbeddedManifestDOs())) {
			detailsTOs = new ArrayList<BPLOutManifestDoxDetailsTO>(
					manifestDOHeader.getEmbeddedManifestDOs().size());
			for (ManifestDO manifestDOChild : manifestDOHeader
					.getEmbeddedManifestDOs()) {

				BPLOutManifestDoxDetailsTO bplOutManifestDetailsTO = new BPLOutManifestDoxDetailsTO();
				// bplOutManifestDetailsTO.setMapEmbeddedManifestId(manifestDOChild.getMapId());
				// ManifestDO manifestDOChild =
				// outManifestCommonService.getManifestById(manifestDOChild.getManifestId());
				/*
				 * bplOutManifestDetailsTO.setManifestId(manifestDOChild
				 * .getManifestId());
				 */
				// need id for print
				bplOutManifestDetailsTO.setManifestId(manifestDOChild
						.getManifestId());
				bplOutManifestDetailsTO.setManifestNo(manifestDOChild
						.getManifestNo());
				bplOutManifestDetailsTO.setWeight(manifestDOChild
						.getManifestWeight());
				bplOutManifestDetailsTO.setPosition(manifestDOChild
						.getPosition());
				bplOutManifestDetailsTO.setManifestOpenType(manifestDOChild.getManifestOpenType());
				bplOutManifestDetailsTO.setBplManifestType(manifestDOChild.getBplManifestType());
				CityDO cityDO = manifestDOChild.getDestinationCity();
				if (!StringUtil.isNull(cityDO)) {
					bplOutManifestDetailsTO.setDestCity(cityDO.getCityName());
					bplOutManifestDetailsTO.setDestCityId(cityDO.getCityId());
				}
				if (!StringUtil.isNull(manifestDOChild.getNoOfElements())) {
					bplOutManifestDetailsTO.setNoOfConsignment(manifestDOChild
							.getNoOfElements());
				}
				if (!StringUtil.isNull(manifestDOChild.getContainsOnlyCoMail())) {
					bplOutManifestDetailsTO
							.setComailStatusPrint(manifestDOChild
									.getContainsOnlyCoMail());
				}
				detailsTOs.add(bplOutManifestDetailsTO);
			}

			Collections.sort(detailsTOs);
		}
		LOGGER.trace("BPLOutManifestDoxConverter :: bplOutManifestDomainConvertorForEmbeddedIn() :: End --------> ::::::");
		return detailsTOs;

	}

	public static ManifestDO prepareManifestDOList(
			BPLOutManifestDoxTO bplOutManifestDoxTO) {
		LOGGER.trace("BPLOutManifestDoxConverter :: prepareManifestDOList() :: Start --------> ::::::");

		ManifestDO manifestDO = null;
		// Setting Common attributes
		manifestDO = OutManifestBaseConverter
				.outManifestTransferObjConverter(bplOutManifestDoxTO);

		if (StringUtils.isNotBlank(bplOutManifestDoxTO.getDestinationOffcId())
				&& !(bplOutManifestDoxTO.getDestinationOffcId().split(
						CommonConstants.TILD)[0]).equals("0")) {
			OfficeDO officeDO = new OfficeDO();
			officeDO.setOfficeId(Integer.valueOf(bplOutManifestDoxTO
					.getDestinationOffcId().split(CommonConstants.TILD)[0]));
			manifestDO.setDestOffice(officeDO);
		}

		if (StringUtils.isEmpty(bplOutManifestDoxTO.getDestOfficeType())
				|| (bplOutManifestDoxTO.getDestOfficeType() != null)) {
			OfficeTypeDO officeTypeDO = new OfficeTypeDO();
			officeTypeDO.setOffcTypeId(Integer.parseInt(bplOutManifestDoxTO
					.getDestOfficeType().split(CommonConstants.TILD)[0]));
			manifestDO.setOfficeType(officeTypeDO);
		}

		// Specific to MBPL Out manifest
		if (bplOutManifestDoxTO.getBplManifestType().equals("PURE")) {
			manifestDO.setBplManifestType(ManifestConstants.PURE);
		} else {
			manifestDO.setBplManifestType(ManifestConstants.TRANS);
		}

		manifestDO.setNoOfElements(bplOutManifestDoxTO.getRowCount());

		LOGGER.trace("BPLOutManifestDoxConverter :: prepareManifestDOList() :: End --------> ::::::");
		return manifestDO;
	}

	public static ManifestDO convertInPacketToDO(ManifestDO manifestDO,
			ProcessDO processDO, BPLOutManifestDoxTO bplOutManifestDoxTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("BPLOutManifestDoxConverter :: convertInPacketToDO() :: Start --------> ::::::");

		ManifestDO manifest = new ManifestDO();
		manifest.setManifestId(null);
		manifest.setManifestNo(manifestDO.getManifestNo());
		manifest.setBagLockNo(manifestDO.getBagLockNo());
		manifest.setManifestDirection(CommonConstants.MANIFEST_TYPE_OUT);
		manifest.setManifestProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_PKT_DOX);

		if (!StringUtil.isStringEmpty(manifestDO.getReceivedStatus())
				&& manifestDO.getReceivedStatus().equals(
						ManifestConstants.EXCESS_MANIFEST)) {

			if (StringUtils.isNotBlank(bplOutManifestDoxTO
					.getDestinationOffcId())
					&& !(bplOutManifestDoxTO.getDestinationOffcId().split(
							CommonConstants.TILD)[0]).equals("0")) {
				OfficeDO officeDO = new OfficeDO();

				officeDO.setOfficeId(Integer.valueOf(bplOutManifestDoxTO
						.getDestinationOffcId().split(CommonConstants.TILD)[0]));
				manifest.setDestOffice(officeDO);
			} else {
				manifest.setDestOffice(null);

				Set<OutManifestDestinationDO> multipleDests = prepareOutManifestDestinations(bplOutManifestDoxTO);
				if (!StringUtil.isEmptyColletion(multipleDests)) {
					manifest.setMultipleDestinations(multipleDests);
				}
				manifest.setMultipleDestination(CommonConstants.YES);
			}

			CityDO cityDO = new CityDO();
			if (!StringUtil.isNull(bplOutManifestDoxTO.getDestinationCityId())) {
				cityDO.setCityId(bplOutManifestDoxTO.getDestinationCityId());
				manifest.setDestinationCity(cityDO);
			}
		} else {
			if (!StringUtil.isNull(manifestDO.getDestOffice())) {
				manifest.setDestOffice(manifestDO.getDestOffice());
			}
			CityDO cityDO = new CityDO();
			if (!StringUtil.isNull(manifestDO.getDestinationCity())) {
				cityDO.setCityId(manifestDO.getDestinationCity().getCityId());
				manifest.setDestinationCity(cityDO);
			}
			manifest.setMultipleDestination(manifestDO.getMultipleDestination());
		}

		if (!StringUtil.isNull(manifestDO.getOriginOffice())) {
			manifest.setOriginOffice(manifestDO.getOriginOffice());
		}

		if (!StringUtil.isNull(Calendar.getInstance())) {
			manifest.setCreatedDate(Calendar.getInstance().getTime());
		}
		manifest.setNoOfElements(manifestDO.getNoOfElements());
		if (StringUtil.isEmptyInteger(manifest.getNoOfElements())) {
			Integer noOfElements = outManifestCommonService
					.getNoOfElementsFromIn(manifest.getManifestNo());
			manifest.setNoOfElements(noOfElements);
		}
		manifest.setContainsOnlyCoMail(manifestDO.getContainsOnlyCoMail());
		manifest.setManifestStatus(manifestDO.getManifestStatus());
		manifest.setManifestType(CommonConstants.MANIFEST_TYPE_OUT);
		manifest.setBagRFID(manifestDO.getBagRFID());
		ConsignmentTypeDO manifestLoadContent = new ConsignmentTypeDO();
		if (!StringUtil.isNull(manifestDO.getManifestLoadContent())) {
			manifestLoadContent.setConsignmentId(manifestDO
					.getManifestLoadContent().getConsignmentId());
			manifest.setManifestLoadContent(manifestLoadContent);
		} else {
			ConsignmentTypeTO consgType = new ConsignmentTypeTO();
			consgType
					.setConsignmentCode(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
			List<ConsignmentTypeTO> consignmanetTypeTOs = outManifestCommonService
					.getConsignmentTypes(consgType);
			if (!CGCollectionUtils.isEmpty(consignmanetTypeTOs)) {
				consgType = consignmanetTypeTOs.get(0);
				ConsignmentTypeDO consgTypeDO = new ConsignmentTypeDO();
				consgTypeDO.setConsignmentId(consgType.getConsignmentId());
				manifest.setManifestLoadContent(consgTypeDO);
			}
		}
		manifest.setUpdatedDate(manifestDO.getUpdatedDate());
		if (!StringUtil.isNull(Calendar.getInstance())) {
			manifest.setManifestDate(Calendar.getInstance().getTime());
		}

		if (!StringUtil.isEmptyInteger(bplOutManifestDoxTO.getLoginOfficeId())) {
			manifest.setOperatingOffice(bplOutManifestDoxTO.getLoginOfficeId());
		}
		// manifest.setOperatingLevel(manifestDO.getOperatingLevel());
		manifest.setBplManifestType(manifestDO.getBplManifestType());
		manifest.setManifestOpenType(manifestDO.getManifestOpenType());
		manifest.setManifestWeight(manifestDO.getManifestWeight());
		manifest.setReceivedStatus(manifestDO.getReceivedStatus());
		manifest.setUpdatingProcess(processDO);
		manifest.setDtToCentral(CommonConstants.NO);
		manifest.setCreatedBy(manifestDO.getCreatedBy());
		manifest.setUpdatedBy(manifestDO.getUpdatedBy());
		LOGGER.trace("BPLOutManifestDoxConverter :: convertInPacketToDO() :: End --------> ::::::");
		return manifest;
	}
}
