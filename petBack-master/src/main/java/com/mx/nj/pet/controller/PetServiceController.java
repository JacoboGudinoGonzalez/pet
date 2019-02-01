package com.mx.nj.pet.controller;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mx.nj.pet.model.ServicePet;
import com.mx.nj.pet.model.Usuario;
import com.mx.nj.pet.service.PetServiceService;

@Component
@Path("/petServiceController")
public class PetServiceController {
	
	@Autowired(required=true) PetServiceService petServiceService;

	@POST
	@Path("/addPetService/{idUserFrom}/{idUserTo}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addPetService(ServicePet sp, @PathParam("idUserFrom") int idUserFrom, @PathParam("idUserTo") int idTUsero){
		if(sp.getDateFrom() !=null
				&& sp.getDate()!=null 
				&& (sp.getAnimalType()!=null && sp.getAnimalType()!="") 
				){
			Usuario user = new Usuario();
			user.setId(idUserFrom);
			sp.setUsuario(user);
			sp.setIdUserTo(idTUsero);
			petServiceService.addPetService(sp);
			JsonObject msj = Json.createObjectBuilder()
					.add("msj", "se ha generado la reservación exitosamente").build();
			return Response.status(Response.Status.OK).entity(msj.toString()).build();
		}
		return null;
	}
}
