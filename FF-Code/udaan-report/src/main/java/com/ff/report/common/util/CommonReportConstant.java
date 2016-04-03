package com.ff.report.common.util;

public interface CommonReportConstant {
	String COMMON_REPORT_SERVICE = "commonReportService";
	String CUSTOMER_REPORT_SERVICE = "customerReportService";
	String PRODUCT_TO = "productTo";
	String REGION_TO = "regionTo";
	String SUCCESS_FORWARD = "success";
	String LEAD_LEAD_SOURCE = "LEAD_LEAD_SOURCE";
	String LEAD_NUMBER_IS_NOT_VALIED = "LM018";
	String SALES_PERSON_TO = "salesPersonTO";
	String BUSINESS_TYPE_TO = "businessTypeTO";
	String IS_AUTHORISED = "isAuthorised";
	String REPORT_TO = "reportTO";
	String WRAPPER_REPORT_TO = "wrapperReportTO";
	String VIEW_DSR_REPORT = "viewDSR";
	String VIEW_DSR = "prepareDsr";
	String PRODUCTS = "products";
	String CONSG_TYPE = "consignmentType";
	String CATEGORY = "category";
	String LOAD = "load";
	String OFFICE_TO = "officeTo";

	String REGION_SALES_QUERY = "select concat(MONTHNAME(booking.BOOKING_DATE),'-',YEAR(booking.BOOKING_DATE))as month,"
			+ "  (select count(book.BOOKING_ID) from ff_f_booking book left join ff_f_consignment consg on book.CONSG_NUMBER = consg.CONSG_NO where book.booking_type not in (6)  and book.booking_off = booking.booking_off and (if(:product is null,consg.PRODUCT not in (0) ,consg.PRODUCT in(:product))))as totalPickup,"
			+ "  sum((case when booking.booking_type  NOT IN(6)  then consignment_rate.TOTAL_WITHOUT_TAX end)) as totalWithoutTax,"
			+ "  ((sum((case when booking.booking_type  NOT IN(6)  then consignment_rate.TOTAL_WITHOUT_TAX end))* 100) /"
			+ "  (select count(book.BOOKING_ID) from ff_f_booking book left join ff_f_consignment consg on book.CONSG_NUMBER = consg.CONSG_NO where book.booking_type not in (6)  and book.booking_off = booking.booking_off and (if(:product is null,consg.PRODUCT not in (0) ,consg.PRODUCT in(:product))))) as pickupPercent,"
			+ "  sum((case when booking.booking_type  NOT IN(6)  then consignment_rate.TOTAL_WITHOUT_TAX end)) as revenueWithoutTax,"
			+ "  ((sum((case when booking.booking_type  NOT IN(6)  then consignment_rate.TOTAL_WITHOUT_TAX end))* 100) /"
			+ "  (select count(book.BOOKING_ID) from ff_f_booking book left join ff_f_consignment consg on book.CONSG_NUMBER = consg.CONSG_NO where book.booking_type not in (6)  and book.booking_off = booking.booking_off and (if(:product is null,consg.PRODUCT not in (0) ,consg.PRODUCT in(:product))))) as revenuePercent"
			+ "  from ff_f_booking booking"
			+ "  left join ff_f_consignment consignment on booking.CONSG_NUMBER = consignment.CONSG_NO"
			+ "  left join ff_f_consignment_rate consignment_rate on consignment.CONSG_ID = consignment_rate.CONSIGNMENT_ID"
			+ "  left join ff_d_office office on booking.BOOKING_OFF = office.OFFICE_ID"
			+ "  left join ff_d_city city on office.CITY_ID = city.CITY_ID"
			+ "  left join ff_d_region region on office.MAPPED_TO_REGION = region.REGION_ID"
			+ "  where booking.BOOKING_DATE between  str_to_date(:fromMonth,'%m/%Y') and str_to_date(:toMonth,'%m/%Y')"
			+ "  and  booking.BOOKING_OFF =:branch"
			+ "  AND   if(:product is null, consignment.PRODUCT not in( 0)       ,consignment.PRODUCT in(:product))"
			+ "   group by YEAR(booking.BOOKING_DATE),MONTHNAME(booking.BOOKING_DATE) desc";

