package com.ff.universe.billing.converter;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.billing.BillDO;
import com.ff.organization.OfficeTO;
import com.ff.to.billing.BillTO;
import com.ff.to.billing.FinancialProductTO;

// TODO: Auto-generated Javadoc
/**
 * The Class BillingUniversalConverter.
 * 
 * @author narmdr
 */
public class BillingUniversalConverter {

	/**
	 * Convert bill d os to bill t os.
	 *
	 * @param billDOs the bill d os
	 * @return the list
	 * @throws CGBusinessException the cG business exception
	 */
	public static List<BillTO> convertBillDOsToBillTOs(List<BillDO> billDOs)
			throws CGBusinessException {
		List<BillTO> billTOs = null;
		if (!StringUtil.isEmptyColletion(billDOs)) {
			billTOs = new ArrayList<>(billDOs.size());

			for (BillDO billDO : billDOs) {
				BillTO billTO = convertBillDOToBillTO(billDO);
				billTOs.add(billTO);
			}
		}
		return billTOs;
	}

	/**
	 * Convert bill do to bill to.
	 *
	 * @param billDO the bill do
	 * @return the bill to
	 * @throws CGBusinessException the cG business exception
	 */
	private static BillTO convertBillDOToBillTO(BillDO billDO)
			throws CGBusinessException {
		BillTO billTO = new BillTO();
		CGObjectConverter.createToFromDomain(billDO, billTO);
		
		/*if (billDO.getContractPaymentBillingLocationDO() != null) {
			ContractPaymentBillingLocationTO contractPaymentBillingLocationTO = new ContractPaymentBillingLocationTO();
			billTO.setContractPaymentBillingLocationTO(contractPaymentBillingLocationTO);
			CGObjectConverter.createToFromDomain(
					billDO.getContractPaymentBillingLocationDO(),
					contractPaymentBillingLocationTO);
		}*/
		if (billDO.getFinancialProductDO() != null) {
			FinancialProductTO financialProductTO = new FinancialProductTO();
			billTO.setFinancialProductTO(financialProductTO);
			CGObjectConverter.createToFromDomain(billDO.getFinancialProductDO(), financialProductTO);
		}
		if (billDO.getPickupOfficeDO() != null) {
			OfficeTO pickupOfficeTO = new OfficeTO();
			billTO.setPickupOfficeTO(pickupOfficeTO);
			CGObjectConverter.createToFromDomain(billDO.getPickupOfficeDO(), pickupOfficeTO);
		}
		
		/*List<BillingConsignmentTO> billingConsignmentTOs = convertBillingConsignmentDOsToTOs(billDO.getBillingConsignmentDOs());
		List<BillingConsignmentSummaryTO> billingConsignmentSummaryTOs = convertBillingConsignmentSummaryDOsToTOs(billDO.getBillingConsignmentSummaryDOs());*/
		
		//billTO.setBillingConsignmentSummaryTOs(billingConsignmentSummaryTOs);
		//billTO.setBillingConsignmentTOs(billingConsignmentTOs);
		
		return billTO;
	}

}
