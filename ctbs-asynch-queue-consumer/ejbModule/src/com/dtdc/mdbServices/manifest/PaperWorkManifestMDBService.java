package src.com.dtdc.mdbServices.manifest;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.dtdc.to.manifest.PaperWorkManifestNonDoxTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface PaperWorkManifestMDBService.
 */
public interface PaperWorkManifestMDBService {
	
	/**
	 * Save or update paper work manifest.
	 *
	 * @param cgBaseTO the cg base to
	 * @return the paper work manifest non dox to
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public PaperWorkManifestNonDoxTO saveOrUpdatePaperWorkManifest(CGBaseTO cgBaseTO)throws CGSystemException, CGBusinessException;
	
	/**
	 * Save or update paper work manifest.
	 *
	 * @param pwmanifestTO the pwmanifest to
	 * @return the paper work manifest non dox to
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public PaperWorkManifestNonDoxTO saveOrUpdatePaperWorkManifest(PaperWorkManifestNonDoxTO pwmanifestTO)throws CGSystemException, CGBusinessException;

}
