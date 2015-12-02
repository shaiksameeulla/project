/**
 * 
 */
package src.com.dtdc.mdbServices.heldup;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import src.com.dtdc.constants.DRSConstants;
import src.com.dtdc.constants.HelpdUpReleaseConstants;
import src.com.dtdc.mdbDao.heldup.HeldUpReleaseMDBDAO;
import src.com.dtdc.mdbServices.CTBSApplicationMDBDAO;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.domain.expense.ExpenditureTypeDO;
import com.dtdc.domain.expense.MiscExpenseDO;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.manifest.ManifestTypeDO;
import com.dtdc.domain.master.ReasonDO;
import com.dtdc.domain.master.employee.EmployeeDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.master.warehouse.WarehouseDO;
import com.dtdc.domain.transaction.heldup.HeldUpReleaseDO;
import com.dtdc.domain.transaction.heldup.HeldUpReleaseItemsDtlsDO;
import com.dtdc.to.expense.CnMiscExpenseTO;
import com.dtdc.to.heldup.HeldUpDtlsTO;
import com.dtdc.to.heldup.HeldUpReleaseTO;
import com.dtdc.to.heldup.ReleaseDtlsTO;
import com.dtdc.to.heldup.ReleaseRowTO;
import com.dtdc.to.heldup.ReleaseTO;
import com.dtdc.to.heldup.heldupReleasedbsyn.HeldUpReleaseItemsDtlsTO;
import com.dtdc.to.heldup.heldupReleasedbsyn.HeldUpReleaseParentTO;

// TODO: Auto-generated Javadoc
/**
 * The Class HeldUpReleaseMDBServiceImpl.
 *
 * @author mohammes
 */
public class HeldUpReleaseMDBServiceImpl implements HeldUpReleaseMDBService {

	/** The logger. */
	private Logger logger = LoggerFactory
	.getLogger(HeldUpReleaseMDBServiceImpl.class);

	/** The held up release dao. */
	private HeldUpReleaseMDBDAO heldUpReleaseDAO;

	/** The ctbs application dao. */
	private CTBSApplicationMDBDAO ctbsApplicationDAO;

	/**
	 * Gets the held up release dao.
	 *
	 * @return the heldUpReleaseDAO
	 */
	public HeldUpReleaseMDBDAO getHeldUpReleaseDAO() {
		return heldUpReleaseDAO;
	}

	/**
	 * Sets the held up release dao.
	 *
	 * @param heldUpReleaseDAO the heldUpReleaseDAO to set
	 */
	public void setHeldUpReleaseDAO(HeldUpReleaseMDBDAO heldUpReleaseDAO) {
		this.heldUpReleaseDAO = heldUpReleaseDAO;
	}

	/**
	 * Gets the ctbs application dao.
	 *
	 * @return the ctbsApplicationDAO
	 */
	public CTBSApplicationMDBDAO getCtbsApplicationDAO() {
		return ctbsApplicationDAO;
	}

