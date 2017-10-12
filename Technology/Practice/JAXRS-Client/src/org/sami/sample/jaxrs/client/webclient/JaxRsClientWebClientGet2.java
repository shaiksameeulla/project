package org.sami.sample.jaxrs.client.webclient;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.sami.sample.jaxrs.client.util.PlayerUtil;

import in.benchresources.cdm.player.PlayerListType;

public class JaxRsClientWebClientGet2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/ApacheCXFExample/services/");
		WebTarget playerService = target.path("playerservice");
		WebTarget allPlayer= playerService.path("getallplayer");

		Invocation.Builder builder = allPlayer.request();
		Response response = builder.get();
		if(response.getStatus() == 200){
			System.out.println(response);
			PlayerListType playerList = builder.get(PlayerListType.class);
			PlayerUtil.displayPlayerList(playerList);
		}else{
			System.out.println(" invalid response"+response.getStatus());
		}
	}

}
