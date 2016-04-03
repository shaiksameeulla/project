package com.ff.ud.dto;

public class ManifestBean {
	private String manifest_Id;
	private String manifest_No;
	private String operating_Office;
	private String oprigin_Office;
	
	
	public ManifestBean(){
		super();
	}
	
	public ManifestBean(String manifest_Id, String manifest_No, String operating_Office,
			String oprigin_Office) {
		super();
		this.manifest_Id = manifest_Id;
		this.manifest_No = manifest_No;
		this.operating_Office = operating_Office;
		this.oprigin_Office = oprigin_Office;
	}

	public String getManifest_Id() {
		return manifest_Id;
	}

	public void setManifest_Id(String manifest_Id) {
		this.manifest_Id = manifest_Id;
	}

	public String getManifest_No() {
		return manifest_No;
	}

	public void setManifest_No(String manifest_No) {
		this.manifest_No = manifest_No;
	}

	public String getOperating_Office() {
		return operating_Office;
	}

	public void setOperating_Office(String operating_Office) {
		this.operating_Office = operating_Office;
	}

	public String getOprigin_Office() {
		return oprigin_Office;
	}

	public void setOprigin_Office(String oprigin_Office) {
		this.oprigin_Office = oprigin_Office;
	}

	@Override
	public String toString() {
		return "ManifestBean [manifest_Id=" + manifest_Id + ", manifest_No="
				+ manifest_No + ", operating_Office=" + operating_Office
				+ ", oprigin_Office=" + oprigin_Office + "]";
	}

	
}
