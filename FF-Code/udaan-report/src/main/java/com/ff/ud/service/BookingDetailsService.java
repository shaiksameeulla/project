package com.ff.ud.service;

import java.util.List;

import com.ff.ud.domain.CityDO;
import com.ff.ud.domain.OfficeDO;
import com.ff.ud.domain.RegionDO;
import com.ff.ud.dto.CityDTO;



/**
 * 
 * @author "Kir@N"
 * This is the service class which deals with all operations in <b>branch to hub</b> process.
 */
public interface BookingDetailsService {
	
	public String getOriginCityFromRegion(String regionAlpahabate);

	public List<String> getCityCodesOpsman();

	/*public List<BoPktDTO> getBranchPacketData(String regionAlpahabate,String originRegion, String desRegion, String product) throws HibernateException, SQLException;

	public void update_DtToDBF(List<String> pktNos, String regionAlpahabate);
	
	public List<BoMblDTO> getBranchMblData(String region,String originCity,String destinatioCity, String product) throws SQLException,HibernateException;
	
	public void update_DtToDBF1(List<String> MblNo, String regionAlpahabate);
*/
	/**
	 * This method will return all the data despatch from origin branch to origin hub.
	 * @param originBranchCode : udaan branch code 
	 * @return The ManifestBODTO Object having packet,ogm and MBL details.
	 * @throws SQLException 
	 * @throws HibernateException 
	 */
	/*public ManifestBODTO getDataForOriginBranchToOriginHub(String region, String originCity,Date dbfDate, Date endDate) throws HibernateException, SQLException;
	*/
	
/**
 * This method returns the list of the region
 * @return list of region objects
 */
	public List<RegionDO> getRegion();
	
	/**
	 * This methods returns the city
	 * @return list of cities
	 */
	public List<CityDO> getCity();
	/**
	 * This methods return the list of the cities whos regCode is given
	 * @param regCode : aphabate of the reason
	 * @return list of city of the given regCode
	 */
	public List<CityDTO> getCityByRegCode(String regCode);

	public List<OfficeDO> getOfficesByCityId(Integer cityId);

	/**
	 * This methods returns the script code of branch
	 * @param originBranchCode : udaan branch code
	 * @return 3 alphabetic  script code
	 */
	public String getScriptCodeByOfficeCode(String originBranchCode);

	


}
