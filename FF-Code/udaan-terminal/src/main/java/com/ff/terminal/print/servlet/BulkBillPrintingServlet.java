package com.ff.terminal.print.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ff.terminal.print.BillPrintJobUtil;

/**
 * Servlet implementation class BulkBillPrintingServlet
 */
public class BulkBillPrintingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BulkBillPrintingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletInputStream servletInputStream = request.getInputStream();
		
		BillPrintJobUtil billPrintJobUtil = new BillPrintJobUtil(); 
		byte[] inputByteArray = billPrintJobUtil.getByteArrayFromInputStream(servletInputStream);
		
		/** The below lines of code have been added to test the integrity of data received **/
		FileOutputStream fos = new FileOutputStream("D:/jettyOutputZipFile.zip"); 
		fos.write(inputByteArray);
		fos.flush();
		fos.close();
		
		if (inputByteArray.length != 0) {
			inputByteArray = billPrintJobUtil.unizpInputByteArray(inputByteArray);
		}
		else {
			System.out.println("BulkBillPrintingServlet :: doPost :: ERROR ");
		}
		
		/** The below lines of code have been added to test the integrity of data received **/
		fos = new FileOutputStream("D:/jettyOutputHtmlFile.html"); 
		fos.write(inputByteArray);
		fos.flush();
		fos.close();
		
		if (inputByteArray != null && inputByteArray.length > 0) {
			try {
				BillPrintJobUtil billPrintUtility = new BillPrintJobUtil();
				String printerName = billPrintUtility.printJob(inputByteArray);
				System.out.println("Print Text:\n" + billPrintUtility);
				System.out.println("Printing to " + printerName + " printer.");
			} catch (Exception e) {
				System.out.print("ERROR :: PrintJobServlet :: doPost() :");
				e.printStackTrace();
			}
		} else {
			System.out.print("ERROR :: Null Content :: There is no data to print.");
		}
		servletInputStream.close();
	}

}
