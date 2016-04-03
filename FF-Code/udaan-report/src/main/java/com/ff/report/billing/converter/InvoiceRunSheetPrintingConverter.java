/**
 * 
 */
package com.ff.report.billing.converter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.business.CustomerTO;
import com.ff.domain.billing.BillDO;
import com.ff.domain.billing.InvoiceRunSheetDetailDO;
import com.ff.domain.billing.InvoiceRunSheetHeaderDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.organization.EmployeeTO;
import com.ff.to.billing.BillTO;
import com.ff.to.billing.InvoiceRunSheetDetailsTO;
import com.ff.to.billing.InvoiceRunSheetTO;

// TODO: Auto-generated Javadoc
/**
 * The Class InvoiceRunSheetPrintingConverter.
 *
 * @author abarudwa
 */
public class InvoiceRunSheetPrintingConverter {
	
	/**
	 * Invoice run sheet domain converter.
	 *
	 * @param invoiceRunSheetTO the invoice run sheet to
	 * @return the invoice run sheet header do
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(InvoiceRunSheetPrintingConverter.class);
		
	
	public static InvoiceRunSheetHeaderDO invoiceRunSheetDomainConverter(InvoiceRunSheetTO invoiceRunSheetTO)
			throws CGBusinessException,CGSystemException{
		
		LOGGER.debug("InvoiceRunSheetPrintingConverter::invoiceRunSheetDomainConverter::START----->");
		InvoiceRunSheetHeaderDO invoiceRunSheetHeaderDO = new InvoiceRunSheetHeaderDO();
		
		if(!StringUtil.isEmptyInteger(invoiceRunSheetTO.getInvoiceRunSheetId())){
			invoiceRunSheetHeaderDO.setInvoiceRunSheetId(invoiceRunSheetTO.getInvoiceRunSheetId());
		}
		if(!StringUtil.isStringEmpty(invoiceRunSheetTO.getInvoiceRunSheetNumber())){
			invoiceRunSheetHeaderDO.setInvoiceRunSheetNumber(invoiceRunSheetTO.getInvoiceRunSheetNumber());
		}
		if(!StringUtil.isStringEmpty(invoiceRunSheetTO.getStartDateStr())){
			invoiceRunSheetHeaderDO.setStartDate(DateUtil.stringToDDMMYYYYFormat(invoiceRunSheetTO.getStartDateStr()));
		}
		
		if(!StringUtil.isStringEmpty(invoiceRunSheetTO.getEndDateStr())){
			invoiceRunSheetHeaderDO.setEndDate(DateUtil.stringToDDMMYYYYFormat(invoiceRunSheetTO.getEndDateStr()));
		}
		
		if(!StringUtil.isNull(invoiceRunSheetTO.getPickUpBoy().getEmployeeId())){
			EmployeeDO employeeDO = new EmployeeDO();
			employeeDO.setEmployeeId(invoiceRunSheetTO.getPickUpBoy().getEmployeeId());
			invoiceRunSheetHeaderDO.setPickUpBoyEmployeeDO(employeeDO);
		}
		if(!StringUtil.isNull(invoiceRunSheetTO.getLoginOfficeId())){
			OfficeDO officeDO = new OfficeDO();
			officeDO.setOfficeId(invoiceRunSheetTO.getLoginOfficeId());
			invoiceRunSheetHeaderDO.setOfficeDO(officeDO);
		}
		if(!StringUtil.isNull(invoiceRunSheetTO.getCreatedBy())){
			invoiceRunSheetHeaderDO.setCreatedBy(invoiceRunSheetTO.getCreatedBy());
		}
		if(!StringUtil.isNull(invoiceRunSheetTO.getCreatedDateStr())){
			invoiceRunSheetHeaderDO.setCreatedDate(DateUtil
					.stringToDDMMYYYYFormat(invoiceRunSheetTO.getCreatedDateStr()));
		}
		if(invoiceRunSheetTO.getSaveOrUpdate()=="update"){
			if(!StringUtil.isNull(invoiceRunSheetTO.getUpdatedBy())){
				invoiceRunSheetHeaderDO.setUpdatedBy(invoiceRunSheetTO.getUpdatedBy());
			}
			if(!StringUtil.isNull(invoiceRunSheetTO.getUpdatedDateStr())){
				invoiceRunSheetHeaderDO.setUpdatedDate(DateUtil
						.stringToDDMMYYYYFormat(invoiceRunSheetTO.getUpdatedDateStr()));
			}
		}
		
		Set<InvoiceRunSheetDetailDO> invoiceRunSheetDetailDOsSet = new HashSet<>();
		for(InvoiceRunSheetDetailsTO invoiceRunSheetDetailsTO : invoiceRunSheetTO.getInvoiceRunSheetDetailsList()){
			InvoiceRunSheetDetailDO invoiceRunSheetDetailDO = invoiceRunSheetDetailDOConverter(invoiceRunSheetDetailsTO,invoiceRunSheetTO);
			invoiceRunSheetDetailDO.setInvoiceRunSheetHeaderDO(invoiceRunSheetHeaderDO);
			invoiceRunSheetDetailDOsSet.add(invoiceRunSheetDetailDO);
		}
		invoiceRunSheetHeaderDO.setInvoiceRunSheetDetailDOs(invoiceRunSheetDetailDOsSet);
		LOGGER.debug("InvoiceRunSheetPrintingConverter::invoiceRunSheetDomainConverter::END----->");
		return invoiceRunSheetHeaderDO;
	}
	
	/**
	 * Invoice run sheet detail do converter.
	 *
	 * @param invoiceRunSheetDetailsTO the invoice run sheet details to
	 * @return the invoice run sheet detail do
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public static InvoiceRunSheetDetailDO invoiceRunSheetDetailDOConverter(InvoiceRunSheetDetailsTO invoiceRunSheetDetailsTO,InvoiceRunSheetTO invoiceRunSheetTO)
			throws CGBusinessException,CGSystemException{
		
		LOGGER.debug("InvoiceRunSheetPrintingConverter::invoiceRunSheetDetailDOConverter::START----->");
		InvoiceRunSheetDetailDO invoiceRunSheetDetailDO = new InvoiceRunSheetDetailDO();
		if(!StringUtil.isNull(invoiceRunSheetDetailsTO.getCustomerTO().getCustomerId())){
			CustomerDO customerDO = new CustomerDO();
			customerDO.setCustomerId(invoiceRunSheetDetailsTO.getCustomerTO().getCustomerId());
			invoiceRunSheetDetailDO.setCustomerDO(customerDO);
		}
		if(!StringUtil.isNull(invoiceRunSheetDetailsTO.getShipToCode())){
			invoiceRunSheetDetailDO.setShipToCode(invoiceRunSheetDetailsTO.getShipToCode());
		}
		
/*		if(!StringUtil.isNull(invoiceRunSheetDetailsTO.getInvoiceBillTO().getShipToCode())){
			invoiceRunSheetDetailDO.setShipToCode(invoiceRunSheetDetailsTO.getInvoiceBillTO().getShipToCode());
		}*/
		
