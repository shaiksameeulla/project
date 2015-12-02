package src.com.dtdc.mdbServices.purchase.requistion;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import src.com.dtdc.mdbDao.purchase.PurchaseMBDDAO;
import src.com.dtdc.mdbDao.purchase.StoreReqSlipMBDDAO;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.dtdc.domain.login.UserDO;
import com.dtdc.domain.master.employee.EmployeeDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.purchase.StoreReqSlipDO;
import com.dtdc.to.purchase.requisition.PurchaseReqRowTo;
import com.dtdc.to.purchase.requisition.StoreRequstSlipTO;

// TODO: Auto-generated Javadoc
/**
 * class is responsible for Purchase Requisition service business logic.
 * 
 * @author joaugust
 */
public class StoreReqSlipServiceMBDImpl implements StoreReqSlipMBDService {

	/** The purchase mbddao. */
	private PurchaseMBDDAO purchaseMBDDAO;
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(StoreReqSlipServiceMBDImpl.class);

	
	/** The store req slip mbddao. */
	private StoreReqSlipMBDDAO storeReqSlipMBDDAO;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtdc.ng.bs.purchase.requisition.PurchaseRequisitionService#
	 * saveRequisition(com.dtdc.to.purchase.PurchaseTO)
	 */
	@Override
	public Boolean saveSTODetails(CGBaseTO stoRequstTO) throws CGBusinessException {
		
		@SuppressWarnings("unchecked")
		List<PurchaseReqRowTo> rowTo = (List<PurchaseReqRowTo>) stoRequstTO.getJsonChildObject();
		List<StoreReqSlipDO> storeReqSlipDOList = new ArrayList<StoreReqSlipDO>();
		
		StoreRequstSlipTO storeRequstSlipTO=(StoreRequstSlipTO) stoRequstTO.getBaseList().get(0);
		
		Boolean flag=false;

		try {
			int counter = 0;
			StoreReqSlipDO domainobj = null;
			while (rowTo != null && rowTo.size() > counter) {
				domainobj = new StoreReqSlipDO();
				
				CGObjectConverter.createDomainFromTo(rowTo.get(counter),domainobj);
				
				OfficeDO officeDOPlant=new OfficeDO();
				officeDOPlant.setOfficeId(rowTo.get(counter).getPlantId());
				
				
				CGObjectConverter.createDomainFromTo(storeRequstSlipTO, domainobj);
				
				EmployeeDO createdByDO = new EmployeeDO();				
				createdByDO.setEmployeeId(storeRequstSlipTO.getCreatedById());
				domainobj.setCreatedByDO(createdByDO);
				
				OfficeDO requestingPlantDO = new OfficeDO();
				requestingPlantDO.setOfficeId(storeRequstSlipTO.getRequestingPlantId());
				domainobj.setRequestingPlantDO(requestingPlantDO);
				
				EmployeeDO goodsReceipientDO= new EmployeeDO();
				goodsReceipientDO.setEmployeeId(storeRequstSlipTO.getGoodsRecipientId());
				domainobj.setGoodsReceipientDO(goodsReceipientDO);
				
				UserDO userDO= new UserDO();
				userDO.setUserId(1);
				domainobj.setLoginUser(userDO);
				
				storeReqSlipDOList.add(domainobj);
				// purchaseDAO.saveRequisition(domainobj );
				counter++;
			}
			
			if (storeReqSlipDOList != null) {
				storeReqSlipMBDDAO.saveSTOdetails(storeReqSlipDOList);

			}
		} catch (CGSystemException e) {
			LOGGER.error("StoreReqSlipServiceMBDImpl::saveSTODetails::Exception occured:"
					+e.getMessage());
		}
		
		return flag;
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

	/**
	 * Gets the store req slip mbddao.
	 *
	 * @return the storeReqSlipMBDDAO
	 */
	public StoreReqSlipMBDDAO getStoreReqSlipMBDDAO() {
		return storeReqSlipMBDDAO;
	}

	/**
	 * Sets the store req slip mbddao.
	 *
	 * @param storeReqSlipMBDDAO the storeReqSlipMBDDAO to set
	 */
	public void setStoreReqSlipMBDDAO(StoreReqSlipMBDDAO storeReqSlipMBDDAO) {
		this.storeReqSlipMBDDAO = storeReqSlipMBDDAO;
	}
}
