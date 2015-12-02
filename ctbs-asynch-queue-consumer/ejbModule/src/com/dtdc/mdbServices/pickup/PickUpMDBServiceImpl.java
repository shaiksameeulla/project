package src.com.dtdc.mdbServices.pickup;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import src.com.dtdc.mdbDao.pickup.PickUpMDBDAO;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.dtdc.domain.login.UserDO;
import com.dtdc.domain.master.agent.AgentDO;
import com.dtdc.domain.master.customer.CustAddressDO;
import com.dtdc.domain.master.customer.CustomerDO;
import com.dtdc.domain.master.employee.EmployeeDO;
import com.dtdc.domain.master.franchisee.FranchiseeDO;
import com.dtdc.domain.master.geography.AreaDO;
import com.dtdc.domain.master.geography.CityDO;
import com.dtdc.domain.master.geography.PincodeDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.master.product.ServiceDO;
import com.dtdc.domain.pickup.PickUpDO;
import com.dtdc.domain.pickup.PickUpProductDO;
import com.dtdc.domain.pickup.PickupDailySheetDO;
import com.dtdc.to.pickup.PickUpTO;

// TODO: Auto-generated Javadoc
/**
 * The Class PickUpMDBServiceImpl.
 */
public class PickUpMDBServiceImpl implements PickUpMDBService {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(PickUpMDBServiceImpl.class);

	/** The Constant DATE_FORMAT. */
	private static final String DATE_FORMAT="dd/MM/yyyy";

	/** The Constant ADD_TYPE_CONTACT. */
	private static final String ADD_TYPE_CONTACT="CONTACT";

	/** The Constant SELECT_BOX. */
	private static final String SELECT_BOX="select";

	/** The Constant EMPLOYEE_CODE. */
	private static final String EMPLOYEE_CODE="EMP";

	/** The Constant FRNCHISEE_CODE. */
	private static final String FRNCHISEE_CODE="FR";

	/** The Constant AGENT_CODE. */
	private static final String AGENT_CODE="AG";

	/** The pick up mdbdao. */
	private PickUpMDBDAO pickUpMDBDAO;