		if(!StringUtil.isNull(invoiceRunSheetDetailsTO.getInvoiceBillTO().getInvoiceId())){
			BillDO billDO = new BillDO();
			billDO.setInvoiceId(invoiceRunSheetDetailsTO.getInvoiceBillTO().getInvoiceId());
			invoiceRunSheetDetailDO.setBillDO(billDO);
		}
		if(!StringUtil.isNull(invoiceRunSheetDetailsTO.getStatus()) && !StringUtil.isStringEmpty(invoiceRunSheetDetailsTO.getStatus())){
			invoiceRunSheetDetailDO.setStatus(invoiceRunSheetDetailsTO.getStatus());
		}
		if(!StringUtil.isNull(invoiceRunSheetDetailsTO.getReceiverName())){
			invoiceRunSheetDetailDO.setReceiverName(invoiceRunSheetDetailsTO.getReceiverName());
		}
		
		if(invoiceRunSheetDetailsTO.getSaveOrUpdate()=="update"){
			/*if(!StringUtil.isNull(invoiceRunSheetDetailsTO.getCreatedBy())){
				invoiceRunSheetDetailDO.setCreatedBy(invoiceRunSheetDetailsTO.getCreatedBy());
			}
			if(!StringUtil.isNull(invoiceRunSheetDetailsTO.getCreatedDateStr())){
				invoiceRunSheetDetailDO.setCreatedDate(DateUtil
						.stringToDDMMYYYYFormat(invoiceRunSheetDetailsTO.getCreatedDateStr()));
			}*/
		
			if(!StringUtil.isNull(invoiceRunSheetDetailsTO.getUpdatedBy())){
				invoiceRunSheetDetailDO.setUpdatedBy(invoiceRunSheetDetailsTO.getUpdatedBy());
			}
			if(!StringUtil.isNull(invoiceRunSheetDetailsTO.getUpdatedDateStr())){
				invoiceRunSheetDetailDO.setUpdatedDate(DateUtil
						.stringToDDMMYYYYFormat(invoiceRunSheetDetailsTO.getUpdatedDateStr()));
			}
		}
		else{
			if(!StringUtil.isNull(invoiceRunSheetTO.getCreatedBy())){
				invoiceRunSheetDetailDO.setCreatedBy(invoiceRunSheetTO.getCreatedBy());
			}
			if(!StringUtil.isNull(invoiceRunSheetTO.getCreatedDateStr())){
				invoiceRunSheetDetailDO.setCreatedDate(DateUtil
						.stringToDDMMYYYYFormat(invoiceRunSheetTO.getCreatedDateStr()));
			}
		}
		
