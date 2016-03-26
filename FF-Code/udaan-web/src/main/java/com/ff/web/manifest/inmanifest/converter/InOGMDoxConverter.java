/**
 * 
 */
package com.ff.web.manifest.inmanifest.converter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.manifest.ComailDO;
import com.ff.domain.manifest.ComailManifestDO;
import com.ff.domain.manifest.ConsignmentManifestDO;
import com.ff.domain.manifest.GridItemOrderDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.manifest.inmanifest.InManifestOGMDetailTO;
import com.ff.manifest.inmanifest.InManifestOGMTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.tracking.service.TrackingUniversalService;
import com.ff.web.booking.utils.BookingUtils;
import com.ff.web.consignment.service.ConsignmentService;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.inmanifest.service.InManifestCommonService;
import com.ff.web.manifest.inmanifest.service.InOGMDoxService;
import com.ff.web.manifest.inmanifest.utils.InManifestUtils;
import com.ff.web.manifest.service.ManifestCommonService;

// TODO: Auto-generated Javadoc
/**
 * The Class InOGMDoxConverter.
 * 
 * @author uchauhan
 */
public class InOGMDoxConverter extends InManifestBaseConverter {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(InOGMDoxConverter.class);

	/** The geography common service. */
	private static GeographyCommonService geographyCommonService;

	/** The in ogm dox service. */
	private static InOGMDoxService inOgmDoxService;

	/** The tracking universal service. */
	private static TrackingUniversalService trackingUniversalService;

	/** The in manifest common service. */
	private static InManifestCommonService inManifestCommonService;

	/** The consignment service. */
	private static ConsignmentService consignmentService;

	/** The manifest common service. */
	private static ManifestCommonService manifestCommonService;
	
	/**
	 * @param trackingUniversalService
	 *            the trackingUniversalService to set
	 */
	public static void setTrackingUniversalService(
			TrackingUniversalService trackingUniversalService) {
		InOGMDoxConverter.trackingUniversalService = trackingUniversalService;
	}

	/**
	 * Sets the in ogm dox service.
	 * 
	 * @param inOgmDoxService
	 *            the inOgmDoxService to set
	 */
	public static void setInOgmDoxService(InOGMDoxService inOgmDoxService) {
		InOGMDoxConverter.inOgmDoxService = inOgmDoxService;
	}

