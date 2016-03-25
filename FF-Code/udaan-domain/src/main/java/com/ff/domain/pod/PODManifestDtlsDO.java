/**
 * 
 */
package com.ff.domain.pod;
import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.pod.PODManifestDO;

/**
 * @author prmeher
 *
 */
public class PODManifestDtlsDO extends CGFactDO  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** The manifest details id. */
	private Integer manifestDetailId;
	
	/** The manifest id. */
	private PODManifestDO manifestId;
	
	private String consgNo;
	
	/** The Received date. */
	private Date receivedDate;
	
	/** The DLV date and time. */
	private Date dlvDate;
	
	/** The RECV name or Comp seal. */
	private String recvNameOrCompSeal;
	
	private Integer position;
	
	
	
	/**
	 * @return the position
	 */
	public Integer getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}

	/**
	 * @return the manifestDetailId
	 */
	public Integer getManifestDetailId() {
		return manifestDetailId;
	}

	/**
	 * @param manifestDetailId the manifestDetailId to set
	 */
	public void setManifestDetailId(Integer manifestDetailId) {
		this.manifestDetailId = manifestDetailId;
	}

	/**
	 * @return the manifestId
	 */
	public PODManifestDO getManifestId() {
		return manifestId;
	}

	/**
	 * @param manifestId the manifestId to set
	 */
	public void setManifestId(PODManifestDO manifestId) {
		this.manifestId = manifestId;
	}

	/**
	 * @return the consgNo
	 */
	public String getConsgNo() {
		return consgNo;
	}

	/**
	 * @param consgNo the consgNo to set
	 */
	public void setConsgNo(String consgNo) {
		this.consgNo = consgNo;
	}

	/**
	 * @return the receivedDate
	 */
	public Date getReceivedDate() {
		return receivedDate;
	}

	/**
	 * @param receivedDate the receivedDate to set
	 */
	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	/**
	 * @return the dlvDate
	 */
	public Date getDlvDate() {
		return dlvDate;
	}

	/**
	 * @param dlvDate the dlvDate to set
	 */
	public void setDlvDate(Date dlvDate) {
		this.dlvDate = dlvDate;
	}

	/**
	 * @return the recvNameOrCompSeal
	 */
	public String getRecvNameOrCompSeal() {
		return recvNameOrCompSeal;
	}

	/**
	 * @param recvNameOrCompSeal the recvNameOrCompSeal to set
	 */
	public void setRecvNameOrCompSeal(String recvNameOrCompSeal) {
		this.recvNameOrCompSeal = recvNameOrCompSeal;
	}

	/**
	 * @return the receivedStatus
	 */
	public String getReceivedStatus() {
		return receivedStatus;
	}

	/**
	 * @param receivedStatus the receivedStatus to set
	 */
	public void setReceivedStatus(String receivedStatus) {
		this.receivedStatus = receivedStatus;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**The Received status. */
	private String receivedStatus;
	
}
