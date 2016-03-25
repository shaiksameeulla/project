/**
 * 
 */
package com.cg.lbs.bcun.utility;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.constant.BcunDataFormaterConstants;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.ff.domain.stockmanagement.operations.issue.BcunStockIssueDO;
import com.ff.domain.stockmanagement.operations.transfer.BcunStockTransferDO;

/**
 * @author mohammes
 *
 */
public final class BcunStockUtil {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BcunStockUtil.class);
	/**
	 * @param bcunService
	 * @param destinationOffice
	 * @param namedQryCust
	 * @param params1
	 * @param values1
	 */
	public static List <Integer> getCustomerOfficeForContractByShippedToCode(BcunDatasyncService bcunService,String shippedToCode) {
		LOGGER.trace("BcunStockUtil::getCustomerOfficeForContractByShippedToCode ::START FOR shippedToCode :["+shippedToCode+"]");
		List <Integer> repOffice=null;
		if(!StringUtil.isStringEmpty(shippedToCode)){
			String namedQry=BcunDataFormaterConstants.QRY_GET_OFFICE_ID_BY_SHIPPED_TO_CODE;
			String[] params={BcunDataFormaterConstants.QRY_PARAM_SHIPPEDTOCODE};
			Object[] values={shippedToCode.trim()};
			repOffice=bcunService.getNumbersByNamedQueryAndNamedParam(namedQry, params, values);

		}
		LOGGER.trace("BcunStockUtil::getCustomerOfficeForContractByShippedToCode ::ENDS FOR shippedToCode :["+shippedToCode+"] Ends With Reporting office: " +repOffice);
		return repOffice;
	}

	public static List <Integer> getCustomerOfficeFromCustomer(BcunDatasyncService bcunService,Integer customerId) {
		LOGGER.trace("BcunStockUtil::getCustomerOfficeFromCustomer ::START FOR customerId :["+customerId+"]");
		List <Integer> repOffice=null;
		if(!StringUtil.isEmptyInteger(customerId)){
			String namedQry=BcunDataFormaterConstants.QRY_GET_OFFICEID_BY_CUSTOMER_ID;
			String params[]={"customerId"};
			Object values[]={customerId};
			repOffice=bcunService.getNumbersByNamedQueryAndNamedParam(namedQry, params, values);

		}
		LOGGER.trace("BcunStockUtil::getCustomerOfficeFromCustomer ::Ends FOR customerId :["+customerId+"] repOffice :"+repOffice);
		return repOffice;
	}
	public static List <Integer> getCustomerOfficeFromDeliveryContract(BcunDatasyncService bcunService,Integer customerId) {
		LOGGER.trace("BcunStockUtil::getCustomerOfficeFromDeliveryContract ::START FOR customerId :["+customerId+"]");
		List <Integer> repOffice=null;
		if(!StringUtil.isEmptyInteger(customerId)){
			String namedQry=BcunDataFormaterConstants.QRY_GET_OFFICEID_BY_CUSTOMER_ID_FOR_CONTRACT;
			String params[]={"customerId"};
			Object values[]={customerId};
			repOffice=bcunService.getNumbersByNamedQueryAndNamedParam(namedQry, params, values);

		}
		LOGGER.trace("BcunStockUtil::getCustomerOfficeFromCustomer ::Ends FOR customerId :["+customerId+"] repOffice :"+repOffice);
		return repOffice;
	}



	public static List <Integer> getEmployeeOfficeByEmpId(BcunDatasyncService bcunService,Integer employeeId) {
		LOGGER.trace("BcunStockUtil::getEmployeeOfficeByEmpId ::START FOR employeeId :["+employeeId+"]");
		List <Integer> repOffice=null;
		if(!StringUtil.isEmptyInteger(employeeId)){
			String namedQry=BcunDataFormaterConstants.QRY_GET_OFFICE_ID_BY_EMP_ID;
			String params[]={"employeeId"};
			Object values[]={employeeId};
			repOffice=bcunService.getNumbersByNamedQueryAndNamedParam(namedQry, params, values);

		}
		LOGGER.trace("BcunStockUtil::getEmployeeOfficeByEmpId ::ENDS FOR employeeId :["+employeeId+"] and office :"+repOffice);
		return repOffice;
	}

	public static String getShippedTOCodeFromStockIssue(BcunDatasyncService bcunService,Integer customerId) {
		return getShippedTOCodeFromStock(bcunService, customerId, BcunDataFormaterConstants.QRY_GET_STOCK_ISSUE_SHIPPED_TO_CODE_BY_CUSTOMER_ID);
	}

	public static String getShippedTOCodeFromStockTransfer(BcunDatasyncService bcunService,Integer customerId) {
		return getShippedTOCodeFromStock(bcunService, customerId, BcunDataFormaterConstants.QRY_GET_STOCK_TRANSFER_SHIPPED_TO_CODE_BY_CUSTOMER_ID);
	}

	private static String getShippedTOCodeFromStock(BcunDatasyncService bcunService,Integer customerId,String queryName) {
		List <String> repOffice=null;
		LOGGER.trace("BcunStockUtil::getShippedTOCodeFromStock ::START FOR customerId :["+customerId+"] Query :["+queryName+"]");
		if(!StringUtil.isEmptyInteger(customerId)){
			String namedQry=queryName;
			String params[]={"customerId"};
			Object values[]={customerId};
			repOffice=(List <String>)bcunService.getAnonymusTypeDataByNamedQueryAndNamedParam(namedQry, params, values);

		}
		LOGGER.trace("BcunStockUtil::getShippedTOCodeFromStock ::End FOR customerId :["+customerId+"] Query :["+queryName+"] result office :"+repOffice);
		return !CGCollectionUtils.isEmpty(repOffice)?repOffice.get(0):null;
	}


	public static List<Integer> getCustomerOfficeByCustomerIdForStock(BcunDatasyncService bcunService,Integer customerId,Integer transactionOfficeId) {
		LOGGER.trace("BcunStockUtil::getCustomerOfficeByCustomerIdForStock ::End FOR customerId :["+customerId+"] ");
		List<Integer> officeList=null;
		officeList =getCustomerOfficeFromCustomer(bcunService, customerId);

		if(CGCollectionUtils.isEmpty(officeList) || StringUtil.isEmptyInteger(officeList.get(0)) ){
			String shippedToCode=getShippedTOCodeFromStockIssue(bcunService, customerId);

			if(StringUtil.isStringEmpty(shippedToCode)){
				shippedToCode=getShippedTOCodeFromStockTransfer(bcunService, customerId);
			}
			LOGGER.trace("BcunStockUtil::getCustomerOfficeByCustomerIdForStock :: getting Customer Office :["+customerId+"]  shippedToCode:["+shippedToCode+"]");
			officeList = getCustomerOfficeForContractByShippedToCode(bcunService,shippedToCode);
		}

		if(CGCollectionUtils.isEmpty(officeList) || StringUtil.isEmptyInteger(officeList.get(0))){
			LOGGER.trace("BcunStockUtil::getCustomerOfficeByCustomerIdForStock :: getting Customer Office :["+customerId+"]  from Pick delivery Contract");
			officeList =getCustomerOfficeFromDeliveryContract(bcunService, customerId);
		}

		if(!CGCollectionUtils.isEmpty(officeList) && !StringUtil.isEmptyInteger(officeList.get(0))){
			officeList = extractReportingOfficeOfCustomerByTransactionOffice(transactionOfficeId, officeList, bcunService);
		}
		LOGGER.trace("BcunStockUtil::getCustomerOfficeByCustomerIdForStock :: getting Customer Office :["+customerId+"]  officeList:["+officeList+"]");

		return officeList;
	}

	/**
	 * Gets the all offices of branch city including Hubs
	 *
	 * @param bcunService the bcun service
	 * @param officeId the office id
	 * @return the all offices of branch city
	 */
	public static List<Integer> getAllOfficesOfBranchCity(BcunDatasyncService bcunService,Integer officeId){
		List<Integer> officeList=null;
		String namedQuery=BcunDataFormaterConstants.QRY_GET_ALL_OFFICES_OF_THE_BRANCH_CITY;
		String params[]={BcunDataFormaterConstants.QRY_PARAM_BRANCHID};
		LOGGER.trace("BcunStockUtil::getAllOfficesOfBranchCity ::START Office :["+officeId+"] Query :["+namedQuery+"] ");
		Object values[]={officeId};
		officeList= bcunService.getNumbersByNamedQueryAndNamedParam(namedQuery, params, values);
		LOGGER.trace("BcunStockUtil::getAllOfficesOfBranchCity ::End Office :["+officeId+"] Query :["+namedQuery+"] officeList :"+officeList);
		return officeList;
	}

	/**
	 * @param fromOffice
	 * @param bcunService
	 * @return
	 */
	public static  List<Integer> getAllHubsOfBranchCity(Integer fromOffice,
			BcunDatasyncService bcunService) {
		//All Hubs Offices under the office city
		String namedQuery=BcunDataFormaterConstants.QRY_GET_ALL_HUBS_OF_BRANCH_CITY;
		String params[]={BcunDataFormaterConstants.QRY_PARAM_BRANCHID};
		LOGGER.trace("BcunStockUtil::getAllHubsOfBranchCity ::START Office :["+fromOffice+"] Query :["+namedQuery+"] ");

		Object values[]={fromOffice};
		List<Integer> officeList=bcunService.getNumbersByNamedQueryAndNamedParam(namedQuery, params, values);
		LOGGER.trace("BcunStockUtil::getAllHubsOfBranchCity ::ENDS Office :["+fromOffice+"] Query :["+namedQuery+"]  result :"+officeList);
		return officeList;
	}

	public static  List<Integer> getAllHubsOfBranchCityForStockCustomer(String officeType,Integer loggedInoffice, List<Integer> officeList,
			BcunDatasyncService bcunService) {
		//All Hubs Offices under the office city
		String namedQuery=BcunDataFormaterConstants.QRY_GET_ALL_HUBS_FORCUSTOEMR_WITH_LOGGIN_OFFICE;
		LOGGER.trace("BcunStockUtil::getAllHubsOfBranchCityForStockCustomer ::START Office :["+loggedInoffice+"] Query :["+namedQuery+"] ");

		List<Integer> destinationList=bcunService.getDestinationOfficeForStockCustomer(namedQuery, officeType, loggedInoffice, officeList);
		LOGGER.trace("BcunStockUtil::getAllHubsOfBranchCityForStockCustomer ::ENDS Office :["+loggedInoffice+"] Query :["+namedQuery+"]  result :"+destinationList);
		return destinationList;
	}

	public static  List<Integer> extractReportingOfficeOfCustomerByTransactionOffice(Integer loggedInoffice, List<Integer> officeList,
			BcunDatasyncService bcunService) {
		String officeType=null;
		String namedQuery=null;
		officeType = getOfficeTypeByOffice(loggedInoffice, bcunService);
		if(!StringUtil.isStringEmpty(officeType)){
			if(officeType.equalsIgnoreCase(CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE)){
				namedQuery=BcunDataFormaterConstants.QRY_GET_ALL_OFFICE_FROM_RHO_CONTRACT_FOR_STOCK_CUSTOMER;
			}
		}
		if(StringUtil.isStringEmpty(namedQuery)){
			namedQuery=BcunDataFormaterConstants.QRY_GET_ALL_OFFICE_FROM_BRANCH_CONTRACT_FOR_STOCK_CUSTOMER;
		}
		LOGGER.trace("BcunStockUtil::extractReportingOfficeOfCustomerByIssuingOffice ::START Office :["+loggedInoffice+"] Query :["+namedQuery+"] ");

		List<Integer> extractReportingOffice=bcunService.getDestinationOfficeForStockCustomer(namedQuery, null, loggedInoffice, officeList);
		LOGGER.trace("BcunStockUtil::extractReportingOfficeOfCustomerByIssuingOffice ::ENDS Office :["+loggedInoffice+"] Query :["+namedQuery+"]  result :"+extractReportingOffice);
		return extractReportingOffice;
	}

	/**
	 * @param loggedInoffice
	 * @param bcunService
	 * @param namedQuery
	 * @return
	 */
	public static String getOfficeTypeByOffice(Integer loggedInoffice,
			BcunDatasyncService bcunService) {
		String officeType=null;
		LOGGER.trace("BcunStockUtil::getOfficeTypeByOffice ::START Office :["+loggedInoffice+"]");
		List<String> officeTypeList=null;
		officeTypeList = (List<String>)bcunService.getAnonymusTypeDataByNamedQueryAndNamedParam(BcunDataFormaterConstants.QRY_GET_OFFICE_TYPE_BY_OFFICE_ID,new String[]{BcunDataFormaterConstants.QRY_PARAM_BRANCHID},new Object[]{loggedInoffice});
		if(!CGCollectionUtils.isEmpty(officeTypeList) && !StringUtil.isStringEmpty(officeTypeList.get(0))){
			officeType=officeTypeList.get(0);
		}
		LOGGER.trace("BcunStockUtil::getOfficeTypeByOffice ::End's with Office :["+loggedInoffice+"] Office Type ["+officeType+"]");
		return officeType;
	}



	/**
	 * @param bcunService
	 * @param destinationOffice
	 * @param stockDO
	 */
	public static void populatingStockIssuedToPartyOfficeForStockIssue(
			BcunDatasyncService bcunService, Set<Integer> destinationOffice,
			BcunStockIssueDO stockDO) {
		List<Integer> officeList=null;
		if(!StringUtil.isEmptyInteger(stockDO.getIssuedToCustomerId())){
			//getting customer reporting office
			officeList =getCustomerOfficeFromCustomer(bcunService, stockDO.getIssuedToCustomerId());
			if( CGCollectionUtils.isEmpty(officeList) || StringUtil.isEmptyInteger(officeList.get(0))){
				officeList = getCustomerOfficeByCustomerIdForStock(bcunService,stockDO.getIssuedToCustomerId(),stockDO.getIssueOfficeId());
			}
			/**BR:2 getting  all  Hubs if the transferFrom Customer office city */
			if(!CGCollectionUtils.isEmpty(officeList)&&!StringUtil.isEmptyInteger(officeList.get(0))){
				List<Integer> destinationList=getAllHubsOfBranchCityForStockCustomer(CommonConstants.OFF_TYPE_HUB_OFFICE, stockDO.getIssueOfficeId(), officeList, bcunService);
				if(!CGCollectionUtils.isEmpty(destinationList)){
					officeList.addAll(destinationList);
				}
			}



		}else if(!StringUtil.isEmptyInteger(stockDO.getIssuedToFranchiseeId())){
			officeList =getCustomerOfficeFromCustomer(bcunService, stockDO.getIssuedToFranchiseeId());
			if(CGCollectionUtils.isEmpty(officeList) || StringUtil.isEmptyInteger(officeList.get(0))){
				officeList = getCustomerOfficeByCustomerIdForStock(bcunService,stockDO.getIssuedToFranchiseeId(),stockDO.getIssueOfficeId());
			}

			/**BR:2 getting  all  Hubs if the transferFrom Customer office city */
			if(!CGCollectionUtils.isEmpty(officeList)&&!StringUtil.isEmptyInteger(officeList.get(0))){
				List<Integer> destinationList=getAllHubsOfBranchCityForStockCustomer(CommonConstants.OFF_TYPE_HUB_OFFICE, stockDO.getIssueOfficeId(), officeList, bcunService);
				if(!CGCollectionUtils.isEmpty(destinationList)){
					officeList.addAll(destinationList);
				}
			}
		}else if(!StringUtil.isEmptyInteger(stockDO.getIssuedToBAId())){/*
			officeList =BcunStockUtil.getCustomerOfficeFromCustomer(bcunService, stockDO.getIssuedToBAId());
			if(CGCollectionUtils.isEmpty(officeList) || StringUtil.isEmptyInteger(officeList.get(0))){
				officeList = getCustomerOfficeForContractByShippedToCode(bcunService,stockDO.getShippedToCode());
			}

			List <Integer> baOfficeList=getAllOfficesOfBranchCity(bcunService, stockDO.getIssueOfficeId());
			if(!CGCollectionUtils.isEmpty(officeList) && !StringUtil.isEmptyInteger(officeList.get(0))){
				if(!CGCollectionUtils.isEmpty(baOfficeList)){
					officeList.addAll(baOfficeList);
				}
			}else{
				officeList=baOfficeList;
			}
		*/
			Set<Integer> destinationOfficeSet=getSiblingsOrDescendentOfOffice(stockDO.getIssueOfficeId(), bcunService);
			officeList= new ArrayList<Integer>(destinationOfficeSet);
		}
		else if(!StringUtil.isEmptyInteger(stockDO.getIssuedToPickupBoyId())){
			officeList =getEmployeeOfficeByEmpId(bcunService, stockDO.getIssuedToPickupBoyId());
		}else if(!StringUtil.isEmptyInteger(stockDO.getIssuedToOfficeId())){
			/*** if stock issue to Branch ,issue data must travelled to all the branches*/

			officeList=getAllOfficesOfBranchCity(bcunService, stockDO.getIssuedToOfficeId());
		}

		if(!CGCollectionUtils.isEmpty(officeList)){
			destinationOffice.addAll(officeList);
		}
		destinationOffice.add(stockDO.getIssueOfficeId());
	}

	public static void populateStockTransferedToAndFromPartyOfficeForStockTransfer(
			BcunDatasyncService bcunService, Set<Integer> destinationOffice,
			BcunStockTransferDO stockDO) {
		List<Integer> officeFromList=null;
		List<Integer> officeToList=null;


		if(!StringUtil.isEmptyInteger(stockDO.getTransferFromBaId())){
			//commented to avoid st issue & transfer of Ba's different city
							/*
							*//**BR:1 getting transferFrom BA office Id*//*
							officeFromList =getCustomerOfficeFromCustomer(bcunService, stockDO.getTransferFromBaId());
							*//**BR:2 getting  all office of the transferFrom BA office *//*
							List<Integer> officeList = BcunStockUtil.getAllOfficesOfBranchCity(bcunService,stockDO.getCreatedOfficeId());
							if(!CGCollectionUtils.isEmpty(officeFromList) && !StringUtil.isEmptyInteger(officeFromList.get(0))){
								if(!CGCollectionUtils.isEmpty(officeList)){
									officeFromList.addAll(officeList);
								}
							}else{
								officeFromList=officeList;
							}
				
						*/
			//sending these details to all office's of the in the region
			Set<Integer> destinationOfficeSet=getSiblingsOrDescendentOfOffice(stockDO.getCreatedOfficeId(), bcunService);
			officeFromList= new ArrayList<Integer>(destinationOfficeSet);	
		}else if(!StringUtil.isEmptyInteger(stockDO.getTransferFromCustomerId())){
			/**BR:1 getting transferFrom Customer office Id*/
			officeFromList =getCustomerOfficeFromCustomer(bcunService, stockDO.getTransferFromCustomerId());
			if(CGCollectionUtils.isEmpty(officeFromList) || StringUtil.isEmptyInteger(officeFromList.get(0))){
				officeFromList = getCustomerOfficeByCustomerIdForStock(bcunService,stockDO.getTransferFromCustomerId(),stockDO.getCreatedOfficeId());
			}
			/**BR:2 getting  all  Hubs if the transferFrom Customer office city */
			if(!CGCollectionUtils.isEmpty(officeFromList)&&!StringUtil.isEmptyInteger(officeFromList.get(0))){
				List<Integer> destinationList=getAllHubsOfBranchCityForStockCustomer(CommonConstants.OFF_TYPE_HUB_OFFICE, stockDO.getCreatedOfficeId(), officeFromList, bcunService);
				if(!CGCollectionUtils.isEmpty(destinationList)){
					officeFromList.addAll(destinationList);
				}
			}
		}else if(!StringUtil.isEmptyInteger(stockDO.getTransferFromEmpId())){
			/**BR:1 getting transferFrom Employee office Id*/
			officeFromList =getEmployeeOfficeByEmpId(bcunService, stockDO.getTransferFromEmpId());
			/**BR:2 Get all Hubs of the TransferFromEmp's Office Id*/
			if(!CGCollectionUtils.isEmpty(officeFromList)&& !StringUtil.isEmptyInteger(officeFromList.get(0))){
				List<Integer> officeList = getAllHubsOfBranchCity(officeFromList.get(0),
						bcunService);
				if(!CGCollectionUtils.isEmpty(officeList)){
					officeFromList.addAll(officeList);
				}
			}
		}

		if(!StringUtil.isEmptyInteger(stockDO.getTransferTOBaId())){
			/*
			officeToList=getCustomerOfficeFromCustomer(bcunService, stockDO.getTransferTOBaId());

			List<Integer> officeList = getAllOfficesOfBranchCity(bcunService,stockDO.getCreatedOfficeId());
			if(!CGCollectionUtils.isEmpty(officeToList) && !StringUtil.isEmptyInteger(officeToList.get(0))){
				if(!CGCollectionUtils.isEmpty(officeList)){
					officeToList.addAll(officeList);
				}
			}else{
				officeToList=officeList;
			}

		*/
			//sending these details to all office's of the in the region
			Set<Integer> destinationOfficeSet=getSiblingsOrDescendentOfOffice(stockDO.getCreatedOfficeId(), bcunService);
			officeFromList= new ArrayList<Integer>(destinationOfficeSet);	
		}else if(!StringUtil.isEmptyInteger(stockDO.getTransferTOCustomerId())){
			officeToList =getCustomerOfficeFromCustomer(bcunService, stockDO.getTransferTOCustomerId());
			if(CGCollectionUtils.isEmpty(officeToList) || StringUtil.isEmptyInteger(officeToList.get(0))){
				officeToList = getCustomerOfficeByCustomerIdForStock(bcunService,stockDO.getTransferTOCustomerId(),stockDO.getCreatedOfficeId());
			}
			/**BR:2 getting  all  Hubs if the transferTo Customer office city */
			if(!CGCollectionUtils.isEmpty(officeToList)&&!StringUtil.isEmptyInteger(officeToList.get(0))){
				List<Integer> destinationList=getAllHubsOfBranchCityForStockCustomer(CommonConstants.OFF_TYPE_HUB_OFFICE, stockDO.getCreatedOfficeId(), officeToList, bcunService);
				if(!CGCollectionUtils.isEmpty(destinationList)){
					officeFromList.addAll(destinationList);
				}
			}

		}else if(!StringUtil.isEmptyInteger(stockDO.getTransferTOEmpId())){
			officeToList=getEmployeeOfficeByEmpId(bcunService, stockDO.getTransferTOEmpId());

			List<Integer> officeList = getAllHubsOfBranchCity(stockDO.getCreatedOfficeId(),
					bcunService);
			if(!CGCollectionUtils.isEmpty(officeToList) && !StringUtil.isEmptyInteger(officeToList.get(0))){
				if(!CGCollectionUtils.isEmpty(officeList)){
					officeToList.addAll(officeList);
				}
			}else{
				officeToList=officeList;
			}


		}else if(!StringUtil.isEmptyInteger(stockDO.getTransferTOOfficeId())){
			officeToList= new ArrayList<>();
			officeToList.add(stockDO.getTransferTOOfficeId());
		}

		if(!CGCollectionUtils.isEmpty(officeToList)){
			destinationOffice.addAll(officeToList);
		}
		if(!CGCollectionUtils.isEmpty(officeFromList)){
			destinationOffice.addAll(officeFromList);
		}

		destinationOffice.add(stockDO.getCreatedOfficeId());

	}
	public static Set<Integer> getSiblingsOrDescendentOfOffice(Integer fromOffice,
			BcunDatasyncService bcunService) {
		Set<Integer> destinationOffice=null;
		
		//All Offices under the office
		String namedQuery=BcunDataFormaterConstants.QRY_GET_ALL_OFFICES_UNDER_LOGGED_IN_OFFICE;
		String params[]={BcunDataFormaterConstants.QRY_PARAM_BRANCHID};
		Object values[]={fromOffice};
		List<Integer> officeList=bcunService.getNumbersByNamedQueryAndNamedParam(namedQuery, params, values);
		if(!CGCollectionUtils.isEmpty(officeList)){
			destinationOffice=new HashSet<>(officeList.size()+1);
			destinationOffice.addAll(officeList);
		}else{
			namedQuery=BcunDataFormaterConstants.QRY_SIBLINGS_OF_BRANCH;
			String params1[]={BcunDataFormaterConstants.QRY_PARAM_BRANCHID,BcunDataFormaterConstants.QRY_PRARAM_OFFICE_TYPE_CODE};
			Object values1[]={fromOffice,CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE};
			officeList=bcunService.getNumbersByNamedQueryAndNamedParam(namedQuery, params1, values1);
			if(!CGCollectionUtils.isEmpty(officeList)){
				destinationOffice=new HashSet<>(officeList.size()+1);
				destinationOffice.addAll(officeList);
			}else{
			destinationOffice=new HashSet<>(1);
			}
		}
		destinationOffice.add(fromOffice);
		return destinationOffice;
	}
}
