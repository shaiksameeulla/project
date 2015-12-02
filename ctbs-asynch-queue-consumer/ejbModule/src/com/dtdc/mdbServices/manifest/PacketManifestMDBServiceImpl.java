/**
 * 
 */
package src.com.dtdc.mdbServices.manifest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import src.com.dtdc.constants.PacketManifestConstants;
import src.com.dtdc.mdbDao.manifest.PacketManifestMDBDAO;
import src.com.dtdc.mdbServices.CTBSApplicationMDBDAO;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.domain.expense.ExpenditureTypeDO;
import com.dtdc.domain.expense.MiscExpenseDO;
import com.dtdc.domain.login.UserDO;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.manifest.ManifestTypeDO;
import com.dtdc.domain.master.employee.EmployeeDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.to.expense.CnMiscExpenseTO;
import com.dtdc.to.manifest.PacketManifestDetailTO;

// TODO: Auto-generated Javadoc
/**
 * The Class PacketManifestMDBServiceImpl.
 *
 * @author nisahoo
 */
public class PacketManifestMDBServiceImpl implements PacketManifestMDBService {
	
	/** logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(OutgoingManifestMDBServiceImpl.class);
	
	/** The packet manifest mdbdao. */
	private PacketManifestMDBDAO packetManifestMDBDAO = null;
	
	/** The ctbs application mdbdao. */
	private CTBSApplicationMDBDAO ctbsApplicationMDBDAO = null;
	
	/**
	 * Gets the packet manifest mdbdao.
	 *
	 * @return the packet manifest mdbdao
	 */
	public PacketManifestMDBDAO getPacketManifestMDBDAO() {
		return packetManifestMDBDAO;
	}

