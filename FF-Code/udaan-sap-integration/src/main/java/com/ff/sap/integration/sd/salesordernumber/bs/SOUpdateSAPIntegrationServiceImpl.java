package com.ff.sap.integration.sd.salesordernumber.bs;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.billing.SAPBillSalesOrderDO;
import com.ff.domain.billing.SAPBillSalesOrderStagingDO;
import com.ff.domain.umc.UserDO;
import com.ff.sap.integration.dao.SAPIntegrationDAO;
import com.ff.sap.integration.to.SAPSalesOrderTO;
import com.ff.universe.billing.dao.BillingUniversalDAO;

/**
 * @author cbhure
 *
 */
public class SOUpdateSAPIntegrationServiceImpl implements SOUpdateSAPIntegrationService {

	
	Logger logger = Logger.getLogger(SOUpdateSAPIntegrationServiceImpl.class);
	
	private SAPIntegrationDAO integrationDAO; 
	private BillingUniversalDAO billingUniversalDAO;
	
	@Override
	public boolean updateSalesOrderNumber(List<SAPSalesOrderTO> salesOrder) throws CGBusinessException, CGSystemException {
		// TODO Auto-generated method stub
		logger.debug("SALES ORDER :: SOUpdateSAPIntegrationServiceImpl :: updateSalesOrderNumber :: Start");
		boolean isUpdate = false;
		//List<CGBaseDO> updateSAPBillSalesOrderDOs;
		try {
			
			//Update Bill Status....
			// updateInvoiceStatus(salesOrder);
			
			//Save Or Update
			// updateSAPBillSalesOrderDOs = saveOrUpdateBillNumberStatus(salesOrder);
			
			//Update Data in staging
			saveOrUpdateSalesOrderInStaging(salesOrder);
			
		} catch (CGSystemException e) {
			logger.error("SALES ORDER :: Exception IN :: SOUpdateSAPIntegrationServiceImpl :: updateSalesOrderNumber :: ",e);
		}
		logger.debug("SALES ORDER :: SOUpdateSAPIntegrationServiceImpl :: updateSalesOrderNumber :: End");
		return isUpdate;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void saveOrUpdateSalesOrderInStaging(
			List<SAPSalesOrderTO> salesOrder) throws CGSystemException {
		List<SAPBillSalesOrderStagingDO> DOList = new ArrayList(salesOrder.size());
		Iterator itr = salesOrder.iterator();
		while (itr.hasNext()) {
			SAPSalesOrderTO baseTo = (SAPSalesOrderTO) itr.next();
			try {
				SAPBillSalesOrderStagingDO baseEntity = new SAPBillSalesOrderStagingDO();
				PropertyUtils.copyProperties(baseEntity, baseTo);
				DOList.add(baseEntity);

			} catch (Exception obj) {
				logger.error("SALES ORDER :: SOUpdateSAPIntegrationServiceImpl :: updateSalesOrderNumber::", obj);
			}
		}
		integrationDAO.saveOrUpdateSalesOrderInStaging(DOList);
		
	}


	/*private boolean updateSalesOrderNoAgainstSummaryID(List<SAPSalesOrderTO> salesOrder) throws CGSystemException {
		logger.debug("SOUpdateSAPIntegrationServiceImpl :: updateSalesOrderNoAgainstSummaryID :: Start");
		boolean isUpdate = false;
		if(salesOrder != null && !salesOrder.isEmpty()) {			
			BillingConsignmentSummaryDO bcsDO = null;
			List<BillingConsignmentDO> billingConsgDOs = null;
			for(SAPSalesOrderTO soTO : salesOrder) {	
				if(!StringUtil.isEmptyInteger(soTO.getSummaryId())){
					Integer billingConsignmentId = soTO.getSummaryId();
					//To update SO Number
					bcsDO =	billingUniversalDAO.getBCSDtlsBySummaryID(billingConsignmentId);
					if(!StringUtil.isNull(bcsDO)){
						logger.debug("BCS ID From Table  ------->"+bcsDO.getBillingConsignmentSummaryId());
						isUpdate = integrationDAO.updateSalesOrderNumber(bcsDO,soTO);
						logger.debug("SO Number Updated Successfully....................");
					}
					//To Update Bill Number agianst Summary_Id
					billingConsgDOs = billingUniversalDAO.getInvoiceAgainstSummary(billingConsignmentId);
					if(!StringUtil.isNull(billingConsgDOs)){
						for(BillingConsignmentDO billConsgDO : billingConsgDOs){
							if(!StringUtil.isNull(billConsgDO.getBillDO())
									&& (!StringUtil.isEmptyInteger(billConsgDO.getBillDO().getInvoiceId()))){
								logger.debug("Invoice ID From Table  ------->"+billConsgDO.getBillDO().getInvoiceId());
								isUpdate = integrationDAO.updateInvoiceNumber(billConsgDO,soTO);
								logger.debug("Invoice Number Updated Successfully.......................");
							}
						}
					}
				}
			}
		}
		logger.debug("SOUpdateSAPIntegrationServiceImpl :: updateSalesOrderNoAgainstSummaryID :: End");
		return isUpdate;
	}*/

	private boolean updateInvoiceStatus(List<SAPSalesOrderTO> salesOrder) throws CGSystemException {
		logger.debug("SALES ORDER :: SOUpdateSAPIntegrationServiceImpl :: updateInvoiceStatus :: Start");
		boolean isUpdate = false;
		if(salesOrder != null && !salesOrder.isEmpty()) {
			SAPBillSalesOrderDO sapBillSalesOrderDO = null;
			for(SAPSalesOrderTO sapsalesOrderTO : salesOrder) {
				if(!StringUtil.isStringEmpty(sapsalesOrderTO.getBillNumber())
						&& !StringUtil.isStringEmpty(sapsalesOrderTO.getBillStatus())){
					logger.debug("SALES ORDER :: Bill Number From SAP PI----------->"+sapsalesOrderTO.getBillNumber());
					logger.debug("SALES ORDER :: Bill Status From SAP PI----------->"+sapsalesOrderTO.getBillStatus());
					sapBillSalesOrderDO = billingUniversalDAO.getSAPBillSalesOrderDetailsByBillNumber(sapsalesOrderTO.getBillNumber());
					if(!StringUtil.isNull(sapBillSalesOrderDO)){
						logger.debug("SALES ORDER :: updateInvoiceStatus : BILL NUMBER Status Going to be UPDATE----->"+sapBillSalesOrderDO.getBillNo());
						isUpdate = integrationDAO.updateInvoiceStatusInStaging(sapBillSalesOrderDO,sapsalesOrderTO); 
						logger.debug("SALES ORDER :: Staging Table Updated Successfully");
						if(isUpdate){
							isUpdate = false;
							logger.debug("SALES ORDER :: Updating in Staging Table");
							isUpdate = integrationDAO.updateInvoiceStatusInBillTable(sapBillSalesOrderDO,sapsalesOrderTO);
							logger.debug("SALES ORDER :: Bill Table Updated Successfully");
						}
					}
				}
			}
		}
		logger.debug("SOUpdateSAPIntegrationServiceImpl :: updateInvoiceStatus :: End");
		return isUpdate;
	}

	/**
	 * @param integrationDAO the integrationDAO to set
	 */
	public void setIntegrationDAO(SAPIntegrationDAO integrationDAO) {
		this.integrationDAO = integrationDAO;
	}

	/**
	 * @param billingUniversalDAO the billingUniversalDAO to set
	 */
	public void setBillingUniversalDAO(BillingUniversalDAO billingUniversalDAO) {
		this.billingUniversalDAO = billingUniversalDAO;
	}


	public List<CGBaseDO> saveSalesOrderNumber(List<SAPSalesOrderTO> salesOrder)throws CGBusinessException, IllegalAccessException,InvocationTargetException, NoSuchMethodException, CGSystemException {
		logger.debug("SALES ORDER :: SOUpdateSAPIntegrationServiceImpl :: saveSalesOrderNumber :: Start");
		List<CGBaseDO> baseList = null;
		if(salesOrder != null && !salesOrder.isEmpty()) {
			baseList = new ArrayList<CGBaseDO>(salesOrder.size());
			SAPBillSalesOrderDO sapBillSalesOrderDO = null;
			for(SAPSalesOrderTO sapsalesOrderTO : salesOrder) {
				if(!StringUtil.isEmptyInteger(sapsalesOrderTO.getSummaryId())){
					logger.debug("SALES ORDER ::  @ Save SO Number From SAP PI----------->"+sapsalesOrderTO.getSalesOrderNumber());
					logger.debug("SALES ORDER :: Bill Number From SAP PI----------->"+sapsalesOrderTO.getBillNumber());
					logger.debug("SALES ORDER :: Sum ID Number From SAP PI----------->"+sapsalesOrderTO.getSummaryId());
					sapBillSalesOrderDO = billingUniversalDAO.getSAPBillSalesOrderDetails(sapsalesOrderTO.getSummaryId());
					if(StringUtil.isNull(sapBillSalesOrderDO)){
						SAPBillSalesOrderDO billSalesOrderDO = null;
						boolean isUpdate = false;
						billSalesOrderDO = convertSalesOrderTOtoDO(sapsalesOrderTO,isUpdate,sapBillSalesOrderDO);
						baseList.add(billSalesOrderDO);
					}
				}
			}
		}
		logger.debug("SALES ORDER :: SOUpdateSAPIntegrationServiceImpl :: saveSalesOrderNumber :: End");
		return baseList;
	}


	private SAPBillSalesOrderDO convertSalesOrderTOtoDO(SAPSalesOrderTO sapsalesOrderTO, boolean isUpdate,SAPBillSalesOrderDO sapBillSalesOrderDO) throws CGSystemException {
		
		logger.debug("SALES ORDER :: SOUpdateSAPIntegrationServiceImpl :: convertSalesOrderTOtoDO :: Start");
		
		SAPBillSalesOrderDO sapBillSalesDo = new SAPBillSalesOrderDO();
		Date dateTime = Calendar.getInstance().getTime();
		if(isUpdate && (!StringUtil.isNull(sapBillSalesOrderDO)
				&& (!StringUtil.isEmptyLong(sapBillSalesOrderDO.getId())))){ 
			sapBillSalesDo.setId(sapBillSalesOrderDO.getId());
			sapBillSalesDo.setSapInbound("N");
			sapBillSalesDo.setCreatedDate(sapBillSalesOrderDO.getCreatedDate());
		}else{
			sapBillSalesDo.setId(!StringUtil.isEmptyLong(sapsalesOrderTO.getId())?sapsalesOrderTO.getId() :null);
			logger.debug("SALES ORDER :: SAP BILL SALES ORDER PK ----------------->"+sapBillSalesDo.getId());
		}
		
		if(!StringUtil.isStringEmpty(sapsalesOrderTO.getBillNumber())){
			sapBillSalesDo.setBillNo(sapsalesOrderTO.getBillNumber());
		}
		logger.debug("SALES ORDER :: Bill Number -------->"+sapBillSalesDo.getBillNo());
		
		if(!StringUtil.isStringEmpty(sapsalesOrderTO.getSalesOrderNumber())){
			sapBillSalesDo.setSalesOrderNo(sapsalesOrderTO.getSalesOrderNumber());
		}
		logger.debug("SALES ORDER :: Sales Order Number -------->"+sapBillSalesDo.getSalesOrderNo());
		
		if(!StringUtil.isStringEmpty(sapsalesOrderTO.getBillStatus())){
			sapBillSalesDo.setBillStatus(sapsalesOrderTO.getBillStatus());
		}
		logger.debug("SALES ORDER :: Bill Status -------->"+sapBillSalesDo.getBillStatus());
		
		if(!StringUtil.isEmptyInteger(sapsalesOrderTO.getSummaryId())){
			sapBillSalesDo.setSummaryId(sapsalesOrderTO.getSummaryId());
		}
		logger.debug("SALES ORDER :: Summary Id-------->"+sapBillSalesDo.getSummaryId());
		
		if(!StringUtil.isEmptyDouble(sapsalesOrderTO.getGrandTotal())){
			sapBillSalesDo.setGrandTotal(sapsalesOrderTO.getGrandTotal());
		}
		logger.debug("SALES ORDER :: Grand Total-------->"+sapBillSalesDo.getGrandTotal());
		
		if(!StringUtil.isNull(sapsalesOrderTO.getBillCreationDate())){
			sapBillSalesDo.setBillCreationDate(sapsalesOrderTO.getBillCreationDate());
		}
		logger.debug("SALES ORDER :: BILL CREATION DATE ---->"+sapBillSalesDo.getBillCreationDate());
		//sapBillSalesDo.setBillStatus("O");
		String userName = "SAP_USER";
		UserDO userDO = integrationDAO.getSAPUserDtls(userName);
		if(!StringUtil.isNull(userDO)
				&& !StringUtil.isEmptyInteger(userDO.getUserId())){
			sapBillSalesDo.setCreatedBy(userDO.getUserId());
			sapBillSalesDo.setUpdatedBy(userDO.getUserId());
		}
		if(!isUpdate){
			sapBillSalesDo.setSapInbound("N");
			sapBillSalesDo.setCreatedDate(dateTime);
		}
		sapBillSalesDo.setSapTimeStamp(dateTime);
		sapBillSalesDo.setUpdatedDate(dateTime);
		return sapBillSalesDo;
	}
	
	@Override
	public List<CGBaseDO> saveOrUpdateBillNumberStatus(List<SAPSalesOrderTO> salesOrder) throws CGBusinessException, CGSystemException {
		logger.debug("SALES ORDER :: SOUpdateSAPIntegrationServiceImpl :: updateBillNumberStatus :: Start");
		List<CGBaseDO> baseList = null;
		List<Integer> summaryIds = new ArrayList<Integer>();
		logger.debug("SALES ORDER :: SOUpdateSAPIntegrationServiceImpl :: updateInvoiceStatus :: Start");
		boolean isUpdate = false;
		if(salesOrder != null && !salesOrder.isEmpty()) {
			SAPBillSalesOrderDO sapBillSalesOrderDO = null;
			for(SAPSalesOrderTO sapsalesOrderTO : salesOrder) {
				if (!StringUtil.isEmptyInteger(sapsalesOrderTO.getSummaryId())) {
					summaryIds.add(sapsalesOrderTO.getSummaryId());
				}
				if(!StringUtil.isStringEmpty(sapsalesOrderTO.getBillNumber())
						&& !StringUtil.isStringEmpty(sapsalesOrderTO.getBillStatus())){
					logger.debug("SALES ORDER :: Bill Number From SAP PI----------->"+sapsalesOrderTO.getBillNumber());
					logger.debug("SALES ORDER :: Bill Status From SAP PI----------->"+sapsalesOrderTO.getBillStatus());
					sapBillSalesOrderDO = billingUniversalDAO.getSAPBillSalesOrderDetailsByBillNumber(sapsalesOrderTO.getBillNumber());
					if(!StringUtil.isNull(sapBillSalesOrderDO)){
						logger.debug("SALES ORDER :: updateInvoiceStatus : BILL NUMBER Status Going to be UPDATE----->"+sapBillSalesOrderDO.getBillNo());
						isUpdate = integrationDAO.updateInvoiceStatusInStaging(sapBillSalesOrderDO,sapsalesOrderTO); 
						logger.debug("SALES ORDER :: Staging Table Updated Successfully");
						if(isUpdate){
							isUpdate = false;
							logger.debug("SALES ORDER :: Updating in Staging Table");
							isUpdate = integrationDAO.updateInvoiceStatusInBillTable(sapBillSalesOrderDO,sapsalesOrderTO);
							logger.debug("SALES ORDER :: Bill Table Updated Successfully");
						}
					}
				}
			}
		}
		logger.debug("SOUpdateSAPIntegrationServiceImpl :: updateInvoiceStatus :: End");
		if (salesOrder != null && !salesOrder.isEmpty()) {
			logger.debug("SALES ORDER :: SOUpdateSAPIntegrationServiceImpl :: Total number of Sales Order received from SAP  :: ===> "
					+ salesOrder.size());
			baseList = new ArrayList<CGBaseDO>(salesOrder.size());
			// SAPBillSalesOrderDO sapBillSalesOrderDo = null;
			List<SAPBillSalesOrderDO> sapBillSalesOrderDOs = null;
			isUpdate = false;
			if (!CGCollectionUtils.isEmpty(summaryIds)){
				sapBillSalesOrderDOs = billingUniversalDAO
					.getSAPBillSalesOrderDetailsList(summaryIds);
			}
			// sapBillSalesOrderDo =
		    // billingUniversalDAO.getSAPBillSalesOrderDetails(sapsalesOrderTO.getSummaryId());
			for (SAPSalesOrderTO sapsalesOrderTO : salesOrder) {
				for (SAPBillSalesOrderDO sapBillSalesOrderDo : sapBillSalesOrderDOs) {
					if (!StringUtil.isNull(sapsalesOrderTO.getSummaryId())
							&& (sapsalesOrderTO.getSummaryId()
									.equals(sapBillSalesOrderDo.getSummaryId()))) {
						logger.debug("SALES ORDER ::  @ Update SO Number From SAP PI----------->"+ sapsalesOrderTO.getSalesOrderNumber());
						logger.debug("SALES ORDER :: Bill Number From SAP PI----------->"+ sapsalesOrderTO.getBillNumber());
						logger.debug("SALES ORDER :: Sum ID Number From SAP PI----------->"+ sapsalesOrderTO.getSummaryId());
						logger.debug("SALES ORDER :: Bill Status From SAP PI----------->"+ sapsalesOrderTO.getBillStatus());
						logger.debug("SALES ORDER :: BILL NUMBER Status Going to be UPDATE----->"+ sapBillSalesOrderDo.getBillNo());
						SAPBillSalesOrderDO billsSalesOrderDO = null;
						isUpdate = true;
						billsSalesOrderDO = convertSalesOrderTOtoDO(sapsalesOrderTO, isUpdate, sapBillSalesOrderDo);
						baseList.add(billsSalesOrderDO);
						break;
					}
				}
				if (!isUpdate) {
					logger.debug("SALES ORDER ::  @ Save SO Number From SAP PI----------->"+ sapsalesOrderTO.getSalesOrderNumber());
					logger.debug("SALES ORDER :: Bill Number From SAP PI----------->"+ sapsalesOrderTO.getBillNumber());
					logger.debug("SALES ORDER :: Sum ID Number From SAP PI----------->"+ sapsalesOrderTO.getSummaryId());
					SAPBillSalesOrderDO billSalesOrderDO = null;
					isUpdate = false;
					billSalesOrderDO = convertSalesOrderTOtoDO(sapsalesOrderTO,isUpdate, null);
					baseList.add(billSalesOrderDO);
				} else {
					isUpdate = false;
				}
			}
		}
		
		if(!CGCollectionUtils.isEmpty(baseList)){
			isUpdate = integrationDAO.updateDetails(baseList);
		}
		logger.debug("SALES ORDER :: SOUpdateSAPIntegrationServiceImpl :: updateBillNumberStatus :: End");
		return baseList;
      }
}
