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
public class StockTransferBAOutboundOfficeFinderServiceImpl implements
		OutboundOfficeFinderService {

	@Override
	public Set<Integer> getAllOutboundOffices(Integer fromOffice,
			BcunDatasyncService bcunService, List<CGBaseDO> baseList) {
		Set<Integer> destinationOffice=null;
		
		//All Offices under the office
		List<Integer> officeList = BcunStockUtil.getAllOfficesOfBranchCity(bcunService,fromOffice);
		if(!CGCollectionUtils.isEmpty(officeList)){
			destinationOffice=new HashSet<>(officeList.size()+1);
			destinationOffice.addAll(officeList);
		}else{
			destinationOffice=new HashSet<>();
		}
		destinationOffice.add(fromOffice);
		

		for(CGBaseDO baseDO: baseList){
			BcunStockTransferDO transferDO= (BcunStockTransferDO)baseDO;
			BcunStockUtil.populateStockTransferedToAndFromPartyOfficeForStockTransfer(bcunService,destinationOffice,transferDO);
		}
	
		return destinationOffice;
	}


}
