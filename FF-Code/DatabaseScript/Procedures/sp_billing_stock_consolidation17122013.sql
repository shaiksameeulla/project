DROP PROCEDURE IF EXISTS sp_billing_stock_consolidation;
CREATE PROCEDURE sp_billing_stock_consolidation()
BEGIN
DECLARE cr_stack_depth INTEGER DEFAULT cr_debug.ENTER_MODULE2('sp_billing_stock_consolidation', 'udaan_gold', 7, 100420);
  
  
  
  
  
  DECLARE _step varchar(150);
  DECLARE stock_summary_rowcount int(11) DEFAULT 0;
  DECLARE stock_issue_rowcount int(11) DEFAULT 0;
  DECLARE update_rowcount int(11) DEFAULT 0;

  DECLARE no_such_table CONDITION FOR 1051;
  DECLARE no_data_found CONDITION FOR SQLSTATE '45000';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  BEGIN
DECLARE cr_stack_depth INTEGER DEFAULT cr_debug.ENTER_MODULE3('sp_billing_stock_consolidation_Handler', 'sp_billing_stock_consolidation', 'udaan_gold', 7, 100420);
    CALL cr_debug.UPDATE_WATCH3('_step', _step, 'varchar(150)', cr_stack_depth);
CALL cr_debug.UPDATE_WATCH3('stock_summary_rowcount', stock_summary_rowcount, 'int(11)', cr_stack_depth);
CALL cr_debug.UPDATE_WATCH3('stock_issue_rowcount', stock_issue_rowcount, 'int(11)', cr_stack_depth);
CALL cr_debug.UPDATE_WATCH3('update_rowcount', update_rowcount, 'int(11)', cr_stack_depth);
CALL cr_debug.TRACE(17, 17, 2, 7, cr_stack_depth);
CALL cr_debug.TRACE(18, 18, 4, 16, cr_stack_depth);
SHOW ERRORS;
    CALL cr_debug.TRACE(19, 19, 4, 17, cr_stack_depth);
SELECT _step;
CALL cr_debug.UPDATE_SYSTEM_CALLS(101);
    CALL cr_debug.TRACE(20, 20, 4, 13, cr_stack_depth);
rollback;
  CALL cr_debug.TRACE(21, 21, 2, 5, cr_stack_depth);
CALL cr_debug.LEAVE_MODULE(cr_stack_depth - 1);
END;








  
  
  

  CALL cr_debug.UPDATE_WATCH3('_step', _step, 'varchar(150)', cr_stack_depth);
CALL cr_debug.UPDATE_WATCH3('stock_summary_rowcount', stock_summary_rowcount, 'int(11)', cr_stack_depth);
CALL cr_debug.UPDATE_WATCH3('stock_issue_rowcount', stock_issue_rowcount, 'int(11)', cr_stack_depth);
CALL cr_debug.UPDATE_WATCH3('update_rowcount', update_rowcount, 'int(11)', cr_stack_depth);
CALL cr_debug.TRACE(2, 2, 0, 5, cr_stack_depth);
CALL cr_debug.TRACE(34, 34, 2, 20, cr_stack_depth);
START TRANSACTION;

    
    
    
    CALL cr_debug.TRACE(39, 39, 4, 72, cr_stack_depth);
SET _step = "GENERATE SUMMARY FOR STOCK ISSUED TO CUSTOMER TYPE BA";
CALL cr_debug.UPDATE_WATCH3('_step', _step, '', cr_stack_depth);
    
    CALL cr_debug.TRACE(41, 107, 4, 22, cr_stack_depth);
