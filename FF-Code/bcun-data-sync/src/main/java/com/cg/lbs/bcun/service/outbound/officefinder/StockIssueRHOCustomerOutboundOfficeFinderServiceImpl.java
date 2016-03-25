/**
 * 
 */
package com.cg.lbs.bcun.service.outbound.officefinder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.cg.lbs.bcun.constant.BcunDataFormaterConstants;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.cg.lbs.bcun.utility.BcunStockUtil;
import com.ff.domain.stockmanagement.operations.issue.BcunStockIssueDO;

/**
 * @author mohammes
 *
 */
public class StockIssueRHOCustomerOutboundOfficeFinderServiceImpl implements
		OutboundOfficeFinderService {

	@Override
	public Set<Integer> getAllOutboundOffices(Integer fromOffice,
			BcunDatasyncService bcunService, List<CGBaseDO> baseList) {
		Set<Integer> destinationOffice=null;
		
		//All Hubs of the Branch city
		String namedQuery=BcunDataFormaterConstants.QRY_GET_ALL_OFFICE_BY_OFFICE_CITY;
		String params[]={BcunDataFormaterConstants.QRY_PARAM_BRANCHID,"officeTypeCode"};
		Object values[]={fromOffice,"HO"};
		List <Integer> offices=bcunService.getNumbersByNamedQueryAndNamedParam(namedQuery, params, values);
		if(!CGCollectionUtils.isEmpty(offices)){
			int size=offices.size()+baseList.size();
			destinationOffice= new HashSet<>(size);
			destinationOffice.addAll(offices);
			destinationOffice.add(fromOffice);
		}else{
			int size=baseList.size()+1;
			destinationOffice= new HashSet<>(size);
			destinationOffice.add(fromOffice);
		}
		for(CGBaseDO stockBaseDO: baseList){
			
			BcunStockIssueDO stockDO= (BcunStockIssueDO)stockBaseDO;
			BcunStockUtil.populatingStockIssuedToPartyOfficeForStockIssue(bcunService, destinationOffice,
					stockDO);

		}

		return destinationOffice;
	}

	

	

}
