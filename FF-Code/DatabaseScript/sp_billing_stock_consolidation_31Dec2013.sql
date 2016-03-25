DROP PROCEDURE IF EXISTS udaan_gold.sp_billing_stock_consolidation;
CREATE PROCEDURE udaan_gold.`sp_billing_stock_consolidation`()
BEGIN
  -- --------------------------------------------
  -- Created by: Kaustubh A. Gajare (37434)
  -- --------------------------------------------
  
  -- DECLARE consolidation_window int;
  DECLARE _step varchar(150);
  DECLARE stock_summary_rowcount int(11) DEFAULT 0;
  DECLARE stock_issue_rowcount int(11) DEFAULT 0;
  DECLARE update_rowcount int(11) DEFAULT 0;

  DECLARE no_such_table CONDITION FOR 1051;
  DECLARE no_data_found CONDITION FOR SQLSTATE '45000';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  BEGIN
    SHOW ERRORS;
    SELECT _step;
    rollback;
  END;
-- 
--   DECLARE EXIT HANDLER FOR no_such_table
--   BEGIN
--     SHOW ERRORS;
--     SELECT _step;
--     -- rollback;
--   END;

  -- -------------------------------------
  -- CONSOLIDATE STOCK ISSUES TO BA
  -- -------------------------------------
  START TRANSACTION;

    -- -------------------------------------------
    -- GENERATE STOCK SUMMARY
    -- -------------------------------------------
    SET _step = "GENERATE SUMMARY FOR STOCK ISSUED TO CUSTOMER TYPE BA";
    -- SO SUMMARY
    INSERT INTO ff_f_billing_consignment_summary(
    `TRANSACTION_IDENTIFIER`,
    `SALES_ORDER`,
    `BOOKING_DATE`,
    `PRODUCT_CODE`,
    `PICKUP_OFFICE_ID`,
    `INVOICE`,
    `CREATED_BY`,
    `CREATED_DATE`,
    `UPDATE_BY`,
    `UPDATE_DATE`,
    `NO_OF_PICKUPS`,
    `TRANSFER_STATUS`,
    `SAP_TIMESTAMP`,
    `DISTRIBUTION_CHANNEL`,
    `SHIP_TO_CODE`,
    `SUMMARY_TYPE`,
    `UNIT_OF_MEASUREMENT`,
    `SUMMARY_CATEGORY`,
    `VERSION`,
    `CUSTOMER`
    )
    -- GENERATE STOCK SUMMARY FOR COMBINATION OF
    -- ISSUE DATE, ISSUE OFFICE, 'SHIP TO CODE'/'CUSTOMER CODE', PRODUCT TYPE AND UNIT OF MEASUREMENT;
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
      'OTHERS' AS SUMMARY_CATEGORY,
      1 AS VERSION,
      CUSTOMER.CUSTOMER_ID
    FROM -- stock tables 
    ff_f_stock_issue si
      JOIN ff_f_stock_issue_item_dtls siid
        ON siid.STOCK_ISSUE_ID = si.STOCK_ISSUE_ID
      JOIN ff_d_item item
        ON item.ITEM_ID = siid.ITEM_ID
      JOIN ff_d_item_type itemtype
        ON itemtype.ITEM_TYPE_ID = item.ITEM_TYPE_ID
      JOIN ff_f_stock_payment_detls ffspd
        ON siid.STOCK_ISSUE_ID = ffspd.STOCK_ISSUE_ID
      -- customer
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
  
    SELECT cr_debug.get_ROW_COUNT() INTO stock_summary_rowcount;

    IF stock_summary_rowcount <= 0 THEN
      SIGNAL no_data_found
        SET MESSAGE_TEXT='Stock - Problem in execution of procedure, rolling back', MYSQL_ERRNO=219;
      rollback;
    END IF;
    -- ------------------------------------------
    -- ------------------------------------------
    INSERT INTO ff_f_billing_stock_issue(
    `STOCK_ISSUE_ID`,
    `SUMMARY`,
    `QUANTITY`,
    `PRICE`,
    `SERVICE_TAX_AMOUNT`,
    `EDUCATION_CESS_AMOUNT`,
    `HIGHER_EDUCATION_CESS_AMOUNT`,
    `STATE_TAX_AMOUNT`,
    `SURCHARGE_ON_STATE_TAX_AMOUNT`,
    `GRAND_TOTAL_INCLUDING_TAX`,
    `VERSION`
    )
    -- GENERATE STOCK ISSUE DETAILS
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
      FROM -- stock tables
      ff_f_stock_issue si
        JOIN ff_f_stock_issue_item_dtls siid
          ON siid.STOCK_ISSUE_ID = si.STOCK_ISSUE_ID
        JOIN ff_d_item item
          ON item.ITEM_ID = siid.ITEM_ID
        JOIN ff_d_item_type itemtype
          ON itemtype.ITEM_TYPE_ID = item.ITEM_TYPE_ID
        JOIN ff_f_stock_payment_detls ffspd
          ON siid.STOCK_ISSUE_ID = ffspd.STOCK_ISSUE_ID
        -- customer
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
      -- stock summary
      JOIN ff_f_billing_consignment_summary ffbcs
        ON ffbcs.SUMMARY_CATEGORY = 'OTHERS'
        AND (DATE_FORMAT(ffbcs.BOOKING_DATE, '%d/%m/%Y') =
        DATE_FORMAT(RESULT.ISSUE_DATE, '%d/%m/%Y')
        AND ffbcs.PICKUP_OFFICE_ID = RESULT.ISSUE_OFFICE_ID
        AND ffbcs.SHIP_TO_CODE = RESULT.CUSTOMER_CODE
        AND ffbcs.DISTRIBUTION_CHANNEL = '00'
        AND ffbcs.PRODUCT_CODE = RESULT.product_type
        AND ffbcs.UNIT_OF_MEASUREMENT = RESULT.UOM);

    SELECT cr_debug.get_ROW_COUNT() INTO stock_issue_rowcount;

    IF stock_issue_rowcount <= 0 THEN
      SIGNAL no_data_found
        SET MESSAGE_TEXT='Stock - Problem in execution of procedure, rolling back', MYSQL_ERRNO=220;
      rollback;
    END IF;
    -- ------------------------------------------
    -- ------------------------------------------
    UPDATE ff_f_stock_issue ffsi
      JOIN ff_f_billing_stock_issue ffbsi
      ON ffsi.STOCK_ISSUE_ID = ffbsi.STOCK_ISSUE_ID
      SET ffsi.BILLING_STATUS = 'BLD'
      WHERE ffbsi.VERSION = 0;

    SELECT cr_debug.get_ROW_COUNT() INTO update_rowcount;

    IF update_rowcount <= 0 THEN
      SIGNAL no_data_found
        SET MESSAGE_TEXT='Stock - Problem in execution of procedure, rolling back', MYSQL_ERRNO=221;
      rollback;
    END IF;
    -- ------------------------------------------
    -- ------------------------------------------
    UPDATE ff_f_billing_stock_issue ffbsi
      SET ffbsi.VERSION = 1
      WHERE ffbsi.VERSION = 0;

    SELECT cr_debug.get_ROW_COUNT() INTO update_rowcount;

    IF update_rowcount <= 0 then
      SIGNAL no_data_found
        SET MESSAGE_TEXT='Stock - Problem in execution of procedure, rolling back', MYSQL_ERRNO=222;
      rollback;
    ELSEIF
      stock_summary_rowcount > 0 and
            stock_issue_rowcount > 0 and
            update_rowcount > 0 then
      select 'Stock - Execution Completed, committing - 223';
      commit;
    ELSE
      SIGNAL no_data_found
        SET MESSAGE_TEXT='Stock - Problem in execution of procedure, rolling back', MYSQL_ERRNO=224;
      rollback;
    END IF;
    -- ------------------------------------------
END;
