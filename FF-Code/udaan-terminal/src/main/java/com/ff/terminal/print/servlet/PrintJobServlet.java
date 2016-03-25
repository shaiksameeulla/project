package com.ff.terminal.print.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ff.terminal.print.PrintJobUtil;

/**
 * @author hkansagr
 * 
 */
public class PrintJobServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String PRINT_STR = "printStr";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//String printText = request.getHeader(PRINT_STR);
		String printText = request.getParameter(PRINT_STR);
		if (printText != null && !printText.equals("")) {
			try {
				PrintJobUtil pj = new PrintJobUtil();
				String printerName = pj.printJob(printText);
				System.out.println("Print Text:\n" + printText);
				System.out.println("Printing to " + printerName + " printer.");
			} catch (Exception e) {
				System.out.print("ERROR :: PrintJobServlet :: doPost() :");
				e.printStackTrace();
			}
		} else {
			System.out
					.print("ERROR :: Null Content :: There is no data to print.");
		}
	}

}
