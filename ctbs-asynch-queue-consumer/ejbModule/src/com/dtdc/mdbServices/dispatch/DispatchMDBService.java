package src.com.dtdc.mdbServices.dispatch;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.dtdc.to.dispatch.DispatchTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface DispatchMDBService.
 */
public interface DispatchMDBService {
	
	/**
	 * Save dispatch.
	 *
	 * @param cgDispatchTO the cg dispatch to
	 * @throws CGSystemException the cG system exception
	 */
	void saveDispatch(CGBaseTO cgDispatchTO) throws CGSystemException;
	
	/**
	 * Save dispatch.
	 *
	 * @param dispatchTO the dispatch to
	 * @throws CGSystemException the cG system exception
	 */
	void saveDispatch(DispatchTO dispatchTO) throws CGSystemException;
	
	/**
	 * Modify dispatch.
	 *
	 * @param cgDispatchTO the cg dispatch to
	 * @return the dispatch to
	 * @throws CGSystemException the cG system exception
	 */
	DispatchTO modifyDispatch(CGBaseTO cgDispatchTO) throws CGSystemException;
	
	/**
	 * Modify dispatch.
	 *
	 * @param to the to
	 * @return the dispatch to
	 * @throws CGSystemException the cG system exception
	 */
	DispatchTO modifyDispatch(DispatchTO to) throws CGSystemException;
	
	/**
	 * Save receiver.
	 *
	 * @param cgDispatchTO the cg dispatch to
	 * @throws CGSystemException the cG system exception
	 */
	void saveReceiver(CGBaseTO cgDispatchTO) throws CGSystemException;
	
	/**
	 * Save receiver.
	 *
	 * @param dispatchTO the dispatch to
	 * @throws CGSystemException the cG system exception
	 */
	void saveReceiver(DispatchTO dispatchTO) throws CGSystemException;
}
