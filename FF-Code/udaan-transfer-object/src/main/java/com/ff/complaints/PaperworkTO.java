package com.ff.complaints;

import com.capgemini.lbs.framework.to.CGBaseTO;
import org.apache.struts.upload.FormFile;

public class PaperworkTO extends CGBaseTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String complaintLetter;
	String consignorCopy;
	String mailCopy;
	String undertakingLetter;
	String invoiceLetter;
	String transferTOIcc;
	String clientMeet;
	String paperworkDateStr;
	String feedback;
	
	FormFile complaintLetterFile;
	FormFile consignorCopyFile;
	FormFile mailCopyFile;
	FormFile undertakingLetterFile;
	FormFile invoiceLetterFile;
	
	/**
	 * @return the complaintLetter
	 */
	public String getComplaintLetter() {
		return complaintLetter;
	}
	/**
	 * @param complaintLetter the complaintLetter to set
	 */
	public void setComplaintLetter(String complaintLetter) {
		this.complaintLetter = complaintLetter;
	}
	/**
	 * @return the consignorCopy
	 */
	public String getConsignorCopy() {
		return consignorCopy;
	}
	/**
	 * @param consignorCopy the consignorCopy to set
	 */
	public void setConsignorCopy(String consignorCopy) {
		this.consignorCopy = consignorCopy;
	}
	/**
	 * @return the mailCopy
	 */
	public String getMailCopy() {
		return mailCopy;
	}
	/**
	 * @param mailCopy the mailCopy to set
	 */
	public void setMailCopy(String mailCopy) {
		this.mailCopy = mailCopy;
	}
	/**
	 * @return the undertakingLetter
	 */
	public String getUndertakingLetter() {
		return undertakingLetter;
	}
	/**
	 * @param undertakingLetter the undertakingLetter to set
	 */
	public void setUndertakingLetter(String undertakingLetter) {
		this.undertakingLetter = undertakingLetter;
	}
	/**
	 * @return the invoiceLetter
	 */
	public String getInvoiceLetter() {
		return invoiceLetter;
	}
	/**
	 * @param invoiceLetter the invoiceLetter to set
	 */
	public void setInvoiceLetter(String invoiceLetter) {
		this.invoiceLetter = invoiceLetter;
	}
	/**
	 * @return the transferTOIcc
	 */
	public String getTransferTOIcc() {
		return transferTOIcc;
	}
	/**
	 * @param transferTOIcc the transferTOIcc to set
	 */
	public void setTransferTOIcc(String transferTOIcc) {
		this.transferTOIcc = transferTOIcc;
	}
	/**
	 * @return the clientMeet
	 */
	public String getClientMeet() {
		return clientMeet;
	}
	/**
	 * @param clientMeet the clientMeet to set
	 */
	public void setClientMeet(String clientMeet) {
		this.clientMeet = clientMeet;
	}
	/**
	 * @return the paperworkDateStr
	 */
	public String getPaperworkDateStr() {
		return paperworkDateStr;
	}
	/**
	 * @param paperworkDateStr the paperworkDateStr to set
	 */
	public void setPaperworkDateStr(String paperworkDateStr) {
		this.paperworkDateStr = paperworkDateStr;
	}
	/**
	 * @return the feedback
	 */
	public String getFeedback() {
		return feedback;
	}
	/**
	 * @param feedback the feedback to set
	 */
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	/**
	 * @return the complaintLetterFile
	 */
	public FormFile getComplaintLetterFile() {
		return complaintLetterFile;
	}
	/**
	 * @param complaintLetterFile the complaintLetterFile to set
	 */
	public void setComplaintLetterFile(FormFile complaintLetterFile) {
		this.complaintLetterFile = complaintLetterFile;
	}
	/**
	 * @return the consignorCopyFile
	 */
	public FormFile getConsignorCopyFile() {
		return consignorCopyFile;
	}
	/**
	 * @param consignorCopyFile the consignorCopyFile to set
	 */
	public void setConsignorCopyFile(FormFile consignorCopyFile) {
		this.consignorCopyFile = consignorCopyFile;
	}
	/**
	 * @return the mailCopyFile
	 */
	public FormFile getMailCopyFile() {
		return mailCopyFile;
	}
	/**
	 * @param mailCopyFile the mailCopyFile to set
	 */
	public void setMailCopyFile(FormFile mailCopyFile) {
		this.mailCopyFile = mailCopyFile;
	}
	/**
	 * @return the undertakingLetterFile
	 */
	public FormFile getUndertakingLetterFile() {
		return undertakingLetterFile;
	}
	/**
	 * @param undertakingLetterFile the undertakingLetterFile to set
	 */
	public void setUndertakingLetterFile(FormFile undertakingLetterFile) {
		this.undertakingLetterFile = undertakingLetterFile;
	}
	/**
	 * @return the invoiceLetterFile
	 */
	public FormFile getInvoiceLetterFile() {
		return invoiceLetterFile;
	}
	/**
	 * @param invoiceLetterFile the invoiceLetterFile to set
	 */
	public void setInvoiceLetterFile(FormFile invoiceLetterFile) {
		this.invoiceLetterFile = invoiceLetterFile;
	}

	
}
