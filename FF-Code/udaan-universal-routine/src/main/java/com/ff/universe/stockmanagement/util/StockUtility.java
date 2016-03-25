/**
 * 
 */
package com.ff.universe.stockmanagement.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.business.CustomerTO;
import com.ff.business.CustomerTypeTO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.serviceOffering.PaymentModeDO;
import com.ff.domain.stockmanagement.masters.ItemDO;
import com.ff.domain.stockmanagement.masters.StockBusinessAssociateMappingDO;
import com.ff.domain.stockmanagement.masters.StockCustomerMappingDO;
import com.ff.domain.stockmanagement.masters.StockEmployeeMappingDO;
import com.ff.domain.stockmanagement.masters.StockFranchiseeMappingDO;
import com.ff.domain.stockmanagement.masters.StockOfficeMappingDO;
import com.ff.to.stockmanagement.StockUpdateInputTO;
import com.ff.to.stockmanagement.stockrequisition.StockValidationTO;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.stockmanagement.constant.StockUniveralConstants;

/**
 * The Class StockUtility.
 *
 * @author mohammes
 */
public final class StockUtility {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(StockUtility.class);
	
	/**
	 * Gets the stock update query.
	 *
	 * @param partyType the party type
	 * @param isDecrease the is decrease
	 * @return the stock update query
	 */
	public static String getStockUpdateQuery(String partyType,boolean isDecrease){
		String qry=null;
		switch(partyType){
		case UdaanCommonConstants.ISSUED_TO_BA:
			if(isDecrease){
				qry = StockUniveralConstants.QRY_STOCK_DECREASE_BA;
			}else{
				qry = StockUniveralConstants.QRY_STOCK_INCREASE_BA;
			}
			break;
		case UdaanCommonConstants.ISSUED_TO_FR:
			if(isDecrease){
				qry = StockUniveralConstants.QRY_STOCK_DECREASE_FR;
			}else{
				qry = StockUniveralConstants.QRY_STOCK_INCREASE_FR;
			}
			break;
		case UdaanCommonConstants.ISSUED_TO_BRANCH:
			if(isDecrease){
				qry = StockUniveralConstants.QRY_STOCK_DECREASE_OFFICE;
			}else{
				qry = StockUniveralConstants.QRY_STOCK_INCREASE_OFFICE;
			}
			break;
		case UdaanCommonConstants.ISSUED_TO_EMPLOYEE:
			if(isDecrease){
				qry = StockUniveralConstants.QRY_STOCK_DECREASE_EMP;
			}else{
				qry = StockUniveralConstants.QRY_STOCK_INCREASE_EMP;
			}
			break;
		case UdaanCommonConstants.ISSUED_TO_CUSTOMER:
			if(isDecrease){
				qry = StockUniveralConstants.QRY_STOCK_DECREASE_CUSTOMER;
			}else{
				qry = StockUniveralConstants.QRY_STOCK_INCREASE_CUSTOMER;
			}
			break;

		}
		if(!StringUtil.isStringEmpty(qry)){
		LOGGER.debug("StockUtility:::getStockUpdateQuery:::: Query Name selected ["+qry+"] for partyType :["+partyType+"]");
		}else{
			LOGGER.error("StockUtility:::getStockUpdateQuery:::: Query Name Not selected for partyType :["+partyType+"]");
		}
		return qry;

	}
	
	/**
	 * Update stock by query name.
	 *
	 * @param session the session
	 * @param qryName the qry name
	 * @param updateTO the update to
	 * @return the int
	 */
	public static int updateStockByQueryName(Session session, String qryName,
			StockUpdateInputTO updateTO) {
		Query qry=null;
		qry= session.getNamedQuery(qryName);
		qry.setInteger(StockUniveralConstants.QRY_PARAM_TYPE_ID, updateTO.getPartyTypeId());
		qry.setInteger(StockUniveralConstants.QRY_PARAM_ITEM_ID, updateTO.getItemId());
		qry.setInteger(StockUniveralConstants.QRY_PARAM_QUANTITY, updateTO.getQuantity());
		 return qry.executeUpdate();
	}
	
	/**
	 * Gets the stock entity for ba.
	 *
	 * @param updateTO the update to
	 * @return the stock entity for ba
	 */
	public static StockBusinessAssociateMappingDO getStockEntityForBa(StockUpdateInputTO updateTO) {
		StockBusinessAssociateMappingDO sb;
		sb= new StockBusinessAssociateMappingDO();
		sb.setBaId(updateTO.getPartyTypeId());
		sb.setItemId(updateTO.getItemId());
		sb.setQuantity(updateTO.getQuantity());
		return sb;
	}
	
	/**
	 * Gets the stock entity for fr.
	 *
	 * @param updateTO the update to
	 * @return the stock entity for fr
	 */
	public static StockFranchiseeMappingDO getStockEntityForFr(StockUpdateInputTO updateTO) {
		StockFranchiseeMappingDO sf;
		sf= new StockFranchiseeMappingDO();
		sf.setFranchiseeId(updateTO.getPartyTypeId());
		sf.setItemId(updateTO.getItemId());
		sf.setQuantity(updateTO.getQuantity());
		return sf;
	}
	
