package com.cg.lbs.bcun.service.outbound.officefinder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.cg.lbs.bcun.constant.BcunDataFormaterConstants;
import com.cg.lbs.bcun.service.BcunDatasyncService;

public class BookingOutboundOfficeFinderImpl implements
		OutboundOfficeFinderService {

	@Override
	public Set<Integer> getAllOutboundOffices(Integer originOffice,
			BcunDatasyncService bcunService, List<CGBaseDO> baseList) {
		Set<Integer> destOfficeIds = null;
		String namedQuery = BcunDataFormaterConstants.QRY_GET_DESTINATIONS_OFFICES_EB_BOOKING;
		String params[] = {
				BcunDataFormaterConstants.QRY_PARAM_BRANCHID };
		Object values[] = { originOffice};
		List<Integer> officeList = bcunService
				.getNumbersByNamedQueryAndNamedParam(namedQuery, params, values);
		if (!CGCollectionUtils.isEmpty(officeList)) {
			destOfficeIds = new HashSet();
			destOfficeIds.addAll(officeList);
		}
		return destOfficeIds;
	}
}
