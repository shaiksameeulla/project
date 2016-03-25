
drop PROCEDURE sp_get_latest_stock_by_leaf_for_issue;
CREATE PROCEDURE sp_get_latest_stock_by_leaf_for_issue(
   IN leaf                  INT,
   IN office_product_Code   char(20),
   IN itemId                INT)
   BEGIN
     select * from ( SELECT *
        FROM (SELECT 'SA' AS processName,
                     stockrecei1_.RECEIPT_OFFICE_ID AS stockOfficeId,
                     stockrecei1_.TRANS_MODIFIED_DATE_TIME AS stockDate,
                     stockrecei0_.STOCK_RECEIPT_ITEM_DTLS_ID AS stockDtlsId
                FROM ff_f_stock_receipt_item_dtls stockrecei0_
                     INNER JOIN ff_f_stock_receipt stockrecei1_
                        ON stockrecei0_.STOCK_RECEIPT_ID =
                              stockrecei1_.STOCK_RECEIPT_ID
               WHERE     stockrecei0_.ITEM_ID = itemId
                     AND stockrecei0_.OFFICE_PRODUCT_CODE_IN_SERIES =
                            office_product_Code
                     AND stockrecei0_.TRANS_ACTIVE_STATUS = 'A'
                     AND (leaf BETWEEN stockrecei0_.START_LEAF
                                   AND stockrecei0_.END_LEAF)
              ORDER BY stockrecei0_.TRANS_MODIFIED_DATE_TIME DESC
               LIMIT 1) AS stockReceipt
      UNION ALL
      SELECT *
        FROM (SELECT 'SI' AS processName,
                     stockissue1_.ISSUE_OFFICE_ID AS stockOfficeId,
                     stockissue1_.TRANS_MODIFIED_DATE_TIME AS stockDate,
                     stockissue0_.STOCK_ISSUE_ITEM_DTLS_ID AS stockDtlsId
                FROM ff_f_stock_issue_item_dtls stockissue0_
                     INNER JOIN ff_f_stock_issue stockissue1_
                        ON stockissue0_.STOCK_ISSUE_ID =
                              stockissue1_.STOCK_ISSUE_ID
               WHERE     stockissue0_.ITEM_ID = itemId
                     AND stockissue0_.OFFICE_PRODUCT_CODE_IN_SERIES =
                            office_product_Code
                     AND stockissue0_.TRANS_ACTIVE_STATUS = 'A'
                     AND (leaf BETWEEN stockissue0_.START_LEAF
                                   AND stockissue0_.END_LEAF)
              ORDER BY stockissue0_.TRANS_MODIFIED_DATE_TIME DESC
               LIMIT 1) AS stockIssue
      UNION ALL
      SELECT *
        FROM (SELECT 'ST' AS processName,
                     stockTransfer.TO_BRANCH_ID AS stockOfficeId,
                     stockTransfer.TRANS_MODIFIED_DATE_TIME AS stockDate,
                     stockTransfer.STOCK_TRANSFER_ID AS stockDtlsId
                FROM ff_f_stock_transfer stockTransfer
               WHERE     stockTransfer.ITEM_ID = itemId
                     AND stockTransfer.TRANS_ACTIVE_STATUS = 'A'
                     AND stockTransfer.OFFICE_PRODUCT_CODE_IN_SERIES =
                            office_product_Code
                     AND (leaf BETWEEN stockTransfer.START_LEAF
                                   AND stockTransfer.END_LEAF)
              ORDER BY stockTransfer.TRANS_MODIFIED_DATE_TIME DESC
               LIMIT 1) AS stockTransfer
      UNION ALL
      SELECT *
        FROM (SELECT 'SR' AS processName,
                     stockretur1_.ISSUED_OFFICE_ID AS stockOfficeId,
                     stockretur0_.TRANS_MODIFIED_DATE_TIME AS stockDate,
                     stockretur0_.STOCK_RETURN_ITEM_DTLS_ID AS stockDtlsId
                FROM ff_f_stock_return_item_dtls stockretur0_
                     LEFT OUTER JOIN ff_f_stock_return stockretur1_
                        ON stockretur0_.STOCK_RETURN_ID =
                              stockretur1_.STOCK_RETURN_ID
               WHERE     stockretur0_.ITEM_ID = itemId
                     AND stockretur0_.OFFICE_PRODUCT_CODE_IN_SERIES =
                            office_product_Code
                     AND stockretur0_.TRANS_ACTIVE_STATUS = 'A'
                     AND (leaf BETWEEN stockretur0_.START_LEAF
                                   AND stockretur0_.END_LEAF)
              ORDER BY stockretur0_.TRANS_MODIFIED_DATE_TIME DESC
               LIMIT 1) AS stockReturn ) as stock order  by stock.stockDate desc limit 1;
   END;