	/**
	 * Gets the stock entity for office.
	 *
	 * @param updateTO the update to
	 * @return the stock entity for office
	 */
	public static StockOfficeMappingDO getStockEntityForOffice(StockUpdateInputTO updateTO) {
		StockOfficeMappingDO so;
		so= new StockOfficeMappingDO();
		OfficeDO officeDO= new OfficeDO();
		officeDO.setOfficeId(updateTO.getPartyTypeId());
		so.setOfficeDO(officeDO);
		ItemDO itemDO= new ItemDO();
		itemDO.setItemId(updateTO.getItemId());
		so.setItemDO(itemDO);
		so.setQuantity(updateTO.getQuantity());
		return so;
	}
	
	/**
	 * Gets the stock entity for customer.
	 *
	 * @param updateTO the update to
	 * @return the stock entity for customer
	 */
	public static StockCustomerMappingDO getStockEntityForCustomer(StockUpdateInputTO updateTO) {
		StockCustomerMappingDO sc;
		sc= new StockCustomerMappingDO();
		sc.setCustomerId(updateTO.getPartyTypeId());
		sc.setItemId(updateTO.getItemId());
		sc.setQuantity(updateTO.getQuantity());
		return sc;
	}
	
	/**
	 * Gets the stock entity for emp.
	 *
	 * @param updateTO the update to
	 * @return the stock entity for emp
	 */
	public static StockEmployeeMappingDO getStockEntityForEmp(StockUpdateInputTO updateTO) {
		StockEmployeeMappingDO se;
		se= new StockEmployeeMappingDO();
		se.setEmployeeId(updateTO.getPartyTypeId());
		se.setItemId(updateTO.getItemId());
		se.setQuantity(updateTO.getQuantity());
		return se;
	}
	
	/**
	 * Creates the stock for party type.
	 *
	 * @param session the session
	 * @param updateTO the update to
	 * @return the boolean
	 */
	public static Boolean createStockForPartyType(Session session, StockUpdateInputTO updateTO) {
		Boolean result=false;
		switch(updateTO.getPartyType()){
		case UdaanCommonConstants.ISSUED_TO_BA:
			session.save(getStockEntityForBa(updateTO));
			break;
		case UdaanCommonConstants.ISSUED_TO_FR:
			session.save(getStockEntityForFr(updateTO));
			break;
		case UdaanCommonConstants.ISSUED_TO_BRANCH:
			session.save(getStockEntityForOffice(updateTO));
			break;
		case UdaanCommonConstants.ISSUED_TO_EMPLOYEE:
			session.save(getStockEntityForEmp(updateTO));
			break;
		case UdaanCommonConstants.ISSUED_TO_CUSTOMER:
			session.save(getStockEntityForCustomer(updateTO));
			break;

		}
		LOGGER.debug("StockUtility:::createStockForPartyType:::: record inserted  for party type"+updateTO.getPartyType());
		result=true;
		return result;
	}
	
	/**
	 * Update universal stock.
	 *
	 * @param session the session
	 * @param updateTO the update to
	 * @return the boolean
	 */
	public static Boolean updateUniversalStock(Session session, StockUpdateInputTO updateTO){
		String qryName=null;
		int updatedCount;
		qryName= getStockUpdateQuery(updateTO.getPartyType(),updateTO.getIsDecrease());
		LOGGER.debug("StockUtility:::updateUniversalStock:::: Query Name selected ["+qryName+"] for partyType :["+updateTO.getPartyType()+"] And [ isDecrease :"+updateTO.getIsDecrease()+"]");
		updatedCount=updateStockByQueryName(session, qryName, updateTO);
		LOGGER.debug("StockUtility:::updateUniversalStock:::: [updatedCount :"+updatedCount+"]");
		if(updatedCount<=0 && !updateTO.getIsDecrease()){
			LOGGER.debug("StockUtility:::updateUniversalStock:::: record is about to insert for party type"+updateTO.getPartyType());
			createStockForPartyType(session,updateTO);
		}
		return true;
	}
	
