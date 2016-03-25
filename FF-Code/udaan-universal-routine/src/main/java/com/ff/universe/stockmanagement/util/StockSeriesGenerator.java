/**
 * 
 */
package com.ff.universe.stockmanagement.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.to.stockmanagement.StockIssueValidationTO;
import com.ff.to.stockmanagement.stockrequisition.StockValidationTO;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.constant.UniversalErrorConstants;

/**
 * The Class StockSeriesGenerator.
 *
 * @author mohammes
 */
public class StockSeriesGenerator {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
	.getLogger(StockSeriesGenerator.class);

/*
* This method will calculate the list of serial numbers depending on the number which you have input 
* Useful for Cnotes
*/
	
	/**
 * Calculate series info for cnote.
 *
 * @param startSerialNUmber the start serial n umber
 * @param seriesLength the series length
 * @return the stock validation to
 * @throws NumberFormatException the number format exception
 */
public static StockValidationTO calculateSeriesInfoForCnote(String startSerialNUmber, Integer seriesLength)
			throws NumberFormatException {
		LOGGER.info("StockSeriesGenerator ::calculateSeriesInfoForCnote "+"[startSerialNUmber :"+startSerialNUmber+"]\t"+"[seriesLength :"+seriesLength+"]");
		StockValidationTO seriesTO=new StockValidationTO();
		List<String> siNums=null;
		List<Long> leafList=null;
		String officeProduct=null;
		siNums=new ArrayList<String>(seriesLength);
		leafList=new ArrayList<Long>(seriesLength);

		officeProduct = getOfficeProductDtls(startSerialNUmber);
		LOGGER.debug("StockSeriesGenerator ::calculateSeriesInfoForCnote "+"[officeProduct :"+officeProduct+"]");
		String array[]=	startSerialNUmber.split(officeProduct);
		String startNum=array[1];
		Long endleaf=null;
		officeProduct = officeProduct.toUpperCase();
		int len=startNum.length();
		Long startLeaf=Long.parseLong(startNum);
		int len2=startLeaf.toString().length();
		int diff=len-len2;
		String format="";
		int zero=0;
		if(diff == zero){
			format="%"+len2+"d";
		}else{
			format="%0"+len+"d";
		}
		for(int counter=0;counter<seriesLength;counter++){
			Long leaf=startLeaf+counter;
			String formatted = String.format(format,leaf);  
			siNums.add(officeProduct+formatted);
			leafList.add(leaf);
		}

		seriesTO.setStartLeaf(startLeaf);
		seriesTO.setEndLeaf(endleaf);
		seriesTO.setOfficeProduct(officeProduct);
		seriesTO.setSeriesList(siNums);
		seriesTO.setLeafList(leafList);
		LOGGER.trace("StockSeriesGenerator ::calculateSeriesInfoForCnote "+"[startLeaf :"+startLeaf+"]"+"[ endleaf :"+endleaf+"] [officeProduct :"+officeProduct+"][siNums :"+siNums.toString()+"]");
		return seriesTO;
	}
	
	/**
	 * Global series calculater.
	 *
	 * @param startSerialNUmber the start serial n umber
	 * @param seriesLength the series length
	 * @return the list
	 * @throws NumberFormatException the number format exception
	 */
	public static List<String> globalSeriesCalculater(String startSerialNUmber, Integer seriesLength)
			throws NumberFormatException {
		LOGGER.info("StockSeriesGenerator ::calculateSeriesInfoForCnote "+"[startSerialNUmber :"+startSerialNUmber+"]\t"+"[seriesLength :"+seriesLength+"]");
		List<String> siNums=null;
		String officeProduct=null;
		siNums=new ArrayList<String>(seriesLength);

		officeProduct = getOfficeProductDtls(startSerialNUmber);
		LOGGER.debug("StockSeriesGenerator ::calculateSeriesInfoForCnote "+"[officeProduct :"+officeProduct+"]");
		String array[]=	startSerialNUmber.split(officeProduct);
		String startNum=array[1];
		officeProduct = officeProduct.toUpperCase();
		int len=startNum.length();
		Long startLeaf=Long.parseLong(startNum);
		int len2=startLeaf.toString().length();
		int diff=len-len2;
		String format="";
		int zero=0;
		if(diff == zero){
			format="%"+len2+"d";
		}else{
			format="%0"+len+"d";
		}
		for(int counter=0;counter<seriesLength;counter++){
			String formatted = String.format(format, startLeaf+counter);  
			siNums.add(officeProduct+formatted);

		}
		return siNums;
	}
	
