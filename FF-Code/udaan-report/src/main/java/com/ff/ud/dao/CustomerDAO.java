package com.ff.ud.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ff.ud.dto.CustomerDTO;
import com.ff.ud.utils.OpsmanSQLUtils;


@Repository
@Transactional
public class CustomerDAO {
	
	      @Autowired 
	      @Qualifier(value="dbServer")
	      private SessionFactory sessionFactory;
	
	      static Logger logger = Logger.getLogger(CustomerDAO.class);
	
	
	      @SuppressWarnings("unchecked")
   	      public List<CustomerDTO> getCustomerData(String region,String originCity,String destinatioCity, String product){
	    	  
	    	  logger.info("getCustomerData() ==>  Fetching Customer data ===> region : "+region+"  product : "+product);
	    	  
	    	  String selectClause= "SELECT TOP 10 ORIGIN AS origin,BK_DATE AS bkDate,CON_NO AS conNo,SCRIPT AS script,NAME AS name,DOOR_NO AS doorNo,STREET AS street," +
	    	  		              "AREA AS area,PINCODE AS pincode,PHONE AS phone,MOBILE AS mobile,CNEE_PHONE AS cneePhone,CNEE_LAND AS cneeLand,APPRO_BY AS approBy," +
	    	  		              "DISCOUNT AS discount,WEBUPD AS webupd,FILENAME AS filename,BK_MODE AS bkMode,DT_DBF AS dtdbf,DR_UDAAN AS drudaan,DT_UDAAN AS dtudaan FROM opsman.dbo.BOBCUSTOMER"; 


	    	  String whereCluase= "where ORIGIN=? AND DEST_CODE= ? " +
 		                " AND DR_UDAAN ='Y' AND  DT_DBF IS NULL " + "";
	    	  
	    	  String sql=selectClause+whereCluase;
	    	 
		  
	    	  SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sql);
	    	   
	    	  
	    	  query.setString(0, originCity);
	    	  query.setString(1, destinatioCity);
	    	  
	    	 
	    	  query.addScalar("origin", Hibernate.STRING);
	    	  query.addScalar("bkDate", Hibernate.STRING);
	    	  query.addScalar("conNo", Hibernate.STRING);
	    	  query.addScalar("script", Hibernate.STRING);
	    	  query.addScalar("name", Hibernate.STRING);
	    	  query.addScalar("doorNo", Hibernate.STRING);
	    	  query.addScalar("street", Hibernate.STRING);
	    	  query.addScalar("area", Hibernate.STRING);
	    	  query.addScalar("pincode", Hibernate.STRING);
	    	  query.addScalar("phone", Hibernate.STRING);
	    	  query.addScalar("mobile", Hibernate.STRING);
	    	  query.addScalar("cneePhone", Hibernate.STRING);
	    	  query.addScalar("cneeLand", Hibernate.STRING);
	    	  query.addScalar("approBy", Hibernate.STRING);
	    	  query.addScalar("discount", Hibernate.STRING);
	    	  query.addScalar("webupd", Hibernate.STRING);
	    	  query.addScalar("filename", Hibernate.STRING);
	    	  query.addScalar("bkMode", Hibernate.STRING);
	    	  query.addScalar("dtdbf", Hibernate.STRING);
	    	  query.addScalar("drudaan", Hibernate.STRING);
	    	  query.addScalar("dtudaan", Hibernate.STRING);
	    	 
	    	  
	    	//  each row mapping with mbldto class  
		    	query.setResultTransformer(Transformers.aliasToBean(CustomerDTO.class));
		
		
		return query.list();
		
	
	}


		public void update_DtToDBF(List<String> consignmentNos,String regionAlpahabate) {
			
			logger.info("updating dt_dbf flag to 'Y' booking for region "+regionAlpahabate);
			String tableName=OpsmanSQLUtils.getCustomerTableName(regionAlpahabate);
			System.out.println("table name is :" + tableName);
			
			String consNosCommanSeprated=consignmentNos.toString().replace("[", "'").replace("]", "'").replace(", ", "','");
			System.out.println("consNosCommanSeprated - "+consNosCommanSeprated);
			String updateQuery="update "+tableName+" set DT_DBF='Y' where con_no  in ("+consNosCommanSeprated+")" ;
			System.out.println(updateQuery);
			try
			{
				
				Query query=sessionFactory.getCurrentSession().createSQLQuery(updateQuery);
				query.executeUpdate();
				logger.info("sucessfully updated dt_dbf flag to 'Y' booking for region "+regionAlpahabate);
				//return true;
				
			}catch(Exception e){
				logger.info("Error in updating  dt_dbf flag to 'Y' booking for region "+regionAlpahabate+" - "+e.getMessage());
				//return false;
			}
		}

			
		}


