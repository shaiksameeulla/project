use CorpUDAAN;
ALTER TABLE `ff_d_pincode` MODIFY COLUMN `STATUS` ENUM('I','A') CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT 'A';

update ff_d_pincode set STATUS='A';