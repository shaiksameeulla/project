/**
 * 
 */
package com.ff.web.drs.common.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.bs.sequence.SequenceGeneratorService;
import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.utility.TwoWayWriteProcessCall;
import com.ff.business.LoadMovementVendorTO;
import com.ff.consignment.ChildConsignmentTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.delivery.DeliveryDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.delivery.DeliveryNavigatorDO;
import com.ff.geography.CityTO;
import com.ff.manifest.LoadLotTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.PaymentModeTO;
import com.ff.to.drs.AbstractDeliveryTO;
import com.ff.to.drs.DeliveryConsignmentTO;
import com.ff.to.drs.DeliveryDetailsTO;
import com.ff.to.drs.DeliveryNavigatorTO;
import com.ff.to.drs.DeliveryTO;
import com.ff.to.serviceofferings.IdentityProofTypeTO;
import com.ff.to.serviceofferings.ReasonTO;
import com.ff.to.serviceofferings.RelationTO;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.universe.drs.service.DeliveryUniversalService;
import com.ff.universe.stockmanagement.constant.StockUniveralConstants;
import com.ff.universe.stockmanagement.util.StockSeriesGenerator;
import com.ff.universe.util.UniversalConverterUtil;
import com.ff.web.drs.common.constants.DrsCommonConstants;
import com.ff.web.drs.common.constants.DrsConstants;
import com.ff.web.drs.common.dao.DeliveryCommonDAO;
import com.ff.web.drs.preparation.action.DrsCommonPage;
import com.ff.web.drs.preparation.action.DrsCommonPageContent;
import com.ff.web.drs.preparation.action.Page;
import com.ff.web.drs.preparation.action.PageContent;
import com.ff.web.drs.util.DrsConverterUtil;
import com.ff.web.drs.util.DrsUtil;
import com.ff.web.util.UdaanWebErrorConstants;

/**
 * The Class DeliveryCommonServiceImpl.
 *
 * @author mohammes
 */
