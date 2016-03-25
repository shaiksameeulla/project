package com.cg.lbs.bcun.service.dataformater;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.BcunManifestDO;
import com.ff.domain.manifest.ComailDO;
import com.ff.domain.manifest.OutManifestDestinationDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.tracking.ProcessDO;

/**
 * The Class CreateManifestFormater.
 * 
 * @author narmdr
 */
public class CreateManifestFormater extends AbstractDataFormater {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(CreateManifestFormater.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cg.lbs.bcun.service.dataformater.AbstractDataFormater#formatInsertData
	 * (com.capgemini.lbs.framework.domain.CGBaseDO,
	 * com.cg.lbs.bcun.service.BcunDatasyncService)
	 */
	@Override
	public CGBaseDO formatInsertData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) {
		LOGGER.trace("CreateManifestFormater::formatInsertData::START------------>:::::::");
		BcunManifestDO headerDO = (BcunManifestDO) baseDO;
		getAndSetManifest(bcunService, headerDO);
		LOGGER.trace("CreateManifestFormater::formatInsertData::END------------>:::::::");
		return headerDO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cg.lbs.bcun.service.dataformater.AbstractDataFormater#formatUpdateData
	 * (com.capgemini.lbs.framework.domain.CGBaseDO,
	 * com.cg.lbs.bcun.service.BcunDatasyncService)
	 */
	@Override
	public CGBaseDO formatUpdateData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) {
		LOGGER.trace("CreateManifestFormater::formatUpdateData::START------------>:::::::");
		BcunManifestDO headerDO = (BcunManifestDO) baseDO;
		getAndSetManifest(bcunService, headerDO);
		LOGGER.trace("CreateManifestFormater::formatUpdateData::END------------>:::::::");
		return headerDO;
	}

	/**
	 * Gets the and set manifest.
	 * 
	 * @param bcunService
	 *            the bcun service
	 * @param sourceManifetsDO
	 *            the source manifets do
	 * @return the and set manifest
	 */
	private static void getAndSetManifest(BcunDatasyncService bcunService,
			BcunManifestDO sourceManifetsDO) {
		LOGGER.info("CreateManifestFormater::getAndSetManifest::START------------>:::::::");
		try {
			BcunManifestDO destManifestDO = getManifest(bcunService,
					sourceManifetsDO);
			if (!StringUtil.isNull(sourceManifetsDO)
					&& !StringUtil.isNull(destManifestDO)) {
				updateManifest(bcunService, destManifestDO, sourceManifetsDO);
			} else {
				createManifest(bcunService, sourceManifetsDO);
			}

		} catch (Exception e) {
			LOGGER.error("Exception happened in getAndSetManifest of CreateManifestFormater..."
					, e);
			throw e;
		}
		LOGGER.info("CreateManifestFormater::getAndSetManifest::END------------>:::::::");
	}

	/**
	 * Creates the manifest.
	 * 
	 * @param bcunService
	 *            the bcun service
	 * @param headerDO
	 *            the header do
	 */
	private static void createManifest(BcunDatasyncService bcunService,
			BcunManifestDO headerDO) {
		LOGGER.trace("CreateManifestFormater::createManifest::START------------>:::::::");
		headerDO.setManifestId(null);
		headerDO.setManifestEmbeddedIn(null);

		getComails(bcunService, headerDO);
		getConsignments(bcunService, headerDO);
		getEmbeddedManifestDOs(bcunService, headerDO);

		Set<OutManifestDestinationDO> outManifestDestDOs = headerDO
				.getMultipleDestinations();
		if (!StringUtil.isEmptyColletion(outManifestDestDOs)) {
			for (OutManifestDestinationDO outManifestDstDO : outManifestDestDOs) {
				outManifestDstDO.setOutManifestDestinationId(null);
				outManifestDstDO.setManifestId(headerDO.getManifestId());
			}
		}
		LOGGER.trace("CreateManifestFormater::createManifest::END------------>:::::::");
	}

