/**
 * 
 */
package com.capgemini.lbs.framework.dao.sequence;

import com.capgemini.lbs.framework.domain.SequenceGeneratorConfigDO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;

/**
 * @author mohammes
 *
 */
public interface SequenceGeneratorDAO {
	 SequenceGeneratorConfigDO getSequenceDO(String processType)throws CGSystemException;
	Boolean updateGeneratedSequenceById(Integer sequenceGeneratorId,Long lastSeqNum)throws CGSystemException;
	String getMaxNumberFromProcess(SequenceGeneratorConfigTO seqConfigTo,
			String queryName) throws CGSystemException;
}
