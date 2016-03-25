/**
 * 
 */
package com.cg.lbs.bcun.service.outbound.officefinder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.constant.BcunDataFormaterConstants;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.cg.lbs.bcun.utility.BcunStockUtil;
import com.ff.domain.stockmanagement.operations.stockreturn.BcunStockReturnDO;

/**
 * @author mohammes
 *
 */
public class StockReturnOutboundOfficeFinderServiceImpl implements
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
		List<Integer> issuedOfficeList= new ArrayList<>(baseList.size());
		for(CGBaseDO returnDO:baseList){
			BcunStockReturnDO stRetrnDO=(BcunStockReturnDO)returnDO;
			if(!StringUtil.isNull(stRetrnDO)){
				
				if(!StringUtil.isEmptyInteger(stRetrnDO.getIssuedOfficeId()) && !issuedOfficeList.contains(stRetrnDO.getIssuedOfficeId())){
					destinationOffice.add(stRetrnDO.getIssuedOfficeId());
					List<Integer> officeList=BcunStockUtil.getAllOfficesOfBranchCity(bcunService, stRetrnDO.getIssuedOfficeId());
					issuedOfficeList.add(stRetrnDO.getIssuedOfficeId());
					if(!CGCollectionUtils.isEmpty(officeList)){
					destinationOffice.addAll(officeList);
					}
				}
				
				if(!StringUtil.isEmptyInteger(stRetrnDO.getReturningOfficeId())){
					destinationOffice.add(stRetrnDO.getReturningOfficeId());
				}
			}

		}
		destinationOffice.addAll(issuedOfficeList);
		return destinationOffice;
	}

}
