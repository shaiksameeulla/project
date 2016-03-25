package com.ff.admin.stockmanagement.stockreduction.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.admin.stockmanagement.common.constants.StockCommonConstants;
import com.ff.domain.booking.StockBookingDO;
import com.ff.domain.manifest.ComailDO;
import com.ff.domain.manifest.StockManifestDO;
import com.ff.domain.stockmanagement.operations.reduction.SAPStockConsolidationDO;
import com.ff.domain.stockmanagement.operations.reduction.StockConsumptionLevelDO;
import com.ff.domain.stockmanagement.wrapper.StockHolderWrapperDO;
import com.ff.to.stockmanagement.StockReductionInputTO;
import com.ff.universe.manifest.constant.ManifestUniversalConstants;
import com.ff.universe.stockmanagement.constant.StockUniveralConstants;

/**
 * @author hkansagr
 * 
 */
public class StockReductionDAOImpl extends CGBaseDAO implements
		StockReductionDAO {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(StockReductionDAOImpl.class);

	
	
	@Override
	public List<StockManifestDO> getManifestDetailsForStock(
			StockReductionInputTO to) throws CGSystemException {
		LOGGER.trace("StockReductionDAOImpl :: getManifestDetailsForStock() :: START");
		List<StockManifestDO> results = null;
		Session session=createSession();
		try {
			String queryName = StockCommonConstants.QRY_GET_MANIFEST_STOCK_REDUCTION_DTLS;
			/*String[] paramNames = { StockCommonConstants.QRY_PARAM_FROM_DATE,
					StockCommonConstants.QRY_PARAM_TO_DATE,
					ManifestUniversalConstants.MANIFEST_DIRECTION };
			Object[] values = { to.getFromDate(), to.getToDate(),
					ManifestUniversalConstants.MANIFEST_DIRECTION_OUT };
			results = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, paramNames, values);*/
			Query qry = session.getNamedQuery(queryName);
			qry.setTimestamp(StockCommonConstants.QRY_PARAM_FROM_DATE, to.getFromDate());
			qry.setTimestamp(StockCommonConstants.QRY_PARAM_TO_DATE, to.getToDate());
			qry.setString(ManifestUniversalConstants.MANIFEST_DIRECTION,ManifestUniversalConstants.MANIFEST_DIRECTION_OUT);
			qry.setMaxResults(100);//FIXME
			results = qry.list();
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs StockReductionDAOImpl :: getManifestDetailsForStock() ::",
					e);
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
		LOGGER.trace("StockReductionDAOImpl :: getManifestDetailsForStock() :: END");
		return results;
	}

	@Override
	public boolean saveOrUpdateStockConsumptionLevel(
			List<StockConsumptionLevelDO> stockLevelDOs)
			throws CGSystemException {
		LOGGER.trace("StockReductionDAOImpl :: saveOrUpdateStockConsumptionLevel() :: START");
		boolean result = Boolean.FALSE;
		try {
			//getHibernateTemplate().saveOrUpdateAll(stockLevelDOs);
			for(StockConsumptionLevelDO consumptionLevelDo:stockLevelDOs){
				modifyStockConsumptionLevel(consumptionLevelDo);
				updateStockConsumptionForManifestConsignment(consumptionLevelDo);

			}
			result = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs StockReductionDAOImpl :: saveOrUpdateStockConsumptionLevel() ::",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("StockReductionDAOImpl :: saveOrUpdateStockConsumptionLevel() :: END");
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockBookingDO> getConsgStockReductionDtls(
			StockReductionInputTO to) throws CGSystemException {
		LOGGER.trace("StockReductionDAOImpl :: getConsgStockReductionDtls() :: START");
		List<StockBookingDO> consgObjects = null;
		Session session=createSession();
		try {
			String queryName = StockCommonConstants.QRY_GET_CONSG_STOCK_REDUCTION_DTLS;
			/*String[] paramNames = { StockCommonConstants.QRY_PARAM_FROM_DATE,
					StockCommonConstants.QRY_PARAM_TO_DATE };
			Object[] values = { to.getFromDate(), to.getToDate() };
			consgObjects = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, paramNames,
							values);*/
			Query qry = session.getNamedQuery(queryName);
			qry.setTimestamp(StockCommonConstants.QRY_PARAM_FROM_DATE, to.getFromDate());
			qry.setTimestamp(StockCommonConstants.QRY_PARAM_TO_DATE, to.getToDate());
			qry.setMaxResults(100);//FIXME
			consgObjects = qry.list();
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs StockReductionDAOImpl :: getConsgStockReductionDtls() ::",
					e);
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
		LOGGER.trace("StockReductionDAOImpl :: getConsgStockReductionDtls() :: END");
		return consgObjects;
	}
	
	@Override
	public List<ComailDO> getComailStockReductionDtls(
			StockReductionInputTO to) throws CGSystemException {
		LOGGER.trace("StockReductionDAOImpl :: getComailStockReductionDtls() :: START");
		List<ComailDO> comailList = null;
		Session session=createSession();
		try {
			String queryName = StockCommonConstants.QRY_GET_COMAIL_STOCK_REDUCTION_DTLS;
			
			Query qry = session.getNamedQuery(queryName);
			qry.setTimestamp(StockCommonConstants.QRY_PARAM_FROM_DATE, to.getFromDate());
			qry.setTimestamp(StockCommonConstants.QRY_PARAM_TO_DATE, to.getToDate());
			qry.setMaxResults(100);//FIXME
			comailList = qry.list();
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs StockReductionDAOImpl :: getComailStockReductionDtls() ::",
					e);
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
		LOGGER.trace("StockReductionDAOImpl :: getComailStockReductionDtls() :: END");
		return comailList;
	}

	@Override
	public boolean saveStockConsumptionLevel(
			StockConsumptionLevelDO stockConsumptionLevelDO)
			throws CGSystemException {
		LOGGER.trace("StockReductionDAOImpl :: saveStockConsumptionLevel() :: START");
		boolean result = Boolean.FALSE;
		try {
			modifyStockConsumptionLevel(stockConsumptionLevelDO);
			updateStockConsumptionForManifestConsignment(stockConsumptionLevelDO);
			result = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs StockReductionDAOImpl :: saveStockConsumptionLevel() ::",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("StockReductionDAOImpl :: saveStockConsumptionLevel() :: END");
		return result;
	}

	@Override
	public boolean updateStockConsumptionLevelList(
			List<StockConsumptionLevelDO> stockLevelDOs)
			throws CGSystemException {
		LOGGER.trace("StockReductionDAOImpl :: saveStockConsumptionLevel() :: START");
		boolean result = Boolean.FALSE;
		try {
			getHibernateTemplate().saveOrUpdateAll(stockLevelDOs);
			
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs StockReductionDAOImpl :: saveStockConsumptionLevel() ::",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("StockReductionDAOImpl :: saveStockConsumptionLevel() :: END");
		return result;
	}

	private void modifyStockConsumptionLevel(
			StockConsumptionLevelDO stockConsumptionLevelDO) {
		getHibernateTemplate().saveOrUpdate(stockConsumptionLevelDO);
	}

	@Override
	public boolean updateStockManifestAndBookingFlag(
			StockConsumptionLevelDO stockConsumptionLevelDO)
			 {
		LOGGER.trace("StockReductionDAOImpl :: updateStockManifestAndBookingFlag() :: START");
		boolean result = Boolean.FALSE;
		try {
			updateStockConsumptionForManifestConsignment(stockConsumptionLevelDO);
			result = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs StockReductionDAOImpl :: updateStockManifestAndBookingFlag() ::",
					e);
			//throw new CGSystemException(e);
		}
		LOGGER.trace("StockReductionDAOImpl :: updateStockManifestAndBookingFlag() :: END");
		return result;
	}
	private  void updateStockConsumptionForManifestConsignment(
			StockConsumptionLevelDO stockConsumptionLevelDO) {
		if(stockConsumptionLevelDO.getStockManifestDO()!=null){
			getHibernateTemplate().update(stockConsumptionLevelDO.getStockManifestDO());

		}
		if(stockConsumptionLevelDO.getStockBookingDO()!=null){
			getHibernateTemplate().update(stockConsumptionLevelDO.getStockBookingDO());
		}
		if(stockConsumptionLevelDO.getStockComailDO()!=null){
			getHibernateTemplate().update(stockConsumptionLevelDO.getStockComailDO());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockConsumptionLevelDO> getStockConsolidationDtls()
			throws CGSystemException {
		LOGGER.trace("StockReductionDAOImpl :: getStockConsolidationDtls() :: START");
		List<StockConsumptionLevelDO> stockLevelDOs = null;
		try {
			String queryName = StockCommonConstants.QRY_GET_STOCK_CONSOLIDATION_DTLS;
			stockLevelDOs = getHibernateTemplate().findByNamedQuery(queryName);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs StockReductionDAOImpl :: getStockConsolidationDtls() ::",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("StockReductionDAOImpl :: getStockConsolidationDtls() :: END");
		return stockLevelDOs;
	}
	
	@Override
	public List<String> getChildConsignmentDtls(String parentConsg)
			throws CGSystemException {
		LOGGER.trace("StockReductionDAOImpl :: getStockConsolidationDtls() :: START");
		List<String> childCnList = null;
		try {
			String queryName = StockCommonConstants.QRY_GET_CHILD_CONS_LIST_BY_PARENT_CN;
			childCnList = getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, "consgNo", parentConsg);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs StockReductionDAOImpl :: getStockConsolidationDtls() ::",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("StockReductionDAOImpl :: getStockConsolidationDtls() :: END");
		return childCnList;
	}

	@Override
	public boolean saveOrUpdateSAPStockConsolidationDtls(
			List<SAPStockConsolidationDO> sapStockConsolidationDOs)
			throws CGSystemException {
		LOGGER.trace("StockReductionDAOImpl :: saveOrUpdateSAPStockConsolidationDtls() :: START");
		boolean result = Boolean.FALSE;
		try {
			getHibernateTemplate().saveOrUpdateAll(sapStockConsolidationDOs);
			result = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs StockReductionDAOImpl :: saveOrUpdateSAPStockConsolidationDtls() ::",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("StockReductionDAOImpl :: saveOrUpdateSAPStockConsolidationDtls() :: END");
		return result;
	}

	@Override
	public boolean saveStockConsolidationDtls(
			SAPStockConsolidationDO stckConsolidationDO)
			throws CGSystemException {
		LOGGER.trace("StockReductionDAOImpl :: saveStockConsolidationDtls() :: START");
		boolean result = Boolean.FALSE;
		try {
			getHibernateTemplate().saveOrUpdate(stckConsolidationDO);
			result = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs StockReductionDAOImpl :: saveStockConsolidationDtls() ::",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("StockReductionDAOImpl :: saveStockConsolidationDtls() :: END");
		return result;
	}
	
	@Override
	public List<StockHolderWrapperDO> getStockHoldingDetails(String queryName,String stockNumber,Integer itemId) throws CGSystemException{
			Session session=createSession();
			List<StockHolderWrapperDO> transactionDate=null;
			try {
				Query qry = session.getNamedQuery(queryName);
				qry.setInteger(StockUniveralConstants.QRY_PARAM_ITEM_ID, itemId);
				qry.setString(StockUniveralConstants.QRY_PARAM_TRANSACTION_STATUS, StockUniveralConstants.TRANSACTION_STATUS);
				qry.setString(StockUniveralConstants.QRY_PARAM_START_SERIES_NO,stockNumber);
				qry.setMaxResults(1);
				transactionDate = qry.list();
			} catch (Exception e) {
				logger.error("StockReductionDAOImpl::getStockHoldingDetails :: Exception"+e.getLocalizedMessage());
			throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}
			return transactionDate;
		}
	
	

}