	/**
	 * Sets the ctbs application dao.
	 *
	 * @param ctbsApplicationDAO the ctbsApplicationDAO to set
	 */
	public void setCtbsApplicationDAO(CTBSApplicationMDBDAO ctbsApplicationDAO) {
		this.ctbsApplicationDAO = ctbsApplicationDAO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.heldup.HeldUpReleaseMDBService#saveHeldUp(CGBaseTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Boolean saveHeldUp(CGBaseTO heldUpTO) throws CGBusinessException,
	CGSystemException {
		HeldUpReleaseTO heldUpTo = conversionFromBase2To(heldUpTO);
		return saveHeldUp(heldUpTo);
	}

	/**
	 * Conversion from base2 to.
	 *
	 * @param heldUpTO the held up to
	 * @return the held up release to
	 * @throws NumberFormatException the number format exception
	 */
	private HeldUpReleaseTO conversionFromBase2To(CGBaseTO heldUpTO)
	throws NumberFormatException {
		List<HeldUpDtlsTO> heldToList = (List<HeldUpDtlsTO>) heldUpTO
		.getBaseList();
		HeldUpDtlsTO heldupReleaseTO=heldToList.get(0);
		HeldUpReleaseTO heldUpTo=new HeldUpReleaseTO();
		try {
			PropertyUtils.copyProperties(heldUpTo,heldupReleaseTO);
		} catch (Exception e1) {
			logger.error("HeldUpReleaseMDBServiceImpl::conversionFromBase2To::Exception occured:"
					+e1.getMessage());
		}
		List<ReleaseRowTO> releaseList = new ArrayList<ReleaseRowTO>();
		String rowIds[] = heldupReleaseTO.getSelectedCheckBox();
		heldupReleaseTO.getSlNo();
		int counter=0;
		for (String rowId2 : rowIds) {
			Integer rowid = Integer.parseInt(rowId2);
			if(!StringUtil.isEmpty(heldupReleaseTO
					.getManifestType()[rowid])){
				ReleaseRowTO releaseRowTo = new ReleaseRowTO();
				releaseRowTo.setManfstId(Integer.valueOf(heldupReleaseTO
						.getManifestType()[rowid]));

				// releaseRowTo.setManifestType(request.getParameterValues("to.manifestType")[i]);
				releaseRowTo.setConsignmentNumber(heldupReleaseTO
						.getConsignmentNo()[rowid]);
				releaseRowTo.setHeldupDateStr(heldupReleaseTO
						.getHeldUpDateStr()[rowid]);
				releaseRowTo.setLocation(heldupReleaseTO.getLocation()[rowid]);
				releaseRowTo.setLocationId(heldupReleaseTO
						.getLocationId()[rowid]);
				releaseRowTo.setReasonId(Integer.parseInt(heldupReleaseTO
						.getReasonType()[rowid]));
				releaseRowTo.setHeldUpRemarks(heldupReleaseTO
						.getHeldUpRemarks()[rowid]);
				/*if(StringUtil.isEmptyInteger(heldupReleaseTO.getRowNum()[rowid])){
				releaseRowTo.setRowSeqNum(heldupReleaseTO.getRowNum()[rowid]);
			}else{*/
				releaseRowTo.setRowSeqNum(++counter);
				//}
				releaseRowTo.setRack(heldupReleaseTO.getRack()[rowid]);
				releaseRowTo.setPosition(heldupReleaseTO.getPosition()[rowid]);
				releaseRowTo.setHeldUpCause(heldupReleaseTO.getHeldUpCause()[rowid]);
				if(heldupReleaseTO.getHeldUpCause()[rowid].equalsIgnoreCase("OTH")) {
					releaseRowTo.setHeldUpCauseOthers(heldupReleaseTO.getHeldUpCauseOthers()[rowid]);
				}
				releaseRowTo.setStorageLocationId(StringUtil.isEmpty(heldupReleaseTO.getStorageLocId()[rowid])?null:Integer.valueOf(heldupReleaseTO.getStorageLocId()[rowid]));



				releaseList.add(releaseRowTo);
			}
		}
		heldUpTo.setReleaseRowTo(releaseList);
		return heldUpTo;
	}




	/**
	 * Saves the held up rows.
	 *
	 * @param heldUpReleaseTO the held up release to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public Boolean saveHeldUp(HeldUpReleaseTO heldUpReleaseTO) throws CGBusinessException,CGSystemException {
		Boolean heldUpResults = false;
		HeldUpReleaseDO heldUpReleaseDO = new HeldUpReleaseDO();
		heldUpReleaseDO = copyHeldUpReleaseTO2DO(heldUpReleaseTO);
		//Save the list of held up records
		heldUpResults=heldUpReleaseDAO.saveHeldUp(heldUpReleaseDO);

		/* 
		 * Get the list of held up records. This step is necessary for
		 * getting the newly created held up id's. Then, these id's will be saved in the 
		 * different tables as foreign keys.
		 * dtdc_f_manifest
		 * dtdc_f_dispatch
		 * dtdc_f_vehicle_manifest_load
		 * dtdc_f_delivery
		 * dtdc_f_booking 
		 * 
		 */
		String heldUpNumber = heldUpReleaseTO.getHeldUpNumber();
		heldUpResults = processHeldUpRelease(heldUpResults, heldUpNumber);
		return heldUpResults;
	}