	/**
	 * Sets the packet manifest mdbdao.
	 *
	 * @param packetManifestMDBDAO the new packet manifest mdbdao
	 */
	public void setPacketManifestMDBDAO(PacketManifestMDBDAO packetManifestMDBDAO) {
		this.packetManifestMDBDAO = packetManifestMDBDAO;
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
	 * @see ejbModule.src.com.dtdc.mdbServices.manifest.PacketManifestMDBService#saveIncomingPktManifest(CGBaseTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String saveIncomingPktManifest(CGBaseTO cgBaseTO) throws CGBusinessException {
		PacketManifestDetailTO pktmanifestDtlTO = (PacketManifestDetailTO) cgBaseTO.getBaseList().get(0);
		List<CnMiscExpenseTO> miscExpenseTos = (List<CnMiscExpenseTO>)cgBaseTO.getJsonChildObject();
		pktmanifestDtlTO.setMiscExpenseList(miscExpenseTos);
		return saveIncomingPktManifest(pktmanifestDtlTO);
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.manifest.PacketManifestMDBService#saveIncomingPktManifest(PacketManifestDetailTO)
	 */
	@Override
	public String saveIncomingPktManifest(
			PacketManifestDetailTO pktmanifestDtlTO) throws CGBusinessException {
		
		LOGGER.debug("PacketManifestMDBServiceImpl: saveIncomingPktManifest(): START");
		
		StringBuilder saveMsg = new StringBuilder();
		try{
			List<ManifestDO> manifestDOList = convertPacketManifestTOtoManifestDO(pktmanifestDtlTO);
			
			// Save Misc. Expense
			List<MiscExpenseDO> miscExpenseDOList = null;
			List<CnMiscExpenseTO> miscExpenseToList = pktmanifestDtlTO.getMiscExpenseList();
			if (miscExpenseToList != null && !miscExpenseToList.isEmpty()) {
				miscExpenseDOList = new ArrayList<MiscExpenseDO>();
				for (CnMiscExpenseTO miscExpenseTo : miscExpenseToList) {
					MiscExpenseDO miscExpenseDo = getMiscExpenseDoFromTo(miscExpenseTo);
					miscExpenseDOList.add(miscExpenseDo);
				}
			}
			if (miscExpenseDOList != null && !miscExpenseDOList.isEmpty()) {
				packetManifestMDBDAO.saveMiscExp(miscExpenseDOList);
			}
			
			// Save Packet Manifest
			boolean status = packetManifestMDBDAO.saveIncomingPktManifest(manifestDOList);
			if(status){
				saveMsg.append(PacketManifestConstants.SUCCESS_MSG);
				saveMsg.append(PacketManifestConstants.COMMA_SEPARATER);
				saveMsg.append(pktmanifestDtlTO.getManifestNo());
			}
		}catch (Exception ex) 
		{
			LOGGER.error("PacketManifestServiceImpl: saveIncomingPktManifest():" +ex.getMessage());
			throw new CGBusinessException(ex);
		}
		
		LOGGER.debug("PacketManifestMDBServiceImpl: saveIncomingPktManifest(): END");
		return saveMsg.toString();
	}
	
	/**
	 * Convert packet manifest t oto manifest do.
	 *
	 * @param pktmanifestDtlTO the pktmanifest dtl to
	 * @return the list
	 * @throws CGBusinessException the cG business exception
	 */
	private List<ManifestDO> convertPacketManifestTOtoManifestDO(
			PacketManifestDetailTO pktmanifestDtlTO) throws CGBusinessException {
		

		LOGGER.debug("PacketManifestMDBServiceImpl: convertPacketManifestTOtoManifestDO(): START");
		List<ManifestDO> manifestDOList = new ArrayList<ManifestDO>();
		ManifestDO packetManifest = null;
		UserDO userDO = null;
		String destBranchCode = null;
		try {
			String[] rowType = new String[pktmanifestDtlTO.getRowCount()];
			for (int i = 0; i < pktmanifestDtlTO.getRowCount(); i++) {

				String pktManifestNo = pktmanifestDtlTO.getManifestNo();
				String consignmentNo = pktmanifestDtlTO.getConsignmentNo()[i]
						.trim();

				if (pktmanifestDtlTO.getRecvBranchId() != null) {
					OfficeDO destOffice = ctbsApplicationMDBDAO
							.getBranchByCodeOrID(
									pktmanifestDtlTO.getRecvBranchId(), "");
					destBranchCode = destOffice.getOfficeCode();
				}
				LOGGER.debug("PacketManifestMDBServiceImpl: convertPacketManifestTOtoManifestDO(): Destination Branch Office Code "
						+ destBranchCode);

				if (pktmanifestDtlTO.getUserId() != null) {
					userDO = new UserDO();
					userDO.setUserId(pktmanifestDtlTO.getUserId());
				}

				if (pktmanifestDtlTO.getRowType()[i] == null) {
					LOGGER.debug("PacketManifestMDBServiceImpl: convertPacketManifestTOtoManifestDO(): PacketManifestNo: "
							+ pktManifestNo);
					LOGGER.debug("PacketManifestMDBServiceImpl: convertPacketManifestTOtoManifestDO(): Consignment No: "
							+ consignmentNo);
					packetManifest = packetManifestMDBDAO
							.getPacketManifestByCompositeID(
									pktManifestNo,
									consignmentNo,
									PacketManifestConstants.MANIFEST_TYPE_AGAINST_INCOMING,
									destBranchCode);
					LOGGER.debug("PacketManifestMDBServiceImpl: convertPacketManifestTOtoManifestDO(): packetManifestMDBDAO.getPacketManifestByCompositeID Returns : "
							+ packetManifest);
					if (packetManifest != null) {
						LOGGER.debug("PacketManifestMDBServiceImpl: convertPacketManifestTOtoManifestDO(): UPDATE MANIFEST");
						rowType[i] = PacketManifestConstants.UPDATE_MANIFEST;
						pktmanifestDtlTO.setRowType(rowType);
					} else {
						LOGGER.debug("PacketManifestMDBServiceImpl: convertPacketManifestTOtoManifestDO(): SAVE MANIFEST");
						rowType[i] = PacketManifestConstants.SAVE_MANIFEST;
						pktmanifestDtlTO.setRowType(rowType);
					}

				}
				LOGGER.debug("PacketManifestMDBServiceImpl: convertPacketManifestTOtoManifestDO(): pktmanifestDtlTO.getRowType()[i] "
						+ pktmanifestDtlTO.getRowType()[i]);
				
				// EDIT Packet Manifest
				if (StringUtil.equals(pktmanifestDtlTO.getRowType()[i],
						PacketManifestConstants.UPDATE_MANIFEST)) {
					packetManifest = packetManifestMDBDAO
							.getPacketManifestByCompositeID(pktManifestNo,consignmentNo,
									PacketManifestConstants.MANIFEST_TYPE_AGAINST_INCOMING,destBranchCode);
					
					packetManifest.setNoOfPieces(pktmanifestDtlTO.getNoOfPieces()[i]);
					packetManifest.setIndvWeightKgs(pktmanifestDtlTO.getWeight()[i]);
					packetManifest.setDepsCategory(pktmanifestDtlTO.getDepsCategory()[i]);
					packetManifest.setRemarks(pktmanifestDtlTO.getRemark()[i]);
					packetManifest.setWeighingType(pktmanifestDtlTO.getWeighingTypes()[i]);
					packetManifest.setTotConsgNum(pktmanifestDtlTO.getRowCount());
					packetManifest.setTransLastModifiedDate(DateFormatterUtil.getCurrentDate());
					packetManifest.setUser(userDO);
				}else {
					// SAVE Packet Manifest
					packetManifest = packetManifestMDBDAO.getPacketManifestByCompositeID(pktManifestNo,consignmentNo,
							PacketManifestConstants.MANIFEST_TYPE_AGAINST_OUTGOING,destBranchCode);
					
					if(packetManifest == null){
						// NO OUTGOING MF DATA
						packetManifest = new ManifestDO();
						// Set the Manifest Type
						String manifestType = pktmanifestDtlTO.getMnfstType();
						if(manifestType.equals(PacketManifestConstants.STD_TYPE_ROBO_MANIFEST_TYPE)){
							manifestType = PacketManifestConstants.MANIFEST_TYPE_ROBO_CHECK_LIST;
						}
						ManifestTypeDO manifestTypeDO = ctbsApplicationMDBDAO.getManifestTypeByCode(manifestType);
						packetManifest.setMnsftTypes(manifestTypeDO);
					}else{
						// OUTGOING MF DATA EXISTS
						packetManifest.setManifestId(null);
					}
					packetManifest.setServerDate(DateFormatterUtil.getCurrentDate());
					packetManifest.setManifestType(PacketManifestConstants.MANIFEST_TYPE_AGAINST_INCOMING);
					packetManifest.setManifestNumber(pktManifestNo);
					if (pktmanifestDtlTO.getTotalWeightPkts() != null) {
						packetManifest.setTotWghtPackets(Double.parseDouble(pktmanifestDtlTO.getTotalWeightPkts()));							
					}
					
					if (pktmanifestDtlTO.getPktWeightMismatch() != null) {
						packetManifest.setWeightMismatch(Double.parseDouble(pktmanifestDtlTO.getPktWeightMismatch()));						
					}
					packetManifest.setManifestDate(DateFormatterUtil
							.getDateFromString(
									pktmanifestDtlTO.getOperationDate(),
									DateFormatterUtil.DDMMYYYY_FORMAT));
					packetManifest.setManifestTime(pktmanifestDtlTO.getOperationTime());
					packetManifest.setStatus(PacketManifestConstants.MANIFEST_STATUS);
					
					// Set Volumetric Weight Calc Data
					if (pktmanifestDtlTO.getLength() != null
							&& pktmanifestDtlTO.getLength().length > 0) {
						packetManifest.setLength(pktmanifestDtlTO.getLength()[i]);
						
						if (pktmanifestDtlTO.getWidth() != null
								&& pktmanifestDtlTO.getWidth().length > 0) {
							packetManifest.setBreadth(pktmanifestDtlTO
									.getWidth()[i]);
						}
						if (pktmanifestDtlTO.getHeight() != null
								&& pktmanifestDtlTO.getHeight().length > 0) {
							packetManifest.setHeight(pktmanifestDtlTO
									.getHeight()[i]);
						}
						if (pktmanifestDtlTO.getFinalWeight() != null
								&& pktmanifestDtlTO.getFinalWeight().length > 0) {
							packetManifest.setIndvWeightKgs(pktmanifestDtlTO
									.getFinalWeight()[i]);
						}
						if (pktmanifestDtlTO.getVolWeight() != null
								&& pktmanifestDtlTO.getVolWeight().length > 0) {
							packetManifest.setVolumetricWeight(pktmanifestDtlTO
									.getVolWeight()[i]);
						}
					}

					if (pktmanifestDtlTO.getOriginBranchId() != null
							&& pktmanifestDtlTO.getOriginBranchId() != 0) {
						OfficeDO originOffice = ctbsApplicationMDBDAO
								.getBranchByCodeOrID(pktmanifestDtlTO.getOriginBranchId(),"");
						packetManifest.setOriginBranch(originOffice);

					}

					if (pktmanifestDtlTO.getDespRegBranchId() != null
							&& pktmanifestDtlTO.getDespRegBranchId() != 0) {
						OfficeDO dispatchingRegionOffice = ctbsApplicationMDBDAO
								.getBranchByCodeOrID(pktmanifestDtlTO.getDespRegBranchId(),"");
						packetManifest.setDespRegBranch(dispatchingRegionOffice);
					}
								
					if (pktmanifestDtlTO.getRecvBranchId() != null
							&& pktmanifestDtlTO.getRecvBranchId() != 0) {
						OfficeDO receivingOffice = ctbsApplicationMDBDAO
								.getBranchByCodeOrID(pktmanifestDtlTO.getRecvBranchId(), "");
						packetManifest.setDestBranch(receivingOffice);
						packetManifest.setRecvBranch(receivingOffice);
					}
								
					if (pktmanifestDtlTO.getUserId() != null
							&& pktmanifestDtlTO.getUserId() != 0) {
						EmployeeDO user = ctbsApplicationMDBDAO
								.getEmployeeByCodeOrID(pktmanifestDtlTO.getUserId(), "");
						packetManifest.setEmployee(user);
					}
					
					// Packet Intact
					packetManifest.setPacketIntact(pktmanifestDtlTO.getPacketIntact());			
					if (pktmanifestDtlTO.getPacketIntact() != null
							&& pktmanifestDtlTO.getPacketIntact().equals(
									PacketManifestConstants.AUTO_INSCAN)) {

						packetManifest.setConsgNumber(packetManifest.getConsgNumber());
						packetManifest.setNoOfPieces(packetManifest.getNoOfPieces());
						packetManifest.setIndvWeightKgs(packetManifest.getIndvWeightKgs());
						packetManifest.setDepsCategory(packetManifest.getDepsCategory());
						packetManifest.setRemarks(packetManifest.getRemarks());
						packetManifest.setWeighingType(packetManifest.getWeighingType());
						packetManifest.setTotConsgNum(packetManifest.getTotConsgNum());
					} else {
						packetManifest.setConsgNumber(pktmanifestDtlTO.getConsignmentNo()[i]);
						packetManifest.setNoOfPieces(pktmanifestDtlTO.getNoOfPieces()[i]);
						packetManifest.setIndvWeightKgs(pktmanifestDtlTO.getWeight()[i]);
						packetManifest.setDepsCategory(pktmanifestDtlTO.getDepsCategory()[i]);
						packetManifest.setRemarks(pktmanifestDtlTO.getRemark()[i]);
						packetManifest.setWeighingType(pktmanifestDtlTO.getWeighingTypes()[i]);
						packetManifest.setTotConsgNum(pktmanifestDtlTO.getRowCount());
					}
								
					packetManifest.setManifestTypeDefn(pktmanifestDtlTO.getManifestTypeDefn());
					packetManifest.setTotWeightKgs(Double.parseDouble(pktmanifestDtlTO.getTotalWeight()));
					packetManifest.setUser(userDO);
					packetManifest.setDbServer(pktmanifestDtlTO.getDbServer());
				}
				manifestDOList.add(packetManifest);
			}
				
		}catch (Exception ex) {
			
			LOGGER.error("PacketManifestServiceImpl:convertPacketManifestTOtoManifestDO():" + ex.getMessage());
			throw new CGBusinessException(ex);
		}
		LOGGER.debug("PacketManifestMDBServiceImpl: convertPacketManifestTOtoManifestDO(): END");
		return manifestDOList;
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

}
