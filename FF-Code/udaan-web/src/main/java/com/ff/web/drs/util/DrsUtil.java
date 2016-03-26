/**
 * 
 */
package com.ff.web.drs.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.exception.MessageType;
import com.capgemini.lbs.framework.exception.MessageWrapper;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.delivery.DeliveryDO;
import com.ff.domain.delivery.DeliveryNavigatorDO;
import com.ff.to.drs.AbstractDeliveryTO;
import com.ff.to.drs.DeliveryTO;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.web.drs.common.constants.DrsCommonConstants;
import com.ff.web.drs.common.constants.DrsConstants;
import com.ff.web.drs.common.service.DeliveryCommonService;
import com.ff.web.util.UdaanWebErrorConstants;

/**
 * @author mohammes
 *
 */
public final class DrsUtil {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(DrsUtil.class);
	static ResourceBundle navigatorMesgs = null;

	static{
		LOGGER.debug("DrsUtil ::: Executing static Block");
		loadProperties();
	}

	/**
	 * 
	 */
	private static void loadProperties() {
		navigatorMesgs = ResourceBundle
				.getBundle(DrsCommonConstants.DRS_MSG_PROP_FILE_NAME);
		LOGGER.debug("DrsUtil :::loadProperties  ::properties Status ::-->"+navigatorMesgs!=null?"Loaded":"not loaded");
	}

	/**
	 * @return the errorMessages
	 */
	public static ResourceBundle getErrorMessages() {
		if(navigatorMesgs ==null){
			loadProperties();
		}
		return navigatorMesgs;
	}
	
	
	final public static String getDrsNumber(AbstractDeliveryTO deliveryTO ,DeliveryCommonService deliveryCommonService) throws CGSystemException, CGBusinessException{
		LOGGER.debug("DrsUtil::getDrsNumber##### START");
		String drsNumber = StringUtil.generateDDMMYYHHMMSSRamdomNumber();
		 SequenceGeneratorConfigTO configTO = getSequenceGenerator(deliveryTO);
		 configTO.setProcessRequesting(deliveryTO.getYpDrs());
		 configTO.setSequenceRunningLength(DrsConstants.DRS_RUNNING_NUMBER_LENGTH);
		 configTO.setLengthOfNumber(DrsConstants.DRS_NUMBER_LENGTH);
		 drsNumber= deliveryCommonService.drsProcessNumberGenerator(configTO);
		 LOGGER.debug("DrsUtil::getDrsNumber:::Generated Number "+drsNumber);
		 LOGGER.debug("DrsUtil::getDrsNumber##### END");
	 return drsNumber;
	 }
	
	final public static String getManualDrsNumber(AbstractDeliveryTO deliveryTO ,DeliveryCommonService deliveryCommonService) throws CGSystemException, CGBusinessException{
		LOGGER.debug("DrsUtil::getDrsNumber##### START");
		String drsNumber = StringUtil.generateDDMMYYHHMMSSRamdomNumber();
		 SequenceGeneratorConfigTO configTO = getSequenceGenerator(deliveryTO);
		 configTO.setProcessRequesting(DrsConstants.M_DRS_YES);
		 configTO.setLengthOfNumber(DrsConstants.M_DRS_NUMBER_LENGTH);
		 configTO.setSequenceRunningLength(DrsConstants.M_DRS_NUMBER_LENGTH-5);
		 drsNumber= deliveryCommonService.drsProcessNumberGenerator(configTO);
		 LOGGER.debug("DrsUtil::getDrsNumber:::Generated Number "+drsNumber);
		 LOGGER.debug("DrsUtil::getDrsNumber##### END");
	 return drsNumber;
	 }
	final public static String getBulkDrsNumber(AbstractDeliveryTO deliveryTO ,DeliveryCommonService deliveryCommonService) throws CGSystemException, CGBusinessException{
		LOGGER.debug("DrsUtil::getBulkDrsNumber##### START");
		String drsNumber = StringUtil.generateDDMMYYHHMMSSRamdomNumber();
		 SequenceGeneratorConfigTO configTO = getSequenceGenerator(deliveryTO);
		 if(!StringUtil.isStringEmpty(deliveryTO.getYpDrs()) && deliveryTO.getYpDrs().equalsIgnoreCase(DrsConstants.YP_DRS_YES) ){
			 configTO.setPrefixCode(DrsConstants.YP_DRS_YES);
		 }
		 configTO.setProcessRequesting(DrsConstants.BULK_DRS_TYPE);
		 drsNumber= deliveryCommonService.drsProcessNumberGenerator(configTO);
		 LOGGER.debug("DrsUtil::getBulkDrsNumber:::Generated Number "+drsNumber);
		 LOGGER.debug("DrsUtil::getBulkDrsNumber##### END");
	 return drsNumber;
	 }