	/**
	 * Gets the embedded manifest d os.
	 * 
	 * @param bcunService
	 *            the bcun service
	 * @param headerDO
	 *            the header do
	 * @return the embedded manifest d os
	 */
	private static void getEmbeddedManifestDOs(BcunDatasyncService bcunService,
			BcunManifestDO headerDO) {
		LOGGER.trace("CreateManifestFormater::getEmbeddedManifestDOs::START------------>:::::::");
		if (!StringUtil.isEmptyColletion(headerDO.getEmbeddedManifestDOs())) {
			Set<BcunManifestDO> manifestDOs = new HashSet<>();
			for (BcunManifestDO manifestDO : headerDO.getEmbeddedManifestDOs()) {
				BcunManifestDO childManifestDO = getManifest(bcunService,
						manifestDO);
				if (childManifestDO == null) {
					LOGGER.trace("CreateManifestFormater::getEmbeddedManifestDOs::create child manifest with embeddedIn manifestNo, manifestType, manifestProcessCode, operatingOffice, ::"
							+ manifestDO.getManifestNo()
							+ ", "
							+ manifestDO.getManifestType()
							+ ", "
							+ manifestDO.getManifestProcessCode()
							+ ", "
							+ manifestDO.getOperatingOffice()
							+ "------------>:::::::");
					manifestDO.setManifestId(null);
					manifestDO.setEmbeddedManifestDOs(null);
					getConsignments(bcunService, manifestDO);
					getComails(bcunService, manifestDO);
					manifestDO.setMultipleDestinations(null);
					childManifestDO = manifestDO;
				} else {
					// compareManifestWeight(childManifestDO, manifestDO);
					LOGGER.trace("CreateManifestFormater::getEmbeddedManifestDOs::update child manifest with embeddedIn manifestNo, manifestType, manifestProcessCode, operatingOffice, ::"
							+ childManifestDO.getManifestNo()
							+ ", "
							+ childManifestDO.getManifestType()
							+ ", "
							+ childManifestDO.getManifestProcessCode()
							+ ", "
							+ childManifestDO.getOperatingOffice()
							+ "------------>:::::::");
					compareManifestWeight(manifestDO, childManifestDO);
					compareManifestDestination(bcunService, manifestDO,
							childManifestDO);
				}
				LOGGER.trace("CreateManifestFormater::getEmbeddedManifestDOs::Header Manifest No, ManifestEmbeddedIn(header manifest Id)::"
						+ headerDO.getManifestNo()
						+ ", "
						+ headerDO.getManifestId() + "------------>:::::::");
				childManifestDO.setManifestEmbeddedIn(headerDO.getManifestId());
				if(!StringUtil.isStringEmpty(childManifestDO.getMandatoryFlag()) && childManifestDO.getMandatoryFlag().equalsIgnoreCase(CommonConstants.YES)){
					headerDO.setMandatoryFlag(CommonConstants.YES);
				}
				manifestDOs.add(childManifestDO);
			}
			headerDO.setEmbeddedManifestDOs(manifestDOs);
		}
		LOGGER.trace("CreateManifestFormater::getEmbeddedManifestDOs::END------------>:::::::");
	}

	/**
	 * Gets the consignments.
	 * 
	 * @param bcunService
	 *            the bcun service
	 * @param manifestDO
	 *            the manifest do
	 * @return the consignments
	 */
	private static void getConsignments(BcunDatasyncService bcunService,
			BcunManifestDO manifestDO) {
		LOGGER.trace("CreateManifestFormater::getConsignments::START------------>:::::::");
		if (!StringUtil.isEmptyColletion(manifestDO.getConsignments())) {
			Set<ConsignmentDO> consignmentDOs = new HashSet<>();
			for (ConsignmentDO consignmentDO : manifestDO.getConsignments()) {
				ConsignmentDO newConsignmentDO = null;
				try {
					newConsignmentDO = BcunManifestUtils.getConsignment(
							bcunService, consignmentDO);
				} catch (CGBusinessException e) {
					LOGGER.error("CreateManifestFormater::getConsignments::Invalid Consignment origin office :: Booking office and Consignment origin office should be same.------------>:::::::");
					LOGGER.error("CreateManifestFormater::getConsignments::Consignment No. : "
							+ consignmentDO.getConsgNo()
							+ " removed from the Manifest No. "
							+ manifestDO.getManifestNo()
							+ ".------------>:::::::");
					/**
					 * since incoming Consignment origin office is different from existing consignment origin office, considering the Existing consignment is valid and proceeding further
					 */
					newConsignmentDO = BcunManifestUtils.getConsg(bcunService, consignmentDO);
				}
				if (newConsignmentDO != null) {
					consignmentDOs.add(newConsignmentDO);
					setMandatoryFlagForManifestFromConsg(manifestDO,
							newConsignmentDO);
				}
				
			}
			
			manifestDO.setConsignments(consignmentDOs);
		}
		LOGGER.trace("CreateManifestFormater::getConsignments::END------------>:::::::");
	}

