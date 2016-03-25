/**
 * 
 */
package com.cg.lbs.bcun.service.outbound.officefinder;

import java.util.List;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.cg.lbs.bcun.utility.BcunStockUtil;

/**
 * @author mohammes
 *
 */
public class StockReceiptOutboundOfficeFinderServiceImpl implements
		OutboundOfficeFinderService {

	@Override
	public Set<Integer> getAllOutboundOffices(Integer fromOffice,
			BcunDatasyncService bcunService, List<CGBaseDO> baseList) {
		return BcunStockUtil.getSiblingsOrDescendentOfOffice(fromOffice, bcunService);}


}