	/*
	* This method will calculate the list of serial numbers depending on the number which you have input 
	* Useful for Cnotes
	*/
	/**
	 * Calculate series info for cnote.
	 *
	 * @param seriesTO the series to
	 * @return the stock validation to
	 * @throws NumberFormatException the number format exception
	 */
	public static StockValidationTO calculateSeriesInfoForCnote(StockValidationTO seriesTO)
			throws NumberFormatException {
		String startSerialNUmber=seriesTO.getStartSerialNumber();
		Integer seriesLength = seriesTO.getQuantity();
		List<String> siNums=null;
		List<Long> leafList=null;
		String officeProduct=null;
		siNums=new ArrayList<String>(seriesLength);
		leafList=new ArrayList<Long>(seriesLength);

		officeProduct = getOfficeProductDtls(startSerialNUmber);
		LOGGER.debug("StockSeriesGenerator ::calculateSeriesInfoForCnote "+"[officeProduct :"+officeProduct+"]");
		String array[]=	startSerialNUmber.split(officeProduct);
		String startNum=array[1];
		Long endleaf=null;
		officeProduct = officeProduct.toUpperCase();
		int len=startNum.length();
		Long startLeaf=Long.parseLong(startNum);
		int len2=startLeaf.toString().length();
		int diff=len-len2;
		String format="";
		int zero=0;
		if(diff == zero){
			format="%"+len2+"d";
		}else{
			format="%0"+len+"d";
		}
		for(int counter=0;counter<seriesLength;counter++){
			Long leaf=startLeaf+counter;
			String formatted = String.format(format,leaf);  
			siNums.add(officeProduct+formatted);
			if(counter == seriesLength-1 ){
				endleaf=Long.valueOf(startLeaf+counter);
			}
			leafList.add(leaf);
		}

		seriesTO.setStartLeaf(startLeaf);
		seriesTO.setEndLeaf(endleaf);
		seriesTO.setOfficeProduct(officeProduct);
		seriesTO.setSeriesList(siNums);
		seriesTO.setLeafList(leafList);
		LOGGER.debug("StockSeriesGenerator ::calculateSeriesInfoForCnote "+"[startLeaf :"+startLeaf+"]"+"[ endleaf :"+endleaf+"] [officeProduct :"+officeProduct+"][siNums :"+siNums.toString()+"]");
		return seriesTO;
	}
	