	private static void setMandatoryFlagForManifestFromConsg(
			BcunManifestDO manifestDO, ConsignmentDO newConsignmentDO) {
		if(!StringUtil.isStringEmpty(newConsignmentDO.getMandatoryFlag()) && newConsignmentDO.getMandatoryFlag().equalsIgnoreCase(CommonConstants.YES)){
			manifestDO.setMandatoryFlag(CommonConstants.YES);
		}
	}

	/**
	 * Gets the comails.
	 * 
	 * @param bcunService
	 *            the bcun service
	 * @param manifestDO
	 *            the manifest do
	 * @return the comails
	 */
	private static void getComails(BcunDatasyncService bcunService,
			BcunManifestDO manifestDO) {
		LOGGER.trace("CreateManifestFormater::getComails::START------------>:::::::");
		if (!StringUtil.isEmptyColletion(manifestDO.getComails())) {
			for (ComailDO comailDO : manifestDO.getComails()) {
				getAndSetComail(comailDO, bcunService);
			}
		}
		LOGGER.trace("CreateManifestFormater::getComails::END------------>:::::::");
	}

	/**
	 * Gets the manifest.
	 * 
	 * @param bcunService
	 *            the bcun service
	 * @param manifestDO
	 *            the manifest do
	 * @return the manifest
	 */
	@SuppressWarnings("unchecked")
	private static BcunManifestDO getManifest(BcunDatasyncService bcunService,
			BcunManifestDO manifestDO) {
		LOGGER.trace("CreateManifestFormater::getManifest::START------------>:::::::");

		/*String manifestNo = manifestDO.getManifestNo();
		Integer officeId = manifestDO.getOriginOffice();
		String manifestType = manifestDO.getManifestType();
		String query = "getManifestByOrgOfficeNoAndType";

		// For In Manifest
		if (StringUtils.equalsIgnoreCase(CommonConstants.MANIFEST_TYPE_IN,
				manifestDO.getManifestType())) {
			query = "getManifestByOperatOfficeNoAndType";
			officeId = manifestDO.getOperatingOffice();
		}

		String[] params = { "manifestNo", "officeId", "manifestType" };
		Object[] values = { manifestNo, officeId, manifestType };*/
		String[] params = { "manifestNo", "operatingOffice", "manifestType",
				"manifestProcessCode" };
		Object[] values = { manifestDO.getManifestNo(),
				manifestDO.getOperatingOffice(), manifestDO.getManifestType(),
				manifestDO.getManifestProcessCode() };

		LOGGER.trace("CreateManifestFormater::getManifest For Manifest params are => "
				+ Arrays.asList(params)
				+ " and values are => "
				+ Arrays.asList(values) + "::Processing------------>:::::::");

		List<BcunManifestDO> destManifestDos = (List<BcunManifestDO>) bcunService
				.getDataByNamedQueryAndNamedParam(
						"getManifestByOperatOfficeNoTypeAndProcess", params,
						values);
		BcunManifestDO destManifestDO = (!StringUtil
				.isEmptyColletion(destManifestDos)) ? destManifestDos.get(0)
				: null;

		LOGGER.trace("CreateManifestFormater::getManifest::END------------>:::::::");
		return destManifestDO;
	}

	/**
	 * Gets the and set comail.
	 * 
	 * @param comailDO
	 *            the comail do
	 * @param bcunService
	 *            the bcun service
	 * @return the and set comail
	 */
	private static void getAndSetComail(ComailDO comailDO,
			BcunDatasyncService bcunService) {
		LOGGER.trace("CreateManifestFormater::getAndSetComail::START------------>:::::::");
		if (comailDO == null) {
			return;
		}
		String[] comailParams = { "coMailNo" };
		Object[] comailValues = { comailDO.getCoMailNo() };
		LOGGER.trace("CreateManifestFormater::getAndSetComail For Comail params are => "
				+ Arrays.asList(comailParams)
				+ " and comailValues are => "
				+ Arrays.asList(comailValues)
				+ "::Processing------------>:::::::");

		Integer coMailId = bcunService.getUniqueId("getComailIdByComailNo",
				comailParams, comailValues);
		comailDO.setCoMailId(coMailId);
		LOGGER.trace("CreateManifestFormater::getAndSetComail::END------------>:::::::");
	}

