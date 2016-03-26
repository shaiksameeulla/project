package com.ff.rate.configuration.common.constants;

import com.ff.universe.ratemanagement.constant.RateUniversalConstants;

public interface RateCommonConstants {

	String RATE_INDUSTRY_CATEGORY_LIST = "rateIndustryCategoryList";
	String RATE_BENCH_MARK_IND_CAT_LIST = "rateBenchMarkIndustryCategoryList";
	String RATE_QUOT_IND_CAT_LIST = "rateQuotIndustryCategoryList";
	String RATE_PRODUCT_CATEGORY_LIST = "rateProductCategoryList";
	String RATE_BENCH_MARK_PROD_CAT_LIST = "rateBenchMarkProductCategoryList";
	String RATE_QUOT_PROD_CAT_N_LIST = "rateQuotNormalProdCategoryList";
	String RATE_QUOT_PROD_CAT_E_LIST = "rateQuotEcommerceProdCategoryList";
	String RATE_CUSTOMER_CATEGORY_LIST = "rateCustomerCategoryList";
	String RATE_VOB_SLABS_LIST = "rateVobSlabsList";
	String RATE_BENCH_MARK_VOB_SLAB_LIST = "rateBenchMarkVobSlabList";
	String RATE_QUOT_VOB_SLAB_LIST_N_CO = "rateCOQuotNormalVobSlabList";
	String RATE_QUOT_VOB_SLAB_LIST_E_CO = "rateCOQuotEcommerceVobSlabList";
	String RATE_QUOT_VOB_SLAB_LIST_N_FR = "rateFRQuotNormalVobSlabList";
	String RATE_QUOT_VOB_SLAB_LIST_E_FR = "rateFRQuotEcommerceVobSlabList";
	String RATE_WT_SLABS_LIST = "rateWtSlabsList";
	String RATE_BENCH_MARK_WT_SLAB_LIST = "rateBenchMarkWtSlabList";
	String RATE_QUOT_WT_SLAB_LIST_N_CO = "rateCOQuotNormalWtSlabList";
	String RATE_QUOT_WT_SLAB_LIST_E_CO = "rateCOQuotEcommerceWtSlabList";
	String RATE_QUOT_WT_SLAB_LIST_N_FR = "rateFRQuotNormalWtSlabList";
	String RATE_QUOT_WT_SLAB_LIST_E_FR = "rateFRQuotEcommerceWtSlabList";
	String RATE_SECTORS_LIST = "rateSectorsList";
	String RATE_BENCH_MARK_SECTOR_LIST = "rateBenchMarkSectorList";
	String RATE_QUOT_SECTOR_LIST_N_CO = "rateCOQuotNormalSectorList";
	String RATE_QUOT_SECTOR_LIST_E_CO = "rateCOQuotEcommerceSectorList";
	String RATE_QUOT_SECTOR_LIST_N_FR = "rateFRQuotNormalSectorList";
	String RATE_QUOT_SECTOR_LIST_E_FR = "rateFRQuotEcommerceSectorList";
	String RATE_BENCH_MARK_TO = "rateBenchMarkTO";
	String RATE_CUST_PROD_CAT_MAP_LIST = "rateCustProdCatMapList";
	String RATE_MIN_CHRG_WT_LIST = "rateMinChrgWtList";
	String RATE_BENCH_MARK_MIN_CHAG_WT_LIST = "rateBenchMarkMinChrgWtList";
	String RATE_QUOT_MIN_CHAG_WT_LIST_N_CO = "rateCOQuotNormalMinChrgWtList";
	String RATE_QUOT_MIN_CHAG_WT_LIST_E_CO = "rateCOQuotEcommerceMinChrgWtList";
	String RATE_QUOT_MIN_CHAG_WT_LIST_N_FR = "rateFRQuotNormalMinChrgWtList";
	String RATE_QUOT_MIN_CHAG_WT_LIST_E_FR = "rateFRQuotEcommerceMinChrgWtList";
	String RATE_BENCH_MARK_CUST_CAT_CODE = "CRDT";
	String RATE_BENCH_MARK_IND_CAT_CODE = "GNRL";
	String RATE_BENCH_MARK_PROD_CAT_CODE = "CO";
	String RATE_BENCH_MARK_ECOM_PROD_CAT_CODE = "EC";
	String RATE_BENCH_MARK_PROD_CAT_TYPE = "N";
	String RATE_BENCH_MARK_PROD_CAT_TYPE_N = "N";
	String RATE_BENCH_MARK_PROD_CAT_TYPE_E = "E";
	String RATE_CUST_CRDT = "CRDT";
	String RATE_CUST_FR = "FR";
	String QRY_GET_RATE_VOB_SLAB_LIST_BY_EFFECTIVE_DATE = "getVobSlabsByDate";
	String QRY_GET_RATE_WEIGHT_SLAB_LIST_BY_EFFECTIVE_DATE = "getWeightSlabsByDate";
	String PARAM_EFFECTIVE_FROM = "effectiveFrom";
	String PARAM_EFFECTIVE_TO = "effectiveTo";
	String PARAM_RATE_CUST_CAT_CODE = "rateCustomerCategoryCode";
	String PARAM_RATE_PROD_CAT_TYPE = "rateProductCategoryType";
	String QRY_GET_RATE_MIN_CHRG_WEIGHT = "getRateMinChrgWts";
	String QRY_GET_RATE_SECTORS = "getRateSectors";
	String PIN_CODE_PATTERN = "^\\d{6}?$";
	String RATE_ERROR_MSG_PROP_FILE_NAME = "RateCalculationMessageResources_en_US";
	String RATE_QUOTATION_PRODUCT_CATEGY_HEADER = "rateQuotationProductCategoryHeader";
	String RATE_QUOTATION_PRODUCT_CATEGY_HEADER_ID = "rateQuotationProductCategoryHeaderId";
	String DEST_PINCODE = "destPincode";
	String WEIGHT = "weight";
	String QRY_GET_RATE_QUOTATION_SPECIAL_DESTINATION = "getRateQuotationSpecialDestination";
	String ORIGIN_SECTOR = "orgSectorId";
	String DEST_SECTOR = "destSectorId";
	String QRY_GET_RATE_QUOTATION_OTHER_THAN_SPECIAL_DESTINATION = "getRateQuotationSlabRateForOtherThanSpecialDest";
	String COD_CHARGE_SLAB = "codChargeSlab";
	String CALC_VALUE_FIXED_OR_PER = "calcValueFixedOrPer";
	String RATE_QUOTATION_NUMBER = "rateQuotation";
	String QRY_GET_CALCULATED_COD_CHARGE = "getCalculatedCODCharge";
	Object CALCULATE_VALUE_FIXED = "F";
	Object CALCULATE_VALUE_PER = "G";
	String RATE_QUOTATION_PRODUC_CAT_CODE = "productCategoryCode";
	String RATE_QUOTATION_CUSTOMER_CODE = "customerCode";
	String GET_ALL_RATE_COMP = "getAllRateComponents";

