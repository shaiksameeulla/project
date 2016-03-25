package com.cg.lbs.bcun.service.outbound.officefinder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.cg.lbs.bcun.service.BcunDatasyncService;

/**
 * @author hkansagr
 */
public class ExpenseMasterOutboundOfficeFinder implements
		OutboundOfficeFinderService {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ExpenseMasterOutboundOfficeFinder.class);

	@SuppressWarnings("unchecked")
	@Override
	public Set<Integer> getAllOutboundOffices(Integer fromOffice,
			BcunDatasyncService bcunService, List<CGBaseDO> baseList) {
		LOGGER.trace("ExpenseMasterOutboundOfficeFinder :: getAllOutboundOffices() :: START");
		Set<Integer> allActOffSet = null;
		List<Integer> allActOffList = (List<Integer>) bcunService
				.getNumberByNamedQuery("getAllActiveDatasyncOfficeIDList");
		if (!CGCollectionUtils.isEmpty(allActOffList)) {
			allActOffSet = new HashSet<Integer>();
			allActOffSet.addAll(allActOffList);
		}
		LOGGER.trace("ExpenseMasterOutboundOfficeFinder :: getAllOutboundOffices() :: END");
		return allActOffSet;
	}

}