	String SLAB_QUERY = "select distinct slab.VOB_SLAB_ID as vobSlabId, slab.LOWER_LIMIT_LABEL as lowerLimitLabel, slab.UPPPER_LIMIT_LABEL as upperLimitLabel "
			+ "from ff_d_rate_product_category rpc "
			+ "left join ff_f_rate_quotation_product_category_header rqpch  on rpc.RATE_PRODUCT_CATEGORY_ID = rqpch.RATE_PRODUCT_CATEGORY "
			+ "left join ff_d_vob_slab slab on slab.VOB_SLAB_ID = rqpch.VOB_SLAB "
			+ "where rpc.RATE_PRODUCT_CATEGORY_ID = :product "
			+ "order by slab.LOWER_LIMIT";
	
	
	String SALES_PERSON_QUERY="select distinct ff_dim_customer.SALES_PERSON as firstName from ff_dim_customer where ff_dim_customer.SALES_OFFICE IS NOT NULL order by ff_dim_customer.SALES_PERSON";
	
	
	String SALES_PERSON_QUERY_FOR_PROSPECTS="SELECT fde.employee_id AS employeeId,"
			+ "\r\n"
			+ "       fde.emp_code AS empCode,"
			+ "\r\n"
			+ "       fde.first_name AS firstName,"
			+ "\r\n"
			+ "       fde.last_name AS lastName,"
			+ "\r\n"
			+ "       city.REGION AS regionId,"
			+ "\r\n"
			+ "       city.CITY_ID AS cityId,"
			+ "\r\n"
			+ "       fde.office AS officeId"
			+ "\r\n"
			+ "  FROM ff_d_employee fde"
			+ "\r\n"
			+ "       JOIN ff_d_city fdc ON fde.city = fdc.city_id"
			+ "\r\n"
			+ "       JOIN ff_d_department dept ON dept.DEPARTMENT_ID = fde.DEPARTMENT"
			+ "\r\n"
			+ "       JOIN ff_d_city city ON city.CITY_ID = fde.CITY"
			+ "\r\n"
			+ "       AND city.REGION = :regionId"
			+ "\r\n"
			+ "       AND CASE WHEN :cityId = 0 THEN 0 = 0 ELSE city.CITY_ID = :cityId END"
			+ "\r\n"
			+ "       AND CASE WHEN :officeId = 0 THEN 0 = 0 ELSE office = :officeId END";
	
	
	
	String SALES_PERSON_QUERY_FOR_CLIENTGAINED = "SELECT fde.employee_id AS employeeId,"
			+ "\r\n"
			+ "       fde.emp_code AS empCode,"
			+ "\r\n"
			+ "       fde.first_name AS firstName,"
			+ "\r\n"
			+ "       fde.last_name AS lastName,"
			+ "\r\n"
			+ "       city.REGION AS regionId,"
			+ "\r\n"
			+ "       city.CITY_ID AS cityId,"
			+ "\r\n"
			+ "       fde.office AS officeId"
			+ "\r\n"
			+ "  FROM ff_d_employee fde"
			+ "\r\n"
			+ "       JOIN ff_d_city fdc ON fde.city = fdc.city_id"
			+ "\r\n"
			+ "       JOIN ff_d_department dept ON dept.DEPARTMENT_ID = fde.DEPARTMENT"
			+ "\r\n"
			+ "       JOIN ff_d_city city ON city.CITY_ID = fde.CITY"
			+ "\r\n"
			+ " WHERE     dept.department_code = 'SLS'"
			+ "\r\n"
			+ "       AND city.REGION = :regionId"
			+ "\r\n"
			+ "       AND CASE WHEN :cityId = 0 THEN 0 = 0 ELSE city.CITY_ID = :cityId END"
			+ "\r\n"
			+ "       AND CASE WHEN :officeId = 0 THEN 0 = 0 ELSE office = :officeId END";


	String GET_ALL_OVERHOLIDAYS = " select "
			+ "  count(CASE"
			+ "  WHEN holiday.REGION_ID is null"  // and holiday.STATE_ID  is null and holiday.CITY_ID is null and holiday.BRANCH_ID is null " 
			+ "then"
			+ "  1"
			+ "  END) as alloverHolyday"
			+ "  from ff_d_holiday holiday where DATE_FORMAT(holiday.HOLIDAY_DATE,'%m/%Y')=:monthPeriod AND holiday.STATUS='A'";

	String GET_REGION_HOLIDAY = "  select"
			+ "  count(CASE"
			+ "  WHEN holiday.REGION_ID is not null"  //and holiday.STATE_ID  is null and holiday.CITY_ID is null and holiday.BRANCH_ID is null 
			+ "  then 1"
			+ "  END) as regionHolyday"
			+ "  from ff_d_holiday holiday where DATE_FORMAT(holiday.HOLIDAY_DATE,'%m/%Y')=:monthPeriod AND holiday.REGION_ID=:regionId  AND holiday.STATUS='A'";

