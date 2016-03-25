package com.cg.lbs.bcun.service.dataformater;

import java.util.List;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.ff.domain.ratemanagement.masters.BcunRateContractDO;

public class RateContractFormatter extends AbstractDataFormater {

	@SuppressWarnings("unchecked")
	@Override
	public CGBaseDO formatInsertData(CGBaseDO baseDO,BcunDatasyncService bcunService) throws CGBusinessException,CGSystemException {
		
		BcunRateContractDO brctDO = (BcunRateContractDO)baseDO;
		BcunRateContractDO rateContractDO = new BcunRateContractDO();
		
		rateContractDO = brctDO;
		String[] params = {"rateContractNo"};
		String[] values = {brctDO.getRateContractNo()};
		
		List<CGBaseDO> baseList = ((List<CGBaseDO>) bcunService.getDataByNamedQueryAndNamedParam("getContractDtlsByContractNo", params, values));
		
		if(!CGCollectionUtils.isEmpty(baseList)){
			rateContractDO = convertContractDO2DO(rateContractDO, (BcunRateContractDO) baseList.get(0));
		}
		
		/* The below line is added since to make the RatequotationOriginatedFrom field as null. This is done in order to avoid
		 * foreign key constraint violation on the same field -- Code added by Tejas
		 * */
		rateContractDO.getRateQuotationDO().setRatequotationOriginatedFrom(null);
		return rateContractDO;
	}

	@Override
	public CGBaseDO formatUpdateData(CGBaseDO baseDO,BcunDatasyncService bcunService) throws CGBusinessException,CGSystemException {
		
		return formatInsertData(baseDO, bcunService);
	}
	
	private BcunRateContractDO convertContractDO2DO(BcunRateContractDO rateContractDO, BcunRateContractDO dbContractDO){
		
		dbContractDO.getRateQuotationDO().setExcecutiveRemarks("Approved");
		return rateContractDO;
		/*if(!StringUtil.isNull(rateContractDO) && !StringUtil.isNull(dbContractDO)){
			
			//dbContractDO.setRateContractId(rateContractDO.getRateContractId());
			dbContractDO.setRateContractNo(rateContractDO.getRateContractNo());
			dbContractDO.setValidFromDate(rateContractDO.getValidFromDate());
			dbContractDO.setValidToDate(rateContractDO.getValidToDate());
			
			dbContractDO.setBillingContractType(rateContractDO.getBillingContractType());
			dbContractDO.setTypeOfBilling(rateContractDO.getTypeOfBilling());
			dbContractDO.setModeOfBilling(rateContractDO.getModeOfBilling());
			dbContractDO.setBillingCycle(rateContractDO.getBillingCycle());
			dbContractDO.setPaymentTerm(rateContractDO.getPaymentTerm());
			dbContractDO.setOctraiBourneBy(rateContractDO.getOctraiBourneBy());
			dbContractDO.setContractFor(rateContractDO.getContractFor());
			dbContractDO.setRateContractType(rateContractDO.getRateContractType());
			dbContractDO.setContractStatus(rateContractDO.getContractStatus());
			dbContractDO.setCustomerId(rateContractDO.getCustomerId());
			dbContractDO.setContractCreatedBy(rateContractDO.getContractCreatedBy());
			dbContractDO.setCreatedDate(rateContractDO.getCreatedDate());
			dbContractDO.setOriginatedRateContractId(rateContractDO.getOriginatedRateContractId());
			dbContractDO.setIsRenewed(rateContractDO.getIsRenewed());
			dbContractDO.setUserId(rateContractDO.getUpdatedBy());
			dbContractDO.setUpdatedDate(rateContractDO.getUpdatedDate());
			
			BcunRateQuotationDO rateQuotationDO = new BcunRateQuotationDO();
			
			BcunRateQuotationDO rqDO = rateContractDO.getRateQuotationDO();
			
			rateQuotationDO.setRateQuotationId(rqDO.getRateQuotationId());
			rateQuotationDO.setRateQuotationNo(rqDO.getRateQuotationNo());
			rateQuotationDO.setStatus(rqDO.getStatus());
			rateQuotationDO.setRateQuotationType(rqDO.getRateQuotationType());
			rateQuotationDO.setRatequotationOriginatedFrom(rqDO.getRatequotationOriginatedFrom());
			rateQuotationDO.setRateQuotationOriginatedfromType(rqDO.getRateQuotationOriginatedfromType());
			rateQuotationDO.setApproversRemarks(rqDO.getApproversRemarks());
			rateQuotationDO.setExcecutiveRemarks(rqDO.getExcecutiveRemarks());
			rateQuotationDO.setQuotationCreatedDate(rqDO.getQuotationCreatedDate());
			rateQuotationDO.setQuotationCreatedBy(rqDO.getQuotationCreatedBy());
			rateQuotationDO.setApprovalRequired(rqDO.getApprovalRequired());
			rateQuotationDO.setApprovedAt(rqDO.getApprovedAt());
			rateQuotationDO.setQuotationUsedFor(rqDO.getQuotationUsedFor());
			rateQuotationDO.setRateContractId(rqDO.getRateContractId());
			rateQuotationDO.setCustomer(rqDO.getCustomer());
			
			rateQuotationDO.setRateQuotationProductCategoryHeaderDO(rqDO.getRateQuotationProductCategoryHeaderDO());
			rateQuotationDO.setRateFixedChargeDO(rqDO.getRateFixedChargeDO());
			if(!CGCollectionUtils.isEmpty(rqDO.getCodChargeDO())){
				rateQuotationDO.setCodChargeDO(rqDO.getCodChargeDO());
			}else{
				rateQuotationDO.setCodChargeDO(new HashSet<BcunRateQuotationCODChargeDO>());
			}
			
			if(!CGCollectionUtils.isEmpty(rqDO.getCodChargeDO())){
				rateQuotationDO.setRateQuotationFixedChargesConfigDO(rqDO.getRateQuotationFixedChargesConfigDO());
			}else{
				rateQuotationDO.setRateQuotationFixedChargesConfigDO(new HashSet<BcunRateQuotationFixedChargesConfigDO>());
			}
			dbContractDO.setRateQuotationDO(rateQuotationDO);
			dbContractDO.setConPayBillLocDO(rateContractDO.getConPayBillLocDO());
		}
		
		return dbContractDO;		*/
		
	}
}
