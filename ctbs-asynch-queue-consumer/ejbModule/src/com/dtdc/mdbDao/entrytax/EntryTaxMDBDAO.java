package src.com.dtdc.mdbDao.entrytax;

import java.util.List;

import org.json.JSONObject;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.dtdc.domain.entryTax.EntryTaxCommodityMappingDO;
import com.dtdc.domain.entryTax.EntryTaxConfigDO;
import com.dtdc.domain.entryTax.EntryTaxDetailsDO;
import com.dtdc.to.entryTax.EntryTaxConfigTO;
import com.dtdc.to.entryTax.EntryTaxDetailsTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface EntryTaxMDBDAO.
 */
public interface EntryTaxMDBDAO {
	
	/**
	 * Save update tax config details.
	 *
	 * @param configDO the config do
	 * @param mappingDO the mapping do
	 * @return the integer
	 * @throws CGSystemException the cG system exception
	 */
	Integer saveUpdateTaxConfigDetails(EntryTaxConfigDO configDO,EntryTaxCommodityMappingDO mappingDO) throws CGSystemException;
	
	/**
	 * Save update entry tax details.
	 *
	 * @param to the to
	 * @return the jSON object
	 * @throws CGSystemException the cG system exception
	 */
	JSONObject saveUpdateEntryTaxDetails(EntryTaxDetailsTO to) throws CGSystemException;
	
	/**
	 * checkTaxConfigExists - Takes each consignment detail to check whether
	 * any of the params like dpartyId, franchiseeId, cityId, stateId
	 * has configuration exists.
	 *
	 * @param configDO the config do
	 * @param taxConfigTO the tax config to
	 * @return true, if successful
	 * @throws CGSystemException the cG system exception
	 */
	boolean checkTaxConfigExists(EntryTaxConfigDO configDO,EntryTaxConfigTO taxConfigTO) throws CGSystemException;
	
	/**
	 * Save entry tax details.
	 *
	 * @param detailsList the details list
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean saveEntryTaxDetails(List<EntryTaxDetailsDO> detailsList) throws CGSystemException;
	
	/**
	 * Save entry config details.
	 *
	 * @param configList the config list
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean saveEntryConfigDetails(List<EntryTaxConfigDO> configList) throws CGSystemException;
	
	/**
	 * Find entry dtls by consignmnt nums.
	 *
	 * @param cnNumber the cn number
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	List<EntryTaxDetailsDO> findEntryDtlsByConsignmntNums(List<String> cnNumber) throws CGSystemException;
	
	/**
	 * Delete entry dtls by consignmnt.
	 *
	 * @param cnNumber the cn number
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean deleteEntryDtlsByConsignmnt(List<String> cnNumber) throws CGSystemException;
}
