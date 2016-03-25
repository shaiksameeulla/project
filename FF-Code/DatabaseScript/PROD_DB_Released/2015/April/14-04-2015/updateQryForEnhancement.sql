1. Show the field staff/third party name against the DRS #.
update ff_d_process 
set TRACKING_TXT_TMPL = 'Out for delivery(DRS No: {DRSNo}) from {originOff} by {thirdparty/FS}' 
where PROCESS_CODE='DLRS';

2. Consignment Tracking - In the dispatch row along with the Gatepass number below additional details should also reflect.Mode – Air / Rail / Road (Transport no & Scheduled time of Arrival)
update ff_d_process 
set TRACKING_TXT_TMPL = 'Forwarded (Dispatch No : {gatePassNo}) via {transportModeAndNo} arrive at {arrivalTime} to {destOff} from {originOff} having Weight {weight} kg.' 
where PROCESS_CODE='DSPT';

3. Manifest Tracking - In the dispatch row along with the Gatepass number below additional details should also reflect.Mode – Air / Rail / Road (Transport no & Scheduled time of Arrival)
update ff_d_process 
set TRACKING_TXT_TMPL_MNF = 'Forwarded (Dispatch No : {gatePassNo}) via {transportModeAndNo} arrive at {arrivalTime} to {destOff} from {originOff} having Weight {weight} kg.' 
where PROCESS_CODE='DSPT';
