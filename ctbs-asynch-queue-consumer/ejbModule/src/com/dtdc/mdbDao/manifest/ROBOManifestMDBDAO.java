/**
 * 
 */
package src.com.dtdc.mdbDao.manifest;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.manifest.ManifestTypeDO;

// TODO: Auto-generated Javadoc
/**
 * The Interface ROBOManifestMDBDAO.
 *
 * @author narmdr
 */
public interface ROBOManifestMDBDAO {
	
	/**
	 * Gets the manifest type.
	 *
	 * @param manifestType the manifest type
	 * @return the manifest type
	 * @throws CGSystemException the cG system exception
	 */
	public ManifestTypeDO getManifestType(String manifestType)throws CGSystemException;	
	
	/**
	 * Save or update robo check list.
	 *
	 * @param manifestDOList the manifest do list
	 * @throws CGSystemException the cG system exception
	 */
	void saveOrUpdateROBOCheckList(List<ManifestDO> manifestDOList) throws CGSystemException;
	
	/**
	 * Gets the manifest id by mfst no mfst type id mfst type consg no.
	 *
	 * @param manifestNo the manifest no
	 * @param manifestTypeId the manifest type id
	 * @param manifestType the manifest type
	 * @param consgNo the consg no
	 * @return the manifest id by mfst no mfst type id mfst type consg no
	 * @throws CGSystemException the cG system exception
	 */
	Integer getManifestIdByMfstNoMfstTypeIdMfstTypeConsgNo(String manifestNo, Integer manifestTypeId, 
			String manifestType, String consgNo) throws CGSystemException;		
}
