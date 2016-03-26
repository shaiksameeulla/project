/**
 * 
 */
package com.ff.web.ratecalculator.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.booking.BookingPreferenceDetailsDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;

/**
 * @author prmeher
 * 
 */
public interface RateCalculatorDAO {

	List<ProductDO> getAllProducts() throws CGSystemException;

	List<ConsignmentTypeDO> getConsignmentType() throws CGSystemException;

	List<StockStandardTypeDO> getStockStdTypeByType(String typeName)
			throws CGSystemException;

	List<BookingPreferenceDetailsDO> getAllPreferenceDetails()
			throws CGSystemException;

}
