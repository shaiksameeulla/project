USE CorpUDAAN;
update ff_f_collection co
set co.IS_RECALCULATION_REQ = 'N'
where
co.IS_RECALCULATION_REQ = 'Y' and
date(co.COLLECTION_DATE) < '2015-03-01';