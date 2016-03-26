package com.ff.web.manifest.inmanifest.service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.manifest.ManifestDO;
import com.ff.manifest.ManifestBaseTO;
import com.ff.manifest.inmanifest.InManifestTO;
import com.ff.manifest.inmanifest.InMasterBagManifestDetailsTO;
import com.ff.manifest.inmanifest.InMasterBagManifestTO;
import com.ff.tracking.ProcessTO;
import com.ff.universe.manifest.dao.ManifestUniversalDAO;
import com.ff.universe.manifest.service.OutManifestUniversalService;
import com.ff.universe.tracking.service.TrackingUniversalService;
import com.ff.web.manifest.inmanifest.constants.InManifestConstants;
import com.ff.web.manifest.inmanifest.converter.InMasterBagManifestConverter;
import com.ff.web.manifest.inmanifest.dao.InManifestCommonDAO;
import com.ff.web.manifest.inmanifest.dao.InMasterBagManifestDAO;
import com.ff.web.manifest.inmanifest.utils.InManifestUtils;
import com.ff.web.manifest.service.OutManifestCommonService;

/**
 * The Class InMasterBagManifestServiceImpl.
 * 
 * @author narmdr
 */
public class InMasterBagManifestServiceImpl implements
		InMasterBagManifestService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(InMasterBagManifestServiceImpl.class);

	/** The out manifest universal service. */
	private OutManifestUniversalService outManifestUniversalService;

	/** The manifest universal dao. */
	private ManifestUniversalDAO manifestUniversalDAO;

	/** The in manifest common dao. */
	private InManifestCommonDAO inManifestCommonDAO;

	/** The in manifest common service. */
	private InManifestCommonService inManifestCommonService;

	/** The in master bag manifest dao. */
	private InMasterBagManifestDAO inMasterBagManifestDAO;

	private TrackingUniversalService trackingUniversalService;

	private OutManifestCommonService outManifestCommonService;

	/**
	 * @param trackingUniversalService
	 *            the trackingUniversalService to set
	 */
	public void setTrackingUniversalService(
			TrackingUniversalService trackingUniversalService) {
		this.trackingUniversalService = trackingUniversalService;
	}

	/**
	 * Sets the in master bag manifest dao.
	 * 
	 * @param inMasterBagManifestDAO
	 *            the inMasterBagManifestDAO to set
	 */
	public void setInMasterBagManifestDAO(
			InMasterBagManifestDAO inMasterBagManifestDAO) {
		this.inMasterBagManifestDAO = inMasterBagManifestDAO;
	}

	/**
	 * Sets the in manifest common service.
	 * 
	 * @param inManifestCommonService
	 *            the inManifestCommonService to set
	 */
	public void setInManifestCommonService(
			InManifestCommonService inManifestCommonService) {
		this.inManifestCommonService = inManifestCommonService;
	}

	/**
	 * Sets the in manifest common dao.
	 * 
	 * @param inManifestCommonDAO
	 *            the inManifestCommonDAO to set
	 */
	public void setInManifestCommonDAO(InManifestCommonDAO inManifestCommonDAO) {
		this.inManifestCommonDAO = inManifestCommonDAO;
	}

	/**
	 * Sets the manifest universal dao.
	 * 
	 * @param manifestUniversalDAO
	 *            the manifestUniversalDAO to set
	 */
	public void setManifestUniversalDAO(
			ManifestUniversalDAO manifestUniversalDAO) {
		this.manifestUniversalDAO = manifestUniversalDAO;
	}

	/**
	 * Sets the out manifest universal service.
	 * 
	 * @param outManifestUniversalService
	 *            the outManifestUniversalService to set
	 */
	public void setOutManifestUniversalService(
			OutManifestUniversalService outManifestUniversalService) {
		this.outManifestUniversalService = outManifestUniversalService;
	}

	/**
	 * @param outManifestCommonService
	 *            the outManifestCommonService to set
	 */
	public void setOutManifestCommonService(
			OutManifestCommonService outManifestCommonService) {
		this.outManifestCommonService = outManifestCommonService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.service.InMasterBagManifestService#
	 * saveOrUpdateInMBPL(com.ff.manifest.inmanifest.InMasterBagManifestTO)
	 */
	@Override
	public InMasterBagManifestTO saveOrUpdateInMBPL(
			InMasterBagManifestTO inMasterBagManifestTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("InMasterBagManifestServiceImpl::saveOrUpdateInMBPL::START------------>:::::::");
		String tranMsg = "";
		boolean isSaved = Boolean.FALSE;
		ManifestDO manifestDO = null;
		
		//Check is Header Manifest already inManifested
		inManifestCommonService.isManifestHeaderInManifested(inMasterBagManifestTO);
				
		ProcessTO processTO = new ProcessTO();
		processTO
				.setProcessCode(CommonConstants.PROCESS_IN_MANIFEST_MASTER_BAG);
		processTO = outManifestUniversalService.getProcess(processTO);
		inMasterBagManifestTO.setProcessId(processTO.getProcessId());
		inMasterBagManifestTO.setProcessCode(processTO.getProcessCode());

		// Check if already InManifested
		/*ManifestBaseTO baseTO = new ManifestBaseTO();
		baseTO.setProcessCode(CommonConstants.PROCESS_IN_MANIFEST_MASTER_BAG);
		baseTO.setManifestNumber(inMasterBagManifestTO.getManifestNumber());
		baseTO.setUpdateProcessCode(CommonConstants.PROCESS_IN_MANIFEST_MASTER_BAG);
		manifestDO = inManifestCommonDAO.getManifestDtls(baseTO);
		if (manifestDO != null) {
			ExceptionUtil
					.prepareBusinessException(InManifestConstants.MANIFEST_ALREADY_EXSITS);
			throw new CGBusinessException(
					InManifestConstants.MANIFEST_ALREADY_EXSITS);
		}*/
		

		//Less, Excess, Received status 
		InManifestTO inManifestTO = new InManifestTO();

		inManifestTO.setManifestNumbers(inMasterBagManifestTO.getBplNumbers());

		inManifestTO.setReceivedStatus(inMasterBagManifestTO
				.getReceivedStatus());
		inManifestTO.setManifestNumber(inMasterBagManifestTO
				.getManifestNumber());
		
		inManifestTO = inManifestCommonService.prepareInManifestTO4LessExcess(
				inManifestTO, CommonConstants.PROCESS_OUT_MANIFEST_MATER_BAG,
				CommonConstants.PROCESS_OUT_MANIFEST_MATER_BAG);
		inManifestCommonService.getLessExcessManifest(inMasterBagManifestTO,
				inManifestTO);
		
		
		// Prepare & save ManifestDO
		manifestDO = InMasterBagManifestConverter
				.domainConverter4InMasterBag(inMasterBagManifestTO);
		manifestDO = inManifestCommonDAO.saveOrUpdateManifest(manifestDO);

		//setting manifestId for TwoWayWrite
		InManifestUtils.setManifestId(inMasterBagManifestTO, manifestDO);
		
		// Prepare ManifestProcessDO
		/*List<ManifestProcessDO> manifestProcessDOs = InMasterBagManifestConverter
				.prepareManifestProcessDOList(inMasterBagManifestTO);

		// Saving manifest process
		if (!StringUtil.isEmptyList(manifestProcessDOs)) {
			InManifestUtils.setDestOfficeAndCityToManifestProcess(manifestDO,
					manifestProcessDOs);
			isSaved = inManifestCommonDAO
					.saveOrUpdateManifestProcess(manifestProcessDOs);

			//setting manifestProcessId for TwoWayWrite
			InManifestUtils.setManifestProcessId(inMasterBagManifestTO, manifestProcessDOs.get(0));
		}*/
		
		if (isSaved == Boolean.TRUE) {
			tranMsg = InManifestConstants.SUCCESS;
		}

		inMasterBagManifestTO.setTransMsg(tranMsg);

		LOGGER.trace("InMasterBagManifestServiceImpl::saveOrUpdateInMBPL::END------------>:::::::");
		return inMasterBagManifestTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.service.InMasterBagManifestService#
	 * searchMBPLManifest(com.ff.manifest.inmanifest.InMasterBagManifestTO)
	 */
	@Override
	public InMasterBagManifestTO searchMBPLManifest(
			InMasterBagManifestTO manifestInputTO) throws CGBusinessException,
			CGSystemException {

		LOGGER.trace("InMasterBagManifestServiceImpl::searchMBPLManifest::START------------>:::::::");
		ManifestDO manifestDO = null;
		InMasterBagManifestTO inMasterBagManifestTO = new InMasterBagManifestTO();
		manifestInputTO.setIsFetchProfileManifestEmbedded(Boolean.TRUE);
		manifestDO = inManifestCommonDAO
				.getManifestDetailsWithFetchProfile(manifestInputTO);
		InManifestUtils.resetFetchProfile(manifestInputTO);

		if (!StringUtil.isNull(manifestDO)) {

			inManifestCommonService.validateIsManifestOutManifested(manifestDO);
			// /////////////////////////////////////////////
			// search any MBPL Number in Out Manifest for origin office
			/*if (manifestDO.getOriginOffice() == null) {
				manifestInputTO
						.setProcessCode(InManifestConstants.OUT_MASTER_BAG_PROCESS_CODE);
				manifestInputTO
						.setUpdateProcessCode(InManifestConstants.OUT_MASTER_BAG_PROCESS_CODE);
				ManifestDO outManifestDO = inManifestCommonDAO
						.getManifestDtls(manifestInputTO);

				if (outManifestDO != null) {
					manifestDO.setOriginOffice(outManifestDO.getOriginOffice());
				}
			}*/
			// ///////////////////////////////////////////////

			inMasterBagManifestTO = InMasterBagManifestConverter
					.mbplManifestDomainConverter(manifestDO);
			inMasterBagManifestTO.setIsInManifest(Boolean.TRUE);
			
			if (!StringUtil.isEmptyColletion(manifestDO
					.getEmbeddedManifestDOs())) {
				List<InMasterBagManifestDetailsTO> mbplManifestDetailsTOs = InMasterBagManifestConverter
						.mbplManifestDomainConvertorForEmbeddedIn(manifestDO
								.getEmbeddedManifestDOs());

				double consigTotalWt = 0.0;

				for (InMasterBagManifestDetailsTO inMasterBagManifestDetailsTO : mbplManifestDetailsTOs) {

					if (!StringUtil.isEmptyDouble(inMasterBagManifestDetailsTO
							.getManifestWeight())) {
						consigTotalWt += inMasterBagManifestDetailsTO
								.getManifestWeight();
					}
				}

				inMasterBagManifestTO.setTotalBplWt(Double
						.parseDouble(new DecimalFormat("##.###")
								.format(consigTotalWt)));
				inMasterBagManifestTO
						.setInMasterBagManifestDtlsTOs(mbplManifestDetailsTOs);
			}
		} else {//search for other manifest

			ExceptionUtil.prepareBusinessException(
					InManifestConstants.MANIFEST_NUMBER_NOT_YET_RECEIVED,
					new String[] { manifestInputTO.getManifestNumber() });
			
			ManifestBaseTO manifestBaseTO = new ManifestBaseTO();
			manifestBaseTO.setManifestNumber(manifestInputTO.getManifestNumber());
			manifestDO = inManifestCommonDAO
					.getManifestDetailsWithFetchProfile(manifestBaseTO);
			if (manifestDO != null) {
				inMasterBagManifestTO = InMasterBagManifestConverter
						.mbplManifestDomainConverter(manifestDO);
				inMasterBagManifestTO.setManifestId(null);
			}
		}

		LOGGER.trace("InMasterBagManifestServiceImpl::searchMBPLManifest::END------------>:::::::");
		return inMasterBagManifestTO;
	}

	public InMasterBagManifestTO getInfoForPrint(
			List<InMasterBagManifestDetailsTO> mbplManifestDetailsTOs)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("InMasterBagManifestServiceImpl::getInfoForPrint::START------------>:::::::");
		InMasterBagManifestTO mbplInManifestTO = new InMasterBagManifestTO();
		//Integer totalConsg = 0;
		Long comail = 0L, actualConsg = 0L;
		ManifestDO manifestDOChild=null;
		for (InMasterBagManifestDetailsTO mBPLOutManifestDetailsTO : mbplManifestDetailsTOs) {
		/*	ManifestDO manifestDOChild = outManifestCommonService
					.getManifestById(mBPLOutManifestDetailsTO.getManifestId());//by manifest no and type=out order by desc manifest date
			*/
			ManifestBaseTO baseTO = new ManifestBaseTO(); 
			if(!StringUtil.isNull(mBPLOutManifestDetailsTO.getBplNumber())){
				baseTO.setManifestNumber(mBPLOutManifestDetailsTO.getBplNumber());
			}
			if(!StringUtil.isEmptyInteger(mBPLOutManifestDetailsTO.getOriginOfficeId())){
				baseTO.setOriginOfficeId(mBPLOutManifestDetailsTO.getOriginOfficeId());
			}
			baseTO.setManifestType(CommonConstants.MANIFEST_TYPE_OUT);
			Integer manifestId=inManifestCommonDAO.getManifestIdByManifestNoAndTypeAndOrigin(baseTO);
			if(!StringUtil.isEmptyInteger(manifestId)){
				ManifestBaseTO  baseTO1=new ManifestBaseTO();
				baseTO1.setManifestId(manifestId);
				baseTO1.setIsFetchProfileManifestEmbedded(Boolean.TRUE);
				 manifestDOChild = inManifestCommonDAO
					.getManifestDetailsWithFetchProfile(baseTO1);
			}
			if (!StringUtil.isNull(manifestDOChild.getManifestLoadContent())) {
				if (manifestDOChild.getManifestLoadContent().getConsignmentId()
						.equals(2)) {
					if (!StringUtil.isNull(manifestDOChild.getNoOfElements())) {
						//actualConsg += manifestDOChild.getNoOfElements();
						actualConsg += manifestUniversalDAO.getConsgCountByManifestId(manifestDOChild.getManifestId());
					}
				} else {

					if(!StringUtil.isNull(manifestDOChild.getEmbeddedManifestDOs())){
						 Set<ManifestDO>  packetList= manifestDOChild.getEmbeddedManifestDOs();
						for(ManifestDO manifestPacket:packetList){
							comail +=manifestUniversalDAO
							.getComailCountByManifestId(manifestPacket
									.getManifestId());
							actualConsg+=manifestUniversalDAO.getConsgCountByManifestId(manifestPacket
									.getManifestId());
						}
						
					}
					
					
/*					if (!StringUtil.isNull(manifestDOChild
							.getManifestMappedEmbedDtls())) {

						for (ManifestMappedEmbeddedDO embeddedDO : manifestDOChild
								.getManifestMappedEmbedDtls()) {

							ManifestDO manifestPacket = outManifestCommonService
									.getManifestById(embeddedDO.getManifestId());
							comail += manifestUniversalDAO
									.getComailCountByManifestId(embeddedDO
											.getManifestId());
							if (!StringUtil.isNull(manifestPacket
									.getNoOfElements())) {
								totalConsg += manifestPacket.getNoOfElements();
							}
							// totalPacket++;
						}
					}*/
				}
			}
		}

	/*	x = totalConsg.longValue();
		actualConsg = x - comail;*/
		mbplInManifestTO.setTotalConsg(actualConsg);
		mbplInManifestTO.setTotalComail(comail);
		mbplInManifestTO.setRowCount(mbplManifestDetailsTOs.size());
		LOGGER.trace("InMasterBagManifestServiceImpl::getInfoForPrint::END------------>:::::::");
		return mbplInManifestTO;
	}

}
