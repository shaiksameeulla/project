package src.com.dtdc.mdbDao.dispatch;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

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

/**
 * The Class DispatchMDBDAOImpl.
 */
public class DispatchMDBDAOImpl extends HibernateDaoSupport implements DispatchMDBDAO {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
	.getLogger(DispatchMDBDAOImpl.class);

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.dispatch.DispatchMDBDAO#getOfficeByCode(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public OfficeDO getOfficeByCode(String code) throws CGSystemException{
		Session session=null;
		try {
			session=getHibernateTemplate().getSessionFactory().openSession();
			Criteria crit = session.createCriteria(OfficeDO.class);
			crit.add(Restrictions.eq("officeCode", code));
			List<OfficeDO> ofcDo=crit.list();
			return ofcDo.get(0);
		} catch (Exception e) {
			logger.error("DispatchMDBDAOImpl::getOfficeByCode occured:"
					+e.getMessage());
			LOGGER.error("Error Occured in DispatchMDBDAOImpl::getOfficeByCode"+e.getCause());

			throw new CGSystemException(e);
		}finally{
			if(session!=null){
				session.flush();
				session.close();
			}
		}
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.dispatch.DispatchMDBDAO#getMode(String)
	 */
	@Override
	public ModeDO getMode(String mCode) throws CGSystemException{
		Session session=null;
		try {
			session=getHibernateTemplate().getSessionFactory().openSession();
			Criteria crit = session.createCriteria(ModeDO.class);
			crit.add(Restrictions.eq("modeCode", mCode));
			ModeDO emp=(ModeDO) crit.uniqueResult();
			return emp;
		} catch (Exception e) {
			logger.error("DispatchMDBDAOImpl::getMode occured:"
					+e.getMessage());
			LOGGER.error("Error Occured in DispatchMDBDAOImpl::getMode"+e.getCause());

			throw new CGSystemException(e);
		}finally{
			if(session!=null){
				session.flush();
				session.close();
			}
		}
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.dispatch.DispatchMDBDAO#getBillingMode(String)
	 */
	@Override
	public BillingDO getBillingMode(String billCode) throws CGSystemException{
		Session session=null;
		try {
			session=getHibernateTemplate().getSessionFactory().openSession();
			Criteria crit = session.createCriteria(BillingDO.class);
			crit.add(Restrictions.eq("billingCode", billCode));
			BillingDO emp=(BillingDO) crit.uniqueResult();
			return emp;
		} catch (Exception e) {
			logger.error("DispatchMDBDAOImpl::getBillingMode occured:"
					+e.getMessage());
			LOGGER.error("Error Occured in DispatchMDBDAOImpl::getBillingMode"+e.getCause());

			throw new CGSystemException(e);
		}finally{
			if(session!=null){
				session.flush();
				session.close();
			}
		}
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.dispatch.DispatchMDBDAO#getColoaderByCode(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public CoLoaderDO getColoaderByCode(String code) throws CGSystemException{
		Session session=null;
		try {
			session=getHibernateTemplate().getSessionFactory().openSession();
			Criteria crit = session.createCriteria(CoLoaderDO.class);
			crit.add(Restrictions.eq("loaderCode", code));
			List<CoLoaderDO> colDo=crit.list();
			return colDo.get(0);
		} catch (Exception e) {
			logger.error("DispatchMDBDAOImpl::getColoaderByCode occured:"
					+e.getMessage());
			LOGGER.error("Error Occured in DispatchMDBDAOImpl::getColoaderByCode"+e.getCause());

			throw new CGSystemException(e);
		}finally{
			if(session!=null){
				session.flush();
				session.close();
			}
		}
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.dispatch.DispatchMDBDAO#getVendorByCode(String)
	 */
	@Override
	public VendorDO getVendorByCode(String code) throws CGSystemException{
		Session session=null;
		try {
			session=getHibernateTemplate().getSessionFactory().openSession();
			Criteria crit = session.createCriteria(VendorDO.class);
			crit.add(Restrictions.eq("vendorCode", code));
			VendorDO vnd=(VendorDO) crit.uniqueResult();
			return vnd;
		} catch (Exception e) {
			logger.error("DispatchMDBDAOImpl::getVendorByCode occured:"
					+e.getMessage());
			LOGGER.error("Error Occured in DispatchMDBDAOImpl::getVendorByCode"+e.getCause());

			throw new CGSystemException(e);
		}finally{
			if(session!=null){
				session.flush();
				session.close();
			}
		}
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.dispatch.DispatchMDBDAO#getEmployeeByCode(String)
	 */
	@Override
	public EmployeeDO getEmployeeByCode(String code) throws CGSystemException{
		Session session=null;
		try {
			session=getHibernateTemplate().getSessionFactory().openSession();
			Criteria crit = session.createCriteria(EmployeeDO.class);
			crit.add(Restrictions.eq("empCode", code));
			EmployeeDO emp=(EmployeeDO) crit.uniqueResult();
			return emp;
		} catch (Exception e) {
			logger.error("DispatchMDBDAOImpl::getEmployeeByCode occured:"
					+e.getMessage());
			LOGGER.error("Error Occured in DispatchMDBDAOImpl::getEmployeeByCode"+e.getCause());

			throw new CGSystemException(e);
		}finally{
			if(session!=null){
				session.flush();
				session.close();
			}
		}
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.dispatch.DispatchMDBDAO#getVehicle(String)
	 */
	@Override
	public VehicleDO getVehicle(String vehicleRegNo) throws CGSystemException{
		Session session=null;
		try {
			session=getHibernateTemplate().getSessionFactory().openSession();
			Criteria crit = session.createCriteria(VehicleDO.class);
			crit.add(Restrictions.eq("regNumber", vehicleRegNo));
			VehicleDO emp=(VehicleDO) crit.uniqueResult();
			return emp;
		} catch (Exception e) {
			logger.error("DispatchMDBDAOImpl::getVehicle occured:"
					+e.getMessage());
			LOGGER.error("Error Occured in DispatchMDBDAOImpl::getVehicle"+e.getCause());

			throw new CGSystemException(e);
		}finally{
			if(session!=null){
				session.flush();
				session.close();
			}
		}
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.dispatch.DispatchMDBDAO#saveDispatch(DispatchDO)
	 */
	@Override
	public DispatchDO saveDispatch(DispatchDO dispatchDO){
		try {
			LOGGER.debug("DispatchMDBDAOImpl::saveDispatch=========>START");
			getHibernateTemplate().saveOrUpdate(dispatchDO);
		} catch (DataAccessException e) {
			logger.error("DispatchMDBDAOImpl::saveDispatch occured:"
					+e.getMessage());
			LOGGER.error("Error Occured in DispatchMDBDAOImpl::saveDispatch"+e.getCause());

		}catch (Exception e) {
			logger.error("DispatchMDBDAOImpl::saveDispatch occured:"
					+e.getMessage());
			LOGGER.error("Error Occured in DispatchMDBDAOImpl::saveDispatch"+e.getCause());

		}
		LOGGER.debug("DispatchMDBDAOImpl::saveDispatch=========>END");
		//getHibernateTemplate().flush();
		return dispatchDO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.dispatch.DispatchMDBDAO#getManifestByNum(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ManifestDO getManifestByNum(String manifestNum) throws CGSystemException{
		Session session=null;
		try {
			session=getHibernateTemplate().getSessionFactory().openSession();
			Criteria crit =session.createCriteria(ManifestDO.class);
			crit.add(Restrictions.eq("manifestNumber", manifestNum));
			List<ManifestDO> manifestDoList=crit.list();
			if(manifestDoList.size()>0) {
				return manifestDoList.get(0);
			}
		} catch (Exception e) {
			logger.error("DispatchMDBDAOImpl::getManifestByNum occured:"
					+e.getMessage());
			LOGGER.error("Error Occured in DispatchMDBDAOImpl::getManifestByNum"+e.getCause());

			throw new CGSystemException(e);
		}finally{
			if(session!=null){
				session.flush();
				session.close();
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.dispatch.DispatchMDBDAO#saveUpdateDispManifestList(List)
	 */
	@Override
	public void saveUpdateDispManifestList(List<DispatchBagManifestDO> dispManifestDos){
		try {
			getHibernateTemplate().saveOrUpdateAll(dispManifestDos);
		} catch (Exception e) {
			logger.error("DispatchMDBDAOImpl::saveUpdateDispManifestList occured:"
					+e.getMessage());
			LOGGER.error("Error Occured in DispatchMDBDAOImpl::saveUpdateDispManifestList"+e.getCause());

		}
		getHibernateTemplate().flush();
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.dispatch.DispatchMDBDAO#getDispatchByNumber(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public DispatchDO getDispatchByNumber(String number) throws CGSystemException{
		Session session=null;
		List<DispatchDO> dispatchDoList =null;
		try {
			LOGGER.debug("DispatchDaoImpl::getDispatchByNumber====>START");
			session=getHibernateTemplate().getSessionFactory().openSession();
			Criteria crit = session.createCriteria(DispatchDO.class);
			crit.add(Restrictions.eq("dispatchNumber", number));
			dispatchDoList=crit.list();
			if(dispatchDoList.size()>0){
				logger.trace("DispatchDaoImpl::getDispatchByNumber"+dispatchDoList);
				return dispatchDoList.get(0);
			}
		} catch (Exception e) {
			logger.error("DispatchMDBDAOImpl::getDispatchByNumber occured:"
					+e.getMessage());
			LOGGER.error("DispatchDaoImpl::getDispatchByNumber::exception occured"+e.getCause());

			throw new CGSystemException(e);
		}finally{
			if(session!=null){
				session.flush();
				session.close();
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.dispatch.DispatchMDBDAO#getDispatchManfstChild(Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DispatchBagManifestDO> getDispatchManfstChild(Integer dispatchId) throws CGSystemException{
		Session session=null;
		try {
			session=getHibernateTemplate().getSessionFactory().openSession();
			Criteria crit = session.createCriteria(DispatchBagManifestDO.class);
			crit.add(Restrictions.eq("dispatchDO.dispatchId", dispatchId));
			List<DispatchBagManifestDO> dispatchDoList=crit.list();
			return dispatchDoList;
		} catch (Exception e) {
			logger.error("DispatchMDBDAOImpl::getDispatchManfstChild occured:"
					+e.getMessage());
			LOGGER.error("Error Occured in DispatchMDBDAOImpl::getDispatchManfstChild"+e.getCause());

			throw new CGSystemException(e);
		}finally{
			if(session!=null){
				session.flush();
				session.close();
			}
		}
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.dispatch.DispatchMDBDAO#getDispatchRecieveByNumber(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public DispatchReceiverDO getDispatchRecieveByNumber(String dispatchNumber) throws CGSystemException{
		Session session=null;
		List<DispatchReceiverDO> dispatchDoList =null;
		try {
			LOGGER.debug("DispatchDaoImpl::getDispatchRecieveByNumber::dispatchDoList====>START");
			session=getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.createQuery("from com.dtdc.domain.dispatch.DispatchReceiverDO dispatchReceiver where dispatchReceiver.dispatchNumber=:dispatchNumber");
			query.setString("dispatchNumber", dispatchNumber.trim());
			dispatchDoList = query.list();
			//			Criteria crit = session.createCriteria(DispatchReceiverDO.class);
			//			crit.add(Restrictions.eq("dispatchNumber", dispatchNumber));
			//			List<DispatchReceiverDO> dispatchDoList=(List<DispatchReceiverDO>) crit.list();
			if(dispatchDoList.size()>0){
				logger.trace("DispatchDaoImpl::getDispatchRecieveByNumber::dispatchDoList"+dispatchDoList);
				return dispatchDoList.get(0);
			}
		} catch (Exception e) {
			logger.error("DispatchMDBDAOImpl::getDispatchRecieveByNumber occured:"
					+e.getMessage());			
			LOGGER.error("DispatchDaoImpl::getDispatchRecieveByNumber::exception occured"+e.getCause());
			throw new CGSystemException(e);
		}finally{
			if(session!=null){
				session.flush();
				session.close();
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.dispatch.DispatchMDBDAO#saveReceiver(DispatchReceiverDO)
	 */
	@Override
	public DispatchReceiverDO saveReceiver(DispatchReceiverDO recvDO){
		LOGGER.debug("DispatchMDBDAOImpl::saveReceiver=========>START");
		try {
			getHibernateTemplate().saveOrUpdate(recvDO);
		} catch (Exception e) {
			logger.error("DispatchMDBDAOImpl::saveReceiver occured:"
					+e.getMessage());
			LOGGER.error("Error occured in DispatchMDBDAOImpl::saveReceiver::"+e.getCause());
		}
		LOGGER.debug("DispatchMDBDAOImpl::saveReceiver=========>END");
		//getHibernateTemplate().flush();
		return recvDO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.dispatch.DispatchMDBDAO#deleteDispRecvManifestByParentId(Integer)
	 */
	@Override
	public void deleteDispRecvManifestByParentId(Integer dispatchRecieveId) throws CGSystemException{

		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		Session session=null;
		try {
			session = hibernateTemplate.getSessionFactory().openSession();
			Query query = session.createQuery("delete from com.dtdc.domain.dispatch.DispatchReceiveBagManifestDO pk where pk.dispatchRecvDO=:dispatchRecieveId");
			query.setInteger("dispatchRecieveId", dispatchRecieveId);
			query.executeUpdate();
		}  catch (Exception e) {
			logger.error("DispatchMDBDAOImpl::deleteDispRecvManifestByParentId occured:"
					+e.getMessage());
			LOGGER.error("Error Occured in DispatchMDBDAOImpl::deleteDispRecvManifestByParentId"+e.getCause());

			throw new CGSystemException(e);
		}finally{
			if(session!=null) {
				session.close();
			}
		}
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.dispatch.DispatchMDBDAO#saveUpdateDispRecvManifestList(List)
	 */
	@Override
	public void saveUpdateDispRecvManifestList(List<DispatchReceiveBagManifestDO> dispRecvManifestDos){
		try {
			getHibernateTemplate().saveOrUpdateAll(dispRecvManifestDos);
		} catch (DataAccessException e) {
			logger.error("DispatchMDBDAOImpl::saveUpdateDispRecvManifestList occured:"
					+e.getMessage());
			LOGGER.error("Error Occured in DispatchMDBDAOImpl::saveUpdateDispRecvManifestList"+e.getCause());

		}
		getHibernateTemplate().flush();
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.dispatch.DispatchMDBDAO#getAirportByAirlineNumber(String)
	 */
	@Override
	public AirportDO getAirportByAirlineNumber(String airlineNumber)
	throws CGSystemException {
		Session session=null;
		try {
			session=getHibernateTemplate().getSessionFactory().openSession();
			Criteria crit = session.createCriteria(AirportDO.class);
			crit.add(Restrictions.eq("airlineNumber", airlineNumber));
			AirportDO airportDO=(AirportDO) crit.uniqueResult();
			return airportDO;
		} catch (Exception e) {
			logger.error("DispatchMDBDAOImpl::getAirportByAirlineNumber occured:"
					+e.getMessage());
			LOGGER.error("Error Occured in DispatchMDBDAOImpl::getAirportByAirlineNumber"+e.getCause());

			throw new CGSystemException(e);
		}finally{
			if(session!=null){
				session.flush();
				session.close();
			}
		}
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.dispatch.DispatchMDBDAO#getTrainByTrainNumber(String)
	 */
	@Override
	public TrainDO getTrainByTrainNumber(String trainNumber)
	throws CGSystemException {
		Session session=null;
		try {
			session=getHibernateTemplate().getSessionFactory().openSession();
			Criteria crit = session.createCriteria(TrainDO.class);
			crit.add(Restrictions.eq("trainNumber", trainNumber));
			TrainDO trainDO=(TrainDO) crit.uniqueResult();
			return trainDO;
		} catch (Exception e) {
			logger.error("DispatchMDBDAOImpl::getTrainByTrainNumber occured:"
					+e.getMessage());
			LOGGER.error("Error Occured in DispatchMDBDAOImpl::getTrainByTrainNumber"+e.getCause());
			throw new CGSystemException(e);
		}finally{
			if(session!=null){
				session.flush();
				session.close();
			}
		}
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.dispatch.DispatchMDBDAO#getOTCMasterByOtcCode(String)
	 */
	@Override
	public OTCMasterDO getOTCMasterByOtcCode(String otcCode)
	throws CGSystemException {
		Session session=null;
		try {
			session=getHibernateTemplate().getSessionFactory().openSession();
			Criteria crit = session.createCriteria(OTCMasterDO.class);
			crit.add(Restrictions.eq("otcCode", otcCode));
			OTCMasterDO otcMasterDO=(OTCMasterDO) crit.uniqueResult();
			return otcMasterDO;
		} catch (Exception e) {
			logger.error("DispatchMDBDAOImpl::getOTCMasterByOtcCode occured:"
					+e.getMessage());
			LOGGER.error("Error Occured in DispatchMDBDAOImpl::getOTCMasterByOtcCode"+e.getCause());

			throw new CGSystemException(e);
		}finally{
			if(session!=null){
				session.flush();
				session.close();
			}
		}
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.dispatch.DispatchMDBDAO#getUserByUserCode(String)
	 */
	@Override
	public UserDO getUserByUserCode(String userCode) throws CGSystemException {
		Session session=null;
		try {
			session=getHibernateTemplate().getSessionFactory().openSession();
			Criteria crit = session.createCriteria(UserDO.class);
			crit.add(Restrictions.eq("userCode", userCode));
			UserDO userDO=(UserDO) crit.uniqueResult();
			return userDO;
		} catch (Exception e) {
			logger.error("DispatchMDBDAOImpl::getUserByUserCode occured:"
					+e.getMessage());
			LOGGER.error("Error Occured in DispatchMDBDAOImpl::getUserByUserCode"+e.getCause());
			throw new CGSystemException(e);
		}finally{
			if(session!=null){
				session.flush();
				session.close();
			}
		}
	}
}
