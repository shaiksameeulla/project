package com.ff.web.manifest.rthrto.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.geography.CityTO;
import com.ff.manifest.rthrto.RthRtoDetailsTO;
import com.ff.manifest.rthrto.RthRtoManifestTO;
import com.ff.to.serviceofferings.ReasonTO;
import com.ff.tracking.ProcessTO;
import com.ff.universe.consignment.dao.ConsignmentCommonDAO;
import com.ff.web.manifest.rthrto.service.RthRtoManifestCommonService;
import com.ff.web.manifest.rthrto.utils.RthRtoManifestUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class RthRtoManifestParcelConverter.
 */
public class RthRtoManifestParcelConverter {
	/** The Logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(RthRtoManifestParcelConverter.class);
	
	/** The rth rto manifest common service. */
	private static RthRtoManifestCommonService rthRtoManifestCommonService;
	
	/** The consignment common dao. */
	private static ConsignmentCommonDAO consignmentCommonDAO;
	
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
	 * Sets the consignment common dao.
	 *
	 * @param consignmentCommonDAO the new consignment common dao
	 */
	public static void setConsignmentCommonDAO(
			ConsignmentCommonDAO consignmentCommonDAO) {
		RthRtoManifestParcelConverter.consignmentCommonDAO = consignmentCommonDAO;
	}

	/**
	 * Rth rto detail list converter.
	 *
	 * @param rthRtoManifestTO the rth rto manifest to
	 * @return the list
	 */
	public static List<RthRtoDetailsTO> rthRtoDetailListConverter(RthRtoManifestTO rthRtoManifestTO){
		LOGGER.trace("RthRtoManifestParcelConverter::rthRtoDetailListConverter()::START");
		List<RthRtoDetailsTO> rthRtoDetailsTOs=null;
		if(!StringUtil.isEmpty(rthRtoManifestTO.getConsgNumbers())){
			int noOfRows=rthRtoManifestTO.getConsgNumbers().length;
			rthRtoDetailsTOs=new ArrayList<>(noOfRows);
			
			for(int cnt=0;cnt<noOfRows;cnt++){
				RthRtoDetailsTO rthRtoDetailsTO=new RthRtoDetailsTO();
				rthRtoDetailsTO.setConsgNumber(rthRtoManifestTO.getConsgNumbers()[cnt]);
				rthRtoDetailsTO.setConsignmentId(rthRtoManifestTO.getConsignmentIds()[cnt]);
				
				/* Added by HIMAL set consg. status */
				String consgStatus = AbstractRthRtoManifestConverter
						.getConsgStatus(rthRtoManifestTO.getManifestType());
				rthRtoDetailsTO.setConsgStatus(consgStatus);
				
				rthRtoDetailsTO.setConsignmentManifestId(rthRtoManifestTO.getConsignmentManifestIds()[cnt]);
				rthRtoDetailsTO.setReceivedDate(rthRtoManifestTO.getReceivedDate()[cnt]);
				ReasonTO reasonTO=new ReasonTO();
				reasonTO.setReasonId(rthRtoManifestTO.getReasonIds()[cnt]);
				rthRtoDetailsTO.setReasonTO(reasonTO);
				rthRtoDetailsTO.setRemarks(rthRtoManifestTO.getRemarks()[cnt]);				
				rthRtoDetailsTO.setActualWeight(rthRtoManifestTO.getCnWeights()[cnt]);
				rthRtoDetailsTOs.add(rthRtoDetailsTO);
			}			
		}
		LOGGER.trace("RthRtoManifestParcelConverter::rthRtoDetailListConverter()::END");
		return rthRtoDetailsTOs;
	}
	
	/**
	 * Rth rto manifest parcel domainconverter.
	 *
	 * @param rthRtoManifestTO the rth rto manifest to
	 * @return the manifest do
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public static ManifestDO rthRtoManifestParcelDomainconverter(RthRtoManifestTO 
			rthRtoManifestTO) throws CGBusinessException, CGSystemException {
		LOGGER.trace("RthRtoManifestParcelConverter::rthRtoManifestParcelDomainconverter()::START");
		ManifestDO manifestDO=null;
		
		// Added by Himal setting Destination City
		if (!StringUtil.isNull(rthRtoManifestTO.getDestOfficeTO())
				&& !StringUtil.isEmptyInteger(rthRtoManifestTO
						.getDestOfficeTO().getOfficeId())) {
			CityTO destCityTO = rthRtoManifestCommonService
					.getCityByOfficeId(rthRtoManifestTO.getDestOfficeTO()
							.getOfficeId());
			rthRtoManifestTO.setDestCityTO(destCityTO);
		}
		
		manifestDO = AbstractRthRtoManifestConverter.rthRtoManifestDomainconverter(rthRtoManifestTO);
		
		ProcessDO processDO = processConveter(CommonConstants.PROCESS_RTO_RTH);
		manifestDO.setUpdatingProcess(processDO);
		manifestDO.setManifestProcessCode(CommonConstants.PROCESS_RTO_RTH);		
		
		List<RthRtoDetailsTO> rthRtoDetailsTOs=rthRtoManifestTO.getRthRtoDetailsTOs(); 
		Set<ConsignmentDO> consignmentDOsSet = RthRtoManifestUtils.getAndSetRTOHedConsignments(consignmentCommonDAO,rthRtoDetailsTOs,processDO);
		manifestDO.setConsignments(consignmentDOsSet);
		manifestDO.setNoOfElements(consignmentDOsSet.size());
		
		// Added by Himal set manifest weight
		double manifestWeight = AbstractRthRtoManifestConverter
				.setManifestWt(consignmentDOsSet);
		manifestDO.setManifestWeight(manifestWeight);
		rthRtoManifestTO.setManifestWeight(manifestWeight);

		RthRtoManifestUtils.setOperatingOffInConsgs(manifestDO);
		
		LOGGER.trace("RthRtoManifestParcelConverter::rthRtoManifestParcelDomainconverter()::END");
		return manifestDO;
	}
	
	/**
	 * Rth rto manifest process parcel domainconverter.
	 *
	 * @param rthRtoManifestTO the rth rto manifest to
	 * @return the manifest process do
	 * @throws CGBusinessException the cG business exception
	 */
	/*public static ManifestProcessDO rthRtoManifestProcessParcelDomainconverter(RthRtoManifestTO rthRtoManifestTO) throws CGBusinessException{
		LOGGER.trace("RthRtoManifestParcelConverter::rthRtoManifestProcessParcelDomainconverter()::START");
		ManifestProcessDO manifestProcessDO=null;
		manifestProcessDO = AbstractRthRtoManifestConverter.rthRtoManifestProcessDomainconverter(rthRtoManifestTO);
		manifestProcessDO.setContainsOnlyCoMail(CommonConstants.NO);
		LOGGER.trace("RthRtoManifestParcelConverter::rthRtoManifestProcessParcelDomainconverter()::END");
		return manifestProcessDO;
	}*/
	
	/**
	 * Process conveter.
	 *
	 * @param processCode the process code
	 * @return the process do
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public static ProcessDO processConveter(String processCode) throws CGSystemException, CGBusinessException{
		LOGGER.trace("RthRtoManifestParcelConverter::processConveter()::START");
		ProcessDO processDO=new ProcessDO();
		ProcessTO processTO = new ProcessTO();
		processTO.setProcessCode(processCode);
		processTO = rthRtoManifestCommonService.getProcess(processTO);
		processDO=(ProcessDO) CGObjectConverter.createDomainFromTo(processTO, processDO);
		LOGGER.trace("RthRtoManifestParcelConverter::processConveter()::END");
		return processDO;
	}
}
