package com.ff.ud.dto;
import java.util.List;
import java.util.Set;

import com.ff.ud.domain.Booking;
import com.ff.ud.domain.MblDO;
import com.ff.ud.domain.OutgoDO;
import com.ff.ud.domain.PacketDO;
public class ManifestHODTO {
	
	private List<PacketDO> Pkt=null ;
	private Set<MblDO>  mbl=null;
	private List<OutgoDO> out=null;
	private List<Booking> booking=null;
	
	private List<RegionPktDTO> rPktList = null;
	private Set<RegionMblDTO> rMblSet = null;
	private List<RegionOutgoDTO> rOutgoList = null; 
	private List<RegionMblDTO> rMblList = null;
	private List<BookingDetailsDTO> rbookingList = null;
	
	private List<HubToBranchPKTDTO> hubTobranchPktList = null;
	private List<HubToBranchINMASTDTO> hubTobranchInMastList = null;
	private List<HubToBranchMblDTO> hubTobranchMblList = null; 
	private List<HubToBranchLCTDTO> hubTobranchLctList = null;
	
	private List<BookingDetailsDTO> bookingDetailsList = null;
	
	
	
	

	
	public List<BookingDetailsDTO> getBookingDetailsList() {
		return bookingDetailsList;
	}
	public void setBookingDetailsList(List<BookingDetailsDTO> bookingDetailsList) {
		this.bookingDetailsList = bookingDetailsList;
	}
	
	public List<PacketDO> getPkt() {
		return Pkt;
	}
	public void setPkt(List<PacketDO> pkt) {
		Pkt = pkt;
	}
	public Set<MblDO> getMbl() {
		return mbl;
	}
	public void setMbl(Set<MblDO> mbl) {
		this.mbl = mbl;
	}
	public List<OutgoDO> getOut() {
		return out;
	}
	public void setOut(List<OutgoDO> out) {
		this.out = out;
	}
	public List<RegionPktDTO> getrPktList() {
		return rPktList;
	}
	public void setrPktList(List<RegionPktDTO> rPktList) {
		this.rPktList = rPktList;
	}
	public Set<RegionMblDTO> getrMblSet() {
		return rMblSet;
	}
	public void setrMblSet(Set<RegionMblDTO> rMblSet) {
		this.rMblSet = rMblSet;
	}
	public List<RegionOutgoDTO> getrOutgoList() {
		return rOutgoList;
	}
	public void setrOutgoList(List<RegionOutgoDTO> rOutgoList) {
		this.rOutgoList = rOutgoList;
	}
	public List<RegionMblDTO> getrMblList() {
		return rMblList;
	}
	public void setrMblList(List<RegionMblDTO> rMblList) {
		this.rMblList = rMblList;
	}
	public List<HubToBranchPKTDTO> getHubTobranchPktList() {
		return hubTobranchPktList;
	}
	public void setHubTobranchPktList(List<HubToBranchPKTDTO> hubTobranchPktList) {
		this.hubTobranchPktList = hubTobranchPktList;
	}
	public List<HubToBranchINMASTDTO> getHubTobranchInMastList() {
		return hubTobranchInMastList;
	}
	public void setHubTobranchInMastList(List<HubToBranchINMASTDTO> hubTobranchInMastList) {
		this.hubTobranchInMastList = hubTobranchInMastList;
	}
	public List<HubToBranchMblDTO> getHubTobranchMblList() {
		return hubTobranchMblList;
	}
	public void setHubTobranchMblList(List<HubToBranchMblDTO> hubTobranchMblList) {
		this.hubTobranchMblList = hubTobranchMblList;
	}
	public List<HubToBranchLCTDTO> getHubTobranchLctList() {
		return hubTobranchLctList;
	}
	public void setHubTobranchLctList(List<HubToBranchLCTDTO> hubTobranchLctList) {
		this.hubTobranchLctList = hubTobranchLctList;
	}
	public List<BookingDetailsDTO> getRbookingList() {
		return rbookingList;
	}
	public void setRbookingList(List<BookingDetailsDTO> rbookingList) {
		this.rbookingList = rbookingList;
	}
	

}
