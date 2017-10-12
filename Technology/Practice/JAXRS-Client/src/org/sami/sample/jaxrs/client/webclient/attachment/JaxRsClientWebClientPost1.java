package org.sami.sample.jaxrs.client.webclient.attachment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.ContentDisposition;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;

public class JaxRsClientWebClientPost1 {

	public static void main(String[] args) {
		
		List providers = new ArrayList();
		providers.add(new org.codehaus.jackson.jaxrs.JacksonJsonProvider());
		providers.add(new org.apache.cxf.jaxrs.provider.MultipartProvider());
		
		WebClient client = WebClient.create("http://localhost:8080/ApacheCXFExample/services/attachmentService/postTestData",providers);
		client= client.encoding("UTF-8");
		client = client.type(MediaType.APPLICATION_OCTET_STREAM).accept(MediaType.TEXT_PLAIN);
		List<Attachment> atts = new LinkedList<Attachment>();
		atts.add(new Attachment("note", "application/text", "image file"));
		ContentDisposition cd = new ContentDisposition("attachment;filename=test.csv.gz");
		try {
			InputStream stream = new FileInputStream(new File("D://citrixReceiver.zip"));

			//atts.add(new Attachment("upfile", "application/octet-stream",stream ));
			atts.add(new Attachment("upfile",stream ,cd));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Response resp= client.post(new MultipartBody(atts));
		System.out.println(resp);
		
	}

}
