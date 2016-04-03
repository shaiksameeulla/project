package com.ff.report.customer.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.business.ConsignmentCustomerTO;
import com.ff.domain.geography.RegionDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.organization.OfficeTO;
import com.ff.report.common.util.CommonReportConstant;
import com.ff.serviceOfferring.ProductTO;
import com.ff.universe.serviceOffering.dao.ServiceOfferingServiceDAOImpl;

public class CustomerReportServiceDAOImpl extends CGBaseDAO implements CustomerReportServiceDAO{
	
	private final static Logger LOGGER = LoggerFactory
			.getLogger(CustomerReportServiceDAOImpl.class);

	@Override
	public List<ProductDO> getAllProducts() throws CGBusinessException,
			CGSystemException {
		List<ProductDO> products = null;
		Session session = null;
		try {
			session = createSession();
			/*
			 * products = (List<ProductDO>) session
			 * .createCriteria(ProductDO.class).list();
			 */
			products = session.createCriteria(ProductDO.class)
					.addOrder(Order.asc("productName")).list();

		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ServiceOfferingServiceDAOImpl.getAllProducts", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return products;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeTO> getAllOffices(Integer userId, Integer cityId)
			throws CGBusinessException, CGSystemException {
		List<OfficeTO> officeTOList = null;
		Session session = null;
		String query = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			query = CommonReportConstant.GET_OFFICES_FOR_CUSTOMER;
			officeTOList = session
					.createSQLQuery(query)
					.addScalar("officeName")
					.addScalar("officeCode")
					.addScalar("officeId")
					.setParameter("userId", userId)
					.setParameter("cityId", cityId)
					.setResultTransformer(
							Transformers
									.aliasToBean(OfficeTO.class))
					.list();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: CommonReportDAOImpl::getAllRegionForBoUser ::", e);
			throw new CGSystemException(e);
		}

		return officeTOList;
	}


}
