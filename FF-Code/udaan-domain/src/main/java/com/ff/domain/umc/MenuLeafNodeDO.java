package com.ff.domain.umc;

/**
 * The Class MenuLeafNodeDO.
 *
 * @author narmdr
 */
public class MenuLeafNodeDO extends MenuNodeDO {

	private static final long serialVersionUID = 7780605349095572399L;
	
	private MenuNodeDO menuNodeDO;

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
