package com.ff.ud.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ff.ud.constants.OpsmanDBFConstant;




@Entity(name="com.ff.ud.domain.UserDO")
@Table(name="ff_d_user")
public class UserDO {

	@Id
	@Column(name="USER_ID") private Integer userid;
	@Column(name="USER_CODE") private String usercode;
	@Column(name="USER_NAME") private String username;
	@Column(name="USER_TYPE") private String usertype;
	@Column(name="CREATION_DATE") private Date creationdate;
	@Column(name="ACTIVE") private String active;
	
	
	public UserDO(){
		super();
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
	 * @return the usercode
	 */
	public String getUsercode() {
		return usercode;
	}


	/**
	 * @param usercode the usercode to set
	 */
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}


	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}


	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}


	/**
	 * @return the usertype
	 */
	public String getUsertype() {
		return usertype;
	}


	/**
	 * @param usertype the usertype to set
	 */
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}


	/**
	 * @return the creationdate
	 */
	public Date getCreationdate() {
		return creationdate;
	}


	/**
	 * @param creationdate the creationdate to set
	 */
	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}


	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}


	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}



	

	
}
