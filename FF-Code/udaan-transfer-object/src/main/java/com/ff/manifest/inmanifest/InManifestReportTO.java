package com.ff.manifest.inmanifest;

import java.util.List;


/**
 * @author narmdr
 *
 */
public class InManifestReportTO extends InManifestTO {
	
	private static final long serialVersionUID = -8938069431583599652L;
	
	private List<LessExcessBaggageReportTO> lessExcessBaggageReportTOs;
	private int totalLessBaggages;
	private int totalExcessBaggages;
	
	/**
	 * @return the lessExcessBaggageReportTOs
	 */
	public List<LessExcessBaggageReportTO> getLessExcessBaggageReportTOs() {
		return lessExcessBaggageReportTOs;
	}
	/**
	 * @param lessExcessBaggageReportTOs the lessExcessBaggageReportTOs to set
	 */
	public void setLessExcessBaggageReportTOs(
			List<LessExcessBaggageReportTO> lessExcessBaggageReportTOs) {
		this.lessExcessBaggageReportTOs = lessExcessBaggageReportTOs;
	}
	/**
	 * @return the totalLessBaggages
	 */
	public int getTotalLessBaggages() {
		return totalLessBaggages;
	}
	/**
	 * @param totalLessBaggages the totalLessBaggages to set
	 */
	public void setTotalLessBaggages(int totalLessBaggages) {
		this.totalLessBaggages = totalLessBaggages;
	}
	/**
	 * @return the totalExcessBaggages
	 */
	public int getTotalExcessBaggages() {
		return totalExcessBaggages;
	}
	/**
	 * @param totalExcessBaggages the totalExcessBaggages to set
	 */
	public void setTotalExcessBaggages(int totalExcessBaggages) {
		this.totalExcessBaggages = totalExcessBaggages;
	}	
}
