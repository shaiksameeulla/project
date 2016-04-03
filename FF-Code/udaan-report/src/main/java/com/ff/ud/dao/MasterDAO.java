package com.ff.ud.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ff.ud.constants.OpsmanDBFConstant;
import com.ff.ud.domain.AllFfMastDO;
import com.ff.ud.domain.CityDO;
import com.ff.ud.domain.CustomerDO;
import com.ff.ud.domain.FinancialProductDO;
import com.ff.ud.domain.OfficeDO;
import com.ff.ud.domain.ProductDO;
import com.ff.ud.domain.RightsForUserDO;
import com.ff.ud.dto.AssignOfficeUserDTO;
import com.ff.ud.dto.ChequeDetailsDTO;
import com.ff.ud.dto.CityDTO;
import com.ff.ud.dto.CustomerDTO;
import com.ff.ud.dto.MonthAndDateDTO;
import com.ff.ud.dto.OfficeCodeManagerDTO;
import com.ff.ud.dto.OfficeDTO;
import com.ff.ud.dto.OfficeRightsDTO;
import com.ff.ud.dto.PinckUpDeliveryDTO;
import com.ff.ud.dto.ProductDTO;
import com.ff.ud.dto.RegionDTO;
import com.ff.ud.dto.RegionRightsDTO;
import com.ff.ud.dto.VendorDetailsDTO;
import com.ff.ud.dto.vendorDTO;
import com.ff.ud.utils.StringUtils;




@Repository
public class MasterDAO {

	/*@Autowired 
	@Qualifier(value="dbServer")
	private SessionFactory sessionFactory;*/
	
	@Autowired
	@Qualifier(value="dbServer")
	private SessionFactory hibernateSessionFactory;
	
/*	@Autowired
	@Qualifier("opsmanSeries")
	private SessionFactory opsmanSessionFactory;*/
	
	static Logger logger = Logger.getLogger(MasterDAO.class);
	
	
	public Integer getCityIdFromCityCode(String cityCode) {
		String sql ="select u_city_id from "+OpsmanDBFConstant.DATABASE_NAME_MASTER+"ou_city where o_city_code=?";
		//System.out.println("city code : "+cityCode);
		Query query = hibernateSessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setString(0, cityCode);
		Integer cityId=(Integer) query.uniqueResult();
		if(null==cityId){
			System.out.println(" city id is "+cityCode+" : "+cityId);
		}
		return cityId;
	}
	
	public Integer getEmployeeIdFromEmployeeCode(String EmployeeCode) {
		String sql ="select EMPLOYEE_ID from "+OpsmanDBFConstant.DATABASE_NAME_MASTER+"ff_d_employee where emp_code=?";
		Query query = hibernateSessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setString(0, EmployeeCode);
		return (Integer) query.uniqueResult();
	}
	
	public Integer getConsignmentTypeIdFromConsignmentCode(String ConsignmentCode) {
		//String sql ="select consg_id from ff_f_consignment where consg_no = ?";
		String sql="SELECT CONSIGNMENT_TYPE_ID FROM "+OpsmanDBFConstant.DATABASE_NAME_MASTER+"ff_d_consignment_type WHERE CONSIGNMENT_CODE=?";
		Query query = hibernateSessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setString(0, ConsignmentCode);
		return (Integer) query.uniqueResult();
	}
	
