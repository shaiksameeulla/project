package src.com.dtdc.mdbServices.delivery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import src.com.dtdc.constants.DRSConstants;
import src.com.dtdc.mdbDao.delivery.DeliveryRunMDBDAO;
import src.com.dtdc.mdbDao.delivery.NonDeliveryRunMDBDAO;
import src.com.dtdc.mdbServices.CTBSApplicationMDBDAO;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.domain.booking.ProductDO;
import com.dtdc.domain.expense.MiscExpenseDO;
import com.dtdc.domain.master.ReasonDO;
import com.dtdc.domain.master.customer.ConsigneeDO;
import com.dtdc.domain.master.customer.CustomerDO;
import com.dtdc.domain.master.document.DocumentDO;
import com.dtdc.domain.master.employee.EmployeeDO;
import com.dtdc.domain.master.franchisee.FranchiseeDO;
import com.dtdc.domain.master.geography.PincodeDO;
import com.dtdc.domain.master.identityproof.IdentityProofDocDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.master.product.ServiceDO;
import com.dtdc.domain.transaction.delivery.DeliveryDO;
import com.dtdc.domain.transaction.delivery.DeliveryRunDO;
import com.dtdc.domain.transaction.delivery.NonDeliveryRunDO;
import com.dtdc.to.delivery.DeliveryRunTO;
import com.dtdc.to.delivery.DeliveryTO;
import com.dtdc.to.expense.CnMiscExpenseTO;

// TODO: Auto-generated Javadoc
/**
 * The Class DeliveryRunSheetMDBServiceImpl.
 */
