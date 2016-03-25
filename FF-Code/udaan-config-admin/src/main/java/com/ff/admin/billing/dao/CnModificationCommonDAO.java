package com.ff.admin.billing.dao;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.booking.BookingModifiedDO;
import com.ff.domain.booking.BookingTypeDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.consignment.ConsignmentModifiedDO;
import com.ff.domain.mec.SAPLiabilityEntriesDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.to.billing.CustModificationAliasTO;

public interface CnModificationCommonDAO {

	String getBookingTypeByCustcode(String customer_code) throws CGSystemException;

	public ConsignmentDO getConsignmentDetails(String consgNo)throws CGSystemException;
	
	public BookingDO getBookingDeatils(String consgNo) throws CGSystemException;

	public CustModificationAliasTO getCnModificationValidationDetails(String consgNo)throws CGSystemException;
	
	public boolean saveOrUpdateCustModification(ConsignmentDO consignmentDO, BookingModifiedDO bookingModifiedDO,
			ConsignmentModifiedDO consignmentModifiedDO, BookingDO bookDo,
			SAPLiabilityEntriesDO sapLiabilityEntriesDO) throws CGSystemException;

	public BookingTypeDO getBookingTypeDO(String bookingType) throws CGSystemException;

	public SAPLiabilityEntriesDO checkSapLiabilityEntriesDetails(String consgNo) throws CGSystemException;

	public ProductDO getProductDetails(Integer productId) throws CGSystemException;

	public CustomerDO getCustomerByIdOrCode(Integer customerId, String customerCode) throws CGSystemException;
}
