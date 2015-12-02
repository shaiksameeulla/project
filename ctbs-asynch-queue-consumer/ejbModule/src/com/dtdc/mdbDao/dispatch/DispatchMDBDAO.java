package src.com.dtdc.mdbDao.dispatch;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.dtdc.domain.dispatch.DispatchBagManifestDO;
import com.dtdc.domain.dispatch.DispatchDO;
import com.dtdc.domain.dispatch.DispatchReceiveBagManifestDO;
import com.dtdc.domain.dispatch.DispatchReceiverDO;
import com.dtdc.domain.login.UserDO;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.master.OTCMasterDO;
import com.dtdc.domain.master.Billing.BillingDO;
import com.dtdc.domain.master.airline.AirportDO;
import com.dtdc.domain.master.coloader.CoLoaderDO;
import com.dtdc.domain.master.employee.EmployeeDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.master.train.TrainDO;
import com.dtdc.domain.master.vehicle.VehicleDO;
import com.dtdc.domain.master.vendor.VendorDO;
import com.dtdc.domain.opmaster.shipment.ModeDO;

// TODO: Auto-generated Javadoc
/**
 * The Interface DispatchMDBDAO.
 */
public interface DispatchMDBDAO {

	/**
	 * Gets the office by code.
	 *
	 * @param code the code
	 * @return the office by code
	 * @throws CGSystemException the cG system exception
	 */
	OfficeDO getOfficeByCode(String code) throws CGSystemException;
	
	/**
	 * Gets the mode.
	 *
	 * @param mId the m id
	 * @return the mode
	 * @throws CGSystemException the cG system exception
	 */
	ModeDO getMode(String mId) throws CGSystemException;
	
	/**
	 * Gets the billing mode.
	 *
	 * @param billingmodeCode the billingmode code
	 * @return the billing mode
	 * @throws CGSystemException the cG system exception
	 */
	BillingDO getBillingMode(String billingmodeCode) throws CGSystemException;
	
	/**
	 * Gets the coloader by code.
	 *
	 * @param code the code
	 * @return the coloader by code
	 * @throws CGSystemException the cG system exception
	 */
	CoLoaderDO getColoaderByCode(String code) throws CGSystemException;
	
	/**
	 * Gets the vendor by code.
	 *
	 * @param code the code
	 * @return the vendor by code
	 * @throws CGSystemException the cG system exception
	 */
	VendorDO getVendorByCode(String code) throws CGSystemException;
	
	/**
	 * Gets the employee by code.
	 *
	 * @param code the code
	 * @return the employee by code
	 * @throws CGSystemException the cG system exception
	 */
	EmployeeDO getEmployeeByCode(String code) throws CGSystemException;

	/**
	 * Gets the vehicle.
	 *
	 * @param vehicleRegNo the vehicle reg no
	 * @return the vehicle
	 * @throws CGSystemException the cG system exception
	 */
	VehicleDO getVehicle(String vehicleRegNo) throws CGSystemException;
	
	/**
	 * Save dispatch.
	 *
	 * @param dispatchDO the dispatch do
	 * @return the dispatch do
	 */
	DispatchDO saveDispatch(DispatchDO dispatchDO);
	
	/**
	 * Gets the manifest by num.
	 *
	 * @param manifestNum the manifest num
	 * @return the manifest by num
	 * @throws CGSystemException the cG system exception
	 */
	ManifestDO getManifestByNum(String manifestNum) throws CGSystemException;
	
	/**
	 * Save update disp manifest list.
	 *
	 * @param dispManifestDos the disp manifest dos
	 */
	void saveUpdateDispManifestList(List<DispatchBagManifestDO> dispManifestDos);
	
	/**
	 * Gets the dispatch by number.
	 *
	 * @param number the number
	 * @return the dispatch by number
	 * @throws CGSystemException the cG system exception
	 */
	DispatchDO getDispatchByNumber(String number) throws CGSystemException;
	
	/**
	 * Gets the dispatch manfst child.
	 *
	 * @param dispatchId the dispatch id
	 * @return the dispatch manfst child
	 * @throws CGSystemException the cG system exception
	 */
	List<DispatchBagManifestDO> getDispatchManfstChild(Integer dispatchId) throws CGSystemException;
	
	/**
	 * Gets the dispatch recieve by number.
	 *
	 * @param dispatchId the dispatch id
	 * @return the dispatch recieve by number
	 * @throws CGSystemException the cG system exception
	 */
	DispatchReceiverDO getDispatchRecieveByNumber(String dispatchId) throws CGSystemException;
	
	/**
	 * Save receiver.
	 *
	 * @param recvDO the recv do
	 * @return the dispatch receiver do
	 */
	DispatchReceiverDO saveReceiver(DispatchReceiverDO recvDO);
	
	/**
	 * Delete disp recv manifest by parent id.
	 *
	 * @param dispatchRecieveId the dispatch recieve id
	 * @throws CGSystemException the cG system exception
	 */
	void deleteDispRecvManifestByParentId(Integer dispatchRecieveId) throws CGSystemException;
	
	/**
	 * Save update disp recv manifest list.
	 *
	 * @param dispRecvManifestDos the disp recv manifest dos
	 */
	void saveUpdateDispRecvManifestList(List<DispatchReceiveBagManifestDO> dispRecvManifestDos);

	/**
	 * Gets the airport by airline number.
	 *
	 * @param airlineNumber the airline number
	 * @return the airport by airline number
	 * @throws CGSystemException the cG system exception
	 */
	AirportDO getAirportByAirlineNumber(String airlineNumber) throws CGSystemException;
	
	/**
	 * Gets the train by train number.
	 *
	 * @param trainNumber the train number
	 * @return the train by train number
	 * @throws CGSystemException the cG system exception
	 */
	TrainDO getTrainByTrainNumber(String trainNumber) throws CGSystemException; 

	/**
	 * Gets the oTC master by otc code.
	 *
	 * @param otcCode the otc code
	 * @return the oTC master by otc code
	 * @throws CGSystemException the cG system exception
	 */
	OTCMasterDO getOTCMasterByOtcCode(String otcCode) throws CGSystemException; 
	
	/**
	 * Gets the user by user code.
	 *
	 * @param userCode the user code
	 * @return the user by user code
	 * @throws CGSystemException the cG system exception
	 */
	UserDO getUserByUserCode(String userCode) throws CGSystemException; 
}
