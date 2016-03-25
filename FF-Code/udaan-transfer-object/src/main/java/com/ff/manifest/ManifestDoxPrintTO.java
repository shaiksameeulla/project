package com.ff.manifest;

import java.util.List;

public class ManifestDoxPrintTO {
	// For OGM
	private List<OutManifestDoxDetailsTO> leftOGMList;
	private List<OutManifestDoxDetailsTO> rightOGMList;

	// For BPL DOX
	private List<BPLOutManifestDoxDetailsTO> leftBPLDoxList;
	private List<BPLOutManifestDoxDetailsTO> rightBPLDoxList;
	
	//For branch Manifest DOX
	private List<BranchOutManifestDoxDetailsTO> leftBranchDoxList;
	private List<BranchOutManifestDoxDetailsTO> rightBranchDoxList;
	
	
	
	public List<BranchOutManifestDoxDetailsTO> getLeftBranchDoxList() {
		return leftBranchDoxList;
	}

	public void setLeftBranchDoxList(
			List<BranchOutManifestDoxDetailsTO> leftBranchDoxList) {
		this.leftBranchDoxList = leftBranchDoxList;
	}

	public List<BranchOutManifestDoxDetailsTO> getRightBranchDoxList() {
		return rightBranchDoxList;
	}

	public void setRightBranchDoxList(
			List<BranchOutManifestDoxDetailsTO> rightBranchDoxList) {
		this.rightBranchDoxList = rightBranchDoxList;
	}

	public List<BPLOutManifestDoxDetailsTO> getLeftBPLDoxList() {
		return leftBPLDoxList;
	}

	public void setLeftBPLDoxList(
			List<BPLOutManifestDoxDetailsTO> leftBPLDoxList) {
		this.leftBPLDoxList = leftBPLDoxList;
	}

	public List<BPLOutManifestDoxDetailsTO> getRightBPLDoxList() {
		return rightBPLDoxList;
	}

	public void setRightBPLDoxList(
			List<BPLOutManifestDoxDetailsTO> rightBPLDoxList) {
		this.rightBPLDoxList = rightBPLDoxList;
	}

	public List<OutManifestDoxDetailsTO> getLeftOGMList() {
		return leftOGMList;
	}

	public void setLeftOGMList(List<OutManifestDoxDetailsTO> leftOGMList) {
		this.leftOGMList = leftOGMList;
	}

	public List<OutManifestDoxDetailsTO> getRightOGMList() {
		return rightOGMList;
	}

	public void setRightOGMList(List<OutManifestDoxDetailsTO> rightOGMList) {
		this.rightOGMList = rightOGMList;
	}

}
