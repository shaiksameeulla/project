package com.cg.lbs.bcun.service.outbound.officefinder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.ff.domain.loadmanagement.BcunLoadMovementDO;

public class DispatchOutboundOfficeFinderImpl implements
		OutboundOfficeFinderService {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(DispatchOutboundOfficeFinderImpl.class);
	@Override
	public Set<Integer> getAllOutboundOffices(Integer destOffice,
			BcunDatasyncService bcunService, List<CGBaseDO> baseList) {
		Set<Integer> destOfficeIds = new HashSet(baseList.size());
		try {
			for (CGBaseDO cgBaseDO :baseList){
				BcunLoadMovementDO dispatch= (BcunLoadMovementDO)cgBaseDO;
				destOfficeIds.add(dispatch.getDestOfficeId());
				destOfficeIds.add(destOffice);
			}
		} catch (Exception e) {
			LOGGER.error("DispatchOutboundOfficeFinderImpl :: getAllOutboundOffices",e);
		}
		
		return destOfficeIds;
	}
}