INSERT INTO ff_f_billing_consignment_summary(
    TRANSACTION_IDENTIFIER,
    SALES_ORDER,
    BOOKING_DATE,
    PRODUCT_CODE,
    PICKUP_OFFICE_ID,
    INVOICE,
    CREATED_BY,
    CREATED_DATE,
    UPDATE_BY,
    UPDATE_DATE,
    NO_OF_PICKUPS,
    TRANSFER_STATUS,
    SAP_TIMESTAMP,
    DISTRIBUTION_CHANNEL,
    SHIP_TO_CODE,
    SUMMARY_TYPE,
    UNIT_OF_MEASUREMENT,
    SUMMARY_CATEGORY
    )
    
    
    SELECT
      NULL AS TRANSACTION_IDENTIFIER,
      NULL AS SALES_ORDER,
      si.ISSUE_DATE,
      CASE itemtype.ITEM_TYPE_CODE
        WHEN 'ZCON' THEN 'FF-C-Note' ELSE 'FF-Stationary'
      END AS PRODUCT_CODE,
      si.ISSUE_OFFICE_ID,
      NULL AS INVOICE,
      1 AS CREATED_BY,
      CURDATE() AS CREATED_DATE,
      NULL AS UPDATE_BY,
      NULL AS UPDATE_DATE,
      SUM(siid.ISSUED_QUANTITY) AS COUNT_OF_MATERIAL,
      'N' AS TRANSFER_STATUS,
      NULL AS SAP_TIMESTAMP,
      '00' AS DISTRIBUTION_CHANNELS,
      CUSTOMER.CUSTOMER_CODE AS SHIP_TO_CODE,
      'ST' AS SUMMARY_TYPE,
      siid.UOM AS UNIT_OF_MEASUREMENT,
      'OTHERS' AS SUMMARY_CATEGORY
    FROM 
    ff_f_stock_issue si
      JOIN ff_f_stock_issue_item_dtls siid
        ON siid.STOCK_ISSUE_ID = si.STOCK_ISSUE_ID
      JOIN ff_d_item item
        ON item.ITEM_ID = siid.ITEM_ID
      JOIN ff_d_item_type itemtype
        ON itemtype.ITEM_TYPE_ID = item.ITEM_TYPE_ID
      JOIN ff_f_stock_payment_detls ffspd
        ON siid.STOCK_ISSUE_ID = ffspd.STOCK_ISSUE_ID
      
      JOIN ff_d_customer CUSTOMER
        ON CUSTOMER.CUSTOMER_ID = si.ISSUED_TO_BA
    WHERE si.ISSUED_TO_TYPE = 'BA'
    AND si.BILLING_STATUS = 'TBB'
    AND si.ISSUE_DATE IS NOT NULL
    AND si.ISSUE_OFFICE_ID IS NOT NULL
    AND CUSTOMER.CUSTOMER_CODE IS NOT NULL
    AND siid.UOM IS NOT NULL
    GROUP BY si.ISSUE_DATE,
             si.ISSUE_OFFICE_ID,
             CUSTOMER.CUSTOMER_CODE,
             PRODUCT_CODE,
             siid.UOM;
CALL cr_debug.UPDATE_SYSTEM_CALLS(102);

    CALL cr_debug.TRACE(109, 109, 4, 51, cr_stack_depth);
SELECT cr_debug.get_ROW_COUNT() INTO stock_summary_rowcount;
CALL cr_debug.UPDATE_SYSTEM_CALLS(101);
CALL cr_debug.UPDATE_WATCH3('stock_summary_rowcount', stock_summary_rowcount, '', cr_stack_depth);

    CALL cr_debug.TRACE(111, 115, 4, 11, cr_stack_depth);
IF stock_summary_rowcount <= 0 THEN
      CALL cr_debug.TRACE(112, 113, 6, 100, cr_stack_depth);
SIGNAL no_data_found
        SET MESSAGE_TEXT='Stock - Problem in execution of procedure, rolling back', MYSQL_ERRNO=219;
      CALL cr_debug.TRACE(114, 114, 6, 15, cr_stack_depth);
rollback;
    END IF;
    
    
    CALL cr_debug.TRACE(118, 196, 4, 52, cr_stack_depth);
