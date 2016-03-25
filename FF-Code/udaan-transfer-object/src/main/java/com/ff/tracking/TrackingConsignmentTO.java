/**
 * 
 */
package com.ff.tracking;

import java.util.List;

import com.ff.booking.BookingTO;
import com.ff.consignment.ChildConsignmentTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.organization.OfficeTO;

/**
 * @author uchauhan
 *
 */
public class TrackingConsignmentTO extends TrackingBaseTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6363265550049662496L;
	
	private BookingTO bookingTO;
	private ConsignmentTO consignmentTO;
	private List<ChildConsignmentTO> childCNTO;
	private String pckDate;
    private String bookedBy;
    private OfficeTO officeTO;
	
	/**
	 * @return the bookingTO
	 */
	public BookingTO getBookingTO() {
		return bookingTO;
	}
	/**
	 * @param bookingTO the bookingTO to set
	 */
	public void setBookingTO(BookingTO bookingTO) {
		this.bookingTO = bookingTO;
	}


	/**
	 * @return the childCNTO
	 */
	public List<ChildConsignmentTO> getChildCNTO() {
		return childCNTO;
	}
	/**
	 * @param childCNTO the childCNTO to set
	 */
	public void setChildCNTO(List<ChildConsignmentTO> childCNTO) {
		this.childCNTO = childCNTO;
	}
	/**
	 * @return the pckDate
	 */
	public String getPckDate() {
		return pckDate;
	}
	/**
	 * @param pckDate the pckDate to set
	 */
	public void setPckDate(String pckDate) {
		this.pckDate = pckDate;
	}
	/**
	 * @return the bookedBy
	 */
	public String getBookedBy() {
		return bookedBy;
	}
	/**
	 * @param bookedBy the bookedBy to set
	 */
	public void setBookedBy(String bookedBy) {
		this.bookedBy = bookedBy;
	}
	public OfficeTO getOfficeTO() {
		return officeTO;
	}
	public void setOfficeTO(OfficeTO officeTO) {
		this.officeTO = officeTO;
	}
	/**
	 * @return the consignmentTO
	 */
	public ConsignmentTO getConsignmentTO() {
		return consignmentTO;
	}
	/**
	 * @param consignmentTO the consignmentTO to set
	 */
	public void setConsignmentTO(ConsignmentTO consignmentTO) {
		this.consignmentTO = consignmentTO;
	}

	

}
