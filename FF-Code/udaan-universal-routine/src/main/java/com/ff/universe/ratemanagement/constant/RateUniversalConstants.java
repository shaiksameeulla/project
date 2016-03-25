package com.ff.universe.ratemanagement.constant;

public interface RateUniversalConstants {

	String RATE_INDUSTRY_CATEGORY_LIST = "rateIndustryCategoryList";
	String RATE_PRODUCT_CATEGORY_LIST = "rateProductCategoryList";
	String RATE_CUSTOMER_CATEGORY_LIST = "rateCustomerCategoryList";
	String RATE_VOB_SLABS_LIST = "rateVobSlabsList";
	String RATE_WT_SLABS_LIST = "rateWtSlabsList";
	String RATE_SECTORS_LIST = "rateSectorsList";
	String RATE_BENCH_MARK_TO = "rateBenchMarkTO";
	String RATE_CUST_PROD_CAT_MAP_LIST = "rateCustProdCatMapList";
	String RATE_MIN_CHRG_WT_LIST = "rateMinChrgWtList";
	String RATE_BENCH_MARK_CUST_CAT_CODE = "CRDT";
	String RATE_BENCH_MARK_IND_CAT_CODE = "GNRL";
	String RATE_BENCH_MARK_PROD_CAT_CODE = "CO";
	String RATE_BENCH_MARK_PROD_CAT_TYPE = "N";
	String RATE_BENCH_MARK_PROD_CAT_TYPE_N = "N";
	String RATE_BENCH_MARK_PROD_CAT_TYPE_E = "E";
	String QRY_GET_RATE_VOB_SLAB_LIST_BY_EFFECTIVE_DATE = "getVobSlabsByDate";
	String QRY_GET_RATE_WEIGHT_SLAB_LIST_BY_EFFECTIVE_DATE = "getWeightSlabsByDate";
	String PARAM_EFFECTIVE_FROM = "effectiveFrom";
	String PARAM_EFFECTIVE_TO = "effectiveTo";
	String PARAM_RATE_CUST_CAT_CODE = "rateCustomerCategoryCode";
	String PARAM_RATE_PROD_CAT_TYPE = "rateProductCategoryType";
	String QRY_GET_RATE_MIN_CHRG_WEIGHT = "getRateMinChrgWts";
	String QRY_GET_RATE_SECTORS = "getRateSectors";
	String QRY_GET_RATE_CONFIG_SECTORS = "getRateConfigSectors";
	String QRY_GET_RATE_INDUSTRY_TYPE_BY_CODE = "getRateIndustryTypeByCode";
	String QRY_GET_RATE_CUST_CAT_BY_CUST_GROUP_CODE = "getRateCustCatByCustGroupCode";
	String CUST_GROUP = "customerGroup";
	String RATE_INDUSTRY_TYPE_CODE = "rateIndustryTypeCode";
	String QRY_GET_RATE_CUST_CATEGORY_BY_CODE = "getCustCategoryByCode";
	String CUST_CATEGORY_CODE = "rateCustomerCategoryCode";
	String CUST_TYPE_CODE = "customerTypeCode";
	String QRY_GET_CUST_GROUP_BY_CODE = "getCustomerGroupByCode";
	String CUST_GROUP_CODE = "customerGroupCode";
	String QRY_GET_BILLING_CYCLE_BY_STD_CODE = "getBillingCycleBySTDCode";
	String STD_TYPE_CODE = "stdTypeCode";
	String PARAM_CUSTOMER_ID = "customerId";
	String QRY_GET_SHIPPED_TO_CODES_BY_CUSTOMER_ID = "getShippedToCodesByCustomerId";
	String RATE_BENCH_MARK = "rateBenchMark";
	String RATE_QUOTATION = "rateQuotation";
	String YES = "Y";
	String PARAM_CUSTOMER_IDS = "customerIds";
	String QRY_GET_RATE_CONTRACTS_BY_CUSTOMER_IDS = "getRateContractsByCustomerIds";
	String QRY_GET_CUST_TYPE_BY_CODE = "getCustTypeByCode";
	String QRY_GET_BA_RATE_SECTORS = "getRateSectorListForBARateConfiguration";
	String QRY_GET_RATE_WEIGHT_SLAB_LIST_BY_EFFECTIVE_DATE_FOR_COURIER = "getRateWeightSlabsListgetRateWeightSlabsList";
	String QRY_GET_RATE_SECTORS_LIST = "getRateSectorList";
	String QRY_GET_CUSTOMERS_BY_PICKUP_DELIVERY_LOCATION = "getCustomersByPickupDeliveryLocation";
	String PARAM_OFFICE_ID = "officeId";

