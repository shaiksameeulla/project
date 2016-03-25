/**
 * 
 */
package com.ff.tracking;

import java.util.List;

import org.apache.struts.upload.FormFile;

/**
 * @author uchauhan
 *
 */
public class TrackingBulkImportTO extends TrackingBaseTO{

    
    /**
     * 
     */
    private static final long serialVersionUID = 3243578985513193193L;
    
    private FormFile fileUpload;
    private List<String> validConsg;
    private List<String> inValidConsg;
    private List<String> inValidRef;
    // String docType;
    
    private String consgNum;
    private String refNum;
    private String bookingDate;
    private String origin;
    private String destination;
    private String status;
    private String pendingReason;
    private String delvDate;
    private String rcvrName;
    private String weight;
    private String ogmNum;
    private String ogmDate;
    private String bplNum;
    private String bplDate;
    private String cdNum;
    private String cdDate;
    private String flightNum;
    private String flightDept;
    private String flightArrvl;
    private String rcvDate;
    private String manifestDate;
    
  //Enhancement added DRS No,FS/Third Party Name ,delivery branch name
  	private String drsNo;
  	private String thirdPartyName;
  	private String dlvBranchName;
    /**
     * @return the validConsg
     */
    public List<String> getValidConsg() {
        return validConsg;
    }
    /**
     * @param validConsg the validConsg to set
     */
    public void setValidConsg(List<String> validConsg) {
        this.validConsg = validConsg;
    }
    /**
     * @return the inValidConsg
     */
    public List<String> getInValidConsg() {
        return inValidConsg;
    }
    /**
     * @param inValidConsg the inValidConsg to set
     */
    public void setInValidConsg(List<String> inValidConsg) {
        this.inValidConsg = inValidConsg;
    }
   
    
    /**
     * @return the consgNum
     */
    public String getConsgNum() {
        return consgNum;
    }
    /**
     * @param consgNum the consgNum to set
     */
    public void setConsgNum(String consgNum) {
        this.consgNum = consgNum;
    }
    /**
     * @return the refNum
     */
    public String getRefNum() {
        return refNum;
    }
    /**
     * @param refNum the refNum to set
     */
    public void setRefNum(String refNum) {
        this.refNum = refNum;
    }
    /**
     * @return the bookingDate
     */
    public String getBookingDate() {
        return bookingDate;
    }
    /**
     * @param bookingDate the bookingDate to set
     */
    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }
    /**
     * @return the origin
     */
    public String getOrigin() {
        return origin;
    }
    /**
     * @param origin the origin to set
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }
    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * @return the pendingReason
     */
    public String getPendingReason() {
        return pendingReason;
    }
    /**
     * @param pendingReason the pendingReason to set
     */
    public void setPendingReason(String pendingReason) {
        this.pendingReason = pendingReason;
    }
    /**
     * @return the delvDate
     */
    public String getDelvDate() {
        return delvDate;
    }
    /**
     * @param delvDate the delvDate to set
     */
    public void setDelvDate(String delvDate) {
        this.delvDate = delvDate;
    }
    public String getRcvrName() {
		return rcvrName;
	}
    public void setRcvrName(String rcvrName) {
		this.rcvrName = rcvrName;
	}
    
    /**
	 * @return the weight
	 */
	public String getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(String weight) {
		this.weight = weight;
	}
	/**
     * @return the ogmNum
     */
    public String getOgmNum() {
        return ogmNum;
    }
    /**
     * @param ogmNum the ogmNum to set
     */
    public void setOgmNum(String ogmNum) {
        this.ogmNum = ogmNum;
    }
    /**
     * @return the ogmDate
     */
    public String getOgmDate() {
        return ogmDate;
    }
    /**
     * @param ogmDate the ogmDate to set
     */
    public void setOgmDate(String ogmDate) {
        this.ogmDate = ogmDate;
    }
    /**
     * @return the bplNum
     */
    public String getBplNum() {
        return bplNum;
    }
    /**
     * @param bplNum the bplNum to set
     */
    public void setBplNum(String bplNum) {
        this.bplNum = bplNum;
    }
    /**
     * @return the bplDate
     */
    public String getBplDate() {
        return bplDate;
    }
    /**
     * @param bplDate the bplDate to set
     */
    public void setBplDate(String bplDate) {
        this.bplDate = bplDate;
    }
    /**
     * @return the cdNum
     */
    public String getCdNum() {
        return cdNum;
    }
    /**
     * @param cdNum the cdNum to set
     */
    public void setCdNum(String cdNum) {
        this.cdNum = cdNum;
    }
    /**
     * @return the cdDate
     */
    public String getCdDate() {
        return cdDate;
    }
    /**
     * @param cdDate the cdDate to set
     */
    public void setCdDate(String cdDate) {
        this.cdDate = cdDate;
    }
    /**
     * @return the flightNum
     */
    public String getFlightNum() {
        return flightNum;
    }
    /**
     * @param flightNum the flightNum to set
     */
    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }
    /**
     * @return the flightDept
     */
    public String getFlightDept() {
        return flightDept;
    }
    /**
     * @param flightDept the flightDept to set
     */
    public void setFlightDept(String flightDept) {
        this.flightDept = flightDept;
    }
    /**
     * @return the flightArrvl
     */
    public String getFlightArrvl() {
        return flightArrvl;
    }
    /**
     * @param flightArrvl the flightArrvl to set
     */
    public void setFlightArrvl(String flightArrvl) {
        this.flightArrvl = flightArrvl;
    }
    /**
     * @return the rcvDate
     */
    public String getRcvDate() {
        return rcvDate;
    }
    /**
     * @param rcvDate the rcvDate to set
     */
    public void setRcvDate(String rcvDate) {
        this.rcvDate = rcvDate;
    }
    /**
     * @return the manifestDate
     */
    public String getManifestDate() {
        return manifestDate;
    }
    /**
     * @param manifestDate the manifestDate to set
     */
    public void setManifestDate(String manifestDate) {
        this.manifestDate = manifestDate;
    }
    /**
     * @return the fileUpload
     */
    public FormFile getFileUpload() {
	return fileUpload;
    }
    /**
     * @param fileUpload the fileUpload to set
     */
    public void setFileUpload(FormFile fileUpload) {
	this.fileUpload = fileUpload;
    }
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	/**
	 * @return the inValidRef
	 */
	public List<String> getInValidRef() {
		return inValidRef;
	}
	/**
	 * @param inValidRef the inValidRef to set
	 */
	public void setInValidRef(List<String> inValidRef) {
		this.inValidRef = inValidRef;
	}
	public String getDrsNo() {
		return drsNo;
	}
	public void setDrsNo(String drsNo) {
		this.drsNo = drsNo;
	}
	public String getThirdPartyName() {
		return thirdPartyName;
	}
	public void setThirdPartyName(String thirdPartyName) {
		this.thirdPartyName = thirdPartyName;
	}
	public String getDlvBranchName() {
		return dlvBranchName;
	}
	public void setDlvBranchName(String dlvBranchName) {
		this.dlvBranchName = dlvBranchName;
	}      
}
