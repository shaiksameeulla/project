package src.com.dtdc.mdbDao.entrytax;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import src.com.dtdc.constants.EntryTaxConstants;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.dtdc.domain.entryTax.EntryTaxCommodityMappingDO;
import com.dtdc.domain.entryTax.EntryTaxConfigDO;
import com.dtdc.domain.entryTax.EntryTaxDetailsDO;
import com.dtdc.domain.entryTax.EntryTaxPaymentDtlsDO;
import com.dtdc.domain.master.TaxDO;
import com.dtdc.domain.master.agent.CollectionAgentDO;
import com.dtdc.domain.master.bank.BankDO;
import com.dtdc.to.entryTax.EntryTaxConfigTO;
import com.dtdc.to.entryTax.EntryTaxDetailsTO;

// TODO: Auto-generated Javadoc
/**
 * The Class EntryTaxMDBDAOImpl.
 */
public class EntryTaxMDBDAOImpl extends HibernateDaoSupport implements EntryTaxMDBDAO {

	/** Logger constants. */
	private Logger LOGGER = LoggerFactory.getLogger(EntryTaxMDBDAOImpl.class);
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.entrytax.EntryTaxMDBDAO#saveUpdateTaxConfigDetails(EntryTaxConfigDO, EntryTaxCommodityMappingDO)
	 */
	@Override
	public Integer saveUpdateTaxConfigDetails(EntryTaxConfigDO configDO,EntryTaxCommodityMappingDO mappingDO) throws CGSystemException {
		Integer taxConfigId = null;
				
		boolean isDuplicate = false;
		Session session_1 = null;
		
		try {			
				session_1 = getHibernateTemplate().getSessionFactory().openSession();
				
				if(!isDuplicate){
					getHibernateTemplate().saveOrUpdate(configDO); 				
					taxConfigId = configDO.getEntryTaxConfigID();
					
					//Save Commodity values
					if(configDO.getChargeType().equalsIgnoreCase(EntryTaxConstants.OCTROI_COMMODITYWISE)
							&& mappingDO.getCommodityList() != null && mappingDO.getCommodityList().size() > 0){
							//Get the list of Commodities mapped for the EntryTax Config
							for(int i=0; i < mappingDO.getCommodityList().size(); i++){															
								EntryTaxCommodityMappingDO mappingDO1 = new EntryTaxCommodityMappingDO();
								mappingDO1.setCommodityId(mappingDO.getCommodityList().get(i)); //CommodityDO is mapped
								mappingDO1.setRecordStatus(EntryTaxConstants.ENTRYTAX_COMM_ACTIVE);
								mappingDO1.setEntryTaxConfigId(configDO); //Pk of EntryConfigId
								
								getHibernateTemplate().saveOrUpdate(mappingDO1); 								
								LOGGER.debug("---SAVED Commodity Mapping ID created for the Configuration ----"+mappingDO1.getEntryTaxCmmdtyMapId());
							}						
						
					}else if(!configDO.getChargeType().equalsIgnoreCase(EntryTaxConstants.OCTROI_COMMODITYWISE) 
								&& configDO.getActionSave().equalsIgnoreCase("update")){ //If ChargeType is not Commodity wise 
																						 //then all records came in update to be made record status inactive
						
						//TODO mark inactive all the commoditymapping records for the configId 
						EntryTaxCommodityMappingDO mappingDO1 = new EntryTaxCommodityMappingDO();
						mappingDO1.setEntryTaxConfigId(configDO); //Pk of EntryConfigId
						mappingDO1.setRecordStatus(EntryTaxConstants.ENTRYTAX_COMM_INACTIVE);
						
						getHibernateTemplate().saveOrUpdate(mappingDO1); 								
						LOGGER.debug("---UPDATED Commodity Mapping ID created for the Configuration ----"+mappingDO1.getEntryTaxCmmdtyMapId());
					
					}
					LOGGER.debug("SAVED TAX CONFIG WITH ID --"+taxConfigId);
				}else{
					taxConfigId = 0;
					LOGGER.debug("Configuration already exists");
				}			
				
				
		} catch (Exception e) {
			logger.error("EntryTaxMDBDAOImpl::saveUpdateTaxConfigDetails occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		} finally{
			//SessionFactoryUtils.closeSession(session_1);
			if(session_1 != null){
				session_1.flush();
				session_1.close();
			}
		}
		return taxConfigId;
	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.entrytax.EntryTaxMDBDAO#saveUpdateEntryTaxDetails(EntryTaxDetailsTO)
	 */
	@Override
	public JSONObject saveUpdateEntryTaxDetails(EntryTaxDetailsTO to)
			throws CGSystemException {
		
		EntryTaxPaymentDtlsDO paymentDtlsDO = null;
		EntryTaxDetailsDO detailsDO = new EntryTaxDetailsDO();
		JSONObject detailsIdObj = new JSONObject();
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			
			//setting details of DO
			detailsDO.setConsigNo(to.getCnNumber());
			detailsDO.setReceiptNo(to.getReceiptNo());
			detailsDO.setPaidBy(to.getPaidBy());
			detailsDO.setEntryTaxPaidAmt(to.getTaxAmt());
			detailsDO.setTaxpaymentDate(DateFormatterUtil.stringToDateFormatter(to.getPaymentDate(), DateFormatterUtil.DDMMYYYY_FORMAT));				
			detailsDO.setTotalAmtCollected(to.getTotalTaxAmt());
			detailsDO.setPayableBy(to.getPayableBy());
			detailsDO.setAlert(to.getAlert());
			detailsDO.setCaseClose(to.getStatus()!=null ? "YES" : "NO");
			if(to.getAlert().equalsIgnoreCase(EntryTaxConstants.ALERT_YES)){
				detailsDO.setEmail(to.getEmailID());
				detailsDO.setPhoneNo(to.getPhoneNumber());
			}else{
				detailsDO.setEmail(null);
				detailsDO.setPhoneNo(null);
			}
			
			//respective mapping DOs
			if(to.getAgentId() != null){
				CollectionAgentDO agentDO = (CollectionAgentDO) session.get(CollectionAgentDO.class, to.getAgentId()) ;
				detailsDO.setCollectionAgentId(agentDO);
			}else{
				detailsDO.setCollectionAgentId(null);
			}
			if(to.getServCharges() != null){				
				EntryTaxConfigDO configDO = (EntryTaxConfigDO) session.get(EntryTaxConfigDO.class, to.getEntryTaxConfigId());
				detailsDO.setEntryTaxConfigId(configDO);
			}
			if(to.getServiceTaxId() != null){  //serviceTaxId to be mapped
				TaxDO taxDO = new TaxDO();
				taxDO.setTaxId(to.getServiceTaxId());
				detailsDO.setServiceTaxId(taxDO);
			}
			if(to.getTotalPaymentByAgent() != null){
				detailsDO.setTotalPaymentByAgent(to.getTotalPaymentByAgent());
			}

			getHibernateTemplate().saveOrUpdate(detailsDO); 
			
			//Iterate over payment list of entry tax details
			if(to.getPaymentMode() != null && to.getPaymentMode().length > 0){
				for (int i = 0; i < to.getPaymentMode().length; i++) {
					paymentDtlsDO = new EntryTaxPaymentDtlsDO();
					paymentDtlsDO.setPaymentMode(to.getPaymentMode()[i]);
					
					BankDO bankDO = (BankDO) session.get(BankDO.class, to.getBankId()[i]);
					paymentDtlsDO.setBankId(bankDO); 
					
					paymentDtlsDO.setChequeNumber(to.getChequeNumber()[i]);
					paymentDtlsDO.setPaymentDate(DateFormatterUtil.stringToDateFormatter(to.getAmountPaidDate()[i],DateFormatterUtil.DDMMYYYY_FORMAT));
					paymentDtlsDO.setAmtCollected(to.getAmountCollected()[i]);
					paymentDtlsDO.setAmtRecdDate(DateFormatterUtil.stringToDateFormatter(to.getAmountRecdDate()[i],DateFormatterUtil.DDMMYYYY_FORMAT));
					//Fk ID					
					paymentDtlsDO.setEntryTaxDtlsId(detailsDO);				
					getHibernateTemplate().saveOrUpdate(paymentDtlsDO);
					detailsIdObj.put("PAYMENT_DTLS_ID", paymentDtlsDO.getEntryTaxPaymentDtlsId());
				}
			}
			detailsIdObj.put("DETAILS_ID", detailsDO.getEntryTaxDtlsId());			
			
		}catch (Exception e) {
			logger.error("EntryTaxMDBDAOImpl::saveUpdateEntryTaxDetails occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		}finally{
			session.flush();
			session.close();
		}		
		return detailsIdObj;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.entrytax.EntryTaxMDBDAO#checkTaxConfigExists(EntryTaxConfigDO, EntryTaxConfigTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean checkTaxConfigExists(EntryTaxConfigDO configDO,EntryTaxConfigTO taxConfigTO) throws CGSystemException{
		Session session_1 = null;
		List<?> taxConfigList = new ArrayList<EntryTaxConfigDO>();
		List<EntryTaxCommodityMappingDO> commodityMappingDOs = null;
		try {
			session_1 = getHibernateTemplate().getSessionFactory().openSession();
			
			if(configDO.getDPartyID()!=null){
				taxConfigList = session_1.createCriteria(EntryTaxConfigDO.class)
									.createAlias("DPartyID", "dp",CriteriaSpecification.LEFT_JOIN)
									.add(Restrictions.eq("dp.customerId", configDO.getDPartyID().getCustomerId()))
									.add(Restrictions.eq("entryTaxTypeID", configDO.getEntryTaxTypeID()))
									.add(Restrictions.eq("chargeType", configDO.getChargeType()))
									.add(Restrictions.eq("chargeMethod", configDO.getChargeMethod()))
									.add(Restrictions.eq("chargeValue", configDO.getChargeValue()))
									.list();		
			}
			if(configDO.getFranchiseeID()!=null && taxConfigList.size() == 0){
				taxConfigList = session_1.createCriteria(EntryTaxConfigDO.class)
									.createAlias("franchiseeID", "fr",CriteriaSpecification.LEFT_JOIN)
									.add(Restrictions.eq("fr.franchiseeId", configDO.getFranchiseeID().getFranchiseeId()))
									.add(Restrictions.eq("entryTaxTypeID", configDO.getEntryTaxTypeID()))	
									.add(Restrictions.eq("chargeType", configDO.getChargeType()))
									.add(Restrictions.eq("chargeMethod", configDO.getChargeMethod()))
									.add(Restrictions.eq("chargeValue", configDO.getChargeValue()))
									.list();		
			}
			if(configDO.getCollectionAgentId()!=null && taxConfigList.size() == 0){
				taxConfigList = session_1.createCriteria(EntryTaxConfigDO.class)
									.createAlias("collectionAgentId", "agent",CriteriaSpecification.LEFT_JOIN)
									.add(Restrictions.eq("agent.agentId", configDO.getCollectionAgentId().getAgentId()))
									.add(Restrictions.eq("entryTaxTypeID", configDO.getEntryTaxTypeID()))	
									.add(Restrictions.eq("chargeType", configDO.getChargeType()))
									.add(Restrictions.eq("chargeMethod", configDO.getChargeMethod()))
									.add(Restrictions.eq("chargeValue", configDO.getChargeValue()))
									.list();
			}
			if(configDO.getState()!=null && taxConfigList.size() == 0){
				taxConfigList = session_1.createCriteria(EntryTaxConfigDO.class)
									.createAlias("state", "state",CriteriaSpecification.LEFT_JOIN)
									.add(Restrictions.eq("state.stateId", configDO.getState().getStateId()))
									.add(Restrictions.eq("entryTaxTypeID", configDO.getEntryTaxTypeID()))
									.add(Restrictions.eq("chargeType", configDO.getChargeType()))
									.add(Restrictions.eq("chargeMethod", configDO.getChargeMethod()))
									.add(Restrictions.eq("chargeValue", configDO.getChargeValue()))
									.list();		
			}
			if(configDO.getCityID()!=null && taxConfigList.size() == 0){
				taxConfigList = session_1.createCriteria(EntryTaxConfigDO.class)
									.createAlias("cityID", "city",CriteriaSpecification.LEFT_JOIN)
									.add(Restrictions.eq("city.cityId", configDO.getCityID().getCityId()))
									.add(Restrictions.eq("entryTaxTypeID", configDO.getEntryTaxTypeID()))
									.add(Restrictions.eq("chargeType", configDO.getChargeType()))
									.add(Restrictions.eq("chargeMethod", configDO.getChargeMethod()))
									.add(Restrictions.eq("chargeValue", configDO.getChargeValue()))
									.list();		
			}
			// IF above criteria gets the Result for ChargeType is Commodity
			// Then Check the whether the same commodity type config exists
			if((taxConfigList !=null && taxConfigList.size() > 0) 
						&& (taxConfigTO.getChargeType().equalsIgnoreCase(EntryTaxConstants.OCTROI_COMMODITYWISE))){
				EntryTaxConfigDO taxConfigDO = (EntryTaxConfigDO) taxConfigList.get(0);
				commodityMappingDOs = session_1.createCriteria(EntryTaxCommodityMappingDO.class)
											.createAlias("entryTaxConfigId", "config",CriteriaSpecification.LEFT_JOIN)
											.createAlias("commodityId", "commodity",CriteriaSpecification.LEFT_JOIN)
											.add(Restrictions.eq("config.entryTaxConfigID", taxConfigDO.getEntryTaxConfigID()))
											//.add(Restrictions.in("commodity.commodityId",taxConfigTO.getCommodityType()))
											.list();
				if(commodityMappingDOs.size() > 0){
					return true;
				}
			}
			if(taxConfigList.size() > 0){
				return true;
			}
		} catch (Exception e) {
			logger.error("EntryTaxMDBDAOImpl::checkTaxConfigExists occured:"
					+e.getMessage());
		}finally{
			session_1.flush();
			session_1.close();
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.entrytax.EntryTaxMDBDAO#saveEntryTaxDetails(List)
	 */
	@Override
	public Boolean saveEntryTaxDetails(List<EntryTaxDetailsDO> detailsList)
			throws CGSystemException {
		getHibernateTemplate().saveOrUpdateAll(detailsList);
		return null;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.entrytax.EntryTaxMDBDAO#saveEntryConfigDetails(List)
	 */
	@Override
	public Boolean saveEntryConfigDetails(List<EntryTaxConfigDO> configList)
			throws CGSystemException {
		getHibernateTemplate().saveOrUpdateAll(configList);
		return null;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.entrytax.EntryTaxMDBDAO#findEntryDtlsByConsignmntNums(List)
	 */
	@Override
	public List<EntryTaxDetailsDO> findEntryDtlsByConsignmntNums(List<String> cnNumber)
			throws CGSystemException {
		return getHibernateTemplate().findByNamedQueryAndNamedParam(EntryTaxConstants.GET_TAX_DTLS_BY_CN_NUMBERS_QRY, EntryTaxConstants.CN_LIST_PARAM, cnNumber);
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.entrytax.EntryTaxMDBDAO#deleteEntryDtlsByConsignmnt(List)
	 */
	@Override
	public Boolean deleteEntryDtlsByConsignmnt(List<String> cnNumber)
			throws CGSystemException {
		Session session =null;
		 try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query deleteQry= session.getNamedQuery(EntryTaxConstants.DELETE_TAX_DTLS_BY_CN_NUMBERS);
			deleteQry.setParameterList(EntryTaxConstants.CN_LIST_PARAM, cnNumber);
			deleteQry.executeUpdate();
		} catch (Exception e) {
			logger.error("EntryTaxMDBDAOImpl::deleteEntryDtlsByConsignmnt occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		}
		finally{
			if(session!=null){
				session.close();
			}
		}
		return true;
	}


	
}
