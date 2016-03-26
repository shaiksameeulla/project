package com.ff.web.codReceipt.constants;

public interface CodReceiptConstants {

	 String SUCCESS = "Success";
	 String GET_EXPENSE_DETAILS="select "
                               + " fdgm.GL_DESC as glDescription,"
                               + " sum(ffee.AMOUNT) as amount,"
                               + " sum(ffee.SERVICE_CHARGE) as seviceCharge,"
                               + " sum(ffee.SERVICE_TAX) as serviceTax,"
                               + " sum(ffee.EDUCATION_CESS) as educationCess,"
                               + " sum(ffee.HIGHER_EDUCATION_CESS) as higherEduCess,"
                               + " sum(ffee.OTHER_CHARGES) as otherCharges,"
                               + " sum(ffee.TOTAL_EXPENSE_ENTRY_AMT) as totalExpenseAmt"
                               + " from"
                               + " ff_f_consignment ffc"
                               //+ " -- get expenses"
                               + " JOIN ff_f_expense_entries ffee ON ffc.CONSG_ID = ffee.CONSG_ID"
                               + " JOIN ff_f_expense ffe"
                               + " ON (ffe.EXPENSE_ID = ffee.EXPENSE_ID AND"
                               + " ffe.EXPENSE_FOR = 'C')"
                               + " JOIN ff_d_gl_master fdgm ON fdgm.GL_ID = ffe.TYPE_OF_EXPENSE"
                               + " where"
                               + " ffc.CONSG_ID =:consgId and"
                               + " ((fdgm.IS_OCTROI_GL = 'Y' and"
                               + " ffee.OCTROI_BOURNE_BY = 'CE') or"
                               + " (fdgm.IS_OCTROI_GL != 'Y'))"
                               + " group by fdgm.GL_CODE";

	 String COD_RECEIPT_NO="COD_RECEIPT_NO";
	 String URL_CODRECEIPT_PRINT="printCodReceipt";

}
