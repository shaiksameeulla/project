USE CorpUDAAN;
update ff_f_consignment co set co.DT_SAP_OUTBOUND = 'C'
where
co.DT_SAP_OUTBOUND = 'N' and
co.PRODUCT in (2,3,9);