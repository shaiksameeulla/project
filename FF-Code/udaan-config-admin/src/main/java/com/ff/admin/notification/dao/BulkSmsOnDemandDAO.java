package com.ff.admin.notification.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.notification.BulkSmsOnDemandTO;

public interface BulkSmsOnDemandDAO {

	public List<Object[]> getConsignmentDetailsByStatus(BulkSmsOnDemandTO smsOnDemandTO) throws CGSystemException;

}
