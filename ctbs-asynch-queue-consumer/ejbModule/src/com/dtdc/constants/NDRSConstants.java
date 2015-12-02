package src.com.dtdc.constants;

// TODO: Auto-generated Javadoc
/**
 * Responsible for holding all constants related to
 * UC 69 & 70 DRS Franchisee and Branch.
 */
public interface NDRSConstants {
	
	//tiles mapping ...
	 /** The nd runsheet. */
	final String ND_RUNSHEET = "nonDelivRunSheet";
	 
 	/** The print ndrs. */
 	final String PRINT_NDRS = "printNonDeliveryRunSheet";
	
	//set attributes
	 /** The display list. */
	final String DISPLAY_LIST="dislplayList";
	 
 	/** The req attb header file. */
 	final String REQ_ATTB_HEADER_FILE="headerFile";
	
	//2way write up logic
	 /** The ndrs service name. */
	final String NDRS_SERVICE_NAME = "NonDeliveryRunSheetService"; 
	 
 	/** The ndrs update. */
 	final String NDRS_UPDATE = "updateNDRS"; 
	
	//method level constants
	 /** The discriminator. */
	final String DISCRIMINATOR = "discriminator";
	 
 	/** The ndrs. */
 	final String NDRS = "NDRS";
	 
 	/** The franchisee header. */
 	final String FRANCHISEE_HEADER = "franchisee";
	 
 	/** The branch header. */
 	final String BRANCH_HEADER = "branch";
	 
 	/** The franchise consg status. */
 	final String FRANCHISE_CONSG_STATUS = "P";
	 
 	/** The branch consg status. */
 	final String BRANCH_CONSG_STATUS = "O";
	
	//Message Constants
	 /** The ndrs update exception. */
	final String NDRS_UPDATE_EXCEPTION = "NDRS Update Exception";
	 
 	/** The ndrs update success. */
 	final String NDRS_UPDATE_SUCCESS = "NDRS Updated Successfully";
	
	 /** The valid drs. */
 	final String VALID_DRS = "Valid DRS";
}
