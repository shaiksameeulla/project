/*
INDEXING FOR FOR THE OFFICE PACKET TABLE
*/
USE CorpUDAAN;
ALTER TABLE `ff_d_outbound_office_packet` DROP INDEX `FK_outbound_data_sync1`,
ADD INDEX `FK_outbound_data_sync1` USING BTREE(`OFFICE_ID`, `TRANSFER_STATUS`);

OPTIMIZE TABLE ff_d_outbound_office_packet;

