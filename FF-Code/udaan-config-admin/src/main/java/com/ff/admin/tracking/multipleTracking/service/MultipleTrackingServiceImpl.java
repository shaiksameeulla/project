package com.ff.admin.tracking.multipleTracking.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.admin.tracking.common.service.TrackingCommonService;
import com.ff.tracking.TrackingBulkImportTO;

public class MultipleTrackingServiceImpl implements MultipleTrackingService {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(MultipleTrackingServiceImpl.class);

	private TrackingCommonService trackingCommonService;
	
	/**
	 * @param trackingCommonService the trackingCommonService to set
	 */
	public void setTrackingCommonService(
			TrackingCommonService trackingCommonService) {
		this.trackingCommonService = trackingCommonService;
	}

	@Override
	public List<TrackingBulkImportTO> getMultipleConsgDetails(
			TrackingBulkImportTO bulkTO)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("MultipleTrackingServiceImpl::getMultipleConsgDetails()::START");
		List<TrackingBulkImportTO> trackingBulkImportTOs = new ArrayList<>();
		
		String consgNo = StringUtil.trimAllWhitespace(bulkTO.getConsgNumber());
		List<String> consgNoSet = StringUtil.parseStringList(consgNo, CommonConstants.COMMA);
		
		if (consgNoSet.size() <= 100) {
			bulkTO = trackingCommonService.isValidConsgNum(consgNoSet, bulkTO);
			if (!StringUtil.isEmptyColletion(bulkTO.getValidConsg())) {
				trackingBulkImportTOs = trackingCommonService
						.getBulkTrackDetails(bulkTO);
				if(StringUtil.isEmptyList(trackingBulkImportTOs)){
					throw new CGBusinessException(AdminErrorConstants.TRACKING_DETAILS_NOT_FOUND);
				}
			} else {
				//Since no valid numbers found clearing the text area 
				//bulkTO.setConsgNumber("");
				//trackingBulkImportTOs.add(bulkTO);
				throw new CGBusinessException(AdminSpringConstants.INVALID_NUMBER_TYPE);
			}
		} else {
			throw new CGBusinessException(AdminErrorConstants.CONSIGNMENT_EXCEED_LIMIT);				
		}
		LOGGER.trace("MultipleTrackingServiceImpl::getMultipleConsgDetails()::END");
		return trackingBulkImportTOs;
	}
}
