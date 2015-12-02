package src.com.dtdc.mdbServices.manifest;

/**
 * 
 */

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import src.com.dtdc.constants.CommonConstants;
import src.com.dtdc.constants.DeliveryManifestConstants;
import src.com.dtdc.constants.ManifestConstant;
import src.com.dtdc.mdbDao.manifest.PODMDBDAO;
import src.com.dtdc.mdbServices.CTBSApplicationMDBDAO;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.domain.booking.ProductDO;
import com.dtdc.domain.configurableparam.ConfigurableParamsDO;
import com.dtdc.domain.expense.ExpenditureTypeDO;
import com.dtdc.domain.expense.MiscExpenseDO;
import com.dtdc.domain.login.UserDO;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.manifest.ManifestTypeDO;
import com.dtdc.domain.master.customer.CustomerDO;
import com.dtdc.domain.master.employee.EmployeeDO;
import com.dtdc.domain.master.franchisee.FranchiseeDO;
import com.dtdc.domain.master.geography.CityDO;
import com.dtdc.domain.master.identityproof.IdentityProofDocDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.transaction.delivery.PODDeliveryDO;
import com.dtdc.to.expense.CnMiscExpenseTO;
import com.dtdc.to.manifest.OutgoingManifestPODTO;
import com.dtdc.to.manifest.UnstampedPODManifestTO;

// TODO: Auto-generated Javadoc
/**
 * The Class PODMDBServiceImpl.
 *
 * @author mohammal
 */
public class PODMDBServiceImpl implements PODMDBService {

	/** The pod dao. */
	private PODMDBDAO podDao ;

	/** The ctbs application mdbdao. */
	private CTBSApplicationMDBDAO ctbsApplicationMDBDAO;	

	/**
	 * Gets the ctbs application mdbdao.
	 *
	 * @return the ctbsApplicationMDBDAO
	 */
	public CTBSApplicationMDBDAO getCtbsApplicationMDBDAO() {
		return ctbsApplicationMDBDAO;
	}

	/**
	 * Sets the ctbs application mdbdao.
	 *
	 * @param ctbsApplicationMDBDAO the ctbsApplicationMDBDAO to set
	 */
	public void setCtbsApplicationMDBDAO(CTBSApplicationMDBDAO ctbsApplicationMDBDAO) {
		this.ctbsApplicationMDBDAO = ctbsApplicationMDBDAO;
	}

	/**
	 * Gets the pod dao.
	 *
	 * @return the podDao
	 */
	public PODMDBDAO getPodDao() {
		return podDao;
	}

	/**
	 * Sets the pod dao.
	 *
	 * @param podDao the podDao to set
	 */
	public void setPodDao(PODMDBDAO podDao) {
		this.podDao = podDao;
	}


	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(PODMDBServiceImpl.class);

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.manifest.PODMDBService#savePOD(CGBaseTO)
	 */
	@Override
	public OutgoingManifestPODTO savePOD(CGBaseTO baseTo)
	throws Exception {
		OutgoingManifestPODTO podMfTo = (OutgoingManifestPODTO)baseTo.getBaseList().get(0);
		return savePOD(podMfTo);
	}

	/*@Override
	public OutgoingManifestPODTO savePOD(OutgoingManifestPODTO pod) throws Exception {

		if(pod.getPodType().equals(CommonConstants.MANIFEST_TYPE_AGAINST_INCOMING)) {
			List<ManifestDO> mfDetails = podDao.getIncomingPODManifestByMfNumber(pod.getPodMfNumber());
			if( mfDetails == null || mfDetails.isEmpty()) {
				int temp =0;
				for(String podNumber:pod.getPods()) {
					ManifestDO manifestDo = getManifestDoFromTo(pod, pod.getPodType());
					manifestDo.setConsgNumber(podNumber);
					manifestDo.setRemarks(pod.getPodRemarks()[temp]);
					podDao.savePodManifest(manifestDo);
				}
			}
		} else {
			List<ManifestDO> mfDetails = podDao.getOutgoingPODManifestByMfNumber(pod.getPodMfNumber());
			if( mfDetails == null || mfDetails.isEmpty()) {
				int temp =0;

				for(String podNumber:pod.getPods()) {
					ManifestDO manifestDo = getManifestDoFromTo(pod, pod.getPodType());
					manifestDo.setConsgNumber(podNumber);
					manifestDo.setNoOfPod(pod.getPods().length + "");
					if(pod.getPodType().equals(CommonConstants.MANIFEST_TYPE_AGAINST_INCOMING)) {
						manifestDo.setRemarks(pod.getPodRemarks()[temp]);
						temp++;
					}
					podDao.savePodManifest(manifestDo);
				}
			} 
		}
		return pod;
	}*/

