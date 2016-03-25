/**
 * 
 */
package com.capgemini.lbs.framework.bs.sequence;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.dao.sequence.SequenceGeneratorDAO;
import com.capgemini.lbs.framework.domain.SequenceGeneratorConfigDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;

/**
 * @author mohammes
 *
 */
public class SequenceGeneratorServiceImpl implements SequenceGeneratorService {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(SequenceGeneratorServiceImpl.class);
	public SequenceGeneratorDAO sequenceGeneratorDAO;
	/**
	 * Input :SequenceGeneratorConfigTO
	 * Purpose: generate Running Sequence number for requested process
	 */
	@Override
	public synchronized SequenceGeneratorConfigTO getGeneratedSequence(SequenceGeneratorConfigTO	inputTO)
			throws CGBusinessException, CGSystemException {
		//SequenceGeneratorConfigTO	inputTO = prepareConfigTO();
		SequenceGeneratorConfigDO seqGeneratorDo=null;
		Integer seqLength=null;
		Integer incrementValue=null;
		Long lastGeneratedSeqNum=0l;
		Integer initialValue=1;
		
		List<String> generatedSeqList=null;
		Integer noOfSequences= inputTO.getNoOfSequencesToBegenerated();
		
		if(isValidInput(inputTO)){
			seqGeneratorDo= sequenceGeneratorDAO.getSequenceDO(inputTO.getProcessRequesting());
			//BusinessLogic
			if(seqGeneratorDo!=null){
				seqLength = seqGeneratorDo.getSequenceLength();
				initialValue= StringUtil.isEmptyInteger(seqGeneratorDo.getInitialValue())? initialValue : seqGeneratorDo.getInitialValue();
				incrementValue= seqGeneratorDo.getIncrementValue();
				if(seqGeneratorDo.getReInitializeRequired().equalsIgnoreCase(FrameworkConstants.ENUM_YES)){

					switch ( seqGeneratorDo.getReInitInterval()){
					
							case FrameworkConstants.ENUM_DAY : //(for day)
								if(DateUtil.equalsDate(inputTO.getRequestDate(),seqGeneratorDo.getLastGeneratedDate())){
									lastGeneratedSeqNum = seqGeneratorDo.getLastGeneratedNumber();
								}else{
									lastGeneratedSeqNum = initialValue.longValue();
								}
								break;
							case FrameworkConstants.ENUM_MONTH : //(for month)
								if(DateUtil.equalsMonths(inputTO.getRequestDate(),seqGeneratorDo.getLastGeneratedDate())){
									lastGeneratedSeqNum = seqGeneratorDo.getLastGeneratedNumber();
								}else{
									lastGeneratedSeqNum = initialValue.longValue();
								}
								break;
							case FrameworkConstants.ENUM_YEAR : //(for year)
								if(DateUtil.equalsMonths(inputTO.getRequestDate(),seqGeneratorDo.getLastGeneratedDate())){
									lastGeneratedSeqNum = seqGeneratorDo.getLastGeneratedNumber();
								}else{
									lastGeneratedSeqNum = initialValue.longValue();
								}
									
					}

				}else{
					lastGeneratedSeqNum = seqGeneratorDo.getLastGeneratedNumber();
				}
				generatedSeqList =  new ArrayList<String>(noOfSequences);
				for(int counter=1;counter<=noOfSequences;counter++){
					lastGeneratedSeqNum +=incrementValue;
					generatedSeqList.add(sequencePadding(lastGeneratedSeqNum,seqLength));

				}
			}
			
			
		}else{
			LOGGER.debug("SequenceGeneratorServiceImpl::getGeneratedSequence--Throwing Business Exception");
			throw new CGBusinessException("Improper inputs");
		}
		
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("SequenceGeneratorServiceImpl::getGeneratedSequence--Generated sequence "+(!StringUtil.isEmptyList(generatedSeqList)?("Size :["+generatedSeqList.size()+"] seq:"+generatedSeqList.toString()):"EMPTY"));
		}
		
