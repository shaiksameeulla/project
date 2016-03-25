/**
 * 
 */
package com.ff.universe.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.organization.EmployeeTO;
import com.ff.universe.ratemanagement.constant.RateUniversalConstants;
import com.ff.universe.stockmanagement.constant.StockUniveralConstants;

/**
 * @author mohammes
 *
 */
public final class UniversalConverterUtil {

	
	public static Map<Integer, String> getEmployeeMapFromList(
			List<EmployeeTO> empDtls) {
		Map<Integer, String> employeeDtls=null;
		if(!CGCollectionUtils.isEmpty(empDtls)){
			employeeDtls= new HashMap<>(empDtls.size());
			for(EmployeeTO empTo:empDtls){
				prepareEmployeeMapFromEmpTO(employeeDtls, empTo);
			}
			employeeDtls=CGCollectionUtils.sortByValue(employeeDtls);
		}
		return employeeDtls;
	}

	/**
	 * @param employeeDtls
	 * @param empTo
	 */
	public static void prepareEmployeeMapFromEmpTO(
			Map<Integer, String> employeeDtls, EmployeeTO empTo) {
		String name = getEmployeeName(empTo);
		employeeDtls.put(empTo.getEmployeeId(),(StringUtil.isStringEmpty(empTo.getEmpCode())?FrameworkConstants.EMPTY_STRING:empTo.getEmpCode())+FrameworkConstants.CHARACTER_HYPHEN+name );
	}

	private static String getEmployeeName(EmployeeTO empTo) {
		String name=null;
		name= !StringUtil.isStringEmpty(empTo.getFirstName())?empTo.getFirstName():FrameworkConstants.EMPTY_STRING;
		name=!StringUtil.isStringEmpty(name)?name+(!StringUtil.isStringEmpty(empTo.getLastName())?(FrameworkConstants.EMPTY_STRING_WITH_SPACE+empTo.getLastName()):FrameworkConstants.EMPTY_STRING):FrameworkConstants.EMPTY_STRING;
		name=name.replaceAll(",", "");
		return name;
	}
	
	public static Map<Integer, EmployeeTO> getEmployeeMapWithEmpTOFromList(
			List<EmployeeTO> empDtls) {
		Map<Integer, EmployeeTO> employeeDtls=null;
		if(!CGCollectionUtils.isEmpty(empDtls)){
			employeeDtls= new HashMap<>(empDtls.size());
			for(EmployeeTO empTo:empDtls){
				
				employeeDtls.put(empTo.getEmployeeId(),empTo );
			}
		}
		return employeeDtls;
	}
	
	public static Map<Integer, String> getEmployeeMapFromEmpMapWithEmpTO(
			Map<Integer, EmployeeTO> emptoMap) {
		Map<Integer, String> employeeDtls=null;
		if(!CGCollectionUtils.isEmpty(emptoMap)){
			employeeDtls= new HashMap<>(emptoMap.size());
			for(Integer empId:emptoMap.keySet()){
				EmployeeTO empTo=emptoMap.get(empId);
				String name = getEmployeeName(empTo);
				employeeDtls.put(empTo.getEmployeeId(),empTo.getEmpCode()+FrameworkConstants.CHARACTER_HYPHEN+name );
			}
		}
		return employeeDtls;
	}
	
	public static Map<Integer, String> prepareMapFromList(List<?> itemTypeList) {
		Map<Integer, String> itemTypeMap=null;
		if(!StringUtil.isEmptyList(itemTypeList)){
			itemTypeMap = new HashMap<Integer, String>(itemTypeList.size());
			for(Object itemType :itemTypeList){
				Map map= (Map)itemType;
				String name=(String)map.get(StockUniveralConstants.TYPE_NAME);
				itemTypeMap.put((Integer)map.get(StockUniveralConstants.TYPE_ID),name.replaceAll(",", ""));
			}
		}
		return itemTypeMap;
	}
	
	public static void prepareValidTaxComponents(Map<String, Double> taxComponents) {
		if(!CGCollectionUtils.isEmpty(taxComponents)&&taxComponents.containsKey(RateUniversalConstants.STATE_TAX_CODE) && taxComponents.containsKey(RateUniversalConstants.SERVICE_TAX_CODE)){
			//it means it's related to JK and remove service related tax information
			taxComponents.remove(RateUniversalConstants.SERVICE_TAX_CODE);
			taxComponents.remove(RateUniversalConstants.EDU_CESS_CODE);
			taxComponents.remove(RateUniversalConstants.HIGHER_EDU_CES_CODE);
			
		}
	}
}
