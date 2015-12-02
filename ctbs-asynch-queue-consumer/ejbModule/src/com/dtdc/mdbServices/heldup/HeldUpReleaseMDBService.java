/**
 * 
 */
package src.com.dtdc.mdbServices.heldup;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.dtdc.to.heldup.HeldUpReleaseTO;
import com.dtdc.to.heldup.heldupReleasedbsyn.HeldUpReleaseParentTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface HeldUpReleaseMDBService.
 *
 * @author mohammes
 */
public interface HeldUpReleaseMDBService {
	
	/**
	 * Save held up.
	 *
	 * @param heldUpReleaseTO the held up release to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public Boolean saveHeldUp(HeldUpReleaseTO heldUpReleaseTO) throws CGBusinessException,CGSystemException;
	
	/**
	 * Save held up.
	 *
	 * @param heldUpTo the held up to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public Boolean saveHeldUp(CGBaseTO heldUpTo) throws CGBusinessException,CGSystemException;
	
	/**
	 * Save release.
	 *
	 * @param releaseTo the release to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public Boolean saveRelease(CGBaseTO releaseTo) throws CGBusinessException,CGSystemException;
	
	/**
	 * Save held up release db syn.
	 *
	 * @param heldUpTo the held up to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public Boolean saveHeldUpReleaseDbSyn(CGBaseTO heldUpTo) throws CGBusinessException,CGSystemException;
	
	/**
	 * Save held up release db syn.
	 *
	 * @param helupReleaseList the helup release list
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public Boolean saveHeldUpReleaseDbSyn(List<HeldUpReleaseParentTO> helupReleaseList) throws CGBusinessException,CGSystemException;
}
