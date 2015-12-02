/*
 * @author soagarwa
 */

package com.capgemini.lbs.centralserver.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.capgemini.lbs.centralserver.util.PopulateMacAddressOnStartup;
import com.capgemini.lbs.framework.constants.SplitModelConstant;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.centralserver.centraldao.PopulateMacAddressDAO;
import com.dtdc.domain.master.authorization.SystemAuthenticationDO;

// TODO: Auto-generated Javadoc
/**
 * The Class LoginServlet.
 */
@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(LoginServlet.class);
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	/**
	 * Do get.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.debug("LoginServlet::doGet:: start=======>");
		// Retrieve user info from request
		String userInfo = (String) request
				.getParameter(SplitModelConstant.USER_INFORMATION);
		LOGGER.debug("LoginServlet::doGet:: request recieved for user [ " + userInfo + " ]");
		String macAddress = (String) request
				.getParameter(SplitModelConstant.MAC_ADDRESS);
		LOGGER.debug("LoginServlet::doGet:: request recieved for mac [ " + macAddress + " ]");
		
		// Get httpsession object from request
		HttpSession httpSession = request.getSession();

		// Retrieve mac address from map populated at server start up with key
		// as user id and value as mac address
		String macAddressfromDB = PopulateMacAddressOnStartup
				.getMacAddressMap().get(userInfo);
		LOGGER.debug("LoginServlet::doGet:: mac from db [ " + macAddressfromDB + " ]");
		if (httpSession != null) {
			
			if(macAddressfromDB == null){
				LOGGER.debug("LoginServlet::retrieving any mac address and userinfo added at runtime");
				WebApplicationContext springApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
				PopulateMacAddressDAO macAddressDao = (PopulateMacAddressDAO)springApplicationContext.getBean("macAddressDao");
				SystemAuthenticationDO systemAuthenticationDO = macAddressDao.getSysAuthenticationDOByMac(userInfo, macAddress);
				if(systemAuthenticationDO != null && systemAuthenticationDO.getAllocatedEmpCode().equalsIgnoreCase(userInfo)){
					macAddressfromDB = systemAuthenticationDO.getMacAddress();
					PopulateMacAddressOnStartup.getMacAddressMap().put(systemAuthenticationDO.getAllocatedEmpCode(), systemAuthenticationDO.getMacAddress());
				}
			}

			if (!StringUtil.isEmpty(macAddress)
					&& macAddress.equals(macAddressfromDB.trim())) {
				Cookie userCookie = new Cookie(SplitModelConstant.USER_STATUS,
						SplitModelConstant.USER_LOGGED_IN);

				response.addCookie(userCookie);
				LOGGER.debug(" central server authentication success.. created cookie");
			} else {
				Cookie userCookie = new Cookie(SplitModelConstant.USER_STATUS,
						SplitModelConstant.UN_AUTHORISED_USER);
				response.addCookie(userCookie);
			}

		}
	}
}
