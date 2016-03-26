/**
 * 
 */
package com.ff.web.pickup.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.pickup.PickupOrderTO;

/**
 * @author uchauhan
 *
 */
public class ConfirmPickupOrderForm extends CGBaseForm {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5034884646076173140L;
	
	public ConfirmPickupOrderForm(){
			setTo(new PickupOrderTO());
			
		}
		private PickupOrderTO to;
		/**
		 * @return the to
		 */
		public PickupOrderTO getTo() {
			return to;
		}
		/**
		 * @param to the to to set
		 */
		public void setTo(PickupOrderTO to) {
			this.to = to;
		}
	

	
	
	

	

}
