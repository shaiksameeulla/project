/**
 * 
 */
package com.ff.manifest.inmanifest;

import java.util.List;

import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.InsuredByTO;


/**
 * @author narmdr
 *
 */
public class InBagManifestParcelTO extends InBagManifestTO {

	private static final long serialVersionUID = -8143280727837432399L;
	
	private List<CNContentTO> cnContentTOs;
	private List<InsuredByTO> insuredByTOs;
	private List<InBagManifestDetailsParcelTO>  inBagManifestDetailsParcelTOs ;
	private int rowCount;
	private double finalWt;
	private int totalPieces;
	
	/**
	 * @return the cnContentTOs
	 */
	public List<CNContentTO> getCnContentTOs() {
		return cnContentTOs;
	}
	/**
	 * @param cnContentTOs the cnContentTOs to set
	 */
	public void setCnContentTOs(List<CNContentTO> cnContentTOs) {
		this.cnContentTOs = cnContentTOs;
	}
	/**
	 * @return the insuredByTOs
	 */
	public List<InsuredByTO> getInsuredByTOs() {
		return insuredByTOs;
	}
	/**
	 * @param insuredByTOs the insuredByTOs to set
	 */
	public void setInsuredByTOs(List<InsuredByTO> insuredByTOs) {
		this.insuredByTOs = insuredByTOs;
	}
	/**
	 * @return the inBagManifestDetailsParcelTOs
	 */
	public List<InBagManifestDetailsParcelTO> getInBagManifestDetailsParcelTOs() {
		return inBagManifestDetailsParcelTOs;
	}
	/**
	 * @param inBagManifestDetailsParcelTOs the inBagManifestDetailsParcelTOs to set
	 */
	public void setInBagManifestDetailsParcelTOs(
			List<InBagManifestDetailsParcelTO> inBagManifestDetailsParcelTOs) {
		this.inBagManifestDetailsParcelTOs = inBagManifestDetailsParcelTOs;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public double getFinalWt() {
		return finalWt;
	}
	public void setFinalWt(double finalWt) {
		this.finalWt = finalWt;
	}
	public int getTotalPieces() {
		return totalPieces;
	}
	public void setTotalPieces(int totalPieces) {
		this.totalPieces = totalPieces;
	}
}
