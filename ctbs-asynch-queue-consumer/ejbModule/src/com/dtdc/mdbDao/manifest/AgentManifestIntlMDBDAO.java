/**
 * 
 */
package src.com.dtdc.mdbDao.manifest;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.master.office.OpsOfficeDO;

// TODO: Auto-generated Javadoc
/**
 * The Interface AgentManifestIntlMDBDAO.
 *
 * @author rchalich
 */
public interface AgentManifestIntlMDBDAO {

	/**
	 * Insert or update agent manifest.
	 *
	 * @param manifestDOList the manifest do list
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 */
	public String insertOrUpdateAgentManifest(List<ManifestDO> manifestDOList) throws CGSystemException;
	
	/**
	 * Find by manifest number.
	 *
	 * @param manifestNumber the manifest number
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	public List<ManifestDO> findByManifestNumber(String manifestNumber)throws CGSystemException;
	
	/**
	 * Gets the gateway offices by country.
	 *
	 * @param originOfficeId the origin office id
	 * @param opsOffType the ops off type
	 * @return the gateway offices by country
	 * @throws CGSystemException the cG system exception
	 */
	List<OpsOfficeDO> getGatewayOfficesByCountry(Integer originOfficeId,String opsOffType) throws CGSystemException;
}