	String RATE_COMPONENT_TYPE_COD = "CODCG";
	String RATE_COMPONENT_TYPE_OCTROI = "OCTRI";
	String RATE_COMPONENT_TYPE_RISK_SURCHARGE = "RSKCG";
	String RATE_COMPONENT_TYPE_FUEL_SURCHARGE = "FSCHG";
	String RATE_COMPONENT_TYPE_AIRPORT_HANDLING_CAHRGES = "ARHCG";
	String RATE_COMPONENT_TYPE_SURCHARGE_ON_ST = "SCSTX";
	String RATE_COMPONENT_TYPE_OTHER_CHARGES = "OTSCG";
	String RATE_COMPONENT_TYPE_PARCEL_HANDLING_CHARGES = "PRHCG";
	String RATE_COMPONENT_TYPE_EDUCATION_CESS = "EDUCS";
	String RATE_COMPONENT_TYPE_HIGHER_EDUCATION_CESS = "HEDCS";
	String RATE_COMPONENT_TYPE_DOCUMENT_HANDLING_CAHRGES = "DCHCG";
	String RATE_COMPONENT_TYPE_TO_PAY_CHARGES = "TPCHG";
	String RATE_COMPONENT_TYPE_SERVICE_TAX = "SRVTX";
	String RATE_COMPONENT_TYPE_STATE_TAX = "STTAX";
	String RATE_COMPONENT_TYPE_LC_CHARGES = "LCCHG";
	String RATE_COMPONENT_TYPE_OCTROI_SERVICE_CHARGE = "OCSCG";
	String RATE_COMPONENT_TYPE_OCTROI_BOURNE_BY = "OCTRI";
	String RATE_COMPONENT_TYPE_DECLARED_VALUE = "DCDVL";
	String RATE_COMPONENT_TYPE_FINAL_SLAB_RATE = "FNSLB";
    String RATE_COMPONENT_FINAL_SLBRATE_ADDEDTO_FUEL_SURCHARGE="FSRSA";
	String PRODUCT_CODE_PRIORITY = "PC000005";
	int MAX_RESULT_SET_COUNT = 1;

	String RATE_COMPONENT_OPERATION_ADD = "A";
	String RATE_COMPONENT_OPERATION_SUB = "S";
	String RATE_COMPONENT_OPERATION_MUL = "M";
	String RATE_COMPONENT_OPERATION_DIV = "D";
	String RATE_COMPONENT_OPERATION_PS = "PS";
	String RATE_COMPONENT_OPERATION_PERCENTILE = "P";
	String RATE_COMPONENT_OPERATION_PA = "PA";
	int RATE_COMPONENT_SEQ_SlAB_RATE = 2;
	int RATE_COMPONENT_SEQ_DECLAIRED_VALUE = 1;
	int RATE_COMPONENT_SEQ_TOTAL_WTOUT_TAX = 14;
	int RATE_COMPONENT_SEQ_GRAND_TOTAL = 25;
	String RATE_PROD_CAT_LIST = "prodCatList";
	String USER_ZONE_CODE = "zoneCode";
	String QRY_DELETE_FIXED_CHARGES = "deleteFixedCharges";
	String QUOTATION_ID = "rateQuotationId";
	String RATE_COMPONENT_LIST = "rateComponentList";

