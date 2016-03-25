/**
 * 
 */
package com.ff.admin.coloading.service;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.admin.coloading.dao.ColoadingRateCalculationDAO;

/**
 * @author isawarka
 * 
 */
public class ColoadingRateCalculationServiceImpl implements ColoadingRateCalculationService {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ColoadingRateCalculationServiceImpl.class);

	
	private transient ColoadingRateCalculationDAO coloadingRateCalculationDAO;
	
	@Override
	public void calculateAndSaveRateForAir() throws CGBusinessException,CGSystemException,
					HttpException, ClassNotFoundException, IOException{
		LOGGER.debug("ColoadingRateCalculationServiceImpl::calculateRateForAir::START");
		coloadingRateCalculationDAO.calculateAndSaveRateForAir();
		LOGGER.debug("ColoadingRateCalculationServiceImpl::calculateRateForAir::End");
	}
	@Override
	public void calculateAndSaveRateForTrain() throws CGBusinessException,CGSystemException,
					HttpException, ClassNotFoundException, IOException{
		LOGGER.debug("ColoadingRateCalculationServiceImpl::calculateRateForTrain::START");
		coloadingRateCalculationDAO.calculateAndSaveRateForTrain();
		LOGGER.debug("ColoadingRateCalculationServiceImpl::calculateRateForTrain::End");
	}
	
	@Override
	public void calculateAndSaveRateForSurface() throws CGBusinessException,
			CGSystemException,HttpException, ClassNotFoundException, IOException {
		LOGGER.debug("ColoadingRateCalculationServiceImpl::calculateAndSaveRateForSurface::START");
		coloadingRateCalculationDAO.calculateAndSaveRateForSurface();
		LOGGER.debug("ColoadingRateCalculationServiceImpl::calculateAndSaveRateForSurface::End");
		
	}
	
	@Override
	public void calculateAndSaveRateForVehicle() throws CGBusinessException,CGSystemException,HttpException, ClassNotFoundException, IOException{
		LOGGER.debug("ColoadingRateCalculationServiceImpl::calculateRateForVehicle::START");
		coloadingRateCalculationDAO.calculateAndSaveRateForVehicle();
		LOGGER.debug("ColoadingRateCalculationServiceImpl::calculateRateForVehicle::End");
	}
	
	@Override
	public void calculateAndSaveRateForVehicleMonthly()
			throws CGBusinessException, CGSystemException ,HttpException, ClassNotFoundException, IOException{
		LOGGER.debug("ColoadingRateCalculationServiceImpl::calculateAndSaveRateForVehicleMonthly::START");
		coloadingRateCalculationDAO.calculateAndSaveRateForVehicleMonthly();
		LOGGER.debug("ColoadingRateCalculationServiceImpl::calculateAndSaveRateForVehicleMonthly::End");
		
	}
	
	
	public ColoadingRateCalculationDAO getColoadingRateCalculationDAO() {
		return coloadingRateCalculationDAO;
	}
	public void setColoadingRateCalculationDAO(
			ColoadingRateCalculationDAO coloadingRateCalculationDAO) {
		this.coloadingRateCalculationDAO = coloadingRateCalculationDAO;
	}
	
}
