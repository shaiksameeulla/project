package src.com.dtdc.mdbServices.manifest;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.dtdc.to.manifest.BagManifestDetailTO;


// TODO: Auto-generated Javadoc
/**
 * The Interface BagManifestMDBService.
 */
public interface BagManifestMDBService {
	
	/**
	 * Save incoming bag manifest details.
	 *
	 * @param cgBaseTO the cg base to
	 * @return the bag manifest detail to
	 * @throws CGBusinessException the cG business exception
	 */
	public BagManifestDetailTO saveIncomingBagManifestDetails(CGBaseTO cgBaseTO) throws CGBusinessException;
	
	/**
	 * Save incoming bag manifest details.
	 *
	 * @param bagManifestDetailTOList the bag manifest detail to list
	 * @return the bag manifest detail to
	 * @throws CGBusinessException the cG business exception
	 */
	public BagManifestDetailTO saveIncomingBagManifestDetails(List<BagManifestDetailTO> bagManifestDetailTOList) throws CGBusinessException;

}
