/*
 * @author mohammes
 */
package com.cg.framework.bcun.jnlp;

import java.io.IOException;

/**
 * The Class UdaanDeployUtil.
 * 
 * @author mohammes
 */
public final class UdaanDeployUtil implements Runnable{

	static String PLAIN_DEPLOY_FOR_LINUX = "sh "+FileLocator.getScriptFileLocation()+"PlainDeploy.sh &";
	static String PLAIN_DEPLOY_FOR_WINDOW ="cmd /c start "+FileLocator.getScriptFileLocation()+"PlainDeploy.bat";

	static String WINDOW_OS_NAME = "Window";


	/* This will return the unique machine address of client machine */

	/**
	 * Gets the mac address.
	 * 
	 * @return the mac address
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */

	public static void performDeploy() throws IOException {
		String command = null;
		String osName = System.getProperty("os.name");
		Process pid = null;;
		/* For Windows machine */
		if (osName.contains(WINDOW_OS_NAME)) {
			command = PLAIN_DEPLOY_FOR_WINDOW;
			/* Setting the exe command */

			try {
				System.out.println("deploying war file ## initiating command ##start with the command :"+command);
				pid = Runtime.getRuntime().exec(command);

				Thread.sleep(20000);
				System.out.println(pid.exitValue());
				System.out.println("deploying war file ## initiating command ##END");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/* For Linux Machine */
		else {
			command = PLAIN_DEPLOY_FOR_LINUX;
			/* Setting the exe command */

			try {
				System.out.println("deploying war file ## initiating command ##start with the command :"+command);
				//ProcessBuilder processBuilder = new ProcessBuilder("gneom", "-e",PLAIN_DEPLOY_FOR_LINUX );
				 String[] cmdArray = {"gnome-terminal", "-e", PLAIN_DEPLOY_FOR_LINUX + " ; le_exec"};
				 pid = Runtime.getRuntime().exec(cmdArray);
				Thread.sleep(20000);
				System.out.println(pid.exitValue());
				System.out.println("deploying war file ## initiating command ##END");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}



	}



	@Override
	public void run() {
		try {
			System.out.println("deploying war file **START**");
			performDeploy();
			System.out.println("deploying war file **END**");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}











}
