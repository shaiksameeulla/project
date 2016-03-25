/**
 * 
 */
package com.ff.domain.booking;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.geography.CityDO;

/**
 * @author shashsax
 * 
 */
public class FocBookingEmailDO extends CGFactDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2918417451980271176L;
	/* Properties for Email */
	private String emailFrom;
	private String[] emailTo;
	private String mailSubject;

	/* Properties for Approver / Employee */
	private String employeName;
	private Integer employeeId;
	private String employeeEmailId;

	/* Properties for booking */
	private String cNumber;
	private Date bookingDate;
	private Date orderDate;
	private CityDO destCityId;

	private List<BookingDO> bookingDOs;

	/**
	 * @return the bookingDOs
	 */
	public List<BookingDO> getBookingDOs() {
		return bookingDOs;
	}

	/**
	 * @param bookingDOs
	 *            the bookingDOs to set
	 */
	public void setBookingDOs(List<BookingDO> bookingDOs) {
		this.bookingDOs = bookingDOs;
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
	public Integer getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId
	 *            the employeeId to set
	 */
	public void setEmployeeId(Integer employeeId) {
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
	public CityDO getDestCityId() {
		return destCityId;
	}

	/**
	 * @param destCityId
	 *            the destCityId to set
	 */
	public void setDestCityId(CityDO destCityId) {
		this.destCityId = destCityId;
	}

}