	String QRY_DELETE_RTO_CHARGES = "deleteRTOCharges";
	String RATE_TAX_COMPONENT_LIST = "rateTaxComponentList";
	String QRY_UPDATE_QUOTATION = "updateQuotation";
	String APPROVERS_REMARKS = "approversRemarks";
	String EXCECUTIVE_REMARKS = "excecutiveRemarks";
	String QRY_GET_RATE_CALCULATION_PRODUCT_HEADER = "getQuotationForCustomerAndProduct";
	String CUSTOMER_CODE = "customerCode";
	String PRODUCT_CODE = "productCode";
	String QRY_GET_NORMAL_SLABS = "getNormalRateSlabs";
	String DEST_SECTOR_ID = "destinationSector";
	String ORIGIN_SECTOR_ID = "originSector";
	String CITY_ID = "cityId";
	String QRY_GET_SPECIAL_DESTINATION_SLABS = "getSpecialDestinationSlabs";
	String QRY_GET_FIX_CHARGES_CONFIG_FOR_QUOTATION = "getFixedChargesConfigForQuotation";
	String DECLARED_VALUE = "declaredValue";
	String CUSTOMER_CATEGORY = "customerCategory";
	String QRY_GET_RISK_SURCHARGE = "getRiskSurcharge";
	String STATE_ID = "state";
	String CURRENT_DATE = "currentDate";
	String QRY_GET_RTO_CHARGES_FOR_RATE_QUOTATION = "getRTOChargesForRateQuotation";
	String QRY_GET_INSURED_BY = "getRiskSurchargeInsuredByForRateCalculation";
	String INSURED_BY = "insuredBy";
	String QRY_GET_TAX_COMPONENTS = "getTaxComponents";
	String BA_RATE_PRODUCT_CATEGY_HEADER_ID = "baRateHeaderId";
	String QRY_GET_BA_NORMAL_SLABS = "getBaNormalSlab";
	String QRY_GET_BA_SPECIAL_DESTINATION_SLABS = "getBASpecialDestinationSlabs";
	String RATE_RTO_CHARGES_LIST = "rateRTOChargesList";
	String COMPONENTS = "components";
	String RESULT_PAGE = "result";
	String RATE_QUOTATION_TYPE_E = "E";
	String RATE_QUOTATION_TYPE_N = "N";
	String COD = "COD";
	String RATE_QUOTATION_TYPE = "rateQuotationType";
	String TAX_GROUP_SEC = "SEC";
	String TAX_GROUP_SSU = "SSU";
	String CE = "CE";
	String SLAB_RATE_CODE = "SLBRT";
	String DECLARED_VALUE_CODE = "DCDVL";
	String OTHER_CHARGES_CODE = "OTSCG";
	String OCTROI_CODE = "OCTRI";
	String DISCOUNT_CODE = "DSCNT";
	String RTO_CODE = "RTODN";
	String COD_CHARGE_CODE = "CODCG";
	String RISK_CHARGE_CODE = "RSKCG";
	String COD_PRODUCT = "PC000002";
	String SERVICE_CHARGE_ON_OCTROI = "OCSCG";
	String STATE_TAX_CODE = "STTAX";
	String SURCHARGE_ON_STATE_TAX_CODE = RateUniversalConstants.SURCHARGE_ON_STATE_TAX_CODE;
	String HIGHER_EDU_CESS_CODE = "HEDOG";
	String EDU_CESS_CODE = RateUniversalConstants.EDU_CESS_CODE;
	String SERVICE_CHARGE_ON_OCTROI_CODE = "OCSTG";
	String HIGHER_EDU_CES_CODE = RateUniversalConstants.HIGHER_EDU_CES_CODE;
	String EDU_CESS_ON_OCTROI_CODE = "EDOSG";
	String SERVICE_TAX_CODE = RateUniversalConstants.SERVICE_TAX_CODE;
	String YES = "Y";
	String NO = "N";
	String QRY_GET_SECTOR_BY_CITY_CODE = "getSectorByCityCode";
	String CITY_CODE = "cityCode";
	String QRY_GET_QUOTATION_BY_CUSTOMER = "getCustomerQuotation";
	String QRY_GET_CUSTOMER_QUOTATION_DETAILS = "getCustomerQuotationDetails";
	String PRODUCT_SERIES = "productSeries";
	String QRY_GET_SLAB_BY_CUSTOMER_PRODUCT_AND_SECTOR = "getSlabByCustomerProductNSector";
	String DESTSECTOR = "destSector";
	String QRY_GET_COURIER_PRO_SLABS_BY_CUSTOMER_SECTOR = "getCourierProSlabsByCustomerNSector";
	String QRY_GET_CITY = "getCity";
	String PINCODE = "Pincode";
	String QRY_GET_DEPENDENT_COMPONENTS_FOR_RATE_CALCULATION = "getDependentRateComponentForCalculation";
	String QRY_GET_CITY_BY_CITY_CODE = "getCityByCityCode";
	String RATE_BENCH_MARK = "rateBenchMark";
	String RATE_QUOTATION = "rateQuotation";
	String QRY_DELETE_COD_CHARGES = "deleteCODCharges";
	String CREATED_BY = "createdBy";
	String QUOTATION_NO = "quotationNo";
	String QRY_SEARCH_QUOTATION = "searchQuotationDetails";
	String QRY_GET_CALCULATED_BA_COD_CHARGE = "getCalculatedBACODCharge";
	String QRY_GET_FIX_CHARGES_CONFIG_FOR_BA = "getFixedChargesConfigForBA";
	String QRY_GET_RATE_CALCULATION_PRODUCT_HEADER_FOR_CASH = "getCashProductHeaderMap";
	String RATE_TYPE = "rateType";
	String QRY_GET_FIX_CHARGES_CONFIG_FOR_CASH = "getFixedChargesConfigForCash";
	String RATE_PRODUCT_CATEGY_HEADER_ID = "header";
	String QRY_GET_CASH_NORMAL_SLABS = "getCashNormalRateSlabs";
	String QRY_GET_CASH_SPECIAL_DESTINATION_SLABS = "getCashSpecialDestinationSlabs";
	String ZONE_ID = "zoneId";
	String QRY_GET_CALCULATED_CASH_COD_CHARGE = "getCalculatedCashCODCharge";
	String LC_AMOUNT = "LCAMT";
	String COD_AMOUNT = "CODA";
	String QRY_GET_RTO_CHARGES_FOR_BA = "getRTOChargesForBA";
	String QRY_GET_RTO_CHARGES_FOR_CASH = "getRTOChargesForCash";
	String RATE_CASH_HEADER_ID = "header";

