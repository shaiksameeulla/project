package src.com.dtdc.mdbDao.purchase;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.dtdc.domain.purchase.GoodsIssueDO;

// TODO: Auto-generated Javadoc
/**
 * The Class GoodsIssueMBDDAOImpl.
 */
public class GoodsIssueMBDDAOImpl extends HibernateDaoSupport implements GoodsIssueMBDDAO {
	/** The logger. */
	private Logger logger = LoggerFactory.getLogger(GoodsIssueMBDDAOImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dtdc.ng.dao.purchase.GoodsIssueDAO#saveGoodsIssueDetails(java.util
	 * .List)
	 */
	@Override
	public boolean saveGoodsIssueDetails(List<GoodsIssueDO> goodsIssueDOList) throws CGBusinessException {
		boolean result=false;
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		Session session=null;
		for (GoodsIssueDO requestGoodsIssueDO : goodsIssueDOList) {
				// Saving the quotation into the request for quotation table
			session = hibernateTemplate.getSessionFactory().openSession();
			Transaction tx=session.beginTransaction();
			/*String itemCode=requestGoodsIssueDO.getItemCode();
			Integer issuedQuantity=requestGoodsIssueDO.getIssuedQuantity();
			Query query = session.createQuery(hql);
			query.setString(PurchaseConstants.ITEM_CODE, itemCode);			
			query.setInteger(PurchaseConstants.ISSUED_QUANTITY, issuedQuantity);
			query.executeUpdate();*/
			session.saveOrUpdate(requestGoodsIssueDO);
			tx.commit();
			session.flush();
			session.close();
			result=true;										
				
			}
		return result;
		}
}


