/**
 * 
 */
package src.com.dtdc.mdbDao.manifest;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.dtdc.domain.manifest.ManifestDO;

// TODO: Auto-generated Javadoc
/**
 * The Interface BagManifestMDBDAO.
 *
 * @author nisahoo
 */
public interface BagManifestMDBDAO {
	
	/**
	 * Gets the bag manifest details by composite id.
	 *
	 * @param manifestNo the manifest no
	 * @param cnNo the cn no
	 * @param loginOfficeCode the login office code
	 * @return the bag manifest details by composite id
	 * @throws CGSystemException the cG system exception
	 */
	public ManifestDO getBagManifestDetailsByCompositeID(String manifestNo,
			String cnNo, String loginOfficeCode) throws CGSystemException;
	
	/**
	 * Save bag manifest data.
	 *
	 * @param manifestDOList the manifest do list
	 */
	public void saveBagManifestData(List<ManifestDO> manifestDOList);

}
