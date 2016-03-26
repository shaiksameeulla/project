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
import com.ff.manifest.rthrto.RthRtoManifestDoxTO;
import com.ff.to.serviceofferings.ReasonTO;
import com.ff.tracking.ProcessTO;
import com.ff.universe.consignment.dao.ConsignmentCommonDAO;
import com.ff.web.manifest.rthrto.service.RthRtoManifestCommonService;
import com.ff.web.manifest.rthrto.utils.RthRtoManifestUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class RthRtoManifestDoxConverter.
 */
public class RthRtoManifestDoxConverter {

	/** The Logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(RthRtoManifestDoxConverter.class);

	/** The rthRtoManifestCommonService. */
	private static RthRtoManifestCommonService rthRtoManifestCommonService;

	/** The consignment common dao. */
	private static ConsignmentCommonDAO consignmentCommonDAO;

	/**
	 * Sets the rth rto manifest common service.
	 * 
	 * @param rthRtoManifestCommonService
	 *            the rthRtoManifestCommonService to set
	 */
	@SuppressWarnings("static-access")
	public void setRthRtoManifestCommonService(
			RthRtoManifestCommonService rthRtoManifestCommonService) {
		this.rthRtoManifestCommonService = rthRtoManifestCommonService;
	}

	/**
	 * Sets the consignment common dao.
	 * 
	 * @param consignmentCommonDAO
	 *            the new consignment common dao
	 */
	public static void setConsignmentCommonDAO(
			ConsignmentCommonDAO consignmentCommonDAO) {
		RthRtoManifestDoxConverter.consignmentCommonDAO = consignmentCommonDAO;
	}

	/**
	 * To convert RthRtoManifestDoxTO to RthRtoDetailsTO and prepare List.
	 * 
	 * @param rthRtoManifestDoxTO
	 *            the rth rto manifest dox to
	 * @return rthRtoDetailsTOs
	 */
	public static List<RthRtoDetailsTO> rthRtoDetailListConverter(
			RthRtoManifestDoxTO rthRtoManifestDoxTO) {
		LOGGER.trace("RthRtoManifestDoxConverter::rthRtoDetailListConverter()::START");
		List<RthRtoDetailsTO> rthRtoDetailsTOs = null;
		if (!StringUtil.isEmpty(rthRtoManifestDoxTO.getConsgNumbers())) {
			int noOfRows = rthRtoManifestDoxTO.getConsgNumbers().length;
			rthRtoDetailsTOs = new ArrayList<>(noOfRows);
			for (int cnt = 0; cnt < noOfRows; cnt++) {
				if (!StringUtil.isStringEmpty(rthRtoManifestDoxTO
						.getConsgNumbers()[cnt])) {
					RthRtoDetailsTO rthRtoDetailsTO = new RthRtoDetailsTO();
					rthRtoDetailsTO.setConsgNumber(rthRtoManifestDoxTO
							.getConsgNumbers()[cnt]);
					rthRtoDetailsTO.setConsignmentId(rthRtoManifestDoxTO
							.getConsignmentIds()[cnt]);
					
					/* Added by HIMAL set consg. status */
					String consgStatus = AbstractRthRtoManifestConverter
							.getConsgStatus(rthRtoManifestDoxTO.getManifestType());
					rthRtoDetailsTO.setConsgStatus(consgStatus);
					
					/*
					 * rthRtoDetailsTO.setConsignmentManifestId(rthRtoManifestDoxTO
					 * .getConsignmentManifestIds()[cnt]);
					 */
					rthRtoDetailsTO.setActualWeight(rthRtoManifestDoxTO
							.getCnWeights()[cnt]);
					rthRtoDetailsTO.setReceivedDate(rthRtoManifestDoxTO
							.getReceivedDate()[cnt]);
					ReasonTO reasonTO = new ReasonTO();
					reasonTO.setReasonId(rthRtoManifestDoxTO.getReasonIds()[cnt]);
					rthRtoDetailsTO.setReasonTO(reasonTO);
					rthRtoDetailsTO
							.setRemarks(rthRtoManifestDoxTO.getRemarks()[cnt]);
					/*
					 * rthRtoDetailsTO.setPosition(rthRtoManifestDoxTO.getPosition
					 * ()[cnt]);
					 */
					rthRtoDetailsTOs.add(rthRtoDetailsTO);
				}
			}
		}
		LOGGER.trace("RthRtoManifestDoxConverter::rthRtoDetailListConverter()::END");
		return rthRtoDetailsTOs;
	}

