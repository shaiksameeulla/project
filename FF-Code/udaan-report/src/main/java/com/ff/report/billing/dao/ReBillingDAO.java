package com.ff.report.billing.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.billing.BillingConsignmentRateDO;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.billing.ReBillingConsignmentDO;
import com.ff.domain.billing.ReBillingConsignmentRateDO;
import com.ff.domain.billing.ReBillingHeaderDO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.to.billing.ReBillConsgAliasTO;
import com.ff.to.billing.ReBillGDRAliasTO;
import com.ff.to.billing.ReBillingGDRTO;
import com.ff.to.billing.ReBillingRateAliasTO;

public interface ReBillingDAO {

	public ReBillingHeaderDO saveOrUpdateReBilling(ReBillingHeaderDO reBillingHeaderDO)throws CGSystemException;
	public List<ReBillingHeaderDO> getRebillDetails(ReBillingGDRTO rebillingGDRTO)throws CGSystemException;
	public List<ReBillingHeaderDO> getRebillingJobDetails()throws CGSystemException;
	public List<ReBillConsgAliasTO> getRebillConsignmentData(ReBillingHeaderDO reBillingHeaderDO)throws CGSystemException;
	public ConsignmentBillingRateDO getAlreadyExistConsgRate (ConsignmentDO consingnment, String rateFor)throws CGBusinessException, CGSystemException;
	public ConsignmentBillingRateDO saveOrUpdateRateAndStatus(ConsignmentBillingRateDO consignmentBillingRateDO, String consgNo)throws CGBusinessException, CGSystemException;
	public ConsignmentDO getConsgDetails(String consgNo)throws CGBusinessException,CGSystemException;
	public Integer  saveOrUpdateRebillingConsignment(ReBillingConsignmentDO rebillConsgDO)throws CGBusinessException,CGSystemException;
	public boolean saveOrUpdateRebillingRate(List<ReBillingConsignmentRateDO> reBillingConsignmentRateDO)throws CGBusinessException,CGSystemException;
	public List<ReBillingRateAliasTO> getRebillingConsignmentRateForOld(Integer consgId)throws CGSystemException;
	public boolean updateRebillingHeaderFlag(String rebillingHeaderNo)throws CGSystemException;
	public List<BillingConsignmentRateDO>  getRebillingConsgRateFromBillingConsgRate(Integer billingConsgId)throws CGSystemException;
	public Long getTotalRebillCnCount(Integer reBillId)throws CGSystemException;
	public Long getNewContractForCount(Integer reBillId)throws CGSystemException;
	public Long getOldContractForCount(Integer reBillId)throws CGSystemException;
}
