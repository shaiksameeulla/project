/**
 * 
 */
package src.com.dtdc.mdbServices.manifest;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.dtdc.to.manifest.SelfSectorManifestTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface SelfSectorManifestMDBService.
 *
 * @author nisahoo
 */
public interface SelfSectorManifestMDBService {
	
	/**
	 * Save or update self sector manifest.
	 *
	 * @param cgBaseTO the cg base to
	 * @return the self sector manifest to
	 * @throws CGSystemException the cG system exception
	 */
	public SelfSectorManifestTO saveOrUpdateSelfSectorManifest(CGBaseTO cgBaseTO)throws CGSystemException;
	
	/**
	 * Save or update self sector manifest.
	 *
	 * @param selfSectorTO the self sector to
	 * @return the self sector manifest to
	 * @throws CGSystemException the cG system exception
	 */
	public SelfSectorManifestTO saveOrUpdateSelfSectorManifest(SelfSectorManifestTO selfSectorTO)throws CGSystemException;
	
	/**
	 * Save or update self sector mnfst intl db sync.
	 *
	 * @param manifestTOs the manifest t os
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 */
	public String saveOrUpdateSelfSectorMnfstIntlDBSync(List<SelfSectorManifestTO> manifestTOs) throws CGSystemException;

	/**
	 * Save or update self sector mnfst intl db sync.
	 *
	 * @param manifestTOs the manifest t os
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 */
	public String saveOrUpdateSelfSectorMnfstIntlDBSync(CGBaseTO manifestTOs) throws CGSystemException;
	
}
