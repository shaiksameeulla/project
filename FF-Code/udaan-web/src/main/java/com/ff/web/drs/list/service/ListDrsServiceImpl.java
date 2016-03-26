/**
 * 
 */
package com.ff.web.drs.list.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.business.LoadMovementVendorDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.to.drs.AbstractDeliveryTO;
import com.ff.to.drs.DeliveryNavigatorTO;
import com.ff.to.drs.list.ListDrsDetailsTO;
import com.ff.to.drs.list.ListDrsHeaderTO;
import com.ff.web.drs.common.constants.DrsCommonConstants;
import com.ff.web.drs.common.constants.DrsConstants;
import com.ff.web.drs.common.service.DeliveryCommonService;
import com.ff.web.drs.list.dao.ListDrsDAO;
import com.ff.web.drs.util.DrsUtil;
import com.ff.web.util.UdaanWebErrorConstants;

/**
 * @author mohammes
 *
 */
public class ListDrsServiceImpl implements ListDrsService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ListDrsServiceImpl.class);
	
	/** The list drs dao. */
	private ListDrsDAO listDrsDAO;
	
	/** The delivery common service. */
	private DeliveryCommonService deliveryCommonService;

	
	
	/**
	 * @return the listDrsDAO
	 */
	public ListDrsDAO getListDrsDAO() {
		return listDrsDAO;
	}



	/**
	 * @param listDrsDAO the listDrsDAO to set
	 */
	public void setListDrsDAO(ListDrsDAO listDrsDAO) {
		this.listDrsDAO = listDrsDAO;
	}



	/**
	 * Gets the all delivery employees By Office
	 *
	 * @param delvTo the delv to
	 * @return the all delivery employees
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Deprecated
	@Override
	public Map<Integer,String> getAllDeliveryEmployees(AbstractDeliveryTO delvTo)throws CGBusinessException,CGSystemException{
		Map<Integer,String> dlvFieldStaff=null;
		List<EmployeeDO> dlvEmpList= listDrsDAO.getAllDeliveryEmployees(delvTo);
		if(!CGCollectionUtils.isEmpty(dlvEmpList)){
			dlvFieldStaff= new HashMap<>(dlvEmpList.size());
			for(EmployeeDO emp :dlvEmpList){
				String name=null;
				name= !StringUtil.isStringEmpty(emp.getFirstName())?emp.getFirstName():FrameworkConstants.EMPTY_STRING;
				name=!StringUtil.isStringEmpty(name)?name+(!StringUtil.isStringEmpty(emp.getLastName())?(FrameworkConstants.EMPTY_STRING_WITH_SPACE+emp.getLastName()):FrameworkConstants.EMPTY_STRING):FrameworkConstants.EMPTY_STRING;
				dlvFieldStaff.put(emp.getEmployeeId(),emp.getEmpCode()+FrameworkConstants.CHARACTER_HYPHEN+name );
			}
		}else{
			LOGGER.warn("ListDrsServiceImpl :: getAllDeliveryEmployees:: Employee List Does not exist for the(List DRS FF List) Office Code :["+delvTo.getLoginOfficeCode()+"]");
		}
		return dlvFieldStaff;
	}
	
	@Override
	public Map<String,String> getDrsPartyDetailsByDate(AbstractDeliveryTO delvTo)throws CGBusinessException,CGSystemException{
		Map<String,String> dlvFieldStaff=null;
		List<?> dlvEmpList= listDrsDAO.getDrsForDtlsByDate(delvTo);
		if(!CGCollectionUtils.isEmpty(dlvEmpList)){
			dlvFieldStaff= new HashMap<>(dlvEmpList.size());
			for (Object itemType : dlvEmpList) {
				Map map = (Map) itemType;
				EmployeeDO empDO = (EmployeeDO) map.get("emp");
				LoadMovementVendorDO baDO = (LoadMovementVendorDO) map.get("baDO");
				LoadMovementVendorDO franchiseDO = (LoadMovementVendorDO) map.get("franchiseDO");
				LoadMovementVendorDO coCourierDO = (LoadMovementVendorDO) map.get("coCourierDO");
				if(empDO!=null){
					String name= !StringUtil.isStringEmpty(empDO.getFirstName())?empDO.getFirstName():FrameworkConstants.EMPTY_STRING;
					name=!StringUtil.isStringEmpty(name)?name+(!StringUtil.isStringEmpty(empDO.getLastName())?(FrameworkConstants.EMPTY_STRING_WITH_SPACE+empDO.getLastName()):FrameworkConstants.EMPTY_STRING):FrameworkConstants.EMPTY_STRING;
					name =DrsConstants.DRS_FOR_FIELD_STAFF+FrameworkConstants.CHARACTER_HYPHEN+empDO.getEmpCode()+FrameworkConstants.CHARACTER_HYPHEN+name;
					dlvFieldStaff.put(empDO.getEmployeeId()+FrameworkConstants.CHARACTER_TILDE+DrsConstants.DRS_FOR_FIELD_STAFF, name);
				}else {
					LoadMovementVendorDO vendorDO=null;
					String vendorType =null;
					if(baDO!=null){
						vendorDO=baDO;
						vendorType =DrsConstants.DRS_FOR_BA;
					}else if(franchiseDO!=null){
						vendorDO=franchiseDO;
						vendorType =DrsConstants.DRS_FOR_FR;
					}else if (coCourierDO!=null){
						vendorDO=coCourierDO;
						vendorType =DrsConstants.DRS_FOR_CO_COURIER;
					}
					String name=vendorType +FrameworkConstants.CHARACTER_HYPHEN+vendorDO.getVendorCode()+FrameworkConstants.CHARACTER_HYPHEN+vendorDO.getBusinessName();
					name=name.replaceAll(",", "");
					dlvFieldStaff.put(vendorDO.getVendorId()+FrameworkConstants.CHARACTER_TILDE+vendorType, name);
				}
			}
			dlvFieldStaff= CGCollectionUtils.sortByValue(dlvFieldStaff);
		}else{
			LOGGER.warn("ListDrsServiceImpl :: getAllDeliveryEmployees:: Employee List Does not exist for the(List DRS FF List) Office Code :["+delvTo.getLoginOfficeCode()+"]");
		}
		return dlvFieldStaff;
	}
	
	/**
	 * Gets the all drs by office and employee.
	 *
	 * @param drsTo the drs to
	 * @return the all drs by office and employee
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public ListDrsHeaderTO getAllDrsByOfficeAndEmployee(ListDrsHeaderTO drsTo)throws CGBusinessException,CGSystemException{
		LOGGER.debug("ListDrsServiceImpl :: getAllDrsByOfficeAndEmployee:: ##START##");
		
		if(!StringUtil.isStringEmpty(drsTo.getDrsFor())){
			String drsForIdentifier[]=drsTo.getDrsFor().split(FrameworkConstants.CHARACTER_TILDE);
			if(!StringUtil.isEmpty(drsForIdentifier) && drsForIdentifier.length ==2 ){
				drsTo.setDrsPartyId(StringUtil.parseInteger(drsForIdentifier[0]));
				switch(drsForIdentifier[1]){
				case DrsConstants.DRS_FOR_FIELD_STAFF:
					drsTo.setQueryName(DrsCommonConstants.QRY_DRS_BY_OFFICE_AND_EMPLOYEE);
					break;
				case DrsConstants.DRS_FOR_BA:
					drsTo.setQueryName(DrsCommonConstants.QRY_DRS_BY_OFFICE_AND_BA);
					break;
				case DrsConstants.DRS_FOR_CO_COURIER:
					drsTo.setQueryName(DrsCommonConstants.QRY_DRS_BY_OFFICE_AND_CC);
					break;
				case DrsConstants.DRS_FOR_FR:
					drsTo.setQueryName(DrsCommonConstants.QRY_DRS_BY_OFFICE_AND_FR);
					break;
					
				default :
					//throw Business Exception
					DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_INVALID_SEARCH_RESULTS);

				}
			}else{
				//throw Business Exception
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_INVALID_SEARCH_RESULTS);
			}
		}else{
			//throw Business Exception
			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_INVALID_SEARCH_RESULTS);
		}
		List<String> drsNumberList= listDrsDAO.getAllDrsByOfficeAndEmployee(drsTo);
		List<DeliveryNavigatorTO> navaigatorList=null;
		if(!CGCollectionUtils.isEmpty(drsNumberList)){
			navaigatorList = deliveryCommonService.getDrsNavigationDetails(drsNumberList, drsTo.getDrsScreenCode());
		
			if(!CGCollectionUtils.isEmpty(navaigatorList)){
				List<ListDrsDetailsTO> listDrsDtls=new ArrayList<>(navaigatorList.size());
				for(DeliveryNavigatorTO navTO:navaigatorList){
					ListDrsDetailsTO dtls= new ListDrsDetailsTO();
					dtls.setDrsNumber(navTO.getDrsNumber());
					dtls.setUrl(navTO.getNavigateToUrl()+navTO.getDrsNumber());
					listDrsDtls.add(dtls);
				}
				Collections.sort(listDrsDtls);
				drsTo.setListDrsDetails(listDrsDtls);
			}else{
				//throw Business Exception
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_INVALID_SEARCH_RESULTS);
			}
		}else{
			/** No DRS EXIST for the Given Load Number */
			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.LIST_DRS_SEARCH_MSG);

			//throw Business Exception ie : DRS does not exist for the load number
		}
		LOGGER.debug("ListDrsServiceImpl :: getAllDrsByOfficeAndEmployee:: ##END##");
		return drsTo;
	}



	/**
	 * @return the deliveryCommonService
	 */
	public DeliveryCommonService getDeliveryCommonService() {
		return deliveryCommonService;
	}



	/**
	 * @param deliveryCommonService the deliveryCommonService to set
	 */
	public void setDeliveryCommonService(DeliveryCommonService deliveryCommonService) {
		this.deliveryCommonService = deliveryCommonService;
	}
	
}
