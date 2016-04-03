package com.ff.ud.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ff.ud.constants.ReportConstants;
import com.ff.ud.utils.ReportUtils;




/**
 * Servlet implementation class ReportController
 */
@WebServlet("/udaanReport")
public class ReportController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("I am here");
		final String reportName=request.getParameter(ReportConstants.REPORT_NAME);
		
		request.setAttribute(ReportConstants.VAR_REPORT_URL, ReportUtils.getReportURL(request,reportName));
		RequestDispatcher rd=request.getRequestDispatcher("/WEB-INF/jsp/udaanReports.jsp");
		
		rd.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
