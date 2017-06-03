package org.sami.sample.jaxrs.client.proxy;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;

import in.benchresources.cdm.player.PlayerType;

public class JaxRsClientPost {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IPlayerService player = JAXRSClientFactory.create("http://localhost:8080/ApacheCXFExample/services/", IPlayerService.class);
		// (1) remote GET call to http://bookstore.com/bookstore
		
		PlayerType pt= new PlayerType();
		pt.setName("sami");
		pt.setAge(32);
		pt.setMatches(33);
		pt.setPlayerId(pt.getName().hashCode());
		String string = player.createOrSaveNewPLayerInfo(pt);
		System.out.println("create/save player result :"+string);
		
	}

}
