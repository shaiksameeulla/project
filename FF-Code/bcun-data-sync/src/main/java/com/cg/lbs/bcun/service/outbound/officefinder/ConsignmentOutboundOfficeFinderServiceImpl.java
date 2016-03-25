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
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.ff.domain.consignment.BcunConsignmentDO;

/**
 * @author amimehta
 *
 */
public class ConsignmentOutboundOfficeFinderServiceImpl implements
		OutboundOfficeFinderService {

	@Override
	public Set<Integer> getAllOutboundOffices(Integer fromOffice,
			BcunDatasyncService bcunService, List<CGBaseDO> baseList) {
		Set<Integer> destinationOffice=null;
		
		/*All Live Office Dtls*/
		List <Integer> allOutManifestDestnOffices=null;
		for (CGBaseDO headerDO : baseList) {
			allOutManifestDestnOffices = getDestnIdsForOutboundManifest(
					fromOffice, bcunService, headerDO);
		}
		if(!CGCollectionUtils.isEmpty(allOutManifestDestnOffices)){
			int size=allOutManifestDestnOffices.size()+baseList.size();
			destinationOffice= new HashSet(size);
			destinationOffice.addAll(allOutManifestDestnOffices);
			
		}
		return destinationOffice;
	}

	
	/*
	 *  comparing  framework  office  id  with  either list  of
	 *  outmanifest  destn  offices  or  manifest  destn  office
	 */
	private List<Integer> getDestnIdsForOutboundManifest(Integer fromOffice, BcunDatasyncService bcunService, CGBaseDO headerDO) {
		List<Integer> manifestIdList;
		List<Integer> allOutManifestDestnOffices = new ArrayList<>();
		BcunConsignmentDO do1 = (BcunConsignmentDO) headerDO;
		
		String[] param1 = { "consgId" };
		Integer[] value1 = { do1.getConsgId() };
		manifestIdList = (List<Integer>)bcunService.getNumbersByNamedQueryAndNamedParam("getManifestIdFromConsgId", param1, value1);
		if(!StringUtil.isEmptyList(manifestIdList)){
		String[] param = { "manifestId" };
		Object[] value = manifestIdList.toArray();
		
		allOutManifestDestnOffices = (List<Integer>) bcunService
				.getNumbersByNamedQueryAndNamedParam(
						"getUnsyncOutboundOutManifestDestination", param,
						value);
		}
		return allOutManifestDestnOffices;
	}

}