public class DeliveryCommonServiceImpl implements DeliveryCommonService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(DeliveryCommonServiceImpl.class);

	/** The delivery common dao. */
	private DeliveryCommonDAO deliveryCommonDAO;

	/** The delivery universal service. */
	private DeliveryUniversalService deliveryUniversalService;
	
	private  SequenceGeneratorService sequenceGeneratorService;

	/**
	 * Gets the delivery common dao.
	 *
	 * @return the deliveryCommonDAO
	 */
	public DeliveryCommonDAO getDeliveryCommonDAO() {
		return deliveryCommonDAO;
	}

	/**
	 * Sets the delivery common dao.
	 *
	 * @param deliveryCommonDAO the deliveryCommonDAO to set
	 */
	public void setDeliveryCommonDAO(DeliveryCommonDAO deliveryCommonDAO) {
		this.deliveryCommonDAO = deliveryCommonDAO;
	}

	/**
	 * Gets the delivery universal service.
	 *
	 * @return the deliveryUniversalService
	 */
	public  DeliveryUniversalService getDeliveryUniversalService() {
		return deliveryUniversalService;
	}

	/**
	 * Sets the delivery universal service.
	 *
	 * @param deliveryUniversalService the deliveryUniversalService to set
	 */
	public  void setDeliveryUniversalService(
			DeliveryUniversalService deliveryUniversalService) {
		this.deliveryUniversalService = deliveryUniversalService;
	}

	/**
	 * @return the sequenceGeneratorService
	 */
	public SequenceGeneratorService getSequenceGeneratorService() {
		return sequenceGeneratorService;
	}

	/**
	 * @param sequenceGeneratorService the sequenceGeneratorService to set
	 */
	public void setSequenceGeneratorService(
			SequenceGeneratorService sequenceGeneratorService) {
		this.sequenceGeneratorService = sequenceGeneratorService;
	}

	/* (non-Javadoc)
	 * @see com.ff.web.drs.common.service.DeliveryCommonService#getCityById(java.lang.Integer)
	 */
	@Override
	public CityTO getCityById(Integer cityId) throws CGSystemException,
	CGBusinessException {
		LOGGER.info("DeliveryCommonServiceImpl::getCityById :: Input cityId :"+cityId);
		CityTO inTo= new CityTO();
		inTo.setCityId(cityId);
		return deliveryUniversalService.getCity(inTo);
	}

	/**
	 * getStandardTypesAsMap  :: to get standard type constants based on type name.
	 *
	 * @param typeName the type name
	 * @return Map<String, String>
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public Map<String, String> getStandardTypesAsMap(String typeName)
			throws CGSystemException, CGBusinessException {
		return deliveryUniversalService.getStandardTypesAsMap(typeName);
	}

	/**
	 * getDrsForStdTypes  :: to get standard type constants For type name DRS_FOR.
	 *
	 * @return Map<String, String>
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public Map<String, String> getDrsForStdTypes()
			throws CGSystemException, CGBusinessException {
		return getStandardTypesAsMap(DrsConstants.DRS_FOR_STD_TYPE);
	}

	/**
	 * getYpDrsStdTypes  :: to get standard type constants For type name GLOBAL_BOOLEAN_TYPE.
	 *
	 * @return Map<String, String>
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public Map<String, String> getYpDrsStdTypes()
			throws CGSystemException, CGBusinessException {
		return getStandardTypesAsMap(DrsConstants.YP_DRS_STD_TYPE);
	}

	/**
	 * getDeliveryTypeStdTypes  :: to get standard type constants For type name DELIVERY_TYPE.
	 *
	 * @return Map<String, String>
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public Map<String, String> getDeliveryTypeStdTypes()
			throws CGSystemException, CGBusinessException {
		return getStandardTypesAsMap(DrsConstants.DELIVERY_TYPE_STD_TYPE);
	}

	/**
	 * getSealAndSignStdTypes  :: to get standard type constants For type name DRS_SEAL.
	 *
	 * @return Map<String, String>
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public Map<String, String> getSealAndSignStdTypes()
			throws CGSystemException, CGBusinessException {
		return getStandardTypesAsMap(DrsConstants.DRS_SEAL_STD_TYPE);
	}

	/**
	 * getLoadLotForDRS  :: get Load numberf from Load Lot master
	 *  here we prepare map<Interger,Integer> and both key and value refers to Load number
	 *
	 * @return Map<Integer, Integer>
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public Map<Integer, Integer> getLoadLotForDRS()
			throws CGSystemException, CGBusinessException {
		Map<Integer, Integer> loadNoMap=null;
		List<LoadLotTO>  loadLotList=deliveryUniversalService.getLoadNo();
		if(!CGCollectionUtils.isEmpty(loadLotList)){
			loadNoMap =  new TreeMap<Integer, Integer>();
			for(LoadLotTO loadNo :loadLotList){
				loadNoMap.put(loadNo.getLoadNo(), loadNo.getLoadNo());
			}
		}
		return loadNoMap;
	}

	/**
	 * getPartyTypeDetailsForDRS :: it loads Delivery-Agent details based on DRS-ForType
	 *  ie if DRS-FOR-Type ='FS' FS-Field staff
	 *  then all Employees under logged in office will be populated similarly for Others
	 * @param drsForType
	 * @return Map<Integer, String>
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	@Override
	public Map<Integer, String> getPartyTypeDetailsForDRS(String drsForType,OfficeTO officeTO)throws CGSystemException, CGBusinessException {
		Map<Integer, String> partyTypeDetails=null;

		if(!StringUtil.isStringEmpty(drsForType)){
			switch(drsForType){
			case DrsConstants.DRS_FOR_BA :
				partyTypeDetails=getAllBusinessAssociatesByLoggedInOffice(officeTO);
				break;
			case DrsConstants.DRS_FOR_CO_COURIER :
				partyTypeDetails=getAllCoCourierVendorsByLoggedInOffice(officeTO);
				break;
			case DrsConstants.DRS_FOR_FIELD_STAFF :
				partyTypeDetails=getAllEmployeesByLoggedInOffice(officeTO);
				break;
			case DrsConstants.DRS_FOR_FR :
				partyTypeDetails=getAllFranchiseesByLoggedInOffice(officeTO);
				break;
			default :
				partyTypeDetails=null;
			}
		}

		return partyTypeDetails;
	}
	/**
	 * getAllBusinessAssociatesByLoggedInOffice
	 * @param officeId
	 * @return Map<Integer, String>
	 */
	private Map<Integer, String> getAllBusinessAssociatesByLoggedInOffice(OfficeTO officeTO)throws CGBusinessException, CGSystemException{
		LoadMovementVendorTO vendorTo=new LoadMovementVendorTO();
		vendorTo.setVendorType(UdaanCommonConstants.VENDOR_TYPE_BA);
		OfficeTO offTO= new OfficeTO();
		offTO.setCityId(officeTO.getCityId());
		vendorTo.setOfficeTO(offTO);
		return DrsConverterUtil.prepareVendorMap(deliveryUniversalService.getVendorDtlsForDrs(vendorTo.getVendorType(), officeTO.getOfficeId())) ;
	}

	/**
	 * getAllFranchiseesByLoggedInOffice
	 * @param officeId
	 * @return Map<Integer, String>
	 */
	private Map<Integer, String> getAllFranchiseesByLoggedInOffice(OfficeTO officeTO) throws CGBusinessException, CGSystemException{

		LoadMovementVendorTO vendorTo=new LoadMovementVendorTO();
		vendorTo.setVendorType(UdaanCommonConstants.VENDOR_TYPE_FRANCHISE);
		OfficeTO offTO= new OfficeTO();
		offTO.setCityId(officeTO.getCityId());
		vendorTo.setOfficeTO(offTO);
		return DrsConverterUtil.prepareVendorMap(deliveryUniversalService.getVendorDtlsForDrs(vendorTo.getVendorType(), officeTO.getOfficeId())) ;
		
	}
	/**
	 * getAllCoCourierVendorsByLoggedInOffice
	 * @param officeId
	 * @return Map<Integer, String>
	 * @throws CGBusinessException 
	 * @throws CGSystemException 
	 */
	private Map<Integer, String> getAllCoCourierVendorsByLoggedInOffice(OfficeTO officeTO) throws CGSystemException, CGBusinessException{
		Map<Integer, String> partyTypeDetails=null;
		partyTypeDetails= getAllCoCourierVendorList(officeTO);
		return partyTypeDetails;
	}
	/**
	 * getAllEmployeesByLoggedInOffice
	 * @param officeId
	 * @return Map<Integer, String>
	 * @throws CGSystemException 
	 * @throws CGBusinessException 
	 */
	private Map<Integer, String> getAllEmployeesByLoggedInOffice(OfficeTO officeTO) throws CGBusinessException, CGSystemException{
		Map<Integer, String> partyTypeDetails=null;
		Integer officeId=officeTO.getOfficeId();
		if(!StringUtil.isEmptyInteger(officeId)){
			List<EmployeeTO> employeeList=null;
			EmployeeTO empTo= new EmployeeTO();
			empTo.setOfficeId(officeId);
			empTo.setEmpVirtual(CommonConstants.YES);
			employeeList= deliveryUniversalService.getEmployeeDetails(empTo);
			if(!CGCollectionUtils.isEmpty(employeeList)){
				partyTypeDetails =  new HashMap<>(employeeList.size());
				partyTypeDetails=UniversalConverterUtil.getEmployeeMapFromList(employeeList);
			}else{
				LOGGER.warn("DeliveryCommonServiceImpl ::getAllEmployeesByLoggedInOffice :: DRS-FOR Employee details does not Exist");
			}
		}else{
			LOGGER.warn("DeliveryCommonServiceImpl ::getAllEmployeesByLoggedInOffice :: Logged in Office id is null :"+officeId);
		}
		return partyTypeDetails;
	}

	/**
	 * Drs process number generator :Generete Uniqe DRS/YP DRS number based on the Branch Code.
	 * @param to the to
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public String drsProcessNumberGenerator(SequenceGeneratorConfigTO to)
			throws CGSystemException, CGBusinessException {
		String generatedNumber=null;
		String qryName=DrsCommonConstants.QRY_GET_MAX_DRS_NUMBER;

		switch (to.getProcessRequesting()) {
		case DrsConstants.YP_DRS_NO :
			to.setProcessRequesting(DrsConstants.DRS);
			break;
		case DrsConstants.YP_DRS_YES :
			to.setProcessRequesting(DrsConstants.YP_DRS);
			to.setLengthOfNumber(DrsConstants.YDRS_NUMBER_LENGTH);
			break;
		case DrsConstants.M_DRS_YES:
			break;
		case DrsConstants.BULK_DRS_TYPE:
			
			to.setSequenceRunningLength(DrsConstants.DRS_RUNNING_NUMBER_LENGTH);
			if(StringUtil.isStringEmpty(to.getPrefixCode())){
				to.setProcessRequesting(to.getProcessRequesting());
				to.setLengthOfNumber(DrsConstants.DRS_NUMBER_LENGTH_13);
			}else{
				to.setProcessRequesting(to.getPrefixCode()+to.getProcessRequesting());
				to.setLengthOfNumber(DrsConstants.DRS_NUMBER_LENGTH_14);
			}
			break;
		default:
			LOGGER.error("DeliveryCommonServiceImpl::drsProcessNumberGenerator######Taking DEFAULT NUMBER AS DRS######");
			to.setProcessRequesting(DrsConstants.DRS);
		}

		if(StringUtil.isStringEmpty(qryName)){
			LOGGER.error("DeliveryCommonServiceImpl::drsProcessNumberGenerator---process Query not defined for the given process");
			throw new CGBusinessException("process Query not defined for the given process");
		}
		generatedNumber = getGeneratedNumber(to,qryName);




		return generatedNumber;
	}
	/**
	 * Gets the generated number.
	 *
	 * @param to the to
	 * @param qryName the qry name
	 * @return the generated number
	 * @throws CGSystemException the cG system exception
	 */
	private String getGeneratedNumber(SequenceGeneratorConfigTO to,String qryName) throws CGSystemException {
		String generatedNumber=null;
		String number=null;
		Long result=0l;
		number = deliveryCommonDAO.getMaxNumberFromProcess(to, qryName);
		if(!StringUtil.isStringEmpty(number)){
			String  res=number.substring(to.getLengthOfNumber()-to.getSequenceRunningLength());

			result=StringUtil.parseLong(res);
			if(StringUtil.isNull(result) ){
				result=0l;
			}else if(result >= 0l){
				result +=1;
			}

		}else{
			LOGGER.info("DeliveryCommonServiceImpl::getGeneratedNumber---Max number does not exist for Request"+to.getProcessRequesting());
		}
		generatedNumber=to.getProcessRequesting().toUpperCase()+to.getRequestingBranchCode().toUpperCase()+sequencePadding(result,to.getSequenceRunningLength());
		return generatedNumber;
	}
	/**
	 * Sequence padding.
	 *
	 * @param number the number
	 * @param length the length
	 * @return the string
	 */
	private String sequencePadding(Long number,Integer length){
		String format=null;
		format="%0"+length+"d";
		return String.format(format, number);  
	}

	/**
	 * Discard generated drs.
	 *
	 * @param deliveryTO the delivery to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public Boolean discardGeneratedDrs(AbstractDeliveryTO deliveryTO)throws CGBusinessException, CGSystemException{
		Boolean result=Boolean.FALSE;
		DeliveryDO deliveryDO= new DeliveryDO();
		DrsConverterUtil.convertHeaderTO2DO(deliveryTO, deliveryDO);
		DrsUtil.preProcessForTwoWayWrite(deliveryDO);
		result = deliveryCommonDAO.discardGeneratedDrs(deliveryDO);
		return result;
	}


	/**
	 * Modify generated drs.Modifies only DRS-FOR & its code if and only if it's in open status
	 *
	 * @param deliveryTO the delivery to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public Boolean modifyGeneratedDrs(AbstractDeliveryTO deliveryTO)throws CGBusinessException, CGSystemException{
		Boolean result=Boolean.FALSE;
		DeliveryDO deliveryDO= new DeliveryDO();
		DrsConverterUtil.convertHeaderTO2DO(deliveryTO, deliveryDO);

		DrsUtil.preProcessForTwoWayWrite(deliveryDO);
		result = deliveryCommonDAO.modifyDrs(deliveryDO);
		return result;
	}



	//FIXME need to modify
	public Boolean getDrsNavigationDetails(AbstractDeliveryTO deliveryTO)throws CGBusinessException, CGSystemException{
		Boolean result=Boolean.FALSE;
		DeliveryDO deliveryDO= null;
		DrsConverterUtil.convertHeaderTO2DO(deliveryTO, deliveryDO);
		deliveryDO= deliveryCommonDAO.getDrsDetailsByDrsNumber(deliveryTO);
		return result;
	}

	/**
	 * Checks if is the consg is valid for drs prepration.
	 *
	 * @param deliveryTO the delivery to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public DeliveryConsignmentTO validateConsignmentDetails(AbstractDeliveryTO deliveryTO)throws CGBusinessException, CGSystemException{
		DeliveryConsignmentTO dlvConsgTo=null;
		long startMilliseconds=System.currentTimeMillis();
		LOGGER.debug(
				"DeliveryCommonServiceImpl::validateConsignmentDetails::start------------>:::::::startMilliseconds"+startMilliseconds+"For Consignment["+deliveryTO.getConsignmentNumber()+"]");
		String inConsgType=deliveryTO.getConsignmentType();

		if(!StringUtil.isStringEmpty(inConsgType)){

			String consgNumber=deliveryTO.getConsignmentNumber();
			validateStopDelivery(consgNumber);
			String seriesType=UniversalDeliveryContants.DRS_CONSIGMENT;
			if(DrsUtil.isComailNumber(consgNumber)){
				seriesType=UniversalDeliveryContants.DRS_COMAIL;
			}else{
				String consgType= getConsignmentTypeByConsgNumber(consgNumber);
				if(inConsgType.equalsIgnoreCase(DrsConstants.CONSG_TYPE_NA)){
					//this code executes for Bulk cn update for pending
					inConsgType=consgType;
					deliveryTO.setConsignmentType(consgType);
				}
				DrsUtil.validateConsignmentType(deliveryTO, consgNumber, consgType);
			}
			validateConsignmentFromDelivery(consgNumber, seriesType);
			/**
			 * New Change Added to restrict RTOed consignments not to allow in general DRS.
			 * */
			validateForRTOed(consgNumber);
			switch(inConsgType){
			case CommonConstants.CONSIGNMENT_TYPE_DOCUMENT:
				dlvConsgTo	=validateConsignmentForDOXDRS(deliveryTO);
				break;
			case CommonConstants.CONSIGNMENT_TYPE_PARCEL:
				dlvConsgTo	=validateConsignmentForPPXDRS(deliveryTO);
				break;

			}


			if(!StringUtil.isNull(dlvConsgTo)){

				String	outConsgType=dlvConsgTo.getConsignmentTypeCode();
				if(StringUtil.isStringEmpty(outConsgType)|| !outConsgType.equalsIgnoreCase(inConsgType)){

					/** Consgnment type mismatch ,throw Business Exception*/
					DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NOT_INVALID_CONSG_TYPE,new String[]{consgNumber});
				}
				dlvConsgTo.setNoOfPcs(1);
				/** get attempt number for the consignment */
				Integer attempNum=getAttemptCountForConsignment(deliveryTO.getConsignmentNumber());
				if(StringUtil.isEmptyInteger(attempNum)){
					attempNum=1;
				}
				dlvConsgTo.setAttemptNumber(attempNum);
				//FIXME ADD RATE COMPONENETS
			}
		}else{
			//FIXME THrow EXception
			LOGGER.trace("DeliveryCommonServiceImpl::validateConsignmentDetails---Consignment type input is empty");
			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_CAN_NOT_EMPTY,new String[]{DrsCommonConstants.CONSIGNMENT_TYPE});
		}
		if(!StringUtil.isNull(dlvConsgTo)){
			dlvConsgTo.setOtherAmount(deliveryUniversalService.getOctroiAmountForDrs(deliveryTO.getConsignmentNumber()));

		}
		long endMilliSeconds=System.currentTimeMillis();
		long diff=endMilliSeconds-startMilliseconds;
		LOGGER.debug(
				"DeliveryCommonServiceImpl::validateConsignmentDetails::END------------>:::::::endMilliSeconds:["+endMilliSeconds+"] Difference"+(diff) +" Difference IN HH:MM:SS format ::"+DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff)+"For Consignment["+deliveryTO.getConsignmentNumber()+"]");
	
		return dlvConsgTo;
	}

	/**
	 * @param consgNumber
	 * @param seriesType
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	@Override
	public void validateConsignmentFromDelivery(String consgNumber,
			String seriesType) throws CGSystemException, CGBusinessException {
		String status=getConsignmentStatusFromDelivery(consgNumber);
		LOGGER.trace("DeliveryCommonServiceImpl ::validateConsignmentDetails :: getConsignmentStatusFromDelivery :For the Consg ["+consgNumber+"]Status["+status+"]");
		if(!StringUtil.isStringEmpty(status)){
			if(status.equalsIgnoreCase(UniversalDeliveryContants.DELIVERY_STATUS_DELIVERED)){
				/** Consignemtn already delivered ,throw Business Exception*/
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_CONSG_DELIVERED,new String[]{seriesType,consgNumber});
			}else if(status.equalsIgnoreCase(UniversalDeliveryContants.DELIVERY_STATUS_OUT_DELIVERY)){
				/** Consignemtn already prepared , throw Business Exception*/
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_PREPARED_ALREADY_FOR_CONSG,new String[]{seriesType,consgNumber});
			}
		}
	}
	
	@Override
	public void validateConsignmentFromDeliveryForSave(String consgNumber,
			String seriesType) throws CGSystemException, CGBusinessException {
		try {
			validateConsignmentFromDelivery(consgNumber, seriesType);
		} catch (CGBusinessException e) {
			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.CN_HAVE_UPDATED_IN_DRS);
		}
	}






	/**
	 * Validate consignment for doxdrs.
	 * This Method will be executed for Normal DRS Preparation (not for YP-DRS)
	 */
	public DeliveryConsignmentTO validateConsignmentForDOXDRS(AbstractDeliveryTO deliveryTO)throws CGBusinessException, CGSystemException{
		long startMilliseconds=System.currentTimeMillis();
		LOGGER.debug(
				"DeliveryCommonServiceImpl::validateConsignmentDetails::validateConsignmentForDOXDRS::start------------>:::::::startMilliseconds"+startMilliseconds+"For Consignment["+deliveryTO.getConsignmentNumber()+"]");
	
		DeliveryConsignmentTO consgTO=null;
		Boolean statuFlag=Boolean.FALSE;
		Boolean requiredBooking=false;
		boolean isComail=false;

		String consgNumber = deliveryTO.getConsignmentNumber();
		isComail = DrsUtil.isComailNumber(consgNumber);

		if(isComail){
			consgTO = validateForComail(deliveryTO);
		}else{
			/** it checks whether given consignment exist in the Consignment table or not*/
			
			/*
			 * Below Code is commented since this validatation has been taken care in Consignment type validation
			 * statuFlag= isConsignmentValid(consgNumber);
			LOGGER.trace("DeliveryCommonServiceImpl ::validateConsignmentDetails :: validateConsignmentForDOXDRS : isConsignmentValid::"+statuFlag);
			if(!statuFlag){
				*//** throw Business Exception*//*
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_INVALID_CONSG_NUMBER,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT,consgNumber});
			}*/
			String ypdrs= deliveryTO.getYpDrs();
			if(!StringUtil.isStringEmpty(ypdrs)){
				Date mnfstDate=null;
				Date currentDate=DateUtil.getCurrentDateWithoutTime();
				switch(ypdrs){
				case DrsConstants.YP_DRS_NO:
					/** for To-days-drs Consignment entry should not exist in Delivery table*//*
					 * commented : Artifact artf3000647
					Boolean isExist=deliveryUniversalService.isConsignmentExistInDelivery(deliveryTO.getConsignmentNumber());
					if(isExist){
						DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_CAN_NOT_INCLUDE_IN_TODAYS_DRS,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT});
					}*/
					mnfstDate=getManifestedDateByConsgNumber(deliveryTO,true);
					if(StringUtil.isNull(mnfstDate)){
						requiredBooking=true;
						//DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NOT_MANIFESTED,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT,consgNumber});
					}else{
						if(!DateUtil.equalsDate(mnfstDate, currentDate)){
							//BR:Any Consignment received on the previous day, whether it is marked pending or not should be considered in YP DRS. 
							DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_CAN_NOT_PREP_FOR_OLD_CN,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT});
						}
					}

					break;
				case DrsConstants.YP_DRS_YES:
					mnfstDate=getManifestedDateByConsgNumber(deliveryTO,true);

					if(StringUtil.isNull(mnfstDate)){
						requiredBooking=true;
					}else{
						if(DateUtil.equalsDate(mnfstDate, DateUtil.getCurrentDate())){
							//BR:Any Consignment received on the previous day, whether it is marked pending or not should be considered in YP DRS. 
							DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_CAN_NOT_PREP_FOR_NEW_CN_YP,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT});
						}else if(!currentDate.after(mnfstDate)){
							//BR:Any Consignment received on the previous day, whether it is marked pending or not should be considered in YP DRS. 
							DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_CAN_NOT_PREP_FOR_NEW_CN_YP,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT});
						}
					}
					break;
				}
			}

			if(!requiredBooking){
				/** it checks whether given consignment is IN-Manifested or not*/
				LOGGER.trace("DeliveryCommonServiceImpl ::validateConsignmentDetails :: getInManifestedConsgnDtls :For the Consg ["+consgNumber+"] verifying in Manifest table");
				consgTO= deliveryUniversalService.getInManifestedConsgnDtls(deliveryTO);
			}else{
				//defect Fixed artf3400319	Third party pending CN, not accepting in DRS
				//check & allow if Cn is third party manifested...(this part executes only if it's Thirdparty Manifest DOX)
				String manifestNumber=getOutManifestedNumberByLoggedOfficeForTPManifestParentConsgForDOXManualDrs(deliveryTO);
				
				if(!StringUtil.isStringEmpty(manifestNumber)){
					LOGGER.debug("DeliveryCommonServiceImpl ::validateConsignmentDetails::validateConsignmentForDOXDRS :: For the Consg ["+consgNumber+"] Exist in third party Manifest number :["+manifestNumber+"]");
					//get consg dtls
					consgTO = getOutManifestedConsgnDtls(deliveryTO);
				}else{
					LOGGER.warn("DeliveryCommonServiceImpl ::validateConsignmentDetails::validateConsignmentForDOXDRS :: For the Consg ["+consgNumber+"] not Exist in third party Manifest also");
				}
				
			}
			if(StringUtil.isNull(consgTO)){
				LOGGER.trace("DeliveryCommonServiceImpl ::validateConsignmentDetails ::validateConsignmentForDOXDRS:: For the Consg ["+consgNumber+"] NO Exist in Manifest table");
				//Check in Booking table whether consignment is booked with logged in office
				consgTO = getDoxConsgDtlsFromBooking(deliveryTO);
				if(StringUtil.isNull(consgTO)){
					/** Consgnment not yet inmanifested ,throw Business Exception*/
					LOGGER.trace("DeliveryCommonServiceImpl ::validateConsignmentDetails :: getDoxConsgDtlsFromBooking :For the Consg ["+consgNumber+"] Does not exist in booking");
					DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NOT_MANIFESTED,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT,consgNumber});
				}else{
					LOGGER.trace("DeliveryCommonServiceImpl ::validateConsignmentDetails ::validateConsignmentForDOXDRS:: getDoxConsgDtlsFromBooking :For the Consg ["+consgNumber+"] exist in booking");
				}
			}
		}
		if(!StringUtil.isNull(consgTO)){
			consgTO.setParentChildCnType(UniversalDeliveryContants.DRS_PARENT_CONSG_TYPE);
		}
		long endMilliSeconds=System.currentTimeMillis();
		long diff=endMilliSeconds-startMilliseconds;
		LOGGER.debug(
				"DeliveryCommonServiceImpl::validateConsignmentDetails::validateConsignmentForDOXDRS::END------------>:::::::endMilliSeconds:["+endMilliSeconds+"] Difference"+(diff) +" Difference IN HH:MM:SS format ::"+DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff)+"For Consignment["+deliveryTO.getConsignmentNumber()+"]");
	
		return consgTO;
	}




	/**
	 * Validate consignment for ppxdrs. ::
	 */
	public DeliveryConsignmentTO validateConsignmentForPPXDRS(AbstractDeliveryTO deliveryTO)throws CGBusinessException, CGSystemException{
		DeliveryConsignmentTO dlvConsgTo=null;
		long startMilliseconds=System.currentTimeMillis();
		LOGGER.debug(
				"DeliveryCommonServiceImpl::validateConsignmentDetails::validateConsignmentForPPXDRS::start------------>:::::::startMilliseconds"+startMilliseconds+"For Consignment["+deliveryTO.getConsignmentNumber()+"]");
	
		boolean isParent=true;
		Boolean requiredBooking=false;
		String consgNumber = deliveryTO.getConsignmentNumber();
		Boolean isConghasChlds=deliveryUniversalService.isConsgHavingChildCns(consgNumber);

		if(isConghasChlds){
			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_PARENT_CONSG_NUMBER,new String[]{consgNumber});
		}
		Boolean isChildcn=deliveryUniversalService.isChildCn(consgNumber);

		if(isChildcn){
			isParent=false;
		}
		String ypdrs= deliveryTO.getYpDrs();
		if(!StringUtil.isStringEmpty(ypdrs)){
			Date mnfstDate=null;
			Date currentDate=DateUtil.getCurrentDateWithoutTime();
			switch(ypdrs){
			case DrsConstants.YP_DRS_NO:
				/** for To-days-drs Consignment entry should not exist in Delivery table*/
				/*commented : Artifact artf3000647
				 * Boolean isExist=deliveryUniversalService.isConsignmentExistInDelivery(deliveryTO.getConsignmentNumber());
				if(isExist){
					DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_CAN_NOT_INCLUDE_IN_TODAYS_DRS,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT});
				}*/
				mnfstDate=getManifestedDateByConsgNumber(deliveryTO,isParent);
				if(StringUtil.isNull(mnfstDate)){
					//DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NOT_MANIFESTED,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT,consgNumber});
					requiredBooking=true;
				}else{
					if(!DateUtil.equalsDate(mnfstDate, currentDate)){
						//BR:Any Consignment received on the previous day, whether it is marked pending or not should be considered in YP DRS. 
						DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_CAN_NOT_PREP_FOR_OLD_CN,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT});
					}
				}

				break;
			case DrsConstants.YP_DRS_YES:
				mnfstDate=getManifestedDateByConsgNumber(deliveryTO,isParent);

				if(StringUtil.isNull(mnfstDate)){
					//DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NOT_MANIFESTED,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT,consgNumber});
					requiredBooking=true;
				}else{
					if(DateUtil.equalsDate(mnfstDate, DateUtil.getCurrentDate())){
						//BR:Any Consignment received on the previous day, whether it is marked pending or not should be considered in YP DRS. 
						DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_CAN_NOT_PREP_FOR_NEW_CN_YP,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT});
					}

					if(!currentDate.after(mnfstDate)){
						//BR:Any Consignment received on the previous day, whether it is marked pending or not should be considered in YP DRS. 
						DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_CAN_NOT_PREP_FOR_NEW_CN_YP,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT});
					}
				}
				break;
			}
		}


		/** it checks whether given consignment is single piece consg(it does nt have child consgments)*/


		ConsignmentTO consgTo= deliveryUniversalService.getParentConsgDetailsForDRS(consgNumber);
		if(!StringUtil.isNull(consgTo)){
			/** this consgmnt does not have any child consg details*/
			//BR:1. check whether consg is inmanifested
			/** it checks whether given consignment is IN-Manifested or not*/
			if(!requiredBooking){
				dlvConsgTo= deliveryUniversalService.getInManifestedConsgnDtls(deliveryTO);
			}else{
				//defect Fixed artf3400319	Third party pending CN, not accepting in DRS
				//check & allow if Cn is third party manifested...(this part executes only if it's Thirdparty Manifest PPX)
				//1. verify whether it's TPMF for PPX
				String mnfstNumber=getOutManifestedNumberByLoggedOfficeForTPManifestParentConsgForPpxManualDrs(deliveryTO);
				//2. if yes then get the Cn details
				if(!StringUtil.isStringEmpty(mnfstNumber)){
					LOGGER.debug("DeliveryCommonServiceImpl ::validateConsignmentDetails(PPX parent)::validateConsignmentForPPXDRS :: For the Consg ["+consgNumber+"] Exist in third party Manifest number :["+mnfstNumber+"]");
					dlvConsgTo = getOutManifestedConsgnDtls(deliveryTO);
				}else{
					LOGGER.warn("DeliveryCommonServiceImpl ::validateConsignmentDetails(PPX parent) ::validateConsignmentForPPXDRS:: For the Consg ["+consgNumber+"] not Exist in third party Manifest also");
				}
			}

			if(StringUtil.isNull(dlvConsgTo)){
				//BR:2. check whether consg is booked in logged in office for delivery
				LOGGER.trace("DeliveryCommonServiceImpl ::validateConsignmentDetails::validateConsignmentForPPXDRS :: For the Consg ["+consgNumber+"] NOt Exist in Manifest table");
				//Check in Booking table whether consignment is booked with logged in office
				dlvConsgTo = deliveryUniversalService.getDoxConsgDtlsFromBooking(deliveryTO);
				if(StringUtil.isNull(dlvConsgTo)){
					/** Consgnment not yet inmanifested ,throw Business Exception*/
					DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NOT_MANIFESTED,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT,consgNumber});
				}else{
					LOGGER.trace("DeliveryCommonServiceImpl ::validateConsignmentDetails :: For the Consg ["+consgNumber+"]  Exist in Booking table");
				}

			}
			if(!StringUtil.isNull(dlvConsgTo)){
				dlvConsgTo.setParentChildCnType(UniversalDeliveryContants.DRS_PARENT_CONSG_TYPE);

			}

		}else{
			//Is Exist atleast in child consg table
			if(!isChildcn){
				//if it's not exist in child consg table aswell then through business exception
				/** throw Business Exception*/
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_INVALID_CONSG_NUMBER,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT,consgNumber});

			}
			/** this consgmnt   has  child consg details*/
			//BR:1. check whether give child consg is inmanifested
			Boolean isMnsfted=deliveryUniversalService.isChildConsgInManifestedForPpx(deliveryTO);
			if(!isMnsfted){
				//defect Fixed artf3400319	Third party pending CN, not accepting in DRS
				//check & allow if Cn is third party manifested...(this part executes only if it's Thirdparty Manifest PPX)

				String	mnfstNumber=getOutManifestedNumberByLoggedOfficeForTPManifestChildConsgForPpxManualDrs(deliveryTO);
				//2. if yes then get the Cn details
				if(!StringUtil.isStringEmpty(mnfstNumber)){
					LOGGER.debug("DeliveryCommonServiceImpl ::validateConsignmentDetails(PPX child ) ::validateConsignmentForPPXDRS:: For the Consg ["+consgNumber+"] Exist in third party Manifest number :["+mnfstNumber+"]");
					dlvConsgTo= getChildConsgDetailsForDRS(consgNumber);
				}else{
					LOGGER.warn("DeliveryCommonServiceImpl ::validateConsignmentDetails(PPX child)::validateConsignmentForPPXDRS :: For the Consg ["+consgNumber+"] not Exist in third party Manifest also");
				}
				if(StringUtil.isNull(dlvConsgTo)){
					dlvConsgTo = deliveryUniversalService.getPpxConsgDtlsFromBooking(deliveryTO);
					if(StringUtil.isNull(dlvConsgTo)){
						/** Consgnment not yet inmanifested ,throw Business Exception*/
						DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NOT_MANIFESTED,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT,consgNumber});
					}
				}
			}else{
				if(!requiredBooking){
					dlvConsgTo=deliveryUniversalService.getChildConsgDetailsForDRS(deliveryTO.getConsignmentNumber());
				}
				if(StringUtil.isNull(dlvConsgTo)){
					/** throw Business Exception*/
					DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_INVALID_CONSG_NUMBER,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT,consgNumber});

				}
			}
			if(!StringUtil.isNull(dlvConsgTo)){
				dlvConsgTo.setParentChildCnType(UniversalDeliveryContants.DRS_CHILD_CONSG_TYPE);
			}

		}
		long endMilliSeconds=System.currentTimeMillis();
		long diff=endMilliSeconds-startMilliseconds;
		LOGGER.debug(
				"DeliveryCommonServiceImpl::validateConsignmentDetails::validateConsignmentForPPXDRS::END------------>:::::::endMilliSeconds:["+endMilliSeconds+"] Difference"+(diff) +" Difference IN HH:MM:SS format ::"+DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff)+"For Consignment["+deliveryTO.getConsignmentNumber()+"]");
	
		return dlvConsgTo;
	}

	/**
	 * @param deliveryTO
	 * @param consgNumber
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	private DeliveryConsignmentTO validateForComail(AbstractDeliveryTO deliveryTO) throws CGSystemException, CGBusinessException {
		Boolean statuFlag;
		DeliveryConsignmentTO consgTO=null;
		/** Note : since comail consgment's do not have Booking Information */
		String consgNumber = deliveryTO.getConsignmentNumber();
		statuFlag=isComailValid(consgNumber);
		if(!statuFlag){
			/** throw Business Exception*/
			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_INVALID_CONSG_NUMBER,new String[]{UniversalDeliveryContants.DRS_COMAIL,consgNumber});
		}

		String ypdrs= deliveryTO.getYpDrs();
		if(!StringUtil.isStringEmpty(ypdrs)){
			Date mnfstDate=null;
			Date currentDate=DateUtil.getCurrentDateWithoutTime();
			switch(ypdrs){
			case DrsConstants.YP_DRS_NO:
				/** for To-days-drs Consignment entry should not exist in Delivery table*/
				/*commented : Artifact artf3000647
				 * Boolean isExist=deliveryUniversalService.isConsignmentExistInDelivery(deliveryTO.getConsignmentNumber());
				if(isExist){
					DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_CAN_NOT_INCLUDE_IN_TODAYS_DRS,new String[]{UniversalDeliveryContants.DRS_COMAIL});
				}*/
				mnfstDate=getManifestedDateByComailNumber(deliveryTO.getConsignmentNumber());
				if(StringUtil.isNull(mnfstDate)){
					DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NOT_MANIFESTED,new String[]{UniversalDeliveryContants.DRS_COMAIL,consgNumber});
				}else{
					if(!DateUtil.equalsDate(mnfstDate, currentDate)){
						//BR:Any Consignment received on the previous day, whether it is marked pending or not should be considered in YP DRS. 
						DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_CAN_NOT_PREP_FOR_OLD_CN,new String[]{UniversalDeliveryContants.DRS_COMAIL});
					}
				}

				break;
			case DrsConstants.YP_DRS_YES:
				mnfstDate=getManifestedDateByComailNumber(deliveryTO.getConsignmentNumber());

				if(StringUtil.isNull(mnfstDate)){
					DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NOT_MANIFESTED,new String[]{UniversalDeliveryContants.DRS_COMAIL,consgNumber});
				}else{
					if(DateUtil.equalsDate(mnfstDate, DateUtil.getCurrentDate())){
						//BR:Any Consignment received on the previous day, whether it is marked pending or not should be considered in YP DRS. 
						DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_CAN_NOT_PREP_FOR_NEW_CN_YP,new String[]{UniversalDeliveryContants.DRS_COMAIL});
					}

					if(!currentDate.after(mnfstDate)){
						//BR:Any Consignment received on the previous day, whether it is marked pending or not should be considered in YP DRS. 
						DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_CAN_NOT_PREP_FOR_NEW_CN_YP,new String[]{UniversalDeliveryContants.DRS_COMAIL});
					}
				}
				break;
			}
		}

		/** it checks whether given consignment is IN-Manifested or not*/
		consgTO= deliveryUniversalService.getInManifestedComailDtls(deliveryTO);

		/** it checks whether given consignment is valid Consignment Type or not*/
		if(consgTO==null){
			/** Consgnment not yet inmanifested ,throw Business Exception*/
			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NOT_MANIFESTED,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT,consgNumber});
		}
		return consgTO;
	}

	/**
	 * Gets the drs navigation details.
	 *
	 * @param drsNumber the drs number
	 * @param drsCode the drs code
	 * @return the drs navigation details
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public List<DeliveryNavigatorTO> getDrsNavigationDetails(List<String> drsNumber,String drsCode)throws CGBusinessException, CGSystemException{
		List<DeliveryNavigatorTO> navaigatorToList=null;
		List<DeliveryNavigatorDO> navaigatorDoList=null;

		navaigatorDoList=deliveryCommonDAO.getDrsNavigationDetails(drsNumber, drsCode);

		if(!CGCollectionUtils.isEmpty(navaigatorDoList)){
			navaigatorToList= new ArrayList<>(navaigatorDoList.size());
			for(DeliveryNavigatorDO navDO :navaigatorDoList ){
				DeliveryNavigatorTO navTO= new DeliveryNavigatorTO();
				CGObjectConverter.createToFromDomain(navDO, navTO);
				navaigatorToList.add(navTO);
			}
		}
		return navaigatorToList;

	}

	/**
	 * Gets the drs navigation details.
	 *
	 * @param drsNumber the drs number
	 * @param drsCode the drs code
	 * @return the drs navigation details
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public DeliveryNavigatorTO getDrsNavigationDetails(String drsNumber,String drsCode)throws CGBusinessException, CGSystemException{
		DeliveryNavigatorTO navaigatorTo=null;
		DeliveryNavigatorDO navaigatorDo=null;

		navaigatorDo=deliveryCommonDAO.getDrsNavigationDetails(drsNumber, drsCode);

		if(!StringUtil.isNull(navaigatorDo)){
			navaigatorTo= new DeliveryNavigatorTO();
			CGObjectConverter.createToFromDomain(navaigatorDo, navaigatorTo);
		}
		return navaigatorTo;

	}


	/**
	 * Gets the dox consg dtls from booking.
	 *
	 * @param deliveryTO the delivery to
	 * @return the dox consg dtls from booking
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public DeliveryConsignmentTO getDoxConsgDtlsFromBooking(AbstractDeliveryTO deliveryTO)throws CGBusinessException, CGSystemException{
		return deliveryUniversalService.getDoxConsgDtlsFromBooking(deliveryTO);
	}

	@Override	
	public DeliveryConsignmentTO getInManifestedConsgnDtls(
			AbstractDeliveryTO inputTo) throws CGSystemException,
			CGBusinessException {
		return deliveryUniversalService.getInManifestedConsgnDtls(inputTo);
	}
	@Override
	public DeliveryConsignmentTO getThirdPartyManifestedConsgnDtlsForParentCn(
			AbstractDeliveryTO inputTo) throws CGSystemException,
			CGBusinessException {
		return deliveryUniversalService.getThirdPartyManifestedConsgnDtlsForDrsParentCn(inputTo);
	}
	@Override
	public DeliveryConsignmentTO getThirdPartyManifestedConsgnDtlsForChildCn(
			AbstractDeliveryTO inputTo) throws CGSystemException,
			CGBusinessException {
		return deliveryUniversalService.getThirdpartyManifestedConsgDetailsForDRSChildCn(inputTo);
	}

	@Override
	public DeliveryConsignmentTO getPpxConsgDtlsFromBooking(
			AbstractDeliveryTO inputTo) throws CGSystemException,
			CGBusinessException {
		return deliveryUniversalService.getPpxConsgDtlsFromBooking(inputTo);
	}

	/**
	 * Gets the non delivery reasons.
	 *
	 * @return the non delivery reasons
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public Map<Integer, String> getNonDeliveryReasons()throws CGBusinessException, CGSystemException{
		Map<Integer, String> nonDlvReasons=null;

		List<ReasonTO> reasonToList=deliveryUniversalService.getNonDeliveryReasons();

		nonDlvReasons = getReasonsAsMap(nonDlvReasons, reasonToList);

		return nonDlvReasons;
	}

	private Map<Integer, String> getReasonsAsMap(
			Map<Integer, String> nonDlvReasons, List<ReasonTO> reasonToList) {
		if(!CGCollectionUtils.isEmpty(reasonToList)){
			nonDlvReasons =  new HashMap<>(reasonToList.size());
			for(ReasonTO reason :reasonToList){
				String reasonName= reason.getReasonCode()+FrameworkConstants.CHARACTER_HYPHEN+reason.getReasonName();
				nonDlvReasons.put(reason.getReasonId(), reasonName.replaceAll(",", ""));
			}
		}else{
			LOGGER.warn("DeliveryCommonServiceImpl ::getNonDeliveryReasons :: Non Delivery Reason detials does not exist ");
		}
		return nonDlvReasons;
	}
	@Override
	public Map<Integer, String> getNonDeliveryReasonsByType(String reasonType)throws CGBusinessException, CGSystemException{
		Map<Integer, String> nonDlvReasons=null;

		List<ReasonTO> reasonToList=deliveryUniversalService.getReasonsByReasonType(reasonType);

		nonDlvReasons = getReasonsAsMap(nonDlvReasons, reasonToList);

		return nonDlvReasons;
	}

	/**
	 * Gets the drs details by drs number for preparation.
	 *
	 * @param inputTo the input to
	 * @return the drs details by drs number for preparation
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public DeliveryTO getDrsDetailsForPreparation(AbstractDeliveryTO inputTo)throws CGBusinessException, CGSystemException{
		DeliveryTO deliveryTo=null;
		DeliveryDO deliveryDO= null;
		if(StringUtil.isStringEmpty(inputTo.getDrsNumber())){
			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NUMBER_NOT_EMPTY);
		}
		deliveryDO= deliveryCommonDAO.getDrsDetailsByDrsNumber(inputTo);
		if(!StringUtil.isNull(deliveryDO)){

			String code=inputTo.getDrsScreenCode();

			if((StringUtil.isStringEmpty(code)|| StringUtil.isStringEmpty(deliveryDO.getDrsScreenCode()))|| (!deliveryDO.getDrsScreenCode().equalsIgnoreCase(code) && !code.equalsIgnoreCase(DrsConstants.PENDING_DRS_SCREEN_CODE))){
				// throw Business Exception since requested & loaded DRS-Screen code is different
				LOGGER.error("DeliveryCommonServiceImpl ::getDrsDetailsByDrsNumberForUpdate ::Business Exception (since requested & loaded DRS-Screen code is different)");
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NO_GENERATED_HERE, new String[]{deliveryDO.getDrsNumber()});
			}
			deliveryTo= DrsConverterUtil.convertHeaderDO2TO(deliveryDO, inputTo);
			if(inputTo.getLoggedInUserId() !=null && deliveryDO.getCreatedBy()!=null && inputTo.getLoggedInUserId().intValue()!= deliveryDO.getCreatedBy().intValue()){
				/** check whether user can update the details*/
				LOGGER.error("DeliveryCommonServiceImpl ::getDrsDetailsByDrsNumberForPreparation ::Business Exception (since Created & requested users are different)");
				DrsUtil.setBusinessException4Modification(deliveryTo,UdaanWebErrorConstants.DRS_INVALID_USER);
			}
			if(StringUtil.isStringEmpty(deliveryDO.getDrsStatus())|| !deliveryDO.getDrsStatus().equalsIgnoreCase(DrsConstants.DRS_STATUS_OPEN)){
				/** check whether DRS details allowed to update*/
				LOGGER.error("DeliveryCommonServiceImpl ::getDrsDetailsByDrsNumberForPreparation ::Business Exception (DRS already been updated)");
				DrsUtil.setBusinessException4Modification(deliveryTo,UdaanWebErrorConstants.DRS_ALREADY_UPDATED,new String[]{deliveryDO.getDrsStatus()});
			}
			/** get All DRS-FOR party type details*/
			prepareDeliveryForPartyTypeDetails(inputTo, deliveryTo);
			prepareGridDetails(deliveryTo, deliveryDO);

		}else{
			//FIXME throw CGBusiness Exception since there are no drs details
			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_DOES_NOT_EXIST, new String[]{inputTo.getDrsNumber()});
		}
		return deliveryTo;
	}

	/**
	 * Gets the drs details by drs number for update.
	 *
	 * @param inputTo the input to
	 * @return the drs details by drs number for update
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public DeliveryTO getDrsDetailsForUpdate(AbstractDeliveryTO inputTo)throws CGBusinessException, CGSystemException{
		DeliveryTO deliveryTo=null;
		DeliveryDO deliveryDO= null;
		if(StringUtil.isStringEmpty(inputTo.getDrsNumber())){
			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NUMBER_NOT_EMPTY);
		}

		deliveryDO= deliveryCommonDAO.getDrsDetailsByDrsNumber(inputTo);
		if(!StringUtil.isNull(deliveryDO)){
			String code=inputTo.getDrsScreenCode();

			if((StringUtil.isStringEmpty(code)|| StringUtil.isStringEmpty(deliveryDO.getDrsScreenCode()))|| (!deliveryDO.getDrsScreenCode().equalsIgnoreCase(code) && !code.equalsIgnoreCase(DrsConstants.PENDING_DRS_SCREEN_CODE))){
				// throw Business Exception since requested & loaded DRS-Screen code is different
				LOGGER.error("DeliveryCommonServiceImpl ::getDrsDetailsByDrsNumberForUpdate ::Business Exception (since requested & loaded DRS-Screen code is different)");
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NO_GENERATED_HERE, new String[]{deliveryDO.getDrsNumber()});
			}
			deliveryTo= DrsConverterUtil.convertHeaderDO2TO(deliveryDO, inputTo);
			prepareDeliveryForPartyTypeDetails(inputTo, deliveryTo);
			prepareGridDetails(deliveryTo, deliveryDO);

		}else{
			//FIXME throw CGBusiness Exception since there are no drs details
			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_DOES_NOT_EXIST, new String[]{inputTo.getDrsNumber()});
		}
		return deliveryTo;
	}
	@Override
	public DeliveryTO getDrsDetailsForManualDrs(AbstractDeliveryTO inputTo)throws CGBusinessException, CGSystemException{
		DeliveryTO deliveryTo=null;
		DeliveryDO deliveryDO= null;

		deliveryDO= deliveryCommonDAO.getDrsDetailsByDrsNumber(inputTo);
		if(!StringUtil.isNull(deliveryDO)){
			String code=inputTo.getDrsScreenCode();

			if((StringUtil.isStringEmpty(code)|| StringUtil.isStringEmpty(deliveryDO.getDrsScreenCode()))|| (!deliveryDO.getDrsScreenCode().equalsIgnoreCase(code) && !code.equalsIgnoreCase(DrsConstants.MANUAL_PENDING_DRS_SCREEN_CODE))){
				// throw Business Exception since requested & loaded DRS-Screen code is different
				LOGGER.error("DeliveryCommonServiceImpl ::getDrsDetailsByDrsNumberForUpdate ::Business Exception (since requested & loaded DRS-Screen code is different)");
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NO_GENERATED_HERE, new String[]{deliveryDO.getDrsNumber()});
			}

			if(StringUtil.isStringEmpty(deliveryDO.getManifestDrsType())){
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_MANIFEST_DRS_NUMBER_NOT_PREP_HERE, new String[]{deliveryDO.getDrsNumber()});
			}
			if(!StringUtil.isStringEmpty(deliveryDO.getDrsScreenCode()) && !(deliveryDO.getDrsScreenCode().equalsIgnoreCase(DrsConstants.MANUAL_TMF_DRS_SCREEN_CODE))){
				LOGGER.error("DeliveryCommonServiceImpl ::getDrsDetailsForManualDrs ::Business Exception (Not generated here)");
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NO_GENERATED_HERE,new String[]{deliveryDO.getDrsNumber()});
			}

			deliveryTo= DrsConverterUtil.convertHeaderDO2TO(deliveryDO, inputTo);
			prepareDeliveryForPartyTypeDetails(inputTo, deliveryTo);
			prepareGridDetails(deliveryTo, deliveryDO);


		}else{
			//FIXME throw CGBusiness Exception since there are no drs details
			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_DOES_NOT_EXIST, new String[]{inputTo.getDrsNumber()});
		}
		return deliveryTo;
	}

	/**
	 * @param deliveryTo
	 * @param deliveryDO
	 * @throws CGBusinessException
	 */
	private void prepareGridDetails(DeliveryTO deliveryTo, DeliveryDO deliveryDO)
			throws CGBusinessException {
		int childSize=0;
		int updatedConsignmentCounter=0;
		boolean isDrsUpdated=false;
		if(!CGCollectionUtils.isEmpty(deliveryDO.getDeliveryDtlsDO())){
			childSize=deliveryDO.getDeliveryDtlsDO().size();
		}
		if(!StringUtil.isStringEmpty(deliveryDO.getDrsStatus()) && deliveryDO.getDrsStatus().equalsIgnoreCase(DrsConstants.DRS_STATUS_UPDATED)){
			isDrsUpdated=true;
		}
		if(!StringUtil.isEmptyInteger(childSize)){
			List<DeliveryDetailsTO> details= new ArrayList<>(childSize);
			for(DeliveryDetailsDO detailDO:deliveryDO.getDeliveryDtlsDO() ){
				DeliveryDetailsTO detailTO=DrsConverterUtil.convertDetailsDO2TO(detailDO);
				if(isDrsUpdated){
					if(!StringUtil.isStringEmpty(detailDO.getDeliveryStatus()) && !detailDO.getDeliveryStatus().equalsIgnoreCase(UniversalDeliveryContants.DELIVERY_STATUS_OUT_DELIVERY)){
						++updatedConsignmentCounter;
					}
				}
				details.add(detailTO);
			}
			//in order to view all records in the same order which was entered 
			Collections.sort(details);//Sorting records by RowNumber(Sr No: in the screen) 
			deliveryTo.setDtlsTOList(details);
			if(updatedConsignmentCounter ==childSize && (!StringUtil.isStringEmpty(deliveryDO.getDrsScreenCode())&& !(deliveryDO.getDrsScreenCode().equalsIgnoreCase(DrsConstants.MANUAL_PENDING_DRS_SCREEN_CODE) || deliveryDO.getDrsScreenCode().equalsIgnoreCase(DrsConstants.MANUAL_TMF_DRS_SCREEN_CODE)))){
				DrsUtil.setBusinessException4Modification(deliveryTo,UdaanWebErrorConstants.DRS_ALREADY_UPDATED,new String[]{DrsConstants.DRS_STATUS_CLOSED});
			}
		}else{
			//FIXME throw CGBusiness Exception since there are no Grid information
		}
	}
	/**
	 * @param inputTo
	 * @param deliveryTo
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public void prepareDeliveryForPartyTypeDetails(AbstractDeliveryTO inputTo,
			DeliveryTO deliveryTo) throws CGSystemException,
			CGBusinessException {
		String  drsFor= deliveryTo.getDrsFor();
		Map<Integer, String> partyTypeMap = getPartyTypeDetailsForDRS(inputTo,
				drsFor);
		Map<Integer, String> selectedPartyTypeMap= getSelectedPartyTypeDetailsForDRS(deliveryTo);
		if(!CGCollectionUtils.isEmpty(partyTypeMap) && !CGCollectionUtils.isEmpty(selectedPartyTypeMap)){
			partyTypeMap.putAll(selectedPartyTypeMap);
		}else if(!CGCollectionUtils.isEmpty(selectedPartyTypeMap) && CGCollectionUtils.isEmpty(partyTypeMap)){
			partyTypeMap=selectedPartyTypeMap;
		}
		deliveryTo.setPartyTypeMap(partyTypeMap);
	}

	/**
	 * @param deliveryTo
	 * @param drsFor
	 * @param selectedPartyTypeMap
	 * @return saved dropdown details 
	 */
	public Map<Integer, String> getSelectedPartyTypeDetailsForDRS(
			DeliveryTO deliveryTo) {
		Map<Integer, String> selectedPartyTypeMap=null;
		switch(deliveryTo.getDrsFor()){
		
		case DrsConstants.DRS_FOR_BA :
			selectedPartyTypeMap= new HashMap<Integer, String>(1);
			DrsConverterUtil.prepareEachVendorMap(selectedPartyTypeMap, deliveryTo.getBaTO());
			break;
		case DrsConstants.DRS_FOR_CO_COURIER :
			selectedPartyTypeMap= new HashMap<Integer, String>(1);
			DrsConverterUtil.prepareEachVendorMap(selectedPartyTypeMap, deliveryTo.getCoCourierTO());
			break;
		case DrsConstants.DRS_FOR_FIELD_STAFF :
			selectedPartyTypeMap= new HashMap<Integer, String>(1);
			UniversalConverterUtil.prepareEmployeeMapFromEmpTO(selectedPartyTypeMap,deliveryTo.getFieldStaffTO());
			break;
		case DrsConstants.DRS_FOR_FR :
			selectedPartyTypeMap= new HashMap<Integer, String>(1);
			DrsConverterUtil.prepareEachVendorMap(selectedPartyTypeMap, deliveryTo.getFranchiseTO());
			break;
		
		}
		return selectedPartyTypeMap;
	}

	/**
	 * @param inputTo
	 * @param drsFor
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	@Override
	public Map<Integer, String> getPartyTypeDetailsForDRS(
			AbstractDeliveryTO inputTo, String drsFor)
					throws CGSystemException, CGBusinessException {
		OfficeTO officeTO=new OfficeTO();
		officeTO.setOfficeId(inputTo.getLoginOfficeId());
		officeTO.setOfficeCode(inputTo.getLoginOfficeCode());
		officeTO.setCityId(inputTo.getLoginCityId());
		Map<Integer,String> partyTypeMap=getPartyTypeDetailsForDRS(drsFor,officeTO);
		return partyTypeMap;
	}

	/**
	 * Validate consignment by drs number.
	 *
	 * @param inputTo the input to
	 * @return the delivery details to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public DeliveryDetailsTO validateConsignmentByDrsNumber(AbstractDeliveryTO inputTo)throws CGBusinessException, CGSystemException{
		DeliveryDetailsTO detailsTO=null;
		DeliveryDetailsDO  detailsDO = deliveryCommonDAO.getDrsDetailsByConsgAndDrsNumber(inputTo);
		if(!StringUtil.isNull(detailsDO)){
			if(!detailsDO.getDeliveryStatus().equalsIgnoreCase(UniversalDeliveryContants.DELIVERY_STATUS_OUT_DELIVERY)){
				//FIXME :: throws Business Exception it's already Updated 
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_CONSG_ALREADY_UPDATED,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT,inputTo.getConsignmentNumber()});
			}else {
				/** get the DRS status*/
				String status=detailsDO.getDeliveryDO().getDrsStatus();
				/** IF DRS status in not OPEN then DRS should not be updated*/
				if(StringUtil.isStringEmpty(status)){
					DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_INVALID_CONSG_NUMBER,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT,inputTo.getConsignmentNumber()});
				}else if(!StringUtil.isStringEmpty(status)&& status.equalsIgnoreCase(DrsConstants.DRS_STATUS_CLOSED)){
					DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_CONSG_ALREADY_UPDATED,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT,inputTo.getConsignmentNumber()});
				} 
				/** conversion*/
				detailsTO=DrsConverterUtil.convertDetailsDO2TO(detailsDO);
			}
		}else{
			//
			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NO_NOT_BELONG_TO_CONSG,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT,inputTo.getConsignmentNumber(),inputTo.getDrsNumber()});
		}
		return detailsTO;
	}


	/**
	 * Gets the all relations for delivery.
	 *
	 * @return the all relations for delivery
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public Map<Integer, String> getAllRelationsForDelivery()
			throws CGSystemException, CGBusinessException {
		Map<Integer, String> relationMap=null;
		RelationTO relationTO=new  RelationTO();
		List<RelationTO>  relationToList=deliveryUniversalService.getAllRelationsForDelivery(relationTO);
		if(!CGCollectionUtils.isEmpty(relationToList)){
			relationMap =  new HashMap<>(relationToList.size());
			for(RelationTO relation :relationToList){
				relationMap.put(relation.getRelationId(), relation.getRelationDescription());
			}
		}
		return relationMap;
	}

	/**
	 * Gets the all id proofs for delivery.
	 *
	 * @return the all id proofs for delivery
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public Map<Integer, String> getAllIdProofsForDelivery()
			throws CGSystemException, CGBusinessException {
		Map<Integer, String> relationMap=null;
		IdentityProofTypeTO idProofTo=new  IdentityProofTypeTO();
		List<IdentityProofTypeTO>  idProofToList=deliveryUniversalService.getAllIdProofsForDelivery(idProofTo);
		if(!CGCollectionUtils.isEmpty(idProofToList)){
			relationMap =  new HashMap<>(idProofToList.size());
			for(IdentityProofTypeTO idproof :idProofToList){
				relationMap.put(idproof.getIdentityProofTypeId(), idproof.getIdentityProofTypeName());
			}
		}
		return relationMap;
	}

	/**
	 * Gets the all vendor list.
	 *
	 * @param vendorTO the vendor to
	 * @return the all vendor list
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public List<LoadMovementVendorTO> getAllVendorList(LoadMovementVendorTO vendorTO)
			throws CGSystemException, CGBusinessException {
		return deliveryUniversalService.getVendorsList(vendorTO);

	}

	/**
	 * Gets the all co courier vendor list.
	 *
	 * @return the all co courier vendor list
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public Map<Integer, String> getAllCoCourierVendorList(OfficeTO officeTO)
			throws CGSystemException, CGBusinessException {
		LoadMovementVendorTO vendorTo=new LoadMovementVendorTO();
		vendorTo.setVendorType(UdaanCommonConstants.VENDOR_TYPE_CO_COURIER);
		OfficeTO offTO= new OfficeTO();
		offTO.setCityId(officeTO.getCityId());
		vendorTo.setOfficeTO(offTO);
		return DrsConverterUtil.prepareVendorMap(deliveryUniversalService.getVendorDtlsForDrs(vendorTo.getVendorType(), officeTO.getOfficeId())) ;
	}
	/**
	 * Gets the all mode of payment details for delivery.
	 *
	 * @return the all mode of payment details for delivery
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public Map<String, String> getModeOfPaymentDetails()
			throws CGSystemException, CGBusinessException {
		Map<String, String> modeOfPaymentDtlsMap=null;
		List<PaymentModeTO>  modeOfPaymntList=deliveryUniversalService.getModeOfPaymentDetails();
		if(!CGCollectionUtils.isEmpty(modeOfPaymntList)){
			modeOfPaymentDtlsMap =  new HashMap<>(modeOfPaymntList.size());
			for(PaymentModeTO paymentMode :modeOfPaymntList){
				modeOfPaymentDtlsMap.put(paymentMode.getPaymentCode(), paymentMode.getDescription());
			}
		}
		return modeOfPaymentDtlsMap;
	}
	/**
	 * 
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	@Override
	public Map<String, String> getDrsPaymentTypeDtls()
			throws CGSystemException, CGBusinessException {
		return getStandardTypesAsMap(DrsConstants.DRS_PAYMENT_STD_TYPE);
	}

	/**
	 * Gets the manifested date by consg number.
	 * @param isParent the is parent
	 *
	 * @return the manifested date by consg number
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public Date getManifestedDateByConsgNumber(AbstractDeliveryTO dlvInputTO,boolean isParent)throws CGSystemException, CGBusinessException{
		Date mnfstDate=null;
		LOGGER.trace("DeliveryCommonServiceImpl ::getManifestedDateByConsgNumber ::START");
		boolean isDox=false;
		if(!StringUtil.isStringEmpty(dlvInputTO.getConsignmentType())){
			switch(dlvInputTO.getConsignmentType()){
			case CommonConstants.CONSIGNMENT_TYPE_DOCUMENT:
				mnfstDate=deliveryUniversalService.getManifestedDateByConsgNumberForDox(dlvInputTO);
				isDox=true;
				break;
			case CommonConstants.CONSIGNMENT_TYPE_PARCEL:
				if(isParent){
					mnfstDate=deliveryUniversalService.getManifestedDateByConsgNumberParentForPpx(dlvInputTO);
				}else{
					mnfstDate=deliveryUniversalService.getManifestedDateByConsgNumberChildForPpx(dlvInputTO);
				}
				break;

			}
			LOGGER.trace("DeliveryCommonServiceImpl ::getManifestedDateByConsgNumber ::MNFST DATE:"+mnfstDate);
			if(!StringUtil.isNull(mnfstDate)){
				String mnfstNumber=null;
				if(isDox){
					mnfstNumber=deliveryUniversalService.validateInManifestedAndLoggedInOfficeForDOX(dlvInputTO);
				}else{
					if(isParent){
						mnfstNumber=deliveryUniversalService.validateInManifestedAndLoggedInOfficeForParentCNPPX(dlvInputTO);
					}else{
						mnfstNumber=deliveryUniversalService.validateInManifestedAndLoggedInOfficeForChildCNPPX(dlvInputTO);
					}
					LOGGER.trace("DeliveryCommonServiceImpl ::getManifestedDateByConsgNumber ::validateInManifestedAndLoggedInOffice with Manifested Number:["+mnfstNumber+"]And Manifested Date ["+mnfstDate+"]");
				}
				if(StringUtil.isStringEmpty(mnfstNumber)){
					getManifestedTypeByConsignmentAndLoggedInoffice(dlvInputTO);
					DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NOT_MANIFESTED_IN_OFFICE,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT,dlvInputTO.getConsignmentNumber()});
				}
			}else{
				getManifestedTypeByConsignmentAndLoggedInoffice(dlvInputTO);
			}
		}
		LOGGER.trace("DeliveryCommonServiceImpl ::getManifestedDateByConsgNumber ::END");
		return mnfstDate;

	}
	@Override
	public Date getManifestedDateByConsgNumberForThirdPartyManifestDrs(AbstractDeliveryTO dlvInputTO,boolean isParent)throws CGSystemException, CGBusinessException{
		Date mnfstDate=null;
		LOGGER.trace("DeliveryCommonServiceImpl ::getManifestedDateByConsgNumberForThirdPartyManifestDrs ::START");
		boolean isDox=false;
		if(!StringUtil.isStringEmpty(dlvInputTO.getConsignmentType())){
			switch(dlvInputTO.getConsignmentType()){
			case CommonConstants.CONSIGNMENT_TYPE_DOCUMENT:
				mnfstDate=deliveryUniversalService.getManifestedDateByConsgNumberForThirdPartyDox(dlvInputTO);
				isDox=true;
				break;
			case CommonConstants.CONSIGNMENT_TYPE_PARCEL:
				if(isParent){
					mnfstDate=deliveryUniversalService.getManifestedDateByConsgNumberForThirdPartyParentPPx(dlvInputTO);
				}else{
					mnfstDate=deliveryUniversalService.getManifestedDateByConsgNumberForThirdPartyChildPPx(dlvInputTO);
				}
				break;

			}
			LOGGER.trace("DeliveryCommonServiceImpl ::getManifestedDateByConsgNumberForManualDrs ::MNFST DATE:"+mnfstDate);
			if(!StringUtil.isNull(mnfstDate)){
				String mnfstNumber=null;
				if(isDox){
					mnfstNumber=deliveryUniversalService.validateOutManifestedAndLoggedInOfficeForThirdPartyDOX(dlvInputTO);
				}else{
					if(isParent){
						mnfstNumber=deliveryUniversalService.validateOutManifestedAndLoggedInOfficeForParentCNForThirdPartyPPX(dlvInputTO);
					}else{
						mnfstNumber=deliveryUniversalService.validateOutManifestedAndLoggedInOfficeForChildCNThirdPartyPPX(dlvInputTO);
					}
					LOGGER.trace("DeliveryCommonServiceImpl ::getManifestedDateByConsgNumberForManualDrs ::validateInManifestedAndLoggedInOffice with Manifested Number:["+mnfstNumber+"]And Manifested Date ["+mnfstDate+"]");
				}
				if(!StringUtil.isStringEmpty(mnfstNumber)){
					getManifestedTypeByConsignmentAndLoggedInofficeForThirdPartyManifest(dlvInputTO);
				}
			}else{
				getManifestedTypeByConsignmentAndLoggedInofficeForThirdPartyManifest(dlvInputTO);
			}
		}
		LOGGER.trace("DeliveryCommonServiceImpl ::getManifestedDateByConsgNumberForThirdPartyManifestDrs ::END");
		return mnfstDate;

	}

	@Override
	public Date getManifestedDateByComailNumber(String comailNumber)throws CGSystemException, CGBusinessException{
		Date mnfst=null;
		mnfst=deliveryUniversalService.getManifestedDateByComailNumber(comailNumber);
		return mnfst;

	}
	@Override
	public DeliveryConsignmentTO getInManifestedComailDtls(AbstractDeliveryTO inputTo)throws CGSystemException, CGBusinessException{
		return deliveryUniversalService.getInManifestedComailDtls(inputTo);
	}


	/**
	 * Gets the consignment status from delivery.
	 *
	 * @param consignment the consignment
	 * @return the consignment status from delivery
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public String getConsignmentStatusFromDelivery(String consignment)
			throws CGSystemException {
		return deliveryUniversalService.getConsignmentStatusFromDelivery(consignment);
	}

	/**
	 * Checks if is child cn.
	 *
	 * @param consignment the consignment
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public Boolean isChildCn(String consignment) throws CGBusinessException, CGSystemException{
		return deliveryUniversalService.isChildCn(consignment);
	}

	/**
	 * Checks if is consg having child cns.
	 *
	 * @param consignment the consignment
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public Boolean isConsgHavingChildCns(String consignment) throws CGBusinessException, CGSystemException{
		return deliveryUniversalService.isConsgHavingChildCns(consignment);
	}

	/**
	 * Gets the attempt count for consignment.
	 *
	 * @param consignment the consignment
	 * @return the attempt count for consignment
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public Integer getAttemptCountForConsignment(String consignment)
			throws CGBusinessException, CGSystemException {
		return deliveryUniversalService.getAttemptCountForConsignment(consignment);
	}

	/**
	 * Gets the child consg details for drs.
	 *
	 * @param consignment the consignment
	 * @return the child consg details for drs
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public DeliveryConsignmentTO getChildConsgDetailsForDRS(String consignment)
			throws CGBusinessException, CGSystemException {
		return deliveryUniversalService.getChildConsgDetailsForDRS(consignment);
	}
	public DeliveryConsignmentTO getParenetCnFromConsg(String consignment)
			throws CGBusinessException, CGSystemException {
		return deliveryUniversalService.getChildConsgDetailsForDRS(consignment);
	}

	@Override
	public ConsignmentTO getParentConsgDetailsForDRS(String consignment)
			throws CGBusinessException, CGSystemException {
		return deliveryUniversalService.getParentConsgDetailsForDRS(consignment);
	}


	/**
	 * Gets the cities by offices.
	 *
	 * @param officeId the office id
	 * @return the cities by offices
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public CityTO getCitiesByOffices(Integer officeId)
			throws CGSystemException, CGBusinessException {
		return deliveryUniversalService.getCitiesByOffices(officeId);
	}

	/**
	 * Gets the details for dox print.
	 *
	 * @param DrsNumber the drs number
	 * @return the details for dox print
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public DeliveryTO getDetailsForDoxPrint(String drsNumber)throws CGSystemException, CGBusinessException{
		DeliveryTO deliveryTO=null;
		DeliveryDO deliveryDo= deliveryCommonDAO.getDrsDetailsByDrsNumber(drsNumber);
		if(!StringUtil.isNull(deliveryDo)){
			deliveryTO= convertDeliveryHeaderDO2TO(deliveryDo);
			prepareGridDetails(deliveryTO, deliveryDo);
		}
		else{
			LOGGER.error("DeliveryCommonServiceImpl::getDetailsForDoxPrint:: throwing Business Exception Since  Details Not Exist for DRS NO:["+drsNumber+"]");
			throw new CGBusinessException(UdaanWebErrorConstants.ERROR_IN_PRINTING_DRS); 
		}
		return deliveryTO;
	}
	/**
	 * Gets the details for PPX print.
	 *
	 * @param DrsNumber the drs number
	 * @return the details for PPX print
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public DeliveryTO getDetailsForPpxPrint(String drsNumber)throws CGSystemException, CGBusinessException{
		DeliveryTO deliveryTO=null;
		DeliveryDO deliveryDo= deliveryCommonDAO.getDrsDetailsByDrsNumber(drsNumber);
		if(!StringUtil.isNull(deliveryDo)){
			deliveryTO= convertDeliveryHeaderDO2TO(deliveryDo);
			prepareGridDetails(deliveryTO, deliveryDo);
			//Set<String> parentCn= new HashSet<>(deliveryTO.getDtlsTOList().size());
			Set<DeliveryDetailsTO> dtlvDtls= new HashSet<>(deliveryTO.getDtlsTOList().size());
			for(DeliveryDetailsTO dlvDtlsDO:deliveryTO.getDtlsTOList()){
				DeliveryDetailsTO dlvTo= null;
				String parentChildtype=dlvDtlsDO.getParentChildCnType();
				if(!StringUtil.isStringEmpty(parentChildtype)){
					switch(parentChildtype){
					case UniversalDeliveryContants.DRS_CHILD_CONSG_TYPE:
						/*if(dlvDtlsDO.getConsignmentTO()!=null && !parentCn.contains(dlvDtlsDO.getConsignmentTO().getConsgNo()) ){
							dlvTo= new DeliveryDetailsTO();
							try {
								PropertyUtils.copyProperties(dlvTo, dlvDtlsDO);
							} catch (Exception obj) {
								LOGGER.error("DeliveryCommonServiceImpl::getDetailsForPpxPrint::(Property Conversion) Exception",obj);
								throw new CGBusinessException(obj.getMessage());

							}
							dlvTo.setConsignmentNumber(dlvDtlsDO.getConsignmentTO().getConsgNo());
							if(!CGCollectionUtils.isEmpty(dlvDtlsDO.getConsignmentTO().getChildTOSet())){
								dlvTo.setNoOfPieces(dlvDtlsDO.getConsignmentTO().getChildTOSet().size());
							}
							parentCn.add(dlvDtlsDO.getConsignmentTO().getConsgNo());
						}else{
							continue;
						}

						break;*/
					case UniversalDeliveryContants.DRS_PARENT_CONSG_TYPE:
						dlvTo =dlvDtlsDO;
						break;
					}
				}else{

				}
				dtlvDtls.add(dlvTo);
			}
		}
		else{
			LOGGER.error("DeliveryCommonServiceImpl::getDetailsForPpxPrint::throwing Business Exception ");
			throw new CGBusinessException(UdaanWebErrorConstants.ERROR_IN_PRINTING_DRS); 
		}

		return deliveryTO;
	}

	/**
	 * @param deliveryDo
	 * @throws CGBusinessException
	 */
	private DeliveryTO convertDeliveryHeaderDO2TO(DeliveryDO deliveryDo)
			throws CGBusinessException {
		DeliveryTO deliveryTO;
		deliveryTO= new DeliveryTO();
		try {
			PropertyUtils.copyProperties(deliveryTO, deliveryDo);
			if(!StringUtil.isNull(deliveryDo.getCreatedOfficeDO())){
				OfficeTO OfficeTO=new OfficeTO();
				PropertyUtils.copyProperties(OfficeTO, deliveryDo.getCreatedOfficeDO());	
				deliveryTO.setCreatedOfficeTO(OfficeTO);
			}
		} catch (Exception obj) {
			LOGGER.error("DeliveryCommonServiceImpl::convertDeliveryHeaderDO2TO::(Property Conversio delivery Do toTo) Exception",obj);
			throw new CGBusinessException(obj.getMessage());


		}

		//Set Party type Details.
		if(!StringUtil.isStringEmpty(deliveryDo.getDrsFor())){
			switch(deliveryDo.getDrsFor()){
			case DrsConstants.DRS_FOR_BA :
				if(!StringUtil.isNull(deliveryDo.getBaDO())){
					LoadMovementVendorTO baTO = new LoadMovementVendorTO();
					try {
						PropertyUtils.copyProperties(baTO, deliveryDo.getBaDO());
					} catch (Exception obj) {
						LOGGER.error("DeliveryCommonServiceImpl::convertDeliveryHeaderDO2TO::(Property Conversion) Exception",obj);
						throw new CGBusinessException(obj.getMessage());

					}
					deliveryTO.setBaTO(baTO);
				}
				break;
			case DrsConstants.DRS_FOR_CO_COURIER :
				if(!StringUtil.isNull(deliveryDo.getCoCourierDO())){
					LoadMovementVendorTO vendorTo=new LoadMovementVendorTO();
					try {
						PropertyUtils.copyProperties(vendorTo, deliveryDo.getCoCourierDO());
					} catch (Exception obj) {
						LOGGER.error("DeliveryCommonServiceImpl::getDetailsForPpxPrint::(Property Conversion) Exception",obj);
						throw new CGBusinessException(obj.getMessage());


					}
					deliveryTO.setCoCourierTO(vendorTo);
				}
				break;
			case DrsConstants.DRS_FOR_FIELD_STAFF :
				if(!StringUtil.isNull(deliveryDo.getFieldStaffDO())){
					EmployeeTO empTo= new EmployeeTO();
					try {
						PropertyUtils.copyProperties(empTo, deliveryDo.getFieldStaffDO());
					} catch (Exception obj) {
						LOGGER.error("DeliveryCommonServiceImpl::convertDeliveryHeaderDO2TO::(Property Conversion Fieldstaff to Enmpto) Exception",obj);
						throw new CGBusinessException(obj.getMessage());

					}
					deliveryTO.setFieldStaffTO(empTo);
				}
				break;
			case DrsConstants.DRS_FOR_FR :
				if(!StringUtil.isNull(deliveryDo.getFranchiseDO())){
					LoadMovementVendorTO frto = new LoadMovementVendorTO();
					try {
						PropertyUtils.copyProperties(frto, deliveryDo.getFranchiseDO());
					} catch (Exception obj) {
						LOGGER.error("DeliveryCommonServiceImpl::convertDeliveryHeaderDO2TO::(Property Conversion FrDo to FRTO) Exception",obj);
						throw new CGBusinessException(obj.getMessage());

					}
					deliveryTO.setFranchiseTO(frto);
				}
				break;

			}//end of Switch
		}//end of DRS-For
		return deliveryTO;
	}
	/**
	 * getConsignmentTypes
	 * @param consgTypeTO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	@Override
	public List<ConsignmentTypeTO> getConsignmentTypes(ConsignmentTypeTO consgTypeTO)throws CGBusinessException, CGSystemException{
		return deliveryUniversalService.getConsignmentTypes(consgTypeTO);
	}
	@Override
	public DeliveryConsignmentTO getParentConsgDetailsFromConsignment(String consignment)
			throws CGBusinessException, CGSystemException {
		return deliveryUniversalService.getParentConsgDetailsFromConsignment(consignment);
	}

	/**
	 * 
	 * @return MAP
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	@Override
	public Map<String,String> getConsignmentTypeForDelivery()throws CGBusinessException, CGSystemException{
		ConsignmentTypeTO consgTypeTO = new ConsignmentTypeTO();
		Map<String,String> consignmentTypeMap=null;
		List<ConsignmentTypeTO>  consignmentType= deliveryUniversalService.getConsignmentTypes(consgTypeTO);

		if(!CGCollectionUtils.isEmpty(consignmentType)){
			consignmentTypeMap = new HashMap<String, String>(consignmentType.size());
			for(ConsignmentTypeTO cnType:consignmentType){
				consignmentTypeMap.put(cnType.getConsignmentCode(), cnType.getConsignmentName());
			}
		}
		return consignmentTypeMap;
	}
	/**
	 * getDrsStatusByDrsNumber
	 * @param drsHeaderTO
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	@Override
	public String getDrsStatusByDrsNumber(AbstractDeliveryTO drsHeaderTO)throws CGSystemException,CGBusinessException{
		return deliveryCommonDAO.getDrsStatusByDrsNumber(drsHeaderTO);
	}

	@Override
	public boolean isComailValid(String comailNumber)throws CGSystemException,CGBusinessException{
		return deliveryUniversalService.isComailValid(comailNumber);
	}

	@Override
	public boolean isConsignmentValid(String consgNumber)throws CGSystemException,CGBusinessException{
		return deliveryUniversalService.isConsignmentValid(consgNumber);
	}
	@Override
	public String validateInManifestedAndLoggedInOfficeForDOX(AbstractDeliveryTO dlvInputTO)throws CGSystemException,CGBusinessException{
		return deliveryUniversalService.validateInManifestedAndLoggedInOfficeForDOX(dlvInputTO);
	}

	@Override
	public String validateInManifestedAndLoggedInOfficeForParentCNPPX(AbstractDeliveryTO dlvInputTO)throws CGSystemException,CGBusinessException{
		return deliveryUniversalService.validateInManifestedAndLoggedInOfficeForParentCNPPX(dlvInputTO);
	}
	@Override
	public String validateInManifestedAndLoggedInOfficeForChildCNPPX(AbstractDeliveryTO dlvInputTO)throws CGSystemException,CGBusinessException{
		return deliveryUniversalService.validateInManifestedAndLoggedInOfficeForChildCNPPX(dlvInputTO);
	}
	@Override
	public String getConsignmentTypeByConsgNumber(String consignment) throws CGBusinessException, CGSystemException{
		return deliveryUniversalService.getConsignmentTypeByConsgNumber(consignment);
	}
	@Override
	public String getConsignmentStatusFromConsg(String consignment) throws CGBusinessException, CGSystemException{
		return deliveryUniversalService.getConsignmentStatusFromConsg(consignment);
	}


	public List<Page> preparePrint(DeliveryTO drsTo)
			throws CGSystemException,CGBusinessException{
		List<DeliveryDetailsTO> deliveryDetailsTOs = drsTo.getDtlsTOList();
		//Collections.sort(deliveryDetailsTOs);
		Integer size=deliveryDetailsTOs.size();
		Integer maxRow=drsTo.getMaxAllowedPrintRows();
		Integer midSize=maxRow/2;
		List<Page> pageList = new ArrayList<Page>();
		List<PageContent> leftCol = new ArrayList<PageContent>();
		List<PageContent> rightCol = new ArrayList<PageContent>();
		Page page = new Page();
		Integer k=1;
		Integer srNo=1;
		for(DeliveryDetailsTO deliveryDetailsTO : deliveryDetailsTOs){
			PageContent  pageContent =new PageContent();
			//SimpleDateFormat printFormat = new SimpleDateFormat("HH:mm:ss");
			//pageContent.setSrNo(srNo++);
			/*if(!StringUtil.isNull(deliveryDetailsTO.getRowNumber())){
				pageContent.setSrNo(deliveryDetailsTO.getRowNumber());
			}*/
			pageContent.setSrNo(srNo++);
			if(!StringUtil.isNull(deliveryDetailsTO.getConsignmentNumber())){
				pageContent.setConsigment(deliveryDetailsTO.getConsignmentNumber());
			}

			if(!StringUtil.isNull(deliveryDetailsTO.getOriginCityTO().getCityName())){
				pageContent.setCityCode(deliveryDetailsTO.getOriginCityTO().getCityName());
			}
			if(!StringUtil.isNull(deliveryDetailsTO.getDeliveryDate())){
				//pageContent.setTime(printFormat.format(deliveryDetailsTO.getDeliveryDate()));
				pageContent.setTime(DateUtil.getTimeFromDate(deliveryDetailsTO.getDeliveryDate()));
			}
			if(k<=midSize){
				leftCol.add(pageContent);
				page.setFirstCol(leftCol);
				page.setTotalConsg(size);
			}else if(k>midSize && k<=maxRow){
				rightCol.add(pageContent);
				page.setSecondCol(rightCol);
				page.setTotalConsg(size);
				if(k==maxRow){
					pageList.add(page);
					page = new Page();
					leftCol = new ArrayList<PageContent>();
					rightCol = new ArrayList<PageContent>();
					k=0;
				}
			}
			k++;
		}


		if(k!=1 && k<=maxRow){
			pageList.add(page);	
		}

		return pageList;
	}


	public List<DrsCommonPage> prepareCreditPrint(DeliveryTO  drsTo )throws CGSystemException, CGBusinessException{
		List<DeliveryDetailsTO> deliveryDetailsTOs = drsTo.getDtlsTOList();
		Integer size=deliveryDetailsTOs.size();
		Integer maxRow=drsTo.getMaxAllowedPrintRows();
		List<DrsCommonPage> pageList = new ArrayList<DrsCommonPage>();
		List<DrsCommonPageContent> colList = new ArrayList<DrsCommonPageContent>();
		DrsCommonPage page=new DrsCommonPage();
		Integer k=1;

		for(DeliveryDetailsTO deliveryDetailsTO : deliveryDetailsTOs){
			DrsCommonPageContent pageContent=new DrsCommonPageContent();
			//SimpleDateFormat printFormat = new SimpleDateFormat("HH:mm:ss");
			if(!StringUtil.isNull(deliveryDetailsTO.getRowNumber())){
				pageContent.setSrNo(deliveryDetailsTO.getRowNumber());
			}
			if(!StringUtil.isNull(deliveryDetailsTO.getConsignmentNumber())){
				pageContent.setConsigment(deliveryDetailsTO.getConsignmentNumber());
			}

			if(!StringUtil.isNull(deliveryDetailsTO.getOriginCityTO().getCityName())){
				pageContent.setCityCode(deliveryDetailsTO.getOriginCityTO().getCityName());
			}
			if(!StringUtil.isNull(deliveryDetailsTO.getDeliveryDate())){
				//pageContent.setTime(printFormat.format(deliveryDetailsTO.getDeliveryDate()));
				pageContent.setTime(DateUtil.getTimeFromDate(deliveryDetailsTO.getDeliveryDate()));
			}

			if(!StringUtil.isNull(deliveryDetailsTO.getRelationTO())){
				pageContent.setRelationship(deliveryDetailsTO.getRelationTO().getRelationDescription());
			}

			if(!StringUtil.isNull(deliveryDetailsTO.getIdNumber())){
				pageContent.setIdNumber(deliveryDetailsTO.getIdNumber());
			}

			if (!StringUtil.isNull(deliveryDetailsTO.getIdProofTO())) {
				pageContent.setIdProof(deliveryDetailsTO.getIdProofTO().getIdentityProofTypeName());
			}

			if(k<=maxRow){
				colList.add(pageContent);
				page.setFirstCol(colList);
				page.setTotalConsg(size);
			}
			if(k==maxRow){
				pageList.add(page);
				page = new DrsCommonPage();
				colList = new ArrayList<DrsCommonPageContent>();
				k=0;
			}
			k++;
		}

		if(k!=1 && k<=maxRow){
			pageList.add(page);	
		}
		return pageList;
	}

	public  List<DrsCommonPage> prepareCodLcDoxPrint(DeliveryTO  drsTo )throws CGSystemException, CGBusinessException{
		List<DeliveryDetailsTO> deliveryDetailsTOs = drsTo.getDtlsTOList();
		Integer size=deliveryDetailsTOs.size();
		Integer maxRow=drsTo.getMaxAllowedPrintRows();
		List<DrsCommonPage> pageList = new ArrayList<DrsCommonPage>();
		List<DrsCommonPageContent> colList = new ArrayList<DrsCommonPageContent>();
		DrsCommonPage page=new DrsCommonPage();
		Integer k=1;

		for(DeliveryDetailsTO deliveryDetailsTO : deliveryDetailsTOs){
			DrsCommonPageContent pageContent=new DrsCommonPageContent();
			//SimpleDateFormat printFormat = new SimpleDateFormat("HH:mm:ss");
			if(!StringUtil.isNull(deliveryDetailsTO.getRowNumber())){
				pageContent.setSrNo(deliveryDetailsTO.getRowNumber());
			}
			/*pageContent.setConsigment(deliveryDetailsTO.getConsignmentNumber());
			pageContent.setCityCode(deliveryDetailsTO.getOriginCityTO().getCityCode());
				pageContent.setNoOfPeices(deliveryDetailsTO.getNoOfPieces());
			pageContent.setIdNumber(deliveryDetailsTO.getIdNumber());*/

			if(!StringUtil.isNull(deliveryDetailsTO.getConsignmentNumber())){
				pageContent.setConsigment(deliveryDetailsTO.getConsignmentNumber());
			}

			if(!StringUtil.isNull(deliveryDetailsTO.getOriginCityTO().getCityName())){
				pageContent.setCityCode(deliveryDetailsTO.getOriginCityTO().getCityName());
			}

			if(!StringUtil.isEmptyInteger(deliveryDetailsTO.getConsignmentTO().getNoOfPcs())){
				pageContent.setNoOfPeices(deliveryDetailsTO.getConsignmentTO().getNoOfPcs());
			}

			if(!StringUtil.isEmptyDouble(deliveryDetailsTO.getConsignmentTO().getFinalWeight())){
				pageContent.setWeight(deliveryDetailsTO.getConsignmentTO().getFinalWeight());
			}
			/*if(!StringUtil.isNull(deliveryDetailsTO.getConsignmentTO().getCnContents())){
				if(deliveryDetailsTO.getConsignmentTO().getCnContents().getCnContentDesc().equals(DrsConstants.OTHER_CN_CONTENT)){
					pageContent.setContent(deliveryDetailsTO.getConsignmentTO().getOtherCNContent());
				}
				else{
					pageContent.setContent(deliveryDetailsTO.getConsignmentTO().getCnContents().getCnContentDesc());
				}
			}*/
			prepareWeightForPPX(deliveryDetailsTO, pageContent);

			//topay,cod,lc
			if(!StringUtil.isNull(deliveryDetailsTO.getToPayAmount())){
				pageContent.setToPayAmt(deliveryDetailsTO.getToPayAmount());
			}
			if(!StringUtil.isNull(deliveryDetailsTO.getCodAmount())){
				pageContent.setCodAmt(deliveryDetailsTO.getCodAmount());
			}
			if(!StringUtil.isNull(deliveryDetailsTO.getLcAmount())){
				pageContent.setLcAmt(deliveryDetailsTO.getLcAmount());
			}

			if(!StringUtil.isNull(deliveryDetailsTO.getDeliveryDate())){
				//pageContent.setTime(printFormat.format(deliveryDetailsTO.getDeliveryDate()));
				pageContent.setTime(DateUtil.getTimeFromDate(deliveryDetailsTO.getDeliveryDate()));
			}
			if(k<=maxRow){
				colList.add(pageContent);
				page.setFirstCol(colList);
				page.setTotalConsg(size);
			}
			if(k==maxRow){
				pageList.add(page);
				page = new DrsCommonPage();
				colList = new ArrayList<DrsCommonPageContent>();
				k=0;
			}
			k++;
		}

		if(k!=1 && k<=maxRow){
			pageList.add(page);	
		}
		return pageList;
	}

	public  List<DrsCommonPage> prepareNormalPpxPrint(DeliveryTO  drsTo )throws CGSystemException, CGBusinessException{
		List<DeliveryDetailsTO> deliveryDetailsTOs = drsTo.getDtlsTOList();
		Integer size=deliveryDetailsTOs.size();
		Integer maxRow=drsTo.getMaxAllowedPrintRows();
		List<DrsCommonPage> pageList = new ArrayList<DrsCommonPage>();
		List<DrsCommonPageContent> colList = new ArrayList<DrsCommonPageContent>();
		DrsCommonPage page=new DrsCommonPage();
		Integer k=1;

		for(DeliveryDetailsTO deliveryDetailsTO : deliveryDetailsTOs){
			DrsCommonPageContent pageContent=new DrsCommonPageContent();
			//SimpleDateFormat printFormat = new SimpleDateFormat("HH:mm:ss");
			if(!StringUtil.isNull(deliveryDetailsTO.getRowNumber())){
				pageContent.setSrNo(deliveryDetailsTO.getRowNumber());
			}
			if(!StringUtil.isNull(deliveryDetailsTO.getConsignmentNumber())){
				pageContent.setConsigment(deliveryDetailsTO.getConsignmentNumber());
			}

			if(!StringUtil.isNull(deliveryDetailsTO.getOriginCityTO().getCityName())){
				pageContent.setCityCode(deliveryDetailsTO.getOriginCityTO().getCityName());
			}
			prepareWeightForPPX(deliveryDetailsTO, pageContent);


			if(!StringUtil.isNull(deliveryDetailsTO.getDeliveryDate())){
				//pageContent.setTime(printFormat.format(deliveryDetailsTO.getDeliveryDate()));
				pageContent.setTime(DateUtil.getTimeFromDate(deliveryDetailsTO.getDeliveryDate()));
			}

			if(k<=maxRow){
				colList.add(pageContent);
				page.setFirstCol(colList);
				page.setTotalConsg(size);
			}
			if(k==maxRow){
				pageList.add(page);
				page = new DrsCommonPage();
				colList = new ArrayList<DrsCommonPageContent>();
				k=0;
			}
			k++;
		}

		if(k!=1 && k<=maxRow){
			pageList.add(page);	
		}
		return pageList;
	}

	private void prepareWeightForPPX(DeliveryDetailsTO deliveryDetailsTO,
			DrsCommonPageContent pageContent) {
		if(!StringUtil.isNull(deliveryDetailsTO.getConsignmentTO())){
			Integer noOfPices=1;
			Double weight=0.0d;

			if(StringUtil.isStringEmpty(deliveryDetailsTO.getParentChildCnType()) || deliveryDetailsTO.getParentChildCnType().equalsIgnoreCase(UniversalDeliveryContants.DRS_PARENT_CONSG_TYPE)){
				noOfPices=deliveryDetailsTO.getConsignmentTO().getNoOfPcs();
				weight=deliveryDetailsTO.getConsignmentTO().getFinalWeight();
			}else{
				noOfPices=1;
				if(!StringUtil.isNull(deliveryDetailsTO.getConsignmentTO().getChildTOSet())){
					for(ChildConsignmentTO childCN:deliveryDetailsTO.getConsignmentTO().getChildTOSet()){
						if(!StringUtil.isStringEmpty(childCN.getChildConsgNumber()) && childCN.getChildConsgNumber().equalsIgnoreCase(childCN.getChildConsgNumber())){
							weight=childCN.getChildConsgWeight();
							break;
						}

					}
				}

			}
			deliveryDetailsTO.setNoOfPieces(noOfPices);

			if(!StringUtil.isEmptyInteger(noOfPices)){
				pageContent.setNoOfPeices(noOfPices);
			}

			pageContent.setWeight(weight);
			if(!StringUtil.isNull(deliveryDetailsTO.getConsignmentTO().getCnContents())){
				if(deliveryDetailsTO.getConsignmentTO().getCnContents().getCnContentDesc().equals(DrsConstants.OTHER_CN_CONTENT)){
					pageContent.setContent(deliveryDetailsTO.getConsignmentTO().getOtherCNContent());
				}
				else{
					pageContent.setContent(deliveryDetailsTO.getConsignmentTO().getCnContents().getCnContentDesc());
				}
			}

		}
	}


	@Override
	public void getManifestedTypeByConsignmentAndLoggedInoffice(AbstractDeliveryTO dlvInputTO) throws CGBusinessException, CGSystemException{
		LOGGER.trace("DeliveryCommonServiceImpl ::getManifestedTypeByConsignmentAndLoggedInoffice:: START");
		List<?> mapProcessList=deliveryUniversalService.getManifestedTypeByConsignmentAndLoggedInoffice(dlvInputTO);
		if(!CGCollectionUtils.isEmpty(mapProcessList)){
			Object itemType=mapProcessList.get(0);
			Map map= (Map)itemType;
			String manifestType=(String)map.get(StockUniveralConstants.TYPE_NAME);
			String processCode=(String)map.get(StockUniveralConstants.TYPE_ID);
			LOGGER.trace("DeliveryUniversalServiceImpl ::getManifestedTypeByConsignmentAndLoggedInoffice:: manifestType["+manifestType+"] processCode:["+processCode+"]");
			if(!StringUtil.isStringEmpty(processCode)){
				switch(processCode){
				//case CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_DOX:
				//case CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_BPL:
				case CommonConstants.PROCESS_OUT_MANIFEST_PKT_DOX:
				case CommonConstants.PROCESS_OUT_MANIFEST_BAG_DOX:
				case CommonConstants.PROCESS_OUT_MANIFEST_BAG_PARCEL:
				case CommonConstants.PROCESS_OUT_MANIFEST_MATER_BAG:
				case CommonConstants.PROCESS_BRANCH_OUT_MANIFEST:
				case CommonConstants.PROCESS_RTO_RTH:
				case CommonConstants.PROCESS_MIS_ROUTE:
				case CommonConstants.PROCESS_POD:
					LOGGER.error("DeliveryCommonServiceImpl ::getManifestedTypeByConsignmentAndLoggedInoffice:: END with Business Exception");
					DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_ALREADY_IN_MANIFESTED,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT,dlvInputTO.getConsignmentNumber()});
					break;

				}
			}
		}
		LOGGER.trace("DeliveryCommonServiceImpl ::getManifestedTypeByConsignmentAndLoggedInoffice:: END");

	}

	@Override
	public void getManifestedTypeByConsignmentAndLoggedInofficeForThirdPartyManifest(AbstractDeliveryTO dlvInputTO) throws CGBusinessException, CGSystemException{
		LOGGER.trace("DeliveryCommonServiceImpl ::getManifestedTypeByConsignmentAndLoggedInoffice:: START");
		List<?> mapProcessList=deliveryUniversalService.getManifestedTypeByConsignmentAndLoggedInofficeAndManifestNumber(dlvInputTO);
		if(!CGCollectionUtils.isEmpty(mapProcessList)){
			Object itemType=mapProcessList.get(0);
			Map map= (Map)itemType;
			String manifestType=(String)map.get(StockUniveralConstants.TYPE_NAME);
			String processCode=(String)map.get(StockUniveralConstants.TYPE_ID);
			LOGGER.trace("DeliveryUniversalServiceImpl ::getManifestedTypeByConsignmentAndLoggedInofficeForThirdPartyManifest:: manifestType["+manifestType+"] processCode:["+processCode+"]");
			if(!StringUtil.isStringEmpty(processCode)){
				switch(processCode){
				case CommonConstants.PROCESS_OUT_MANIFEST_PKT_DOX:
				case CommonConstants.PROCESS_OUT_MANIFEST_BAG_DOX:
				case CommonConstants.PROCESS_OUT_MANIFEST_BAG_PARCEL:
				case CommonConstants.PROCESS_OUT_MANIFEST_MATER_BAG:
				case CommonConstants.PROCESS_BRANCH_OUT_MANIFEST:
				case CommonConstants.PROCESS_BRANCH_IN_MANIFEST:
				case CommonConstants.PROCESS_RTO_RTH:
				case CommonConstants.PROCESS_MIS_ROUTE:
				case CommonConstants.PROCESS_POD:
					LOGGER.error("DeliveryCommonServiceImpl ::getManifestedTypeByConsignmentAndLoggedInofficeForThirdPartyManifest:: END with Business Exception");
					DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_ALREADY_IN_MANIFESTED,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT,dlvInputTO.getConsignmentNumber()});
					break;

				}
			}
		}
		LOGGER.trace("DeliveryCommonServiceImpl ::getManifestedTypeByConsignmentAndLoggedInoffice:: END");

	}

	@Override
	public String validateStopDelivery(String consgNumber)throws CGBusinessException, CGSystemException{
		String status=null;
		status=deliveryUniversalService.validateStopDelivery(consgNumber);
		LOGGER.trace("DeliveryCommonServiceImpl ::validateStopDelivery :: Consg status from CN Table For the Consg ["+consgNumber+"] ");
		if(!StringUtil.isStringEmpty(status) && status.equalsIgnoreCase(CommonConstants.CONSIGNMENT_STATUS_STOPDELV)){
			ExceptionUtil.prepareBusinessException(UdaanWebErrorConstants.CN_STOP_DELIVERY, new String[]{consgNumber});
		}

		return status;
	}
	@Override
	public String validateForRTOed(String consgNumber)throws CGBusinessException, CGSystemException{
		String status=null;
		status=deliveryUniversalService.validateStopDelivery(consgNumber);
		LOGGER.trace("DeliveryCommonServiceImpl ::validateForRTOed :: Consg status from CN Table For the Consg ["+consgNumber+"] ");
		if(!StringUtil.isStringEmpty(status) && status.equalsIgnoreCase(CommonConstants.CONSIGNMENT_STATUS_RTOH)){
			String product=StockSeriesGenerator.getProductDtls(consgNumber);
			if(!StringUtil.isStringEmpty(product)){
				switch(product){
				case CommonConstants.PRODUCT_SERIES_CASH_COD:
				case CommonConstants.PRODUCT_SERIES_LETTER_OF_CREDIT:
				case CommonConstants.PRODUCT_SERIES_TO_PAY_PARTY_COD:
					ExceptionUtil.prepareBusinessException(UdaanWebErrorConstants.RTOED_CN_NOT_ALLOWED_IN_DRS);
				}
			}

		}
		return status;
	}

	@Override
	public Double getOctroiAmountForDrs(String consignment)
			throws CGBusinessException, CGSystemException{
		return deliveryUniversalService.getOctroiAmountForDrs(consignment);
	}

	@Override
	public Boolean isPaymentCapturedForCn(Integer consgId)
			throws CGBusinessException, CGSystemException {
		return deliveryCommonDAO.isPaymentCapturedForCn(consgId);
	}


