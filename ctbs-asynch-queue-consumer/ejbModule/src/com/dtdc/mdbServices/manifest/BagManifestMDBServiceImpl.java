/**
 * 
 */
package src.com.dtdc.mdbServices.manifest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import src.com.dtdc.constants.ManifestConstant;
import src.com.dtdc.mdbDao.manifest.BagManifestMDBDAO;
import src.com.dtdc.mdbServices.CTBSApplicationMDBDAO;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.domain.booking.ProductDO;
import com.dtdc.domain.login.UserDO;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.manifest.ManifestTypeDO;
import com.dtdc.domain.master.StdHandlingInstDO;
import com.dtdc.domain.master.document.DocumentDO;
import com.dtdc.domain.master.employee.EmployeeDO;
import com.dtdc.domain.master.geography.CityDO;
import com.dtdc.domain.master.geography.PincodeDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.master.product.ServiceDO;
import com.dtdc.domain.opmaster.shipment.ModeDO;
import com.dtdc.to.manifest.BagManifestDetailTO;

// TODO: Auto-generated Javadoc
/**
 * The Class BagManifestMDBServiceImpl.
 *
 * @author nisahoo
 */
public class BagManifestMDBServiceImpl implements BagManifestMDBService {
	
	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BagManifestMDBServiceImpl.class);
	
	/** The bag manifest mdbdao. */
	private BagManifestMDBDAO bagManifestMDBDAO = null;
	
	/** The ctbs application mdbdao. */
	private CTBSApplicationMDBDAO ctbsApplicationMDBDAO = null;

	/**
	 * Gets the bag manifest mdbdao.
	 *
	 * @return the bag manifest mdbdao
	 */
	public BagManifestMDBDAO getBagManifestMDBDAO() {
		return bagManifestMDBDAO;
	}

	/**
	 * Sets the bag manifest mdbdao.
	 *
	 * @param bagManifestMDBDAO the new bag manifest mdbdao
	 */
	public void setBagManifestMDBDAO(BagManifestMDBDAO bagManifestMDBDAO) {
		this.bagManifestMDBDAO = bagManifestMDBDAO;
	}
	
	/**
	 * Gets the ctbs application mdbdao.
	 *
	 * @return the ctbs application mdbdao
	 */
	public CTBSApplicationMDBDAO getCtbsApplicationMDBDAO() {
		return ctbsApplicationMDBDAO;
	}

	/**
	 * Sets the ctbs application mdbdao.
	 *
	 * @param ctbsApplicationMDBDAO the new ctbs application mdbdao
	 */
	public void setCtbsApplicationMDBDAO(CTBSApplicationMDBDAO ctbsApplicationMDBDAO) {
		this.ctbsApplicationMDBDAO = ctbsApplicationMDBDAO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.manifest.BagManifestMDBService#saveIncomingBagManifestDetails(CGBaseTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BagManifestDetailTO saveIncomingBagManifestDetails(CGBaseTO cgBaseTO)
			throws CGBusinessException {
		List<BagManifestDetailTO> bagManifestDetailTOList = (List<BagManifestDetailTO>) cgBaseTO.getBaseList();
		return saveIncomingBagManifestDetails(bagManifestDetailTOList);
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.manifest.BagManifestMDBService#saveIncomingBagManifestDetails(List)
	 */
	@Override
	public BagManifestDetailTO saveIncomingBagManifestDetails(
			List<BagManifestDetailTO> bagManifestDetailTOList)
			throws CGBusinessException {
		LOGGER.debug("BagManifestMDBServiceImpl: saveImcomingBagManifestDetails():START");
		
		BagManifestDetailTO bagMfBizErrorTO = new BagManifestDetailTO();
		List<ManifestDO> manifestDOList = convertTOtoManifestDO(bagManifestDetailTOList);
		LOGGER.debug("BagManifestMDBServiceImpl: saveImcomingBagManifestDetails():manifestDOList: " + manifestDOList);
		bagManifestMDBDAO.saveBagManifestData(manifestDOList);
		
		LOGGER.debug("BagManifestMDBServiceImpl: saveImcomingBagManifestDetails():END");
		return bagMfBizErrorTO;
	}
	
	/**
	 * Convert t oto manifest do.
	 *
	 * @param bagManifestDetailTOList the bag manifest detail to list
	 * @return the list
	 * @throws CGBusinessException the cG business exception
	 */
	private List<ManifestDO> convertTOtoManifestDO(List<BagManifestDetailTO> bagManifestDetailTOList) throws CGBusinessException {
		LOGGER.debug("BagManifestMDBServiceImpl: convertTOtoManifestDO():START");
		
		List<ManifestDO> manifestDOList = new ArrayList<ManifestDO>();
		try{
			for (BagManifestDetailTO bagManifestTO : bagManifestDetailTOList) {
				LOGGER.debug("BagManifestMDBServiceImpl: convertTOtoManifestDO():Within LOOP BagManifestDetailTO bagManifestTO : bagManifestDetailTOList ");
				ManifestDO manifestDo =null;
				
				LOGGER.debug("BagManifestMDBServiceImpl: convertTOtoManifestDO():RowType :  "+bagManifestTO.getRowType());
				if (StringUtil.isEmpty(bagManifestTO.getRowType()))
				{
					
					OfficeDO loginOfficeDO = ctbsApplicationMDBDAO
					.getBranchByCodeOrID(
							bagManifestTO.getDestBranchId(), "");
					
					manifestDo = bagManifestMDBDAO
					.getBagManifestDetailsByCompositeID(
							bagManifestTO.getManifestNo(),
							bagManifestTO.getConsignmentNo(),loginOfficeDO.getOfficeCode());
					
					if (manifestDo != null)
					{
						LOGGER.debug("BagManifestMDBServiceImpl: convertTOtoManifestDO()::UPDATE");
						//rowType[i] = ManifestConstant.BAG_MANIFEST_UPDATE;
						bagManifestTO.setRowType(ManifestConstant.BAG_MANIFEST_UPDATE);
					}else {
						LOGGER.debug("BagManifestMDBServiceImpl: convertTOtoManifestDO()::INSERT");
						//rowType[i] = ManifestConstant.BAG_MANIFEST_SAVE;
						bagManifestTO.setRowType(ManifestConstant.BAG_MANIFEST_SAVE);
					}
					
				}
				LOGGER.debug("BagManifestMDBServiceImpl: convertTOtoManifestDO():RowType :  "+bagManifestTO.getRowType());
				
				
				// Save Bag Manifest
				if(StringUtil.equals(bagManifestTO.getRowType(), ManifestConstant.BAG_MANIFEST_SAVE)){
					manifestDo = new ManifestDO();
					// Set Data		
					setDataToBagManifestDO(bagManifestTO, manifestDo);
					manifestDo.setServerDate(DateFormatterUtil.getCurrentDate());
				}else if (StringUtil.equals(bagManifestTO.getRowType(),ManifestConstant.BAG_MANIFEST_UPDATE)) {
					// Update Bag Manifest
					OfficeDO loginOfficeDO = ctbsApplicationMDBDAO
							.getBranchByCodeOrID(
									bagManifestTO.getDestBranchId(), "");
					manifestDo = bagManifestMDBDAO
							.getBagManifestDetailsByCompositeID(
									bagManifestTO.getManifestNo(),
									bagManifestTO.getConsignmentNo(),loginOfficeDO.getOfficeCode());
				}
				
				// Set Save/Update Common Data
				if(manifestDo != null){
					manifestDo.setIndvWeightKgs(Double.parseDouble(bagManifestTO.getIndvWeight()));
					manifestDo.setDepsCategory(bagManifestTO.getDepsCategory());
					manifestDo.setRemarks(bagManifestTO.getRemarks());
					
					// Set "SUCCESS" Status of Central Server Writing
					manifestDo.setReadByLocal(ManifestConstant.WRITTEN_TO_CENTRAL);
					manifestDo.setTransLastModifiedDate(DateFormatterUtil.getCurrentDate());
				
					if(bagManifestTO.getPktOrigin() != null){
						String[] originDetails = bagManifestTO.getPktOrigin().split(ManifestConstant.HYPEN);
						OfficeDO pktOfficeDO = ctbsApplicationMDBDAO.getBranchByCodeOrID(null, originDetails[0]);
						manifestDo.setDespBranch(pktOfficeDO);
					}else{
						manifestDo.setDespBranch(null);
					}
					
					// Add to List
					manifestDOList.add(manifestDo);
				} else {
					LOGGER.debug("BagManifestMDBServiceImpl: convertTOtoManifestDO():bagManifestTO.getRowType(): " + bagManifestTO.getRowType());
				}
			}
		}catch (Exception ex) {
			LOGGER.debug("BagManifestServiceImpl:convertTOtoManifestDO():"+ex.getMessage());
			throw new CGBusinessException(ex);
		}
		LOGGER.debug("BagManifestServiceImpl: convertTOtoManifestDO():END");
		return manifestDOList;
	}
	
	/**
	 * Sets the data to bag manifest do.
	 *
	 * @param bagManifestTO the bag manifest to
	 * @param manifestDo the manifest do
	 * @throws CGSystemException the cG system exception
	 */
	private void setDataToBagManifestDO(BagManifestDetailTO bagManifestTO,
			ManifestDO manifestDo) throws CGSystemException {
		
		// Set Bag manifest Number
		manifestDo.setManifestNumber(bagManifestTO.getManifestNo());
		// Set CN No
		manifestDo.setConsgNumber(bagManifestTO.getConsignmentNo());
		// set Date & Time
		manifestDo.setManifestDate(DateFormatterUtil
				.slashDelimitedstringToDDMMYYYYFormat(bagManifestTO
						.getManifestDate()));
		manifestDo.setManifestTime(bagManifestTO.getManifestTime());
		
		// set the origin branch details
		if(bagManifestTO.getOriginBranchId() != null && bagManifestTO.getOriginBranchId() != 0){
			OfficeDO originOfficeDO = new OfficeDO();
			originOfficeDO.setOfficeId(bagManifestTO.getOriginBranchId());
			manifestDo.setOriginBranch(originOfficeDO);
		}else{
			manifestDo.setOriginBranch(null);
		}
		
		// set the dest branch details
		if(bagManifestTO.getDestBranchId() != null && bagManifestTO.getDestBranchId() != 0){
			OfficeDO destOfficeDO = new OfficeDO();
			destOfficeDO.setOfficeId(bagManifestTO.getDestBranchId());
			manifestDo.setDestBranch(destOfficeDO);
		}else{
			manifestDo.setDestBranch(null);
		}
		
		// Set the reporting branch
		if(bagManifestTO.getRepBranchId() != null && bagManifestTO.getRepBranchId() != 0){
			OfficeDO reportingOffice = new OfficeDO();
			reportingOffice.setOfficeId(bagManifestTO.getRepBranchId());
			manifestDo.setReportingBranch(reportingOffice);
		}else{
			manifestDo.setReportingBranch(null);
		}
		
		// Set the Rcv branch
		if(bagManifestTO.getRcvBranchId() != null && bagManifestTO.getRcvBranchId() != 0){
			OfficeDO rcvOffice = new OfficeDO();
			rcvOffice.setOfficeId(bagManifestTO.getRcvBranchId());
			manifestDo.setRecvBranch(rcvOffice);
		}else{
			manifestDo.setRecvBranch(null);
		}
		
		// set mode details
		if(bagManifestTO.getModeId() != null && bagManifestTO.getModeId() != 0){
			ModeDO mode = new ModeDO();
			mode.setModeId(bagManifestTO.getModeId());
			manifestDo.setMode(mode);
		}else{
			manifestDo.setMode(null);
		}
		
		// Set Dest City
		if(bagManifestTO.getDestCityId() != null && bagManifestTO.getDestCityId() != 0){
			CityDO cityDO = new CityDO();
			cityDO.setCityId(bagManifestTO.getDestCityId());
			manifestDo.setDestCity(cityDO);
		}else{
			manifestDo.setDestCity(null);
		}
		
		// Set Pincode
		if(bagManifestTO.getDestPincodeId() != null && bagManifestTO.getDestPincodeId() != 0){
			PincodeDO pinCodeDO = new PincodeDO();
			pinCodeDO.setPincodeId(bagManifestTO.getDestPincodeId());
			manifestDo.setDestPinCode(pinCodeDO);
		}else{
			manifestDo.setDestPinCode(null);
		}
		
		// Set Handling Instr Id
		if(bagManifestTO.getHandleInstId() != null && bagManifestTO.getHandleInstId() != 0){
			StdHandlingInstDO stdhandlingDO = new StdHandlingInstDO();
			stdhandlingDO.setHandleInstId(bagManifestTO.getHandleInstId());
			manifestDo.setStdHandleInst(stdhandlingDO);
		}else{
			manifestDo.setStdHandleInst(null);
		}
		
		// Set Service
		if(bagManifestTO.getServiceId() != null && bagManifestTO.getServiceId() != 0){
			ServiceDO serviceDO = new ServiceDO();
			serviceDO.setServiceId(bagManifestTO.getServiceId());
			manifestDo.setService(serviceDO);
		}else{
			manifestDo.setService(null);
		}
		
		// Set product
		if(bagManifestTO.getProductId() != null && bagManifestTO.getProductId() != 0){
			ProductDO productDO = new ProductDO();
			productDO.setProductId(bagManifestTO.getProductId());
			manifestDo.setProduct(productDO);
		}else{
			manifestDo.setProduct(null);
		}
		
		// Set Document
		if(bagManifestTO.getDocumentId() != null && bagManifestTO.getDocumentId() != 0){
			DocumentDO documentDO = new DocumentDO();
			documentDO.setDocumentId(bagManifestTO.getDocumentId());
			manifestDo.setDocument(documentDO);
		}else{
			manifestDo.setDocument(null);
		}
		
		// Set Employee
		if(bagManifestTO.getEmployeeId() != null && bagManifestTO.getEmployeeId() != 0){
			EmployeeDO employeeDO = new EmployeeDO();
			employeeDO.setEmployeeId(bagManifestTO.getEmployeeId());
			manifestDo.setEmployee(employeeDO);
		}else{
			manifestDo.setEmployee(null);
		}
		
		// Set User
		if(bagManifestTO.getUserId() != null){
			UserDO userDO = new UserDO();
			userDO.setUserId(bagManifestTO.getUserId());
			manifestDo.setUser(userDO);
		}else{
			manifestDo.setUser(null);
		}
		
		//Set manifest Type
		if(bagManifestTO.getManifestTypeId() != null && bagManifestTO.getManifestTypeId() != 0){
			ManifestTypeDO mfTypeDO = new ManifestTypeDO();
			mfTypeDO.setMnfstTypeId(bagManifestTO.getManifestTypeId());
			manifestDo.setMnsftTypes(mfTypeDO);
		}else{
			manifestDo.setMnsftTypes(null);
		}

		manifestDo.setTotWghtPackets(Double.parseDouble(bagManifestTO.getTotalPktWeight()));	
		manifestDo.setTotWeightKgs(Double.parseDouble(bagManifestTO.getTotalWeight()));
		manifestDo.setNoOfPacket(bagManifestTO.getNoOfPkts());
		manifestDo.setPacketIntact(bagManifestTO.getPktIntact());
		
		// Set the No of Pkt & Bag Weight Mismatch
		manifestDo.setPktMismatch(bagManifestTO.getPktMismatch());
		manifestDo.setWeightMismatch(Double.parseDouble(bagManifestTO.getWeightMismatch()));
		
		/* set the datagrid values to the ManifestDO object */
		manifestDo.setWeighingType(bagManifestTO.getWeighingType());
		manifestDo.setManifestTypeDefn(bagManifestTO.getManifestTypeDefn());
		
		manifestDo.setServiceType(bagManifestTO.getServiceType());
		manifestDo.setNoOfBagMade(bagManifestTO.getNoOfBags());
		manifestDo.setNoOfPieces(bagManifestTO.getNoOfPieces());
		manifestDo.setStatus(ManifestConstant.MANIFEST_STATUS);

		// Set packet Manifest Status as Incoming
		manifestDo.setManifestType(ManifestConstant.MANIFEST_TYPE_AGAINST_INCOMING);
	}
}