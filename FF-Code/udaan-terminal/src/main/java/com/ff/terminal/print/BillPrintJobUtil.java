package com.ff.terminal.print;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;

public class BillPrintJobUtil {

	public byte[] getByteArrayFromInputStream(InputStream inputStream) {
		byte[] byteArray = null;
		try {
			List<Byte> byteList = new ArrayList<>();
			int byteData = 0;
			while ( (byteData = inputStream.read()) != -1) {
				byteList.add((byte)byteData);
			}
			
			byteArray = new byte[byteList.size()];
			for (int i=0; i < byteList.size(); i++ ) {
				byteArray[i] = byteList.get(i);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return byteArray;
	}
	
	public byte[] unizpInputByteArray(byte[] inputByteArray) {
		byte[] finalByteArray = null;
		try {
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(inputByteArray); 
			ZipInputStream zipInputStream = new ZipInputStream(byteArrayInputStream);
			ZipEntry zipEntry = zipInputStream.getNextEntry();
			System.out.println("Zipe file name : " + zipEntry.getName());
			finalByteArray = getByteArrayFromInputStream(zipInputStream);
			
			zipInputStream.closeEntry();
			zipInputStream.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return finalByteArray;
	}
	
	/**
	 * To print job from printer
	 * 
	 * @param page
	 * @return defaultPrinter - printer name
	 * @throws PrintException
	 * @throws IOException
	 */
	public String printJob(byte[] page) throws PrintException, IOException {
		System.out.println("BillPrintJobUtil :: printJob() :: START");
		String defaultPrinter = PrintServiceLookup.lookupDefaultPrintService().getName();
		System.out.println("Default Printer :: " + defaultPrinter);
		PrintService service = PrintServiceLookup.lookupDefaultPrintService();

		// To print the famous hello world! plus a form feed
		// InputStream is = new ByteArrayInputStream((page + "\f").getBytes("UTF8"));

		InputStream is = new ByteArrayInputStream(page);
		
		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		pras.add(new Copies(1));

		DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		Doc doc = new SimpleDoc(is, flavor, null);
		DocPrintJob job = service.createPrintJob();

		PrintJobWatcher pjw = new PrintJobWatcher(job);
		job.print(doc, pras);
		pjw.waitForDone();
		is.close();
		System.out.println("BillPrintJobUtil :: printJob() :: END");
		return defaultPrinter;
	}
	
}
