package src.com.dtdc.mdbServices.manifest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class AgentManifestIntlMDBValidator.
 */
public class AgentManifestIntlMDBValidator {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(AgentManifestIntlMDBValidator.class);

	/**
	 * Checks if is valid consignment.
	 *
	 * @param consgNumber the consg number
	 * @return true, if is valid consignment
	 */
	public static boolean isValidConsignment(String consgNumber) {
		boolean isValidConNum = Boolean.TRUE;
		try {
			String consgNumberSeries = consgNumber.substring(0, 1);
			if (!(StringUtils.equals(consgNumberSeries, "N"))) {
				isValidConNum = Boolean.FALSE;
			}
		} catch (Exception ex) {
			LOGGER.error("AgentManifestIntlMDBValidator::isValidConsignment::Exception occured:"
					+ex.getMessage());
		}
		return isValidConNum;

	}
}
