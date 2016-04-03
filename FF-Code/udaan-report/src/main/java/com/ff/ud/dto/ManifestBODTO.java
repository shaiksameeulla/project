package com.ff.ud.dto;

import java.util.List;

import com.ff.ud.domain.RightsForUserDO;
import com.ff.ud.domain.Outgo;

/**
 * This class is used to store all DBF data from origin branch to origin hub.
 * @author Kiran
 *
 */
public class ManifestBODTO {

	private List<BoPktDTO> packetList;
	private List<BoMblDTO> mblList;
	private List<Outgo> ogmList;
	private List<RightsForUserDO> bookingList;
	
	
	
	
	public List<RightsForUserDO> getBookingList() {
		return bookingList;
	}
	public void setBookingList(List<RightsForUserDO> bookingList) {
		this.bookingList = bookingList;
	}
	public List<BoPktDTO> getPacketList() {
		return packetList;
	}
	public void setPacketList(List<BoPktDTO> packetList) {
		this.packetList = packetList;
	}
	public List<BoMblDTO> getMblList() {
		return mblList;
	}
	public void setMblList(List<BoMblDTO> mblList) {
		this.mblList = mblList;
	}
	public List<Outgo> getOgmList() {
		return ogmList;
	}
	public void setOgmList(List<Outgo> ogmList) {
		this.ogmList = ogmList;
	}
	
	 
	
	
		
	
}
