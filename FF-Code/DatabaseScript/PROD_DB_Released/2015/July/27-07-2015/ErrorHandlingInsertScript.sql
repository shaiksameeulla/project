insert into ff_d_sap_interface_error_config (INTERFACE_NAME, INTERFACE_TYPE, TABLE_NAME, STATUS_COLUMN_NAME, CREATED_DATE)
values('Customer' , 'MASTER', 'com.ff.domain.ratemanagement.masters.SAPCustomerDO', 'sapInbound', '2015-07-24');

insert into ff_d_sap_interface_error_config (INTERFACE_NAME, INTERFACE_TYPE, TABLE_NAME, STATUS_COLUMN_NAME, CREATED_DATE)
values('Vendor' , 'MASTER', 'com.ff.domain.business.SAPVendorDO', 'sapStatusInBound', '2015-07-24');

insert into ff_d_sap_interface_error_config (INTERFACE_NAME, INTERFACE_TYPE, TABLE_NAME, STATUS_COLUMN_NAME, CREATED_DATE)
values('Billing' , 'TRANSACTIONAL', 'com.ff.domain.billing.SAPBillSalesOrderDO', 'sapInbound', '2015-07-24');