/**
 * 
 */
package src.com.dtdc.mdbServices.manifest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import src.com.dtdc.mdbDao.manifest.RTOManifestOutgoingMDBDAO;
import src.com.dtdc.mdbServices.CTBSApplicationMDBDAO;

import com.capgemini.lbs.framework.constants.ManifestConstant;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.dtdc.domain.login.UserDO;
import com.dtdc.domain.manifest.RtnToOrgDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.opmaster.shipment.ModeDO;
import com.dtdc.to.manifest.RTOManifestDetailTO;

// TODO: Auto-generated Javadoc
/**
 * The Class RTOManifestOutgoingMDBServiceImpl.
 *
 * @author vsulibha
 */
public class RTOManifestOutgoingMDBServiceImpl implements
		RTOManifestOutgoingMDBService {

	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
	.getLogger(RTOManifestOutgoingMDBServiceImpl.class);
	
	/** The CTBSApplicationDAO dao. */
	private  CTBSApplicationMDBDAO ctbsApplicationMDBDAO;
	
	/** The CTBSApplicationDAO dao. */
	private  RTOManifestOutgoingMDBDAO rtoManifestOutgoingMDBDAO;
	
	
	/**
	 * method to save the RTO outgoing details.
	 *
	 * @param cgBaseTO the cg base to
	 * @return RTOManifestDetailTO RTOManifestDetailTO
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public RTOManifestDetailTO saveRTOOutgoingBagManifestDetails(
			CGBaseTO cgBaseTO) throws CGSystemException, CGBusinessException {
		
		
		List<RTOManifestDetailTO> rtoManifestDetailTOList = (List<RTOManifestDetailTO>) cgBaseTO.getBaseList();
		return saveRTOOutgoingBagManifestDetails(rtoManifestDetailTOList);	
		
	}

	/**
	 * method to save the RTO outgoing details.
	 *
	 * @param rtoManifestTOList the rto manifest to list
	 * @return RTOManifestDetailTO RTOManifestDetailTO
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public RTOManifestDetailTO saveRTOOutgoingBagManifestDetails(
			List<RTOManifestDetailTO> rtoManifestTOList)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("RTOManifestOutgoingMDBServiceImpl::saveRTOOutgoingBagManifestDetails()::START======>");	
		
		RTOManifestDetailTO rtoManifestDetailTO = new RTOManifestDetailTO();
		List<RtnToOrgDO> rtoOrgList = convertTOtoDO(rtoManifestTOList.get(0));
		
		rtoManifestOutgoingMDBDAO.insertRTOManifestOutgoingData(rtoOrgList);

		LOGGER.debug("RTOManifestOutgoingMDBServiceImpl::saveRTOOutgoingBagManifestDetails()::END======>");	
		return rtoManifestDetailTO;
		
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
	 * Gets the rto manifest outgoing mdbdao.
	 *
	 * @return the rtoManifestOutgoingMDBDAO
	 */
	public RTOManifestOutgoingMDBDAO getRtoManifestOutgoingMDBDAO() {
		return rtoManifestOutgoingMDBDAO;
	}

	/**
	 * Sets the rto manifest outgoing mdbdao.
	 *
	 * @param rtoManifestOutgoingMDBDAO the rtoManifestOutgoingMDBDAO to set
	 */
	public void setRtoManifestOutgoingMDBDAO(
			RTOManifestOutgoingMDBDAO rtoManifestOutgoingMDBDAO) {
		this.rtoManifestOutgoingMDBDAO = rtoManifestOutgoingMDBDAO;
	}
	
	/**
	 * method to convert to to DO.
	 *
	 * @param rtoManifestDetailTO the rto manifest detail to
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	private List<RtnToOrgDO> convertTOtoDO(RTOManifestDetailTO rtoManifestDetailTO) throws CGSystemException, CGBusinessException 
	{
		LOGGER.debug("RTOManifestOutgoingMDBServiceImpl::convertTOtoDO()::START======>");
		List<RtnToOrgDO> rtnToOrgList = new ArrayList<RtnToOrgDO>();

		LOGGER.debug("RTOManifestOutgoingMDBServiceImpl::convertTOtoDO()::rtoManifestDetailTO.getConsignmentNumber().length==>"+
				rtoManifestDetailTO.getConsignmentNumber().length);
		LOGGER.debug("RTOManifestOutgoingMDBServiceImpl::convertTOtoDO()::rtoManifestDetailTO.getConsignmentNumber()==>"+
				rtoManifestDetailTO.getConsignmentNumber());

		UserDO userDO = null; 
		if(rtoManifestDetailTO.getUser()!=null){
			if(StringUtils.isNotBlank(rtoManifestDetailTO.getUser().getUserCode())){
				userDO = ctbsApplicationMDBDAO.getUserByUserCode(rtoManifestDetailTO.getUser().getUserCode());
			}
		}
		
		for (int i = 0; i < rtoManifestDetailTO.getConsignmentNumber().length; i++)
		{
			try {
				RtnToOrgDO rtnOrgDO = new RtnToOrgDO();
				
				rtnOrgDO.setManifestNumber(rtoManifestDetailTO.getManifestNo());
				rtnOrgDO.setManifestWeight(rtoManifestDetailTO.getManifestWeight());
				rtnOrgDO.setManifestStatus(rtoManifestDetailTO.getRtoStatus());
				rtnOrgDO.setManifestDate(DateFormatterUtil.getDateFromString(rtoManifestDetailTO.getStatusDateStr(), DateFormatterUtil.DD_MM_YYYY));
				rtnOrgDO.setRtoDate(DateFormatterUtil.getDateFromString(rtoManifestDetailTO.getStatusDateStr(), DateFormatterUtil.DD_MM_YYYY));
				rtnOrgDO.setRtoStatusDate(DateFormatterUtil.getDateFromString(rtoManifestDetailTO.getStatusDateStr(), DateFormatterUtil.DD_MM_YYYY));
				
				// get the origin office
				OfficeDO originOfficeDO = ctbsApplicationMDBDAO.getOfficebyOfficeCode(rtoManifestDetailTO.getOriginOffice());
				rtnOrgDO.setDispatchingOff(originOfficeDO);
				
				//ModeDO modeDO = ctbsApplicationMDBDAO.getModeById(Integer.parseInt(rtoManifestDetailTO.getDispatchMode()));
				
				ModeDO modeDO = ctbsApplicationMDBDAO.getModeByCode(rtoManifestDetailTO.getDispatchMode());
				rtnOrgDO.setMode(modeDO);
				
				// get the Dest office
				OfficeDO destOfficeDO = ctbsApplicationMDBDAO.getOfficebyOfficeCode(rtoManifestDetailTO.getReceivingOffice());
				rtnOrgDO.setDestOff(destOfficeDO);
				
				rtnOrgDO.setConsgNumber(rtoManifestDetailTO.getConsignmentNumber()[i]);
				rtnOrgDO.setConsgBookDate(DateFormatterUtil.stringToDDMMYYYYFormat(rtoManifestDetailTO.getBookingDatestr()[i]));
				
				rtnOrgDO.setTotalNoPieces(rtoManifestDetailTO.getNoofpieces()[i]);
				rtnOrgDO.setInScanWt(rtoManifestDetailTO.getInscanWeight()[i]);
				rtnOrgDO.setRtoMnfstCat(ManifestConstant.MANIFEST_TYPE_AGAINST_OUTGOING);
				rtnOrgDO.setRtoStatus(ManifestConstant.RTO_OUTGOING_STATUS_PREPARED);
				rtnOrgDO.setRtoWt(rtoManifestDetailTO.getRtoWeight()[i]);
				rtnOrgDO.setTypeOfDoc(rtoManifestDetailTO.getDoxType()[i]);
				rtnOrgDO.setReasonForReturn(rtoManifestDetailTO.getReasoForRTO()[i]);

				if(rtoManifestDetailTO.getManifestDate()!=null){
					rtnOrgDO.setManifestDate(rtoManifestDetailTO.getManifestDate());			
				}
				if(rtoManifestDetailTO.getRtoDate()!=null){
					rtnOrgDO.setRtoDate(rtoManifestDetailTO.getRtoDate());			
				}
				if(rtoManifestDetailTO.getStatusDate()!=null){
					rtnOrgDO.setRtoStatusDate(rtoManifestDetailTO.getStatusDate());			
				}
				if(rtoManifestDetailTO.getTransLastModifiedDate()!=null){
					rtnOrgDO.setTransLastModifiedDate(rtoManifestDetailTO.getTransLastModifiedDate());			
				}
				if(StringUtils.isNotBlank(rtoManifestDetailTO.getTime())){
					rtnOrgDO.setManifestTime(rtoManifestDetailTO.getTime());
				}	
				if(userDO!=null){
					rtnOrgDO.setUser(userDO);
				}
				//rtnOrgDO.setDbServer("N");
				//rtnOrgDO.setReadByLocal("N");
				rtnOrgDO.setDiFlag("N");
				
				rtnToOrgList.add(rtnOrgDO);
			} catch (Exception e) {
				LOGGER.error("RTOManifestOutgoingMDBServiceImpl::convertTOtoDO()" + e.getMessage());
				LOGGER.error("Exception Occured in::RTOManifestOutgoingMDBServiceImpl::convertTOtoDO()" + e.getCause().getMessage());
				LOGGER.error("Exception Occured in::RTOManifestOutgoingMDBServiceImpl::convertTOtoDO()" + e.getStackTrace());
			}
		}

		LOGGER.debug("RTOManifestOutgoingMDBServiceImpl::convertTOtoDO()::END======>");
		return rtnToOrgList;
	}	

}
