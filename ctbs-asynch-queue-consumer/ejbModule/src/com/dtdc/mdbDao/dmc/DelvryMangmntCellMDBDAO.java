/**
 * 
 */
package src.com.dtdc.mdbDao.dmc;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.dtdc.domain.transaction.delivery.DeliveryManagementDO;
import com.dtdc.to.dmc.DmcGridTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface DelvryMangmntCellMDBDAO.
 *
 * @author mohammes
 */
public interface DelvryMangmntCellMDBDAO {
	
	/**
	 * Save deliverymanagement info.
	 *
	 * @param deliveryManagementDoList the delivery management do list
	 * @param dmcGridTo the dmc grid to
	 * @return true, if successful
	 * @throws CGSystemException the cG system exception
	 */
	public boolean  saveDeliverymanagementInfo(List<DeliveryManagementDO> deliveryManagementDoList,List<DmcGridTO>	dmcGridTo) throws  CGSystemException;

	/**
	 * Save dmc.
	 *
	 * @param deliveryManagementDoList the delivery management do list
	 * @return true, if successful
	 * @throws CGSystemException the cG system exception
	 */
	boolean saveDMC(List<DeliveryManagementDO> deliveryManagementDoList)
			throws CGSystemException;

}