	String SECTOR_TO = "sectorTO";
	String FUEL_SURCHARGE = "fuelSurcharge";
	String MONTHS_LIST = "monthsList";

	String MONTH_YEAR = "select DISTINCT "
			+ "  concat(substring(month,1,3),'-',year)   "
			+ " from ff_dim_date   where date <= now() ";

	String GET_SORTING_ORDER = "SORTING ORDER";
	String GET_PRODUCT = "PRODUCT LCCOD";
	String GET_TYPE = "TYPE LCCOD";
	String GET_SUMMARY_OPTION = "SUMMARY OPTION";
	String GET_SORTING = "SORTING LCCOD";
	String NO_DATA_FOUND = "lc001";
	String QRY_GET_Type_Name = "getTypeName";
	String NO_CUSTOMER_FOUND = "lc002";

	String QRY_CUSTOMERSBY_REGION_PRODUCT = "SELECT"+"\r\n"+
					"  distinct fdc.CUSTOMER_ID as customerId,fdc.BUSINESS_NAME as customerName,fdc.CUSTOMER_CODE as custCode"+"\r\n"+
					/*" ,fdc.*"+"\r\n"+*/
					"FROM"+"\r\n"+
					"  ff_d_customer fdc"+"\r\n"+
					"  JOIN ff_d_customer_type fdct ON fdct.CUSTOMER_TYPE_ID = fdc.CUSTOMER_TYPE"+"\r\n"+
					"  LEFT JOIN ff_d_office fdo ON fdc.SALES_OFFICE = fdo.OFFICE_ID"+"\r\n"+
					"WHERE"+"\r\n"+
					"  CASE"+"\r\n"+
					"    WHEN :prodSeries = 'L'"+"\r\n"+
					"    THEN"+"\r\n"+
					"      fdct.CUSTOMER_TYPE_CODE = 'CD'"+"\r\n"+
					"    WHEN :prodSeries = 'D'"+"\r\n"+
					"    THEN"+"\r\n"+
					"      fdct.CUSTOMER_TYPE_CODE = 'LC'"+"\r\n"+
					"    WHEN :prodSeries = 'ALL'"+"\r\n"+
					"    THEN"+"\r\n"+
					"      fdct.CUSTOMER_TYPE_CODE IN ('CD'"+"\r\n"+
					"                                 ,'LC')"+"\r\n"+
					"  END AND"+"\r\n"+
					"  (fdo.MAPPED_TO_REGION IS NULL OR fdo.MAPPED_TO_REGION IN (:regionId))";
			
			/*" SELECT"
			+ "  distinct fdc.CUSTOMER_ID as customerId,fdc.BUSINESS_NAME as customerName,fdc.CUSTOMER_CODE as custCode"
			+ "  FROM"
			+ "  ff_d_customer fdc"
			+ "  JOIN ff_d_rate_customer_category fdrcc"
			+ "  ON fdc.CUSTOMER_CATEGORY = fdrcc.RATE_CUSTOMER_CATEGORY_ID"
			+ "  JOIN ff_d_rate_customer_product_cat_map fdrcpcm"
			+ "  ON fdrcpcm.RATE_CUSTOMER_CATEGORY = fdrcc.RATE_CUSTOMER_CATEGORY_ID"
			+ "  JOIN ff_d_rate_product_category fdrpc"
			+ "  ON fdrcpcm.RATE_PRODUCT_CATEGORY = fdrpc.RATE_PRODUCT_CATEGORY_ID"
			+ "  JOIN ff_d_rate_prod_prod_cat_map fdrppcm"
			+ "  ON fdrppcm.RATE_PRODUCT_CATEGORY = fdrpc.RATE_PRODUCT_CATEGORY_ID"
			+ "  JOIN ff_d_product fdp ON (fdp.PRODUCT_ID = fdrppcm.PRODUCT and fdp.CONSG_SERIES in (:prodSeries))"
			+ "  where fdc.OFFICE_MAPPED_TO in (select off.OFFICE_ID from ff_d_office off where off.MAPPED_TO_REGION in (:regionId))";*/

