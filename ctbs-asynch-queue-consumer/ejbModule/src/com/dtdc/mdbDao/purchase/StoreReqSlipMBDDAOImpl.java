package src.com.dtdc.mdbDao.purchase;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.dtdc.domain.purchase.StoreReqSlipDO;

// TODO: Auto-generated Javadoc
/**
 * The Class StoreReqSlipMBDDAOImpl.
 */
public class StoreReqSlipMBDDAOImpl extends HibernateDaoSupport implements StoreReqSlipMBDDAO {

	
	/*
	 * Saving into Quotation and Requisition tables
	 * @see com.dtdc.ng.dao.purchase.PurchaseDAO#saveReqForQuotations(java.util.List)
	 */
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.purchase.StoreReqSlipMBDDAO#saveSTOdetails(List)
	 */
	@Override
	public Boolean saveSTOdetails(List<StoreReqSlipDO> storeReqSlipDOList) throws CGSystemException {
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		
		Boolean flag=false;
		
		try {
			for (StoreReqSlipDO storeReqSlipDO : storeReqSlipDOList) {
				//Saving the quotation into the request for quotation table
				hibernateTemplate.saveOrUpdate(storeReqSlipDO);
				flag=true;
				//end
			}
			hibernateTemplate.flush();
		} catch (Exception obj) {
			logger.error("StoreReqSlipMBDDAOImpl::saveSTOdetails::Exception occured:"
					+obj.getMessage());
			throw new CGSystemException(obj);
		}
		
		return flag;
	}
}


