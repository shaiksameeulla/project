package com.ff.admin.notification.form;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.notification.BulkSmsConsignmentDtlsTO;
import com.ff.notification.BulkSmsOnDemandTO;

public class BulkSmsOnDemandForm extends CGBaseForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BulkSmsOnDemandForm(){
		List<BulkSmsConsignmentDtlsTO> bulkSmsConsignmentDtlsTOs = new ArrayList<BulkSmsConsignmentDtlsTO>();
		BulkSmsOnDemandTO to = new BulkSmsOnDemandTO();
		to.setConsignmentDtlTOs(bulkSmsConsignmentDtlsTOs);
		setTo(to);
	}
}
