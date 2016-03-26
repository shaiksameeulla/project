package com.ff.web.manifest.action;

import java.util.List;

import com.ff.web.pickup.action.GeneratePickUpPageContent;

@SuppressWarnings("unused")
public class BranchManifestPage {

	List<BranchManifestPageContent> firstCol;
	List<BranchManifestPageContent> secondCol;
	
	public List<BranchManifestPageContent> getFirstCol() {
		return firstCol;
	}
	public void setFirstCol(List<BranchManifestPageContent> firstCol) {
		this.firstCol = firstCol;
	}
	public List<BranchManifestPageContent> getSecondCol() {
		return secondCol;
	}
	public void setSecondCol(List<BranchManifestPageContent> secondCol) {
		this.secondCol = secondCol;
	}
	
	
}
