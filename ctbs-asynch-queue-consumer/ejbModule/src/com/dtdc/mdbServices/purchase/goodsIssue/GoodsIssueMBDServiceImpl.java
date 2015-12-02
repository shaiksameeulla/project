package src.com.dtdc.mdbServices.purchase.goodsIssue;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import src.com.dtdc.constants.PurchaseConstants;
import src.com.dtdc.mdbDao.purchase.GoodsIssueMBDDAO;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.dtdc.domain.login.UserDO;
import com.dtdc.domain.master.employee.EmployeeDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.purchase.GoodsIssueDO;
import com.dtdc.to.purchase.goods.GoodsIssueRowTo;
import com.dtdc.to.purchase.goods.GoodsIssueTo;

// TODO: Auto-generated Javadoc
/**
 * class is responsible for Request For Quotation service business logic.
 *
 * @author joaugust
 */
public class GoodsIssueMBDServiceImpl implements GoodsIssueMBDService {
	
	

	/** The Constant LOGGER. */
	private static final Logger logger= LoggerFactory.getLogger(GoodsIssueMBDServiceImpl.class);
	
	/** The goods issue dao. */
	private GoodsIssueMBDDAO goodsIssueMBDDAO;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dtdc.ng.bs.purchase.goods.GoodsIssueService#saveGoodsIssueDetails
	 * (com.dtdc.to.purchase.goods.GoodsIssueTo)
	 */
	@Override
	public boolean saveGoodsIssueDetails(GoodsIssueTo goodsIssueTo)
			throws CGBusinessException {
		
		logger.debug("*********************** In Spring Bean Class *********************"
				+ goodsIssueTo.getBaseList());
		
		boolean result=false;
		
		GoodsIssueTo goodsIssueToDO = (GoodsIssueTo) goodsIssueTo.getBaseList().get(0);
		
		@SuppressWarnings("unchecked")
		List<GoodsIssueRowTo> rowToList = (List<GoodsIssueRowTo>) goodsIssueTo.getJsonChildObject();
		List<GoodsIssueDO> goodsIssueDOList = new ArrayList<GoodsIssueDO>();
				
			int counter = 0;
			GoodsIssueDO domainobj = null;
			while (rowToList != null && rowToList.size() > counter) {
				domainobj = new GoodsIssueDO();

				/*
				 * OfficeDO officeDOPlant=new OfficeDO();
				 * officeDOPlant.setOfficeId
				 * (StringUtil.checkForNull(rowTo.get(counter
				 * ).getPlantId())?rowTo.get(counter).getPlantId():null);
				 * domainobj.setPlant(officeDOPlant);
				 */

				// Covert RowTO To DO
				CGObjectConverter.createDomainFromTo(rowToList.get(counter),
						domainobj);
				// Covert TO To DO
				CGObjectConverter.createDomainFromTo(goodsIssueToDO, domainobj);
				//issuingPlant
				OfficeDO iPlant=new OfficeDO();
				iPlant.setOfficeId(goodsIssueTo.getIssPlantId());				
				domainobj.setIssuingPlant(iPlant);
				//receiving plant
				OfficeDO rPlant=new OfficeDO();
				rPlant.setOfficeId(goodsIssueTo.getRecPlantId());	
				domainobj.setReceivingPlantId(rPlant);
				//issuing person Id
				EmployeeDO issuingPersonId=new EmployeeDO();
				issuingPersonId.setEmployeeId(goodsIssueTo.getIssPersonId());
				domainobj.setIssuingPerson(issuingPersonId);
				if(goodsIssueTo.getDocumentType().equalsIgnoreCase(PurchaseConstants.GO1)){
					domainobj.setReceivingPlantType(null);
				} else if(goodsIssueTo.getDocumentType().equalsIgnoreCase(PurchaseConstants.GO2)){
					domainobj.setReceivingPlantId(null);
					domainobj.setReceiptStorageLocation(null);
				}
				//FIXME harded
				domainobj.setPurchaseReqId(null);
				//FIXME harded				
				UserDO ud=new UserDO();
				ud.setUserId(1);
				//domainobj.setUserId(ud);
				goodsIssueDOList.add(domainobj);
				counter++;
			}
			if (goodsIssueDOList != null) {
				// Call DAO Imp Method
					result=	goodsIssueMBDDAO.saveGoodsIssueDetails(goodsIssueDOList);
			}
			return result;
	}

	/**
	 * Gets the goods issue mbddao.
	 *
	 * @return the goodsIssueMBDDAO
	 */
	public GoodsIssueMBDDAO getGoodsIssueMBDDAO() {
		return goodsIssueMBDDAO;
	}

	/**
	 * Sets the goods issue mbddao.
	 *
	 * @param goodsIssueMBDDAO the goodsIssueMBDDAO to set
	 */
	public void setGoodsIssueMBDDAO(GoodsIssueMBDDAO goodsIssueMBDDAO) {
		this.goodsIssueMBDDAO = goodsIssueMBDDAO;
	}
}