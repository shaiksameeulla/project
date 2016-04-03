package com.ff.report.billing.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.report.billing.constants.BillingConstants;
import com.ff.domain.billing.BillingConsignmentRateDO;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.billing.ReBillingConsignmentDO;
import com.ff.domain.billing.ReBillingConsignmentRateDO;
import com.ff.domain.billing.ReBillingHeaderDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.to.billing.ReBillConsgAliasTO;
import com.ff.to.billing.ReBillingGDRTO;
import com.ff.to.billing.ReBillingRateAliasTO;

public class ReBillingDAOImpl extends CGBaseDAO implements ReBillingDAO {

	private final static Logger LOGGER = LoggerFactory.getLogger(ReBillingDAOImpl.class);
	
	public ReBillingHeaderDO saveOrUpdateReBilling(ReBillingHeaderDO reBillingHeaderDO)throws CGSystemException {
		LOGGER.debug("ReBillingDAOImpl :: saveOrUpdateReBilling() :: Start --------> ::::::");
		try{
			
			getHibernateTemplate().saveOrUpdate(reBillingHeaderDO);
		}
		
		catch(Exception e){
			LOGGER.error("Exception Occured in::ReBillingDAOImpl::saveOrUpdateReBilling() :: " + e);
			throw new CGSystemException(e);
		}	
		
		LOGGER.debug("ReBillingDAOImpl :: saveOrUpdateReBilling() :: End --------> ::::::");
	
		return reBillingHeaderDO;
		
	}
	
	
	public List<ReBillingHeaderDO> getRebillDetails(ReBillingGDRTO rebillingGDRTO)throws CGSystemException {
		LOGGER.debug("ReBillingDAOImpl :: getBillsByShippedToCodeAndStartEndDate() :: Start --------> ::::::");
		List<ReBillingHeaderDO> reBillDOs = null;
		try {
			reBillDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							BillingConstants.QRY_GET_REBILLS_DETAILS,
							new String[] {
									BillingConstants.CUSTOMER_ID,
									BillingConstants.BRANCH_ID,
									BillingConstants.START_DATE,
									BillingConstants.END_DATE},
							new Object[] { rebillingGDRTO.getCustomerTO(), rebillingGDRTO.getOfficeTO(),DateUtil.stringToDDMMYYYYFormat(rebillingGDRTO.getStartDateStr()) ,DateUtil.stringToDDMMYYYYFormat(rebillingGDRTO.getEndDateStr()) });

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::ReBillingDAOImpl::getRebillDetails() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ReBillingDAOImpl :: getRebillDetails() :: End --------> ::::::");
		return reBillDOs;
	}
	
	public List<ReBillingHeaderDO> getRebillingJobDetails()throws CGSystemException {
		LOGGER.debug("ReBillingDAOImpl :: getRebillingJobDetails() :: Start --------> ::::::");
		List<ReBillingHeaderDO> reBillingHeaderDOs=null;
		try{
			reBillingHeaderDOs=getHibernateTemplate().findByNamedQuery(BillingConstants.QRY_GET_REBILL_JOB_DETAILS);
		}catch(Exception e){
			LOGGER.error("Exception Occured in::ReBillingDAOImpl::getRebillingJobDetails() :: "
					+ e);
			throw new CGSystemException(e);
		}
	
		LOGGER.debug("ReBillingDAOImpl :: getRebillingJobDetails() :: End --------> ::::::");
		return reBillingHeaderDOs;
	}
	
	@SuppressWarnings("unchecked")
	public List<ReBillConsgAliasTO> getRebillConsignmentData(ReBillingHeaderDO reBillingHeaderDO)throws CGSystemException {
		LOGGER.debug("ReBillingDAOImpl :: getRebillConsignmentData() :: Start --------> ::::::");
		List<ReBillConsgAliasTO> reBillAliasTOs = null;
		Session session = null;
		String query = null;
		try {
		    session = getHibernateTemplate().getSessionFactory().openSession();
		    query = BillingConstants.GET_REBILL_CONSIGNMENT_DATA;
			   
		    reBillAliasTOs = session
			    .createSQLQuery(query)
			    .addScalar("consgId")
			    .addScalar("consgNo")
			    .addScalar("consgStatus")
			    .addScalar("operaOffice")
			    .addScalar("finalWt")
			    .addScalar("customer")
			    .addScalar("consgCode")
			    .addScalar("destPincode")
			    .addScalar("productCode")
			    .addScalar("insuredCode")
			    .addScalar("discount")
			    .addScalar("topayAmt")
			    .addScalar("splChg")
			    .addScalar("codAmt")
			    .addScalar("lcAmt")
			    .addScalar("serviceOn")
			    .addScalar("declareValue")
			    .addScalar("ebPrefCode")
			    .addScalar("rateType")
			    .addScalar("eventDate")
			    .addScalar("bookDate")
			    .addScalar("billingConsignmentId")
			    .addScalar("bill_generated")
			    .addScalar("booking_Rate")
			    .addScalar("rto_Rate")
			    
			   /* .addScalar("fuelSurchargePercentage")*/
			    .setDate("startDate", reBillingHeaderDO.getStartDate())
			    .setDate("endDate", reBillingHeaderDO.getEndDate())
			    .setParameter("customer", reBillingHeaderDO.getCustomer())
			    .setParameter("orgOff", reBillingHeaderDO.getOffice())
			    .setResultTransformer(Transformers.aliasToBean(ReBillConsgAliasTO.class)).list();
		} catch (Exception e) {
		    LOGGER.error("ERROR : BillPrintingDAOImpl.getBillsForBillNumbers", e);
		    throw new CGSystemException(e);
		} finally {
		    session.close();
		    session = null;
		}

		LOGGER.debug("ReBillingDAOImpl :: getRebillConsignmentData() :: End --------> ::::::");
		return reBillAliasTOs;
	}
	