public class DeliveryRunSheetMDBServiceImpl implements DeliveryRunSheetMDBService {


	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryRunSheetMDBServiceImpl.class);
	
	/** The delivery run mdbdao. */
	private DeliveryRunMDBDAO deliveryRunMDBDAO;
	
	/** The non delivery run mdbdao. */
	private NonDeliveryRunMDBDAO nonDeliveryRunMDBDAO;
	
	/** The ctbs application mdbdao. */
	private CTBSApplicationMDBDAO ctbsApplicationMDBDAO;
	
	/**
	 * Sets the delivery run mdbdao.
	 *
	 * @param deliveryRunMDBDAO the new delivery run mdbdao
	 */
	public void setDeliveryRunMDBDAO(DeliveryRunMDBDAO deliveryRunMDBDAO) {
		this.deliveryRunMDBDAO = deliveryRunMDBDAO;
	}
	
	/**
	 * Sets the non delivery run mdbdao.
	 *
	 * @param nonDeliveryRunMDBDAO the new non delivery run mdbdao
	 */
	public void setNonDeliveryRunMDBDAO(
			NonDeliveryRunMDBDAO nonDeliveryRunMDBDAO) {
		this.nonDeliveryRunMDBDAO = nonDeliveryRunMDBDAO;
	}
	
	/**
	 * Sets the ctbs application mdbdao.
	 *
	 * @param ctbsApplicationMDBDAO the new ctbs application mdbdao
	 */
	public void setCtbsApplicationMDBDAO(
			CTBSApplicationMDBDAO ctbsApplicationMDBDAO) {
		this.ctbsApplicationMDBDAO = ctbsApplicationMDBDAO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.delivery.DeliveryRunSheetMDBService#updateDRS(CGBaseTO)
	 */
	@Override
	public void updateDRS(CGBaseTO cgBaseTO) throws CGSystemException,
			CGBusinessException {
		final DeliveryRunTO deliveryRunTO = (DeliveryRunTO)cgBaseTO.getBaseList().get(0);
		@SuppressWarnings("unchecked")
		List<CnMiscExpenseTO> miscExpenseTos = (List<CnMiscExpenseTO>)cgBaseTO.getJsonChildObject();
		deliveryRunTO.setMiscExpnseList(miscExpenseTos);
		updateDRS(deliveryRunTO);
	}
	
	//artf1604634
	/**
	 * Update as ndrs by try tomorrow reason.
	 *
	 * @param bdmDOs the bdm d os
	 * @throws CGBusinessException the cG business exception
	 */
	public void updateAsNdrsByTryTomorrowReason(List<DeliveryDO> bdmDOs) throws CGBusinessException {
		Map<String, List<? extends DeliveryDO>> drsMap = DrsNdrsConverter.getNDRSEntity(bdmDOs, nonDeliveryRunMDBDAO);
		List<? extends DeliveryDO> ndrsDos = drsMap.get(DRSConstants.NDRS);
		List<? extends DeliveryDO> bdmFdmDOs = drsMap.get(DRSConstants.BDM_FDM);
		List<? extends DeliveryDO> newBdmFdm = drsMap.get(DRSConstants.NEW_BDM_FDM);
		
		if(!CollectionUtils.isEmpty(ndrsDos)){
			nonDeliveryRunMDBDAO.saveOrUpdateAllDeliveryDOs(ndrsDos);
		}
		
		if(!CollectionUtils.isEmpty(bdmFdmDOs)){
			nonDeliveryRunMDBDAO.saveOrUpdateAllDeliveryDOs(bdmFdmDOs);
		}
		
		if(!CollectionUtils.isEmpty(newBdmFdm)){
			nonDeliveryRunMDBDAO.saveOrUpdateAllDeliveryDOs(newBdmFdm);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.delivery.DeliveryRunSheetMDBService#updateDRS(DeliveryRunTO)
	 */
	@Override
	public void updateDRS(final DeliveryRunTO deliveryRunTO) throws CGSystemException, CGBusinessException {
		
		
		LOGGER.debug("entered updateDRS");
		List<DeliveryDO> deliveryDOs = null;
		List<DeliveryDO> bdmDOs = null;
		List<IdentityProofDocDO> identityProofDocDOs = null;
		
		try {
			if(deliveryRunTO != null){
				//starts ... artf1604634
				bdmDOs = nonDeliveryRunMDBDAO.getBdmlessThanDeliveryDate(deliveryRunTO.getDeliveryDate(), deliveryRunTO.getConsgNum());
				if(!CollectionUtils.isEmpty(bdmDOs)){	
					updateAsNdrsByTryTomorrowReason(bdmDOs);
				}
				//ends ... artf1604634
				deliveryDOs = nonDeliveryRunMDBDAO.getDeliveryDOs(deliveryRunTO.getConsgNum());
				
				identityProofDocDOs = ctbsApplicationMDBDAO.getIdProofDocsByCode(deliveryRunTO.getIdProof());
				Map<String,Integer> idProofCodeIdMap =  new HashMap<String, Integer>();
				
				for (IdentityProofDocDO identityProofDocDO : identityProofDocDOs) {
					idProofCodeIdMap.put(identityProofDocDO.getIdentityProofDocCode(), identityProofDocDO.getIdentityProofDocId());
				}
				
				Map<String, List<? extends DeliveryDO>> drsMap = DrsNdrsConverter.getDRSEntity(deliveryRunTO, deliveryDOs, idProofCodeIdMap);
				
				List<? extends DeliveryDO> runDOs = drsMap.get(DRSConstants.DRS);
				List<? extends DeliveryDO> bdmFdms = drsMap.get(DRSConstants.BDM_FDM);
				
				List<MiscExpenseDO> miscExpenseDos = null;
				if(deliveryRunTO.getMiscExpnseList() != null && !deliveryRunTO.getMiscExpnseList().isEmpty()){
					miscExpenseDos = new ArrayList<MiscExpenseDO>();
					for (CnMiscExpenseTO cnMiscExpenseTO : deliveryRunTO.getMiscExpnseList()) {
						miscExpenseDos.add(DrsNdrsConverter.getMiscExpenseDoFromTo(cnMiscExpenseTO,DRSConstants.DRS));
					}
				}	
				if(!CollectionUtils.isEmpty(runDOs)){				
					//runDOs- new DeliveryRunDO to insert
					//miscExpenseDos - to insert
					//deliveryDOs - bdm/fdm dos to make consStatus as I (inactive)
					deliveryRunMDBDAO.saveOrUpdateAllDeliveryDOs(runDOs, miscExpenseDos, bdmFdms);
				}else{
					throw new CGBusinessException("Data not saved succefully");
				}
			}
		} catch (Exception e) {
			LOGGER.error("DeliveryRunSheetMDBServiceImpl::updateDRS::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		}
		
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.delivery.DeliveryRunSheetMDBService#saveDrs(CGBaseTO)
	 */
	@Override
	public void saveDrs(CGBaseTO cgBaseTO) throws CGSystemException,
			CGBusinessException {
		List<DeliveryTO> deliveryRunTOList = (List<DeliveryTO>)cgBaseTO.getBaseList();
		saveDrs(deliveryRunTOList);
		
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.delivery.DeliveryRunSheetMDBService#saveDrs(List)
	 */
	@Override
	public void saveDrs(List<DeliveryTO> deliveryToList) throws CGSystemException,
			CGBusinessException {
		List<DeliveryDO> deliveryDo =new ArrayList<DeliveryDO>();
		
		
		for(DeliveryTO deliveryTo :deliveryToList){
			DeliveryRunDO runDo = new DeliveryRunDO();
			convertTO2DO(deliveryTo, runDo);
			runDo.setDeliveryId(null);
			List<? extends DeliveryDO> bdmFdm = deliveryRunMDBDAO.getDeliveryDtls(deliveryTo.getFdmNumber(),deliveryTo.getRunSheetNum(),deliveryTo.getConNum());
			for(DeliveryDO dlv :bdmFdm){
				dlv.setConsgStatus(DRSConstants.INACTIVE_STATUS);
			}
			if(bdmFdm != null && !bdmFdm.isEmpty()){
			deliveryDo.addAll(bdmFdm);
			}
			deliveryDo.add(runDo);
		}
		nonDeliveryRunMDBDAO.saveOrUpdateAllDeliveryDOs(deliveryDo);
		
	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.delivery.DeliveryRunSheetMDBService#saveNDrs(CGBaseTO)
	 */
	@Override
	public void saveNDrs(CGBaseTO cgBaseTO) throws CGSystemException,
			CGBusinessException {
		List<DeliveryTO> deliveryRunTOList = (List<DeliveryTO>)cgBaseTO.getBaseList();
		saveNDrs(deliveryRunTOList);
		
	}


	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.delivery.DeliveryRunSheetMDBService#saveNDrs(List)
	 */
	@Override
	public void saveNDrs(List<DeliveryTO> deliveryToList)
			throws CGSystemException, CGBusinessException {
		List<DeliveryDO> deliveryDo =new ArrayList<DeliveryDO>();
		for(DeliveryTO deliveryTo :deliveryToList){
			NonDeliveryRunDO runDo = new NonDeliveryRunDO();
			convertTO2DO(deliveryTo, runDo);
			runDo.setDeliveryId(null);
			deliveryDo.add(runDo);
		}
		nonDeliveryRunMDBDAO.saveOrUpdateAllDeliveryDOs(deliveryDo);
		
	}
	
	/**
	 * Convert t o2 do.
	 *
	 * @param deliveryTo the delivery to
	 * @param runDo the run do
	 */
	private void convertTO2DO(DeliveryTO deliveryTo, DeliveryDO runDo) {
		List<String> list = new ArrayList<String>(10);
		list.add(DRSConstants.DB_STATUS_Y);
		list.add(DRSConstants.DB_STATUS_N);
		list.add(DRSConstants.DB_STATUS_A);
		list.add(DRSConstants.DB_STATUS_M);
		list.add(DRSConstants.DB_STATUS_P);
		try {
			PropertyUtils.copyProperties(runDo, deliveryTo);
		} catch (Exception e) {
			LOGGER.error("DeliveryRunSheetMDBServiceImpl::convertTO2DO::Exception occured:"
					+e.getMessage());
		} 
		//runDo.setDbServer(DRSConstants.DI_FLAG);
		runDo.setDiFlag(DRSConstants.DI_FLAG);
		//*************START************
		//In the DB,following fields are having constraints,In order to avoid DataIntegrity violations
		//we are validating the values
		
		// for Column :DELIVERED_AT_RESIDENCE
		if(StringUtil.isEmpty(deliveryTo.getDeliveredAtResidence())){
			runDo.setDeliveredAtResidence(DRSConstants.DB_STATUS_Y);
		}else{
			if(list.contains(deliveryTo.getDeliveredAtResidence().trim())){
				runDo.setDeliveredAtResidence(deliveryTo.getDeliveredAtResidence().trim());
			}else{
				runDo.setDeliveredAtResidence(DRSConstants.DB_STATUS_N);
			}
		}
		
		// for Column :READ_BY_LOCAL
		if(StringUtil.isEmpty(deliveryTo.getReadByLocal())){
			runDo.setDeliveredAtResidence(DRSConstants.DB_STATUS_Y);
		}else{
			if(list.contains(deliveryTo.getDeliveredAtResidence().trim())){
				runDo.setDeliveredAtResidence(deliveryTo.getDeliveredAtResidence().trim());
			}else{
				runDo.setDeliveredAtResidence(DRSConstants.DB_STATUS_N);
			}
		}
		
		// for Column :SIGNATURE_RECEIVED
		if(StringUtil.isEmpty(deliveryTo.getSignatureReceived())){
			runDo.setSignatureReceived(DRSConstants.DB_STATUS_Y);
		}else{
			if(list.contains(deliveryTo.getSignatureReceived().trim())){
				runDo.setSignatureReceived(deliveryTo.getSignatureReceived().trim());
			}else{
				runDo.setSignatureReceived(DRSConstants.DB_STATUS_N);
			}
		}
		
		// for Column :IS_DRS_AUTOGENERATED
		if(StringUtil.isEmpty(deliveryTo.getDrsAutoGenerated())){
			runDo.setDrsAutoGenerated(DRSConstants.DB_STATUS_Y);
		}else{
			if(list.contains(deliveryTo.getDrsAutoGenerated().trim())){
				runDo.setDrsAutoGenerated(deliveryTo.getDrsAutoGenerated().trim());
			}else{
				runDo.setDrsAutoGenerated(DRSConstants.DB_STATUS_N);
			}
		}
		//*************END************
		
		if(!StringUtil.isEmptyInteger(deliveryTo.getDocumentId())){
			DocumentDO document = new DocumentDO();
			document.setDocumentId(deliveryTo.getDocumentId());
		runDo.setDocument(document);
		}
		
		if(!StringUtil.isEmptyInteger(deliveryTo.getProductId())){
			ProductDO product = new ProductDO();
			product.setProductId(deliveryTo.getProductId());
			runDo.setProduct(product);
		}
		if(!StringUtil.isEmptyInteger(deliveryTo.getReasonId())){
			ReasonDO reason = new ReasonDO();
			reason.setReasonId(deliveryTo.getReasonId());
			runDo.setReason(reason);
		}
		
		if(!StringUtil.isEmptyInteger(deliveryTo.getBranchId())){
			OfficeDO branch = new OfficeDO();
			branch.setOfficeId(deliveryTo.getBranchId());
			runDo.setBranch(branch);
		}
		
		if(!StringUtil.isEmptyInteger(deliveryTo.getReportingBranchId())){
			OfficeDO reportingBranch = new OfficeDO();
			reportingBranch.setOfficeId(deliveryTo.getReportingBranchId());
			runDo.setReportingBranch(reportingBranch);
		}
		
		if(!StringUtil.isEmptyInteger(deliveryTo.getEmployeeId())){
			EmployeeDO employee = new EmployeeDO();
			employee.setEmployeeId(deliveryTo.getEmployeeId());
			runDo.setEmployee(employee);
		}
		
		if(!StringUtil.isEmptyInteger(deliveryTo.getFranchiseeId())){
			FranchiseeDO  franchisee = new FranchiseeDO();
			franchisee.setFranchiseeId(deliveryTo.getFranchiseeId());
			runDo.setFranchisee(franchisee);
		}
		
		if(!StringUtil.isEmptyInteger(deliveryTo.getCustomerId())){
			CustomerDO customer =new CustomerDO();
			customer.setCustomerId(deliveryTo.getCustomerId());
			runDo.setCustomer(customer);
		}
		
		if(!StringUtil.isEmptyInteger(deliveryTo.getOriginROBranchId())){
			OfficeDO originROBranch =new OfficeDO();
			originROBranch.setOfficeId(deliveryTo.getOriginROBranchId());
			runDo.setOriginROBranch(originROBranch);
		}
		
		if(!StringUtil.isEmptyInteger(deliveryTo.getDestPinCodeId())){
			PincodeDO destPinCode = new PincodeDO();
			destPinCode.setPincodeId(deliveryTo.getDestPinCodeId());
			runDo.setDestPinCode(destPinCode);
		}
		if(!StringUtil.isEmptyInteger(deliveryTo.getConsigneeId())){
			ConsigneeDO consignee = new ConsigneeDO();
			consignee.setConsigneeId(deliveryTo.getConsigneeId());
			runDo.setConsignee(consignee);
		}
		if(!StringUtil.isEmptyInteger(deliveryTo.getServiceId())){
			ServiceDO service = new ServiceDO();
			service.setServiceId(deliveryTo.getServiceId());
			runDo.setService(service);
		}
		
		if(!StringUtil.isEmptyInteger(deliveryTo.getIdentityProofId())){
			IdentityProofDocDO identityProofDO= new IdentityProofDocDO();
			identityProofDO.setIdentityProofDocId(deliveryTo.getIdentityProofId());
			runDo.setIdentityProofDO(identityProofDO);
		}
		
		//date conversions from string to Date
		
		runDo.setAttemptDate(DateFormatterUtil.slashDelimitedstringToDDMMYYYYFormat(deliveryTo.getAttemptDateStr()));
		runDo.setDeliveryDate(DateFormatterUtil.slashDelimitedstringToDDMMYYYYFormat(deliveryTo.getDeliveryDateStr()));
		runDo.setManifestDate(DateFormatterUtil.slashDelimitedstringToDDMMYYYYFormat(deliveryTo.getManifestDateStr()));
		runDo.setHandOverDate(DateFormatterUtil.slashDelimitedstringToDDMMYYYYFormat(deliveryTo.getHandOverDateStr()));
		runDo.setPreparationDate(DateFormatterUtil.slashDelimitedstringToDDMMYYYYFormat(deliveryTo.getPreparationDateStr()));
		runDo.setLastTrnsModifiedDate(DateFormatterUtil.slashDelimitedstringToDDMMYYYYFormat(deliveryTo.getLastTrnsModifiedDateStr()));
	}
}