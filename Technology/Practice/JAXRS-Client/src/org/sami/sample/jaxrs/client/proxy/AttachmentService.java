package org.sami.sample.jaxrs.client.proxy;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;


@Path("/attachmentService")
public interface AttachmentService {

	@POST
    @Path("upload")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    public Response uploadFile(@Multipart("note") String note, 
     @Multipart("upfile") Attachment attachment);             
}