	 public ConsignmentBillingRateDO getAlreadyExistConsgRate(
			    ConsignmentDO consingnment, String rateFor)
			    throws CGBusinessException, CGSystemException {

			logger.debug("ReBillingDAOImpl :: getAlreadyExistConsgRate() :: Start --------> ::::::");
			ConsignmentBillingRateDO consignmentBillingRateDO = null;
			Session session = null;
			Criteria cr = null;
			try {
			    session = createSession();
			    cr = session.createCriteria(ConsignmentBillingRateDO.class,
				    "consignmentBillingRateDO");
			    cr.add(Restrictions.eq("consignmentBillingRateDO.consignmentDO",
				    consingnment));
			    cr.add(Restrictions.eq(
				    "consignmentBillingRateDO.rateCalculatedFor", rateFor));
			    List<ConsignmentBillingRateDO> consignmentBillingRateDOs = (List<ConsignmentBillingRateDO>) cr
				    .list();
			    if (!StringUtil.isEmptyColletion(consignmentBillingRateDOs)) {
				consignmentBillingRateDO = consignmentBillingRateDOs.get(0);
			    }
			} catch (Exception e) {
			    logger.error("ReBillingDAOImpl :: getAlreadyExistConsgRate()::::::"
				    + e);
			    throw new CGSystemException(e);
			} finally {
			    closeSession(session);
			}
			logger.debug("ReBillingDAOImpl: getAlreadyExistConsgRate(): END");
			return consignmentBillingRateDO;
	}
     
	 
	 public ConsignmentBillingRateDO saveOrUpdateRateAndStatus(ConsignmentBillingRateDO consignmentBillingRateDO, String consgNo) throws CGBusinessException, CGSystemException {

			logger.debug("ReBillingDAOImpl :: saveOrUpdateRateAndStatus() :: Start --------> ::::::");
			Session session = null;
			Transaction tx = null;
			Query query = null;
			boolean result = Boolean.FALSE;
			List<String> consgNos = new ArrayList<String>();
			try {
			    session = createSession();
			    tx = session.beginTransaction();
			    session.saveOrUpdate(consignmentBillingRateDO);

			    // Updating flag billingstatus into consignment table
			    if (!StringUtil.isNull(consgNo)) {
				consgNos.add(consgNo);
			    }
			    query = session
				    .getNamedQuery(BillingConstants.QRY_UPDATE_REBILLING_STATUS);
			    query.setString(BillingConstants.BILLING_STATUS_RTB, BillingConstants.TRB_STATUS);
			    query.setParameterList(BillingConstants.CONSIGNMENT_NO, consgNos);
			    int i = query.executeUpdate();
			    if (i > 0)
				result = Boolean.TRUE;

			    tx.commit();
			} catch (Exception e) {
			    tx.rollback();
			    logger.error("Exception Occured in::ReBillingDAOImpl::saveOrUpdateRateAndStatus() :: "
				    + e);
			    throw new CGSystemException(e);
			} finally {
			    closeSession(session);
			}

			logger.debug("ReBillingDAOImpl :: saveOrUpdateRateAndStatus() :: End --------> ::::::");
			return consignmentBillingRateDO;
	 } 
	 
	 
	 public ConsignmentDO getConsgDetails(String consgNo)throws CGBusinessException,
		CGSystemException{
			
			LOGGER.debug("ReBillingDAOImpl :: getConsgDetails() :: Start --------> ::::::");
			ConsignmentDO consgDO = null;
			Session session = null;
			Criteria cr = null;
			try {
				session = createSession();
				cr = session.createCriteria(ConsignmentDO.class, "consg");
				cr.add(Restrictions.eq("consg.consgNo", consgNo));
				List<ConsignmentDO> consgDOs = (List<ConsignmentDO>)cr.list();
				if(!StringUtil.isEmptyColletion(consgDOs)){
					consgDO = consgDOs.get(0);
				}
			} catch (Exception e) {
				LOGGER.error("ReBillingDAOImpl :: getConsgDetails()::::::"
						+ e.getMessage());
				throw new CGSystemException(e);
			} finally{
				closeSession(session);
			}
			 LOGGER .debug("ReBillingDAOImpl: getConsgDetails(): END");
			return consgDO;
		}	
	 
