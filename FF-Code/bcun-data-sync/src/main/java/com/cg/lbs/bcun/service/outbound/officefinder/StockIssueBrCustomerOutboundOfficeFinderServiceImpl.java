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
public class StockIssueBrCustomerOutboundOfficeFinderServiceImpl implements
		OutboundOfficeFinderService {

	@Override
	public Set<Integer> getAllOutboundOffices(Integer fromOffice,
			BcunDatasyncService bcunService, List<CGBaseDO> baseList) {
		Set<Integer> destinationOffice=null;
		String namedQuery=BcunDataFormaterConstants.QRY_GET_ALL_HUBS_OF_BRANCH_CITY;
		String params[]={BcunDataFormaterConstants.QRY_PARAM_BRANCHID};
		Object values[]={fromOffice};
		/*All Hubs of the Branch's city*/
		List <Integer> allHubs=bcunService.getNumbersByNamedQueryAndNamedParam(namedQuery, params, values);
		if(!CGCollectionUtils.isEmpty(allHubs)){
			int size=allHubs.size()+baseList.size();
			destinationOffice= new HashSet(size);
			destinationOffice.addAll(allHubs);
			destinationOffice.add(fromOffice);
		}else{
			int size=baseList.size()+1;
			destinationOffice= new HashSet<>(size);
			destinationOffice.add(fromOffice);
		}
		
		for(CGBaseDO baseDO:baseList){
			BcunStockIssueDO stockIssueDO=(BcunStockIssueDO)baseDO;
			BcunStockUtil.populatingStockIssuedToPartyOfficeForStockIssue(bcunService, destinationOffice,
					stockIssueDO);
		}
		return destinationOffice;
	}

}
