/**
 * 
 */
package com.capgemini.lbs.framework.bs.sequence;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;

/**
 * @author mohammes
 *
 */
public interface SequenceGeneratorService {
	SequenceGeneratorConfigTO getGeneratedSequence(SequenceGeneratorConfigTO inputTO)throws CGBusinessException,CGSystemException;
	
	//Added by Shaheed
	SequenceGeneratorConfigTO getCollectionSequence(SequenceGeneratorConfigTO inputTO)throws CGBusinessException,CGSystemException;
	
	//Code ends here- Shaheed
}
