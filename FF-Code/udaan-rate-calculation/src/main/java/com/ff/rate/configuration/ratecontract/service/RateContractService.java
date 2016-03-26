package com.ff.rate.configuration.ratecontract.service;

import java.io.IOException;
import java.util.List;

import org.apache.commons.httpclient.HttpException;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.ratemanagement.masters.RateContractDO;
import com.ff.geography.CityTO;
import com.ff.organization.OfficeTO;
import com.ff.to.ratemanagement.masters.ContractPaymentBillingLocationTO;
import com.ff.to.ratemanagement.masters.RateContractSpocTO;
import com.ff.to.ratemanagement.masters.RateContractTO;
import com.ff.to.ratemanagement.operations.ratecontract.RateCustomerSearchTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotationListViewTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;

/**
 * @author hkansagr
 */

public interface RateContractService {

	/**
	 * To Save Rate Contract Billing Details
	 * 
	 * @param rateContractTO
	 * @return rateContractTO
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	RateContractTO saveRateContractBillingDtls(RateContractTO rateContractTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * To Search Rate Contract Billing Details
	 * 
	 * @param rateContractTO
	 * @return rateContractTO
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	RateContractTO searchRateContractBillingDtls(RateContractTO rateContractTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * @param contractTO
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	RateContractTO searchContractDetails(RateContractTO contractTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * To Save Rate Contract Pickup/Delivery Details
	 * 
	 * @param rateContractTO
	 * @return boolean
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	RateContractDO saveRateContractPickupDlvDtls(RateContractTO rateContractTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * @param custName
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	List<RateCustomerSearchTO> searchCustomerInfo(String custName)
			throws CGSystemException, CGBusinessException;

	/**
	 * @param loggedInRHO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	OfficeTO getOfficeDetails(Integer loggedInRHO) throws CGBusinessException,
			CGSystemException;

	/**
	 * @param cityTO
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	CityTO getCity(CityTO cityTO) throws CGSystemException, CGBusinessException;

	/**
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws HttpException 
	 */
	void contractExpiryEmailTrigger() throws CGSystemException,
			CGBusinessException, HttpException, ClassNotFoundException, IOException;

	/**
	 * @param contractTO
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	Boolean submitContract(RateContractTO contractTO) throws CGSystemException,
			CGBusinessException;

	/**
	 * @param string
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<StockStandardTypeTO> getStandardType(String string)
			throws CGBusinessException, CGSystemException;

	/**
	 * @param userId
	 * @param region
	 * @param city
	 * @param fromDate
	 * @param toDate
	 * @param contractNo
	 * @param status
	 * @param type
	 * @param officeType 
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<RateQuotationListViewTO> rateContractListViewDetails(Integer userId,
			Integer[] region, Integer[] city, String fromDate, String toDate,
			String contractNo, String status, String type, String officeType)
			throws CGBusinessException, CGSystemException;

	/**
	 * To Search Rate Contract Pickup/Delivery Details
	 * 
	 * @param rateContractTO
	 * @return conPayBillLocTO List
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	List<ContractPaymentBillingLocationTO> searchRateContractPickupDlvDtls(
			RateContractTO rateContractTO) throws CGSystemException,
			CGBusinessException;

	/**
	 * @param contractId
	 * @param quotationId
	 * @param userId
	 * @param officeCode
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	String renewContract(Integer contractId, Integer quotationId, Integer userId, String officeCode) 
			throws CGSystemException, CGBusinessException;

	List<RateQuotationListViewTO> rateContractListViewSEDetails(Integer userId,
			String fromDate, String toDate, String contractNo, String status)
					throws CGSystemException, CGBusinessException;

	List<RateContractSpocTO> getRateContractSpocDetails(String contactType,
			Integer contractId) throws CGSystemException, CGBusinessException;

	Boolean saveRateContractSpocDetails(RateContractSpocTO rcsTO)
			 throws CGSystemException, CGBusinessException;

	RateContractDO searchRateContractPickDlvDtls(RateContractTO to)
			 throws CGSystemException, CGBusinessException;

}
