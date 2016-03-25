package com.ff.umc;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.capgemini.lbs.framework.utils.StringUtil;

/**
 * The Class MenuNodeTO.
 * 
 * @author narmdr
 */
public class MenuNodeTO extends CGBaseTO implements Comparable<MenuNodeTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6928092897539516057L;
	private Integer menuId;
	private Integer embeddedInMenu;
	private Integer position;
	private String menuType;
	private String menuLabel;
	private ApplScreensTO applScreensTO;

	// abstraction
	private MenuNodeTO menuNodeTO;
	private List<MenuNodeTO> menuNodeTOs;
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
	 * @return the menuType
	 */
	public String getMenuType() {
		return menuType;
	}

	/**
	 * @param menuType
	 *            the menuType to set
	 */
	public void setMenuType(String menuType) {
		this.menuType = menuType;
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
	 * @return the applScreensTO
	 */
	public ApplScreensTO getApplScreensTO() {
		return applScreensTO;
	}

	/**
	 * @param applScreensTO
	 *            the applScreensTO to set
	 */
	public void setApplScreensTO(ApplScreensTO applScreensTO) {
		this.applScreensTO = applScreensTO;
	}

	/**
	 * @return the menuNodeTO
	 */
	public MenuNodeTO getMenuNodeTO() {
		return menuNodeTO;
	}

	/**
	 * @param menuNodeTO
	 *            the menuNodeTO to set
	 */
	public void setMenuNodeTO(MenuNodeTO menuNodeTO) {
		this.menuNodeTO = menuNodeTO;
	}

	/**
	 * @return the menuNodeTOs
	 */
	public List<MenuNodeTO> getMenuNodeTOs() {
		return menuNodeTOs;
	}

	/**
	 * @param menuNodeTOs
	 *            the menuNodeTOs to set
	 */
	public void setMenuNodeTOs(List<MenuNodeTO> menuNodeTOs) {
		this.menuNodeTOs = menuNodeTOs;
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

	@Override
	public int compareTo(MenuNodeTO obj) {
		int result = 0;
		if (!StringUtil.isEmptyInteger(position)
				&& !StringUtil.isEmptyInteger(obj.getPosition())) {
			int curent = this.getPosition();
			int compareTo = obj.getPosition();
			if (curent == compareTo)
				result = 0;
			else if (curent > compareTo)
				result = 1;
			else
				result = -1;
		}
		return result;
	}
}
