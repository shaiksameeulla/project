package com.ff.domain.umc;

import java.util.Set;


/**
 * The Class MenuCompositeNodeDO.
 *
 * @author narmdr
 */
public class MenuCompositeNodeDO extends MenuNodeDO {

	private static final long serialVersionUID = 5023186505089833532L;

	private Set<MenuCompositeNodeDO> menuNodeDOs;
	private MenuNodeDO menuNodeDO;

	/**
	 * @return the menuNodeDOs
	 */
	public Set<MenuCompositeNodeDO> getMenuNodeDOs() {
		return menuNodeDOs;
	}

	/**
	 * @param menuNodeDOs the menuNodeDOs to set
	 */
	public void setMenuNodeDOs(Set<MenuCompositeNodeDO> menuNodeDOs) {
		this.menuNodeDOs = menuNodeDOs;
	}

	/**
	 * @return the menuNodeDO
	 */
	public MenuNodeDO getMenuNodeDO() {
		return menuNodeDO;
	}

	/**
	 * @param menuNodeDO the menuNodeDO to set
	 */
	public void setMenuNodeDO(MenuNodeDO menuNodeDO) {
		this.menuNodeDO = menuNodeDO;
	}
}
