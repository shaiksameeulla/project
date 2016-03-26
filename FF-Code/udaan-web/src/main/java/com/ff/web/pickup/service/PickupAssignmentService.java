/**
 * 
 */
package com.ff.web.pickup.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.pickup.PickupAssignmentTypeTO;
import com.ff.pickup.PickupCustomerTO;
import com.ff.pickup.RunsheetAssignmentDetailTO;
import com.ff.pickup.RunsheetAssignmentTO;

/**
 * @author kgajare
 * 
 */
public interface PickupAssignmentService {
    public List<PickupAssignmentTypeTO> getPickupRunsheetType()
	    throws CGBusinessException, CGSystemException;

    public RunsheetAssignmentTO getCustomerListForAssignment(
	    RunsheetAssignmentTO runsheetAssignmentInputTO)
	    throws CGBusinessException, CGSystemException;

    public RunsheetAssignmentTO savePickupAssignment(RunsheetAssignmentTO runsheetAssignmentTO)
	    throws CGBusinessException, CGSystemException;
    
    /*public RunsheetAssignmentTO getCustomerListForReversePickupCanDelete(RunsheetAssignmentTO runsheetAssignmentTO)
    	    throws CGBusinessException, CGSystemException;*/
    
    public RunsheetAssignmentTO getAssignedCustomerList(RunsheetAssignmentTO runsheetAssignmentTO)
	    throws CGBusinessException, CGSystemException;

    public List<RunsheetAssignmentDetailTO> getAssignmentDetailsForRunsheetAtHub(
			RunsheetAssignmentTO runsheetAssignmentInputTO)
			throws CGBusinessException, CGSystemException;

    public List<PickupCustomerTO> getCustomerDetailsForRunsheetAtHub(
			RunsheetAssignmentTO runsheetAssignmentInputTO)
			throws CGBusinessException, CGSystemException;
    
}
