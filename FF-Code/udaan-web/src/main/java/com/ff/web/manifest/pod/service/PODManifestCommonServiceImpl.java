/**
 * 
 */
package com.ff.web.manifest.pod.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.manifest.ConsignmentManifestDO;
import com.ff.domain.manifest.GridItemOrderDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.manifest.pod.PODManifestDtlsTO;
import com.ff.manifest.pod.PODManifestTO;
import com.ff.organization.OfficeTO;
import com.ff.organization.OfficeTypeTO;
import com.ff.to.drs.DeliveryDetailsTO;
import com.ff.to.drs.DeliveryTO;
import com.ff.tracking.ProcessTO;
import com.ff.universe.consignment.service.ConsignmentCommonService;
import com.ff.universe.drs.service.DeliveryUniversalService;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.manifest.service.ManifestUniversalService;
import com.ff.universe.organization.service.OrganizationCommonService;
import com.ff.universe.tracking.service.TrackingUniversalService;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.pod.constants.PODManifestConstants;
import com.ff.web.manifest.pod.converter.PODManifestConverter;
import com.ff.web.manifest.pod.dao.PODManifestCommonDAO;
import com.ff.web.manifest.service.ManifestCommonService;

/**
 * @author nkattung
 * 
 */
public class PODManifestCommonServiceImpl implements PODManifestCommonService {
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(PODManifestCommonServiceImpl.class);
	private DeliveryUniversalService deliveryUniversalService;
	private PODManifestCommonDAO podManifestCommonDAO;
	/** The organization common service. */
	private OrganizationCommonService organizationCommonService;

	/** The geography common service. */
	private GeographyCommonService geographyCommonService;
	/** The tracking universal service. */
	private TrackingUniversalService trackingUniversalService;

	private ManifestUniversalService manifestUniversalService;
	private ManifestCommonService manifestCommonService;
	/** The consignment common service. */
	private ConsignmentCommonService consignmentCommonService;

	
	/**
	 * @return the consignmentCommonService
	 */
	public ConsignmentCommonService getConsignmentCommonService() {
		return consignmentCommonService;
	}

	/**
	 * @param consignmentCommonService
	 *            the consignmentCommonService to set
	 */
	public void setConsignmentCommonService(
			ConsignmentCommonService consignmentCommonService) {
		this.consignmentCommonService = consignmentCommonService;
	}
	
	
	
	/**
	 * @param manifestCommonService the manifestCommonService to set
	 */
	public void setManifestCommonService(ManifestCommonService manifestCommonService) {
		this.manifestCommonService = manifestCommonService;
	}

	/**
	 * @param deliveryUniversalService
	 *            the deliveryUniversalService to set
	 */
	public void setDeliveryUniversalService(
			DeliveryUniversalService deliveryUniversalService) {
		this.deliveryUniversalService = deliveryUniversalService;
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
	 * @param trackingUniversalService
	 *            the trackingUniversalService to set
	 */
	public void setTrackingUniversalService(
			TrackingUniversalService trackingUniversalService) {
		this.trackingUniversalService = trackingUniversalService;
	}

	/**
	 * @param organizationCommonService
	 *            the organizationCommonService to set
	 */
	public void setOrganizationCommonService(
			OrganizationCommonService organizationCommonService) {
		this.organizationCommonService = organizationCommonService;
	}

	/**
	 * @param geographyCommonService
	 *            the geographyCommonService to set
	 */
	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}

	/**
	 * @param manifestUniversalService
	 *            the manifestUniversalService to set
	 */
	public void setManifestUniversalService(
			ManifestUniversalService manifestUniversalService) {
		this.manifestUniversalService = manifestUniversalService;
	}

