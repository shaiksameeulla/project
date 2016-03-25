USE CorpUDAAN;
DELETE cr1.*
  FROM ff_f_consignment_rate cr1
       JOIN ff_f_consignment_rate cr2
          ON cr1.CONSIGNMENT_ID = cr2.CONSIGNMENT_ID
where cr1.CONSIGNMENT_RATE_ID < cr2.CONSIGNMENT_RATE_ID;