	String LCCOD_PRE_ALERT = "lcCodReportPreAlert";
	String LCCOD_PARTY_WISE = "lcCodReportOutPartWise";
	String LCCOD_OUT_SUMMARY = "lcCodReportOutSummary";
	String DAILY_SALES_TYPE = "DSR Report";
	String QRY_GET_CATEGORY = "select distinct CATEGORY_DESC as categoryDesc,CATEGORY_CODE as categoryCode from ff_d_category_for_reports";
	String CUSTOMER_QUALITY = "Customer Wise Quality Rep";
	String QRY_GET_CUSTOMER_BY_OFFICE_AND_RATE_PRODUCT = "getCustomerByBranchAndRateProductCategory";
	String QRY_PARAM_OFFICE_ID = "officeId";
	String QRY_PARAM_RATE_PRODUCT_CATEGORY = "rateProductCategory";
	String QRY_GET_CUSTOMER_BY_STATION_AND_PRODUCT = "getCustomerByStationAndProduct";
	String QRY_GET_CUSTOMER_BY_STATION = "getCustomerByStation";
	String QRY_GET_PRODUCTS_BY_CUSTOMERS = "getProductByCustomers";
	String QRY_GET_PRODUCTS_FOR_ALL_CUSTOMERS = "getProductForAllCustomers";
	String GEOGRAPHY_COMMON_SERVICE = "geographyCommonService";
	String GLOBAL_UNIVERSAL_SERVICE = "globalUniversalService";
	String FUEL_TO = "fuelTO";
	String QRY_GET_CUSTOMERS_BY_CONTRACT_BRANCHES = "getCustomersByContractBranches";
	
	String GET_CUSTOMER_FOR_CONSIGNMENT_REPORT = 
			"SELECT finalResult.CUSTOMER_ID as customerId,"+"\r\n"+
			"       finalResult.CUSTOMER_CODE as customerCode,"+"\r\n"+
			"       finalResult.BUSINESS_NAME as businessName"+"\r\n"+
			"  FROM (/* contracted customers */"+"\r\n"+
			"        SELECT fdc.*"+"\r\n"+
			"          FROM ff_d_office fdo"+"\r\n"+
			"               JOIN ff_d_pickup_delivery_contract fdpdc"+"\r\n"+
			"                  ON fdo.OFFICE_ID = fdpdc.OFFICE_ID"+"\r\n"+
			"               JOIN ff_d_pickup_delivery_location fdpdl"+"\r\n"+
			"                  ON fdpdc.CONTRACT_ID = fdpdl.CONTRACT_ID"+"\r\n"+
			"               JOIN ff_d_contract_payment_billing_location fdcpbl"+"\r\n"+
			"                  ON fdcpbl.PICKUP_DELIVERY_LOCATION = fdpdl.LOCATION_ID"+"\r\n"+
			"               JOIN ff_d_rate_contract fdrc"+"\r\n"+
			"                  ON fdrc.RATE_CONTRACT_ID = fdcpbl.RATE_CONTRACT"+"\r\n"+
			"               JOIN ff_d_customer fdc ON fdc.CUSTOMER_ID = fdpdc.CUSTOMER_ID"+"\r\n"+
			"               JOIN ff_d_customer_type fdct"+"\r\n"+
			"                  ON fdct.CUSTOMER_TYPE_ID = fdc.CUSTOMER_TYPE"+"\r\n"+
			"         WHERE     fdo.OFFICE_ID IN (:officeIds)"+"\r\n"+
			"               AND fdct.CUSTOMER_TYPE_CODE IN ('CR',"+"\r\n"+
			"                                               'CC',"+"\r\n"+
			"                                               'LC',"+"\r\n"+
			"                                               'CD',"+"\r\n"+
			"                                               'GV',"+"\r\n"+
			"                                               'FR')"+"\r\n"+
			"               AND fdc.CUR_STATUS = 'A'"+"\r\n"+
			"        GROUP BY fdo.OFFICE_ID, fdpdc.CUSTOMER_ID, fdcpbl.SHIPPED_TO_CODE"+"\r\n"+
			"        UNION"+"\r\n"+
			"        /* non contracted customers AC */"+"\r\n"+
			"        SELECT fdc.*"+"\r\n"+
			"          FROM ff_d_office fdo"+"\r\n"+
			"               JOIN ff_d_customer fdc ON fdc.OFFICE_MAPPED_TO = fdo.OFFICE_ID"+"\r\n"+
			"               JOIN ff_d_customer_type fdct"+"\r\n"+
			"                  ON fdct.CUSTOMER_TYPE_ID = fdc.CUSTOMER_TYPE"+"\r\n"+
			"               JOIN ff_d_product_customer_type_map fdpctm1"+"\r\n"+
			"                  ON fdpctm1.CUSTOMER_TYPE_ID = fdct.CUSTOMER_TYPE_ID"+"\r\n"+
			"         WHERE     fdo.OFFICE_ID IN (:officeIds)"+"\r\n"+
			"               AND fdct.CUSTOMER_TYPE_CODE IN ('AC')"+"\r\n"+
			"               AND fdc.CUR_STATUS = 'A'"+"\r\n"+
			"        UNION"+"\r\n"+
			"        /* non contracted customers BA, BV */"+"\r\n"+
			"        SELECT fdc.*"+"\r\n"+
			"          FROM ff_d_office fdo"+"\r\n"+
			"               JOIN ff_d_customer fdc ON fdc.OFFICE_MAPPED_TO = fdo.OFFICE_ID"+"\r\n"+
			"               JOIN ff_d_customer_type fdct"+"\r\n"+
			"                  ON fdct.CUSTOMER_TYPE_ID = fdc.CUSTOMER_TYPE"+"\r\n"+
			"               JOIN ff_d_city fdcity ON fdcity.CITY_ID = fdo.CITY_ID"+"\r\n"+
			"         WHERE     fdcity.CITY_ID IN (:cityId)"+"\r\n"+
			"               AND fdct.CUSTOMER_TYPE_CODE IN ('BA', 'BV')"+"\r\n"+
			"               AND fdc.CUR_STATUS = 'A'"+"\r\n"+
			"        UNION"+"\r\n"+
			"        /* reverse logistics customer */"+"\r\n"+
			"        SELECT fdc.*"+"\r\n"+
			"          FROM ff_d_customer fdc"+"\r\n"+
			"               JOIN ff_d_customer_type fdct"+"\r\n"+
			"                  ON fdct.CUSTOMER_TYPE_ID = fdc.CUSTOMER_TYPE"+"\r\n"+
			"         WHERE fdct.CUSTOMER_TYPE_CODE = 'RL' AND fdc.CUR_STATUS = 'A')"+"\r\n"+
			"       AS finalResult"+"\r\n"+
			"ORDER BY finalResult.BUSINESS_NAME";

