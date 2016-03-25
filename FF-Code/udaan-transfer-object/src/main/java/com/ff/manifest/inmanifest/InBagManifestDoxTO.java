/**
 * 
 */
package com.ff.manifest.inmanifest;

import java.util.List;

/**
 * @author narmdr
 *
 */
public class InBagManifestDoxTO extends InBagManifestTO {

	private static final long serialVersionUID = 2209908727048264713L;
	
	private int rowCount;
    private Long totalConsg;
	private List<InBagManifestDetailsDoxTO> inBagManifestDetailsDoxTOs;

	/**
	 * @return the inBagManifestDetailsDoxTOs
	 */
	public List<InBagManifestDetailsDoxTO> getInBagManifestDetailsDoxTOs() {
		return inBagManifestDetailsDoxTOs;
	}

	/**
	 * @param inBagManifestDetailsDoxTOs the inBagManifestDetailsDoxTOs to set
	 */
	public void setInBagManifestDetailsDoxTOs(
			List<InBagManifestDetailsDoxTO> inBagManifestDetailsDoxTOs) {
		this.inBagManifestDetailsDoxTOs = inBagManifestDetailsDoxTOs;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * @return the totalConsg
	 */
	public Long getTotalConsg() {
		return totalConsg;
	}

	/**
	 * @param totalConsg the totalConsg to set
	 */
	public void setTotalConsg(Long totalConsg) {
		this.totalConsg = totalConsg;
	}
	
	
}
