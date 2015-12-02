package src.com.capgemini.lbs.mdbutil;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import src.com.dtdc.constants.EntryTaxConstants;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.domain.entryTax.EntryTaxConfigDO;
import com.dtdc.domain.entryTax.EntryTaxDetailsDO;
import com.dtdc.domain.entryTax.EntryTaxPaymentDtlsDO;
import com.dtdc.domain.master.TaxDO;
import com.dtdc.domain.master.agent.CollectionAgentDO;
import com.dtdc.domain.master.customer.CustomerDO;
import com.dtdc.domain.master.franchisee.FranchiseeDO;
import com.dtdc.domain.master.geography.CityDO;
import com.dtdc.domain.master.geography.StateDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.to.entryTax.EntryTaxConfigTO;
import com.dtdc.to.entryTax.EntryTaxDetailsTO;
import com.dtdc.to.entryTax.EntryTaxPaymentDtlsTO;

// TODO: Auto-generated Javadoc
/**
 * The Class EntryTaxUtil.
 */
public class EntryTaxUtil {

	/** The Constant LOGGER. */
	private static final Logger logger = LoggerFactory
			.getLogger(EntryTaxUtil.class);
	//Copy ConfigDO to ConfigTO
	/**
	 * Copy config d o2 to.
	 *
	 * @param configDOs the config d os
	 * @return the list
	 * @throws CGBusinessException the cG business exception
	 */
	List<EntryTaxConfigTO> copyConfigDO2TO(List<EntryTaxConfigDO> configDOs) throws CGBusinessException{
		List<EntryTaxConfigTO> configTOs = null;
		EntryTaxConfigTO configTO = null;

		try {
			if(configDOs != null && configDOs.size() > 0){
				configTOs =  new ArrayList<EntryTaxConfigTO>();
				for (EntryTaxConfigDO configDO : configDOs) {
					configTO = new EntryTaxConfigTO();

					configTO.setEntryTaxConfigId(configDO.getEntryTaxConfigID());
					configTO.setTaxType(configDO.getEntryTaxTypeID().getTaxId());		
					configTO.setTaxName(configDO.getEntryTaxTypeID().getTaxName());					
					configTO.setCustomerType(configDO.getCustomerType());
					configTO.setRegionId(configDO.getRegionOfficeID()!=null ? configDO.getRegionOfficeID().getOfficeId() : 0);
					configTO.setBranchId(configDO.getBranchOfficeID()!=null ? configDO.getBranchOfficeID().getOfficeId() : 0);	
					configTO.setRegionName(configDO.getRegionOfficeID()!=null ? configDO.getRegionOfficeID().getOfficeName() : "");
					configTO.setBranchName(configDO.getBranchOfficeID()!=null ? configDO.getBranchOfficeID().getOfficeName() : "");						

					//Customer /Franchisee Code
					if(configDO.getCustomerType() != null
							&& configDO.getCustomerType().equalsIgnoreCase(EntryTaxConstants.OCTROI_CUST_FRANCHISEE)){

						configTO.setDpFranchiseeCode(configDO.getFranchiseeID().getFranchiseeCode());
						configTO.setDpFranchiseeName(configDO.getFranchiseeID().getFirstName()!=null
								? configDO.getFranchiseeID().getFirstName():""
									+ configDO.getFranchiseeID().getLastName()!= null 
									? configDO.getFranchiseeID().getLastName() : ""	);

					}else if(configDO.getCustomerType() != null && 
							configDO.getCustomerType().equalsIgnoreCase(EntryTaxConstants.OCTROI_CUST_DParty)){

						configTO.setDpFranchiseeCode(configDO.getDPartyID().getCustomerCode());
						configTO.setDpFranchiseeName(configDO.getDPartyID().getFirstName()!=null 
								? configDO.getDPartyID().getFirstName() : ""
									+ configDO.getDPartyID().getLastName()!= null
									? configDO.getDPartyID().getLastName() : "");

					}
					if(configDO.getDPartyID() != null){
						configTO.setdPartyId(configDO.getDPartyID().getCustomerId());
					}
					if(configDO.getFranchiseeID() !=null){
						configTO.setFranchiseeId(configDO.getFranchiseeID().getFranchiseeId());
					}				
					//City and State
					configTO.setCityId(configDO.getCityID()!=null ? configDO.getCityID().getCityId() : 0);
					configTO.setStateId(configDO.getState()!=null ? configDO.getState().getStateId() : 0);			
					configTO.setCityName(configDO.getCityID()!=null ? configDO.getCityID().getCityName() : "");
					configTO.setStateName(configDO.getState()!=null ? configDO.getState().getStateName() : "");			

					PropertyUtils.copyProperties(configTO, configDO);
					/*//Service Charge
					configTO.setChargeType(configDO.getChargeType());
					configTO.setChargeMethod(configDO.getChargeMethod());

					//configTO.setCommodityType(configDO.getCommodityID() != null ? configDO.getCommodityID().getCommodityId() : 0);
					configTO.setChargeValue(configDO.getChargeValue());
					configTO.setAutomationReqd(configDO.getSapAutomation());*/

					configTOs.add(configTO);
				}
			}
		} catch (Exception e) {
			logger.error("EntryTaxUtil::copyConfigDO2TO::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException();
		}

		return configTOs;
	}

	/**
	 * Copy detail d o2 to.
	 *
	 * @param detailsDO the details do
	 * @return the entry tax details to
	 */
	public EntryTaxDetailsTO copyDetailDO2TO(EntryTaxDetailsDO detailsDO){
		EntryTaxDetailsTO detailsTO = null;

		detailsTO = new EntryTaxDetailsTO();
		detailsTO.setEntryTaxDtlsId(detailsDO.getEntryTaxDtlsId());
		detailsTO.setCnNumber(detailsDO.getConsigNo());
		//detailsTO.setTaxType(detailsDO.getTaxTypeId()); //get the taxtype Id for the entryTaxConfigId
		detailsTO.setReceiptNo(detailsDO.getReceiptNo());
		detailsTO.setPaidBy(detailsDO.getPaidBy());
		detailsTO.setTaxAmt(detailsDO.getEntryTaxPaidAmt());
		detailsTO.setPaymentDate(DateFormatterUtil.getDDMMYYYYDateString(detailsDO.getTaxpaymentDate()));
		detailsTO.setServCharges(detailsDO.getEntryTaxConfigId().getChargeValue()); //get the service charge Entry Tax Config configured for this region
		detailsTO.setServiceTaxId(detailsDO.getServiceTaxId()!=null ? detailsDO.getServiceTaxId().getTaxId():0); //get the service tax 
		detailsTO.setServTax(detailsDO.getServiceTaxAmt());
		detailsTO.setTotalTaxAmt(detailsDO.getTotalAmtCollected());
		detailsTO.setPayableBy(detailsDO.getPayableBy());
		detailsTO.setAlert(detailsDO.getAlert());
		detailsTO.setEmailID(detailsDO.getEmail());
		detailsTO.setPhoneNumber(detailsDO.getPhoneNo());
		detailsTO.setStatus(detailsDO.getCaseClose());			

		return detailsTO;
	}

	/**
	 * Copy payment detail d o2 to.
	 *
	 * @param paymentDtlsDOs the payment dtls d os
	 * @return the list
	 */
	public List<EntryTaxPaymentDtlsTO> copyPaymentDetailDO2TO(List<EntryTaxPaymentDtlsDO> paymentDtlsDOs){

		//Payment Details
		EntryTaxPaymentDtlsTO dtlsTO = null;
		List<EntryTaxPaymentDtlsTO> dtlsTOs = new ArrayList<EntryTaxPaymentDtlsTO>();

		for (EntryTaxPaymentDtlsDO paymentDtlsDO : paymentDtlsDOs) {
			dtlsTO = new EntryTaxPaymentDtlsTO();
			dtlsTO.setPaymentMode(paymentDtlsDO.getPaymentMode());
			dtlsTO.setChequeNumber(paymentDtlsDO.getChequeNumber());
			dtlsTO.setBankID(paymentDtlsDO.getBankId().getBankId());
			dtlsTO.setAmountPaidDate(DateFormatterUtil.getDDMMYYYYDateString(paymentDtlsDO.getPaymentDate()));
			dtlsTO.setAmountCollected(paymentDtlsDO.getAmtCollected());
			dtlsTO.setAmountRecdDate(DateFormatterUtil.getDDMMYYYYDateString(paymentDtlsDO.getAmtRecdDate()));
			dtlsTO.setEntryTaxPaymentDetailId(paymentDtlsDO.getEntryTaxPaymentDtlsId());
			dtlsTOs.add(dtlsTO);

		}	

		return dtlsTOs;	
	}


	//Get the JSONObject for the Configuration detail
	/**
	 * Gets the entry tax config detail for tax id.
	 *
	 * @param configTOs the config t os
	 * @param entryTaxConfigId the entry tax config id
	 * @return the entry tax config detail for tax id
	 */
	public JSONObject getEntryTaxConfigDetailForTaxID(List<EntryTaxConfigTO> configTOs,Integer entryTaxConfigId){
		JSONObject jsonObject = null;
		try {
			if(configTOs!=null && configTOs.size() > 0) {
				for (EntryTaxConfigTO entryTaxConfigTO : configTOs) {

					if(entryTaxConfigId.equals(entryTaxConfigTO.getEntryTaxConfigId())){				
						jsonObject = new JSONObject();
						jsonObject.put("TAX_ID", entryTaxConfigTO.getTaxType());
						jsonObject.put("ENTRY_CONFIG_TAX_ID", entryTaxConfigTO.getEntryTaxConfigId());
						jsonObject.put("REGION_ID", entryTaxConfigTO.getRegionId());
						jsonObject.put("BRANCH_ID",entryTaxConfigTO.getBranchId() );
						jsonObject.put("CUSTOMER_TYPE", entryTaxConfigTO.getCustomerType());

						if(entryTaxConfigTO.getCustomerType() != null &&
								entryTaxConfigTO.getCustomerType().equalsIgnoreCase("FR")){							
							jsonObject.put("FRDP_ID", entryTaxConfigTO.getFranchiseeId());
							jsonObject.put("FRDP_NAME", entryTaxConfigTO.getDpFranchiseeName());
						}else {							
							jsonObject.put("FRDP_ID", entryTaxConfigTO.getdPartyId());
							jsonObject.put("FRDP_NAME", entryTaxConfigTO.getDpFranchiseeName());
						}

						jsonObject.put("CHARGE_TYPE",entryTaxConfigTO.getChargeType() );
						jsonObject.put("COMMODITY_TYPE",entryTaxConfigTO.getCommodityType() );//int
						jsonObject.put("CHARGE_METHOD",entryTaxConfigTO.getChargeMethod());
						jsonObject.put("CHARGE_VALUE",entryTaxConfigTO.getChargeValue());
						jsonObject.put("AUTOMATION_REQD",entryTaxConfigTO.getSapAutomation());
					}			
				}
			}
		} catch (Exception e) {
			logger.error("EntryTaxUtil::getEntryTaxConfigDetailForTaxID::Exception occured:"
					+e.getMessage());
		}		
		return jsonObject;
	}

	/**
	 * Utility method to copy the Domain object to Transfer object.
	 *
	 * @param detailsDOs the details d os
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	public List<EntryTaxDetailsTO> copyDO2TO(List<EntryTaxDetailsDO> detailsDOs)
	throws CGSystemException {
		EntryTaxDetailsTO taxDetailsTO = null;
		List<EntryTaxDetailsTO> detailsTOs = new ArrayList<EntryTaxDetailsTO>();
		for (EntryTaxDetailsDO do1: detailsDOs) {
			taxDetailsTO = new EntryTaxDetailsTO();

			taxDetailsTO.setCnNumber(do1.getConsigNo());
			taxDetailsTO.setPaidBy(do1.getPaidBy());
			taxDetailsTO.setReceiptNo(do1.getReceiptNo());
			taxDetailsTO.setPaymentDate(DateFormatterUtil.getDDMMYYYYDateString(do1.getTaxpaymentDate()));

			taxDetailsTO.setEntryTaxConfigId(do1.getEntryTaxConfigId()!=null 
					? do1.getEntryTaxConfigId().getEntryTaxConfigID():0);

			taxDetailsTO.setEntryTaxDtlsId(do1.getEntryTaxDtlsId());
			//taxDetailsTO.setTaxType(do1.getTaxTypeId()) ;

			if(do1.getCommodityId()!=null){
				taxDetailsTO.setCommodityType(do1.getCommodityId().getCommodityId()); //set commodityType	
			}

			taxDetailsTO.setTotalTaxAmt(do1.getTotalAmtCollected());
			taxDetailsTO.setPayableBy(do1.getPayableBy());
			taxDetailsTO.setStatus(do1.getCaseClose()); //status of tax details

			//taxDetailsTO.setBranchId(do1.get);
			//taxDetailsTO.set franchisee and dp

			detailsTOs.add(taxDetailsTO);
		}
		return detailsTOs;
	}


	/**
	 * Copy d o2 to.
	 *
	 * @param detailsDO the details do
	 * @return the entry tax details to
	 * @throws CGSystemException the cG system exception
	 */
	public EntryTaxDetailsTO copyDO2TO(EntryTaxDetailsDO detailsDO) throws CGSystemException {
		EntryTaxDetailsTO taxDetailsTO = null;
		if(detailsDO!=null) {
			taxDetailsTO = new EntryTaxDetailsTO();
			//taxDetailsTO.setTaxType(StringUtil.isNullInteger(detailsDO.getTaxTypeId()));
			taxDetailsTO.setCnNumber(StringUtil.checkString(detailsDO.getConsigNo()));
			taxDetailsTO.setPaidBy(StringUtil.checkString(detailsDO.getPaidBy()));
			taxDetailsTO.setReceiptNo(StringUtil.isNullInteger(detailsDO.getReceiptNo()));
			taxDetailsTO.setPaymentDate(DateFormatterUtil.getDDMMYYYYDateString(
					StringUtil.checkForNull(detailsDO.getTaxpaymentDate())?detailsDO.getTaxpaymentDate():null));
			if(detailsDO.getEntryTaxConfigId()!=null) {
				//taxDetailsTO.setChargetType(StringUtil.checkString(detailsDO.getEntryTaxConfigId().getChargeType()));
				if(detailsDO.getEntryTaxConfigId().getState()!=null) {
					taxDetailsTO.setStateId(detailsDO.getEntryTaxConfigId().getState().getStateId());
				}
				if(detailsDO.getEntryTaxConfigId().getEntryTaxTypeID()!=null) {
					taxDetailsTO.setTaxType(StringUtil.isNullInteger(detailsDO.getEntryTaxConfigId().getEntryTaxTypeID().getTaxId()));
					taxDetailsTO.setTaxTypeName(StringUtil.checkString(detailsDO.getEntryTaxConfigId().getEntryTaxTypeID().getTaxType()));
				}
			}
			if(detailsDO.getCommodityId()!=null){
				taxDetailsTO.setCommodityType(detailsDO.getCommodityId().getCommodityId()); //set commodityType	
				taxDetailsTO.setCommodityTypeName(detailsDO.getCommodityId().getCommodityName()); //set commodityType	
			}
			if(detailsDO.getCollectionAgentId()!=null){
				taxDetailsTO.setAgentName(detailsDO.getCollectionAgentId().getFirstName()); //set commodityType	
			}
			taxDetailsTO.setAlert(StringUtil.checkString(detailsDO.getAlert()));
			taxDetailsTO.setEntryTaxPaid(detailsDO.getEntryTaxPaidAmt());
			taxDetailsTO.setTotalTaxAmt(detailsDO.getTotalAmtCollected());
			taxDetailsTO.setPayableBy(detailsDO.getPayableBy());
			taxDetailsTO.setStatus(detailsDO.getCaseClose()); //status of tax details
			taxDetailsTO.setEmailID(StringUtil.checkString(detailsDO.getEmail())); //status of tax details
			taxDetailsTO.setPhoneNumber(StringUtil.checkString(detailsDO.getPhoneNo())); //status of tax details
		}	
		return taxDetailsTO;
	}

	/**
	 * Copy d o2 to.
	 *
	 * @param paymentDetailsDO the payment details do
	 * @return the entry tax details to
	 * @throws CGSystemException the cG system exception
	 */
	public EntryTaxPaymentDtlsTO copyDO2TO(EntryTaxPaymentDtlsDO paymentDetailsDO) throws CGSystemException {
		EntryTaxPaymentDtlsTO taxPaymentDetailsTO = null;
		if(paymentDetailsDO!=null) {
			taxPaymentDetailsTO = new EntryTaxPaymentDtlsTO();

			taxPaymentDetailsTO.setPaymentMode(StringUtil.checkString(paymentDetailsDO.getPaymentMode()));
			taxPaymentDetailsTO.setChequeNumber(StringUtil.checkString(paymentDetailsDO.getChequeNumber()));
			if(paymentDetailsDO.getBankId()!=null) {
				taxPaymentDetailsTO.setBankName(StringUtil.checkString(paymentDetailsDO.getBankId().getBankName()));
			}	
			taxPaymentDetailsTO.setAmountPaidDate(DateFormatterUtil.getDDMMYYYYDateString(paymentDetailsDO.getPaymentDate()));
			taxPaymentDetailsTO.setAmountCollected(paymentDetailsDO.getAmtCollected());
			taxPaymentDetailsTO.setAmountRecdDate(DateFormatterUtil.getDDMMYYYYDateString(paymentDetailsDO.getAmtRecdDate()));
		}	
		return taxPaymentDetailsTO;
	}

	/**
	 * Copy entry tax config t o2 do.
	 *
	 * @param to the to
	 * @return the entry tax config do
	 * @throws CGSystemException the cG system exception
	 */
	public EntryTaxConfigDO copyEntryTaxConfigTO2DO(EntryTaxConfigTO to)throws CGSystemException{
		EntryTaxConfigDO configDO = new EntryTaxConfigDO();

		try {				
			configDO.setCustomerType(to.getCustomerType());				
			//Following needs to mapped with their respective DOs (FK)
			if(to.getTaxType() != null){
				TaxDO taxDO = new TaxDO();
				taxDO.setTaxId(to.getTaxType());
				configDO.setEntryTaxTypeID(taxDO);
			}

			//Check Config Type
			if(to.getConfigType().equalsIgnoreCase("customer")){

				if(to.getBranchId() != null){
					OfficeDO officeDO = new OfficeDO();
					officeDO.setOfficeId(to.getBranchId());
					configDO.setBranchOfficeID(officeDO);
				}
				if(to.getRegionId() != null){
					OfficeDO officeDO = new OfficeDO();
					officeDO.setOfficeId(to.getRegionId());
					configDO.setRegionOfficeID(officeDO);
				}

				if(to.getCustomerType() != null 
						&& to.getCustomerType().equalsIgnoreCase(EntryTaxConstants.OCTROI_CUST_DParty)){

					CustomerDO customerDO = new CustomerDO();
					customerDO.setCustomerId(to.getFranchiseeDPCode());
					configDO.setDPartyID(customerDO);

				}else if(to.getCustomerType() != null 
						&& to.getCustomerType().equalsIgnoreCase(EntryTaxConstants.OCTROI_CUST_FRANCHISEE)){
					FranchiseeDO frDo = new FranchiseeDO();
					frDo.setFranchiseeId(to.getFranchiseeDPCode());
					configDO.setFranchiseeID(frDo);
				}else if(to.getCustomerType() != null 
						&& to.getCustomerType().equalsIgnoreCase(EntryTaxConstants.ENTRYTAX_AGENT)){
					CollectionAgentDO agentDO = new CollectionAgentDO();
					agentDO.setAgentId(to.getAgentCode());
					configDO.setCollectionAgentId(agentDO);
				}

			}else{
				configDO.setBranchOfficeID(null);
				configDO.setRegionOfficeID(null);
				configDO.setDPartyID(null);
				configDO.setFranchiseeID(null);
				configDO.setCollectionAgentId(null);
			}

			if(to.getConfigType().equalsIgnoreCase("state")){
				//state 			
				if(to.getStateId() != null){
					//StateDO stateDO = (StateDO) session.get(StateDO.class, to.getStateId());
					StateDO stateDO = new StateDO();
					stateDO.setStateId(to.getStateId());
					configDO.setState(stateDO);
				}
			}else {
				configDO.setState(null);
			}

			if(to.getConfigType().equalsIgnoreCase("city")){
				//state and city
				if(to.getCityId() != null){
					//CityDO cityDO = (CityDO) session.get(CityDO.class, to.getCityId());
					CityDO cityDO = new CityDO();
					cityDO.setCityId(to.getCityId());
					configDO.setCityID(cityDO);
				}
				if(to.getStateId() != null){
					//StateDO stateDO = (StateDO) session.get(StateDO.class, to.getStateId());
					StateDO stateDO = new StateDO();
					stateDO.setStateId(to.getStateId());
					configDO.setState(stateDO);
				}
			}else{
				configDO.setCityID(null);				
			}

			configDO.setChargeType(to.getChargeType());				
			//commodity mapper table id to be save	

			configDO.setChargeMethod(to.getChargeMethod());
			configDO.setChargeValue(to.getChargeValue()); 
			configDO.setSapAutomation(to.getSapAutomation());

			if(to.getEntryTaxConfigId() != null && to.getEntryTaxConfigId() != 0){
				configDO.setEntryTaxConfigID(to.getEntryTaxConfigId());
			}
		} catch (Exception e) {
			logger.error("EntryTaxUtil::copyEntryTaxConfigTO2DO::Exception occured:"
					+e.getMessage());
		}
		return configDO;
	}
}
