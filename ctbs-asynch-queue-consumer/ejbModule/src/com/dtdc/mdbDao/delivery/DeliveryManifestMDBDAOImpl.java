/**
 * 
 */
package src.com.dtdc.mdbDao.delivery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import src.com.dtdc.constants.CommonConstants;
import src.com.dtdc.constants.DeliveryManifestConstants;
import src.com.dtdc.constants.ManifestConstant;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseDO.CGBaseEntity;
import com.dtdc.domain.booking.BookingLogDO;
import com.dtdc.domain.expense.MiscExpenseDO;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.manifest.RtnToOrgDO;
import com.dtdc.domain.master.HandHeldDeviceDO;
import com.dtdc.domain.master.employee.EmployeeDO;
import com.dtdc.domain.master.franchisee.FranchiseePincodeMappingDO;
import com.dtdc.domain.master.geography.AreaDO;
import com.dtdc.domain.master.geography.PincodeDO;
import com.dtdc.domain.transaction.delivery.DeliveryDO;
import com.dtdc.domain.transaction.delivery.DeliveryManifestBookingDO;
import com.dtdc.domain.transaction.delivery.DeliveryManifestFranchiseeDO;
import com.dtdc.domain.transaction.delivery.DeliveryManifestOfficeDO;
import com.dtdc.domain.transaction.delivery.FranchiseDeliveryManifestDO;
import com.dtdc.domain.transaction.delivery.FranchiseDeliveryManifestHandOverDO;

// TODO: Auto-generated Javadoc
/**
 * The Class DeliveryManifestMDBDAOImpl.
 *
 * @author rchalich
 */
