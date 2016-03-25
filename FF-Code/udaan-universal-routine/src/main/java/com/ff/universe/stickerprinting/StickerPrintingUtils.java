package com.ff.universe.stickerprinting;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.stickerprinting.StickerPrintingTO;
import com.ff.universe.business.constant.BusinessUniversalConstants;

public class StickerPrintingUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(StickerPrintingUtils.class);
	
	public static List<StickerPrintingTO>  printStickers(List<String> barcodeTextList, String cnBranch, String imagePath) {
		if(barcodeTextList == null || imagePath == null)
			return null;
		
		if(cnBranch == null)
			cnBranch = "";
		
		List<StickerPrintingTO> barcodeList = new ArrayList<StickerPrintingTO>(barcodeTextList.size());
		if(barcodeTextList!=null && !barcodeTextList.isEmpty()){
			for (String barcodeText : barcodeTextList) {
				StickerPrintingTO stickerPrintTO = printStickers(barcodeText, cnBranch, imagePath);
				barcodeList.add(stickerPrintTO);
			}
		}
		return barcodeList;
	}
	
	public static StickerPrintingTO  printStickers(String barcodeText, String cnBranch, String imagePath) {
		if(barcodeText == null || imagePath == null)
			return null;
		
		if(cnBranch == null)
			cnBranch = "";
		
		StickerPrintingTO barcode = new StickerPrintingTO();
		
		//tempTO.setStartSlNo(imageName);
		
	//	File f=new File(imgFullPath);
		//File f=new File("D:\\First_Flight\\SVN_CODE\\Udaan_Main_Trunk\\udaan-web\\src\\main\\webapp\\"+cnImagePath);
		// StickerPrintingUtils.generateBarcode(imageName,f,);
		
		return barcode;
	}
	
	
	public static void  printStickers(String barcodeText, String imagePath) {
		if(barcodeText == null || imagePath == null)
			return;
		String imageName1 = barcodeText+ BusinessUniversalConstants._TOP;
		String imageName2 = barcodeText+BusinessUniversalConstants._BOTTOM;
		String imgFullPath1 = imagePath + imageName1 + ".png";
		String imgFullPath2 = imagePath + imageName2 + ".png";
		File f1=new File(imgFullPath1);
		File f2=new File(imgFullPath2);
		StickerPrintingUtils.generateBarcode(barcodeText,f1,f2);
	}
	public static void generateBarcode(String codeDigits,File outputFile1, File outputFile2){
		LOGGER.debug("StickerPrintingUtils::generateBarcode::Start=======>");
		try {
			Code128Bean bean = new Code128Bean();
			
			final int dpi = 100;

			//Configure the barcode generator
			
			//makes the narrow bar - width exactly one pixel
			bean.setModuleWidth(UnitConv.in2mm(1.0f / dpi));  
			//bean.setWideFactor(1.5);
			//bean.setHeight(15);
			//bean.setBarHeight(11);
			//bean.doQuietZone(false);
			//bean.setFontSize(6);
			//bean.setModuleWidth(50);
			
			bean.setMsgPosition(HumanReadablePlacement.HRP_TOP);
			//bean.setBarHeight(height)
			//bean.setHeight(3*1000);
			//bean.setModuleWidth(20*1000);

			//Stream output file
			OutputStream out = new FileOutputStream(outputFile1);
	
			try {
				//Set up the canvas provider for monochrome JPEG output 
				BitmapCanvasProvider canvas = new BitmapCanvasProvider(out, "image/png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
				bean.generateBarcode(canvas, codeDigits);
				canvas.finish();
				out.flush();
				out.close();
				
				// 2nd immage
				out = new FileOutputStream(outputFile2);
				canvas = new BitmapCanvasProvider(out, "image/png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
				bean.setMsgPosition(HumanReadablePlacement.HRP_BOTTOM);
				//bean.setHeight(5.8);
				bean.generateBarcode(canvas, codeDigits);
				canvas.finish();
				
				
			} finally {
				out.flush();
				out.close();
				
			}
		} catch (Exception e) {
			LOGGER.debug("StickerPrintingUtils::generateBarcode::Exception Happended :"+e.getMessage());
		}
		LOGGER.debug("StickerPrintingUtils::generateBarcode::End=======>");
	}
	

	public static List<String> calculateNumericOrAlphaNumericEndSerialNumber(String stNum, Integer quantity) throws NumberFormatException {
		LOGGER.debug("StickerPrintingUtils::calculateNumericOrAlphaNumericEndSerialNumber::Start=======>");
		
		List<String> siNums;
		siNums=new ArrayList<String>(0);
		//stNum = stNum.substring(4);
		Boolean isNumeric = isNumeric(stNum);
		if(!isNumeric)
		{
			char alphabet=stNum.charAt(0);
			char alphabatUpper = Character.toUpperCase(alphabet);
			
			int len=stNum.substring(1).length();
			
			Integer number=Integer.parseInt(stNum.substring(1));
			int len2=number.toString().length();
			int diff=len-len2;
			
			String format="";
			int zero=0;
			if(diff == zero){
				format="%"+len2+"d";
			}else{
				format="%0"+len+"d";
			}
			
			for(int counter=0;counter<quantity;counter++){
				String formatted = String.format(format, number+counter);  
				siNums.add(alphabatUpper+formatted);
			}
	}else{
		for(int counter=0;counter<quantity;counter++){
			Long serialNum = Long.parseLong(stNum);
			serialNum = serialNum+counter;
			siNums.add(serialNum.toString());
		}
	}
		LOGGER.debug("StickerPrintingUtils::calculateNumericOrAlphaNumericEndSerialNumber::End=======>");
		return siNums;
	}
	
	public static Boolean isNumeric(String cnSeries) {
		LOGGER.debug("StickerPrintingUtils::isNumeric::Start=======>");
		Boolean isNumeric = StringUtil.isInteger(cnSeries);
		
		LOGGER.debug("StickerPrintingUtils::isNumeric::End=======>");
		return isNumeric;
	}
}
