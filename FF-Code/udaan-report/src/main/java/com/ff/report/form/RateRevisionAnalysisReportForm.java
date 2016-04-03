/**
 * 
 */
package com.ff.report.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.report.RateRevisionAnalysisReportTO;

/**
 * @author shashsax
 * 
 */
public class RateRevisionAnalysisReportForm extends CGBaseForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5924563705093665776L;

	public RateRevisionAnalysisReportForm() {
		setTo(new RateRevisionAnalysisReportTO());
	}
}