	/**
	 * Prepare json for result.
	 *
	 * @param to the to
	 * @return the jSON object
	 */
	public static  JSONObject prepareJsonForResult(StockValidationTO to) {
		JSONObject detailObj =new JSONObject();
		//response Format : startserialnumber,endserialnumber,officeproductcode,startleaf,endleaf,itemId
		 detailObj.put(StockUniveralConstants.RESP_SUCCESS,to.getSeriesList().get(0)+CommonConstants.COMMA+to.getSeriesList().get(to.getSeriesList().size()-1)+CommonConstants.COMMA+to.getOfficeProduct()+CommonConstants.COMMA+to.getLeafList().get(0)+CommonConstants.COMMA+to.getLeafList().get(to.getLeafList().size()-1)+CommonConstants.COMMA+to.getItemId());
		 return detailObj;
	}
	/**
	 * Sets the payment mode.
	 *
	 * @param paymentTypeMap the payment type map
	 * @param stockPaymnt the stock paymnt
	 * @return the payment mode do
	 */
	public static PaymentModeDO preparePaymentModeForCollection(Map<String, Integer> paymentTypeMap,String stockPaymnt) {
		PaymentModeDO paymentModeDO=null;
		if(!StringUtil.isStringEmpty(stockPaymnt) && !CGCollectionUtils.isEmpty(paymentTypeMap)){
			String paymntType=null;
			switch(stockPaymnt){
			case StockUniveralConstants.STOCK_CASH_PAYMENT_TYPE:
				paymntType=CommonConstants.PAYMENT_MODE_CODE_CASH;
				break;
			case StockUniveralConstants.STOCK_DD_PAYMENT_TYPE:
				paymntType=CommonConstants.PAYMENT_MODE_CODE_DD;
				break;
			case StockUniveralConstants.STOCK_CHEQUE_PAYMENT_TYPE:
				paymntType=CommonConstants.PAYMENT_MODE_CODE_CHEQUE;
				break;
				default :
					paymntType=CommonConstants.PAYMENT_MODE_CODE_CHEQUE;
			}
			if(!StringUtil.isStringEmpty(paymntType) && paymentTypeMap.containsKey(paymntType)){
				 paymentModeDO= new PaymentModeDO();
				paymentModeDO.setPaymentId(paymentTypeMap.get(paymntType));
				
			}else{
				//If Collection Module Doesnot Have payment mode as selected in DRS we are making this as CHEQUE payment
					paymentModeDO= new PaymentModeDO();
					paymentModeDO.setPaymentId(paymentTypeMap.get(CommonConstants.PAYMENT_MODE_CODE_CHEQUE));
			}
		}else{
			paymentModeDO= new PaymentModeDO();
			paymentModeDO.setPaymentId(paymentTypeMap.get(CommonConstants.PAYMENT_MODE_CODE_CASH));
		}
		return paymentModeDO;
	}
	
	public static String getEmployeeName(EmployeeDO empDo) {
		String name;
		name = !StringUtil.isStringEmpty(empDo.getFirstName()) ? empDo
				.getFirstName() : "";
				name = !StringUtil.isStringEmpty(name) ? name
						+ (!StringUtil.isStringEmpty(empDo.getLastName()) ? empDo
								.getLastName() : "")
								: "";
		return name;
	}
	public static String getPartyFullName(String issuedToType) {
		String fullIssuedType=null;
		switch(issuedToType){
		case UdaanCommonConstants.ISSUED_TO_BA:
			fullIssuedType=" Business Associate ";
			break;
		case UdaanCommonConstants.ISSUED_TO_FR:
			fullIssuedType=" Franchisee ";
			break;
		case UdaanCommonConstants.ISSUED_TO_BRANCH:
			fullIssuedType=" Office ";
			break;
		case UdaanCommonConstants.ISSUED_TO_EMPLOYEE:
			fullIssuedType=" Employee ";
			break;
		case UdaanCommonConstants.ISSUED_TO_CUSTOMER:
			fullIssuedType=" Customer";
			break;

		}
		LOGGER.debug("StockUtility:::createStockForPartyType:::: record inserted  for party type"+fullIssuedType);
		return fullIssuedType;
	}
	
	public static Map<Integer, String> prepareCustomerMap(List<CustomerTO> result) {
		Map<Integer, String> baMapDtls=null;
		if(!CGCollectionUtils.isEmpty(result)){
			baMapDtls= new HashMap<>(result.size());
			for(CustomerTO baTo:result){
				String code=StringUtil.isStringEmpty(baTo.getShippedToCode())?baTo.getCustomerCode():baTo.getShippedToCode();
				String  name=!StringUtil.isStringEmpty(baTo.getBusinessName())?baTo.getBusinessName():"" ;
				if(!StringUtil.isStringEmpty(code)){
					name= code+FrameworkConstants.CHARACTER_HYPHEN+name;
				}
				name=name.replaceAll(",", "");
				baMapDtls.put(baTo.getCustomerId(),name);
			}
			baMapDtls=CGCollectionUtils.sortByValue(baMapDtls);
		}
		
		return baMapDtls;
	}
	
	public static CustomerTypeTO populateCustomerTypeFromCustomer(CustomerDO customer) {
		CustomerTypeTO customerTypeTO=null;
		if(customer.getCustomerType()!=null){
			customerTypeTO = new CustomerTypeTO();
			customerTypeTO.setCustomerTypeId(customer.getCustomerType().getCustomerTypeId());
			customerTypeTO.setCustomerTypeCode(customer.getCustomerType().getCustomerTypeCode());
			customerTypeTO.setCustomerTypeDesc(customer.getCustomerType().getCustomerTypeDesc());
		}
		return customerTypeTO;
	}
	
}
