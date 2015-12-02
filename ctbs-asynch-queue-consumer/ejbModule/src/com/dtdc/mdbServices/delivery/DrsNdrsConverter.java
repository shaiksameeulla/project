package src.com.dtdc.mdbServices.delivery;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import src.com.dtdc.constants.DRSConstants;
import src.com.dtdc.mdbDao.delivery.NonDeliveryRunMDBDAO;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.dtdc.domain.expense.ExpenditureTypeDO;
import com.dtdc.domain.expense.MiscExpenseDO;
import com.dtdc.domain.master.ReasonDO;
import com.dtdc.domain.master.employee.EmployeeDO;
import com.dtdc.domain.master.franchisee.FranchiseeDO;
import com.dtdc.domain.master.identityproof.IdentityProofDocDO;
import com.dtdc.domain.transaction.delivery.DeliveryDO;
import com.dtdc.domain.transaction.delivery.DeliveryRunDO;
import com.dtdc.domain.transaction.delivery.NonDeliveryRunDO;
import com.dtdc.to.delivery.DeliveryRunTO;
import com.dtdc.to.delivery.NonDeliveryRunTO;
import com.dtdc.to.expense.CnMiscExpenseTO;
import com.dtdc.to.franchisee.FranchiseeTO;

// TODO: Auto-generated Javadoc
/**
 * The Class DrsNdrsConverter.
 */
