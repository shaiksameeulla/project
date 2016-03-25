package com.cg.lbs.bcun.service.outbound.officefinder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.ff.domain.manifest.ConsignmentReturnDO;

/**
 * The Class RthConsgReturnOfficeFinder.
 * 
 * @author narmdr
 */
public class RthConsgReturnOfficeFinder implements OutboundOfficeFinderService {

	@Override
	public Set<Integer> getAllOutboundOffices(Integer fromOffice,
			BcunDatasyncService bcunService, List<CGBaseDO> baseList) {
		Set<Integer> allHubOfficeIds = null;
		
		/*All Hub Office Dtls*/

		List<Integer> allHubOffIds = null;
		allHubOffIds = (List<Integer>) bcunService
				.getNumbersByNamedQueryAndNamedParam(
						"getAllHubsOftheBranchCity",
						new String[] { "branchId" },
						new Object[] { fromOffice });
		
		/*List <Integer> allRthConsgReturnHubOfficeIds = null;
		for (CGBaseDO headerDO : baseList) {
			allRthConsgReturnHubOfficeIds = getallHubOfficeIdsForRthConsgReturn(
					fromOffice, bcunService, headerDO);
		}*/
		if(!CGCollectionUtils.isEmpty(allHubOffIds)){
			allHubOfficeIds = new HashSet<>();
			allHubOfficeIds.addAll(allHubOffIds);
		}
		return allHubOfficeIds;
	}

	private List<Integer> getallHubOfficeIdsForRthConsgReturn(
			Integer fromOffice, BcunDatasyncService bcunService,
			CGBaseDO headerDO) {
		ConsignmentReturnDO consignmentReturnDO = (ConsignmentReturnDO) headerDO;
		List<Integer> allHubOfficeIds = null;
		
		if (consignmentReturnDO.getReturnType().equals("H")) {

			allHubOfficeIds = (List<Integer>) bcunService
					.getNumbersByNamedQueryAndNamedParam(
							"getUnsyncOutboundRthConsgReturnHubOfficeIds",
							new String[] { "consignmentReturnId" },
							new Object[] { consignmentReturnDO
									.getConsignmentReturnId() });
		}

		return allHubOfficeIds;
	}

}
