package com.ff.terminal.print;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

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

public class PrintJobUtil {

	/**
	 * To print job from printer
	 * 
	 * @param page
	 * @return defaultPrinter - printer name
	 * @throws PrintException
	 * @throws IOException
	 */
	public String printJob(String page) throws PrintException, IOException {
		System.out.println("PrintJobUtil :: printJob() :: START");
		String defaultPrinter = PrintServiceLookup.lookupDefaultPrintService()
				.getName();
		System.out.println("Default Printer :: " + defaultPrinter);
		PrintService service = PrintServiceLookup.lookupDefaultPrintService();

		// To prints the famous hello world! plus a form feed
		InputStream is = new ByteArrayInputStream(
				(page + "\f").getBytes("UTF8"));

		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		pras.add(new Copies(1));

		DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		Doc doc = new SimpleDoc(is, flavor, null);
		DocPrintJob job = service.createPrintJob();

		PrintJobWatcher pjw = new PrintJobWatcher(job);
		job.print(doc, pras);
		pjw.waitForDone();
		is.close();
		System.out.println("PrintJobUtil :: printJob() :: END");
		return defaultPrinter;
	}

}