public class DrsNdrsConverter {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DrsNdrsConverter.class);
	/**
	 * Gets the drs entity.
	 *
	 * @param deliveryRunTO the delivery run to
	 * @param deliveryDOs the delivery d os
	 * @param idProofCodeIdMap the id proof code id map
	 * @return the DRS entity
	 * @throws CGBusinessException the cG business exception
	 */
	public static Map<String, List<? extends DeliveryDO>> getDRSEntity(DeliveryRunTO deliveryRunTO, 
			List<DeliveryDO> deliveryDOs, Map<String,Integer> idProofCodeIdMap) throws CGBusinessException {
		
		List<DeliveryRunDO> drsDos = new ArrayList<DeliveryRunDO>();
		List<DeliveryDO> bdmFdmDOs = new ArrayList<DeliveryDO>();
		DeliveryRunDO drsDo =  new DeliveryRunDO();
		Map<String, List<? extends DeliveryDO>> dRSMap = new HashMap<String, List<? extends DeliveryDO>>();
		try {
		for (int i = 0; i < deliveryRunTO.getDeliveryId().length; i++) {
			for (int j = 0; j < deliveryDOs.size(); j++) {
				DeliveryDO deliveryDO = deliveryDOs.get(j);
				if(deliveryRunTO.getUserAction().equalsIgnoreCase(DRSConstants.SAVE)){
					drsDo = new DeliveryRunDO();
				}
				if(deliveryRunTO.getUserAction().equalsIgnoreCase(DRSConstants.EDIT)){
					drsDo = (DeliveryRunDO)deliveryDO;
				}
				
				PropertyUtils.copyProperties(drsDo, deliveryDO);
				if(deliveryRunTO.getUserAction().equalsIgnoreCase(DRSConstants.SAVE)){
					drsDo.setDeliveryId(null);
				}
				if( deliveryDO !=null && deliveryRunTO.getConsgNum()[i] != null && deliveryRunTO.getConsgNum()[i].equalsIgnoreCase(deliveryDO.getConNum())){
					
					if(StringUtils.hasText(deliveryRunTO.getDelivAttemptNo()[i])){
						drsDo.setAttemptNum(Integer.valueOf(deliveryRunTO.getDelivAttemptNo()[i]));
					}
					
					String flagFranchisee = deliveryRunTO.getHeaderFileName();
					if(StringUtils.hasText(flagFranchisee) && 
							flagFranchisee.equalsIgnoreCase(DRSConstants.FRANCHISEE)){
						if(StringUtils.hasText(deliveryRunTO.getDrsNo()[i])){
							drsDo.setRunSheetNum(deliveryRunTO.getDrsNo()[i]);
						}
					}
					drsDo.setDeliveryStatus(DRSConstants.STATUS_D);
					if(StringUtils.hasText(deliveryRunTO.getDeliveryDate()[i])){
						//drsDo.setDeliveryDate(DateFormatterUtil.getDateFromStringDDMMYYY(deliveryRunTO.getDeliveryDate()[i]));
						// sets datetime format
						drsDo.setDeliveryDate(DateFormatterUtil
								.getTimestampFromDateTime(deliveryRunTO.getDeliveryDate()[i], 
										DateFormatterUtil.convertTimeHHMMToHHMMWithColon(deliveryRunTO.getAttemptTime()[i])));
					}
					if(StringUtils.hasText(deliveryRunTO.getAttemptTime()[i])){
						drsDo.setDeliveryTime(deliveryRunTO.getAttemptTime()[i]);
					}
					if(StringUtils.hasText(deliveryRunTO.getPodReceived()[i])){
						drsDo.setPod(deliveryRunTO.getPodReceived()[i]);
					}
					if(StringUtils.hasText(deliveryRunTO.getPodReceived()[i])){
						drsDo.setRecvByCode(deliveryRunTO.getPodReceived()[i]);
					}
					if(StringUtils.hasText(deliveryRunTO.getPodReceivedBy()[i])){
						drsDo.setReceiverDetails((deliveryRunTO.getPodReceivedBy()[i]).toUpperCase());
					}
					
					if(StringUtils.hasText(deliveryRunTO.getRelationCode()[i])) {
						drsDo.setRelationShip(deliveryRunTO.getRelationCode()[i]);
					}
					if(StringUtils.hasText(deliveryRunTO.getPhoneNo()[i])){
						drsDo.setPhNum(deliveryRunTO.getPhoneNo()[i]);
					}
					if(StringUtils.hasText(deliveryRunTO.getRemarks()[i])){
						drsDo.setDrsRemarks(deliveryRunTO.getRemarks()[i]);
					}
					
					if(StringUtils.hasText(deliveryRunTO.getIdProof()[i])){
						IdentityProofDocDO idProofDocDO = new IdentityProofDocDO();
						Integer idProofId = idProofCodeIdMap.get(deliveryRunTO.getIdProof()[i]);
						if(idProofId != null && NumberUtils.isDigits(String.valueOf(idProofId))){
							idProofDocDO.setIdentityProofDocId(idProofId);
							drsDo.setIdentityProofDO(idProofDocDO);
						}
					}
					
					if(deliveryRunTO.getIdProofNo()!=null
							&& deliveryRunTO.getIdProofNo().length > 0
							&& StringUtils.hasText(deliveryRunTO.getIdProofNo()[i])){
						drsDo.setIdentityProofNo(deliveryRunTO.getIdProofNo()[i]);
					} 
					
					if(StringUtils.hasText(deliveryRunTO.getLoggedInUSerId())){
						drsDo.setUserId(Integer.valueOf(deliveryRunTO.getLoggedInUSerId()));
					}
					if(StringUtils.hasText(deliveryRunTO.getEmpId())){
						EmployeeDO employee = new EmployeeDO();
						employee.setEmployeeId(Integer.valueOf(deliveryRunTO.getEmpId()));
						drsDo.setEmployee(employee);
					}
					
					
					drsDo.setTotalWeight(null);
					drsDo.setTransactionCrDate(new Date());
					drsDos.add(drsDo);
					//to make the bdm/fdm records to inactive while inserting new DRS Entity
					if(deliveryRunTO.getUserAction().equalsIgnoreCase(DRSConstants.SAVE)){
						deliveryDO.setConsgStatus(DRSConstants.INACTIVE_STATUS);
						bdmFdmDOs.add(deliveryDO);
					}
				}
			}
		}
		} catch (Exception e) {
			LOGGER.error("DrsNdrsConverter::getDRSEntity::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
			
		}
		dRSMap.put(DRSConstants.DRS, drsDos);
		dRSMap.put(DRSConstants.BDM_FDM, bdmFdmDOs);
		return dRSMap;
	}
	
	/**
	 * Gets the ndrs entity.
	 *
	 * @param nonDeliveryRunTO the non delivery run to
	 * @param deliveryDOs the delivery d os
	 * @param nondeliveryRunDAO the nondelivery run dao
	 * @return the NDRS entity
	 * @throws CGBusinessException the cG business exception
	 */
	public static Map<String, List<? extends DeliveryDO>> getNDRSEntity(NonDeliveryRunTO nonDeliveryRunTO, 
			List<DeliveryDO> deliveryDOs, NonDeliveryRunMDBDAO nondeliveryRunDAO) throws CGBusinessException {
		
		List<NonDeliveryRunDO> ndrsDos = new ArrayList<NonDeliveryRunDO>();
		List<DeliveryDO> bdmFdmDOs = new ArrayList<DeliveryDO>();
		Map<String, List<? extends DeliveryDO>> ndRSMap = new HashMap<String, List<? extends DeliveryDO>>();
		
		try {
			for (int i = 0; i < nonDeliveryRunTO.getDeliveryId().length; i++) {
				for (int j = 0; j < deliveryDOs.size(); j++) {
					DeliveryDO deliveryDO = deliveryDOs.get(j);
					NonDeliveryRunDO ndrsDo = new NonDeliveryRunDO();
					PropertyUtils.copyProperties(ndrsDo, deliveryDO);
					ndrsDo.setDeliveryId(null);
					if( deliveryDO !=null && nonDeliveryRunTO.getDeliveryId()[i] != null 
							&& nonDeliveryRunTO.getDeliveryId()[i].equals(deliveryDO.getDeliveryId())){
						String flagFranchisee = nonDeliveryRunTO.getHeaderFileName();
						if(StringUtils.hasText(flagFranchisee) && 
								flagFranchisee.equalsIgnoreCase(DRSConstants.FRANCHISEE)){
							if(StringUtils.hasText(nonDeliveryRunTO.getDrsNo()[i])){
								ndrsDo.setRunSheetNum(nonDeliveryRunTO.getDrsNo()[i]);
							}
						}
						ndrsDo.setAttemptNum(Integer.valueOf(nonDeliveryRunTO.getDelivAttemptNo()[i]));
						ndrsDo.setDeliveryStatus(DRSConstants.STATUS_N);
						
						// Sets the datetime of attempt
						/*ndrsDo.setAttemptTime(nonDeliveryRunTO.getAttemptTime()[i]);	
						ndrsDo.setAttemptDate(DateFormatterUtil
								.getDateFromStringDDMMYYY(nonDeliveryRunTO.getDeliveryDate()[i]));*/						
						ndrsDo.setAttemptDate(DateFormatterUtil
								.getTimestampFromDateTime(nonDeliveryRunTO.getDeliveryDate()[i], 
										DateFormatterUtil.convertTimeHHMMToHHMMWithColon(nonDeliveryRunTO.getAttemptTime()[i])));
						
						ndrsDo.setMissedYou(nonDeliveryRunTO.getMissedYou()[i]);
						ndrsDo.setConsgWeight(nonDeliveryRunTO.getWeight()[i]);
						ndrsDo.setConsgNumOFPieces(nonDeliveryRunTO.getNoofPieces()[i]);
						if(nonDeliveryRunTO.getReason().length > 0){
							ndrsDo.setReason(nondeliveryRunDAO.getReasonDOById(Integer.valueOf(nonDeliveryRunTO.getReason()[i])));
						}
						if(StringUtils.hasText(String.valueOf(nonDeliveryRunTO.getLoggedInUSerId()))){
							ndrsDo.setUserId(Integer.valueOf(nonDeliveryRunTO.getLoggedInUSerId()));
						}
						
						/*
					NonDeliveryRunDisplyTO nonDeliveryRunDisplyTO = new NonDeliveryRunDisplyTO();
					nonDeliveryRunDisplyTO.setConsgNum(deliveryDO.getConNum());
					nonDeliveryRunDisplyTO.setDeliveryId(deliveryDO.getDeliveryId());
					nonDeliveryRunDisplyTO.setAttemptTime(deliveryDO.getAttemptTime());
					nonDeliveryRunDisplyTO.setDelivAttemptNo(deliveryDO.getAttemptNum()+"");
					nonDeliveryRunDisplyTO.setDeliveryStatus(deliveryDO.getDeliveryStatus());
					nonDeliveryRunDisplyTO.setMissedYou(deliveryDO.getMissedYou());
					nonDeliveryRunDisplyTO.setWeight(deliveryDO.getConsgWeight());
					nonDeliveryRunDisplyTO.setNoOfPieces(deliveryDO.getConsgNumOFPieces()); */						
						ndrsDos.add(ndrsDo);
						//to make the bdm/fdm records to inactive
						deliveryDO.setConsgStatus(DRSConstants.INACTIVE_STATUS);
						bdmFdmDOs.add(deliveryDO);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("DrsNdrsConverter::getNDRSEntity::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		}
		ndRSMap.put(DRSConstants.NDRS, ndrsDos);
		ndRSMap.put(DRSConstants.BDM_FDM, bdmFdmDOs);
		return ndRSMap;
		
	}
	
	
	
	
	/**
	 * Gets the misc expense do from to.
	 *
	 * @param teo the teo
	 * @param processName the process name
	 * @return the misc expense do from to
	 */
	public static MiscExpenseDO getMiscExpenseDoFromTo( CnMiscExpenseTO teo,String processName) {
		MiscExpenseDO miscExpnDo = new MiscExpenseDO();
		miscExpnDo.setConsignmentNumber("");
		miscExpnDo.setExpenditureId(teo.getExpndTypeId());
		ExpenditureTypeDO expType = new ExpenditureTypeDO();
		expType.setExpndTypeId(teo.getExpenditureType());
		miscExpnDo.setExpenditureType(expType);
		
		miscExpnDo.setExpenditureAmount(teo.getExpenditureAmount());
		EmployeeDO auth = new EmployeeDO();
		auth.setEmployeeId(teo.getEmpId());
		miscExpnDo.setAuthorizer(auth);
		miscExpnDo.setRemark(teo.getRemark());
		miscExpnDo.setExpenditureDate(DateFormatterUtil.getDateFromStringDDMMYYY(teo.getExpenditureDate()));
		miscExpnDo.setVoucherNumber(teo.getVoucherNumber());
		miscExpnDo.setVoucherDate(DateFormatterUtil.getDateFromStringDDMMYYY(teo.getVoucherDate()));
		miscExpnDo.setConsignmentNumber(teo.getCnNumber());
		miscExpnDo.setApproved("Y");
		//miscExpnDo.setProcessName(processName);
		EmployeeDO enteredBy = new EmployeeDO();
		enteredBy.setEmployeeId(Integer.parseInt(teo.getEnteredBy()));
		miscExpnDo.setEnterebBy(enteredBy);
		return miscExpnDo;
	}
	
	
	/**
	 * utility method to copy values.
	 *
	 * @param formTO the form to
	 * @param doList the do list
	 * @return the delivery run to
	 */
	public static DeliveryRunTO copyDrsDO2TO(DeliveryRunTO formTO, final List<DeliveryRunDO>  doList){
		if(!doList.isEmpty()){
			int rowCount = doList.size();
			EmployeeDO employeeDO = null;
			FranchiseeDO franchiseeDO =null;
			IdentityProofDocDO identityProofDocDO = null;
			String name = "";
			boolean isFranchisee = formTO.getHeaderFileName().equalsIgnoreCase(DRSConstants.FRANCHISEE) ? true : false;
			String[] consgNum = new String[rowCount];
			String[] deliveryId = new String[rowCount];
			String[] delivAttemptNo = new String[rowCount];
			String[] attemptTime = new String[rowCount];
			String[] idProof = new String[rowCount];
			String[] idProofDesc = new String[rowCount];
			String[] idProofNo = new String[rowCount];
			String[] podReceived = new String[rowCount];
			String[] podReceivedBy = new String[rowCount];
			String[] relationCode = new String[rowCount];
			String[] phoneNo = new String[rowCount];
			String[] remarks = new String[rowCount];
			String[] deliveryDate = new String[rowCount];
			String[] drsNo = new String[rowCount];
			
			for (int i = 0; i < doList.size(); i++) {
				final DeliveryRunDO deliveryDO = doList.get(i);
				if(deliveryDO != null){
					employeeDO = deliveryDO.getEmployee();
					if(employeeDO != null){
						formTO.setEmpId(String.valueOf(employeeDO.getEmployeeId()));
						formTO.setEmpCode(employeeDO.getEmpCode());
						name =  name.concat(employeeDO.getFirstName()).concat(" ").concat(employeeDO.getLastName() != null ? employeeDO.getLastName() : "");
						formTO.setEmpName(name);
					}
					franchiseeDO = deliveryDO.getFranchisee();
					if(isFranchisee && franchiseeDO != null){
						formTO.setFranchiseCode(franchiseeDO.getFranchiseeCode());
						formTO.setFranchiseeId(String.valueOf(franchiseeDO.getFranchiseeId()));
						formTO.setFranchiseeName(franchiseeDO.getBusinessName());
						formTO.setFrBranchCode(deliveryDO.getFranchisee().getOfficeId().getOfficeCode());
						formTO.setFrBranchDesc(deliveryDO.getFranchisee().getOfficeId().getOfficeName());
					}
					
					consgNum[i]=deliveryDO.getConNum();
					deliveryId[i] = deliveryDO.getDeliveryId().toString();
					delivAttemptNo[i] = String.valueOf(deliveryDO.getAttemptNum());
					attemptTime[i] = DateFormatterUtil.extractTimeFromDate(deliveryDO.getDeliveryDate()).replace(":", "") ;
					identityProofDocDO = deliveryDO.getIdentityProofDO();
					
					if(identityProofDocDO != null){
						idProof[i] = identityProofDocDO.getIdentityProofDocCode();
						idProofDesc[i]= identityProofDocDO.getIdentityProofDocName();
						idProofNo[i] = deliveryDO.getIdentityProofNo();
					}
					podReceived[i] = deliveryDO.getPod();
					podReceivedBy[i] = deliveryDO.getReceiverDetails();
					relationCode[i] = deliveryDO.getRelationShip();
					phoneNo[i] = deliveryDO.getPhNum();
					remarks[i] = deliveryDO.getDrsRemarks();
					deliveryDate[i] = DateFormatterUtil.getDDMMYYYYDateToString(deliveryDO.getDeliveryDate());
					drsNo[i] = deliveryDO.getRunSheetNum();
				}
			}
			
			formTO.setRowCount(rowCount);
			formTO.setAttemptTime(attemptTime);
			formTO.setConsgNum(consgNum);
			formTO.setDelivAttemptNo(delivAttemptNo);
			formTO.setDeliveryDate(deliveryDate);
			formTO.setDeliveryId(deliveryId);
			formTO.setDrsNo(drsNo);
			formTO.setIdProof(idProof);
			formTO.setIdProofDesc(idProofDesc);
			formTO.setIdProofNo(idProofNo);
			formTO.setPhoneNo(phoneNo);
			formTO.setPodReceived(podReceivedBy);
			formTO.setPodReceivedBy(podReceivedBy);
			formTO.setRelationCode(relationCode);
			formTO.setRemarks(remarks);
			
		} 
		 return formTO;      
	}
	
	/**
	 * utility method to copy values.
	 *
	 * @param formTO the form to
	 * @param doList the do list
	 * @return the non delivery run to
	 */
	public static NonDeliveryRunTO copyNdrsDO2TO(NonDeliveryRunTO formTO, final List<NonDeliveryRunDO>  doList){
		if(!doList.isEmpty()){
			EmployeeDO employeeDO = null;
			FranchiseeDO franchiseeDO =null;
			String name = "";
			boolean isFranchisee = formTO.getHeaderFileName().equalsIgnoreCase(DRSConstants.FRANCHISEE) ? true : false;
			int rowCount = doList.size();
			Integer[] deliveryId = new Integer[rowCount];
			String[] drsNo = new String[rowCount];
			String[] consgNum = new String[rowCount];
			String[] delivAttemptNo = new String[rowCount];
			String[] deliveryDate = new String[rowCount];
			String[] attemptTime = new String[rowCount];
			String[] reasonDescp = new String[rowCount];
			String[] reasonTxt = new String[rowCount];
			String[] reasonId = new String[rowCount];
			Integer[] missedYou = new Integer[rowCount];
			double[] weight = new double[rowCount];
			Integer[] noOfPieces = new Integer[rowCount];
			
			for (int i = 0; i < doList.size(); i++) {
				final NonDeliveryRunDO deliveryDO = doList.get(i);
				if(deliveryDO != null){
					employeeDO = deliveryDO.getEmployee();
					if(employeeDO != null){
						formTO.setEmpId(String.valueOf(employeeDO.getEmployeeId()));
						formTO.setEmpCode(employeeDO.getEmpCode());
						name =  name.concat(employeeDO.getFirstName()).concat(" ").concat(employeeDO.getLastName() != null ? employeeDO.getLastName() : "");
						formTO.setEmpName(name);
					}
					franchiseeDO = deliveryDO.getFranchisee();
					if(isFranchisee && franchiseeDO != null){
						formTO.setFranchiseCode(franchiseeDO.getFranchiseeCode());
						formTO.setFranchiseeId(String.valueOf(franchiseeDO.getFranchiseeId()));
						formTO.setFranchiseeName(franchiseeDO.getBusinessName());
						formTO.setFrBranchCode(deliveryDO.getFranchisee().getOfficeId().getOfficeCode());
						formTO.setFrBranchDesc(deliveryDO.getFranchisee().getOfficeId().getOfficeName());
					}
					deliveryId[i] = deliveryDO.getDeliveryId();
					drsNo[i] = deliveryDO.getRunSheetNum();
					consgNum[i]=deliveryDO.getConNum();
					delivAttemptNo[i] = String.valueOf(deliveryDO.getAttemptNum());
					deliveryDate[i] = DateFormatterUtil.getDDMMYYYYDateToString(deliveryDO.getAttemptDate());
					attemptTime[i] = DateFormatterUtil.extractTimeFromDate(deliveryDO.getAttemptDate()).replace(":","") ; // attemptDate needs to tokenized for date,time
					reasonTxt[i] = deliveryDO.getReason().getReasonCode() != null ? deliveryDO.getReason().getReasonCode() : "";
					reasonDescp[i] = deliveryDO.getReason().getReasonDesc() != null ? deliveryDO.getReason().getReasonDesc() : "";
					reasonId[i] = String.valueOf(deliveryDO.getReason().getReasonId());
					missedYou[i] = (deliveryDO.getMissedYou()!=null ? deliveryDO.getMissedYou():Integer.valueOf(0));
					weight[i] = (deliveryDO.getConsgWeight()!=null ? deliveryDO.getConsgWeight() : null);
					noOfPieces[i] = (deliveryDO.getTotalNumOFPieces()!=null ? deliveryDO.getTotalNumOFPieces() : null);
				}
			}
			
			formTO.setDeliveryId(deliveryId);
			formTO.setDrsNo(drsNo);
			formTO.setConsgNum(consgNum);
			formTO.setDelivAttemptNo(delivAttemptNo);
			formTO.setDeliveryDate(deliveryDate);
			formTO.setAttemptTime(attemptTime);
			formTO.setReasonDescp(reasonDescp);
			formTO.setReasonText(reasonTxt);
			formTO.setReason(reasonId);
			formTO.setMissedYou(missedYou);
			formTO.setWeight(weight);
			formTO.setNoofPieces(noOfPieces);
			formTO.setRowCount(rowCount);
		} 
		 return formTO;      
	}
	
	/**
	 * Gets the franchisee t os.
	 *
	 * @param frDos the fr dos
	 * @return the franchisee t os
	 */
	public static List<FranchiseeTO> getFranchiseeTOs(List<FranchiseeDO> frDos){
	
		if(CollectionUtils.isEmpty(frDos)){
			return new ArrayList<FranchiseeTO>();
		}
		List<FranchiseeTO> tos = new ArrayList<FranchiseeTO>();
		for (FranchiseeDO franchiseeDO : frDos) {
			FranchiseeTO to = new FranchiseeTO();
			to.setFranchiseeId(franchiseeDO.getFranchiseeId());
			to.setFranchiseeCode(franchiseeDO.getFranchiseeCode());
			to.setFirstName(franchiseeDO.getFirstName());
			to.setLastName(franchiseeDO.getLastName());
			tos.add(to);
		}
		return tos;
	}

	/**
	 * Gets the ndrs entity.
	 *
	 * @param bdmDOs the bdm d os
	 * @param nondeliveryRunDAO the nondelivery run dao
	 * @return the NDRS entity
	 * @throws CGBusinessException the cG business exception
	 */
	public static Map<String, List<? extends DeliveryDO>> getNDRSEntity(
			List<DeliveryDO> bdmDOs, NonDeliveryRunMDBDAO nondeliveryRunDAO) throws CGBusinessException{
		
		List<NonDeliveryRunDO> ndrsDos = new ArrayList<NonDeliveryRunDO>();
		List<DeliveryDO> bdmFdmDOs = new ArrayList<DeliveryDO>();
		List<DeliveryDO> tempBdmFdm = new ArrayList<DeliveryDO>();
		List<DeliveryDO> newBdmFdm = new ArrayList<DeliveryDO>();
		tempBdmFdm.addAll(bdmDOs);
		for (DeliveryDO deliveryDO : tempBdmFdm) {
			deliveryDO.setDeliveryId(null);
			deliveryDO.setPreparationDate(new Date());
			newBdmFdm.add(deliveryDO);
		}
		
		
		Map<String, List<? extends DeliveryDO>> ndRSMap = new HashMap<String, List<? extends DeliveryDO>>();
		try {
			for (int j = 0; j < bdmDOs.size(); j++) {
				DeliveryDO deliveryDO = bdmDOs.get(j);
				NonDeliveryRunDO ndrsDo = new NonDeliveryRunDO();
				PropertyUtils.copyProperties(ndrsDo, deliveryDO);
				ndrsDo.setDeliveryId(null);
				ndrsDo.setDeliveryStatus(DRSConstants.STATUS_N);
				ndrsDo.setAttemptTime(deliveryDO.getAttemptTime());	
				ndrsDo.setAttemptDate(deliveryDO.getPreparationDate());
				ndrsDo.setConsgStatus(DRSConstants.INACTIVE_STATUS);
				ReasonDO reasonDO = nondeliveryRunDAO.getReasonDOByCode("TTM");
				if(reasonDO != null){
					ndrsDo.setReason(reasonDO);
				}
				ndrsDos.add(ndrsDo);
				//to make the bdm/fdm records to inactive
				deliveryDO.setConsgStatus(DRSConstants.INACTIVE_STATUS);
				bdmFdmDOs.add(deliveryDO);
			}
		} catch (Exception e) {
			LOGGER.error("DrsNdrsConverter::getNDRSEntity::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		}
		ndRSMap.put(DRSConstants.NDRS, ndrsDos);
		ndRSMap.put(DRSConstants.BDM_FDM, bdmFdmDOs);
		ndRSMap.put(DRSConstants.NEW_BDM_FDM, newBdmFdm);
		return ndRSMap;
	}

}