	/**
	 * Sets the geography common service.
	 * 
	 * @param geographyCommonService
	 *            the geographyCommonService to set
	 */
	public static void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		InOGMDoxConverter.geographyCommonService = geographyCommonService;
	}

	/**
	 * @param inManifestCommonService
	 *            the inManifestCommonService to set
	 */
	public static void setInManifestCommonService(
			InManifestCommonService inManifestCommonService) {
		InOGMDoxConverter.inManifestCommonService = inManifestCommonService;
	}

	/**
	 * Sets the consignment service.
	 *
	 * @param consignmentService the new consignment service
	 */
	public static void setConsignmentService(ConsignmentService consignmentService) {
		InOGMDoxConverter.consignmentService = consignmentService;
	}

	/**
	 * @param manifestCommonService the manifestCommonService to set
	 */
	public static void setManifestCommonService(
			ManifestCommonService manifestCommonService) {
		InOGMDoxConverter.manifestCommonService = manifestCommonService;
	}
	
	/**
	 * creates a list of InManifestOGMDetailTO from list of ConsignmentDO.
	 * 
	 * @param consignmentDO
	 *            the consignment do
	 * @return list of InManifestOGMDetailTO
	 * @throws CGBusinessException
	 *             if any buisness rules fail
	 * @throws CGSystemException
	 *             for any database failure
	 */
	public static InManifestOGMDetailTO createInOGMDoxDetailTO(
			ConsignmentDO consignmentDO) throws CGBusinessException,
			CGSystemException {
		PincodeDO pincodeDO = null;
		InManifestOGMDetailTO detailTO = null;
		if (consignmentDO != null) {

			detailTO = new InManifestOGMDetailTO();
			detailTO.setConsignmentNumber(consignmentDO.getConsgNo());
			detailTO.setConsignmentId(consignmentDO.getConsgId());
			if (consignmentDO.getDestPincodeId() != null) {
				pincodeDO = (consignmentDO.getDestPincodeId());

				PincodeTO pincodeTO = new PincodeTO();
				CGObjectConverter.createToFromDomain(pincodeDO, pincodeTO);
				detailTO.setDestPincode(pincodeTO);
				
				CityTO cityTO = new CityTO();
				cityTO = geographyCommonService.getCity(pincodeDO.getPincode());
				detailTO.setDestCity(cityTO);
			}
			detailTO.setManifestWeight(consignmentDO.getFinalWeight());
			detailTO.setMobileNumber(consignmentDO.getMobileNo());

			detailTO.setRemarks(consignmentDO.getRemarks());
			detailTO.setReceivedStatus(consignmentDO
					.getReceivedStatus());
			
			//get Pricing details
			detailTO.setCodAmt(consignmentDO
					.getCodAmt());
			detailTO.setToPayAmt(consignmentDO
					.getTopayAmt());
			detailTO.setLcBankName(consignmentDO
					.getLcBankName());

			String cnSeries = consignmentDO.getConsgNo().substring(4, 5);
			if(StringUtils.equalsIgnoreCase(cnSeries, "D")){
				detailTO.setCodAmt(consignmentDO.getLcAmount());
			}
			detailTO.setBaAmt(consignmentDO.getBaAmt());
		}
		return detailTO;
	}

	/**
	 * creates a list of InManifestOGMDetailTO from inManifestOGMTO.
	 * 
	 * @param inManifestOGMTO
	 *            the in manifest ogmto
	 * @return list of InManifestOGMDetailTO
	 */
	public static List<InManifestOGMDetailTO> createInOGMDoxDetailTO(
			InManifestOGMTO inManifestOGMTO) {

		List<InManifestOGMDetailTO> ogmDetailTOs = null;

		if (!StringUtil.isNull(inManifestOGMTO)) {

			CityTO cityTO = null;
			PincodeTO pincodeTO = null;

			ogmDetailTOs = new ArrayList<InManifestOGMDetailTO>(
					inManifestOGMTO.getConsgNumbers().length);
			if (inManifestOGMTO.getConsgNumbers() != null
					&& inManifestOGMTO.getConsgNumbers().length > 0) {
				for (int rowCount = 0; rowCount < inManifestOGMTO
						.getConsgNumbers().length; rowCount++) {

					if (inManifestOGMTO.getIsCN()[rowCount] != null
							&& (inManifestOGMTO.getIsCN()[rowCount])
									.equals("Y")) {

						InManifestOGMDetailTO ogmDetailTO = new InManifestOGMDetailTO();
						if (StringUtils.isNotEmpty(inManifestOGMTO
								.getConsgNumbers()[rowCount])) {

							ogmDetailTO.setConsignmentNumber(inManifestOGMTO
									.getConsgNumbers()[rowCount]);
						}
						cityTO = new CityTO();
						cityTO.setCityName(inManifestOGMTO.getDestCityNames()[rowCount]);
						cityTO.setCityId(inManifestOGMTO.getDestCityIds()[rowCount]);
						ogmDetailTO.setDestCity(cityTO);
						pincodeTO = new PincodeTO();
						pincodeTO
								.setPincode(inManifestOGMTO.getDestPincodes()[rowCount]);
						pincodeTO.setPincodeId(inManifestOGMTO
								.getDestPincodeIds()[rowCount]);
						ogmDetailTO.setDestPincode(pincodeTO);
						ogmDetailTO.setManifestWeight(inManifestOGMTO
								.getManifestWeights()[rowCount]);
						ogmDetailTO.setMobileNumber(inManifestOGMTO
								.getMobileNos()[rowCount]);
						ogmDetailTO
								.setRemarks(inManifestOGMTO.getRemarks()[rowCount]);
						ogmDetailTO.setConsignmentManifestId(inManifestOGMTO
								.getConsignmentManifestIds()[rowCount]);
						ogmDetailTO.setReceivedStatus(inManifestOGMTO
								.getReceivedStatus()[rowCount]);
						ogmDetailTOs.add(ogmDetailTO);
					}

				}

			}
		}
		return ogmDetailTOs;
	}

	/**
	 * creates a list of ConsignmentDOs.
	 * 
	 * @param ogmDoxTO
	 *            the ogm dox to
	 * @return list of ConsignmentDO
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static List<ConsignmentDO> createConsigmentDOList(
			InManifestOGMTO ogmDoxTO) throws CGBusinessException,
			CGSystemException {

		List<ConsignmentDO> consignmentDOs = null;
		if (ogmDoxTO.getConsgNumbers().length > 0) {
			consignmentDOs = new ArrayList<>(ogmDoxTO.getConsgNumbers().length);
		}
		ConsignmentTypeDO consignmentTypeDO = null;
		for (int i = 0; i < ogmDoxTO.getConsgNumbers().length; i++) {
			if (StringUtils.isBlank(ogmDoxTO.getConsgNumbers()[i])) {
				continue;
			}
			if (ogmDoxTO.getIsCN()[i] != null
					&& ogmDoxTO.getIsCN()[i].equalsIgnoreCase("Y")) {
				ConsignmentDO consignmentDO = new ConsignmentDO();
				consignmentTypeDO = new ConsignmentTypeDO();
				ConsignmentTypeTO consgTypeTO = ogmDoxTO.getConsignmentTypeTO();
				if (consgTypeTO != null) {
					if (!StringUtil.isEmptyInteger(consgTypeTO
							.getConsignmentId())) {
						CGObjectConverter.createDomainFromTo(consgTypeTO,
								consignmentTypeDO);
						consignmentDO.setConsgType(consignmentTypeDO);
					}
				}
				if (!StringUtil.isEmptyInteger(ogmDoxTO.getConsignmentIds()[i])) {
					consignmentDO.setConsgId(ogmDoxTO.getConsignmentIds()[i]);
					InBPLManifestConverter.setUpdateFlag4DBSync(consignmentDO);
				} else {
					InBPLManifestConverter.setSaveFlag4DBSync(consignmentDO);
				}
				consignmentDO.setConsgNo(ogmDoxTO.getConsgNumbers()[i]);
				PincodeDO pincodeDO = new PincodeDO();
				pincodeDO.setPincodeId(ogmDoxTO.getDestPincodeIds()[i]);
				pincodeDO.setPincode(ogmDoxTO.getDestPincodes()[i]);
				consignmentDO.setDestPincodeId(pincodeDO);
				consignmentDO.setFinalWeight(ogmDoxTO.getManifestWeights()[i]);
				consignmentDO.setMobileNo(ogmDoxTO.getMobileNos()[i]);
				if (!StringUtil.isEmptyInteger(ogmDoxTO.getOriginOfficeTO()
						.getOfficeId())) {
					consignmentDO.setOrgOffId(ogmDoxTO.getOriginOfficeTO()
							.getOfficeId());
				}

				// added operating office
				consignmentDO
						.setOperatingOffice(ogmDoxTO.getLoggedInOfficeId());
				consignmentDO.setRemarks(ogmDoxTO.getRemarks()[i]);
				consignmentDOs.add(consignmentDO);
			}
		}
		return consignmentDOs;
	}

	/**
	 * creates a list of ConsignmentDOs.
	 * 
	 * @param ogmDoxTO
	 *            the ogm dox to
	 * @return list of ConsignmentDO
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static Set<ComailDO> createComailDOList(InManifestOGMTO ogmDoxTO)
			throws CGBusinessException, CGSystemException {

		Set<ComailDO> comailDOs = null;
		if (ogmDoxTO.getCoMailTOs().length > 0) {
			comailDOs = new LinkedHashSet<>(ogmDoxTO.getCoMailTOs().length);
		}

		for (int i = 0; i < ogmDoxTO.getCoMailTOs().length; i++) {
			if (StringUtils.isBlank(ogmDoxTO.getCoMailTOs()[i])) {
				continue;
			}
			ComailDO comailDO = new ComailDO();

			if (!StringUtil.isEmptyInteger(ogmDoxTO.getComailIds()[i])) {
				comailDO.setCoMailId(ogmDoxTO.getComailIds()[i]);
				InBPLManifestConverter.setUpdateFlag4DBSync(comailDO);
			} else {
				InBPLManifestConverter.setSaveFlag4DBSync(comailDO);
			}
			comailDO.setCoMailNo(ogmDoxTO.getCoMailTOs()[i]);
			if (ogmDoxTO.getOriginOfficeTO() != null) {
				comailDO.setOriginOffice(ogmDoxTO.getOriginOfficeTO()
						.getOfficeId());
			}
			comailDO.setDestinationOffice(ogmDoxTO.getLoggedInOfficeId());
			InManifestUtils.setCreatedByUpdatedBy(comailDO);	
			comailDOs.add(comailDO);
		}
		return comailDOs;
	}

	/**
	 * Prepare comail manifest do list.
	 * 
	 * @param ogmDoxTO
	 *            the ogm dox to
	 * @param comailDOs
	 *            the comail d os
	 * @return the sets the
	 */
	public static Set<ComailManifestDO> prepareComailManifestDOList(
			InManifestOGMTO ogmDoxTO, List<ComailDO> comailDOs) {
		Set<ComailManifestDO> comailManifestDOs = new HashSet<>();
		int i = 0;
		for (ComailDO comailDO : comailDOs) {
			ComailManifestDO comailManifestDO = new ComailManifestDO();
			comailManifestDO.setComailDO(comailDO);
			if (!StringUtil.isEmptyInteger(ogmDoxTO.getComailManifestIds()[i])) {
				comailManifestDO.setCoMailManifestId(ogmDoxTO
						.getComailManifestIds()[i]);
			}
			i++;
			comailManifestDOs.add(comailManifestDO);
		}
		return comailManifestDOs;

	}

	/**
	 * Creates the manifest consignment do.
	 * 
	 * @param ogmDoxTO
	 *            the ogm dox to
	 * @param manifestDO
	 *            the manifest do
	 * @return the manifest do
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static ManifestDO createManifestConsignmentDO(
			InManifestOGMTO ogmDoxTO, ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("InOGMDoxConverter::createManifestConsignmentDO::START------------>:::::::");

		manifestDO = InManifestBaseConverter
				.inManifestDomainObjConverter(ogmDoxTO);

		if (!StringUtil.isEmptyInteger(ogmDoxTO.getDestCityId())) {
			final CityDO cityDO = new CityDO();
			cityDO.setCityId(ogmDoxTO.getDestCityId());
			manifestDO.setDestinationCity(cityDO);
		}
		
		if (!StringUtil.isEmptyInteger(ogmDoxTO.getConsignmentTypeId())) {
			final ConsignmentTypeDO manifestLoadContent = new ConsignmentTypeDO();
			manifestLoadContent.setConsignmentId(ogmDoxTO
					.getConsignmentTypeId());
			manifestDO.setManifestLoadContent(manifestLoadContent);
		}
		
		InManifestUtils.setEmbeddedInRemarksPositionToDO(ogmDoxTO, manifestDO);

		String consgNoArray[] = new String[ogmDoxTO.getInManifestOGMDetailTOs().size()];
		int noOfElements = 0;
		for (int i = 0; i < ogmDoxTO.getInManifestOGMDetailTOs().size(); i++) {
			if (StringUtils.isBlank(ogmDoxTO.getInManifestOGMDetailTOs().get(i)
					.getConsignmentNumber())) {
				continue;
			}
			consgNoArray[i] = (ogmDoxTO.getInManifestOGMDetailTOs().get(i).getConsignmentNumber());
			noOfElements++;
		}
		for (int i = 0; i < ogmDoxTO.getCoMailTOs().length; i++) {
			if (StringUtils.isBlank(ogmDoxTO.getCoMailTOs()[i])) {
				continue;
			}
			noOfElements++;
		}
		manifestDO.setNoOfElements(noOfElements);
		if(StringUtils.isNotBlank(ogmDoxTO.getManifestReceivedStatus())){
			manifestDO.setReceivedStatus(ogmDoxTO.getManifestReceivedStatus());			
		}
		// manifestDO.setManifestId(null);
		manifestDO.setManifestWeight(ogmDoxTO.getTotalManifestWeight());
		if (StringUtils.isNotBlank(ogmDoxTO.getIsCoMail())
				&& (ogmDoxTO.getIsCoMail().equals(CommonConstants.YES) || ogmDoxTO
						.getIsCoMail().equals(CommonConstants.NO))) {
			manifestDO.setContainsOnlyCoMail(ogmDoxTO.getIsCoMail());
		}

		Set<ConsignmentDO> consignmentDOs = InOGMDoxConverter
				.setupConsigmentDOs(ogmDoxTO);
		manifestDO.setConsignments(consignmentDOs);
		/*		 
		Set<ConsignmentManifestDO> consignmentManifestDOs = null;
		Set<ComailManifestDO> comailManifestDOs = null;
		 List<ConsignmentDO> consignmentDOs = InOGMDoxConverter
				.createConsigmentDOList(ogmDoxTO);
		for (ConsignmentDO consignmentDO : consignmentDOs) {
			consignmentDO = inOgmDoxService
					.saveOrUpdateConsignment(consignmentDO);
		}
		consignmentManifestDOs = InOGMDoxConverter
				.prepareConsignmentManifestDOList(ogmDoxTO, consignmentDOs);
		manifestDO.setManifestConsgDtls(consignmentManifestDOs);*/
		// prepareProcessMapTO(ogmDoxTO,consignmentDOs);
		Set<ComailDO> comailDOs = InOGMDoxConverter
				.createComailDOList(ogmDoxTO);
		manifestDO.setComails(comailDOs);
		/*for (ComailDO comailDO : comailDOs) {
			comailDO = inOgmDoxService.saveOrUpdateComail(comailDO);
		}
		comailDOs = InOGMDoxConverter.prepareComailManifestDOList(
				ogmDoxTO, comailDOs);*/


		/***************** Set Grid Position Start *****************/
		GridItemOrderDO gridItemOrderDO = new GridItemOrderDO();
		if (!StringUtil.isEmpty(ogmDoxTO.getConsgNumbers())) {
			gridItemOrderDO.setConsignments(consgNoArray);
		}
		if (!StringUtil.isEmpty(ogmDoxTO.getCoMailTOs())) {
			gridItemOrderDO.setComails(ogmDoxTO.getCoMailTOs());
		}
		gridItemOrderDO = manifestCommonService.arrangeOrder(gridItemOrderDO,
				ManifestConstants.ACTION_SAVE);
		if (gridItemOrderDO != null
				&& StringUtils.isNotBlank(gridItemOrderDO.getGridPosition())) {
			manifestDO.setGridItemPosition(gridItemOrderDO.getGridPosition());
		}
		/***************** Set Grid Position End *****************/
		
		LOGGER.debug("InOGMDoxConverter::createManifestConsignmentDO::END------------>:::::::");
		return manifestDO;
	}

	private static Set<ConsignmentDO> setupConsigmentDOs(
			InManifestOGMTO ogmDoxTO) throws CGBusinessException,
			CGSystemException {

		Set<ConsignmentDO> consignmentDOs = null;
		if (ogmDoxTO.getConsgNumbers().length > 0) {
			consignmentDOs = new LinkedHashSet<>(
					ogmDoxTO.getConsgNumbers().length);
		}

		for (int i = 0; i < ogmDoxTO.getConsgNumbers().length; i++) {

			if (StringUtils.isBlank(ogmDoxTO.getConsgNumbers()[i])
					|| (ogmDoxTO.getIsCN()[i] != null && !ogmDoxTO.getIsCN()[i]
							.equalsIgnoreCase(CommonConstants.YES))) {
				continue;
			}

			//consignmentTO convereter
			ConsignmentTO consignmentTO = setUpConsignmentTO(ogmDoxTO, i);

			ConsignmentDO consignmentDO = null;

			if (!StringUtil.isEmptyInteger(ogmDoxTO.getConsignmentIds()[i])) {
				// updateConsignment
				List<ConsignmentTO> consgTOs = new ArrayList<>();
				consgTOs.add(consignmentTO);
				List<ConsignmentDO> consignmentDOs2 = consignmentService
						.updateConsignments(consgTOs);
				if (!StringUtil.isEmptyColletion(consignmentDOs2)) {
					consignmentDO = consignmentDOs2.get(0);
				}
			} else {
				// saveConsingment
				consignmentDO = BookingUtils.setUpConsignment(consignmentTO);
				inManifestCommonService.getAndSetBookingOffice(consignmentDO);
				//TODO
				/*if (!StringUtil.isEmptyInteger(inBagManifestParcelTO
						.getDestinationOfficeId())) {
					consignmentDO.setOrgOffId(inBagManifestParcelTO
							.getDestinationOfficeId());
				}*/
			}
			//consignmentDO.setRemarks(ogmDoxTO.getRemarks()[i]);
			inManifestCommonService.setBillingFlagsInConsignment(consignmentDO);
			InManifestUtils.setCreatedByUpdatedBy(consignmentDO);
			consignmentDOs.add(consignmentDO);

		}
		return consignmentDOs;
	}

	private static ConsignmentTO setUpConsignmentTO(
			InManifestOGMTO ogmDoxTO, int i) {

		ConsignmentTO consignmentTO = new ConsignmentTO();

		if (!StringUtil.isEmptyInteger(ogmDoxTO.getConsignmentIds()[i])) {
			consignmentTO.setConsgId(ogmDoxTO.getConsignmentIds()[i]);
			
		} else if (ogmDoxTO.getOriginOfficeTO() != null && !StringUtil.isEmptyInteger(ogmDoxTO.getOriginOfficeTO()
				.getOfficeId())) {
			consignmentTO.setOrgOffId(ogmDoxTO.getOriginOfficeTO().getOfficeId());
		}
		
		ConsignmentTypeTO consgTypeTO = ogmDoxTO.getConsignmentTypeTO();
		if (consgTypeTO != null && !StringUtil.isEmptyInteger(consgTypeTO.getConsignmentId())) {
			consignmentTO.setConsgTypeId(consgTypeTO.getConsignmentId());
		}
		if (!StringUtil.isEmptyInteger(ogmDoxTO
				.getDestPincodeIds()[i])) {
			PincodeTO pincodeTO = new PincodeTO();
			pincodeTO.setPincodeId(ogmDoxTO
					.getDestPincodeIds()[i]);
			consignmentTO.setDestPincode(pincodeTO);
		}
		consignmentTO.setFinalWeight(ogmDoxTO.getManifestWeights()[i]);
		consignmentTO.setMobileNo(ogmDoxTO.getMobileNos()[i]);
		consignmentTO.setRemarks(ogmDoxTO.getRemarks()[i]);
		consignmentTO.setConsgNo(ogmDoxTO.getConsgNumbers()[i]);
		if (StringUtil.isEmptyInteger(ogmDoxTO.getConsignmentIds()[i])) {
			consignmentTO.setActualWeight(consignmentTO.getFinalWeight());
		}
		//TODO need to check
		/*if (!StringUtil.isEmptyInteger(ogmDoxTO.getOriginOfficeTO()
				.getOfficeId())) {
			consignmentTO.setOrgOffId(ogmDoxTO.getOriginOfficeTO()
					.getOfficeId());
		}*/
		// added operating office
		consignmentTO.setOperatingOffice(ogmDoxTO.getLoggedInOfficeId());
		consignmentTO.setUpdatedProcessFrom(ogmDoxTO.getProcessTO());

		consignmentTO.setTopayAmt(ogmDoxTO.getToPayAmts()[i]);
		consignmentTO.setBaAmt(ogmDoxTO.getBaAmts()[i]);

		String cnSeries = consignmentTO.getConsgNo().substring(4, 5);
		if(StringUtils.equalsIgnoreCase(cnSeries, "D")){
			consignmentTO.setLcBankName(ogmDoxTO.getLcBankNames()[i]);
		}
		//cod Amt, LC Amt
		if(!StringUtil.isEmptyDouble(ogmDoxTO.getCodAmts()[i])){
			if(StringUtils.equalsIgnoreCase(cnSeries, "D")){
				consignmentTO.setLcAmount(ogmDoxTO.getCodAmts()[i]);
			}else{
				consignmentTO.setCodAmt(ogmDoxTO.getCodAmts()[i]);
			}
		}

		return consignmentTO;
	}

}
