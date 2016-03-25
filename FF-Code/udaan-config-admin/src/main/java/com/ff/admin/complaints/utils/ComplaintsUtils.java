package com.ff.admin.complaints.utils;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.bs.sequence.SequenceGeneratorService;
import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;

public class ComplaintsUtils {

	private final static Logger LOGGER = LoggerFactory.getLogger(ComplaintsUtils.class);
	private static SequenceGeneratorService sequenceGeneratorService;
	
	public static void setSequenceGeneratorService(
			SequenceGeneratorService sequenceGeneratorService) {
		ComplaintsUtils.sequenceGeneratorService = sequenceGeneratorService;
	}
	
	public static void setUpdateFlag4DBSync(CGBaseDO cgBaseDO) {
		cgBaseDO.setDtUpdateToCentral(CommonConstants.YES);
		cgBaseDO.setDtToCentral(CommonConstants.NO);
	}
	
	public static String generateRefNumber(String startCode, String officeCode, String processRequestingCode) 
			throws CGBusinessException, CGSystemException {	
		LOGGER.debug("ComplaintsUtils :: generateRefNumber() :: Start --------> ::::::");
		String generatedNumber = null;
		String runningNumber = null;
		SequenceGeneratorConfigTO sequenceGeneratorConfigTO = new SequenceGeneratorConfigTO();
		sequenceGeneratorConfigTO.setProcessRequesting(processRequestingCode);
		sequenceGeneratorConfigTO.setNoOfSequencesToBegenerated(1);
		sequenceGeneratorConfigTO.setRequestDate(new Date());
		sequenceGeneratorConfigTO = sequenceGeneratorService.getGeneratedSequence(sequenceGeneratorConfigTO);
		sequenceGeneratorConfigTO.getGeneratedSequences();
		
		if(sequenceGeneratorConfigTO.getGeneratedSequences()!=null && 
				sequenceGeneratorConfigTO.getGeneratedSequences().size()>0){
			runningNumber = sequenceGeneratorConfigTO.getGeneratedSequences().get(0);
		}
		
		generatedNumber = startCode + officeCode + runningNumber;
		LOGGER.debug("ComplaintsUtils :: generateRefNumber() :: End --------> ::::::");
		return generatedNumber;
	}

	
}
