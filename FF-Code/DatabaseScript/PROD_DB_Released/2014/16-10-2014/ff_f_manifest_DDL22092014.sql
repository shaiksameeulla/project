/*Himal - as per 22/9/2014 mail*/
Use CorpUDAAN;

ALTER TABLE ff_f_manifest 
MODIFY RECEIVED_STATUS ENUM('R','N','E') DEFAULT NULL COMMENT 'R-Received, N-Not Received, E – Excess';