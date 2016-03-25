package com.ff.admin.tracking.common.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.domain.tracking.BulkCnTrackDO;

public class TrackingCommonDAOImpl extends CGBaseDAO implements
		TrackingCommonDAO {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(TrackingCommonDAOImpl.class);

	@SuppressWarnings(CommonConstants.UNCHECKED)
	public List<BulkCnTrackDO> getDetailedCommonTracking(String consgNum, String type)
			throws CGSystemException {
		LOGGER.trace("TrackingCommonDAOImpl::getDetailedCommonTracking()::START");
		List<BulkCnTrackDO> processMapDOs = null;
		if (consgNum != null) {
			String[] params = {"trackType", AdminSpringConstants.CONSG_NO};
			String[] values = {type, consgNum};
			processMapDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam("getBulkCnTrackingDetails", params, values);
		}
		LOGGER.trace("TrackingCommonDAOImpl::getDetailedCommonTracking()::END");
		return processMapDOs;
	}
}
