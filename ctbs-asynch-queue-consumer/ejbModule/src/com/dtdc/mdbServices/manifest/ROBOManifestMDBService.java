/**
 * 
 */
package src.com.dtdc.mdbServices.manifest;

import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.dtdc.to.manifest.RoboCheckListTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface ROBOManifestMDBService.
 *
 * @author narmdr
 */
public interface ROBOManifestMDBService {	
	
	/**
	 * Save robo manifest.
	 *
	 * @param cgBaseTO the cg base to
	 * @throws Exception the exception
	 */
	public void saveROBOManifest(CGBaseTO cgBaseTO) throws Exception;	
	
	/**
	 * Save robo manifest.
	 *
	 * @param roboCheckListTO the robo check list to
	 * @throws Exception the exception
	 */
	public void saveROBOManifest(RoboCheckListTO roboCheckListTO) throws Exception;

}
