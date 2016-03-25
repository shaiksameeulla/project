package com.cg.lbs.bcun.service.outbound.officefinder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.cg.lbs.bcun.service.BcunDatasyncService;


public class RateContractOutboundOfficeFinder implements
		OutboundOfficeFinderService {
		
		@Override
		public Set<Integer> getAllOutboundOffices(Integer originOffice,
			BcunDatasyncService bcunService, List<CGBaseDO> baseList) {
		Set<Integer> destOfficeIds = null;


		List <Integer> officeList=(List <Integer>)bcunService.getNumberByNamedQuery("getAllActiveDatasyncOfficeIDList");

		if (!CGCollectionUtils.isEmpty(officeList)) {
			destOfficeIds = new HashSet();
			destOfficeIds.addAll(officeList);
		}
		return destOfficeIds;
		}

}
