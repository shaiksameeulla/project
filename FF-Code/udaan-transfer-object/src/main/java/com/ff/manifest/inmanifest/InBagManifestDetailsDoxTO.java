/**
 * 
 */
package com.ff.manifest.inmanifest;

/**
 * @author narmdr
 *
 */
public class InBagManifestDetailsDoxTO extends InManifestDetailsTO implements Comparable<InBagManifestDetailsDoxTO> {

	private static final long serialVersionUID = -2309263989312879677L;

	 //Print
	 private int noOfPcs;

	/**
	 * @return the noOfPcs
	 */
	public int getNoOfPcs() {
		return noOfPcs;
	}

	/**
	 * @param noOfPcs the noOfPcs to set
	 */
	public void setNoOfPcs(int noOfPcs) {
		this.noOfPcs = noOfPcs;
	}

	@Override
	public int compareTo(InBagManifestDetailsDoxTO detailsTO) {
		return this.getManifestId().compareTo(detailsTO.getManifestId());
	}
	 
	
}
