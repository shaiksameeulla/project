/**
 * 
 */
package com.ff.to.drs.list;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.to.drs.AbstractDeliveryDetailTO;
import com.ff.to.drs.CodLcDrsDetailsTO;

/**
 * The Class ListDrsDetailsTO.
 *
 * @author mohammes
 */
public class ListDrsDetailsTO extends CGBaseTO implements Comparable<ListDrsDetailsTO>{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7778438329463758885L;
	
	/** The drs number. */
	private String drsNumber;
	
	/** The url. */
	private String url;

	/**
	 * Gets the drs number.
	 *
	 * @return the drsNumber
	 */
	public final String getDrsNumber() {
		return drsNumber;
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public final String getUrl() {
		return url;
	}

	/**
	 * Sets the drs number.
	 *
	 * @param drsNumber the drsNumber to set
	 */
	public final void setDrsNumber(String drsNumber) {
		this.drsNumber = drsNumber;
	}

	/**
	 * Sets the url.
	 *
	 * @param url the url to set
	 */
	public final void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public int compareTo(ListDrsDetailsTO arg0) {
		int result=0;
		if(!StringUtil.isStringEmpty(drsNumber) && !StringUtil.isStringEmpty(arg0.getDrsNumber())) {
			result = this.drsNumber.compareTo(arg0.drsNumber);
		}
		return result;
	}
}
