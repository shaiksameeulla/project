package com.ff.web.manifest.rthrto.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.manifest.rthrto.RthRtoDetailsTO;
import com.ff.manifest.rthrto.RthRtoManifestTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.umc.UserTO;
import com.ff.universe.util.UdaanContextService;
import com.ff.web.manifest.rthrto.converter.RthRtoManifestParcelConverter;
import com.ff.web.manifest.rthrto.dao.RthRtoManifestCommonDAO;
import com.ff.web.manifest.rthrto.utils.RthRtoManifestUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class RthRtoManifestParcelServiceImpl.
 */
public class RthRtoManifestParcelServiceImpl implements RthRtoManifestParcelService{
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(RthRtoManifestParcelService.class);
	
	/** The rth rto manifest common dao. */
	private RthRtoManifestCommonDAO rthRtoManifestCommonDAO;
	
	/** The rth rto manifest common service. */
	private static RthRtoManifestCommonService rthRtoManifestCommonService;
	
	private transient UdaanContextService udaanContextService;
	/**
	 * Sets the rth rto manifest common service.
	 *
	 * @param rthRtoManifestCommonService the rthRtoManifestCommonService to set
	 */
	@SuppressWarnings("static-access")
	public void setRthRtoManifestCommonService(
			RthRtoManifestCommonService rthRtoManifestCommonService) {
		this.rthRtoManifestCommonService = rthRtoManifestCommonService;
	}
	
	/**
	 * Sets the rth rto manifest common dao.
	 *
	 * @param rthRtoManifestCommonDAO the rthRtoManifestCommonDAO to set
	 */
	public void setRthRtoManifestCommonDAO(
			RthRtoManifestCommonDAO rthRtoManifestCommonDAO) {
		this.rthRtoManifestCommonDAO = rthRtoManifestCommonDAO;
	}

	
	
	public void setUdaanContextService(UdaanContextService udaanContextService) {
		this.udaanContextService = udaanContextService;
	}