		if(!StringUtil.isNull(invoiceRunSheetDetailsTO.getInvoiceRunSheetId())){
			InvoiceRunSheetHeaderDO invoiceRunSheetHeaderDO = new InvoiceRunSheetHeaderDO();
			invoiceRunSheetHeaderDO.setInvoiceRunSheetId(invoiceRunSheetDetailsTO.getInvoiceRunSheetId());
			invoiceRunSheetDetailDO.setInvoiceRunSheetHeaderDO(invoiceRunSheetHeaderDO);
		}
		if(!StringUtil.isNull(invoiceRunSheetDetailsTO.getInvoiceRunSheetDetailId())){
			invoiceRunSheetDetailDO.setInvoiceRunSheetDetailId(invoiceRunSheetDetailsTO.getInvoiceRunSheetDetailId());
		}
		LOGGER.debug("InvoiceRunSheetPrintingConverter::invoiceRunSheetDetailDOConverter::END----->");
		return invoiceRunSheetDetailDO;
		
	}
	
	/**
	 * Invoice runsheet to converter.
	 *
	 * @param invoiceRunSheetHeaderDO the invoice run sheet header do
	 * @return the invoice run sheet to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public static InvoiceRunSheetTO invoiceRunsheetTOConverter(InvoiceRunSheetHeaderDO invoiceRunSheetHeaderDO)
			throws CGBusinessException,CGSystemException{
		
		LOGGER.debug("InvoiceRunSheetPrintingConverter::invoiceRunsheetTOConverter::START----->");
		InvoiceRunSheetTO invoiceRunSheetTO = new InvoiceRunSheetTO();
		EmployeeTO employeeTO = new EmployeeTO();
		invoiceRunSheetTO.setPickUpBoy(employeeTO);
		
		CGObjectConverter.createToFromDomain(invoiceRunSheetHeaderDO, invoiceRunSheetTO);
		if(invoiceRunSheetHeaderDO.getPickUpBoyEmployeeDO()!=null){
			CGObjectConverter.createToFromDomain(invoiceRunSheetHeaderDO.getPickUpBoyEmployeeDO(), employeeTO);
		}
		if(invoiceRunSheetHeaderDO.getStartDate()!=null){
			invoiceRunSheetTO.setStartDateStr(DateUtil.getDDMMYYYYDateString(invoiceRunSheetHeaderDO.getStartDate()));
		}
		if(invoiceRunSheetHeaderDO.getEndDate()!=null){
			invoiceRunSheetTO.setEndDateStr(DateUtil.getDDMMYYYYDateString(invoiceRunSheetHeaderDO.getEndDate()));
		}
		Set<InvoiceRunSheetDetailDO> invoiceRunSheetDetailDOs = invoiceRunSheetHeaderDO.getInvoiceRunSheetDetailDOs();
		if(!StringUtil.isEmptyColletion(invoiceRunSheetDetailDOs)){
			ArrayList<InvoiceRunSheetDetailsTO> invoiceRunSheetDetailsTOs = new ArrayList<>();
			for(InvoiceRunSheetDetailDO invoiceRunSheetDetailDO : invoiceRunSheetDetailDOs){
				InvoiceRunSheetDetailsTO invoiceRunSheetDetailsTO = invoiceRunSheetDetailsTOConverter(invoiceRunSheetDetailDO);
				invoiceRunSheetDetailsTOs.add(invoiceRunSheetDetailsTO);
			}
			Collections.sort(invoiceRunSheetDetailsTOs);
			invoiceRunSheetTO.setInvoiceRunSheetDetailsList(invoiceRunSheetDetailsTOs);
		}
		LOGGER.debug("InvoiceRunSheetPrintingConverter::invoiceRunsheetTOConverter::END----->");
		return invoiceRunSheetTO;
	}

	/**
	 * Invoice run sheet details to converter.
	 *
	 * @param invoiceRunSheetDetailDO the invoice run sheet detail do
	 * @return the invoice run sheet details to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private static InvoiceRunSheetDetailsTO invoiceRunSheetDetailsTOConverter(InvoiceRunSheetDetailDO invoiceRunSheetDetailDO)
			throws CGBusinessException,CGSystemException{
		
		LOGGER.debug("InvoiceRunSheetPrintingConverter::invoiceRunSheetDetailsTOConverter::START----->");
		InvoiceRunSheetDetailsTO invoiceRunSheetDetailsTO = new InvoiceRunSheetDetailsTO();
		
		CustomerTO customerTO = new CustomerTO();
		BillTO billTO = new BillTO();
		invoiceRunSheetDetailsTO.setCustomerTO(customerTO);
		invoiceRunSheetDetailsTO.setInvoiceBillTO(billTO);
		CGObjectConverter.createToFromDomain(invoiceRunSheetDetailDO, invoiceRunSheetDetailsTO);
		if(invoiceRunSheetDetailDO.getCustomerDO()!=null){
			CGObjectConverter.createToFromDomain(invoiceRunSheetDetailDO.getCustomerDO(), customerTO);
		}
		if(invoiceRunSheetDetailDO.getBillDO()!=null){
			CGObjectConverter.createToFromDomain(invoiceRunSheetDetailDO.getBillDO(), billTO);
		}
		LOGGER.debug("InvoiceRunSheetPrintingConverter::invoiceRunSheetDetailsTOConverter::END----->");
		return invoiceRunSheetDetailsTO;
	}

}
