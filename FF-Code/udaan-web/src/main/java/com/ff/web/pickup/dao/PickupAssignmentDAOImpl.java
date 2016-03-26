package com.ff.web.pickup.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.pickup.PickupAssignmentTypeDO;
import com.ff.domain.pickup.PickupDeliveryContractDO;
import com.ff.domain.pickup.PickupDeliveryLocationDO;
import com.ff.domain.pickup.ReversePickupOrderDetailDO;
import com.ff.domain.pickup.RunsheetAssignmentDetailDO;
import com.ff.domain.pickup.RunsheetAssignmentHeaderDO;

public class PickupAssignmentDAOImpl extends CGBaseDAO implements
		PickupAssignmentDAO {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(PickupAssignmentDAOImpl.class);

	public static final String GET_ASSIGNED_CUSTOMERS_PARAMS = "assignmentType,employeeOfficeId,loginUserOfficeId,employeeId";
	public static final String GET_UNASSIGNED_MASTER_CUSTOMERS_PARAMS = "assignmentType,employeeOfficeType,employeeOfficeId,loginUserOfficeType,loginUserOfficeId";
	public static final String GET_UNASSIGNED_TEMPORARY_CUSTOMERS_PARAMS = "assignmentType,employeeOfficeType,loginUserOfficeId,employeeId";

	/**
	 * @see com.ff.web.pickup.dao.PickupAssignmentDAO#getPickupRunsheetType()
	 *      Nov 11, 2012
	 * @return
	 * @throws CGSystemException
	 *             getPickupRunsheetType PickupAssignmentDAO kgajare
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PickupAssignmentTypeDO> getPickupRunsheetType()
			throws CGSystemException {
		LOGGER.debug("METHOD_ENTRY : [PickupAssignmentDAOImpl.getPickupRunsheetType]");

		List<PickupAssignmentTypeDO> pickupAssignmentTypeDOs = null;
		try {
			String queryName = "getPickupRunsheetType";
			pickupAssignmentTypeDOs = getHibernateTemplate().findByNamedQuery(
					queryName);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : PickupAssignmentDAOImpl.getPickupRunsheetType", e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("METHOD_EXIT : [PickupAssignmentDAOImpl.getPickupRunsheetType]");

		return pickupAssignmentTypeDOs;
	}

	/**
	 * @see com.ff.web.pickup.dao.PickupAssignmentDAO#getUnassignedMasterCustomerList(com.ff.pickup.RunsheetAssignmentTO)
	 *      Nov 12, 2012
	 * @param runsheetAssignmentInputTO
	 * @return
	 * @throws CGSystemException
	 *             getUnassignedMasterCustomerList PickupAssignmentDAO kgajare
	 */
	@SuppressWarnings("unchecked")
	// Start..Added By Narasimha for Pickup req#2 dev
	@Override
	public List<PickupDeliveryLocationDO> getUnassignedMasterCustomers(
			RunsheetAssignmentHeaderDO runsheetAssignmentHeaderDO)
			throws CGSystemException {
		LOGGER.debug("METHOD_ENTRY : [PickupAssignmentDAOImpl.getUnassignedMasterCustomers] (Master)");
		List<PickupDeliveryLocationDO> pickupDeliveryLocationDOs = null;
		try {
			String queryName = "getUnassignedMasterCustomersForBranchAssignmentAtHub";
			String[] params = { "assignmentType", "selectedOfficeId","loginUserOfficeId" };
			Object[] values = {
					runsheetAssignmentHeaderDO.getPickupAssignmentType().getAssignmentTypeId(),
					runsheetAssignmentHeaderDO.getCreatedForOfficeId(),
					runsheetAssignmentHeaderDO.getCreatedAtOfficeId() };
			pickupDeliveryLocationDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, params, values);

		} catch (Exception e) {
			LOGGER.error(
					"ERROR : PickupAssignmentDAOImpl.getUnassignedMasterCustomers",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("METHOD_EXIT : [PickupAssignmentDAOImpl.getUnassignedMasterCustomers] (Master)");
		return pickupDeliveryLocationDOs;
	}

	// End..Added By Narasimha for Pickup req#2 dev

	// For Master & Temporary assignment purpose
	@SuppressWarnings("unchecked")
	@Override
	public List<RunsheetAssignmentDetailDO> getAssignedMasterCustomers(
			RunsheetAssignmentHeaderDO runsheetAssignmentHeaderDO)
			throws CGSystemException {
		LOGGER.debug("METHOD_ENTRY : [PickupAssignmentDAOImpl.getAssignedMasterCustomers] (Master)");
		List<RunsheetAssignmentDetailDO> pickupAssignmentTypeDOs = null;
		String queryName = null;
		try {
			queryName = "getAssignedMasterCustomers";
			Object[] values = {
					runsheetAssignmentHeaderDO.getPickupAssignmentType()
							.getAssignmentTypeId(),
					runsheetAssignmentHeaderDO.getCreatedForOfficeId(),
					runsheetAssignmentHeaderDO.getCreatedAtOfficeId(),
					runsheetAssignmentHeaderDO.getEmployeeFieldStaffId() };
			pickupAssignmentTypeDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName,
							GET_ASSIGNED_CUSTOMERS_PARAMS.split(","), values);

		} catch (Exception e) {
			LOGGER.error(
					"ERROR : PickupAssignmentDAOImpl.getAssignedMasterCustomers",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("METHOD_EXIT : [PickupAssignmentDAOImpl.getAssignedMasterCustomers] (Master)");
		return pickupAssignmentTypeDOs;
	}

	/**
	 * @see com.ff.web.pickup.dao.PickupAssignmentDAO#getReversePickupCustomers(com.ff.domain.pickup.RunsheetAssignmentHeaderDO)
	 *      Nov 16, 2012
	 * @param runsheetAssignmentHeaderDO
	 * @return
	 * @throws CGSystemException
	 *             getReversePickupCustomers PickupAssignmentDAO kgajare
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ReversePickupOrderDetailDO> getReversePickupCustomers(
			RunsheetAssignmentHeaderDO runsheetAssignmentHeaderDO)
			throws CGSystemException {
		LOGGER.debug("METHOD_ENTRY : [PickupAssignmentDAOImpl.getReversePickupCustomers] (Temporary)");
		List<ReversePickupOrderDetailDO> reversePickupOrderDetailDOs = null;
		String queryName = null;
		try {
			queryName = "getUnassignedTemporaryCustomers";
			String[] params = { "assignmentType", "employeeOfficeId",
					"employeeId" };
			Object[] values = {

					runsheetAssignmentHeaderDO.getPickupAssignmentType()
							.getAssignmentTypeId(),
					runsheetAssignmentHeaderDO.getCreatedForOfficeId(),
					runsheetAssignmentHeaderDO.getEmployeeFieldStaffId() };

			reversePickupOrderDetailDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, params, values);

		} catch (Exception e) {
			LOGGER.error(
					"ERROR : PickupAssignmentDAOImpl.getReversePickupCustomers",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("METHOD_EXIT : [PickupAssignmentDAOImpl.getReversePickupCustomers] (Temporary)");
		return reversePickupOrderDetailDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	// For Temporary assignment purpose
	public List<RunsheetAssignmentDetailDO> getAssignedTemporaryCustomers(
			RunsheetAssignmentHeaderDO runsheetAssignmentHeaderDO)
			throws CGSystemException {
		LOGGER.debug("METHOD_ENTRY : [PickupAssignmentDAOImpl.getAssignedTemporaryCustomers] (Temporary)");
		List<RunsheetAssignmentDetailDO> runsheetAssignmentDetailDOs = null;
		String queryName = null;
		try {
			queryName = "getAssignedTemporaryCustomers";
			String[] params = { "assignmentType", "loginUserOfficeId",
					"employeeOfficeId", "employeeId" };
			Object[] values = {
					runsheetAssignmentHeaderDO.getPickupAssignmentType()
							.getAssignmentTypeId(),
					runsheetAssignmentHeaderDO.getCreatedAtOfficeId(),
					runsheetAssignmentHeaderDO.getCreatedForOfficeId(),
					runsheetAssignmentHeaderDO.getEmployeeFieldStaffId() };
			runsheetAssignmentDetailDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, params, values);

		} catch (Exception e) {
			LOGGER.error(
					"ERROR : PickupAssignmentDAOImpl.getAssignedTemporaryCustomers",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("METHOD_EXIT : [PickupAssignmentDAOImpl.getAssignedTemporaryCustomers] (Temporary)");
		return runsheetAssignmentDetailDOs;
	}

	/**
	 * @see com.ff.web.pickup.dao.PickupAssignmentDAO#getUnassignedStandardCustomersForTemporary(com.ff.domain.pickup.RunsheetAssignmentHeaderDO)
	 *      Nov 28, 2012
	 * @param runsheetAssignmentHeaderDO
	 * @return
	 * @throws CGSystemException
	 *             getUnassignedStandardCustomersForTemporary
	 *             PickupAssignmentDAO kgajare
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PickupDeliveryLocationDO> getUnassignedStandardCustomersForTemporary(
			RunsheetAssignmentHeaderDO runsheetAssignmentHeaderDO)
			throws CGSystemException {
		LOGGER.debug("METHOD_ENTRY : [PickupAssignmentDAOImpl.getUnassignedStandardCustomersForTemporary] (Temporary)");
		List<PickupDeliveryLocationDO> pickupDeliveryContractDOs = null;
		String queryName = null;
		try {
			queryName = "getUnassignedStandardCustomersForTemporary";
			String[] params = { "assignmentType", "loginUserOfficeId",
					"employeeOfficeId", "employeeId" };
			Object[] values = {
					runsheetAssignmentHeaderDO.getPickupAssignmentType()
							.getAssignmentTypeId(),
					runsheetAssignmentHeaderDO.getCreatedAtOfficeId(),
					runsheetAssignmentHeaderDO.getCreatedForOfficeId(),
					runsheetAssignmentHeaderDO.getEmployeeFieldStaffId() };
			pickupDeliveryContractDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, params, values);

		} catch (Exception e) {
			LOGGER.error(
					"ERROR : PickupAssignmentDAOImpl.getUnassignedStandardCustomersForTemporary",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("METHOD_EXIT : [PickupAssignmentDAOImpl.getUnassignedStandardCustomersForTemporary] (Temporary)");
		return pickupDeliveryContractDOs;
	}
	
	// Start..Added By Narasimha for Pickup req#2 dev
	@SuppressWarnings("unchecked")
	@Override
	public List<PickupDeliveryLocationDO> getUnassignedStandardCustomersForTemporaryForHub(
			RunsheetAssignmentHeaderDO runsheetAssignmentHeaderDO)
			throws CGSystemException {
		LOGGER.debug("METHOD_ENTRY : [PickupAssignmentDAOImpl.getUnassignedStandardCustomersForTemporaryForHub] (Temporary)");
		List<PickupDeliveryLocationDO> pickupDeliveryLocationDOs = null;
		String queryName = null;
		try {
			queryName = "getUnassignedStandardCustomersForTemporaryHub";
			String[] params = { "selectedOfficeId"};
			Object[] values = {runsheetAssignmentHeaderDO.getCreatedForOfficeId()};
			pickupDeliveryLocationDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, params, values);

		} catch (Exception e) {
			LOGGER.error(
					"ERROR : PickupAssignmentDAOImpl.getUnassignedStandardCustomersForTemporaryForHub",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("METHOD_EXIT : [PickupAssignmentDAOImpl.getUnassignedStandardCustomersForTemporaryForHub] (Temporary)");
		return pickupDeliveryLocationDOs;
	}

	// End..Added By Narasimha for Pickup req#2 dev

	@SuppressWarnings("unchecked")
	@Override
	public List<ReversePickupOrderDetailDO> getUnassignedTemporaryCustomersHub(
			RunsheetAssignmentHeaderDO runsheetAssignmentHeaderDO)
			throws CGSystemException {
		LOGGER.debug("METHOD_ENTRY : [PickupAssignmentDAOImpl.getUnassignedTemporaryCustomersHub] (Temporary)");
		List<ReversePickupOrderDetailDO> reversePickupOrderDetailDOs = null;
		String queryName = null;
		try {
			queryName = "getUnassignedTemporaryCustomersHub";
			String[] params = { "selectedOfficeId", "assignmentType",
					"employeeOfficeId", "employeeId" };
			Object[] values = {
					runsheetAssignmentHeaderDO.getCreatedForOfficeId(),
					runsheetAssignmentHeaderDO.getPickupAssignmentType()
							.getAssignmentTypeId(),
					runsheetAssignmentHeaderDO.getCreatedForOfficeId(),
					runsheetAssignmentHeaderDO.getEmployeeFieldStaffId() };

			reversePickupOrderDetailDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, params, values);

		} catch (Exception e) {
			LOGGER.error(
					"ERROR : PickupAssignmentDAOImpl.getUnassignedTemporaryCustomersHub",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("METHOD_EXIT : [PickupAssignmentDAOImpl.getUnassignedTemporaryCustomersHub] (Temporary)");
		return reversePickupOrderDetailDOs;
	}

	/**
	 * @see com.ff.web.pickup.dao.PickupAssignmentDAO#savePickupAssignment(com.ff.domain.pickup.RunsheetAssignmentHeaderDO)
	 *      Nov 9, 2012
	 * @param runsheetAssignmentHeaderDO
	 * @return
	 * @throws CGSystemException
	 *             savePickupAssignment PickupAssignmentDAOImpl kgajare
	 */
	@Override
	public Boolean savePickupAssignment(
			RunsheetAssignmentHeaderDO runsheetAssignmentHeaderDO)
			throws CGSystemException {
		LOGGER.debug("METHOD_ENTRY : [PickupAssignmentDAOImpl.savePickupAssignment]");
		Session session = null;
		Boolean success = null;
		try {
			session = openTransactionalSession();
			session.merge(runsheetAssignmentHeaderDO);
			success = true;
		} catch (Exception e) {
			success = false;
			LOGGER.error(
					"ERROR : PickupAssignmentDAOImpl.savePickupAssignment", e);
			throw new CGSystemException(e);
		} finally {
			closeTransactionalSession(session);
		}

		LOGGER.debug("METHOD_EXIT : [PickupAssignmentDAOImpl.savePickupAssignment]");
		return success;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReversePickupOrderDetailDO> getAllReverseCustomers(
			Integer officeId) throws CGSystemException {
		LOGGER.debug("METHOD_ENTRY : [PickupAssignmentDAOImpl.getAllReverseCustomers]");
		List<ReversePickupOrderDetailDO> reversePickupOrderDetailDOs = null;
		try {
			reversePickupOrderDetailDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam("getAllReverseCustomers",
							"employeeOfficeId", officeId);
		} catch (Exception e) {
			LOGGER.error("Error occured in PickupAssignmentDAOImpl :: getAllReverseCustomers() ::"
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("METHOD_EXIT : [PickupAssignmentDAOImpl.getAllReverseCustomers]");
		return reversePickupOrderDetailDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PickupDeliveryContractDO getBranchDeliveryContractByCustomer(
			Integer officeId, Integer customerId) throws CGSystemException {
		LOGGER.trace("BPLOutManifestDoxAction :: getBranchDeliveryContractByCustomer() :: Start --------> ::::::");
		PickupDeliveryContractDO delContract = null;
		String hql = "getOfficeDeliverContracByCustomer";
		String[] params = { "officeId", "customerId" };
		Object[] values = { officeId, customerId };
		try {
			List<PickupDeliveryContractDO> contractList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(hql, params, values);
			if (contractList != null && !contractList.isEmpty()) {
				delContract = contractList.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("Error occured in PickupAssignmentDAOImpl :: getBranchDeliveryContractByCustomer() ::"
					+ e);
			throw new CGSystemException(e);
		}

		LOGGER.trace("PickupAssignmentDAOImpl :: getBranchDeliveryContractByCustomer() :: End --------> ::::::");
		return delContract;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PickupDeliveryContractDO getPickupContracByCustomerAndOffice(
			Integer officeId, Integer customerId) throws CGSystemException {
		LOGGER.trace("BPLOutManifestDoxAction :: getPickupContracByCustomerAndOffice() :: Start --------> ::::::");
		PickupDeliveryContractDO delContract = null;
		String hql = "getPickupContracByCustomerAndOffice";
		String[] params = { "officeId", "customerId" };
		Object[] values = { officeId, customerId };
		try {
			List<PickupDeliveryContractDO> contractList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(hql, params, values);
			if (contractList != null && !contractList.isEmpty()) {
				delContract = contractList.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("Error occured in PickupAssignmentDAOImpl :: getPickupContracByCustomerAndOffice() ::"
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("PickupAssignmentDAOImpl :: getPickupContracByCustomerAndOffice() :: End --------> ::::::");
		return delContract;
	}

	@Override
	public Boolean savePickupMasterAssignment(
			RunsheetAssignmentHeaderDO runsheetAssignmentHeaderDO)
			throws CGSystemException {
		LOGGER.debug("METHOD_ENTRY : [PickupAssignmentDAOImpl.savePickupMasterAssignment]");
		Session session = null;
		// Transaction tx = null;
		Boolean success = null;
		RunsheetAssignmentDetailDO runsheetAssignmentDetailDO = null;
		try {
			session = openTransactionalSession();
			// tx = session.beginTransaction();

			// saving for the first time
			if (runsheetAssignmentHeaderDO.getAssignmentHeaderId() == null
					|| runsheetAssignmentHeaderDO.getAssignmentHeaderId() == 0) {
				runsheetAssignmentHeaderDO.setAssignmentHeaderId(null);
				session.save(runsheetAssignmentHeaderDO);
			} else { // for the subsequent save operations
				session.update(runsheetAssignmentHeaderDO);
				for (Iterator<RunsheetAssignmentDetailDO> iterator = runsheetAssignmentHeaderDO
						.getAssignmentDetails().iterator(); iterator.hasNext();) {
					runsheetAssignmentDetailDO = iterator.next();
					if (runsheetAssignmentDetailDO.getAssignmentDetailId() == null
							|| runsheetAssignmentDetailDO
									.getAssignmentDetailId() == 0) {
						runsheetAssignmentDetailDO.setAssignmentDetailId(null);
						session.save(runsheetAssignmentDetailDO);
					} else {
						session.update(runsheetAssignmentDetailDO);
					}
				}
			}

			// tx.commit();
			success = true;
		} catch (Exception e) {
			success = false;
			LOGGER.error(
					"ERROR : PickupAssignmentDAOImpl.savePickupMasterAssignment", e);
			// tx.rollback();

			throw new CGSystemException(e);
		} finally {
			closeTransactionalSession(session);
		}

		LOGGER.debug("METHOD_EXIT : [PickupAssignmentDAOImpl.savePickupMasterAssignment]");
		return success;
	}

	// Start..Added By Narasimha for Pickup req#2 dev
	@SuppressWarnings("unchecked")
	@Override
	public List<PickupDeliveryLocationDO> getUnassignedMasterCustomersForHub(
			RunsheetAssignmentHeaderDO runsheetAssignmentHeaderDO)
			throws CGSystemException {
		LOGGER.debug("METHOD_ENTRY : [PickupAssignmentDAOImpl.getUnassignedMasterCustomersForHub] (Master)");
		List<PickupDeliveryLocationDO> pickupDeliveryLocationDOs = null;
		String queryName = null;
		try {
			queryName = "getUnassignedMasterCustomersForHub";
			String[] params = { "selectedOfficeId"};
			Object[] values = {runsheetAssignmentHeaderDO.getCreatedForOfficeId()};
			pickupDeliveryLocationDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, params, values);

		} catch (Exception e) {
			LOGGER.error(
					"ERROR : PickupAssignmentDAOImpl.getUnassignedMasterCustomersForHub",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("METHOD_EXIT : [PickupAssignmentDAOImpl.getUnassignedMasterCustomersForHub] (Master)");
		return pickupDeliveryLocationDOs;
	}

	// End..Added By Narasimha for Pickup req#2 dev
}
