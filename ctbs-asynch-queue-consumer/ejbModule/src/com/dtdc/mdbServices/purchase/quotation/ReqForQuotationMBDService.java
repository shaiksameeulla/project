package src.com.dtdc.mdbServices.purchase.quotation;

import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface ReqForQuotationService.
 */
public interface ReqForQuotationMBDService{
	
	/**
	 * Save req quotation.
	 *
	 * @param reqQuotationTO the req quotation to
	 * @throws Exception the exception
	 */
	public  void saveReqQuotation(CGBaseTO reqQuotationTO) throws Exception;
}
