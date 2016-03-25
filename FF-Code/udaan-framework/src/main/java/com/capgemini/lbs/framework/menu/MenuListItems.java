package com.capgemini.lbs.framework.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.utils.StringUtil;

/**
 * @author anwar
 * 
 */
public class MenuListItems {
	private Map<String, List<LeftMenuNavigationItems>> leftMenuMap = new HashMap<String, List<LeftMenuNavigationItems>>();
	private Map<String, String> mainMenuURLMap = new TreeMap<String, String>();
	private List<LeftMenuNavigationItems> navigationItemsList = new ArrayList<LeftMenuNavigationItems>();
	private List<Integer> screenIDList = null;

	/**
	 * @return
	 */
	public Map<String, List<LeftMenuNavigationItems>> getLeftMenuMap() {
		return leftMenuMap;
	}

	/**
	 * @return
	 */
	public List<LeftMenuNavigationItems> getNavigationItemsList() {
		return navigationItemsList;
	}

	/**
	 * @param modName
	 * @param leftMenuItems
	 */
	public void setMenuItems(String modName,LeftMenuNavigationItems leftMenuItems ){
		StringBuffer buff = new StringBuffer();
		if(modName != null){
			String urlName = leftMenuItems.getUrlName();
			Integer screenID = leftMenuItems.getScreenId();
			boolean isDuplicate = false;
			
			// Screen ID list check, to avoid duplicate screens - if mapped for multiple roles for user
			if(screenIDList != null){
				if(!screenIDList.contains(screenID)){
					screenIDList.add(screenID);
					isDuplicate = false;
				}else{
					isDuplicate = true;
				}
				
			}else{
				screenIDList = new ArrayList<Integer>();
				screenIDList.add(screenID);
				isDuplicate = false;
			}
			
			// If screen is non-duplicate, list of map will be maintained
			if(!isDuplicate){
				//Added by Narasimha for Silent login
				if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
						leftMenuItems.getIsCentralizedUrl())
						&& StringUtils.equalsIgnoreCase(
								FrameworkConstants.APP_NAME_WEB,
								leftMenuItems.getAppName())) {
					//Preparing silent 
					

				} else {
					// Custome url for specific modules (website routing)
					if (leftMenuItems.getUrlName().startsWith("http://")) {
						buff.append(urlName);
					} else {
						buff.append("/");
						buff.append(urlName);
						buff.append("?");
						buff.append(leftMenuItems.getParamName());
						buff.append("&modName=");
						buff.append(modName);
					}
				}
				leftMenuItems.setActionPathParam(buff.toString());
				
				if(leftMenuMap.containsKey(modName) && navigationItemsList != null){
					navigationItemsList = leftMenuMap.get(modName);
					navigationItemsList.add(leftMenuItems);
				}else{
					navigationItemsList = new ArrayList<LeftMenuNavigationItems>();
					navigationItemsList.add(leftMenuItems);			
					mainMenuURLMap.put(StringUtil.convertInitialLetterCap(modName), buff.toString()); // default URL 
				}				
				
				leftMenuMap.put(modName, navigationItemsList); // LeftMenuMap with Each Module Key	
			}
					
		}// end if main
		
	}

	/**
	 * @return
	 */
	public Map<String, List<LeftMenuNavigationItems>> getMenuAllItems() {
		return this.leftMenuMap;
	}

	/**
	 * @return the mainMenuURLMap
	 */
	public Map<String, String> getMainMenuURLMap() {
		return mainMenuURLMap;
	}

	/**
	 * @param mainMenuURLMap
	 *            the mainMenuURLMap to set
	 */
	public void setMainMenuURLMap(Map<String, String> mainMenuURLMap) {
		this.mainMenuURLMap = mainMenuURLMap;
	}

}