	String QRY_BLOCK_OR_UNBLOCK_CUSTOMER = "blockOrUnblockCustomer";
	String PARAM_CUSTOMER_ID = "customerId";
	String PARAM_STATUS = "status";
	String RATE_MESSAGES_RESOURCE_FILE = "RateCalculationMessageResources_en_US";
	String SUCCESS = "success";
	String QUOTATION_USER_FOR = "quotationUserFor";
	String QRY_UPDATE_CUST_PAN_NO = "updateCustPanNo";
	String QRY_UPDATE_CUST_TAN_NO = "updateCustTanNo";
	String PARAM_PAN_NO = "panNo";
	String PARAM_TAN_NO = "tanNo";
	String QRY_GET_CUST_BY_ID = "getCustById";
	String QRY_DELETE_FIXED_CHARGES_CONFIG = "deleteFixedChargesConfig";

	String QRY_UPDATE_CONTRACT_STATUS = "updateContractStatus";
	String PARAM_RATE_CONTRACT_ID = "rateContractId";
	String PARAM_CONTRACT_STATUS = "contractStatus";

	/* C- Created, S- Submitted, A- Active, I- Inactive, B- Blocked */
	String CONTRACT_STATUS_CREATED = "C";
	String CONTRACT_STATUS_SUBMITTED = "S";
	String CONTRACT_STATUS_ACTIVE = "A";
	String CONTRACT_STATUS_INACTIVE = "I";
	String CONTRACT_STATUS_BLOCKED = "B";

	String PARAM_CONTRACT_CREATED = "CONTRACT_CREATED";
	String PARAM_CONTRACT_SUBMITTED = "CONTRACT_SUBMITTED";
	String PARAM_CONTRACT_ACTIVE = "CONTRACT_ACTIVE";
	String PARAM_CONTRACT_INACTIVE = "CONTRACT_INACTIVE";
	String PARAM_CONTRACT_BLOCKED = "CONTRACT_BLOCKED";
	String CONTRACT_ID = "contractId";

