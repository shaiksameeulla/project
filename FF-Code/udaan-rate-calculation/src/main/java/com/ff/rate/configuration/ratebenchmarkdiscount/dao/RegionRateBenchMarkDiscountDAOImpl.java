package com.ff.rate.configuration.ratebenchmarkdiscount.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.ratemanagement.masters.RateIndustryCategoryDO;
import com.ff.domain.ratemanagement.operations.rateBenchmarkDiscount.RegionRateBenchMarkDiscountDO;
import com.ff.rate.configuration.ratebenchmarkdiscount.constants.RateBenchMarkDiscountConstants;
import com.ff.to.ratemanagement.operations.rateBenchmarkDiscount.RegionRateBenchMarkDiscountTO;

/**
 * @author preegupt
 *
 */
public class RegionRateBenchMarkDiscountDAOImpl extends CGBaseDAO implements
		RegionRateBenchMarkDiscountDAO {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(RegionRateBenchMarkDiscountDAOImpl.class);

	@Override
	public boolean saveOrUpdateRateBenchMark(
			List<RegionRateBenchMarkDiscountDO> discountDO)
			throws CGBusinessException, CGSystemException {
		LOGGER.info("RegionRateBenchMarkDiscountDAOImpl :: saveOrUpdateRateBenchMark() :: START --------> ::::::");
		boolean isRBMCreated = Boolean.FALSE;

		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			for (RegionRateBenchMarkDiscountDO DO : discountDO) {
				session.merge(DO);
			}
			tx.commit();
		} catch (Exception e) {
			if (!StringUtil.isNull(tx))
				tx.rollback();
			LOGGER.error("Exception Occured in::RegionRateBenchMarkDiscountDAOImpl::saveOrUpdateRateBenchMark :: "
					+ e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.info("RegionRateBenchMarkDiscountDAOImpl :: saveOrUpdateRateBenchMark() :: End --------> ::::::");
		return isRBMCreated;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RateIndustryCategoryDO> getRateIndustryCategoryList()
			throws CGSystemException {
		LOGGER.info("RegionRateBenchMarkDiscountDAOImpl :: getRateIndustryCategoryList() :: Start --------> ::::::");

		Session session = null;
		List<RateIndustryCategoryDO> rateIndustryCategoryDOList = null;
		Criteria criteria = null;
		try {
			session = createSession();
			criteria = session.createCriteria(RateIndustryCategoryDO.class,
					"rateIndustryCategoryDO");
			criteria.addOrder(Order
					.asc("rateIndustryCategoryDO.rateIndustryCategoryId"));
			rateIndustryCategoryDOList = criteria.list();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : RegionRateBenchMarkDiscountDAOImpl.getRateIndustryCategoryList", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.info("RegionRateBenchMarkDiscountDAOImpl :: getRateIndustryCategoryList() :: End --------> ::::::");
		return rateIndustryCategoryDOList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RegionRateBenchMarkDiscountDO> getDiscountDetails(
			RegionRateBenchMarkDiscountTO rbmdTO) throws CGBusinessException,
			CGSystemException {

		List<RegionRateBenchMarkDiscountDO> rbmdDO = null;
		try {
			LOGGER.info("RegionRateBenchMarkDiscountDAOImpl :: getDiscountDetails() :: START --------> ::::::");
			String[] paramNames = { RateBenchMarkDiscountConstants.INDUSTRY_CATEGORY_ID };
			Object[] values = { rbmdTO.getIndustryCategory() };
			rbmdDO = getHibernateTemplate().findByNamedQueryAndNamedParam(
					RateBenchMarkDiscountConstants.QRY_GET_DISCOUNT_DTLS,
					paramNames, values);
		} catch (Exception e) {
			LOGGER.error("RegionRateBenchMarkDiscountDAOImpl :: getDiscountDetails()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.info("RegionRateBenchMarkDiscountDAOImpl :: getDiscountDetails() :: START --------> ::::::");
		return rbmdDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RegionRateBenchMarkDiscountDO> getDiscountDetailsbyRegionId(
			Integer regionId) throws CGSystemException {
		Session session = null;
		 List<RegionRateBenchMarkDiscountDO> discountDOList = null;
		 Criteria criteria=null;
			try{
				LOGGER.info("RegionRateBenchMarkDiscountDAOImpl :: getDiscountDetailsbyRegionId() :: START --------> ::::::");
				session = createSession();
				criteria = session.createCriteria(RegionRateBenchMarkDiscountDO.class,"discount");
				
				if(!StringUtil.isEmptyInteger(regionId)){
					criteria.add(Restrictions.eq("discount.region", regionId));
				}
				discountDOList = criteria.list(); 
				
			}
			catch (Exception e) {
				LOGGER.error("ERROR : RegionRateBenchMarkDiscountDAOImpl.getDiscountDetailsbyRegionId", e);
				throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}	
			LOGGER.info("RegionRateBenchMarkDiscountDAOImpl :: getDiscountDetailsbyRegionId() :: END --------> ::::::");
		return discountDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RegionRateBenchMarkDiscountDO> checkEmpIdRegionalApprover(
			Integer empId) throws CGSystemException {
		
		List<RegionRateBenchMarkDiscountDO> rbmdDOList = null;
		try {
			LOGGER.info("RegionRateBenchMarkDiscountDAOImpl :: checkEmpIdRegionalApprover() :: START --------> ::::::");
			String[] paramNames = { RateBenchMarkDiscountConstants.EMPLOYEE_ID };
			Object[] values = { empId };
			rbmdDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					RateBenchMarkDiscountConstants.QRY_REGION_RATE_BENCHMARK_DISCOUNT_BY_EMP_ID,
					paramNames, values);
		} catch (Exception e) {
			LOGGER.error("RegionRateBenchMarkDiscountDAOImpl :: checkEmpIdRegionalApprover()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.info("RegionRateBenchMarkDiscountDAOImpl :: checkEmpIdRegionalApprover() :: END --------> ::::::");
		return rbmdDOList;
	}

}
