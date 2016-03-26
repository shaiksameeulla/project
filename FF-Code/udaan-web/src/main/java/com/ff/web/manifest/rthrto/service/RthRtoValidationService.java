package com.ff.web.manifest.rthrto.service;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.manifest.rthrto.RthRtoValidationTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface RthRtoValidationService.
 */
public interface RthRtoValidationService {

	/**
	 * Save or update rth rto validation.
	 *
	 * @param rthRtoValidationTO the rth rto validation to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	void saveOrUpdateRthRtoValidation(RthRtoValidationTO rthRtoValidationTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Find consignment number4 rth.
	 *
	 * @param rthRtoValidationTO the rth rto validation to
	 * @return the rth rto validation to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	RthRtoValidationTO findConsignmentNumber4Rth(
			RthRtoValidationTO rthRtoValidationTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * Find consignment number4 rto.
	 *
	 * @param rthRtoValidationTO the rth rto validation to
	 * @return the rth rto validation to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	RthRtoValidationTO findConsignmentNumber4Rto(
			RthRtoValidationTO rthRtoValidationTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * Generate eod report4 on hold consignment.
	 *
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws HttpException 
	 */
	void generateEodReport4OnHoldConsignment() throws CGBusinessException,
			CGSystemException, HttpException, ClassNotFoundException, IOException;

	/**
	 * Two way write.
	 *
	 * @param rthRtoValidationTO the rth rto validation to
	 */
	void twoWayWrite(RthRtoValidationTO rthRtoValidationTO);

}
