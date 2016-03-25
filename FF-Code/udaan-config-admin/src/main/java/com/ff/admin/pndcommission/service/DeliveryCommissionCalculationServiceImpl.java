package com.ff.admin.pndcommission.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.ff.admin.pndcommission.dao.DeliveryCommissionCalculationDAO;
import com.ff.domain.pndcommission.DeliveryCommissionCalculationDO;
import com.ff.domain.pndcommission.SAPDeliveryCommissionCalcDO;

/**
 * @author hkansagr
 * 
 */
public class DeliveryCommissionCalculationServiceImpl implements
		DeliveryCommissionCalculationService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DeliveryCommissionCalculationServiceImpl.class);

	/** The deliveryCommissionCalcDAO. */
	private DeliveryCommissionCalculationDAO deliveryCommissionCalcDAO;

	/**
	 * @param deliveryCommissionCalcDAO
	 *            the deliveryCommissionCalcDAO to set
	 */
	public void setDeliveryCommissionCalcDAO(
			DeliveryCommissionCalculationDAO deliveryCommissionCalcDAO) {
		this.deliveryCommissionCalcDAO = deliveryCommissionCalcDAO;
	}

	@Override
	public void generateDlvCommission() throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("DeliveryCommissionCalculationServiceImpl :: generateDlvCommission() :: START ");
		deliveryCommissionCalcDAO.generateDlvCommission();
		LOGGER.trace("DeliveryCommissionCalculationServiceImpl :: generateDlvCommission() :: END ");
	}

	@Override
	public void dataCopyToSAPStagingTable() throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("DeliveryCommissionCalculationServiceImpl :: dataCopyToSAPStagingTable() :: START ");
		// To Search Delivery Commission Details
		List<DeliveryCommissionCalculationDO> dlvCommCalcDOs = deliveryCommissionCalcDAO
				.getDlvCommCalcDtls();

		if (!CGCollectionUtils.isEmpty(dlvCommCalcDOs)) {
			// To Convert Accordingly To SAP Staging Table
			List<SAPDeliveryCommissionCalcDO> sapDlvCommCalcDOs = convertDlvCommCalcToSAPStaging(dlvCommCalcDOs);

			// To Save SAP Staging Table Details
			saveSAPDlvCommCalcDtls(sapDlvCommCalcDOs);
		}
		LOGGER.trace("DeliveryCommissionCalculationServiceImpl :: dataCopyToSAPStagingTable() :: END ");
	}

	/**
	 * To convert delivery commission calculation to SAP staging table
	 * 
	 * @param dlvCommCalcDOs
	 * @return sapDlvCommCalcList
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private List<SAPDeliveryCommissionCalcDO> convertDlvCommCalcToSAPStaging(
			List<DeliveryCommissionCalculationDO> dlvCommCalcDOs)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("DeliveryCommissionCalculationServiceImpl :: convertDlvCommCalcToSAPStaging() :: START ");
		List<SAPDeliveryCommissionCalcDO> sapDlvCommCalcList = new ArrayList<SAPDeliveryCommissionCalcDO>(
				dlvCommCalcDOs.size());

		for (DeliveryCommissionCalculationDO dlvCommCalcDO : dlvCommCalcDOs) {
			SAPDeliveryCommissionCalcDO sapDlvCommCalcDO = new SAPDeliveryCommissionCalcDO();
			try {
				// To set common properties.
				PropertyUtils.copyProperties(sapDlvCommCalcDO, dlvCommCalcDO);

				// To set employee code
				sapDlvCommCalcDO.setEmployeeCode(dlvCommCalcDO.getEmployeeDO()
						.getEmpCode());

				// To set transaction time stamp.
				sapDlvCommCalcDO.setTransactionCreateDate(DateUtil
						.getCurrentDate());

				// To set sap status - N - default.
				sapDlvCommCalcDO.setSapStatus(CommonConstants.NO);

				sapDlvCommCalcList.add(sapDlvCommCalcDO);
			} catch (Exception e) {
				LOGGER.error(
						"Exception occurs in DeliveryCommissionCalculationServiceImpl :: convertDlvCommCalcToSAPStaging() :: EXCEPTION :: ",
						e);
			}
		}
		LOGGER.trace("DeliveryCommissionCalculationServiceImpl :: convertDlvCommCalcToSAPStaging() :: END ");
		return sapDlvCommCalcList;
	}

	/**
	 * To save SAP delivery commission calculation details
	 * 
	 * @param sapDlvCommCalcDOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private void saveSAPDlvCommCalcDtls(
			List<SAPDeliveryCommissionCalcDO> sapDlvCommCalcDOs)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("DeliveryCommissionCalculationServiceImpl :: saveSAPDlvCommCalcDtls() :: START ");
		for (SAPDeliveryCommissionCalcDO sapDlvCommCalcDO : sapDlvCommCalcDOs) {
			try {
				deliveryCommissionCalcDAO
						.saveSAPDlvCommCalcDtls(sapDlvCommCalcDO);
			} catch (Exception e) {
				LOGGER.error(
						"Exception occurs in DeliveryCommissionCalculationServiceImpl :: saveSAPDlvCommCalcDtls() :: EXCEPTION :: ",
						e);
			}
		}
		LOGGER.trace("DeliveryCommissionCalculationServiceImpl :: saveSAPDlvCommCalcDtls() :: END ");
	}

}