	String RATE_COMMON_SERVICE = "rateCmnService";
	String REGION_ID = "regionId";
	String REGION_TOS = "regionTOs";
	String PARAM_TYPE_NAME = "typeName";
	String QRY_GET_STOCK_STD_TYPE_BY_TYPE_NAME = "getStockStdTypeByTypeName";
	String STD_TYPE_COLLECTION_AGAINST = "RATE_CONF_BA_TYPE";
	String BA_TYPE_LIST = "BATypeList";
	String BA_RATE_CONFIGURATION_SERVICE = "baRateConfigurationService";
	String BA_RATE_PRODUCT_CATEGORY_COURIER = "CO";
	String RATE_CUSTOMER_CATEGORY = "BA";
	String CUSTOMER_CODE_BA = "BA";
	String QRY_GET_BA_CUSTOMER_TYPE_BY_CUSTOMER_TYPE = "getBATypeList";
	String QRY_GET_BA_RATE_CONFIGURATION_DETAILS = "getBARateConfigurationDetails";
	String QRY_DELETE_BA_FIXED_CHARGES = "deleteBAFixedCharges";
	String BA_PRODUCT_HEADER = "baProductHeader";
	String QRY_GET_BA_ADDITIONAL_CHARGES = "getFixedChargesByHeaderIdAndPriorityIndicator";
	String INSURED_BY_LIST = "insuredByList";
	String QRY_DELETE_BA_FIXED_CHARGES_CONFIG = "deleteBAFixedChargesConfig";
	String QRY_GET_BA_FIXED_CHARGES_CONFIG = "getBAFixedChargesConfigDetails";
	String QRY_GET_BA_RTO_CHARGES_BY_HEADER = "getRTOChargesByProductHeader";
	String BA_RATE_PRODUCT_CATEGORY_PRIORITY = "PR";
	String QRY_SEARCH_QUOTATION_FOR_CORP = "searchQuotatnForCorp";
	String CITY_TOS = "cityTOs";
	String REGION_NAME="regionName";
	String CITY_NAME="cityName";

	/* CASH Rate Configuration - START */
	String VIEW_CASH_RATE_CONFIG = "viewCashRateConfiguration";
	String VIEW_RENEW_CASH_RATE_CONFIG = "viewRenewCashRateConfiguration";
	String CASH_RATE_CONFIG = "cashRateConfiguration";
	String RATE_CUST_CATEGORY_CASH = "CASH";
	String RATE_CUST_CATEGORY_ACC = "ACC";
	String RATE_SEC_TYPE_DEST = "D";
	String RATE_SEC_TYPE_ORIGIN = "O";
	String PRO_CODE_COURIER = "CO";
	String PRO_CODE_AIR_CARGO = "AR";
	String PRO_CODE_TRAIN = "TR";
	String PRO_CODE_PRIORITY = "PR";
	String PRO_CODE_EMOTIONAL_BOND = "EB";
	String PRO_CODE_ECOMMERCE = "EC";
	String RATE_PRO_CAT_TYPE_N = "N";// Non-Priority
	String RATE_PRO_CAT_TYPE_P = "P";// Priority
	String INSURED_BY_FFCL_CODE = "F";
	String INSURED_BY_CUST_CODE = "C";
	String ON = "ON";
	String ZONE_NORTH = "NORTH";
	String ZONE_EAST = "EAST";
	String ZONE_WEST = "WEST";
	String ZONE_SOUTH = "SOUTH";
	String ZONE_NORTH_EAST = "NORTH EAST";
	String ERR_MSG_CNF_ADMIN_FILE_NAME = "UdaanCnfadminApplicationResources_en_US";
	String CASH_RATE_CNF_ALREADY_EXIST = "CRC001";
	String CASH_RATE_CNF_NOT_SAVED = "CRC002";
	String VIEW_CASH_RATE_CONFIG_AT_RHO = "viewCashRateConfigAtRHO";
	String VIEW_BA_RATE_CONFIG_AT_RHO = "viewBARateConfigAtRHO";

	// param(s)
	String PARAM_REGIONS = "regions";
	String PARAM_CASH_RATE_SECTORS_FOR_CO = "cashRateSectorsForCourier";
	String PARAM_CASH_WT_SLAB_LIST_FOR_CO = "cashWeightSlabListForCourier";
	String PARAM_CASH_RATE_SECTORS_FOR_AR = "cashRateSectorsForAir";
	String PARAM_CASH_WT_SLAB_LIST_FOR_AR = "cashWeightSlabListForAir";
	String PARAM_CASH_RATE_SECTORS_FOR_TR = "cashRateSectorsForTrain";
	String PARAM_CASH_WT_SLAB_LIST_FOR_TR = "cashWeightSlabListForTrain";
	String PARAM_CASH_RATE_SECTORS_FOR_PR = "cashRateSectorsForPriority";
	String PARAM_CASH_WT_SLAB_LIST_FOR_PR = "cashWeightSlabListForPriority";
	String PARAM_CUST_CAT_CO = "CUST_CAT_CO";
	String PARAM_YES = "YES";
	String PARAM_NO = "NO";
	String PARAM_DEST = "DEST";
	String PARAM_ORIGIN = "ORIGIN";
	String PARAM_PRO_CODE_COURIER = "PRO_CODE_COURIER";
	String PARAM_PRO_CODE_AIR_CARGO = "PRO_CODE_AIR_CARGO";
	String PARAM_PRO_CODE_TRAIN = "PRO_CODE_TRAIN";
	String PARAM_PRO_CODE_PRIORITY = "PRO_CODE_PRIORITY";
	String PARAM_ORIGIN_SECTOR_LIST = "originSectorList";
	String PARAM_SERVICED_ON_LIST = "servicedOnList";
	String STD_TYPE_NAME_SERVICED_ON = "SERVICED_ON";
	String PARAM_INSURED_BY = "insuredBy";
	String PARAM_FFCL_PERCENTILE = "FFCL_PERCENTILE";
	String PARAM_CUST_PERCENTILE = "CUST_PERCENTILE";
	String PARAM_PRO_CAT_TYPE_N = "PRO_CAT_TYPE_N";
	String PARAM_PRO_CAT_TYPE_P = "PRO_CAT_TYPE_P";
	String PARAM_PRODUCT_CODE = "productCode";
	String PARAM_FROM_DATE = "fromDate";
	String PARAM_TO_DATE = "toDate";
	String PARAM_LOGGED_IN_DATE = "loggedInDate";
	String PARAM_REGION_ID = "regionId";
	String PARAM_PRODUCT_MAP_ID = "productMapId";
	String PARAM_CASH_RATE_HEADER_ID = "cashRateHeaderId";
	String PARAM_PRODUCT_MAP_IDS = "productMapIds";
	String PARAM_PREV_CASH_RATE_HEADER_ID = "prevCashRateHeaderId";
	String PARAM_CASH_MIN_CHRGL_WT_LIST = "cashMinChargeableWeightList";

