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
 * The Class RTOManifestIncomingMDBDAOImpl.
 *
 * @author vsulibha
 */
public class RTOManifestIncomingMDBDAOImpl extends CGBaseDAO implements
		RTOManifestIncomingMDBDAO {

	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
	.getLogger(RTOManifestIncomingMDBDAOImpl.class);
	
	/* (non-Javadoc)
	 * @see src.com.dtdc.mdbDao.manifest.RTOManifestIncomingMDBDAO#insertRTOManifestIncomingData(java.util.List)
	 */
	@Override
	public void insertRTOManifestIncomingData(List<RtnToOrgDO> rtoOrgDOList) {
		
		LOGGER.info("RTOManifestIncomingMDBDAOImpl: insertRTOManifestIncomingData():START ");

		for (RtnToOrgDO rtnToOrgDO : rtoOrgDOList) {
			getHibernateTemplate().save(rtnToOrgDO);
		}
		LOGGER.info("RTOManifestIncomingMDBDAOImpl: insertRTOManifestIncomingData():END ");
	}

}
