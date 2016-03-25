/**
 * 
 */
package com.ff.admin.stockmanagement.excelupload.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts.upload.FormFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.exception.MessageType;
import com.capgemini.lbs.framework.exception.MessageWrapper;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGExcelUploadUtil;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.stockmanagement.common.constants.StockCommonConstants;
import com.ff.admin.stockmanagement.common.service.StockCommonService;
import com.ff.admin.stockmanagement.common.util.StockBeanUtil;
import com.ff.admin.stockmanagement.excelupload.constants.StockExcelUploadConstants;
import com.ff.admin.stockmanagement.excelupload.dao.StockExcelUploadDAO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.stockmanagement.masters.ItemDO;
import com.ff.domain.stockmanagement.masters.ItemTypeDO;
import com.ff.domain.stockmanagement.operations.receipt.StockReceiptDO;
import com.ff.domain.stockmanagement.operations.receipt.StockReceiptItemDtlsDO;
import com.ff.geography.CityTO;
import com.ff.organization.OfficeTO;
import com.ff.to.stockmanagement.StockExcelUploadMaterialTO;
import com.ff.to.stockmanagement.StockExcelUploadTO;
import com.ff.to.stockmanagement.masters.ItemTO;
import com.ff.to.stockmanagement.stockrequisition.StockValidationTO;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.constant.UniversalErrorConstants;

/**
 * @author mohammes
 *
 */
public class StockExcelUploadServiceImpl implements StockExcelUploadService {
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(StockExcelUploadServiceImpl.class);
	

	private StockExcelUploadDAO stockExcelUploadDAO;
	private StockCommonService stockCommonService;

	

	/**
	 * @return the stockCommonService
	 */
	public StockCommonService getStockCommonService() {
		return stockCommonService;
	}

	/**
	 * @return the stockExcelUploadDAO
	 */
	public StockExcelUploadDAO getStockExcelUploadDAO() {
		return stockExcelUploadDAO;
	}

	/**
	 * @param stockExcelUploadDAO the stockExcelUploadDAO to set
	 */
	public void setStockExcelUploadDAO(StockExcelUploadDAO stockExcelUploadDAO) {
		this.stockExcelUploadDAO = stockExcelUploadDAO;
	}

	/**
	 * @param stockCommonService the stockCommonService to set
	 */
	public void setStockCommonService(StockCommonService stockCommonService) {
		this.stockCommonService = stockCommonService;
	}
	