	/**
	 * Process held up release.
	 *
	 * @param heldUpResults the held up results
	 * @param heldUpNumber the held up number
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 * @throws NumberFormatException the number format exception
	 */
	private Boolean processHeldUpRelease(Boolean heldUpResults,
			String heldUpNumber) throws CGBusinessException, CGSystemException,
			NumberFormatException {
		HeldUpReleaseDO heldupDO = heldUpReleaseDAO.find(heldUpNumber);
		if(heldupDO != null){
			Set<HeldUpReleaseItemsDtlsDO> itemList = heldupDO.getItemDetailsSet();
			if(itemList!=null && itemList.size()>0) {
				for(HeldUpReleaseItemsDtlsDO item : itemList) {
					String manifestCode = item.getMnfstTypeId().getMnfstCode();
					if(manifestCode != null && !manifestCode.equals("")){
						if((manifestCode.equals(HelpdUpReleaseConstants.BAG_MANIFEST_NONDOX))||
								(manifestCode.equals(HelpdUpReleaseConstants.BAG_MANIFEST_DOX))||
								(manifestCode.equals(HelpdUpReleaseConstants.MASTER_BAG_MANIFEST))||
								(manifestCode.equals(HelpdUpReleaseConstants.PACKET_MANIFEST))||
								(manifestCode.equals(HelpdUpReleaseConstants.ROBO_CHECKLIST))||
								(manifestCode.equals(HelpdUpReleaseConstants.POD))||
								(manifestCode.equals(HelpdUpReleaseConstants.MNP_MANIFEST))||
								(manifestCode.equals(HelpdUpReleaseConstants.RE_ROUT_MANIFEST))||
								(manifestCode.equals(HelpdUpReleaseConstants.PAPER_MANIFEST))||
								(manifestCode.equals(HelpdUpReleaseConstants.SELF_SECTOR_MANIFEST))||
								(manifestCode.equals(HelpdUpReleaseConstants.AGENT_MANIFEST_INTL))){
							// List<ManifestDO> manifestsDOList = heldUpReleaseDAO.findChildManifestsByMFNumber(item.getManifestNumber());
							List<ManifestDO> manifestsDOList = heldUpReleaseDAO.getMasterbagChildList(item.getManifestNumber());
							if (!manifestsDOList.isEmpty()) {
								updateManifestDOList(manifestsDOList, heldupDO);//Update the Manifest Status and HeldUpReleaseID 
								heldUpReleaseDAO.updateManifest(manifestsDOList);//Update the Manifest table
							}
						}else if(manifestCode.equals(HelpdUpReleaseConstants.VEHICLE_MANIFEST)){
							List<String> manifestNoList = heldUpReleaseDAO.updateVehicleManifest(Integer.valueOf(item.getManifestNumber()),heldupDO);
							int counter = 0; 
							while(manifestNoList.size() > counter){
								List<ManifestDO> manifestsDOList = heldUpReleaseDAO.getMasterbagChildList(manifestNoList.get(counter));
								if (!manifestsDOList.isEmpty()) {
									updateManifestDOList(manifestsDOList, heldupDO);//Update the Manifest Status and HeldUpReleaseID 
									heldUpReleaseDAO.updateManifest(manifestsDOList);//Update the Manifest table
								}
								counter++;
							}
						}else if(manifestCode.equals(HelpdUpReleaseConstants.FRANCHISEE_DELIVERY_MANIFEST)){
							heldUpReleaseDAO.updateFRDeliveryManifest(item.getManifestNumber(),heldupDO,HelpdUpReleaseConstants.MANIFEST_STATUS_HELDUP);//Update the delivery table
						}else if(manifestCode.equals(HelpdUpReleaseConstants.BRANCH_DELIVERY_MANIFEST)){
							heldUpReleaseDAO.updateBRDeliveryManifest(item.getManifestNumber(),heldupDO, HelpdUpReleaseConstants.MANIFEST_STATUS_HELDUP);//Update the delivery table
						}else if((manifestCode.equals(HelpdUpReleaseConstants.CONSIGNMENT))){
							heldUpReleaseDAO.updateBookingConsignment(item.getManifestNumber(),heldupDO,HelpdUpReleaseConstants.MANIFEST_STATUS_HELDUP);//Update the booking table
						}else if(manifestCode.equals(HelpdUpReleaseConstants.COLOADER_MANIFEST)){
							heldUpReleaseDAO.updateCDDispatch(item.getManifestNumber(),heldupDO,HelpdUpReleaseConstants.MANIFEST_STATUS_HELDUP);
						}

						/* else if(manifestCode.equals(HelpdUpReleaseConstants.BAG_MANIFEST_DOX)||(manifestCode.equals(HelpdUpReleaseConstants.BAG_MANIFEST_NONDOX))){
								List<ManifestDO> manifestsDOList =heldUpReleaseDAO.getMasterbagChildListForDoxNonDox(item.getManifestNumber(),manifestCode);
								if (!manifestsDOList.isEmpty()) {
									updateManifestDOList(manifestsDOList, heldupDO);//Update the Manifest Status and HeldUpReleaseID 
									heldUpReleaseDAO.updateManifest(manifestsDOList);//Update the Manifest table
								}
							}	*/
					}
				}
			}
			heldUpResults=true;
		}
		return heldUpResults;
	}

