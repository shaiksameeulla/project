/**
 * 
 */
package com.ff.domain.umc.bcun;

import com.capgemini.lbs.framework.domain.CGMasterDO;

/**
 * @author mohammes
 *
 */
public class BcunMenuDO extends CGMasterDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7651201113855577578L;
	private Integer menuId;
	private Integer embeddedInMenu;
	private Integer position;
	private String menuType;
	private String menuLabel;
	private Integer appScreenId;
	private String appName;
	private String status;
	/**
	 * @return the menuId
	 */
	public Integer getMenuId() {
		return menuId;
	}
	/**
	 * @return the embeddedInMenu
	 */
	public Integer getEmbeddedInMenu() {
		return embeddedInMenu;
	}
	/**
	 * @return the position
	 */
	public Integer getPosition() {
		return position;
	}
	/**
	 * @return the menuType
	 */
	public String getMenuType() {
		return menuType;
	}
	/**
	 * @return the menuLabel
	 */
	public String getMenuLabel() {
		return menuLabel;
	}
	/**
	 * @return the appScreenId
	 */
	public Integer getAppScreenId() {
		return appScreenId;
	}
	/**
	 * @param menuId the menuId to set
	 */
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	/**
	 * @param embeddedInMenu the embeddedInMenu to set
	 */
	public void setEmbeddedInMenu(Integer embeddedInMenu) {
		this.embeddedInMenu = embeddedInMenu;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}
	/**
	 * @param menuType the menuType to set
	 */
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	/**
	 * @param menuLabel the menuLabel to set
	 */
	public void setMenuLabel(String menuLabel) {
		this.menuLabel = menuLabel;
	}
	/**
	 * @param appScreenId the appScreenId to set
	 */
	public void setAppScreenId(Integer appScreenId) {
		this.appScreenId = appScreenId;
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
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}
	/**
	 * @param appName the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

}
