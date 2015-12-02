/**
 * 
 */
package src.com.dtdc.mdbDao.manifest;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.manifest.ManifestTypeDO;

// TODO: Auto-generated Javadoc
/**
 * The Interface MasterBagManifestMDBDAO.
 *
 * @author vsulibha
 */
public interface MasterBagManifestMDBDAO {
	
	/**
	 * Insert master bag manifest data.
	 *
	 * @param manifestDO the manifest do
	 */
	public void insertMasterBagManifestData(List<ManifestDO> manifestDO);
	
	/**
	 * Gets the manifest type.
	 *
	 * @param manfstCode the manfst code
	 * @return the manifest type
	 * @throws CGBusinessException the cG business exception
	 */
	public ManifestTypeDO getManifestType(String manfstCode) throws CGBusinessException ;
	
	/**
	 * Check whether record exists.
	 *
	 * @param mnfstNO the mnfst no
	 * @param consgNo the consg no
	 * @param officeCode the office code
	 * @return the list
	 * @throws CGBusinessException the cG business exception
	 */
	public List<ManifestDO> checkWhetherRecordExists(String mnfstNO, String consgNo, String officeCode) throws CGBusinessException ;

}