	// Duplicate Constants (copied from RateCommonConstants)
	String QRY_GET_TAX_COMPONENTS = "getTaxComponents";
	String STATE_ID = "state";
	String CURRENT_DATE = "currentDate";
	String TAX_GROUP_SEC = "SEC";
	String TAX_GROUP_SSU = "SSU";
	String TAX_GROUP = "taxGroup";
	String QRY_GET_TAX_COMPONENTS_FOR_CONFIGURATION = "getTaxComponentsForRateConfiguration";

	// Basic Tax Components For PAN INDIA
	String SERVICE_TAX_CODE = "SRVTX";
	String EDU_CESS_CODE = "EDUCS";
	String HIGHER_EDU_CES_CODE = "HEDCS";

	// Basic Tax Components For J&K
	String STATE_TAX_CODE = "STTAX";
	String SURCHARGE_ON_STATE_TAX_CODE = "SCSTX";

	// Contract interface Changes ---

	String QRY_GET_PICKUP_CONTRACT_DTLS = "getOffAndContractID";
	String QRY_GET_CONTRACT_PAY_BILLING_LOCATION_DTLS = "getContractPayBillocationDtlsByLocationId";
	String QRY_GET_PICKUP_DLV_LOCATION_BY_CONTRACT_ID = "getPickupDlvLocationByContractId";
	String QRY_GET_PICKUP_CONTRACT_DTLS_BY_OFC_ID = "getPickupctrctDtlsByOfcId";
	String QRY_GET_PICKUP_CONTRACT_WRAPPERDO_BY_OFC_ID = "geBusinessNameAndCustCodeByOfcId";
	String RATE_UNIVERSAL_SERVICE = "rateUniversalDAO";
	String BUSINESS_UNIVERSAL_SERVICE = "businessUniversalService";

	String PICKUP_DELIVERY_LOCATION = "pickupDlvLocId";
	String PARAM_CONTRACT_ID = "contractId";

	/* Rate customer category */
	String RATE_CUST_CAT_CRDT = "CRDT";
	String RATE_CUST_CAT_CASH = "CASH";
	String RATE_CUST_CAT_BA = "BA";
	String RATE_CUST_CAT_FR = "FR";
	String RATE_CUST_CAT_ACC = "ACC";

	/* Rate Type */
	String RATE_TYPE_CC = "CC";
	String RATE_TYPE_CH = "CH";
	String RATE_TYPE_BA = "BA";
	String QRY_GET_IND_CAT_BY_CODE = "getIndustryCategoryByCode";
	String IND_CAT_CODE = "indCatCode";
	
	String QRY_GET_WEIGHT_SLAB_BY_WT_SLAB_CATE = "getWeightSlabByWtSlabCate";
	String QRY_PARAM_WT_SLAB_CAT = "weightSlabCategory";
	String QRY_PARAM_END_WT = "endWeight";
	String WT_SLAB_CAT_MC = "MC";
	String WT_SLAB_CAT_PK = "PK";
	String QRY_GET_WEIGHT_SLAB_BY_WT_SLAB_CATE_AND_END_WT = "getWeightSlabByWtSlabCateAndEndWt";
}
