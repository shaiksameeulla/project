package src.com.dtdc.mdbDao.pickup;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseDAO.CGBaseDAO;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.domain.login.UserDO;
import com.dtdc.domain.master.agent.AgentDO;
import com.dtdc.domain.master.customer.CustAddressDO;
import com.dtdc.domain.master.customer.CustomerDO;
import com.dtdc.domain.master.employee.EmployeeDO;
import com.dtdc.domain.master.franchisee.FranchiseeDO;
import com.dtdc.domain.master.geography.AreaDO;
import com.dtdc.domain.master.geography.CityDO;
import com.dtdc.domain.master.geography.PincodeDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.master.product.ServiceDO;
import com.dtdc.domain.pickup.PickUpDO;
import com.dtdc.domain.pickup.PickUpHistoryDO;
import com.dtdc.domain.pickup.PickUpProductDO;
import com.dtdc.domain.pickup.PickupDailySheetDO;
import com.dtdc.to.pickup.PickUpTO;

// TODO: Auto-generated Javadoc
/**
 * The Class PickUpMDBDAOImpl.
 */
public class PickUpMDBDAOImpl extends CGBaseDAO implements PickUpMDBDAO {

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.pickup.PickUpMDBDAO#savePickUpGeneration(PickUpDO)
	 */
	@Override
	public void savePickUpGeneration(PickUpDO pickUpDO){
		try {
			getHibernateTemplate().saveOrUpdate(pickUpDO);
			getHibernateTemplate().flush();
		} catch (Exception e) {
			logger.error("PickUpMDBDAOImpl::savePickUpGeneration::Exception occured:"
					+e.getMessage());
			//logger.error("Exception Occured in::PickUpMDBDAOImpl::savePickUpGeneration()==>" + e.getCause().getMessage());
			logger.error("Exception Occured in::PickUpMDBDAOImpl::savePickUpGeneration()==>" + e.getStackTrace());
		}
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.pickup.PickUpMDBDAO#getCustomerByCode(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public CustomerDO getCustomerByCode(String code){
		Session session = null;
		CustomerDO custDo=null;
		List<CustomerDO> customerDOList = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.createQuery("from com.dtdc.domain.master.customer.CustomerDO pdo where pdo.customerCode=:code");
			query.setString("code", code);
			//custDo=(CustomerDO) query.uniqueResult();
			customerDOList = query.list();
			if(customerDOList!=null && customerDOList.size()>0) {
				custDo = customerDOList.get(0);
			}
		} catch (Exception e) {
			logger.error("PickUpMDBDAOImpl::getCustomerByCode::Exception occured:"
					+e.getMessage());
			//logger.error("Exception Occured in::PickUpMDBDAOImpl::getCustomerByCode()==>" + e.getCause().getMessage());
			logger.error("Exception Occured in::PickUpMDBDAOImpl::getCustomerByCode()==>" + e.getStackTrace());
		}finally {
			if(session != null){
				session.flush();
				session.close();
			}
		}

