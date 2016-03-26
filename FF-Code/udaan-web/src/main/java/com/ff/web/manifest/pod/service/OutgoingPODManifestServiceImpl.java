/**
 * 
 */
package com.ff.web.manifest.pod.service;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.GridItemOrderDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.pod.PODManifestTO;
import com.ff.tracking.ProcessTO;
import com.ff.universe.consignment.dao.ConsignmentCommonDAO;
import com.ff.universe.manifest.dao.ManifestUniversalDAO;
import com.ff.universe.tracking.service.TrackingUniversalService;
import com.ff.web.manifest.Utils.ManifestUtil;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.pod.converter.PODManifestConverter;
import com.ff.web.manifest.pod.dao.PODManifestCommonDAO;
import com.ff.web.manifest.service.ManifestCommonService;

/**
 * @author nkattung
 * 
 */
public class OutgoingPODManifestServiceImpl implements
		OutgoingPODManifestService {
	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(OutgoingPODManifestServiceImpl.class);
	private PODManifestCommonDAO podManifestCommonDAO;
	private ConsignmentCommonDAO consignmentCommonDAO;
	private TrackingUniversalService trackingUniversalService;
	private ManifestCommonService manifestCommonService;
	/** The manifest universal dao. */
	private ManifestUniversalDAO manifestUniversalDAO;
	

	/**
	 * @param manifestUniversalDAO the manifestUniversalDAO to set
	 */
	public void setManifestUniversalDAO(ManifestUniversalDAO manifestUniversalDAO) {
		this.manifestUniversalDAO = manifestUniversalDAO;
	}

	public void setManifestCommonService(
			ManifestCommonService manifestCommonService) {
		this.manifestCommonService = manifestCommonService;
	}

	/**
	 * @param podManifestCommonDAO
	 *            the podManifestCommonDAO to set
	 */
	public void setPodManifestCommonDAO(
			PODManifestCommonDAO podManifestCommonDAO) {
		this.podManifestCommonDAO = podManifestCommonDAO;
	}

	/**
	 * @param consignmentCommonDAO
	 *            the consignmentCommonDAO to set
	 */
	public void setConsignmentCommonDAO(
			ConsignmentCommonDAO consignmentCommonDAO) {
		this.consignmentCommonDAO = consignmentCommonDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.pod.service.OutgoingPODManifestService#
	 * saveOrUpdatePODManifest(com.ff.manifest.pod.PODManifestTO)
	 */
	@Override
	public String saveOrUpdatePODManifest(PODManifestTO podManifestTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("OutgoingPODManifestServiceImpl :: saveOrUpdatePODManifest() :: START");
		String isMnfstSaved = "N";
		boolean isSaved = Boolean.FALSE;
		/*ManifestProcessDO manifestProcess = null;
		List<ManifestProcessDO> manifestProcessDOs = null;*/
		try {
			ManifestDO podManifest = PODManifestConverter
					.podManifestHerderDomainConverter(podManifestTO);
			// Prepare Consingments List
			Set<ConsignmentDO> podManifestDtls = PODManifestConverter
					.podManifestConsgDomainConverter(podManifestTO,
							consignmentCommonDAO);
			//setting the operating office in Manifest
			
			podManifest.setOperatingOffice(podManifestTO.getOperatingOffice());
			
			// Setting process id
			ProcessTO processTO = new ProcessTO();
			processTO.setProcessCode(CommonConstants.PROCESS_POD);
			processTO = trackingUniversalService.getProcess(processTO);
			ProcessDO processDO = new ProcessDO();
			processDO.setProcessId(processTO.getProcessId());
			podManifest.setUpdatingProcess(processDO);
			podManifest.setConsignments(podManifestDtls);

			// Set Grid Position
			GridItemOrderDO gridItemOrderDO = null;
			if (!StringUtil.isEmpty(podManifestTO.getConsgNumbers())) {
				gridItemOrderDO = new GridItemOrderDO();
				gridItemOrderDO
						.setConsignments(podManifestTO.getConsgNumbers());
				gridItemOrderDO = manifestCommonService.arrangeOrder(
						gridItemOrderDO, ManifestConstants.ACTION_SAVE);
				podManifest.setGridItemPosition(gridItemOrderDO
						.getGridPosition().toString());
			}
			
			//to set DT_TO_CENTRAL as Y while saving
			ManifestUtil.validateAndSetTwoWayWriteFlag(podManifest);
			
			
			if (!StringUtil.isNull(podManifest)) {
				podManifest = podManifestCommonDAO
						.saveOrUpdateManifest(podManifest);

				//for two way write
				podManifestTO.setManifestId(podManifest.getManifestId());
				
				isSaved=Boolean.TRUE;
				
			}		
			
			
			/* Search manifest Process */
			ManifestInputs manifestTO = prepareForManifestProcess(podManifestTO);
			/*manifestProcessDOs = manifestUniversalDAO
					.getManifestProcessDtls(manifestTO);
			
			 prepare Manifest Process 
			if (!CGCollectionUtils.isEmpty(manifestProcessDOs)) {
				manifestProcess = manifestProcessDOs.get(0);
			}*/
			/*if (StringUtil.isNull(manifestProcess)
					|| StringUtils.isEmpty(manifestProcess.getManifestProcessNo())) {
				OfficeTO officeTO = new OfficeTO();
				officeTO.setOfficeId(podManifestTO.getDispachOfficeTO().getOfficeId());
				if (!StringUtil.isStringEmpty(podManifestTO.getDispachOfficeTO().getOfficeCode())) {
					officeTO.setOfficeCode(podManifestTO.getDispachOfficeTO().getOfficeCode());
				}
				String processNumber = trackingUniversalService
						.createProcessNumber(processTO, officeTO);
				podManifestTO.setManifestProcessNumber(processNumber);
				manifestProcessDOs = PODManifestConverter
						.podManifestProcessDomainConverter(podManifestTO, manifestProcess);
			}*/
			
			//to set DT_TO_CENTRAL as Y while saving
			/*ManifestUtil.validateAndSetTwoWayWriteFlag(manifestProcessDOs.get(0));
			
			if (!StringUtil.isEmptyList(manifestProcessDOs)) {
				isSaved = podManifestCommonDAO
						.saveOrUpdateManifestProcess(manifestProcessDOs);
				podManifestTO.setManifestProcessId(manifestProcessDOs.get(0).getManifestProcessId());
			}		*/
			
			// Commented By Pravin

			/*
			 * if (!StringUtil.isEmptyList(podManifestDtls)) { isSaved =
			 * consignmentDAO .saveOrUpdateConsignment(podManifestDtls); }
			 */

			/*
			 * OfficeTO officeTO = podManifestTO.getDispachOfficeTO(); ProcessTO
			 * processTO = podManifestTO.getProcess(); String processNumber =
			 * trackingUniversalService .createProcessNumber(processTO,
			 * officeTO);
			 * 
			 * ProcessMapTO processMapTO = PODManifestConverter.setProcessmapTO(
			 * podManifestTO, processNumber, trackingUniversalService);
			 * processMapTO.setProcessOrder(processTO.getProcessOrder());
			 * ManifestBaseTO manifestTO = new ManifestBaseTO();
			 * manifestTO.setOriginOfficeTO(podManifestTO.getDispachOfficeTO());
			 * OfficeTO destofficeTO = new OfficeTO();
			 * destofficeTO.setOfficeId(podManifestTO.getDestOffId());
			 * manifestTO.setDestinationOfficeTO(destofficeTO);
			 * trackingUniversalService.saveProcessMap(processMapTO, officeTO,
			 * null, manifestTO);
			 */

			if (isSaved)
				isMnfstSaved = CommonConstants.SUCCESS;
		} catch (CGBusinessException e) {
			LOGGER.error(
					"OutgoingPODManifestServiceImpl :: saveOrUpdateIncomingPODMnfst() :: ",
					e);
			throw new CGBusinessException(e);
		} catch (CGSystemException e) {
			LOGGER.error(
					"OutgoingPODManifestServiceImpl :: saveOrUpdateIncomingPODMnfst() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("OutgoingPODManifestServiceImpl :: saveOrUpdatePODManifest() :: END");
		return isMnfstSaved;
	}

	private ManifestInputs prepareForManifestProcess(PODManifestTO podManifestTO) {
			ManifestInputs manifestInputs = new ManifestInputs();
			manifestInputs.setManifestNumber(podManifestTO.getManifestNo());
			manifestInputs.setLoginOfficeId(podManifestTO.getDispachOfficeTO().getOfficeId());
			manifestInputs.setManifestType("P");
			manifestInputs.setManifestDirection(podManifestTO.getManifestType());
			return manifestInputs;
	}

	/**
	 * @param trackingUniversalService
	 *            the trackingUniversalService to set
	 */
	public void setTrackingUniversalService(
			TrackingUniversalService trackingUniversalService) {
		this.trackingUniversalService = trackingUniversalService;
	}

}
