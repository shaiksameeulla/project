package com.ff.admin.coloading.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.coloading.CdAwbModificationTO;
import com.ff.geography.RegionTO;

public class CdAwbModificationForm extends CGBaseForm {

	
	private static final long serialVersionUID = 8350391868090719968L;

	public CdAwbModificationForm(){
		CdAwbModificationTO cdAwbModificationTO = new CdAwbModificationTO();
		RegionTO regionTO = new RegionTO();
		cdAwbModificationTO.setRegionTO(regionTO);		
		setTo(cdAwbModificationTO);
	}
}
