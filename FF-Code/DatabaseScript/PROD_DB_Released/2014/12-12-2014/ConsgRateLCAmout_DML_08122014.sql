update ff_f_consignment_rate cr
join ff_f_consignment c on cr.CONSIGNMENT_ID = c.CONSG_ID
join ff_f_booking  b on c.consg_no=b.consg_number
set cr.LC_AMOUNT = c.LC_AMT  
where c.PRODUCT=9 and c.LC_AMT is not null
AND (    date(b.BOOKING_DATE) >= STR_TO_DATE('01/11/2014', '%d/%m/%Y')
            AND date(b.BOOKING_DATE) <= STR_TO_DATE('30/11/2014', '%d/%m/%Y'))
AND cr.LC_AMOUNT <> c.LC_AMT;
