package com.ff.report.customer.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.geography.RegionDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.geography.RegionTO;
import com.ff.organization.OfficeTO;
import com.ff.report.customer.dao.CustomerReportServiceDAO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.universe.serviceOffering.dao.ServiceOfferingServiceDAO;
import com.ff.universe.serviceOffering.service.ServiceOfferingCommonServiceImpl;

public class CustomerReportServiceImpl implements CustomerReportService{
	
	private final static Logger LOGGER = LoggerFactory
			.getLogger(CustomerReportServiceImpl.class);
	private CustomerReportServiceDAO customerReportServiceDAO;
	

	public void setCustomerReportServiceDAO(
			CustomerReportServiceDAO customerReportServiceDAO) {
		this.customerReportServiceDAO = customerReportServiceDAO;
	}

	@Override
	public List<OfficeTO> getAllOffices(Integer userId, Integer cityId) throws CGBusinessException,
			CGSystemException {
		List<OfficeDO> officeDoList = null;
		List<OfficeTO> officeToList = null;

		officeToList = customerReportServiceDAO.getAllOffices(userId, cityId);
		

		return officeToList;
	}

	@Override
	public List<ProductTO> getProducts() throws CGBusinessException,
			CGSystemException {List<ProductDO> products = null;
			List<ProductTO> productTOs = null;
			try {
				products = customerReportServiceDAO.getAllProducts();
				if (!StringUtil.isEmptyList(products)) {
					productTOs = new ArrayList<ProductTO>();
					productTOs = (List<ProductTO>) CGObjectConverter
							.createTOListFromDomainList(products, ProductTO.class);
				}
			} catch (CGBusinessException e) {
				LOGGER.error(
						"ERROR : CustomerReportServiceImpl.getProducts()",
						e);
			}
			return productTOs;
		}

}
