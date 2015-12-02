package src.com.dtdc.mdbServices.purchase.goodsIssue;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.dtdc.to.purchase.goods.GoodsIssueTo;

// TODO: Auto-generated Javadoc
/**
 * The Interface ReqForQuotationService.
 */
public interface GoodsIssueMBDService{
	
	/**
	 * Save goods issue details.
	 *
	 * @param goodsIssueTo the goods issue to
	 * @return true, if successful
	 * @throws CGBusinessException the cG business exception
	 */
	public boolean saveGoodsIssueDetails(GoodsIssueTo goodsIssueTo) throws CGBusinessException;
}