	/**
	 * Update manifest.
	 * 
	 * @param bcunService
	 *            the bcun service
	 * @param destManifestDO
	 *            the dest manifest do
	 * @param sourceManifetsDO
	 *            the source manifets do
	 */
	private static void updateManifest(BcunDatasyncService bcunService,
			BcunManifestDO destManifestDO, BcunManifestDO sourceManifetsDO) {
		LOGGER.trace("CreateManifestFormater::updateManifest::START------------>:::::::");
		// Start...Manifest Header

		// Setting header manifest Id
		sourceManifetsDO.setManifestId(destManifestDO.getManifestId());
		sourceManifetsDO.setManifestEmbeddedIn(destManifestDO
				.getManifestEmbeddedIn());
		getComails(bcunService, sourceManifetsDO);
		getConsignments(bcunService, sourceManifetsDO);

		// Comparing weight from Source - Destination
		compareManifestWeight(destManifestDO, sourceManifetsDO);
		compareManifestDestination(bcunService, destManifestDO,
				sourceManifetsDO);

		// Start...Manifest Grid
		getEmbeddedManifestDOs(bcunService, sourceManifetsDO);

		// Setting for multiple destinations
		Set<OutManifestDestinationDO> sourceMultipleDestnations = sourceManifetsDO
				.getMultipleDestinations();
		Set<OutManifestDestinationDO> destMultipleDestnations = destManifestDO
				.getMultipleDestinations();
		if (!StringUtil.isEmptyColletion(sourceMultipleDestnations)
				&& !StringUtil.isEmptyColletion(destMultipleDestnations)) {
			for (OutManifestDestinationDO sourceManifetsDesiDO : sourceMultipleDestnations) {
				for (OutManifestDestinationDO destManifetsDesiDO : destMultipleDestnations) {
					OfficeDO sourceOffice = sourceManifetsDesiDO.getOffice();
					OfficeDO destOffice = destManifetsDesiDO.getOffice();
					if (StringUtils.equalsIgnoreCase(
							sourceOffice.getOfficeCode(),
							destOffice.getOfficeCode())) {
						sourceManifetsDesiDO.setManifestId(destManifetsDesiDO
								.getManifestId());
						sourceManifetsDesiDO
								.setOutManifestDestinationId(destManifetsDesiDO
										.getOutManifestDestinationId());
						sourceManifetsDesiDO
								.setIsOutManifestDestinationUpdated(Boolean.TRUE);
						break;
					}
				}
			}
		}

		// new OutManifestDestinations who all are not updated needs to create
		for (OutManifestDestinationDO outManifestDestinationDO : sourceMultipleDestnations) {
			if (!outManifestDestinationDO.getIsOutManifestDestinationUpdated()) {
				Integer outManifestDestinationId = getOutManifestDestinationId(
						bcunService, destManifestDO.getManifestId(),
						outManifestDestinationDO.getOffice());
				outManifestDestinationDO
						.setOutManifestDestinationId(outManifestDestinationId);
				outManifestDestinationDO.setManifestId(destManifestDO
						.getManifestId());
			}
		}
		retainCentralVal(sourceManifetsDO, destManifestDO);
		LOGGER.trace("CreateManifestFormater::updateManifest::END------------>:::::::");
	}

	private static void retainCentralVal(BcunManifestDO sourceManifetsDO,
			BcunManifestDO destManifestDO) {
		sourceManifetsDO.setDtFromOpsman(destManifestDO.getDtFromOpsman());		
	}

	/**
	 * Gets the out manifest destination id.
	 * 
	 * @param bcunService
	 *            the bcun service
	 * @param manifestId
	 *            the manifest id
	 * @param officeDO
	 *            the office do
	 * @return the out manifest destination id
	 */
	private static Integer getOutManifestDestinationId(
			BcunDatasyncService bcunService, Integer manifestId,
			OfficeDO officeDO) {
		LOGGER.trace("CreateManifestFormater::getOutManifestDestinationId::START------------>:::::::");
		Integer outManifestDestinationId = null;
		if (officeDO == null) {
			return outManifestDestinationId;
		}

		String[] params = { "manifestId", "OfficeId" };
		Object[] values = { manifestId, officeDO.getOfficeId() };
		outManifestDestinationId = bcunService.getUniqueId(
				"isOutManifestDestnId", params, values);
		LOGGER.trace("CreateManifestFormater::getOutManifestDestinationId::END------------>:::::::");
		return outManifestDestinationId;
	}

