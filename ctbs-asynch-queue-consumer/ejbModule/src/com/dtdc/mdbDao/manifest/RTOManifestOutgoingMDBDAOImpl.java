/**
 * 
 */
package src.com.dtdc.mdbDao.manifest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.frameworkbaseDAO.CGBaseDAO;
import com.dtdc.domain.manifest.RtnToOrgDO;

// TODO: Auto-generated Javadoc
/**
 * The Class RTOManifestOutgoingMDBDAOImpl.
 *
 * @author vsulibha
 */
public class RTOManifestOutgoingMDBDAOImpl extends CGBaseDAO implements
		RTOManifestOutgoingMDBDAO {

	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
	.getLogger(RTOManifestOutgoingMDBDAOImpl.class);
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.RTOManifestOutgoingMDBDAO#insertRTOManifestOutgoingData(List)
	 */
	@Override
	public void insertRTOManifestOutgoingData(List<RtnToOrgDO> rtoOrgDOList) {
		
		LOGGER.info("RTOManifestOutgoingMDBDAOImpl: insertRTOManifestIncomingData():START ");

		for (RtnToOrgDO rtnToOrgDO : rtoOrgDOList) {
			getHibernateTemplate().save(rtnToOrgDO);
		}
		LOGGER.info("RTOManifestOutgoingMDBDAOImpl: insertRTOManifestIncomingData():END ");
		
	}
	
	

}
