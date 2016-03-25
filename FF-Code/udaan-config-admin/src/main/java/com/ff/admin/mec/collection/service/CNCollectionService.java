/**
 * 
 */
package com.ff.admin.mec.collection.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.mec.collection.CNCollectionTO;

/**
 * @author prmeher
 *
 */
public interface CNCollectionService {
	
	public CNCollectionTO getTodaysDeliverdConsgDtls(CNCollectionTO cnCollectionTO)
			throws CGBusinessException, CGSystemException;
	
	public CNCollectionTO saveOrUpdateCNCollection(CNCollectionTO cnCollectionTO)
			throws CGBusinessException, CGSystemException;


}
