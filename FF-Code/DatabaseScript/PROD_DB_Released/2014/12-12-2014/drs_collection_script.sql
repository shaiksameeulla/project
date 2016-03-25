use CorpUDAAN;
update ff_f_delivery_dtls dlv

join ff_f_consignment c on dlv.CONSIGNMENT_NUMBER = c.consg_no

set dlv.LC_AMOUNT = c.LC_AMT,
dlv.BA_AMT= c.BA_AMT ,
dlv.TO_PAY_AMOUNT=c.TOPAY_AMT ,
dlv.COD_AMOUNT= c.COD_AMT
where
    dlv.DELIVERY_STATUS='D'
    and
    dlv.COLLECTION_STATUS='N'
    and
(
    (dlv.BA_AMT is null  and c.BA_AMT is not null)
    or
    (dlv.TO_PAY_AMOUNT is null and c.TOPAY_AMT is not null )
    or
    (dlv.COD_AMOUNT is null and c.COD_AMT is not null)
    or
    (dlv.LC_AMOUNT is null  and c.LC_AMT is not null)
  );

