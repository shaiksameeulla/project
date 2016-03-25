/**
 * 
 */
package com.cg.lbs.bcun.service.outbound.officefinder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.cg.lbs.bcun.utility.BcunStockUtil;
import com.ff.domain.stockmanagement.operations.transfer.BcunStockTransferDO;

/**
 * @author mohammes
 *
 */
public class StockTransferOutboundOfficeFinderServiceImpl implements
		OutboundOfficeFinderService {

	@Override
	public Set<Integer> getAllOutboundOffices(Integer fromOffice,
			BcunDatasyncService bcunService, List<CGBaseDO> baseList) {
		Set<Integer> destinationOffice=null;
		
		List<Integer> officeList = BcunStockUtil.getAllHubsOfBranchCity(fromOffice,
				bcunService);
		if(!CGCollectionUtils.isEmpty(officeList)){
			destinationOffice=new HashSet<>(officeList.size()+1);
			destinationOffice.addAll(officeList);
		}else{
			destinationOffice=new HashSet<>(1);
		}
		for(CGBaseDO baseDO: baseList){
			BcunStockTransferDO transferDO= (BcunStockTransferDO)baseDO;
			BcunStockUtil.populateStockTransferedToAndFromPartyOfficeForStockTransfer(bcunService,destinationOffice,transferDO);
		}
		
		destinationOffice.add(fromOffice);
		return destinationOffice;
	}

	


}
