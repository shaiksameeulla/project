package src.com.dtdc.mdbDao.purchase;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.dtdc.domain.purchase.PurchaseReqDO;
import com.dtdc.domain.purchase.RequestForQuotationDO;

// TODO: Auto-generated Javadoc
/**
 * The Class PurchaseMBDDAOImpl.
 */
public class PurchaseMBDDAOImpl extends HibernateDaoSupport implements PurchaseMBDDAO {
	
	/** The logger. */
	private Logger logger = LoggerFactory
			.getLogger(PurchaseMBDDAOImpl.class);

	/* (non-Javadoc)
	 * @see src.com.dtdc.mdbDao.purchase.PurchaseMBDDAO#saveRequisition(java.util.List)
	 */
	public Boolean saveRequisition(List<PurchaseReqDO> purchaseReqDOList) throws Exception {
		
		boolean saveRequisitionStatus = false;
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		
		try {
			for (PurchaseReqDO purchaseReqDO : purchaseReqDOList) {
				hibernateTemplate.saveOrUpdate(purchaseReqDO);
				hibernateTemplate.flush();
				saveRequisitionStatus = true;
			}
		} catch (Exception obj) {
			logger.error("PurchaseMBDDAOImpl::saveRequisition::Exception occured:"
					+obj.getMessage());
			 throw new CGBusinessException("Error while saving PR records");
		//	throw new Exception();
		}
		return saveRequisitionStatus;
	}
	
	
	/* (non-Javadoc)
	 * @see src.com.dtdc.mdbDao.purchase.PurchaseMBDDAO#saveReqForQuotations(java.util.List)
	 */
	public Boolean saveReqForQuotations(List<RequestForQuotationDO> requestForQuotationDOList) throws Exception {
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		boolean saveRFQStatus = false;
		
		try {
			for (RequestForQuotationDO requestForQuotationDO : requestForQuotationDOList) {
				//Saving the quotation into the request for quotation table
				hibernateTemplate.saveOrUpdate(requestForQuotationDO);
				hibernateTemplate.flush();
				//end
				
				//Saving the quantity and deliverydate in the purchase requisition table
				PurchaseReqDO reqDO= requestForQuotationDO.getPurchaseReq();
				Integer purreqid = reqDO.getPurchase_req_id();
				PurchaseReqDO reqDO1 = hibernateTemplate.get(PurchaseReqDO.class, purreqid);
				/*reqDO1.setQuantity(reqDO.getQuantity());
				reqDO1.setDeliveryDate(reqDO.getDeliveryDate());*/
				hibernateTemplate.saveOrUpdate(reqDO1);
				hibernateTemplate.flush();
				//End
				saveRFQStatus = true;
				
				
			}
		} catch (Exception obj) {
			logger.error("PurchaseMBDDAOImpl::saveReqForQuotations::Exception occured:"
					+obj.getMessage());
			 throw new CGBusinessException("Error while saving RFQ records");
		}
		
		return saveRFQStatus;
	}
}


