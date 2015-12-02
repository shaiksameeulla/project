/**
 * 
 */
package src.com.dtdc.mdbServices.manifest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import src.com.dtdc.constants.ManifestConstant;
import src.com.dtdc.constants.PacketManifestConstants;
import src.com.dtdc.mdbDao.manifest.ROBOManifestMDBDAO;
import src.com.dtdc.mdbServices.CTBSApplicationMDBDAO;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.domain.login.UserDO;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.manifest.ManifestTypeDO;
import com.dtdc.domain.master.document.DocumentDO;
import com.dtdc.domain.master.employee.EmployeeDO;
import com.dtdc.domain.master.geography.PincodeDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.to.manifest.RoboCheckListTO;

// TODO: Auto-generated Javadoc
/**
 * The Class ROBOManifestMDBServiceImpl.
 *
 * @author narmdr
 */
public class ROBOManifestMDBServiceImpl implements ROBOManifestMDBService {


	/** logger. */
	private final static Logger logger = LoggerFactory.getLogger(ROBOManifestMDBServiceImpl.class);

	/** The robo manifest mdbdao. */
	private ROBOManifestMDBDAO roboManifestMDBDAO = null;

	/** The ctbs application mdbdao. */
	private CTBSApplicationMDBDAO ctbsApplicationMDBDAO = null;

	/**
	 * Gets the robo manifest mdbdao.
	 *
	 * @return the robo manifest mdbdao
	 */
	public ROBOManifestMDBDAO getRoboManifestMDBDAO() {
		return roboManifestMDBDAO;
	}

