# Rem insert into ff_d_service_request_transfer_to


insert into ff_d_service_request_transfer_to
(service_request_transfer_to_id ,transfer_to_code,transfer_to_name,transfer_tos_description)
values
(12, 'AIR',  'AIRLINES',  'AIRLINES');

insert into ff_d_service_request_transfer_to
(service_request_transfer_to_id ,transfer_to_code,transfer_to_name,transfer_tos_description)
values
(13, 'CONSR', 'CONSIGNOR','CONSIGNOR');

insert into ff_d_service_request_transfer_to
(service_request_transfer_to_id ,transfer_to_code,transfer_to_name,transfer_tos_description)
values
(14, 'CONSE', 'CONSIGNEE','CONSIGNEE');

insert into ff_d_service_request_transfer_to
(service_request_transfer_to_id ,transfer_to_code,transfer_to_name,transfer_tos_description)
values
(15,'CO-CR','CO-COURIER','CO_COURIER');

insert into ff_d_service_request_transfer_to
(service_request_transfer_to_id ,transfer_to_code,transfer_to_name,transfer_tos_description)
values
(16,'CO-LR','CO-LOADER','CO_LOADER');

insert into ff_d_service_request_transfer_to
(service_request_transfer_to_id ,transfer_to_code,transfer_to_name,transfer_tos_description)
values
(17,'DELVA','DELIVERY AGENT','DELIVERY AGENT');

insert into ff_d_service_request_transfer_to
(service_request_transfer_to_id ,transfer_to_code,transfer_to_name,transfer_tos_description)
values
(18,'DELVB','DELIVERY BA','DELIVERY BA');

insert into ff_d_service_request_transfer_to
(service_request_transfer_to_id ,transfer_to_code,transfer_to_name,transfer_tos_description)
values
(19,'DELVA','DELIVERY AGENT','DELIVERY AGENT');

insert into ff_d_service_request_transfer_to
(service_request_transfer_to_id ,transfer_to_code,transfer_to_name,transfer_tos_description)
values
(20,'FR','FRANCHISEE','FRANCHISEE');

insert into ff_d_service_request_transfer_to
(service_request_transfer_to_id ,transfer_to_code,transfer_to_name,transfer_tos_description)
values
(21,'OTC','OTC','OTC');

insert into ff_d_service_request_transfer_to
(service_request_transfer_to_id ,transfer_to_code,transfer_to_name,transfer_tos_description)
values
(22,'SALES','SALES','SALES');

UPDATE ff_d_service_request_transfer_to 
SET transfer_to_code = 'BBC', transfer_to_name = 'BBC',
    transfer_tos_description = 'BBC'
WHERE service_request_transfer_to_id = 7;

COMMIT;