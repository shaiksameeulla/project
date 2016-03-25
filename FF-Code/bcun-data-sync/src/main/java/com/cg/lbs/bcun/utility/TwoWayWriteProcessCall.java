package com.cg.lbs.bcun.utility;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.utils.BcunCentralAuthenticationUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.constant.BcunConstant;
import com.cg.lbs.bcun.service.twowaywrite.TwoWayWriteService;

public class TwoWayWriteProcessCall implements Runnable {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger
			.getLogger(TwoWayWriteProcessCall.class);

	private volatile List<? extends Number> ids;
	private volatile List<String> processNames;
	private static String twoWayWriteEnableCode;
	private static TwoWayWriteService twoWayWriteService;

	/**
	 * Instantiates a new two way write process call.
	 */
	public TwoWayWriteProcessCall() {
		// TODO let JVM to use this
	}

	/**
	 * Instantiates a new two way write process call.
	 * 
	 * @param ids
	 *            the ids
	 * @param processNames
	 *            the process names
	 */
	public TwoWayWriteProcessCall(List<? extends Number> ids,
			List<String> processNames) {
		super();
		this.ids = ids;
		this.processNames = processNames;
	}

	/**
	 * @return the ids
	 */
	public List<? extends Number> getIds() {
		return ids;
	}

	/**
	 * @param ids
	 *            the ids to set
	 */
	public void setIds(List<Number> ids) {
		this.ids = ids;
	}

	/**
	 * @return the processNames
	 */
	public List<String> getProcessNames() {
		return processNames;
	}

	/**
	 * @param processNames
	 *            the processNames to set
	 */
	public void setProcessNames(List<String> processNames) {
		this.processNames = processNames;
	}

	/**
	 * @param twoWayWriteEnableCode
	 *            the twoWayWriteEnableCode to set
	 */
	public static void setTwoWayWriteEnableCode(String twoWayWriteEnableCode) {
		TwoWayWriteProcessCall.twoWayWriteEnableCode = twoWayWriteEnableCode;
	}

	/**
	 * @param twoWayWriteService
	 *            the twoWayWriteService to set
	 */
	public static void setTwoWayWriteService(
			TwoWayWriteService twoWayWriteService) {
		TwoWayWriteProcessCall.twoWayWriteService = twoWayWriteService;
	}

	/**
	 * Checks if is two way write enabled.
	 * 
	 * @return true, if is two way write enabled
	 */
	public static boolean isTwoWayWriteEnabled() {
		if (StringUtils.equalsIgnoreCase(twoWayWriteEnableCode,
				BcunConstant.FLAG_YES) && BcunCentralAuthenticationUtil.getBcunCentralAuthenticationStatus()) {
			return true;
		}
		return false;
	}

	@Override
	public void run() {
		LOGGER.debug("TwoWayWriteProcessCall::run::Thread processing started------------>:::::::");
		// TwoWayWriteService twoWayWriteService = null;
		try {
			/*
			 * twoWayWriteService = (TwoWayWriteService) SpringContext
			 * .getBean("twoWayWriteService");
			 */

			twoWayWriteService.twoWayWriteProcessStart(ids, processNames);
			/*
			 * if (isTwoWayWriteEnabled()) {
			 * twoWayWriteService.twoWayWriteProcessStart(ids, processNames); }
			 * else { LOGGER.trace(
			 * "TwoWayWriteProcessCall :: run() :: Two Way Write flag is not Enabled for Branch."
			 * ); twoWayWriteService.reProcessTwoWayWrite(ids, processNames); }
			 */

		} catch (Exception e) {
			LOGGER.error("TwoWayWriteProceesCall :: twoWriteProcess() :: Exception Happened : "
					, e);
			twoWayWriteService.reProcessTwoWayWrite(ids, processNames);
		}
		LOGGER.debug("TwoWayWriteProcessCall::run::Thread processing End------------>:::::::");
	}

	/**
	 * Two way write process.
	 * 
	 * @param id
	 *            the id
	 * @param processName
	 *            the process name
	 */
	public static void twoWayWriteProcess(Long id, String processName) {
		if (!(StringUtil.isEmptyLong(id))
				&& StringUtils.isNotBlank(processName)) {
			List<Long> ids = new ArrayList<>(1);
			List<String> processNames = new ArrayList<>(1);
			ids.add(id);
			processNames.add(processName);
			callTwoWayWriteProcess(ids, processNames);
		}
	}

	/**
	 * Two way write process.
	 *
	 * @param id the id
	 * @param processName the process name
	 */
	public static void twoWayWriteProcess(Integer id, String processName) {
		if (!StringUtil.isEmptyInteger(id)
				&& StringUtils.isNotBlank(processName)) {
			List<Integer> ids = new ArrayList<>(1);
			List<String> processNames = new ArrayList<>(1);
			ids.add(id);
			processNames.add(processName);
			callTwoWayWriteProcess(ids, processNames);
		}
	}

	/**
	 * Two way write process.
	 * 
	 * @param ids
	 *            the ids
	 * @param processNames
	 *            the process names
	 */
	public static void twoWayWriteProcess(ArrayList<Integer> ids,
			ArrayList<String> processNames) {
		callTwoWayWriteProcess(ids, processNames);
	}

	private static void callTwoWayWriteProcess(List<? extends Number> ids,
			List<String> processNames) {
		LOGGER.debug("TwoWayWriteProcessCall :: callTwoWayWriteProcess() :: Entered into twoWayWriteProcess method");
		if (!StringUtil.isEmptyList(ids)
				&& !StringUtil.isEmptyList(processNames)
				&& isTwoWayWriteEnabled()) {
			TwoWayWriteProcessCall twoWayWriteProcessCall = new TwoWayWriteProcessCall(
					ids, processNames);
			new Thread(twoWayWriteProcessCall).start();
		}
		LOGGER.debug("TwoWayWriteProcessCall :: callTwoWayWriteProcess() :: End into twoWayWriteProcess method");

	}
}
