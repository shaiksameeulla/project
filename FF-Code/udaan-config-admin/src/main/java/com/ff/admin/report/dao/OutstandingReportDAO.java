package com.ff.admin.report.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.mec.SAPReportDO;
import com.ff.domain.mec.SAPOutstandingPaymentDO;

/**
 * @author khassan
 * 
 */
public interface OutstandingReportDAO {

	/**
	 * @param transactionNo
	 * @param collectionType
	 * @return
	 * @throws CGSystemException
	 */
	public Boolean saveReportData(SAPOutstandingPaymentDO outstandingReportDO)
			throws CGSystemException;

	public List<SAPReportDO> getReportList() throws CGSystemException;
			

}
