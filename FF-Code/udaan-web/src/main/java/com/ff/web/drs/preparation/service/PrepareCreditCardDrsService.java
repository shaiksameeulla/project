package com.ff.web.drs.preparation.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.to.drs.CreditCardDrsTO;

/**
 * The Interface PrepareCreditCardDrsService.
 *
 * @author nihsingh
 */
public interface PrepareCreditCardDrsService {

	/**
	 * Save prepare drs.
	 *
	 * @param drsInputTo the drs input to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Boolean savePrepareDrs(CreditCardDrsTO drsInputTo)throws CGBusinessException,CGSystemException;
	
	/**
	 * Find drs by drs number.
	 *
	 * @param drsInputTo the drs input to
	 * @return the prepare np dox drs to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	CreditCardDrsTO findDrsByDrsNumber(CreditCardDrsTO drsInputTo)
			throws CGBusinessException, CGSystemException;
	
	/**
	 * Modify drs.
	 *
	 * @param drsInputTo the drs input to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Boolean modifyDrs(CreditCardDrsTO drsInputTo) throws CGBusinessException,
			CGSystemException;
	
	/**
	 * Discard drs.
	 *
	 * @param drsInputTo the drs input to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Boolean discardDrs(CreditCardDrsTO drsInputTo)
			throws CGBusinessException, CGSystemException;
}
