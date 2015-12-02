/**
 * 
 */
package src.com.dtdc.mdbServices.manifest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import src.com.dtdc.mdbDao.manifest.RTOManifestIncomingMDBDAO;
import src.com.dtdc.mdbServices.CTBSApplicationMDBDAO;

import com.capgemini.lbs.framework.constants.ManifestConstant;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.dtdc.domain.login.UserDO;
import com.dtdc.domain.manifest.RtnToOrgDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.to.manifest.RTOManifestDetailTO;

// TODO: Auto-generated Javadoc
/**
 * The Class RTOManifestIncomingMDBServiceImpl.
 *
 * @author vsulibha
 */
public class RTOManifestIncomingMDBServiceImpl implements
		RTOManifestIncomingMDBService {


	/** The logger. */
	private final static Logger logger = LoggerFactory
	.getLogger(RTOManifestIncomingMDBServiceImpl.class);
	
	/** The CTBSApplicationDAO dao. */
	private  CTBSApplicationMDBDAO ctbsApplicationMDBDAO;
	
	/** The CTBSApplicationDAO dao. */
	private  RTOManifestIncomingMDBDAO rtoManifestIncomingMDBDAO;
	
	/**
	 * method to save the details.
	 *
	 * @param cgBaseTO the cg base to
	 * @return the rTO manifest detail to
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public RTOManifestDetailTO saveRTOIncomingBagManifestDetails(CGBaseTO cgBaseTO)
			throws CGSystemException, CGBusinessException {
		
		List<RTOManifestDetailTO> rtoManifestDetailTOList = (List<RTOManifestDetailTO>) cgBaseTO.getBaseList();
		return saveRTOIncomingBagManifestDetails(rtoManifestDetailTOList);		
		
	}

	/**
	 * method to save the details.
	 *
	 * @param rtoManifestDetailTOList the rto manifest detail to list
	 * @return the rTO manifest detail to
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public RTOManifestDetailTO saveRTOIncomingBagManifestDetails(
			List<RTOManifestDetailTO> rtoManifestDetailTOList)
			throws CGSystemException, CGBusinessException {

		logger.debug("RTOManifestIncomingMDBServiceImpl::saveRTOIncomingBagManifestDetails::Start======>");
		
		RTOManifestDetailTO rtoManifestDetailTO = new RTOManifestDetailTO();
		List<RtnToOrgDO> rtoOrgList = convertTOtoDO(rtoManifestDetailTOList.get(0));
		
		rtoManifestIncomingMDBDAO.insertRTOManifestIncomingData(rtoOrgList);		

		logger.debug("RTOManifestIncomingMDBServiceImpl::saveRTOIncomingBagManifestDetails::End======>");
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
	 * Gets the rto manifest incoming mdbdao.
	 *
	 * @return the rtoManifestIncomingMDBDAO
	 */
	public RTOManifestIncomingMDBDAO getRtoManifestIncomingMDBDAO() {
		return rtoManifestIncomingMDBDAO;
	}

	/**
	 * Sets the rto manifest incoming mdbdao.
	 *
	 * @param rtoManifestIncomingMDBDAO the rtoManifestIncomingMDBDAO to set
	 */
	public void setRtoManifestIncomingMDBDAO(
			RTOManifestIncomingMDBDAO rtoManifestIncomingMDBDAO) {
		this.rtoManifestIncomingMDBDAO = rtoManifestIncomingMDBDAO;
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
		List<RtnToOrgDO> rtnToOrgList = new ArrayList<RtnToOrgDO>();

		UserDO userDO = null; 
		if(rtoManifestDetailTO.getUser()!=null){
			if(StringUtils.isNotBlank(rtoManifestDetailTO.getUser().getUserCode())){
				userDO = ctbsApplicationMDBDAO.getUserByUserCode(rtoManifestDetailTO.getUser().getUserCode());
			}
		}
		
		for (int i = 0; i < rtoManifestDetailTO.getConsignmentNumber().length; i++)
		{
			RtnToOrgDO rtnOrgDO = new RtnToOrgDO();
			
			rtnOrgDO.setManifestNumber(rtoManifestDetailTO.getManifestNo());
			rtnOrgDO.setManifestStatus(rtoManifestDetailTO.getRtoStatus());
			rtnOrgDO.setRtoStatusDate(DateFormatterUtil.getDateFromString(rtoManifestDetailTO.getStatusDateStr(), 
					"dd-mm-yyyy"));
			
			// get the origin office object
		    OfficeDO originOffice = ctbsApplicationMDBDAO.getOfficebyOfficeCode(rtoManifestDetailTO.getOriginOffice());
			
		    // get the origin office object
		    OfficeDO destOffice = ctbsApplicationMDBDAO.getOfficebyOfficeCode(rtoManifestDetailTO.getReceivingOffice());
            
            rtnOrgDO.setOriginOffice(originOffice);            
            rtnOrgDO.setDispatchingOff(destOffice);
			
			/*OfficeDO destOfficeDO = (OfficeDO)hibernateTemplate.getSessionFactory().openSession().get(OfficeDO.class, Integer.parseInt(rtoManifestTo.getReceivingOffice().getOfficeCode()));
			rtnOrgDO.setDispatchingOff(destOfficeDO);*/
			
			rtnOrgDO.setConsgNumber(rtoManifestDetailTO.getConsignmentNumber()[i]);
			rtnOrgDO.setConsgBookDate(DateFormatterUtil.stringToDDMMYYYYFormat(rtoManifestDetailTO.getBookingDatestr()[i]));
			
			rtnOrgDO.setTotalNoPieces(rtoManifestDetailTO.getNoofpieces()[i]);
			
			logger.debug("RTOManifestIncomingMDBServiceImpl::convertTOtoDO::rtoManifestDetailTO.getInscanWeight()["+i+"]==>"+rtoManifestDetailTO.getInscanWeight()[i]);	
			if(rtoManifestDetailTO.getInscanWeight()[i]!=0){
				rtnOrgDO.setInScanWt(rtoManifestDetailTO.getInscanWeight()[i]);
			}
			logger.debug("RTOManifestIncomingMDBServiceImpl::convertTOtoDO::rtnOrgDO.getInScanWt()==>"+rtnOrgDO.getInScanWt());	
			
			rtnOrgDO.setRtoWt(rtoManifestDetailTO.getRtoWeight()[i]);
			rtnOrgDO.setTypeOfDoc(rtoManifestDetailTO.getDoxType()[i]);
			rtnOrgDO.setReasonForReturn(rtoManifestDetailTO.getReasoForRTO()[i]);
			rtnOrgDO.setRtoMnfstCat(ManifestConstant.MANIFEST_TYPE_AGAINST_INCOMING);
			rtnOrgDO.setRemarks(rtoManifestDetailTO.getRemarks()[i]);

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
			
		}
			return rtnToOrgList;
	}
	

}
