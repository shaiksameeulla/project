package com.ff.web.pickup.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.pickup.PickupRunsheetHeaderDO;

/**
 * The Interface UpdateRunsheetDAO.
 */
public interface UpdateRunsheetDAO {
	public List<PickupRunsheetHeaderDO> getPickupRunsheetDetails(
			String runsheetNo) throws CGSystemException;

	public boolean updatePickupRunsheet(
			PickupRunsheetHeaderDO pickupRunsheetHeaderDO)
			throws CGSystemException;
}
