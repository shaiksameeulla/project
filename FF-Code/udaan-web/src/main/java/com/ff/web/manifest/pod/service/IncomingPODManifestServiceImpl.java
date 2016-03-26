/**
 * 
 */
package com.ff.web.manifest.pod.service;

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
import com.ff.domain.manifest.ConsignmentManifestDO;
import com.ff.domain.manifest.GridItemOrderDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.pod.PODConsignmentDtlsTO;
import com.ff.manifest.pod.PODManifestDtlsTO;
import com.ff.manifest.pod.PODManifestTO;
import com.ff.tracking.ProcessTO;
import com.ff.universe.consignment.dao.ConsignmentCommonDAO;
import com.ff.universe.manifest.dao.ManifestUniversalDAO;
import com.ff.universe.tracking.service.TrackingUniversalService;
import com.ff.web.manifest.Utils.ManifestUtil;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.pod.constants.PODManifestConstants;
import com.ff.web.manifest.pod.converter.PODManifestConverter;
import com.ff.web.manifest.pod.dao.IncomingPODManifestDAO;
import com.ff.web.manifest.pod.dao.PODManifestCommonDAO;
import com.ff.web.manifest.service.ManifestCommonService;

/**
 * @author nkattung
 * 
 */
public class IncomingPODManifestServiceImpl implements
		IncomingPODManifestService {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(IncomingPODManifestServiceImpl.class);
	private IncomingPODManifestDAO incomingPODManifestDAO;
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

	/**
	 * @param manifestCommonService
	 *            the manifestCommonService to set
	 */
	public void setManifestCommonService(
			ManifestCommonService manifestCommonService) {
		this.manifestCommonService = manifestCommonService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.pod.service.IncomingPODManifestService#
	 * getOutgoingPODConsgDtls(java.lang.String, java.lang.Integer,
	 * java.lang.String)
	 */
	@Override
	public PODManifestDtlsTO getOutgoingPODConsgDtls(String consignment,
			Integer officeId, String ManifestType, String manifestNo)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("IncomingPODManifestServiceImpl :: getOutgoingPODConsgDtls() :: :: Start");
		PODManifestDtlsTO podManiefstDtls = null;
		ConsignmentManifestDO consgManifestDo = new ConsignmentManifestDO();
		boolean isCNManifested = isConsgnNoManifested(consignment,
				PODManifestConstants.POD_MANIFEST_IN,
				PODManifestConstants.POD_MANIFEST, CommonConstants.PROCESS_POD);
		if (isCNManifested)
			throw new CGBusinessException(PODManifestConstants.CN_EXISTS);
		consgManifestDo = incomingPODManifestDAO.getOutgoingPODConsgDtls(
				consignment, PODManifestConstants.POD_MANIFEST_OUT,
				PODManifestConstants.POD_MANIFEST, CommonConstants.PROCESS_POD,
				officeId);
		if (!StringUtil.isNull(consgManifestDo)) {
			ConsignmentDO consignmentDo = consgManifestDo.getConsignment();
			podManiefstDtls = new PODManifestDtlsTO();
			podManiefstDtls.setConsgId(consignmentDo.getConsgId());
			podManiefstDtls.setConsgNumber(consignmentDo.getConsgNo());
			podManiefstDtls.setReceivedDate(DateUtil
					.getDateInDDMMYYYYHHMMSlashFormat(consignmentDo
							.getReceivedDateTime()));
			podManiefstDtls.setDlvDate(DateUtil
					.getDateInDDMMYYYYHHMMSlashFormat(consignmentDo
							.getDeliveredDate()));
			podManiefstDtls.setRecvNameOrCompSeal(consignmentDo
					.getRecvNameOrCompName());
			String recvStatus = isConsgnmentBelongsToManifest(consignment,
					manifestNo);
			podManiefstDtls.setReceivedStatus(recvStatus);
		} else
			throw new CGBusinessException(
					PODManifestConstants.INVALID_CN_IN_POD);
		LOGGER.trace("IncomingPODManifestServiceImpl :: getOutgoingPODConsgDtls() :: :: END");
		return podManiefstDtls;
	}

	/**
	 * @param consgNumber
	 * @param manifestDirection
	 * @param manifestType
	 * @param manifestPorcessCode
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public boolean isConsgnNoManifested(String consgNumber,
			String manifestDirection, String manifestType,
			String manifestPorcessCode) throws CGSystemException,
			CGBusinessException {
		LOGGER.trace("IncomingPODManifestServiceImpl :: isConsgnNoManifested() ::");
		return podManifestCommonDAO.isConsgnNoManifested(consgNumber,
				manifestDirection, manifestType, manifestPorcessCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.pod.service.IncomingPODManifestService#
	 * saveOrUpdatePODManifest(com.ff.manifest.pod.PODManifestTO)
	 */
	@Override
	public String saveOrUpdateIncomingPODMnfst(PODManifestTO podManifestTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("IncomingPODManifestServiceImpl :: saveOrUpdateIncomingPODMnfst() :: START");
		String isMnfstSaved = "N";
		boolean isSaved = Boolean.FALSE;
		/*ManifestProcessDO manifestProcess = null;
		List<ManifestProcessDO> manifestProcessDOs = null;*/
		try {
			ManifestDO podManifest = PODManifestConverter
					.podManifestHerderDomainConverter(podManifestTO);

			/* Search manifest Process */
			ManifestInputs manifestTO = prepareForManifestProcess(podManifestTO);
			/*manifestProcessDOs = manifestUniversalDAO
					.getManifestProcessDtls(manifestTO);*/

			
			//setting the operating office in ManifestDO
			podManifest.setOperatingOffice(podManifestTO.getOperatingOffice());
			
			// Setting process id
			ProcessTO processTO = new ProcessTO();
			processTO.setProcessCode(CommonConstants.PROCESS_POD);
			processTO = trackingUniversalService.getProcess(processTO);
			ProcessDO processDO = new ProcessDO();
			processDO.setProcessId(processTO.getProcessId());
			podManifest.setUpdatingProcess(processDO);
			
			/* prepare Manifest Process */
			/*if (!CGCollectionUtils.isEmpty(manifestProcessDOs)) {
				manifestProcess = manifestProcessDOs.get(0);
			}
			if (StringUtil.isNull(manifestProcess)
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
			/*ManifestUtil.validateAndSetTwoWayWriteFlag(manifestProcessDOs.get(0));*/
			
			/*if (!StringUtil.isEmptyList(manifestProcessDOs)) {
				isSaved = podManifestCommonDAO
						.saveOrUpdateManifestProcess(manifestProcessDOs);
				podManifestTO.setManifestProcessId(manifestProcessDOs.get(0).getManifestProcessId());
			}*/
			
			
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
			// prepare Consingments set
			Set<ConsignmentDO> podManifestDtls = PODManifestConverter
					.podManifestConsgDomainConverter(podManifestTO,
							consignmentCommonDAO);
			
			//to set DT_TO_CENTRAL as Y while saving
			ManifestUtil.validateAndSetTwoWayWriteFlag(podManifest);
			
			if (!StringUtil.isNull(podManifest)) {
				podManifest.setConsignments(podManifestDtls);
				podManifest = podManifestCommonDAO
						.saveOrUpdateManifest(podManifest);
				//for two way write
				podManifestTO.setManifestId(podManifest.getManifestId());
				isSaved = Boolean.TRUE;
			}
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
			 * 
			 * trackingUniversalService.saveProcessMap(processMapTO, officeTO,
			 * null, manifestTO);
			 */

			if (isSaved) {
				isMnfstSaved = CommonConstants.SUCCESS;
			}
			LOGGER.trace("IncomingPODManifestServiceImpl :: saveOrUpdateIncomingPODMnfst() :: END");
		} catch (CGBusinessException e) {
			LOGGER.error(
					"IncomingPODManifestServiceImpl :: saveOrUpdateIncomingPODMnfst() :: ",
					e);
			throw new CGBusinessException(e);
		} catch (CGSystemException e) {
			LOGGER.error(
					"IncomingPODManifestServiceImpl :: saveOrUpdateIncomingPODMnfst() :: ",
					e);
			throw new CGSystemException(e);
		}
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.pod.service.IncomingPODManifestService#
	 * isConsgnmentBelongsToManifest(java.lang.String, java.lang.String)
	 */
	public String isConsgnmentBelongsToManifest(String consgNumber,
			String manifestNo) throws CGSystemException {
		LOGGER.trace("IncomingPODManifestServiceImpl :: isConsgnmentBelongsToManifest() :: START");
		String cnRecvStatus = "";
		boolean isCnBelongsTOManifest = Boolean.FALSE;
		isCnBelongsTOManifest = incomingPODManifestDAO
				.isConsgnmentBelongsToManifest(consgNumber, manifestNo);
		if (isCnBelongsTOManifest) {
			cnRecvStatus = PODManifestConstants.POD_RECEIVED;
		} else {
			cnRecvStatus = PODManifestConstants.POD_NOT_RECEIVED;
		}
		LOGGER.trace("IncomingPODManifestServiceImpl :: isConsgnmentBelongsToManifest() :: END");
		return cnRecvStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.pod.service.IncomingPODManifestService#podConsignmentDtls
	 * (java.lang.String, java.lang.String)
	 */
	public PODConsignmentDtlsTO podConsignmentDtls(String consgNumbers,
			String manifestNo) throws CGSystemException, CGBusinessException {
		LOGGER.trace("IncomingPODManifestServiceImpl :: podConsignmentDtls() :: START");
		PODConsignmentDtlsTO podConsigDtlsTO = new PODConsignmentDtlsTO();
		List<String> scannedCns = StringUtil.parseStringList(consgNumbers, ",");
		List<String> outPODCns = incomingPODManifestDAO
				.getConsignmentsOfManifest(manifestNo);
		StringBuilder receivedCNs = new StringBuilder();
		StringBuilder missedCNs = new StringBuilder();
		boolean isReceived = Boolean.FALSE;
		for (String podCN : outPODCns) {
			for (String scannedCn : scannedCns) {
				if (StringUtils.equalsIgnoreCase(podCN, scannedCn)) {
					isReceived = Boolean.TRUE;
				}
			}
			if (isReceived) {
				podConsigDtlsTO.setConsgNumber(podCN);
				podConsigDtlsTO
						.setReceivedStatus(PODManifestConstants.POD_RECEIVED);
				receivedCNs.append(podCN);
				receivedCNs.append(CommonConstants.COMMA);
				isReceived = Boolean.FALSE;// Resetting the value
			} else {
				podConsigDtlsTO.setConsgNumber(podCN);
				podConsigDtlsTO
						.setReceivedStatus(PODManifestConstants.POD_NOT_RECEIVED);
				missedCNs.append(podCN);
				missedCNs.append(CommonConstants.COMMA);
			}
		}
		int size = missedCNs.length();
		if (size > 0) {
			missedCNs.deleteCharAt(size - 1);
		}
		podConsigDtlsTO.setMissedConsignments(missedCNs.toString());
		String errorMsg = "Missed Outgoing POD Consignments: " + missedCNs;
		podConsigDtlsTO.setReceivedConsingments(receivedCNs.toString());
		podConsigDtlsTO.setErrorMsg(errorMsg);
		LOGGER.trace("IncomingPODManifestServiceImpl :: podConsignmentDtls() :: END");
		return podConsigDtlsTO;
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

	/**
	 * @param incomingPODManifestDAO
	 *            the incomingPODManifestDAO to set
	 */
	public void setIncomingPODManifestDAO(
			IncomingPODManifestDAO incomingPODManifestDAO) {
		this.incomingPODManifestDAO = incomingPODManifestDAO;
	}

	public void setTrackingUniversalService(
			TrackingUniversalService trackingUniversalService) {
		this.trackingUniversalService = trackingUniversalService;
	}

}
