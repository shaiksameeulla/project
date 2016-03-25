-- clear consignment consolidation tables
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE ff_f_billing_stock_issue;
TRUNCATE TABLE ff_f_billing_consignment_rate_extension;
TRUNCATE TABLE ff_f_billing_consignment_rate;
TRUNCATE TABLE ff_f_billing_consignment;
TRUNCATE TABLE ff_f_billing_consignment_summary;
TRUNCATE TABLE ff_f_bill;
SET FOREIGN_KEY_CHECKS = 1;