	public Integer  saveOrUpdateRebillingConsignment(ReBillingConsignmentDO rebillConsgDO)throws CGBusinessException,
	CGSystemException{
		
		LOGGER.debug("ReBillingDAOImpl :: saveOrUpdateRebillingConsignment() :: Start --------> ::::::");
		//boolean result = Boolean.FALSE;
		Integer reBillingconsgId;
		try{
			
			getHibernateTemplate().saveOrUpdate(rebillConsgDO);
			//result = Boolean.TRUE;
			reBillingconsgId=rebillConsgDO.getReBillingConsignmentId();
		}
		
		catch(Exception e){
			LOGGER.error("Exception Occured in::ReBillingDAOImpl::saveOrUpdateRebillingConsignment() :: " + e);
			throw new CGSystemException(e);
		}	
		
		LOGGER.debug("ReBillingDAOImpl :: saveOrUpdateRebillingConsignment() :: End --------> ::::::");
	
		return reBillingconsgId;
		
	}
	
   public boolean saveOrUpdateRebillingRate(List<ReBillingConsignmentRateDO> reBillingConsignmentRateDO)throws CGBusinessException,CGSystemException{
	   LOGGER.debug("ReBillingDAOImpl :: saveOrUpdateRebillingRate() :: Start --------> ::::::");
		boolean isSaved = Boolean.FALSE;
		try{
			
			getHibernateTemplate().saveOrUpdateAll(reBillingConsignmentRateDO);
			isSaved = Boolean.TRUE;
		}
		
		catch(Exception e){
			LOGGER.error("Exception Occured in::ReBillingDAOImpl::saveOrUpdateRebillingRate() :: " + e);
			throw new CGSystemException(e);
		}	
		
		LOGGER.debug("ReBillingDAOImpl :: saveOrUpdateRebillingRate() :: End --------> ::::::");
	
		return isSaved;
	 
   }
   
   public List<ReBillingRateAliasTO> getRebillingConsignmentRateForOld(Integer consgId)throws CGSystemException{
	   LOGGER.debug("ReBillingDAOImpl :: getRebillingConsignmentRateForOld() :: Start --------> ::::::");
		List<ReBillingRateAliasTO> reBillRateAliasTOs = null;
		Session session = null;
		String query = null;
		try {
		    session = getHibernateTemplate().getSessionFactory().openSession();
		    query = BillingConstants.GET_REBILLING_CONSIGNMENT_RATE;
			   
		    reBillRateAliasTOs = session
			    .createSQLQuery(query)
			    .addScalar("consignmentRateId")
			    .addScalar("consignmentId")
			    .addScalar("contractFor")
			    .addScalar("rateCalculated")
			    .addScalar("finalSlabRate")
			    .addScalar("fuelSurcharge")
			    .addScalar("riskSurcharge")
			    .addScalar("tOPayCharge")
			    .addScalar("codCharges")
			    .addScalar("parcelHandlingCharge")
			    .addScalar("airportHandlingCharge")
			    .addScalar("documentHandlingCharge")
			    .addScalar("rtoDiscount")
			    .addScalar("otherOrSpecialCharge")
			    .addScalar("discount")
			    .addScalar("serviceTax")
			    .addScalar("educationCess")
			    .addScalar("higherEducationCess")
			    .addScalar("stateTax")
			    .addScalar("surchargeOnStateTax")
			    .addScalar("octroi")
			    .addScalar("octroiServiceCharge")
			    .addScalar("serviceTaxOnOctroiServiceCharge")
			    .addScalar("eduCessOnOctroiServiceCharge")
			    .addScalar("higherEduCessOnOctroiServiceCharge")
			    .addScalar("totalWithoutTax")
			    .addScalar("grandTotalIncludingTax")
			    .addScalar("lcCharge")
			    .addScalar("declaredValue")
			    .addScalar("slabRate")
			    .addScalar("finalSlabRateAddedToRiskSurcharge")
			    .addScalar("lcAmount")
			    .addScalar("stateTaxOnOctroiServiceCharge")
			    .addScalar("surchargeOnStateTaxOnoctroiServiceCharge")
			    .addScalar("codAmount")
			    /*.addScalar("createdDate")
			    .addScalar("createdBy")
			    .addScalar("updatedDate")
			    .addScalar("updatedBy")*/
			    .setParameter("consignment", consgId)
			    .setResultTransformer(
				    Transformers.aliasToBean(ReBillingRateAliasTO.class)).list();
		} catch (Exception e) {
		    LOGGER.error("ERROR : BillPrintingDAOImpl.getRebillingConsignmentRateForOld",
			    e);
		    throw new CGSystemException(e);
		} finally {
		    session.close();
		    session = null;
		}

		LOGGER.debug("ReBillingDAOImpl :: getRebillingConsignmentRateForOld() :: End --------> ::::::");
		return reBillRateAliasTOs;


   }
   