	/* (non-Javadoc)
	 * @see com.ff.web.manifest.rthrto.service.RthRtoManifestParcelService#saveOrUpdateRthRtoManifestParcel(com.ff.manifest.rthrto.RthRtoManifestTO)
	 */
	@Override
	public RthRtoManifestTO saveOrUpdateRthRtoManifestParcel(
			RthRtoManifestTO rthRtoManifestTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("RthRtoManifestParcelServiceImpl::saveOrUpdateRthRtoManifestParcel::START------------>:::::::");
	  try{	
		ManifestDO manifestDO = null;
//		ManifestProcessDO manifestProcessDO = null;	
		List<ConsignmentBillingRateDO> consignmentBillingRateList = new ArrayList<ConsignmentBillingRateDO>();
		RthRtoManifestUtils.setRthRtoManifestTO(rthRtoManifestTO,rthRtoManifestCommonService);	
		
		manifestDO = RthRtoManifestParcelConverter.rthRtoManifestParcelDomainconverter(rthRtoManifestTO);
		//added operating level & office
		rthRtoManifestCommonService.getAndSetOperatingLevelAndOfficeToManifest(manifestDO);
		manifestDO = rthRtoManifestCommonDAO.saveOrUpdateManifest(manifestDO);

		//setting manifestId for TwoWayWrite
		RthRtoManifestUtils.setManifestId(rthRtoManifestTO, manifestDO);
		
//		manifestProcessDO = RthRtoManifestParcelConverter.rthRtoManifestProcessParcelDomainconverter(rthRtoManifestTO);
//		manifestProcessDO = rthRtoManifestCommonDAO.saveOrUpdateManifestProcess(manifestProcessDO);
		
		//setting manifestProcessId for TwoWayWrite
//		RthRtoManifestUtils.setManifestProcessId(rthRtoManifestTO, manifestProcessDO);
		
		List<RthRtoDetailsTO> rthRtoDetailTO=rthRtoManifestTO.getRthRtoDetailsTOs();
		ConsignmentBillingRateDO consignmentBillingRateDO = null;
		String rtoStatus="R";
		if(rthRtoManifestTO.getManifestType().equals("R")){
			for(RthRtoDetailsTO rthRtoDetailsTO:rthRtoDetailTO){
				Map<String,ConsignmentRateCalculationOutputTO> rateDetails=rthRtoManifestTO.getRateCompnents();
				if(!StringUtil.isNull(rateDetails)){
					for (Map.Entry<String, ConsignmentRateCalculationOutputTO> entry : rateDetails.entrySet()) {
						LOGGER.debug("Key = " + entry.getKey() + ", Value = " + entry.getValue());
						if(entry.getKey().equalsIgnoreCase(rthRtoDetailsTO.getConsgNumber())){
							ConsignmentRateCalculationOutputTO rateOutputTO=entry.getValue();
							ConsignmentDO consingnment = new ConsignmentDO();
							consingnment.setConsgId(rateOutputTO.getConsgId());
							consignmentBillingRateDO=rthRtoManifestCommonDAO.getAlreadyExistConsgRtoRate(consingnment,rtoStatus);
							if(StringUtil.isNull(consignmentBillingRateDO)){
					    	   /* ConsignmentBillingRateDO consignmentBillingRate=new ConsignmentBillingRateDO();
							    prepareConsgRateCalcDomain(entry.getValue(),consignmentBillingRate);
							    ConsignmentDO consg = new ConsignmentDO();
							    consg.setConsgId(rateOutputTO.getConsgId());
							    consignmentBillingRate.setConsignmentDO(consg);
							    consignmentBillingRate.setRateCalculatedFor(rtoStatus);
							    rthRtoManifestCommonDAO.saveOrUpdateConsgRtoRate(consignmentBillingRate);*/
								ConsignmentBillingRateDO consignmentBillingRate=new ConsignmentBillingRateDO();
								prepareConsgRateCalcDomain(entry.getValue(),consignmentBillingRate);
					    		ConsignmentDO consg = new ConsignmentDO();
							    consg.setConsgId(rateOutputTO.getConsgId());
							    consignmentBillingRate.setConsignmentDO(consg);
							    consignmentBillingRate.setRateCalculatedFor(rtoStatus);
					    		consignmentBillingRateList.add(consignmentBillingRate);			    		
							}
						}
					}
				}
			}
		   if(consignmentBillingRateList.size()>0){
			   rthRtoManifestCommonDAO.saveOrUpdateConsgRtoRate(consignmentBillingRateList);
		   }
		}
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			LOGGER.error("RthRtoManifestParcelServiceImpl::saveOrUpdateRthRtoManifestParcel::Error",e);
		}

		
/*		if(!StringUtil.isNull(manifestDO)){
			manifestProcessDO=RthRtoManifestParcelConverter.rthRtoManifestProcessParcelDomainconverter(rthRtoManifestTO);
			manifestProcessDO = rthRtoManifestCommonDAO.saveOrUpdateManifestProcess(manifestProcessDO);
			if(!StringUtil.isNull(manifestProcessDO)){
				String transMsg = CommonConstants.EMPTY_STRING;
				transMsg = ManifestErrorCodesConstants.MANIFEST_SAVED_SUCCESSFULLY;
				if (StringUtils.isNotEmpty(transMsg)) {
					ResourceBundle errorMessages = ResourceBundle
							.getBundle(FrameworkConstants.ERROR_MSG_PROP_FILE_NAME);
					rthRtoManifestTO.setTransMsg(errorMessages.getString(transMsg));
				}				
			}else{
				throw new CGBusinessException(ManifestErrorCodesConstants.MANIFEST_NOT_SAVED);
			}				
		}else{
			throw new CGBusinessException(ManifestErrorCodesConstants.MANIFEST_NOT_SAVED);
		}*/

		LOGGER.debug("RthRtoManifestParcelServiceImpl::saveOrUpdateRthRtoManifestParcel::END------------>:::::::");
		return rthRtoManifestTO;
	}
	
	private void  prepareConsgRateCalcDomain(ConsignmentRateCalculationOutputTO consignmentRateCalculationOutputTO,ConsignmentBillingRateDO consignmentBillingRateDO) throws CGBusinessException, CGSystemException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		LOGGER.debug("BillingCommonServiceImpl::prepareConsgRateCalcDomainConverter::START------------>:::::::");
		 if(!StringUtil.isNull(consignmentRateCalculationOutputTO)){
			 UserTO userTO=udaanContextService.getUserInfoTO().getUserto();
			 if(!StringUtil.isNull(userTO)){
				 consignmentBillingRateDO.setUpdatedBy(userTO.getUserId());
				 consignmentBillingRateDO.setCreatedBy(userTO.getUserId());
			 }
			 PropertyUtils.copyProperties(consignmentBillingRateDO, consignmentRateCalculationOutputTO);
			 consignmentBillingRateDO.setUpdatedDate(DateUtil.getCurrentDate());
			 consignmentBillingRateDO.setCreatedDate(DateUtil.getCurrentDate());
			
		 }
		
		
		LOGGER.debug("BillingCommonServiceImpl::prepareConsgRateCalcDomainConverter::END------------>:::::::");
	}
}
