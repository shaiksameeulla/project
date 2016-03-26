package com.ff.sap.integration.plantmaster.bs;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.organization.CSDSAPEmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.umc.UserDO;
import com.ff.sap.integration.dao.SAPIntegrationDAO;
import com.ff.sap.integration.to.SAPErrorTO;

/**
 * @author hkansagr
 * 
 */
public class PlantMasterTransactionServiceImpl implements
		PlantMasterTransactionService {

	/** The LOGGER. */
	Logger LOGGER = Logger.getLogger(PlantMasterTransactionServiceImpl.class);

	/** The integrationDAO. */
	private SAPIntegrationDAO integrationDAO;

	/**
	 * @param integrationDAO
	 *            the integrationDAO to set
	 */
	public void setIntegrationDAO(SAPIntegrationDAO integrationDAO) {
		this.integrationDAO = integrationDAO;
	}

	@Override
	public SAPErrorTO saveOfficeAndEmpDtls(OfficeDO officeDO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("OFFCIE :: PlantMasterTransactionServiceImpl :: saveOfficeAndEmpDtls() :: START ");
		SAPErrorTO sapErrorTO = null;
		try {
			sapErrorTO = integrationDAO.saveOrUpdateOfficeDetail(officeDO);
			if (StringUtil.isNull(sapErrorTO)) {
				CSDSAPEmployeeDO sapEmp = (CSDSAPEmployeeDO) convertEmpDOFromOffDO(officeDO);
				sapErrorTO = integrationDAO.saveDetails(sapEmp);
			}
		} catch (Exception e) {
			LOGGER.error("OFFCIE :: Exception occurs in :: PlantMasterSAPIntegrationServiceImpl :: saveOfficeDetails() :: for office code :: "+ officeDO.getOfficeCode(), e);
		}
		LOGGER.debug("OFFCIE :: PlantMasterTransactionServiceImpl :: saveOfficeAndEmpDtls() :: END ");
		return sapErrorTO;
	}

	/**
	 * To convert officeDO to employeeDO
	 * 
	 * @param officeDO
	 * @return CSDSAPEmployeeDO
	 * @throws CGBusinessException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws CGSystemException
	 * @author hkansagr
	 */
	private CSDSAPEmployeeDO convertEmpDOFromOffDO(CGBaseDO officeDO)
			throws CGBusinessException, CGSystemException {

		LOGGER.debug("convertEmpDOFromOffDO---------------------> Start");
		CSDSAPEmployeeDO empDO = new CSDSAPEmployeeDO();
		OfficeDO offcDO = null;
		offcDO = (OfficeDO) officeDO;

		if (!StringUtil.isStringEmpty(offcDO.getOfficeCode())) {
			empDO.setEmpCode(offcDO.getOfficeCode());
		}
		LOGGER.debug("Emp Code -------->" + empDO.getEmpCode());

		if (!StringUtil.isStringEmpty(offcDO.getOfficeName())) {
			empDO.setFirstName(offcDO.getOfficeName());
		}
		LOGGER.debug("First Name -------->" + empDO.getFirstName());

		if (!StringUtil.isEmptyInteger(offcDO.getCityId())) {
			empDO.setCty(offcDO.getCityId());
		}
		LOGGER.debug("City ID -------->" + empDO.getCty());

		if (!StringUtil.isStringEmpty(offcDO.getEmail())) {
			empDO.setEmailId(offcDO.getEmail());
		}
		LOGGER.debug("Email -------->" + empDO.getEmailId());

		empDO.setEmpVirtual("Y");
		empDO.setDtToBranch("N");
		empDO.setEmpStatus("A");

		if (!StringUtil.isEmptyInteger(offcDO.getOfficeId())) {
			empDO.setOfficeId(offcDO.getOfficeId());
		}
		LOGGER.debug("Employee Office ID Punched--------------------------------------->"
				+ empDO.getOfficeId());

		empDO.setLastName(" ");

		String userName = "SAP_USER";
		UserDO userDO = integrationDAO.getSAPUserDtls(userName);
		if (!StringUtil.isNull(userDO)
				&& !StringUtil.isEmptyInteger(userDO.getUserId())) {
			empDO.setCreatedBy(userDO.getUserId());
			empDO.setUpdatedBy(userDO.getUserId());
			Date today = Calendar.getInstance().getTime();
			empDO.setCreatedDate(today);
			empDO.setUpdatedDate(today);
		}

		LOGGER.debug("Last Name -------->" + empDO.getFirstName());

		LOGGER.debug("convertEmpDOFromOffDO---------------------> End");
		return empDO;
	}

}
