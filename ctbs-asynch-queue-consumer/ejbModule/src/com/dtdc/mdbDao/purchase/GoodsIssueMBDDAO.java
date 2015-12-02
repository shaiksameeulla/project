/**
 * 
 */
package src.com.dtdc.mdbDao.purchase;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.dtdc.domain.purchase.GoodsIssueDO;

// TODO: Auto-generated Javadoc
/**
 * The Interface GoodsIssueMBDDAO.
 *
 * @author joaugust
 * This interface defines the methods for the Purchase related CTBS functions
 */
public interface GoodsIssueMBDDAO {
	
	/**
	 * Save goods issue details.
	 *
	 * @param goodsIssueDOList the goods issue do list
	 * @return true, if successful
	 * @throws CGBusinessException the cG business exception
	 */
	public boolean saveGoodsIssueDetails(List<GoodsIssueDO> goodsIssueDOList) throws CGBusinessException;
}
