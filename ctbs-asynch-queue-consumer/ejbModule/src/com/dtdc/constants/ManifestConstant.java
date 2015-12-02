/**
 * 
 */
package src.com.dtdc.constants;

// TODO: Auto-generated Javadoc
/**
 * The Interface ManifestConstant.
 *
 * @author jaydutta
 */
public interface ManifestConstant {
	
	
	 /** The product normal type. */
 	String PRODUCT_NORMAL_TYPE = "NORMAL";

	 /** The Constant MANIFEST_TYPE_AGAINST_OUTGOING. */
 	public static final String MANIFEST_TYPE_AGAINST_OUTGOING = "O";
		
		/** The Constant MANIFEST_TYPE_AGAINST_INCOMING. */
		public static final String MANIFEST_TYPE_AGAINST_INCOMING = "I";

		/** The Constant POD_MANIFEST_TYPE_CODE. */
		public static final String POD_MANIFEST_TYPE_CODE = "POD";

		/** The Constant MANIFEST_TYPE_MASTESR_BAG. */
		public static final String MANIFEST_TYPE_MASTESR_BAG = "M";
		
		/** The Constant MANIFEST_TYPE_BAG. */
		public static final String MANIFEST_TYPE_BAG = "B";

		/** The Constant MANIFEST_TYPE_PACKET. */
		public static final String MANIFEST_TYPE_PACKET = "PMF";
		
		/** The Constant MANIFEST_TYPE_PACKET_SPL_CUST. */
		public static final String MANIFEST_TYPE_PACKET_SPL_CUST = "SPMF";
		
		/** The Constant MANIFEST_TYPE_MNPBAG. */
		public static final String MANIFEST_TYPE_MNPBAG = "MNP";
		
		/** The Constant MANIFEST_TYPE_BAG_NONDOX. */
		public static final String MANIFEST_TYPE_BAG_NONDOX = "BMN";
		
		/** The Constant MANIFEST_TYPE_REROUTE_PACKET. */
		public static final String MANIFEST_TYPE_REROUTE_PACKET = "RMF";
		
		/** The Constant MANIFEST_TYPE_BAG_DOX. */
		public static final String MANIFEST_TYPE_BAG_DOX = "BMD";
		
		/** The Constant MANIFEST_TYPE_MASTER_BAG. */
		public static final String MANIFEST_TYPE_MASTER_BAG = "MBM";
		
		/** The Constant MANIFEST_TYPE_CD. */
		public static final String MANIFEST_TYPE_CD = "CD";
		
		/** The Constant MANIFEST_TYPE_VEHICLE. */
		public static final String MANIFEST_TYPE_VEHICLE = "VMF";
		
		/** The manifest type agent manifest intl. */
		String MANIFEST_TYPE_AGENT_MANIFEST_INTL = "AGI";

		/** The franchisee type code. */
		String FRANCHISEE_TYPE_CODE = "FR";
		
		/** The direct party type code. */
		String DIRECT_PARTY_TYPE_CODE = "DP";
		
		/** The standard type customer. */
		String STANDARD_TYPE_CUSTOMER = "Customer";

		// String BAG_MANIFEST_SERVICE = "bagManifestService";

		/** The Constant GET_OFFICE_BY_OFFICE_ID. */
		public static final String GET_OFFICE_BY_OFFICE_ID = "getOfficeByOfficeId";

		/** The Constant INCOMING_BAG_MANIFEST_SERVICE. */
		public static final String INCOMING_BAG_MANIFEST_SERVICE = "bagManifestService";

		/** The Constant ORIGIN_OFFICE_TO. */
		public static final String ORIGIN_OFFICE_TO = "originOfficeTO";
		
		/** The Constant REG_OFFICE_TO. */
		public static final String REG_OFFICE_TO = "regOfficeTOObj";

		/** The Constant OFFICE_BRANCH_LIST. */
		public static final String OFFICE_BRANCH_LIST = "officeList";
		
		/** The Constant EMPLOYEE_TO. */
		public static final String EMPLOYEE_TO = "employeeTO";
		
		/** The Constant STD_HANDLING_INSTRUCTION_TO_LIST. */
		public static final String STD_HANDLING_INSTRUCTION_TO_LIST = "stdHandlingInstTOList";
		
		/** The Constant MODE_TO_LIST. */
		public static final String MODE_TO_LIST = "modeTOList";
		
		/** The office type list. */
		String OFFICE_TYPE_LIST = "officeTypeList";
		
		/** The dest offices list. */
		String DEST_OFFICES_LIST = "destOfficesList";
		
		/** The rto office type. */
		String RTO_OFFICE_TYPE = "OFFICE_TYPE";

		/** The get offices by office type query. */
		String GET_OFFICES_BY_OFFICE_TYPE_QUERY = "getOfficesByOfficeType";
		
		/** The get offices by office type query param. */
		String GET_OFFICES_BY_OFFICE_TYPE_QUERY_PARAM = "officeType";

		/** The get geography object query. */
		String GET_GEOGRAPHY_OBJECT_QUERY = "getGeographyObj";

		/** The get geography id by pin code query. */
		String GET_GEOGRAPHY_ID_BY_PIN_CODE_QUERY = "getGeographyIdByPinCode";

		/** The get geography id by dest code query. */
		String GET_GEOGRAPHY_ID_BY_DEST_CODE_QUERY = "getGeographyIdByDestCode";

		/** The get branch details by branch code query. */
		String GET_BRANCH_DETAILS_BY_BRANCH_CODE_QUERY = "getBranchDetailsByBranchCode";

		/** The get manifest type query. */
		String GET_MANIFEST_TYPE_QUERY = "getManifestType";
		
		/** The get final weight. */
		String getFinalWeight = "getFinalWeight";

		/** The get mode list query. */
		String GET_MODE_LIST_QUERY = "getModeList";

		/** The get mode objects query. */
		String GET_MODE_OBJECTS_QUERY = "getModeObjects";

		/** The get office id from employee query. */
		String GET_OFFICE_ID_FROM_EMPLOYEE_QUERY = "getOfficeId";
		
		/** The get rep office id from employee query. */
		String GET_REP_OFFICE_ID_FROM_EMPLOYEE_QUERY = "getRepOfficeId";
		
		/** The get rep office details query. */
		String GET_REP_OFFICE_DETAILS_QUERY = "getRepOfficeDetails";

		/** The get origin ro details query. */
		String GET_ORIGIN_RO_DETAILS_QUERY = "getOriginRODetails";
		
		/** The get employee detail by employee id query. */
		String GET_EMPLOYEE_DETAIL_BY_EMPLOYEE_ID_QUERY = "getEmployeeDetails";

		/** The get std handling insts query. */
		String GET_STD_HANDLING_INSTS_QUERY = "getStdHandlingInsts";

		/** The get origin rep office details query. */
		String GET_ORIGIN_REP_OFFICE_DETAILS_QUERY = "getOriginRepOfficeDetails";

		/** The get office details query. */
		String GET_OFFICE_DETAILS_QUERY = "getOfficeDetails";

		// Added by Anwar for POD
		/** The get ro by employee id query. */
		String GET_RO_BY_EMPLOYEE_ID_QUERY = "getRegionalOfficeByEmployeeId";
		
		/** The get ro by employee id param. */
		String GET_RO_BY_EMPLOYEE_ID_PARAM = "employeeId";

		/** The get bo by ro id query. */
		String GET_BO_BY_RO_ID_QUERY = "getBranchOfficeByROId";
		
		/** The get bo by ro id param. */
		String GET_BO_BY_RO_ID_PARAM = "reginolOfficeId";

		/** The get all ro query. */
		String GET_ALL_RO_QUERY = "getAllRegionalOffices";
		
		/** The get all offices query. */
		String GET_ALL_OFFICES_QUERY = "getAllOffices";

		/** The get customer by type query. */
		String GET_CUSTOMER_BY_TYPE_QUERY = "getCustomersByCustomerType";
		
		/** The get active customer by type query. */
		String GET_ACTIVE_CUSTOMER_BY_TYPE_QUERY = "getActiveCustomersByCustomerType";
		
		/** The get customer by type param. */
		String GET_CUSTOMER_BY_TYPE_PARAM = "custType";

		/** The get active customer by type and off query. */
		String GET_ACTIVE_CUSTOMER_BY_TYPE_AND_OFF_QUERY = "getActiveCustByCusTypeAndRo";
		
		/** The get active customer by type and off param. */
		String GET_ACTIVE_CUSTOMER_BY_TYPE_AND_OFF_PARAM = "custType,repOfficeId";

		/** The get types by name query. */
		String GET_TYPES_BY_NAME_QUERY = "getTypesByTypeName";
		
		/** The get types by name param. */
		String GET_TYPES_BY_NAME_PARAM = "typeName";
		
		/** The check duplication. */
		String CHECK_DUPLICATION = "getConsignmentVerStatus";
		
		/** The info msg. */
		String INFO_MSG = "INFO";

		/** The get pod status by cn query. */
		String GET_POD_STATUS_BY_CN_QUERY = "getPodStatusByConsgNo";
		
		/** The get pod status by cn param. */
		String GET_POD_STATUS_BY_CN_PARAM = "consgNo";
		
		/** The get pod status by cn and product param. */
		String GET_POD_STATUS_BY_CN_AND_PRODUCT_PARAM = "consgNo,productId";

