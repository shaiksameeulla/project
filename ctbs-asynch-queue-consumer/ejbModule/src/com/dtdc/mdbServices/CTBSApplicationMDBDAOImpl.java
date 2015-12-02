package src.com.dtdc.mdbServices;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import src.com.dtdc.constants.CommonConstants;
import src.com.dtdc.constants.DRSConstants;
import src.com.dtdc.constants.DeliveryManifestConstants;
import src.com.dtdc.constants.ManifestConstant;

import com.capgemini.lbs.framework.constants.BookingConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseDAO.CGBaseDAO;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.domain.booking.BookingDO;
import com.dtdc.domain.booking.ProductDO;
import com.dtdc.domain.configurableparam.ConfigurableParamsDO;
import com.dtdc.domain.expense.ExpenditureTypeDO;
import com.dtdc.domain.login.UserDO;
import com.dtdc.domain.manifest.ManifestTypeDO;
import com.dtdc.domain.master.ReasonDO;
import com.dtdc.domain.master.StandardTypeDO;
import com.dtdc.domain.master.StdHandlingInstDO;
import com.dtdc.domain.master.agent.AgentAddressDO;
import com.dtdc.domain.master.agent.AgentDO;
import com.dtdc.domain.master.airline.AirportDO;
import com.dtdc.domain.master.coloader.CoLoaderDO;
import com.dtdc.domain.master.customer.ConsigneeAddressDO;
import com.dtdc.domain.master.customer.CustomerDO;
import com.dtdc.domain.master.document.DocumentDO;
import com.dtdc.domain.master.employee.EmployeeDO;
import com.dtdc.domain.master.franchisee.FranchiseeDO;
import com.dtdc.domain.master.geography.CityDO;
import com.dtdc.domain.master.geography.CountryDO;
import com.dtdc.domain.master.geography.PincodeDO;
import com.dtdc.domain.master.identityproof.IdentityProofDocDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.master.office.OpsOfficeDO;
import com.dtdc.domain.master.product.ServiceDO;
import com.dtdc.domain.master.product.ServiceMappingDO;
import com.dtdc.domain.master.vendor.VendorDO;
import com.dtdc.domain.opmaster.shipment.ModeDO;
import com.dtdc.domain.shortapproval.ShortApprovalDO;

// TODO: Auto-generated Javadoc
/**
 * The Class CTBSApplicationDAOImpl.
 *
 * @author Narasimha Rao Kattunga
 */
