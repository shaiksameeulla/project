package com.ff.domain.booking;

import java.util.Date;



public class BookingInterfaceWrapperDO {

    /**
     * 
     */
    
    private Integer bookingOfficeId;
    private Double grandTotalIncludingTax;
	private Date bookingDate;
	//private Integer bookingOfficeID;
	/**
	 * @return the bookingOfficeId
	 */
	public Integer getBookingOfficeId() {
		return bookingOfficeId;
	}
	/**
	 * @param bookingOfficeId the bookingOfficeId to set
	 */
	public void setBookingOfficeId(Integer bookingOfficeId) {
		this.bookingOfficeId = bookingOfficeId;
	}
	/**
	 * @return the grandTotalIncludingTax
	 */
	public Double getGrandTotalIncludingTax() {
		return grandTotalIncludingTax;
	}
	/**
	 * @param grandTotalIncludingTax the grandTotalIncludingTax to set
	 */
	public void setGrandTotalIncludingTax(Double grandTotalIncludingTax) {
		this.grandTotalIncludingTax = grandTotalIncludingTax;
	}
	/**
	 * @return the bookingDate
	 */
	public Date getBookingDate() {
		return bookingDate;
	}
	/**
	 * @param bookingDate the bookingDate to set
	 */
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}
	
	public BookingInterfaceWrapperDO() {
		super();
	}
	
	public BookingInterfaceWrapperDO(Integer bookingOfficeId,Date bookingDate,Double grandTotalIncludingTax){
		super();
		this.bookingOfficeId = bookingOfficeId;
		this.bookingDate = bookingDate;
		this.grandTotalIncludingTax = grandTotalIncludingTax;
	}
    
   }
