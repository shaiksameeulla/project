package com.ff.ud.domain;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ff.ud.constants.OpsmanDBFConstant;
import com.ff.ud.utils.DateUtils;
import com.ff.ud.utils.StringUtils;



@Entity(name="com.ff.ud.domain.RightsForUserDO")
@Table(name=OpsmanDBFConstant.DATABASE_NAME_MASTER+"ff_d_user_office_rights")
public class RightsForUserDO {
	
	@Id
	@Column(name="USER_ID") private Integer userId;
	@Column(name="OFFICE_ID") private String officeId;
	
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getOfficeId() {
		return officeId;
	}
	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	
	
	
}
