package com.ff.sap.integration.consignmentratecalculation.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.billing.ConsignmentBilling;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.consignment.ConsignmentDO;

/**
 * @author hkansagr
 * 
 */
public interface ConsignmentRateCalculationDAO {

	/**
	 * To get all consignment details whose consignment rate is not
	 * calculated/missing/NULL
	 * 
	 * @return consgDOs
	 * @throws CGSystemException
	 */
	public List<ConsignmentBilling> getConsgDtlsWhoseRateIsNull()
			throws CGSystemException;

	/**
	 * To save or update consignment details along with calculated rate
	 * 
	 * @param consignmentBillingRateDO
	 * @throws CGSystemException
	 */
	public void saveOrUpdateConsgDtlsWhoseRateIsCalculated(ConsignmentBillingRateDO consignmentBillingRateDO)
			throws CGSystemException;

	/**
	 * @param consgNo
	 * @param billingStatus
	 * @return
	 * @throws CGSystemException
	 */
	boolean UpdateConsgnBillStatusByConsgnNo(List<String> consgNo,
			String billingStatus) throws CGSystemException;

	public List<ConsignmentBilling> getRTOConsgDtlsWhoseRateIsNull() throws CGSystemException;

	public void updateBookingOpsmanStatus(String consgNo) throws CGSystemException;

	public ConsignmentDO getConsignmentByConsgNo(String consgNo) throws CGSystemException;

	public void saveOrUpdateConsgDO(ConsignmentDO consgDO) throws CGSystemException;

}
