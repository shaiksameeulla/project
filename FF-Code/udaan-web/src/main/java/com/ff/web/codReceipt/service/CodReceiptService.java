package com.ff.web.codReceipt.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.to.codreceipt.CodReceiptTO;

public interface CodReceiptService {

	public CodReceiptTO getConsgDetails(String consgNo)throws CGBusinessException,
    CGSystemException;
	
	public String generateCodReceiptNumber(String officeCode)
			throws CGBusinessException, CGSystemException;
}
