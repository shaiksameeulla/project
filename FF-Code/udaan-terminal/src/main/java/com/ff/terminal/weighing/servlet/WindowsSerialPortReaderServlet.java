package com.ff.terminal.weighing.servlet;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WindowsSerialPortReaderServlet extends HttpServlet {

	private String portName = null;
	private CommPortIdentifier portId;
	private Enumeration portList;
	private SerialPort serialPort = null;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss:SSS");
	final static int DIVISION_FACTOR = 1000;
	

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		portName = config.getInitParameter("port-name");
		System.out.println("WindowsSerialPortReaderServlet::init::config param port name:: " + portName);
		portList = CommPortIdentifier.getPortIdentifiers();
		while (portList.hasMoreElements()) {
			portId = (CommPortIdentifier) portList.nextElement();
			String identifiedPortName = portId.getName();
			System.out.println("WindowsSerialPortReaderServlet::init::identified port:: " + identifiedPortName);
			if(identifiedPortName.equals(portName))
				break;
		}
		try {
			if(portId.getName().equals(portName)) {
				serialPort = (SerialPort) portId.open("udaan-terminal", 3000);
				serialPort.setSerialPortParams(1200, SerialPort.DATABITS_8,	SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			}
		} catch (Exception e) {
			System.out.println("WindowsSerialPortReaderServlet::init::Exception in initialization");
			e.printStackTrace();
		}//
		//getWeight(portName);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String weight = "-1";
		
		try {
			System.out.println("WindowsSerialPortReaderServlet::doPost::trying to connect on port:: "	+ portName);
			weight = getWeight(portName);
			weight = getNReverseWt(weight);
			System.out.println("WindowsSerialPortReaderServlet::doPost::weight.equals(-1): " + weight.equals("-1"));
			if(weight.equals("-1")) {
				System.out.println("WindowsSerialPortReaderServlet::doPost::connecting port 2nd time due to wrong wt:: "	+ weight);
				String newPortweight = getWeight(portName);
				weight = getNReverseWt(newPortweight);
			}
			System.out.println("WindowsSerialPortReaderServlet::doPost::getNReverseWt:: "	+ weight);
			System.out.println("WindowsSerialPortReaderServlet::doPost::END");
		} catch (Exception e) {
			weight = "-1";
			e.printStackTrace();
			System.out.println("WindowsSerialPortReaderServlet::doPost::end time with exception====>");
		} finally {
			String finalwt = weight != null && !weight.isEmpty() ? weight : "-1";
			System.out.println("WindowsSerialPortReaderServlet::finally::final weight [ "+ sdf.format(new Date()) +" ] ====>" + finalwt);
			out.write(finalwt);
			out.flush();
			out.close();
		}
		System.out.println("WindowsSerialPortReaderServlet::doPost::end time ====>");
	}

	public String getNReverseWt(String weight) {
		System.out.println("WindowsSerialPortReaderServlet::doPost::received weight from machine::[ "+ sdf.format(new Date()) +"] " + weight);
		String[] weightArr;
		StringBuffer actualWeight;
		int startIndex = 0;
		if (weight != null && !weight.isEmpty()) {
			weightArr = weight.split("=");
			System.out.println("WindowsSerialPortReaderServlet::doPost::received weight from machine seriese length is:: "	+ weightArr.length);
			
			String firstReading = "-1";
			String secondReading = "-2";
			String thirdReading = "-3";
			String fourthReading = "-4";
			if(weightArr != null && weightArr.length >= 3) {
				firstReading = weightArr[weightArr.length-2];
				System.out.println("WindowsSerialPortReaderServlet::getNReverseWt::firstReading:: "	+ firstReading);
			}
			if(weightArr != null && weightArr.length >= 4) {
				secondReading = weightArr[weightArr.length-3];
				System.out.println("WindowsSerialPortReaderServlet::getNReverseWt::secondReading:: "	+ secondReading);
			}
			if(weightArr != null && weightArr.length >= 5) {
				thirdReading = weightArr[weightArr.length-4];
				System.out.println("WindowsSerialPortReaderServlet::getNReverseWt::thirdReading:: "	+ thirdReading);
			}
			if(weightArr != null && weightArr.length >= 6) {
				fourthReading = weightArr[weightArr.length-5];
				System.out.println("WindowsSerialPortReaderServlet::getNReverseWt::fourthReading:: "	+ fourthReading);
			}
			
			String finalReading = firstReading.equals(secondReading) ? secondReading :secondReading.equals(thirdReading)? secondReading:
				thirdReading.equals(fourthReading)? thirdReading: "-1";
			System.out.println("WindowsSerialPortReaderServlet::getNReverseWt::individual fetched weight:: "	+ finalReading);		
			if(finalReading.equals("-1")) {
				weight=finalReading;
			}else  {
				actualWeight = new StringBuffer(finalReading);
				
					
				weight = actualWeight.reverse().toString();
				for (int j = 0; j < weight.length(); j++) {
					if (weight.substring(j, j + 1).equals("0")) {
						// skip
					} else {
						startIndex = j;
						break;
					}
				}
				weight = weight.substring(startIndex);
				weight = (Float.parseFloat(weight) / DIVISION_FACTOR)+ "";
			} 
				
		}
		System.out.println("WindowsSerialPortReaderServlet::getNReverseWt::returned weight:: "	+ weight);
		return weight;
	}
	
	public synchronized String getWeight(String portName) {
		String portReading = null;
		portReading = readPort();
		return portReading;
	}

	public String readPort() {
		byte[] readBuffer = new byte[200];
		InputStream inputStream = null;
		String portReading = null; // newly added
		try {
			System.out.println("WindowsSerialPortReaderServlet::readPort::opening port:: "+ serialPort);
			inputStream = serialPort.getInputStream();
			System.out.println("WindowsSerialPortReaderServlet::readPort::input Stream:: " + inputStream);
			serialPort.sendBreak(200);
			System.out.println("WindowsSerialPortReaderServlet::readPort::reading input stream starts and length is :: "+ inputStream.available());
			while (inputStream.available() > 0) {
				inputStream.read(readBuffer);
				portReading = new String(readBuffer);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			//System.out.println("WindowsSerialPortReaderServlet::readPort::finally:: start::");
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//System.out.println("WindowsSerialPortReaderServlet::readPort::finally:: end::");
		}
		System.out.println("WindowsSerialPortReaderServlet::readPort::End");
		return portReading;
	}

	public void destroy () {
		if (serialPort != null) {
			try {
				serialPort.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}