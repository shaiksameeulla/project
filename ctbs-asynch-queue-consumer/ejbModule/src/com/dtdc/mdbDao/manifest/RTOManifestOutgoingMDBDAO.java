/**
 * 
 */
package src.com.dtdc.mdbDao.manifest;

import java.util.List;

import com.dtdc.domain.manifest.RtnToOrgDO;

// TODO: Auto-generated Javadoc
/**
 * The Interface RTOManifestOutgoingMDBDAO.
 *
 * @author vsulibha
 */
public interface RTOManifestOutgoingMDBDAO {
	
	/**
	 * Insert rto manifest outgoing data.
	 *
	 * @param rtoOrgDO the rto org do
	 */
	public void insertRTOManifestOutgoingData(List<RtnToOrgDO> rtoOrgDO);

}
