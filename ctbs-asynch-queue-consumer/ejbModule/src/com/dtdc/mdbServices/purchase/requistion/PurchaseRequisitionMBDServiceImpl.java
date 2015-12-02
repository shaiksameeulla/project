package src.com.dtdc.mdbServices.purchase.requistion;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import src.com.dtdc.mdbDao.purchase.PurchaseMBDDAO;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.domain.master.StdBankDO;
import com.dtdc.domain.master.employee.EmployeeDO;
import com.dtdc.domain.master.franchisee.FranchiseeDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.purchase.PurchaseReqDO;
import com.dtdc.to.purchase.requisition.PurchaseReqRowTo;
import com.dtdc.to.purchase.requisition.PurchaseRequisitionTO;

// TODO: Auto-generated Javadoc
// TODO: Auto-generated Java doc
/**
 * class is responsible for Purchase Requisition service business logic.
 * 
 * @author joaugust
 */
public class PurchaseRequisitionMBDServiceImpl implements
		PurchaseRequisitionMBDService {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PurchaseRequisitionMBDServiceImpl.class);

	
	/** The purchase mbddao. */
	private PurchaseMBDDAO purchaseMBDDAO;
	
	/**
	 * Gets the purchase mbddao.
	 *
	 * @return the purchaseMBDDAO
	 */
	public PurchaseMBDDAO getPurchaseMBDDAO() {
		return purchaseMBDDAO;
	}

	/**
	 * Sets the purchase mbddao.
	 *
	 * @param purchaseMBDDAO the purchaseMBDDAO to set
	 */
	public void setPurchaseMBDDAO(PurchaseMBDDAO purchaseMBDDAO) {
		this.purchaseMBDDAO = purchaseMBDDAO;
	}
		
	/* (non-Javadoc)
	 * @see src.com.dtdc.mdbServices.purchase.requistion.PurchaseRequisitionMBDService#saveRequisition(com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO)
	 */
	public Boolean saveRequisition(CGBaseTO purchaseTO)
			throws Exception {
		LOGGER.debug("*********************** In Spring Bean Class *********************"
				+ purchaseTO.getBaseList());
		
		PurchaseRequisitionTO purchaseTo = (PurchaseRequisitionTO) purchaseTO.getBaseList().get(0);
		
		boolean purchaseeSaveStatus =false;
		
		@SuppressWarnings("unchecked")
		List<PurchaseReqRowTo> rowTo = (List<PurchaseReqRowTo>) purchaseTO.getJsonChildObject();
		
		List<PurchaseReqDO> purchaseReqDOList = new ArrayList<PurchaseReqDO>();
			int counter = 0;
			PurchaseReqDO domainobj = null;
			while (rowTo != null && rowTo.size() > counter) {
				domainobj = new PurchaseReqDO();
				CGObjectConverter.createDomainFromTo(rowTo.get(counter),
						domainobj);
				/*OfficeDO officeDOPlant=new OfficeDO();
				officeDOPlant.setOfficeId(StringUtil.checkForNull(rowTo.get(counter).getPlantId())?rowTo.get(counter).getPlantId():null);
				domainobj.setPlant(officeDOPlant);*/
				
				CGObjectConverter.createDomainFromTo(purchaseTo, domainobj);
				OfficeDO officeDORO=new OfficeDO();
				officeDORO.setOfficeId(StringUtil.checkForNull(purchaseTo.getRequestingBranchROId())?purchaseTo.getRequestingBranchROId():null);
				domainobj.setRequestingBranch_ro(officeDORO);
				
				purchaseReqDOList.add(domainobj);
				counter++;
			}
			if (purchaseReqDOList != null) {
				purchaseeSaveStatus =purchaseMBDDAO.saveRequisition(purchaseReqDOList);
				purchaseeSaveStatus=true;
			}
			
			/* * To throw the exception explicitly so that request can be written into
			 * ERROR Q*/
			 
			if (!purchaseeSaveStatus) {
				throw new Exception();
			}
			return purchaseeSaveStatus;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtdc.ng.bs.purchase.requisition.PurchaseRequisitionService#
	 * saveRequisition(com.dtdc.to.purchase.PurchaseTO)
	 */
	@Override
	public Boolean saveRequestFranchisee(CGBaseTO purchaseTo) throws Exception {
		@SuppressWarnings("unchecked")
		
		PurchaseRequisitionTO purchaseToDO = (PurchaseRequisitionTO) purchaseTo.getBaseList().get(0);
		@SuppressWarnings("unchecked")
		List<PurchaseReqRowTo> rowTo = (List<PurchaseReqRowTo>) purchaseTo.getJsonChildObject();
		List<PurchaseReqDO> purchaseReqDOList = new ArrayList<PurchaseReqDO>();
		
		Boolean flagSuccess = false;

		try {
			int counter = 0;
			PurchaseReqDO domainobj = null;
			while (rowTo != null && rowTo.size() > counter) {
				domainobj = new PurchaseReqDO();
				CGObjectConverter.createDomainFromTo(rowTo.get(counter),domainobj);
				CGObjectConverter.createDomainFromTo(purchaseToDO, domainobj);

				EmployeeDO enteredByDO = new EmployeeDO();
				enteredByDO.setEmployeeId(purchaseToDO.getEnteredEmpId());
				
				OfficeDO requestingBranchRODO=null;
				FranchiseeDO requestFranchiseeDO = new FranchiseeDO();
				requestFranchiseeDO.setFranchiseeId(purchaseToDO.getRequestFranchiseeId());
				
				EmployeeDO updatedByDO = new EmployeeDO();
				updatedByDO.setEmployeeId(purchaseToDO.getUpdatedEmpId());
				
				StdBankDO bankDO = new StdBankDO();
				bankDO.setStdBankId(purchaseToDO.getBankDetailId());
				
				OfficeDO supplyingPlantDO=new OfficeDO();
				supplyingPlantDO.setOfficeId(purchaseToDO.getSupplyingPlantId());
				
				domainobj.setEnteredBy(enteredByDO);
				domainobj.setRequestingBranch_ro(requestingBranchRODO);
				domainobj.setRequestFranchisee(requestFranchiseeDO);
				domainobj.setUpdatedBy(updatedByDO);
				domainobj.setBankID(bankDO);
				
				
				purchaseReqDOList.add(domainobj);
			
				counter++;
			}
			if (purchaseReqDOList != null) {
				purchaseMBDDAO.saveRequisition(purchaseReqDOList);
				flagSuccess=true;
			}
			
		} catch (CGSystemException e) {
			LOGGER.error("PurchaseRequisitionMBDServiceImpl::saveRequestFranchisee::Exception occured:"
					+e.getMessage());
		}
		return flagSuccess;
	}
	
}
