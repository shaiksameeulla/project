package com.ff.admin.complaints.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.admin.complaints.constants.ComplaintsCommonConstants;
import com.ff.admin.leads.constants.LeadCommonConstants;
import com.ff.domain.umc.EmployeeUserDO;

public class ServiceRequestForConsignmentDAOImpl extends CGBaseDAO implements
		ServiceRequestForConsignmentDAO {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ServiceRequestForConsignmentDAOImpl.class);

	@Override
	public List<EmployeeUserDO> getBackLineEmpList(Integer officeId,
			String designation) throws CGSystemException {
		LOGGER.debug("ServiceRequestForConsignmentDAOImpl :: getBackLineEmpList() :: Start --------> ::::::");
		List<EmployeeUserDO> employeeUserDOList = null;
		try{
			employeeUserDOList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							ComplaintsCommonConstants.QRY_GET_EMPLOYEES_DTLS_BY_DEPARTMENT_TYPE_AND_EMP_DESIGN,
							new String[] { ComplaintsCommonConstants.DEPARTMENT_NAME,ComplaintsCommonConstants.DESIGNATION,ComplaintsCommonConstants.OFFICEID  },
							new Object[] { ComplaintsCommonConstants.DEPARTMENT_NAME_TYPE,designation,officeId });
			
			}catch(Exception e){
			LOGGER.error("Exception Occured in::ServiceRequestForConsignmentDAOImpl::getBackLineEmpList() :: "
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("ServiceRequestForConsignmentDAOImpl :: getBackLineEmpList() :: End --------> ::::::");
		return employeeUserDOList;
	}

}
