/**
 * 
 */
package com.ff.domain.stockmanagement.operations.transfer;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * The Class SAPStockTransferDO.
 *
 * @author cbhure
 */
	public class SAPStockTransferDO extends CGFactDO{

	
		/**
	 * 
	 */
	private static final long serialVersionUID = 6467783327507666880L;
	
		private Long Id;
		private String baNo;
		private String itemCode;
		private Date returnDate;
		private String returnNumber;
		private String returnOfficeCode;
		private Integer returnQty;
		private String issueNumber;
		private String exception;
		/**
		 * @return the id
		 */
		public Long getId() {
			return Id;
		}
		/**
		 * @param id the id to set
		 */
		public void setId(Long id) {
			Id = id;
		}
		/**
		 * @return the baNo
		 */
		public String getBaNo() {
			return baNo;
		}
		/**
		 * @param baNo the baNo to set
		 */
		public void setBaNo(String baNo) {
			this.baNo = baNo;
		}
		/**
		 * @return the itemCode
		 */
		public String getItemCode() {
			return itemCode;
		}
		/**
		 * @param itemCode the itemCode to set
		 */
		public void setItemCode(String itemCode) {
			this.itemCode = itemCode;
		}
		/**
		 * @return the returnDate
		 */
		public Date getReturnDate() {
			return returnDate;
		}
		/**
		 * @param returnDate the returnDate to set
		 */
		public void setReturnDate(Date returnDate) {
			this.returnDate = returnDate;
		}
		/**
		 * @return the returnNumber
		 */
		public String getReturnNumber() {
			return returnNumber;
		}
		/**
		 * @param returnNumber the returnNumber to set
		 */
		public void setReturnNumber(String returnNumber) {
			this.returnNumber = returnNumber;
		}
		/**
		 * @return the returnOfficeCode
		 */
		public String getReturnOfficeCode() {
			return returnOfficeCode;
		}
		/**
		 * @param returnOfficeCode the returnOfficeCode to set
		 */
		public void setReturnOfficeCode(String returnOfficeCode) {
			this.returnOfficeCode = returnOfficeCode;
		}
		/**
		 * @return the returnQty
		 */
		public Integer getReturnQty() {
			return returnQty;
		}
		/**
		 * @param returnQty the returnQty to set
		 */
		public void setReturnQty(Integer returnQty) {
			this.returnQty = returnQty;
		}
		/**
		 * @return the issueNumber
		 */
		public String getIssueNumber() {
			return issueNumber;
		}
		/**
		 * @param issueNumber the issueNumber to set
		 */
		public void setIssueNumber(String issueNumber) {
			this.issueNumber = issueNumber;
		}
		/**
		 * @return the exception
		 */
		public String getException() {
			return exception;
		}
		/**
		 * @param exception the exception to set
		 */
		public void setException(String exception) {
			this.exception = exception;
		}
		
	}
