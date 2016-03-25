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
import com.ff.domain.manifest.BcunManifestDO;

/**
 * @author narmdr
 * 
 */
public class ManifestOutboundOfficeFinderServiceImpl implements
		OutboundOfficeFinderService {

	@Override
	public Set<Integer> getAllOutboundOffices(Integer fromOffice,
			BcunDatasyncService bcunService, List<CGBaseDO> baseList) {

		Set<Integer> allBranchesHubsOfficeIds = null;

		List<Integer> allBranchHubOffIds = null;
		allBranchHubOffIds = (List<Integer>) bcunService
				.getNumbersByNamedQueryAndNamedParam(
						"getAllBranchesHubsOftheBranchCity",
						new String[] { "branchId" },
						new Object[] { fromOffice });
		if (!CGCollectionUtils.isEmpty(allBranchHubOffIds)) {
			allBranchesHubsOfficeIds = new HashSet<>();
			allBranchesHubsOfficeIds.addAll(allBranchHubOffIds);
		}
		/* All Hub Office Dtls */
		// List<Integer> allBranchesHubsIds = null;
		/*for (CGBaseDO headerDO : baseList) {
			List<Integer> allBranchesHubsIds = getAllBranchesHubsOfficeIdsOfOriginAndDest(
					fromOffice, bcunService, headerDO);

			if (!CGCollectionUtils.isEmpty(allBranchesHubsIds)) {
				allBranchesHubsOfficeIds.addAll(allBranchesHubsIds);
			}
		}*/
		
		return allBranchesHubsOfficeIds;
	}

	private List<Integer> getAllBranchesHubsOfficeIdsOfOriginAndDest(
			Integer fromOffice, BcunDatasyncService bcunService,
			CGBaseDO headerDO) {
		BcunManifestDO bcunManifestDO = (BcunManifestDO) headerDO;
		List<Integer> allBranchesHubsOfficeIds = null;

		allBranchesHubsOfficeIds = (List<Integer>) bcunService
				.getNumbersByNamedQueryAndNamedParam(
						"getUnsyncOutboundManifestOfficeIds",
						new String[] { "manifestId" },
						new Object[] { bcunManifestDO.getManifestId() });

		return allBranchesHubsOfficeIds;
	}

	public static Set<Integer> getAllHubsOftheBranchCity(
			BcunDatasyncService bcunService, Integer branchId) {

		Set<Integer> hubsOfficeIds = null;

		List<Integer> allHubOffIds = (List<Integer>) bcunService
				.getNumbersByNamedQueryAndNamedParam(
						"getAllHubsOftheBranchCity",
						new String[] { "branchId" }, new Object[] { branchId });
		if (!CGCollectionUtils.isEmpty(allHubOffIds)) {
			hubsOfficeIds = new HashSet<>();
			hubsOfficeIds.addAll(allHubOffIds);
		}

		return hubsOfficeIds;
	}

	public static Set<Integer> getAllBranchesOfBranchCity(
			BcunDatasyncService bcunService, Integer branchId) {
		Set<Integer> branchesOfficeIds = null;

		List<Integer> allBranchOffIds = (List<Integer>) bcunService
				.getNumbersByNamedQueryAndNamedParam(
						"getAllBrOftheBranchCity",
						new String[] { "branchId" }, new Object[] { branchId });
		if (!CGCollectionUtils.isEmpty(allBranchOffIds)) {
			branchesOfficeIds = new HashSet<>();
			branchesOfficeIds.addAll(allBranchOffIds);
		}

		return branchesOfficeIds;
	}

	public static Set<Integer> getAllBranchesAndHubsOftheBranchCity(
			BcunDatasyncService bcunService, Integer branchId) {
		Set<Integer> hubsOfficeIds = null;

		List<Integer> allHubOffIds = (List<Integer>) bcunService
				.getNumbersByNamedQueryAndNamedParam(
						"getAllBranchesHubsOftheBranchCity",
						new String[] { "branchId" }, new Object[] { branchId });
		if (!CGCollectionUtils.isEmpty(allHubOffIds)) {
			hubsOfficeIds = new HashSet<>();
			hubsOfficeIds.addAll(allHubOffIds);
		}

		return hubsOfficeIds;
	}

}
