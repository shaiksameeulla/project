package org.sami.sample.jaxrs.client.webclient;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.apache.cxf.transport.common.gzip.GZIPInInterceptor;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

public class JaxRsClientWebClientDelete {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Client client = ClientBuilder.newClient();
		//JacksonJsonProvider provider = new JacksonJsonProvider();
		//provider.setMapper(new org.codehaus.jackson.map.ObjectMapper());
		client.register(JacksonJsonProvider.class);
		WebTarget target = client.target("http://localhost:8080/ApacheCXFExample/services/");
		WebTarget playerService = target.path("playerService/{id}");
		WebTarget onePlayer= playerService.resolveTemplate("id", "1");
		Invocation.Builder builder = onePlayer.request();
		
		Response  response = builder.delete();
		
		if(response.getStatus() == 200){
			String result = response.readEntity(String.class);
			System.out.println(result);
		}else{
			System.out.println("invalid response : "+response.getStatus());
		}
		
	}

}
