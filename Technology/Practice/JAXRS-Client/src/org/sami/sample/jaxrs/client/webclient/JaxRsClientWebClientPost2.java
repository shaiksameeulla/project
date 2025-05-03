package org.sami.sample.jaxrs.client.webclient;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import in.benchresources.cdm.player.PlayerType;

public class JaxRsClientWebClientPost2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Client client = ClientBuilder.newClient();
		//JacksonJsonProvider provider = new JacksonJsonProvider();
		//provider.setMapper(new org.codehaus.jackson.map.ObjectMapper());
		client.register(JacksonJsonProvider.class);
		WebTarget target = client.target("http://localhost:8080/ApacheCXFExample/services/");
		WebTarget playerService = target.path("playerService");
		WebTarget onePlayer= playerService.path("addplayer");
		Invocation.Builder builder = onePlayer.request();
		PlayerType pt= new PlayerType();
		pt.setName("sami");
		pt.setAge(32);
		pt.setMatches(33);
		pt.setPlayerId(pt.getName().hashCode());
		
		Response  response = builder.post(Entity.json(pt));
		
		if(response.getStatus() ==200){
			String result = response.readEntity(String.class);
			System.out.println(result);
		}else{
			System.out.println("invalid response");
		}
		
	}

}