		/** The get ro by mfn query. */
		String GET_RO_BY_MFN_QUERY = "getRegionalOfficeForMfNo";
		
		/** The get ro by mfn param. */
		String GET_RO_BY_MFN_PARAM = "manifestNumber";

		/** The get bo by mfn query. */
		String GET_BO_BY_MFN_QUERY = "getBranchOfficeForMfNo";
		
		/** The et bo by mfn param. */
		String ET_BO_BY_MFN_PARAM = "manifestNumber";

		/** The get incoming pods by mfn query. */
		String GET_INCOMING_PODS_BY_MFN_QUERY = "getIncomingPodsByMfNumber";
		
		/** The get incoming pods by mfn param. */
		String GET_INCOMING_PODS_BY_MFN_PARAM = "manifestNumber,manifestType,mnfstCode";

		/** The get manifest by mf number query. */
		String GET_MANIFEST_BY_MF_NUMBER_QUERY = "getManifestDetailsQuery";
		
		/** The get manifest by mf number param. */
		String GET_MANIFEST_BY_MF_NUMBER_PARAM = "manifestNumber";

		/** The get out going pods by mf number query. */
		String GET_OUT_GOING_PODS_BY_MF_NUMBER_QUERY = "findOutgoingPodMfByMfNumber";
		
		/** The get incoming pods by mf number query. */
		String GET_INCOMING_PODS_BY_MF_NUMBER_QUERY = "findIncomingPodMfByMfNumber";
		
		/** The get delivery by cn query. */
		String GET_DELIVERY_BY_CN_QUERY = "getDeliveryByConsgNo";
		
		/** The get pod mf type query. */
		String GET_POD_MF_TYPE_QUERY = "getPodManifestType";

		/** The pod failure code no manifest. */
		String POD_FAILURE_CODE_NO_MANIFEST = "NO_MANIFEST";
		
		/** The pod failure code pod update. */
		String POD_FAILURE_CODE_POD_UPDATE = "POD_UPDATE";
		
		/** The pod failure code no pod update. */
		String POD_FAILURE_CODE_NO_POD_UPDATE = "NO_POD_UPDATE";

		/** The validate incoming pod in incoming bag query. */
		String VALIDATE_INCOMING_POD_IN_INCOMING_BAG_QUERY = "getMFDetailsByTypeAndCn";
		
		/** The no of pods in gridrow for print. */
		int NO_OF_PODS_IN_GRIDROW_FOR_PRINT = 5;

		/* Unstamped POD Manifest : START */
		/** The get all manifested pods query. */
		String GET_ALL_MANIFESTED_PODS_QUERY = "getAllManifestedPODs";
		
		/** The get all manifested pods query param pod no. */
		String GET_ALL_MANIFESTED_PODS_QUERY_PARAM_POD_NO = "podNo";
		
		/** The get all manifested pods query param manifest code. */
		String GET_ALL_MANIFESTED_PODS_QUERY_PARAM_MANIFEST_CODE = "mnfstCode";

		/** The get delivered pod query. */
		String GET_DELIVERED_POD_QUERY = "getDeliveredPodsByPODNumber";
		
		/** The get delivered pod query param. */
		String GET_DELIVERED_POD_QUERY_PARAM = "podNumber";

		/** The pod manifest class type. */
		String POD_MANIFEST_CLASS_TYPE = "PODService";
		
		/** The pod manifest class type two way write. */
		String POD_MANIFEST_CLASS_TYPE_TWO_WAY_WRITE = "PODMDBService";
		
		/** The unstamped pod manifest save method. */
		String UNSTAMPED_POD_MANIFEST_SAVE_METHOD = "saveUnstampedPODManifest";

		/** The get unstamped pod manifest query. */
		String GET_UNSTAMPED_POD_MANIFEST_QUERY = "getUnstampedPODManifestDetails";
		
		/** The get unstamped pod manifest query param transaction no. */
		String GET_UNSTAMPED_POD_MANIFEST_QUERY_PARAM_TRANSACTION_NO = "transactionNumber";
		
		/** The get unstamped pod manifest query param manifest code. */
		String GET_UNSTAMPED_POD_MANIFEST_QUERY_PARAM_MANIFEST_CODE = "mnfstCode";
		
		/** The get unstamped pod manifest query param manifest type defn. */
		String GET_UNSTAMPED_POD_MANIFEST_QUERY_PARAM_MANIFEST_TYPE_DEFN = "mnfstTypeDefn";
		
		/** The get unstamped pod manifest query param manifest type. */
		String GET_UNSTAMPED_POD_MANIFEST_QUERY_PARAM_MANIFEST_TYPE = "manifestType";
		
		/** The unsatmped pod manifest type defn. */
		String UNSATMPED_POD_MANIFEST_TYPE_DEFN = "UNSTAMPED_POD";
		/* Unstamped POD Manifest : END */

		/** The booking status. */
		String BOOKING_STATUS = "BOOKED";
		
		/** The error flag. */
		String ERROR_FLAG = "ERROR";
		
		/** The manifest status. */
		String MANIFEST_STATUS = "M";
		
		/** The success msg. */
		String SUCCESS_MSG = "SUCCESS";
		
		/** The failure msg. */
		String FAILURE_MSG = "FAILURE";

		/** The print packet manifest. */
		String printPacketManifest = "printPacketManifest";
		
		/** The print bag dox manifest. */
		String printBagDoxManifest = "printBagDoxManifest";
		
		/** The print master bag dox manifest. */
		String printMasterBagDoxManifest = "printOutgoingMasterBagManifest";
		
		/** The print bag non dox manifest. */
		String printBagNonDoxManifest = "printOutgoingBagManifestNonDox";
		
		/** The print mnp manifest. */
		String printMNPManifest = "printOutgoingMNPManifest";
		
		/** The print mis route manifest. */
		String printMisRouteManifest = "printOutgoingMisRouteManifest";
		
		/** The print ag mnfst intl. */
		String PRINT_AG_MNFST_INTL = "printAgentManifestIntl";

		/** The incoming packet manifest class type two way write. */
		String INCOMING_PACKET_MANIFEST_CLASS_TYPE_TWO_WAY_WRITE = "IncomingPacketManifestMDBService";
		
		/** The incoming packet manifest save method. */
		String INCOMING_PACKET_MANIFEST_SAVE_METHOD = "saveIncomingPktManifest";

		/** The redirect packet manifest main page. */
		String REDIRECT_PACKET_MANIFEST_MAIN_PAGE = "/ctbs-web/packetManifestDox.do?submitName=show&manifestCode=";
		
		/** The redirect masterbag manifest main page. */
		String REDIRECT_MASTERBAG_MANIFEST_MAIN_PAGE = "/ctbs-web/bagManifestDox.do?submitName=show&manifestCode=";

		/** The manifest number reg code. */
		String MANIFEST_NUMBER_REG_CODE = "Manifest number should start with Region Code.";
		
		/** The manifest number length. */
		String MANIFEST_NUMBER_LENGTH = "Manifest number should be alphanumeric and length is 8.";

		/** The manifest origin branch ro code. */
		String MANIFEST_ORIGIN_BRANCH_RO_CODE = "RO";
		
		/** The packet manifest to. */
		String PACKET_MANIFEST_TO = "packetManifestTO";
		
		/** The manifest to. */
		String MANIFEST_TO = "manifestTO";
		
		/** The action type. */
		String ACTION_TYPE = "actionType";
		
		/** The action method find. */
		String ACTION_METHOD_FIND = "find";
		
		/** The action method add. */
		String ACTION_METHOD_ADD = "add";
		
		/** The dox. */
		String DOX = "DOX";
		
		/** The non dox. */
		String NON_DOX = "NONDOX";
		
		/** The get manifest by no. */
		String GET_MANIFEST_BY_NO = "getManifestByNo";
		
		/** The manifest number. */
		String MANIFEST_NUMBER = "manifestNumber";
		
		/** The comma. */
		String COMMA = ",";
		
		/** The get geography. */
		String GET_GEOGRAPHY = "getGeographyByID";
		
		/** The geography id. */
		String GEOGRAPHY_ID = "geographyId";
		
		/** The products. */
		String PRODUCTS = "products";

		/** The product ptp type. */
		String PRODUCT_PTP_TYPE = "PTP";
		
		/** The product pep type. */
		String PRODUCT_PEP_TYPE = "PEP";
		
		/** The product lite type. */
		String PRODUCT_LITE_TYPE = "LITE";
		
		/** The product vas type. */
		String PRODUCT_VAS_TYPE = "VAS";
		
		/** The product intl type. */
		String PRODUCT_INTL_TYPE = "INTL";
		
		/** The product priority type. */
		String PRODUCT_PRIORITY_TYPE = "PRIORITY";
		
		/** The consg number n series. */
		String CONSG_NUMBER_N_SERIES = "N";
		
		/** The consg number e series. */
		String CONSG_NUMBER_E_SERIES = "E";
		
		/** The consg number v series. */
		String CONSG_NUMBER_V_SERIES = "V";
		
		/** The consg number x series. */
		String CONSG_NUMBER_X_SERIES = "X";

		/** The mode air. */
		String MODE_AIR = "AR";
		
		/** The mode surface. */
		String MODE_SURFACE = "SF";
		
		/** The mode air cargo. */
		String MODE_AIR_CARGO = "AC";
		
		/** The mode ground express cargo. */
		String MODE_GROUND_EXPRESS_CARGO = "GC";
		
