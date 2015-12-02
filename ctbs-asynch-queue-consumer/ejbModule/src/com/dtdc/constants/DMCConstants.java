package src.com.dtdc.constants;

// TODO: Auto-generated Javadoc
/**
 * The Interface DMCConstants.
 */
public interface DMCConstants {

	/** The franchisee. */
	public static String FRANCHISEE = "franchisee";
	
	/** The direct employee. */
	public static String DIRECT_EMPLOYEE = "directemployee";

	/** The all franchisee. */
	public static String ALL_FRANCHISEE = "allFranchaseCode";
	
	/** The all direct employee. */
	public static String ALL_DIRECT_EMPLOYEE = "allEmployeeCode";

	/** The product type list. */
	public static String  PRODUCT_TYPE_LIST="productTypeList";
	
	/** The service type list. */
	public static String  SERVICE_TYPE_LIST="serviceTypeList";
	
	/** The fproduct type. */
	public static String FPRODUCT_TYPE="fproductType";
	
	/** The ffdm total. */
	public static String FFDM_TOTAL="ffdmTotal";
	
	/** The fmissort reasons. */
	public static String  FMISSORT_REASONS="fmisSortReasons";
	
	/** The fdelivered. */
	public static String FDELIVERED="fdelivered";
	
	/** The fnon delivered. */
	public static String FNON_DELIVERED="fnonDelivered";
	
	/** The fno info. */
	public static String FNO_INFO="fnoInfo";
	
	/** The fretained. */
	public static String FRETAINED="fretained";
	
	/** The fcharges. */
	public static String  FCHARGES="fcharges";
	
	/** The consignment no. */
	public static String CONSIGNMENT_NO="consignmentNo";
	
	/** The drs no. */
	public static String DRS_NO="drsNo";
	
	/** The delivered. */
	public static String DELIVERED="delivered";
	
	/** The non delivered. */
	public static String  NON_DELIVERED="nonDelivered";
	
	/** The mis sort. */
	public static String MIS_SORT="misSort";
	
	/** The retained. */
	public static String RETAINED="retained";
	
	/** The vas service codes. */
	public static String VAS_SERVICE_CODES="vasServicesCode";
	
	/** The cod amount. */
	public static String COD_AMOUNT="codAmount";
	
	/** The fod amount. */
	public static String FOD_AMOUNT="fodAmount";
	
	/** The receipt no. */
	public static String RECEIPT_NO="receiptNo";
	
	/** The cheque no. */
	public static String CHEQUE_NO="chequeNo";
	
	/** The one. */
	public static String ONE="1";
	
	/** The two. */
	public static String TWO="2";
	
	/** The three. */
	public static String THREE="3";
	
	/** The four six. */
	public static String FOUR_SIX="46";
	
	/** The four seven. */
	public static String FOUR_SEVEN="47";
	
	/** The four five. */
	public static String FOUR_FIVE="45";
	
	/** The char d. */
	public static String CHAR_D="D";
	
	/** The franch emp id. */
	public static String FRANCH_EMP_ID="franchEmpId";
	
	/** The delivery date. */
	public static String  DELIVERY_DATE="deliverydate";
	
	/** The get drsnumber info hql. */
	public static String GET_DRSNUMBER_INFO_HQL	="getDrsNumberInfo";
	
	/** The Constant FOUR_SIX_CONSTANT. */
	public static final Integer FOUR_SIX_CONSTANT=46;
	
	/** The Constant FOUR_FIVE_CONSTANT. */
	public static final Integer FOUR_FIVE_CONSTANT=45;
	
	/** The Constant FOUR_SEVEN_CONSTANT. */
	public static final Integer FOUR_SEVEN_CONSTANT=47;
	
	/** The Constant ONE_CONSTANT. */
	public static final Integer ONE_CONSTANT=1;
	
	/** The Constant TWO_CONSTANT. */
	public static final Integer TWO_CONSTANT=2;
	
	/** The Constant THREE_CONSTANT. */
	public static final Integer THREE_CONSTANT=3;
	
