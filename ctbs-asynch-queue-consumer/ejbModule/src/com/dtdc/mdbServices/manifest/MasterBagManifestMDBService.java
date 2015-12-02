/**
 * 
 */
package src.com.dtdc.mdbServices.manifest;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.dtdc.to.manifest.MasterBagManifestDetailTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface MasterBagManifestMDBService.
 *
 * @author vsulibha
 */
public interface MasterBagManifestMDBService {
	
	/**
	 * Save incoming master bag manifest details.
	 *
	 * @param cgBaseTO the cg base to
	 * @return the master bag manifest detail to
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public MasterBagManifestDetailTO saveIncomingMasterBagManifestDetails(CGBaseTO cgBaseTO) throws CGSystemException, CGBusinessException;
	
	/**
	 * Save incoming master bag manifest details.
	 *
	 * @param bagManifestDetailTOList the bag manifest detail to list
	 * @return the master bag manifest detail to
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public MasterBagManifestDetailTO saveIncomingMasterBagManifestDetails(List<MasterBagManifestDetailTO> bagManifestDetailTOList) throws CGSystemException, CGBusinessException;

}
