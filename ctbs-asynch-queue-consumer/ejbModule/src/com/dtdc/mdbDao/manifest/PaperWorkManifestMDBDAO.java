package src.com.dtdc.mdbDao.manifest;

import com.dtdc.domain.booking.BookingDO;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.master.geography.CityDO;

// TODO: Auto-generated Javadoc
/**
 * The Interface PaperWorkManifestMDBDAO.
 *
 * @author nisahoo
 */
public interface PaperWorkManifestMDBDAO {
	
	/**
	 * Checks if is manifest in bag.
	 *
	 * @param manifestNo the manifest no
	 * @return true, if is manifest in bag
	 */
	public boolean isManifestInBag(String manifestNo);
	
	/**
	 * Save paper work manifest.
	 *
	 * @param manifestDO the manifest do
	 */
	public void savePaperWorkManifest(ManifestDO manifestDO);
	
	/**
	 * Update booking weight.
	 *
	 * @param bookingDO the booking do
	 */
	public void updateBookingWeight(BookingDO bookingDO);
	
	/**
	 * Gets the manifest details by composite id.
	 *
	 * @param manifestNo the manifest no
	 * @param cnNo the cn no
	 * @param manifestType the manifest type
	 * @return the manifest details by composite id
	 */
	public ManifestDO getManifestDetailsByCompositeID(String manifestNo, String cnNo, String manifestType);
	
	/**
	 * Gets the booking details by parent cn no.
	 *
	 * @param parentCnNumber the parent cn number
	 * @return the booking details by parent cn no
	 */
	public BookingDO getBookingDetailsByParentCnNo(String parentCnNumber);
	
	/**
	 * Gets the booked consignment by cn number.
	 *
	 * @param cnNumber the cn number
	 * @return the booked consignment by cn number
	 */
	public BookingDO getBookedConsignmentByCnNumber(String cnNumber);
	
	/**
	 * Gets the dest city by city name.
	 *
	 * @param destCityName the dest city name
	 * @return the dest city by city name
	 */
	public CityDO getDestCityByCityName(String destCityName);

}
