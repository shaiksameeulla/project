/**
 * 
 */
package com.ff.to.drs.list;

import java.util.List;

import com.ff.to.drs.AbstractDeliveryTO;

/**
 * The Class ListDrsHeaderTO.
 *
 * @author mohammes
 */
public class ListDrsHeaderTO extends AbstractDeliveryTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1357277930501390535L;

	/** The list drs details. */
	private List<ListDrsDetailsTO> listDrsDetails;
	
	private String queryName;//for List DRS search

	/**
	 * Gets the list drs details.
	 *
	 * @return the listDrsDetails
	 */
	public final List<ListDrsDetailsTO> getListDrsDetails() {
		return listDrsDetails;
	}

	/**
	 * Sets the list drs details.
	 *
	 * @param listDrsDetails the listDrsDetails to set
	 */
	public final void setListDrsDetails(List<ListDrsDetailsTO> listDrsDetails) {
		this.listDrsDetails = listDrsDetails;
	}

	/**
	 * @return the queryName
	 */
	public String getQueryName() {
		return queryName;
	}

	/**
	 * @param queryName the queryName to set
	 */
	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}
}