	/**
	 * Gets the manifest do from to.
	 *
	 * @param pod the pod
	 * @param type the type
	 * @return the manifest do from to
	 * @throws Exception the exception
	 */
	private ManifestDO getManifestDoFromTo(OutgoingManifestPODTO pod, String type) throws Exception {
		ManifestDO manifestDo = new ManifestDO();

		manifestDo.setManifestNumber(pod.getPodMfNumber());
		manifestDo.setManifestType(type);
		manifestDo.setManifestDate(new Date());
		manifestDo.setManifestTime(pod.getManifestTime());
		manifestDo.setMnsftTypes(podDao.getPodManifestType());

		if(type.equals(CommonConstants.MANIFEST_TYPE_AGAINST_OUTGOING)) {

			OfficeDO dispRegion = new OfficeDO();
			dispRegion.setOfficeId(Integer.parseInt(pod.getDispatchingRegion()));
			manifestDo.setDespRegBranch(dispRegion);

			OfficeDO despBranch = new OfficeDO ();
			despBranch.setOfficeId(Integer.parseInt(pod.getDispatchingBranch()));
			manifestDo.setDespBranch(despBranch);

			OfficeDO destOffice = new OfficeDO();
			destOffice.setOfficeId(Integer.parseInt(pod.getDestination()));
			manifestDo.setDestBranch(destOffice);

			manifestDo.setPodHandover(pod.getHandOver());

			ProductDO product = new ProductDO();
			product.setProductId(Integer.parseInt(pod.getProductType()));
			manifestDo.setProduct(product);

			CustomerDO customer = new CustomerDO();
			customer.setCustomerId(Integer.parseInt(pod.getCustomerName()));
			manifestDo.setCustomer(customer);

			LOGGER.debug("PODServiceImpl::getManifestDoFromTo::product id: " + pod.getProductType());

		} else {
			OfficeDO orgBranch = new OfficeDO();
			orgBranch.setOfficeId(pod.getOrgBo());
			manifestDo.setOriginBranch(orgBranch);

			OfficeDO repBranch = new OfficeDO();
			repBranch.setOfficeId(pod.getOrgRo());
			manifestDo.setReportingBranch(repBranch);
		}

		//FIXME put code for pod type id

		return manifestDo;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.manifest.PODMDBService#updatePOD(CGBaseTO)
	 */
	public OutgoingManifestPODTO updatePOD(final CGBaseTO baseTo) throws Exception {
		OutgoingManifestPODTO podMfTo = (OutgoingManifestPODTO)baseTo.getBaseList().get(0);
		return updatePOD(podMfTo);
	}

	/*@Override
	public OutgoingManifestPODTO updatePOD(OutgoingManifestPODTO podTO) throws Exception {
		LOGGER.error("PODServiceImpl::updatePOD::Start=======>");

		PODDeliveryDO podDetails = getPODDeliveryDO(podTO);
		podDao.updatePODDetails(podDetails);

		return podTO;
	}

	private PODDeliveryDO getPODDeliveryDO(OutgoingManifestPODTO podTO) throws Exception {
		PODDeliveryDO podDetails = new PODDeliveryDO();

		podDetails.setConNum(podTO.getPodNumber());

		ProductDO product = ctbsApplicationMDBDAO.getProductByIdOrCode(Integer.parseInt(podTO.getProductType()), "");
		podDetails.setProduct(product);

		podDetails.setDeliveryStatus(DeliveryManifestConstants.DELIVERY_STATUS);
		podDetails.setConsgStatus(ManifestConstant.ACTIVE_DELIVERY_STATUS);
		if(podTO.getRelation() != null && !podTO.getRelation().equals(ManifestConstant.DEFAULT_SELECT_VALUE)){
			podDetails.setRelationShip(podTO.getRelation());
		}

		podDetails.setDeliveryDate(new Timestamp(DateFormatterUtil
				.combineDateWithTimeHHMM(podTO.getManifestDate(),
						podTO.getManifestTime()).getTime()));
		//podDetails.setDeliveryTime(podTO.getManifestTime());
		podDetails.setReceiverDetails(podTO.getRecieverDetails());
		podDetails.setPhNum(podTO.getRecieverPhone());

		IdentityProofDocDO idProofDO = null;
		if(podTO.getIdProofCode() != null && !podTO.getIdProofCode().equals(ManifestConstant.DEFAULT_SELECT_VALUE)){
			idProofDO = ctbsApplicationMDBDAO.getIdProofDocsByIdOrCode(null,podTO.getIdProofCode());
		}

		podDetails.setIdentityProofDO(idProofDO);
		podDetails.setIdentityProofNo(podTO.getIdProofDetail());

		if(podTO.getResidenceDelivery() != null && podTO.getResidenceDelivery().equals(ManifestConstant.DELIVERED_AT_RESIDENCE)){
			podDetails.setDeliveredAtResidence(ManifestConstant.DELIVERED_AT_RESIDENCE);
		}else{
			podDetails.setDeliveredAtResidence(ManifestConstant.NOT_DELIVERED_AT_RESIDENCE);
		}

		if(podTO.getSignatureReceived() != null && podTO.getSignatureReceived().equals(ManifestConstant.SIGNATURE_RECIVED)){
			podDetails.setSignatureReceived(ManifestConstant.SIGNATURE_RECIVED);
		}else{
			podDetails.setSignatureReceived(ManifestConstant.SIGNATURE_NOT_RECIVED);
		}

		return podDetails;
	}
	 */

	/*@Override
	public UnstampedPODManifestTO saveUnstampedPODManifest(CGBaseTO baseTO) throws CGSystemException {
		UnstampedPODManifestTO podTO = (UnstampedPODManifestTO) baseTO.getBaseList().get(0);
		return saveUnstampedPODManifest(podTO);
	}

	@Override
	public UnstampedPODManifestTO saveUnstampedPODManifest(UnstampedPODManifestTO podTO) throws CGSystemException {
		LOGGER.info("PODManifestMDBServiceImpl: saveUnstampedPODManifest(): START");
		List<ManifestDO> manifestDOList = createManifestDOfromTO(podTO);

		for (ManifestDO manifestDO: manifestDOList){
			podDao.savePodManifest(manifestDO);
		}
		LOGGER.info("PODManifestMDBServiceImpl: saveUnstampedPODManifest(): END");
		return podTO;
	}

	private List<ManifestDO> createManifestDOfromTO(UnstampedPODManifestTO podTO) throws CGSystemException {
		LOGGER.info("PODManifestMDBServiceImpl: createManifestDOfromTO(): START");
		List<ManifestDO> manifestDOList = new ArrayList<ManifestDO>();

	 *//**
	 * Find the number of valid PODs.
	 *
	 * @param pod the pod
	 * @return the outgoing manifest podto
	 * @throws Exception the exception
	 *//*
		int podCounter = 0;
		int noOfPods = podTO.getPodNo().length;
		for(int j = 0; j < noOfPods; j++){
			if (podTO.getPodNo()[j] != null && !podTO.getPodNo()[j].trim().equals("")) {
				podCounter++;
			}
		}
		for (int i = 0; i < noOfPods; i++) {

			if (podTO.getPodNo()[i] != null && !podTO.getPodNo()[i].trim().equals("")) {
				ManifestDO manifestDO = new ManifestDO();

				 Set Manifest Related Details 
				manifestDO.setManifestNumber(podTO.getPodTransactionNumber());
				manifestDO.setManifestDate(DateFormatterUtil.getDateFromString(
						podTO.getManifestDate(), DateFormatterUtil.DD_MM_YYYY));
				manifestDO.setManifestTime(podTO.getManifestTime());

				manifestDO.setTotConsgNum(podCounter);
				manifestDO.setManifestType(ManifestConstant.MANIFEST_TYPE_AGAINST_OUTGOING);
				manifestDO.setManifestTypeDefn(ManifestConstant.UNSATMPED_POD_MANIFEST_TYPE_DEFN);

				ManifestTypeDO manifestTypeDO = ctbsApplicationMDBDAO.getManifestTypeByCode(ManifestConstant.POD_MANIFEST_TYPE_CODE);
				manifestDO.setMnsftTypes(manifestTypeDO);

				EmployeeDO employeeDO = ctbsApplicationMDBDAO.getEmployeeByCodeOrID(podTO.getLoggedInUserId(), "");
				manifestDO.setEmployee(employeeDO);

				 POD Details 
				manifestDO.setConsgNumber(podTO.getPodNo()[i]);

				FranchiseeDO franchiseeDO = null;
				if(podTO.getCustId()[i] != null && podTO.getCustId()[i] != 0){
					franchiseeDO = ctbsApplicationMDBDAO.getFranchiseeByFrCodeOrId("", podTO.getCustId()[i]);
				}
				manifestDO.setFranchisee(franchiseeDO);

				CityDO cityDO = null;
				if(podTO.getCityId()[i] != null && podTO.getCityId()[i] != 0){
					cityDO = ctbsApplicationMDBDAO.getCityByIdOrCode(podTO.getCityId()[i], "");
				}
				manifestDO.setDestCity(cityDO);

				OfficeDO originOfficeDO = null;
				if(podTO.getDestBranchId() != null && podTO.getDestBranchId() != 0){
					originOfficeDO = ctbsApplicationMDBDAO.getBranchByCodeOrID(podTO.getDestBranchId(), "");
				}
				manifestDO.setOriginBranch(originOfficeDO);

				manifestDO.setRemarks(podTO.getRemark()[i]);
				manifestDO.setDbServer(podTO.getDbServer());

				manifestDOList.add(manifestDO);
			}
		}
		LOGGER.info("PODManifestMDBServiceImpl: createManifestDOfromTO(): END");
		return manifestDOList;
	}
	  */

	//** Add By Jay 03-Oct-2011 --**/

	@Override
	public OutgoingManifestPODTO savePOD(OutgoingManifestPODTO pod) throws Exception {
		//-----------
		try{
			if(pod.getPodType().equals(ManifestConstant.MANIFEST_TYPE_AGAINST_INCOMING)) {
				int temp =0;

				String[] rowTypes = pod.getRowType();
				for(String podNumber:pod.getPods()) {

					if(StringUtil.equals(rowTypes[temp], "Update")){


						List<ManifestDO> mfDetails = podDao.getIncomingPODManifestByConsigNumber(podNumber,pod.getPodMfNumber());

						if( mfDetails != null) {
							if(!(mfDetails.isEmpty())) {
								int serialNo = 1;
								for(ManifestDO manifestDo : mfDetails){
									manifestDo.setConsgNumber(podNumber);

									manifestDo.setLineItemSequenceNo(serialNo);

									if (pod.getUserId() != null
											&& pod.getUserId() != 0) {
										UserDO userDO= new UserDO();
										userDO.setUserId(pod.getUserId());
										manifestDo.setUser(userDO);
									}else{
										manifestDo.setUser(null);
									}
									//		manifestDo.setUser(user)

									manifestDo.setTransLastModifiedDate(DateFormatterUtil
											.getCurrentDate());
									if(pod.getPodType().equals(ManifestConstant.MANIFEST_TYPE_AGAINST_INCOMING)) {
										manifestDo = getIncomingUpdatePODManifestDoFromTo(pod, pod.getPodType(),manifestDo);
										manifestDo.setRemarks(pod.getPodRemarks()[temp]);
										temp++;
									}
									podDao.savePodManifest(manifestDo);
									serialNo++;

								}
							} else {
								mfDetails.clear();
								/*	MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(ErrorCodeConstants.DUPLICATE_MANIFEST_NUMBER_INFO_CODE, MessageType.Error, "podMfNumber", pod.getPodMfNumber());
								CGBusinessException bex = new CGBusinessException(msgWrapper);
								pod.setBusinessException(bex);*/
							}
						}
					}else{

						List<ManifestDO> mfDetailsList = podDao.getIncomingPODManifestByMfNumber(pod.getPodMfNumber());

						if(mfDetailsList!=null && !(mfDetailsList.isEmpty())){
							ManifestDO manifestDo = null;
							int serialNo = 1;
							if(manifestDo == null){
								manifestDo = getIncomingPODManifestDoFromTo(pod, pod.getPodType());
								int noOfValidPod = getNoOfValidPOD(pod.getPods());
								manifestDo.setNoOfPod(noOfValidPod + "");
								manifestDo.setServerDate(DateFormatterUtil.getCurrentDate());

								if (pod.getUserId() != null
										&& pod.getUserId() != 0) {
									UserDO userDO= new UserDO();
									userDO.setUserId(pod.getUserId());
									manifestDo.setUser(userDO);
								}else{
									manifestDo.setUser(null);
								}
								manifestDo.setLineItemSequenceNo(serialNo);
								if(pod.getPodType().equals(ManifestConstant.MANIFEST_TYPE_AGAINST_INCOMING)) {
									manifestDo = getIncomingPODManifestDoFromTo(pod, pod.getPodType());
									manifestDo.setRemarks(pod.getPodRemarks()[temp]);
									temp++;
								}
							}
							manifestDo.setConsgNumber(podNumber);
							podDao.savePodManifest(manifestDo);
							serialNo++;
						}

						if( mfDetailsList != null) {
							if(!(mfDetailsList.isEmpty())) {

								//int temp =0;

								List<ManifestDO> manifestDoList = podDao.getOutgoingPODManifestByMfNumber(pod.getPodMfNumber());

								if( manifestDoList != null) {
									if(!(manifestDoList.isEmpty())) {
										int serialNo = 1;
										for(ManifestDO manifestDOObj : manifestDoList){
											manifestDOObj.setManifestType(ManifestConstant.MANIFEST_TYPE_AGAINST_INCOMING);
											manifestDOObj.setServerDate(DateFormatterUtil
													.stringToDDMMYYYYFormat(pod.getManifestDate()));
											manifestDOObj.setTransLastModifiedDate(DateFormatterUtil
													.getCurrentDate());
											manifestDOObj.setLineItemSequenceNo(serialNo);
											if (pod.getUserId() != null
													&& pod.getUserId() != 0) {
												UserDO userDO= new UserDO();
												manifestDOObj.setUser(userDO);
											}else{
												manifestDOObj.setUser(null);
											}
											podDao.saveIncomingPodManifest(manifestDOObj);
											temp++;
											serialNo++;
										}
										/*for(String podNumber:pod.getPods()) {
										if(manifestDo == null){
											manifestDo = getManifestDoFromTo(pod, pod.getPodType());
											int noOfValidPod = getNoOfValidPOD(pod.getPods());
											manifestDo.setNoOfPod(noOfValidPod + "");
										}
										manifestDo.setConsgNumber(podNumber);
										//manifestDo.setRemarks(pod.getPodRemarks()[temp]);
										//temp++;
									}*/
									}
								}else{
									List<ManifestDO> manifestDOList_Obj = podDao.getIncomingPODManifestByConsigNumber(podNumber,pod.getPodMfNumber());
									int serialNo = 1;
									if( manifestDOList_Obj == null || manifestDOList_Obj.isEmpty()) {
										ManifestDO manifestDO = null;
										manifestDO = getIncomingPODManifestDoFromTo(pod, pod.getPodType());
										manifestDO.setManifestType(ManifestConstant.MANIFEST_TYPE_AGAINST_INCOMING);
										manifestDO.setLineItemSequenceNo(serialNo);
										manifestDO.setServerDate(DateFormatterUtil.getCurrentDate());

										if (pod.getUserId() != null
												&& pod.getUserId() != 0) {
											UserDO userDO= new UserDO();
											userDO.setUserId(pod.getUserId());
											manifestDO.setUser(userDO);
										}else{
											manifestDO.setUser(null);
										}
										podDao.savePodManifest(manifestDO);
										serialNo++;
										temp++;
									}
								}
							} /*else {
								MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(ErrorCodeConstants.DUPLICATE_MANIFEST_NUMBER_INFO_CODE, MessageType.Error, "podMfNumber", pod.getPodMfNumber());
								CGBusinessException bex = new CGBusinessException(msgWrapper);
								pod.setBusinessException(bex);
							}*/
						}
					}
				}
			} else {
				/* Save Misc Expense Data */
				List<MiscExpenseDO> miscExpenseDOList = null;
				List<CnMiscExpenseTO> miscExpenseToList = pod.getMiscExpenseList();
				if (miscExpenseToList != null && !miscExpenseToList.isEmpty()) {
					miscExpenseDOList = new ArrayList<MiscExpenseDO>();
					for (CnMiscExpenseTO miscExpenseTo : miscExpenseToList) {
						MiscExpenseDO miscExpenseDo = getMiscExpenseDoFromTo(miscExpenseTo);
						//miscExpenseDo.setProcessName("Outgoing POD Manifest");
						miscExpenseDOList.add(miscExpenseDo);
					}
				}
				if (miscExpenseDOList != null && !miscExpenseDOList.isEmpty()) {
					podDao.saveMiscExp(miscExpenseDOList);
				}
				for(String podNumber:pod.getPods()) {
					int serialNo = 1;
					if(podNumber != null && !podNumber.trim().equals("")){
						List<ManifestDO> mfDetails = podDao.getOutgoingPODManifestByConsigNumber(podNumber,pod.getPodMfNumber());
						if( mfDetails == null || mfDetails.isEmpty()) {
							int temp =0;
							ManifestDO manifestDo = null;


							if(manifestDo == null){
								manifestDo = getManifestDoFromTo(pod, pod.getPodType());
								int noOfValidPod = getNoOfValidPOD(pod.getPods());
								manifestDo.setNoOfPod(noOfValidPod + "");
							}
							manifestDo.setConsgNumber(podNumber);
							manifestDo.setDbServer(pod.getDbServer());
							manifestDo.setServerDate(DateFormatterUtil
									.stringToDDMMYYYYFormat(pod.getManifestDate()));
							manifestDo.setTransLastModifiedDate(DateFormatterUtil
									.getCurrentDate());
							if (pod.getUserId() != null
									&& pod.getUserId() != 0) {
								UserDO userDO= new UserDO();
								userDO.setUserId(pod.getUserId());
								manifestDo.setUser(userDO);
							}else{
								manifestDo.setUser(null);
							}
							manifestDo.setLineItemSequenceNo(serialNo);
							if(pod.getPodType().equals(ManifestConstant.MANIFEST_TYPE_AGAINST_INCOMING)) {
								manifestDo.setRemarks(pod.getPodRemarks()[temp]);
								manifestDo.setDbServer(pod.getDbServer());
								temp++;
							}
							podDao.savePodManifest(manifestDo);
							serialNo++;

						} else {
							mfDetails.clear();
							/*MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(ErrorCodeConstants.DUPLICATE_MANIFEST_NUMBER_INFO_CODE, MessageType.Error, "podMfNumber", pod.getPodMfNumber());
							CGBusinessException bex = new CGBusinessException(msgWrapper);
							pod.setBusinessException(bex);*/
						}
					}
				}
			}
		}catch (Exception ex) {
			LOGGER.error("PODMDBServiceImpl::savePOD::Exception occured:"
					+ex.getMessage());
		}
		return pod;
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


	/**
	 * Gets the incoming update pod manifest do from to.
	 *
	 * @param pod the pod
	 * @param type the type
	 * @param manifestDo the manifest do
	 * @return the incoming update pod manifest do from to
	 * @throws Exception the exception
	 */
	private ManifestDO getIncomingUpdatePODManifestDoFromTo(OutgoingManifestPODTO pod, String type,ManifestDO manifestDo) throws Exception {


		manifestDo.setManifestNumber(pod.getPodMfNumber());
		manifestDo.setManifestType(type);
		manifestDo.setManifestDate(new Date());
		manifestDo.setManifestTime(pod.getManifestTime());
		manifestDo.setMnsftTypes(podDao.getPodManifestType());

		if(type.equals(ManifestConstant.MANIFEST_TYPE_AGAINST_INCOMING)) {

			OfficeDO dispatchingRegion = ctbsApplicationMDBDAO.getBranchByCodeOrID(null, pod.getDispatchingRegionCode());
			new OfficeDO();
			//dispRegion.setOfficeId(Integer.parseInt(pod.getDispatchingRegion()));
			manifestDo.setDespRegBranch(dispatchingRegion);

			OfficeDO dispatchingBranch =ctbsApplicationMDBDAO.getBranchByCodeOrID(null, pod.getDispatchingBranchCode());
			new OfficeDO ();
			//	despBranch.setOfficeId(Integer.parseInt(pod.getDispatchingBranch()));
			manifestDo.setDespBranch(dispatchingBranch);

			//OfficeDO destOffice = new OfficeDO();

			//manifestDo.setDestBranch(destOffice);


			//manifestDo.setHandoverDate(new Date());



			//CustomerDO customer = new CustomerDO();
			//customer.setCustomerId(Integer.parseInt(pod.getCustomerName()));
			//manifestDo.setCustomer(customer);




		} else {
			OfficeDO orgBranch = new OfficeDO();
			orgBranch.setOfficeId(pod.getOrgBo());
			manifestDo.setOriginBranch(orgBranch);

			OfficeDO repBranch = new OfficeDO();
			repBranch.setOfficeId(pod.getOrgRo());
			manifestDo.setReportingBranch(repBranch);
		}

		//FIXME put code for pod type id

		return manifestDo;
	}

	/**
	 * Gets the incoming pod manifest do from to.
	 *
	 * @param pod the pod
	 * @param type the type
	 * @return the incoming pod manifest do from to
	 * @throws Exception the exception
	 */
	private ManifestDO getIncomingPODManifestDoFromTo(OutgoingManifestPODTO pod, String type) throws Exception {
		ManifestDO manifestDo = new ManifestDO();

		manifestDo.setManifestNumber(pod.getPodMfNumber());
		manifestDo.setManifestType(type);
		manifestDo.setManifestDate(new Date());
		manifestDo.setManifestTime(pod.getManifestTime());
		manifestDo.setMnsftTypes(podDao.getPodManifestType());

		if(type.equals(ManifestConstant.MANIFEST_TYPE_AGAINST_INCOMING)) {

			OfficeDO dispatchingRegion = ctbsApplicationMDBDAO.getBranchByCodeOrID(null, pod.getDispatchingRegionCode());
			new OfficeDO();
			//dispRegion.setOfficeId(Integer.parseInt(pod.getDispatchingRegion()));
			manifestDo.setDespRegBranch(dispatchingRegion);

			OfficeDO dispatchingBranch =ctbsApplicationMDBDAO.getBranchByCodeOrID(null, pod.getDispatchingBranchCode());
			new OfficeDO ();
			//	despBranch.setOfficeId(Integer.parseInt(pod.getDispatchingBranch()));
			manifestDo.setDespBranch(dispatchingBranch);

			//OfficeDO destOffice = new OfficeDO();

			//manifestDo.setDestBranch(destOffice);


			//manifestDo.setHandoverDate(new Date());



			//CustomerDO customer = new CustomerDO();
			//customer.setCustomerId(Integer.parseInt(pod.getCustomerName()));
			//manifestDo.setCustomer(customer);




		} else {
			OfficeDO orgBranch = new OfficeDO();
			orgBranch.setOfficeId(pod.getOrgBo());
			manifestDo.setOriginBranch(orgBranch);

			OfficeDO repBranch = new OfficeDO();
			repBranch.setOfficeId(pod.getOrgRo());
			manifestDo.setReportingBranch(repBranch);
		}

		//FIXME put code for pod type id

		return manifestDo;
	}

	/**
	 * Gets the no of valid pod.
	 *
	 * @param pods the pods
	 * @return the no of valid pod
	 */
	private int getNoOfValidPOD(String[] pods) {
		int count = 0;
		for(String podNumber:pods) {
			if(podNumber != null && !podNumber.equals("")){
				count++;
			}
		}
		return count;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.manifest.PODMDBService#updatePOD(OutgoingManifestPODTO)
	 */
	@Override
	public OutgoingManifestPODTO updatePOD(OutgoingManifestPODTO podTO) throws Exception {
		LOGGER.error("PODServiceImpl::updatePOD() : START");

		PODDeliveryDO podDetails = getPODDeliveryDO(podTO);
		podDao.updatePODDetails(podDetails);

		return podTO;
	}

	/**
	 * Gets the pOD delivery do.
	 *
	 * @param podTO the pod to
	 * @return the pOD delivery do
	 * @throws CGSystemException the cG system exception
	 */
	private PODDeliveryDO getPODDeliveryDO(OutgoingManifestPODTO podTO) throws CGSystemException{
		PODDeliveryDO podDetails = new PODDeliveryDO();

		podDetails.setConNum(podTO.getPodNumber());

		ProductDO product = ctbsApplicationMDBDAO.getProductByIdOrCode(Integer.parseInt(podTO.getProductType()), "");
		podDetails.setProduct(product);

		podDetails.setDeliveryStatus(DeliveryManifestConstants.DELIVERY_STATUS);
		podDetails.setConsgStatus(ManifestConstant.ACTIVE_DELIVERY_STATUS);
		podDetails.setPod(ManifestConstant.POD_RECEIVED);
		if(podTO.getRelation() != null && !podTO.getRelation().equals(ManifestConstant.DEFAULT_SELECT_VALUE)){
			podDetails.setRelationShip(podTO.getRelation());
		}
		String formattedTime = DateFormatterUtil.convertTimeHHMMToHHMMWithColon(podTO.getManifestTime());
		if(formattedTime != null){
			podDetails.setDeliveryDate(new Timestamp(DateFormatterUtil
					.combineDateWithTimeHHMM(podTO.getManifestDate(),formattedTime).getTime()));
		}

		//podDetails.setDeliveryTime(podTO.getManifestTime());
		podDetails.setReceiverDetails(podTO.getRecieverDetails());
		podDetails.setPhNum(podTO.getRecieverPhone());

		IdentityProofDocDO idProofDO = null;
		if(podTO.getIdProofCode() != null && !podTO.getIdProofCode().equals(ManifestConstant.DEFAULT_SELECT_VALUE)){
			idProofDO = ctbsApplicationMDBDAO.getIdProofDocsByIdOrCode(null,podTO.getIdProofCode());
		}

		podDetails.setIdentityProofDO(idProofDO);
		podDetails.setIdentityProofNo(podTO.getIdProofDetail());

		if(podTO.getResidenceDelivery() != null && podTO.getResidenceDelivery().equals(ManifestConstant.DELIVERED_AT_RESIDENCE)){
			podDetails.setDeliveredAtResidence(ManifestConstant.DELIVERED_AT_RESIDENCE);
		}else{
			podDetails.setDeliveredAtResidence(ManifestConstant.NOT_DELIVERED_AT_RESIDENCE);
		}

		if(podTO.getSignatureReceived() != null && podTO.getSignatureReceived().equals(ManifestConstant.SIGNATURE_RECIVED)){
			podDetails.setSignatureReceived(ManifestConstant.SIGNATURE_RECIVED);
		}else{
			podDetails.setSignatureReceived(ManifestConstant.SIGNATURE_NOT_RECIVED);
		}
		return podDetails;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.manifest.PODMDBService#saveUnstampedPODManifest(CGBaseTO)
	 */
	@Override
	public UnstampedPODManifestTO saveUnstampedPODManifest(CGBaseTO baseTO) throws CGSystemException {
		UnstampedPODManifestTO podTO = (UnstampedPODManifestTO) baseTO.getBaseList().get(0);
		return saveUnstampedPODManifest(podTO);
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.manifest.PODMDBService#saveUnstampedPODManifest(UnstampedPODManifestTO)
	 */
	@Override
	public UnstampedPODManifestTO saveUnstampedPODManifest(UnstampedPODManifestTO podTO) throws CGSystemException {

		/* Charge Penalty to FR*/
		List<MiscExpenseDO> miscExpenseDOList = createMiscExpenseForFranchisee(podTO);
		podDao.savePenaltychargeForUnstampedPOD(miscExpenseDOList);

		List<ManifestDO> manifestDOList = createManifestDOfromTO(podTO);
		int serialNo = 1;
		for (ManifestDO manifestDO: manifestDOList){
			manifestDO.setLineItemSequenceNo(serialNo);
			podDao.savePodManifest(manifestDO);
			serialNo++;
		}
		return podTO;
	}

	/**
	 * Creates the misc expense for franchisee.
	 *
	 * @param podTO the pod to
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	private List<MiscExpenseDO> createMiscExpenseForFranchisee(
			UnstampedPODManifestTO podTO) throws CGSystemException {

		List<MiscExpenseDO> miscExpenseList = new ArrayList<MiscExpenseDO>();

		ExpenditureTypeDO podExpenditureTypeDO = ctbsApplicationMDBDAO
		.getExpenditureTypeByType(ManifestConstant.UNSTAMPED_POD_EXPEND_TYPE);
		ConfigurableParamsDO configParamDO = ctbsApplicationMDBDAO
		.getConfigurabParamByParamName(ManifestConstant.UNSTAMPED_POD_PENALTY_CHARGE);
		EmployeeDO loginEmployeeDO = ctbsApplicationMDBDAO.getEmployeeByCodeOrID(
				podTO.getLoggedInUserId(), "");

		for (int i = 0; i < podTO.getPodNo().length; i++) {
			if (podTO.getPodNo()[i] != null && !podTO.getPodNo()[i].trim().equals("")) {

				MiscExpenseDO miscExpDO = new MiscExpenseDO();
				miscExpDO.setConsignmentNumber(podTO.getPodNo()[i]);
				miscExpDO.setExpenditureType(podExpenditureTypeDO);
				miscExpDO.setExpenditureDate(DateFormatterUtil
						.getDateFromString(podTO.getManifestDate(),
								DateFormatterUtil.DDMMYYYY_FORMAT));
				miscExpDO.setExpenditureAmount(Double.parseDouble(configParamDO.getParamValue()));
				miscExpDO.setEnterebBy(loginEmployeeDO);
				//miscExpDO.setProcessName(ManifestConstant.UNSTAMPED_POD_PROCESS);
				miscExpDO.setAuthorizer(loginEmployeeDO);

				miscExpenseList.add(miscExpDO);
			}
		}
		return miscExpenseList;
	}

	/**
	 * Creates the manifest d ofrom to.
	 *
	 * @param podTO the pod to
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	private List<ManifestDO> createManifestDOfromTO(UnstampedPODManifestTO podTO) throws CGSystemException {
		List<ManifestDO> manifestDOList = new ArrayList<ManifestDO>();

		/** Find the number of valid PODs */
		int podCounter = 0;
		int noOfPods = podTO.getPodNo().length;
		for(int j = 0; j < noOfPods; j++){
			if (podTO.getPodNo()[j] != null && !podTO.getPodNo()[j].trim().equals("")) {
				podCounter++;
			}
		}

		for (int i = 0; i < noOfPods; i++) {

			if (podTO.getPodNo()[i] != null && !podTO.getPodNo()[i].trim().equals("")) {
				ManifestDO manifestDO = new ManifestDO();

				/* Set Manifest Related Details */
				manifestDO.setManifestNumber(podTO.getPodTransactionNumber());
				manifestDO.setManifestDate(DateFormatterUtil.getDateFromString(
						podTO.getManifestDate(), DateFormatterUtil.DDMMYYYY_FORMAT));
				manifestDO.setManifestTime(podTO.getManifestTime());

				manifestDO.setTotConsgNum(podCounter);
				manifestDO.setManifestType(ManifestConstant.MANIFEST_TYPE_AGAINST_OUTGOING);
				manifestDO.setManifestTypeDefn(ManifestConstant.UNSATMPED_POD_MANIFEST_TYPE_DEFN);

				ManifestTypeDO manifestTypeDO = ctbsApplicationMDBDAO.getManifestTypeByCode(ManifestConstant.POD_MANIFEST_TYPE_CODE);
				manifestDO.setMnsftTypes(manifestTypeDO);

				EmployeeDO employeeDO = ctbsApplicationMDBDAO.getEmployeeByCodeOrID(podTO.getLoggedInUserId(), "");
				manifestDO.setEmployee(employeeDO);

				/* POD Details */
				manifestDO.setConsgNumber(podTO.getPodNo()[i]);

				FranchiseeDO franchiseeDO = null;
				if(podTO.getCustId()[i] != null && podTO.getCustId()[i] != 0){
					franchiseeDO = ctbsApplicationMDBDAO.getFranchiseeByFrCodeOrId("", podTO.getCustId()[i]);
				}
				manifestDO.setFranchisee(franchiseeDO);

				CityDO cityDO = null;
				if(podTO.getCityId()[i] != null && podTO.getCityId()[i] != 0){
					cityDO = ctbsApplicationMDBDAO.getCityByIdOrCode(podTO.getCityId()[i], "");
				}
				manifestDO.setDestCity(cityDO);

				OfficeDO originOfficeDO = null;
				if(podTO.getDestBranchId() != null && podTO.getDestBranchId() != 0){
					originOfficeDO = ctbsApplicationMDBDAO.getBranchByCodeOrID(podTO.getDestBranchId(), "");
				}
				manifestDO.setOriginBranch(originOfficeDO);

				manifestDO.setRemarks(podTO.getRemark()[i]);
				manifestDO.setDbServer(podTO.getDbServer());

				if (podTO.getUserId() != null
						&& podTO.getUserId() != 0) {
					UserDO userDO= new UserDO();
					userDO.setUserId(podTO.getUserId());
					manifestDO.setUser(userDO);
				}else{
					manifestDO.setUser(null);
				}

				manifestDOList.add(manifestDO);
			}
		}
		return manifestDOList;
	}


}