	/**
	 * @param deliveryTO
	 * @return
	 */
	public static SequenceGeneratorConfigTO getSequenceGenerator(
			AbstractDeliveryTO deliveryTO) {
		SequenceGeneratorConfigTO configTO= new SequenceGeneratorConfigTO();
		 configTO.setRequestingBranchCode(deliveryTO.getLoginOfficeCode());
		 configTO.setRequestingBranchId(deliveryTO.getLoginOfficeId());
		return configTO;
	}

	/**
	 * @throws CGBusinessException
	 */
	public static void prepareBusinessException(String key,String[] placeHolders) throws CGBusinessException {
		MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(key, MessageType.Warning,DrsCommonConstants.DRS,placeHolders);
		throw new CGBusinessException(msgWrapper,key);
	}
	/**
	 * @throws CGBusinessException
	 */
	public static void prepareBusinessException(String key) throws CGBusinessException {
		MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(key, MessageType.Warning);
		throw new CGBusinessException(msgWrapper,key);
	}
	
	public static void prepareDrsNavigator(DeliveryDO drsDo){
		List<DeliveryNavigatorDO> doList= new ArrayList<>(2);
		DeliveryNavigatorDO nvaigatorDo1=null;
		nvaigatorDo1= prepareNavigatorDO(drsDo,DrsConstants.LIST_DRS_SCREEN_CODE,navigatorMesgs.getString(drsDo.getDrsScreenCode()+DrsConstants.DRS_TYPE_PREPARATION));
		doList.add(nvaigatorDo1);
		DeliveryNavigatorDO nvaigatorDo2=null;
		nvaigatorDo2= prepareNavigatorDO(drsDo,DrsConstants.PENDING_DRS_SCREEN_CODE,navigatorMesgs.getString(drsDo.getDrsScreenCode()+DrsConstants.DRS_TYPE_UPDATE));
		doList.add(nvaigatorDo2);
		drsDo.setNavigatorList(doList);
	}
	
	public static String prepareDrsNavigatorForManualDrs(DeliveryDO drsDo){
		String updateDlvlink=navigatorMesgs.getString(drsDo.getDrsScreenCode());
		List<DeliveryNavigatorDO> doList= new ArrayList<>(2);
		DeliveryNavigatorDO nvaigatorDo1=null;
		nvaigatorDo1= prepareNavigatorDO(drsDo,DrsConstants.LIST_DRS_SCREEN_CODE,updateDlvlink);
		doList.add(nvaigatorDo1);
		DeliveryNavigatorDO nvaigatorDo2=null;
		nvaigatorDo2= prepareNavigatorDO(drsDo,DrsConstants.MANUAL_PENDING_DRS_SCREEN_CODE,updateDlvlink);
		doList.add(nvaigatorDo2);
		drsDo.setNavigatorList(doList);
		return updateDlvlink+drsDo.getDrsNumber();
	}

	/**
	 * @param drsDo
	 */
	private static DeliveryNavigatorDO prepareNavigatorDO(DeliveryDO drsDo,String code,String url) {
		DeliveryNavigatorDO nvaigatorDo= new DeliveryNavigatorDO();
		nvaigatorDo.setDrsNumber(drsDo.getDrsNumber());
		nvaigatorDo.setCreatedScreenCode(drsDo.getDrsScreenCode());
		nvaigatorDo.setFromScreenCode(code );
		nvaigatorDo.setNavigateToUrl(url);
	return nvaigatorDo;
	}
	
	
	
	/**
	 * Sets the business exception4 user.
	 *
	 * @param returnTO the return to
	 * @param erroCodeConst the erro code const
	 *  check whether DRS details allowed to update*/
	
	public static void setBusinessException4Modification(DeliveryTO returnTO,String erroCodeConst) {
		setBusinessException4Modification(returnTO,erroCodeConst,null);
	}
	
