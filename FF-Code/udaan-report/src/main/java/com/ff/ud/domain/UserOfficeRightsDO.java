package com.ff.ud.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ff.ud.constants.OpsmanDBFConstant;




@Entity(name="com.ff.ud.domain.UserOfficeRightsDO")
@Table(name="ff_d_user_office_rights")
public class UserOfficeRightsDO {

	@Id
	@Column(name="USER_MAPPING_ID") private Integer usermappingId;
	@Column(name="USER_ID") private Integer userid;
	@Column(name="OFFICE_ID") private Integer officeid;
	@Column(name="MAPPED_TO") private String mappedto;
	@Column(name="STATUS") private String status;
	
	
	
	public UserOfficeRightsDO(){
		super();
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserOfficeRightsDO [usermappingId=" + usermappingId
				+ ", userid=" + userid + ", officeid=" + officeid
				+ ", mappedto=" + mappedto + ", status=" + status + "]";
	}



	/**
	 * @return the usermappingId
	 */
	public Integer getUsermappingId() {
		return usermappingId;
	}



	/**
	 * @param usermappingId the usermappingId to set
	 */
	public void setUsermappingId(Integer usermappingId) {
		this.usermappingId = usermappingId;
	}



	/**
	 * @return the userid
	 */
	public Integer getUserid() {
		return userid;
	}



	/**
	 * @param userid the userid to set
	 */
	public void setUserid(Integer userid) {
		this.userid = userid;
	}



	/**
	 * @return the officeid
	 */
	public Integer getOfficeid() {
		return officeid;
	}



	/**
	 * @param officeid the officeid to set
	 */
	public void setOfficeid(Integer officeid) {
		this.officeid = officeid;
	}



	/**
	 * @return the mappedto
	 */
	public String getMappedto() {
		return mappedto;
	}



	/**
	 * @param mappedto the mappedto to set
	 */
	public void setMappedto(String mappedto) {
		this.mappedto = mappedto;
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



	
}