   public boolean updateRebillingHeaderFlag(String rebillingHeaderNo)throws CGSystemException{
	   LOGGER.debug("ReBillingDAOImpl :: updateRebillingHeaderFlag() :: Start --------> ::::::");
		boolean result = Boolean.FALSE;
		Session session = null;
		Query query = null;

		try{

			session = createSession();
			query = session.getNamedQuery(BillingConstants.QRY_UPDATE_REBILLINGHEADER_STATUS);
			query.setString(BillingConstants.REBILLING_HEADERNO, rebillingHeaderNo);
			int i = query.executeUpdate();
			if(i>0)

				result = Boolean.TRUE;

		} catch(Exception e) {
			LOGGER.error("ReBillingDAOImpl :: updateRebillingHeaderFlag()::::::"
					+ e);
			throw new CGSystemException(e);

		}finally{
           closeSession(session);
			//closeTransactionalSession(session);

		}
		 LOGGER .debug("ReBillingDAOImpl: updateRebillingHeaderFlag(): END");
		return result;
   }
   
  public List<BillingConsignmentRateDO>  getRebillingConsgRateFromBillingConsgRate(Integer billingConsgId)throws CGSystemException{
	  LOGGER.debug("ReBillingDAOImpl :: getRebillingConsgRateFromBillingConsgRate() :: Start --------> ::::::");
		Session session = null;
		Criteria cr = null;
		List<BillingConsignmentRateDO> billConsgRateDOs=null;
		try {
			session = createSession();
			cr = session.createCriteria(BillingConsignmentRateDO.class, "billingRate");
			cr.add(Restrictions.eq("billingRate.billingConsignmentId", billingConsgId));
			billConsgRateDOs = (List<BillingConsignmentRateDO>)cr.list();
			
		} catch (Exception e) {
			LOGGER.error("ReBillingDAOImpl :: getRebillingConsgRateFromBillingConsgRate()::::::"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally{
			closeSession(session);
		}
		 LOGGER .debug("ReBillingDAOImpl: getRebillingConsgRateFromBillingConsgRate(): END");
		return billConsgRateDOs;
  }
  
   public Long getTotalRebillCnCount(Integer reBillId)throws CGSystemException{
	   LOGGER.debug("ReBillingDAOImpl :: getTotalRebillCnCount() :: Start --------> ::::::");
	   Long totalCns;
	   try{
		   totalCns = (Long) getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						BillingConstants.QRY_TOTAL_REBILLCNS,
						"RebillConsgId", reBillId).get(0);
	   }catch(Exception e){
		   LOGGER.error("ReBillingDAOImpl :: getTotalRebillCnCount()::::::"
					+ e);
			throw new CGSystemException(e);
	   }
	   
	   LOGGER.debug("ReBillingDAOImpl :: getTotalRebillCnCount() :: End --------> ::::::");
	   return totalCns;
   }
   
   public Long getNewContractForCount(Integer reBillId)throws CGSystemException{
	   LOGGER.debug("ReBillingDAOImpl :: getNewContractForCount() :: Start --------> ::::::");
	   Long totalCns;
	   try{
		   totalCns = (Long) getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						BillingConstants.QRY_NewContractFor,
						"RebillConsgId", reBillId).get(0);
	   }catch(Exception e){
		   LOGGER.error("ReBillingDAOImpl :: getNewContractForCount()::::::"
					+ e);
			throw new CGSystemException(e);
	   }
	   LOGGER.debug("ReBillingDAOImpl :: getNewContractForCount() :: End --------> ::::::");
	   return totalCns;
   }
   
   public Long getOldContractForCount(Integer reBillId)throws CGSystemException{
	   LOGGER.debug("ReBillingDAOImpl :: getOldContractForCount() :: Start --------> ::::::");
	   Long totalCns;
	   try{
		   totalCns = (Long) getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						BillingConstants.QRY_OldContractFor,
						"RebillConsgId", reBillId).get(0);
	   }catch(Exception e){
		   LOGGER.error("ReBillingDAOImpl :: getOldContractForCount()::::::"
					+ e);
			throw new CGSystemException(e);
	   }
	   LOGGER.debug("ReBillingDAOImpl :: getOldContractForCount() :: End --------> ::::::");
	   return totalCns;
   }
   
 
}
