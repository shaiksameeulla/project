/*
 * @author mohammes
 */
package com.capgemini.lbs.framework.to;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import com.capgemini.lbs.framework.exception.CGBusinessException;

// TODO: Auto-generated Javadoc
/**
 * The Class CGBaseTO.
 */
public class CGBaseTO implements Serializable {

	/**
	 * This variable will hold all the validation messages sent from central
	 * server.
	 */
	private CGBusinessException businessException;

	/*
	 * Request type is the type of request made by user it can be of Search ,
	 * Vaidation , Search
	 */
	/** The request type. */
	private String requestType;

	/** The base list. */
	private List<? extends CGBaseTO> baseList;

	/** The object type. */
	private Class<? extends CGBaseTO> objectType;

	/** The class type. */
	private String classType;

	/** The method name. */
	private String methodName;

	/** The bean id. */
	private String beanId;
	
	private List<? extends CGBaseTO> jsonChildObject;
	
	private Class<? extends CGBaseTO> jsonobjectType;
	
	//Added By Jay
	/** The db server. */
	private String dbServer;
	
	private String nodeId;
	
	private String dtToCentral="N";
	private String dtUpdateToCentral="N";
	
	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
   
		
	/**
	 * Gets the bean id.
	 *
	 * @return the bean id
	 */
	public String getBeanId() {
		return beanId;
	}

	/**
	 * Sets the bean id.
	 *
	 * @param beanId the new bean id
	 */
	public void setBeanId(String beanId) {
		this.beanId = beanId;
	}

	/**
	 * Gets the method name.
	 *
	 * @return the method name
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * Sets the method name.
	 *
	 * @param methodName the new method name
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * Gets the class type.
	 *
	 * @return the class type
	 */
	public String getClassType() {
		return classType;
	}

	/**
	 * Sets the class type.
	 *
	 * @param classType the new class type
	 */
	public void setClassType(String classType) {
		this.classType = classType;
	}

	/**
	 * Gets the business exception.
	 * 
	 * @return the business exception
	 */
	@XmlTransient //Annotated for xml file generation
	public CGBusinessException getBusinessException() {
		return businessException;
	}

	/**
	 * * @param businessException.
	 * 
	 * @param businessException
	 *            the new business exception
	 */
	public void setBusinessException(CGBusinessException businessException) {
		this.businessException = businessException;
	}

	/**
	 * Gets the request type.
	 * 
	 * @return the request type
	 */
	public String getRequestType() {
		return requestType;
	}

	/**
	 * Sets the request type.
	 * 
	 * @param requestType
	 *            the new request type
	 */
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	/**
	 * Gets the base list.
	 * 
	 * @return the base list
	 */
	public List<? extends CGBaseTO> getBaseList() {
		return baseList;
	}

	/**
	 * Sets the base list.
	 * 
	 * @param baseList
	 *            the new base list
	 */
	public void setBaseList(List<? extends CGBaseTO> baseList) {
		this.baseList = baseList;
	}

	/**
	 * Gets the object type.
	 * 
	 * @return the object type
	 */
	public Class<? extends CGBaseTO> getObjectType() {
		return objectType;
	}

	/**
	 * Sets the object type.
	 * 
	 * @param objectType
	 *            the new object type
	 */
	public void setObjectType(Class<? extends CGBaseTO> objectType) {
		this.objectType = objectType;
	}
	
	/**
	 * Gets the db server.
	 *
	 * @return the db server
	 */
	public String getDbServer() {
		return dbServer;
	}

	/**
	 * Sets the db server.
	 *
	 * @param dbServer the new db server
	 */
	public void setDbServer(String dbServer) {
		this.dbServer = dbServer;
	}

	

	/**
	 * @return the jsonobjectType
	 */
	public Class<? extends CGBaseTO> getJsonobjectType() {
		return jsonobjectType;
	}

	/**
	 * @param jsonobjectType the jsonobjectType to set
	 */
	public void setJsonobjectType(Class<? extends CGBaseTO> jsonobjectType) {
		this.jsonobjectType = jsonobjectType;
	}

	/**
	 * @return the jsonChildObject
	 */
	public List<? extends CGBaseTO> getJsonChildObject() {
		return jsonChildObject;
	}

	/**
	 * @param jsonChildObject the jsonChildObject to set
	 */
	public void setJsonChildObject(List<? extends CGBaseTO> jsonChildObject) {
		this.jsonChildObject = jsonChildObject;
	}

	/**
	 * @return the dtToCentral
	 */
	public String getDtToCentral() {
		return dtToCentral;
	}

	/**
	 * @param dtToCentral the dtToCentral to set
	 */
	public void setDtToCentral(String dtToCentral) {
		this.dtToCentral = dtToCentral;
	}

	/**
	 * @return the dtUpdateToCentral
	 */
	public String getDtUpdateToCentral() {
		return dtUpdateToCentral;
	}

	/**
	 * @param dtUpdateToCentral the dtUpdateToCentral to set
	 */
	public void setDtUpdateToCentral(String dtUpdateToCentral) {
		this.dtUpdateToCentral = dtUpdateToCentral;
	}

	

}