	@Override
	public Boolean saveStockExcelUpload(StockExcelUploadTO to)throws CGBusinessException,CGSystemException{
		Boolean result=false;
		LOGGER.debug("StockExcelUploadServiceImpl ::saveStockExcelUpload::######### start#########");
		List<StockReceiptDO> receiptDOList=null;
		
		FormFile exlFile=to.getStockExcelFile();
		if(exlFile!=null){
			receiptDOList= prepareStockReceiptFromExcel(to, exlFile);
		}else{
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.STOCK_EXCEL_UPLOAD_INVALID);
		}
		if(!CGCollectionUtils.isEmpty(receiptDOList)){
			LOGGER.debug("StockExcelUploadServiceImpl ::saveStockExcelUpload::about  save Stock ReceiptDO List ");
			result=stockExcelUploadDAO.saveStockReceiptList(receiptDOList);
			LOGGER.debug("StockExcelUploadServiceImpl ::saveStockExcelUpload::  saved Stock ReceiptDO List status:["+result+"]");
		}else{
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.STOCK_EXCEL_UPLOAD_INVALID);
		}
		LOGGER.debug("StockExcelUploadServiceImpl ::saveStockExcelUpload::######### END#########");
		return result;
	}

	private List<StockReceiptDO> prepareStockReceiptFromExcel(StockExcelUploadTO to,
			FormFile exlFile) throws CGBusinessException, CGSystemException {
		List<StockExcelUploadMaterialTO> stockMaterialTOList = null;
		List<StockReceiptDO> receiptDOList=null;
		Set<String> stockReceiptNoList=null;
		
		List<List> uploadedStockExcelDtls = CGExcelUploadUtil
				.getAllRowsValues(to.getFilePath()+exlFile.getFileName(), exlFile);
		if(!CGCollectionUtils.isEmpty(uploadedStockExcelDtls)){
			if(uploadedStockExcelDtls.size()==1){
				ExceptionUtil.prepareBusinessException(AdminErrorConstants.STOCK_EXCEL_UPLOAD_INVALID);
			}
			List<String> excelHeaderList = uploadedStockExcelDtls.get(0);
			if (StockBeanUtil.isValidStockExcelHeader(excelHeaderList)) {
				stockMaterialTOList = new ArrayList<>(uploadedStockExcelDtls.size());
				int i=0;
				Map<String, OfficeTO> officeMap= new HashMap<>(uploadedStockExcelDtls.size());
				Map<String, CityTO> cityMap= new HashMap<>(uploadedStockExcelDtls.size());
				Map<String, ItemTO> itemMap= new HashMap<>(uploadedStockExcelDtls.size());
				
				for (List<String> stockMaterials : uploadedStockExcelDtls) {
					boolean isRho=false;
					if(i==0){
						i++;
						continue;
					}

					if(stockMaterials.size()!=5){
						ExceptionUtil.prepareBusinessException(AdminErrorConstants.STOCK_EXCEL_UPLOAD_INVALID);
					}

					String receiptOfficeCode=stockMaterials.get(StockExcelUploadConstants.RECEIPT_OFFICE_INDEX);
					String receipDate=stockMaterials.get(StockExcelUploadConstants.RECEIPT_DATE_INDEX);
					String itemCode=stockMaterials.get(StockExcelUploadConstants.ITEM_CODE_INDEX);
					String quantity=stockMaterials.get(StockExcelUploadConstants.RECEIPT_QUANTITY_INDEX);
					String startSerial=stockMaterials.get(StockExcelUploadConstants.START_SERIES_INDEX);
					OfficeTO officeTO=null;
					OfficeTO reportingOfficeTO=null;
					Integer stockReceiptQnty=null;
					String officeProduct=null;
					Integer expectedSeriesLength=0;
					String cityCode=null;
					CityTO cityTO=null;
					if(StringUtil.isStringEmpty(receiptOfficeCode) || StringUtil.isStringEmpty(receipDate) || StringUtil.isStringEmpty(itemCode) || StringUtil.isStringEmpty(quantity)){
						ExceptionUtil.prepareBusinessException(AdminErrorConstants.STOCK_EXCEL_UPLOAD_INVALID);
					}
					if(StringUtil.isStringContainsInteger(quantity)){
						stockReceiptQnty=StringUtil.parseInteger(quantity);
					}else{
						ExceptionUtil.prepareBusinessException(AdminErrorConstants.STOCK_EXCEL_UPLOAD_INVALID);
					}
					if(!StringUtil.isStringEmpty(startSerial)){
						startSerial=startSerial.trim().toUpperCase();
					}
					if(!StringUtil.isStringEmpty(itemCode)){
						itemCode=itemCode.trim().toUpperCase();
					}
					if(!StringUtil.isStringEmpty(receiptOfficeCode)){
						receiptOfficeCode=receiptOfficeCode.trim().toUpperCase();
					}
					ItemTO itemTO=null;
					if(itemMap.containsKey(itemCode)){
						itemTO=itemMap.get(itemCode);
					}else{
						itemTO= new ItemTO();
						itemTO.setItemCode(itemCode);
						List<ItemTO> itemTOList=stockCommonService.getAllItemDetails(itemTO);
						if(!CGCollectionUtils.isEmpty(itemTOList)){
							itemTO=itemTOList.get(0);
						}else{
							ExceptionUtil.prepareBusinessException(AdminErrorConstants.STOCK_EXCEL_UPLOAD_INVALID);
						}
						if(itemTO ==null || StringUtil.isEmptyInteger(itemTO.getItemId()) || itemTO.getItemTypeTO() == null){
							ExceptionUtil.prepareBusinessException(AdminErrorConstants.STOCK_EXCEL_UPLOAD_INVALID);
						}else{
							itemMap.put(itemCode, itemTO);
						}
					}
					if(officeMap.containsKey(receiptOfficeCode)){
						officeTO=officeMap.get(receiptOfficeCode);
					}else{
						//get Office Details
						officeTO=stockCommonService.getOfficeDtlsByOfficeCode(receiptOfficeCode);
					}
					if(officeTO!=null){
						officeMap.put(receiptOfficeCode, officeTO);
						if(officeTO.getOfficeTypeTO()!=null && !StringUtil.isStringEmpty(officeTO.getOfficeTypeTO().getOffcTypeCode()) && officeTO.getOfficeTypeTO().getOffcTypeCode().equalsIgnoreCase(CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE) ){
							//ExceptionUtil.prepareBusinessException(AdminErrorConstants.STOCK_EXCEL_UPLOAD_INVALID);
							isRho=true;
						}
						if(!isRho){
							reportingOfficeTO=stockCommonService.getOfficeDetails(officeTO.getReportingRHO());
							if(reportingOfficeTO==null){
								ExceptionUtil.prepareBusinessException(AdminErrorConstants.STOCK_EXCEL_UPLOAD_INVALID);
							}
							officeMap.put(reportingOfficeTO.getOfficeCode(), reportingOfficeTO);
						}
					}else{
						ExceptionUtil.prepareBusinessException(AdminErrorConstants.STOCK_EXCEL_UPLOAD_INVALID);
					}


					StockExcelUploadMaterialTO materialTO= new StockExcelUploadMaterialTO();
					materialTO.setIssuedQuantity(stockReceiptQnty);
					materialTO.setReceivedQuantity(stockReceiptQnty);
					materialTO.setRequestedQuantity(stockReceiptQnty);
					materialTO.setApprovedQuantity(stockReceiptQnty);

					StockValidationTO validationTo= new StockValidationTO();
					validationTo.setStartSerialNumber(startSerial);
					validationTo.setRegionCode(officeTO.getRegionTO()!=null ?officeTO.getRegionTO().getRegionCode():null);
					validationTo.setQuantity(stockReceiptQnty);
					validationTo.setExpectedSeriesLength(itemTO.getSeriesLength());
					validationTo.setSeriesType(itemTO.getItemTypeTO().getItemTypeCode());


					switch(itemTO.getItemTypeTO().getItemTypeCode()){
					case UdaanCommonConstants.SERIES_TYPE_CNOTES:
						//format :Office Code(4 digits)+Product+7 digits ;length :12
						String rhoCodeForCn=null;
						if(StringUtil.isStringEmpty(itemTO.getItemSeries())){
							if(isRho){
								rhoCodeForCn=officeTO.getOfficeCode();
							}else{
								rhoCodeForCn=startSerial.substring(0, 4);
							}
							officeProduct=rhoCodeForCn;
						}else{
							if(isRho){
								rhoCodeForCn=officeTO.getOfficeCode();
							}else{
								if(reportingOfficeTO!=null){
									rhoCodeForCn=reportingOfficeTO.getOfficeCode();
								}
							}
							officeProduct=rhoCodeForCn+itemTO.getItemSeries();

						}

						validationTo.setRhoCode(rhoCodeForCn);


						break;
					case UdaanCommonConstants.SERIES_TYPE_OGM_STICKERS:
						//format : City Code+7 digits ;length :10
						cityCode=startSerial.substring(0, 3);
						officeProduct=cityCode;
						break;
					case UdaanCommonConstants.SERIES_TYPE_BPL_STICKERS://BPL Number
						//format : City Code+B+7 digits ;length :10
						cityCode=startSerial.substring(0, 3);
						officeProduct=cityCode+"B";
						break;
					case UdaanCommonConstants.SERIES_TYPE_MBPL_STICKERS://MBPL Number
						//format : City Code+M+7 digits ;length :10
						cityCode=startSerial.substring(0, 3);
						officeProduct=cityCode+"M";
						break;

					case UdaanCommonConstants.SERIES_TYPE_CO_MAIL_NO:
						//Format:CM+10 digits ;length :12
						officeProduct="CM";

						break;
					case UdaanCommonConstants.SERIES_TYPE_BAG_LOCK_NO:
						//Format:Region Code+7Digits
						officeProduct=validationTo.getRegionCode();
						break;

					}

					if(!StringUtil.isStringEmpty(cityCode)){
						if(cityMap.containsKey(cityCode)){
							cityTO=cityMap.get(cityCode);
						}else{
							cityTO=stockCommonService.getCityById(null, cityCode);
						}
						if(cityTO==null){
							//throw Exception
							ExceptionUtil.prepareBusinessException(AdminErrorConstants.STOCK_EXCEL_UPLOAD_INVALID);
						}else{
							cityMap.put(cityCode, cityTO);
						}

					}	
					validationTo.setCityCode(cityCode);
					validationTo.setOfficeProduct(officeProduct);

					if(!StringUtil.isStringEmpty(startSerial)){
						expectedSeriesLength=itemTO.getSeriesLength();
						if(startSerial.length() != expectedSeriesLength.intValue()){
							ExceptionUtil.prepareBusinessException(AdminErrorConstants.STOCK_EXCEL_INVALID_SERIES_LENGTH);
						}
						if(!startSerial.startsWith(officeProduct)){
							ExceptionUtil.prepareBusinessException(AdminErrorConstants.STOCK_EXCEL_INVALID_SERIES);
						}
						calculateSeriesInfo(validationTo);//prepare leaf info
						if(validationTo.getBusinessException()!=null){
							throw validationTo.getBusinessException();
						}
					}else{
						LOGGER.warn("StockExcelUploadServiceImpl ::getStockLeafDtls::Executing for Non-serialized materials ");
					}
					populateStockLeafDtls(materialTO, receiptOfficeCode,
							officeTO, itemTO, validationTo);
					stockMaterialTOList.add(materialTO);
				}
			}else{
				ExceptionUtil.prepareBusinessException(AdminErrorConstants.STOCK_EXCEL_UPLOAD_INVALID);
			}
			if(!CGCollectionUtils.isEmpty(stockMaterialTOList)){
				LOGGER.debug("StockExcelUploadServiceImpl ::save::prepareStockReceiptFromExcel::Prepared Leaf Details:: about to start Stock ReceiptDO preparation ");
				Map<String,StockReceiptDO> excelMap=null;
				 excelMap= new HashMap<>(stockMaterialTOList.size());
				 stockReceiptNoList=new HashSet(stockMaterialTOList.size());
				for(StockExcelUploadMaterialTO materialTO:stockMaterialTOList){
					if(excelMap.containsKey(materialTO.getStockReceiptOfficeCode())){
						StockReceiptDO receiptDO=excelMap.get(materialTO.getStockReceiptOfficeCode());
						prepareStockReceiptItemDtls(materialTO, receiptDO);

					}else{
						StockReceiptDO receiptDO = prepareStockReceiptHeader(
								to, materialTO);
						stockReceiptNoList.add(receiptDO.getAcknowledgementNumber());
						prepareStockReceiptItemDtls(materialTO, receiptDO);
						excelMap.put(materialTO.getStockReceiptOfficeCode(), receiptDO);

					}
				}
				receiptDOList=new ArrayList<>(excelMap.values());
				LOGGER.debug("StockExcelUploadServiceImpl ::save::prepareStockReceiptFromExcel::Prepared  Stock ReceiptDO List ");
			}
		}else{
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.STOCK_EXCEL_UPLOAD_INVALID);
		}
		if(!CGCollectionUtils.isEmpty(stockReceiptNoList)){
		to.setAcknowledgementNumber(stockReceiptNoList.toString().replaceAll("\\[", "").replaceAll("\\]", ""));
		LOGGER.debug("StockExcelUploadServiceImpl ::save::prepareStockReceiptFromExcel::Generated Number ",to.getAcknowledgementNumber());
		}
		return receiptDOList;
	}

	private void populateStockLeafDtls(StockExcelUploadMaterialTO materialTO,
			String receiptOfficeCode, OfficeTO officeTO, ItemTO itemTO,
			StockValidationTO validationTo) {
		materialTO.setStartLeaf(validationTo.getStartLeaf());
		materialTO.setEndLeaf(validationTo.getEndLeaf());
		materialTO.setEndSerialNumber(validationTo.getEndSerialNumber());
		materialTO.setOfficeProductCodeInSeries(validationTo.getOfficeProduct());
		materialTO.setStartSerialNumber(validationTo.getStartSerialNumber());
		materialTO.setItemId(itemTO.getItemId());
		materialTO.setItemTypeId(itemTO.getItemTypeTO().getItemTypeId());
		materialTO.setStockReceiptOfficeCode(receiptOfficeCode);
		materialTO.setStockReceiptOfficeId(officeTO.getOfficeId());
	}

	

	private StockReceiptDO prepareStockReceiptHeader(StockExcelUploadTO to,
			StockExcelUploadMaterialTO materialTO) throws CGBusinessException {
		StockReceiptDO receiptDO= new StockReceiptDO();
		OfficeDO receiptOffice= new OfficeDO();
		receiptOffice.setOfficeId(materialTO.getStockReceiptOfficeId());
		receiptOffice.setOfficeCode(materialTO.getStockReceiptOfficeCode());
		receiptDO.setReceiptOfficeId(receiptOffice);
		receiptDO.setCreatedBy(to.getLoggedInUserId());
		receiptDO.setUpdatedBy(to.getLoggedInUserId());
		receiptDO.setIssuedDate(DateUtil.getCurrentDate());
		receiptDO.setReceivedDate(DateUtil.getCurrentDate());
		receiptDO.setIssuedOfficeId(null);

		//generate the number
		String ackNumber = StringUtil.generateDDMMYYHHMMSSRamdomNumber();
		SequenceGeneratorConfigTO seqTo = prepareSeqGeneratorTO(receiptDO);
		try {
			ackNumber = stockCommonService.stockProcessNumberGenerator(seqTo);
			LOGGER.trace("StockExcelUploadServiceImpl ::prepareStockReceiptHeader::Generated Number ",ackNumber);
		} catch (Exception e) {
			LOGGER.error("StockExcelUploadServiceImpl ::prepareStockReceiptHeader::ERROR ",e);
			MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.PROBLEM_NUMBER_GENERATION, MessageType.Error,StockCommonConstants.STOCK_RECEIPT,new String[]{StockCommonConstants.STOCK_RECEIPT_NUM});
			throw new CGBusinessException(msgWrapper);
		} 
		receiptDO.setAcknowledgementNumber(ackNumber.trim().toUpperCase());
		receiptDO.setIssueNumber(receiptDO.getAcknowledgementNumber());
		receiptDO.setRequisitionNumber(receiptDO.getAcknowledgementNumber());
		
	
		return receiptDO;
	}

	private void prepareStockReceiptItemDtls(
			StockExcelUploadMaterialTO materialTO, StockReceiptDO receiptDO)
			throws CGBusinessException {
		StockReceiptItemDtlsDO receitpItemDtls= new StockReceiptItemDtlsDO();
		CGObjectConverter.createDomainFromTo(materialTO, receitpItemDtls);
		receiptDO.setNextNumber(receiptDO.getNextNumber()+1);
		receitpItemDtls.setRowNumber(receiptDO.getNextNumber());
		ItemDO itemDO= new ItemDO();
		itemDO.setItemId(materialTO.getItemId());
		receitpItemDtls.setItemDO(itemDO);
		ItemTypeDO itemTypeDO= new ItemTypeDO();
		itemTypeDO.setItemTypeId(materialTO.getItemTypeId());
		receitpItemDtls.setItemTypeDO(itemTypeDO);
		receitpItemDtls.setRemarks("Excel Uploaded");
		receitpItemDtls.setStockReceiptDO(receiptDO);
		if(receiptDO.getStockReceiptItemDtls()!=null){
			receiptDO.getStockReceiptItemDtls().add(receitpItemDtls);
		}else{
			Set<StockReceiptItemDtlsDO> receiptDtlsSet= new HashSet<>();
			receiptDtlsSet.add(receitpItemDtls);
			receiptDO.setStockReceiptItemDtls(receiptDtlsSet);
		}
	}
	/**
	 * Prepare seq generator to.
	 *
	 * @param receitpDO the to
	 * @return 	: SequenceGeneratorConfigTO
	 */
	private SequenceGeneratorConfigTO prepareSeqGeneratorTO(StockReceiptDO receitpDO) {
		SequenceGeneratorConfigTO seqTo = new SequenceGeneratorConfigTO();
		seqTo.setProcessRequesting(StockCommonConstants.PROCESS_ACKNOWLEDGE);
		seqTo.setRequestingBranchCode(receitpDO.getReceiptOfficeId().getOfficeCode());
		seqTo.setRequestingBranchId(receitpDO.getReceiptOfficeId().getOfficeId());
		seqTo.setSequenceRunningLength(StockCommonConstants.PROCESS_RUNNING_NUMBER);
		seqTo.setLengthOfNumber(StockCommonConstants.PROCESS_NUMBER_LENGTH);
		return seqTo;
	}
	
	public static StockValidationTO calculateSeriesInfo(StockValidationTO seriesTO)
			throws NumberFormatException, CGBusinessException {
		seriesTO.setStartSerialNumber(seriesTO.getStartSerialNumber().toUpperCase());
		String startSerialNUmber=seriesTO.getStartSerialNumber();
		Integer seriesLength = seriesTO.getQuantity();
		if(StringUtil.isEmptyInteger(seriesLength)){
			throw new CGBusinessException(
					UniversalErrorConstants.STOCK_QUANTITY_ZERO);
		}
		LOGGER.debug("StockSeriesGenerator ::calculateSeriesInfo "+"[startSerialNUmber :"+startSerialNUmber+"]\t"+"[Quantity :"+seriesLength+"]");
		
		String officeProduct=null;
		

		officeProduct = seriesTO.getOfficeProduct();
		LOGGER.debug("StockSeriesGenerator ::calculateSeriesInfo "+"[Product :"+officeProduct+"]\t");
		String array[]=	startSerialNUmber.split(officeProduct);
		//LOGGER.debug("StockSeriesGenerator ::calculateSeriesInfo "+"[Split array :"+array+"]\t");
		String startNum=array[1];
		LOGGER.debug("StockSeriesGenerator ::calculateSeriesInfo "+"[ array Index 0 :"+array[0]+"]\t"+"[ array Index 1 :"+startNum+"]\t");
		Long endleaf=null;
		String endSerialNumber=null;
		officeProduct = officeProduct.toUpperCase();
		int len=startNum.length();
		Long startLeaf=Long.parseLong(startNum);
		if(StringUtil.isEmptyLong(startLeaf)){
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.STOCK_EXCEL_INVALID_SERIES_LEAF);
		}
		int len2=startLeaf.toString().length();
		int diff=len-len2;
		String format="";
		int zero=0;
		if(diff == zero){
			format="%"+len2+"d";
		}else{
			format="%0"+len+"d";
		}
		endleaf=new Long(seriesLength+startLeaf.intValue()-1);
		if(endleaf <= 0L){
			endleaf=startLeaf;
		}
		String formatted = String.format(format, endleaf);  
		
			endSerialNumber=officeProduct+formatted;
			if(endSerialNumber.length()!=startSerialNUmber.length()){
				ExceptionUtil.prepareBusinessException(AdminErrorConstants.STOCK_EXCEL_INVALID_END_SERIES);
			}
		seriesTO.setStartLeaf(startLeaf);
		seriesTO.setEndLeaf(endleaf);
		seriesTO.setEndSerialNumber(endSerialNumber);
		//LOGGER.trace("StockSeriesGenerator ::calculateSeriesInfo "+"[startLeaf :"+startLeaf+"]"+"[ endleaf :"+endleaf+"] [officeProduct :"+officeProduct+"][siNums :"+siNums.toString()+"]\t[leafList :"+leafList+"]");
		return seriesTO;
	}
	
	/*public static void main(String args[]) throws NumberFormatException, CGBusinessException{
		StockValidationTO validationTo= new StockValidationTO();
		validationTo.setStartSerialNumber("B991M0000010");
		validationTo.setRegionCode("B991");
		validationTo.setQuantity(10);
		validationTo.setOfficeCode("B991");
		validationTo.setOfficeProduct("B991M");
		validationTo.setExpectedSeriesLength(12);
		calculateSeriesInfo(validationTo);
	}*/
}
