package com.ff.admin.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;

public class PickUpCommissionCalculationDaoImpl extends CGBaseDAO implements PickUpCommissionCalculationDao {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PickUpCommissionCalculationDaoImpl.class);
		
		public void generatePickUpCount() throws CGSystemException {
			LOGGER.trace("PickUpCommissionCalculationDaoImpl :: generatePickUpCount :: START");
			
			Session session=null;
			
			Query qry=null;
			
			
			try {
				
				//session = getHibernateTemplate().getSessionFactory().openSession();
				session=createSession();
				Calendar calendar = Calendar.getInstance();

				calendar.add(Calendar.MONTH, -1);
				int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
				calendar.set(Calendar.DAY_OF_MONTH, max);
				Date lastMonthDate = calendar.getTime();
				
				//DateUtil.getDDMMYYYYDateString(lastMonthDate);
				
				String dateInYYMMDD=getCurrentDateInYYYYMMDD(DateUtil.getDDMMYYYYDateString(lastMonthDate));
				
				qry= session.getNamedQuery("generatePickUpCommissionCount");
				//qry.setString("lastMonthDate", lastMonthDate);
				qry.setParameter("lastMonthDate",dateInYYMMDD);
				LOGGER.trace("PickUpCommissionCalculationDaoImpl :: generatePickUpCount :: END");
				//.qry.executeUpdate();
				//int i= qry.executeUpdate();
				List list= qry.list(); 

				
				//LOGGER.trace("PickUpCommissionCalculationDaoImpl :: generatePickUpCount :: Updated Count :[" +i+"]");
			}catch(Exception e){
				LOGGER.error("PickUpCommissionCalculationDaoImpl :: generatePickUpCount :: END"+e);
				throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}
			LOGGER.trace("PickUpCommissionCalculationDaoImpl :: generatePickUpCount :: END");
		}
		
		
		private String getCurrentDateInYYYYMMDD(String date) {
			String dateStr = CommonConstants.EMPTY_STRING;
			String dateArr[] = date.split(CommonConstants.HYPHEN);
			String day = dateArr[0];
			String month = dateArr[1];
			String year = dateArr[2];
			dateStr = year + CommonConstants.HYPHEN + month
					+ CommonConstants.HYPHEN + day;
			return dateStr;
		}
	
	
}