	public Integer getBranchIdFromBranchCode(String branchCode) {
		
		String sql ="SELECT u_office_id FROM "+OpsmanDBFConstant.DATABASE_NAME_MASTER+"ou_office WHERE o_office_code_1=?";
		String sql1 ="SELECT u_office_id FROM "+OpsmanDBFConstant.DATABASE_NAME_MASTER+"ou_office WHERE o_office_code=?";
		
		Query query = hibernateSessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setString(0, branchCode);
		//System.out.println(query.toString());
		//System.out.println("Branch cpde "+branchCode);
		Integer branchId=(Integer) query.uniqueResult();
		if(null==branchId){
			query = hibernateSessionFactory.getCurrentSession().createSQLQuery(sql1);
			query.setString(0, branchCode);
			branchId=(Integer) query.uniqueResult();
		}
		
		return branchId;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getUniversities() {
		String sql = "SELECT  university_name FROM University;";
		Query query = hibernateSessionFactory.getCurrentSession().createSQLQuery(sql);
	    return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getCityCodesOpsman() {
		String sql = "SELECT city_code FROM "+OpsmanDBFConstant.DATABASE_NAME_MASTER+"ff_d_city";
		//String sql = "SELECT TOP 5 city_code FROM FFclMaster.dbo.ff_d_city WHERE city_code='DEI'";
		//WHERE city_code='BOY';
		Query query = hibernateSessionFactory.getCurrentSession().createSQLQuery(sql);
	    return query.list();
	}
	
	/*@SuppressWarnings("unchecked")
	public List<Jobs> getJobs() {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Jobs.class);
		criteria.add(Restrictions.eq("display", Boolean.TRUE));
		return criteria.list();
	}*/
	

	public String getBookingType(String string) {
		// TODO Auto-generated method stub
		String sql="SELECT  BOOKING_TYPE_ID FROM "+OpsmanDBFConstant.DATABASE_NAME_MASTER+"ff_d_booking_type WHERE BOOKING_TYPE=?;";
		Query query = hibernateSessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setString(0, string);
		return (String) query.uniqueResult();
	}

	public String getCustomerId(String customerCode) {
		String sql="SELECT u_customer_id FROM "+OpsmanDBFConstant.DATABASE_NAME_MASTER+"ou_customer WHERE o_customer_code =?";
		//System.out.println("Customer "+customerCode+" "+sql);
		SQLQuery query = hibernateSessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setString(0, customerCode);
		query.addScalar("u_customer_id",Hibernate.STRING);
		return (String) query.uniqueResult();
	
	}

	
	
	public String getPincodeIdFromPincode(String pincode) {
		String sql="SELECT PINCODE_ID FROM "+OpsmanDBFConstant.DATABASE_NAME_MASTER+"ff_d_pincode WHERE PINCODE=? ; ";
		SQLQuery query = hibernateSessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setString(0, pincode);
		query.addScalar("PINCODE_ID",Hibernate.STRING);
		return (String) query.uniqueResult();
	}

	public String getsetPincodeDlvTimeMapId(String pincodeId, String originCiyID) throws NonUniqueResultException {
		//String sql="SELECT PINCODE_DLV_TIME_MAP_ID FROM ff_d_pincode_product_serviceability WHERE pincode_id=? AND origin_city_id=? AND delivery_time IS NOT NULL  AND dlv_time_qualification='B' ;";
		String sql="SELECT PINCODE_DLV_TIME_MAP_ID FROM "+OpsmanDBFConstant.DATABASE_NAME_MASTER+"ff_d_pincode_product_serviceability WHERE pincode_id=? AND  delivery_time IS NOT NULL  AND (dlv_time_qualification='B' || dlv_time_qualification='A' ) ;";
		SQLQuery query = hibernateSessionFactory.getCurrentSession().createSQLQuery(sql);
		//System.out.println(pincodeId+" - "+originCiyID);
		query.setString(0, pincodeId);
		//query.setString(1, originCiyID);
		//System.out.println(query.toString());
		query.addScalar("PINCODE_DLV_TIME_MAP_ID",Hibernate.STRING);
		String id=null;
		try{
			id=(String) query.list().get(0);
		}catch(Exception e){
			e.getMessage();
			id="0";
		}
		return id;
		
		
	}

	public String getOfficeCode_OpsmanCode_from_UdaanCode(String opsmanCode) {
		String sql="SELECT u_office_code FROM "+OpsmanDBFConstant.DATABASE_NAME_MASTER+"ou_office WHERE o_office_code_1= ? ; ";
		SQLQuery query = hibernateSessionFactory.getCurrentSession().createSQLQuery(sql);
		//System.out.println("Office_code "+opsmanCode);
		query.setString(0, opsmanCode);
		query.addScalar("u_office_code",Hibernate.STRING);
		return (String) query.uniqueResult();
	}

	public String getBranchCodeFromId(String branchId) {
		String sql = "SELECT o_office_code FROM "+OpsmanDBFConstant.DATABASE_NAME_MASTER+"ou_office WHERE u_office_id = ?";
		SQLQuery query = hibernateSessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setString(0, branchId);
		query.addScalar("o_office_code", Hibernate.STRING);
		return (String) query.uniqueResult();
	}

	public String getOfficeCodeFromPincode(String pincode) {
		
		String sql = "SELECT office_code FROM "+OpsmanDBFConstant.DATABASE_NAME_MASTER+"ou_office_types WHERE Pincode = ? ; ";
		SQLQuery query = hibernateSessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setString(0, pincode);
		query.addScalar("office_code", Hibernate.STRING);
		String officeCode=null;
		try {
			officeCode=(String) query.uniqueResult();
		} catch (Exception e) {
			officeCode=(String) query.list().get(0);
		}
		return officeCode;
	}
	
	public String getOfficeTypeFromOfficeCode(String officeCode) {
		String sql = "SELECT office_type_code FROM "+OpsmanDBFConstant.DATABASE_NAME_MASTER+"ou_office_types WHERE office_code = ?";
		SQLQuery query = hibernateSessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setString(0, officeCode);
		query.addScalar("office_type_code", Hibernate.STRING);
		return (String) query.uniqueResult();
	}

	public String getCityCodeFromCityId(String cityId){
		String sql = "SELECT o_city_code FROM "+OpsmanDBFConstant.DATABASE_NAME_MASTER+"ou_city WHERE u_city_id = ?";
		SQLQuery query = hibernateSessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setString(0, cityId);
		query.addScalar("o_city_code", Hibernate.STRING);
		return (String) query.uniqueResult();
		
	}

	public String getOriginCityFromRegion(String regionAlpahabate) {
		String originCity=null;
		
		if("B".equalsIgnoreCase(regionAlpahabate)){
			originCity="BOY";
		}
		if("D".equalsIgnoreCase(regionAlpahabate)){
			originCity="DEI";
		}
		if("G".equalsIgnoreCase(regionAlpahabate)){
			originCity="SUT";
		}
		return originCity;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<RegionDTO> getRegionAlphabet()
	{
		logger.info("MasterDAO :: getRegionAlphabet ::  -----------------> Stat");
	List<RegionDTO> list=null;
	Session session = hibernateSessionFactory.openSession();
	try{
	Criteria criteria=session.createCriteria(RegionDTO.class);
	criteria.addOrder(Order.asc("regionName"));
	list=criteria.list();
	session.flush();
	}catch(Exception e){
		 logger.error("MasterDAO :: getRegionAlphabet :: "+e.getMessage());
	}
	finally{
		if(session !=null){
			logger.info("MasterDAO :: getRegionAlphabet ::  Seesion is closed properly ");
			session.close();
		}
		
	}
	logger.info("MasterDAO :: getRegionAlphabet ::  -----------------> End");
	return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<OfficeDTO> getRegionNames(String cityName)
	{
		logger.info("MasterDAO :: getRegionNames ::  -----------------> Stat");
	List<OfficeDTO> list=null;
	Session session = hibernateSessionFactory.openSession();
	try{
	Criteria criteria=session.createCriteria(OfficeDTO.class);
	criteria.add(Restrictions.eq("officeCode", cityName));
	criteria.addOrder(Order.asc("regionName"));
	list=criteria.list();
	session.flush();
	}catch(Exception e){
		 logger.error("MasterDAO :: getRegionNames :: "+e.getMessage());
	}
	finally{
		if(session != null){
			logger.info("MasterDAO :: getRegionNames ::  Seesion is  closed properly ");
			session.close();
		}
		
	}
	logger.info("MasterDAO :: getRegionNames ::  -----------------> End");
	return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<CityDTO> getCityByRegion()
	{
		logger.info("MasterDAO :: getCityByRegion ::  -----------------> Stat");
		List<CityDTO> cityList=null;
		Session session = hibernateSessionFactory.openSession();
		try{

	           Criteria criteria=session.createCriteria(CityDTO.class);
	           criteria.addOrder(Order.asc("cityName"));
	           cityList=criteria.list();
	           session.flush();
	         }catch(Exception e){
		         logger.error("MasterDAO :: getCityByRegion :: "+e.getMessage());
	        }
		      finally{
			      if(session !=null){
				   logger.info("MasterDAO :: getCityByRegion ::  Seesion is closed properly ");
			 	   session.close();
			   }
			
		 }
		logger.info("MasterDAO :: getCityByRegion ::  -----------------> End");
	    return cityList;
	}

	@SuppressWarnings("unchecked")
	public List<CityDTO> getCityByRegCode(String regCode) {
		logger.info("MasterDAO :: getCityByRegCode ::  -----------------> Stat");
		List<CityDTO> cityList=null;
		Session session = hibernateSessionFactory.openSession();
		try{
		Criteria criteria=session.createCriteria(CityDTO.class);
		//Criteria criteria=hibernateSessionFactory.getCurrentSession().createCriteria(CityDTO.class);
		criteria.add(Restrictions.eq("regCode", regCode));
		criteria.addOrder(Order.asc("cityName"));
		cityList=criteria.list();
		session.flush();
		}catch(Exception e){
			logger.error("MasterDAO :: getCityByRegCode ::  ----------------->"+e.getMessage());
		}
		finally{
			if(session !=null){
				logger.info("MasterDAO :: getCityByRegCode ::  Seesion is closed properly ");
				session.close();
			}
			
		}
		logger.info("MasterDAO :: getCityByRegCode ::  -----------------> Stat");
		return cityList;
	}
	
	public List<String> getCityCodeFromRegionCode(String regionCode){
		String sql="SELECT office_id from " +OpsmanDBFConstant.DATABASE_NAME_MASTER +"ff_d_office WHERE city_id IN " +
				" (SELECT city_id FROM " +OpsmanDBFConstant.DATABASE_NAME_MASTER+ "ff_d_city WHERE REGION =(SELECT REGION_ID FROM master_uat.dbo.ff_d_region " +
				" WHERE REGION_id=?))";
		
		//logger.info("getCityCodeFromRegionCode ==> start ");
		SQLQuery query = hibernateSessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setString(0, regionCode);
		query.addScalar("office_id",Hibernate.STRING);
		
		return query.list();
		
	}

	@SuppressWarnings("unchecked")
	public List<OfficeDO> getOfficesByCityId(Integer cityId) {
		logger.info("MasterDAO :: getOfficesByCityId ::  -----------------> Stat");
		List<OfficeDO> cityList=null;
		Session session = hibernateSessionFactory.openSession();
		try{
		Criteria criteria=session.createCriteria(OfficeDO.class);
		//Criteria criteria=hibernateSessionFactory.getCurrentSession().createCriteria(OfficeDO.class);
		criteria.add(Restrictions.eq("cityId", cityId));
		criteria.addOrder(Order.asc("officeName"));
		cityList=criteria.list();
		logger.info("MasterDAO :: getOfficesByCityId :: ------->"+cityList.toString());
		session.flush();
		}catch(Exception e){
			logger.error("MasterDAO :: getOfficesByCityId :: ------->"+e.getMessage());
		}
		finally{
			if(session !=null){
				logger.info("MasterDAO :: getOfficesByCityId ::  Seesion is closed properly ");
				session.close();
			}
			
		}
		logger.info("MasterDAO :: getOfficesByCityId ::  -----------------> End");
		return cityList;
	}

	public String getScriptCodeByOfficeCode(String originBranchCode) {
		//String sql = "SELECT branch_script FROM "+OpsmanDBFConstant.DATABASE_NAME_MASTER+"ff_d_office WHERE office_code=?";
		String sql ="SELECT branch_script FROM master_uat.dbo.ff_d_office WHERE office_code=?";
		SQLQuery query = hibernateSessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setString(0, originBranchCode);
		query.addScalar("branch_script", Hibernate.STRING);
		return (String) StringUtils.getOneObject(query.list());
	}

	@SuppressWarnings("unchecked")
	public List<OfficeDO> getHUBOffices(List<Integer>  cityId) {
		Criteria criteria=hibernateSessionFactory.getCurrentSession().createCriteria(OfficeDO.class);
		criteria.add(Restrictions.eq("officeType", Integer.valueOf(3)));
		if(!StringUtils.isEmptyColletion(cityId)){
			criteria.add(Restrictions.in("cityId", cityId));
		}
		
		return criteria.list();
	}

	public List<Integer> getCityesIdByRegionCode(String regCode) {
		String sql="SELECT  regCode FROM ff_d_city WHERE region =(SELECT REGION_ID FROM ff_d_region WHERE REGION_CODE='?')";
		SQLQuery query = hibernateSessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setString(0, regCode);
		query.addScalar("regCode", Hibernate.INTEGER);
		return query.list();
		
		
	}


	@SuppressWarnings("unchecked")
	public List<RegionDTO> getRegionAlphabetid(String region) {
		logger.info("MasterDAO :: getRegionAlphabetid ::  -----------------> Stat");
		List<RegionDTO> list=null;
	    Session session = hibernateSessionFactory.openSession();
	    try{
		Criteria criteria=session.createCriteria(RegionDTO.class);
		criteria.add(Restrictions.eq("regionCode",region));
		criteria.addOrder(Order.asc("regionName"));
		list=criteria.list();
		session.flush();
	    }catch(Exception e){
	    	logger.error("MasterDAO :: getRegionAlphabetid ::  -----------------> "+e.getMessage());
	    }
	    finally{
	    	if(session !=null){
	    		logger.info("MasterDAO :: getRegionAlphabetid ::  Seesion is  closed properly ");
	    		session.close();
	    	}
	    	
	    }
	    logger.info("MasterDAO :: getRegionAlphabetid ::  -----------------> End");
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<OfficeDO> getOfficeType(String officeType) {
		logger.info("MasterDAO :: getOfficeType ::  -----------------> Stat");
		List<OfficeDO> list=null;
		Session session=null;
		try{
		//String hql = "From com.ff.ud.domain.OfficeDO off where off.officeName = :officeName order by off.officeName";
		 //session = hibernateSessionFactory.openSession();
		 //Query query = session.createQuery(hql);
		 //query.setParameter("officeName", officeType);
		session = hibernateSessionFactory.openSession(); 
		Criteria criteria=session.createCriteria(OfficeDO.class);
		criteria.add(Restrictions.eq("officeCode",officeType));
		criteria.addOrder(Order.asc("officeName"));
		list=criteria.list();
		session.flush();
		}catch(Exception e){
			logger.error("MasterDAO :: getOfficeType :: "+e.getMessage());
		}
		finally{
			if(session !=null){
				logger.info("MasterDAO :: getOfficeType :: Seesion is  closed properly");
			  session.close();
			  
			}
		}
		logger.info("MasterDAO :: getOfficeType :: -----------------> End");
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<CityDO> getRHOName(String cityName) {
		logger.info("MasterDAO :: getRHOName :: -----------------> stated");
		List<CityDO> list=null;
		Session session = hibernateSessionFactory.openSession();
		try{
		Criteria criteria=session.createCriteria(CityDO.class);
		criteria.add(Restrictions.eq("regionId",Integer.valueOf(cityName)));
		criteria.addOrder(Order.asc("cityName"));
		list=criteria.list();
		session.flush();
		}catch(Exception e){
			logger.error("MasterDAO :: getRHOName :: "+e.getMessage());
		}
		finally{
			if(session !=null){
				logger.info("MasterDAO :: getRHOName :: Session was closed properly ");
				session.close();
			}
			
		}
		logger.info("MasterDAO :: getRHOName :: -----------------> End");
		return list;

	}

	@SuppressWarnings("unchecked")
	public List<CityDO> getCityNameForBranch(String cityNameOffice) {
		logger.info("MasterDAO :: getCityNameForBranch -----------------> stated");
		List<CityDO> list=null;
		Session session = hibernateSessionFactory.openSession();
		try{
		Criteria criteria=session.createCriteria(CityDO.class);
		criteria.add(Restrictions.eq("cityId",cityNameOffice));
		criteria.addOrder(Order.asc("cityName"));
		list=criteria.list();
		session.flush();
		}catch(Exception e){
			logger.error("MasterDAO :: getCityNameForBranch :: "+e.getMessage());
		}
		finally{
			if(session !=null){
				logger.info("MasterDAO :: getCityNameForBranch :: Session was closed properly ");
				session.close();
			}
			
		}
		logger.info("MasterDAO :: getCityNameForBranch -----------------> End");
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<OfficeDO> getBranchCode(String branchName) {
		logger.info("MasterDAO :: getBranchCode -----------------> State");
		List<OfficeDO> list=null;
		Session session = hibernateSessionFactory.openSession();
		try{
		Criteria criteria=session.createCriteria(OfficeDO.class);
		criteria.add(Restrictions.eq("officeName",branchName));
		criteria.addOrder(Order.asc("officeName"));
		list=criteria.list();
		session.flush();
		}catch(Exception e){
			logger.error("MasterDAO :: getBranchCode ----------------->"+e.getMessage());
		}
		finally{
			if(session !=null){
			logger.info("MasterDAO :: getBranchCode :: Session was closed properly ");
			session.close();
			}
		}
		logger.info("MasterDAO :: getBranchCode -----------------> End");
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<CityDO> getCityIdForRHOList(String cityCode) {
		logger.info("MasterDAO :: getCityIdForRHOList -----------------> State");
		List<CityDO> list=null;
		Session session = hibernateSessionFactory.openSession();
		try{
		
		Criteria criteria=session.createCriteria(CityDO.class);
		criteria.add(Restrictions.eq("regionId",Integer.valueOf(cityCode)));
		criteria.addOrder(Order.asc("cityName"));
		list=criteria.list();
		session.flush();
		}catch(Exception e){
			logger.error("MasterDAO :: getCityIdForRHOList -------------- >"+e.getMessage());
		}
		finally{
			if(session !=null){
				logger.info("MasterDAO :: getCityIdForRHOList :: Session was closed properly ");
				session.close();
				}
		}
		logger.info("MasterDAO :: getCityIdForRHOList -----------------> End");
		return list;
	}

	public void setHibernateSessionFactory(SessionFactory hibernateSessionFactory) {
		this.hibernateSessionFactory = hibernateSessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<ProductDO> getProductSeries() {
		logger.info("MasterDAO :: getProductSeries -----------------> State");
		List<ProductDO> list=null;
		Session session = hibernateSessionFactory.openSession();
		try{
		
		Criteria criteria=session.createCriteria(ProductDO.class);
		criteria.addOrder(Order.asc("consgSeries"));
		list=criteria.list();
		session.flush();
		}catch(Exception e){
			logger.error("MasterDAO :: getProductSeries -------------- >"+e.getMessage());
		}
		finally{
			if(session !=null){
				logger.info("MasterDAO :: getProductSeries :: Session was closed properly ");
				session.close();
				}
		}
		logger.info("MasterDAO :: getProductSeries -----------------> End");
		return list;
	}
	

	/*
	 * Reverse Logistic Client Request Report
	 */
	@SuppressWarnings("unchecked")
	public List<CustomerDO> getBussinessName() {
		logger.info("MasterDAO :: getBussinessName -----------------> State");
		List<CustomerDO> list=null;
		Session session = hibernateSessionFactory.openSession();
		try{
		
		Criteria criteria=session.createCriteria(CustomerDO.class);
		criteria.add(Restrictions.eq("customerType",6));
		criteria.addOrder(Order.asc("customerName"));
		list=criteria.list();
		session.flush();
		}catch(Exception e){
			logger.error("MasterDAO :: getBussinessName -------------- >"+e.getMessage());
		}
		finally{
			if(session !=null){
				logger.info("MasterDAO :: getBussinessName :: Session was closed properly ");
				session.close();
				}
		}
		logger.info("MasterDAO :: getBussinessName -----------------> End");
		return list;
	}
/*	
	 * Reverse Logistic Summary Report
	 
	
	@SuppressWarnings("unchecked")
	public List<CustomerDO> getClientSummaryList() {
		logger.info("MasterDAO :: getClientSummaryList -----------------> State");
		List<CustomerDO> list=null;
		Session session = hibernateSessionFactory.openSession();
		try{
		
		Criteria criteria=session.createCriteria(CustomerDO.class);
		criteria.add(Restrictions.eq("customerType",6));
		criteria.addOrder(Order.asc("customerName"));
		list=criteria.list();
		session.flush();
		}catch(Exception e){
			logger.error("MasterDAO :: getClientSummaryList -------------- >"+e.getMessage());
		}
		finally{
			session.close();
		}
		logger.info("MasterDAO :: getClientSummaryList -----------------> End");
		return list;
	}*/
	

	@SuppressWarnings("unchecked")
	public List<CityDTO> getCityCodeFromAllRegion() {
		logger.info("MasterDAO :: getCityCodeFromAllRegion ::  -----------------> Stat");
		List<CityDTO> cityList=null;
		Session session = hibernateSessionFactory.openSession();
		try{
		Criteria criteria=session.createCriteria(CityDTO.class);
		//Criteria criteria=hibernateSessionFactory.getCurrentSession().createCriteria(CityDTO.class);
		criteria.addOrder(Order.asc("cityName"));
		cityList=criteria.list();
		session.flush();
		}catch(Exception e){
			logger.error("MasterDAO :: getCityCodeFromAllRegion ::  ----------------->"+e.getMessage());
		}
		finally{
			if(session !=null){
				logger.info("MasterDAO :: getCityCodeFromAllRegion ::  Seesion is  closed properly ");
				session.close();
			}
			
		}
		logger.info("MasterDAO :: getCityCodeFromAllRegion ::  -----------------> Stat");
		return cityList;
	}

	@SuppressWarnings("unchecked")
	public List<AllFfMastDO> getOfficesByALL(Integer regCode) {
		logger.info("MasterDAO :: getOfficesByALL ::  -----------------> Stat");
		List<AllFfMastDO> officeList=null;
		Session session = hibernateSessionFactory.openSession();
		try{
		Criteria criteria=session.createCriteria(AllFfMastDO.class);
		criteria.add(Restrictions.eq("mappedToRegion",regCode));
		criteria.addOrder(Order.asc("officeName"));
		officeList=criteria.list();
		session.flush();
		}catch(Exception e){
			logger.error("MasterDAO :: getOfficesByALL ::  ----------------->"+e.getMessage());
		}
		finally{
			if(session !=null){
				logger.info("MasterDAO :: getOfficesByALL ::  Seesion is closed properly ");
				session.close();
			}
			
		}
		logger.info("MasterDAO :: getOfficesByALL ::  -----------------> Stat");
		return officeList;
	}

	@SuppressWarnings("unchecked")
	public List<OfficeDO> getCityCodeByStation(String regCode, String cityName) {
		logger.info("MasterDAO :: getCityCodeByStation ::  -----------------> Stat");
		List<OfficeDO> officeList=null;
		Session session = hibernateSessionFactory.openSession();
		try{
		Criteria criteria=session.createCriteria(OfficeDO.class);
		criteria.add(Restrictions.eq("officeCode",cityName));
		criteria.addOrder(Order.asc("officeName"));
		officeList=criteria.list();
		session.flush();
		}catch(Exception e){
			logger.error("MasterDAO :: getCityCodeByStation ::  ----------------->"+e.getMessage());
		}
		finally{
			if(session !=null){
				logger.info("MasterDAO :: getCityCodeByStation ::  Seesion is  closed properly ");
				session.close();
			}
			
		}
		logger.info("MasterDAO :: getCityCodeByStation ::  -----------------> Stat");
		return officeList;
	}

	@SuppressWarnings("unchecked")
	public List<CityDO> getCityIdByRegionStation(Integer cityCode) {
		logger.info("MasterDAO :: getCityIdByRegionStation ::  -----------------> Stat");
		List<CityDO> cityList=null;
		Session session = hibernateSessionFactory.openSession();
		try{
		Criteria criteria=session.createCriteria(CityDO.class);
		criteria.add(Restrictions.eq("cityId",String.valueOf(cityCode)));
		criteria.addOrder(Order.asc("cityName"));
		cityList=criteria.list();
		session.flush();
		}catch(Exception e){
			logger.error("MasterDAO :: getCityIdByRegionStation ::  ----------------->"+e.getMessage());
		}
		finally{
			if(session !=null){
				logger.info("MasterDAO :: getCityIdByRegionStation ::  Seesion is  closed properly ");
				session.close();
			}
			
		}
		logger.info("MasterDAO :: getCityCodeFromAllRegion ::  -----------------> Stat");
		return cityList;
	}
	
	@SuppressWarnings("unchecked")
	public List<CustomerDO> getCustomerName(Integer salesOffice) {
		logger.info("MasterDAO :: getCustomerName ::  -----------------> Stat");
		List<CustomerDO> customerList=null;
		Session session = hibernateSessionFactory.openSession();
		try{
		Criteria criteria=session.createCriteria(CustomerDO.class);
		criteria.add(Restrictions.eq("salesOffice",salesOffice));
		criteria.addOrder(Order.asc("customerName"));
		customerList=criteria.list();
		session.flush();
		}catch(Exception e){
			logger.error("MasterDAO :: getCustomerNAme ::  ----------------->"+e.getMessage());
		}
		finally{
			if(session !=null){
				logger.info("MasterDAO :: getCustomerANme ::  Seesion is  closed properly ");
				session.close();
			}
			
		}
		logger.info("MasterDAO :: getCustomerName ::  -----------------> Stat");
		return customerList;
	}

	@SuppressWarnings("unchecked")
	public List<OfficeDO> getOfficeIdFromOfficeCode(String officeCode) {
		logger.info("MasterDAO :: getOfficeIdFromOfficeCode ::  -----------------> Stat");
		List<OfficeDO> officeList=null;
		Session session = hibernateSessionFactory.openSession();
		try{
			Criteria criteria=session.createCriteria(OfficeDO.class);
			criteria.add(Restrictions.eq("officeCode",officeCode));
			criteria.addOrder(Order.asc("officeId"));
			officeList=criteria.list();
			session.flush();
			}catch(Exception e){
				logger.error("MasterDAO :: getOfficeIdFromOfficeCode ::  ----------------->"+e.getMessage());
			}
			finally{
				if(session !=null){
					logger.info("MasterDAO :: getOfficeIdFromOfficeCode ::  Seesion is  closed properly ");
					session.close();
				}
				
			}
			logger.info("MasterDAO :: getOfficeIdFromOfficeCode ::  -----------------> End");
			return officeList;
	}
	/**
	 * Mangesr
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AssignOfficeUserDTO> getUserAssignedOffices(Integer userId) {
		logger.info("MasterDAO :: getUserAssignedOffices ::  -----------------> Stat");
		Session session=null;
		List<AssignOfficeUserDTO> officeList=null;
		try{
		
		String sql = "SELECT ffd.office_code AS officeCode,ffd.office_id as officeId" +
				",ffd.office_name AS officeName FROM ff_d_office ffd LEFT JOIN  ff_d_user_office_rights ffu ON ffd.office_id=ffu.office_id WHERE ffu.user_id=" + userId;
		session= hibernateSessionFactory.openSession();
		Query query = session.createSQLQuery(sql);
		//query.setResultTransformer(Transformers.aliasToBean(OfficeDO.class));
		officeList=  query.list();
		}catch(Exception e){
			logger.error("MasterDAO :: getUserAssignedOffices ::  ----------------->"+e.getMessage());
		}
		finally{
			if(session !=null){
				logger.info("MasterDAO :: getUserAssignedOffices ::  Seesion is  closed properly ");
				session.close();
			}
			
		}
		logger.info("MasterDAO :: getUserAssignedOffices ::  -----------------> End");
		return officeList;
	}

	@SuppressWarnings("unchecked")
	public List<AllFfMastDO> getOfficeCodeForAllOriginOffice(Integer regionId) {
		logger.info("MasterDAO :: getOfficeCodeForAllOriginOffice ::  -----------------> Stat");
		List<AllFfMastDO> officeList=null;
		Session session = hibernateSessionFactory.openSession();
		try{
		Criteria criteria=session.createCriteria(AllFfMastDO.class);
		criteria.add(Restrictions.ne("mappedToRegion", regionId));
		criteria.addOrder(Order.asc("officeName"));
		officeList=criteria.list();
		
		logger.error("MasterDAO :: getOfficeCodeForAllOriginOffice :: ------->"+officeList.toString());
		session.flush();
		}catch(Exception e){
			logger.error("MasterDAO :: getOfficeCodeForAllOriginOffice :: ------->"+e.getMessage());
		}
		finally{
			if(session !=null){
				logger.info("MasterDAO :: getOfficeCodeForAllOriginOffice ::  Seesion was closed properly ");
				session.close();
			}
			
		}
		logger.info("MasterDAO :: getOfficesByCityId ::  -----------------> End");
		return officeList;
	}

	@SuppressWarnings("unchecked")
	public List<AllFfMastDO> getRegionIdForRegionCode(String cityName) {
		
		
		logger.info("MasterDAO :: getRegionIdForRegionCode ::  -----------------> Stat");
		List<AllFfMastDO> officeList=null;
		
		Session session = hibernateSessionFactory.openSession();
		try{
		Criteria criteria=session.createCriteria(AllFfMastDO.class);
		if("Corporate Office".equalsIgnoreCase(cityName)){
			criteria.add(Restrictions.ne("officeName", cityName));
		}else{
			criteria.add(Restrictions.eq("officeName", cityName));
		}
		
		criteria.addOrder(Order.asc("officeName"));
		//officeList=criteria.list();
		officeList = criteria.list();
		
		logger.error("MasterDAO :: getRegionIdForRegionCode :: ------->"+officeList.toString());
		session.flush();
		}catch(Exception e){
			logger.error("MasterDAO :: getRegionIdForRegionCode :: ------->"+e.getMessage());
		}
		finally{
			if(session !=null){
				logger.info("MasterDAO :: getRegionIdForRegionCode ::  Seesion was closed properly ");
				session.close();
			}
			
		}
		logger.info("MasterDAO :: getOfficesByCityId ::  -----------------> End");
		return officeList;
		
		/*logger.info("MasterDAO :: getRegionIdForRegionCode ::  -----------------> Stat");
		String sql = "SELECT MAPPED_TO_REGION FROM ff_d_office where office_name = '"+cityName+"' ";
		Session session = hibernateSessionFactory.openSession();
		Query query = session.createSQLQuery(sql);
		//query.setResultTransformer(Transformers.aliasToBean(OfficeDO.class));
		Integer officeList=  (Integer) query.uniqueResult();
	
		System.out.println("getUserAssignedOffices =====>"+officeList);
		logger.info("MasterDAO :: getRegionIdForRegionCode ::  -----------------> end");
		return officeList;*/
			
		}

	@SuppressWarnings("unchecked")
	public List<AllFfMastDO> getOfficesByAllHUb(Integer regCode) {
		logger.info("MasterDAO :: getOfficesByALL ::  -----------------> Stat");
		List<AllFfMastDO> officeList=null;
		Session session = hibernateSessionFactory.openSession();
		try{
		Criteria criteria=session.createCriteria(AllFfMastDO.class);
		criteria.add(Restrictions.eq("mappedToRegion",regCode));
		criteria.add(Restrictions.eq("officeTypeId","3"));
		criteria.addOrder(Order.asc("officeName"));
		officeList=criteria.list();
		session.flush();
		}catch(Exception e){
			logger.error("MasterDAO :: getOfficesByAllHUb ::  ----------------->"+e.getMessage());
		}
		finally{
			if(session !=null){
				logger.info("MasterDAO :: getOfficesByAllHUb ::  Seesion was closed properly ");
				session.close();
			}
			
		}
		logger.info("MasterDAO :: getOfficesByAllHUb ::  -----------------> Stat");
		return officeList;
	}

	@SuppressWarnings("unchecked")
	public List<OfficeDO> getOfficesByCityIdForHubNames(Integer regCode) {
		logger.info("MasterDAO :: getOfficesByCityIdForHub ::  -----------------> Stat");
		List<OfficeDO> cityList=null;
		Session session = hibernateSessionFactory.openSession();
		try{
		Criteria criteria=session.createCriteria(OfficeDO.class);
		//Criteria criteria=hibernateSessionFactory.getCurrentSession().createCriteria(OfficeDO.class);
		criteria.add(Restrictions.eq("cityId", regCode));
		criteria.add(Restrictions.eq("officeType", Integer.valueOf("3")));
		criteria.addOrder(Order.asc("officeName"));
		cityList=criteria.list();
		logger.error("MasterDAO :: getOfficesByCityIdForHub :: ------->"+cityList.toString());
		session.flush();
		}catch(Exception e){
			logger.error("MasterDAO :: getOfficesByCityIdForHub :: ------->"+e.getMessage());
		}
		finally{
			if(session !=null){
				logger.info("MasterDAO :: getOfficesByCityIdForHub ::  Seesion was closed properly ");
				session.close();
			}
			
		}
		logger.info("MasterDAO :: getOfficesByCityId ::  -----------------> End");
		return cityList;
	}

	@SuppressWarnings("unchecked")
	public List<OfficeDO> getOfficesByAllHUbByCityCode(Integer cityId) {
		
		logger.info("MasterDAO :: getOfficesByAllHUbByCityCode ::  -----------------> Stat");
		List<OfficeDO> officeList=null;
		Session session = hibernateSessionFactory.openSession();
		try{
		Criteria criteria=session.createCriteria(OfficeDO.class);
		criteria.add(Restrictions.eq("cityId",cityId));
		criteria.add(Restrictions.eq("officeType",Integer.valueOf("3")));
		criteria.addOrder(Order.asc("officeName"));
		officeList=criteria.list();
		session.flush();
		}catch(Exception e){
			logger.error("MasterDAO :: getOfficesByAllHUbByCityCode ::  ----------------->"+e.getMessage());
		}
		finally{
			if(session !=null){
				logger.info("MasterDAO :: getOfficesByAllHUbByCityCode ::  Seesion was closed properly ");
				session.close();
			}
			
		}
		logger.info("MasterDAO :: getOfficesByAllHUbByCityCode ::  -----------------> Stat");
		return officeList;
	
	}
	
		
		/*String sql = "SELECT region_id,region_code,region_name, as cityId FROM ff_d_region";
		Session session = hibernateSessionFactory.openSession();
		Query query = session.createSQLQuery(sql);
		//query.setResultTransformer(Transformers.aliasToBean(OfficeDO.class));
		List<RegionDO> officeList =  query.list();
		System.out.println("getUserAssignedOffices =====>"+officeList);
		return officeList;*/
	/*
	 * this method use for getting only hubs based on station or city selection
	 */
	@SuppressWarnings("unchecked")
	public List<OfficeDO> getOfficesByCityIdForHub(Integer regCode) {
		logger.info("MasterDAO :: getOfficesByCityIdForHub ::  -----------------> Stat");
		List<OfficeDO> cityList=null;
		
		Session session = hibernateSessionFactory.openSession();
		try{
		Integer	officeTypeId=3;
		Criteria criteria=session.createCriteria(OfficeDO.class);
		//Criteria criteria=hibernateSessionFactory.getCurrentSession().createCriteria(OfficeDO.class);
		criteria.add(Restrictions.eq("cityId", regCode));
		criteria.add(Restrictions.eq("officeType", officeTypeId));
		criteria.addOrder(Order.asc("officeName"));
		cityList=criteria.list();
		logger.error("MasterDAO :: getOfficesByCityIdForHub :: ------->"+cityList.toString());
		session.flush();
		}catch(Exception e){
			logger.error("MasterDAO :: getOfficesByCityIdForHub :: ------->"+e.getMessage());
		}
		finally{
			if(session !=null){
				logger.info("MasterDAO :: getOfficesByCityIdForHub ::  Seesion was closed properly ");
				session.close();
			}
			
		}
		logger.info("MasterDAO :: getOfficesByCityId ::  -----------------> End");
		return cityList;
	}
	/**
	 * Manager Login City Names
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<OfficeRightsDTO> getBranchAllocationForManager(Integer userId) {
		logger.info("MasterDAO :: getBranchAllocationForManager ::  -----------------> Stat");
		
		List<OfficeRightsDTO> officeList=null;
		Session session=null;
		try{
		String sql = "SELECT ffc.city_id AS cityId,ffc.city_name As cityName FROM ff_d_city ffc JOIN "+
		             "(SELECT DISTINCT ffd.city_id FROM ff_d_user_office_rights ufr "+
				     "JOIN  ff_d_office ffd ON ufr.office_id=ffd.office_id "+
		             "WHERE user_id='"+userId+"' and ufr.status='A' )a " +
				     "ON a.city_id=ffc.city_id ";
	
		session= hibernateSessionFactory.openSession();
	        SQLQuery query = session.createSQLQuery(sql);
	
	query.setResultTransformer(Transformers.aliasToBean(OfficeRightsDTO.class));
	
	officeList=query.list();
	
		}catch(Exception e){
		logger.error("MasterDAO :: getBranchAllocationForManager :: ------->"+e.getMessage());
	}
	finally{
		if(session !=null){
			logger.info("MasterDAO :: getBranchAllocationForManager ::  Seesion was closed properly ");
			session.close();
		}
		
	}
	
	logger.info("MasterDAO :: getBranchAllocationForManager ::  -----------------> End");
		
		return officeList;//(OfficeRightsDTO) StringUtils.getOneObject(query.list());
	}

	public List<OfficeRightsDTO> getCityAllocationForManager(List<OfficeRightsDTO> rightOfofficeDTO) {
		logger.info("MasterDAO :: getCityAllocationForManager ::  -----------------> Stat");
		List<RightsForUserDO> upsOfficeList=null;
		
		Session session = hibernateSessionFactory.openSession();
		try{
			String user=null;
			String office=null;
		Criteria criteria=session.createCriteria(OfficeDO.class);
		criteria.add(Restrictions.in("officeId", rightOfofficeDTO));
		criteria.addOrder(Order.asc("officeId"));
		upsOfficeList=criteria.list();
		logger.error("MasterDAO :: getCityAllocationForManager :: ------->"+upsOfficeList.toString());
		session.flush();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("MasterDAO :: getCityAllocationForManager :: ------->"+e.getMessage());
		}
		finally{
			if(session !=null){
				logger.info("MasterDAO :: getCityAllocationForManager ::  Seesion was closed properly ");
				session.close();
			}
			
		}
		logger.info("MasterDAO :: getCityAllocationForManager ::  -----------------> End");
		
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<OfficeCodeManagerDTO> getOfficeNamesForManager(Integer userId) {
		logger.info("MasterDAO :: getOfficeNamesForManager ::  -----------------> Stated");
		
		Session session=null;
		List<OfficeCodeManagerDTO> officeList=null;
		try{
		String sql = "SELECT DISTINCT ffd.office_id AS officeId,ffd.office_name AS officeName,ffd.office_code AS officeCode FROM ff_d_user_office_rights ufr "+
		             "JOIN  ff_d_office ffd ON ufr.office_id=ffd.office_id "+
				     "WHERE user_id='"+userId+"' AND ufr.status='A' ORDER BY officeName";

		session = hibernateSessionFactory.openSession();
           SQLQuery query = session.createSQLQuery(sql);

             query.setResultTransformer(Transformers.aliasToBean(OfficeCodeManagerDTO.class));

       
             officeList=query.list();
		}catch(Exception e){
			//e.printStackTrace();
			logger.error("MasterDAO :: getOfficeNamesForManager :: ------->"+e.getMessage());
		}
		finally{
			if(session !=null){
				logger.info("MasterDAO :: getOfficeNamesForManager ::  Seesion was closed properly ");
				session.close();
			}
			
		}
           logger.info("MasterDAO :: getOfficeNamesForManager ::  -----------------> end");
		return officeList;
	}

	@SuppressWarnings("unchecked")
	public List<CustomerDO> getCustomerCodeByNames() {
		logger.info("MasterDAO :: getCustomerCodeByNames ::  -----------------> Stat");
		List<CustomerDO> custList=null;
		
		Session session = hibernateSessionFactory.openSession();
		try{
			Object[] customerCode={78662,78805,79032};
		Criteria criteria=session.createCriteria(CustomerDO.class);
		criteria.add(Restrictions.in("customerId",customerCode));
		criteria.addOrder(Order.asc("customerName"));
		custList=criteria.list();
		logger.error("MasterDAO :: getCustomerCodeByNames :: ------->"+custList.toString());
		session.flush();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("MasterDAO :: getCustomerCodeByNames :: ------->"+e.getMessage());
		}
		finally{
			if(session !=null){
				logger.info("MasterDAO :: getCustomerCodeByNames ::  Seesion was closed properly ");
				session.close();
			}
			
		}
		logger.info("MasterDAO :: getCustomerCodeByNames ::  -----------------> End");
		
		return custList;
	}
/*
 * This is use for BA customer
 */
	@SuppressWarnings("unchecked")
	public List<CustomerDO> getBACustomerName(Integer salesOffice) {
		logger.info("MasterDAO :: getCustomerName ::  -----------------> Stat");
		List<CustomerDO> customerList=null;
	
		ArrayList list=new ArrayList();
		          list.add(9);
		          list.add(10);
		Session session = hibernateSessionFactory.openSession();
		try{
		Criteria criteria=session.createCriteria(CustomerDO.class);
		criteria.add(Restrictions.eq("salesOffice",salesOffice));
		criteria.add(Restrictions.in("customerType",list));
		criteria.addOrder(Order.asc("customerName"));
		
		
		customerList=criteria.list();
		session.flush();
		}catch(Exception e){
			logger.error("MasterDAO :: getCustomerNAme ::  ----------------->"+e.getMessage());
		}
		finally{
			if(session !=null){
				logger.info("MasterDAO :: getCustomerANme ::  Seesion was closed properly ");
				session.close();
			}
			
		}
		logger.info("MasterDAO :: getCustomerName ::  -----------------> Stat");
		return customerList;
	}

	
	@SuppressWarnings("unchecked")
	public List<PinckUpDeliveryDTO> getCustomerIdFromPickUpLocation(Integer offId,String officeCode) {
		logger.info("MasterDAO :: getOfficeNamesForManager ::  -----------------> Stated");
		Session session=null;
		List<PinckUpDeliveryDTO> officeList=null;
		try{
		String sql = "SELECT DISTINCT(fdc.customer_code)AS customer,fdc.customer_id AS customerId,fdc.business_name AS businessName,fdc.customer_code AS customerCode FROM ff_d_pickup_delivery_contract fdp "+
		             "JOIN ff_d_customer fdc ON fdc.customer_id=fdp.customer_id " +
				     " JOIN ff_d_office fdo ON fdo.office_id=fdp.office_id "+
		             "WHERE fdo.office_code in ('"+officeCode+"')  ORDER BY businessName ";
				    

          session = hibernateSessionFactory.openSession();
           SQLQuery query = session.createSQLQuery(sql);

             query.setResultTransformer(Transformers.aliasToBean(PinckUpDeliveryDTO.class));

           officeList=query.list();
           
		}catch(Exception e){
			logger.error("MasterDAO :: getCustomerIdFromPickUpLocation ::  -----------------> "+e.getMessage());
	    }
	    finally{
	    	if(session !=null){
	    		logger.info("MasterDAO :: getCustomerIdFromPickUpLocation ::  Seesion was closed properly ");
	    		session.close();
	    	}
	    	
	    }
           logger.info("MasterDAO :: getOfficeNamesForManager ::  -----------------> end");
		return officeList;
	}

	

	/*
	 * This is for Region allocation for manager
	 */
		@SuppressWarnings("unchecked")
		public List<RegionRightsDTO> getRegionAllocationForManager(Integer regionId) {
			logger.info("MasterDAO :: getRegionAllocationForManager ::  -----------------> Stat");
			Session session=null;
			List<RegionRightsDTO> regionList=null;
			try{
			String sql = " SELECT region_id AS regionCode,region_name AS regionName  FROM ff_d_region WHERE region_id IN (SELECT mapped_to_region FROM ff_d_office "+
				     " WHERE office_id IN(SELECT office_id FROM `ff_d_user_office_rights` ur WHERE  "+
				     " ur.user_id IN (SELECT user_id FROM ff_d_user WHERE user_id='"+regionId+"'))) ";

		        session = hibernateSessionFactory.openSession();
		        SQLQuery query = session.createSQLQuery(sql);
		        
		        query.setResultTransformer(Transformers.aliasToBean(RegionRightsDTO.class));
		   
		      
		        regionList=query.list();
		       
			}catch(Exception e){
				logger.error("MasterDAO :: getRegionAllocationForManager ::  -----------------> "+e.getMessage());
		    }
		    finally{
		    	if(session !=null){
		    		logger.info("MasterDAO :: getRegionAllocationForManager ::  Seesion was closed properly ");
		    		session.close();
		    	}
		    	
		    }
		        logger.info("MasterDAO :: getRegionAllocationForManager ::  -----------------> End");
			
		        return regionList;
		}
	
	/*
	 * This method for multiple region allocation for manager
	 */
	
	@SuppressWarnings("unchecked")
	public List<RegionDTO> getRegionAlphabetidForRegion(List<RegionRightsDTO> region) {
		logger.info("MasterDAO :: getRegionAlphabetidForRegion ::  -----------------> Stat");
		List<RegionDTO> list=null;
		ArrayList<String> regionList=new ArrayList<String>();
		
		for(RegionRightsDTO regionid:region)
		   {
			RegionRightsDTO dto=new RegionRightsDTO();
			dto.setRegionCode(regionid.getRegionCode());
			
			regionList.add(String.valueOf(dto.getRegionCode()));
			
	     	}
		
	    Session session = hibernateSessionFactory.openSession();
	
	    try{
		Criteria criteria=session.createCriteria(RegionDTO.class);
		criteria.add(Restrictions.in("regionCode",regionList));
		criteria.addOrder(Order.asc("regionName"));
		
		list=criteria.list();
		session.flush();
	    }catch(Exception e){
	    	logger.error("MasterDAO :: getRegionAlphabetidForRegion ::  -----------------> "+e.getMessage());
	    }
	    finally{
	    	if(session !=null){
	    		logger.info("MasterDAO :: getRegionAlphabetidForRegion ::  Seesion was closed properly ");
	    		session.close();
	    	}
	    	
	    }
	    logger.info("MasterDAO :: getRegionAlphabetidForRegion ::  -----------------> End");
	  
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<PinckUpDeliveryDTO> getCustomerIdFromPickUpLocationForAll(String allValue) {
		logger.info("MasterDAO :: getOfficeNamesForManager ::  -----------------> Stated");
		
		System.out.println("getCustomerIdFromPickUpLocationForAll values ========>"+allValue);
		
		Session session=null;
		List<PinckUpDeliveryDTO> officeList=null;
		try{
			int officeCodeLength=allValue.length();
			if(officeCodeLength >= 10 ){
				
				String[]  office=allValue.split(",");
				
				 ArrayList<String> list = new ArrayList<String>();
				 for (int i = 0; i < office.length; i++) {
					 list.add("'" + office[i] + "'"); 
		           }
			
				 String off=list.toString().replace("[","").replace("]","").replace(",",",");
			     System.out.println("values ========>"+off);
			     
				  String sql ="Select DISTINCT(a.customerCode)AS customer,a.customerId,a.businessName,a.customerCode from "+ 
						   "(SELECT DISTINCT fdp.office_id AS officeId,fdc.customer_id AS customerId,fdc.business_name AS businessName, " +
						     "fdc.customer_code AS customerCode FROM  ff_d_pickup_delivery_contract fdp JOIN "+
						     "ff_d_office fdo ON fdo.office_id=fdp.office_id JOIN ff_d_customer fdc ON fdc.customer_id=fdp.customer_id "+
				             "WHERE fdo.office_code IN ("+off+") AND fdc.business_name IS NOT NULL " +
				             "AND fdc.customer_id IS NOT NULL  ORDER BY businessName)a "+
				             "GROUP BY businessName  ";
						    

		            session = hibernateSessionFactory.openSession();
		           SQLQuery query = session.createSQLQuery(sql);

		             query.setResultTransformer(Transformers.aliasToBean(PinckUpDeliveryDTO.class));

		             officeList=query.list();
				
			}else{
		  String sql ="Select DISTINCT(a.customerCode)AS customer,a.customerId,a.businessName,a.customerCode from "+ 
				   "(SELECT DISTINCT fdp.office_id AS officeId,fdc.customer_id AS customerId,fdc.business_name AS businessName, " +
				     "fdc.customer_code AS customerCode FROM  ff_d_pickup_delivery_contract fdp JOIN "+
				     "ff_d_office fdo ON fdo.office_id=fdp.office_id JOIN ff_d_customer fdc ON fdc.customer_id=fdp.customer_id "+
		             "WHERE fdo.city_id IN ('"+allValue+"')  AND fdc.business_name IS NOT NULL " +
		             "AND fdc.customer_id IS NOT NULL  ORDER BY businessName)a "+
		             "GROUP BY businessName  ";
				    

            session = hibernateSessionFactory.openSession();
           SQLQuery query = session.createSQLQuery(sql);

             query.setResultTransformer(Transformers.aliasToBean(PinckUpDeliveryDTO.class));

             officeList=query.list();
             
			}
		 
		
		
		}catch(Exception e){}
		finally{
   	    	if(session !=null){
   	    		logger.info("MasterDAO :: getOfficeNamesForManager ::  Seesion was closed properly ");
   	    		session.close();
   	    	}
   	    	
		}
           logger.info("MasterDAO :: getOfficeNamesForManager ::  -----------------> end");
		return officeList;
	}


	@SuppressWarnings("unchecked")
	public List<PinckUpDeliveryDTO> getHubIdFormCustomerNames(Integer hubOffice,String officeCode) {
		logger.info("MasterDAO :: getHubIdFormCustomerNames ::  -----------------> Stated");
		/*String sql = "SELECT fdp.office_id AS officeId,fdc.customer_id AS customerId,fdc.business_name AS businessName,fdc.customer_code AS customerCode "+
		             "FROM ff_d_pickup_delivery_contract fdp "+
				     "JOIN ff_d_customer fdc ON fdc.customer_id=fdp.customer_id "+
		             "JOIN ff_d_office fdo ON fdp.office_id=fdo.office_id "+
				     "WHERE fdo.reporting_hub IN ('"+hubOffice+"') AND fdc.business_name IS NOT NULL  ORDER BY businessName";*/
		List<PinckUpDeliveryDTO> officeList=null;
		Session session=null;
		try{
		String sql="SELECT DISTINCT(a.customerCode)AS customer,a.customerId,a.businessName,a.customerCode FROM "+
		            "(SELECT DISTINCT fdp.office_id AS officeId,fdc.customer_id AS customerId,fdc.business_name AS businessName,fdc.customer_code AS customerCode,fdp.contract_id "+
				    "FROM ff_d_pickup_delivery_contract fdp JOIN ff_d_customer fdc ON fdc.customer_id=fdp.customer_id "+
		            "JOIN ff_d_office fdo ON fdp.office_id=fdo.office_id OR fdp.office_id=fdo.reporting_hub WHERE " +
		            "fdo.reporting_hub IN ('"+hubOffice+"') AND fdc.business_name IS NOT NULL "+
				    " ORDER BY fdp.contract_id DESC,businessName )a "+
		            "ORDER BY businessName";
				    

            session = hibernateSessionFactory.openSession();
           SQLQuery query = session.createSQLQuery(sql);

             query.setResultTransformer(Transformers.aliasToBean(PinckUpDeliveryDTO.class));

       
          
           officeList =query.list();
           if(StringUtils.isEmptyColletion(officeList)){
        	   officeList=getCustomerIdFromPickUpLocation(hubOffice,officeCode);
           }
		}catch(Exception e){}
           finally{
   	    	if(session !=null){
   	    		logger.info("MasterDAO :: getHubIdFormCustomerNames ::  Seesion was closed properly ");
   	    		session.close();
   	    	}
   	    	
   	    }
           
           logger.info("MasterDAO :: getHubIdFormCustomerNames ::  -----------------> end");
		
		return officeList;
	}

	@SuppressWarnings("unchecked")
	public List<PinckUpDeliveryDTO> getHubIdbyOfficeCode(String officeCode) {
		logger.info("MasterDAO :: getHubIdbyOfficeCode ::  -----------------> Stated");
		Session session=null;
		List<PinckUpDeliveryDTO>officeList=null;
		try{
		String sql = "Select office_id as officeId from ff_d_office where office_code in ('"+officeCode+"') AND office_type_id='3'";
				    
      
		session= hibernateSessionFactory.openSession();
           SQLQuery query = session.createSQLQuery(sql);

             query.setResultTransformer(Transformers.aliasToBean(PinckUpDeliveryDTO.class));

           officeList= query.list();
          
		}catch(Exception e){
			logger.error("MasterDAO :: getHubIdbyOfficeCode :: ======>",e);
		}finally{
   	    	if(session !=null){
   	    		logger.info("MasterDAO :: getHubIdbyOfficeCode ::  Seesion was closed properly ");
   	    		session.close();
   	    	}
		}
           logger.info("MasterDAO :: getHubIdbyOfficeCode ::  -----------------> end");
		return officeList;
	}

	@SuppressWarnings("unchecked")
	public List<PinckUpDeliveryDTO> getRHOIdbyOfficeCode(String officeCode) {
		logger.info("MasterDAO :: getRHOIdbyOfficeCode ::  -----------------> Stated");
		
		Session session=null;
		List<PinckUpDeliveryDTO>officeList=null;
		try{
		String sql = "Select city_id as officeId from ff_d_office where office_code in ('"+officeCode+"') AND office_type_id='2'";
				    

            session = hibernateSessionFactory.openSession();
           SQLQuery query = session.createSQLQuery(sql);

             query.setResultTransformer(Transformers.aliasToBean(PinckUpDeliveryDTO.class));
           officeList= query.list();
		}catch(Exception e){
			logger.error("MasterDAO :: getRHOIdbyOfficeCode :: ======>",e);
		}
		finally{
   	    	if(session !=null){
   	    		logger.info("MasterDAO :: getRHOIdbyOfficeCode ::  Seesion was closed properly ");
   	    		session.close();
   	    	}
		}
           logger.info("MasterDAO :: getRHOIdbyOfficeCode ::  -----------------> end");
		return officeList;

	
	}

	@SuppressWarnings("unchecked")
	public List<PinckUpDeliveryDTO> getRHODetailsByCustomerNames(Integer offId) {
		logger.info("MasterDAO :: getRHODetailsByCustomerNames ::  -----------------> Stated");
		List<PinckUpDeliveryDTO> officeList=null;
		Session session=null;
		try{
		String sql="SELECT DISTINCT(a.customerCode)AS customer,a.customerId,a.businessName,a.customerCode FROM "+
		            "(SELECT DISTINCT fdo.office_id AS officeId,fdc.customer_id AS customerId,fdc.business_name AS businessName, "+
				    "fdc.customer_code AS customerCode FROM ff_d_office fdo JOIN "+
		            "ff_d_pickup_delivery_contract fdp ON fdp.office_id=fdo.office_id "+
				    "JOIN ff_d_customer fdc ON fdc.customer_id=fdp.customer_id "+
		            "WHERE fdo.city_id in ('"+offId+"') AND fdc.business_name IS NOT NULL ORDER BY businessName)a "+
				    "ORDER BY businessName ";
				    

            session = hibernateSessionFactory.openSession();
           SQLQuery query = session.createSQLQuery(sql);

             query.setResultTransformer(Transformers.aliasToBean(PinckUpDeliveryDTO.class));
           officeList =query.list();
		}catch(Exception e){}
           finally{
   	    	if(session !=null){
   	    		logger.info("MasterDAO :: getRHODetailsByCustomerNames ::  Seesion was closed properly ");
   	    		session.close();
   	    	}
   	    	
   	    }
           
           logger.info("MasterDAO :: getRHODetailsByCustomerNames ::  -----------------> end");
		
		return officeList;
	}

	@SuppressWarnings("unchecked")
	public List<VendorDetailsDTO> getVendorCustomerType() {
		logger.info("MasterDAO :: getVendorCustomerType ::  -----------------> Stated");
		List<VendorDetailsDTO> officeList=null;
		Session session=null;
		
		/*String sql="SELECT vendor_type_id AS vendorId,vendor_type_desc AS vendorName FROM ff_d_vendor_type WHERE vendor_type_id IN (2,6,7)" +
		            "ORDER BY vendor_type_desc DESC ";*/
			
			 session = hibernateSessionFactory.openSession();
			try{
				Object[] vendorId={2,5,6,7,12};
			Criteria criteria=session.createCriteria(VendorDetailsDTO.class);
			criteria.add(Restrictions.in("vendorTypeId",vendorId));
			criteria.addOrder(Order.asc("vendorTypeName"));
			officeList=criteria.list();
			
			session.flush();
		}catch(Exception e){
			logger.error("MasterDAO :: getVendorCustomerType =================> ",e);
		}
           finally{
   	    	if(session !=null){
   	    		logger.info("MasterDAO :: getVendorCustomerType ::  Seesion was closed properly ");
   	    		session.close();
   	    	}
   	    	
   	    }
           
           logger.info("MasterDAO :: getVendorCustomerType ::  -----------------> end");
		
		return officeList;
		
	}

	@SuppressWarnings("unchecked")
	public List<vendorDTO> getVendorDetailsByVendorId(String vendorId,String officeCode) {
		logger.info("MasterDAO :: getVendorDetailsByVendorId ::  -----------------> Stated");
		List<vendorDTO> vendorList=null;
		Session session=null;
		try{
		String sql="SELECT fdv.vendor_id AS vendorId,fdv.vendor_type AS vendorType,fdv.vendor_code AS vendorCode, "+
		           "fdv.first_name AS vendorName FROM ff_d_vendor_office_map fdf  "+
				"JOIN ff_d_vendor_region_map fdvr ON fdvr.vendor_region_map_id=fdf.vendor_office_region_id "+
		           "JOIN ff_d_vendor fdv ON fdv.vendor_id=fdvr.vendor_id "+
				"WHERE office_id in (SELECT office_id FROM ff_d_office WHERE office_code IN('"+officeCode+"')) AND fdv.vendor_type in ("+vendorId+") ORDER BY vendorName ";
			
		   session = hibernateSessionFactory.openSession();
            SQLQuery query = session.createSQLQuery(sql);
          query.setResultTransformer(Transformers.aliasToBean(vendorDTO.class));

       
          vendorList =query.list();
		}catch(Exception e){
			logger.error("MasterDAO :: getVendorDetailsByVendorId =================> ",e);
		}
           finally{
   	    	if(session !=null){
   	    		session.close();
   	    		logger.info("MasterDAO :: getVendorDetailsByVendorId ::  Seesion was closed properly ");
   	    		
   	    	}
   	    	
   	    }
           
           logger.info("MasterDAO :: getVendorDetailsByVendorId ::  -----------------> end");
		
		return vendorList;
	}
	/*
	 * this method use for get customer from productId and officeCode
	 */

	@SuppressWarnings("unchecked")
	public List<PinckUpDeliveryDTO> getCutomerDetailsproductId(String productId,String officeCode) {
		logger.info("MasterDAO :: getCutomerDetailsproductId ::  -----------------> Stated");
		
	/*	String ecomConsg1 =ecomConsg.toString().replace("[","'").replace("]","'")
		        .replace(",","','");*/
		
		String[]  office=officeCode.split(",");
		
		 ArrayList<String> list = new ArrayList<String>();
		 for (int i = 0; i < office.length; i++) {
			 list.add("'" + office[i] + "'"); 
	        }
		
		 String off=list.toString().replace("[","").replace("]","").replace(",",",");
       
		List<PinckUpDeliveryDTO> customerList=null;
		Session session=null;
		System.out.println(productId+"-----"+officeCode);
		try{
		/*String sql="SELECT a.customerId,a.businessName,a.customerCode FROM ff_d_pickup_delivery_contract fdpc "+
"JOIN (SELECT DISTINCT ffc.customer_id AS customerId,ffc.customer_code AS customerCode,ffc.business_name AS businessName FROM ff_d_customer ffc "+
"JOIN ff_d_financial_product_series_customer_type_map fps ON ffc.customer_type=fps.customer_type "+
"JOIN ff_d_financial_product fdp ON fdp.financial_product_id=fps.financial_product "+
"WHERE fdp.financial_product_id IN('"+productId+"') )a "+
"ON a.customerId=fdpc.customer_id AND fdpc.office_id IN(SELECT office_id FROM ff_d_office WHERE office_code IN("+off+") ) ORDER BY businessName";*/
		
			String sql="SELECT a.CUSTOMER_ID AS customerId,a.CUSTOMER_CODE AS customerCode,fn_convert_in_upper(a.BUSINESS_NAME) AS businessName FROM "+
					"(SELECT fdc.CUSTOMER_ID,fdc.CUSTOMER_CODE,fdc.BUSINESS_NAME "+
					"FROM ff_d_office fdo JOIN ff_d_pickup_delivery_contract fdpdc ON fdo.OFFICE_ID=fdpdc.OFFICE_ID "+
					"JOIN ff_d_pickup_delivery_location fdpdl ON fdpdc.CONTRACT_ID=fdpdl.CONTRACT_ID "+
					"JOIN ff_d_contract_payment_billing_location fdcpbl ON fdpdl.LOCATION_ID=fdcpbl.PICKUP_DELIVERY_LOCATION "+
					"JOIN ff_d_rate_contract fdrc ON fdpdc.CUSTOMER_ID=fdrc.CUSTOMER "+
					"JOIN ff_d_customer fdc ON fdrc.CUSTOMER=fdc.CUSTOMER_ID "+
					"JOIN ff_d_customer_type fdct ON fdc.CUSTOMER_TYPE=fdct.CUSTOMER_TYPE_ID "+
					"JOIN ff_d_product_customer_type_map fdpctm ON fdpctm.CUSTOMER_TYPE_ID=fdct.CUSTOMER_TYPE_ID "+
					"JOIN ff_d_product fdp ON fdp.PRODUCT_ID=fdpctm.PRODUCT_ID "+
					"JOIN ff_d_financial_product_series_customer_type_map fdfpsctm ON  "+
					"(fdfpsctm.PRODUCT=fdpctm.PRODUCT_ID AND fdfpsctm.CUSTOMER_TYPE=fdct.CUSTOMER_TYPE_ID) "+
					"JOIN ff_d_financial_product fdfp ON fdfp.FINANCIAL_PRODUCT_ID = fdfpsctm.FINANCIAL_PRODUCT "+
					"WHERE  fdo.OFFICE_ID IN (SELECT OFFICE_ID FROM ff_d_office WHERE OFFICE_CODE IN ("+off+")) "+ 
					"AND fdfp.FINANCIAL_PRODUCT_ID IN ('"+productId+"') AND fdfpsctm.STATUS='A' "+
					"AND fdc.CUR_STATUS='A' AND ((fdcpbl.LOCATION_OPERATION_TYPE IN ('B','BP') AND fdct.CUSTOMER_TYPE_CODE IN ('CR','CC','LC','CD','GV','FR') AND "+
					"fdrc.TYPE_OF_BILLING = 'CBCP') OR (fdcpbl.LOCATION_OPERATION_TYPE IN ('B','BP') AND fdct.CUSTOMER_TYPE_CODE IN ('CR','CC','LC','CD','GV','FR') AND "+
					"fdrc.TYPE_OF_BILLING IN ('DBDP','DBCP')) OR "+
					"(fdcpbl.LOCATION_OPERATION_TYPE = 'BP' AND fdct.CUSTOMER_TYPE_CODE = 'RL'))"+
					"GROUP BY fdc.CUSTOMER_ID,fdc.CUSTOMER_CODE,fdc.BUSINESS_NAME  "+
					"UNION ALL "+
					"SELECT fdc.CUSTOMER_ID,fdc.CUSTOMER_CODE,fdc.BUSINESS_NAME FROM ff_d_office fdo "+
					"JOIN ff_d_customer fdc ON fdc.OFFICE_MAPPED_TO= fdo.OFFICE_ID "+
					"JOIN ff_d_customer_type fdct ON fdct.CUSTOMER_TYPE_ID=fdc.CUSTOMER_TYPE "+
					"JOIN ff_d_product_customer_type_map fdpctm ON fdct.CUSTOMER_TYPE_ID=fdpctm.CUSTOMER_TYPE_ID "+
					"JOIN ff_d_product fdp ON fdpctm.PRODUCT_ID=fdp.PRODUCT_ID "+
					"JOIN ff_d_financial_product_series_customer_type_map fdpsctm ON fdpsctm.PRODUCT=fdp.PRODUCT_ID AND fdpsctm.CUSTOMER_TYPE=fdct.CUSTOMER_TYPE_ID "+
					"JOIN ff_d_financial_product fffp "+
					"ON fffp.FINANCIAL_PRODUCT_ID = fdpsctm.FINANCIAL_PRODUCT "+
					"WHERE  fdc.CUR_STATUS='A' "+
					"AND fdct.CUSTOMER_TYPE_CODE IN ('BA','BV','AC')  "+
					"AND fffp.STATUS='A'  AND fdpsctm.STATUS='A' "+
					"AND fdo.OFFICE_ID IN (SELECT OFFICE_ID FROM ff_d_office WHERE OFFICE_CODE IN ("+off+")) "+
					"AND fffp.FINANCIAL_PRODUCT_ID IN ('"+productId+"') "+
					"GROUP BY fdc.CUSTOMER_ID,fdc.CUSTOMER_CODE,fdc.BUSINESS_NAME ) a ORDER BY a.BUSINESS_NAME "; 
			
		   session = hibernateSessionFactory.openSession();
		   
            SQLQuery query = session.createSQLQuery(sql);

            query.setResultTransformer(Transformers.aliasToBean(PinckUpDeliveryDTO.class));

       
          customerList =query.list();
          
        
		}catch(Exception e){
			logger.error("MasterDAO :: getCutomerDetailsproductId =================> ",e);
		  }
           finally{
   	    	if(session !=null){
   	    		logger.info("MasterDAO :: getCutomerDetailsproductId ::  Seesion was closed properly ");
   	    		session.close();
   	    	}
   	    	
   	    }
           
           logger.info("MasterDAO :: getCutomerDetailsproductId ::  -----------------> end");
		
		return customerList;
	}
	
	/*
	 * This method returns financial product name
	 */
			
	@SuppressWarnings("unchecked")
	public List<FinancialProductDO> getFinacialProductName() {
		logger.info("MasterDAO :: getFinacialProductName -----------------> State");
		List<FinancialProductDO> list=null;
		Session session = hibernateSessionFactory.openSession();
		try{
		
		Criteria criteria=session.createCriteria(FinancialProductDO.class);
		criteria.addOrder(Order.asc("productName"));
		list=criteria.list();
		session.flush();
		}catch(Exception e){
			logger.error("MasterDAO :: getFinacialProductName -------------- >"+e.getMessage());
		}
		 finally{
	   	    	if(session !=null){
	   	    		logger.info("MasterDAO :: getFinacialProductName ::  Seesion was closed properly ");
	   	    		session.close();
	   	    	}
	   	    	
	   	    }
		logger.info("MasterDAO :: getFinacialProductName -----------------> End");
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<PinckUpDeliveryDTO> getEmployeeNameByFiledStaff(String vendorId, String officeCode) {
		logger.info("MasterDAO :: getEmployeeNameByFiledStaff ::  -----------------> Stated");
		List<PinckUpDeliveryDTO> vendorList=null;
		Session session=null;
		
		String[]  office=officeCode.split(",");
		
		 ArrayList<String> list = new ArrayList<String>();
		 for (int i = 0; i < office.length; i++) {
			 list.add("'" + office[i] + "'"); 
	        }
		
		 String off=list.toString().replace("[","").replace("]","").replace(",",",");
		 System.out.println(off+"-----"+officeCode);
		try{
		String sql="SELECT  EMPLOYEE_ID AS officeId,FIRST_NAME AS customer,last_name AS businessName FROM ff_d_employee fde "+
		           "WHERE office IN(SELECT office_id FROM ff_d_office WHERE office_code in ("+off+")) ";
			
		   session = hibernateSessionFactory.openSession();
            SQLQuery query = session.createSQLQuery(sql);
          query.setResultTransformer(Transformers.aliasToBean(PinckUpDeliveryDTO.class));

       
          vendorList =query.list();
		}catch(Exception e){
			logger.error("MasterDAO :: getEmployeeNameByFiledStaff =================> ",e);
		}
           finally{
   	    	if(session !=null){
   	    		session.close();
   	    		logger.info("MasterDAO :: getEmployeeNameByFiledStaff ::  Seesion was closed properly ");
   	    		
   	    	}
   	    	
   	    }
           
           logger.info("MasterDAO :: getEmployeeNameByFiledStaff ::  -----------------> end");
		
		return vendorList;
	}

	public List<vendorDTO> getAllBranchForVendor(String vendorId,String officeCode) {
		logger.info("MasterDAO :: getAllBranchForVendor ::  -----------------> Stated");
		List<vendorDTO> vendorList=null;
    	Session session=null;
		
		String[]  office=officeCode.split(",");
		
		 ArrayList<String> list = new ArrayList<String>();
		 for (int i = 0; i < office.length; i++) {
			 list.add("'" + office[i] + "'"); 
        }
	
		 String off=list.toString().replace("[","").replace("]","").replace(",",",");
	 System.out.println(off+"-----"+officeCode);
		try{
		String sql="SELECT DISTINCT fdv.vendor_id AS vendorId,fdv.vendor_type AS vendorType,fdv.vendor_code AS vendorCode, "+
		           "fdv.first_name AS vendorName FROM ff_d_vendor_office_map fdf  "+
			"JOIN ff_d_vendor_region_map fdvr ON fdvr.vendor_region_map_id=fdf.vendor_office_region_id "+
	           "JOIN ff_d_vendor fdv ON fdv.vendor_id=fdvr.vendor_id "+
				"WHERE office_id in (SELECT office_id FROM ff_d_office WHERE office_code IN("+off+")) AND fdv.vendor_type in ("+vendorId+") ORDER BY vendorName  ";
		
	   session = hibernateSessionFactory.openSession();
            SQLQuery query = session.createSQLQuery(sql);
          query.setResultTransformer(Transformers.aliasToBean(vendorDTO.class));

         vendorList =query.list();
	}catch(Exception e){
			logger.error("MasterDAO :: getAllBranchForVendor =================> ",e);
	}
          finally{
  	    	if(session !=null){
 	    		session.close();
 	    		logger.info("MasterDAO :: getAllBranchForVendor ::  Seesion was closed properly ");
  	    	}
 	    	
  	    }
           
           logger.info("MasterDAO :: getAllBranchForVendor ::  -----------------> end");
	
		return vendorList;
	}

	public List<PinckUpDeliveryDTO> getCustomerNameByRegion(String allValesForRegion) {
     logger.info("MasterDAO :: getCustomerNameByRegion ::  -----------------> Stated");
		
		Session session=null;
		List<PinckUpDeliveryDTO> officeList=null;
		try{
		String sql ="Select DISTINCT(a.customerCode)AS customer,a.customerId,a.businessName,a.customerCode from "+ 
				   "(SELECT DISTINCT fdp.office_id AS officeId,fdc.customer_id AS customerId,fdc.business_name AS businessName, " +
				     "fdc.customer_code AS customerCode FROM  ff_d_pickup_delivery_contract fdp JOIN "+
				     "ff_d_office fdo ON fdo.office_id=fdp.office_id JOIN ff_d_customer fdc ON fdc.customer_id=fdp.customer_id "+
		             "WHERE fdo.mapped_to_region IN ('"+allValesForRegion+"')  AND fdc.business_name IS NOT NULL " +
		             "AND fdc.customer_id IS NOT NULL  ORDER BY businessName)a "+
		             "GROUP BY businessName  ";
				    

            session = hibernateSessionFactory.openSession();
           SQLQuery query = session.createSQLQuery(sql);

             query.setResultTransformer(Transformers.aliasToBean(PinckUpDeliveryDTO.class));

       
           
		 officeList=query.list();
		}catch(Exception e){}
		finally{
   	    	if(session !=null){
   	    		logger.info("MasterDAO :: getCustomerNameByRegion ::  Seesion was closed properly ");
   	    		session.close();
   	    	}
   	    	
		}
           logger.info("MasterDAO :: getCustomerNameByRegion ::  -----------------> end");
		return officeList;
	}

	@SuppressWarnings("unchecked")
	public List<AllFfMastDO> getAllRegionAllOffice() {
		logger.info("MasterDAO :: getAllRegionAllOffice ::  -----------------> Stat");
		List<AllFfMastDO> officeList=null;
		Session session = hibernateSessionFactory.openSession();
		try{
		Criteria criteria=session.createCriteria(AllFfMastDO.class);
		criteria.addOrder(Order.asc("officeName"));
		officeList=criteria.list();
		session.flush();
		}catch(Exception e){
			logger.error("MasterDAO :: getAllRegionAllOffice ::  ----------------->"+e.getMessage());
		}
		finally{
			if(session !=null){
				logger.info("MasterDAO :: getAllRegionAllOffice ::  Seesion is closed properly ");
				session.close();
			}
			
		}
		logger.info("MasterDAO :: getAllRegionAllOffice ::  -----------------> Stat");
		return officeList;
	}

	public List<PinckUpDeliveryDTO> getForAllLCCODCustomer() {
     logger.info("MasterDAO :: getForAllLCCODCustomer ::  -----------------> Stated");
		
		Session session=null;
		List<PinckUpDeliveryDTO> officeList=null;
		try{
		String sql ="Select DISTINCT(a.customerCode)AS customer,a.customerId,a.businessName,a.customerCode from "+ 
				   "(SELECT DISTINCT fdp.office_id AS officeId,fdc.customer_id AS customerId,fdc.business_name AS businessName, " +
				     "fdc.customer_code AS customerCode FROM  ff_d_pickup_delivery_contract fdp JOIN "+
				     "ff_d_office fdo ON fdo.office_id=fdp.office_id JOIN ff_d_customer fdc ON fdc.customer_id=fdp.customer_id "+
		             "WHERE fdc.customer_type IN (7,8)  AND fdc.business_name IS NOT NULL " +
		             "AND fdc.customer_id IS NOT NULL  ORDER BY businessName)a "+
		             "GROUP BY businessName  ";
				    

            session = hibernateSessionFactory.openSession();
           SQLQuery query = session.createSQLQuery(sql);

             query.setResultTransformer(Transformers.aliasToBean(PinckUpDeliveryDTO.class));

       
           
		 officeList=query.list();
		}catch(Exception e){}
		finally{
   	    	if(session !=null){
   	    		logger.info("MasterDAO :: getForAllLCCODCustomer ::  Seesion was closed properly ");
   	    		session.close();
   	    	}
   	    	
		}
           logger.info("MasterDAO :: getForAllLCCODCustomer ::  -----------------> end");
		return officeList;
	}

	public List<PinckUpDeliveryDTO> getLcCodHUbDetails(Integer hubOffice,String officeCode) {
		logger.info("MasterDAO :: getLcCodHUbDetails ::  -----------------> Stated");
		
		List<PinckUpDeliveryDTO> officeList=null;
		Session session=null;
		try{
		String sql="SELECT DISTINCT(a.customerCode)AS customer,a.customerId,a.businessName,a.customerCode FROM "+
		            "(SELECT DISTINCT fdp.office_id AS officeId,fdc.customer_id AS customerId,fdc.business_name AS businessName,fdc.customer_code AS customerCode,fdp.contract_id "+
				    "FROM ff_d_pickup_delivery_contract fdp JOIN ff_d_customer fdc ON fdc.customer_id=fdp.customer_id "+
		            "JOIN ff_d_office fdo ON fdp.office_id=fdo.office_id OR fdp.office_id=fdo.reporting_hub WHERE " +
		            "fdo.reporting_hub IN ('"+hubOffice+"') AND fdc.customer_type IN (7,8) AND fdc.customer_code IS NOT NULL AND fdc.business_name IS NOT NULL "+
				    " ORDER BY fdp.contract_id DESC,businessName )a "+
		            "ORDER BY businessName";
				    

            session = hibernateSessionFactory.openSession();
           SQLQuery query = session.createSQLQuery(sql);

             query.setResultTransformer(Transformers.aliasToBean(PinckUpDeliveryDTO.class));

       
          
           officeList =query.list();
           if(StringUtils.isEmptyColletion(officeList)){
        	   officeList=getLCCodBranchCustomerId(hubOffice,officeCode);
           }
		}catch(Exception e){}
           finally{
   	    	if(session !=null){
   	    		logger.info("MasterDAO :: getLcCodHUbDetails ::  Seesion was closed properly ");
   	    		session.close();
   	    	}
   	    	
   	    }
           
           logger.info("MasterDAO :: getLcCodHUbDetails ::  -----------------> end");
		
		return officeList;
	}

	
	
	
   @SuppressWarnings("unchecked")
   public List<PinckUpDeliveryDTO> getLCCODRHOByCustomerNames(Integer officeCode) {
	logger.info("MasterDAO :: getOfficeNamesForManager ::  -----------------> Stated");
	Session session=null;
	List<PinckUpDeliveryDTO> officeList=null;
	try{
	String sql = "SELECT DISTINCT(fdc.customer_code)AS customer,fdc.customer_id AS customerId,fdc.business_name AS businessName,fdc.customer_code AS customerCode FROM ff_d_pickup_delivery_contract fdp "+
	             "JOIN ff_d_customer fdc ON fdc.customer_id=fdp.customer_id " +
			     " JOIN ff_d_office fdo ON fdo.office_id=fdp.office_id "+
	             "WHERE fdo.office_code in ('"+officeCode+"') AND fdc.customer_type IN (7,8) AND fdc.customer_code IS NOT NULL  ORDER BY businessName ";
			    

      session = hibernateSessionFactory.openSession();
       SQLQuery query = session.createSQLQuery(sql);

         query.setResultTransformer(Transformers.aliasToBean(PinckUpDeliveryDTO.class));

       officeList=query.list();
       
	}catch(Exception e){
		logger.error("MasterDAO :: getCustomerIdFromPickUpLocation ::  -----------------> "+e.getMessage());
    }
    finally{
    	if(session !=null){
    		logger.info("MasterDAO :: getCustomerIdFromPickUpLocation ::  Seesion was closed properly ");
    		session.close();
    	}
    	
    }
	
       logger.info("MasterDAO :: getOfficeNamesForManager ::  -----------------> end");
	return officeList;
	}
   @SuppressWarnings("unchecked")
	public List<PinckUpDeliveryDTO> getLCCodBranchCustomerId(Integer offId,String officeCode) {
		logger.info("MasterDAO :: getLCCodBranchCustomerId ::  -----------------> Stated");
		Session session=null;
		List<PinckUpDeliveryDTO> officeList=null;
		try{
		String sql = "SELECT DISTINCT(fdc.customer_code)AS customer,fdc.customer_id AS customerId,fdc.business_name AS businessName,fdc.customer_code AS customerCode FROM ff_d_pickup_delivery_contract fdp "+
		             "JOIN ff_d_customer fdc ON fdc.customer_id=fdp.customer_id " +
				     " JOIN ff_d_office fdo ON fdo.office_id=fdp.office_id "+
		             "WHERE fdo.office_code in ('"+officeCode+"')  AND fdc.customer_type IN (7,8)  ORDER BY businessName ";
				    

         session = hibernateSessionFactory.openSession();
          SQLQuery query = session.createSQLQuery(sql);

            query.setResultTransformer(Transformers.aliasToBean(PinckUpDeliveryDTO.class));

          officeList=query.list();
          
		}catch(Exception e){
			logger.error("MasterDAO :: getLCCodBranchCustomerId ::  -----------------> "+e.getMessage());
	    }
	    finally{
	    	if(session !=null){
	    		logger.info("MasterDAO :: getLCCodBranchCustomerId ::  Seesion was closed properly ");
	    		session.close();
	    	}
	    	
	    }
          logger.info("MasterDAO :: getLCCodBranchCustomerId ::  -----------------> end");
		return officeList;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<PinckUpDeliveryDTO> getCustomerNamesForAllLCCOD(String officeCode) {
		logger.info("MasterDAO :: getCustomerNamesForAllLCCOD ::  -----------------> Stated");
		
		System.out.println("getCustomerIdFromPickUpLocationForAll values ========>"+officeCode);
		
		String[]  office=officeCode.split(",");
		
		 ArrayList<String> list = new ArrayList<String>();
		 for (int i = 0; i < office.length; i++) {
			 list.add("'" + office[i] + "'"); 
       }
	
		 String off=list.toString().replace("[","").replace("]","").replace(",",",");
	 System.out.println("values ========>"+off);
		
		
		Session session=null;
		List<PinckUpDeliveryDTO> officeList=null;
		try{
		String sql ="Select DISTINCT(a.customerCode)AS customer,a.customerId,a.businessName,a.customerCode from "+ 
				   "(SELECT DISTINCT fdp.office_id AS officeId,fdc.customer_id AS customerId,fdc.business_name AS businessName, " +
				     "fdc.customer_code AS customerCode FROM  ff_d_pickup_delivery_contract fdp JOIN "+
				     "ff_d_office fdo ON fdo.office_id=fdp.office_id JOIN ff_d_customer fdc ON fdc.customer_id=fdp.customer_id "+
		             "WHERE fdo.office_code IN ("+off+")  AND fdc.business_name IS NOT NULL AND fdc.customer_type IN (7,8) " +
		             "AND fdc.customer_id IS NOT NULL  ORDER BY businessName)a "+
		             "GROUP BY businessName  ";
				    

	       session = hibernateSessionFactory.openSession();
	      SQLQuery query = session.createSQLQuery(sql);

	        query.setResultTransformer(Transformers.aliasToBean(PinckUpDeliveryDTO.class));

	  
	      
		 officeList=query.list();
		}catch(Exception e){}
		finally{
		    	if(session !=null){
		    		logger.info("MasterDAO :: getCustomerNamesForAllLCCOD ::  Seesion was closed properly ");
		    		session.close();
		    	}
		    	
		}

	      logger.info("MasterDAO :: getCustomerNamesForAllLCCOD ::  -----------------> end");
		return officeList;
		
	}
	

	@SuppressWarnings("unchecked")
	public List<MonthAndDateDTO> getMonthWiseDateFormate(String values) {
		logger.info("MasterDAO :: getMonthWiseDateFormate -----------------> State");
		List<MonthAndDateDTO> list=null;
		Session session = hibernateSessionFactory.openSession();
		try{
			String sql="SELECT DISTINCT DATE(FROM_DATE) as startdate   FROM ff_f_bill WHERE MONTH(FROM_DATE) in ("+values+") " +
					"AND DATE(FROM_DATE)>=DATE_SUB(NOW(),INTERVAL 4 MONTH) ORDER BY DATE(FROM_DATE) ASC;";
			
		   session = hibernateSessionFactory.openSession();
	            SQLQuery query = session.createSQLQuery(sql);
	          query.setResultTransformer(Transformers.aliasToBean(MonthAndDateDTO.class));

	          list =query.list();
	          
	          logger.info("MasterDAO :: getMonthWiseDateFormate ----------------->  List Size "+list.size());
	          
		}catch(Exception e){
			logger.error("MasterDAO :: getMonthWiseDateFormate -------------- >"+e.getMessage());
		}
		 finally{
	   	    	if(session !=null){
	   	    		logger.info("MasterDAO :: getMonthWiseDateFormate ::  Seesion was closed properly ");
	   	    		session.close();
	   	    	}
	   	    	
	   	    }
		logger.info("MasterDAO :: getMonthWiseDateFormate -----------------> End");
		return list;
	}

	public List<MonthAndDateDTO> getMonthWiseDateEndDay(String startValues) {
		logger.info("MasterDAO :: getMonthWiseDateEndDay -----------------> State");
		List<MonthAndDateDTO> list=null;
		Session session = hibernateSessionFactory.openSession();
		try{
			String sql="SELECT DISTINCT DATE(TO_DATE) as enddate FROM ff_f_bill WHERE MONTH(TO_DATE) IN("+startValues+") AND DATE(TO_DATE)>=DATE_SUB(NOW()," +
					"INTERVAL 4 MONTH) ORDER BY DATE(TO_DATE) ASC";
			
		   session = hibernateSessionFactory.openSession();
	            SQLQuery query = session.createSQLQuery(sql);
	          query.setResultTransformer(Transformers.aliasToBean(MonthAndDateDTO.class));

	          list =query.list();
	          
	          logger.info("MasterDAO :: getMonthWiseDateEndDay ----------------->  List Size "+list.size());
	          
		}catch(Exception e){
			logger.error("MasterDAO :: getMonthWiseDateEndDay -------------- >"+e.getMessage());
		}
		 finally{
	   	    	if(session !=null){
	   	    		logger.info("MasterDAO :: getMonthWiseDateEndDay ::  Seesion was closed properly ");
	   	    		session.close();
	   	    	}
	   	    	
	   	    }
		logger.info("MasterDAO :: getMonthWiseDateEndDay -----------------> End");
		return list;
	}

	public List<PinckUpDeliveryDTO> getCustomerNamesForLCCODToPayFromRegion(String region) {

		logger.info("MasterDAO :: getCustomerNamesForAllLCCOD ::  -----------------> Stated");
		
		System.out.println("getCustomerNamesForLCCODToPayFromRegion values ========>"+region);
		
		String[]  office=region.split(",");
		
		 ArrayList<String> list = new ArrayList<String>();
		 for (int i = 0; i < office.length; i++) {
			 list.add("'" + office[i] + "'"); 
       }
	
		 String off=list.toString().replace("[","").replace("]","").replace(",",",");
	 System.out.println("values ========>"+off);
		
		
		Session session=null;
		List<PinckUpDeliveryDTO> officeList=null;
		try{
		String sql ="SELECT DISTINCT(a.customerCode)AS customer,a.customerId,a.businessName,a.customerCode FROM "+  
					"(SELECT DISTINCT fdp.office_id AS officeId,fdc.customer_id AS customerId,fdc.business_name AS businessName, "+  
					"fdc.customer_code AS customerCode FROM  ff_d_pickup_delivery_contract fdp JOIN "+ 
					"ff_d_office fdo ON fdo.office_id=fdp.office_id JOIN ff_d_customer fdc ON fdc.customer_id=fdp.customer_id "+ 
					"WHERE fdo.mapped_to_region IN ("+off+") AND fdc.business_name IS NOT NULL AND fdc.customer_type IN (4,3,7,8) "+ 
					"AND fdc.customer_id IS NOT NULL  ORDER BY businessName)a "+ 
					"GROUP BY businessName";
				    

	       session = hibernateSessionFactory.openSession();
	      SQLQuery query = session.createSQLQuery(sql);

	        query.setResultTransformer(Transformers.aliasToBean(PinckUpDeliveryDTO.class));

	  
	      
		 officeList=query.list();
		}catch(Exception e){}
		finally{
		    	if(session !=null){
		    		logger.info("MasterDAO :: getCustomerNamesForLCCODToPayFromRegion ::  Seesion was closed properly ");
		    		session.close();
		    	}
		    	
		}

	      logger.info("MasterDAO :: getCustomerNamesForLCCODToPayFromRegion ::  -----------------> end");
		return officeList;

	}

	
	
	
	@SuppressWarnings("unchecked")
	public List<PinckUpDeliveryDTO> getCustomerListByRegionId(String regionId) {
		logger.info("MasterDAO :: getCustomerListByRegionId ::  -----------------> Stated");
		
		List<PinckUpDeliveryDTO> customerList=null;
		Session session=null;
		try{

			/*String sql=     "SELECT  DISTINCT customerdo3_.CUSTOMER_ID AS customerId,"+
				       
				       "customerdo3_.CUSTOMER_CODE AS customerCode,"+
				       "customerdo3_.BUSINESS_NAME AS businessName"+
				      
				     " FROM   ff_d_pickup_delivery_contract pickupdeli0_ "+
				   "INNER JOIN  ff_d_customer customerdo3_ "+
				           "ON pickupdeli0_.CUSTOMER_ID=customerdo3_.CUSTOMER_ID CROSS "+
				   "JOIN  ff_d_contract_payment_billing_location contractpa1_ CROSS "+
				   "JOIN    ff_d_pickup_delivery_location pickupdeli2_ CROSS "+
				   "JOIN   ff_d_office officedo4_ CROSS "+
				   "JOIN    ff_d_customer customerdo5_ CROSS "+
				   "JOIN    ff_d_customer_type customerty6_ "+
				   "WHERE   pickupdeli0_.OFFICE_ID=officedo4_.OFFICE_ID "+
				       "AND pickupdeli0_.CUSTOMER_ID=customerdo5_.CUSTOMER_ID "+
				       "AND customerdo5_.CUSTOMER_TYPE=customerty6_.CUSTOMER_TYPE_ID "+
				       "AND officedo4_.MAPPED_TO_REGION="+regionId+" "+
				       "AND pickupdeli0_.CONTRACT_ID=pickupdeli2_.CONTRACT_ID "+
				       "AND pickupdeli2_.LOCATION_ID=contractpa1_.PICKUP_DELIVERY_LOCATION "+
				       "AND ( contractpa1_.LOCATION_OPERATION_TYPE IN ( 'P' , 'BP' ) ) "+
				       "AND ( customerty6_.CUSTOMER_TYPE_CODE IN ( 'CD','LC'  )  )";*/
			
			String sql= "SELECT  fdc.CUSTOMER_ID AS customerId,fdc.CUSTOMER_CODE  AS customerCode,fdc.BUSINESS_NAME  AS businessName"+
			            " From ff_d_pickup_delivery_contract fdp Join ff_d_customer fdc ON fdc.CUSTOMER_ID=fdp.CUSTOMER_ID"+ 
				        " Join ff_d_office fdo ON fdp.OFFICE_ID=fdo.OFFICE_ID"+
		                "	Where fdc.CUSTOMER_CODE is not NULL"+
				"  And fdo.CITY_ID in (Select CITY_ID from ff_d_city Where REGION="+regionId+")"+
				"  And fdc.customer_type in (7,8)";
			
			logger.info("MasterDAO :: getChequeNumbersByCustomerId ::  -----------------> sql::: "+sql);
		   session = hibernateSessionFactory.openSession();
		   
            SQLQuery query = session.createSQLQuery(sql);

            query.setResultTransformer(Transformers.aliasToBean(PinckUpDeliveryDTO.class));

       
          customerList =query.list();
          
        
		}catch(Exception e){
			logger.error("MasterDAO :: getCustomerListByRegionId =================> ",e);
		  }
           finally{
   	    	if(session !=null){
   	    		logger.info("MasterDAO :: getCustomerListByRegionId ::  Seesion was closed properly ");
   	    		session.close();
   	    	}
   	    	
   	    }
           
           logger.info("MasterDAO :: getCustomerListByRegionId ::  -----------------> end");
		
		return customerList;
	}
	
	
	public List<ChequeDetailsDTO> getChequeNumbersByCustomerId(String customerId,String fromDate,String toDate){
		logger.info("MasterDAO :: getChequeNumbersByCustomerId ::  -----------------> Stated");
		List<ChequeDetailsDTO> ChequeDetailsDTOList=null;
		List<ChequeDetailsDTO> ChequeDetailsDTOList1=null;
		
		Session session=null;
		try{
          
		   session = hibernateSessionFactory.openSession();
		   
         
            	String sql1 = " SELECT LIABILITY_ID AS liabilityId , region_id AS regionId , customer_id AS customerId, cheque_no AS chequeNumber , bank_id AS bankId"+
                 " FROM  ff_f_liability_payment   WHERE  CUSTOMER_ID IN ("+customerId+")   AND "+
                   "DATE BETWEEN '"+fromDate+"' AND '"+toDate+"'";
            	
            	logger.info("MasterDAO :: getChequeNumbersByCustomerId ::  -----------------> sql::: "+sql1);
            	 SQLQuery query1 = session.createSQLQuery(sql1);

                 query1.setResultTransformer(Transformers.aliasToBean(ChequeDetailsDTO.class));

            
                 ChequeDetailsDTOList1 =query1.list();
            	if(null != ChequeDetailsDTOList1 && ChequeDetailsDTOList1.size() > 0){
            		ChequeDetailsDTOList = new ArrayList<ChequeDetailsDTO>();
            		for (ChequeDetailsDTO chequeDetailsDTO : ChequeDetailsDTOList1){
            			if(null != chequeDetailsDTO && null != chequeDetailsDTO.getChequeNumber()){
            				ChequeDetailsDTOList.add(chequeDetailsDTO);
            			}
            		}
            	}
            
          
        
		}catch(Exception e){
			logger.error("MasterDAO :: getCustomerListByRegionId =================> ",e);
		  }
           finally{
   	    	if(session !=null){
   	    		logger.info("MasterDAO :: getCustomerListByRegionId ::  Seesion was closed properly ");
   	    		session.close();
   	    	}
   	    	
   	    }
           
           logger.info("MasterDAO :: getCustomerListByRegionId ::  -----------------> end");
		
		
		return ChequeDetailsDTOList;
	}
}



