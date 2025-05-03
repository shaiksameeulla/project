package com.apache.cxf.xml.json.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.springframework.stereotype.Service;

@Service("attachmentService")
public class AttachmentServiceImpl implements AttachmentService{

	
    public Response uploadFile(@Multipart("note") String note, 
     @Multipart("upfile") Attachment attachment) {

       String filename = attachment.getContentDisposition().getParameter("filename");

       try {
    	   java.nio.file.Path path = Paths.get("c:/" + filename);
    	   Files.deleteIfExists(path);
    	   InputStream in = attachment.getObject(InputStream.class);

    	   Files.copy(in, path);
       } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       return Response.ok("file uploaded").build();
    }

	@Override
	public Response postTestData(List<Attachment> attachments, HttpServletRequest request) {
		
		return Response.ok("file uploaded").build();
	}                                  
}