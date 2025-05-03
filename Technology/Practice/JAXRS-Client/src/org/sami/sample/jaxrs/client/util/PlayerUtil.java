package org.sami.sample.jaxrs.client.util;

import in.benchresources.cdm.player.PlayerListType;
import in.benchresources.cdm.player.PlayerType;

public class PlayerUtil {

	
	public static void displayPlayer(PlayerType subresource) {
		// {3} remote GET call to http://bookstore.com/bookstore/1
		System.out.println("name :"+subresource.getName()+"age :" +subresource.getAge() +"matches :" +subresource.getMatches());
	}

	public static void displayPlayerList(PlayerListType playerList) {
		System.out.println("All player list details");
		for(PlayerType pt:playerList.getPlayerType()){
			displayPlayer(pt);
		}
	}
}