	// query(s)
	String QRY_GET_CASH_RATE_HEADER_DTLS = "getCashRateHeaderDtls";
	String QRY_GET_CASH_RATE_HEADER_DTLS_FOR_RENEW = "getCashRateHeaderDtlsForRenew";
	String QRY_GET_CASH_RATE_FIXED_CHRGS_DTLS = "getCashRateFixedChrgsDtls";
	String QRY_GET_CASH_RATE_ADDITIONAL_CHRGS_DTLS = "getCashRateAdditionalChrgsDtls";
	String QRY_GET_CASH_RATE_RTO_CHRGS_DTLS = "getCashRateRtoChrgsDtls";
	String QRY_UPDATE_CASH_RATE_HEADER_STATUS = "updateCashRateHeaderStatus";
	String QRY_DELETE_CASH_RATE_FIXED_CHRG_DTLS = "deleteCashRateFixedChrgDtls";
	String QRY_DELETE_CASH_RATE_ADDITIONAL_CHRG_DTLS = "deleteCashRateAdditionalChrgDtls";
	String QRY_DELETE_CASH_RATE_RTO_CHRG_DTLS = "deleteCashRateRTOChrgDtls";
	String QRY_GET_ORIGIN_SECTOR_BY_REGION_ID = "getOriginSectorByRegionId";
	String QRY_UPDATE_CASH_RATE_CONFIG_TO_DATE = "updateCashRateConfigToDate";
	String QRY_GET_FROM_DATE_BY_CASH_RATE_HEADER_ID = "getFromDateByCashRateHeaderId";

	/* CASH Rate Configuration - END */
	String QRY_DELETE_BA_RTO_CHARGES = "deleteBaRTOChargesByProductHeader";
	String QRY_SUBMIT_BA_RATE_CONFIGURATION = "submitBaRateConfiguration";
	String BA_HEADER = "headerId";
	String CUSTOMER_ID = "customerId";
	String CONTRACT_NO = "contractNo";
	String IND_CODE_GENEREL = "GNRL";
	String IND_CODE_BFSI = "BFSI";
	String RATE_CONTRACT_TYPE = "rateContractType";
	String QRY_GET_BA_RATE_CONFIGURATION_DETAILS_BY_CITY_ID = "isExistsBaRateConfiguration";
	String QRY_UPDATE_BA_RATE_CONFIGURATION_RENEW_STATUS = "updateBAConfgRenewStatus";
	String TO_DATE = "toDate";
	String QRY_UPDATE_BA_RATE_CONFIGURATION_TODATE = "updateBAConfgTODate";
	String QRY_UPDATE_BA_RATE_CONFIGURATION_FORMDATE = "updateBAConfgFromDate";
	String PARAM_SERVICE_ON = "servicedOn";
	String TAX_GROUP = "taxGroup";
	String JAMMU_KASHMIR = "JK";
	String INDIA = "I";
	String RATE_STATE = "state";
	String RATE_PREFERENCES = "prefCodes";
	String QRY_GET_EB_RATE_PREFERENCE_DETAILS = "getEBRatePreferenceDetails";
	String PRODUCT_CODE_EMOTIONAL_BOND = "PC000001";
	String OCTROI_STATE_TAX_CODE = "OCSRT";
	String OCTROI_SURCHARGE_ON_STATE_TAX_CODE = "OCSST";
	String QRY_GET_CURR_PERIOD_CASH_CONFIG = "getCurrentPeriodCashConfig";