		/** The valid flag. */
		String VALID_FLAG = "VALID";
		
		/** The maniefst std type name. */
		String MANIEFST_STD_TYPE_NAME = "PRODUCT_MANIFEST";
		
		/** The get consignment details by consignment number query. */
		String GET_CONSIGNMENT_DETAILS_BY_CONSIGNMENT_NUMBER_QUERY = "getConsignmentDetailsByConsignmentNo";
		
		/** The office id. */
		String OFFICE_ID = "officeId";
		
		/** The consg number. */
		String CONSG_NUMBER = "consgNumber";
		
		/** The manifest type. */
		String MANIFEST_TYPE = "mnfstTypeId";

		/** The manifest type robo check list. */
		String MANIFEST_TYPE_ROBO_CHECK_LIST = "RBO";
		
		/** The std type robo manifest type. */
		String STD_TYPE_ROBO_MANIFEST_TYPE = "ROBO";
		
		/** The find by manifest number query. */
		String FIND_BY_MANIFEST_NUMBER_QUERY = "findByManifestNumber";
		
		/** The get maniest details by maniest number query. */
		String GET_MANIEST_DETAILS_BY_MANIEST_NUMBER_QUERY = "getManifestDetailsByManifestNo";

		/** The INVALI d_ man if es t_ number. */
		String INVALID_MANIfEST_NUMBER = "Manifest Number does not Exits.";

		/** The get manifest by code and number. */
		String GET_MANIFEST_BY_CODE_AND_NUMBER = "getMFByNumberAndType";
		
		/** The get manifest by code and number param. */
		String GET_MANIFEST_BY_CODE_AND_NUMBER_PARAM = "manifestNumber,mnfstCode";
		
		/** The robo check list to. */
		String ROBO_CHECK_LIST_TO = "roboCheckListTO";

		/** The outgoing master bag manifest to. */
		String OUTGOING_MASTER_BAG_MANIFEST_TO = "outgoingMasterBagManifestTOObj";

		/** The get document type query. */
		String GET_DOCUMENT_TYPE_QUERY = "getDocumentType";

		/** The document to list. */
		String DOCUMENT_TO_LIST = "documentTOList";

		/** The get duplicate consignment query. */
		String GET_DUPLICATE_CONSIGNMENT_QUERY = "getDuplicateConsignment";

		/** The consignment type dox. */
		String CONSIGNMENT_TYPE_DOX = "DOX";
		
		/** The consignment type nondox. */
		String CONSIGNMENT_TYPE_NONDOX = "NONDOX";

		/** The get document id bydocument type query. */
		String GET_DOCUMENT_ID_BYDOCUMENT_TYPE_QUERY = "getDocumentIDByDocumentType";

		/** The get robodetails by consignmentno and documentid query. */
		String GET_ROBODETAILS_BY_CONSIGNMENTNO_AND_DOCUMENTID_QUERY = "getROBODetailsByConNumAndDocId";

		/** The document. */
		String DOCUMENT = "document";

		/** The invalid consignment no. */
		String INVALID_CONSIGNMENT_NO = "Consignment No does not Valid.";

		// PaperWork Manifest For Non Dox
		/** The paper work manifest service. */
		String PAPER_WORK_MANIFEST_SERVICE = "paperWorkManifestService";
		
		/** The separator. */
		String SEPARATOR = "**";
		
		/** The get offices by office name query. */
		String GET_OFFICES_BY_OFFICE_NAME_QUERY = "getOfficesByOfficeName";
		
		/** The get offices by office name query param. */
		String GET_OFFICES_BY_OFFICE_NAME_QUERY_PARAM = "officeName";

		/** The validate manifest number in bag query. */
		String VALIDATE_MANIFEST_NUMBER_IN_BAG_QUERY = "validateManifestNoInBag";
		
		/** The validate manifest number in bag query param. */
		String VALIDATE_MANIFEST_NUMBER_IN_BAG_QUERY_PARAM = "manifestNumber";

		/** The paperwork manifest save update method. */
		String PAPERWORK_MANIFEST_SAVE_UPDATE_METHOD = "saveOrUpdatePaperWorkManifest";
		
		/** The paperwork manifest class type. */
		String PAPERWORK_MANIFEST_CLASS_TYPE = "PaperWorkManifestMDBService";

		/** The redirect paperwork manifest main page. */
		String REDIRECT_PAPERWORK_MANIFEST_MAIN_PAGE = "/ctbs-web/paperWorkManifestNonDox.do?submitName=showPaperWorkManifest";

		// Added By Saumya for ManifestBusiness Rule Implementation
		/** The get embedded manifests query. */
		String GET_EMBEDDED_MANIFESTS_QUERY = "getEmbeddedManifests";
		
		/** The get total booking weight for packet manifest query. */
		String GET_TOTAL_BOOKING_WEIGHT_FOR_PACKET_MANIFEST_QUERY = "getTotalBookingWeightForPacketManifest";
		
		/** The get embedded manifests param. */
		String GET_EMBEDDED_MANIFESTS_PARAM = "manifestNumber,mnfstCode";
		
		/** The get total booking weight for packet manifest param. */
		String GET_TOTAL_BOOKING_WEIGHT_FOR_PACKET_MANIFEST_PARAM = "manifestNumber";

		/** The redirect robo check list manifest main page. */
		String REDIRECT_ROBO_CHECK_LIST_MANIFEST_MAIN_PAGE = "/ctbs-web/roboCheckList.do?submitName=show";

		/** The ROBO CHECK_LIST_ clas s_ type. */
		String ROBO_CHECK_LIST_CLASS_TYPE = "RoboCheckListService";

		/** The save update packet manifest. */
		String SAVE_UPDATE_PACKET_MANIFEST = "saveOrUpdatePacketManifest";

		/** The save updaet bag manifest. */
		String SAVE_UPDAET_BAG_MANIFEST = "saveOrUpdateBagManifest";

		/** The save update robo check list. */
		String SAVE_UPDATE_ROBO_CHECK_LIST = "saveOrUpdateRoboCheckList";

		/** The not written to central. */
		String NOT_WRITTEN_TO_CENTRAL = "N";

		/** The written to central. */
		String WRITTEN_TO_CENTRAL = "Y";

		/** The outgoing manifest service. */
		String OUTGOING_MANIFEST_SERVICE = "outgoingManifestService";

		/** The outgoing manifest class type. */
		String OUTGOING_MANIFEST_CLASS_TYPE = "OutgoingManifestService";

		/** The deps category. */
		String DEPS_CATEGORY = "DEPS_CATEGORY";

		/** The hash. */
		String HASH = "#";

		/** The manifest type id. */
		String MANIFEST_TYPE_ID = "mnfstTypeId";

		/** The deps type code. */
		String DEPS_TYPE_CODE = "typeCode";

		/** The get by name and code. */
		String GET_BY_NAME_AND_CODE = "getByTypeNameAndCode";

		/** The get held up by manifest. */
		String GET_HELD_UP_BY_MANIFEST = "getHeldUpDetailsByManifestNo";

		/** The incoming master bag manifest service. */
		String INCOMING_MASTER_BAG_MANIFEST_SERVICE = "incomingMasterBagManifestService";

		/** The incoming master bag manifest service class type. */
		String INCOMING_MASTER_BAG_MANIFEST_SERVICE_CLASS_TYPE = "IncomingMasterBagManifestService";

		/** The incoming master bag save. */
		String INCOMING_MASTER_BAG_SAVE = "save";

		/** The manifest header list to. */
		String MANIFEST_HEADER_LIST_TO = "manifestHeaderTO";

		/** The mode id. */
		String MODE_ID = "modeId";

		/** The get mode code by mode id. */
		String GET_MODE_CODE_BY_MODE_ID = "getModeCodeByModeId";

		/** The retrive details bag manifestno from outgoing manifest. */
		String RETRIVE_DETAILS_BAG_MANIFESTNO_FROM_OUTGOING_MANIFEST = "retriveDetailsBagManifestNoFromOutGoingManifest";

		/** The manifest code. */
		String MANIFEST_CODE = "mnfstCode";

		/** The print robo checklist page. */
		String PRINT_ROBO_CHECKLIST_PAGE = "printRoboCheckListPage";

		/** The origin ro code. */
		String ORIGIN_RO_CODE = "originROCode";

		/** The origin ro name. */
		String ORIGIN_RO_NAME = "originROName";
		
		/** The consignment type. */
		String CONSIGNMENT_TYPE = "consignmentType";

		/** The branch code name. */
		String BRANCH_CODE_NAME = "branchCodeName";

		/** The employee code. */
		String EMPLOYEE_CODE = "employeeCode";
		
		/** The employee name. */
		String EMPLOYEE_NAME = "employeeName";

		/** The manifest weight. */
		String MANIFEST_WEIGHT = "manifestWeight";

		/** The consignment number. */
		String CONSIGNMENT_NUMBER = "consignmentNumber";

		/** The consignment weight. */
		String CONSIGNMENT_WEIGHT = "consgWeight";

		/** The no of pieces. */
		String NO_OF_PIECES = "noOfPieces";

		/** The depscategory. */
		String DEPSCATEGORY = "depsCategory";

		/** The robo check list. */
		String ROBO_CHECK_LIST = "roboCheckListTOList";

		/** The manifest date. */
		String MANIFEST_DATE = "manifestDate";

		/** The manifest time. */
		String MANIFEST_TIME = "manifestTime";

