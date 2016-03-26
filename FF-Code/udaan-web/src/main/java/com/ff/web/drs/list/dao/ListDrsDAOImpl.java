/**
 * 
 */
package com.ff.web.drs.list.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.ff.domain.organization.EmployeeDO;
import com.ff.to.drs.AbstractDeliveryTO;
import com.ff.to.drs.list.ListDrsHeaderTO;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.web.drs.common.constants.DrsCommonConstants;

/**
 * @author mohammes
 *
 */
public class ListDrsDAOImpl extends CGBaseDAO implements ListDrsDAO {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ListDrsDAOImpl.class);
	
	/**
	 * getAllDeliveryEmployees :: load all employees/FieldStaff for which DRS has generated by office
	 * @param delvTo
	 * @return
	 * @throws CGSystemException
	 */
	@Override
	public List<EmployeeDO> getAllDeliveryEmployees(AbstractDeliveryTO delvTo) throws CGSystemException {
		List<EmployeeDO> delivrdEmpList=null;
		String params[]={DrsCommonConstants.QRY_PARAM_OFFICEID,DrsCommonConstants.QRY_PARAM_DISCARD,UniversalDeliveryContants.QRY_PARAM_FROM_DATE,UniversalDeliveryContants.QRY_PARAM_TO_DATE};
		Object value[] = {delvTo.getLoginOfficeId(),UniversalDeliveryContants.DRS_DISCARDED_NO,DateUtil.getCurrentDateWithoutTime(),DateUtil.appendLastHourToDate(DateUtil.getCurrentDate())};
		delivrdEmpList=getHibernateTemplate().findByNamedQueryAndNamedParam(DrsCommonConstants.QRY_DLV_FIELD_STAFF,params, value);
		return delivrdEmpList;
	}
	
	@Override
	public List<?> getDrsForDtlsByDate(AbstractDeliveryTO delvTo) throws CGSystemException {
		List<?> delivrdEmpList=null;
		String params[]={DrsCommonConstants.QRY_PARAM_OFFICEID,DrsCommonConstants.QRY_PARAM_DISCARD,UniversalDeliveryContants.QRY_PARAM_FROM_DATE,UniversalDeliveryContants.QRY_PARAM_TO_DATE};
		Object value[] = {delvTo.getLoginOfficeId(),UniversalDeliveryContants.DRS_DISCARDED_NO,DateUtil.getCurrentDateWithoutTime(),DateUtil.appendLastHourToDate(DateUtil.getCurrentDate())};
		delivrdEmpList=getHibernateTemplate().findByNamedQueryAndNamedParam(DrsCommonConstants.QRY_GET_DRS_FOR_DTLS_BY_DATE,params, value);
		return delivrdEmpList;
	}
	
	/**
	 * Gets the all drs by office and employee.
	 *
	 * @param drsTo the drs to
	 * @return the all drs by office and employee
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public List<String> getAllDrsByOfficeAndEmployee(ListDrsHeaderTO drsTo)throws CGBusinessException,CGSystemException{
		LOGGER.debug("ListDrsDAOImpl :: getAllDrsByOfficeAndEmployee:: ##START##");
		List<String> delivrdEmpList=null;
		String params[]={DrsCommonConstants.QRY_PARAM_OFFICEID,DrsCommonConstants.QRY_PARAM_DISCARD, UniversalDeliveryContants.QRY_PARAM_EMPLOYEE_ID,DrsCommonConstants.QRY_PARAM_LOAD_NUMBER,UniversalDeliveryContants.QRY_PARAM_FROM_DATE ,UniversalDeliveryContants.QRY_PARAM_TO_DATE};
		Object value[] = {drsTo.getLoginOfficeId(),UniversalDeliveryContants.DRS_DISCARDED_NO,drsTo.getDrsPartyId(), drsTo.getLoadNumber(),DateUtil.getCurrentDateWithoutTime(),DateUtil.appendLastHourToDate(DateUtil.getCurrentDate())};
		delivrdEmpList=getHibernateTemplate().findByNamedQueryAndNamedParam(drsTo.getQueryName(),params, value);
		LOGGER.debug("ListDrsDAOImpl :: getAllDrsByOfficeAndEmployee:: ##END##");
		return delivrdEmpList;
	}
	
}
