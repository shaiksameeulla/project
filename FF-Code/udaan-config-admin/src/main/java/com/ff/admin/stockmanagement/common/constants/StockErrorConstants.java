/**
 * 
 */
package com.ff.admin.stockmanagement.common.constants;

/**
 * The Interface StockErrorConstants.
 *
 * @author mohammes
 */
public interface StockErrorConstants {

	/** The series cancelled. */
	String SERIES_CANCELLED="Some or all of the  series are already cancelled";
	
	/** The series issued. */
	String SERIES_ISSUED="Some or all of the  series are already issued";
	
	/** The series not available. */
	String SERIES_NOT_AVAILABLE  ="Some or all of the  series are not available in the office";
	
	/** The series used. */
	String SERIES_USED="Some or all of the series are already consumed";
	
	/** The series returned. */
	String SERIES_RETURNED="Some or all of the series are already returned";
	
	/** The series expired. */
	String SERIES_EXPIRED="Some or all of  the series are already expired";
	
	/** The series already transfered. */
	String SERIES_ALREADY_TRANSFERED="Some or all of  the series are already transferd";
	
	/** The series not exist as issued. */
	String SERIES_NOT_EXIST_AS_ISSUED="Some or all of  the series are not available in issued information";
	
	/** The series can not transfered. */
	String SERIES_CAN_NOT_TRANSFERED="Some or all of  the series are not available";
	
	/** The series received with req number. */
	String SERIES_RECEIVED_WITH_REQ_NUMBER="Some or all of the Series are already received with  the Requisition Number";
	
	/** The series already received. */
	String SERIES_ALREADY_RECEIVED="Some or all of the Series are already received";
	
	/** The series not issued with number. */
	String SERIES_NOT_ISSUED_WITH_NUMBER="Some or all of the Series are not associated with Issue number";
	
	/** The series not issued. */
	String SERIES_NOT_ISSUED="Some or all of the Series are not  in the issued information";
	
	/** The series not received with issue number. */
	String SERIES_NOT_RECEIVED_WITH_ISSUE_NUMBER="Some or all of the Series are not  received with given issue number";
	
	/** The series not exist as issued. */
	String SERIES_NOT_ISSUED_FROM_OFFICE="Some or all of  the series are not   issued from this office";
	
	String SERIES_NOT_ISSUED_FROM_PARTY="Some or all of  the series are not   issued from this party";
	
	String SERIES_NOT_RETURNED_TO_OFFICE="Some or all of  the series are not   returned to this office";
	/** The series issued. */
	String SERIES_NOT_RECEIVED_WITH_RECEIPT_NUMBER="Some or all of the  series are not received with given Acknowledgement number";
	
	String SERIES_NOT_RECEIVED_WITH_RECEIPT_NUMBER_GRID_ID="Some or all of the  series are not received(with same  row number) with given Acknowledgement number";
	
	String SERIES_NOT_RECEIVED_WITH_LATEST_RECEIPT_NUMBER="Some or all of the  series are not received with the latest Acknowledgement number";
	
}
