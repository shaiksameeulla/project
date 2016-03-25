/**
 * 
 */
package com.ff.booking;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.geography.CityTO;

/**
 * @author shashsax
 * 
 */
public class FocBookingEmailTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7173409339048342805L;

	/* Properties for Email */
	private String emailFrom;
	private String[] emailTo;
	private String mailSubject;

	/* Properties for Approver / Employee */
	private String employeName;
	private String employeeId;
	private String employeeEmailId;

	/* Properties for booking */
	private String cNumber;
	private Date bookingDate;
	private Date orderDate;
	private CityTO destCityId;

	private List<BookingTO> bookingTOs;

	
	/**
	 * @return the bookingTOs
	 */
	public List<BookingTO> getBookingTOs() {
		return bookingTOs;
	}

	/**
	 * @param bookingTOs
	 *            the bookingTOs to set
	 */
	public void setBookingTOs(List<BookingTO> bookingTOs) {
		this.bookingTOs = bookingTOs;
	}

	/**
	 * @return the emailFrom
	 */
	public String getEmailFrom() {
		return emailFrom;
	}

	/**
	 * @param emailFrom
	 *            the emailFrom to set
	 */
	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}

	/**
	 * @return the emailTo
	 */
	public String[] getEmailTo() {
		return emailTo;
	}

	/**
	 * @param emailTo
	 *            the emailTo to set
	 */
	public void setEmailTo(String[] emailTo) {
		this.emailTo = emailTo;
	}

	/**
	 * @return the mailSubject
	 */
	public String getMailSubject() {
		return mailSubject;
	}

	/**
	 * @param mailSubject
	 *            the mailSubject to set
	 */
	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	/**
	 * @return the employeName
	 */
	public String getEmployeName() {
		return employeName;
	}

	/**
	 * @param employeName
	 *            the employeName to set
	 */
	public void setEmployeName(String employeName) {
		this.employeName = employeName;
	}

	/**
	 * @return the employeeId
	 */
	public String getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId
	 *            the employeeId to set
	 */
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * @return the employeeEmailId
	 */
	public String getEmployeeEmailId() {
		return employeeEmailId;
	}

	/**
	 * @param employeeEmailId
	 *            the employeeEmailId to set
	 */
	public void setEmployeeEmailId(String employeeEmailId) {
		this.employeeEmailId = employeeEmailId;
	}

	/**
	 * @return the cNumber
	 */
	public String getcNumber() {
		return cNumber;
	}

	/**
	 * @param cNumber
	 *            the cNumber to set
	 */
	public void setcNumber(String cNumber) {
		this.cNumber = cNumber;
	}

	/**
	 * @return the bookingDate
	 */
	public Date getBookingDate() {
		return bookingDate;
	}

	/**
	 * @param bookingDate
	 *            the bookingDate to set
	 */
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	/**
	 * @return the orderDate
	 */
	public Date getOrderDate() {
		return orderDate;
	}

	/**
	 * @param orderDate
	 *            the orderDate to set
	 */
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	/**
	 * @return the destCityId
	 */
	public CityTO getDestCityId() {
		return destCityId;
	}

	/**
	 * @param destCityId
	 *            the destCityId to set
	 */
	public void setDestCityId(CityTO destCityId) {
		this.destCityId = destCityId;
	}
}
