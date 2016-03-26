/**
 * 
 */
package com.ff.web.ratecalculator.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.booking.BookingPreferenceDetailsDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;
import com.ff.web.ratecalculator.constants.RateCalculatorConstants;

/**
 * @author prmeher
 * 
 */
public class RateCalculatorDAOImpl extends CGBaseDAO implements
		RateCalculatorDAO {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(RateCalculatorDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductDO> getAllProducts() throws CGSystemException {
		LOGGER.debug("RateCalculatorDAOImpl::getAllProducts::START------------>:::::::");
		List<ProductDO> productDO = null;
		try {
			productDO = getHibernateTemplate().findByNamedQuery(
					RateCalculatorConstants.QRY_GET_ALL_PRODUCTS);
		} catch (Exception e) {
			LOGGER.error("ERROR : RateCalculatorDAOImpl.getAllProducts", e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("RateCalculatorDAOImpl::getAllProducts::END------------>:::::::");
		return productDO;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ConsignmentTypeDO> getConsignmentType()
			throws CGSystemException {
		try {
			List<ConsignmentTypeDO> consignmentTypeDOs = null;
			String queryName = "getConsignmentType";
			consignmentTypeDOs = getHibernateTemplate().findByNamedQuery(
					queryName);
			return consignmentTypeDOs;
		} catch (Exception e) {
					LOGGER.error("ERROR : RateCalculatorDAOImpl.getConsignmentType", e);
			throw new CGSystemException(e);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StockStandardTypeDO> getStockStdTypeByType(String typeName)
			throws CGSystemException {
		LOGGER.debug("RateCalculatorDAOImpl::getStockStdTypeByType::START------------>:::::::");
		List<StockStandardTypeDO> typesList = null;
		String params[] = {"typeName"};
		Object[] values = new Object[]{typeName};
		typesList = getHibernateTemplate().findByNamedQueryAndNamedParam(
				"getStockStdTypeByTypeName", params, values);
		LOGGER.debug("RateCalculatorDAOImpl::getStockStdTypeByType::END------------>:::::::");
		return typesList;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<BookingPreferenceDetailsDO> getAllPreferenceDetails()
			throws CGSystemException {
		LOGGER.debug("RateCalculatorDAOImpl::getAllPreferenceDetails::START------------>:::::::");
		List<BookingPreferenceDetailsDO> preferencesDO = null;
		try {
			preferencesDO = getHibernateTemplate().findByNamedQuery(
					RateCalculatorConstants.QRY_GET_ALL_BOOKING_PREFERENCES);
		} catch (Exception e) {
			LOGGER.error("ERROR : RateCalculatorDAOImpl.getAllPreferenceDetails", e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("RateCalculatorDAOImpl::getAllPreferenceDetails::END------------>:::::::");
		return preferencesDO;
	}

}
