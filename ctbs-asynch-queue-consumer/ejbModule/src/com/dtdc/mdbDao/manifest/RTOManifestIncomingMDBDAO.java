/**
 * 
 */
package src.com.dtdc.mdbDao.manifest;

import java.util.List;

import com.dtdc.domain.manifest.RtnToOrgDO;

// TODO: Auto-generated Javadoc
/**
 * The Interface RTOManifestIncomingMDBDAO.
 *
 * @author vsulibha
 */
public interface RTOManifestIncomingMDBDAO {
	
	/**
	 * Insert rto manifest incoming data.
	 *
	 * @param rtoOrgDO the rto org do
	 */
	public void insertRTOManifestIncomingData(List<RtnToOrgDO> rtoOrgDO);

}
