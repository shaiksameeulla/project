package com.ff.web.manifest.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.geography.CityDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.manifest.ManifestMappedEmbeddedDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.manifest.BPLBranchOutManifestDetailsTO;
import com.ff.manifest.BplBranchOutManifestTO;
import com.ff.universe.manifest.dao.ManifestUniversalDAO;
import com.ff.web.manifest.Utils.ManifestUtil;
import com.ff.web.manifest.service.OutManifestCommonService;

/**
 * @author nihsingh
 *
 */
public class BPLBranchOutManifestConverter extends OutManifestBaseConverter{

	private final static Logger LOGGER = LoggerFactory
			.getLogger(BranchOutManifestParcelConverter.class);
	

	private static ManifestUniversalDAO manifestUniversalDao;
	private static OutManifestCommonService outManifestCommonService;

	
	
	
	
	/**
	 * @return the manifestUniversalDao
	 */
	public static ManifestUniversalDAO getManifestUniversalDao() {
		return manifestUniversalDao;
	}


	/**
	 * @param manifestUniversalDao the manifestUniversalDao to set
	 */
	public static void setManifestUniversalDao(
			ManifestUniversalDAO manifestUniversalDao) {
		BPLBranchOutManifestConverter.manifestUniversalDao = manifestUniversalDao;
	}


	/**
	 * @return the outManifestCommonService
	 */
	public static OutManifestCommonService getOutManifestCommonService() {
		return outManifestCommonService;
	}


	/**
	 * @param outManifestCommonService the outManifestCommonService to set
	 */
	public static void setOutManifestCommonService(
			OutManifestCommonService outManifestCommonService) {
		BPLBranchOutManifestConverter.outManifestCommonService = outManifestCommonService;
	}


