package com.ff.terminal.node.servlet;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.capgemini.lbs.framework.constants.SplitModelConstant;

public class ClientMacAddressServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static String PHYSICAL_ADDRESS_PATTERN =".*Physical Address.*: (.*)";
	private final static String WINDOWS_OS_NAME ="Window";
	private final static String IF_CONFIG_FOR_LINUX ="ifconfig -a";
	private final static String IP_CONFIG ="ipconfig /all";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("ClientMacAddressServlet::doPost::start");

		PrintWriter out = response.getWriter();
		String macAddrss = null;
		try {
			macAddrss = getMacAddress();
			System.out.println("macAddrss is -------------:" + macAddrss);
		} catch (IOException e) {
			macAddrss = "-1";
			System.out.println("Cant get the macAddrss -------------:"
					+ e.getMessage());
		} finally {
			out.write(macAddrss != null && !macAddrss.equals("") ? macAddrss : "-1");
			out.flush();
			out.close();
		}
	}

	public void doOptions(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("ConsignmentmacAddrssReaderAction::doOptions::start========>");
		
		PrintWriter out = response.getWriter();
		String macAddrss = null;
		try {
			macAddrss = getMacAddress();
			System.out.println("macAddrss is -------------:" + macAddrss);
		} catch (IOException e) {
			macAddrss = "-1";
			System.out.println("Cant get the macAddrss -------------:"
					+ e.getMessage());
		} finally {
			out.write(macAddrss != null && !macAddrss.equals("") ? macAddrss : "-1");
			out.flush();
			out.close();
		}
	}

	/**
	 * Gets the mac address.
	 * 
	 * @return the mac address
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static String getMacAddress() throws IOException {
		String macAddress = null;
		String osName = System.getProperty("os.name");
		
		//Getting command name based on OS
		String command = osName.startsWith(WINDOWS_OS_NAME) ?IP_CONFIG : IF_CONFIG_FOR_LINUX;
		
		//Executing the command 
		Process pid = Runtime.getRuntime().exec(command);
		// Buffering the input Stream
		BufferedReader in = new BufferedReader(new InputStreamReader(pid.getInputStream()));
		while (true) {
			String line = in.readLine();
			if (line == null)
				break;
			Pattern p = Pattern.compile(PHYSICAL_ADDRESS_PATTERN);
			Matcher m = p.matcher(line);
			if (m.matches()) {
				macAddress = m.group(1);
				break;
			}
		}
		//Closed the buffered stream
		
		in.close();
		return macAddress;
	}
}