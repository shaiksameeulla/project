package com.ff.admin.pndcommission.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.pndcommission.DeliveryCommissionCalculationDO;
import com.ff.domain.pndcommission.SAPDeliveryCommissionCalcDO;
import com.ff.universe.constant.UdaanCommonConstants;

/**
 * @author hkansagr
 * 
 */
public class DeliveryCommissionCalculationDAOImpl extends CGBaseDAO implements
		DeliveryCommissionCalculationDAO {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DeliveryCommissionCalculationDAOImpl.class);

	@Override
	public void generateDlvCommission() throws CGSystemException {
		LOGGER.trace("DeliveryCommissionCalculationDAOImpl :: generateDlvCommission() :: START ");
		Session session = null;
		try {
			session = createSession();
			Query qry = session
					.getNamedQuery(UdaanCommonConstants.SP_GEN_DLV_COMMISSION_CALC);
			qry.list();
		} catch (Exception e) {
			LOGGER.error(
					"DeliveryCommissionCalculationDAOImpl :: generateDlvCommission() :: EXCEPTION ::",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("DeliveryCommissionCalculationDAOImpl :: generateDlvCommission() :: END ");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryCommissionCalculationDO> getDlvCommCalcDtls()
			throws CGSystemException {
		LOGGER.trace("DeliveryCommissionCalculationDAOImpl :: getDlvCommCalcDtls() :: START ");
		List<DeliveryCommissionCalculationDO> dlvCommCalcDOs = null;
		try {
			dlvCommCalcDOs = getHibernateTemplate().findByNamedQuery(
					UdaanCommonConstants.QRY_GET_DLV_COMM_CALC_DTLS);
		} catch (Exception e) {
			LOGGER.error(
					"DeliveryCommissionCalculationDAOImpl :: getDlvCommCalcDtls() :: EXCEPTION ::",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("DeliveryCommissionCalculationDAOImpl :: getDlvCommCalcDtls() :: END ");
		return dlvCommCalcDOs;
	}

	@Override
	public void saveSAPDlvCommCalcDtls(
			SAPDeliveryCommissionCalcDO sapDlvCommCalcDO)
			throws CGSystemException {
		LOGGER.trace("DeliveryCommissionCalculationDAOImpl :: saveSAPDlvCommCalcDtls() :: START ");
		try {
			getHibernateTemplate().save(sapDlvCommCalcDO);
		} catch (Exception e) {
			LOGGER.error(
					"DeliveryCommissionCalculationDAOImpl :: saveSAPDlvCommCalcDtls() :: EXCEPTION ::",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("DeliveryCommissionCalculationDAOImpl :: saveSAPDlvCommCalcDtls() :: END ");
	}

}
