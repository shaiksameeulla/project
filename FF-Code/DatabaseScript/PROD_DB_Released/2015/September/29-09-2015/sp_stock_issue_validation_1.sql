DROP PROCEDURE IF EXISTS sp_stock_issue_validation_1;
CREATE PROCEDURE `sp_stock_issue_validation_1`(
   IN start_serail_no       INT,
   IN end_serial_no         INT,
   IN office_product_Code   char(20),
   IN itemId                INT,
   IN partyTypeId           INT)
BEGIN
      DECLARE MSG   TEXT DEFAULT NULL;

     loop_label:
      LOOP
         -- Start of  loop
         IF (start_serail_no <= end_serial_no)
         THEN
            SET MSG =
                   sp_get_latest_stock_by_leaf_for_issue_4(
                      start_serail_no,
                      office_product_Code,
                      itemId,
                      partyTypeId);

            IF (MSG IS NOT NULL OR MSG NOT LIKE '')
            THEN
               LEAVE loop_label;
            END IF;
         ELSE
            LEAVE loop_label;
         END IF;

         -- For Loop incrementer
         SET start_serail_no = start_serail_no + 1;
         -- END of while loop
         ITERATE loop_label;
      END LOOP loop_label;

      SELECT MSG;
   END;

