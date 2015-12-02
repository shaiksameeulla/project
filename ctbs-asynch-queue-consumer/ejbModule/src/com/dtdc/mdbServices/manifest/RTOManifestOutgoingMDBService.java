/**
 * 
 */
package src.com.dtdc.mdbServices.manifest;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.dtdc.to.manifest.RTOManifestDetailTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface RTOManifestOutgoingMDBService.
 *
 * @author vsulibha
 */
public interface RTOManifestOutgoingMDBService {
	
	/**
	 * Save rto outgoing bag manifest details.
	 *
	 * @param cgBaseTO the cg base to
	 * @return the rTO manifest detail to
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public RTOManifestDetailTO saveRTOOutgoingBagManifestDetails(CGBaseTO cgBaseTO) throws CGSystemException, CGBusinessException;
	
	/**
	 * Save rto outgoing bag manifest details.
	 *
	 * @param rtoManifestTOList the rto manifest to list
	 * @return the rTO manifest detail to
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public RTOManifestDetailTO saveRTOOutgoingBagManifestDetails(List<RTOManifestDetailTO> rtoManifestTOList) throws CGSystemException, CGBusinessException;


}
