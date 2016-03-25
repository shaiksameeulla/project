package com.ff.admin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.admin.dao.PickUpCommissionCalculationDao;


public class PickUpCommissionCalculationServiceImpl implements PickUpCommissionCalculationService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PickUpCommissionCalculationServiceImpl.class);

	/** The stock common dao. */
	private PickUpCommissionCalculationDao pickUpCommissionDao;

	
	/**
	 * @return the pickUpCommissionDao
	 */
	public PickUpCommissionCalculationDao getPickUpCommissionDao() {
		return pickUpCommissionDao;
	}

	/**
	 * @param pickUpCommissionDao the pickUpCommissionDao to set
	 */
	public void setPickUpCommissionDao(
			PickUpCommissionCalculationDao pickUpCommissionDao) {
		this.pickUpCommissionDao = pickUpCommissionDao;
	}

	public void generatePickUpCount() throws CGSystemException{
		pickUpCommissionDao.generatePickUpCount();
	}
	
	
	
	
	
}
