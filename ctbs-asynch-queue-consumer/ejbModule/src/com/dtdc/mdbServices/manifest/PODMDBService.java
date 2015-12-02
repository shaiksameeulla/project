package src.com.dtdc.mdbServices.manifest;

/**
 * 
 */

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.dtdc.to.manifest.OutgoingManifestPODTO;
import com.dtdc.to.manifest.UnstampedPODManifestTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface PODMDBService.
 *
 * @author mohammal
 */
public interface PODMDBService {
	
	
	/**
	 * Save pod.
	 *
	 * @param pod the pod
	 * @return the outgoing manifest podto
	 * @throws Exception the exception
	 */
	public OutgoingManifestPODTO savePOD(OutgoingManifestPODTO pod) throws Exception;
	
	/**
	 * Save pod.
	 *
	 * @param miscExpenseTo the misc expense to
	 * @return the outgoing manifest podto
	 * @throws Exception the exception
	 */
	public OutgoingManifestPODTO savePOD(final CGBaseTO miscExpenseTo) throws Exception;
	
	/**
	 * Update pod.
	 *
	 * @param pod the pod
	 * @return the outgoing manifest podto
	 * @throws Exception the exception
	 */
	public OutgoingManifestPODTO updatePOD(OutgoingManifestPODTO pod) throws Exception;
	
	/**
	 * Update pod.
	 *
	 * @param baseTo the base to
	 * @return the outgoing manifest podto
	 * @throws Exception the exception
	 */
	public OutgoingManifestPODTO updatePOD(final CGBaseTO baseTo) throws Exception;
	
	/**
	 * Save unstamped pod manifest.
	 *
	 * @param baseTO the base to
	 * @return the unstamped pod manifest to
	 * @throws CGSystemException the cG system exception
	 */
	public UnstampedPODManifestTO saveUnstampedPODManifest(CGBaseTO baseTO) throws CGSystemException;
	
	/**
	 * Save unstamped pod manifest.
	 *
	 * @param podTO the pod to
	 * @return the unstamped pod manifest to
	 * @throws CGSystemException the cG system exception
	 */
	public UnstampedPODManifestTO saveUnstampedPODManifest(UnstampedPODManifestTO podTO) throws CGSystemException;
	
}
