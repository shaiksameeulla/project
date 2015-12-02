package src.com.dtdc.mdbServices.entrytax;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import src.com.capgemini.lbs.mdbutil.EntryTaxUtil;
import src.com.dtdc.constants.EntryTaxConstants;
import src.com.dtdc.mdbDao.entrytax.EntryTaxMDBDAO;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.domain.entryTax.EntryTaxCommodityMappingDO;
import com.dtdc.domain.entryTax.EntryTaxConfigDO;
import com.dtdc.domain.entryTax.EntryTaxDetailsDO;
import com.dtdc.domain.entryTax.EntryTaxPaymentDtlsDO;
import com.dtdc.domain.login.UserDO;
import com.dtdc.domain.master.TaxDO;
import com.dtdc.domain.master.agent.CollectionAgentDO;
import com.dtdc.domain.master.bank.BankDO;
import com.dtdc.domain.master.customer.CustomerDO;
import com.dtdc.domain.master.franchisee.FranchiseeDO;
import com.dtdc.domain.master.geography.CityDO;
import com.dtdc.domain.master.geography.StateDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.master.product.CommodityDO;
import com.dtdc.to.entryTax.EntryTaxCommodityTO;
import com.dtdc.to.entryTax.EntryTaxConfigTO;
import com.dtdc.to.entryTax.EntryTaxDetailsTO;
import com.dtdc.to.entryTax.EntryTaxPaymentDtlsTO;

// TODO: Auto-generated Javadoc
/**
 * The Class EntryTaxMDBServiceImpl.
 */
public class EntryTaxMDBServiceImpl implements EntryTaxMDBService {

	/** Logger constants. */
	private Logger LOGGER = LoggerFactory.getLogger(EntryTaxMDBServiceImpl.class);

	/** The octroi dao. */
	private EntryTaxMDBDAO octroiDAO;	