		/** The branch code. */
		String BRANCH_CODE = "officeBranchCode";

		/** The branch name. */
		String BRANCH_NAME = "branchCodeName";

		/** The document id. */
		String DOCUMENT_ID = "documentId";

		/** The get document id by document type. */
		String GET_DOCUMENT_ID_BY_DOCUMENT_TYPE = "getDocumentIDByDocumentType";

		/** The get manifest details by type and num. */
		String GET_MANIFEST_DETAILS_BY_TYPE_AND_NUM = "getManifestDetailsByTypeAndNum";

		/* Is PaperWork Consignment */
		/** The get paperwork consignment query. */
		String GET_PAPERWORK_CONSIGNMENT_QUERY = "getPaperWorkConsignment";
		
		/** The consignment number query param. */
		String CONSIGNMENT_NUMBER_QUERY_PARAM = "cnNo";
		
		/** The paperwork manifest type query param. */
		String PAPERWORK_MANIFEST_TYPE_QUERY_PARAM = "paperWorkCNType";
		
		/** The manifest number query param. */
		String MANIFEST_NUMBER_QUERY_PARAM = "manifestNumber";

		/** The paperwork booking manifest type. */
		String PAPERWORK_BOOKING_MANIFEST_TYPE = "NONDOX_PAPERWORK";

		/** The get regional office by office code query. */
		String GET_REGIONAL_OFFICE_BY_OFFICE_CODE_QUERY = "getRegionalOfficeByOfficeCode";
		// String GET_REGIONAL_OFFICE_BY_OFFICE_NAME_QUERY =
		// "getRegionalOfficeByOfficeName";

		/** The get geography by pincode query. */
		String GET_GEOGRAPHY_BY_PINCODE_QUERY = "getGeographyByPinCode";
		
		/** The get geography by pincode query param. */
		String GET_GEOGRAPHY_BY_PINCODE_QUERY_PARAM = "pinCode";
		
		/** The geography detail separator. */
		String GEOGRAPHY_DETAIL_SEPARATOR = "**";
		
		/** The booking update process paperwork. */
		String BOOKING_UPDATE_PROCESS_PAPERWORK = "PAPERWORK_MANIFEST";
		// String PAPERWORK_TYPE_ID_QUERY_PARAM = "paperWorkTypeId";

		/** The get paper work manifest details query. */
		String GET_PAPER_WORK_MANIFEST_DETAILS_QUERY = "getpaperWorkManifestDetailsQuery";

		/** The get origin ro details query param. */
		String GET_ORIGIN_RO_DETAILS_QUERY_PARAM = "officeId";

		/** The validate cn in manifest query. */
		String VALIDATE_CN_IN_MANIFEST_QUERY = "validateConsignmentInManifestQuery";

		// String SEARCH_PAPER_WORK_MANIFESTS_BY_MODE_AND_DEST_BRANCH_QUERY =
		// "searchPaperWrkManifestByModeAndDestBranch";
		// String SEARCH_PAPER_WORK_MANIFESTS_BY_MODE_QUERY =
		// "searchPaperWrkManifestByMode";
		// String SEARCH_PAPER_WORK_MANIFESTS_BY_DEST_BRANCH_QUERY =
		// "searchPaperWrkManifestByDestBranch";
		// String MODE_ID_QUERY_PARAM = "modeId";
		// String DEST_OFFICE_ID_QUERY_PARAM = "destOfficeId";
		// String ACT_BRANCH_ID_QUERY_PARAM = "repOfficeId";
		// String MANIFEST_DATE_QUERY_PARAM = "manifestDate";

		/** The get city by city name query. */
		String GET_CITY_BY_CITY_NAME_QUERY = "getCityDetailsByCityName";
		
		/** The get city by city name query param. */
		String GET_CITY_BY_CITY_NAME_QUERY_PARAM = "cityName";
		
		/** The get dest pin code query. */
		String GET_DEST_PIN_CODE_QUERY = "getPinCodeDetailsByPinCode";
		
		/** The get dest pin code query param. */
		String GET_DEST_PIN_CODE_QUERY_PARAM = "pinCode";
		
		/** The booking automatic weighing type. */
		String BOOKING_AUTOMATIC_WEIGHING_TYPE = "automatic";
		
		/** The booking manual weighing type. */
		String BOOKING_MANUAL_WEIGHING_TYPE = "manual";
		
		/** The manifest automatic weighing type. */
		String MANIFEST_AUTOMATIC_WEIGHING_TYPE = "A";
		
		/** The manifest manual weighing type. */
		String MANIFEST_MANUAL_WEIGHING_TYPE = "M";

		/** The rto mnfst cat. */
		String RTO_MNFST_CAT = "rtoMnfstCat";
		
		/** The rto request by. */
		String RTO_REQUEST_BY = "RTO_REQUEST_BY";
		
		/** The rto request mode. */
		String RTO_REQUEST_MODE = "RTO_REQUEST_MODE";
		
		/** The rto request status. */
		String RTO_REQUEST_STATUS = "RTO_REQUEST_STATUS";
		
		/** The rto request reject reason type. */
		String RTO_REQUEST_REJECT_REASON_TYPE = "U";
		
		/** The rto outgoing status prepared. */
		String RTO_OUTGOING_STATUS_PREPARED = "P";
		
		/** The office type. */
		String OFFICE_TYPE = "OFFICE_TYPE";

		/** The source of information. */
		String SOURCE_OF_INFORMATION = "SOURCE_OF_INFORMATION";
		
		/** The rto manifest cn verification service. */
		String RTO_MANIFEST_CN_VERIFICATION_SERVICE = "rtoManifestCNVerificationService";
		
		/** The std soi list. */
		String STD_SOI_LIST = "stdTOList";
		
		/** The ver list. */
		String VER_LIST = "stdTOVList";
		
		/** The get consignment rto status. */
		String GET_CONSIGNMENT_RTO_STATUS = "getConsignmentRTOStatus";
		
		/** The verification details. */
		String VERIFICATION_DETAILS = "VERIFICATION_DETAILS";
		
		/** The date formater. */
		String DATE_FORMATER = "dd-MM-yyyy";
		
		/** The rto cn error msg. */
		String RTO_CN_ERROR_MSG = "Consignment Not RTO Manifested";
		
		/** The rto cn info msg. */
		String RTO_CN_INFO_MSG = "Consignment already verified";

		/** The robo manifest type. */
		String ROBO_MANIFEST_TYPE = "RBO";

		/** The message resources. */
		String MESSAGE_RESOURCES = "MessageResources";

		/** The invalid consignment number. */
		String INVALID_CONSIGNMENT_NUMBER = "error.consignmentNumberdoesnotExits";
		
		/** The invalid consignment. */
		String INVALID_CONSIGNMENT = "error.invalidConsigment";
		
		/** The invalid consignment intl. */
		String INVALID_CONSIGNMENT_INTL = "error.invalidConsigmentIntl";
		
		/** The product validation. */
		String PRODUCT_VALIDATION = "error.productsPEPandPTPshouldbemanifestseparately";

		/** The mode validation. */
		String MODE_VALIDATION = "error.consignmentbookedInADifferentMode";
		
		/** The bag mode validation. */
		String BAG_MODE_VALIDATION = "error.pktInADifferentMode";
		
		/** The master bag mode validation. */
		String MASTER_BAG_MODE_VALIDATION = "error.bagInADifferentMode";

		/** The validate consignment series. */
		String VALIDATE_CONSIGNMENT_SERIES = "error.XandYSeriesConsignmentsarenotallowed";

		/** The duplicate consignment. */
		String DUPLICATE_CONSIGNMENT = "error.consignmentAlreadyManifestedYouCannotManifestAgain";

		/** The validate consignment packet manifest. */
		String VALIDATE_CONSIGNMENT_PACKET_MANIFEST = "error.packetManifestWillAllowOnlyDOXConsignments";

		/** The validate consignment bag nondox manifest. */
		String VALIDATE_CONSIGNMENT_BAG_NONDOX_MANIFEST = "error.nonDoxBagManifestWillAllowOnlyNONDOXConsignments";

		/** The validate consignment delivery status. */
		String VALIDATE_CONSIGNMENT_DELIVERY_STATUS = "error.thisConsignmentIsInOutofDeliveryStatusHenceCannotBeManifested";
		
		/** The validate consignment non delivery status. */
		String VALIDATE_CONSIGNMENT_NON_DELIVERY_STATUS = "error.thisConsignmentIsInNonDeliveryStatusUseRTOManifest";

		/** The mnp manifest validaion. */
		String MNP_MANIFEST_VALIDAION = "error.mNPManifestWillAllowOnlyMNPBookingConsignments";

		/** The manifest number numeric validation. */
		String MANIFEST_NUMBER_NUMERIC_VALIDATION = "error.manifestNumberStartWithCharcterAndRemaining7AreDigits";

		/** The manifest number not issued to branch. */
		String MANIFEST_NUMBER_NOT_ISSUED_TO_BRANCH = "error.manifestNumbernotissuedtoBranch";
		
		/** The duplicate manifest. */
		String DUPLICATE_MANIFEST = "error.manifestNumberAlreadyExits";

		/** The invalid mode. */
		String INVALID_MODE = "error.consignmentDoesNotMappedWithMode";

		/* Incoming Bag Manifest : START */

		/** The get manifest type by conf. */
		String GET_MANIFEST_TYPE_BY_CONF = "getManifestTypeByConf";
		