	/**
	 * @Desc Prepares List<BPLBranchOutManifestDetailsTO>
	 * @param bplBranchOutManifestTO
	 * @return List<BPLBranchOutManifestDetailsTO>
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public static List<BPLBranchOutManifestDetailsTO> bplBranchOutManifestDtlsListConverter(
			BplBranchOutManifestTO bplBranchOutManifestTO)
			throws CGBusinessException, CGSystemException {
		List<BPLBranchOutManifestDetailsTO> bplBranchOutManifestDetailsTOs = null;
		BPLBranchOutManifestDetailsTO bplBranchOutManifestDetailsTO = null;
		
		if (bplBranchOutManifestTO.getManifestNos() != null	&& bplBranchOutManifestTO.getManifestNos().length > 0) {
				bplBranchOutManifestDetailsTOs = new ArrayList<BPLBranchOutManifestDetailsTO>();
				for (int rowCount = 0; rowCount < bplBranchOutManifestTO.getManifestNos().length; rowCount++) {
					if (StringUtils.isNotEmpty(bplBranchOutManifestTO.getManifestNos()[rowCount])) {
						// Setting the common grid level attributes
						bplBranchOutManifestDetailsTO = new BPLBranchOutManifestDetailsTO();
						bplBranchOutManifestDetailsTO.setManifestNo(bplBranchOutManifestTO.getManifestNos()[rowCount]);
						bplBranchOutManifestDetailsTO.setWeight(bplBranchOutManifestTO.getWeights()[rowCount]);
						bplBranchOutManifestDetailsTOs.add(bplBranchOutManifestDetailsTO);
					}
				}
			}
		return bplBranchOutManifestDetailsTOs;
	}
	
	
	/**
	 * @Desc prepares List<BPLBranchOutManifestDetailsTO>
	 * @param bplBranchOutManifestTO
	 * @return List<BPLBranchOutManifestDetailsTO>
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public static List<BPLBranchOutManifestDetailsTO> prepareBPLBranchOutManifestDtlList(
			BplBranchOutManifestTO bplBranchOutManifestTO) throws CGBusinessException,
			CGSystemException {
		BPLBranchOutManifestDetailsTO bplBranchOutManifestDetailsTO = null;
		List<BPLBranchOutManifestDetailsTO> bplBranchOutManifestDetailsTOs = null;
		int noOfElements = 0;

		if (!StringUtil.isNull(bplBranchOutManifestTO)) {
			bplBranchOutManifestDetailsTOs = new ArrayList<>(
					bplBranchOutManifestTO.getManifestNos().length);
			for (int rowCount = 0; rowCount < bplBranchOutManifestTO.getManifestNos().length; rowCount++) {
				if (StringUtils
						.isNotEmpty(bplBranchOutManifestTO.getManifestNos()[rowCount])) {
					noOfElements++;
				}
			}
			//bplBranchOutManifestTO.setNoOfConsignments(noOfElements);
			bplBranchOutManifestTO.setRowCount(noOfElements);
			if (bplBranchOutManifestTO.getManifestNos() != null
					&& bplBranchOutManifestTO.getManifestNos().length > 0) {
				for (int rowCount = 0; rowCount < bplBranchOutManifestTO.getManifestNos().length; rowCount++) {
					if (StringUtils
							.isNotEmpty(bplBranchOutManifestTO.getManifestNos()[rowCount])) {
						// Setting the common grid level attributes
						bplBranchOutManifestDetailsTO = (BPLBranchOutManifestDetailsTO) setUpManifestDtlsTOs(
								bplBranchOutManifestTO, rowCount);
						// Can add specific to MBPL Out manifest
						if (bplBranchOutManifestTO.getNoOfConsignments()!= null
								
								&& bplBranchOutManifestTO.getNoOfConsignments().length > 0) {
							bplBranchOutManifestDetailsTO
									.setNoOfConsignment(bplBranchOutManifestTO
											.getNoOfConsignments()[rowCount]);

						}
						if (!StringUtil.isEmptyInteger(bplBranchOutManifestTO
								.getManifestMappedEmbeddeId()[rowCount])) {
							bplBranchOutManifestDetailsTO
									.setMapEmbeddedManifestId(bplBranchOutManifestTO
											.getManifestMappedEmbeddeId()[rowCount]);

						}
					
						/***********setting old wt in detail to*************/
						if (bplBranchOutManifestTO.getOldWeights()!= null
								&& bplBranchOutManifestTO.getOldWeights().length > 0) {
							bplBranchOutManifestDetailsTO
									.setOldWeight(bplBranchOutManifestTO
											.getOldWeights()[rowCount]);

						}
						/************************/
						bplBranchOutManifestDetailsTO.setPosition(
								bplBranchOutManifestTO.getPosition()[rowCount]);
						bplBranchOutManifestDetailsTOs.add(bplBranchOutManifestDetailsTO);
					}
				}
			}
		}
		return bplBranchOutManifestDetailsTOs;
	}


	/**
	 * @Desc prepares List<ManifestDO>
	 * @param bplBranchOutManifestTO
	 * @return List<ManifestDO>
	 * @throws CGSystemException 
	 */
	public static ManifestDO prepareManifestDOList(
			BplBranchOutManifestTO bplBranchOutManifestTO)  {
		
		ManifestDO manifestDO = null;
		// Setting Common attributes
		manifestDO = OutManifestBaseConverter
				.outManifestTransferObjConverter(bplBranchOutManifestTO);
		//Setting specific attributes
		manifestDO.setLoadLotId(bplBranchOutManifestTO.getLoadNo());
		manifestDO.setNoOfElements(bplBranchOutManifestTO.getRowCount());
		

		return manifestDO;
	}
	
	
	/**
	 * @Desc prepares List<ManifestProcessDO>
	 * @param bplBranchOutManifestTO
	 * @return List<ManifestProcessDO>
	 */
	/*public static ManifestProcessDO prepareManifestProcessDOList(
			BplBranchOutManifestTO bplBranchOutManifestTO) {
		
		ManifestProcessDO manifestProcessDO = null;
		// Setting Common attributes
		manifestProcessDO = OutManifestBaseConverter
				.outManifestBaseTransferObjConverter(bplBranchOutManifestTO);
		//Setting specific attributes
		manifestProcessDO.setLoadLotId(bplBranchOutManifestTO.getLoadNo());
		manifestProcessDO.setNoOfElements(bplBranchOutManifestTO.getRowCount());
		manifestProcessDO.setManifestStatus(bplBranchOutManifestTO.getManifestStatus());
		

		return manifestProcessDO;
	}*/

	
	/**
	 * @Desc prepares BplBranchOutManifestTO
	 * @param manifestDO
	 * @return BplBranchOutManifestTO
	 * @throws CGBusinessException
	 */
	public static BplBranchOutManifestTO bplBranchoutManifestDomainConverter(
			ManifestDO manifestDO) throws CGBusinessException {
		// Set the common attributes for the header
		BplBranchOutManifestTO bplBranchOutManifestTO = (BplBranchOutManifestTO) outManifestDomainConverter(
				manifestDO, ManifestUtil.getBPLBranchOutManifestFactory());
		
		if (!StringUtil.isEmptyInteger(manifestDO.getLoadLotId()))
			bplBranchOutManifestTO.setLoadNo(manifestDO.getLoadLotId());

		return bplBranchOutManifestTO;
	}
	
	/**
	 * @Desc prepares List<BPLBranchOutManifestDetailsTO>
	 * @param manifestDO
	 * @return List<BPLBranchOutManifestDetailsTO>
	 * @throws CGSystemException 
	 * @throws CGBusinessException 
	 */
	public static List<BPLBranchOutManifestDetailsTO> bplBranchOutManifestDomainConvertorForEmbeddedIn(
			ManifestDO manifestDOHeader) throws CGBusinessException, CGSystemException {
		List<BPLBranchOutManifestDetailsTO> detailsTOs = null;
		if (manifestDOHeader != null) {
			detailsTOs = new ArrayList<BPLBranchOutManifestDetailsTO>(
					manifestDOHeader.getEmbeddedManifestDOs().size());
			for (ManifestDO manifestDOChild : manifestDOHeader.getEmbeddedManifestDOs()) {

				BPLBranchOutManifestDetailsTO bplBarnchOutManifestDetailsTO = new BPLBranchOutManifestDetailsTO();
				
				//setting the grid details in the detailTO
				//need id for print
				bplBarnchOutManifestDetailsTO.setManifestId(manifestDOChild
						.getManifestId());
				bplBarnchOutManifestDetailsTO.setManifestNo(manifestDOChild
						.getManifestNo());
				bplBarnchOutManifestDetailsTO.setWeight(manifestDOChild
						.getManifestWeight());
				bplBarnchOutManifestDetailsTO.setNoOfConsignment(manifestDOChild.getNoOfElements());
			
				bplBarnchOutManifestDetailsTO.setPosition(manifestDOChild
						.getPosition());

				if(manifestDOChild.getDestinationCity()!=null){
				CityDO cityDO = manifestDOChild.getDestinationCity();
				bplBarnchOutManifestDetailsTO.setDestCity(cityDO.getCityName());
				bplBarnchOutManifestDetailsTO.setDestCityId(cityDO.getCityId());
				}

				detailsTOs.add(bplBarnchOutManifestDetailsTO);
			}
			Collections.sort(detailsTOs);
		}
		return detailsTOs;

	}
	
