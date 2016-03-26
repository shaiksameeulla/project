/**
 * 
 */
package com.ff.web.manifest.pod.converter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.manifest.ConsignmentManifestDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.geography.RegionTO;
import com.ff.manifest.pod.PODManifestDtlsTO;
import com.ff.manifest.pod.PODManifestTO;
import com.ff.organization.OfficeTO;
import com.ff.tracking.ProcessMapTO;
import com.ff.tracking.TrackingParameterTO;
import com.ff.universe.consignment.dao.ConsignmentCommonDAO;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.tracking.constant.UniversalTrackingConstants;
import com.ff.universe.tracking.service.TrackingUniversalService;
import com.ff.web.manifest.pod.constants.PODManifestConstants;

/**
 * @author nkattung
 * 
 */
public class PODManifestConverter {
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(PODManifestConverter.class);

	/**
	 * TO Set PODManifest details to TO
	 * 
	 * @param podManifestTO
	 * @return
	 * @throws CGSystemException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static PODManifestTO setUpPODMnfstDetails(PODManifestTO podManifestTO)
			throws CGSystemException {
		LOGGER.trace("PODManifestConverter :: setUpPODMnfstDetails() :: Start --------> ::::::");
		Set<PODManifestDtlsTO> podManifestDtlsTOs = null;
		podManifestDtlsTOs = new HashSet();
		if (podManifestTO.getConsgNumbers() != null
				&& podManifestTO.getConsgNumbers().length > 0) {
			for (int rowCount = 0; rowCount < podManifestTO.getConsgNumbers().length; rowCount++) {
				if (StringUtils
						.isNotEmpty(podManifestTO.getConsgNumbers()[rowCount])) {
					PODManifestDtlsTO podManifestDtlsTO = new PODManifestDtlsTO();
					podManifestDtlsTO.setConsgNumber(podManifestTO
							.getConsgNumbers()[rowCount]);
					podManifestDtlsTO
							.setConsgId(podManifestTO.getConsgIds()[rowCount]);
					podManifestDtlsTO
							.setDlvDate(podManifestTO.getDlvDates()[rowCount]);
					podManifestDtlsTO.setReceivedDate(podManifestTO
							.getReceivedDates()[rowCount]);
					podManifestDtlsTO.setRecvNameOrCompSeal(podManifestTO
							.getReceiverNames()[rowCount]);
					podManifestDtlsTO
							.setPosition(podManifestTO.getPosition()[rowCount]);
					if (!StringUtil.isNull(podManifestTO.getReceiveStatus())
							&& podManifestTO.getReceiveStatus().length > 0
							&& StringUtils.isNotEmpty(podManifestTO
									.getReceiveStatus()[rowCount]))
						podManifestDtlsTO.setReceivedStatus(podManifestTO
								.getReceiveStatus()[rowCount]);
					podManifestDtlsTOs.add(podManifestDtlsTO);
				}
			}
			podManifestTO.setManifestDtls(podManifestDtlsTOs);
		}
		LOGGER.trace("PODManifestConverter :: setUpPODMnfstDetails() :: END --------> ::::::");
		return podManifestTO;
	}

	/**
	 * @param podManifestTO
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public static ManifestDO podManifestHerderDomainConverter(
			PODManifestTO podManifestTO) throws CGSystemException,
			CGBusinessException {
		ManifestDO manifestDO = null;
		manifestDO = setUpPODMnfstHeaderValues(podManifestTO);
		return manifestDO;
	}

	/**
	 * SET PODManifest Header values
	 * 
	 * @param podManifestTO
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	private static ManifestDO setUpPODMnfstHeaderValues(
			PODManifestTO podManifestTO) throws CGSystemException,
			CGBusinessException {
		LOGGER.trace("PODManifestConverter :: setUpPODMnfstHeaderValues() :: Start --------> ::::::");
		ManifestDO manifest = new ManifestDO();
		manifest.setManifestNo(podManifestTO.getManifestNo());
		OfficeDO originOffice = new OfficeDO();
		originOffice.setOfficeId(podManifestTO.getDispachOfficeTO()
				.getOfficeId());
		manifest.setOriginOffice(originOffice);
		OfficeDO destOffice = new OfficeDO();
		destOffice.setOfficeId(podManifestTO.getDestOffId());
		manifest.setDestOffice(destOffice);
		CityDO destCity = new CityDO();
		destCity.setCityId(podManifestTO.getDestCityId());
		manifest.setDestinationCity(destCity);
//		manifest.setManifestDate(DateUtil
//				.parseStringDateToDDMMYYYYHHMMFormat(podManifestTO
//						.getManifestDate()));
		/*Submit or Close date and time to be populated in Manifest date and Time (In other words final updated date & time)*/
		manifest.setManifestDate(Calendar.getInstance().getTime());
		if (!StringUtil.isEmptyInteger(podManifestTO.getManifestId()))
			manifest.setManifestId(podManifestTO.getManifestId());
		// Setting consignment manifes details
		// Set<ConsignmentManifestDO> manifestConsignments =
		// podManifestCNsDomainConverter(podManifestTO);
		// if (!StringUtil.isEmptyColletion(manifestConsignments))
		// manifest.setManifestConsgDtls(manifestConsignments);//FIXME design
		// change
		manifest.setManifestProcessCode(CommonConstants.PROCESS_POD);
		manifest.setManifestStatus(CommonConstants.MANIFEST_STATUS_CLOSED);
		manifest.setManifestType(PODManifestConstants.POD_MANIFEST);
		manifest.setUpdatedBy(podManifestTO.getLogingUserId());
		manifest.setUpdatedDate(manifest.getUpdatedDate());
		manifest.setCreatedBy(podManifestTO.getLogingUserId());
		manifest.setCreatedDate(manifest.getCreatedDate());
		manifest.setManifestDirection(podManifestTO.getManifestType());