		/** The office code param. */
		String OFFICE_CODE_PARAM = "officeCode";
		
		/** The mode param. */
		String MODE_PARAM = "mode";
		
		/** The bag weight tolerance. */
		String BAG_WEIGHT_TOLERANCE = "BAG_WEIGHT_TOLERANCE";
		
		/** The manifest types in bag manifest. */
		String MANIFEST_TYPES_IN_BAG_MANIFEST = "MANIFEST_TYPES_IN_BAG_MANIFEST";

		/* Incoming Bag Manifest : END */

		/** The duplicate consignment number. */
		String DUPLICATE_CONSIGNMENT_NUMBER = "error.consignmentNumberAlreadyExits";

		/** The rto weight. */
		String RTO_WEIGHT = "rtoweight";

		/** The get consgn type dox query param. */
		String GET_CONSGN_TYPE_DOX_QUERY_PARAM = "getOutgoingReturnToOrgDtlsByNoMnfCatDoXTypeAndRToweight";

		/** The get return to org by mnfst cat query. */
		String GET_RETURN_TO_ORG_BY_MNFST_CAT_QUERY = "getReturnToOrgDetailsByNoAndManifestCategory";

		/** The get by type and code. */
		String GET_BY_TYPE_AND_CODE = "getByTypeNameAndCode";

		/** The get return to org by mnfst cat consgn no query. */
		String GET_RETURN_TO_ORG_BY_MNFST_CAT_CONSGN_NO_QUERY = "getReturnToOrgDetailsByRTNManfstNoAndConsgNoAndManifestCategory";

		/** The validate cn already rtoed. */
		String VALIDATE_CN_ALREADY_RTOED = "validateCNalreadyRTOed";

		/** The get delivery by consgn no query. */
		String GET_DELIVERY_BY_CONSGN_NO_QUERY = "getDeliveryByConsgNoForRTO";

		/** The get consgn details query. */
		String GET_CONSGN_DETAILS_QUERY = "getConsignmentDetailsForRtnToOrg";

		/** The get offices query. */
		String GET_OFFICES_QUERY = "getOffices";

		/** The off type. */
		String OFF_TYPE = "offType";

		/** The rto details query. */
		String RTO_DETAILS_QUERY = "getRtnToOrgList";

		/** The mnfst no. */
		String MNFST_NO = "mnfstNo";

		/** The consgn details query. */
		String CONSGN_DETAILS_QUERY = "getDetailsForConsignmentNO";

		/** The fdm number. */
		String FDM_NUMBER = "fdmNumber";
		
		/** The fr delivery tos. */
		String FR_DELIVERY_TOS = "frDelivery";
		
		/** The og packet manifest. */
		String OG_PACKET_MANIFEST = "ogPacketManifest";
		
		/** The og agent manifest intl. */
		String OG_AGENT_MANIFEST_INTL = "ogagentManifestIntl";
		
		/** The print dc consg report. */
		String PRINT_DC_CONSG_REPORT = "deliveryChargesTOObj";
		
		/** The agent manifest intl service. */
		String AGENT_MANIFEST_INTL_SERVICE = "agentManifestIntlService";
		
		/** The agent manifest intl class type. */
		String AGENT_MANIFEST_INTL_CLASS_TYPE = "AgentManifestIntlService";
		
		/** The save or update agent manifest intl. */
		String SAVE_OR_UPDATE_AGENT_MANIFEST_INTL = "saveAgentManifest";
		
		/** The agent manifest to. */
		String AGENT_MANIFEST_TO = "agentManifestTO";
		
		/** The og bag manifest. */
		String OG_BAG_MANIFEST = "ogBagManifest";
		
		/** The invalid manifest. */
		String INVALID_MANIFEST = "error.invalidManifestNumber";
		
		/** The manifest type fdm. */
		String MANIFEST_TYPE_FDM = "FDM";
		
		/** The manifest type rto. */
		String MANIFEST_TYPE_RTO = "RTO";
		
		/** The show agent manifest intl. */
		String SHOW_AGENT_MANIFEST_INTL = "showAgentManifestIntl";
		
		/** The agents. */
		String AGENTS = "agents";
		
		/** The std manifest type. */
		String STD_MANIFEST_TYPE = "AGENT_MANIFEST_INTL";
		
		/** The std manifest types. */
		String STD_MANIFEST_TYPES = "stdManifestTypes";
		
		/** The redirect rto manifest cn verification page. */
		String REDIRECT_RTO_MANIFEST_CN_VERIFICATION_PAGE = "/ctbs-web/rtoCnVerification.do?submitName=show";
		
		/** The rto manifest cn verification page. */
		String RTO_MANIFEST_CN_VERIFICATION_PAGE = "rtoCnVerificationPage";

		/** The redirect rto outgoing manifest main page. */
		String REDIRECT_RTO_OUTGOING_MANIFEST_MAIN_PAGE = "/ctbs-web/rtoManifestOutgoing.do?submitName=show";

		/** The Constant NON_DOX_D. */
		public static final String NON_DOX_D = "D100";

		/** The Constant BAG_NON_DOX. */
		public static final String BAG_NON_DOX = "BMN";

		/* Self Sector Manifest : START */
		/** The std type pickup type. */
		String STD_TYPE_PICKUP_TYPE = "PICKUP_TYPE";
		
		/** The mixed doc type. */
		String MIXED_DOC_TYPE = "BOTH";
		
		/** The self sector manifest code. */
		String SELF_SECTOR_MANIFEST_CODE = "SSM";
		
		/** The self sector off type. */
		String SELF_SECTOR_OFF_TYPE = "SELF_SECTOR_OFF_TYPE";
		
		/** The office code name separator. */
		String OFFICE_CODE_NAME_SEPARATOR = "||";

		/** The get self sector manifest detail query. */
		String GET_SELF_SECTOR_MANIFEST_DETAIL_QUERY = "getSelfSectorManifestDetailsQuery";
		
		/** The get self sector manifest detail query param. */
		String GET_SELF_SECTOR_MANIFEST_DETAIL_QUERY_PARAM = "manifestNumber";

		/** The redirect self sector manifest main page. */
		String REDIRECT_SELF_SECTOR_MANIFEST_MAIN_PAGE = "/ctbs-web/selfSectorManifest.do?parameter=show";
		
		/** The co loaders. */
		String CO_LOADERS = "coLoaders";
		
		/** The self sector manifest class type. */
		String SELF_SECTOR_MANIFEST_CLASS_TYPE = "SelfSectorManifestService";
		
		/** The self sector manifest class type two way write. */
		String SELF_SECTOR_MANIFEST_CLASS_TYPE_TWO_WAY_WRITE = "SelfSectorManifestMDBService";
		
		/** The self sector manifest save update method. */
		String SELF_SECTOR_MANIFEST_SAVE_UPDATE_METHOD = "saveOrUpdateSelfSectorManifest";
		/* Self Sector Manifest : END */

		/** The Constant ROBO_CHECK_LIST_SRVC. */
		public static final String ROBO_CHECK_LIST_SRVC = "RoboCheckListService";
		
		/** The prepared by. */
		String PREPARED_BY = "preparedBy";

		/** The redirect unstamped pod manifest main page. */
		String REDIRECT_UNSTAMPED_POD_MANIFEST_MAIN_PAGE = "/ctbs-web/unstampedPODManifest.do?submitName=show";

		/** The Constant VEHICLE_TO_LIST. */
		public static final String VEHICLE_TO_LIST = "vehicleList";

		/** The Constant VEHICLE_ID. */
		public static final String VEHICLE_ID = "vehicleId";

		/** The Constant VEHICLE_MNFST_LOAD. */
		public static final String VEHICLE_MNFST_LOAD = "VEHICLE_MANIFEST_LOAD_TYPE";

		/** The Constant CN. */
		public static final String CN = "CN";

		/** The Constant BAG. */
		public static final String BAG = "Bag";

		/** The Constant PAPER_WORK_MANIFEST. */
		public static final String PAPER_WORK_MANIFEST = "PWM";

		/** The Constant VEHICLE_MANIFEST_QUERY. */
		public static final String VEHICLE_MANIFEST_QUERY = "getManifestForVehicle";

		/** The Constant MNFST_LIST. */
		public static final String MNFST_LIST = "mnfstTypeList";

		/** The Constant DELIVERY_VEHICLE_MANIFEST_QUERY. */
		public static final String DELIVERY_VEHICLE_MANIFEST_QUERY = "getDeliveryDetailsForVehicleManifest";

		/** The Constant RTO_VEHICLE_MANIFEST_QUERY. */
		public static final String RTO_VEHICLE_MANIFEST_QUERY = "getRTODtlsByNoMnfCatAndOffice";
		
		/** The gateway code. */
		String GATEWAY_CODE = "GW";
		
		/** The validate dox consignment. */
		String VALIDATE_DOX_CONSIGNMENT = "error.invalidDocument.Dox";
		
		/** The validate non dox consignment. */
		String VALIDATE_NON_DOX_CONSIGNMENT = "error.invalidDocument.NonDox";
		
		/** The invalid document. */
		String INVALID_DOCUMENT = "error.invalidDocument";
		
		/** The validate pep consignment. */
		String VALIDATE_PEP_CONSIGNMENT = "error.invalidDocument.pepConsignments";
		
		/** The validate product. */
		String VALIDATE_PRODUCT = "error.invalidProduct";

		/** The Constant HYPEN. */
		public static final String HYPEN = "-";

