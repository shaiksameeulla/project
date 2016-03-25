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

public class LinuxSerialPortReaderServlet extends HttpServlet {

	CommPortIdentifier portIdentifier = null;
	private Enumeration portList;
	private String portName = null;
	private CommPortIdentifier portId;
	private SerialPort serialPort = null;
	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-dd-MM HH:mm:ss:SSS");
	final static int DIVISION_FACTOR = 1000;

	public void init(ServletConfig config) throws ServletException {

		super.init(config);
		portName = config.getInitParameter("port-name");
		System.out
				.println("LinuxSerialPortReaderServlet::init::config param port name:: "
						+ portName);
		portList = CommPortIdentifier.getPortIdentifiers();
		while (portList.hasMoreElements()) {
			portId = (CommPortIdentifier) portList.nextElement();
			String identifiedPortName = portId.getName();
			System.out
					.println("LinuxSerialPortReaderServlet::init::identified port:: "
							+ identifiedPortName);
			if (identifiedPortName.equals(portName))
				break;
		}
		try {
			if (portId.getName().equals(portName)) {
				serialPort = (SerialPort) portId.open("udaan-terminal", 3000);
				serialPort.setSerialPortParams(1200, SerialPort.DATABITS_8,
						SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			}
		} catch (Exception e) {
			System.out
					.println("LinuxSerialPortReaderServlet::init::Exception in initialization");
			e.printStackTrace();
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out
				.println("LinuxSerialPortReaderServlet::doPost::start time====>"
						+ System.currentTimeMillis());
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String weight = "-1";

		try {
			System.out
					.println("LinuxSerialPortReaderServlet::doPost::trying to connect on port:: "
							+ portName);
			weight = getWeight();
			weight = getNReverseWt(weight);
			if (weight.equals("-1")) {
				weight = getWeight();
				weight = getNReverseWt(weight);
			}
			System.out
					.println("LinuxSerialPortReaderServlet::doPost::getNReverseWt:: "
							+ weight);
			System.out.println("LinuxSerialPortReaderServlet::doPost::END");
		} catch (Exception e) {
			weight = "-1";
			e.printStackTrace();
			System.out
					.println("LinuxSerialPortReaderServlet::doPost::end time with exception====>");
		} finally {
			String finalwt = weight != null && !weight.isEmpty() ? weight
					: "-1";
			System.out
					.println("LinuxSerialPortReaderServlet::finally::final weight [ "
							+ sdf.format(new Date()) + " ] ====>" + finalwt);
			out.write(finalwt);
			out.flush();
			out.close();
		}
		System.out
				.println("LinuxSerialPortReaderServlet::doPost::end time====>"
						+ System.currentTimeMillis());
	}

	public String getNReverseWt(String weight) {
		System.out
				.println("LinuxSerialPortReaderServlet::doPost::received weight from machine::[ "
						+ sdf.format(new Date()) + "] " + weight);
		String[] weightArr;
		StringBuffer actualWeight;
		int startIndex = 0;
		if (weight != null && !weight.isEmpty()) {
			weightArr = weight.split("=");
			System.out
					.println("LinuxSerialPortReaderServlet::doPost::received weight from machine seriese length is:: "
							+ weightArr.length);

			String firstReading = "-1";
			String secondReading = "-2";
			String thirdReading = "-3";
			String fourthReading = "-4";
			if (weightArr != null && weightArr.length >= 3) {
				firstReading = weightArr[weightArr.length - 2];
				System.out
						.println("LinuxSerialPortReaderServlet::getNReverseWt::firstReading:: "
								+ firstReading);
			}
			if (weightArr != null && weightArr.length >= 4) {
				secondReading = weightArr[weightArr.length - 3];
				System.out
						.println("LinuxSerialPortReaderServlet::getNReverseWt::secondReading:: "
								+ secondReading);
			}
			if (weightArr != null && weightArr.length >= 5) {
				thirdReading = weightArr[weightArr.length - 4];
				System.out
						.println("LinuxSerialPortReaderServlet::getNReverseWt::thirdReading:: "
								+ thirdReading);
			}
			if (weightArr != null && weightArr.length >= 6) {
				fourthReading = weightArr[weightArr.length - 5];
				System.out
						.println("LinuxSerialPortReaderServlet::getNReverseWt::fourthReading:: "
								+ fourthReading);
			}

			String finalReading = firstReading.equals(secondReading) ? secondReading
					: secondReading.equals(thirdReading) ? secondReading
							: thirdReading.equals(fourthReading) ? thirdReading
									: "-1";
			System.out
					.println("LinuxSerialPortReaderServlet::getNReverseWt::individual fetched weight:: "
							+ finalReading);
			if (finalReading.equals("-1")) {
				weight = finalReading;
			} else {
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
				weight = (Float.parseFloat(weight) / DIVISION_FACTOR) + "";
			}

		}
		System.out
				.println("LinuxSerialPortReaderServlet::getNReverseWt::returned weight:: "
						+ weight);
		return weight;
	}

	public synchronized String getWeight() {
		System.out
				.println("LinuxSerialPortReaderServlet::getWeight::identified port list start:: "
						+ System.currentTimeMillis());
		String portReading = readPort();
		System.out.println("LinuxSerialPortReaderServlet::getWeight::END:: "
				+ portReading);
		return portReading;
	}

	public String readPort() {
		byte[] readBuffer = new byte[100];
		InputStream inputStream = null;
		String portReading = null; // newly added
		try {

			System.out
					.println("LinuxSerialPortReaderServlet::readPort::opening port:: "
							+ serialPort);
			inputStream = serialPort.getInputStream();
			System.out
					.println("LinuxSerialPortReaderServlet::readPort::input Stream:: "
							+ inputStream);
			serialPort.sendBreak(200);
			System.out
					.println("LinuxSerialPortReaderServlet::readPort::reading input stream starts and length is :: "
							+ inputStream.available());
			while (inputStream.available() > 0) {
				inputStream.read(readBuffer);
				portReading = new String(readBuffer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("LinuxSerialPortReaderServlet::readPort::end :: ");
		return portReading;
	}

	public void destroy() {
		if (serialPort != null) {
			try {
				serialPort.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
