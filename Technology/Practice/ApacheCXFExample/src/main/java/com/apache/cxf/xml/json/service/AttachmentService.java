package com.apache.cxf.xml.json.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;


@Path("/attachmentService")
public interface AttachmentService {

	@POST
    @Path("upload")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@Multipart("note") String note, 
     @Multipart("upfile") Attachment attachment);    
	
	@POST
    @Path("postTestData")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
	  public Response postTestData( final List<Attachment> attachments,
	    @Context HttpServletRequest request);  
}