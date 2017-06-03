package org.sami.sample.jaxrs.client.proxy;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.sami.sample.jaxrs.client.util.PlayerUtil;

import in.benchresources.cdm.player.PlayerListType;
import in.benchresources.cdm.player.PlayerType;

public class JaxRsClientGet {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IPlayerService player = JAXRSClientFactory.create("http://localhost:8080/ApacheCXFExample/services/", IPlayerService.class);
		// (1) remote GET call to http://bookstore.com/bookstore
		PlayerListType playerList = player.getAllPlayerInfo();
		PlayerUtil.displayPlayerList(playerList);
		// (2) no remote call
		PlayerType subresource = player.getPlayerInfo(1);
		PlayerUtil.displayPlayer(subresource);
		
	}

	

}
