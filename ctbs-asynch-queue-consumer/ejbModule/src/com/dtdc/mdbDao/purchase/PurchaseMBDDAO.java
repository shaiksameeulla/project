/**
 * 
 */
package src.com.dtdc.mdbDao.purchase;

import java.util.List;

import com.dtdc.domain.purchase.PurchaseReqDO;
import com.dtdc.domain.purchase.RequestForQuotationDO;

// TODO: Auto-generated Javadoc
/**
 * The Interface PurchaseMBDDAO.
 *
 * @author joaugust
 * This interface defines the methods for the Purchase related CTBS functions
 */
public interface PurchaseMBDDAO {
	
	/**
	 * Save requisition.
	 *
	 * @param purchaseReqDOList the purchase req do list
	 * @return the boolean
	 * @throws Exception the exception
	 */
	public Boolean saveRequisition(List<PurchaseReqDO> purchaseReqDOList) throws Exception;
	
	/**
	 * Save req for quotations.
	 *
	 * @param requestForQuotationDOList the request for quotation do list
	 * @return the boolean
	 * @throws Exception the exception
	 */
	public  Boolean saveReqForQuotations(List<RequestForQuotationDO> requestForQuotationDOList) throws  Exception;
}