	/* BA Material Rate START */
	String VIEW_BA_MATERIAL_RATE_CONFIG = "viewBAMaterialRateConfig";
	String RENEW_BA_MATERIAL_RATE_CONFIG = "renewBAMaterialRateConfig";
	String PARAM_ITEM_TYPE = "itemType";
	String PARAM_TYPE_ID = "typeId";
	String PARAM_ITEM_TYPE_ID = "itemTypeId";
	String PARAM_ITEM_ID = "itemId";
	String PARAM_IND_TAX_CMPTS = "indiaTaxComponents";
	String PARAM_JK_TAX_CMPTS = "jkTaxComponents";
	String QRY_GET_BA_MTRL_RATE_CONFIG_DTLS = "getBAMaterialRateConfigDtls";
	String QRY_GET_RENEWED_BA_MTRL_RATE_CONFIG_DTLS = "getRenewedBAMtrlRateConfigDtls";
	String LOGGED_IN_DATE = "loggedInDate";
	String PARAM_BA_MTRL_RATE_ID = "baMaterialRateId";
	String PARAM_VALID_TO_DATE = "validToDate";
	String QRY_UPDATE_VALID_TO_DATE = "updateValidToDate";
	String ERR_VALID_TO_DATE_CAN_NOT_UPDATED = "Error: valid to date cannot updated";
	/* BA Material Rate END */
	String QRY_GET_RENEWED_BA_RATE_CONFIGURATION_DETAILS = "getRenewedBARateConfigurationDetails";
	String QRY_GET_BA_RATE_CONFIGURATION_RENEWED_DETAILS = "getBARateConfigurationRenewedDetails";
	String BA_CUSTOMER_CODE = "baCustomerCode";
	String RENEW_BA_RATE_CONFIGURATION = "renewBAConfiguration";
	String QRY_GET_BA_RATE_CONFIGURATION_DETAILS_BY_HEADER_ID = "getBARateConfigurationDetailsByHeaderId";
	String QRY_GET_FROM_DATE_BY_BA_RATE_HEADER_ID = "getFromDateByBARateHeaderId";
	String QRY_GET_TAX_COMPONENTS_FOR_CONFIGURATION = "getTaxComponentsForRateConfiguration";
	String QRY_GET_INDUSTRY_CATEGORY = "getIndustryCategoryById";

	String ORIGIN_CONSIDERED = "originConsidered";
	String CALCULATION_TYPE_ABS = "ABS";
	String CONSIGNOR = "CO";
	String RATE_COMPONENT_GRAND_TOTAL_INCLUDING_TAX = "GTTAX";
	String RATE_COMPONENT_TOTAL_WITHOUT_TAX = "TOTBT";

	// Added by Narasimha for Rate Types
	String RATE_TYPE_CASH = "CH";
	String RATE_TYPE_CREDIT_CUSTOMER = "CC";
	String RATE_TYPE_FRANCHISEE = "FR";
	String RATE_TYPE_BA = "BA";

	String QRY_GET_CUSTOMER_CODE_CUTOMER_RATE_TYPE_BY_CUSTOMER_ID = "getCustomerCodeAndRateCustomerCategoryByCustomerId";
	String QRY_SEARCH_RATE_QUOTATION = "searchRateQuotation";
	String QRY_GET_CASH_RATE_DETAILS_BY_HEADER_ID = "getCashRateDetailsByHeaderId";
	String STATUS_ACTIVE = "A";
	String QRY_GET_BA_RATE_DETAILS = "getBARateConfigDetails";
	String BA_SECTOR_CODE = "baSectorCode";
	String RATE_PRIORITY_IND = "priorityInd";
	String BRANCHES_NOT_FOUND ="Branches are not configured";
	String STATUS_INACTIVE = "I";
	String CONSIGNEE = "CONSIGNEE";
	String RESULT_OCTROI_PAGE = "octroiResult";
	String PARAM_BA_RATE_HEADER_ID = "headerId";
	String GET_CONSIG_DTLS = 
			"SELECT ffc.*,"+"\r\n"+
					"       fdp.CONSG_SERIES,"+"\r\n"+
					"       ffb.BOOKING_DATE,"+"\r\n"+
					"       fdp.CONSOLIDATION_WINDOW,"+"\r\n"+
					"       fdbt.BOOKING_TYPE,"+"\r\n"+
					"       fdcnt.CONSIGNMENT_CODE,"+"\r\n"+
					"       bo.OFFICE_ID,"+"\r\n"+
					"       fdp.PRODUCT_CODE,"+"\r\n"+
					"       ffcr_booking_rate.CONSIGNMENT_RATE_ID,"+"\r\n"+
					"       fdct.CUSTOMER_TYPE_CODE,"+"\r\n"+
					"       fdbt.BOOKING_TYPE_DESC,"+"\r\n"+
					"       fdp.PRODUCT_NAME,"+"\r\n"+
					"       insuby.INSURED_BY_CODE,"+"\r\n"+
					"       CASE"+"\r\n"+
					"          WHEN ffcr_booking_rate.CONSIGNMENT_RATE_ID IS NULL"+"\r\n"+
					"          THEN"+"\r\n"+
					"             'N'"+"\r\n"+
					"          ELSE"+"\r\n"+
					"             IF(ffcr_booking_rate.BILLED IS NOT NULL,"+"\r\n"+
					"                ffcr_booking_rate.BILLED,"+"\r\n"+
					"                'N')"+"\r\n"+
					"       END"+"\r\n"+
					"          AS BOOKING_RATE_BILLED,"+"\r\n"+
					"       NULL AS RTO_RATE_BILLED,"+"\r\n"+
					"       NULL AS SHIP_TO_CODE"+"\r\n"+
					"  FROM ff_f_consignment ffc"+"\r\n"+
					"       JOIN ff_d_product fdp ON ffc.PRODUCT = fdp.PRODUCT_ID"+"\r\n"+
					"       JOIN ff_f_booking ffb ON ffc.CONSG_NO = ffb.CONSG_NUMBER"+"\r\n"+
					"       JOIN ff_d_booking_type fdbt ON ffb.BOOKING_TYPE = fdbt.BOOKING_TYPE_ID"+"\r\n"+
					"       JOIN ff_d_office bo ON bo.OFFICE_ID = ffb.BOOKING_OFF"+"\r\n"+
					"       JOIN ff_d_consignment_type fdcnt"+"\r\n"+
					"          ON fdcnt.CONSIGNMENT_TYPE_ID = ffc.CONSG_TYPE"+"\r\n"+
					"       LEFT JOIN ff_d_insured_by insuby"+"\r\n"+
					"          ON insuby.INSURED_BY_ID = ffc.INSURED_BY"+"\r\n"+
					"       /* get customer */"+"\r\n"+
					"       JOIN ff_d_customer fdc ON fdc.CUSTOMER_ID = ffc.CUSTOMER"+"\r\n"+
					"       JOIN ff_d_customer_type fdct"+"\r\n"+
					"          ON fdct.CUSTOMER_TYPE_ID = fdc.CUSTOMER_TYPE"+"\r\n"+
					"       /* get rates */"+"\r\n"+
					"       LEFT JOIN ff_f_consignment_rate ffcr_booking_rate"+"\r\n"+
					"          ON (    ffc.CONSG_ID = ffcr_booking_rate.CONSIGNMENT_ID"+"\r\n"+
					"              AND ffcr_booking_rate.RATE_CALCULATED_FOR = 'B')"+"\r\n"+
					" WHERE   ffc.RATE_TYPE  = ? ";
	String QRY_GET_REGIONAL_APPROVER = "getRagionalApprovalDetails";
	
