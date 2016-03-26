package com.ff.web.loadmanagement.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.utility.TwoWayWriteProcessCall;
import com.ff.loadmanagement.LoadManagementTO;
import com.ff.universe.loadmanagement.service.LoadManagementCommonService;
import com.ff.web.loadmanagement.dao.LoadManagementDAO;

/**
 * The Class LoadReceiveLocalServiceImpl.
 *
 * @author narmdr
 */
public class LoadManagementServiceImpl implements LoadManagementService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(LoadManagementServiceImpl.class);
	
	/** The load management common service. */
	private LoadManagementCommonService loadManagementCommonService;
	
	/** The load management dao. */
	private LoadManagementDAO loadManagementDAO;

	/**
	 * @param loadManagementCommonService the loadManagementCommonService to set
	 */
	public void setLoadManagementCommonService(
			LoadManagementCommonService loadManagementCommonService) {
		this.loadManagementCommonService = loadManagementCommonService;
	}

	/**
	 * @param loadManagementDAO the loadManagementDAO to set
	 */
	public void setLoadManagementDAO(LoadManagementDAO loadManagementDAO) {
		this.loadManagementDAO = loadManagementDAO;
	}

	@Override
	public void twoWayWrite(LoadManagementTO loadManagementTO)
			throws CGBusinessException {
		if (loadManagementTO != null
				&& !StringUtil.isEmptyInteger(loadManagementTO
						.getLoadMovementId())) {
			LOGGER.debug("LoadManagementServiceImpl::twoWayWrite::Calling TwoWayWrite service to save same in central------------>:::::::");
			TwoWayWriteProcessCall.twoWayWriteProcess(
					loadManagementTO.getLoadMovementId(),
					CommonConstants.TWO_WAY_WRITE_PROCESS_DISPATCH_RECEIVE);
		}
	}	
}