	/** The check for drs hql. */
	public static String CHECK_FOR_DRS_HQL="checkForDrs";
	
	/** The get employee by code hql. */
	public static String GET_EMPLOYEE_BY_CODE_HQL="getEmployeeByEmpCode";
	
	/** The get franchisee by code hql. */
	public static String GET_FRANCHISEE_BY_CODE_HQL="getFranchiseeByFrCode";
	
	/** The genereate report fr hql. */
	public static String GENEREATE_REPORT_FR_HQL="SELECT new com.dtdc.domain.transaction.delivery.DMCReportWrapperDO(delivery.franchiseeDO.franchiseeCode ,"+
                      " SUM(delivery.fdmdrsCount),"+
                      " SUM(delivery.delivered),"+
                      " SUM(delivery.nonDelivered),"+
                      " SUM(delivery.noInformationCount),"+
                      " SUM(delivery.misSort),"+
                      " SUM(delivery.totalDrsCount)";
	
	/** The genereate report emp hql. */
	public static String GENEREATE_REPORT_EMP_HQL="SELECT new com.dtdc.domain.transaction.delivery.DMCReportWrapperDO(delivery.employeeDO.empCode ,"+
    " SUM(delivery.fdmdrsCount),"+
    " SUM(delivery.delivered),"+
    " SUM(delivery.nonDelivered),"+
    " SUM(delivery.noInformationCount),"+
    " SUM(delivery.misSort),"+
    " SUM(delivery.totalDrsCount)";
	
	/** The genereate report fr hql where. */
	public static String GENEREATE_REPORT_FR_HQL_WHERE=" FROM com.dtdc.domain.transaction.delivery.DeliveryManagementDO delivery "+
               " WHERE  delivery.franchiseeDO.franchiseeId =:franchEmpId "+
                  " AND delivery.handoverDate BETWEEN :fromDate AND :toDate "+
                  " AND delivery.productTypeDO.productId = :productId ";
                 
	
	/** The genereate report emp hql where. */
	public static String GENEREATE_REPORT_EMP_HQL_WHERE=" FROM com.dtdc.domain.transaction.delivery.DeliveryManagementDO delivery "+
    " WHERE  delivery.employeeDO.employeeId =:employeID"+
       " AND delivery.handoverDate BETWEEN :fromDate AND :toDate"+
       " AND delivery.productTypeDO.productId = :productId";
	
	/** The genereate report service. */
	public static String GENEREATE_REPORT_SERVICE=", delivery.serviceDO.serviceType) ";
	
	/** The genereate report for all. */
	public static String GENEREATE_REPORT_FOR_ALL=" FROM com.dtdc.domain.transaction.delivery.DeliveryManagementDO delivery "+
    " WHERE  delivery.handoverDate BETWEEN :fromDate AND :toDate "+
       " AND delivery.productTypeDO.productId = :productId";
	
	/** The group by fr. */
	public static String GROUP_BY_FR=" GROUP BY delivery.franchiseeDO.franchiseeCode ";
	
	/** The group by emp. */
	public static String GROUP_BY_EMP=" GROUP BY delivery.employeeDO.empCode ";
	
	/** The region id fr. */
	public static String REGION_ID_FR="  AND delivery.franchiseeDO.officeId.reginolOfficeId = :reginolOfficeId   ";
	
	/** The branch id fr. */
	public static String BRANCH_ID_FR="  AND delivery.franchiseeDO.officeId.officeId = :bracnchId  ";
	
	/** The region id emp. */
	public static String REGION_ID_EMP="  AND delivery.employeeDO.office.reginolOfficeId = :reginolOfficeId   ";
	
	/** The branch id emp. */
	public static String BRANCH_ID_EMP=" AND delivery.employeeDO.office.officeId = :bracnchId  ";
	 
     /** The dmc report service. */
     String DMC_REPORT_SERVICE="DmcReportService";
     
     /** The save delivery management info. */
     String SAVE_DELIVERY_MANAGEMENT_INFO="saveDeliverymanagementInfo";
	

}