public class CTBSApplicationMDBDAOImpl extends CGBaseDAO implements
CTBSApplicationMDBDAO {
	/** logger. */
	private final static Logger LOGGER = LoggerFactory
	.getLogger(CTBSApplicationMDBDAOImpl.class);

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getStandardTypesByParent(String, String)
	 */
	@SuppressWarnings("unchecked")
	public List<StandardTypeDO> getStandardTypesByParent(String typeName,
			String parentType) throws CGSystemException {
		List<StandardTypeDO> stdTypesList = null;
		String[] params = CommonConstants.GET_STD_TYPES_BY_PARENT_TYPE_AND_TYPE_NAME_PARAM
		.split(",");
		Object[] values = { parentType, typeName };
		stdTypesList = getHibernateTemplate()
		.findByNamedQueryAndNamedParam(
				CommonConstants.GET_STD_TYPES_BY_PARENT_TYPE_AND_TYPE_NAME_QUERY,
				params, values);
		return stdTypesList;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getProductByIdOrCode(Integer, String)
	 */
	@Override
	public ProductDO getProductByIdOrCode(Integer productId, String productCode)
	throws CGSystemException {
		ProductDO product = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			if (StringUtil.isEmpty(productCode)) {
				product = (ProductDO) session.createCriteria(ProductDO.class)
				.add(Restrictions.eq("productId", productId))
				.uniqueResult();
			} else {
				product = (ProductDO) session.createCriteria(ProductDO.class)
				.add(Restrictions.eq("productCode", productCode))
				.uniqueResult();
			}

		} catch (Exception e) {
			logger.error("CTBSApplicationMDBDAOImpl::getProductByIdOrCode::Exception occured:"
					+e.getMessage());
		} finally {
			session.flush();
			session.close();
		}
		return product;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getModeById(Integer)
	 */
	@Override
	public ModeDO getModeById(Integer modeId) throws CGSystemException {
		ModeDO mode = null;
		try {
			HibernateTemplate hibernateTemplate = getHibernateTemplate();
			mode = hibernateTemplate.get(ModeDO.class, modeId);
		} catch (Exception e) {
			logger.error("CTBSApplicationMDBDAOImpl::getModeById::Exception occured:"
					+e.getMessage());
		}
		return mode;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getModes()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ModeDO> getModes() throws CGSystemException {
		List<ModeDO> modeList = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			modeList = new ArrayList<ModeDO>();
			modeList = (ArrayList<ModeDO>) session.createCriteria(ModeDO.class)
			.list();
		} catch (Exception e) {
			logger.error("CTBSApplicationMDBDAOImpl::getModes::Exception occured:"
					+e.getMessage());
		} finally {
			session.flush();
			session.close();
		}
		return modeList;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getStandardCodes(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StandardTypeDO> getStandardCodes(String typeName)
	throws CGSystemException {
		ArrayList<StandardTypeDO> stdTypes = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			stdTypes = (ArrayList<StandardTypeDO>) session
			.createCriteria(StandardTypeDO.class)
			.add(Restrictions.eq("typeName", typeName)).list();
		} catch (Exception e) {
			logger.error("CTBSApplicationMDBDAOImpl::getStandardCodes::Exception occured:"
					+e.getMessage());
		} finally {
			session.flush();
			session.close();
		}
		return stdTypes;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getStdHandlingInsts()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StdHandlingInstDO> getStdHandlingInsts() {
		List<StdHandlingInstDO> stdHandIntsList = null;
		Session session = null;
		try {
			session =getHibernateTemplate().getSessionFactory().openSession();
			stdHandIntsList = new ArrayList<StdHandlingInstDO>();
			stdHandIntsList = (ArrayList<StdHandlingInstDO>) session
			.createCriteria(StdHandlingInstDO.class).list();
		} catch (Exception e) {
			logger.error("CTBSApplicationMDBDAOImpl::getStdHandlingInsts::Exception occured:"
					+e.getMessage());

		} finally {
			session.flush();
			session.close();
		}
		return stdHandIntsList;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getProducts()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductDO> getProducts() throws CGSystemException {
		List<ProductDO> products = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			products = (List<ProductDO>) session
			.createCriteria(ProductDO.class).list();
		} catch (Exception obj) {
			logger.error("CTBSApplicationMDBDAOImpl::getProducts::Exception occured:"
					+obj.getMessage());
			throw new CGSystemException(obj);
		} finally {
			session.flush();
			session.close();
		}
		return products;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getReasons()
	 */
	@SuppressWarnings("unchecked")
	public List<ReasonDO> getReasons() throws CGSystemException {
		List<ReasonDO> reasonsList = new ArrayList<ReasonDO>();
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			reasonsList = (List<ReasonDO>) session.createCriteria(
					ReasonDO.class).list();
		} catch (Exception obj) {
			logger.error("CTBSApplicationMDBDAOImpl::getReasons::Exception occured:"
					+obj.getMessage());
			throw new CGSystemException(obj);
		} finally {
			session.flush();
			session.close();
		}
		return reasonsList;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getOffices()
	 */
	@Override
	public List<OfficeDO> getOffices() throws CGSystemException {
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		// FIXME The query to be modified to get specific branch offices
		List<OfficeDO> list = hibernateTemplate.loadAll(OfficeDO.class);
		return list;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getOfficesbyOfficeId(Integer)
	 */
	@Override
	public OfficeDO getOfficesbyOfficeId(Integer officeId) {
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		OfficeDO obj = hibernateTemplate.get(OfficeDO.class, officeId);
		return obj;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getOfficebyOfficeCode(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public OfficeDO getOfficebyOfficeCode(String officeCode)
	throws CGSystemException {
		OfficeDO officeDO = null;
		List<OfficeDO> officeDOList = null;
		String paramNames = "officeCode";
		Object values = officeCode;
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		officeDOList = hibernateTemplate.findByNamedQueryAndNamedParam(
				ManifestConstant.GET_OFFICE_DETAILS_QUERY, paramNames, values);
		if (officeDOList != null && officeDOList.size() > 0) {
			officeDO = officeDOList.get(0);
		}
		return officeDO;
	}



	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getBranchByCodeOrID(Integer, String)
	 */
	public OfficeDO getBranchByCodeOrID(Integer branchId, String branchCode)
	throws CGSystemException {
		OfficeDO officeDO = new OfficeDO();
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			if (StringUtil.isEmpty(branchCode)) {
				officeDO = (OfficeDO) session
				.createCriteria(OfficeDO.class)
				.add(Restrictions.eq(
						DeliveryManifestConstants.OFFICE_ID, branchId))
						.uniqueResult();
			} else {
				officeDO = (OfficeDO) session
				.createCriteria(OfficeDO.class)
				.add(Restrictions.eq(
						DeliveryManifestConstants.OFFICE_CODE,
						branchCode)).uniqueResult();
			}
		} catch (Exception obj) {
			logger.error("CTBSApplicationMDBDAOImpl::getBranchByCodeOrID::Exception occured:"
					+obj.getMessage());
			throw new CGSystemException(obj);
		} finally {
			session.flush();
			session.close();
		}
		return officeDO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getROByBranchOffice(Integer)
	 */
	@Override
	public OfficeDO getROByBranchOffice(Integer branchId)
	throws CGSystemException {
		OfficeDO office = null;
		List<OfficeDO> offices = getHibernateTemplate()
		.findByNamedQueryAndNamedParam(
				CommonConstants.GET_RO_BY_BO_QUERY,
				CommonConstants.GET_RO_BY_BO_PARAM, branchId);

		if (offices != null && !offices.isEmpty()) {
			office = offices.get(0);
		}
		return office;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getEmployeeByCodeOrID(Integer, String)
	 */
	@Override
	public EmployeeDO getEmployeeByCodeOrID(Integer employeeId,
			String employeeCode) throws CGSystemException {
		EmployeeDO employee = new EmployeeDO();
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			if (StringUtil.isEmpty(employeeCode)) {
				employee = (EmployeeDO) session
				.createCriteria(EmployeeDO.class)
				.add(Restrictions.eq(CommonConstants.EMPLOYEE_ID,
						employeeId)).uniqueResult();
			} else {
				employee = (EmployeeDO) session
				.createCriteria(EmployeeDO.class)
				.add(Restrictions.eq(CommonConstants.EMPLOYEE_CODE,
						employeeCode)).uniqueResult();
			}
		} catch (Exception obj) {
			logger.error("CTBSApplicationMDBDAOImpl::getEmployeeByCodeOrID::Exception occured:"
					+obj.getMessage());
			throw new CGSystemException(obj);
		} finally {
			session.flush();
			session.close();
		}
		return employee;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getCustomerByIdOrCode(Integer, String)
	 */
	@Override
	public CustomerDO getCustomerByIdOrCode(Integer custId, String custCode)
	throws CGSystemException {
		CustomerDO customer = new CustomerDO();
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			if (custId != null) {
				customer = (CustomerDO) session
				.createCriteria(CustomerDO.class)
				.add(Restrictions.eq(CommonConstants.CUSTOMER_ID,
						custId)).uniqueResult();
			} else {
				customer = (CustomerDO) session
				.createCriteria(CustomerDO.class)
				.add(Restrictions.eq(CommonConstants.CUSTOMER_CODE,
						custCode)).uniqueResult();
			}
		} catch (Exception obj) {
			logger.error("CTBSApplicationMDBDAOImpl::getCustomerByIdOrCode::Exception occured:"
					+obj.getMessage());
			throw new CGSystemException(obj);
		} finally {
			session.flush();
			session.close();
		}
		return customer;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getFranchiseeByFrCode(String)
	 */
	@Override
	public FranchiseeDO getFranchiseeByFrCode(String frCode)
	throws CGSystemException {
		FranchiseeDO franchiseeDO = new FranchiseeDO();
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		if (org.springframework.util.StringUtils.hasText(frCode)) {
			Query q = session
			.getNamedQuery(CommonConstants.GET_FRANCHISEE_BY_CODE);
			q.setString("frCode", frCode);
			franchiseeDO = (FranchiseeDO) q.uniqueResult();
		}
		return franchiseeDO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getPinCodeByIdOrCode(Integer, String)
	 */
	@Override
	public PincodeDO getPinCodeByIdOrCode(Integer pinCodeId, String pinCode)
	throws CGSystemException {
		PincodeDO pinCodeDO = null;
		Session session = null;
		try {
			session =getHibernateTemplate().getSessionFactory().openSession();
			if (StringUtil.isEmpty(pinCode)) {
				pinCodeDO = (PincodeDO) session.createCriteria(PincodeDO.class)
				.add(Restrictions.eq("pincodeId", pinCodeId))
				.uniqueResult();
			} else {
				pinCodeDO = (PincodeDO) session.createCriteria(PincodeDO.class)
				.add(Restrictions.eq("pincode", pinCode))
				.uniqueResult();
			}
		} catch (Exception e) {
			logger.error("CTBSApplicationMDBDAOImpl::getPinCodeByIdOrCode::Exception occured:"
					+e.getMessage());
		} finally {
			session.flush();
			session.close();
		}
		return pinCodeDO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getCityByIdOrCode(Integer, String)
	 */
	@Override
	public CityDO getCityByIdOrCode(Integer cityId, String cityCode)
	throws CGSystemException {
		CityDO city = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			if (!(StringUtil.isEmpty(cityCode))) {
				city = (CityDO) session.createCriteria(CityDO.class)
				.add(Restrictions.eq("cityCode", cityCode))
				.uniqueResult();
			} else {
				city = (CityDO) session.createCriteria(CityDO.class)
				.add(Restrictions.eq("cityId", cityId)).uniqueResult();
			}
		} catch (Exception e) {
			logger.error("CTBSApplicationMDBDAOImpl::getCityByIdOrCode::Exception occured:"
					+e.getMessage());
		} finally {
			session.flush();
			session.close();
		}
		return city;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getConsigneeAddress(Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ConsigneeAddressDO getConsigneeAddress(Integer consigneeID)
	throws CGSystemException {
		List<ConsigneeAddressDO> consigneeAddressList = null;
		ConsigneeAddressDO consigneeAddress = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			consigneeAddressList = new ArrayList<ConsigneeAddressDO>();
			consigneeAddressList = (ArrayList<ConsigneeAddressDO>) session
			.createCriteria(ConsigneeAddressDO.class)
			.add(Restrictions.eq("consignee.consigneeId", consigneeID))
			.list();
			if (consigneeAddressList != null) {
				if (consigneeAddressList.size() > 0) {
					consigneeAddress = consigneeAddressList.get(0);
				}

			}

		} catch (Exception e) {
			logger.error("CTBSApplicationMDBDAOImpl::getConsigneeAddress::Exception occured:"
					+e.getMessage());
		} finally {
			session.flush();
			session.close();
		}
		return consigneeAddress;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getBookingDetailsByCn(String)
	 */
	public BookingDO getBookingDetailsByCn(String cnNumber) {
		BookingDO bookingDetails = null;
		List<BookingDO> bookedCnList = getHibernateTemplate()
		.findByNamedQueryAndNamedParam(
				CommonConstants.GET_BOOKING_BY_CN_QUERY,
				CommonConstants.GET_BOOKING_BY_CN_PARAM, cnNumber);
		if (bookedCnList != null && !bookedCnList.isEmpty()) {
			bookingDetails = bookedCnList.get(0);
		}
		return bookingDetails;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getApproversByApprovalNameAndOffice(String, Integer)
	 */
	@Override
	public List<ShortApprovalDO> getApproversByApprovalNameAndOffice(
			String approvalName, Integer officeid) {
		List<ShortApprovalDO> doList = getHibernateTemplate()
		.findByNamedQueryAndNamedParam(CommonConstants.GET_APPROVERS,
				new String[] { "approvalName", "officeId" },
				new Object[] { approvalName, officeid });

		return doList;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getAgentsByOfficeId(Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AgentDO> getAgentsByOfficeId(Integer officeid)
	throws CGSystemException {
		List<AgentDO> agents = new ArrayList<AgentDO>();
		Session session = null;
		try {
			session =getHibernateTemplate().getSessionFactory().openSession();
			agents = (List<AgentDO>) session.createCriteria(AgentDO.class)
			.add(Restrictions.eq("office.officeId", officeid)).list();
		} catch (Exception obj) {
			logger.error("CTBSApplicationMDBDAOImpl::getAgentsByOfficeId::Exception occured:"
					+obj.getMessage());
			throw new CGSystemException(obj);
		} finally {
			session.flush();
			session.close();
		}
		return agents;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getAgentsByCode(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public AgentDO getAgentsByCode(String agentCode) throws CGSystemException {
		AgentDO agents = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			agents = (AgentDO) session.createCriteria(AgentDO.class)
			.add(Restrictions.eq("agentCode", agentCode))
			.uniqueResult();
		} catch (Exception obj) {
			logger.error("CTBSApplicationMDBDAOImpl::getAgentsByCode::Exception occured:"
					+obj.getMessage());
			throw new CGSystemException(obj);
		} finally {
			session.flush();
			session.close();
		}
		return agents;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getAllCountries()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CountryDO> getAllCountries() {
		List<CountryDO> countryList = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			countryList = new ArrayList<CountryDO>();
			countryList = (ArrayList<CountryDO>) session.createCriteria(
					CountryDO.class).list();
		} catch (Exception e) {
			logger.error("CTBSApplicationMDBDAOImpl::getAllCountries::Exception occured:"
					+e.getMessage());
		} finally {
			session.flush();
			session.close();
		}
		return countryList;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getAllDocuments()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentDO> getAllDocuments() {
		List<DocumentDO> docList = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			docList = new ArrayList<DocumentDO>();
			docList = (ArrayList<DocumentDO>) session.createCriteria(
					DocumentDO.class).list();
		} catch (Exception e) {
			logger.error("CTBSApplicationMDBDAOImpl::getAllDocuments::Exception occured:"
					+e.getMessage());
		} finally {
			session.flush();
			session.close();
		}
		return docList;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getCoLoadersByOfficeId(Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CoLoaderDO> getCoLoadersByOfficeId(Integer officeid)
	throws CGSystemException {
		List<CoLoaderDO> coLoaders = new ArrayList<CoLoaderDO>();
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			coLoaders = (List<CoLoaderDO>) session
			.createCriteria(CoLoaderDO.class)
			.add(Restrictions.eq("office.officeId", officeid)).list();
		} catch (Exception obj) {
			logger.error("CTBSApplicationMDBDAOImpl::getCoLoadersByOfficeId::Exception occured:"
					+obj.getMessage());
			throw new CGSystemException(obj);
		} finally {
			session.flush();
			session.close();
		}
		return coLoaders;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getOfficeByEmployeeId(Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public OfficeDO getOfficeByEmployeeId(Integer employeeId)
	throws CGSystemException {
		LOGGER.info("CTBSApplicationDAOImpl: getOfficeByEmployeeId():START");
		OfficeDO officeDO = null;

		EmployeeDO employeeDO = this.getEmployeeByCodeOrID(employeeId, "");
		if (employeeDO != null) {
			officeDO = employeeDO.getOffice();
		}
		LOGGER.info("CTBSApplicationDAOImpl: getOfficeByEmployeeId():END");
		return officeDO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getEmployeeByOfficeId(Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeDO> getEmployeeByOfficeId(Integer officeId)
	throws CGSystemException {
		List<EmployeeDO> employees = new ArrayList<EmployeeDO>();
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			employees = (List<EmployeeDO>) session
			.createCriteria(EmployeeDO.class)
			.add(Restrictions.eq("office.officeId", officeId)).list();
		} catch (Exception obj) {
			logger.error("CTBSApplicationMDBDAOImpl::getEmployeeByOfficeId::Exception occured:"
					+obj.getMessage());
			throw new CGSystemException(obj);
		} finally {
			session.flush();
			session.close();
		}
		return employees;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getFranchisees()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FranchiseeDO> getFranchisees() throws CGSystemException {
		List<FranchiseeDO> franchisees = new ArrayList<FranchiseeDO>();
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			franchisees = (List<FranchiseeDO>) session.createCriteria(
					FranchiseeDO.class).list();
		} catch (Exception obj) {
			logger.error("CTBSApplicationMDBDAOImpl::getFranchisees::Exception occured:"
					+obj.getMessage());
			throw new CGSystemException(obj);
		} finally {
			session.flush();
			session.close();
		}
		return franchisees;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getFranchiseesByCode(String)
	 */
	@Override
	public FranchiseeDO getFranchiseesByCode(String frCode)
	throws CGSystemException {
		FranchiseeDO franchisee = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			franchisee = (FranchiseeDO) session
			.createCriteria(FranchiseeDO.class)
			.add(Restrictions.eq("franchiseeCode", frCode))
			.uniqueResult();

		} catch (Exception e) {
			logger.error("CTBSApplicationMDBDAOImpl::getFranchiseesByCode::Exception occured:"
					+e.getMessage());
		} finally {
			session.flush();
			session.close();
		}
		return franchisee;

	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getManifestTypeByCode(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ManifestTypeDO getManifestTypeByCode(String manifestTypeCode) {
		LOGGER.info("CTBSApplicationDAOImpl: getManifestTypeByCode(): START");
		ManifestTypeDO manifestTypeDo = null;

		List<ManifestTypeDO> manifestTypeList = getHibernateTemplate()
		.findByNamedQueryAndNamedParam(
				CommonConstants.GET_MANIFEST_TYPE_QUERY,
				CommonConstants.GET_MANIFEST_TYPE_QUERY_PARAM,
				manifestTypeCode);

		if (manifestTypeList != null && manifestTypeList.size() > 0) {
			manifestTypeDo = manifestTypeList.get(0);
		}

		LOGGER.info("CTBSApplicationDAOImpl: getManifestTypeByCode(): END");
		return manifestTypeDo;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getManifestTypes()
	 */
	public List<ManifestTypeDO> getManifestTypes() {
		List<ManifestTypeDO> typeList = null;
		typeList = getHibernateTemplate().findByNamedQuery(CommonConstants.GET_ALL_MANIFEST_TYPE_QUERY);
		return typeList;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getCitiesByDistrictId(Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CityDO> getCitiesByDistrictId(Integer districtId)
	throws CGSystemException {
		List<CityDO> cities = new ArrayList<CityDO>();
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();

			cities = (List<CityDO>) session.createCriteria(CityDO.class)
			.add(Restrictions.eq("district.districtId", districtId))
			.list();
		} catch (Exception obj) {
			logger.error("CTBSApplicationMDBDAOImpl::getCitiesByDistrictId::Exception occured:"
					+obj.getMessage());
			throw new CGSystemException(obj);
		} finally {
			session.flush();
			session.close();
		}
		return cities;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getDPCodesForFranchisee(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getDPCodesForFranchisee(String franchiseeId)
	throws CGSystemException {
		String queryName = "getActiveDirectPartiesForFranchisee";
		HibernateTemplate hibernateTemplate = getHibernateTemplate();

		List<String> dpCodes = null;
		try {
			dpCodes = hibernateTemplate.findByNamedQueryAndNamedParam(
					queryName, BookingConstants.FRANCHISEE_ID,
					Integer.parseInt(franchiseeId));
			hibernateTemplate.flush();
		} catch (Exception obj) {
			logger.error("CTBSApplicationMDBDAOImpl::getDPCodesForFranchisee::Exception occured:"
					+obj.getMessage());
			throw new CGSystemException(obj);
		}
		return dpCodes;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getVendorName(String)
	 */
	@Override
	public String getVendorName(String vendCode) throws CGSystemException {
		String name = null;
		String hql = "select concat(vendor.firstName,' ', vendor.lastname is not null) from com.dtdc.domain.master.vendor.VendorDO vendor where  vendor.vendorCode=:vendorCode";
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.createQuery(hql);
			query.setString("vendorCode", vendCode);
			name = (String) query.uniqueResult();
		} catch (Exception e) {
			logger.error("CTBSApplicationMDBDAOImpl::getVendorName::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}

		return name;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getVendorId(String)
	 */
	@Override
	public Integer getVendorId(String vendCode) throws CGSystemException {
		Integer Id = null;
		String hql = "select vendor.vendorId from com.dtdc.domain.master.vendor.VendorDO vendor where  vendor.vendorCode=:vendorCode";
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.createQuery(hql);
			query.setString("vendorCode", vendCode);
			Id = (Integer) query.uniqueResult();
		} catch (Exception e) {
			logger.error("CTBSApplicationMDBDAOImpl::getVendorId::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return Id;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getVendor(Integer)
	 */
	@Override
	public VendorDO getVendor(Integer vendId) throws CGSystemException {
		VendorDO vendDo = null;
		String hql = "from com.dtdc.domain.master.vendor.VendorDO vendor where  vendor.vendorId=:vendorId";
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.createQuery(hql);
			query.setInteger("vendorId", vendId);
			vendDo = (VendorDO) query.uniqueResult();
		} catch (Exception e) {
			logger.error("CTBSApplicationMDBDAOImpl::getVendor::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return vendDo;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getFranchiseeCodeValuesForBranch(Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FranchiseeDO> getFranchiseeCodeValuesForBranch(Integer branchId)
	throws CGSystemException {
		List<FranchiseeDO> franchiseeDOs = null;
		Session session = null;
		// String hql =
		// "from com.dtdc.domain.master.franchisee.FranchiseeDO franchisee where franchisee.officeId = :branchId";
		String paramName = "branchId";
		// Object value = branchId;
		try {
			OfficeDO officeDO = getHibernateTemplate().get(OfficeDO.class,
					branchId);
			Object value = officeDO;
			franchiseeDOs = (List<FranchiseeDO>) getHibernateTemplate()
			.findByNamedQueryAndNamedParam("getFranchiseesByBranchID",
					paramName, value);
		} catch (Exception e) {
			logger.error("CTBSApplicationMDBDAOImpl::getFranchiseeCodeValuesForBranch::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return franchiseeDOs;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getDPCodeValuesForBranch(Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerDO> getDPCodeValuesForBranch(Integer branchId)
	throws CGSystemException {

		List<CustomerDO> customerDOs = null;
		String paramName = "officeId";

		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			// OfficeDO = session.createCriteria(OfficeDO.class, branchId);
			OfficeDO officeDO = getHibernateTemplate().get(OfficeDO.class,
					branchId);
			Object value = officeDO;
			customerDOs = (List<CustomerDO>) getHibernateTemplate()
			.findByNamedQueryAndNamedParam("getDirectPartiesForBranch",
					paramName, value);
		} catch (Exception e) {
			logger.error("CTBSApplicationMDBDAOImpl::getDPCodeValuesForBranch::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return customerDOs;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getCoLoaderByCode(String)
	 */
	@Override
	public CoLoaderDO getCoLoaderByCode(String coLoaderCode)
	throws CGSystemException {
		CoLoaderDO coLoader = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			coLoader = (CoLoaderDO) session.createCriteria(CoLoaderDO.class)
			.add(Restrictions.eq("loaderCode", coLoaderCode))
			.uniqueResult();
		} catch (Exception obj) {
			logger.error("CTBSApplicationMDBDAOImpl::getCoLoaderByCode::Exception occured:"
					+obj.getMessage());
			throw new CGSystemException(obj);
		} finally {
			session.flush();
			session.close();
		}
		return coLoader;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getOpsOfficeByCodeAndType(String, String)
	 */
	@Override
	public OpsOfficeDO getOpsOfficeByCodeAndType(String offCode, String opsType)
	throws CGSystemException {
		OpsOfficeDO opsOffice = null;
		Session session = null;
		try {
			session =getHibernateTemplate().getSessionFactory().openSession();
			opsOffice = (OpsOfficeDO) session.createCriteria(OpsOfficeDO.class)
			.add(Restrictions.eq("opsOfficeCode", offCode))
			.add(Restrictions.eq("opsOfficeType", opsType))
			.uniqueResult();
		} catch (Exception e) {
			logger.error("CTBSApplicationMDBDAOImpl::getOpsOfficeByCodeAndType::Exception occured:"
					+e.getMessage());
		} finally {
			session.flush();
			session.close();
		}
		return opsOffice;

	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getAirportDetails()
	 */
	@Override
	public List<AirportDO> getAirportDetails() throws CGSystemException {
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		List<AirportDO> airportList = hibernateTemplate
		.loadAll(AirportDO.class);
		return airportList;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getOpsOfficeById(Integer, String)
	 */
	@Override
	public OpsOfficeDO getOpsOfficeById(Integer officeId, String opsType)
	throws CGSystemException {
		OpsOfficeDO opsOffice = null;
		Session session = null;
		try {
			session =getHibernateTemplate().getSessionFactory().openSession();
			opsOffice = (OpsOfficeDO) session.createCriteria(OpsOfficeDO.class)
			.add(Restrictions.eq("officeId.officeId", officeId))
			.add(Restrictions.eq("opsOfficeType", opsType))
			.uniqueResult();
		} catch (Exception e) {
			logger.error("CTBSApplicationMDBDAOImpl::getOpsOfficeById::Exception occured:"
					+e.getMessage());
		} finally {
			session.flush();
			session.close();
		}
		return opsOffice;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getOfficesByOfficeType(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getOfficesByOfficeType(String officeType) {
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		List<OfficeDO> officeDOList = hibernateTemplate
		.findByNamedQueryAndNamedParam(
				CommonConstants.GET_OFFICES_BY_OFFICE_TYPE_QUERY,
				CommonConstants.GET_OFFICES_BY_OFFICE_TYPE_QUERY_PARAM,
				officeType);

		return officeDOList;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getRODetailsByOfficeCode(String)
	 */
	@Override
	public OfficeDO getRODetailsByOfficeCode(String officeCode) {
		OfficeDO regionalOfficeDO = null;
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		List<OfficeDO> officeDOList = hibernateTemplate
		.findByNamedQueryAndNamedParam(
				CommonConstants.GET_RO_DETAIL_BY_RO_CODE_QUERY,
				CommonConstants.GET_RO_DETAIL_BY_RO_CODE_QUERY_PARAM,
				officeCode);
		if (officeDOList != null && officeDOList.size() > 0) {
			regionalOfficeDO = officeDOList.get(0);
		}
		return regionalOfficeDO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getAllBOsUnderRO(Integer)
	 */
	@Override
	public List<OfficeDO> getAllBOsUnderRO(Integer roId) {
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		List<OfficeDO> officeDOList = hibernateTemplate
		.findByNamedQueryAndNamedParam(
				CommonConstants.GET_ALL_BO_BY_RO_ID_QUERY,
				CommonConstants.GET_ALL_BO_BY_RO_ID_QUERY_PARAM, roId);

		return officeDOList;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getAgentAddressByAgentId(Integer)
	 */
	@Override
	public AgentAddressDO getAgentAddressByAgentId(Integer agentId) {
		AgentAddressDO agentAddress = null;
		Session session = null;
		try {
			session =getHibernateTemplate().getSessionFactory().openSession();
			agentAddress = (AgentAddressDO) session
			.createCriteria(AgentAddressDO.class)
			.add(Restrictions.eq("agent.agentId", agentId))
			.uniqueResult();
		} catch (Exception e) {
			logger.error("CTBSApplicationMDBDAOImpl::getAgentAddressByAgentId::Exception occured:"
					+e.getMessage());
		} finally {
			session.flush();
			session.close();
		}
		return agentAddress;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#retrieveServiceType(String)
	 */
	@Override
	public List<ServiceDO> retrieveServiceType(String productId)
	throws CGBusinessException {
		String queryName = "retrieveServiceTypeList";
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		ProductDO productDO=new ProductDO();
		productDO.setProductId(Integer.parseInt(productId));
		List<ServiceDO> serviceTypeList = null;
		try {
			serviceTypeList = hibernateTemplate.findByNamedQueryAndNamedParam(queryName, BookingConstants.PRODUCT_DO, productDO);
			hibernateTemplate.flush();
		} catch (Exception obj) {
			logger.error("CTBSApplicationMDBDAOImpl::retrieveServiceType::Exception occured:"
					+obj.getMessage());
			throw new CGBusinessException(obj);
		}
		return serviceTypeList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtdc.ng.dao.booking.DTDCBookingDAO#retrieveProductType()
	 */
	@Override
	public List<ProductDO> retrieveProductType() throws CGBusinessException {
		String queryName = "retrieveProductTypeList";
		HibernateTemplate hibernateTemplate = getHibernateTemplate();

		List<ProductDO> productTypeList = null;
		try {
			productTypeList = hibernateTemplate.findByNamedQuery(queryName);
			hibernateTemplate.flush();
		} catch (Exception obj) {
			logger.error("CTBSApplicationMDBDAOImpl::retrieveProductType::Exception occured:"
					+obj.getMessage());
			throw new CGBusinessException(obj);
		}
		return productTypeList;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getFranchisees(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FranchiseeDO> getFranchisees(String value)
	throws CGSystemException {
		List<FranchiseeDO> franchisees = new ArrayList<FranchiseeDO>();
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			franchisees = (List<FranchiseeDO>) session
			.createCriteria(FranchiseeDO.class)
			.add(Restrictions.like("franchiseeCode", "%"+value+"%")).list();
		} catch (Exception obj) {
			logger.error("CTBSApplicationMDBDAOImpl::getFranchisees::Exception occured:"
					+obj.getMessage());
			throw new CGSystemException(obj);
		} finally {
			session.flush();
			session.close();
		}
		return franchisees;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getFranchiseeByFrCodeOrId(String, Integer)
	 */
	@Override
	public FranchiseeDO getFranchiseeByFrCodeOrId(String frCode, Integer franchiseeId)
	throws CGSystemException {
		FranchiseeDO franchiseeDO = new FranchiseeDO();
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		if (StringUtil.isEmpty(frCode)) {
			franchiseeDO = (FranchiseeDO) session
			.createCriteria(FranchiseeDO.class)
			.add(Restrictions.eq(
					DRSConstants.FRANCHISEE_ID, franchiseeId))
					.uniqueResult();
		} else {
			Query q = session
			.getNamedQuery(CommonConstants.GET_FRANCHISEE_BY_CODE);
			q.setString("frCode", frCode);
			franchiseeDO = (FranchiseeDO) q.uniqueResult();
		}
		return franchiseeDO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getModesBetweenOffices(Integer, Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ModeDO> getModesBetweenOffices(Integer orgOfficeId,
			Integer destOfficeId) {
		LOGGER.info("CTBSApplicationDAOImpl: getModesBetweenOffices(): START");

		String queryName = "getModesBetweenOffices";
		String[] params = { "originOfficeId", "destOfficeId" };
		Object[] values = { orgOfficeId, destOfficeId };

		List<ModeDO> modeDOList = getHibernateTemplate()
		.findByNamedQueryAndNamedParam(queryName, params, values);

		LOGGER.info("CTBSApplicationDAOImpl: getModesBetweenOffices(): END");
		return modeDOList;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getDocumentByIdOrType(Integer, String)
	 */
	@Override
	public DocumentDO getDocumentByIdOrType(Integer documentId,
			String documentType) throws CGSystemException {
		DocumentDO document = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			if (StringUtil.isEmpty(documentType)) {
				document = (DocumentDO) session
				.createCriteria(DocumentDO.class)
				.add(Restrictions.eq("documentId", documentId))
				.uniqueResult();
			} else {
				document = (DocumentDO) session
				.createCriteria(DocumentDO.class)
				.add(Restrictions.eq("documentType", documentType))
				.uniqueResult();
			}

		} catch (Exception e) {
			logger.error("CTBSApplicationMDBDAOImpl::getDocumentByIdOrType::Exception occured:"
					+e.getMessage());
		} finally {
			session.flush();
			session.close();
		}
		return document;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getStdHandlingByIdOrCode(Integer, String)
	 */
	@Override
	public StdHandlingInstDO getStdHandlingByIdOrCode(Integer stdHandlingId,
			String stdHandlingCode) throws CGSystemException {
		StdHandlingInstDO stdHandling = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			if (StringUtil.isEmpty(stdHandlingCode)) {
				stdHandling = (StdHandlingInstDO) session
				.createCriteria(StdHandlingInstDO.class)
				.add(Restrictions.eq("handleInstId", stdHandlingId))
				.uniqueResult();
			} else {
				stdHandling = (StdHandlingInstDO) session
				.createCriteria(StdHandlingInstDO.class)
				.add(Restrictions.eq("handleInstCode", stdHandlingCode))
				.uniqueResult();
			}

		} catch (Exception e) {
			logger.error("CTBSApplicationMDBDAOImpl::getStdHandlingByIdOrCode::Exception occured:"
					+e.getMessage());
		} finally {
			session.flush();
			session.close();
		}
		return stdHandling;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getIdProofDocsByIdOrCode(Integer, String)
	 */
	@Override
	public IdentityProofDocDO getIdProofDocsByIdOrCode(Integer idProofDocId,
			String idProofDocCode) throws CGSystemException {
		IdentityProofDocDO ifProofDocDO = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			if (StringUtils.isEmpty(idProofDocCode)) {
				ifProofDocDO = (IdentityProofDocDO) session
				.createCriteria(IdentityProofDocDO.class)
				.add(Restrictions
						.eq("identityProofDocId", idProofDocId))
						.uniqueResult();
			} else {
				ifProofDocDO = (IdentityProofDocDO) session
				.createCriteria(IdentityProofDocDO.class)
				.add(Restrictions.eq("identityProofDocCode",
						idProofDocCode)).uniqueResult();
			}
		} catch (Exception e) {
			logger.error("CTBSApplicationMDBDAOImpl::getIdProofDocsByIdOrCode::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		} finally {
			session.flush();
			session.close();
		}
		return ifProofDocDO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getConfigurabParamByParamName(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ConfigurableParamsDO getConfigurabParamByParamName(String paramName) {
		ConfigurableParamsDO configurableParam = null;
		String queryName = "getConfigurableParmByName";
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		try {
			List<ConfigurableParamsDO> configurableParamsDOList = hibernateTemplate
			.findByNamedQueryAndNamedParam(queryName, "paramName",
					paramName);
			if (configurableParamsDOList != null
					&& configurableParamsDOList.size() == 1) {
				configurableParam = configurableParamsDOList.get(0);
			}
		} catch (Exception obj) {
			logger.error("CTBSApplicationMDBDAOImpl::getConfigurabParamByParamName::Exception occured:"
					+obj.getMessage());
		}
		return configurableParam;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getExpenditureTypeByType(String)
	 */
	@Override
	public ExpenditureTypeDO getExpenditureTypeByType(String expendType)
	throws CGSystemException {
		LOGGER.debug("CTBSApplicationDAOImpl : getExpenditureTypeByType(): START");
		ExpenditureTypeDO expenditureType = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			expenditureType = (ExpenditureTypeDO) session
			.createCriteria(ExpenditureTypeDO.class)
			.add(Restrictions.eq("expndType", expendType))
			.uniqueResult();
		} catch (Exception e) {
			logger.error("CTBSApplicationMDBDAOImpl::getExpenditureTypeByType::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		} finally {
			session.flush();
			session.close();
		}
		LOGGER.debug("CTBSApplicationDAOImpl : getExpenditureTypeByType(): END");
		return expenditureType;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getManifestTypeIds(List)
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getManifestTypeIds(List<String> manifestCodes)
	throws CGSystemException {
		Session session = null;
		List<ManifestTypeDO> manifestTypes = null;
		List<Integer> manifestTypeIds = null;
		try {
			manifestTypeIds = new ArrayList<Integer>();
			session = getHibernateTemplate().getSessionFactory().openSession();
			manifestTypes = (List<ManifestTypeDO>) session
			.createCriteria(ManifestTypeDO.class)
			.add(Restrictions.in("mnfstCode", manifestCodes)).list();
			if (manifestTypes != null && manifestTypes.size() > 0) {
				for (ManifestTypeDO manifestType : manifestTypes) {
					manifestTypeIds.add(manifestType.getMnfstTypeId());
				}
			}
		} catch (Exception ex) {
			logger.error("CTBSApplicationMDBDAOImpl::getManifestTypeIds::Exception occured:"
					+ex.getMessage());
			throw new CGSystemException("  " + ex.getMessage(), ex);
		} finally {
			session.flush();
			session.close();
		}
		return manifestTypeIds;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getServiceMappings(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ServiceMappingDO getServiceMappings(int serviceId)
	throws CGBusinessException {
		List<ServiceMappingDO> serviceMappingList = null;
		ServiceMappingDO serviceMappingDO = null;
		if (serviceId > 0) {
			String queryName = "retrieveServiceMapping";
			HibernateTemplate hibernateTemplate = getHibernateTemplate();
			try {
				serviceMappingList = hibernateTemplate
				.findByNamedQueryAndNamedParam(queryName,
						BookingConstants.SERVICE_ID, serviceId);
				if (serviceMappingList != null && serviceMappingList.size() > 0) {
					serviceMappingDO = serviceMappingList.get(0);
				}
				hibernateTemplate.flush();
			} catch (Exception obj) {
				logger.error("CTBSApplicationMDBDAOImpl::getServiceMappings::Exception occured:"
						+obj.getMessage());
				throw new CGBusinessException(obj);
			}
		}
		return serviceMappingDO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getIdProofDocsByCode(String[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<IdentityProofDocDO> getIdProofDocsByCode(String... idProofCode)
	throws CGSystemException {
		List<IdentityProofDocDO> idproofdos = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Criteria criteria = session
			.createCriteria(IdentityProofDocDO.class);
			/*
			 * ProjectionList pl = Projections.projectionList();
			 * pl.add(Projections.property("identityProofDocId"));
			 * pl.add(Projections.property("identityProofDocCode"));
			 * criteria.setProjection(pl);
			 */
			criteria.add(Restrictions.in("identityProofDocCode", idProofCode));
			idproofdos = (List<IdentityProofDocDO>) criteria.list();
		} catch (Exception e) {
			logger.error("CTBSApplicationMDBDAOImpl::getIdProofDocsByCode::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		} finally {
			session.flush();
			session.close();
		}
		return idproofdos;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getServiceByIdOrCode(Integer, String)
	 */
	@Override
	public ServiceDO getServiceByIdOrCode(Integer serviceId, String serviceCode)
	throws CGSystemException {
		ServiceDO service = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			if (StringUtils.isEmpty(serviceCode)) {
				service = (ServiceDO) session.createCriteria(ServiceDO.class)
				.add(Restrictions.eq("serviceId", serviceId))
				.uniqueResult();
			} else {
				service = (ServiceDO) session.createCriteria(ServiceDO.class)
				.add(Restrictions.eq("serviceCode", serviceCode))
				.uniqueResult();
			}

		} catch (Exception e) {
			logger.error("CTBSApplicationMDBDAOImpl::getServiceByIdOrCode::Exception occured:"
					+e.getMessage());
		} finally {
			session.flush();
			session.close();
		}
		return service;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getModeByCode(String)
	 */
	@Override
	public ModeDO getModeByCode(String modeCode) throws CGSystemException {
		ModeDO mode = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			mode = (ModeDO) session.createCriteria(ModeDO.class)
			.add(Restrictions.eq("modeCode", modeCode)).uniqueResult();
		} catch (Exception e) {
			logger.error("CTBSApplicationMDBDAOImpl::getModeByCode::Exception occured:"
					+e.getMessage());
		} finally {
			session.flush();
			session.close();
		}
		return mode;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getUserByUserCode(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UserDO getUserByUserCode(String userCode) throws CGSystemException {
		List<UserDO> userDOList = new ArrayList<UserDO>();
		UserDO userDO = null;
		Session session = null;
		Criteria criteria=null;
		try{
			session = getHibernateTemplate().getSessionFactory().openSession();
			criteria = session.createCriteria(UserDO.class);
			criteria.add(Restrictions.eq("userCode", userCode));
			userDOList = criteria.list();
			if(userDOList!=null && userDOList.size()>0) {
				userDO = userDOList.get(0);
			}
		}
		catch(Exception e){
			logger.error("CTBSApplicationMDBDAOImpl::getUserByUserCode::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		}
		finally{
			if(session!=null){
				session.flush();
				session.close();				
			}
		}
		return userDO;
	}	

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.CTBSApplicationMDBDAO#getDocumentByDocumentCode(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public DocumentDO getDocumentByDocumentCode(String documentCode) throws CGSystemException {
		List<DocumentDO> documentDOList = new ArrayList<DocumentDO>();
		DocumentDO documentDO = null;
		Session session = null;
		Criteria criteria=null;
		try{
			session = getHibernateTemplate().getSessionFactory().openSession();
			criteria = session.createCriteria(DocumentDO.class);
			criteria.add(Restrictions.eq("documentCode", documentCode));
			documentDOList = criteria.list();
			if(documentDOList!=null && documentDOList.size()>0) {
				documentDO = documentDOList.get(0);
			}
		}
		catch(Exception e){
			logger.error("CTBSApplicationMDBDAOImpl::getDocumentByDocumentCode::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		}
		finally{
			if(session!=null){
				session.flush();
				session.close();				
			}
		}
		return documentDO;
	}
}
