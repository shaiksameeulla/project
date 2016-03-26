package com.ff.web.manifest.rthrto.utils;

import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.domain.CGFactDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.serviceOffering.ReasonDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.manifest.rthrto.RthRtoDetailsTO;
import com.ff.manifest.rthrto.RthRtoManifestTO;
import com.ff.organization.OfficeTO;
import com.ff.universe.consignment.dao.ConsignmentCommonDAO;
import com.ff.web.loadmanagement.utils.LoadManagementUtils;
import com.ff.web.manifest.rthrto.service.RthRtoManifestCommonService;

/**
 * The Class RthRtoManifestUtils.
 * 
 * @author narmdr
 */
public class RthRtoManifestUtils {
	
	private static RthRtoManifestCommonService rthRtoManifestCommonService;
	
	/**
	 * @param rthRtoManifestCommonService the rthRtoManifestCommonService to set
	 */
	public static void setRthRtoManifestCommonService(
			RthRtoManifestCommonService rthRtoManifestCommonService) {
		RthRtoManifestUtils.rthRtoManifestCommonService = rthRtoManifestCommonService;
	}

	/**
	 * Sets the update flag4 db sync.
	 *
	 * @param cgBaseDO the new update flag4 db sync
	 */
	public static void setUpdateFlag4DBSync(CGBaseDO cgBaseDO) {
		cgBaseDO.setDtUpdateToCentral(CommonConstants.YES);
		cgBaseDO.setDtToCentral(CommonConstants.NO);
		LoadManagementUtils.validateAndSetTwoWayWriteFlag(cgBaseDO);
	}
	
	/**
	 * Sets the save flag4 db sync.
	 *
	 * @param cgBaseDO the new save flag4 db sync
	 */
	public static void setSaveFlag4DBSync(CGBaseDO cgBaseDO) {
		cgBaseDO.setDtToCentral(CommonConstants.NO);
		LoadManagementUtils.validateAndSetTwoWayWriteFlag(cgBaseDO);
	}
	
