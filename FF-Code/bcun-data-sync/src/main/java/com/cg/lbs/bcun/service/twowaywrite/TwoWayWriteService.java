/**
 * 
 */
package com.cg.lbs.bcun.service.twowaywrite;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;

/**
 * The Interface TwoWayWriteService.
 *
 * @author narmdr
 */
public interface TwoWayWriteService {

	/**
	 * Two way write process start.
	 *
	 * @param ids the ids
	 * @param processNames the process names
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	void twoWayWriteProcessStart(List<? extends Number> ids, List<String> processNames) throws CGBusinessException, CGSystemException;

	/**
	 * Re process two way write.
	 *
	 * @param ids the ids
	 * @param processNames the process names
	 */
	void reProcessTwoWayWrite(List<? extends Number> ids, List<String> processNames);

}