	/**
	 * Gets the delivered consignments details.
	 * 
	 * @param consignment
	 *            the consignment
	 * @return PODManifestDtlsTO
	 * @throws CGBusinessException
	 *             ,CGSystemException the cG system exception
	 */
	public PODManifestDtlsTO getDeliverdConsgDtls(String consignment,
			Integer officeId, String podManifestType)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PODManifestCommonServiceImpl :: getDeliverdConsgDtls() :: Start --------> ::::::");
		PODManifestDtlsTO podManiefstDtls = null;
		// Check for is consingment is already manifested or not
		boolean isCNManifested = isConsgnNoManifested(consignment,
				PODManifestConstants.POD_MANIFEST_OUT,
				PODManifestConstants.POD_MANIFEST, CommonConstants.PROCESS_POD);
		if (isCNManifested)
			throw new CGBusinessException(PODManifestConstants.CN_EXISTS);
		DeliveryDetailsTO deliveryDtls = deliveryUniversalService
				.getDeliverdConsgDtls(consignment, officeId);
		if (StringUtil.isNull(deliveryDtls)) {
			ExceptionUtil.prepareBusinessException(PODManifestConstants.INVALID_CN, new String[]{consignment});
			//throw new CGBusinessException(PODManifestConstants.INVALID_CN);
		} else {
			podManiefstDtls = new PODManifestDtlsTO();
			podManiefstDtls.setConsgId(deliveryDtls.getConsignmentTO()
					.getConsgId());
			podManiefstDtls.setConsgNumber(deliveryDtls.getConsignmentNumber());
			podManiefstDtls.setReceivedDate(DateUtil
					.getDateInDDMMYYYYHHMMSlashFormat(deliveryDtls
							.getDeliveryTO().getFsOutTime()));
			podManiefstDtls.setDlvDate(DateUtil
					.getDateInDDMMYYYYHHMMSlashFormat(deliveryDtls
							.getDeliveryDate()));
			if (StringUtils.isNotEmpty(deliveryDtls.getReceiverName())
					&& StringUtils
							.isNotEmpty(deliveryDtls.getCompanySealSign()))
				podManiefstDtls.setRecvNameOrCompSeal(deliveryDtls
						.getReceiverName()
						+ " / "
						+ deliveryDtls.getCompanySealSign());
			else if (StringUtils.isNotEmpty(deliveryDtls.getReceiverName()))
				podManiefstDtls.setRecvNameOrCompSeal(deliveryDtls
						.getReceiverName());
			else if (StringUtils.isNotEmpty(deliveryDtls.getCompanySealSign()))
				podManiefstDtls.setRecvNameOrCompSeal(deliveryDtls
						.getCompanySealSign());
			podManiefstDtls.setReceivedStatus(deliveryDtls.getDeliveryStatus());
		}
		LOGGER.trace("PODManifestCommonServiceImpl :: getDeliverdConsgDtls() :: END --------> ::::::");
		return podManiefstDtls;
	}

	/**
	 * Get all the cities comes under the zone.
	 * <p>
	 * <ul>
	 * <li>the list of cities comes under the zone will be returned.
	 * </ul>
	 * <p>
	 * 
	 * @param regionTO
	 *            the region to
	 * @return List<CityTO> will get filled with all the city details.
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 * @author shahnsha
	 */

	public List<CityTO> getCitiesByRegion(RegionTO regionTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PODManifestCommonServiceImpl :: getCitiesByRegion() :: START --------> ::::::");
		List<CityTO> cityTOs = null;
		if (!StringUtil.isNull(regionTO)) {
			CityTO cityTO = new CityTO();
			cityTO.setRegion(regionTO.getRegionId());
			cityTOs = geographyCommonService.getCitiesByCity(cityTO);
		}
		LOGGER.trace("PODManifestCommonServiceImpl :: getCitiesByRegion() :: END --------> ::::::");
		return cityTOs;
	}

	
	public List<CityTO> getCitiesForLoggdInBranchOffice(Integer loggdInbranchOfficeId)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PODManifestCommonServiceImpl :: getCitiesForLoggdInBranchOffice() :: START --------> ::::::");
		List<CityTO> cityTOs = new ArrayList<CityTO>();
		CityTO cityTO = geographyCommonService.getCityByOfficeId(loggdInbranchOfficeId);
		cityTOs.add(cityTO);
		LOGGER.trace("PODManifestCommonServiceImpl :: getCitiesForLoggdInBranchOffice() :: END --------> ::::::");
		return cityTOs;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.inmanifest.service.InManifestCommonService#getAllRegions
	 * ()
	 */
	@Override
	public List<RegionTO> getAllRegions() throws CGBusinessException,
			CGSystemException {
		return geographyCommonService.getAllRegions();

	}

	@Override
	public List<OfficeTO> getAllOfficesByCity(Integer cityId)
			throws CGBusinessException, CGSystemException {
		List<OfficeTO> officeTOList = organizationCommonService
				.getAllOfficesByCity(cityId);
		return officeTOList;
	}
	
	
	@Override
	public List<OfficeTO> getAllHubsByCity(Integer cityId,String officeTypeCode)
			throws CGBusinessException, CGSystemException {
		List<OfficeTO> officeTOList = organizationCommonService
				.getAllHubOfficesByCity(cityId, officeTypeCode);
		return officeTOList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#getProcess(com.ff.tracking
	 * .ProcessTO)
	 */
	public ProcessTO getProcess(ProcessTO process) throws CGSystemException,
			CGBusinessException {
		return trackingUniversalService.getProcess(process);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.pod.service.PODManifestCommonServiceImpl#isManifestExists
	 * (java.lang.String)
	 */
	@Override
	public boolean isManifestExists(String manifestNo,
			String manifestDirection, String manifestType,
			String manifestPorcessCode) throws CGBusinessException,
			CGSystemException {
		return manifestUniversalService.isManifestExists(manifestNo,
				manifestDirection, manifestType, manifestPorcessCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.pod.service.PODManifestCommonServiceImpl#getManifetsDtls
	 * (java.lang.String)
	 */
	@Override
	public PODManifestTO searchManifetsDtls(String manifestNo,
			String manifestType, String processCode, Integer orginOfficeId)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PODManifestCommonServiceImpl :: searchManifetsDtls() :: START --------> ::::::");
		PODManifestTO podManifest = null;
		ManifestDO manifest = podManifestCommonDAO.searchManifestDtls(
				manifestNo, manifestType, processCode, orginOfficeId);
		if (StringUtil.isNull(manifest))
			throw new CGBusinessException(PODManifestConstants.NO_RESULT);
		else {
			GridItemOrderDO gridItemOrderDO = new GridItemOrderDO();
			gridItemOrderDO.setGridPosition(manifest.getGridItemPosition());
			gridItemOrderDO.setConsignmentDOs(manifest.getConsignments());
			gridItemOrderDO = manifestCommonService.arrangeOrder(
					gridItemOrderDO, ManifestConstants.ACTION_SEARCH);
			manifest.setConsignments(gridItemOrderDO.getConsignmentDOs());
			podManifest = PODManifestConverter.podMnfstTrasnferObjConverter(
					manifest, geographyCommonService);
		}
		LOGGER.trace("PODManifestCommonServiceImpl :: searchManifetsDtls() :: END --------> ::::::");
		return podManifest;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.pod.service.PODManifestCommonService#isConsgnNoManifested
	 * (java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean isConsgnNoManifested(String consgNumber,
			String manifestDirection, String manifestType,
			String manifestPorcessCode) throws CGSystemException,
			CGBusinessException {
		return podManifestCommonDAO.isConsgnNoManifested(consgNumber,
				manifestDirection, manifestType, manifestPorcessCode);
	}

	public OfficeTO getOfficeDetailsById(Integer officeId)
			throws CGBusinessException, CGSystemException {
		OfficeTO officedtails = organizationCommonService
				.getOfficeDetails(officeId);
		return officedtails;
	}

	@Override
	public PODManifestTO searchOutgoingPODManifetsDtls(String manifestNo,
			String manifestType, String processCode, Integer orginOfficeId)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PODManifestCommonServiceImpl :: searchOutgoingPODManifetsDtls() :: START --------> ::::::");
		PODManifestTO podManifest = null;
		ManifestDO manifest = podManifestCommonDAO.searchOutgoingPODManifetsDtls(
				manifestNo, manifestType, processCode, orginOfficeId);
		if (StringUtil.isNull(manifest))
			throw new CGBusinessException(PODManifestConstants.NO_RESULT);
		else {
			GridItemOrderDO gridItemOrderDO = new GridItemOrderDO();
			gridItemOrderDO.setGridPosition(manifest.getGridItemPosition());
			gridItemOrderDO.setConsignmentDOs(manifest.getConsignments());
			gridItemOrderDO = manifestCommonService.arrangeOrder(
					gridItemOrderDO, ManifestConstants.ACTION_SEARCH);
			manifest.setConsignments(gridItemOrderDO.getConsignmentDOs());
			podManifest = PODManifestConverter.podMnfstTrasnferObjConverter(
					manifest, geographyCommonService);
		}
		LOGGER.trace("PODManifestCommonServiceImpl :: searchOutgoingPODManifetsDtls() :: END --------> ::::::");
		return podManifest;
	}
	
	
	
	
	public PODManifestDtlsTO getDeliverdConsgDtlsForDestBranchToDestHub(String consignment,
			Integer officeId, String podManifestType,String loggdOfficType )
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PODManifestCommonServiceImpl :: getDeliverdConsgDtls() :: Start --------> ::::::");
		PODManifestDtlsTO podManiefstDtls = null;
		DeliveryDetailsTO dlvDtlsTO = null;
		// Check for is consingment is already manifested or not
		boolean isCNManifested = isConsgnNoManifested(consignment,
				PODManifestConstants.POD_MANIFEST_OUT,
				PODManifestConstants.POD_MANIFEST, CommonConstants.PROCESS_POD);
		
		if(loggdOfficType.equalsIgnoreCase(PODManifestConstants.BRANCH_OFFICE)){
			if (isCNManifested)
				throw new CGBusinessException(PODManifestConstants.CN_EXISTS);
		}
		
		DeliveryDetailsDO dlvDtlsDO = podManifestCommonDAO
				.getDeliverdConsgDtlsForDestBranchToDestHub(consignment,
						officeId);
		if (StringUtil.isNull(dlvDtlsDO)) {
			dlvDtlsDO = podManifestCommonDAO.getConsignmentDetailsForParentCN(
					consignment, officeId);
			if (!StringUtil.isNull(dlvDtlsDO)) {
				dlvDtlsTO = new DeliveryDetailsTO();
				ConsignmentTO consingment = consignmentCommonService
						.getConsingmentDtls(consignment);
				if (StringUtil.isNull(consingment)){
					throw new CGBusinessException(PODManifestConstants.CHILD_CONSIGNMENT_NOT_ALLOWED);
				}
				dlvDtlsTO.setDeliveryDate(dlvDtlsDO.getDeliveryDate());
				dlvDtlsTO
						.setConsignmentNumber(dlvDtlsDO.getConsignmentNumber());
				dlvDtlsTO.setConsignmentTO(consingment);
				dlvDtlsTO.setReceiverName(dlvDtlsDO.getReceiverName());
				dlvDtlsTO.setCompanySealSign(dlvDtlsDO.getCompanySealSign());
				dlvDtlsTO.setDeliveryStatus(dlvDtlsDO.getDeliveryStatus());
				DeliveryTO deliveryTO = new DeliveryTO();
				deliveryTO.setFsOutTime(dlvDtlsDO.getDeliveryDO()
						.getFsOutTime());
				dlvDtlsTO.setDeliveryTO(deliveryTO);
			}
		} else {
			if (!StringUtil.isNull(dlvDtlsDO)) {
				dlvDtlsTO = new DeliveryDetailsTO();
				dlvDtlsTO.setDeliveryDate(dlvDtlsDO.getDeliveryDate());
				dlvDtlsTO
						.setConsignmentNumber(dlvDtlsDO.getConsignmentNumber());
				ConsignmentTO consingment = consignmentCommonService
						.getConsingmentDtls(dlvDtlsDO.getConsignmentNumber());
				dlvDtlsTO.setConsignmentTO(consingment);
				dlvDtlsTO.setReceiverName(dlvDtlsDO.getReceiverName());
				dlvDtlsTO.setCompanySealSign(dlvDtlsDO.getCompanySealSign());
				dlvDtlsTO.setDeliveryStatus(dlvDtlsDO.getDeliveryStatus());
				DeliveryTO deliveryTO = new DeliveryTO();
				deliveryTO.setFsOutTime(dlvDtlsDO.getDeliveryDO()
						.getFsOutTime());
				dlvDtlsTO.setDeliveryTO(deliveryTO);
			}
		}
		
		if (StringUtil.isNull(dlvDtlsTO)) {
			if(loggdOfficType.equalsIgnoreCase(PODManifestConstants.BRANCH_OFFICE)){
				ExceptionUtil.prepareBusinessException(PODManifestConstants.INVALID_CN, new String[]{consignment});
				//throw new CGBusinessException(PODManifestConstants.INVALID_CN);
			}
			
		} else {
			podManiefstDtls = new PODManifestDtlsTO();
			podManiefstDtls.setConsgId(dlvDtlsTO.getConsignmentTO()
					.getConsgId());
			podManiefstDtls.setConsgNumber(dlvDtlsTO.getConsignmentNumber());
			podManiefstDtls.setReceivedDate(DateUtil
					.getDateInDDMMYYYYHHMMSlashFormat(dlvDtlsTO
							.getDeliveryTO().getFsOutTime()));
			podManiefstDtls.setDlvDate(DateUtil
					.getDateInDDMMYYYYHHMMSlashFormat(dlvDtlsTO
							.getDeliveryDate()));
			if (StringUtils.isNotEmpty(dlvDtlsTO.getReceiverName())
					&& StringUtils
							.isNotEmpty(dlvDtlsTO.getCompanySealSign()))
				podManiefstDtls.setRecvNameOrCompSeal(dlvDtlsTO
						.getReceiverName()
						+ " / "
						+ dlvDtlsTO.getCompanySealSign());
			else if (StringUtils.isNotEmpty(dlvDtlsTO.getReceiverName()))
				podManiefstDtls.setRecvNameOrCompSeal(dlvDtlsTO
						.getReceiverName());
			else if (StringUtils.isNotEmpty(dlvDtlsTO.getCompanySealSign()))
				podManiefstDtls.setRecvNameOrCompSeal(dlvDtlsTO
						.getCompanySealSign());
			podManiefstDtls.setReceivedStatus(dlvDtlsTO.getDeliveryStatus());
		}
		LOGGER.trace("PODManifestCommonServiceImpl :: getDeliverdConsgDtls() :: END --------> ::::::");
		return podManiefstDtls;
	}
	
	
	public List<OfficeTypeTO> getOfficeTypeList(List<String> officeTypeCodes)
			throws CGBusinessException, CGSystemException {
		OfficeTypeTO officeTypeTO=null;
		List<OfficeTypeTO> officeTypeList = new ArrayList<OfficeTypeTO>();;
		for(int i=0;i<officeTypeCodes.size();i++){
			
			 officeTypeTO = organizationCommonService.getOfficeTypeIdByOfficeTypeCode(officeTypeCodes.get(i));
			 officeTypeList.add(officeTypeTO);
		}
		
		return officeTypeList;
	}
	
	
	
	@Override
	public List<OfficeTO> getAllBranchesByCity(Integer cityId,Integer officeTypeId)
			throws CGBusinessException, CGSystemException {
		List<OfficeTO> officeTOList = organizationCommonService
				.getAllOfficesByCityAndOfficeType(cityId, officeTypeId);
		return officeTOList;
	}

	public List<ConsignmentManifestDO> isConsgnNoManifestedToDestHub(String consignment,
			String podManifestType, String manifestProcessCode,Integer officeId) throws CGSystemException{
		List<ConsignmentManifestDO> consPodManifestList = null;
		//consPodManifestList = podManifestCommonDAO.isConsgnNoManifestedToDestHub(consignment,podManifestType,manifestProcessCode,officeId);
		consPodManifestList = podManifestCommonDAO.isConsgnNoInManifestedAtLoggdInOffc(consignment,podManifestType,manifestProcessCode,officeId);
		return consPodManifestList;
	}
	
	public  PODManifestDtlsTO getPODInManifstdConsingmentDtls(String consignmentNo) throws CGSystemException, CGBusinessException{
		PODManifestDtlsTO deliveryDtls =null;
		
		//consPodManifestList = podManifestCommonDAO.isConsgnNoManifestedToDestHub(consignment,podManifestType,manifestProcessCode,officeId);
		ConsignmentTO consTO = consignmentCommonService.getConsingmentDtls(consignmentNo);
		if(!StringUtil.isNull(consTO)){
		deliveryDtls = new PODManifestDtlsTO();
		deliveryDtls.setConsgNumber(consTO.getConsgNo());
		deliveryDtls.setReceivedDate(DateUtil
				.getDateInDDMMYYYYHHMMSlashFormat(consTO
						.getReceivedDateTime()));
		deliveryDtls.setDlvDate(DateUtil
				.getDateInDDMMYYYYHHMMSlashFormat(consTO
						.getDeliveredDate()));
		if (StringUtils.isNotEmpty(consTO.getRecvNameOrCompName())){
			deliveryDtls.setRecvNameOrCompSeal(consTO.getRecvNameOrCompName());
		}
		
		deliveryDtls.setConsgId(consTO.getConsgId());
		}
		
		
		/*else if (StringUtils.isNotEmpty(dlvDtlsTO.getReceiverName()))
			deliveryDtls.setRecvNameOrCompSeal(dlvDtlsTO
					.getReceiverName());
		else if (StringUtils.isNotEmpty(dlvDtlsTO.getCompanySealSign()))
			deliveryDtls.setRecvNameOrCompSeal(dlvDtlsTO
					.getCompanySealSign());
		deliveryDtls.setReceivedStatus(dlvDtlsTO.getDeliveryStatus());*/
		
		return deliveryDtls;
	}
	

}
