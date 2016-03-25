package com.ff.admin.heldup.converter;

import java.util.Calendar;

import org.apache.commons.lang.StringUtils;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.heldup.service.HeldUpService;
import com.ff.domain.heldup.HeldUpDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.serviceOffering.ReasonDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.domain.umc.UserDO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.to.heldup.HeldUpTO;
import com.ff.to.serviceofferings.ReasonTO;
import com.ff.tracking.ProcessTO;
import com.ff.umc.UserTO;

// TODO: Auto-generated Javadoc
/**
 * The Class HeldUpConverter.
 *
 * @author narmdr
 */
public class HeldUpConverter {

	/** The held up service. */
	private static HeldUpService heldUpService;

	/**
	 * Sets the held up service.
	 *
	 * @param heldUpService the heldUpService to set
	 */
	public static void setHeldUpService(final HeldUpService heldUpService) {
		HeldUpConverter.heldUpService = heldUpService;
	}
		
	/**
	 * Held up transfer converter.
	 *
	 * @param heldUpDO the held up do
	 * @return the held up to
	 * @throws CGBusinessException the cG business exception
	 */
	public static HeldUpTO heldUpTransferConverter(final HeldUpDO heldUpDO)
			throws CGBusinessException {
		final HeldUpTO heldUpTO = new HeldUpTO();		
		final OfficeTO officeTO = new OfficeTO();
		final EmployeeTO employeeTO = new EmployeeTO();
		final ReasonTO reasonTO = new ReasonTO();
		final UserTO userTO = new UserTO();
		
		heldUpTO.setOfficeTO(officeTO);
		heldUpTO.setReasonTO(reasonTO);
		heldUpTO.setEmployeeTO(employeeTO);
		heldUpTO.setUserTO(userTO);
		
		CGObjectConverter.createToFromDomain(heldUpDO, heldUpTO);
		if(heldUpDO.getOfficeDO()!=null){
			CGObjectConverter.createToFromDomain(heldUpDO.getOfficeDO(), officeTO);
			officeTO.setBuildingName(officeTO.getOfficeCode()
					+ CommonConstants.HYPHEN + officeTO.getOfficeName());
		}
		if(heldUpDO.getEmployeeDO()!=null){
			CGObjectConverter.createToFromDomain(heldUpDO.getEmployeeDO(), employeeTO);
			//employeeTO.setValue(heldUpDO.getEmployeeDO().getEmployeeId());
		}
		if (heldUpDO.getReasonDO() != null) {
			CGObjectConverter.createToFromDomain(heldUpDO.getReasonDO(), reasonTO);
			reasonTO.setReasonName(reasonTO.getReasonId()
					+ CommonConstants.TILD + reasonTO.getReasonCode()
					+ CommonConstants.TILD + reasonTO.getReasonTypeDesc());
		}
		if(heldUpDO.getUserDO()!=null){
			CGObjectConverter.createToFromDomain(heldUpDO.getUserDO(), userTO);			
		}
		if(heldUpDO.getHeldUpDate()!=null){
			heldUpTO.setHeldUpDateTime(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(heldUpDO.getHeldUpDate()));
		}
		return heldUpTO;
	}

