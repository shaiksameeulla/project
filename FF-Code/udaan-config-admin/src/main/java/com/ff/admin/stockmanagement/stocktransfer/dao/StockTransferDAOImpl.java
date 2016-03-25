/**
 * 
 */
package com.ff.admin.stockmanagement.stocktransfer.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.stockmanagement.common.constants.StockCommonConstants;
import com.ff.domain.stockmanagement.operations.transfer.StockTransferDO;
import com.ff.to.stockmanagement.StockUpdateInputTO;
import com.ff.to.stockmanagement.stocktransfer.StockTransferTO;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.stockmanagement.util.StockUtility;

/**
 * The Class StockTransferDAOImpl.
 *
 * @author mohammes
 */
public class StockTransferDAOImpl extends CGBaseDAO implements StockTransferDAO {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(StockTransferDAOImpl.class);
	
	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stocktransfer.dao.StockTransferDAO#findStockTransferDtls(com.ff.to.stockmanagement.stocktransfer.StockTransferTO)
	 */
	@Override
	public StockTransferDO findStockTransferDtls(StockTransferTO to)
			throws CGSystemException {
		List<StockTransferDO> transferList = null;
		String params[] = {StockCommonConstants.QRY_PARAM_TRANSFER_NUMBER,StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,StockCommonConstants.QRY_PARAM_OFFICEID};
		Object values[] = {to.getStockTransferNumber(),StockCommonConstants.ACTIVE_STATUS,to.getLoggedInOfficeId()};
		transferList = getHibernateTemplate().findByNamedQueryAndNamedParam(StockCommonConstants.QRY_FIND_STOCK_TRANSFER_DTLS, params, values);
		return !StringUtil.isEmptyList(transferList)? transferList.get(0) :null;
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stocktransfer.dao.StockTransferDAO#saveStockTransferDtls(com.ff.domain.stockmanagement.operations.transfer.StockTransferDO)
	 */
	@Override
	public Boolean saveStockTransferDtls(StockTransferDO domainEntity)
			throws CGSystemException {
		
		Session session = null;
		Transaction tx=null;
		try {
			session = createSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(domainEntity);
			updateStockForTransfer(domainEntity, session);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			LOGGER.error("StockTransferDAOImpl ::saveStockTransferDtls ::exception",e);
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
		
		return Boolean.TRUE;
	}
	
	/**
	 * Update stock for transfer.
	 *
	 * @param domainEntity the domain entity
	 * @param session the session
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	private Boolean updateStockForTransfer(StockTransferDO domainEntity,
			Session session) throws CGSystemException {
		Boolean result=null;
		StockUpdateInputTO stockUpdateFromTo=prepareStockUpdateTOForStockTransferFrom(domainEntity);
		result= StockUtility.updateUniversalStock(session, stockUpdateFromTo);
		LOGGER.debug("StockTransferDAOImpl::updateStockForTransfer:: calling StockUtility.updateUniversalStock(TransferFrom) result:"+result);
		StockUpdateInputTO stockUpdateTOTo=prepareStockUpdateTOForStockTransferTO(domainEntity);
		result= StockUtility.updateUniversalStock(session, stockUpdateTOTo);
		LOGGER.debug("StockTransferDAOImpl::updateStockForTransfer:: calling StockUtility.updateUniversalStock(TransferTO) result:"+result);
		return result;
	}
	
	/**
	 * Prepare stock update to for stock transfer from.
	 *
	 * @param domainEntity the domain entity
	 * @return the stock update input to
	 */
	private StockUpdateInputTO prepareStockUpdateTOForStockTransferFrom(StockTransferDO domainEntity){
		StockUpdateInputTO stockUpdateTo=null;
		if(!StringUtil.isStringEmpty(domainEntity.getTransferFromType())){
			stockUpdateTo = prepareStockUpdateTO(domainEntity);
			
		}
		if(!StringUtil.isNull(stockUpdateTo)){
			switch(domainEntity.getTransferFromType()){
			case UdaanCommonConstants.ISSUED_TO_BA:
				stockUpdateTo.setPartyTypeId(domainEntity.getTransferFromBaDO().getCustomerId());
				break;
			case UdaanCommonConstants.ISSUED_TO_EMPLOYEE:
				stockUpdateTo.setPartyTypeId(domainEntity.getTransferFromEmpDO().getEmployeeId());
				break;
			case UdaanCommonConstants.ISSUED_TO_CUSTOMER:
				stockUpdateTo.setPartyTypeId(domainEntity.getTransferFromCustomerDO().getCustomerId());
				break;

			}
			stockUpdateTo.setIsDecrease(true);
		}
		return stockUpdateTo;
	}
	
	/**
	 * Prepare stock update to for stock transfer to.
	 *
	 * @param domainEntity the domain entity
	 * @return the stock update input to
	 */
	private StockUpdateInputTO prepareStockUpdateTOForStockTransferTO(StockTransferDO domainEntity){
		StockUpdateInputTO stockUpdateTo=null;
		if(!StringUtil.isStringEmpty(domainEntity.getTransferTOType())){
			stockUpdateTo = prepareStockUpdateTO(domainEntity);
			stockUpdateTo.setPartyType(domainEntity.getTransferTOType());
		}
		if(!StringUtil.isNull(stockUpdateTo)){
			switch(domainEntity.getTransferTOType()){
			case UdaanCommonConstants.ISSUED_TO_BA:
				stockUpdateTo.setPartyTypeId(domainEntity.getTransferTOBaDO().getCustomerId());
				break;
			case UdaanCommonConstants.ISSUED_TO_BRANCH:
				stockUpdateTo.setPartyTypeId(domainEntity.getTransferTOOfficeDO().getOfficeId());
				break;
			case UdaanCommonConstants.ISSUED_TO_EMPLOYEE:
				stockUpdateTo.setPartyTypeId(domainEntity.getTransferTOEmpDO().getEmployeeId());
				break;
			case UdaanCommonConstants.ISSUED_TO_CUSTOMER:
				stockUpdateTo.setPartyTypeId(domainEntity.getTransferTOCustomerDO().getCustomerId());
				break;
			}
			stockUpdateTo.setIsDecrease(false);
		}
		return stockUpdateTo;
	}

	/**
	 * Prepare stock update to.
	 *
	 * @param domainEntity the domain entity
	 * @return the stock update input to
	 */
	private StockUpdateInputTO prepareStockUpdateTO(StockTransferDO domainEntity) {
		StockUpdateInputTO stockUpdateTo;
		stockUpdateTo = new StockUpdateInputTO();
		stockUpdateTo.setPartyType(domainEntity.getTransferFromType());
		stockUpdateTo.setItemId(domainEntity.getItemDO().getItemId());
		stockUpdateTo.setQuantity(domainEntity.getTransferQuantity());
		return stockUpdateTo;
	}
	
	
	
	
	

}