		return custDo;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.pickup.PickUpMDBDAO#getPincode(Integer)
	 */
	@Override
	public PincodeDO getPincode(Integer pinId){
		Session session = null;
		PincodeDO pin=null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.createQuery("from com.dtdc.domain.master.geography.PincodeDO pin where pin.pincodeId=:pinId");
			query.setInteger("pinId", pinId);
			pin=(PincodeDO) query.uniqueResult();
		} finally {
			if(session != null){
				session.flush();
				session.close();
			}
		}
		return pin;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.pickup.PickUpMDBDAO#getCity(Integer)
	 */
	@Override
	public CityDO getCity(Integer cityId){
		Session session = null;
		CityDO city=null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.createQuery("from com.dtdc.domain.master.geography.CityDO pin where pin.cityId=:cityId");
			query.setInteger("cityId", cityId);
			city=(CityDO) query.uniqueResult();
		} finally {
			if(session != null){
				session.flush();
				session.close();
			}
		}
		return city;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.pickup.PickUpMDBDAO#saveAreaDo(AreaDO)
	 */
	@Override
	public void saveAreaDo(AreaDO areaDo){
		getHibernateTemplate().saveOrUpdate(areaDo);
		getHibernateTemplate().flush();
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.pickup.PickUpMDBDAO#saveCustAddressDo(CustAddressDO)
	 */
	@Override
	public void saveCustAddressDo(CustAddressDO custAddDo){
		getHibernateTemplate().saveOrUpdate(custAddDo);
		getHibernateTemplate().flush();
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.pickup.PickUpMDBDAO#saveCustmerDo(CustomerDO)
	 */
	@Override
	public void saveCustmerDo(CustomerDO custDo){
		getHibernateTemplate().saveOrUpdate(custDo);
		getHibernateTemplate().flush();
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.pickup.PickUpMDBDAO#getOfficeByCode(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public OfficeDO getOfficeByCode(String code){
		Session session = null;
		List<OfficeDO> ofcDo=null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.createQuery("from com.dtdc.domain.master.office.OfficeDO pdo where pdo.officeCode=:code");
			query.setString("code", code);
			ofcDo=query.list();
			if(ofcDo!=null && ofcDo.size()>0){
				return ofcDo.get(0);
			}
		} catch (Exception e) {
			logger.error("PickUpMDBDAOImpl::getOfficeByCode::Exception occured:"
					+e.getMessage());
			//logger.error("Exception Occured in::PickUpMDBDAOImpl::getOfficeByCode()==>" + e.getCause().getMessage());
			logger.error("Exception Occured in::PickUpMDBDAOImpl::getOfficeByCode()==>" + e.getStackTrace());
		}finally {
			if(session != null){
				session.flush();
				session.close();
			}
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.pickup.PickUpMDBDAO#getAreaByCode(String)
	 */
	@Override
	public AreaDO getAreaByCode(String code){
		Session session = null;
		AreaDO areaDo=null;
		List<AreaDO> areaDOList = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.createQuery("from com.dtdc.domain.master.geography.AreaDO pdo where pdo.areaCode=:code");
			query.setString("code", code);
			//areaDo=(AreaDO) query.uniqueResult();			
			areaDOList = query.list();
			if(areaDOList!=null && areaDOList.size()>0) {
				areaDo = areaDOList.get(0);
			}
		}catch (Exception e) {
			logger.error("PickUpMDBDAOImpl::getAreaByCode::Exception occured:"
					+e.getMessage());
			//logger.error("Exception Occured in::PickUpMDBDAOImpl::getAreaByCode()==>" + e.getCause().getMessage());
			logger.error("Exception Occured in::PickUpMDBDAOImpl::getAreaByCode()==>" + e.getStackTrace());
		} finally {
			if(session != null){
				session.flush();
				session.close();
			}
		}
		return areaDo;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.pickup.PickUpMDBDAO#getEmployee(Integer)
	 */
	@Override
	public EmployeeDO getEmployee(Integer empId){
		Session session = null;
		EmployeeDO emp=null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.createQuery("from com.dtdc.domain.master.employee.EmployeeDO emp where emp.employeeId=:empId");
			query.setInteger("empId", empId);
			emp=(EmployeeDO) query.uniqueResult();
		} finally {
			if(session != null){
				session.flush();
				session.close();
			}
		}
		return emp;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.pickup.PickUpMDBDAO#getFranchisee(Integer)
	 */
	@Override
	public FranchiseeDO getFranchisee(Integer frId){
		Session session = null;
		FranchiseeDO fr=null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.createQuery("from com.dtdc.domain.master.franchisee.FranchiseeDO emp where emp.franchiseeId=:frId");
			query.setInteger("frId", frId);
			fr=(FranchiseeDO) query.uniqueResult();
		} finally {
			if(session != null){
				session.flush();
				session.close();
			}
		}
		return fr;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.pickup.PickUpMDBDAO#getAgent(Integer)
	 */
	@Override
	public AgentDO getAgent(Integer agId){
		Session session = null;
		AgentDO ag=null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.createQuery("from com.dtdc.domain.master.agent.AgentDO emp where emp.agentId=:agId");
			query.setInteger("agId", agId);
			ag=(AgentDO) query.uniqueResult();
		} finally {
			if(session != null){
				session.flush();
				session.close();
			}
		}
		return ag;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.pickup.PickUpMDBDAO#getPickUp(Integer)
	 */
	@Override
	public PickUpDO getPickUp(Integer pickUpId) {
		Session session = null;
		PickUpDO pickUpDO =null;
		try {
			HibernateTemplate hibernateTemplate = getHibernateTemplate();
			session = hibernateTemplate.getSessionFactory().openSession();
			Query query = session.createQuery("from com.dtdc.domain.pickup.PickUpDO pdo where pdo.pickupId=:pickupId");
			query.setInteger("pickupId", pickUpId);
			pickUpDO = (PickUpDO)query.uniqueResult();
		} finally {
			if(session != null){
				session.flush();
				session.close();
			}
		}
		return pickUpDO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.pickup.PickUpMDBDAO#getPickUpByCompositeKey(PickUpTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PickUpDO getPickUpByCompositeKey(PickUpTO pickUpTO) throws CGSystemException {
		Session session = null;
		Criteria criteria = null;
		List<PickUpDO> searchList = null;
		try{
			session =getHibernateTemplate().getSessionFactory().openSession();
			criteria = session.createCriteria(PickUpDO.class,"qry");
			if(pickUpTO!=null) {
				if (!StringUtil.isEmpty(pickUpTO.getCustomerCode())){
					CustomerDO customer=getCustomerByCode(pickUpTO.getCustomerCode());
					criteria.add(Restrictions.eq("qry.customerDO", customer));
				}
				if (!StringUtil.isEmpty(pickUpTO.getOriginOfficeCode())){
					OfficeDO orgOfc=getOfficeByCode(pickUpTO.getOriginOfficeCode());
					criteria.add(Restrictions.eq("qry.originOfficeDO", orgOfc));
				}
				if (!StringUtil.isEmpty(pickUpTO.getAssignOfficeCode())){
					OfficeDO asgOfc=getOfficeByCode(pickUpTO.getAssignOfficeCode());
					criteria.add(Restrictions.eq("qry.officeDO", asgOfc));
				}

				criteria.add(Restrictions.or(Property.forName( "qry.curStatus" ).eq("UA"), Property.forName( "qry.curStatus" ).eq("AS")));

				if(!StringUtil.isEmpty(pickUpTO.getPickupDate())) {
					criteria.add(Restrictions.eq("qry.pickupDate", DateFormatterUtil.getDateFromStringDDMMYYY(pickUpTO.getPickupDate())));
				}
				//Get the List result
				searchList = criteria.list();
			}
		}catch (Exception obj) {
			logger.error("PickUpMDBDAOImpl::getPickUpByCompositeKey::Exception occured:"
					+obj.getMessage());
			throw new CGSystemException(obj);
		} finally {//Restrictions.isNull("name"));
			session.flush();
			session.close();
		}
		if(searchList!=null && searchList.size()>0){
			return searchList.get(0);
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.pickup.PickUpMDBDAO#updatePickUpHistoryDo(PickUpHistoryDO)
	 */
	@Override
	public void updatePickUpHistoryDo(PickUpHistoryDO pickupDo){
		getHibernateTemplate().saveOrUpdate(pickupDo);
		getHibernateTemplate().flush();
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.pickup.PickUpMDBDAO#updatePickUpDo(PickUpDO)
	 */
	@Override
	public void updatePickUpDo(PickUpDO pickupDo){
		getHibernateTemplate().update(pickupDo);
		getHibernateTemplate().flush();
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.pickup.PickUpMDBDAO#saveUpdatePickUpDoList(List)
	 */
	@Override
	public void saveUpdatePickUpDoList(List<PickUpDO> pickupDos) throws CGSystemException{
		try{
			getHibernateTemplate().saveOrUpdateAll(pickupDos);
			getHibernateTemplate().flush();	
		}catch(Exception e){
			logger.error("PickUpMDBDAOImpl::saveUpdatePickUpDoList::Exception occured:"
					+e.getMessage());
			//logger.error("Exception Occured in::PickUpMDBDAOImpl::saveUpdatePickUpDoList()==>" + e.getCause().getMessage());
			logger.error("Exception Occured in::PickUpMDBDAOImpl::saveUpdatePickUpDoList()==>" + e.getStackTrace());
			throw new CGSystemException(e);
		}
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.pickup.PickUpMDBDAO#deletePickupProductsByPickupId(Integer)
	 */
	@Override
	public void deletePickupProductsByPickupId(Integer pickupId) throws CGSystemException{

		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		Session session=null;
		try {
			session = hibernateTemplate.getSessionFactory().openSession();
			Query query = session.createQuery("delete from com.dtdc.domain.pickup.PickUpProductDO pk where pk.pickupId=:pickupId");
			query.setInteger("pickupId", pickupId);
			query.executeUpdate();
		}  catch (Exception e) {
			logger.error("PickUpMDBDAOImpl::deletePickupProductsByPickupId::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		}finally{
			if(session!=null) {
				session.close();
			}
		}
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.pickup.PickUpMDBDAO#savePickupProducts(List)
	 */
	@Override
	public void savePickupProducts(List<PickUpProductDO> list){
		try {
			getHibernateTemplate().saveOrUpdateAll(list);
			getHibernateTemplate().flush();			
		} catch (Exception e) {
			logger.error("PickUpMDBDAOImpl::savePickupProducts::Exception occured:"
					+e.getMessage());
			//logger.error("Exception Occured in::PickUpMDBDAOImpl::getPickUpDoByPickupReqNum()==>" + e.getCause().getMessage());
			logger.error("Exception Occured in::PickUpMDBDAOImpl::getPickUpDoByPickupReqNum()==>" + e.getStackTrace());
		}

	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.pickup.PickUpMDBDAO#getPickUpDoByPickupReqNum(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PickUpDO getPickUpDoByPickupReqNum(String pickupReqNum) throws CGSystemException {		
		List<PickUpDO> pickUpDOList = new ArrayList<PickUpDO>();
		PickUpDO pickUpDO = null;
		Session session = null;
		Criteria criteria=null;
		try{
			session = getHibernateTemplate().getSessionFactory().openSession();
			criteria = session.createCriteria(PickUpDO.class);
			criteria.add(Restrictions.eq("pickupReqNum", pickupReqNum));
			pickUpDOList = criteria.list();
			if(pickUpDOList!=null && pickUpDOList.size()>0) {
				pickUpDO = pickUpDOList.get(0);
			}
		}
		catch(Exception e){
			logger.error("PickUpMDBDAOImpl::getPickUpDoByPickupReqNum::Exception occured:"
					+e.getMessage());
			//logger.error("Exception Occured in::PickUpMDBDAOImpl::getPickUpDoByPickupReqNum()==>" + e.getCause().getMessage());
			logger.error("Exception Occured in::PickUpMDBDAOImpl::getPickUpDoByPickupReqNum()==>" + e.getStackTrace());
			throw new CGSystemException(e);
		}
		finally{
			if(session!=null){
				session.flush();
				session.close();				
			}
		}
		return pickUpDO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.pickup.PickUpMDBDAO#getPickupDailySheetIdByPickupSheetNo(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer[] getPickupDailySheetIdByPickupSheetNo(String pickupSheetNo)
	throws CGSystemException {
		List<PickupDailySheetDO> pickupDailySheetDOList = new ArrayList<PickupDailySheetDO>();
		Session session = null;
		Criteria criteria=null;
		Integer pickupDailySheetId[] = null;
		try{
			session = getHibernateTemplate().getSessionFactory().openSession();
			criteria = session.createCriteria(PickupDailySheetDO.class);
			criteria.add(Restrictions.eq("pickupSheetNo", pickupSheetNo));
			criteria.addOrder(Order.asc("pickupSheetNo"));
			pickupDailySheetDOList = criteria.list();

			if(pickupDailySheetDOList!=null && pickupDailySheetDOList.size()>0){
				pickupDailySheetId = new Integer[pickupDailySheetDOList.size()];
				int count = 0;
				for(PickupDailySheetDO dailySheetDO : pickupDailySheetDOList){
					pickupDailySheetId[count++] = dailySheetDO.getPickupDailySheetId();
				}
			}
		}
		catch(Exception e){
			logger.error("PickUpMDBDAOImpl::getPickupDailySheetIdByPickupSheetNo::Exception occured:"
					+e.getMessage());
			//logger.error("Exception Occured in::PickUpMDBDAOImpl::getPickupDailySheetIdByPickupSheetNo()" + e.getCause().getMessage());
			logger.error("Exception Occured in::PickUpMDBDAOImpl::getPickupDailySheetIdByPickupSheetNo()" + e.getStackTrace());
			throw new CGSystemException(e);
		}
		finally{
			if(session!=null){
				session.flush();
				session.close();				
			}
		}
		return pickupDailySheetId;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.pickup.PickUpMDBDAO#saveOrUpdatePickupDailySheetDOList(List)
	 */
	@Override
	public void saveOrUpdatePickupDailySheetDOList(
			List<PickupDailySheetDO> pickupDailySheetDOList)
	throws CGSystemException, CGBusinessException {
		try{
			getHibernateTemplate().saveOrUpdateAll(pickupDailySheetDOList);
			getHibernateTemplate().flush();
		}catch(Exception e){
			logger.error("PickUpMDBDAOImpl::saveOrUpdatePickupDailySheetDOList::Exception occured:"
					+e.getMessage());
			//logger.error("Exception Occured in::PickUpMDBDAOImpl::saveOrUpdatePickupDailySheetDOList()==>" + e.getCause().getMessage());
			logger.error("Exception Occured in::PickUpMDBDAOImpl::saveOrUpdatePickupDailySheetDOList()==>" + e.getStackTrace());
			throw new CGSystemException(e);
		}

	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.pickup.PickUpMDBDAO#getServiceDOByServiceCode(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ServiceDO getServiceDOByServiceCode(String serviceCode)
	throws CGSystemException {
		List<ServiceDO> serviceDOList = new ArrayList<ServiceDO>();
		ServiceDO serviceDO = null;
		Session session = null;
		Criteria criteria=null;
		try{
			session = getHibernateTemplate().getSessionFactory().openSession();
			criteria = session.createCriteria(ServiceDO.class);
			criteria.add(Restrictions.eq("serviceCode", serviceCode));
			serviceDOList = criteria.list();
			if(serviceDOList!=null && serviceDOList.size()>0) {
				serviceDO = serviceDOList.get(0);
			}
		}
		catch(Exception e){
			logger.error("PickUpMDBDAOImpl::getServiceDOByServiceCode::Exception occured:"
					+e.getMessage());
			//logger.error("Exception Occured in::PickUpMDBDAOImpl::getServiceDOByServiceCode()" + e.getCause().getMessage());
			logger.error("Exception Occured in::PickUpMDBDAOImpl::getServiceDOByServiceCode()" + e.getStackTrace());
			throw new CGSystemException(e);
		}
		finally{
			if(session!=null){
				session.flush();
				session.close();				
			}
		}
		return serviceDO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.pickup.PickUpMDBDAO#getUserDOByUserCode(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UserDO getUserDOByUserCode(String userCode) throws CGSystemException {
		List<UserDO> userDOList = new ArrayList<UserDO>();
		UserDO userDO = null;
		Session session = null;
		Criteria criteria=null;
		try{
			session = getHibernateTemplate().getSessionFactory().openSession();
			criteria = session.createCriteria(UserDO.class);
			criteria.add(Restrictions.eq("userCode", userCode));
			userDOList = criteria.list();
			if(userDOList!=null && userDOList.size()>0) {
				userDO = userDOList.get(0);
			}
		}
		catch(Exception e){
			logger.error("PickUpMDBDAOImpl::getUserDOByUserCode::Exception occured:"
					+e.getMessage());
			//logger.error("Exception Occured in::PickUpMDBDAOImpl::getUserDOByUserCode()" + e.getCause().getMessage());
			logger.error("Exception Occured in::PickUpMDBDAOImpl::getUserDOByUserCode()" + e.getStackTrace());
			throw new CGSystemException(e);
		}
		finally{
			if(session!=null){
				session.flush();
				session.close();				
			}
		}
		return userDO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.pickup.PickUpMDBDAO#getDailySheetIdByPickupId(Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer getDailySheetIdByPickupId(Integer pickupId)
	throws CGSystemException {
		List<PickupDailySheetDO> pickupDailySheetDOList = new ArrayList<PickupDailySheetDO>();
		Session session = null;
		Criteria criteria=null;
		Integer pickupDailySheetId = null;
		try{
			session = getHibernateTemplate().getSessionFactory().openSession();
			criteria = session.createCriteria(PickupDailySheetDO.class);
			criteria.add(Restrictions.eq("pickupDO.pickupId", pickupId));
			pickupDailySheetDOList = criteria.list();

			if(pickupDailySheetDOList!=null && pickupDailySheetDOList.size()>0){
				pickupDailySheetId = pickupDailySheetDOList.get(0).getPickupDailySheetId();
			}
		}
		catch(Exception e){
			logger.error("PickUpMDBDAOImpl::getDailySheetIdByPickupId::Exception occured:"
					+e.getMessage());
			//logger.error("Exception Occured in::PickUpMDBDAOImpl::getDailySheetIdByPickupId()" + e.getCause().getMessage());
			logger.error("Exception Occured in::PickUpMDBDAOImpl::getDailySheetIdByPickupId()" + e.getStackTrace());
			throw new CGSystemException(e);
		}
		finally{
			if(session!=null){
				session.flush();
				session.close();				
			}
		}
		return pickupDailySheetId;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.pickup.PickUpMDBDAO#savePickupProductsAndPickupDetails(List, PickUpDO)
	 */
	@Override
	public void savePickupProductsAndPickupDetails(List<PickUpProductDO> list,PickUpDO pickDO) throws CGSystemException {
		for(PickUpProductDO prodDO :list ){
			getHibernateTemplate().saveOrUpdate(prodDO);
		}
		getHibernateTemplate().saveOrUpdate(pickDO);
		getHibernateTemplate().flush();

	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.pickup.PickUpMDBDAO#getPickupProductIdByPickupId(Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer[] getPickupProductIdByPickupId(Integer pickupId)
	throws CGSystemException {
		List<PickUpProductDO> pickupProductDOList = new ArrayList<PickUpProductDO>();
		Session session = null;
		Criteria criteria=null;
		Integer pickupProductIds[] = null;
		try{
			session = getHibernateTemplate().getSessionFactory().openSession();
			criteria = session.createCriteria(PickUpProductDO.class);
			criteria.add(Restrictions.eq("pickupDO.pickupId", pickupId));
			criteria.addOrder(Order.asc("pickupDO.pickupId"));
			pickupProductDOList = criteria.list();

			if(pickupProductDOList!=null && pickupProductDOList.size()>0){
				pickupProductIds = new Integer[pickupProductDOList.size()];
				int count = 0;
				for(PickUpProductDO pickUpProductDO : pickupProductDOList){
					pickupProductIds[count++] = pickUpProductDO.getPickupClosedId();
				}
			}
		}
		catch(Exception e){
			logger.error("PickUpMDBDAOImpl::getPickupProductIdByPickupId::Exception occured:"
					+e.getMessage());
			//logger.error("Exception Occured in::PickUpMDBDAOImpl::getPickupProductIdByPickupId()" + e.getCause().getMessage());
			logger.error("Exception Occured in::PickUpMDBDAOImpl::getPickupProductIdByPickupId()" + e.getStackTrace());
			throw new CGSystemException(e);
		}
		finally{
			if(session!=null){
				session.flush();
				session.close();				
			}
		}
		return pickupProductIds;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.pickup.PickUpMDBDAO#getEmployeeByEmpCode(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public EmployeeDO getEmployeeByEmpCode(String empCode)
	throws CGSystemException {
		List<EmployeeDO> employeeDOList = null;
		EmployeeDO empDO = null;
		Session session = null;
		Criteria criteria=null;
		try{
			session = getHibernateTemplate().getSessionFactory().openSession();
			criteria = session.createCriteria(EmployeeDO.class);
			criteria.add(Restrictions.eq("empCode", empCode));
			employeeDOList = criteria.list();
			if(employeeDOList!=null && employeeDOList.size()>0) {
				empDO = employeeDOList.get(0);
			}
		}
		catch(Exception e){
			logger.error("PickUpMDBDAOImpl::getEmployeeByEmpCode::Exception occured:"
					+e.getMessage());
			//logger.error("Exception Occured in::PickUpMDBDAOImpl::getEmployeeByEmpCode()" + e.getCause().getMessage());
			logger.error("Exception Occured in::PickUpMDBDAOImpl::getEmployeeByEmpCode()" + e.getStackTrace());
			throw new CGSystemException(e);
		}
		finally{
			if(session!=null){
				session.flush();
				session.close();				
			}
		}
		return empDO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.pickup.PickUpMDBDAO#getAgentByAgentCode(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public AgentDO getAgentByAgentCode(String agentCode)
	throws CGSystemException {
		List<AgentDO> agentDOList = null;
		AgentDO agentDO = null;
		Session session = null;
		Criteria criteria=null;
		try{
			session = getHibernateTemplate().getSessionFactory().openSession();
			criteria = session.createCriteria(AgentDO.class);
			criteria.add(Restrictions.eq("agentCode", agentCode));
			agentDOList = criteria.list();
			if(agentDOList!=null && agentDOList.size()>0) {
				agentDO = agentDOList.get(0);
			}
		}
		catch(Exception e){
			logger.error("PickUpMDBDAOImpl::getAgentByAgentCode::Exception occured:"
					+e.getMessage());
			//logger.error("Exception Occured in::PickUpMDBDAOImpl::getAgentByAgentCode()" + e.getCause().getMessage());
			logger.error("Exception Occured in::PickUpMDBDAOImpl::getAgentByAgentCode()" + e.getStackTrace());
			throw new CGSystemException(e);
		}
		finally{
			if(session!=null){
				session.flush();
				session.close();				
			}
		}
		return agentDO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.pickup.PickUpMDBDAO#getPincodeByPincode(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PincodeDO getPincodeByPincode(String pincode)
	throws CGSystemException {
		List<PincodeDO> pincodeDOList = null;
		PincodeDO pincodeDO = null;
		Session session = null;
		Criteria criteria=null;
		try{
			session = getHibernateTemplate().getSessionFactory().openSession();
			criteria = session.createCriteria(PincodeDO.class);
			criteria.add(Restrictions.eq("pincode", pincode));
			pincodeDOList = criteria.list();
			if(pincodeDOList!=null && pincodeDOList.size()>0) {
				pincodeDO = pincodeDOList.get(0);
			}
		}
		catch(Exception e){
			logger.error("PickUpMDBDAOImpl::getPincodeByPincode::Exception occured:"
					+e.getMessage());
			//logger.error("Exception Occured in::PickUpMDBDAOImpl::getPincodeByPincode()" + e.getCause().getMessage());
			logger.error("Exception Occured in::PickUpMDBDAOImpl::getPincodeByPincode()" + e.getStackTrace());
			throw new CGSystemException(e);
		}
		finally{
			if(session!=null){
				session.flush();
				session.close();				
			}
		}
		return pincodeDO;
	}	

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.pickup.PickUpMDBDAO#getCityByCityCode(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public CityDO getCityByCityCode(String cityCode)
	throws CGSystemException {
		List<CityDO> cityDOList = null;
		CityDO cityDO = null;
		Session session = null;
		Criteria criteria=null;
		try{
			session = getHibernateTemplate().getSessionFactory().openSession();
			criteria = session.createCriteria(CityDO.class);
			criteria.add(Restrictions.eq("cityCode", cityCode));
			cityDOList = criteria.list();
			if(cityDOList!=null && cityDOList.size()>0) {
				cityDO = cityDOList.get(0);
			}
		}
		catch(Exception e){
			logger.error("PickUpMDBDAOImpl::getCityByCityCode::Exception occured:"
					+e.getMessage());
			//logger.error("Exception Occured in::PickUpMDBDAOImpl::getCityByCityCode()" + e.getCause().getMessage());
			logger.error("Exception Occured in::PickUpMDBDAOImpl::getCityByCityCode()" + e.getStackTrace());
			throw new CGSystemException(e);
		}
		finally{
			if(session!=null){
				session.flush();
				session.close();				
			}
		}
		return cityDO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.pickup.PickUpMDBDAO#geFranchiseeByFranchiseeCode(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public FranchiseeDO geFranchiseeByFranchiseeCode(String franchiseeCode)
	throws CGSystemException {
		List<FranchiseeDO> franchiseeDOList = null;
		FranchiseeDO franchiseeDO = null;
		Session session = null;
		Criteria criteria=null;
		try{
			session = getHibernateTemplate().getSessionFactory().openSession();
			criteria = session.createCriteria(FranchiseeDO.class);
			criteria.add(Restrictions.eq("franchiseeCode", franchiseeCode));
			franchiseeDOList = criteria.list();
			if(franchiseeDOList!=null && franchiseeDOList.size()>0) {
				franchiseeDO = franchiseeDOList.get(0);
			}
		}
		catch(Exception e){
			logger.error("PickUpMDBDAOImpl::geFranchiseeByFranchiseeCode::Exception occured:"
					+e.getMessage());
			//logger.error("Exception Occured in::PickUpMDBDAOImpl::geFranchiseeByFranchiseeCode()" + e.getCause().getMessage());
			logger.error("Exception Occured in::PickUpMDBDAOImpl::geFranchiseeByFranchiseeCode()" + e.getStackTrace());
			throw new CGSystemException(e);
		}
		finally{
			if(session!=null){
				session.flush();
				session.close();				
			}
		}
		return franchiseeDO;
	}
}
