package com.ff.errorHandling;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;

@SuppressWarnings("serial")
public class ErrorHandlingTo extends CGBaseTO {
	String interfaceName;
	String customerNo;
	String exception;
/*	List<SAPCustomerTO> errorList;
	List<SAPCustomerTO> successList;*/
	
	List<CGBaseTO> errorList;
	List<CGBaseTO> successList;
	
	//	List<ErrorHandlingTo> errorList;
//	Integer successCount = 0;
//	Integer errorCount = 0;

	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	
/*	public List<SAPCustomerTO> getErrorList() {
		return errorList;
	}
	public void setErrorList(List<SAPCustomerTO> errorList) {
		this.errorList = errorList;
	}*/
	
/*	public Integer getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(Integer successCount) {
		this.successCount = successCount;
	}
	
	public Integer getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}*/
	
	public List<CGBaseTO> getErrorList() {
		return errorList;
	}
	public void setErrorList(List<CGBaseTO> errorList) {
		this.errorList = errorList;
	}
	
	public List<CGBaseTO> getSuccessList() {
		return successList;
	}
	public void setSuccessList(List<CGBaseTO> successList) {
		this.successList = successList;
	}	
	
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	
/*	public List<SAPCustomerTO> getErrorList() {
		return errorList;
	}
	public void setErrorList(List<SAPCustomerTO> errorList) {
		this.errorList = errorList;
	}
	
	public List<SAPCustomerTO> getSuccessList() {
		return successList;
	}
	public void setSuccessList(List<SAPCustomerTO> successList) {
		this.successList = successList;
	}*/	

}