INSERT INTO ff_f_billing_stock_issue(
    STOCK_ISSUE_ID,
    SUMMARY,
    QUANTITY,
    PRICE,
    SERVICE_TAX_AMOUNT,
    EDUCATION_CESS_AMOUNT,
    HIGHER_EDUCATION_CESS_AMOUNT,
    STATE_TAX_AMOUNT,
    SURCHARGE_ON_STATE_TAX_AMOUNT,
    GRAND_TOTAL_INCLUDING_TAX,
    VERSION
    )
    
    SELECT
      STOCK_ISSUE_ID,
      ffbcs.SUMMARY_ID AS SUMMARY,
      QUANTITY,
      RESULT.VALUE_OF_MATERIAL,
      SERVICE_TAX_AMOUNT,
      EDUCATION_CESS_AMOUNT,
      HIGHER_EDUCATION_CESS_AMOUNT,
      STATE_TAX_AMOUNT,
      SURCHARGE_ON_STATE_TAX_AMOUNT,
      GRAND_TOTAL_INCLUDING_TAX,
      0
    FROM (SELECT
        si.STOCK_ISSUE_ID,
        siid.UOM,
        CUSTOMER.CUSTOMER_CODE,
        si.ISSUE_OFFICE_ID,
        si.ISSUE_DATE,
        SUM(siid.ISSUED_QUANTITY) AS QUANTITY,
        SUM(siid.ITEM_PRICE) AS VALUE_OF_MATERIAL,
        SUM(ROUND((siid.ITEM_PRICE * ffspd.SERVICE_TAX) / 100, 2)) AS SERVICE_TAX_AMOUNT,
        SUM(ROUND((((siid.ITEM_PRICE * ffspd.SERVICE_TAX) / 100) * ffspd.EDU_CESS / 100), 2)) AS EDUCATION_CESS_AMOUNT,
        SUM(ROUND(((((siid.ITEM_PRICE * ffspd.SERVICE_TAX) / 100) * 2 / 100) * ffspd.HEDU_CESS / 100), 2)) AS HIGHER_EDUCATION_CESS_AMOUNT,
        SUM(ROUND((siid.ITEM_PRICE * ffspd.STATE_TAX) / 100, 2)) AS STATE_TAX_AMOUNT,
        SUM(ROUND(((siid.ITEM_PRICE * ffspd.STATE_TAX) / 100) * ffspd.SUR_CHRG_ON_ST_TAX, 2)) AS SURCHARGE_ON_STATE_TAX_AMOUNT,
        SUM(siid.ITEM_PRICE +
        ROUND((siid.ITEM_PRICE * ffspd.SERVICE_TAX) / 100, 2) +
        ROUND((((siid.ITEM_PRICE * ffspd.SERVICE_TAX) / 100) * ffspd.EDU_CESS / 100), 2) +
        ROUND(((((siid.ITEM_PRICE * ffspd.SERVICE_TAX) / 100) * 2 / 100) * ffspd.HEDU_CESS / 100), 2) +
        ROUND((siid.ITEM_PRICE * ffspd.STATE_TAX) / 100, 2) +
        ROUND(((siid.ITEM_PRICE * ffspd.STATE_TAX) / 100) * ffspd.SUR_CHRG_ON_ST_TAX, 2)) AS GRAND_TOTAL_INCLUDING_TAX,
        CASE itemtype.ITEM_TYPE_CODE
          WHEN 'ZCON' THEN 'FF-C-Note' ELSE 'FF-Stationary'
        END AS product_type
      FROM 
      ff_f_stock_issue si
        JOIN ff_f_stock_issue_item_dtls siid
          ON siid.STOCK_ISSUE_ID = si.STOCK_ISSUE_ID
        JOIN ff_d_item item
          ON item.ITEM_ID = siid.ITEM_ID
        JOIN ff_d_item_type itemtype
          ON itemtype.ITEM_TYPE_ID = item.ITEM_TYPE_ID
        JOIN ff_f_stock_payment_detls ffspd
          ON siid.STOCK_ISSUE_ID = ffspd.STOCK_ISSUE_ID
        
        JOIN ff_d_customer CUSTOMER
          ON CUSTOMER.CUSTOMER_ID = si.ISSUED_TO_BA
      WHERE si.ISSUED_TO_TYPE = 'BA'
      AND si.BILLING_STATUS = 'TBB'
      AND si.ISSUE_DATE IS NOT NULL
      AND si.ISSUE_OFFICE_ID IS NOT NULL
      AND CUSTOMER.CUSTOMER_CODE IS NOT NULL
      AND siid.UOM IS NOT NULL
      GROUP BY si.STOCK_ISSUE_ID,
               product_type) AS RESULT
      
      JOIN ff_f_billing_consignment_summary ffbcs
        ON ffbcs.SUMMARY_CATEGORY = 'OTHERS'
        AND (DATE_FORMAT(ffbcs.BOOKING_DATE, '%d/%m/%Y') =
        DATE_FORMAT(RESULT.ISSUE_DATE, '%d/%m/%Y')
        AND ffbcs.PICKUP_OFFICE_ID = RESULT.ISSUE_OFFICE_ID
        AND ffbcs.SHIP_TO_CODE = RESULT.CUSTOMER_CODE
        AND ffbcs.DISTRIBUTION_CHANNEL = '00'
        AND ffbcs.PRODUCT_CODE = RESULT.product_type
        AND ffbcs.UNIT_OF_MEASUREMENT = RESULT.UOM);
CALL cr_debug.UPDATE_SYSTEM_CALLS(102);

    CALL cr_debug.TRACE(198, 198, 4, 49, cr_stack_depth);