	/**
	 * Sets the robo manifest mdbdao.
	 *
	 * @param roboManifestMDBDAO the new robo manifest mdbdao
	 */
	public void setRoboManifestMDBDAO(ROBOManifestMDBDAO roboManifestMDBDAO) {
		this.roboManifestMDBDAO = roboManifestMDBDAO;
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
	 * @see ejbModule.src.com.dtdc.mdbServices.manifest.ROBOManifestMDBService#saveROBOManifest(CGBaseTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void saveROBOManifest(CGBaseTO cgBaseTO)
	throws CGBusinessException {
		if(cgBaseTO.getBaseList()!=null){
			for (RoboCheckListTO roboCheckListTO : (List<RoboCheckListTO>)cgBaseTO.getBaseList()) {
				try{
					saveROBOManifest(roboCheckListTO);
				}catch (Exception e) {
					logger.error("ROBOManifestMDBServiceImpl::saveROBOManifest::==>"+e.getMessage());
					logger.error("Exception occured in::ROBOManifestMDBServiceImpl::saveROBOManifest::==>"+e.getStackTrace());
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.manifest.ROBOManifestMDBService#saveROBOManifest(RoboCheckListTO)
	 */
	@Override
	public void saveROBOManifest(RoboCheckListTO roboCheckListTO)
	throws Exception {
		logger.debug("ROBOManifestMDBServiceImpl::saveROBOManifest::Start======>");		
		try{			
			List<ManifestDO> manifestDOList = convertRoboCheckListTOtoManifestDO(roboCheckListTO);
			// Save Robo Manifest
			roboManifestMDBDAO.saveOrUpdateROBOCheckList(manifestDOList);		
		}catch (Exception e) {
			logger.error("ROBOManifestMDBServiceImpl::saveROBOManifest::==>"+e.getMessage());
			logger.error("Exception occured in::ROBOManifestMDBServiceImpl::saveROBOManifest::==>"+e.getStackTrace());
			throw e;
		}
		logger.debug("ROBOManifestMDBServiceImpl::saveROBOManifest::End======>");
	}


	/**
	 * Convert robo check list t oto manifest do.
	 *
	 * @param roboCheckListTO the robo check list to
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	private List<ManifestDO> convertRoboCheckListTOtoManifestDO(
			RoboCheckListTO roboCheckListTO) throws CGSystemException,
			CGBusinessException {
		logger.debug("ROBOManifestMDBServiceImpl::convertRoboCheckListTOtoManifestDO::Start======>");

		List<ManifestDO> manifestDOList = new ArrayList<ManifestDO>();
		OfficeDO originOfficeDO = null;
		OfficeDO destOfficeDO = null;
		DocumentDO documentDO = null;
		EmployeeDO employeeDO = null;
		UserDO userDO = null;

		try {			
			ManifestTypeDO manifestTypeDO = roboManifestMDBDAO.getManifestType(ManifestConstant.MANIFEST_TYPE_ROBO_CHECK_LIST);

			if(StringUtils.isNotBlank(roboCheckListTO.getOriginROCode())) {
				originOfficeDO = ctbsApplicationMDBDAO.getOfficebyOfficeCode(roboCheckListTO.getOriginROCode());
			}
			if(StringUtils.isNotBlank(roboCheckListTO.getOfficeBranchCode())) {
				destOfficeDO = ctbsApplicationMDBDAO.getOfficebyOfficeCode(roboCheckListTO.getOfficeBranchCode());
			}
			if(StringUtils.isNotBlank(roboCheckListTO.getEmployeeCode())) {
				employeeDO = ctbsApplicationMDBDAO.getEmployeeByCodeOrID(-1, roboCheckListTO.getEmployeeCode());
			}

			if(StringUtils.isNotBlank(roboCheckListTO.getConsignmentType())) {
				documentDO = ctbsApplicationMDBDAO.getDocumentByDocumentCode(roboCheckListTO.getConsignmentType());
			}

			if(roboCheckListTO.getUser()!=null){
				if(StringUtils.isNotBlank(roboCheckListTO.getUser().getUserCode())) {
					userDO = ctbsApplicationMDBDAO.getUserByUserCode(roboCheckListTO.getUser().getUserCode());
				}				
			}


			for (int i = 0; i < roboCheckListTO.getConsignmentNumber().length; i++) 
			{				
				if (!StringUtil.isEmpty(roboCheckListTO.getConsignmentNumber()[i]))
				{				
					// create an object of ManifestDO
					ManifestDO manifestDo = new ManifestDO();

					manifestDo.setDocument(documentDO);
					manifestDo.setMnsftTypes(manifestTypeDO);	
					manifestDo.setOriginBranch(originOfficeDO);
					manifestDo.setDestBranch(destOfficeDO);
					manifestDo.setEmployee(employeeDO);
					manifestDo.setUser(userDO);

					manifestDo.setManifestNumber(roboCheckListTO.getManifestNumber());
					manifestDo.setManifestTime(roboCheckListTO.getManifestTime());
					manifestDo.setWeighingType(roboCheckListTO.getWeighingType());
					manifestDo.setManifestType(PacketManifestConstants.MANIFEST_TYPE_AGAINST_OUTGOING);

					if(StringUtils.isNotBlank(roboCheckListTO.getManifestDate())){
						manifestDo.setManifestDate(DateFormatterUtil.getDateFromStringDDMMYYY(roboCheckListTO.getManifestDate()));
					}
					if(roboCheckListTO.getTotalWeight()!=null){
						manifestDo.setTotWeightKgs(roboCheckListTO.getTotalWeight());
					}

					// set the datagrid values to the ManifestDO object			
					manifestDo.setConsgNumber((roboCheckListTO.getConsignmentNumber()[i]));
					manifestDo.setNoOfPieces(roboCheckListTO.getNoOfPieces()[i]);
					manifestDo.setIndvWeightKgs(roboCheckListTO.getWeight()[i]);
					manifestDo.setRemarks(roboCheckListTO.getRemarks()[i]);
					manifestDo.setDepsCategory(roboCheckListTO.getDepsCategory()[i]);

					PincodeDO pinCodeDO = null;
					if(StringUtils.isNotBlank(roboCheckListTO.getPinCode()[i])) {
						pinCodeDO = ctbsApplicationMDBDAO.getPinCodeByIdOrCode(-1, roboCheckListTO.getPinCode()[i]);
					}
					manifestDo.setDestPinCode(pinCodeDO);
					logger.debug("ROBOManifestMDBServiceImpl::convertRoboCheckListTOtoManifestDO::pinCodeDO==>"+pinCodeDO);


					Integer manifestId = null;
					if(manifestTypeDO!=null){
						manifestId = roboManifestMDBDAO.getManifestIdByMfstNoMfstTypeIdMfstTypeConsgNo(
								roboCheckListTO.getManifestNumber(), manifestTypeDO.getMnfstTypeId(), 
								PacketManifestConstants.MANIFEST_TYPE_AGAINST_OUTGOING, 
								roboCheckListTO.getConsignmentNumber()[i]);
					}
					manifestDo.setManifestId(manifestId);
					logger.debug("ROBOManifestMDBServiceImpl::convertRoboCheckListTOtoManifestDO::manifestId==>"+manifestId);
					//manifestDo.setDbServer("N");
					//manifestDo.setReadByLocal("N");
					manifestDo.setDiFlag("N");

					manifestDOList.add(manifestDo);					
				}
			} 			
		}catch (Exception ex) {
			logger.error("ROBOManifestMDBServiceImpl::convertRoboCheckListTOtoManifestDO::==>"+ex.getMessage());
			logger.error("Exception occured in::ROBOManifestMDBServiceImpl::convertRoboCheckListTOtoManifestDO::==>"+ex.getStackTrace());
		}
		logger.debug("ROBOManifestMDBServiceImpl::convertRoboCheckListTOtoManifestDO::End======>");
		return manifestDOList;

	}

}