	/**
	 * Copy held up release t o2 do.
	 *
	 * @param heldUpReleaseTO the held up release to
	 * @return the held up release do
	 * @throws CGBusinessException the cG business exception
	 */
	private HeldUpReleaseDO copyHeldUpReleaseTO2DO(HeldUpReleaseTO heldUpReleaseTO) 
	throws CGBusinessException {
		HeldUpReleaseDO heldUpReleaseDO = new HeldUpReleaseDO();
		HeldUpReleaseItemsDtlsDO itemDtlsDO = null;
		Set<HeldUpReleaseItemsDtlsDO> heldupItemDetailsSet = new HashSet<HeldUpReleaseItemsDtlsDO>();

		//Setting the Header fields
		//Setting the receiving office
		OfficeDO recvingOfficeDO = new OfficeDO();
		recvingOfficeDO.setOfficeId(heldUpReleaseTO.getRcvngofficeTypeId());
		//Setting the data entry office
		OfficeDO dataEntryOfficeDO = new OfficeDO();
		dataEntryOfficeDO.setOfficeId(heldUpReleaseTO.getDataEntryOfficeCodeId());
		//Setting the Employee ID (Entered By)		 
		EmployeeDO empDO = new EmployeeDO();
		empDO.setEmployeeId(StringUtil.isEmptyInteger(heldUpReleaseTO.getEmpId())?null:heldUpReleaseTO.getEmpId());
		heldUpReleaseDO.setEmployeeId(empDO);
		heldUpReleaseDO.setUserId(heldUpReleaseTO.getUserId());
		heldUpReleaseDO.setReceiveOfficeId(recvingOfficeDO);
		heldUpReleaseDO.setEntryOfficeId(dataEntryOfficeDO);
		heldUpReleaseDO.setDbServer(heldUpReleaseTO.getDbServer());
		heldUpReleaseDO.setHeldupNumber(heldUpReleaseTO.getHeldUpNumber());

		heldUpReleaseDO.setCurDateTime(DateFormatterUtil.combineDateWithTimeHHMM(heldUpReleaseTO.getOperationDateStr(), heldUpReleaseTO.getOperationTimeStr()));
		//Setting the rows

		List<ReleaseRowTO> releaseRowTo=heldUpReleaseTO.getReleaseRowTo();
		int count=0;
		for(ReleaseRowTO rowTo:releaseRowTo){
			itemDtlsDO = new HeldUpReleaseItemsDtlsDO();
			//Setting the Manifest Type DO
			ManifestTypeDO manifestTypeDO = new ManifestTypeDO();
			manifestTypeDO.setMnfstTypeId(rowTo.getManfstId());
			itemDtlsDO.setMnfstTypeId(manifestTypeDO);
			//Setting the Reason for heldup / release
			ReasonDO reasonDO = new ReasonDO();			 
			reasonDO.setReasonId(rowTo.getReasonId());
			itemDtlsDO.setHeldupReasonId(reasonDO);
			itemDtlsDO.setManifestNumber(rowTo.getConsignmentNumber());
			if(StringUtil.isEmptyInteger(rowTo.getRowSeqNum())){
				itemDtlsDO.setRowSeqNum(++count);
			}else{
				itemDtlsDO.setRowSeqNum(rowTo.getRowSeqNum());
			}
			itemDtlsDO.setDbServer(heldUpReleaseTO.getDbServer());
			itemDtlsDO.setHeldupDate(DateFormatterUtil.stringToDateFormatter(
					rowTo.getHeldupDateStr(),DateFormatterUtil.DDMMYYYY_FORMAT));
			// itemDtlsDO.setReleaseReasonId(reasonDO);
			itemDtlsDO.setLocation(rowTo.getLocation());
			itemDtlsDO.setHeldUpRemarks(rowTo.getHeldUpRemarks());
			itemDtlsDO.setHeldUpStatus("Y");
			itemDtlsDO.setHeldupDO(heldUpReleaseDO);
			itemDtlsDO.setHeldUpCause(rowTo.getHeldUpCause());
			itemDtlsDO.setRack(rowTo.getRack());
			itemDtlsDO.setPosition(rowTo.getPosition());
			if(rowTo.getHeldUpCause().equalsIgnoreCase("OTH")) {
				itemDtlsDO.setHeldUpCauseOthers(rowTo.getHeldUpCauseOthers());
			}
			if(!StringUtil.isEmptyInteger((rowTo.getStorageLocationId()))){
				WarehouseDO storageLocationId=new WarehouseDO();
				storageLocationId.setWarehouseId(rowTo.getStorageLocationId());
				itemDtlsDO.setStorageLocId(storageLocationId);
				//itemDtlsDO.setRack(rack)
			}
			itemDtlsDO.setReleaseAction(rowTo.getReleaseAction());


			heldupItemDetailsSet.add(itemDtlsDO);
		}
		heldUpReleaseDO.setItemDetailsSet(heldupItemDetailsSet);

		return heldUpReleaseDO;
	}

