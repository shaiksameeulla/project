/**
 * 
 */
package com.ff.sap.integration.sd.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.httpclient.HttpException;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.billing.SAPBillingConsignmentSummaryDO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.ratemanagement.masters.SAPContractDO;
import com.ff.sap.integration.to.SAPBillingConsgSummaryTO;
import com.ff.sap.integration.to.SAPContractTO;
import com.ff.serviceOfferring.ProductTO;

/**
 * @author cbhure
 * 
 */
public interface SDSAPIntegrationService {

	/**
	 * @param sapContractTO
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	List<SAPContractDO> findContractDtlsForSAPIntegration(
			SAPContractTO sapContractTO) throws CGSystemException,
			CGBusinessException;

	void updateContractStagingStatusFlag(String sapStatus,
			List<SAPContractDO> sapContractDOs, String exception)
			throws CGSystemException;

	// List<SAPBillingConsignmentSummaryDO>
	// findBillingConsgSummaryForSAPIntegration(SAPBillingConsgSummaryTO
	// sapBillConsgSummaryTO) throws CGSystemException, ParseException,
	// CGBusinessException;

	void updateBCSStagingStatusFlag(String sapStatus,
			List<SAPBillingConsignmentSummaryDO> sapBillingConsgSummaryDOList,
			String exception) throws CGSystemException;

	void updateBillSalesOrderNumber() throws CGSystemException, HttpException,
			ClassNotFoundException, IOException;

	public void getConsignmentsForRate() throws CGBusinessException,
			CGSystemException, InterruptedException, HttpException,
			ClassNotFoundException, IOException;

	public BookingDO getConsgBookingDetails(String consgNo)
			throws CGBusinessException, CGSystemException;

	public ProductTO getProduct(Integer productId) throws CGBusinessException,
			CGSystemException;

	public boolean billing_Stock_consolidation_Proc()
			throws CGBusinessException, CGSystemException;

	public boolean billing_consolidation_Proc() throws CGBusinessException,
			CGSystemException;

	boolean insertBillingSummaryIntoSAPStagingTable(
			SAPBillingConsgSummaryTO sapBillConsgSummaryTO)
			throws CGSystemException, ParseException, CGBusinessException;

	Long getMaxLimitToSendDataToSAP(String maxCheck) throws CGSystemException;

	public List<SAPBillingConsignmentSummaryDO> findBCSDtlsFromStaging(
			SAPBillingConsgSummaryTO sapBillConsgSummaryTO,
			Long maxDataCountLimit) throws CGSystemException;

	Long getBCSDtlsCountFromStaging(
			SAPBillingConsgSummaryTO sapBillConsgSummaryTO)throws CGSystemException;

	boolean ba_billing_consolidation_Proc() throws CGBusinessException,
	CGSystemException;;
}