	/**
	 * To convert RthRtoManifestDoxTO to ManifestDO.
	 * 
	 * @param rthRtoManifestDoxTO
	 *            the rth rto manifest dox to
	 * @return manifestDO
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static ManifestDO rthRtoManifestDoxDomainConverter(
			RthRtoManifestDoxTO rthRtoManifestDoxTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("RthRtoManifestDoxConverter::rthRtoManifestDoxDomainConverter()::START");
		ManifestDO manifestDO = null;
		
		// Added by Himal setting Destination City
		if (!StringUtil.isNull(rthRtoManifestDoxTO.getDestOfficeTO())
				&& !StringUtil.isEmptyInteger(rthRtoManifestDoxTO
						.getDestOfficeTO().getOfficeId())) {
			CityTO destCityTO = rthRtoManifestCommonService
					.getCityByOfficeId(rthRtoManifestDoxTO.getDestOfficeTO()
							.getOfficeId());
			rthRtoManifestDoxTO.setDestCityTO(destCityTO);
		}
		
		manifestDO = AbstractRthRtoManifestConverter
				.rthRtoManifestDomainconverter(rthRtoManifestDoxTO);
		manifestDO.setContainsOnlyCoMail(CommonConstants.NO);
		ProcessDO processDO = processConveter(CommonConstants.PROCESS_RTO_RTH);
		manifestDO.setUpdatingProcess(processDO);
		manifestDO.setManifestProcessCode(CommonConstants.PROCESS_RTO_RTH);

		// Added by shahnaz according to new design changes
		List<RthRtoDetailsTO> rthRtoDetailsTOs = rthRtoManifestDoxTO
				.getRthRtoDetailsTOs();
		Set<ConsignmentDO> consignmentDOsSet = RthRtoManifestUtils
				.getAndSetRTOHedConsignments(consignmentCommonDAO,
						rthRtoDetailsTOs, processDO);
		manifestDO.setConsignments(consignmentDOsSet);
		manifestDO.setNoOfElements(consignmentDOsSet.size());

		// Added by Himal set manifest weight
		double manifestWeight = AbstractRthRtoManifestConverter
				.setManifestWt(consignmentDOsSet);
		manifestDO.setManifestWeight(manifestWeight);
		rthRtoManifestDoxTO.setManifestWeight(manifestWeight);

		RthRtoManifestUtils.setOperatingOffInConsgs(manifestDO);
		LOGGER.trace("RthRtoManifestDoxConverter::rthRtoManifestDoxDomainConverter()::END");
		return manifestDO;
	}

	/**
	 * To convert RthRtoManifestDoxTO to ManifestProcessDO.
	 * 
	 * @param rthRtoManifestDoxTO
	 *            the rth rto manifest dox to
	 * @return manifestProcessDO
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	/*public static ManifestProcessDO rthRtoManifestProcessDoxDomainConverter(
			RthRtoManifestDoxTO rthRtoManifestDoxTO) throws CGBusinessException {
		LOGGER.trace("RthRtoManifestDoxConverter::rthRtoManifestProcessDoxDomainConverter()::START");
		ManifestProcessDO manifestProcessDO = null;
		manifestProcessDO = AbstractRthRtoManifestConverter
				.rthRtoManifestProcessDomainconverter(rthRtoManifestDoxTO);
		manifestProcessDO.setContainsOnlyCoMail(CommonConstants.NO);
		manifestProcessDO
				.setManifestProcessCode(CommonConstants.PROCESS_RTO_RTH);
		LOGGER.trace("RthRtoManifestDoxConverter::rthRtoManifestProcessDoxDomainConverter()::END");
		return manifestProcessDO;
	}*/

	/**
	 * To processCode to ProcessDO.
	 * 
	 * @param processCode
	 *            the process code
	 * @return processDO
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public static ProcessDO processConveter(String processCode)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("RthRtoManifestDoxConverter::processConveter()::START");
		ProcessDO processDO = new ProcessDO();
		ProcessTO processTO = new ProcessTO();
		processTO.setProcessCode(processCode);
		processTO = rthRtoManifestCommonService.getProcess(processTO);
		processDO = (ProcessDO) CGObjectConverter.createDomainFromTo(processTO,
				processDO);
		LOGGER.trace("RthRtoManifestDoxConverter::processConveter()::END");
		return processDO;
	}

}
