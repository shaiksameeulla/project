/**
 * 
 */
package com.ff.admin.stockmanagement.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.MessageType;
import com.capgemini.lbs.framework.exception.MessageWrapper;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.stockmanagement.common.constants.StockCommonConstants;
import com.ff.admin.stockmanagement.excelupload.constants.StockExcelUploadConstants;
import com.ff.business.CustomerTO;
import com.ff.domain.stockmanagement.masters.ItemDO;
import com.ff.domain.stockmanagement.masters.ItemTypeDO;
import com.ff.to.stockmanagement.StockDetailTO;
import com.ff.to.stockmanagement.StockHeaderTO;
import com.ff.universe.stockmanagement.util.StockUtility;

/**
 * The Class StockBeanUtil.
 *
 * @author mohammes
 */
public final class StockBeanUtil {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(StockBeanUtil.class);
	
	/**
	 * Prepare material details.
	 *
	 * @param stockDetail the stock detail
	 * @param itemDo the item do
	 * @param itemTypeDo the item type do
	 * @throws CGBusinessException the cG business exception
	 */
	public static void prepareMaterialDetails(StockDetailTO stockDetail,
			final ItemDO itemDo, final ItemTypeDO itemTypeDo)
			throws CGBusinessException {
		LOGGER.debug("StockBeanUtil::prepareMaterialDetails :: ######start######");
		Map<Integer,String> item= new HashMap<>(1);
		Map<Integer,String> itemType= new HashMap<>(1);
		if(!StringUtil.isNull(itemDo)){
			if(!StringUtil.isNull(itemTypeDo)){//Item specific details
				stockDetail.setItemId(itemDo.getItemId());
				stockDetail.setDescription(itemDo.getDescription());
				stockDetail.setUom(itemDo.getUom());
				stockDetail.setItemTypeId(itemTypeDo.getItemTypeId());
				stockDetail.setIsItemHasSeries(itemTypeDo.getItemHasSeries().trim());
				stockDetail.setSeriesLength(itemDo.getSeriesLength());
				stockDetail.setSeries(itemDo.getItemSeries());

				/*String seriesType=itemTypeDo.getItemTypeCode();
				if(!StringUtil.isStringEmpty(seriesType)&& seriesType.equalsIgnoreCase(UdaanCommonConstants.SERIES_TYPE_CNOTES)){
					stockDetail.setSeriesType(seriesType);
				}else{
					stockDetail.setSeriesType(itemDo.getItemCode());
				}*/
				stockDetail.setSeriesType(itemTypeDo.getItemTypeCode());
			}else{
				//ItemTypeDO does not exist
				//throw Exception
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.DETAILS_DOES_NOT_EXIST,new String[]{StockCommonConstants.STOCK_ITEM_TYPE_DTLS_NOT_EXIST});
				}
		}else{
			//ItemDO does not exist
			//throw Exception
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.DETAILS_DOES_NOT_EXIST,new String[]{StockCommonConstants.STOCK_ITEM_DTLS_NOT_EXIST});
		}
		
		///item.put(itemDo.getItemId(), itemDo.getItemName());//for Dropdown details
		String itemName=itemDo.getItemCode()+FrameworkConstants.CHARACTER_HYPHEN+itemDo.getDescription();
		item.put(itemDo.getItemId(), itemName.replaceAll(",", ""));
		itemType.put(itemTypeDo.getItemTypeId(), itemTypeDo.getItemTypeName());//for Dropdown details
		stockDetail.setItem(item);//for Dropdown details
		stockDetail.setItemType(itemType);//for Dropdown details
		LOGGER.debug("StockBeanUtil::prepareMaterialDetails :: ######END######");
	}
	
	/**
	 * Sets the business exception4 user.
	 *
	 * @param returnTO the new business exception4 user
	 */
	public static void setBusinessException4User(StockHeaderTO returnTO) {
		returnTO.setCanUpdate(StockCommonConstants.CAN_UPDATE);
		MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.UN_AUTHORIZED_USER, MessageType.Warning);
		returnTO.setBusinessException(new CGBusinessException(msgWrapper));
	}
	
	public static Map<Integer, String> prepareCustomerMap(List<CustomerTO> result) {
		return StockUtility.prepareCustomerMap(result);
	}
	
	public static Map<Integer, CustomerTO> prepareCustomerListMap(List<CustomerTO> result) {
		Map<Integer, CustomerTO> baMapDtls;
		baMapDtls= new HashMap<>(result.size());
		for(CustomerTO baTo:result){
			baMapDtls.put(baTo.getCustomerId(),baTo);
		}
		return baMapDtls;
	}
	
	public static List<String> getStockExcelHeaderList() {

		List<String> headerList = new ArrayList<String>();
		headerList.add(StockExcelUploadConstants.RECEIPT_OFFICE);
		headerList.add(StockExcelUploadConstants.RECEIPT_DATE);
		headerList.add(StockExcelUploadConstants.ITEM_CODE);
		headerList.add(StockExcelUploadConstants.RECEIPT_QUANTITY);
		headerList.add(StockExcelUploadConstants.START_SERIES);
		return headerList;
	}
	public static Boolean isValidStockExcelHeader(List<String> excelHeaderList) {

		boolean result = true;
		List<String> headerLst = getStockExcelHeaderList();
		if (excelHeaderList != null && !excelHeaderList.isEmpty()
				&& (headerLst.size() == excelHeaderList.size())) {
			for (int i = 0; i < headerLst.size(); i++) {
				if (!headerLst.get(i).equalsIgnoreCase(excelHeaderList.get(i))) {
					result = false;
					break;
				}
			}
		} else
			result = false;
		return result;
	}
	
	
}
