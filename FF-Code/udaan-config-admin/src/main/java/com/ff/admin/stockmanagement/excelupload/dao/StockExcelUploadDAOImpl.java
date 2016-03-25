/**
 * 
 */
package com.ff.admin.stockmanagement.excelupload.dao;

import java.util.List;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.stockmanagement.operations.receipt.StockReceiptDO;
import com.ff.domain.stockmanagement.operations.receipt.StockReceiptItemDtlsDO;
import com.ff.to.stockmanagement.StockUpdateInputTO;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.stockmanagement.util.StockUtility;

/**
 * @author mohammes
 *
 */
public class StockExcelUploadDAOImpl extends CGBaseDAO implements
		StockExcelUploadDAO {
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(StockExcelUploadDAOImpl.class);
	
	
	@Override
	public Boolean saveStockReceiptList(List<StockReceiptDO> receiptDoList)throws CGBusinessException,CGSystemException{
		Boolean result=false;
		LOGGER.debug("StockExcelUploadDAOImpl ::saveStockReceiptList::START ");
		long starttime=System.currentTimeMillis();
		Session session=null;
		try {

			LOGGER.debug("StockExcelUploadDAOImpl::saveStockReceiptList ..Start Time:[ "+starttime+"]");
			session=openTransactionalSession();
			for(StockReceiptDO domainEntity:receiptDoList){
				stockIncreaseAtBranch(domainEntity, session);
				session.save(domainEntity);
			}
		}
		 catch (Exception e) {
				LOGGER.error("StockExcelUploadDAOImpl:::saveStockReceiptList:: Tx Rollbacked ");
				throw new CGSystemException(e);
			} finally {
				closeTransactionalSession(session);
			}
		long endtime=System.currentTimeMillis();
		LOGGER.debug("StockExcelUploadDAOImpl ::saveStockReceiptList::END  ..End Time:[ "+endtime+"] Differece :["+(endtime-starttime)+"]");
		result=true;
		return result;
	}
	
	private void stockIncreaseAtBranch(StockReceiptDO domainEntity,
			Session session) throws CGSystemException {
		LOGGER.trace("StockReceiptDAOImpl ::stockIncreaseAtBranch :: START");
		StockUpdateInputTO stockUpdateTo=prepareStockUpdateTO(domainEntity);
		
		for (StockReceiptItemDtlsDO dtlsDO : domainEntity
				.getStockReceiptItemDtls()) {
			stockUpdateTo.setQuantity(dtlsDO.getReceivedQuantity());
			stockUpdateTo.setItemId(dtlsDO.getItemDO().getItemId());
			boolean status=	StockUtility.updateUniversalStock(session, stockUpdateTo);
			
			LOGGER.debug("StockReceiptDAOImpl:::saveReceiptDtls::stockIncreaseAtBranch:: record status :["+status+"]");
		}
		LOGGER.trace("StockReceiptDAOImpl ::stockIncreaseAtBranch :: END");
	}
	private StockUpdateInputTO prepareStockUpdateTO(StockReceiptDO domainEntity) {
		StockUpdateInputTO stockUpdateTo;
		stockUpdateTo = new StockUpdateInputTO();
		stockUpdateTo.setPartyType(UdaanCommonConstants.ISSUED_TO_BRANCH);
		stockUpdateTo.setPartyTypeId(domainEntity.getReceiptOfficeId().getOfficeId());
		stockUpdateTo.setIsDecrease(false);
		return stockUpdateTo;
	}

}