/**
 *  * Usage:To check whether the  Consignment  included in Third Party manifest
 *  purpose: to allow CN in Manual Drs ( inspite of CN manifested in Third pary manifest) FOr DOX parent cn
 * @return
 * @throws CGBusinessException
 * @throws CGSystemException
 */@Override
	public String getOutManifestedNumberByLoggedOfficeForTPManifestParentConsgForDOXManualDrs(AbstractDeliveryTO deliveryTO) throws CGBusinessException, CGSystemException{
		String mnfstNumber=null;
		deliveryTO.setQueryName(UniversalDeliveryContants.QRY_GET_MANIFESTED_NUMBER_BY_LOGGED_IN_OFFICE_FOR_PARENT_CONSG_TP_MANIFEST_FOR_MANUAL_DRS);
		deliveryTO.setProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_DOX);
		mnfstNumber= deliveryCommonDAO.getOutManifestedConsignmentNumberByOfficeForTPManifestForManualDrsForParentCn(deliveryTO);
		return mnfstNumber;
}
	/**
	 *  * Usage:To check whether the  Consignment  included in Third Party manifest
	 *  purpose: to allow CN in Manual Drs ( inspite of CN manifested in Third pary manifest) for PPX parent CN
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
 @Override
	public String getOutManifestedNumberByLoggedOfficeForTPManifestParentConsgForPpxManualDrs(AbstractDeliveryTO deliveryTO) throws CGBusinessException, CGSystemException{
		String mnfstNumber=null;
		deliveryTO.setQueryName(UniversalDeliveryContants.QRY_GET_MANIFESTED_NUMBER_BY_LOGGED_IN_OFFICE_FOR_PARENT_CONSG_TP_MANIFEST_FOR_MANUAL_DRS);
		deliveryTO.setProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_BPL);
		mnfstNumber= deliveryCommonDAO.getOutManifestedConsignmentNumberByOfficeForTPManifestForManualDrsForParentCn(deliveryTO);
		return mnfstNumber;
}
	
	/**
	 * * Usage:To check whether the  child Consignment  included in Third Party manifest
	 *  purpose: to allow CN in Manual Drs ( inspite of CN manifested in Third pary manifest) for PPX child CN
	 *
	 * @return the out manifested number by logged office for tp manifest child consg for ppx manual drs
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
 @Override
	public String getOutManifestedNumberByLoggedOfficeForTPManifestChildConsgForPpxManualDrs(AbstractDeliveryTO deliveryTO) throws CGBusinessException, CGSystemException{
		String mnfstNumber=null;
		deliveryTO.setQueryName(UniversalDeliveryContants.QRY_GET_MANIFESTED_NUMBER_BY_LOGGED_IN_OFFICE_FOR_CHILD_CONSG_TP_MANIFEST_FOR_MANUAL_DRS);
		deliveryTO.setProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_BPL);
		mnfstNumber= deliveryCommonDAO.getOutManifestedConsignmentNumberByOfficeForTPManifestForManualDrsForChildCn(deliveryTO);
		return mnfstNumber;
}
 @Override	
	public DeliveryConsignmentTO getOutManifestedConsgnDtls(
			AbstractDeliveryTO inputTo) throws CGSystemException,
			CGBusinessException {
		return deliveryUniversalService.getOutManifestedConsgnDtlsForThirdParty(inputTo);
	}
 
 /**
  * Check if cons is delivered.
  *
  * @param consNO the cons no
  * @param delivryStatus the delivry status
  * @return true, if successful
  */
 @Override
 public String checkIfConsIsDelivered(String consNO, String delivryStatus){
	 String  status = "";
	 String qryName="getDeliveryStatusOfThirdPartyConsignment";
		
	 status= deliveryCommonDAO.checkifConsIsDelivered(qryName, consNO, delivryStatus);
		return status;
 }
	
 @Override
 public void twoWayWriteForDRS(AbstractDeliveryTO deliveryTo)
			throws CGBusinessException {
		if (deliveryTo != null
				&& !StringUtil.isEmptyLong(deliveryTo
						.getDeliveryId())) {
			LOGGER.info("DeliveryCommonServiceImpl::twoWayWrite::Calling TwoWayWrite service to save same in central------------>::::::: with Default DT_TO_CENTRL :["+deliveryTo.getDtToCentral()+"]");
			TwoWayWriteProcessCall.twoWayWriteProcess(
					deliveryTo
					.getDeliveryId(),
					CommonConstants.TWO_WAY_WRITE_PROCESS_DRS);
		}
	}

	@Override
	public DeliveryTO getDetailsForRtoCodPrint(String drsNumber)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("DeliveryCommonServiceImpl :: getDetailsForRtoCodPrint() :: START");
		DeliveryTO deliveryTO = null;
		DeliveryDO deliveryDo = deliveryCommonDAO
				.getDrsDetailsByDrsNumber(drsNumber);
		if (!StringUtil.isNull(deliveryDo)) {
			deliveryTO = convertDeliveryHeaderDO2TO(deliveryDo);
			prepareGridDetails(deliveryTO, deliveryDo);
			Set<DeliveryDetailsTO> dtlvDtls = new HashSet<DeliveryDetailsTO>(
					deliveryTO.getDtlsTOList().size());
			for (DeliveryDetailsTO dlvDtlsTO : deliveryTO.getDtlsTOList()) {
				DeliveryDetailsTO dlvTo = null;
				String parentChildtype = dlvDtlsTO.getParentChildCnType();
				if (!StringUtil.isStringEmpty(parentChildtype)) {
					switch (parentChildtype) {
					case UniversalDeliveryContants.DRS_CHILD_CONSG_TYPE:
					case UniversalDeliveryContants.DRS_PARENT_CONSG_TYPE:
						dlvTo = dlvDtlsTO;
						break;
					}
				}
				dtlvDtls.add(dlvTo);
			}
		} else {
			LOGGER.error("DeliveryCommonServiceImpl :: getDetailsForRtoCodPrint() :: throwing Business Exception ");
			throw new CGBusinessException(
					UdaanWebErrorConstants.ERROR_IN_PRINTING_DRS);
		}
		LOGGER.trace("DeliveryCommonServiceImpl :: getDetailsForRtoCodPrint() :: END");
		return deliveryTO;
	}
	
	@Override
	public SequenceGeneratorConfigTO getCommonSequenceNumber(SequenceGeneratorConfigTO sequenceTO) throws CGBusinessException,CGSystemException{
		return sequenceGeneratorService.getCollectionSequence(sequenceTO);
	}
}

