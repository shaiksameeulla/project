package com.ff.umc;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;


//  @ Project : Untitled
//  @ File Name : UserLoginTO.java
//  @ Date : 10/4/2012
//  @ Author : 
//


public class UserLoginTO extends CGBaseTO
{

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	private String userName;
	private String password;
	protected String action = null;
	
	 private UserTO user;
	 private List rights;
/**
 * @return the user
 */
public UserTO getUser() {
	return user;
}
/**
 * @param user the user to set
 */
public void setUser(UserTO user) {
	this.user = user;
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