	/**
	 * Sets the business exception4 modification.
	 *
	 * @param returnTO the return to
	 * @param erroCodeConst the erro code const
	 */
	public static void setBusinessException4Modification(AbstractDeliveryTO returnTO,String erroCodeConst) {
		setBusinessException4Modification(returnTO,erroCodeConst,null);
	}
	
	/**
	 * Sets the business exception4 modification.
	 *
	 * @param returnTO the return to
	 * @param erroCodeConst the erro code const
	 * @param placeHolders the place holders
	 */
	public static void setBusinessException4Modification(DeliveryTO returnTO,String erroCodeConst,String[] placeHolders) {
		MessageWrapper msgWrapper = null;
		returnTO.setCanUpdate(DrsConstants.CAN_UPDATE);
		if(!StringUtil.isEmpty(placeHolders)){
		 msgWrapper = ExceptionUtil.getMessageWrapper(erroCodeConst, MessageType.Warning,DrsCommonConstants.DRS,placeHolders);
		}else{
			 msgWrapper = ExceptionUtil.getMessageWrapper(erroCodeConst, MessageType.Warning);
		}
		returnTO.setBusinessException(new CGBusinessException(msgWrapper));
	}
	
	/**
	 * Sets the business exception4 modification.
	 *
	 * @param returnTO the return to
	 * @param erroCodeConst the erro code const
	 * @param placeHolders the place holders
	 */
	public static void setBusinessException4Modification(AbstractDeliveryTO returnTO,String erroCodeConst,String[] placeHolders) {
		MessageWrapper msgWrapper = null;
		returnTO.setCanUpdate(DrsConstants.CAN_UPDATE);
		if(!StringUtil.isEmpty(placeHolders)){
		 msgWrapper = ExceptionUtil.getMessageWrapper(erroCodeConst, MessageType.Warning,DrsCommonConstants.DRS,placeHolders);
		}else{
			 msgWrapper = ExceptionUtil.getMessageWrapper(erroCodeConst, MessageType.Warning);
		}
		returnTO.setBusinessException(new CGBusinessException(msgWrapper));
	}
	
	

	/**
	 * preProcessForTwoWayWrite
	 * @param deliveryDO
	 */
	public static void preProcessForTwoWayWrite(DeliveryDO deliveryDO){
		/*if(TwoWayWriteProcessCall.isTwoWayWriteEnabled()){
			deliveryDO.setDtToCentral(CommonConstants.YES);
		} else {
			deliveryDO.setDtToCentral(CommonConstants.NO);                  
		}*/
		deliveryDO.setDtToCentral(CommonConstants.NO);  //FIXME
	}
	/**
	 * Prepare delivery header query.
	 *
	 * @param deliveryDO the delivery do
	 * @param session the session
	 * @return the int
	 */
	public static int prepareDeliveryHeaderQuery(DeliveryDO deliveryDO,Session session) {
		
		preProcessForTwoWayWrite(deliveryDO);
		Query qry = session.getNamedQuery(DrsCommonConstants.QRY_UPDATE_DRS_STATUS);
		qry.setLong(DrsCommonConstants.QRY_PARAM_DELIVERY_ID, deliveryDO.getDeliveryId());
		qry.setString(UniversalDeliveryContants.QRY_PARAM_DRS_STATUS,deliveryDO.getDrsStatus());
		qry.setInteger(UniversalDeliveryContants.QRY_PARAM_UPDATED_BY, deliveryDO.getUpdatedBy());
		qry.setTimestamp(UniversalDeliveryContants.QRY_PARAM_FS_IN_TIME, deliveryDO.getFsInTime());
		qry.setString(DrsCommonConstants.QRY_PARAM_DT_TO_CENTRAL, deliveryDO.getDtToCentral());
		return qry.executeUpdate();
	}
	
	/**
	 * @param isComail
	 * @param consgNumber
	 * @return
	 */
	public static boolean isComailNumber(String consgNumber) {
		boolean isComail=false;
		if(!StringUtil.isStringEmpty(consgNumber)){
			if( consgNumber.startsWith(UdaanCommonConstants.SERIES_TYPE_CO_MAIL_NO_PRODUCT)){
				isComail=true;
				LOGGER.debug("DrsUtil :::isComailNumber :: consgNumber :["+consgNumber+"] is Comail");
			}
		}
		return isComail;
	}
	
