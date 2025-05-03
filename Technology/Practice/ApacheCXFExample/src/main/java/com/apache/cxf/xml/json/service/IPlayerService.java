package com.apache.cxf.xml.json.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.annotations.GZIP;

import in.benchresources.cdm.player.PlayerListType;
import in.benchresources.cdm.player.PlayerType;

@GZIP ( force = true, threshold =1  )
@Path("/playerService")
public interface IPlayerService {

	// Basic CRUD operations for Player Service

	// http://localhost:8080/ApacheCXF-XML-JSON-IO/services/playerservice/addplayer
	@POST
	@Path("addplayer")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_FORM_URLENCODED})
	public String createOrSaveNewPLayerInfo(PlayerType playerType);

	// http://localhost:8080/ApacheCXF-XML-JSON-IO/services/playerservice/getplayer/238
	
	@GET
	
	@Path("getplayer/{id}")
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	
	public PlayerType getPlayerInfo(@PathParam("id") int playerId);

	// http://localhost:8080/ApacheCXF-XML-JSON-IO/services/playerservice/updateplayer
	@PUT
	@Path("updateplayer")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_FORM_URLENCODED})
	public String updatePlayerInfo(PlayerType playerType);

	// http://localhost:8080/ApacheCXF-XML-JSON-IO/services/playerservice/deleteplayer
	@DELETE
	@Path("deleteplayer")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,})
	@Produces({MediaType.APPLICATION_FORM_URLENCODED})
	public String deletePlayerInfo(PlayerType playerType);

	// http://localhost:8080/ApacheCXF-XML-JSON-IO/services/playerservice/getallplayer
	/**GET /resource.xml 
	 > GET /resource.en
	  GET /resource.xml 
> CXF also supports a _type query as an alternative to appending extensions like '.xml' to request URIs
GET /resource?_type=xml
	 *
	 */
	@GET
	@Path("getallplayer")
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public PlayerListType getAllPlayerInfo();

	@Path("{id}")
	   @DELETE
	public void delete(@PathParam("id") int id);
	@Path("/retrievePlayers")
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getAllPlayers();
}