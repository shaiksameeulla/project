package com.ff.admin.dao;

import com.capgemini.lbs.framework.exception.CGSystemException;

public interface PickUpCommissionCalculationDao {

	public void generatePickUpCount() throws CGSystemException;
	
}
