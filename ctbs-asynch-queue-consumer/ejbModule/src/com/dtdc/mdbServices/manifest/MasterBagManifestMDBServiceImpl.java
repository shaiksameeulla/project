/**
 * 
 */
package src.com.dtdc.mdbServices.manifest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import src.com.dtdc.mdbDao.manifest.MasterBagManifestMDBDAO;
import src.com.dtdc.mdbServices.CTBSApplicationMDBDAO;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.dtdc.domain.login.UserDO;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.manifest.ManifestTypeDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.opmaster.shipment.ModeDO;
import com.dtdc.to.manifest.MasterBagManifestDetailTO;
import com.capgemini.lbs.framework.constants.ApplicationConstants;
import com.capgemini.lbs.framework.constants.ManifestConstant;

// TODO: Auto-generated Javadoc
/**
 * The Class MasterBagManifestMDBServiceImpl.
 *
 * @author vsulibha
 */
public class MasterBagManifestMDBServiceImpl implements
		MasterBagManifestMDBService {


	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
	.getLogger(MasterBagManifestMDBServiceImpl.class);
	
	/** The CTBSApplicationDAO dao. */
	private  CTBSApplicationMDBDAO ctbsApplicationMDBDAO;
	
	/** The CTBSApplicationDAO dao. */
	private  MasterBagManifestMDBDAO masterBagManifestMDBDAO;
	
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.manifest.MasterBagManifestMDBService#saveIncomingMasterBagManifestDetails(CGBaseTO)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public MasterBagManifestDetailTO saveIncomingMasterBagManifestDetails(
			CGBaseTO cgBaseTO) throws CGSystemException, CGBusinessException {
		
		List<MasterBagManifestDetailTO> bagManifestDetailTOList = (List<MasterBagManifestDetailTO>) cgBaseTO.getBaseList();
		return saveIncomingMasterBagManifestDetails(bagManifestDetailTOList);
		
		
	}

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
	 * Gets the master bag manifest mdbdao.
	 *
	 * @return the masterBagManifestMDBDAO
	 */
	public MasterBagManifestMDBDAO getMasterBagManifestMDBDAO() {
		return masterBagManifestMDBDAO;
	}

	/**
	 * Sets the master bag manifest mdbdao.
	 *
	 * @param masterBagManifestMDBDAO the masterBagManifestMDBDAO to set
	 */
	public void setMasterBagManifestMDBDAO(
			MasterBagManifestMDBDAO masterBagManifestMDBDAO) {
		this.masterBagManifestMDBDAO = masterBagManifestMDBDAO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.manifest.MasterBagManifestMDBService#saveIncomingMasterBagManifestDetails(List)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public MasterBagManifestDetailTO saveIncomingMasterBagManifestDetails(
			List<MasterBagManifestDetailTO> bagManifestDetailTOList)
			throws CGSystemException, CGBusinessException {
		
		
		MasterBagManifestDetailTO bagMfBizErrorTO = new MasterBagManifestDetailTO();
		List<ManifestDO> manifestDOList = convertTOtoManifestDO(bagManifestDetailTOList.get(0));
		
		masterBagManifestMDBDAO.insertMasterBagManifestData(manifestDOList);
		
		LOGGER.info("BagManifestServiceImpl: BagManifestMDBServiceImpl():END");
		
		return bagMfBizErrorTO;
	}
	
	/**
	 * method to convert to to DO.
	 *
	 * @param masterBagManifestDetailTO the master bag manifest detail to
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	private List<ManifestDO> convertTOtoManifestDO(MasterBagManifestDetailTO masterBagManifestDetailTO) throws CGSystemException, CGBusinessException 
	{
		    // create an object of ManifestDOList
			List<ManifestDO> convertedManifestDOList = new ArrayList<ManifestDO>();
			
			// its hardcoded because this is not yet clear from the requirement
			String manifstTypeCode = ManifestConstant.MANIFEST_TYPE_MASTER_BAG;
			
			
			// get the origin office object
			OfficeDO officeDO = ctbsApplicationMDBDAO.getOfficebyOfficeCode(masterBagManifestDetailTO.getOrgOfficeCode());
			
			
			// get the destination office object
			OfficeDO recivngOfficeDO = ctbsApplicationMDBDAO.getOfficebyOfficeCode(masterBagManifestDetailTO.getDestOfficeCode());
			
			
			// get the mode details
			ModeDO mode =  ctbsApplicationMDBDAO.getModeById(masterBagManifestDetailTO.getModeCode());
			
			// get the manifestTypeId
			ManifestTypeDO manifestTypeDo = masterBagManifestMDBDAO.getManifestType(manifstTypeCode);
			
			
			// create the manifestDo object from TO
			for (int i = 0; i < masterBagManifestDetailTO.getManifestNumber().length; i++) {
				
				
				List<ManifestDO> manifestDoList = masterBagManifestMDBDAO.checkWhetherRecordExists(masterBagManifestDetailTO.getManifestNO(), 
						masterBagManifestDetailTO.getManifestNumber()[i], recivngOfficeDO.getOfficeCode());
				
				if (manifestDoList != null  && manifestDoList.size() > 0 )
				{
					ManifestDO manifestDo = manifestDoList.get(0);
					
					// manifest Number
					manifestDo.setManifestNumber(masterBagManifestDetailTO.getManifestNO());

					// set the origin branch details
					manifestDo.setOriginBranch(officeDO);
					
					// set the recieving branch details
					manifestDo.setDestBranch(recivngOfficeDO);
					
					manifestDo.setMode(mode);
					
					// set the datagrid values to the ManifestDO object
					manifestDo.setConsgNumber(masterBagManifestDetailTO.getManifestNumber()[i]);
					manifestDo.setIndvWeightKgs(masterBagManifestDetailTO.getWeight()[i]);
			
					manifestDo.setDepsCategory(masterBagManifestDetailTO.getDepsCategory()[i]);
					manifestDo.setRemarks(masterBagManifestDetailTO.getRemark()[i]);
					
					if (manifestTypeDo != null)
					{
						manifestDo.setMnsftTypes(manifestTypeDo);
						
					}
					
					//manifestDo.setMnsftTypes(manifestTypeDo);
					manifestDo.setManifestDate(DateFormatterUtil.slashDelimitedstringToDDMMYYYYFormat(masterBagManifestDetailTO.getOperationDateStr()));
					manifestDo.setManifestTime(masterBagManifestDetailTO.getOperationTimeStr());
					
					manifestDo.setManifestType(ApplicationConstants.INCOMING);
					manifestDo.setWeighingType(masterBagManifestDetailTO.getWeighingTypes()[i]);
					
					manifestDo.setNoOfBagMade(masterBagManifestDetailTO.getElementCount());
					manifestDo.setTotWeightKgs(Double.valueOf(masterBagManifestDetailTO.getTotWeight()));
					
					if(masterBagManifestDetailTO.getUserId() != null){
						UserDO userDO = new UserDO();
						userDO.setUserId(masterBagManifestDetailTO.getUserId());
						manifestDo.setUser(userDO);
					}else{
						manifestDo.setUser(null);
					}
					
					
					manifestDo.setServerDate(DateFormatterUtil
							.slashDelimitedstringToDDMMYYYYFormat(masterBagManifestDetailTO.getOperationDateStr()));
					manifestDo.setTransLastModifiedDate(DateFormatterUtil
							.getCurrentDate());
					
					// add the ManifestDO object to list
					convertedManifestDOList.add(manifestDo);
					
				}else {
					
					//create an object of ManifestDO 
					ManifestDO manifestDo = new ManifestDO();
					
					manifestDo.setManifestNumber(masterBagManifestDetailTO.getManifestNO());

					// set the origin branch details
					manifestDo.setOriginBranch(officeDO);
					
					// set the recieving branch details
					manifestDo.setDestBranch(recivngOfficeDO);
					
					manifestDo.setMode(mode);
					
					// set the datagrid values to the ManifestDO object
					manifestDo.setConsgNumber(masterBagManifestDetailTO.getManifestNumber()[i]);
					manifestDo.setIndvWeightKgs(masterBagManifestDetailTO.getWeight()[i]);
			
					manifestDo.setDepsCategory(masterBagManifestDetailTO.getDepsCategory()[i]);
					manifestDo.setRemarks(masterBagManifestDetailTO.getRemark()[i]);
					
					if (manifestTypeDo != null)
					{
						manifestDo.setMnsftTypes(manifestTypeDo);
						
					}
					
					//manifestDo.setMnsftTypes(manifestTypeDo);
					manifestDo.setManifestDate(DateFormatterUtil.slashDelimitedstringToDDMMYYYYFormat(masterBagManifestDetailTO.getOperationDateStr()));
					manifestDo.setManifestTime(masterBagManifestDetailTO.getOperationTimeStr());
					
					manifestDo.setManifestType(ApplicationConstants.INCOMING);
					manifestDo.setWeighingType(masterBagManifestDetailTO.getWeighingTypes()[i]);
					
					manifestDo.setNoOfBagMade(masterBagManifestDetailTO.getElementCount());
					manifestDo.setTotWeightKgs(Double.valueOf(masterBagManifestDetailTO.getTotWeight()));
					
					if(masterBagManifestDetailTO.getUserId() != null){
						UserDO userDO = new UserDO();
						userDO.setUserId(masterBagManifestDetailTO.getUserId());
						manifestDo.setUser(userDO);
					}else{
						manifestDo.setUser(null);
					}
					
					
					manifestDo.setServerDate(DateFormatterUtil
							.slashDelimitedstringToDDMMYYYYFormat(masterBagManifestDetailTO.getOperationDateStr()));
					manifestDo.setTransLastModifiedDate(DateFormatterUtil
							.getCurrentDate());
					
					// add the ManifestDO object to list
					convertedManifestDOList.add(manifestDo);					
					
				}
			}
			
			return convertedManifestDOList;
	}

}