	/**
	 * Sets the rth rto manifest to.
	 *
	 * @param rthRtoManifestTO the rth rto manifest to
	 * @param rthRtoManifestCommonService the rth rto manifest common service
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public static void setRthRtoManifestTO(RthRtoManifestTO rthRtoManifestTO,RthRtoManifestCommonService rthRtoManifestCommonService)
			throws CGBusinessException, CGSystemException {
		List<OfficeTO> orgHubOfficeTOs=null;
		List<OfficeTO> destHubOffficeTOs=null;
		if(StringUtils.equalsIgnoreCase(rthRtoManifestTO.getOriginOfficeTO().getOfficeTypeTO().getOffcTypeCode(), CommonConstants.OFF_TYPE_BRANCH_OFFICE)){
			orgHubOfficeTOs=rthRtoManifestCommonService.getOfficesByCityAndOfficeTypes(rthRtoManifestTO.getOriginOfficeTO().getCityId(), CommonConstants.OFF_TYPE_BRANCH_OFFICE);
		}else if(StringUtils.equalsIgnoreCase(rthRtoManifestTO.getOriginOfficeTO().getOfficeTypeTO().getOffcTypeCode(), CommonConstants.OFF_TYPE_HUB_OFFICE)){
			destHubOffficeTOs=rthRtoManifestCommonService.getOfficesByCityAndOfficeTypes(rthRtoManifestTO.getDestCityTO().getCityId(), CommonConstants.OFF_TYPE_HUB_OFFICE);
		}
		//List<Integer> destOfficeIdsList=null;
		Set<Integer> destOfficeIdSet = null;
		if(!StringUtil.isEmptyList(orgHubOfficeTOs)){
			destOfficeIdSet=new HashSet<Integer>();
			for (OfficeTO to : orgHubOfficeTOs) {
				destOfficeIdSet.add(to.getOfficeId());
			}
		}
		if(!StringUtil.isEmptyList(destHubOffficeTOs)){
			if(StringUtil.isEmptyColletion(destOfficeIdSet)){
				destOfficeIdSet=new HashSet<Integer>();
			}
			for (OfficeTO to : destHubOffficeTOs) {
				destOfficeIdSet.add(to.getOfficeId());
			}
			destOfficeIdSet.add(rthRtoManifestTO.getDestOfficeTO().getOfficeId());
		}
		//destOfficeIdSet = new HashSet<Integer>(destOfficeIdsList);
		rthRtoManifestTO.setDestOffIds(destOfficeIdSet);
	}

	//Read the Consignment and set the details in consignmentDO as well as set the cn status and process code etc..
	/**
	 * Gets the and set rto hed consignments.
	 *
	 * @param consignmentCommonDAO the consignment common dao
	 * @param rthRtoDetailsTOs the rth rto details t os
	 * @param processDO the process do
	 * @return the and set rto hed consignments
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public static Set<ConsignmentDO> getAndSetRTOHedConsignments(
			ConsignmentCommonDAO consignmentCommonDAO,
			List<RthRtoDetailsTO> rthRtoDetailsTOs,ProcessDO processDO) throws CGSystemException, CGBusinessException {
		Set<ConsignmentDO> consignmentDOsSet = new LinkedHashSet<>();
		for (RthRtoDetailsTO rthRtoDtlTO : rthRtoDetailsTOs) {
			if (StringUtils.isNotEmpty(rthRtoDtlTO.getConsgNumber())) {
				//Set grid details
				ConsignmentDO consignmentDO=consignmentCommonDAO.getConsingmentDtls(rthRtoDtlTO.getConsgNumber());
				if(!StringUtil.isNull(consignmentDO)){
					consignmentDO.setRemarks(rthRtoDtlTO.getRemarks());
					if(!StringUtil.isNull(rthRtoDtlTO.getReasonTO())){
						ReasonDO reasonDO=new ReasonDO();
						reasonDO=(ReasonDO) CGObjectConverter.createDomainFromTo(rthRtoDtlTO.getReasonTO(), reasonDO);
						consignmentDO.setCnReturnReason(reasonDO);
					}
					
					//set update details
					consignmentDO.setUpdatedProcess(processDO);
					
					// Added by Narmdeshwar
					//Set RTO Status, Event Date, RTO Billing Flags
					if(StringUtils.equals(CommonConstants.MANIFEST_TYPE_RTO, rthRtoDtlTO.getConsgStatus())){
						consignmentDO.setConsgStatus(rthRtoDtlTO.getConsgStatus());
						rthRtoManifestCommonService.setRtoBillingFlagsInConsignment(consignmentDO);
					}else{//Set RTH Status
						consignmentDO.setConsgStatus(CommonConstants.CONSIGNMENT_STATUS_RTH);
					}
					consignmentDO.setEventDate(Calendar.getInstance().getTime());
					consignmentDOsSet.add(consignmentDO);
				}
			}
		}
		return consignmentDOsSet;
	}

	/**
	 * Sets the manifest process id.
	 *
	 * @param rthRtoManifestTO the rth rto manifest to
	 * @param manifestProcessDO the manifest process do
	 */
	/*public static void setManifestProcessId(RthRtoManifestTO rthRtoManifestTO,
			ManifestProcessDO manifestProcessDO) {
		rthRtoManifestTO.setManifestProcessId(manifestProcessDO
				.getManifestProcessId());
	}*/
	
	/**
	 * Sets the manifest id.
	 *
	 * @param rthRtoManifestTO the rth rto manifest to
	 * @param manifestDO the manifest do
	 */
	public static void setManifestId(RthRtoManifestTO rthRtoManifestTO,
			ManifestDO manifestDO) {
		rthRtoManifestTO.setTwoWayManifestId(manifestDO.getManifestId());
	}
	
	/**
	 * Sets the created and updated date.
	 *
	 * @param cgFactDO the new created and updated date
	 */
	public static void setCreatedAndUpdatedDate(CGFactDO cgFactDO) {
		if(cgFactDO.getCreatedDate()==null){
			cgFactDO.setCreatedDate(Calendar.getInstance().getTime());
		}
		cgFactDO.setUpdatedDate(Calendar.getInstance().getTime());
	}

	/**
	 * Sets the operating off in consgs.
	 *
	 * @param manifestDO the new operating off in consgs
	 */
	public static void setOperatingOffInConsgs(ManifestDO manifestDO) {
		if (manifestDO != null
				&& !StringUtil.isEmptyInteger(manifestDO.getOperatingOffice())
				&& !StringUtil.isEmptyColletion(manifestDO.getConsignments())) {
			for (ConsignmentDO consignmentDO : manifestDO.getConsignments()) {
				consignmentDO.setOperatingOffice(manifestDO
						.getOperatingOffice());
			}
		}
	}
	
}
