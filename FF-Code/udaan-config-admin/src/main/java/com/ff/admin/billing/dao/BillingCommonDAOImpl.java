package com.ff.admin.billing.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.billing.constants.BillingConstants;
import com.ff.domain.billing.ConsignmentBilling;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.billing.FinancialProductDO;
import com.ff.domain.billing.InvoiceRunSheetHeaderDO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.serviceOffering.ProductDO;


// TODO: Auto-generated Javadoc
/**
 * The Class BillingCommonDAOImpl.
 *
 * @author narmdr
 */
public class BillingCommonDAOImpl extends CGBaseDAO implements BillingCommonDAO {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(BillingCommonDAOImpl.class);

	/* (non-Javadoc)
	 * @see com.ff.admin.billing.dao.BillingCommonDAO#saveInvoiceRunSheet(com.ff.domain.billing.InvoiceRunSheetHeaderDO)
	 */
	@Override
	public InvoiceRunSheetHeaderDO saveInvoiceRunSheet(InvoiceRunSheetHeaderDO invoiceRunSheetHeaderDO)
			throws CGBusinessException, CGSystemException {
		// TODO Auto-generated method stub
		LOGGER.debug("BillingCommonDAOImpl :: saveInvoiceRunSheet() :: Start --------> ::::::");
		try{
			
			getHibernateTemplate().saveOrUpdate(invoiceRunSheetHeaderDO);
		}
		
		catch(Exception e){
			LOGGER.error("Exception Occured in::BillingCommonDAOImpl::saveInvoiceRunSheet() :: " + e);
			throw new CGSystemException(e);
		}	
		
		LOGGER.debug("BillingCommonDAOImpl :: saveInvoiceRunSheet() :: End --------> ::::::");
	
		return invoiceRunSheetHeaderDO;
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.billing.dao.BillingCommonDAO#getInvoiceRunSheet(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvoiceRunSheetHeaderDO> getInvoiceRunSheet(String invoiceRunSheetNumber) 
			throws CGBusinessException,CGSystemException {
		// TODO Auto-generated method stub
		LOGGER.debug("BillingCommonDAOImpl :: getInvoiceRunSheet() :: Start --------> ::::::");
		List<InvoiceRunSheetHeaderDO> invoiceRunSheetHeaderDOs = null;
		try{
			invoiceRunSheetHeaderDOs=getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							BillingConstants.QRY_GET_RUN_SHEET_DTLS_BY_INVOICE_RUN_SHEET_NO,
							new String[] { BillingConstants.INVOICE_RUNSHEET_NUMBER },
							new Object[] { invoiceRunSheetNumber });
		}
		catch(Exception e){
			LOGGER.error("Exception Occured in::BillingCommonDAOImpl::getInvoiceRunSheet() :: "
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("BillingCommonDAOImpl :: getInvoiceRunSheet() :: End --------> ::::::");
		
		return invoiceRunSheetHeaderDOs;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.admin.billing.dao.BillingCommonDAO#getConsignmentForRate()
	 */
	@SuppressWarnings("unchecked")
	public List<ConsignmentBilling> getConsignmentForRate(Long limit)throws CGBusinessException,
	CGSystemException{
		
		LOGGER.debug("BillingCommonDAOImpl :: getConsignmentForRate() :: Start --------> ::::::");
		List<ConsignmentBilling> consignmentList=new ArrayList<ConsignmentBilling>();
		//Session session = openTransactionalSession();
		Session session =null;
		try {
			session = createSession();
			/*Query query=session.createSQLQuery("select cn.* from ff_f_consignment cn ,ff_f_booking bk,ff_d_product p where cn.BILLING_STATUS ='TBB' AND cn.CONSG_NO=bk.CONSG_NUMBER AND p.PRODUCT_ID=cn.PRODUCT AND ((cn.CHANGED_AFTER_NEW_RATE_COMPONENT='N' AND (date_format(bk.BOOKING_DATE, '%d/%m/%Y') = date_format(DATE_SUB(CURRENT_DATE(),INTERVAL p.CONSOLIDATION_WINDOW DAY), '%d/%m/%Y') ))OR (cn.CHANGED_AFTER_NEW_RATE_COMPONENT='Y')) LIMIT :limit").addEntity(ConsignmentDO.class).setParameter("limit", limit);
			Query query=session.createSQLQuery("select cn.* from ff_f_consignment cn join ff_f_booking bk on cn.CONSG_NO=bk.CONSG_NUMBER  join ff_d_product p on p.PRODUCT_ID=cn.PRODUCT where cn.BILLING_STATUS ='TBB' AND cn.FINAL_WEIGHT IS NOT NULL AND cn.DEST_PINCODE IS NOT NULL AND ((cn.CHANGED_AFTER_NEW_RATE_COMPONENT='N' AND (date_format(bk.BOOKING_DATE, '%d/%m/%Y') = date_format(DATE_SUB(CURRENT_DATE(),INTERVAL 0 DAY), '%d/%m/%Y') )) OR (cn.CHANGED_AFTER_NEW_RATE_COMPONENT='Y')) LIMIT :limit").addEntity(ConsignmentDO.class).setParameter("limit", limit);
			Query query=session.createSQLQuery("select cn.* from ff_f_consignment cn join ff_f_booking bk on cn.CONSG_NO=bk.CONSG_NUMBER  join ff_d_product p on p.PRODUCT_ID=cn.PRODUCT  join ff_d_booking_type bt on bt.BOOKING_TYPE_ID=bk.BOOKING_TYPE where cn.BILLING_STATUS ='TBB' AND cn.FINAL_WEIGHT IS NOT NULL AND cn.DEST_PINCODE IS NOT NULL AND ((cn.CHANGED_AFTER_NEW_RATE_COMPONENT='N' AND cn.CHANGED_AFTER_BILLING_WEIGHT_DEST='N' AND (date_format(bk.BOOKING_DATE, '%d/%m/%Y') = date_format(DATE_SUB(CURRENT_DATE(),INTERVAL 1 DAY), '%d/%m/%Y') )) OR (cn.CHANGED_AFTER_NEW_RATE_COMPONENT='Y') OR (cn.CHANGED_AFTER_BILLING_WEIGHT_DEST='Y')) AND ((p.CONSG_SERIES!='T') OR (p.CONSG_SERIES='T' AND (( bt.BOOKING_TYPE='CS' AND cn.CONSG_STATUS!='R') OR (bt.BOOKING_TYPE='CR' AND (cn.CONSG_STATUS='D' OR cn.CONSG_STATUS='R'))))) LIMIT :limit").addEntity(ConsignmentDO.class).setParameter("limit", limit);
			consignmentList = query.list();*/
			Query query=session.createSQLQuery(BillingConstants.ELIGIBLE_CN_FOR_BILLING).addEntity(ConsignmentBilling.class);
			query.setMaxResults(limit.intValue());
			/*List list=getHibernateTemplate().findByNamedQuery("getEligibleCNForBilling");
			System.out.print("hello"+list);*/
			//query.setInteger(0,limit.intValue());
			consignmentList = query.list();
			/*List results = query.list();
			System.out.print("hello"+results);*/
		} catch (Exception e) {
			LOGGER.error("BillingCommonDAOImpl :: getConsignmentForRate()::::::"
 					+ e.getMessage());
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
		 LOGGER .debug("BillingCommonDAOImpl: getConsignmentForRate(): END");
		return consignmentList;
	}
	
	@Override
	@SuppressWarnings("unused")
	public ProductDO getProduct(Integer productId)throws CGBusinessException,
	CGSystemException{
		
		LOGGER.debug("BillingCommonDAOImpl :: getProduct() :: Start --------> ::::::");
		ProductDO productDO = null;
		Session session = null;
		Criteria cr = null;
		try {
			session = createSession();
			cr = session.createCriteria(ProductDO.class, "product");
			cr.add(Restrictions.eq("product.productId", productId));
			List<ProductDO> productDOs = (List<ProductDO>)cr.list();
			if(!StringUtil.isEmptyColletion(productDOs)){
				productDO = productDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("BillingCommonDAOImpl :: getProduct()::::::"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally{
			closeSession(session);
		}
		 LOGGER .debug("BillingCommonDAOImpl: getProduct(): END");
		return productDO;
		
	}
	
	@Override
	@SuppressWarnings("unused")
	public OfficeDO getOffice(Integer officeId)throws CGBusinessException,
	CGSystemException{
		
		LOGGER.debug("BillingCommonDAOImpl :: getOffice() :: Start --------> ::::::");
		OfficeDO officeDO = null;
		Session session = null;
		Criteria cr = null;
		try {
			session = createSession();
			cr = session.createCriteria(OfficeDO.class, "office");
			cr.add(Restrictions.eq("office.officeId", officeId));
			List<OfficeDO> officeDOs = (List<OfficeDO>)cr.list();
			if(!StringUtil.isEmptyColletion(officeDOs)){
				officeDO = officeDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("BillingCommonDAOImpl :: getOffice()::::::"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally{
			closeSession(session);
		}
		 LOGGER .debug("BillingCommonDAOImpl: getOffice(): END");
		return officeDO;
		
	}
	
	public CityDO getCityByOffice(Integer cityId)throws CGBusinessException,
	CGSystemException{
		
		LOGGER.debug("BillingCommonDAOImpl :: getCityByOffice() :: Start --------> ::::::");
		CityDO cityDO = null;
		Session session = null;
		Criteria cr = null;
		try {
			session = createSession();
			cr = session.createCriteria(CityDO.class, "city");
			cr.add(Restrictions.eq("city.cityId", cityId));
			List<CityDO> cityDOs = (List<CityDO>)cr.list();
			if(!StringUtil.isEmptyColletion(cityDOs)){
				cityDO = cityDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("BillingCommonDAOImpl :: getCityByOffice()::::::"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally{
			closeSession(session);
		}
		 LOGGER .debug("BillingCommonDAOImpl: getCityByOffice(): END");
		return cityDO;
	}
	
	public BookingDO getCustomerFromTypeBooking(String consgNo)throws CGBusinessException,
	CGSystemException{
		
		LOGGER.debug("BillingCommonDAOImpl :: getCustomerFromTypeBooking() :: Start --------> ::::::");
		BookingDO bookingDO = null;
		Session session = null;
		Criteria cr = null;
		try {
			session = createSession();
			cr = session.createCriteria(BookingDO.class, "booking");
			cr.add(Restrictions.eq("booking.consgNumber", consgNo));
			List<BookingDO> bookingDOs = (List<BookingDO>)cr.list();
			if(!StringUtil.isEmptyColletion(bookingDOs)){
				bookingDO = bookingDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("BillingCommonDAOImpl :: getCustomerFromTypeBooking()::::::"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally{
			closeSession(session);
		}
		 LOGGER .debug("BillingCommonDAOImpl: getCustomerFromTypeBooking(): END");
		return bookingDO;
	}	
	
	public boolean UpdateConsignmentBillingStatus(String consgNo)throws CGBusinessException,
	CGSystemException{
		
		LOGGER.debug("BillingCommonDAOImpl :: UpdateConsignmentBillingStatus() :: Start --------> ::::::");
		boolean result = Boolean.FALSE;
        List<String> consgNos=new ArrayList<String>();
		Session session = null;
		//Session session = openTransactionalSession();
		Query query = null;

		try{
			if(!StringUtil.isNull(consgNo)){
				consgNos.add(consgNo);
			}

			session = createSession();
			query = session.getNamedQuery(BillingConstants.QRY_UPDATE_BILLING_STATUS);
			query.setString(BillingConstants.BILLING_STATUS_RTB, BillingConstants.RTB_STATUS);
			query.setParameterList(BillingConstants.CONSIGNMENT_NO, consgNos);
			int i = query.executeUpdate();
			if(i>0)

				result = Boolean.TRUE;

		} catch(Exception e) {
			LOGGER.error("BillingCommonDAOImpl :: UpdateConsignmentBillingStatus()::::::"
					+ e);
			throw new CGSystemException(e);

		}finally{
            closeSession(session);
			//closeTransactionalSession(session);

		}
		 LOGGER .debug("BillingCommonDAOImpl: UpdateConsignmentBillingStatus(): END");
		return result;


	}
	
	
	public boolean billing_consolidation_Proc()throws CGBusinessException,
	CGSystemException{
		
			LOGGER.debug("BillingCommonDAOImpl :: billing_consolidation_Proc() :: Start --------> ::::::");
			Session session = null; 
            boolean flag = false;
            //Session session = openTransactionalSession();
            try{
                // session = getSession();
                 //Query q = session.createSQLQuery(" { call sp_billing_consolidation_1() }");
            	 session=createSession();
                 Query q = session.getNamedQuery(BillingConstants.BILLING_CONSOLIDATION_PROC);
                 List list= q.list();
                 logger.debug("BillingCommonDAOImpl: billing_consolidation_Proc: Bill Consolidation SP Result" +list);
                 flag =  true;               
            }catch (Exception ex) {
                 flag =  false;      
                 LOGGER.error("Error occured BillingCommonDAOImpl ::  billing_consolidation_Proc" + ex);
                 new CGSystemException(ex);
            }finally{
                  closeSession(session);
            	//closeTransactionalSession(session);

    		}
            LOGGER .debug("BillingCommonDAOImpl: billing_consolidation_Proc(): END");
            return flag;

	}	
	
	public ConsignmentBillingRateDO saveOrUpdateConsgRate(ConsignmentBillingRateDO consignmentBillingRateDO,String consgNo)throws CGBusinessException,
	CGSystemException{
		
		LOGGER.debug("BillingCommonDAOImpl :: saveOrUpdateConsgRate() :: Start --------> ::::::");
		Session session = null;
		Transaction tx=null;
		Query query = null;
		//boolean result = Boolean.FALSE;
		List<String> consgNos=new ArrayList<String>();
		try{
			session=createSession();
			tx=session.beginTransaction();
			 session.saveOrUpdate(consignmentBillingRateDO);
			
			//Updating flag billingstatus into consignment table
			if(!StringUtil.isNull(consgNo)){
				consgNos.add(consgNo);
			}
			query = session.getNamedQuery(BillingConstants.QRY_UPDATE_BILLING_STATUS);
			query.setString(BillingConstants.BILLING_STATUS_RTB, BillingConstants.RTB_STATUS);
			query.setParameterList(BillingConstants.CONSIGNMENT_NO, consgNos);
			int i = query.executeUpdate();
			if(i>0)
				//result = Boolean.TRUE;
			
			tx.commit();
		}
		catch(Exception e){
			tx.rollback();
			LOGGER.error("Exception Occured in::BillingCommonDAOImpl::saveOrUpdateConsgRate() :: " + e);
			throw new CGSystemException(e);
		}	finally {
			closeSession(session);
     }
	
		LOGGER.debug("BillingCommonDAOImpl :: saveOrUpdateConsgRate() :: End --------> ::::::");
		return consignmentBillingRateDO;
	}
	
public ConsignmentBillingRateDO getAlreadyExistConsgRate(ConsignmentDO consingnment,String rateFor)throws CGBusinessException,
	CGSystemException{
		
	LOGGER.debug("BillingCommonDAOImpl :: getAlreadyExistConsgRate() :: Start --------> ::::::");
	ConsignmentBillingRateDO consignmentBillingRateDO = null;
	Session session = null;
	Criteria cr = null;
	try {
		session = createSession();
		cr = session.createCriteria(ConsignmentBillingRateDO.class, "consignmentBillingRateDO");
		cr.add(Restrictions.eq("consignmentBillingRateDO.consignmentDO", consingnment));
		cr.add(Restrictions.eq("consignmentBillingRateDO.rateCalculatedFor", rateFor));
		List<ConsignmentBillingRateDO> consignmentBillingRateDOs = (List<ConsignmentBillingRateDO>)cr.list();
		if(!StringUtil.isEmptyColletion(consignmentBillingRateDOs)){
			consignmentBillingRateDO = consignmentBillingRateDOs.get(0);
		}
	} catch (Exception e) {
		LOGGER.error("BillingCommonDAOImpl :: getAlreadyExistConsgRate()::::::"
				+ e);
		throw new CGSystemException(e);
	} finally{
		closeSession(session);
	}
	 LOGGER .debug("BillingCommonDAOImpl: getAlreadyExistConsgRate(): END");
	 return consignmentBillingRateDO;
	}

	public Long getLimitOfRecordProcessedForBilling()throws CGSystemException{
		LOGGER.debug("BillingCommonDAOImpl :: getLimitOfRecordProcessedForBilling() :: Start --------> ::::::");
		Long count = 0L;
		String paramName="BILLING_JOB_MAX_CN";
				
		try {
			String count1 = (String) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(BillingConstants.QRY_GET_LIMIT_FOR_BILLING_BATCH,
							BillingConstants.BILLING_LIMIT, paramName).get(0);
			count= Long.parseLong(count1);
			
		} catch (Exception e) {
			LOGGER.error("ERROR :: BillingCommonDAOImpl :: getLimitOfRecordProcessedForBilling()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("BillingCommonDAOImpl :: getLimitOfRecordProcessedForBilling() :: End --------> ::::::");
		return count;
	}
	
	public Long getTotalCNForBillingJob()throws CGSystemException{
		LOGGER.debug("BillingCommonDAOImpl :: getTotalCNForBillingJob() :: Start --------> ::::::");
		Long count = 0L;
		//Session session = openTransactionalSession();	
		Session session=null;
		try {
			session = createSession();
			//Query query=session.createSQLQuery("select cn.* from ff_f_consignment cn join ff_f_booking bk on cn.CONSG_NO=bk.CONSG_NUMBER  join ff_d_product p on p.PRODUCT_ID=cn.PRODUCT where cn.BILLING_STATUS ='TBB' AND cn.FINAL_WEIGHT IS NOT NULL AND cn.DEST_PINCODE IS NOT NULL AND ((cn.CHANGED_AFTER_NEW_RATE_COMPONENT='N' AND (date_format(bk.BOOKING_DATE, '%d/%m/%Y') = date_format(DATE_SUB(CURRENT_DATE(),INTERVAL 0 DAY), '%d/%m/%Y') )) OR (cn.CHANGED_AFTER_NEW_RATE_COMPONENT='Y'))");
			Query query=session.createSQLQuery(BillingConstants.ELIGIBLE_CN_COUNT_FOR_BILLING);
			BigInteger c=(BigInteger)query.uniqueResult();
			String a=c.toString();
			count=Long.parseLong(a);
		} catch (Exception e) {
			LOGGER.error("ERROR :: BillingCommonDAOImpl :: getTotalCNForBillingJob()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}finally{
			//closeTransactionalSession(session);
			closeSession(session);
		}
		return count;
	}
	
	public boolean billing_Stock_consolidation_Proc()throws CGBusinessException,
	CGSystemException{
		
			LOGGER.debug("BillingCommonDAOImpl :: billing_Stock_consolidation_Proc() :: Start --------> ::::::");
			Session session = null; 
            boolean flag = false;
            //Session session = openTransactionalSession();
            try{
                // session = getSession();
                 //Query q = session.createSQLQuery(" { call sp_billing_consolidation_1() }");
            	 session=createSession();
                 Query q = session.getNamedQuery(BillingConstants.BILLING_STOCK_CONSOLIDATION_PROC);
                 List list= q.list();           
                 logger.debug("BillingCommonDAOImpl: billing_Stock_consolidation_Proc: Stock Consolidation SP Result" +list);
                 flag =  true;               
            }catch (Exception ex) {
                 flag =  false;      
                 LOGGER.error("Error occured BillingCommonDAOImpl ::  billing_Stock_consolidation_Proc" + ex);
                 new CGSystemException(ex);
            }finally{
                  closeSession(session);
            	//closeTransactionalSession(session);

    		}
            LOGGER .debug("BillingCommonDAOImpl: billing_Stock_consolidation_Proc(): END");
            return flag;

	}	
	
	
	 public List<FinancialProductDO> getAllFinancialProducts() throws CGSystemException {
		List<FinancialProductDO> products = null;
		Session session = null;
		try {
			session = createSession();
			products = (List<FinancialProductDO>) session
					.createCriteria(FinancialProductDO.class, "financialProductDO").add(Restrictions.eq("financialProductDO.status","A" )).list();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : BillingCommonDAOImpl.getAllFinancialProducts", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return products;
	}
}
