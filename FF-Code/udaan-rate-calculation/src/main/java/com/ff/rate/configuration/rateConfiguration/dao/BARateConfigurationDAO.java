/**
 * 
 */
package com.ff.rate.configuration.rateConfiguration.dao;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.business.CustomerTypeDO;
import com.ff.domain.ratemanagement.masters.RateSectorsDO;
import com.ff.domain.ratemanagement.masters.RateWeightSlabsDO;
import com.ff.domain.ratemanagement.masters.SectorDO;
import com.ff.domain.ratemanagement.operations.ba.BaRateConfigHeaderDO;
import com.ff.domain.ratemanagement.operations.ba.BaRateWeightSlabDO;
import com.ff.domain.ratemanagement.operations.ratecalculation.BARateConfigRTOChargesDO;
import com.ff.domain.serviceOffering.InsuredByDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;

/**
 * @author prmeher
 * 
 */
public interface BARateConfigurationDAO {

	public List<StockStandardTypeDO> getStockStdType(String typeName)
			throws CGSystemException;

	public List<RateSectorsDO> getRateSectorListForBARateConfiguration(
			String baRateProductCategory, String rateCustomerCategory)
			throws CGSystemException;

	public List<RateWeightSlabsDO> getRateWeightSlabsList(
			String baRateProductCategory, String rateCustomerCategory);

	public SectorDO getSectorByCityCode(String cityCode);

	public List<CustomerTypeDO> getBATypeList(String customerCode);

	public BaRateConfigHeaderDO saveOrUpdateBARateConfiguration(
			BaRateConfigHeaderDO baRateHeaderDO) throws CGSystemException;

	public BaRateConfigHeaderDO getBARateConfigurationDetails(Date fromDate,
			Date toDate, Integer cityId, Integer baTypeId,
			Integer courierProdductId) throws CGSystemException;

	public BaRateConfigHeaderDO getFixedChargesByHeader(
			Integer courierProductHeaderId, String priorityIndicator);

	public List<InsuredByDO> getInsuredByDetails();

	public BARateConfigRTOChargesDO getRTOChargesByHeader(
			Integer headerId,String priorityIndicator) throws CGSystemException;

	public boolean submitBaRateConfiguration(Integer headerId, String fromDate,
			String toDate, Integer updatedBy, Date updateDate) throws CGSystemException;

	public Boolean isExistsBaRateConfiguration(Integer cityId, Integer baTypeId);

	boolean updateBAConfgRenewStatus(Integer headerId) throws CGSystemException;

	boolean updateBAConfgTODate(Date toDate, Integer headerId)
			throws CGSystemException;

	public boolean updateBAConfgFromDate(Date newFrmDate, Integer oldHeaderId)
			throws CGSystemException;

	public BaRateConfigHeaderDO getRenewedBARateConfigurationDetails(
			Integer cityId, Integer baTypeId, Integer courierProdductId,
			Date toDate) throws CGSystemException;

	public BaRateConfigHeaderDO getBARateConfigurationDetailsByHeaderId(
			Integer headerId) throws CGSystemException;

	public Date getFromDateByBARateHeaderId(Integer headerId)
			throws CGSystemException;

	/**
	 * To save or update BA Rate Weight Slab Details
	 * 
	 * @param baRateWeightSlabDO
	 * @throws CGSystemException
	 * @author hkansagr
	 */
	public void saveOrUpdateBARateWtSlab(BaRateWeightSlabDO baRateWeightSlabDO)
			throws CGSystemException;

	/**
	 * To search BA Weight Slab Details
	 * 
	 * @param integer
	 * @return domain
	 * @throws CGSystemException
	 * @author hkansagr
	 */
	public BaRateWeightSlabDO searchBARateWtSlabByBaWtSlabId(Integer integer)
			throws CGSystemException;

	/**
	 * @param cityId
	 * @param baTypeId
	 * @return baRateHeaderDO
	 * @throws CGSystemException
	 */
	BaRateConfigHeaderDO _getBARatesDtls(Integer cityId, Integer baTypeId)
			throws CGSystemException;

}