SELECT cr_debug.get_ROW_COUNT() INTO stock_issue_rowcount;
CALL cr_debug.UPDATE_SYSTEM_CALLS(101);
CALL cr_debug.UPDATE_WATCH3('stock_issue_rowcount', stock_issue_rowcount, '', cr_stack_depth);

    CALL cr_debug.TRACE(200, 204, 4, 11, cr_stack_depth);
IF stock_issue_rowcount <= 0 THEN
      CALL cr_debug.TRACE(201, 202, 6, 100, cr_stack_depth);
SIGNAL no_data_found
        SET MESSAGE_TEXT='Stock - Problem in execution of procedure, rolling back', MYSQL_ERRNO=220;
      CALL cr_debug.TRACE(203, 203, 6, 15, cr_stack_depth);
rollback;
    END IF;
    
    
    CALL cr_debug.TRACE(207, 211, 4, 30, cr_stack_depth);
UPDATE ff_f_stock_issue ffsi
      JOIN ff_f_billing_stock_issue ffbsi
      ON ffsi.STOCK_ISSUE_ID = ffbsi.STOCK_ISSUE_ID
      SET ffsi.BILLING_STATUS = 'BLD'
      WHERE ffbsi.VERSION = 0;
CALL cr_debug.UPDATE_SYSTEM_CALLS(104);

    CALL cr_debug.TRACE(213, 213, 4, 44, cr_stack_depth);
SELECT cr_debug.get_ROW_COUNT() INTO update_rowcount;
CALL cr_debug.UPDATE_SYSTEM_CALLS(101);
CALL cr_debug.UPDATE_WATCH3('update_rowcount', update_rowcount, '', cr_stack_depth);

    CALL cr_debug.TRACE(215, 219, 4, 11, cr_stack_depth);
IF update_rowcount <= 0 THEN
      CALL cr_debug.TRACE(216, 217, 6, 100, cr_stack_depth);
SIGNAL no_data_found
        SET MESSAGE_TEXT='Stock - Problem in execution of procedure, rolling back', MYSQL_ERRNO=221;
      CALL cr_debug.TRACE(218, 218, 6, 15, cr_stack_depth);
rollback;
    END IF;
    
    
    CALL cr_debug.TRACE(222, 224, 4, 30, cr_stack_depth);
UPDATE ff_f_billing_stock_issue ffbsi
      SET ffbsi.VERSION = 1
      WHERE ffbsi.VERSION = 0;
CALL cr_debug.UPDATE_SYSTEM_CALLS(104);

    CALL cr_debug.TRACE(226, 226, 4, 44, cr_stack_depth);
SELECT cr_debug.get_ROW_COUNT() INTO update_rowcount;
CALL cr_debug.UPDATE_SYSTEM_CALLS(101);
CALL cr_debug.UPDATE_WATCH3('update_rowcount', update_rowcount, '', cr_stack_depth);

    CALL cr_debug.TRACE(228, 243, 4, 11, cr_stack_depth);
IF 
      update_rowcount <= 0 then
      CALL cr_debug.TRACE(230, 231, 6, 100, cr_stack_depth);
SIGNAL no_data_found
        SET MESSAGE_TEXT='Stock - Problem in execution of procedure, rolling back', MYSQL_ERRNO=222;
      CALL cr_debug.TRACE(232, 232, 6, 15, cr_stack_depth);
rollback;
    ELSEIF
      stock_summary_rowcount > 0 and
            stock_issue_rowcount > 0 and
            update_rowcount > 0 then
      CALL cr_debug.TRACE(237, 237, 6, 61, cr_stack_depth);
select 'Stock - Execution Completed, committing - 223';
CALL cr_debug.UPDATE_SYSTEM_CALLS(101);
      CALL cr_debug.TRACE(238, 238, 6, 13, cr_stack_depth);
commit;
    ELSE
      CALL cr_debug.TRACE(240, 241, 6, 100, cr_stack_depth);
SIGNAL no_data_found
        SET MESSAGE_TEXT='Stock - Problem in execution of procedure, rolling back', MYSQL_ERRNO=224;
      CALL cr_debug.TRACE(242, 242, 6, 15, cr_stack_depth);
rollback;
    END IF;
    
CALL cr_debug.TRACE(245, 245, 0, 3, cr_stack_depth);
CALL cr_debug.LEAVE_MODULE(cr_stack_depth - 1);
END;
