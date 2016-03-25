/**
 * 
 */
package com.ff.domain.stockmanagement.operations.stockreturn;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * The Class StockReturnDO.
 *
 * @author cbhure
 */
	public class SAPStockReturnDO extends CGFactDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -865054544119277697L;
	
		private Long Id;
		private Date returnDate;
		private Integer returnQty;
		private String itemCode;
		private String returningOfcCode;
		private String returnNumber;
		private Date issueDate;
		private String custNo;
		private String issueNumber;
		private String exception;
		
		
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
		 * @return the returningOfcCode
		 */
		public String getReturningOfcCode() {
			return returningOfcCode;
		}
		/**
		 * @param returningOfcCode the returningOfcCode to set
		 */
		public void setReturningOfcCode(String returningOfcCode) {
			this.returningOfcCode = returningOfcCode;
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
		 * @return the issueDate
		 */
		public Date getIssueDate() {
			return issueDate;
		}
		/**
		 * @param issueDate the issueDate to set
		 */
		public void setIssueDate(Date issueDate) {
			this.issueDate = issueDate;
		}
		/**
		 * @return the custNo
		 */
		public String getCustNo() {
			return custNo;
		}
		/**
		 * @param custNo the custNo to set
		 */
		public void setCustNo(String custNo) {
			this.custNo = custNo;
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
		
		
	}