		inputTO.setGeneratedSequences(generatedSeqList);
		if(seqGeneratorDo!=null && !StringUtil.isEmptyList(generatedSeqList)){
			sequenceGeneratorDAO.updateGeneratedSequenceById(seqGeneratorDo.getSequenceGeneratorId(),lastGeneratedSeqNum);
		}else{
			throw new CGBusinessException(FrameworkConstants.SEQUENCE_NUMBER_NOT_GENERATED);
		}
		
		return inputTO;
	}
	//Added by shaheed
	@Override
	public synchronized SequenceGeneratorConfigTO getCollectionSequence(SequenceGeneratorConfigTO	inputTO)
			throws CGBusinessException, CGSystemException {
		String queryName=null;
		
		
		if(!StringUtil.isStringEmpty(inputTO.getProcessRequesting()) && !StringUtil.isEmptyInteger(inputTO.getNoOfSequencesToBegenerated()) && !StringUtil.isEmptyInteger(inputTO.getRequestingBranchId()) && !StringUtil.isEmptyInteger(inputTO.getSequenceRunningLength())){
			switch ( inputTO.getProcessRequesting()){
			case CommonConstants.TX_CODE_BC :
				queryName="getMaxCollectionNumber";
				break;
			case CommonConstants.TX_CODE_CC :
				queryName="getMaxCollectionNumber";
				break;
			case CommonConstants.TX_CODE_EX :
				queryName="getMaxExpenseNumber";
				break;
			case CommonConstants.TX_CODE_LP :
				queryName="getMaxLiabilityNumber";
				break;
			default :
				throw new CGBusinessException(FrameworkConstants.SEQUENCE_NUMBER_NOT_GENERATED);
			}
		}else{
			throw new CGBusinessException(FrameworkConstants.SEQUENCE_NUMBER_NOT_GENERATED);
		}
		
		getGeneratedNumber(inputTO, queryName);
		
		return inputTO;
	}
	
	
	//Code ends here-shaheed
	
	
	
	
	/**
	 * 
	 * @param inputTo
	 * @return
	 */
private Boolean isValidInput(SequenceGeneratorConfigTO inputTo){
	
	if(inputTo != null && !StringUtil.isStringEmpty(inputTo.getProcessRequesting())&& inputTo.getRequestDate()!=null){
		return Boolean.TRUE;
	}
	return Boolean.FALSE;
}
/**
 * 
 * @param number
 * @param length
 * @return
 */

private String sequencePadding(Long number,Integer length){
		String format=null;
		format="%0"+length+"d";
		return String.format(format, number);  
}
/**
 * @return
 */
public SequenceGeneratorDAO getSequenceGeneratorDAO() {
	return sequenceGeneratorDAO;
}
/**
 * @param sequenceGeneratorDAO
 */
public void setSequenceGeneratorDAO(SequenceGeneratorDAO sequenceGeneratorDAO) {
	this.sequenceGeneratorDAO = sequenceGeneratorDAO;
}
private void getGeneratedNumber(SequenceGeneratorConfigTO to,
		String qryName) throws CGSystemException {
	String number = null;
	Long result = 0l;
	Integer length =0;
	length = to.getPrefixCode().length();
	to.setLengthOfNumber(length+to.getSequenceRunningLength());

	number = sequenceGeneratorDAO.getMaxNumberFromProcess(to, qryName);
	if (!StringUtil.isStringEmpty(number)) {
		String res = number.substring(length);

		result = StringUtil.parseLong(res);
		if (StringUtil.isNull(result)) {
			result = 0l;
		} else if (result >= 0l) {
			result += 1;
		}

	} else {
		result = 0l;
		LOGGER.info("StockCommonServiceImpl::getGeneratedNumber---Max number does not exist for Request"
				+ to.getProcessRequesting());
	}
	List<String> generatedSequences= new ArrayList<String>(to.getNoOfSequencesToBegenerated());
	for(int counter=0;counter<to.getNoOfSequencesToBegenerated();counter++){
		generatedSequences.add(to.getPrefixCode().trim()
				+ sequencePadding(result+counter, to.getSequenceRunningLength()));
	}
	to.setGeneratedSequences(generatedSequences);
}
}