	/**
	 * Held up domain converter.
	 *
	 * @param heldUpTO the held up to
	 * @return the held up do
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public static HeldUpDO heldUpDomainConverter(final HeldUpTO heldUpTO)
			throws CGBusinessException, CGSystemException {
		final HeldUpDO heldUpDO = new HeldUpDO();

		if (StringUtils.isBlank(heldUpTO.getHeldUpNumber())) {
			generateAndSetHeldUpNumber(heldUpTO);
		}
		if (StringUtils.isBlank(heldUpTO.getProcessNumber())) {
			generateAndSetProcessNumber(heldUpTO);
		}
		
		CGObjectConverter.createDomainFromTo(heldUpTO, heldUpDO);
		if (heldUpTO.getOfficeTO() != null
				&& !StringUtil.isEmptyInteger(heldUpTO.getOfficeTO()
						.getOfficeId())) {
			final OfficeDO officeDO = new OfficeDO();
			officeDO.setOfficeId(heldUpTO.getOfficeTO().getOfficeId());
			heldUpDO.setOfficeDO(officeDO);
		}
		if (heldUpTO.getEmployeeTO() != null
				&& !StringUtil.isEmptyInteger(heldUpTO.getEmployeeTO()
						.getEmployeeId())) {
			final EmployeeDO employeeDO = new EmployeeDO();
			employeeDO.setEmployeeId(heldUpTO.getEmployeeTO().getEmployeeId());
			heldUpDO.setEmployeeDO(employeeDO);
		}
		if (heldUpTO.getReasonTO() != null
				&& !StringUtil.isEmptyInteger(heldUpTO.getReasonTO()
						.getReasonId())) {
			final ReasonDO reasonDO = new ReasonDO();
			reasonDO.setReasonId(heldUpTO.getReasonTO().getReasonId());
			heldUpDO.setReasonDO(reasonDO);
		}
		if (heldUpTO.getUserTO() != null
				&& !StringUtil.isEmptyInteger(heldUpTO.getUserTO().getUserId())) {
			final UserDO userDO = new UserDO();
			userDO.setUserId(heldUpTO.getUserTO().getUserId());
			heldUpDO.setUserDO(userDO);
		}
		if (StringUtils.isNotBlank(heldUpTO.getHeldUpDateTime())) {
			heldUpDO.setHeldUpDate(DateUtil
					.parseStringDateToDDMMYYYYHHMMFormat(heldUpTO
							.getHeldUpDateTime()));
		}
		
		ProcessTO processTO = new ProcessTO();
		processTO.setProcessCode(CommonConstants.PROCESS_HELD_UP);
		processTO = heldUpService.getProcess(processTO);
		if(processTO!=null){
			ProcessDO processDO = new ProcessDO();
			processDO.setProcessId(processTO.getProcessId());
			heldUpDO.setProcessDO(processDO);
		}
				
		if(!StringUtil.isEmptyInteger(heldUpDO.getHeldUpId())){
			setUpdateFlag4DBSync(heldUpDO);
		}else{
			setSaveFlag4DBSync(heldUpDO);
		}

		heldUpDO.setCreatedDate(Calendar.getInstance().getTime());
		heldUpDO.setUpdatedDate(Calendar.getInstance().getTime());
		
		return heldUpDO;
	}
	
	private static void generateAndSetProcessNumber(HeldUpTO heldUpTO)
			throws CGBusinessException, CGSystemException {
		ProcessTO processTO = new ProcessTO();
		OfficeTO officeTO = new OfficeTO();
		if (heldUpTO.getOfficeTO() != null
				&& !StringUtil.isEmptyInteger(heldUpTO.getOfficeTO()
						.getOfficeId())) {
			officeTO.setOfficeId(heldUpTO.getOfficeTO().getOfficeId());
		}
		processTO.setProcessCode(CommonConstants.PROCESS_HELD_UP);
		String processNumber = heldUpService.createProcessNumber(processTO, officeTO);
		heldUpTO.setProcessNumber(processNumber);
	}

	/**
	 * Generate and set held up number.
	 *
	 * @param heldUpTO the held up to
	 * @throws CGBusinessException the cG business exception
	 */
	private static void generateAndSetHeldUpNumber(final HeldUpTO heldUpTO)
			throws CGBusinessException {
		// Help up Number format would be office code + DDMMYY+HHMMSS = 16 digit
		final StringBuilder heldUpNumber = new StringBuilder(20);
		if (heldUpTO.getOfficeTO() != null) {
			heldUpNumber.append(heldUpTO.getOfficeTO().getOfficeCode()).append(
					StringUtil.generateDDMMYYHHMMSSIn24HrRandomNumber());
		}
		heldUpTO.setHeldUpNumber(heldUpNumber.toString());
	}
	
	/**
	 * Sets the update flag4 db sync.
	 *
	 * @param cgBaseDO the new update flag4 db sync
	 */
	private static void setUpdateFlag4DBSync(CGBaseDO cgBaseDO) {
		cgBaseDO.setDtUpdateToCentral(CommonConstants.YES);
		cgBaseDO.setDtToCentral(CommonConstants.NO);
	}
	
	/**
	 * Sets the save flag4 db sync.
	 *
	 * @param cgBaseDO the new save flag4 db sync
	 */
	private static void setSaveFlag4DBSync(CGBaseDO cgBaseDO) {
		cgBaseDO.setDtToCentral(CommonConstants.NO);
	}
	
}
