package com.ff.domain.umc;

import java.io.Serializable;
import java.util.List;

import com.capgemini.lbs.framework.domain.CGFactDO;




public class UserLoginDO extends CGFactDO implements Serializable{
  private UserDO user;
  private UserRolesDO role;
  private List rights;
/**
 * @return the user
 */
public UserDO getUser() {
	return user;
}
/**
 * @param user the user to set
 */
public void setUser(UserDO user) {
	this.user = user;
}

public UserRolesDO getRole() {
	return role;
}
public void setRole(UserRolesDO role) {
	this.role = role;
}
/**
 * @return the rights
 */
public List getRights() {
	return rights;
}
/**
 * @param rights the rights to set
 */
public void setRights(List rights) {
	this.rights = rights;
}
  
}