	/**
	 * Calculate series info.
	 *
	 * @param seriesTO the series to
	 * @return the stock validation to
	 * @throws NumberFormatException Purpose : calculate leaf information for All types of serial numbers
	 * @throws CGBusinessException 
	 */
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
		List<String> siNums=null;
		List<Long> leafList=null;
		String officeProduct=null;
		/** commented for  performance reasons*/
		//siNums=new ArrayList<String>(seriesLength);
		siNums=new ArrayList<String>(2);
		leafList=new ArrayList<Long>(seriesLength);

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
		int len2=startLeaf.toString().length();
		int diff=len-len2;
		String format="";
		int zero=0;
		if(diff == zero){
			format="%"+len2+"d";
		}else{
			format="%0"+len+"d";
		}
		/** commented for  performance reasons*/
		/*for(int counter=0;counter<seriesLength;counter++){
			Long leaf=startLeaf+counter;
			String formatted = String.format(format, leaf);  
			siNums.add(officeProduct+formatted);
			if(counter == seriesLength-1 ){
				endleaf=leaf;
				endSerialNumber=officeProduct+formatted;
			}
			leafList.add(leaf);
		}*/
		for(int counter=0;counter<seriesLength;counter++){
			Long leaf=startLeaf+counter;
			String formatted =null;
			if(counter==0 || counter == seriesLength-1 ){
				formatted = String.format(format, leaf);  
				siNums.add(officeProduct+formatted);
				if(counter == seriesLength-1 ){
					endleaf=leaf;
					endSerialNumber=officeProduct+formatted;
				}
			}
			
			leafList.add(leaf);
		}
		seriesTO.setStartLeaf(startLeaf);
		seriesTO.setEndLeaf(endleaf);
		seriesTO.setSeriesList(siNums);
		seriesTO.setLeafList(leafList);
		seriesTO.setEndSerialNumber(endSerialNumber);
		//LOGGER.trace("StockSeriesGenerator ::calculateSeriesInfo "+"[startLeaf :"+startLeaf+"]"+"[ endleaf :"+endleaf+"] [officeProduct :"+officeProduct+"][siNums :"+siNums.toString()+"]\t[leafList :"+leafList+"]");
		return seriesTO;
	}
	
	/**
	 * Calculate leaf info for cnote.
	 *
	 * @param startSerialNUmber the start serial n umber
	 * @param seriesLength the series length
	 * @return the stock validation to
	 * @throws NumberFormatException Purpose : calculate leaf information for Cnotes for given serial numbers
	 */
	public static StockValidationTO calculateLeafInfoForCnote(String startSerialNUmber, Integer seriesLength)
			throws NumberFormatException {
		StockValidationTO seriesTO=new StockValidationTO();

		String officeProduct=null;
		officeProduct = getOfficeProductDtls(startSerialNUmber);

		String array[]=	startSerialNUmber.split(officeProduct);
		String startNum=array[1];
		Long endleaf=null;
		officeProduct = officeProduct.toUpperCase();
		Long startLeaf=Long.parseLong(startNum);
		endleaf=Long.valueOf(startLeaf+(seriesLength-1));

		seriesTO.setStartLeaf(startLeaf);//set START leaf
		seriesTO.setEndLeaf(endleaf);//set END leaf
		seriesTO.setOfficeProduct(officeProduct);//set Office Product info
		LOGGER.debug("StockSeriesGenerator ::calculateLeafInfoForCnote "+"[startLeaf :"+startLeaf+"]"+"[ endleaf :"+endleaf+"] [officeProduct :"+officeProduct+"]");
		return seriesTO;
	}
	
	/**
	 * Calculate leaf info.
	 *
	 * @param to the to
	 * @throws NumberFormatException the number format exception
	 */
	public static void calculateLeafInfo(StockIssueValidationTO to)
			throws NumberFormatException {
		String startSerialNUmber=to.getStockItemNumber();
		String officeProduct=null;
		officeProduct = getOfficeProductDtls(startSerialNUmber);

		String array[]=	startSerialNUmber.split(officeProduct);
		String startNum=array[1];
		officeProduct = officeProduct.toUpperCase();
		Long startLeaf=Long.parseLong(startNum);
		to.setLeaf(startLeaf);//set  leaf
		to.setOfficeProductCode(officeProduct);//set Office Product info
		LOGGER.debug("StockSeriesGenerator ::calculateLeafInfo "+"[leaf :"+startLeaf+"][officeProduct :"+officeProduct+"]");
	}
	
	/**
	 * Gets the office product dtls.
	 *
	 * @param startSerialNUmber the start serial n umber
	 * @return the office product dtls
	 */
	public static String getOfficeProductDtls(String startSerialNUmber) {
		String officeProduct;
		if(Character.isLetter(startSerialNUmber.charAt(4))){
			officeProduct= startSerialNUmber.substring(0, 5);
		}else{
			officeProduct= startSerialNUmber.substring(0, 4);
		}
		if(!StringUtil.isStringEmpty(officeProduct)){
			officeProduct= officeProduct.trim().toUpperCase();
		}
		return officeProduct;
	}
	
	/**
	 * Checks if is normal cnote.
	 *
	 * @param startSerialNUmber the start serial n umber
	 * @return true, if is normal cnote
	 */
	public static boolean isNormalCnote(String startSerialNUmber) {
		if(Character.isLetter(startSerialNUmber.charAt(4))){
			return false;
		}
		return true;
	}
	
	/**
	 * Gets the product dtls.
	 *
	 * @param startSerialNUmber the start serial n umber
	 * @return the product dtls
	 */
	public static String getProductDtls(String startSerialNUmber) {
		String product=null;
		if(Character.isLetter(startSerialNUmber.charAt(4))){
			product=startSerialNUmber.charAt(4)+"";
			product= product.toUpperCase();
		}
		return product;
	}
	
	/**
	 * Prepare leaf details for series.
	 *
	 * @param validationTo the validation to
	 * @throws CGBusinessException the cG business exception
	 */
	public static void prepareLeafDetailsForSeries(StockValidationTO validationTo) throws CGBusinessException{
		StockIssueValidationTO to= new StockIssueValidationTO();
		validationTo.setStartSerialNumber(validationTo.getStartSerialNumber().toUpperCase());
		to.setStockItemNumber(validationTo.getStartSerialNumber());
		to.setSeriesType(validationTo.getSeriesType());
		to.setRegionCode(validationTo.getRegionCode());
		to.setRhoCode(validationTo.getRhoCode());
		to.setCityCode(validationTo.getCityCode());
		to.setOfficeCode(validationTo.getOfficeCode());
		to.setExpectedSeriesLength(validationTo.getExpectedSeriesLength());
		prepareLeafDetailsForSeries(to);
		if(to.getBusinessException()!=null){
			validationTo.setBusinessException(to.getBusinessException());
			return;
		}
		validationTo.setOfficeProduct(to.getOfficeProductCode());
		validationTo.setStartLeaf(to.getLeaf());
		calculateSeriesInfo(validationTo);
		to=null;//nullify the object
	}
	
	/**
	 * Prepare leaf details for series.
	 *
	 * @param validationTo the validation to
	 * @throws CGBusinessException the cG business exception
	 */
	public static void prepareLeafDetailsForSeries(StockIssueValidationTO validationTo) throws CGBusinessException{
		Integer length=null;
		String series=validationTo.getStockItemNumber();
		String type=validationTo.getSeriesType();
		String regionCode=validationTo.getRegionCode();
		String cityCode=validationTo.getCityCode();
		String product=null;//the final value is plant +product
		String expProduct=null;
		if(!StringUtil.isStringEmpty(type)){
			switch(type){
			case UdaanCommonConstants.SERIES_TYPE_CNOTES:
				//format :Office Code(4 digits)+Product+7 digits ;length :12
				length = getExpectedLengthOfSeries(validationTo);
				if(StringUtil.isEmptyInteger(length)){
				length=UdaanCommonConstants.CN_LENGTH;
				}
				product=StockSeriesGenerator.getOfficeProductDtls(series);
				break;
			case UdaanCommonConstants.SERIES_TYPE_OGM_STICKERS:
				//format : City Code+7 digits ;length :10
				product=cityCode;
				length = getExpectedLengthOfSeries(validationTo);
				if(StringUtil.isEmptyInteger(length)){
				length=UdaanCommonConstants.STICKER_LENGTH;
				}
				if(StringUtil.isStringEmpty(cityCode)){
					validationTo.setBusinessException(new CGBusinessException(UniversalErrorConstants.CITY_CODE_EMPTY));
					break;
				}
				if(!series.startsWith(cityCode)){
					//throw Exception product mismatch Series must match starts with regionCode
					validationTo.setBusinessException(new CGBusinessException(UniversalErrorConstants.SERIES_MUST_STARTS_WITH_CITY));
					//throw new CGBusinessException(UniversalErrorConstants.SERIES_MUST_STARTS_WITH_REGION);
				}
				
				break;
			case UdaanCommonConstants.SERIES_TYPE_BPL_STICKERS://BPL Number
				//format : City Code+B+7 digits ;length :10
				if(StringUtil.isStringEmpty(cityCode)){
					validationTo.setBusinessException(new CGBusinessException(UniversalErrorConstants.CITY_CODE_EMPTY));
					break;
				}
				product=getProductFromSeries(series,cityCode);
				length = getExpectedLengthOfSeries(validationTo);
				if(StringUtil.isEmptyInteger(length)){
				length=UdaanCommonConstants.STICKER_LENGTH;
				}
				expProduct=UdaanCommonConstants.SERIES_TYPE_BPL_STICKERS_PRODUCT;
				if(!product.equalsIgnoreCase(expProduct)){
					//throw Exception product mismatch
					validationTo.setBusinessException( new CGBusinessException(UniversalErrorConstants.PRODUCT_MIS_MATCH));
					//throw new CGBusinessException(UniversalErrorConstants.PRODUCT_MIS_MATCH);
					break;
				}
				product=cityCode+expProduct;
				break;
			case UdaanCommonConstants.SERIES_TYPE_MBPL_STICKERS://MBPL Number
				//format : City Code+M+7 digits ;length :10
				if(StringUtil.isStringEmpty(cityCode)){
					validationTo.setBusinessException(new CGBusinessException(UniversalErrorConstants.CITY_CODE_EMPTY));
					break;
				}
				product=getProductFromSeries(series,cityCode);
				length = getExpectedLengthOfSeries(validationTo);
				if(StringUtil.isEmptyInteger(length)){
				length=UdaanCommonConstants.STICKER_LENGTH;
				}
				expProduct=UdaanCommonConstants.SERIES_TYPE_MBPL_STICKERS_PRODUCT;
				if(!product.equalsIgnoreCase(expProduct)){
					//throw Exception product mismatch
					validationTo.setBusinessException( new CGBusinessException(UniversalErrorConstants.PRODUCT_MIS_MATCH));
					//throw new CGBusinessException(UniversalErrorConstants.PRODUCT_MIS_MATCH);
					break;
				}
				product=cityCode+expProduct;
				break;

			case UdaanCommonConstants.SERIES_TYPE_CO_MAIL_NO:
				//Format:CM+10 digits ;length :12
				product=UdaanCommonConstants.SERIES_TYPE_CO_MAIL_NO_PRODUCT;
				length = getExpectedLengthOfSeries(validationTo);
				if(StringUtil.isEmptyInteger(length)){
					length=UdaanCommonConstants.CO_MAIL_LENGTH;
				}
				if(!series.startsWith(UdaanCommonConstants.SERIES_TYPE_CO_MAIL_NO_PRODUCT)){
					//throw Exception series must starts with UdaanCommonConstants.SERIES_TYPE_CO_MAIL_NO_PRODUCT
					validationTo.setBusinessException(new CGBusinessException(UniversalErrorConstants.SERIES_MUST_STARTS_WITH_CM));
					//throw new CGBusinessException(UniversalErrorConstants.SERIES_MUST_STARTS_WITH +CommonConstants.CHARACTER_COLON +UdaanCommonConstants.SERIES_TYPE_CO_MAIL_NO_PRODUCT);
					break;
				}
				
				break;
			case UdaanCommonConstants.SERIES_TYPE_BAG_LOCK_NO:
				//Format:Region Code+7Digits
				length = getExpectedLengthOfSeries(validationTo);
				if(StringUtil.isEmptyInteger(length)){
					length=UdaanCommonConstants.BAG_LOCK_LENGTH;
				}
				if(StringUtil.isStringEmpty(regionCode)){
					validationTo.setBusinessException(new CGBusinessException(UniversalErrorConstants.REGION_CODE_EMPTY));
					break;
				}
				//product= regionCode+UdaanCommonConstants.SERIES_TYPE_BAG_LOCK_NO_PRODUCT;
				product= regionCode;
				if(!series.startsWith(product)){
					validationTo.setBusinessException(new CGBusinessException(UniversalErrorConstants.SERIES_MUST_STARTS_WITH_REGION));
					break;
				}
				break;

			}
			if(series!=null && length!=null && series.length()!=length.intValue()){
				//throw Exception Invalid length
				validationTo.setBusinessException(new CGBusinessException(UniversalErrorConstants.INVALID_SERIES_LENGTH));
				//throw new CGBusinessException(UniversalErrorConstants.INVALID_SERIES_LENGTH);
			}

		}
		//Set Office Product details
		validationTo.setOfficeProductCode(product);
		if(validationTo.getBusinessException()!=null){
			return;
		}
		//calculate leaf details
		getLeafInfoForSeries(validationTo);
		validationTo.setSeriesLength(length);
	}

	/**
	 * @param validationTo
	 * @param length
	 * @return
	 */
	private static Integer getExpectedLengthOfSeries(
			StockIssueValidationTO validationTo) {
		Integer length=null;
		if(!StringUtil.isEmptyInteger(validationTo.getExpectedSeriesLength())){
			length=validationTo.getExpectedSeriesLength();
		}
		return length;
	}
	
	/**
	 * Gets the product from series.
	 *
	 * @param series the series
	 * @param region the region
	 * @return the product from series
	 */
	private static String getProductFromSeries(String series,String region){
		return series.charAt(region.length())+"";
	}
	
	
	/**
	 * Gets the leaf info for series.
	 *
	 * @param to the to
	 * @return the leaf info for series
	 * @throws NumberFormatException the number format exception
	 */
	public static void getLeafInfoForSeries(StockIssueValidationTO to)
			throws NumberFormatException {
		String startSerialNUmber=to.getStockItemNumber();
		String officeProduct=to.getOfficeProductCode();
		String array[]=	startSerialNUmber.split(officeProduct);
		String startNum=array[1];
		officeProduct = officeProduct.toUpperCase();
		Long startLeaf=Long.parseLong(startNum);
		to.setLeaf(startLeaf);//set  leaf
		//logger.debug("StockSeriesGenerator ::calculateSeriesInfo "+"[leaf :"+startLeaf+"][officeProduct :"+officeProduct+"]");
	}
	
	/*public static void main(String[] args){
	String a1="A00000000";
	String a2="100000000000";
	String a3="A999A00000000";
	
//	System.out.println("calculateSeriesInfo :"+a2+" : "+calculateSeriesInfo(a3,5));
//	System.out.println("calculateSeriesInfo :"+a1+" : "+calculateSeriesInfo(a1,5));
//	System.out.println("calculateSeriesInfo :"+a1+" : "+calculateSeriesInfo(a3,5));
//	System.out.println("calculateSeriesInfo :"+a1+" : "+calculateLeafInfo(a3,5));
	System.out.println("calculateSeriesInfo :"+a1+" : "+getProductDtls(a3));
}*/
	
}
