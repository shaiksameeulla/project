package com.ff.domain.umc;

import java.util.Set;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.capgemini.lbs.framework.utils.StringUtil;


/**
 * The Class MenuNodeDO.
 * 
 * @author narmdr
 */
public class MenuNodeDO extends CGMasterDO implements Comparable<MenuNodeDO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7046733426402814555L;
	private Integer menuId;
	private Integer embeddedInMenu;
	private Integer position;
	// private String menuType;
	private String menuLabel;
	private ApplScreenDO applScreenDO;
	private String appName;
	private String status;

	// abstraction
	private MenuNodeDO menuNodeDO;
	private Set<MenuCompositeNodeDO> menuNodeDOs;
	private String isSilentLogin;

	/**
	 * @return the menuId
	 */
	public Integer getMenuId() {
		return menuId;
	}

	/**
	 * @param menuId
	 *            the menuId to set
	 */
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	/**
	 * @return the embeddedInMenu
	 */
	public Integer getEmbeddedInMenu() {
		return embeddedInMenu;
	}

	/**
	 * @param embeddedInMenu
	 *            the embeddedInMenu to set
	 */
	public void setEmbeddedInMenu(Integer embeddedInMenu) {
		this.embeddedInMenu = embeddedInMenu;
	}

	/**
	 * @return the position
	 */
	public Integer getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}

	/**
	 * @return the menuLabel
	 */
	public String getMenuLabel() {
		return menuLabel;
	}

	/**
	 * @param menuLabel
	 *            the menuLabel to set
	 */
	public void setMenuLabel(String menuLabel) {
		this.menuLabel = menuLabel;
	}

	/**
	 * @return the applScreenDO
	 */
	public ApplScreenDO getApplScreenDO() {
		return applScreenDO;
	}

	/**
	 * @param applScreenDO
	 *            the applScreenDO to set
	 */
	public void setApplScreenDO(ApplScreenDO applScreenDO) {
		this.applScreenDO = applScreenDO;
	}

	/**
	 * @return the menuNodeDO
	 */
	public MenuNodeDO getMenuNodeDO() {
		return menuNodeDO;
	}

	/**
	 * @param menuNodeDO
	 *            the menuNodeDO to set
	 */
	public void setMenuNodeDO(MenuNodeDO menuNodeDO) {
		this.menuNodeDO = menuNodeDO;
	}

	/**
	 * @return the menuNodeDOs
	 */
	public Set<MenuCompositeNodeDO> getMenuNodeDOs() {
		return menuNodeDOs;
	}

	/**
	 * @param menuNodeDOs
	 *            the menuNodeDOs to set
	 */
	public void setMenuNodeDOs(Set<MenuCompositeNodeDO> menuNodeDOs) {
		this.menuNodeDOs = menuNodeDOs;
	}

	/**
	 * @return the isSilentLogin
	 */
	public String getIsSilentLogin() {
		return isSilentLogin;
	}

	/**
	 * @param isSilentLogin
	 *            the isSilentLogin to set
	 */
	public void setIsSilentLogin(String isSilentLogin) {
		this.isSilentLogin = isSilentLogin;
	}

	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param appName
	 *            the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
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

	@Override
	public int compareTo(MenuNodeDO obj) {
		int result = 0;
		if (!StringUtil.isEmptyInteger(position)
				&& !StringUtil.isEmptyInteger(obj.getPosition())) {
			int curent = this.getPosition();
			int compareTo = obj.getPosition();
			if (curent == compareTo)
				result = 0;
			else if (curent < compareTo)
				result = -1;
			else
				result = 1;
		}
		return result;
	}
	

}
