/*Sami - new column for stock reduction*/


ALTER TABLE ff_f_manifest 
ADD IS_STOCK_CONSUMED ENUM('Y','N') DEFAULT 'N';



ALTER TABLE ff_f_booking 
ADD IS_STOCK_CONSUMED ENUM('Y','N') DEFAULT 'N';


ALTER TABLE ff_f_comail 
ADD IS_STOCK_CONSUMED ENUM('Y','N') DEFAULT 'N';