	/**
	 * Update manifest do list.
	 *
	 * @param manifestDOList the manifest do list
	 * @param heldupDO the heldup do
	 */
	private void updateManifestDOList(List<ManifestDO> manifestDOList, HeldUpReleaseDO heldupDO){
		int counter = 0;
		while(manifestDOList.size() > counter){
			manifestDOList.get(counter).setStatus(HelpdUpReleaseConstants.MANIFEST_STATUS_HELDUP);
			manifestDOList.get(counter).setHeldupDO(heldupDO);
			counter++;
		}
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.heldup.HeldUpReleaseMDBService#saveRelease(CGBaseTO)
	 */
	@Override
	public Boolean saveRelease(CGBaseTO releaseTo) throws CGBusinessException,
	CGSystemException {
		List<ReleaseDtlsTO> heldToList = (List<ReleaseDtlsTO>) releaseTo
		.getBaseList();
		List<CnMiscExpenseTO> miscExpenseTOList = (List<CnMiscExpenseTO>)releaseTo.getJsonChildObject();
		ReleaseDtlsTO heldupReleaseTO=heldToList.get(0);
		ReleaseTO releaseDtls=new ReleaseTO();
		try {
			PropertyUtils.copyProperties(releaseDtls,heldupReleaseTO);
		} catch (Exception e1) {
			logger.error("HeldUpReleaseMDBServiceImpl::saveRelease::Exception occured:"
					+e1.getMessage());
		}
		if(miscExpenseTOList != null && !miscExpenseTOList.isEmpty()){
			releaseDtls.setMiscExpenseTOList(miscExpenseTOList);
		}
		return null;
	}

	/**
	 * Save release details.
	 *
	 * @param releaseTO the release to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public Boolean saveReleaseDetails(ReleaseTO releaseTO)
	throws CGBusinessException,CGSystemException {
		Boolean result=false;
		List<ReleaseRowTO> rowTo = releaseTO.getListReasonRowTO();
		HeldUpReleaseDO heldupDO=new HeldUpReleaseDO();
		heldupDO.setHeldupReleaseId(releaseTO.getHeldUpReleaseId());
		heldupDO.setHeldupNumber(releaseTO.getHeldUpNumber());
		heldupDO.setCurDateTime(releaseTO.getCurrentDate());
		EmployeeDO empDo=new EmployeeDO();
		empDo.setEmployeeId(releaseTO.getEmployeeID());
		heldupDO.setEmployeeId(empDo);
		OfficeDO entryOfficeId=new OfficeDO();
		entryOfficeId.setOfficeId(releaseTO.getDataEntryOfficeId());
		heldupDO.setEntryOfficeId(entryOfficeId);
		OfficeDO receiveOfficeId=new OfficeDO();
		receiveOfficeId.setOfficeId(releaseTO.getDataReceivingOfficeId());
		heldupDO.setReceiveOfficeId(receiveOfficeId);
		Set<HeldUpReleaseItemsDtlsDO> rowDoList=new HashSet<HeldUpReleaseItemsDtlsDO>(0);
		for(ReleaseRowTO releaseRowTo:rowTo){
			HeldUpReleaseItemsDtlsDO rowDo = new HeldUpReleaseItemsDtlsDO();
			CGObjectConverter.createDomainFromTo(releaseRowTo, rowDo);
			ManifestTypeDO mnfstTypeId= new ManifestTypeDO();
			mnfstTypeId.setMnfstTypeId(releaseRowTo.getManfstId());
			mnfstTypeId.setMnfstCode(releaseRowTo.getManifestCode());
			rowDo.setMnfstTypeId(mnfstTypeId);
			rowDo.setManifestNumber(releaseRowTo.getConsignmentNumber());
			ReasonDO heldupReasonId = new  ReasonDO();
			heldupReasonId.setReasonId(releaseRowTo.getReasonId());
			rowDo.setHeldupReasonId(heldupReasonId);
			rowDoList.add(rowDo);
		}
		heldupDO.setItemDetailsSet(rowDoList); 
		List<MiscExpenseDO> miscExpenseDOList=new ArrayList<MiscExpenseDO>(0);
		if(releaseTO.getMiscExpenseTOList() !=null && !releaseTO.getMiscExpenseTOList().isEmpty()){
			List<CnMiscExpenseTO> miscExpenseToList= releaseTO.getMiscExpenseTOList();
			if(miscExpenseToList!=null && !miscExpenseToList.isEmpty()){
				for(CnMiscExpenseTO miscExpenseTo:miscExpenseToList){
					MiscExpenseDO miscExpenseDo = getMiscExpenseDoFromTo(miscExpenseTo);
					//miscExpenseDo.setProcessName("HeldUpRelease");
					miscExpenseDOList.add(miscExpenseDo);
				}
			}

		}
		result =  heldUpReleaseDAO.saveReleaseDetails(heldupDO, miscExpenseDOList);
		return result;

	}

	/**
	 * Gets the misc expense do from to.
	 *
	 * @param teo the teo
	 * @return the misc expense do from to
	 */
	private MiscExpenseDO getMiscExpenseDoFromTo( CnMiscExpenseTO teo) {
		MiscExpenseDO dao = new MiscExpenseDO();
		dao.setConsignmentNumber("");
		dao.setExpenditureId(teo.getExpndTypeId());
		ExpenditureTypeDO expType = new ExpenditureTypeDO();
		expType.setExpndTypeId(teo.getExpenditureType());
		dao.setExpenditureType(expType);

		dao.setExpenditureAmount(teo.getExpenditureAmount());
		EmployeeDO auth = new EmployeeDO();
		auth.setEmployeeId(teo.getEmpId());
		dao.setAuthorizer(auth);
		dao.setRemark(teo.getRemark());
		dao.setExpenditureDate(DateFormatterUtil.getDateFromStringDDMMYYY(teo.getExpenditureDate()));
		dao.setVoucherNumber(teo.getVoucherNumber());
		dao.setVoucherDate(DateFormatterUtil.getDateFromStringDDMMYYY(teo.getVoucherDate()));
		dao.setConsignmentNumber(teo.getCnNumber());
		dao.setApproved("Y");
		EmployeeDO enteredBy = new EmployeeDO();
		enteredBy.setEmployeeId(Integer.parseInt(teo.getEnteredBy()));
		dao.setEnterebBy(enteredBy);
		return dao;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.heldup.HeldUpReleaseMDBService#saveHeldUpReleaseDbSyn(CGBaseTO)
	 */
	@Override
	public Boolean saveHeldUpReleaseDbSyn(CGBaseTO heldUpTo)
	throws CGBusinessException, CGSystemException {
		List<HeldUpReleaseParentTO> heldupToList = (List<HeldUpReleaseParentTO>) heldUpTo
		.getBaseList();	
		saveHeldUpReleaseDbSyn(heldupToList);
		return true;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.heldup.HeldUpReleaseMDBService#saveHeldUpReleaseDbSyn(List)
	 */
	@Override
	public Boolean saveHeldUpReleaseDbSyn(
			List<HeldUpReleaseParentTO> helupReleaseList)
	throws CGBusinessException, CGSystemException {
		List<HeldUpReleaseDO> helupDoList =null;
		Boolean isSaved=Boolean.FALSE;
		List<String> heldupNumberList=null;
		if(!StringUtil.isEmptyList(helupReleaseList)){
			helupDoList= new ArrayList<HeldUpReleaseDO>(helupReleaseList.size());
			heldupNumberList= new ArrayList<String>(helupReleaseList.size());
		}
		try {

			for(HeldUpReleaseParentTO heldUpReleaseParentTO : helupReleaseList){
				heldupNumberList.add(heldUpReleaseParentTO.getHeldupNumber().trim());
				HeldUpReleaseDO mainDo = new HeldUpReleaseDO();
				PropertyUtils.copyProperties(mainDo, heldUpReleaseParentTO);
				mainDo.setDiFlag(DRSConstants.DI_FLAG);
				if(StringUtil.isEmptyInteger(heldUpReleaseParentTO.getUserId())){
					mainDo.setUserId(null);
				}
				if(!StringUtil.isEmptyInteger(heldUpReleaseParentTO.getEmployeeIdVal() )){
					EmployeeDO empDo=new EmployeeDO();
					empDo.setEmployeeId(heldUpReleaseParentTO.getEmployeeIdVal());
					mainDo.setEmployeeId(empDo);
				}
				if(!StringUtil.isEmptyInteger(heldUpReleaseParentTO.getEntryOfficeIdVal())){
					OfficeDO entryOfficeId=new OfficeDO();
					entryOfficeId.setOfficeId(heldUpReleaseParentTO.getEntryOfficeIdVal());
					mainDo.setEntryOfficeId(entryOfficeId);
				}

				if(!StringUtil.isEmptyInteger(heldUpReleaseParentTO.getReceiveOfficeIdVal())){
					OfficeDO recvngOfficeId=new OfficeDO();
					recvngOfficeId.setOfficeId(heldUpReleaseParentTO.getReceiveOfficeIdVal());
					mainDo.setReceiveOfficeId(recvngOfficeId);
				}

				/*getHeldUpReleaseChildDoListFromToList(heldUpReleaseParentTO,
						mainDo);*/
				if(heldUpReleaseParentTO.getHeldUpStatus()!=null && heldUpReleaseParentTO.getHeldUpStatus().length>0){
					int size= heldUpReleaseParentTO.getHeldUpStatus().length;
					Set<HeldUpReleaseItemsDtlsDO> itemListDo=  new HashSet<HeldUpReleaseItemsDtlsDO>(size);

					for(int counter=0;counter<size;counter++){
						HeldUpReleaseItemsDtlsDO  childDo = new HeldUpReleaseItemsDtlsDO();
						childDo.setHeldupDate(!ObjectUtils.isEmpty(heldUpReleaseParentTO.getHeldupDateStr())?DateFormatterUtil.slashDelimitedstringToDDMMYYYYFormat(heldUpReleaseParentTO.getHeldupDateStr()[counter]):null);
						childDo.setReleaseDate(!ObjectUtils.isEmpty(heldUpReleaseParentTO.getReleaseDateStr())?DateFormatterUtil.slashDelimitedstringToDDMMYYYYFormat(heldUpReleaseParentTO.getReleaseDateStr()[counter]):null);
						childDo.setLocation(!ObjectUtils.isEmpty(heldUpReleaseParentTO.getLocation())?heldUpReleaseParentTO.getLocation()[counter]:null);
						childDo.setHeldUpRemarks(!ObjectUtils.isEmpty(heldUpReleaseParentTO.getHeldUpRemarks())?heldUpReleaseParentTO.getHeldUpRemarks()[counter]:null);
						childDo.setReleaseRemarks(!ObjectUtils.isEmpty(heldUpReleaseParentTO.getReleaseRemarks())?heldUpReleaseParentTO.getReleaseRemarks()[counter]:null);
						childDo.setHeldUpStatus(!ObjectUtils.isEmpty(heldUpReleaseParentTO.getHeldUpStatus())?heldUpReleaseParentTO.getHeldUpStatus()[counter]:null);
						childDo.setActiveInactiveStatus(!ObjectUtils.isEmpty(heldUpReleaseParentTO.getActiveInactiveStatus())?heldUpReleaseParentTO.getActiveInactiveStatus()[counter]:null);
						childDo.setManifestNumber(!ObjectUtils.isEmpty(heldUpReleaseParentTO.getActiveInactiveStatus())?heldUpReleaseParentTO.getActiveInactiveStatus()[counter]:null);
						childDo.setHeldUpCause(!ObjectUtils.isEmpty(heldUpReleaseParentTO.getHeldUpCause())? heldUpReleaseParentTO.getHeldUpCause()[counter]:null);
						childDo.setHeldUpCauseOthers(!ObjectUtils.isEmpty(heldUpReleaseParentTO.getHeldUpCauseOthers())?heldUpReleaseParentTO.getHeldUpCauseOthers()[counter]:null);
						childDo.setReleaseAction(!ObjectUtils.isEmpty(heldUpReleaseParentTO.getReleaseAction())?heldUpReleaseParentTO.getReleaseAction()[counter]:null);
						childDo.setAgentDestructionAmount(!ObjectUtils.isEmpty(heldUpReleaseParentTO.getAgentDestructionAmount())?heldUpReleaseParentTO.getAgentDestructionAmount()[counter]:null);
						childDo.setCustomerBilledAmount(!ObjectUtils.isEmpty(heldUpReleaseParentTO.getCustomerBilledAmount())?heldUpReleaseParentTO.getCustomerBilledAmount()[counter]:null);
						childDo.setRack(!ObjectUtils.isEmpty(heldUpReleaseParentTO.getRack())?heldUpReleaseParentTO.getRack()[counter]:null);
						childDo.setPosition(!ObjectUtils.isEmpty(heldUpReleaseParentTO.getPosition())?heldUpReleaseParentTO.getPosition()[counter]:null);
						childDo.setRowSeqNum(!ObjectUtils.isEmpty(heldUpReleaseParentTO.getRowSeqNum())?heldUpReleaseParentTO.getRowSeqNum()[counter]:counter);

						if(heldUpReleaseParentTO.getHeldupReasonIdVal()!=null && !StringUtil.isEmptyInteger(heldUpReleaseParentTO.getHeldupReasonIdVal()[counter])){
							ReasonDO heldupReasonId = new ReasonDO();
							heldupReasonId.setReasonId(heldUpReleaseParentTO.getHeldupReasonIdVal()[counter]);
							childDo.setHeldupReasonId(heldupReasonId);
						}
						if(heldUpReleaseParentTO.getReleaseReasonIdVal()!=null && !StringUtil.isEmptyInteger(heldUpReleaseParentTO.getReleaseReasonIdVal()[counter])){
							ReasonDO releaseReasonId = new ReasonDO();
							releaseReasonId.setReasonId(heldUpReleaseParentTO.getReleaseReasonIdVal()[counter]);
							childDo.setReleaseReasonId(releaseReasonId);
						}
						if(heldUpReleaseParentTO.getMnfstTypeIdVal()!=null && !StringUtil.isEmptyInteger(heldUpReleaseParentTO.getMnfstTypeIdVal()[counter])){
							ManifestTypeDO mnfstTypeId = new ManifestTypeDO();
							mnfstTypeId.setMnfstTypeId(heldUpReleaseParentTO.getMnfstTypeIdVal()[counter]);
							childDo.setMnfstTypeId(mnfstTypeId);
						}
						childDo.setHeldupDO(mainDo);
						itemListDo.add(childDo);
					}
					mainDo.setItemDetailsSet(itemListDo);
				}



				helupDoList.add(mainDo);
			}

		} catch (Exception e) {
			logger.error("HeldUpReleaseMDBServiceImpl ::saveHeldUpReleaseDbSyn :: Exception : "+e.getMessage());
			throw new CGBusinessException(e);
		}
		/*String heldUpNumber = mainDo.getHeldupNumber();
		 heldUpResults=heldUpReleaseDAO.saveHeldUp(mainDo);
		heldUpResults = processHeldUpRelease(heldUpResults, heldUpNumber);*/
		if(!StringUtil.isEmptyList(helupDoList)){
			heldUpReleaseDAO.delete(heldUpReleaseDAO.findHeldupDtls(heldupNumberList));
			heldUpReleaseDAO.saveHeldUpReleaseList(helupDoList);
		}else{
			logger.error("HeldUpReleaseMDBServiceImpl ::saveHeldUpReleaseDbSyn :: data not saved ");
		}
		return isSaved;
	}

	/**
	 * Gets the held up release child do list from to list.
	 *
	 * @param heldUpReleaseParentTO the held up release parent to
	 * @param mainDo the main do
	 * @return the held up release child do list from to list
	 * @throws IllegalAccessException the illegal access exception
	 * @throws InvocationTargetException the invocation target exception
	 * @throws NoSuchMethodException the no such method exception
	 */
	private void getHeldUpReleaseChildDoListFromToList(
			HeldUpReleaseParentTO heldUpReleaseParentTO, HeldUpReleaseDO mainDo)
	throws IllegalAccessException, InvocationTargetException,
	NoSuchMethodException {
		Set<HeldUpReleaseItemsDtlsDO> itemListDo=  new HashSet<HeldUpReleaseItemsDtlsDO>(0);
		List<HeldUpReleaseItemsDtlsTO>	heldupdtlsList = (List<HeldUpReleaseItemsDtlsTO>) heldUpReleaseParentTO.getJsonChildObject();
		for(HeldUpReleaseItemsDtlsTO childTo :heldupdtlsList){
			HeldUpReleaseItemsDtlsDO dtlsDo= new HeldUpReleaseItemsDtlsDO();
			PropertyUtils.copyProperties(dtlsDo, childTo);

			if(!StringUtil.isEmptyInteger(childTo.getMnfstTypeIdVal())){
				ManifestTypeDO mnfstTypeId = new ManifestTypeDO();
				mnfstTypeId.setMnfstTypeId(childTo.getMnfstTypeIdVal());
				dtlsDo.setMnfstTypeId(mnfstTypeId);
			}

			if(!StringUtil.isEmptyInteger(childTo.getHeldupReasonIdVal())){
				ReasonDO heldupReasonId = new ReasonDO();
				heldupReasonId.setReasonId(childTo.getHeldupReasonIdVal());
				dtlsDo.setHeldupReasonId(heldupReasonId);
			}

			if(!StringUtil.isEmptyInteger(childTo.getReleaseReasonIdVal())){
				ReasonDO releaseReasonId = new ReasonDO();
				releaseReasonId.setReasonId(childTo.getReleaseReasonIdVal());
				dtlsDo.setReleaseReasonId(releaseReasonId);
			}
			if(!StringUtil.isEmptyInteger(childTo.getStorageLocIdVal())){
				WarehouseDO storageLocId= new WarehouseDO();
				storageLocId.setWarehouseId(childTo.getStorageLocIdVal());
				dtlsDo.setStorageLocId(storageLocId);
			}
			if(!StringUtil.isEmptyInteger(childTo.getTransLocationIdVal())){
				OfficeDO transLocationId=new OfficeDO();
				transLocationId.setOfficeId(childTo.getTransLocationIdVal());
				dtlsDo.setTransLocationId(transLocationId);
			}
			dtlsDo.setHeldupDO(mainDo);
			itemListDo.add(dtlsDo);

		}
		mainDo.setItemDetailsSet(itemListDo);
	}




}