		/** The Constant CO_LOADER. */
		public static final String CO_LOADER = "coloader";

		/** The Constant OTC. */
		public static final String OTC = "otc";

		/** The Constant DIRECT. */
		public static final String DIRECT = "direct";

		/** The Constant REG_NUMBER. */
		public static final String REG_NUMBER = "regNumber";

		/** The Constant GET_VEHICLE_BY_REG_NUMBER_QUERY. */
		public static final String GET_VEHICLE_BY_REG_NUMBER_QUERY = "getVehicleByRegNumber";

		/** The Constant GET_DRIVER_BY_DRIVER_CODE_QUERY. */
		public static final String GET_DRIVER_BY_DRIVER_CODE_QUERY = "getDriverByDriverCode";

		/** The Constant DRIVER_CODE. */
		public static final String DRIVER_CODE = "driverCode";

		/** The Constant VECHILE_MANIFEST_QUERY. */
		public static final String VECHILE_MANIFEST_QUERY = "getVehicleManifestByTransNO";

		/** The Constant TRANCATION_NUMBER. */
		public static final String TRANCATION_NUMBER = "transcationNumber";

		/** The bag manifest class type. */
		String BAG_MANIFEST_CLASS_TYPE = "BagManifestMDBService";
		
		/** The incoming bag manifest save method. */
		String INCOMING_BAG_MANIFEST_SAVE_METHOD = "saveIncomingBagManifestDetails";

		/** The vehilce manifest class type. */
		String VEHILCE_MANIFEST_CLASS_TYPE = "VehicleManifestService";
		
		/** The vehicle manifest save method. */
		String VEHICLE_MANIFEST_SAVE_METHOD = "save";

		/** The Constant VEH_MNFST_LOAD_TYPE. */
		public static final String VEH_MNFST_LOAD_TYPE = "VEHICLE_MANIFEST_LOAD_TYPE";

		/** The Constant DESCRIPTION. */
		public static final String DESCRIPTION = "description";

		/** The Constant GET_STD_TYPE_BY_DESC_QUERY. */
		public static final String GET_STD_TYPE_BY_DESC_QUERY = "getStdTypesByDescAndTypeName";

		/** The Constant STD_TYPE_RELATIONSHIP_LIST. */
		public static final String STD_TYPE_RELATIONSHIP_LIST = "stdTypeList";

		/** The manifest heldup. */
		public String MANIFEST_HELDUP = "error.manifest.heldUp";
		
		/** The invalid manifest master bag. */
		public String INVALID_MANIFEST_MASTER_BAG = "error.masterBagFound";

		/** The total bag manifest weight in kgs tolerance. */
		String TOTAL_BAG_MANIFEST_WEIGHT_IN_KGS_TOLERANCE = "TOTAL_BAG_MANIFEST_WEIGHT_IN_KGS_TOLERANCE";
		
		/** The bag to total pkt weight in kgs tolerance. */
		String BAG_TO_TOTAL_PKT_WEIGHT_IN_KGS_TOLERANCE = "BAG_TO_TOTAL_PKT_WEIGHT_IN_KGS_TOLERANCE";
		
		/** The max consgmnts in nondox paperwork mnfst. */
		String MAX_CONSGMNTS_IN_NONDOX_PAPERWORK_MNFST = "MAX_CONSGMNTS_IN_NONDOX_PAPERWORK_MNFST";
		
		/** The nondox paperwork cn weight tolerance in kgs. */
		String NONDOX_PAPERWORK_CN_WEIGHT_TOLERANCE_IN_KGS = "NONDOX_PAPERWORK_CN_WEIGHT_TOLERANCE_IN_KGS";
		
		/** The max dox weight tolerance in kgs ssm. */
		String MAX_DOX_WEIGHT_TOLERANCE_IN_KGS_SSM = "MAX_DOX_WEIGHT_TOLERANCE_IN_KGS_SSM";
		
		/** The total mnfst weight tolerance in kgs ssm. */
		String TOTAL_MNFST_WEIGHT_TOLERANCE_IN_KGS_SSM = "TOTAL_MNFST_WEIGHT_TOLERANCE_IN_KGS_SSM";
		
		/** The rto request weight tolerance in kgs. */
		String RTO_REQUEST_WEIGHT_TOLERANCE_IN_KGS = "RTO_REQUEST_WEIGHT_TOLERANCE_IN_KGS";
		
		/** The non dox rto weight. */
		String NON_DOX_RTO_WEIGHT = "NON_DOX_RTO_WEIGHT_IN_KGS";

		/** The invalid product service. */
		public String INVALID_PRODUCT_SERVICE = "error.invalidProductService";

		/** The update to billing in sap integration. */
		String UPDATE_TO_BILLING_IN_SAP_INTEGRATION = "updateBillingStatus";
		
		/** The billing status. */
		String BILLING_STATUS = "billingStatus";
		
		/** The outgoing manifest type. */
		String OUTGOING_MANIFEST_TYPE = "manifestType";
		
		/** The booking consg number. */
		String BOOKING_CONSG_NUMBER = "consgNum";
		
		/** The update query for booking weight. */
		String UPDATE_QUERY_FOR_BOOKING_WEIGHT = "updateBookedWeight";
		
		/** The final weight. */
		String FINAL_WEIGHT = "finalWeight";
		
		/** The rate amount. */
		String RATE_AMOUNT = "rateAmount";
		
		/** The delivery status. */
		String DELIVERY_STATUS = "deliveryStatus";
		
		/** The delivery consg number. */
		String DELIVERY_CONSG_NUMBER = "conNum";
		
		/** The document type. */
		String DOCUMENT_TYPE = "documentType";
		
		/** The consignment status. */
		String CONSIGNMENT_STATUS = "consgStatus";

		/** The outgoing manifest code. */
		String OUTGOING_MANIFEST_CODE = "manifestCode";
		
		/** The outgoing pkt max cn limit. */
		String OUTGOING_PKT_MAX_CN_LIMIT = "OUTGOING_PKT_MAX_CN_LIMIT";
		
		/** The cn weight tolerance. */
		String CN_WEIGHT_TOLERANCE = "CN_WEIGHT_TOLERANCE";
		
		/** The outgoing pkt weight limit kgs. */
		String OUTGOING_PKT_WEIGHT_LIMIT_KGS = "OUTGOING_PKT_WEIGHT_LIMIT_KGS";
		
		/** The outgoing bag weight limit. */
		String OUTGOING_BAG_WEIGHT_LIMIT = "OUTGOING_BAG_WEIGHT_LIMIT";
		
		/** The total records. */
		String TOTAL_RECORDS = "totalRecords";
		
		/** The weight tolerence. */
		String WEIGHT_TOLERENCE = "weightTolerence";
		
		/** The total pkt weight. */
		String TOTAL_PKT_WEIGHT = "totalPktWeight";
		
		/** The total bag weight. */
		String TOTAL_BAG_WEIGHT = "totalBagWeight";
		
		/** The packet manifest dox page. */
		String PACKET_MANIFEST_DOX_PAGE = "packetManifestDoxPage";
		
		/** The dest branch code. */
		String DEST_BRANCH_CODE = "destBrCode";
		
		/** The dest branch name. */
		String DEST_BRANCH_NAME = "destBrName";
		
		/** The origin branch code. */
		String ORIGIN_BRANCH_CODE = "orgBrCode";
		
		/** The origin branch name. */
		String ORIGIN_BRANCH_NAME = "orgBrName";
		
		/** The from region. */
		String FROM_REGION = "frmRegion";
		
		/** The to region. */
		String TO_REGION = "toRegion";
		
		/** The service type. */
		String SERVICE_TYPE = "serviceType";
		
		/** The packet weight. */
		String PACKET_WEIGHT = "pktWeight";
		
		/** The handling instruction. */
		String HANDLING_INSTRUCTION = "handInst";
		
		/** The total consignments. */
		String TOTAL_CONSIGNMENTS = "totCongnts";
		
		/** The total pieces. */
		String TOTAL_PIECES = "totalPieces";
		
		/** The total weights. */
		String TOTAL_WEIGHTS = "totWeights";
		
		/** The mode. */
		String MODE = "mode";
		
		/** The user. */
		String USER = "userName";
		
		/** The remarks. */
		String REMARKS = "consgRemarks";
		
		/** The mnp remarks. */
		String MNP_REMARKS = "mentionPieceRmks";
		
		/** The packet manifest report name. */
		String PACKET_MANIFEST_REPORT_NAME = "attachment; filename=outgoingPacketManifestReport.pdf";
		
		/** The robo manifest report name. */
		String ROBO_MANIFEST_REPORT_NAME = "attachment; filename=roboChecklistManifestReport.pdf";
		
		/** The outgoing bag nondox manifest. */
		String OUTGOING_BAG_NONDOX_MANIFEST = "BAG MANIFEST(NON DOX) - OUTGOING";
		
		/** The total weight. */
		String TOTAL_WEIGHT = "totalWeight";
		
		/** The outgoing bag manifest non dox. */
		String OUTGOING_BAG_MANIFEST_NON_DOX = "attachment; filename=outgoingBagManifestNonDox.pdf";
		
		/** The outgoing misroute manifest. */
		String OUTGOING_MISROUTE_MANIFEST = "OUTGOING MENTION PIECES BAG MANIFEST";
		