	/**
	 * Compare manifest weight.
	 * 
	 * @param destManifestDO
	 *            the dest manifest do
	 * @param sourceManifetsDO
	 *            the source manifets do
	 */
	private static void compareManifestWeight(BcunManifestDO destManifestDO,
			BcunManifestDO sourceManifetsDO) {
		LOGGER.trace("CreateManifestFormater::compareManifestWeight::START------------>:::::::");
		// source is branch manifest
		// dest is central manifest
		Double sourceMnfstWeight = sourceManifetsDO.getManifestWeight();
		Double destMnfstWeight = destManifestDO.getManifestWeight();
		if (!StringUtil.isEmptyDouble(sourceMnfstWeight)
				&& !StringUtil.isEmptyDouble(destMnfstWeight)) {
			if (sourceMnfstWeight.doubleValue() < destMnfstWeight.doubleValue()) {
				sourceManifetsDO.setManifestWeight(destMnfstWeight);
			}
		} else if (StringUtil.isEmptyDouble(sourceMnfstWeight)) {
			sourceManifetsDO.setManifestWeight(destMnfstWeight);
		}
		LOGGER.trace("CreateManifestFormater::compareManifestWeight::END------------>:::::::");
	}

	/**
	 * Compare manifest destination.
	 * 
	 * @param bcunService
	 *            the bcun service
	 * @param destManifestDO
	 *            the dest manifest do
	 * @param sourceManifetsDO
	 *            the source manifets do
	 */
	@SuppressWarnings("unchecked")
	private static void compareManifestDestination(
			BcunDatasyncService bcunService, BcunManifestDO destManifestDO,
			BcunManifestDO sourceManifetsDO) {
		LOGGER.trace("CreateManifestFormater::compareManifestDestination::START------------>:::::::");
		/*if (sourceManifetsDO.getOperatingLevel() == null
				|| destManifestDO.getOperatingLevel() == null) {
			return;
		}*/
		// Comparing destination
/*		if (sourceManifetsDO.getOperatingLevel() != null
				&& destManifestDO.getOperatingLevel() != null
				&& (sourceManifetsDO.getOperatingLevel().intValue() < destManifestDO
						.getOperatingLevel().intValue())) {
			sourceManifetsDO.setDestinationCity(destManifestDO
					.getDestinationCity());
			sourceManifetsDO.setDestOffice(destManifestDO.getDestOffice());

		} else if (sourceManifetsDO.getOperatingLevel() != null
				&& destManifestDO.getOperatingLevel() != null
				&& (sourceManifetsDO.getOperatingLevel().intValue() == destManifestDO
						.getOperatingLevel().intValue())) {*/

			String[] param = { "processCode" };
			Object[] value = { sourceManifetsDO.getManifestProcessCode() };
			List<ProcessDO> sourceProcessDOs = (List<ProcessDO>) bcunService
					.getDataByNamedQueryAndNamedParam("getProcessByCode",
							param, value);

			String[] param1 = { "processCode" };
			Object[] value1 = { destManifestDO.getManifestProcessCode() };
			List<ProcessDO> destProcessDOs = (List<ProcessDO>) bcunService
					.getDataByNamedQueryAndNamedParam("getProcessByCode",
							param1, value1);

			if (!StringUtil.isEmptyColletion(sourceProcessDOs)
					&& !StringUtil.isEmptyColletion(destProcessDOs)
					&& !StringUtil.isEmptyInteger(sourceProcessDOs.get(0)
							.getProcessOrder())
					&& !StringUtil.isEmptyInteger(destProcessDOs.get(0)
							.getProcessOrder())) {

				if (sourceProcessDOs.get(0).getProcessOrder() < destProcessDOs
						.get(0).getProcessOrder()) {
					sourceManifetsDO.setDestinationCity(destManifestDO
							.getDestinationCity());
					sourceManifetsDO.setDestOffice(destManifestDO
							.getDestOffice());
				} else if (sourceProcessDOs.get(0).getProcessOrder().intValue() == destProcessDOs
						.get(0).getProcessOrder().intValue()) {
					if (sourceManifetsDO.getUpdatedDate() != null
							&& destManifestDO.getUpdatedDate() != null
							&& sourceManifetsDO.getUpdatedDate().before(
									destManifestDO.getUpdatedDate())) {
						sourceManifetsDO.setDestinationCity(destManifestDO
								.getDestinationCity());
						sourceManifetsDO.setDestOffice(destManifestDO
								.getDestOffice());
					}
				}
			}

//		}
		LOGGER.trace("CreateManifestFormater::compareManifestDestination::END------------>:::::::");
	}
}
