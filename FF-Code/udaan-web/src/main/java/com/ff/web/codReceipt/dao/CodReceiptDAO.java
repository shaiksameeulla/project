package com.ff.web.codReceipt.dao;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.to.codreceipt.ExpenseAliasTO;

public interface CodReceiptDAO {

	public ConsignmentDO getConsingmentDtlsForCodRecpt(String consgNumner)
			throws CGSystemException;
	public BookingDO getConsgBookingDtsForCodRcpt(String consgNo) throws CGSystemException;
	public ExpenseAliasTO getExpenseDetails(Integer consgId)throws CGSystemException;
}