		/** The outgoing mis route manifest. */
		String OUTGOING_MIS_ROUTE_MANIFEST = "attachment; filename=outgoingMisRouteManifest.pdf";
		
		/** The outgoing mnpbag manifest. */
		String OUTGOING_MNPBAG_MANIFEST = "OUTGOING BAG MANIFEST - MENTIONED PIECES";
		
		/** The outgoing mnp bag manifest. */
		String OUTGOING_MNP_BAG_MANIFEST = "attachment; filename=outgoingMNPBagManifest.pdf";
		
		/** The consg pin code. */
		String CONSG_PIN_CODE = "consgpinCode";
		
		/** The branch id. */
		String BRANCH_ID = "branchID";
		
		/** The manifest types manifest type id. */
		String MANIFEST_TYPES_MANIFEST_TYPE_ID = "mnsftTypes.mnfstTypeId";
		
		/** The booking id. */
		String BOOKING_ID = "bookingId";

		/** The master bag manifest service class type. */
		String MASTER_BAG_MANIFEST_SERVICE_CLASS_TYPE = "MasterBagManifestMDBService";

		/** The incoming master bag manifest save method. */
		String INCOMING_MASTER_BAG_MANIFEST_SAVE_METHOD = "saveIncomingMasterBagManifestDetails";

		/** The rto manifest incoming mdb service class type. */
		String RTO_MANIFEST_INCOMING_MDB_SERVICE_CLASS_TYPE = "RTOManifestIncomingMDBService";

		/** The rto manifest incoming save method. */
		String RTO_MANIFEST_INCOMING_SAVE_METHOD = "saveRTOIncomingBagManifestDetails";

		/** The rto manifest outgoing mdb service class type. */
		String RTO_MANIFEST_OUTGOING_MDB_SERVICE_CLASS_TYPE = "RTOManifestOutgoingMDBService";

		/** The rto manifest outgoing save method. */
		String RTO_MANIFEST_OUTGOING_SAVE_METHOD = "saveRTOOutgoingBagManifestDetails";
		
		/** The is misrouted. */
		String isMisrouted = "isMisrouted";
		
		/** The already misrouted. */
		String ALREADY_MISROUTED = "error.alreadyMisrouted";
		// String HELDUP_CONSIGNMENT =
		// "Consignment is Held Up You Cannot Manifest the Consignment";
		/** The heldup consignment status. */
		String HELDUP_CONSIGNMENT_STATUS = "H";
		
		/** The heldup. */
		String HELDUP = "HELDUP";

		/** The loggedin user details not found error. */
		String LOGGEDIN_USER_DETAILS_NOT_FOUND_ERROR = "Loggedin USer Details Not Found!!";

		/** The vehicle manifest mdb service class type. */
		String VEHICLE_MANIFEST_MDB_SERVICE_CLASS_TYPE = "VehicleManifestMDBService";

		/** The vehicle manifest save update method. */
		String VEHICLE_MANIFEST_SAVE_UPDATE_METHOD = "saveVehicleManifestDetails";

		/** The invalid. */
		String INVALID = "INVALID";
		
		/** The both. */
		String BOTH = "BOTH";
		
		/** The nondox. */
		String NONDOX = "NONDOX";
		
		/** The warning. */
		String WARNING = "WARNING";

		/** The dispatch slip. */
		String DISPATCH_SLIP = "DS";

		/** The get dispatch slip by number and origin branch. */
		String GET_DISPATCH_SLIP_BY_NUMBER_AND_ORIGIN_BRANCH = "getDispatchColoaderObjectByNumberAndOriginBranch";

		/** The dispatch id. */
		String DISPATCH_ID = "dispatchId";
		
		/** The pending manifest on day. */
		String pendingManifestOnDay = "pendingManifestOnDay";
		
		/** The pending rto manifest on day. */
		String pendingRTOManifestOnDay = "pendingRTOManifestOnDay";
		
		/** The pending fdm manifest on day. */
		String pendingFDMManifestOnDay = "pendingFDMManifestOnDay";
		
		/** The pending mnp booking on day. */
		String pendingMNPBookingOnDay = "pendingMNPBookingOnDay";

		/** The pod customer type customer. */
		String POD_CUSTOMER_TYPE_CUSTOMER = "Customer";
		
		/** The pod customer type franchisee. */
		String POD_CUSTOMER_TYPE_FRANCHISEE = "Franchisee";
		
		/** The pod customer type office. */
		String POD_CUSTOMER_TYPE_OFFICE = "Office";

		/** The default select value. */
		String DEFAULT_SELECT_VALUE = "Select";

		/** The read by local default value. */
		String READ_BY_LOCAL_DEFAULT_VALUE = "N";
		
		/** The incoming bag manifest report name. */
		String INCOMING_BAG_MANIFEST_REPORT_NAME = "attachment; filename=incomingBagManifestReport.pdf";

		/** The city id. */
		String CITY_ID = "cityId";

		/** The get consignment details for vehicle. */
		String GET_CONSIGNMENT_DETAILS_FOR_VEHICLE = "getConsignmentDetailsForVehicle";

		/** The get rto details for vehicle manifest. */
		String GET_RTO_DETAILS_FOR_VEHICLE_MANIFEST = "getRTODtlsByNoMnfCatAndOfficeForVM";

		/** The cd ltr number. */
		String CD_LTR_NUMBER = "cdLtrNumber";

		/** The get dispatch by cd ltr number. */
		String GET_DISPATCH_BY_CD_LTR_NUMBER = "getDispatchByCDLTRNumber";

		/** The pickup type. */
		String PICKUP_TYPE = "PICKUP_TYPE";

		/** The office type trans shipment apex. */
		String OFFICE_TYPE_TRANS_SHIPMENT_APEX = "TA";
		
		/** The office type city apex. */
		String OFFICE_TYPE_CITY_APEX = "SA";

		/** The rto incoming already done. */
		String RTO_INCOMING_ALREADY_DONE = "RTO Incoming Allready done for this manifest number";

		/** The get rto outgoing details. */
		String GET_RTO_OUTGOING_DETAILS = "getRtnToOrgListByNumberAndType";
		
		/** The get incoming bag details for rto. */
		String GET_INCOMING_BAG_DETAILS_FOR_RTO = "getIncomingBagForRTOValidation";
		
		/** The invalid pkt in master. */
		String INVALID_PKT_IN_MASTER = "error.pktDoesNotPutInMasterBag";

		/** The validate drs no against goods issue query. */
		String VALIDATE_DRS_NO_AGAINST_GOODS_ISSUE_QUERY = "verifySerialNumber";
		
		/** The validate drs no against goods issue query param. */
		String VALIDATE_DRS_NO_AGAINST_GOODS_ISSUE_QUERY_PARAM = "serialNum";
		
		/** The validate drs no against goods receipt query. */
		String VALIDATE_DRS_NO_AGAINST_GOODS_RECEIPT_QUERY = "verifySerialNumberForReceipt";
		
		/** The validate drs no against goods receipt query param. */
		String VALIDATE_DRS_NO_AGAINST_GOODS_RECEIPT_QUERY_PARAM = "serialNum";

		/** The delivery inactive status. */
		String DELIVERY_INACTIVE_STATUS = "I";
		
		/** The active delivery status. */
		String ACTIVE_DELIVERY_STATUS = "A";

		/** The signature recived. */
		String SIGNATURE_RECIVED = "Y";
		
		/** The signature not recived. */
		String SIGNATURE_NOT_RECIVED = "N";

		/** The delivered at residence. */
		String DELIVERED_AT_RESIDENCE = "Y";
		
		/** The not delivered at residence. */
		String NOT_DELIVERED_AT_RESIDENCE = "N";

		/** The invalid master bag manifest. */
		String INVALID_MASTER_BAG_MANIFEST = "error.invalidMasterBagManifest";

		/** The prepared. */
		String PREPARED = "PREPARED";

		/** The get consignments by consg no and status query. */
		String GET_CONSIGNMENTS_BY_CONSG_NO_AND_STATUS_QUERY = "getConsignmentObject";
		
		/** The validate bag non dox manifest. */
		String VALIDATE_BAG_NON_DOX_MANIFEST = "error.invalidDocument.BagNonDox";
		
		/** The validate bag dox manifest. */
		String VALIDATE_BAG_DOX_MANIFEST = "error.invalidDocument.BagDox";
		
		/** The mnp cn max limit. */
		String MNP_CN_MAX_LIMIT = "MNP_CN_MAX_LIMIT";
		
		/** The total mnp records. */
		String TOTAL_MNP_RECORDS = "totalMNPRecords";

		/** The auto inscan. */
		String AUTO_INSCAN = "Y";
		
		/** The manual inscan. */
		String MANUAL_INSCAN = "N";

		/** The sheet number numeric validation. */
		String SHEET_NUMBER_NUMERIC_VALIDATION = "error.sheetNumberStartWithCharcterAndRemaining7AreDigits";

		/** The sheet number reg code. */
		String SHEET_NUMBER_REG_CODE = "Sheet number should start with Region Code.";
		
		/** The sheet number length. */
		String SHEET_NUMBER_LENGTH = "Sheet number should be alphanumeric and length is 8.";

		/** The max pod count. */
		String MAX_POD_COUNT = "MAX_POD_COUNT";
		
		/** The pod back date limit. */
		String POD_BACK_DATE_LIMIT = "POD_BACK_DATE_LIMIT";
		
		/** The pod product series. */
		String POD_PRODUCT_SERIES = "POD_PRODUCT_SERIES";
		
