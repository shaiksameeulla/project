package com.capgemini.lbs.centralserver.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.capgemini.lbs.framework.utils.StringUtil;

/**
 * Servlet implementation class AuthenticationServlet
 */
public class AuthenticationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthenticationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		navigator(request, response);
	}

	private void navigator(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//Boolean isValid=isUserValid(request, response);
		ServletContext context= getServletContext();
		
		//if(isValid){
			RequestDispatcher rd= context.getRequestDispatcher("/manualDownload.ff");
			rd.forward(request, response);
			/*}else{
			RequestDispatcher rd= context.getRequestDispatcher("/invalidBcunUser.ff");
			rd.forward(request, response);
		}*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		
	}
	
	private Boolean isUserValid(HttpServletRequest request, HttpServletResponse response){
		Boolean isValid=false;
		String samireq=request.getHeader("UDAAN");
		//Cookie[] c=request.getCookies();
		if(!StringUtil.isStringEmpty(samireq) && samireq.equalsIgnoreCase("UDAAN")){
			isValid=true;
		}
		return isValid;
	}

}
