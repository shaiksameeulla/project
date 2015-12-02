package src.com.capgemini.lbs.mdblistener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class FranchiseeDbSyncConsumer.
 */
public class FranchiseeDbSyncConsumer {

	private static final Logger logger = LoggerFactory
	.getLogger(FranchiseeDbSyncConsumer.class);
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		try {
			FranchiseeDbSyncDataProcessor.handlePersistProcess();
		} catch (Exception e) {
			logger.error("FranchiseeDbSyncConsumer::main::Exception occured:"
					+e.getMessage());
		} 
	}
}
