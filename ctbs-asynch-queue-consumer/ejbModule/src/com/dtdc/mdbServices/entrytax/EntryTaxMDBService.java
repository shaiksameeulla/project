package src.com.dtdc.mdbServices.entrytax;

import org.json.JSONObject;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.dtdc.to.entryTax.EntryTaxConfigTO;
import com.dtdc.to.entryTax.EntryTaxDetailsTO;

// TODO: Auto-generated Javadoc
/**
 * UC_27_Entry Tax Octroi Module.
 *
 * @author cjaganna
 */
public interface EntryTaxMDBService {

	
	/**
	 * Save tax config details.
	 *
	 * @param to the to
	 * @return the integer
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Integer saveTaxConfigDetails(EntryTaxConfigTO to) throws CGBusinessException,CGSystemException;
	
	/**
	 * Save tax config details.
	 *
	 * @param to the to
	 * @return the integer
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Integer saveTaxConfigDetails(CGBaseTO to) throws CGBusinessException,CGSystemException;
	
	
	
	/**
	 * Save entry tax details.
	 *
	 * @param to the to
	 * @return the jSON object
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	JSONObject saveEntryTaxDetails(EntryTaxDetailsTO to) throws CGBusinessException,CGSystemException;
	
	/**
	 * Save entry tax details.
	 *
	 * @param to the to
	 * @return the jSON object
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	JSONObject saveEntryTaxDetails(CGBaseTO to) throws CGBusinessException,CGSystemException;

	/**
	 * Save entry tax config details.
	 *
	 * @param to the to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Boolean saveEntryTaxConfigDetails(CGBaseTO to) throws CGBusinessException,
			CGSystemException;
	
	/**
	 * Save entry tax dtls.
	 *
	 * @param to the to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Boolean saveEntryTaxDtls(CGBaseTO to) throws CGBusinessException,
			CGSystemException;

	
	
}