	String REQ_PARAM_CUSTOMER_TO_LIST="customerMapList";
	
	String REQ_PARAM_CUSTOMER="customerMap";
	
	String STOCK_CUSTOMER_DETAILS = "Customer Details";
	String QRY_GET_REPORT_TYPE = "select distinct ID as Id, STD_TYPE_CODE as StdTypeCode, DESCRIPTION as Description from ff_d_std_type where TYPE_NAME = 'BRR DETAIL'" ;
	String QRY_GET_PRIORITY_TYPE = "select distinct ID as Id, STD_TYPE_CODE as StdTypeCode, DESCRIPTION as Description from ff_d_std_type where ID IN (177,176,310)";
	String QRY_GET_CHEQUE_BY_REGION = "select distinct cheque_no as chqNo from ff_f_liability_payment lp where lp.REGION_ID = :regionId and CHEQUE_DATE between :fromDate and :toDate";
	String GET_OFFICES_FOR_CUSTOMER =
			"SELECT "+"\r\n"+
					"finalResult.OFFICE_NAME as officeName,"+"\r\n"+
					"			       finalResult.OFFICE_CODE as officeCode,"+"\r\n"+
					"			       finalResult.OFFICE_ID as officeId"+"\r\n"+
					"			  FROM (/* contracted customers */"+"\r\n"+
					"			        SELECT fdo.OFFICE_NAME,fdo.OFFICE_ID,fdo.OFFICE_CODE"+"\r\n"+
					"			          FROM ff_d_office fdo"+"\r\n"+
					"			               JOIN ff_d_pickup_delivery_contract fdpdc"+"\r\n"+
					"			                  ON fdo.OFFICE_ID = fdpdc.OFFICE_ID"+"\r\n"+
					"			               JOIN ff_d_pickup_delivery_location fdpdl"+"\r\n"+
					"			                  ON fdpdc.CONTRACT_ID = fdpdl.CONTRACT_ID"+"\r\n"+
					"			               JOIN ff_d_contract_payment_billing_location fdcpbl"+"\r\n"+
					"			                  ON fdcpbl.PICKUP_DELIVERY_LOCATION = fdpdl.LOCATION_ID"+"\r\n"+
					"			               JOIN ff_d_rate_contract fdrc"+"\r\n"+
					"			                  ON fdrc.RATE_CONTRACT_ID = fdcpbl.RATE_CONTRACT"+"\r\n"+
					"			               JOIN ff_d_customer fdc ON fdc.CUSTOMER_ID = fdpdc.CUSTOMER_ID"+"\r\n"+
					"			               JOIN ff_d_customer_type fdct"+"\r\n"+
					"			                  ON fdct.CUSTOMER_TYPE_ID = fdc.CUSTOMER_TYPE"+"\r\n"+
					"			         WHERE     "+"\r\n"+
					"               fdc.customer_id = (:userId)"+"\r\n"+
					"			               AND fdct.CUSTOMER_TYPE_CODE IN ('CR',"+"\r\n"+
					"			                                               'CC',"+"\r\n"+
					"			                                               'LC',"+"\r\n"+
					"			                                               'CD',"+"\r\n"+
					"			                                               'GV',"+"\r\n"+
					"			                                               'FR')"+"\r\n"+
					"			               AND fdc.CUR_STATUS = 'A'"+"\r\n"+
					"			        GROUP BY fdo.OFFICE_ID, fdpdc.CUSTOMER_ID, fdcpbl.SHIPPED_TO_CODE"+"\r\n"+
					"			        UNION"+"\r\n"+
					"			        /* non contracted customers AC */"+"\r\n"+
					"			        SELECT fdo.OFFICE_NAME,fdo.OFFICE_ID,fdo.OFFICE_CODE"+"\r\n"+
					"			          FROM ff_d_office fdo"+"\r\n"+
					"			               JOIN ff_d_customer fdc ON fdc.OFFICE_MAPPED_TO = fdo.OFFICE_ID"+"\r\n"+
					"			               JOIN ff_d_customer_type fdct"+"\r\n"+
					"			                  ON fdct.CUSTOMER_TYPE_ID = fdc.CUSTOMER_TYPE"+"\r\n"+
					"			               JOIN ff_d_product_customer_type_map fdpctm1"+"\r\n"+
					"			                  ON fdpctm1.CUSTOMER_TYPE_ID = fdct.CUSTOMER_TYPE_ID"+"\r\n"+
					"			         WHERE    "+"\r\n"+
					"                fdc.customer_id = (:userId)"+"\r\n"+
					"			               AND fdct.CUSTOMER_TYPE_CODE IN ('AC')"+"\r\n"+
					"			               AND fdc.CUR_STATUS = 'A'"+"\r\n"+
					"			        UNION"+"\r\n"+
					"			        /* non contracted customers BA, BV */"+"\r\n"+
					"			        SELECT fdo.OFFICE_NAME,fdo.OFFICE_ID,fdo.OFFICE_CODE"+"\r\n"+
					"			          FROM ff_d_office fdo"+"\r\n"+
					"			               JOIN ff_d_customer fdc ON fdc.OFFICE_MAPPED_TO = fdo.OFFICE_ID"+"\r\n"+
					"			               JOIN ff_d_customer_type fdct"+"\r\n"+
					"			                  ON fdct.CUSTOMER_TYPE_ID = fdc.CUSTOMER_TYPE"+"\r\n"+
					"			               JOIN ff_d_city fdcity ON fdcity.CITY_ID = fdo.CITY_ID"+"\r\n"+
					"			         WHERE     "+"\r\n"+
					"                fdcity.city_id = (:cityId)"+"\r\n"+
					"			               AND fdct.CUSTOMER_TYPE_CODE IN ('BA', 'BV')"+"\r\n"+
					"			               AND fdc.CUR_STATUS = 'A'"+"\r\n"+
					"			        UNION"+"\r\n"+
					"			        /* reverse logistics customer */"+"\r\n"+
					"			        SELECT fdo.OFFICE_NAME,fdo.OFFICE_ID,fdo.OFFICE_CODE"+"\r\n"+
					"			          FROM  ff_d_customer fdc"+"\r\n"+
					"			               JOIN ff_d_customer_type fdct"+"\r\n"+
					"			                  ON fdct.CUSTOMER_TYPE_ID = fdc.CUSTOMER_TYPE"+"\r\n"+
					"                        JOIN ff_d_office fdo ON fdc.OFFICE_MAPPED_TO = fdo.OFFICE_ID"+"\r\n"+
					"			         WHERE fdct.CUSTOMER_TYPE_CODE = 'RL' AND fdc.CUR_STATUS = 'A')"+"\r\n"+
					"			       AS finalResult"+"\r\n"+
					"			ORDER BY finalResult.OFFICE_NAME";






}