		LOGGER.trace("PODManifestConverter :: setUpPODMnfstHeaderValues() :: END --------> ::::::");
		return manifest;
	}

	/**
	 * PODManifest Consignment domain converter
	 * 
	 * @param podManifestTO
	 * @param consignmentCommonDAO
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public static Set<ConsignmentDO> podManifestConsgDomainConverter(
			PODManifestTO podManifestTO,
			ConsignmentCommonDAO consignmentCommonDAO)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("PODManifestConverter :: podManifestConsgDomainConverter() :: Start --------> ::::::");
		Set<ConsignmentDO> consignments = null;
		if (!StringUtil.isEmptyColletion(podManifestTO.getManifestDtls())) {
			consignments = new HashSet<ConsignmentDO>();
			for (PODManifestDtlsTO podManifestDtls : podManifestTO
					.getManifestDtls()) {
				ConsignmentDO consg = consignmentCommonDAO
						.getConsingmentDtls(podManifestDtls.getConsgNumber());
				consg.setRecvNameOrCompName(podManifestDtls
						.getRecvNameOrCompSeal());
				consg.setDeliveredDate(DateUtil
						.parseStringDateToDDMMYYYYHHMMFormat(podManifestDtls
								.getDlvDate()));
				consg.setReceivedDateTime(DateUtil
						.parseStringDateToDDMMYYYYHHMMFormat(podManifestDtls
								.getReceivedDate()));
				consg.setUpdatedBy(podManifestTO.getLogingUserId());
				consg.setUpdatedDate(Calendar.getInstance().getTime());
				consignments.add(consg);
			}

		}
		LOGGER.trace("PODManifestConverter :: podManifestConsgDomainConverter() :: END --------> ::::::");
		return consignments;
	}

	/**
	 * PODManifest Process domain converter
	 * 
	 * @param podManifestTO
	 * @param manifestProcess 
	 * @return
	 */
	/*public static List<ManifestProcessDO> podManifestProcessDomainConverter(
			PODManifestTO podManifestTO, ManifestProcessDO manifest) {
		LOGGER.trace("PODManifestConverter :: podManifestProcessDomainConverter() :: Start --------> ::::::");
		List<ManifestProcessDO> podMaifetsProcessDOs = new ArrayList<>();
		if(StringUtil.isNull(manifest)){
			manifest = new ManifestProcessDO();
		}
		manifest.setManifestNo(podManifestTO.getManifestNo());
		manifest.setOriginOfficeId(podManifestTO.getDispachOfficeTO()
				.getOfficeId());
		manifest.setDestOfficeId(podManifestTO.getDestOffId());
		manifest.setManifestDate(DateUtil
				.parseStringDateToDDMMYYYYHHMMFormat(podManifestTO
						.getManifestDate()));
		if (!StringUtil.isEmptyInteger(podManifestTO.getManifestProcessId())) {
			manifest.setManifestProcessId(podManifestTO.getManifestProcessId());
		}
		manifest.setManifestProcessCode(CommonConstants.PROCESS_POD);
		manifest.setManifestDirection(podManifestTO.getManifestType());
		if (podManifestTO.getManifestType().equalsIgnoreCase(
				PODManifestConstants.POD_MANIFEST_OUT)) {
			manifest.setDestCityId(podManifestTO.getDestCityId());
		}
		if (!StringUtil
				.isNull(podManifestTO.getDispachOfficeTO().getOfficeId())) {

		}
		manifest.setUpdatedBy(podManifestTO.getLogingUserId());
		manifest.setUpdatedDate(manifest.getUpdatedDate());
		manifest.setCreatedBy(podManifestTO.getLogingUserId());
		manifest.setCreatedDate(manifest.getCreatedDate());
		manifest.setManifestProcessNo(podManifestTO.getManifestProcessNumber());

		podMaifetsProcessDOs.add(manifest);
		LOGGER.trace("PODManifestConverter :: podManifestProcessDomainConverter() :: End --------> ::::::");
		return podMaifetsProcessDOs;
	}*/

	/**
	 * @param podManifestTO
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public static Set<ConsignmentManifestDO> podManifestCNsDomainConverter(
			PODManifestTO podManifestTO) throws CGSystemException,
			CGBusinessException {
		LOGGER.trace("PODManifestConverter :: podManifestCNsDomainConverter() :: Start --------> ::::::");
		Set<ConsignmentManifestDO> manifestConsignments = null;
		if (!StringUtil.isEmptyColletion(podManifestTO.getManifestDtls())) {
			manifestConsignments = new HashSet<ConsignmentManifestDO>();
			for (PODManifestDtlsTO podManifestDtls : podManifestTO
					.getManifestDtls()) {
				ConsignmentManifestDO manifestCN = new ConsignmentManifestDO();
				ConsignmentDO consg = new ConsignmentDO();
				consg.setConsgId(podManifestDtls.getConsgId());
				manifestCN.setConsignment(consg);
				//manifestCN.setPosition(podManifestDtls.getPosition());
				// TODO R or N
				if (StringUtils.equalsIgnoreCase(
						podManifestTO.getManifestType(),
						PODManifestConstants.POD_MANIFEST_IN)) {
					/*manifestCN.setReceivedStatus(podManifestDtls
							.getReceivedStatus());*/
				}
				manifestConsignments.add(manifestCN);
			}
		}
		LOGGER.trace("PODManifestConverter :: podManifestCNsDomainConverter() :: END --------> ::::::");
		return manifestConsignments;
	}

	/**
	 * POD Manifest DO to TO converter
	 * 
	 * @param manifest
	 * @return
	 * @throws CGSystemException
	 */
	public static PODManifestTO podMnfstTrasnferObjConverter(
			ManifestDO manifest, GeographyCommonService geographyCommonService)
			throws CGSystemException {
		LOGGER.trace("PODManifestConverter :: podMnfstTrasnferObjConverter() :: Start --------> ::::::");
		PODManifestTO podMnfst = new PODManifestTO();
		// Setting Herder details
		podMnfst.setManifestId(manifest.getManifestId());
		podMnfst.setManifestNo(manifest.getManifestNo());
		podMnfst.setManifestDate(DateUtil
				.getDateInDDMMYYYYHHMMSlashFormat(manifest.getManifestDate()));
		OfficeTO orgOff = new OfficeTO();
		orgOff.setOfficeId(manifest.getOriginOffice().getOfficeId());
		orgOff.setOfficeCode(manifest.getOriginOffice().getOfficeCode() + " - "
				+ manifest.getOriginOffice().getOfficeName());
		orgOff.setOfficeName( manifest.getOriginOffice().getOfficeName());
		orgOff.setAddress1(manifest.getOriginOffice().getAddress1());
		orgOff.setAddress2(manifest.getOriginOffice().getAddress2());
		orgOff.setAddress3(manifest.getOriginOffice().getAddress3());
		podMnfst.setDispachOfficeTO(orgOff);
		podMnfst.setDestOffId(manifest.getDestOffice().getOfficeId());
		podMnfst.setDestOffice(manifest.getDestOffice().getOfficeName());
		podMnfst.setDestCity(manifest.getDestinationCity().getCityName());
		podMnfst.setDestCityId(manifest.getDestinationCity().getCityId());
		podMnfst.setRegionId(manifest.getDestinationCity().getRegion());
		RegionTO region = geographyCommonService.getRegionByIdOrName(
				CommonConstants.EMPTY_STRING, manifest.getDestinationCity()
						.getRegion());
		podMnfst.setDestRegion(region.getRegionName());
		// Setting child details
		Set<ConsignmentDO> manifestCns = manifest.getConsignments();/*
																	 * manifest
																	 * cn
																	 */
		Set<PODManifestDtlsTO> posManifestDtls = new HashSet<>();
		for (ConsignmentDO consg : manifestCns) {
			PODManifestDtlsTO podManifestDtlsTO = new PODManifestDtlsTO();
			// ConsignmentDO consg = manifetsCN.getConsignment();
			podManifestDtlsTO.setConsgId(consg.getConsgId());
			podManifestDtlsTO.setConsgNumber(consg.getConsgNo());
			if (!StringUtil.isNull(consg.getReceivedDateTime()))
				podManifestDtlsTO.setReceivedDate(DateUtil
						.getDateInDDMMYYYYHHMMSlashFormat(consg
								.getReceivedDateTime()));
			if (!StringUtil.isNull(consg.getDeliveredDate()))
				podManifestDtlsTO.setDlvDate(DateUtil
						.getDateInDDMMYYYYHHMMSlashFormat(consg
								.getDeliveredDate()));
			podManifestDtlsTO.setRecvNameOrCompSeal(consg
					.getRecvNameOrCompName());
			// podManifestDtlsTO.setPosition(manifetsCN.getPosition());
			posManifestDtls.add(podManifestDtlsTO);
		}
		// posManifestDtls = new TreeSet <>(posManifestDtls);
		// Collections.(posManifestDtls);
		podMnfst.setManifestDtls(posManifestDtls);
		podMnfst.setManifestType(manifest.getManifestType());
		LOGGER.trace("PODManifestConverter :: podMnfstTrasnferObjConverter() :: End --------> ::::::");
		return podMnfst;
	}

	public static ProcessMapTO setProcessmapTO(PODManifestTO podManifestTO,
			String processNumber,
			TrackingUniversalService trackingUniversalService)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("PODManifestConverter :: setProcessmapTO() :: Start --------> ::::::");
		ProcessMapTO processMapTO = new ProcessMapTO();
		TrackingParameterTO parameterTO = null;
		List<TrackingParameterTO> parameterTOs = new ArrayList<>();

		processMapTO.setProcessNumber(processNumber);
		processMapTO.setArtifactType(CommonConstants.MANIFEST);
		processMapTO.setManifestNo(podManifestTO.getManifestNo());

		parameterTO = new TrackingParameterTO();
		parameterTO.setParamKey(UniversalTrackingConstants.BRANCH_OFFICE);
		parameterTO.setParamValue(podManifestTO.getDispachOfficeTO()
				.getOfficeId()
				+ "#"
				+ podManifestTO.getDispachOfficeTO().getOfficeName()
				+ " "
				+ podManifestTO.getDispachOfficeTO().getOfficeTypeTO()
						.getOffcTypeDesc());
		parameterTOs.add(parameterTO);

		parameterTO = new TrackingParameterTO();
		parameterTO.setParamKey(UniversalTrackingConstants.MANIFEST_NUMBER);
		parameterTO.setParamValue(podManifestTO.getManifestNo());
		parameterTOs.add(parameterTO);

		parameterTO = new TrackingParameterTO();
		parameterTO.setParamKey(UniversalTrackingConstants.HUB_OFFICE);
		parameterTO.setParamValue(podManifestTO.getDestOfficeTO().getOfficeId()
				+ "#"
				+ podManifestTO.getDestOfficeTO().getOfficeName()
				+ " "
				+ podManifestTO.getDestOfficeTO().getOfficeTypeTO()
						.getOffcTypeDesc());
		parameterTOs.add(parameterTO);

		processMapTO.setParameterTOs(parameterTOs);
		LOGGER.trace("PODManifestConverter :: setProcessmapTO() :: End --------> ::::::");
		return processMapTO;

	}
}
