package src.com.dtdc.mdbDao.manifest;

/**
 * 
 */

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.dtdc.domain.expense.MiscExpenseDO;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.manifest.ManifestTypeDO;
import com.dtdc.domain.transaction.delivery.DeliveryDO;

// TODO: Auto-generated Javadoc
/**
 * The Interface PODMDBDAO.
 *
 * @author mohammal
 */
public interface PODMDBDAO {
	
	/**
	 * Save pod manifest.
	 *
	 * @param manifestDo the manifest do
	 */
	public void savePodManifest(ManifestDO manifestDo); 
	
	/**
	 * Update pod details.
	 *
	 * @param podDetails the pod details
	 */
	public void updatePODDetails(DeliveryDO podDetails);
	
	/**
	 * Gets the pod manifest type.
	 *
	 * @return the pod manifest type
	 */
	public ManifestTypeDO getPodManifestType();
	
	/**
	 * Gets the delivery by cn number and product id.
	 *
	 * @param consignNumber the consign number
	 * @param productId the product id
	 * @return the delivery by cn number and product id
	 */
	public DeliveryDO getDeliveryByCnNumberAndProductId(String consignNumber, int productId);
	
	/**
	 * Gets the incoming pod manifest by mf number.
	 *
	 * @param mfNumber the mf number
	 * @return the incoming pod manifest by mf number
	 */
	public List<ManifestDO> getIncomingPODManifestByMfNumber(String mfNumber);
	
	/**
	 * Gets the outgoing pod manifest by mf number.
	 *
	 * @param mfNumber the mf number
	 * @return the outgoing pod manifest by mf number
	 */
	public List<ManifestDO> getOutgoingPODManifestByMfNumber(String mfNumber);
	
	//** Add By Jay 03-Oct-2011 --**/
	/**
	 * Gets the incoming pod manifest by consig number.
	 *
	 * @param consigNumber the consig number
	 * @param podMfNumber the pod mf number
	 * @return the incoming pod manifest by consig number
	 */
	public List<ManifestDO> getIncomingPODManifestByConsigNumber(String consigNumber,String podMfNumber);
	
	/**
	 * Save incoming pod manifest.
	 *
	 * @param manifestDo the manifest do
	 */
	public void saveIncomingPodManifest(ManifestDO manifestDo);
	
	/**
	 * Save misc exp.
	 *
	 * @param miscExpenseDOList the misc expense do list
	 * @throws CGSystemException the cG system exception
	 */
	public void saveMiscExp(List<MiscExpenseDO> miscExpenseDOList) throws CGSystemException ;
	
	/**
	 * Gets the outgoing pod manifest by consig number.
	 *
	 * @param consigNumber the consig number
	 * @param podMfNumber the pod mf number
	 * @return the outgoing pod manifest by consig number
	 */
	public List<ManifestDO> getOutgoingPODManifestByConsigNumber(String consigNumber,String podMfNumber);
	
	/**
	 * Save penaltycharge for unstamped pod.
	 *
	 * @param miscExpenseDOList the misc expense do list
	 * @throws CGSystemException the cG system exception
	 */
	public void savePenaltychargeForUnstampedPOD(
			List<MiscExpenseDO> miscExpenseDOList) throws CGSystemException;
	
	
}
