package com.ff.universe.stickerprinting;

/**
 * 
 */

import java.util.List;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.stickerprinting.StickerPrintingTO;


// TODO: Auto-generated Javadoc
/**
 * The Class GoodsInspectionForm.
 *
 * @author surajsah
 */
public class StickerPrintingForm extends CGBaseForm {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	
	/** The list goods receipt row to. */
	private List<StickerPrintingTO> listCnPrintingTO = null;
	
	/** "to" as Transfer object for PurchaseRequisitionForm. */
	StickerPrintingTO to=null;
	
	/**
	 * Gets the to.
	 *
	 * @return the to
	 */
	public StickerPrintingTO getTo() {
		return to;
	}

	/**
	 * Sets the to.
	 *
	 * @param to the to to set
	 */
	public void setTo(StickerPrintingTO to) {
		this.to = to;
	}

	public List<StickerPrintingTO> getListCnPrintingTO() {
		return listCnPrintingTO;
	}

	public void setListCnPrintingTO(List<StickerPrintingTO> listCnPrintingTO) {
		this.listCnPrintingTO = listCnPrintingTO;
	}

	/**
	 * default constructor to set the new TO object.
	 */
	
	public StickerPrintingForm() {	
		 setTo(new StickerPrintingTO());
	}
}
