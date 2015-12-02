package src.com.dtdc.mdbServices.purchase.quotation;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import src.com.dtdc.mdbDao.purchase.PurchaseMBDDAO;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.domain.master.employee.EmployeeDO;
import com.dtdc.domain.master.vendor.VendorDO;
import com.dtdc.domain.purchase.PurchaseReqDO;
import com.dtdc.domain.purchase.RequestForQuotationDO;
import com.dtdc.to.purchase.quotation.ReqQuotationRowTO;
import com.dtdc.to.purchase.quotation.ReqQuotationTO;

// TODO: Auto-generated Javadoc
/**
 * class is responsible for Request For Quotation service business logic.
 *
 * @author joaugust
 */
public class ReqForQuotationMBDServiceImpl implements ReqForQuotationMBDService {

	/** The purchase mbddao. */
	private PurchaseMBDDAO purchaseMBDDAO;
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReqForQuotationMBDServiceImpl.class);
	
	/* (non-Javadoc)
	 * @see src.com.dtdc.mdbServices.purchase.quotation.ReqForQuotationMBDService#saveReqQuotation(com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO)
	 */
	public void saveReqQuotation(CGBaseTO reqQuotationTo) throws Exception {
		
		LOGGER.debug("*********************** In Spring Bean Class *********************"
				+ reqQuotationTo.getBaseList());
		
		ReqQuotationTO reqQuotationTO = (ReqQuotationTO) reqQuotationTo.getBaseList().get(0);
		
		@SuppressWarnings("unchecked")
		List<ReqQuotationRowTO> rowToList = (List<ReqQuotationRowTO>) reqQuotationTo.getJsonChildObject();
		List<RequestForQuotationDO> RequestQuotationDOList = new ArrayList<RequestForQuotationDO>();
		
		boolean rfqSaveStatus =false;
			int counter = 0;
			RequestForQuotationDO domainobj = null;
			while (rowToList != null && rowToList.size() > counter){
				domainobj = new RequestForQuotationDO();
				PurchaseReqDO reqDO = new PurchaseReqDO();
				CGObjectConverter.createDomainFromTo(rowToList.get(counter), reqDO);
				domainobj.setReqQuotationID(rowToList.get(counter).getReqQuotationID());
				domainobj.setPurchaseReq(reqDO);
				domainobj=createDomainFromTo(reqQuotationTO, domainobj);
				
				RequestQuotationDOList.add(domainobj);			
				counter++;
			}
			if(RequestQuotationDOList!=null){
				rfqSaveStatus=purchaseMBDDAO.saveReqForQuotations(RequestQuotationDOList );				
			}
			
			/*
			 * To throw the exception explicitly so that request can be written into
			 * ERROR Q
			 */
			if (!rfqSaveStatus) {
				throw new CGBusinessException("Error occurred while saving the data");
			}
	}

	/**
	 * Creates the domain from to.
	 *
	 * @param reqQuotationTO the req quotation to
	 * @param domainobj the domainobj
	 * @return the request for quotation do
	 */
	public RequestForQuotationDO createDomainFromTo(ReqQuotationTO reqQuotationTO,RequestForQuotationDO domainobj) {
		
		//RequestForQuotationDO reqForQuotationDO = new RequestForQuotationDO();
		VendorDO vendorDO = new VendorDO();
		EmployeeDO employeeDO=new EmployeeDO();
		PurchaseReqDO purchaseReqDO=new PurchaseReqDO();
		
		if((reqQuotationTO!=null)&&(domainobj!=null)) {
			domainobj.setPrNumber(StringUtil.checkForNull(reqQuotationTO.getPrNumber())?reqQuotationTO.getPrNumber():null);
			domainobj.setQuotationDeadlineDate(StringUtil.checkForNull(reqQuotationTO.getQuotationDeadlineDate())?reqQuotationTO.getQuotationDeadlineDate():null);
			domainobj.setRfqNumber(StringUtil.checkForNull(reqQuotationTO.getRfqNumber())?reqQuotationTO.getRfqNumber():null);
			domainobj.setRfqDate(StringUtil.checkForNull(reqQuotationTO.getRfqDate())?reqQuotationTO.getRfqDate():null);
			domainobj.setRfqType(StringUtil.checkForNull(reqQuotationTO.getRfqType())?reqQuotationTO.getRfqType():null);
			domainobj.setRfqStatus(StringUtil.checkForNull(reqQuotationTO.getRfqStatus())?reqQuotationTO.getRfqStatus():null);
			domainobj.setStatusDate(StringUtil.checkForNull(reqQuotationTO.getStatusDate())?reqQuotationTO.getStatusDate():null);
			
			
			if(StringUtil.checkForNull(reqQuotationTO.getVendorID())){
				vendorDO.setVendorId(reqQuotationTO.getVendorID());
				domainobj.setVendor(vendorDO);
			}
			if(StringUtil.checkForNull(reqQuotationTO.getEmployeeEnteredID())){
				employeeDO.setEmployeeId(reqQuotationTO.getEmployeeEnteredID());
				domainobj.setEmployeeEntered(employeeDO);
			}
			if(StringUtil.checkForNull(reqQuotationTO.getApproverEmpIdStr())){
				employeeDO.setEmployeeId(reqQuotationTO.getApproverEmpId());
				domainobj.setApproverEmp(employeeDO);
			}
			if(StringUtil.checkForNull(reqQuotationTO.getPurchaseReqID())){
				purchaseReqDO.setPurchase_req_id(reqQuotationTO.getPurchaseReqID());
				domainobj.setPurchaseReq(purchaseReqDO);
			}
			
		}
		return domainobj;
	}

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
}