	/**
	 * Sets the pick up mdbdao.
	 *
	 * @param pickUpMDBDAO the pickUpMDBDAO to set
	 */
	public void setPickUpMDBDAO(PickUpMDBDAO pickUpMDBDAO) {
		this.pickUpMDBDAO = pickUpMDBDAO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.pickup.PickUpMDBService#savePickUpGeneration(CGBaseTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void savePickUpGeneration(CGBaseTO cgPickUpTo) throws CGSystemException,Exception{
		//PickUpTO pickUpTo=(PickUpTO)cgPickUpTo.getBaseList().get(0);
		if(cgPickUpTo.getBaseList()!=null){
			for (PickUpTO pickUpTo : (List<PickUpTO>)cgPickUpTo.getBaseList()) {
				try{
					savePickUpGeneration(pickUpTo);
				}catch (Exception e) {
					logger.error("PickUpMDBServiceImpl::savePickUpGeneration::Exception occured:"
							+e.getMessage());
					logger.error("Exception occured in::PickUpMDBServiceImpl::savePickUpGeneration::==>"+e.getStackTrace());
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.pickup.PickUpMDBService#savePickUpGeneration(PickUpTO)
	 */
	@Override
	public void savePickUpGeneration(PickUpTO pickUpTo) throws CGSystemException,Exception{
		logger.debug("PickUpMDBServiceImpl::savePickUpGeneration::Start======>");
		EmployeeDO employeeDO = null;
		OfficeDO officeDO = null;
		OfficeDO originOfficeDO = null;
		CustomerDO customerDO = null;
		AgentDO agentDO = null;
		AreaDO pickupAreaDO = null;
		PincodeDO pickupPincodeDO = null;
		CityDO pickupCityDO = null;
		CityDO deliveryCityDO = null;
		FranchiseeDO franchiseeDO = null;
		AreaDO deliveryAreaDO = null;
		PincodeDO deliveryPincodeDO = null;

		try{
			logger.debug("PickUpMDBServiceImpl::savePickUpGeneration::pickUpTo.getPickupReqNum()==>"+pickUpTo.getPickupReqNum());	
			PickUpDO pickUpDO = null;
			if(StringUtils.isNotBlank(pickUpTo.getPickupReqNum())){
				pickUpDO = pickUpMDBDAO.getPickUpDoByPickupReqNum(pickUpTo.getPickupReqNum());			
			}

			if(pickUpDO == null){
				pickUpDO = new PickUpDO();
				pickUpDO.setPickupId(null);
			}

			pickUpDO.setPickupReqNum(pickUpTo.getPickupReqNum());

			if(StringUtils.isNotBlank(pickUpTo.getEmpCode())){
				employeeDO = pickUpMDBDAO.getEmployeeByEmpCode(pickUpTo.getEmpCode());
			}
			if(StringUtils.isNotBlank(pickUpTo.getOfficeCode())) {
				officeDO = pickUpMDBDAO.getOfficeByCode(pickUpTo.getOfficeCode());
			}
			if(StringUtils.isNotBlank(pickUpTo.getAssignOfficeCode())) {
				originOfficeDO = pickUpMDBDAO.getOfficeByCode(pickUpTo.getAssignOfficeCode());
			}
			if(StringUtils.isNotBlank(pickUpTo.getHiddenCustomerCode())) {
				customerDO = pickUpMDBDAO.getCustomerByCode(pickUpTo.getHiddenCustomerCode());
			}
			if(StringUtils.isNotBlank(pickUpTo.getAgentCode())) {
				agentDO = pickUpMDBDAO.getAgentByAgentCode(pickUpTo.getAgentCode());
			}
			if(StringUtils.isNotBlank(pickUpTo.getAreaCode())) {
				pickupAreaDO = pickUpMDBDAO.getAreaByCode(pickUpTo.getAreaCode());
			}
			if(StringUtils.isNotBlank(pickUpTo.getPincode())) {
				pickupPincodeDO = pickUpMDBDAO.getPincodeByPincode(pickUpTo.getPincode());
			}
			if(StringUtils.isNotBlank(pickUpTo.getCityCode())) {
				pickupCityDO = pickUpMDBDAO.getCityByCityCode(pickUpTo.getCityCode());
			}
			if(StringUtils.isNotBlank(pickUpTo.getDeliveryCityCode())) {
				deliveryCityDO = pickUpMDBDAO.getCityByCityCode(pickUpTo.getDeliveryCityCode());
			}
			if(StringUtils.isNotBlank(pickUpTo.getFranchiseeCode())) {
				franchiseeDO = pickUpMDBDAO.geFranchiseeByFranchiseeCode(pickUpTo.getFranchiseeCode());
			}
			if(StringUtils.isNotBlank(pickUpTo.getDeliveryAreaCode())) {
				deliveryAreaDO = pickUpMDBDAO.getAreaByCode(pickUpTo.getDeliveryAreaCode());
			}
			if(StringUtils.isNotBlank(pickUpTo.getDeliveryPincode())) {
				deliveryPincodeDO = pickUpMDBDAO.getPincodeByPincode(pickUpTo.getDeliveryPincode());
			}

			pickUpDO.setEmployeeDO(employeeDO);
			pickUpDO.setOfficeDO(officeDO);
			pickUpDO.setOriginOfficeDO(originOfficeDO);
			pickUpDO.setCustomerDO(customerDO);
			pickUpDO.setAgentDO(agentDO);
			pickUpDO.setPickupAreaDO(pickupAreaDO);
			pickUpDO.setPickupPincodeDO(pickupPincodeDO);
			pickUpDO.setPickupCityDO(pickupCityDO);
			pickUpDO.setDeliveryCityDO(deliveryCityDO);
			pickUpDO.setFranchiseeDO(franchiseeDO);
			pickUpDO.setDeliveryAreaDO(deliveryAreaDO);
			pickUpDO.setDeliveryPincodeDO(deliveryPincodeDO);

			pickUpDO.setPickupTime(pickUpTo.getPickupTime());
			pickUpDO.setPickupType(pickUpTo.getPickupType());

			if(StringUtils.isNotBlank(pickUpTo.getPickupDate())){
				pickUpDO.setPickupDate(DateFormatterUtil.getDateFromStringDDMMYYY(pickUpTo.getPickupDate()));
			}
			if(pickUpTo.getActualWeight()!=null){
				pickUpDO.setActualWeight(pickUpTo.getActualWeight().toString());
			}
			pickUpDO.setNoOfParcels(pickUpTo.getNoOfParcels());
			pickUpDO.setRemarks(pickUpTo.getRemarks());
			pickUpDO.setCurStatus(pickUpTo.getPickupStatus());
			pickUpDO.setValidation(pickUpTo.getValidation());


			logger.debug("PickUpMDBServiceImpl::savePickUpGeneration::pickUpTo.getPickupGenTime()==>"+pickUpTo.getPickupGenTime());	
			if(StringUtils.isNotBlank(pickUpTo.getPickupGenTime())){		
				logger.debug("PickUpMDBServiceImpl::savePickUpGeneration::Time.valueOf(pickUpTo.getPickupGenTime())==> If block");			
				pickUpDO.setPickupGenTime(Time.valueOf(pickUpTo.getPickupGenTime()));//need to check
			}
			logger.debug("PickUpMDBServiceImpl::savePickUpGeneration::pickUpTo.getPickupGenDate()==>"+pickUpTo.getPickupGenDate());	
			if(StringUtils.isNotBlank(pickUpTo.getPickupGenDate())){
				pickUpDO.setPickupGenDate(DateFormatterUtil.getDateFromStringDDMMYYY(pickUpTo.getPickupGenDate()));
			}

			logger.debug("PickUpMDBServiceImpl::savePickUpGeneration::pickUpTo.getNonPickupReasonId()==>"+pickUpTo.getNonPickupReasonId());			
			if(pickUpTo.getNonPickupReasonId()!=null && Integer.valueOf(pickUpTo.getNonPickupReasonId())!=0){
				pickUpDO.setNonPickupReasonId(pickUpTo.getNonPickupReasonId()); //need to check
				logger.debug("PickUpMDBServiceImpl::savePickUpGeneration::pickUpTo.getNonPickupReasonId()==>If Block"+pickUpTo.getNonPickupReasonId());			
			}
			logger.debug("PickUpMDBServiceImpl::savePickUpGeneration::pickUpDO.getNonPickupReasonId()==>"+pickUpDO.getNonPickupReasonId());			

			logger.debug("PickUpMDBServiceImpl::savePickUpGeneration::pickUpTo.getReshDate()==>"+pickUpTo.getReshDate());	
			if(StringUtils.isNotBlank(pickUpTo.getReshDate())){
				pickUpDO.setReshDate(DateFormatterUtil.getDateFromStringDDMMYYY(pickUpTo.getReshDate()));
			}

			logger.debug("PickUpMDBServiceImpl::savePickUpGeneration::pickUpTo.getPickupNonAssReasonId==>"+pickUpTo.getPickupNonAssReasonId());			
			if(pickUpTo.getPickupNonAssReasonId()!=null && Integer.valueOf(pickUpTo.getPickupNonAssReasonId())!=0){
				pickUpDO.setPickupNonAssReasonId(pickUpTo.getPickupNonAssReasonId());//need to check
				logger.debug("PickUpMDBServiceImpl::savePickUpGeneration::pickUpTo.getPickupNonAssReasonId==>If Block"+pickUpTo.getPickupNonAssReasonId());	
			}
			logger.debug("PickUpMDBServiceImpl::savePickUpGeneration::pickUpDO.getPickupNonAssReasonId==>"+pickUpDO.getPickupNonAssReasonId());			

			pickUpDO.setAssignPickupTime(pickUpTo.getAssignPickupTime());
			logger.debug("PickUpMDBServiceImpl::savePickUpGeneration::pickUpTo.getAssignPickupDate()==>"+pickUpTo.getAssignPickupDate());	
			if(StringUtils.isNotBlank(pickUpTo.getAssignPickupDate())){
				pickUpDO.setAssignPickupDate(DateFormatterUtil.getDateFromStringDDMMYYY(pickUpTo.getAssignPickupDate()));
			}

			pickUpDO.setReshTime(pickUpTo.getReshTime());
			pickUpDO.setCustRefNum(pickUpTo.getCustRefNum());
			pickUpDO.setCancelReason(pickUpTo.getCancelReason());
			pickUpDO.setPickupChannel(pickUpTo.getDomesticInt());
			pickUpDO.setSourceType(pickUpTo.getPickupSource());
			pickUpDO.setDocumentType(pickUpTo.getProductType());
			pickUpDO.setAssignTo(pickUpTo.getAssignedTo());
			pickUpDO.setNodeId(pickUpTo.getNodeId());
			pickUpDO.setPickupConsinerName(pickUpTo.getCustomerName());
			pickUpDO.setPickupAddress1(pickUpTo.getAddress1());
			pickUpDO.setPickupAddress2(pickUpTo.getAddress2());
			pickUpDO.setPickupAddress3(pickUpTo.getAddress3());
			pickUpDO.setPickupContactNo(pickUpTo.getContactNo());
			pickUpDO.setPickupEmail(pickUpTo.getEmail());
			pickUpDO.setDeliveryConsineeName(pickUpTo.getConsigneeName());
			pickUpDO.setDeliveryAddress1(pickUpTo.getDeliveryAddress1());
			pickUpDO.setDeliveryAddress2(pickUpTo.getDeliveryAddress2());
			pickUpDO.setDeliveryAddress3(pickUpTo.getDeliveryAddress3());
			pickUpDO.setDeliveryContactNo(pickUpTo.getDeliveryContactNo());
			pickUpDO.setActualPkupTime(pickUpTo.getActualPkupTime());
			pickUpDO.setPickupDlvFlag(pickUpTo.getPickupDlvFlag());
			pickUpDO.setContent(pickUpTo.getContent());
			pickUpDO.setSourceValue(pickUpTo.getPickupSourceData());			
			logger.debug("PickUpMDBServiceImpl::savePickUpGeneration::pickUpTo.getCancelDateStr()==>"+pickUpTo.getCancelDateStr());
			if(StringUtils.isNotBlank(pickUpTo.getCancelDateStr())){
				pickUpDO.setCancelDate(DateFormatterUtil.getDateFromStringDDMMYYY(pickUpTo.getCancelDateStr()));
			}
			pickUpDO.setCancelTime(pickUpTo.getCancelTime());
			pickUpDO.setCommMode(pickUpTo.getCommMode());
			pickUpDO.setDiFlag("N");
			//pickUpDO.setDbServer("N");
			//pickUpDO.setReadByLocal("N");

			logger.debug("PickUpMDBServiceImpl::savePickUpGeneration::going to save pickup");			
			pickUpMDBDAO.savePickUpGeneration(pickUpDO);
			logger.debug("PickUpMDBServiceImpl::savePickUpGeneration::successfully saved pickup");	
		}catch (Exception e) {
			logger.error("PickUpMDBServiceImpl::savePickUpGeneration::Exception occured:"
					+e.getMessage());
			logger.error("Exception occured in::PickUpMDBServiceImpl::savePickUpGeneration::==>"+e.getStackTrace());
			throw e;
		}
		logger.debug("PickUpMDBServiceImpl::savePickUpGeneration::End======>");
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.pickup.PickUpMDBService#modifyPickUpRequest(CGBaseTO)
	 */
	@Override
	public PickUpTO modifyPickUpRequest(CGBaseTO cgPickUpTo) throws CGBusinessException, Exception {
		PickUpTO pickUpTo=(PickUpTO)cgPickUpTo.getBaseList().get(0);
		return modifyPickUpRequest(pickUpTo);
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.pickup.PickUpMDBService#modifyPickUpRequest(PickUpTO)
	 */
	@Override
	public PickUpTO modifyPickUpRequest(PickUpTO to) throws CGBusinessException,Exception {
		//Integer pickUpId = to.getPickupId();
		//select pickup by id
		//PickUpDO pickUpDO=pickUpMDBDAO.getPickUp(pickUpId);
		PickUpDO pickUpDO=pickUpMDBDAO.getPickUpByCompositeKey(to);

		/*//copy this DO to Log DO
		PickUpHistoryDO historyDo=new PickUpHistoryDO();
		PropertyUtils.copyProperties(historyDo,pickUpDO);
		historyDo.setLogDate(new Date());
		historyDo.setLogTime(currentTime());
		pickUpMDBDAO.updatePickUpHistoryDo(historyDo);*/

		//Now update the existing DO with new value and save
		pickUpDO=copyPickupTOToPickupDO(to,pickUpDO);
		//call update after updating the to
		pickUpMDBDAO.updatePickUpDo(pickUpDO);
		PickUpTO newto=new PickUpTO();
		return copyPickupDOToPickupTO(newto,pickUpDO);
	}

	/**
	 * Copy pickup to to pickup do.
	 *
	 * @param to the to
	 * @param pickUpDO the pick up do
	 * @return the pick up do
	 */
	private PickUpDO copyPickupTOToPickupDO(PickUpTO to,PickUpDO pickUpDO){

		if(pickUpDO!=null){
			if(to.getPickupStatus()!=null && !to.getPickupStatus().equalsIgnoreCase(SELECT_BOX)) {
				pickUpDO.setCurStatus(to.getPickupStatus());
			}
			//f(to.getConsignNo()!=null && !to.getConsignNo().trim().equals(""))
			//	pickUpDO.setConsignNo(to.getConsignNo());
			if(to.getNonPickupReasonId()!=null  && Integer.valueOf(to.getNonPickupReasonId())!=0) {
				pickUpDO.setNonPickupReasonId(to.getNonPickupReasonId());
			}
			if(to.getReshTime()!=null && !to.getReshTime().trim().equals("")) {
				pickUpDO.setReshTime(to.getReshTime());
			}
			if(to.getReshDate()!=null && !to.getReshDate().trim().equals("")) {
				pickUpDO.setReshDate(DateFormatterUtil.stringToDateFormatter(to.getReshDate(),DATE_FORMAT));
			}
			if(to.getPickupTime()!=null && !to.getPickupTime().trim().equals("")) {
				pickUpDO.setPickupTime(to.getPickupTime());
			}
			if(to.getPickupDate()!=null && !to.getPickupDate().trim().equals("")) {
				pickUpDO.setPickupDate(DateFormatterUtil.stringToDateFormatter(to.getPickupDate(),DATE_FORMAT));
			}
			if(to.getAssignPickupTime()!=null && !to.getAssignPickupTime().trim().equals("")) {
				pickUpDO.setAssignPickupTime(to.getAssignPickupTime());
			}
			if(to.getAssignPickupDate()!=null && !to.getAssignPickupDate().trim().equals("")) {
				pickUpDO.setAssignPickupDate(DateFormatterUtil.stringToDateFormatter(to.getAssignPickupDate(),DATE_FORMAT));
			}
			if(to.getCancelReason()!=null && !to.getCancelReason().trim().equals("")) {
				pickUpDO.setCancelReason(to.getCancelReason());
			}
			if(to.getPickupNonAssReasonId()!=null && Integer.valueOf(to.getPickupNonAssReasonId())!=0) {
				pickUpDO.setPickupNonAssReasonId(to.getPickupNonAssReasonId());
			}
			if(to.getAssignOfficeCode()!=null){
				OfficeDO offc=pickUpMDBDAO.getOfficeByCode(to.getAssignOfficeCode());
				pickUpDO.setOfficeDO(offc);
			}

			//based on that store respective IDs
			if(to.getAssignedTo()!=null){
				//ASSIGN TO EMP / FR / AGENT
				pickUpDO.setAssignTo(to.getAssignedTo());

				if(to.getAssignedTo().equalsIgnoreCase(EMPLOYEE_CODE)){
					EmployeeDO emp=pickUpMDBDAO.getEmployee(Integer.valueOf(to.getAssignEmpName()));
					pickUpDO.setEmployeeDO(emp);
					pickUpDO.setFranchiseeDO(null);
					pickUpDO.setAgentDO(null);
				}else if(to.getAssignedTo().equalsIgnoreCase(FRNCHISEE_CODE)){
					FranchiseeDO fr=pickUpMDBDAO.getFranchisee(Integer.valueOf(to.getAssignFrName()));
					pickUpDO.setFranchiseeDO(fr);
					pickUpDO.setEmployeeDO(null);
					pickUpDO.setAgentDO(null);
				}else if(to.getAssignedTo().equalsIgnoreCase(AGENT_CODE)){
					AgentDO ag=pickUpMDBDAO.getAgent(Integer.valueOf(to.getAssignAgName()));
					pickUpDO.setAgentDO(ag);
					pickUpDO.setEmployeeDO(null);
					pickUpDO.setFranchiseeDO(null);
				}
			}
		}
		return pickUpDO;
	}

	/**
	 * Copy pickup do to pickup to.
	 *
	 * @param to the to
	 * @param pickUpDO the pick up do
	 * @return the pick up to
	 */
	private PickUpTO copyPickupDOToPickupTO(PickUpTO to,PickUpDO pickUpDO){

		if(pickUpDO!=null){
			to.setPickupId(pickUpDO.getPickupId());
			to.setPickupReqNum(pickUpDO.getPickupReqNum());
			to.setCustRefNum(pickUpDO.getCustRefNum());
			to.setAppxWeight(pickUpDO.getActualWeight());
			to.setRemarks(pickUpDO.getRemarks());
			to.setPickupTime(pickUpDO.getPickupTime());
			to.setDomesticInt(pickUpDO.getPickupChannel());
			to.setAssignedTo(pickUpDO.getAssignTo());
			if(pickUpDO.getAgentDO()!=null) {
				to.setAssignAgName(pickUpDO.getAgentDO().getAgentId()+"");
			}
			if(pickUpDO.getFranchiseeDO()!=null) {
				to.setAssignFrName(pickUpDO.getFranchiseeDO().getFranchiseeId()+"");
			}
			if(pickUpDO.getEmployeeDO()!=null) {
				to.setAssignEmpName(pickUpDO.getEmployeeDO().getEmployeeId()+"");
			}

			if(pickUpDO.getPickupGenTime()!=null) {
				to.setPickupGenTime(pickUpDO.getPickupGenTime()+"");
			}

			to.setReshTime(pickUpDO.getReshTime());
			to.setNoOfParcels(pickUpDO.getNoOfParcels());
			to.setPickupStatus(pickUpDO.getCurStatus());
			//to.setConsignNo(pickUpDO.getConsignNo());
			to.setNonPickupReasonId(pickUpDO.getNonPickupReasonId());
			to.setPickupNonAssReasonId(pickUpDO.getPickupNonAssReasonId());
			to.setAssignPickupTime(pickUpDO.getAssignPickupTime());
			to.setCancelReason(pickUpDO.getCancelReason());
			if(pickUpDO.getAssignPickupDate()!=null){
				to.setAssignPickupDate(getDDMMYYYYDateString(pickUpDO.getAssignPickupDate()));
			}
			if(pickUpDO.getPickupDate() != null){
				to.setPickupDate(getDDMMYYYYDateString(pickUpDO.getPickupDate()));
			}							
			if(pickUpDO.getPickupGenDate()!=null){
				to.setPickupGenDate(getDDMMYYYYDateString(pickUpDO.getPickupGenDate()));
			}
			if(pickUpDO.getReshDate()!=null){
				to.setReshDate(getDDMMYYYYDateString(pickUpDO.getReshDate()));
			}
			if(pickUpDO.getCustomerDO()!=null){
				to.setCustomerCode(pickUpDO.getCustomerDO().getCustomerCode());
				to.setCustomerName(pickUpDO.getCustomerDO().getFirstName());
				to.setEmail(pickUpDO.getCustomerDO().getEmail());
				to.setCustomerType(pickUpDO.getCustomerDO().getCustType());
				to.setContactNo(pickUpDO.getCustomerDO().getPhone());
				if(pickUpDO.getCustomerDO().getAddresses()!=null){
					Set<CustAddressDO> addressSet = pickUpDO.getCustomerDO().getAddresses();
					for (CustAddressDO custAddressDO : addressSet) {
						if(custAddressDO.getAddressType().endsWith(ADD_TYPE_CONTACT)){
							to.setCustomerAddress(custAddressDO.getBuildingName()+","+custAddressDO.getStreet1());	
						}
					}
				}
			}
			if(pickUpDO.getCustomerDO()!=null){
				Set<CustAddressDO> addressSet=pickUpDO.getCustomerDO().getAddresses();
				for (CustAddressDO custAddressDO : addressSet) {
					if(custAddressDO.getAddressType().endsWith(ADD_TYPE_CONTACT)){
						if(custAddressDO.getArea()!=null && custAddressDO.getArea().getPincode()!=null) {
							to.setPincode(custAddressDO.getArea().getPincode().getPincode());
						}
					}
				}
			}
			if(pickUpDO.getEmployeeDO()!=null){
				to.setPbCode(pickUpDO.getEmployeeDO().getEmpCode());
			}
			if(pickUpDO.getOfficeDO()!=null){
				to.setOfficeId(pickUpDO.getOfficeDO().getOfficeId());
				to.setAssignOfficeCode(pickUpDO.getOfficeDO().getOfficeCode());
				to.setAssignOfficeType(pickUpDO.getOfficeDO().getOffType());
				to.setAssignOfficeName(pickUpDO.getOfficeDO().getOfficeName());
				//to.setOriginOfficeCode(pickUpDO.getOfficeDO().getOfficeCode());	
				//to.setOriginOfficeType(pickUpDO.getOfficeDO().getOffType());	
			}
			if(pickUpDO.getPickupAreaDO()!=null){
				to.setDeliveryAreaCode(pickUpDO.getPickupAreaDO().getAreaCode());
				to.setDeliveryAreaName(pickUpDO.getPickupAreaDO().getAreaName());
				if(pickUpDO.getPickupAreaDO().getPincode()!=null) {
					to.setDeliveryPincode(pickUpDO.getPickupAreaDO().getPincode().getPincode());
				}
			}
		}
		return to;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.pickup.PickUpMDBService#saveDailyPickupData(CGBaseTO)
	 */
	@Override
	public void saveDailyPickupData(CGBaseTO cgPickUpTo) throws Exception, CGBusinessException {
		//PickUpTO pickUpTo=(PickUpTO)cgPickUpTo.getBaseList().get(0);
		//saveDailyPickupData(pickUpTo);
		logger.debug("PickUpMDBServiceImpl::saveDailyPickupData::Begin======>");
		if(cgPickUpTo.getBaseList()!=null){
			for (PickUpTO pickUpTo : (List<PickUpTO>)cgPickUpTo.getBaseList()) {
				try{
					saveDailyPickupData(pickUpTo);
				}catch (Exception e) {
					logger.error("PickUpMDBServiceImpl::saveDailyPickupData::Exception occured:"
							+e.getMessage());
					logger.error("Exception occured in::PickUpMDBServiceImpl::saveDailyPickupData::==>"+e.getStackTrace());
				}
			}
		}
		logger.debug("PickUpMDBServiceImpl::saveDailyPickupData::Stop======>");
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.pickup.PickUpMDBService#saveDailyPickupData(PickUpTO)
	 */
	@Override
	public void saveDailyPickupData(PickUpTO pickUpTO) throws Exception, CGBusinessException{
		logger.debug("PickUpMDBServiceImpl::saveDailyPickupData::Start======>");
		List<PickupDailySheetDO> pickupDailySheetDOList = createPickupDailySheetDOListFromPickupTO(pickUpTO);
		logger.debug("PickUpMDBServiceImpl::saveDailyPickupData::pickupDailySheetDOList======>" + pickupDailySheetDOList);
		if(pickupDailySheetDOList!=null){
			pickUpMDBDAO.saveOrUpdatePickupDailySheetDOList(pickupDailySheetDOList);
		}		
		logger.debug("PickUpMDBServiceImpl::saveDailyPickupData::End======>");
	}

	/**
	 * Creates the pickup daily sheet do list from pickup to.
	 *
	 * @param pickUpTO the pick up to
	 * @return the list
	 * @throws Exception the exception
	 */
	private List<PickupDailySheetDO> createPickupDailySheetDOListFromPickupTO(PickUpTO pickUpTO) throws Exception{
		logger.debug("PickUpMDBServiceImpl::createPickupDailySheetDOListFromPickupTO::Start======>");
		List<PickupDailySheetDO> pickupDailySheetDOList = new ArrayList<PickupDailySheetDO>();

		try {	
			logger.debug("PickUpMDBServiceImpl::createPickupDailySheetDOListFromPickupTO::pickUpTO.getDailyPickupSheetNo()==>"
					+pickUpTO.getDailyPickupSheetNo());
			Integer pickupDailySheetId[] = null;
			if(StringUtils.isNotBlank(pickUpTO.getDailyPickupSheetNo())){
				pickupDailySheetId = pickUpMDBDAO.getPickupDailySheetIdByPickupSheetNo(pickUpTO.getDailyPickupSheetNo());
			}
			logger.debug("PickUpMDBServiceImpl::createPickupDailySheetDOListFromPickupTO::pickupDailySheetId[]==>"+pickupDailySheetId);

			for(int i=0;i<pickUpTO.getPickupSheetRemk().length;i++){
				try {
					PickupDailySheetDO dailySheetDO=new PickupDailySheetDO();
					dailySheetDO.setPickupSheetNo(pickUpTO.getDailyPickupSheetNo());					

					logger.debug("PickUpMDBServiceImpl::createPickupDailySheetDOListFromPickupTO::pickUpTO.getCreatedDateTime()["+i+"]==>"+pickUpTO.getCreatedDateTime()[i]);
					if(pickUpTO.getCreatedDateTime()[i]!=0){
						Timestamp createdDateTime = new Timestamp(pickUpTO.getCreatedDateTime()[i]);
						dailySheetDO.setCreatedDateTime(createdDateTime);
						logger.debug("PickUpMDBServiceImpl::createPickupDailySheetDOListFromPickupTO::If block createdDateTime==>"+createdDateTime);
					}
					logger.debug("PickUpMDBServiceImpl::createPickupDailySheetDOListFromPickupTO::dailySheetDO.setCreatedDateTime==>"+dailySheetDO.getCreatedDateTime());

					dailySheetDO.setPickupSheetRemark(pickUpTO.getPickupSheetRemk()[i]);

					logger.debug("PickUpMDBServiceImpl::createPickupDailySheetDOListFromPickupTO::pickUpTO.getPickupReqNumAr()["
							+i+"]==>"+pickUpTO.getPickupReqNumAr()[i]);
					PickUpDO pickupDO = null;
					if(StringUtils.isNotBlank(pickUpTO.getPickupReqNumAr()[i])){
						pickupDO = pickUpMDBDAO.getPickUpDoByPickupReqNum(pickUpTO.getPickupReqNumAr()[i]);
					}					
					logger.debug("PickUpMDBServiceImpl::createPickupDailySheetDOListFromPickupTO::pickupDO==>"+pickupDO);				
					dailySheetDO.setPickupDO(pickupDO);
					dailySheetDO.setDiFlag("N");
					//dailySheetDO.setDbServer("N");
					//dailySheetDO.setReadByLocal("N");

					if(pickupDailySheetId!=null && pickupDailySheetId.length>i){
						logger.debug("PickUpMDBServiceImpl::createPickupDailySheetDOListFromPickupTO::pickupDailySheetId["+i+"]==>"+pickupDailySheetId[i]);
						dailySheetDO.setPickupDailySheetId(pickupDailySheetId[i]);
					}else{
						logger.debug("PickUpMDBServiceImpl::createPickupDailySheetDOListFromPickupTO::pickupDailySheetId[i]==> else block setPickupDailySheetId null");
						dailySheetDO.setPickupDailySheetId(null);			
					}	
					pickupDailySheetDOList.add(dailySheetDO);
				} catch (Exception e) {
					logger.error("PickUpMDBServiceImpl::createPickupDailySheetDOListFromPickupTO::Exception occured:"
							+e.getMessage());
					logger.error("Exception occured in::PickUpMDBServiceImpl::createPickupDailySheetDOListFromPickupTO::Exception::==>"+e.getStackTrace());
					throw e;
				}

			}
		} catch (Exception e) {
			logger.error("PickUpMDBServiceImpl::createPickupDailySheetDOListFromPickupTO::Exception occured:"
					+e.getMessage());
			logger.error("Exception occured in::PickUpMDBServiceImpl::createPickupDailySheetDOListFromPickupTO::==>"+e.getStackTrace());
			throw e;
		}
		logger.debug("PickUpMDBServiceImpl::createPickupDailySheetDOListFromPickupTO::End======>");
		return pickupDailySheetDOList;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.pickup.PickUpMDBService#savePickupProductChildData(CGBaseTO)
	 */
	@Override
	public void savePickupProductChildData(CGBaseTO cgPickUpTo) throws Exception {
		/*PickUpTO pickUpTo=(PickUpTO)cgPickUpTo.getBaseList().get(0);
		savePickupProductChildData(pickUpTo);*/
		if(cgPickUpTo.getBaseList()!=null){
			for (PickUpTO pickUpTo : (List<PickUpTO>)cgPickUpTo.getBaseList()) {
				try{
					savePickupProductChildData(pickUpTo);
				}catch (Exception e) {
					logger.error("PickUpMDBServiceImpl::savePickupProductChildData::Exception occured:"
							+e.getMessage());
					logger.error("Exception occured in::PickUpMDBServiceImpl::savePickupProductChildData::==>"+e.getStackTrace());
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.pickup.PickUpMDBService#savePickupProductChildData(PickUpTO)
	 */
	@Override
	public void savePickupProductChildData(PickUpTO pickUpTO) throws Exception{
		logger.debug("PickUpMDBServiceImpl::savePickupProductChildData::End======>");
		createAndSavePickUpProductDOListFromPickupTO(pickUpTO);		
		logger.debug("PickUpMDBServiceImpl::savePickupProductChildData::End======>");
	}


	/**
	 * Creates the and save pick up product do list from pickup to.
	 *
	 * @param pickUpTO the pick up to
	 * @throws Exception the exception
	 */
	private void createAndSavePickUpProductDOListFromPickupTO(PickUpTO pickUpTO) throws Exception{
		logger.debug("PickUpMDBServiceImpl::createAndSavePickUpProductDOListFromPickupTO::Start======>");
		List<PickUpProductDO> pickUpProductDOList = new ArrayList<PickUpProductDO>();
		Integer pickupDailySheetId = null;
		Integer pickupProductIds[] = null;
		PickUpDO pickupDO = null;
		UserDO userDO = null;
		try {		
			logger.debug("PickUpMDBServiceImpl::createAndSavePickUpProductDOListFromPickupTO::pickUpTO.getPickupReqNum()==>"+pickUpTO.getPickupReqNum());
			if(StringUtils.isNotBlank(pickUpTO.getPickupReqNum())){
				pickupDO = pickUpMDBDAO.getPickUpDoByPickupReqNum(pickUpTO.getPickupReqNum());
			}
			logger.debug("PickUpMDBServiceImpl::createAndSavePickUpProductDOListFromPickupTO::pickupDO==>"+pickupDO);
			if(pickupDO!=null){
				pickupDO.setActualPkupTime(pickUpTO.getActualPkupTime());
				pickupDO.setCurStatus(pickUpTO.getPickupStatus());
				pickupDO.setRemarks(pickUpTO.getRemarks());
				pickupDO.setPickupNonAssReasonId(pickUpTO.getPickupNonAssReasonId());//need to check
				pickupDO.setAssignPickupTime(pickUpTO.getDailyPickupTime());
				if(StringUtils.isNotBlank(pickUpTO.getDailyPickupDate())){
					pickupDO.setAssignPickupDate(DateFormatterUtil.getDateFromStringDDMMYYY(pickUpTO.getDailyPickupDate()));
				}	
				if(StringUtils.isNotBlank(pickUpTO.getReshDate())){
					pickupDO.setReshDate(DateFormatterUtil.getDateFromStringDDMMYYY(pickUpTO.getReshDate()));
				}

				logger.debug("PickUpMDBServiceImpl::createAndSavePickUpProductDOListFromPickupTO::pickupDO.getPickupId()==>"+pickupDO.getPickupId());
				pickupDailySheetId = pickUpMDBDAO.getDailySheetIdByPickupId(pickupDO.getPickupId());
				logger.debug("PickUpMDBServiceImpl::createAndSavePickUpProductDOListFromPickupTO::pickupDailySheetId==>"+pickupDailySheetId);
				pickupProductIds = pickUpMDBDAO.getPickupProductIdByPickupId(pickupDO.getPickupId());
				logger.debug("PickUpMDBServiceImpl::createAndSavePickUpProductDOListFromPickupTO::pickupProductIds==>"+pickupProductIds);
			}

			logger.debug("PickUpMDBServiceImpl::createAndSavePickUpProductDOListFromPickupTO::pickUpTO.getUserCode()==>"+pickUpTO.getUserCode());
			if(StringUtils.isNotBlank(pickUpTO.getUserCode())){
				userDO = pickUpMDBDAO.getUserDOByUserCode(pickUpTO.getUserCode());
			}
			PickupDailySheetDO pickupDailySheetDO = new PickupDailySheetDO();//need to check
			pickupDailySheetDO.setPickupDailySheetId(pickupDailySheetId);					

			logger.debug("PickUpMDBServiceImpl::createAndSavePickUpProductDOListFromPickupTO::pickUpTO.getPaymentMode().length==>"
					+pickUpTO.getPaymentMode().length);
			for(int i=0;i<pickUpTO.getPaymentMode().length;i++){
				try{
					PickUpProductDO pickUpProductDO = new PickUpProductDO();

					if(pickupProductIds!=null && pickupProductIds.length>i){
						pickUpProductDO.setPickupClosedId(pickupProductIds[i]);
						logger.debug("PickUpMDBServiceImpl::createAndSavePickUpProductDOListFromPickupTO::pickupProductIds["+i+"]==>"+pickupProductIds[i]);						
					}else{
						pickUpProductDO.setPickupClosedId(null);
						logger.debug("PickUpMDBServiceImpl::createAndSavePickUpProductDOListFromPickupTO::pickupProductIds[i]==> else block setPickupClosedId null");									
					}
					pickUpProductDO.setNodeId(pickUpTO.getNodeId());
					pickUpProductDO.setPickupDO(pickupDO);
					pickUpProductDO.setUserDO(userDO);
					pickUpProductDO.setPickupDailySheetDO(pickupDailySheetDO);

					logger.debug("PickUpMDBServiceImpl::createAndSavePickUpProductDOListFromPickupTO::pickUpTO.getServices()["+i+"]==>"+pickUpTO.getServices()[i]);
					ServiceDO serviceDO = null;
					if(StringUtils.isNotBlank(pickUpTO.getServices()[i])){
						serviceDO = pickUpMDBDAO.getServiceDOByServiceCode(pickUpTO.getServices()[i]);
					}
					logger.debug("PickUpMDBServiceImpl::createAndSavePickUpProductDOListFromPickupTO::serviceDO==>"+serviceDO);					
					pickUpProductDO.setServiceDO(serviceDO);	

					logger.debug("PickUpMDBServiceImpl::createAndSavePickUpProductDOListFromPickupTO::pickUpTO.getStartConsg()["+i+"]==>"+pickUpTO.getStartConsg()[i]);					
					pickUpProductDO.setConsgNo(pickUpTO.getStartConsg()[i]);
					pickUpProductDO.setTotalConsg(pickUpTO.getTotalConsg()[i]);
					pickUpProductDO.setPaymentMode(pickUpTO.getPaymentMode()[i]);
					pickUpProductDO.setMisceCharges(pickUpTO.getMiscExp()[i]);
					pickUpProductDO.setPickUpAck(pickUpTO.getPickUpAck()[i]);
					pickUpProductDO.setRemarks(pickUpTO.getDailyPickupRemark()[i]);
					pickUpProductDO.setRecordStatus(pickUpTO.getPickupStatusAr()[i]);
					pickUpProductDO.setTransLstModifyDate(pickUpTO.getTransLstModifyDate()[i]);

					logger.debug("PickUpMDBServiceImpl::createAndSavePickUpProductDOListFromPickupTO::pickUpTO.getPaymentAmount()["+i+"]==>"+pickUpTO.getPaymentAmount()[i]);					

					if(StringUtils.isNotBlank(pickUpTO.getPaymentAmount()[i])){
						pickUpProductDO.setPaymentAmt(Double.valueOf(pickUpTO.getPaymentAmount()[i]));
					}

					pickUpProductDO.setDiFlag("N");
					//pickUpProductDO.setDbServer("N");
					//pickUpProductDO.setReadByLocal("N");

					pickUpProductDOList.add(pickUpProductDO);
				}catch (Exception e) {
					logger.error("PickUpMDBServiceImpl::createAndSavePickUpProductDOListFromPickupTO::Exception occured:"
							+e.getMessage());
					logger.error("Exception occured in::PickUpMDBServiceImpl::createAndSavePickUpProductDOListFromPickupTO::Exception==>"+e.getStackTrace());
					throw e;
				}
			}

			//pickUpMDBDAO.savePickupProductsAndPickupDetails(pickUpProductDOList,pickupDO);
			if(pickupDO!=null){
				pickUpMDBDAO.savePickUpGeneration(pickupDO);
			}
			pickUpMDBDAO.savePickupProducts(pickUpProductDOList);
		} catch (Exception e) {
			logger.error("PickUpMDBServiceImpl::createAndSavePickUpProductDOListFromPickupTO::Exception occured:"
					+e.getMessage());
			logger.error("Exception occured in::PickUpMDBServiceImpl::createAndSavePickUpProductDOListFromPickupTO::==>"+e.getStackTrace());
			throw e;
		}
		logger.debug("PickUpMDBServiceImpl::createAndSavePickUpProductDOListFromPickupTO::End======>");
	}

	/**
	 * Gets the dDMMYYYY date string.
	 *
	 * @param date the date
	 * @return the dDMMYYYY date string
	 */
	public String getDDMMYYYYDateString(Date date) {
		String strDate = "";
		if(date != null){
			String dateFormat = DATE_FORMAT;
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			strDate = sdf.format(date).toString();
		}
		return strDate;
	}
}
