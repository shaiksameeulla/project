/**
 * 
 */
package com.cg.lbs.bcun.service.outbound.officefinder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.cg.lbs.bcun.utility.BcunStockUtil;
import com.ff.domain.stockmanagement.operations.issue.BcunStockIssueDO;

/**
 * @author mohammes
 *
 */
public class StockIssueTOBranchOutboundOfficeFinderServiceImpl implements
		OutboundOfficeFinderService {

	@Override
	public Set<Integer> getAllOutboundOffices(Integer fromOffice,
			BcunDatasyncService bcunService, List<CGBaseDO> baseList) {
		Set<Integer> destinationOffice=new HashSet<>(baseList.size()+1);
		destinationOffice.add(fromOffice);
		for(CGBaseDO baseDo: baseList){
			if(baseDo instanceof BcunStockIssueDO){
				BcunStockIssueDO stockIssueDO=(BcunStockIssueDO)baseDo;
				BcunStockUtil.populatingStockIssuedToPartyOfficeForStockIssue(bcunService, destinationOffice,
						stockIssueDO);
			}
		}
		return destinationOffice;
	}


}