	/**validateConsignmentType
	 * @param deliveryTO
	 * @param consgNumber
	 * @param consgType
	 * @throws CGBusinessException
	 */
	public static void validateConsignmentType(AbstractDeliveryTO deliveryTO,
			String consgNumber, String consgType) throws CGBusinessException {
		if(StringUtil.isStringEmpty(consgType)){
			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_INVALID_CONSG_NUMBER,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT,consgNumber});
		}else if(!StringUtil.isStringEmpty(consgType)&&!StringUtil.isStringEmpty(deliveryTO.getConsignmentType()) && !consgType.equalsIgnoreCase(deliveryTO.getConsignmentType())){
			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NOT_INVALID_CONSG_TYPE,new String[]{consgNumber});
		}
	}
	
	public static Date calculateDeliveryDate(AbstractDeliveryTO deliveryTO,String dlvTime){
		Date dlvDate=null;
		//String fsOutTmeArray[]=deliveryTO.getFsOutTimeMinStr().split(FrameworkConstants.CHARACTER_COLON);
		String fsOutTmeArray[]={deliveryTO.getFsOutTimeHHStr(),deliveryTO.getFsOutTimeMMStr()};
	//	String fsInTmeArray[]=deliveryTO.getFsInTimeMinStr().split(FrameworkConstants.CHARACTER_COLON);
		String fsInTmeArray[]={deliveryTO.getFsInTimeHHStr(),deliveryTO.getFsInTimeMMStr()};
		//String dlvTimeArray[]=dlvTime.split(FrameworkConstants.CHARACTER_COLON);
		String dlvTimeArray[]=dlvTime.split(FrameworkConstants.CHARACTER_COLON);
		
		try {
			Date FsOutTime= DateUtil.combineDateWithTimeHHMM(deliveryTO.getFsOutTimeDateStr(), deliveryTO.getFsOutTimeHHStr(),deliveryTO.getFsOutTimeMMStr());
			Date FsInTime= DateUtil.combineDateWithTimeHHMM(deliveryTO.getFsInTimeDateStr(), deliveryTO.getFsInTimeHHStr(),deliveryTO.getFsInTimeMMStr());
			
			long noOfdays=DateUtil.DayDifferenceBetweenTwoDates(FsOutTime, FsInTime);
			//set Default Dlv Time
			dlvDate=DateUtil.combineDateWithTimeHHMM(deliveryTO.getFsInTimeDateStr(), dlvTime);
			if(noOfdays==0){
				dlvDate=DateUtil.combineDateWithTimeHHMM(deliveryTO.getFsInTimeDateStr(), dlvTime);
			}else if(noOfdays ==1){
				if(StringUtil.parseInteger(dlvTimeArray[0])<= StringUtil.parseInteger("23") && StringUtil.parseInteger(dlvTimeArray[0]) >= StringUtil.parseInteger(fsOutTmeArray[0])){
					dlvDate=DateUtil.combineDateWithTimeHHMM(deliveryTO.getFsOutTimeDateStr(), dlvTime);
				}else if(StringUtil.parseInteger(dlvTimeArray[0])>=StringUtil.parseInteger("00") && StringUtil.parseInteger(dlvTimeArray[0])<=StringUtil.parseInteger(fsInTmeArray[0])){
					dlvDate=DateUtil.combineDateWithTimeHHMM(deliveryTO.getFsInTimeDateStr(), dlvTime);
				}
				
			}else if(noOfdays >=2){
				if(noOfdays ==2){
					if(StringUtil.parseInteger(dlvTimeArray[0])<= StringUtil.parseInteger(fsInTmeArray[0]) && (StringUtil.parseInteger(dlvTimeArray[0])==StringUtil.parseInteger(fsInTmeArray[0])) && StringUtil.parseInteger(dlvTimeArray[1])<=StringUtil.parseInteger(fsInTmeArray[1]) ){
					dlvDate=DateUtil.combineDateWithTimeHHMM(deliveryTO.getFsInTimeDateStr(), dlvTime);
					}else{
						dlvDate=DateUtil.combineDateWithTimeHHMM(deliveryTO.getFsInTimeDateStr(), dlvTime);
						dlvDate =DateUtil.getPreviousDateFromGivenDate(dlvDate);
					}
				}else{
					dlvDate=DateUtil.combineDateWithTimeHHMM(deliveryTO.getFsInTimeDateStr(), dlvTime);
					dlvDate =DateUtil.getPreviousDateFromGivenDate(dlvDate);
				}
			}
		} catch (Exception e) {
			LOGGER.error("DrsUtil ::calculateDeliveryDate ::Exception :",e);
			dlvDate=DateUtil.combineDateWithTimeHHMM(deliveryTO.getFsInTimeDateStr(), dlvTime);
		}
		
		
		return dlvDate;
	}
	
	
	/*public static Date calculateDeliveryDate(AbstractDeliveryTO deliveryTO,String dlvTimeinHH,String dlvTimeinMM){
		Date dlvDate=null;
		//String fsOutTmeArray[]=deliveryTO.getFsOutTimeMinStr().split(FrameworkConstants.CHARACTER_COLON);
		String fsOutTmeArray[]={deliveryTO.getFsOutTimeHHStr(),deliveryTO.getFsOutTimeMMStr()};
	//	String fsInTmeArray[]=deliveryTO.getFsInTimeMinStr().split(FrameworkConstants.CHARACTER_COLON);
		String fsInTmeArray[]={deliveryTO.getFsInTimeHHStr(),deliveryTO.getFsInTimeMMStr()};
		//String dlvTimeArray[]=dlvTime.split(FrameworkConstants.CHARACTER_COLON);
		//String dlvTimeArray[]=dlvTime.split(FrameworkConstants.CHARACTER_COLON);
		
		try {
			Date FsOutTime= DateUtil.combineDateWithTimeHHMM(deliveryTO.getFsOutTimeDateStr(), deliveryTO.getFsOutTimeHHStr(),deliveryTO.getFsOutTimeMMStr());
			Date FsInTime= DateUtil.combineDateWithTimeHHMM(deliveryTO.getFsInTimeDateStr(), deliveryTO.getFsInTimeHHStr(),deliveryTO.getFsInTimeMMStr());
			
			long noOfdays=DateUtil.DayDifferenceBetweenTwoDates(FsOutTime, FsInTime);
			//set Default Dlv Time
			dlvDate=DateUtil.combineDateWithTimeHHMM(deliveryTO.getFsInTimeDateStr(), dlvTime);
			if(noOfdays==0){
				dlvDate=DateUtil.combineDateWithTimeHHMM(deliveryTO.getFsInTimeDateStr(), dlvTime);
			}else if(noOfdays ==1){
				if(StringUtil.parseInteger(dlvTimeinHH)<= StringUtil.parseInteger("23") && StringUtil.parseInteger(dlvTimeinHH) >= StringUtil.parseInteger(fsOutTmeArray[0])){
					dlvDate=DateUtil.combineDateWithTimeHHMM(deliveryTO.getFsOutTimeDateStr(), dlvTime);
				}else if(StringUtil.parseInteger(dlvTimeinHH)>=StringUtil.parseInteger("00") && StringUtil.parseInteger(dlvTimeinHH)<=StringUtil.parseInteger(fsInTmeArray[0])){
					dlvDate=DateUtil.combineDateWithTimeHHMM(deliveryTO.getFsInTimeDateStr(), dlvTime);
				}
				
			}else if(noOfdays >=2){
				if(noOfdays ==2){
					if(StringUtil.parseInteger(dlvTimeinHH)<= StringUtil.parseInteger(fsInTmeArray[0]) && (StringUtil.parseInteger(dlvTimeinHH)==StringUtil.parseInteger(fsInTmeArray[0])) && StringUtil.parseInteger(dlvTimeinMM)<=StringUtil.parseInteger(fsInTmeArray[1]) ){
					dlvDate=DateUtil.combineDateWithTimeHHMM(deliveryTO.getFsInTimeDateStr(), dlvTime);
					}else{
						dlvDate=DateUtil.combineDateWithTimeHHMM(deliveryTO.getFsInTimeDateStr(), dlvTime);
						dlvDate =DateUtil.getPreviousDateFromGivenDate(dlvDate);
					}
				}else{
					dlvDate=DateUtil.combineDateWithTimeHHMM(deliveryTO.getFsInTimeDateStr(), dlvTime);
					dlvDate =DateUtil.getPreviousDateFromGivenDate(dlvDate);
				}
			}
		} catch (Exception e) {
			LOGGER.error("DrsUtil ::calculateDeliveryDate ::Exception :",e);
			dlvDate=DateUtil.combineDateWithTimeHHMM(deliveryTO.getFsInTimeDateStr(), dlvTime);
		}
		
		
		return dlvDate;
	}*/
	
	
	}