		/** The pod series. */
		String POD_SERIES = "POD_SERIES";
		
		/** The pod received. */
		String POD_RECEIVED = "Yes";
		
		/** The stationary item type pod. */
		String STATIONARY_ITEM_TYPE_POD = "POD";
		
		/** The unstamped pod expend type. */
		String UNSTAMPED_POD_EXPEND_TYPE = "UNSTAMPED_POD";
		
		/** The unstamped pod penalty charge. */
		String UNSTAMPED_POD_PENALTY_CHARGE = "UNSTAMPED_POD_PENALTY_CHARGE";
		
		/** The unstamped pod process. */
		String UNSTAMPED_POD_PROCESS = "UnstampedPOD";

		/** The total pkt weight tolerance in kgs. */
		String TOTAL_PKT_WEIGHT_TOLERANCE_IN_KGS = "TOTAL_PKT_WEIGHT_TOLERANCE_IN_KGS";
		
		/** The premium cn series. */
		String PREMIUM_CN_SERIES = "PREMIUM_CN_SERIES";

		/* Set Session variable Names for Incoming Bag Manifest */
		/** The ibagmf config params. */
		String IBAGMF_CONFIG_PARAMS = "configParamValues";
		
		/** The ibagmf pkt types in bag. */
		String IBAGMF_PKT_TYPES_IN_BAG = "packetTypesInBag";
		
		/** The ibagmf deps categories. */
		String IBAGMF_DEPS_CATEGORIES = "depsCategorys";
		
		/** The ibagmf offices. */
		String IBAGMF_OFFICES = "offices";
		
		/** The ibagmf all modes. */
		String IBAGMF_ALL_MODES = "allModes";

		/** The Constant GET_INCOMING_BAG_DOX_OR_NONDOX_DETAILS_FOR_RTO. */
		public static final String GET_INCOMING_BAG_DOX_OR_NONDOX_DETAILS_FOR_RTO = "getIncomingBagDoxOrNonDoxForRTOValidation";

		/** The Constant GET_CONSIGNMENT_NO_DETAILS_FOR_RTO_MANIFEST. */
		public static final String GET_CONSIGNMENT_NO_DETAILS_FOR_RTO_MANIFEST = "getConsignmentNODetailsForRTOManifest";

		/** The Constant OFFICEID. */
		public static final String OFFICEID = "officeID";

		/** The Constant CHECK_FOR_CONSIG_NO_IS_ASSIGN_TO_BRANCH_QUERY. */
		public static final String CHECK_FOR_CONSIG_NO_IS_ASSIGN_TO_BRANCH_QUERY = "checkForConsigNoIsAssignToBranch";

		/** The Constant CHECK_CONSIG_NO_IF_ISSUED_TO_BRANCH. */
		public static final String CHECK_CONSIG_NO_IF_ISSUED_TO_BRANCH = "checkConsigIfIssuedToBranch";

		/** The get incoming packet manifest details. */
		String GET_INCOMING_PACKET_MANIFEST_DETAILS = "getInMFDetails";

		/** The get incoming packet manifest details query. */
		String GET_INCOMING_PACKET_MANIFEST_DETAILS_QUERY = "getInMFDetailsForRTOOutgoing";

		/** The Constant GET_REGIONAL_OFFICEID_BY_OFFICE_ID. */
		public static final String GET_REGIONAL_OFFICEID_BY_OFFICE_ID = "getRegionalOfficeIdByOfficeId";

		/** The Constant GET_APEXLIST_BY_REGIONAL_OFFICEID. */
		public static final String GET_APEXLIST_BY_REGIONAL_OFFICEID = "getApexListByRegionalOfficeId";

		/** The Constant GET_EXPENDITURE_TYPE_RTO_QUERY. */
		public static final String GET_EXPENDITURE_TYPE_RTO_QUERY = "getExpenditureTypeForRTO";

		/** The Constant EXPENDITURE_TYPE. */
		public static final String EXPENDITURE_TYPE = "expnType";

		/** The Constant GET_OUT_GOING_PODS_BY_CONSIG_NUMBER_QUERY. */
		public static final String GET_OUT_GOING_PODS_BY_CONSIG_NUMBER_QUERY = "findOutgoingPodMfByConsigNumber";

		/** The Constant GET_DELIVERY_CONSG_STATUS_QUERY. */
		public static final String GET_DELIVERY_CONSG_STATUS_QUERY = "getDeliveryByConsgNoForRTOIncoming";

		/** The Constant GET_INCOMING_POD_MANIFEST_DETAILS_QUERY. */
		public static final String GET_INCOMING_POD_MANIFEST_DETAILS_QUERY = "getIncomingPODManifestDetails";

		/** The Constant GET_INCOMING_NON_DOX_DETAILS. */
		public static final String GET_INCOMING_NON_DOX_DETAILS = "getIncomingBagDoxOrNonDoxForRTOValidationForNonDOX";

		/** The Constant GET_INCOMING_DOX_DETAILS. */
		public static final String GET_INCOMING_DOX_DETAILS = "getIncomingBagDoxOrNonDoxForRTOValidationForDOX";

		/** The Constant GET_INCOMING_PODS_BY_CONSIG_NUMBER_QUERY. */
		public static final String GET_INCOMING_PODS_BY_CONSIG_NUMBER_QUERY = "getIncomingPodMfByConsigNumber";

		/** The Constant PRODUCT_DO. */
		public static final String PRODUCT_DO = "productDO";

		/** The Constant PRODUCT_ID. */
		public static final String PRODUCT_ID = "productId";

		/** The Constant GET_MODE_DETAILS_OBJECTS. */
		public static final String GET_MODE_DETAILS_OBJECTS = "getModeDetailsObject";

		/** The Constant PRODUCT_TYPE. */
		public static final String PRODUCT_TYPE = "productType";

		/** The Constant GET_BRANCH_OFFICE_QUERY. */
		public static final String GET_BRANCH_OFFICE_QUERY = "getBranchOffice";

		/** The Constant OFFICE_ID_PARAM. */
		public static final String OFFICE_ID_PARAM = "officeId";
		
		/** The Constant GET_ALL_REGIONAL_OFFICE_TYPE_QUERY. */
		public static final String GET_ALL_REGIONAL_OFFICE_TYPE_QUERY = "getAllRegionalOfficesType";

		/** The Constant GET_PINCODE_DETAILS_BY_PINCODE_ID_QUERY. */
		public static final String GET_PINCODE_DETAILS_BY_PINCODE_ID_QUERY = "getPincodeByPincodeId";

		/** The Constant PINCODE_ID. */
		public static final String PINCODE_ID = "pincodeId";

		/** The Constant REGIONAL_OFFICE_CODE. */
		public static final String REGIONAL_OFFICE_CODE = "RO";
		
		/** The Constant BRANCH_OFFICE_CODE. */
		public static final String BRANCH_OFFICE_CODE = "BO";

		/** The Constant GET_ALL_OFFICE_BY_REGIONAL_OFFICE_ID_QUERY. */
		public static final String GET_ALL_OFFICE_BY_REGIONAL_OFFICE_ID_QUERY = "getAllOfficeByRegionalOfficeId";

		/** The Constant REGIONAL_OFFICE_ID. */
		public static final String REGIONAL_OFFICE_ID = "roId";

		/** The Constant GET_BOOKING_DETAILS_BY_CN_AND_OFFICE_LIST_QUERY. */
		public static final String GET_BOOKING_DETAILS_BY_CN_AND_OFFICE_LIST_QUERY = "getBookingDetailsByCnAndOfficeList";

		/** The Constant GET_INCOMING_POD_MF_BY_MANIFEST_NUMBER_PRINT. */
		public static final String GET_INCOMING_POD_MF_BY_MANIFEST_NUMBER_PRINT = "getInComingPodMfByMfNumberPrint";

		/** The Constant VALIDATE_CN_ALLREADY_RTOED_FROM_BRANCH. */
		public static final String VALIDATE_CN_ALLREADY_RTOED_FROM_BRANCH = "validateCNalreadyRTOedFromTheBranch";

		/** The Constant VALIDATE_CN_ALLREADY_RTOED_FROM_BRANCH_INCOMING. */
		public static final String VALIDATE_CN_ALLREADY_RTOED_FROM_BRANCH_INCOMING = "validateCNRTOIncomingFromTheBranch";

		/** The Constant DEST_CITY_NOT_AVALABLE. */
		public static final String DEST_CITY_NOT_AVALABLE = "error.destinationCityNotEixst";
		
		/** The bag manifest update. */
		String BAG_MANIFEST_UPDATE = "U";
		
		/** The bag manifest save. */
		String BAG_MANIFEST_SAVE = "S";
		
		/** The Constant GET_OUT_GOING_POD_MF_BY_MANIFEST_NUMBER_PRINT. */
		public static final String GET_OUT_GOING_POD_MF_BY_MANIFEST_NUMBER_PRINT = "getOutgoingPodMfByMfNumberPrint";
		
		/** The get out going pod mf by mf number query. */
		String GET_OUT_GOING_POD_MF_BY_MF_NUMBER_QUERY = "getOutgoingPODMFByMfNumber";
	    
    	/** The get manifest id. */
    	String GET_MANIFEST_ID  = "getOutgoingManifestIdForBranchForBODBAdmin";
	    
    	/** The manifesttype. */
    	String MANIFESTTYPE = "mnfstType";
	     
}
