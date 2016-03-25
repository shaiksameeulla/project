package com.ff.admin.tracking.common.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.tracking.BulkCnTrackDO;

public interface TrackingCommonDAO {
	public List<BulkCnTrackDO> getDetailedCommonTracking(String consgNum, String type)throws CGSystemException;
}
