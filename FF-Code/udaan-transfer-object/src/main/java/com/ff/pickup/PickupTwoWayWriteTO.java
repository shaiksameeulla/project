package com.ff.pickup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PickupTwoWayWriteTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3147442010469538341L;
	ArrayList<Integer> headerDoIds;
	ArrayList<String> headerProcessNames;
	
	List<Integer> subDoIds;
	String subProcess;
	
	public ArrayList<Integer> getHeaderDoIds() {
		return headerDoIds;
	}
	public void setHeaderDoIds(ArrayList<Integer> headerDoIds) {
		this.headerDoIds = headerDoIds;
	}
	public ArrayList<String> getHeaderProcessNames() {
		return headerProcessNames;
	}
	public void setHeaderProcessNames(ArrayList<String> headerProcessNames) {
		this.headerProcessNames = headerProcessNames;
	}
	public List<Integer> getSubDoIds() {
		return subDoIds;
	}
	public void setSubDoIds(List<Integer> subDoIds) {
		this.subDoIds = subDoIds;
	}
	public String getSubProcess() {
		return subProcess;
	}
	public void setSubProcess(String subProcess) {
		this.subProcess = subProcess;
	}
		
}
