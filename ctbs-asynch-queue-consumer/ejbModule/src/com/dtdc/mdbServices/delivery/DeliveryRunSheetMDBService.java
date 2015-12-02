package src.com.dtdc.mdbServices.delivery;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.dtdc.to.delivery.DeliveryRunTO;
import com.dtdc.to.delivery.DeliveryTO;
// TODO: Auto-generated Javadoc

/**
 * Service class for usecase 71 and 72.
 *
 * @author vishnp
 */
public interface DeliveryRunSheetMDBService {
	
	/**
	 * method to update the DRS details.
	 *
	 * @param cgBaseTO the cg base to
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	void updateDRS(CGBaseTO cgBaseTO) throws CGSystemException, CGBusinessException;

	/**
	 * method to update the DRS details.
	 *
	 * @param deliveryRunSheetTO the delivery run sheet to
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	void updateDRS(DeliveryRunTO deliveryRunSheetTO) throws CGSystemException, CGBusinessException;
	
	/**
	 * Save drs.
	 *
	 * @param cgBaseTO the cg base to
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	void saveDrs(CGBaseTO cgBaseTO) throws CGSystemException, CGBusinessException;
	
	/**
	 * Save drs.
	 *
	 * @param deliveryToList the delivery to list
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	void saveDrs(List<DeliveryTO> deliveryToList) throws CGSystemException, CGBusinessException;
	
	/**
	 * Save n drs.
	 *
	 * @param cgBaseTO the cg base to
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	void saveNDrs(CGBaseTO cgBaseTO) throws CGSystemException, CGBusinessException;
	
	/**
	 * Save n drs.
	 *
	 * @param deliveryToList the delivery to list
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	void saveNDrs(List<DeliveryTO> deliveryToList) throws CGSystemException, CGBusinessException; 
}