	String SALES_PERSON_NAME = "salesPersonName";
	String CUSTOMER_NAME = "customerName";
	String SOLD_TO_CODE = "soldToCode";
	String RATE_CONTRACT_NUMBER = "rateContractNumber";
	String RATE_CONTRACT_PAYMENT_BILLING_DTLS = "paymentBillingDtls";
	String BLOCK_CUSTOMER_ALERT_EMAIL_VM = "blockCustomerAlertEmail.vm";
	String BLOCKED_DATE = "blockedDate";
	String REGIONAL_OPERATOR = "RO";
	String REGIONAL_CORP = "RC";
	String CUSTOMER_BLOCKED_INTIMATION = "Intimation for customer blocked";
	String EFFECTIVE_FROM = "effectiveFrom";
	String EFFECTIVE_TO = "effectiveTo";
	String CREATED_DATE = "createdDate";
	String CONTRACT_CREATED_ALERT_EMAIL_VM = "contractCreatedAlertEmail.vm";
	String CONTRACT_CREATED_INTIMATION = "Intimation for contract creation";
	
	
	String QRY_GET_COD_CHARGES_BY_CONFIGURED_FOR_AND_RATE_CUSTOMER_CATEGORY = "getCodChargesByConfiguredForAndRateCustomerCategory";
	
	//COD configured for
	String COD_CONFIGURED_FOR_NON_PRIORITY = "NP";
	String COD_CONFIGURED_FOR_PRIORITY = "P";
	String RATE_CALCULATED_FOR = "rateCalculatedFor";
	String CALCULATION_TYPE_PKG = "PKG";
	String PRIORITY_IND = "priorityInd";
	
	String BA_RATE_HEADER_DO = "baRateConfigHeaderDO";
	String PRIORITY_INDICATOR = "priorityIndicator";
	String QRY_GET_BA_COD_CHARGES_CONFIG_DETAILS = "getBACODChargesConfigDetails";
	String QRY_GET_BA_RTO_CHARGES_BY_HEADER_ID = "getRTOChargesByHeader";
	String ORIGIN_CITY = "originCity";
	String WEIGHT_SLAB_CATEGORY_PK = "PK";
	String QRY_GET_INSURED_BY_CODE = "getRiskSurchargeInsuredByCodeForRateCalculation";
	
	String PARAM_UPDATED_BY = "updatedBy";
	String PARAM_UPDATE_DATE = "updateDate";
	
	String PARAM_RATE_PRODUCT_CATEGORY_ID = "paramRateProductCategoryId";
	String QRY_GET_RATE_PRODUCT_DETAILS_FROM_RATE_PRODUCT_ID = "getRateProductDetailsFromRateProductId";
}
