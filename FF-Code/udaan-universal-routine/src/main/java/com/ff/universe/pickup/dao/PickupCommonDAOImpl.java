package com.ff.universe.pickup.dao;

/**
 * 
 */

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.pickup.CSDSAPPickupDeliveryLocationDO;
import com.ff.domain.pickup.PickupAssignmentTypeDO;
import com.ff.domain.pickup.PickupDeliveryLocationDO;
import com.ff.domain.pickup.PickupRunsheetHeaderDO;
import com.ff.domain.pickup.RunsheetAssignmentDetailDO;
import com.ff.domain.pickup.RunsheetAssignmentHeaderDO;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.pickup.constant.UniversalPickupConstant;

/**
 * @author kgajare
 * 
 */
public class PickupCommonDAOImpl extends CGBaseDAO implements PickupCommonDAO {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(PickupCommonDAOImpl.class);

	/**
	 * @see com.ff.web.pickup.dao.PickupCommonDAO#getPickupRunsheetType() Nov
	 *      10, 2012
	 * @return
	 * @throws CGSystemException
	 *             getPickupRunsheetType PickupCommonDAO kgajare
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PickupAssignmentTypeDO> getPickupRunsheetType()
			throws CGSystemException {
		LOGGER.debug("METHOD_ENTRY : [PickupCommonDAOImpl.getPickupRunsheetType]");
		String queryName = "getPickupRunsheetType";
		List<PickupAssignmentTypeDO> pickupAssignmentTypeDOs = null;
		try {
			pickupAssignmentTypeDOs = getHibernateTemplate().findByNamedQuery(
					queryName);

		} catch (Exception e) {
			LOGGER.error("ERROR : PickupCommonDAOImpl::getPickupRunsheetType", e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("METHOD_EXIT : [PickupCommonDAOImpl.getPickupRunsheetType]");
		return pickupAssignmentTypeDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getPickupAssignmentType(Integer assignmentTypeId)
			throws CGSystemException {
		LOGGER.debug("METHOD_ENTRY : [PickupCommonDAOImpl.getPickupAssignmentType]");
		List<Object> assignmentTypeDesc = null;
		try {
			assignmentTypeDesc = getHibernateTemplate()
					.findByNamedQueryAndNamedParam("getAssignmentTypeById",
							"assignmentTypeId", assignmentTypeId);
		} catch (Exception e) {
			LOGGER.error("ERROR : PickupCommonDAOImpl::getPickupAssignmentType", e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("METHOD_EXIT : [PickupCommonDAOImpl.getPickupAssignmentType]");
		return assignmentTypeDesc;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RunsheetAssignmentHeaderDO getStatusOfMasterPickupAssignment(
			Integer assignmentHeaderId, Date currentDate)
			throws CGSystemException {
		LOGGER.debug("METHOD_ENTRY : [PickupCommonDAOImpl.getStatusOfMasterPickupAssignment]");
		RunsheetAssignmentHeaderDO assignmentHeaderDO = null;
		try {
			String[] params = { UdaanCommonConstants.ASSIGNMENT_HEADER_ID};
			Object[] values = { assignmentHeaderId};
			List<RunsheetAssignmentHeaderDO> assignmentList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							UdaanCommonConstants.GET_STATUS_MASTER_PICKUP_ASSIGNMENT,
							params, values);
			if (assignmentList != null && !assignmentList.isEmpty()) {
				assignmentHeaderDO = assignmentList.get(0);
			}
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : PickupCommonDAOImpl::getStatusOfMasterPickupAssignment :: ", e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("METHOD_EXIT : [PickupCommonDAOImpl.getStatusOfMasterPickupAssignment]");
		return assignmentHeaderDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PickupDeliveryLocationDO getPickupDlvLocation(Integer customerId,
			Integer officeId) throws CGSystemException {
		LOGGER.debug("METHOD_ENTRY : [PickupCommonDAOImpl.getPickupDlvLocation]");
		PickupDeliveryLocationDO pickupDlvLoc = null;
		try {
			String[] params = { "customerId", "officeId" };
			Object[] values = { customerId, officeId };
			List<PickupDeliveryLocationDO> pickDlvLocList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam("getPickupDlvLocation",
							params, values);
			pickupDlvLoc = !StringUtil.isEmptyList(pickDlvLocList) ? pickDlvLocList
					.get(0) : null;
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : PickupCommonDAOImpl::getPickupDlvLocation :: ", e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("METHOD_EXIT : [PickupCommonDAOImpl.getPickupDlvLocation]");
		return pickupDlvLoc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.pickup.dao.PickupCommonDAO#getPickupRunsheetDetails(java
	 * .util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PickupRunsheetHeaderDO> getPickupRunsheetDetails(
			List<Integer> runsheetHeaderIds) throws CGSystemException {
		LOGGER.debug("METHOD_ENTRY : [PickupCommonDAOImpl.getPickupRunsheetDetails]");
		List<PickupRunsheetHeaderDO> pickupRunsheetHeaderList = null;
		try {
			HibernateTemplate hibernateTemplate = getHibernateTemplate();
			pickupRunsheetHeaderList = hibernateTemplate
					.findByNamedQueryAndNamedParam(
							UniversalPickupConstant.QRY_GET_PICKUP_RUNSHEET_DETAILS,
							UniversalPickupConstant.RUNSHEET_HEADER_ID_LIST,
							runsheetHeaderIds);
		} catch (Exception e) {
			LOGGER.error("ERROR::PickupCommonDAOImpl - getPickupRunsheetDetails() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("METHOD_EXIT : [PickupCommonDAOImpl.getPickupRunsheetDetails]");
		return pickupRunsheetHeaderList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.pickup.dao.PickupCommonDAO#
	 * getMasterRunsheetAssignmentDetailsByEmpId(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RunsheetAssignmentDetailDO> getMasterRunsheetAssignmentDetailsByEmpId(
			Integer employeeId) throws CGSystemException {
		LOGGER.debug("PickupCommonDAOImpl :: getMasterRunsheetAssignmentDetailsByEmpId() :: Start --------> ::::::");
		List<RunsheetAssignmentDetailDO> runsheetAssignmentDetailDOs = null;
		try {
			runsheetAssignmentDetailDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							UniversalPickupConstant.QRY_GET_MASTER_RUNSHEET_ASSIGNMENT_DETAILS_BY_EMP_ID,
							UniversalPickupConstant.PARAM_EMPLOYEE_ID,
							employeeId);
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::PickupCommonDAOImpl::getMasterRunsheetAssignmentDetailsByEmpId() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("PickupCommonDAOImpl :: getMasterRunsheetAssignmentDetailsByEmpId() :: End --------> ::::::");
		return runsheetAssignmentDetailDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RunsheetAssignmentHeaderDO> getRunsheetAssignmentHeader(
			Integer assignmentHeaderId) throws CGSystemException {
		LOGGER.debug("PickupCommonDAOImpl :: getRunsheetAssignmentHeader() :: Start --------> ::::::");
		List<RunsheetAssignmentHeaderDO> runsheetAssignmentHeaderDOs = null;
		try {
			runsheetAssignmentHeaderDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							UniversalPickupConstant.QRY_GET_RUNSHEET_ASSIGNMENT_HEADER,
							UniversalPickupConstant.ASSIGNMENT_HEADER_ID,
							assignmentHeaderId);
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::PickupCommonDAOImpl::getRunsheetAssignmentHeader() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("PickupCommonDAOImpl :: getRunsheetAssignmentHeader() :: End --------> ::::::");
		return runsheetAssignmentHeaderDOs;
	}
	@Override
	public boolean savePickupDeliveryLocation(List<CSDSAPPickupDeliveryLocationDO> pickupDeliveryLocationDOs)
			throws CGSystemException {
		logger.debug("PickupCommonDAOImpl :: savePickupDeliveryLocation() :: START");
		Boolean isSaved = Boolean.FALSE;
			for (CSDSAPPickupDeliveryLocationDO pickupDeliveryLocationDO : pickupDeliveryLocationDOs) {
				try{
					getHibernateTemplate().saveOrUpdate(pickupDeliveryLocationDO);
					isSaved = Boolean.TRUE;
				}catch(Exception ex){
					logger.error("Exception In :: SAPIntegrationDAOImpl :: updateEmployeeDetails :: Error",ex);
					logger.error("Could not insert :: "+pickupDeliveryLocationDO.getPickupDlvLocCode());
				}
			}
		logger.debug("PickupCommonDAOImpl :: savePickupDeliveryLocation() :: END");
		return isSaved;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CSDSAPPickupDeliveryLocationDO getPickupDlvLocForSAPCustContract(
			Integer customerId, Integer officeId) throws CGSystemException {
		LOGGER.debug("PickupCommonDAOImpl :: getPickupDlvLocForSAPCustContract() :: Start --------> ::::::");
		List<CSDSAPPickupDeliveryLocationDO> pickupClvLocDOs = null;
		try {
			String[] params = { "customerId", "officeId" };
			Object[] values = { customerId, officeId };
			pickupClvLocDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							UniversalPickupConstant.QRY_GET_PICKUP_DLV_LOC_FOR_SAP_CUST_CONTRACT,
							params, values);
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::PickupCommonDAOImpl::getPickupDlvLocForSAPCustContract() :: ",e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("PickupCommonDAOImpl :: getPickupDlvLocForSAPCustContract() :: End --------> ::::::");
		return (!CGCollectionUtils.isEmpty(pickupClvLocDOs)) ? pickupClvLocDOs
				.get(0) : null;
	}
	
}
