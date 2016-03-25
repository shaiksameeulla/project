package com.cg.lbs.bcun.to;

import java.util.List;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * The Class TwoWayWriteTO.
 * @author narmdr
 */
public class TwoWayWriteTO extends CGBaseTO {
	
	private static final long serialVersionUID = 1164352700923681630L;
	private Number id;
	private Class processClass;

	private String[] doNames;
	private List<CGBaseDO> cgBaseDOs;
	private List<Class> processClassList;
	
	//private String dtToCentral=CommonConstants.YES;
	
	/**
	 * @return the id
	 */
	public Number getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Number id) {
		this.id = id;
	}
	/**
	 * @return the processClass
	 */
	public Class getProcessClass() {
		return processClass;
	}
	/**
	 * @param processClass the processClass to set
	 */
	public void setProcessClass(Class processClass) {
		this.processClass = processClass;
	}
	/**
	 * @return the doNames
	 */
	public String[] getDoNames() {
		return doNames;
	}
	/**
	 * @param doNames the doNames to set
	 */
	public void setDoNames(String[] doNames) {
		this.doNames = doNames;
	}
	/**
	 * @return the cgBaseDOs
	 */
	public List<CGBaseDO> getCgBaseDOs() {
		return cgBaseDOs;
	}
	/**
	 * @param cgBaseDOs the cgBaseDOs to set
	 */
	public void setCgBaseDOs(List<CGBaseDO> cgBaseDOs) {
		this.cgBaseDOs = cgBaseDOs;
	}
	/**
	 * @return the processClassList
	 */
	public List<Class> getProcessClassList() {
		return processClassList;
	}
	/**
	 * @param processClassList the processClassList to set
	 */
	public void setProcessClassList(List<Class> processClassList) {
		this.processClassList = processClassList;
	}
}
