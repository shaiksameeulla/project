/**
 * 
 */
package src.com.dtdc.mdbDao.manifest;

import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.manifest.ManifestTypeDO;

// TODO: Auto-generated Javadoc
/**
 * The Interface SelfSectorManifestMDBDAO.
 *
 * @author nisahoo
 */
public interface SelfSectorManifestMDBDAO {
	
	/**
	 * Save or update self sector manifest.
	 *
	 * @param manifestDO the manifest do
	 */
	public void saveOrUpdateSelfSectorManifest(ManifestDO manifestDO);
	
	/**
	 * Gets the manifest type by code.
	 *
	 * @param manifestCode the manifest code
	 * @return the manifest type by code
	 */
	public ManifestTypeDO getManifestTypeByCode(String manifestCode);
	
	/**
	 * Gets the manifest details by composite id.
	 *
	 * @param manifestNo the manifest no
	 * @param cnNo the cn no
	 * @param manifestType the manifest type
	 * @return the manifest details by composite id
	 */
	public ManifestDO getManifestDetailsByCompositeID(String manifestNo, String cnNo, String manifestType);
	
	

}