	/**
	 * Sets the octroi dao.
	 *
	 * @param octroiDAO the new octroi dao
	 */
	public void setOctroiDAO(EntryTaxMDBDAO octroiDAO) {
		this.octroiDAO = octroiDAO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.entrytax.EntryTaxMDBService#saveEntryTaxDetails(CGBaseTO)
	 */
	@Override
	public JSONObject saveEntryTaxDetails(CGBaseTO to)
	throws CGBusinessException, CGSystemException {
		try {
			EntryTaxDetailsTO detailsTO = (EntryTaxDetailsTO) to.getBaseList().get(0);
			return saveEntryTaxDetails(detailsTO);
		} catch (Exception e) {
			LOGGER.error("EntryTaxMDBServiceImpl::saveEntryTaxDetails::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		}
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.entrytax.EntryTaxMDBService#saveEntryTaxDetails(EntryTaxDetailsTO)
	 */
	@Override
	public JSONObject saveEntryTaxDetails(EntryTaxDetailsTO to)
	throws CGBusinessException {
		try {
			return octroiDAO.saveUpdateEntryTaxDetails(to);
		} catch (Exception e) {
			LOGGER.error("EntryTaxMDBServiceImpl::saveEntryTaxDetails::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException();
		}		
	}


	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.entrytax.EntryTaxMDBService#saveTaxConfigDetails(CGBaseTO)
	 */
	@Override
	public Integer saveTaxConfigDetails(CGBaseTO to)
	throws CGBusinessException, CGSystemException {

		try {
			EntryTaxConfigTO entryTaxConfigTO = (EntryTaxConfigTO) to.getBaseList().get(0);		
			return saveTaxConfigDetails(entryTaxConfigTO);
		} catch (Exception e) {
			LOGGER.error("EntryTaxMDBServiceImpl::saveTaxConfigDetails::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		}
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.entrytax.EntryTaxMDBService#saveTaxConfigDetails(EntryTaxConfigTO)
	 */
	@Override
	public Integer saveTaxConfigDetails(EntryTaxConfigTO to) throws CGBusinessException {
		Integer configId = null;
		EntryTaxCommodityMappingDO mappingDO = null;

		try {
			if(to.getCustomerType() !=  null){				
				String customerType = to.getCustomerType();				
				if(customerType.equalsIgnoreCase(EntryTaxConstants.OCTROI_CUST_FRANCHISEE)){
					to.setFranchiseeId(to.getFranchiseeDPCode());
				}else if(customerType.equalsIgnoreCase(EntryTaxConstants.OCTROI_CUST_DParty)){
					to.setdPartyId(to.getFranchiseeDPCode());
				}
			}	
			//SET Commodity values

			//COMMENTED TO AVAOID DEFECT FIXING


			/*if(to.getChargeType().equalsIgnoreCase(EntryTaxConstants.OCTROI_COMMODITYWISE) 
					&& to.getCommodityType() != null && to.getCommodityType().length > 0){

				//Set commodityDOs for List to be mapped to EntryTaxCommodityMappingDO having FK to ConfigDO
				mappingDO = new EntryTaxCommodityMappingDO();
				ArrayList<CommodityDO> commodityTypeList = new ArrayList<CommodityDO>();							
				CommodityDO commodityDO = null;

				for(int i=0; i < to.getCommodityType().length; i++){
					commodityDO = new CommodityDO();
					commodityDO.setCommodityId(to.getCommodityType()[i]);
					commodityTypeList.add(commodityDO);								 

					LOGGER.debug("------  COMMODITY VALUES FOR ENTRY TAX CONFIG SET   -------");
				}	
				mappingDO.setCommodityList(commodityTypeList);
			}*/
			/*else{
				//configDO.setCommodityID(null);
			}	*/			

			//Save the Configuration
			EntryTaxConfigDO configDO = new EntryTaxUtil().copyEntryTaxConfigTO2DO(to)	;
			if( (to.getActionSave().equalsIgnoreCase("save") || to.getActionSave().equalsIgnoreCase("update"))  
					&& !octroiDAO.checkTaxConfigExists(configDO,to)){  //Check if config already exists

				configId = octroiDAO.saveUpdateTaxConfigDetails(configDO,mappingDO); //returns the configId of the Save
			}
			return configId; 

		} catch (CGSystemException e) {
			LOGGER.error("EntryTaxMDBServiceImpl::saveTaxConfigDetails::Exception occured:"
					+e.getMessage());
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.entrytax.EntryTaxMDBService#saveEntryTaxConfigDetails(CGBaseTO)
	 */
	@Override
	public Boolean saveEntryTaxConfigDetails(CGBaseTO to)
	throws CGBusinessException, CGSystemException {
		List<EntryTaxConfigTO> taxConfigDtls=null;
		if(to!=null){
			taxConfigDtls =(List<EntryTaxConfigTO>) to.getBaseList();
		}
		if(!StringUtil.isEmptyList(taxConfigDtls)){
			LOGGER.error("EntryTaxMDBServiceImpl ::saveEntryTaxDtls :: size of the EntryTaxConfigTO list :["+taxConfigDtls.size()+"]");
			getEntryTaxCommodityList(to, taxConfigDtls);

			saveEntryTaxConfigDetails(taxConfigDtls);
		}else{
			LOGGER.error("EntryTaxMDBServiceImpl ::saveEntryTaxConfigDetails ::----cause :: Throwing Business Exception due to ::Entry EntryTaxConfigTO is empty/null");
			throw new CGBusinessException("Throwing Business Exception due to ::Entry EntryTaxConfigTO is empty/null ");
		}

		return true;
	}

	/**
	 * Save entry tax config details.
	 *
	 * @param configDtlsList the config dtls list
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public Boolean saveEntryTaxConfigDetails(List<EntryTaxConfigTO> configDtlsList)
	throws CGBusinessException, CGSystemException {
		List<EntryTaxConfigDO> taxDetailsList=new ArrayList<EntryTaxConfigDO>(configDtlsList.size());
		try {
			for(EntryTaxConfigTO configTo: configDtlsList){
				EntryTaxConfigDO configDo=entryTaxConfigTO2DO(configTo);

				if(!StringUtil.isEmptyList(configTo.getJsonChildObject())){
					List<EntryTaxCommodityTO> comodityToList= (List<EntryTaxCommodityTO>)configTo.getJsonChildObject();
					Set<EntryTaxCommodityMappingDO> commodityItems =new HashSet<EntryTaxCommodityMappingDO>(comodityToList.size());
					for(EntryTaxCommodityTO commodityTo :comodityToList){
						EntryTaxCommodityMappingDO commodityDo= new EntryTaxCommodityMappingDO();
						if(!StringUtil.isEmptyInteger(commodityTo.getCommodityIdVal())){
							CommodityDO commodityId= new  CommodityDO();
							commodityId.setCommodityId(commodityTo.getCommodityIdVal());
							commodityDo.setCommodityId(commodityId);
						}
						commodityDo.setChargeMethod(commodityTo.getChargeMethod());
						commodityDo.setChargeType(commodityTo.getChargeType());
						commodityDo.setChargeValue(commodityTo.getChargeValue());
						commodityDo.setEntryTaxConfigId(configDo);
						commodityItems.add(commodityDo);
					}

					configDo.setCommodityItems(commodityItems);
				}


				taxDetailsList.add(configDo);
			}

			octroiDAO.saveEntryConfigDetails(taxDetailsList);
		} catch (Exception e) {
			LOGGER.error("EntryTaxMDBServiceImpl ::saveEntryTaxConfigDetails ::----Exception"+e.getMessage()+"Cause :"+e.getCause());
			throw new CGSystemException(e);
		}
		return true;
	}

	/**
	 * Entry tax config t o2 do.
	 *
	 * @param configTo the config to
	 * @return the entry tax config do
	 */
	private EntryTaxConfigDO entryTaxConfigTO2DO(EntryTaxConfigTO configTo) {
		EntryTaxConfigDO configDo;
		configDo= new EntryTaxConfigDO();


		try {
			PropertyUtils.copyProperties(configDo, configTo);
		} catch (Exception e) {
			LOGGER.error("EntryTaxMDBServiceImpl::entryTaxConfigTO2DO::Exception occured:"
					+e.getMessage());
		}

		if(!StringUtil.isEmptyInteger(configTo.getRegionId())){
			OfficeDO  ro=new OfficeDO();
			ro.setOfficeId(configTo.getRegionId());
			configDo.setRegionOfficeID(ro);
		}
		if(!StringUtil.isEmptyInteger(configTo.getBranchId())){
			OfficeDO  bo=new OfficeDO();
			bo.setOfficeId(configTo.getBranchId());
			configDo.setBranchOfficeID(bo);
		}

		configDo.setCustomerType(configTo.getCustomerType());
		configDo.setChargeMethod(configTo.getChargeMethod());
		configDo.setChargeValue(configTo.getChargeValue());
		configDo.setConfigurationType(configTo.getConfigureRadio());
		configDo.setSapAutomation(configTo.getSapAutomation());

		if(!StringUtil.isEmptyInteger(configTo.getCityId())){
			CityDO cityID = new CityDO();
			cityID.setCityId(configTo.getCityId());
			configDo.setCityID(cityID);

		}

		if(!StringUtil.isEmptyInteger(configTo.getStateId())){
			StateDO state= new StateDO();
			state.setStateId(configTo.getStateId());
			configDo.setState(state);
		}
		if(!StringUtil.isEmptyInteger(configTo.getTaxType())){
			TaxDO taxDO = new TaxDO();
			taxDO.setTaxId(configTo.getTaxType());
			configDo.setEntryTaxTypeID(taxDO);
		}
		if(!StringUtil.isEmptyInteger(configTo.getDataEntryOfficeId())){
			OfficeDO  dataEntryOfficeDo=new OfficeDO();
			dataEntryOfficeDo.setOfficeId(configTo.getDataEntryOfficeId());
			configDo.setDataEntryOfficeDo(dataEntryOfficeDo);

		}
		if(!StringUtil.isEmptyInteger(configTo.getCustomerId())){
			CustomerDO customerDO = new CustomerDO();
			customerDO.setCustomerId(configTo.getCustomerId());
			configDo.setDPartyID(customerDO);
		}
		if(!StringUtil.isEmptyInteger(configTo.getFranchiseeId())){
			FranchiseeDO frDo = new FranchiseeDO();
			frDo.setFranchiseeId(configTo.getFranchiseeId());
			configDo.setFranchiseeID(frDo);

		}
		if(!StringUtil.isEmptyInteger(configTo.getDirectPartyId())){
			CollectionAgentDO agentDO = new CollectionAgentDO();
			agentDO.setAgentId(configTo.getDirectPartyId());
			configDo.setCollectionAgentId(agentDO);
		}
		return configDo;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.entrytax.EntryTaxMDBService#saveEntryTaxDtls(CGBaseTO)
	 */
	@Override
	public Boolean saveEntryTaxDtls(CGBaseTO to)
	throws CGBusinessException, CGSystemException {
		List<EntryTaxDetailsTO> taxDtlsList=null;
		if(to!=null){
			taxDtlsList =(List<EntryTaxDetailsTO>) to.getBaseList();

		}
		if(!StringUtil.isEmptyList(taxDtlsList)){
			getEntryTaxDtlsChildList(to, taxDtlsList);
			saveEntryTaxDtls(taxDtlsList);

		}else{
			LOGGER.error("EntryTaxMDBServiceImpl ::saveEntryTaxDtls ::----cause :: Throwing Business Exception due to ::Entry EntryTaxDetailsTO is empty/null");
			throw new CGBusinessException("Throwing Business Exception due to ::Entry TaxdetailsTo is empty/null ");
		}

		return true;
	}

	/**
	 * Gets the entry tax dtls child list.
	 *
	 * @param to the to
	 * @param taxDtlsList the tax dtls list
	 * @return the entry tax dtls child list
	 */
	private void getEntryTaxDtlsChildList(CGBaseTO to,
			List<EntryTaxDetailsTO> taxDtlsList) {
		LOGGER.error("EntryTaxMDBServiceImpl ::saveEntryTaxDtls :: size of the EntryTaxDetailsTO list :["+taxDtlsList.size()+"]");
		List<EntryTaxPaymentDtlsTO> paymentDtlsTOList =(List<EntryTaxPaymentDtlsTO>)to.getJsonChildObject();

		if(!StringUtil.isEmptyList(paymentDtlsTOList)){
			for(EntryTaxDetailsTO detailsTo: taxDtlsList){
				List<EntryTaxPaymentDtlsTO> childList=new  ArrayList<EntryTaxPaymentDtlsTO>();
				for(EntryTaxPaymentDtlsTO child: paymentDtlsTOList){

					if(detailsTo.getParentId() == child.getParentId()){
						childList.add(child);
					}else{
						continue;
					}
				}
				if(!StringUtil.isEmptyList(childList)){
					detailsTo.setPaymentDtlsTOList(childList);
				}
			}
		}
	}

	/**
	 * Gets the entry tax commodity list.
	 *
	 * @param to the to
	 * @param configDtlsList the config dtls list
	 * @return the entry tax commodity list
	 */
	private void getEntryTaxCommodityList(CGBaseTO to,
			List<EntryTaxConfigTO> configDtlsList) {
		LOGGER.error("EntryTaxMDBServiceImpl ::saveEntryTaxDtls :: size of the EntryTaxDetailsTO list :["+configDtlsList.size()+"]");
		List<EntryTaxCommodityTO> commodityList =(List<EntryTaxCommodityTO>)to.getJsonChildObject();

		if(!StringUtil.isEmptyList(commodityList)){
			for(EntryTaxConfigTO detailsTo: configDtlsList){
				List<EntryTaxCommodityTO> childList=new  ArrayList<EntryTaxCommodityTO>();
				for(EntryTaxCommodityTO child: commodityList){

					if(detailsTo.getParentId() == child.getParentId()){
						childList.add(child);
					}else{
						continue;
					}
				}
				if(!StringUtil.isEmptyList(childList)){
					detailsTo.setCommToList(childList);
				}
			}
		}
	}

	/**
	 * Save entry tax dtls.
	 *
	 * @param taxDtlsList the tax dtls list
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public Boolean saveEntryTaxDtls(List<EntryTaxDetailsTO> taxDtlsList)
	throws CGBusinessException, CGSystemException {
		List<EntryTaxDetailsDO> taxDetailsList=new ArrayList<EntryTaxDetailsDO>(taxDtlsList.size());
		try {

			for(EntryTaxDetailsTO taxTo: taxDtlsList){
				EntryTaxDetailsDO taxDo =entryTaxDetailsTO2DO(taxTo);
				if(!StringUtil.isEmptyList(taxTo.getPaymentDtlsTOList())){
					List<EntryTaxPaymentDtlsTO> paymentDtlsTOList =taxTo.getPaymentDtlsTOList();
					Set <EntryTaxPaymentDtlsDO> entryTaxPaymentDtls = new HashSet<EntryTaxPaymentDtlsDO>(paymentDtlsTOList.size());
					for(EntryTaxPaymentDtlsTO paymntTo :paymentDtlsTOList){
						EntryTaxPaymentDtlsDO paymntDo = new EntryTaxPaymentDtlsDO();
						paymntDo.setPaymentMode(paymntTo.getPaymentMode());
						paymntDo.setAmtCollected(paymntTo.getAmountCollected());
						paymntDo.setChequeNumber(paymntTo.getChequeNumber());
						paymntDo.setPaymentDate(DateFormatterUtil.slashDelimitedstringToDDMMYYYYFormat(paymntTo.getAmountPaidDate()));
						paymntDo.setAmtRecdDate(DateFormatterUtil.slashDelimitedstringToDDMMYYYYFormat(paymntTo.getAmountRecdDate()));

						if(!StringUtil.isEmptyInteger(paymntTo.getBankID())){
							BankDO bankId=new BankDO();
							bankId.setBankId(paymntTo.getBankID());
							paymntDo.setBankId(bankId);
						}
						entryTaxPaymentDtls.add(paymntDo);
					}
					taxDo.setEntryTaxPaymentDtls(entryTaxPaymentDtls);
				}
				taxDetailsList.add(taxDo);
			}

			octroiDAO.saveEntryTaxDetails(taxDetailsList);
		} catch (Exception e) {
			LOGGER.error("EntryTaxMDBServiceImpl ::saveEntryTaxDtls ::----Exception"+e.getMessage()+"Cause :"+e.getCause());
			throw new CGSystemException(e);
		}
		return true;
	}

	/**
	 * Entry tax details t o2 do.
	 *
	 * @param to the to
	 * @return the entry tax details do
	 */
	private EntryTaxDetailsDO entryTaxDetailsTO2DO(EntryTaxDetailsTO to) {
		EntryTaxDetailsDO detailsDO;
		detailsDO = new EntryTaxDetailsDO();


		try {
			PropertyUtils.copyProperties(detailsDO, to);
		} catch (Exception e) {
			LOGGER.error("EntryTaxMDBServiceImpl::entryTaxDetailsTO2DO::Exception occured:"
					+e.getMessage());
		}


		//setting details of DO
		detailsDO.setConsigNo(to.getCnNumber());
		detailsDO.setReceiptNo(to.getReceiptNo());
		detailsDO.setPaidBy(to.getPaidBy());
		detailsDO.setEntryTaxPaidAmt(to.getTaxAmt());
		detailsDO.setServiceTaxPerscentage(to.getServTax());
		detailsDO.setTotalAmtCollected(to.getTotalTaxAmt());
		detailsDO.setTaxpaymentDate(DateFormatterUtil.slashDelimitedstringToDDMMYYYYFormat(to.getPaymentDate()));				
		detailsDO.setTotalAmtCollected(to.getTotalTaxAmt());
		detailsDO.setPayableBy(to.getPayableBy());
		detailsDO.setAlert(to.getAlert());
		detailsDO.setCaseClose(to.getStatus());
		detailsDO.setEmail(to.getEmailID());
		detailsDO.setPhoneNo(to.getPhoneNumber());
		detailsDO.setIsCancelled(to.getIsCancelled());
		detailsDO.setClosingDate(DateFormatterUtil.slashDelimitedstringToDDMMYYYYFormat(to.getClosingDate()));
		//detailsDO.setDiFlag(DRSConstants.DI_FLAG);

		//respective mapping DOs
		if(!StringUtil.isEmptyInteger(to.getAgentId())){
			CollectionAgentDO agentDO = new CollectionAgentDO();
			agentDO.setAgentId(to.getAgentId());
			detailsDO.setCollectionAgentId(agentDO);
		}

		if(!StringUtil.isEmptyInteger(to.getServiceTaxId())){  //serviceTaxId to be mapped
			TaxDO taxDO = new TaxDO();
			taxDO.setTaxId(to.getServiceTaxId());
			detailsDO.setServiceTaxId(taxDO);
		}
		if(!StringUtil.isEmptyInteger(to.getTaxType())){  //serviceTaxId to be mapped
			TaxDO taxDO = new TaxDO();
			taxDO.setTaxId(to.getTaxType());
			detailsDO.setTaxTypeId(taxDO);
		}

		detailsDO.setTotalPaymentByAgent(to.getTotalPaymentByAgent());
		if(!StringUtil.isEmptyInteger(to.getDestOfficeId())){
			OfficeDO destinationOfficeId = new OfficeDO();
			destinationOfficeId.setOfficeId(to.getDestOfficeId());
			detailsDO.setDestinationOfficeId(destinationOfficeId);
		}
		if(StringUtil.isEmptyInteger(to.getCreatedOfficeId())){
			OfficeDO createdByOffice = new OfficeDO();
			createdByOffice.setOfficeId(to.getCreatedOfficeId());
			detailsDO.setCreatedByOfficeId(createdByOffice);

		}

		if(StringUtil.isEmptyInteger(to.getModifiedOfficeId())){
			OfficeDO modifiedByOffice = new OfficeDO();
			modifiedByOffice.setOfficeId(to.getModifiedOfficeId());
			detailsDO.setModifiedOfficeId(modifiedByOffice);
		}

		if(!StringUtil.isEmptyInteger(to.getUserId())){
			UserDO userDo=  new UserDO();
			userDo.setUserId(to.getUserId());
			detailsDO.setUserDo(userDo);
		}
		if(!StringUtil.isEmptyInteger(to.getCommodityType())){
			CommodityDO commodityId= new CommodityDO();
			commodityId.setCommodityId(to.getCommodityType());
			detailsDO.setCommodityId(commodityId);
		}

		return detailsDO;
	}



}
