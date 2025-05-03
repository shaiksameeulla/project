/**
 * 
 */
package com.apache.cxf.xml.json.service;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.apache.cxf.xml.json.exception.BusinessException;

/**
 * @author mohammes
 *
 */
@Provider
public class MyException implements ExceptionMapper<BusinessException> {

	@Override
	public Response toResponse(BusinessException arg0) {
		Response.status(Status.INTERNAL_SERVER_ERROR).entity(" my BusinessException ").build();
		return null;
	}

}