/*use this to fill in grid manifest nos. in ManifestMappedEmbeddedDO */
	
	public static List<ManifestMappedEmbeddedDO> setEmbeddedManifestDetails(
			BplBranchOutManifestTO bplBranchOutManifestTO) {
		List<ManifestMappedEmbeddedDO> embeddedManifestDOs = new ArrayList<>();
		// Set ConsignmentManifestDO
		if (bplBranchOutManifestTO.getManifestIds() != null
				&& bplBranchOutManifestTO.getManifestIds().length > 0) {
			embeddedManifestDOs = ManifestUtil.setEmbeddedManifestDtls(bplBranchOutManifestTO);
		}

		return embeddedManifestDOs;

	}

	public static ManifestDO convertInManifestGridPacketToDO(ManifestDO manifestDO,
			ProcessDO processDO,BplBranchOutManifestTO bplBranchOutManifestTO) {
		LOGGER.trace("BplBranchOutManifestConverter:: convertInManifestGridPacketToDO()::START------------>:::::::");
		ManifestDO manifest = new ManifestDO();
		manifest.setManifestId(null);
		manifest.setManifestNo(manifestDO.getManifestNo());
		manifest.setBagLockNo(manifestDO.getBagLockNo());
		manifest.setManifestDirection(CommonConstants.MANIFEST_TYPE_OUT);
		if(manifestDO.getManifestProcessCode().equalsIgnoreCase(CommonConstants.PROCESS_IN_MANIFEST_PKT_DOX)){
			manifest.setManifestProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_PKT_DOX);
		}
	
		CityDO cityDO = new CityDO();
		if (!StringUtil.isNull(manifestDO.getDestinationCity().getCityId())) {
			cityDO.setCityId(manifestDO.getDestinationCity().getCityId());
			manifest.setDestinationCity(cityDO);
		}

		OfficeDO officeDO = new OfficeDO();
		if (!StringUtil.isNull(manifestDO.getOriginOffice().getOfficeId())) {
			officeDO.setOfficeId(bplBranchOutManifestTO.getLoginOfficeId());
			manifest.setOriginOffice(officeDO);
		}
		OfficeDO destOffice = new OfficeDO();
		if (!StringUtil.isNull(bplBranchOutManifestTO.getDestinationOfficeId())) {
			destOffice.setOfficeId(bplBranchOutManifestTO.getDestinationOfficeId());
			manifest.setDestOffice(destOffice);
		}
		manifest.setCreatedDate(manifestDO.getCreatedDate());
		manifest.setMultipleDestination(manifestDO.getMultipleDestination());
		manifest.setNoOfElements(manifestDO.getNoOfElements());
		manifest.setContainsOnlyCoMail(manifestDO.getContainsOnlyCoMail());
		manifest.setManifestStatus(manifestDO.getManifestStatus());
		manifest.setManifestType(CommonConstants.MANIFEST_TYPE_OUT);
		manifest.setBagRFID(manifestDO.getBagRFID());
		ConsignmentTypeDO manifestLoadContent = new ConsignmentTypeDO();
		if (!StringUtil.isNull(manifestDO.getManifestLoadContent())) {
			manifestLoadContent.setConsignmentId(manifestDO
					.getManifestLoadContent().getConsignmentId());
			manifest.setManifestLoadContent(manifestLoadContent);
		}
		manifest.setUpdatedDate(manifestDO.getUpdatedDate());
		manifest.setManifestDate(manifestDO.getManifestDate());
		manifest.setOperatingOffice(manifestDO.getOperatingOffice());
		//manifest.setOperatingLevel(manifestDO.getOperatingLevel());
		manifest.setBplManifestType(manifestDO.getBplManifestType());
		manifest.setManifestWeight(manifestDO.getManifestWeight());
		manifest.setReceivedStatus(manifestDO.getReceivedStatus());
		manifest.setUpdatingProcess(processDO);
		LOGGER.trace("BPLBranchOutManifestConverter:: convertInManifestGridPacketToDO()::END------------>:::::::");
		return manifest;
	}


	
}