public class DeliveryManifestMDBDAOImpl extends HibernateDaoSupport implements
DeliveryManifestMDBDAO {

	/** logger. */
	private Logger logger = LoggerFactory
	.getLogger(DeliveryManifestMDBDAOImpl.class);

	/*
	 * Inserting Branch Delivery Manifest Into DB
	 * 
	 * @Param: List<DeliveryDO> deiveryManifestList Return: drsNum
	 */
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#saveOrUpdateBrDeliveryManifest(List)
	 */
	public String saveOrUpdateBrDeliveryManifest(
			List<DeliveryDO> deiveryManifestList) throws CGBusinessException {
		String bdmDetails = "";
		String consgNumber = "";
		StringBuilder failureCN = null;
		try {
			HibernateTemplate hibernateTemplate = getHibernateTemplate();
			for (DeliveryDO brDeliveryBean : deiveryManifestList) {
				try {
					consgNumber = brDeliveryBean.getConNum();
					if (brDeliveryBean.getDeliveryId() != null
							&& brDeliveryBean.getDeliveryId() > 0) {
						hibernateTemplate.saveOrUpdate(brDeliveryBean);
					} else {
						hibernateTemplate.save(brDeliveryBean);
					}
					hibernateTemplate.flush();
					logger.debug("*********saveOrUpdateBrDeliveryManifest*****:"
							+ consgNumber + " saved sucessfully!");
				} catch (Exception obj) {
					failureCN = new StringBuilder();
					failureCN.append(brDeliveryBean.getConNum());
					failureCN.append(",");
					logger.debug("*********saveOrUpdateBrDeliveryManifest*****:"
							+ consgNumber + " not saved sucessfully!");
					logger.error("DeliveryManifestMDBDAOImpl::saveOrUpdateBrDeliveryManifest occured:"
							+obj.getMessage());
					logger.error("Exception occured in  BDM:"
							+ obj.getCause().getMessage());
				}
			}
			if (failureCN != null) {
				bdmDetails = failureCN.toString();
			}
		} catch (Exception obj) {
			logger.error("DeliveryManifestMDBDAOImpl::saveOrUpdateBrDeliveryManifest occured:"
					+obj.getMessage());
			logger.error("Exception occured in  saveOrUpdateFrDeliveryManifest:"
					+ obj.getCause().getMessage());
			throw new CGBusinessException(obj);
		}
		return bdmDetails;
	}

	/*
	 * Inserting Branch Delivery Manifest Into DB
	 * 
	 * @Param: List<FranchiseDeliveryManifestDO> frDeiveryManifestList Return:
	 * fdmNumber
	 */
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#saveOrUpdateFrDeliveryManifest(List)
	 */
	@SuppressWarnings("unchecked")
	public String saveOrUpdateFrDeliveryManifest(
			List<FranchiseDeliveryManifestDO> frDeiveryManifestList)
	throws CGBusinessException {
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		String failureCNNos = null;
		String consgNumber = "";
		StringBuilder failureCN = null;
		try {
			if (frDeiveryManifestList != null) {
				if (frDeiveryManifestList.size() > 0) {
					for (FranchiseDeliveryManifestDO frDeliveryBean : frDeiveryManifestList) {
						try {
							consgNumber = frDeliveryBean.getConNum();
							if (frDeliveryBean.getDeliveryId() != null
									&& frDeliveryBean.getDeliveryId() > 0) {
								hibernateTemplate.saveOrUpdate(frDeliveryBean);
							} else {
								hibernateTemplate.save(frDeliveryBean);
							}
							hibernateTemplate.flush();
							logger.debug("*********saveFrDeliveryManifestDBSync*****:"
									+ consgNumber + " saved sucessfully!");
						} catch (Exception obj) {
							failureCN = new StringBuilder();
							failureCN.append(frDeliveryBean.getConNum());
							failureCN.append(",");
							logger.debug("*********saveOrUpdateFrDeliveryManifest*****:"
									+ consgNumber + " not saved sucessfully!");
							logger.error("DeliveryManifestMDBDAOImpl::saveOrUpdateBrDeliveryManifest occured:"
									+obj.getMessage());
							logger.error("Exception occured in  saveOrUpdateFrDeliveryManifest:"
									+ obj.getCause().getMessage());
						}
					}
				}
			}
			if (failureCN != null) {
				failureCNNos = failureCN.toString();
			}
		} catch (Exception obj) {
			logger.error("DeliveryManifestMDBDAOImpl::saveOrUpdateFrDeliveryManifest occured:"
					+obj.getMessage());
			logger.error("Exception occured in  saveOrUpdateFrDeliveryManifest:"
					+ obj.getCause().getMessage());
			throw new CGBusinessException(obj);
		}
		return failureCNNos;

	}

	/*
	 * Getting Branch Delivery Manifest
	 * 
	 * @Param: drsNumber Return: List<DeliveryDO> deliveryManifestList
	 */
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#getBrDeliveryManifestDetails(String)
	 */
	@SuppressWarnings("unchecked")
	public List<DeliveryDO> getBrDeliveryManifestDetails(String drsNumber)
	throws CGBusinessException {
		List<DeliveryDO> deliveryManifestList = new ArrayList<DeliveryDO>();
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			deliveryManifestList = session
			.createCriteria(DeliveryDO.class)
			.add(Restrictions.eq(
					DeliveryManifestConstants.RUN_SHEET_NUMBER,
					drsNumber))
					.add(Restrictions.in(
							DeliveryManifestConstants.CONSG_DELIVERY_STATUS,
							DeliveryManifestConstants.CONSGN_STATUS_BDM_FIND))
							.add(Restrictions.ne(
									DeliveryManifestConstants.CONSG_STATUS,
									DeliveryManifestConstants.DELITED_STATUS)).list();

		} catch (Exception e) {
			logger.error("DeliveryManifestMDBDAOImpl::getBrDeliveryManifestDetails occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		} finally {
			session.flush();
			session.close();
		}
		return deliveryManifestList;
	}

	/*
	 * Getting Branch Delivery Manifest
	 * 
	 * @Param: drsNumber Return: List<DeliveryDO> deliveryManifestList
	 */
	/**
	 * Gets the manifest by consg.
	 *
	 * @param consgNum the consg num
	 * @param deliveryCode the delivery code
	 * @return the manifest by consg
	 * @throws CGBusinessException the cG business exception
	 */
	@SuppressWarnings("unchecked")
	public int getManifestByConsg(String consgNum, String deliveryCode)
	throws CGBusinessException {
		List<DeliveryDO> deliveryManifestList = new ArrayList<DeliveryDO>();
		Session session = null;
		Criteria criteria = null;
		int count = 0;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			criteria = session.createCriteria(DeliveryDO.class);
			criteria.add(Restrictions.eq(
					DeliveryManifestConstants.CONSG_NUMBER, consgNum));
			if (StringUtils.equals(deliveryCode,
					DeliveryManifestConstants.BDM_DELIVERY_CODE)) {
				criteria.add(Restrictions.eq(
						DeliveryManifestConstants.CONSG_DELIVERY_STATUS,
						DeliveryManifestConstants.CONSGN_STATUS_BDM));
			} else if (StringUtils.equals(deliveryCode,
					DeliveryManifestConstants.FDM_DELIVERY_CODE)) {
				criteria.add(Restrictions.eq(
						DeliveryManifestConstants.CONSG_DELIVERY_STATUS,
						DeliveryManifestConstants.CONSGN_STATUS_FDM));
			}
			if (criteria != null) {
				deliveryManifestList = criteria.list();
			}
			if (deliveryManifestList != null && !deliveryManifestList.isEmpty()) {
				count = deliveryManifestList.size();
			}
		} catch (Exception e) {
			logger.error("DeliveryManifestMDBDAOImpl::getManifestByConsg occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		} finally {
			session.flush();
			session.close();
		}
		return count;
	}

	/*
	 * Getting Franchise Delivery Manifest
	 * 
	 * @Param: fdmNum Return: List<FranchiseDeliveryManifestDO>
	 * ftDeliveryManifestList
	 */
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#getFrDeliveryManifestDetails(String)
	 */
	@SuppressWarnings("unchecked")
	public List<FranchiseDeliveryManifestDO> getFrDeliveryManifestDetails(
			String fdmNum) throws CGSystemException {
		List<FranchiseDeliveryManifestDO> ftDeliveryManifestList = new ArrayList<FranchiseDeliveryManifestDO>();
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			String[] status = { DeliveryManifestConstants.CONSGN_STATUS_FDM,
					DeliveryManifestConstants.CONSGN_STATUS_BDM };
			ftDeliveryManifestList = session
			.createCriteria(FranchiseDeliveryManifestDO.class)
			.add(Restrictions.in(
					DeliveryManifestConstants.CONSG_DELIVERY_STATUS,
					status))
					.add(Restrictions.eq(DeliveryManifestConstants.FDM_NUMBER,
							fdmNum))
							.add(Restrictions.ne(
									DeliveryManifestConstants.CONSG_STATUS,
									DeliveryManifestConstants.DELITED_STATUS)).list();
		} catch (Exception e) {
			logger.error("DeliveryManifestMDBDAOImpl::getFrDeliveryManifestDetails occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		} finally {
			session.flush();
			session.close();
		}
		return ftDeliveryManifestList;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#getDuplicateConsignment(String, String)
	 */
	public long getDuplicateConsignment(String consgNum, String status)
	throws CGBusinessException {
		long count = 0;
		try {
			String[] params = { "conNum", "dlvStatus" };
			Object[] values = { consgNum, status };
			count = (Long) getHibernateTemplate()
			.findByNamedQueryAndNamedParam(
					DeliveryManifestConstants.GET_DUPLICATE_CONSG_NUM,
					params, values).get(0);
		} catch (Exception e) {
			logger.error("DeliveryManifestMDBDAOImpl::getDuplicateConsignment occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		}

		return count;
	}

	/*
	 * Getting Employee By Branch ID
	 * 
	 * @Param: integer branchId Return: DeliveryManifestOfficeDO
	 */
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#getEmployeesByBranchCode(int)
	 */
	@SuppressWarnings("unchecked")
	public DeliveryManifestOfficeDO getEmployeesByBranchCode(int branchId)
	throws CGBusinessException {

		DeliveryManifestOfficeDO officeDO = new DeliveryManifestOfficeDO();
		List<DeliveryManifestOfficeDO> officeDOList = new ArrayList<DeliveryManifestOfficeDO>();
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			officeDOList = (List<DeliveryManifestOfficeDO>) session
			.createCriteria(DeliveryManifestOfficeDO.class)
			.add(Restrictions.eq(DeliveryManifestConstants.OFFICE_ID,
					branchId))
					.add(Restrictions.eq(DeliveryManifestConstants.OFFICE_TYPE,
							CommonConstants.BO)).list();
			if (officeDOList != null) {
				if (officeDOList.size() > 0) {
					officeDO = officeDOList.get(0);
				}
			}
		} catch (Exception obj) {
			logger.error("DeliveryManifestMDBDAOImpl::getEmployeesByBranchCode occured:"
					+obj.getMessage());
			throw new CGBusinessException(obj);
		} finally {
			session.flush();
			session.close();
		}
		return officeDO;
	}

	/*
	 * Getting Employee By Branch Code
	 * 
	 * @Param: integer branchId Return: DeliveryManifestOfficeDO
	 */
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#getEmployeesByBranchCode(String)
	 */
	@SuppressWarnings("unchecked")
	public DeliveryManifestOfficeDO getEmployeesByBranchCode(String branchCode)
	throws CGBusinessException {
		DeliveryManifestOfficeDO officeDO = new DeliveryManifestOfficeDO();
		List<DeliveryManifestOfficeDO> officeDOList = new ArrayList<DeliveryManifestOfficeDO>();
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			officeDOList = (List<DeliveryManifestOfficeDO>) session
			.createCriteria(DeliveryManifestOfficeDO.class)
			.add(Restrictions.eq(DeliveryManifestConstants.OFFICE_CODE,
					branchCode)).list();
			if (officeDOList != null) {
				if (officeDOList.size() > 0) {
					officeDO = officeDOList.get(0);
				}
			}
		} catch (Exception obj) {
			logger.error("DeliveryManifestMDBDAOImpl::getEmployeesByBranchCode occured:"
					+obj.getMessage());
			throw new CGBusinessException(obj);
		} finally {
			session.flush();
			session.close();
		}
		return officeDO;
	}

	/*
	 * Getting Employee By Branch Code and Employee Code
	 * 
	 * @Param: integer branchId Return: Employee
	 */
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#getEmployee(String, String)
	 */
	public String getEmployee(String empCode, String branchCode)
	throws CGBusinessException {
		StringBuilder employee = new StringBuilder();
		List empDetailsList = null;
		try {
			empDetailsList = new ArrayList();
			String[] params = { DeliveryManifestConstants.EMP_CODE,
					DeliveryManifestConstants.OFFICE_CODE };
			Object[] values = { empCode, branchCode };
			empDetailsList = getHibernateTemplate()
			.findByNamedQueryAndNamedParam(
					DeliveryManifestConstants.GET_EMPLOYEE, params,
					values);
			if (empDetailsList.size() > 0) {
				for (int i = 0; i < empDetailsList.size(); i++) {
					Object[] obj = (Object[]) empDetailsList.get(i);
					employee.append(obj[0]);
					employee.append(CommonConstants.COMMA);
					employee.append(obj[1]);
					employee.append(CommonConstants.COMMA);
					employee.append(obj[2]);
				}
			}
		} catch (Exception e) {
			logger.error("DeliveryManifestMDBDAOImpl::getEmployee occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		}
		return employee.toString();
	}

	/*
	 * Getting Employee By empID
	 * 
	 * @Param: integer empID Return: Employee
	 */
	/**
	 * Gets the employee.
	 *
	 * @param empID the emp id
	 * @return the employee
	 * @throws CGBusinessException the cG business exception
	 */
	public EmployeeDO getEmployee(int empID) throws CGBusinessException {
		List empDetailsList = null;
		EmployeeDO employee = new EmployeeDO();
		try {
			empDetailsList = new ArrayList();
			empDetailsList = getHibernateTemplate()
			.findByNamedQueryAndNamedParam(
					DeliveryManifestConstants.GET_EMPLOYEE_BY_ID,
					DeliveryManifestConstants.EMP_ID, empID);
			if (empDetailsList.size() > 0) {
				employee = (EmployeeDO) empDetailsList.get(0);
			}
		} catch (Exception e) {
			logger.error("DeliveryManifestMDBDAOImpl::getEmployee occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		}
		return employee;
	}

	/*
	 * Getting getFranchiseeAndReportingBranch By Franchise Code
	 * 
	 * @Param: String frCode Return: DeliveryManifestFranchiseeDO
	 */
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#getFranchiseeAndReportingBranch(String)
	 */
	@SuppressWarnings("unchecked")
	public DeliveryManifestFranchiseeDO getFranchiseeAndReportingBranch(
			String frCode) throws CGBusinessException {
		List<DeliveryManifestFranchiseeDO> frDetails = new ArrayList<DeliveryManifestFranchiseeDO>();
		DeliveryManifestFranchiseeDO franchisee = null;
		try {
			frDetails = getHibernateTemplate()
			.findByNamedQueryAndNamedParam(
					DeliveryManifestConstants.GET_FRANCHISEE_AND_REPORTING_BRANCH,
					DeliveryManifestConstants.FR_CODE, frCode);
			if (frDetails.size() > 0) {
				franchisee = frDetails.get(0);
			}
		} catch (Exception e) {
			logger.error("DeliveryManifestMDBDAOImpl::getFranchiseeAndReportingBranch occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		}
		return franchisee;
	}

	/*
	 * Getting getFranchiseeAndReportingBranch By Franchise ID
	 * 
	 * @Param: String frCode Return: DeliveryManifestFranchiseeDO
	 */
	/**
	 * Gets the franchisee and reporting branch.
	 *
	 * @param frID the fr id
	 * @return the franchisee and reporting branch
	 * @throws CGBusinessException the cG business exception
	 */
	@SuppressWarnings("unchecked")
	public DeliveryManifestFranchiseeDO getFranchiseeAndReportingBranch(int frID)
	throws CGBusinessException {
		List<DeliveryManifestFranchiseeDO> frDetails = new ArrayList<DeliveryManifestFranchiseeDO>();
		DeliveryManifestFranchiseeDO franchisee = null;
		try {
			frDetails = getHibernateTemplate()
			.findByNamedQueryAndNamedParam(
					DeliveryManifestConstants.GET_FRANCHISEE_AND_REPORTING_BRANCH_BY_ID,
					DeliveryManifestConstants.FR_ID, frID);
			if (frDetails.size() > 0) {
				franchisee = frDetails.get(0);
			}
		} catch (Exception e) {
			logger.error("DeliveryManifestMDBDAOImpl::getFranchiseeAndReportingBranch occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		}
		return franchisee;
	}

	/*
	 * Getting getBookingDetails By cnNum
	 * 
	 * @Param: String cnNum Return: cnNum
	 */

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#getBookingDetails(String)
	 */
	@SuppressWarnings("unchecked")
	public DeliveryManifestBookingDO getBookingDetails(String cnNum)
	throws CGBusinessException {
		DeliveryManifestBookingDO booking = null;
		Session session = null;
		// For Held up
		Object[] values = { DeliveryManifestConstants.BOOKING_STATUS, "H" };
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();

			booking = (DeliveryManifestBookingDO) session
			.createCriteria(DeliveryManifestBookingDO.class)
			.add(Restrictions.eq(
					DeliveryManifestConstants.CONSIGNMENT_NUM, cnNum))
					.add(Restrictions.in(
							DeliveryManifestConstants.CONSG_STATUS, values))
							.uniqueResult();
		} catch (Exception e) {
			logger.error("DeliveryManifestMDBDAOImpl::getBookingDetails occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return booking;
	}

	/*
	 * @SuppressWarnings("unchecked") public DeliveryManifestBookingDO
	 * getBookingDetails(String cnNum) throws CGBusinessException {
	 * List<DeliveryManifestBookingDO> bookingList = new
	 * ArrayList<DeliveryManifestBookingDO>(); DeliveryManifestBookingDO booking
	 * = null; try { String[] params = { DeliveryManifestConstants.CONSGNUM,
	 * DeliveryManifestConstants.CONSG_STATUS }; Object[] values = { cnNum,
	 * DeliveryManifestConstants.BOOKING_STATUS }; bookingList =
	 * getHibernateTemplate().findByNamedQueryAndNamedParam(
	 * DeliveryManifestConstants.GET_BOOKING_BY_CONSG_NUM, params, values); if
	 * (bookingList != null && !(bookingList.isEmpty())) { booking =
	 * bookingList.get(0); } } catch (Exception e) { throw new
	 * CGBusinessException(e); } return booking; }
	 */

	/*
	 * Getting Franchise Manifest By FDM Number
	 * 
	 * @Param: String fdmNum Return: List<FranchiseDeliveryManifestDO>
	 * frDeliveryManifestDetails
	 */
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#findByFDMNumber(String)
	 */
	@Override
	public List<FranchiseDeliveryManifestDO> findByFDMNumber(String fdmNum)
	throws CGSystemException, CGBusinessException {
		List<FranchiseDeliveryManifestDO> frDeliveryManifestDetails = new ArrayList<FranchiseDeliveryManifestDO>();
		try {
			frDeliveryManifestDetails = getFrDeliveryManifestDetails(fdmNum);
		} catch (Exception obj) {
			logger.error("DeliveryManifestMDBDAOImpl::findByFDMNumber occured:"
					+obj.getMessage());
			throw new CGBusinessException(obj);
		}
		return frDeliveryManifestDetails;
	}

	/*
	 * Getting getEmployeesByBranchID
	 * 
	 * @Param: int branchID Return: List<EmployeeDO> employees
	 */
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#getEmployeesByBranchID(int)
	 */
	@SuppressWarnings("unchecked")
	public List<EmployeeDO> getEmployeesByBranchID(int branchID)
	throws CGBusinessException {
		List<EmployeeDO> employees = new ArrayList<EmployeeDO>();
		try {
			employees = getHibernateTemplate().findByNamedQueryAndNamedParam(
					DeliveryManifestConstants.GET_EMPLOYEE_BY_BRANCH_ID,
					DeliveryManifestConstants.OFFICE_ID, branchID);
		} catch (Exception obj) {
			logger.error("DeliveryManifestMDBDAOImpl::getEmployeesByBranchID occured:"
					+obj.getMessage());
			throw new CGBusinessException(obj);
		}
		return employees;
	}

	/*
	 * Getting fdmToBdmConversion
	 * 
	 * @Param: List<Integer> deliveryIDs,List<DeliveryDO>
	 * brDeliveryList,List<DeliveryManifestHistoryDO> logDeliveryManifestList
	 * Return: String bdmNumber
	 */
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#fdmToBdmConversion(List, List, Double, int)
	 */
	@Override
	public String fdmToBdmConversion(List<Integer> deliveryIDs,
			List<DeliveryDO> brDeliveryList, Double totalWeight, int totalPieces)
	throws CGBusinessException {
		StringBuilder bdmDetails = new StringBuilder();
		String bdmNumber = null;
		String fdmNum = null;
		Double convertedWeight = 0.0;
		int convertedPieces = 0;
		Session session = null;
		try {
			HibernateTemplate hibernateTemplate = getHibernateTemplate();
			session = getHibernateTemplate().getSessionFactory().openSession();
			// Preparing BDM
			for (DeliveryDO brDeliveryBean : brDeliveryList) {
				if (StringUtils.isEmpty(bdmNumber)) {
					bdmNumber = brDeliveryBean.getRunSheetNum();
				}
				if (StringUtils.isEmpty(fdmNum)) {
					fdmNum = brDeliveryBean.getFdmNumber();
				}
				if (convertedWeight == 0) {
					convertedWeight = brDeliveryBean.getTotalWeight();
					totalWeight = totalWeight - convertedWeight;
				}
				if (convertedPieces == 0) {
					convertedPieces = brDeliveryBean.getTotalNumOFPieces();
					totalPieces = totalPieces - convertedPieces;
				}
				hibernateTemplate.saveOrUpdate(brDeliveryBean);
			}
			/*
			 * for (DeliveryManifestHistoryDO logDelivery :
			 * logDeliveryManifestList) { if (StringUtils.isEmpty(fdmNum)) {
			 * fdmNum = logDelivery.getFdmNumber(); }
			 * hibernateTemplate.saveOrUpdate(logDelivery);
			 * 
			 * }
			 */
			Query query = session.getNamedQuery("deleteFrDeliveryManifest");
			query.setString(DeliveryManifestConstants.STATUS,
					DeliveryManifestConstants.DELITED_STATUS);
			query.setParameterList(DeliveryManifestConstants.Delivery_IDS,
					deliveryIDs);
			query.executeUpdate();
			// updating weight and price for FDM
			Query query1 = session
			.getNamedQuery("updateWeightAndPiecesForFranchisee");
			query1.setDouble(DeliveryManifestConstants.TOTAL_WEIGHT,
					totalWeight);
			query1.setInteger(DeliveryManifestConstants.TOTAL_PIECES,
					totalPieces);
			query1.setString(DeliveryManifestConstants.FDM_NUM, fdmNum);
			int rowCount = query1.executeUpdate();
			// Deleting BDM
			if (rowCount > 0) {
				bdmDetails.append(DeliveryManifestConstants.SUCCESS);
				bdmDetails.append(CommonConstants.COMMA);
				bdmDetails.append(bdmNumber);
			}

		} catch (Exception obj) {
			logger.error("DeliveryManifestMDBDAOImpl::fdmToBdmConversion occured:"
					+obj.getMessage());
			throw new CGBusinessException(obj);
		} finally {
			session.flush();
			session.close();
		}
		return bdmDetails.toString();
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#getFrDeliveryManifestByIDs(List)
	 */
	@SuppressWarnings("unchecked")
	public List<FranchiseDeliveryManifestDO> getFrDeliveryManifestByIDs(
			List<Integer> deliveryIDList) throws CGBusinessException {
		List<FranchiseDeliveryManifestDO> frDeliveryManifestDetails = new ArrayList<FranchiseDeliveryManifestDO>();
		Session session = null;
		try {
			if (deliveryIDList != null) {
				if (deliveryIDList.size() > 0) {
					session = getHibernateTemplate().getSessionFactory()
					.openSession();
					frDeliveryManifestDetails = session
					.createCriteria(FranchiseDeliveryManifestDO.class)
					.add(Restrictions.in(
							DeliveryManifestConstants.Delivery_ID,
							deliveryIDList)).list();
				}
			}
		} catch (Exception obj) {
			logger.error("DeliveryManifestMDBDAOImpl::getFrDeliveryManifestByIDs occured:"
					+obj.getMessage());
			throw new CGBusinessException(obj);
		} finally {
			session.flush();
			session.close();
		}
		return frDeliveryManifestDetails;

	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#getBrDeliveryManifestByIDs(List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryDO> getBrDeliveryManifestByIDs(
			List<Integer> deliveryIDList) throws CGBusinessException {
		List<DeliveryDO> brDeliveryManifestDetails = new ArrayList<DeliveryDO>();
		Session session = null;
		try {
			if (deliveryIDList != null) {
				if (deliveryIDList.size() > 0) {
					session = getHibernateTemplate().getSessionFactory()
					.openSession();
					brDeliveryManifestDetails = session
					.createCriteria(DeliveryDO.class)
					.add(Restrictions.in(
							DeliveryManifestConstants.Delivery_ID,
							deliveryIDList)).list();
				}
			}
		} catch (Exception obj) {
			logger.error("DeliveryManifestMDBDAOImpl::getBrDeliveryManifestByIDs occured:"
					+obj.getMessage());
			throw new CGBusinessException(obj);
		} finally {
			session.flush();
			session.close();
		}
		return brDeliveryManifestDetails;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#bdmToFdmConversion(List, List, Double, int)
	 */
	@Override
	public String bdmToFdmConversion(List<Integer> deliveryIDs,
			List<FranchiseDeliveryManifestDO> frDeliveryList,
			Double totalWeight, int totalPieces) throws CGBusinessException {
		StringBuilder fdmDetails = new StringBuilder();
		String fdmNumber = null;
		String drsNum = null;
		Double convertedWeight = 0.0;
		int convertedPieces = 0;
		Session session = null;
		try {
			HibernateTemplate hibernateTemplate = getHibernateTemplate();
			session = getHibernateTemplate().getSessionFactory().openSession();
			// Inserting FDM
			for (FranchiseDeliveryManifestDO frDeliveryBean : frDeliveryList) {
				if (StringUtils.isEmpty(fdmNumber)) {
					fdmNumber = frDeliveryBean.getFdmNumber();
				}
				if (StringUtils.isEmpty(drsNum)) {
					drsNum = frDeliveryBean.getRunSheetNum();
				}
				if (convertedWeight == 0) {
					convertedWeight = frDeliveryBean.getTotalWeight();
					totalWeight = totalWeight - convertedWeight;
				}
				if (convertedPieces == 0) {
					convertedPieces = frDeliveryBean.getTotalNumOFPieces();
					totalPieces = totalPieces - convertedPieces;
				}
				hibernateTemplate.saveOrUpdate(frDeliveryBean);
			}
			// Deleting BDM
			Query query = session
			.getNamedQuery(DeliveryManifestConstants.DELETE_BDM);
			query.setString(DeliveryManifestConstants.STATUS,
					DeliveryManifestConstants.DELITED_STATUS);
			query.setParameterList(DeliveryManifestConstants.Delivery_IDS,
					deliveryIDs);
			query.executeUpdate();
			// updating weight and price for BDM
			Query query1 = session.getNamedQuery("updateWeightAndPieces");
			query1.setDouble(DeliveryManifestConstants.TOTAL_WEIGHT,
					totalWeight);
			query1.setInteger(DeliveryManifestConstants.TOTAL_PIECES,
					totalPieces);
			query1.setString(DeliveryManifestConstants.DRS_NUM, drsNum);
			int rowCount = query1.executeUpdate();
			if (rowCount > 0) {
				fdmDetails.append(DeliveryManifestConstants.SUCCESS);
				fdmDetails.append(CommonConstants.COMMA);
				fdmDetails.append(fdmNumber);
			}

		} catch (Exception obj) {
			logger.error("DeliveryManifestMDBDAOImpl::bdmToFdmConversion occured:"
					+obj.getMessage());
			throw new CGBusinessException(obj);
		} finally {
			session.flush();
			session.close();
		}
		return fdmDetails.toString();
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#saveFrDeliveryHOManifest(List)
	 */
	@Override
	public String saveFrDeliveryHOManifest(
			List<FranchiseDeliveryManifestHandOverDO> deiveryManifestList)
	throws CGBusinessException {
		String fdmHanfOverNum = "";
		StringBuilder fdmHODetails = new StringBuilder();
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			for (FranchiseDeliveryManifestHandOverDO brDeliveryBean : deiveryManifestList) {
				if (StringUtils.isEmpty(fdmHanfOverNum)) {
					fdmHanfOverNum = brDeliveryBean.getHandOverNum();
				}

				Query query = session
				.getNamedQuery(DeliveryManifestConstants.FDM_UPDATE);
				// query.setString(DeliveryManifestConstants.HANDOVER_TIME,
				// brDeliveryBean.getHandOverTime());
				/*
				 * query.setDate(DeliveryManifestConstants.HANDOVER_DATE,
				 * brDeliveryBean.getHandOverDate());
				 */
				query.setTimestamp(DeliveryManifestConstants.HANDOVER_DATE,
						brDeliveryBean.getHandOverDate());
				query.setString(DeliveryManifestConstants.CONSG_STATUS,
						brDeliveryBean.getConsgStatus());
				query.setString(DeliveryManifestConstants.DELV_STATUS,
						brDeliveryBean.getDeliveryStatus());
				query.setString(DeliveryManifestConstants.HANDOVER_NUMBER,
						brDeliveryBean.getHandOverNum());
				query.setString(DeliveryManifestConstants.FDM_NUM,
						brDeliveryBean.getFdmNumber());
				query.executeUpdate();
				/*
				 * hibernateTemplate.saveOrUpdate(brDeliveryBean);
				 * hibernateTemplate.flush(); if
				 * (StringUtils.isEmpty(fdmHanfOverNum)) { fdmHanfOverNum =
				 * brDeliveryBean.getHandOverNum(); }
				 */
			}
			fdmHODetails.append(DeliveryManifestConstants.SUCCESS);
			fdmHODetails.append(CommonConstants.COMMA);
			fdmHODetails.append(fdmHanfOverNum);
		} catch (Exception obj) {
			logger.error("DeliveryManifestMDBDAOImpl::saveFrDeliveryHOManifest occured:"
					+obj.getMessage());
			throw new CGBusinessException(
					"DeliveryManifestDAOImpl.saveFrDeliveryHOManifest(): Error occured. FDMHandover not saved.",
					obj);
		} finally {
			session.flush();
			session.close();
		}
		return fdmHODetails.toString();
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#isConsignmentManifested(String, String)
	 */
	@Override
	public boolean isConsignmentManifested(String consgNumber,
			String manifestStatus) throws CGSystemException {
		boolean isManifested = Boolean.FALSE;
		Session session = null;
		int count = 0;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			count = session
			.createCriteria(ManifestDO.class)
			.add(Restrictions.eq(
					DeliveryManifestConstants.CONSIGNMENT_NUMBER,
					consgNumber))
					.add(Restrictions.eq(DeliveryManifestConstants.STATUS,
							manifestStatus))
							.add(Restrictions.eq(
									DeliveryManifestConstants.MANIFEST_TYPE,
									ManifestConstant.MANIFEST_TYPE_AGAINST_INCOMING))
									.list().size();
			if (count > 0) {
				isManifested = Boolean.TRUE;
			}
		} catch (Exception ex) {
			logger.error("DeliveryManifestMDBDAOImpl::isConsignmentManifested occured:"
					+ex.getMessage());
			throw new CGSystemException("  " + ex.getMessage(), ex);
		} finally {
			session.flush();
			session.close();
		}
		return isManifested;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#isRTOConsignment(String, String)
	 */
	@Override
	public boolean isRTOConsignment(String consgNum, String rtoManiefstType)
	throws CGBusinessException {
		Session session = null;
		boolean isRTOConsignment = Boolean.FALSE;
		int count = 0;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			count = session
			.createCriteria(RtnToOrgDO.class)
			.add(Restrictions.eq(
					DeliveryManifestConstants.CONSIGNMENT_NUMBER,
					consgNum))
					.add(Restrictions.eq(
							DeliveryManifestConstants.RTO_MANIFEST_CATOGERY,
							rtoManiefstType)).list().size();
			if (count > 0) {
				isRTOConsignment = Boolean.TRUE;
			}
		} catch (Exception e) {
			logger.error("DeliveryManifestMDBDAOImpl::isRTOConsignment occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		} finally {
			session.flush();
			session.close();
		}
		return isRTOConsignment;
	}

	/*
	 * Validating Duplicate consignment number
	 * 
	 * @Param: String consgNum Return: Integer count
	 */
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#getAttemptNumber(String, String[])
	 */
	@SuppressWarnings("rawtypes")
	public int getAttemptNumber(String consgNum, String[] statuses)
	throws CGBusinessException {
		int attemptNumber = 0;
		List attemptNumbers = null;
		try {
			// Getting FDM number
			String[] params = { DeliveryManifestConstants.CONSGNUM,
					DeliveryManifestConstants.STATUS,
					DeliveryManifestConstants.CONSG_STATUS };
			Object[] values = { consgNum, statuses,
					DeliveryManifestConstants.DELITED_STATUS };
			attemptNumbers = getHibernateTemplate()
			.findByNamedQueryAndNamedParam(
					DeliveryManifestConstants.GET_ATTEMPT_NUMBER,
					params, values);
			/*
			 * attemptNumbers = getHibernateTemplate()
			 * .findByNamedQueryAndNamedParam(
			 * DeliveryManifestConstants.GET_ATTEMPT_NUMBER,
			 * DeliveryManifestConstants.CONSGNUM, consgNum);
			 */
			if (attemptNumbers != null && !attemptNumbers.isEmpty()) {
				attemptNumber = (Integer) attemptNumbers.get(0);
			}
		} catch (Exception e) {
			logger.error("DeliveryManifestMDBDAOImpl::getAttemptNumber occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		}
		return attemptNumber;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#getDeliveryByConsg(String, String)
	 */
	@SuppressWarnings("unchecked")
	public DeliveryDO getDeliveryByConsg(String consgNumber, String status)
	throws CGBusinessException {
		DeliveryDO delivery = null;
		Session session = null;
		List<DeliveryDO> deliveryList = null;
		try {
			deliveryList = new ArrayList<DeliveryDO>();
			session = getHibernateTemplate().getSessionFactory().openSession();
			deliveryList = (ArrayList<DeliveryDO>) session
			.createCriteria(DeliveryDO.class)
			.add(Restrictions
					.eq(DeliveryManifestConstants.CONSG_NUMBER,
							consgNumber))
							.add(Restrictions.eq(
									DeliveryManifestConstants.CONSG_DELIVERY_STATUS,
									status))
									.add(Restrictions.ne(
											DeliveryManifestConstants.CONSG_STATUS,
											DeliveryManifestConstants.DELITED_STATUS)).list();
			if (deliveryList != null && !deliveryList.isEmpty()) {
				delivery = deliveryList.get(0);
			}
		} catch (Exception e) {
			logger.error("DeliveryManifestMDBDAOImpl::getDeliveryByConsg occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		} finally {
			session.flush();
			session.close();
		}
		return delivery;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#getFrDliveryDayCount(Integer, Date)
	 */
	@Override
	public int getFrDliveryDayCount(Integer frId, Date fdmDate)
	throws CGBusinessException {
		int frDelCount = 0;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			frDelCount = session
			.createCriteria(DeliveryDO.class)
			.add(Restrictions.eq(
					DeliveryManifestConstants.FRANCHISEE_KEY, frId))
					.add(Restrictions
							.eq(DeliveryManifestConstants.PREPARATION_DATE,
									fdmDate))
									.add(Restrictions.ne(
											DeliveryManifestConstants.CONSG_STATUS,
											DeliveryManifestConstants.DELITED_STATUS)).list()
											.size();
		} catch (Exception e) {
			logger.error("DeliveryManifestMDBDAOImpl::getFrDliveryDayCount occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		} finally {
			session.flush();
			session.close();
		}
		return frDelCount;
	}

	/**
	 * Method to get FDM preparation notification email template.
	 *
	 * @param frDeliveryList the fr delivery list
	 * @param fdmNumber the fdm number
	 * @return messageBody
	 * @throws CGBusinessException the cG business exception
	 */
	public String fdmPreparationNotificationTemplate(
			List<FranchiseDeliveryManifestDO> frDeliveryList, String fdmNumber)
	throws CGBusinessException {
		String messageBody = null;
		StringBuffer messageBodyBuff = new StringBuffer();
		try {
			messageBodyBuff.append("<html><head></head><body>");
			messageBodyBuff.append("<table>");
			messageBodyBuff
			.append("<font style=\"font-family:'Courier New', Courier, monospace\" size=\"2\">Greetings,<br/><br/>");
			messageBodyBuff.append("<tr><td>");
			messageBodyBuff
			.append("FDM '"
					+ fdmNumber
					+ "' has been prepared. Find the below are the details:<br/><br/>");
			messageBodyBuff.append("</td></tr>");

			messageBodyBuff.append("<tr>");
			messageBodyBuff.append("<td>");
			messageBodyBuff.append("Consgignment Number");
			messageBodyBuff.append("</td>");
			messageBodyBuff.append("<td>");
			messageBodyBuff.append("FDM Date");
			messageBodyBuff.append("</td>");
			messageBodyBuff.append("<td>");
			messageBodyBuff.append("No. Of. Pieces");
			messageBodyBuff.append("</td>");
			messageBodyBuff.append("<td>");
			messageBodyBuff.append("Weight");
			messageBodyBuff.append("</td>");
			messageBodyBuff.append("</tr>");
			messageBodyBuff.append("<tr><td></br>");
			messageBodyBuff.append("</td></tr>");

			for (FranchiseDeliveryManifestDO frDelivery : frDeliveryList) {
				messageBodyBuff.append("<tr>");
				messageBodyBuff.append("<td>");
				messageBodyBuff.append(frDelivery.getConNum());
				messageBodyBuff.append("<td>");
				messageBodyBuff.append("<td>");
				messageBodyBuff.append(frDelivery.getPreparationDate());
				messageBodyBuff.append("<td>");
				messageBodyBuff.append(frDelivery.getConsgNumOFPieces());
				messageBodyBuff.append("</td>");
				messageBodyBuff.append("<td>");
				messageBodyBuff.append(frDelivery.getConsgWeight());
				messageBodyBuff.append("</td>");
				messageBodyBuff.append("</tr>");

			}
			messageBodyBuff.append("<tr><td>");
			messageBodyBuff.append("Regards,<br/>CTBS.<br/><br/>");
			messageBodyBuff.append("</td></tr>");
			messageBodyBuff.append("<tr><td>");
			messageBodyBuff
			.append("For any queries? Send email to info@dtdc.com");
			messageBodyBuff.append("</td></tr></font></table></body></html>");
			messageBody = messageBodyBuff.toString();
		} catch (Exception e) {
			logger.error("DeliveryManifestMDBDAOImpl::fdmPreparationNotificationTemplate occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		}
		return messageBody;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#updateBookedWeight(String, Double, Double, String)
	 */
	public void updateBookedWeight(String consgNumber, Double finalWeight,
			Double rateAmount, String updatedfProcessFrom)
	throws CGSystemException {
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
			.getNamedQuery(DeliveryManifestConstants.UPDATE_BOOKED_WEIGHT);
			query.setDouble(DeliveryManifestConstants.FINAL_WEIGHT, finalWeight);
			query.setDouble(DeliveryManifestConstants.RATE_AMOUNT, rateAmount);
			query.setString(DeliveryManifestConstants.UPDATE_PROCESS,
					updatedfProcessFrom);
			query.setString(DeliveryManifestConstants.CONSGNUM, consgNumber);
			query.executeUpdate();
		} catch (Exception ex) {
			logger.error("DeliveryManifestMDBDAOImpl::updateBookedWeight occured:"
					+ex.getMessage());
			throw new CGSystemException("  " + ex.getMessage(), ex);
		} finally {
			session.flush();
			session.close();
		}
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#updateDestIntoBooking(String, String, Double, String)
	 */
	public void updateDestIntoBooking(String pinCode, String conNumber,
			Double rateAmount, String updatedfProcessFrom)
	throws CGSystemException {
		Session session = null;
		int pinCodeId = 0;
		try {
			pinCodeId = getPinCodeIdByCode(pinCode);
			if (pinCodeId > 0) {
				session = getHibernateTemplate().getSessionFactory()
				.openSession();
				Query query = session
				.getNamedQuery(DeliveryManifestConstants.UPDATE_DESTINATION_INTO_BOOKING);
				query.setInteger(DeliveryManifestConstants.DEST_PINCODE,
						pinCodeId);
				query.setDouble(DeliveryManifestConstants.RATE_AMOUNT,
						rateAmount);
				query.setString(DeliveryManifestConstants.UPDATE_PROCESS,
						updatedfProcessFrom);
				query.setString(DeliveryManifestConstants.CONSGNUM, conNumber);
				query.executeUpdate();
			}
		} catch (Exception ex) {
			logger.error("DeliveryManifestMDBDAOImpl::updateDestIntoBooking occured:"
					+ex.getMessage());
			throw new CGSystemException("  " + ex.getMessage(), ex);
		} finally {
			session.flush();
			session.close();
		}
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#updateSAPIntgConsgStatus(String, String)
	 */
	@Override
	public void updateSAPIntgConsgStatus(String consgNumber,
			String billingStatus) throws CGSystemException {
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
			.getNamedQuery(DeliveryManifestConstants.UPDATE_BILLING_STATUS);
			query.setString(DeliveryManifestConstants.BILLING_STATUS,
					billingStatus);
			query.setString(DeliveryManifestConstants.CONSIGNMENT_NUMBER,
					consgNumber);
			query.executeUpdate();
		} catch (Exception ex) {
			logger.error("DeliveryManifestMDBDAOImpl::updateSAPIntgConsgStatus occured:"
					+ex.getMessage());
			throw new CGSystemException("  " + ex.getMessage(), ex);
		} finally {
			session.flush();
			session.close();
		}

	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#validatePinCode(String, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PincodeDO validatePinCode(String consgpinCode, int officeID)
	throws CGSystemException {
		// TODO Auto-generated method stub
		PincodeDO pincode = null;
		Session session = null;
		List<PincodeDO> pincodeList = null;
		try {
			pincodeList = new ArrayList<PincodeDO>();
			session = getHibernateTemplate().getSessionFactory().openSession();
			pincodeList = (ArrayList<PincodeDO>) session
			.createCriteria(PincodeDO.class)
			.add(Restrictions.eq(DeliveryManifestConstants.PINCODE,
					consgpinCode))
					.add(Restrictions.eq(DeliveryManifestConstants.OFFICE_KEY,
							officeID)).list();
			if (pincodeList != null && !pincodeList.isEmpty()) {
				pincode = pincodeList.get(0);
			}
		} catch (Exception e) {
			logger.error("DeliveryManifestMDBDAOImpl::validatePinCode occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		} finally {
			session.flush();
			session.close();
		}
		return pincode;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#getPincodeArea(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public AreaDO getPincodeArea(int pincodeID) throws CGSystemException {
		// TODO Auto-generated method stub
		AreaDO area = null;
		Session session = null;
		List<AreaDO> areaList = null;
		try {
			areaList = new ArrayList<AreaDO>();
			session = getHibernateTemplate().getSessionFactory().openSession();
			areaList = (ArrayList<AreaDO>) session
			.createCriteria(AreaDO.class)
			.add(Restrictions.eq(DeliveryManifestConstants.PINCODE_ID,
					pincodeID)).list();
			if (areaList != null && !areaList.isEmpty()) {
				area = areaList.get(0);
			}
		} catch (Exception e) {
			logger.error("DeliveryManifestMDBDAOImpl::getPincodeArea occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		} finally {
			session.flush();
			session.close();
		}

		return area;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#validateAgnstFranchisee(int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean validateAgnstFranchisee(int franchiseeID, int pincodeID)
	throws CGSystemException {
		// TODO Auto-generated method stub
		FranchiseePincodeMappingDO frpincodeMappingDO = null;
		Session session = null;
		PincodeDO pincode = null;
		boolean isValidPinCode = Boolean.TRUE;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			frpincodeMappingDO = (FranchiseePincodeMappingDO) session
			.createCriteria(FranchiseePincodeMappingDO.class)
			// .add(Restrictions.eq("pincode.pincodeId", pincodeID))
			.add(Restrictions.eq(
					DeliveryManifestConstants.FRANCHISEE_KEY,
					franchiseeID)).uniqueResult();
			if (frpincodeMappingDO != null) {
				pincode = frpincodeMappingDO.getPincode();
				if (pincode != null) {
					if (pincodeID != pincode.getPincodeId()) {
						isValidPinCode = Boolean.FALSE;
					}
				}
			}
		} catch (Exception e) {
			logger.error("DeliveryManifestMDBDAOImpl::validateAgnstFranchisee occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		} finally {
			session.flush();
			session.close();
		}
		return isValidPinCode;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#getBooking(String, CGBaseEntity)
	 */
	@Override
	public CGBaseEntity getBooking(String consgNumber, CGBaseEntity entity)
	throws CGSystemException {
		Session session = null;
		CGBaseEntity booking = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			booking = (CGBaseEntity) session
			.createCriteria(entity.getClass())
			.add(Restrictions.eq(ManifestConstant.CONSIGNMENT_NUMBER,
					consgNumber)).uniqueResult();
		} catch (Exception ex) {
			logger.error("DeliveryManifestMDBDAOImpl::getBooking occured:"
					+ex.getMessage());
			throw new CGSystemException("  " + ex.getMessage(), ex);
		} finally {
			session.flush();
			session.close();
		}
		return booking;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#insertBookingLog(BookingLogDO)
	 */
	public void insertBookingLog(BookingLogDO logDo) throws CGSystemException {
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		try {
			hibernateTemplate.save(logDo);
		} catch (Exception e) {
			logger.error("DeliveryManifestMDBDAOImpl::insertBookingLog occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		} finally {
			hibernateTemplate.flush();
		}
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#getPinCodeIdByCode(String)
	 */
	public int getPinCodeIdByCode(String pinCode) throws CGSystemException {
		PincodeDO pinCodeDO = null;
		Session session = null;
		int pinCodeId = 0;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			pinCodeDO = (PincodeDO) session
			.createCriteria(PincodeDO.class)
			.add(Restrictions.eq(DeliveryManifestConstants.PINCODE,
					pinCode)).uniqueResult();
			if (pinCodeDO != null) {
				pinCodeId = pinCodeDO.getPincodeId();
			}
		} catch (Exception e) {
			logger.error("DeliveryManifestMDBDAOImpl::getPinCodeIdByCode occured:"
					+e.getMessage());
		} finally {
			session.flush();
			session.close();
		}
		return pinCodeId;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#getIncmgMnfst(String, List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ManifestDO getIncmgMnfst(String consgNumber,
			List<Integer> manifestTypeIds) throws CGSystemException {
		ManifestDO manifest = null;
		List<ManifestDO> manifestList = null;
		try {
			String[] params = { "consgNumber", "manifestTypeId" };
			Object[] values = { consgNumber, manifestTypeIds };
			manifestList = (List<ManifestDO>) getHibernateTemplate()
			.findByNamedQueryAndNamedParam("getIncmgManifest", params,
					values);
			if (manifestList != null && manifestList.size() > 0) {
				manifest = manifestList.get(0);
			}
		} catch (Exception ex) {
			logger.error("DeliveryManifestMDBDAOImpl::getIncmgMnfst occured:"
					+ex.getMessage());
			throw new CGSystemException("  " + ex.getMessage(), ex);
		}
		return manifest;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#isConsignmentDelivered(String)
	 */
	@Override
	public boolean isConsignmentDelivered(String consgNum)
	throws CGSystemException {
		boolean isDelivered = Boolean.FALSE;
		long count = 0;
		try {
			count = (Long) getHibernateTemplate()
			.findByNamedQueryAndNamedParam("isConsgDelivered",
					"consgNumber", consgNum).get(0);
			if (count >= 1) {
				isDelivered = Boolean.TRUE;
			}
		} catch (Exception ex) {
			logger.error("DeliveryManifestMDBDAOImpl::isConsignmentDelivered occured:"
					+ex.getMessage());
			throw new CGSystemException("  " + ex.getMessage(), ex);
		}
		return isDelivered;

	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#isConsignmentHeldUp(String)
	 */
	@Override
	public boolean isConsignmentHeldUp(String consgNum)
	throws CGSystemException {
		boolean isHeldUP = Boolean.FALSE;
		long count = 0;
		try {
			count = (Long) getHibernateTemplate()
			.findByNamedQueryAndNamedParam("isConsgInHeldUp",
					"consgNumber", consgNum).get(0);
			if (count >= 1) {
				isHeldUP = Boolean.TRUE;
			}
		} catch (Exception ex) {
			logger.error("DeliveryManifestMDBDAOImpl::isConsignmentHeldUp occured:"
					+ex.getMessage());
			throw new CGSystemException("  " + ex.getMessage(), ex);
		}
		return isHeldUP;

	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#isConsignmentReturned(String)
	 */
	@Override
	public boolean isConsignmentReturned(String consgNum)
	throws CGSystemException {
		boolean isHeldUP = Boolean.FALSE;
		long count = 0;
		try {
			count = (Long) getHibernateTemplate()
			.findByNamedQueryAndNamedParam("isConsgRTOed",
					"consgNumber", consgNum).get(0);
			if (count >= 1) {
				isHeldUP = Boolean.TRUE;
			}
		} catch (Exception ex) {
			logger.error("DeliveryManifestMDBDAOImpl::isConsignmentReturned occured:"
					+ex.getMessage());
			throw new CGSystemException("  " + ex.getMessage(), ex);
		}
		return isHeldUP;

	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#isPaperWorkManifested(String, Integer)
	 */
	@Override
	public boolean isPaperWorkManifested(String consgNum, Integer mnfstTypeId)
	throws CGSystemException {
		boolean isPaperworkExist = Boolean.FALSE;
		long count = 0;
		try {
			String[] params = { "consgNum", "mnpMnfstTypeId" };
			Object[] values = { consgNum, mnfstTypeId };
			count = (Long) getHibernateTemplate()
			.findByNamedQueryAndNamedParam("isPaperWorkManifested",
					params, values).get(0);
			if (count >= 1) {
				isPaperworkExist = Boolean.TRUE;
			}
		} catch (Exception ex) {
			logger.error("DeliveryManifestMDBDAOImpl::isPaperWorkManifested occured:"
					+ex.getMessage());
			throw new CGSystemException("  " + ex.getMessage(), ex);
		}
		return isPaperworkExist;

	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#saveMiscExpForRelease(List)
	 */
	@Override
	public String saveMiscExpForRelease(List<MiscExpenseDO> miscExpenseDoList)
	throws CGSystemException {
		// TODO Auto-generated method stub
		String message = "Failure";
		try {
			HibernateTemplate hibernateTemplate = getHibernateTemplate();
			if (miscExpenseDoList != null) {
				hibernateTemplate.saveOrUpdateAll(miscExpenseDoList);
				message = "SUCCESS";
			}
		} catch (Exception ex) {
			logger.error("DeliveryManifestMDBDAOImpl::saveMiscExpForRelease occured:"
					+ex.getMessage());
			throw new CGSystemException("  " + ex.getMessage(), ex);
		}

		return message;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#isPaperWrkConsg(String)
	 */
	@SuppressWarnings("unchecked")
	public boolean isPaperWrkConsg(String cnNum) throws CGBusinessException {
		long count = 0;
		boolean isPaperWrkConsg = Boolean.FALSE;
		try {
			count = (Long) getHibernateTemplate()
			.findByNamedQueryAndNamedParam(
					DeliveryManifestConstants.GET_PAPER_WRK_BOOKING,
					DeliveryManifestConstants.CONSGNUM, cnNum).get(0);
			if (count >= 1) {
				isPaperWrkConsg = Boolean.TRUE;
			}
		} catch (Exception e) {
			logger.error("DeliveryManifestMDBDAOImpl::isPaperWrkConsg occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		}
		return isPaperWrkConsg;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#isAlreadyHO(String)
	 */
	@SuppressWarnings("unchecked")
	public String isAlreadyHO(String fdmNumber) throws CGBusinessException {
		String fdmHONumber = "";
		List<Object[]> results = null;
		try {
			results = getHibernateTemplate().findByNamedQueryAndNamedParam(
					DeliveryManifestConstants.IS_ALREADY_HO, "fdmNumber",
					fdmNumber);
			if (results != null && !results.isEmpty()) {
				Object object = results.get(0);
				fdmHONumber = (String) object;
			}
		} catch (Exception e) {
			logger.error("DeliveryManifestMDBDAOImpl::isAlreadyHO occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		}

		return fdmHONumber;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#findByFrHOumber(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FranchiseDeliveryManifestDO> findByFrHOumber(String frHONum)
	throws CGSystemException, CGBusinessException {
		List<FranchiseDeliveryManifestDO> frHOList = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			frHOList = session
			.createCriteria(FranchiseDeliveryManifestDO.class)
			.add(Restrictions.eq(
					DeliveryManifestConstants.FR_HO_NUMBER, frHONum))
					.add(Restrictions.ne(
							DeliveryManifestConstants.CONSG_STATUS,
							DeliveryManifestConstants.DELITED_STATUS)).list();
		} catch (Exception obj) {
			logger.error("DeliveryManifestMDBDAOImpl::findByFrHOumber occured:"
					+obj.getMessage());
			throw new CGBusinessException(obj);
		}
		return frHOList;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#getPhonesByID(Integer, String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HandHeldDeviceDO> getPhonesByID(Integer branchId,
			String branchCode) throws CGSystemException {
		List<HandHeldDeviceDO> handHeldDeviceDOList = null;

		try {
			handHeldDeviceDOList = (List<HandHeldDeviceDO>) getHibernateTemplate()
			.findByNamedQueryAndNamedParam(
					"getHandHeldDeviceNumbersByBranchCode",
					"officeCode", branchCode);

		} catch (Exception obj) {
			logger.error("DeliveryManifestMDBDAOImpl::getPhonesByID occured:"
					+obj.getMessage());
			throw new CGSystemException(obj);
		}
		return handHeldDeviceDOList;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#getBrDeliveryId(String, Integer, String, String, String)
	 */
	@SuppressWarnings("unchecked")
	public Integer getBrDeliveryId(String consgNumber, Integer attemptNumber,
			String consgStatus, String dlvStatus, String runSheetNum)
	throws CGSystemException {
		Integer manifestId = 0;
		List<Integer> mnfstTypeIds = null;
		try {
			String params[] = { "consgNumber", "attemptNumber", "consgStatus",
					"dlvStatus", "runSheetNum" };
			Object values[] = { consgNumber, attemptNumber, consgStatus,
					dlvStatus, runSheetNum };
			mnfstTypeIds = (List<Integer>) getHibernateTemplate()
			.findByNamedQueryAndNamedParam("getBrDeliveryIdForDBSYnc",
					params, values);
			if (mnfstTypeIds != null && mnfstTypeIds.size() > 0) {
				manifestId = mnfstTypeIds.get(0);
			}
		} catch (Exception obj) {
			logger.error("DeliveryManifestMDBDAOImpl::getBrDeliveryId occured:"
					+obj.getMessage());
			throw new CGSystemException(obj);
		}
		return manifestId;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#getFrDeliveryId(String, Integer, String, String, String)
	 */
	@SuppressWarnings("unchecked")
	public Integer getFrDeliveryId(String consgNumber, Integer attemptNumber,
			String consgStatus, String dlvStatus, String fdmNumber)
	throws CGSystemException {
		Integer manifestId = 0;
		List<Integer> mnfstTypeIds = null;
		try {
			/*
			 * String params[] = { "consgNumber", "attemptNumber",
			 * "consgStatus", "dlvStatus", "fdmNumber" }; Object values[] = {
			 * consgNumber, attemptNumber, consgStatus, dlvStatus, fdmNumber };
			 */
			String params[] = { "consgNumber", "attemptNumber", "fdmNumber" };
			Object values[] = { consgNumber, attemptNumber, fdmNumber };
			mnfstTypeIds = (List<Integer>) getHibernateTemplate()
			.findByNamedQueryAndNamedParam("getFrDeliveryIdForDBSYnc",
					params, values);
			if (mnfstTypeIds != null && mnfstTypeIds.size() > 0) {
				manifestId = mnfstTypeIds.get(0);
			}
		} catch (Exception obj) {
			logger.error("DeliveryManifestMDBDAOImpl::getFrDeliveryId occured:"
					+obj.getMessage());
			throw new CGSystemException(obj);
		}
		return manifestId;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryManifestMDBDAO#getFrHODeliveryId(String, Integer, String, String, String)
	 */
	@SuppressWarnings("unchecked")
	public Integer getFrHODeliveryId(String consgNumber, Integer attemptNumber,
			String consgStatus, String dlvStatus, String handOverNum)
	throws CGSystemException {
		Integer manifestId = 0;
		List<Integer> mnfstTypeIds = null;
		try {
			String params[] = { "consgNumber", "attemptNumber", "consgStatus",
					"dlvStatus", "handOverNum" };
			Object values[] = { consgNumber, attemptNumber, consgStatus,
					dlvStatus, handOverNum };
			mnfstTypeIds = (List<Integer>) getHibernateTemplate()
			.findByNamedQueryAndNamedParam(
					"getFrHODeliveryIdForDBSYnc", params, values);
			if (mnfstTypeIds != null && mnfstTypeIds.size() > 0) {
				manifestId = mnfstTypeIds.get(0);
			}
		} catch (Exception obj) {
			logger.error("DeliveryManifestMDBDAOImpl::getFrHODeliveryId occured:"
					+obj.getMessage());
			throw new CGSystemException(obj);
		}
		return manifestId;
	}

}
