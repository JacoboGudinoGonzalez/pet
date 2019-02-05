package com.mx.nj.pet.controller;

import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mx.nj.pet.model.Follow;
import com.mx.nj.pet.model.Pagination;
import com.mx.nj.pet.service.FollowService;
import com.mx.nj.pet.service.UsuarioService;
import com.mx.nj.pet.util.Util;

@Component
@Path("/followController")
public class FollowController {

	@Autowired(required=true) FollowService followService;
	@Autowired(required=true) UsuarioService usuarioService;

	@POST
	@Path("/addFollow")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addFollow(@HeaderParam("authorization") String authStringe, Follow follow){
		if(!Util.parseToken(authStringe)){
			JsonObject msj = Json.createObjectBuilder()
					.add("msj", "No tienes permiso para realizar la operacion").build();
			return Response.status(Response.Status.UNAUTHORIZED).entity(msj.toString()).build();
		}
		if(follow!=null) {
			if(follow.getUsuario()!=null && follow.getFollowed()!=null){
				follow.setUsuario(usuarioService.getUserById(Util.parseTokenToUser(authStringe).getId()));
				follow = followService.addFollow(follow);
				ObjectMapper mapper = new ObjectMapper();
				mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
				String result = null;
				try {
					result = mapper.writeValueAsString(follow);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				return Response.status(Response.Status.OK).entity(result).build();
			}
		} 
		return null;
	}
	
	@DELETE
	@Path("/deleteFollow/{folowId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteFollow(@HeaderParam("authorization") String authStringe, @PathParam("folowId") int followId){
		if(!Util.parseToken(authStringe)){
			JsonObject msj = Json.createObjectBuilder()
					.add("msj", "No tienes permiso para realizar la operacion").build();
			return Response.status(Response.Status.UNAUTHORIZED).entity(msj.toString()).build();
		}

		int deleted = followService.deleteFollow(Util.parseTokenToUser(authStringe).getId(), followId);
		
		if(deleted !=0) {
			JsonObject msj = Json.createObjectBuilder()
					.add("msj", "el follow se ha eliminado").build();
			return Response.status(Response.Status.OK).entity(msj.toString()).build();
		}else {
			JsonObject msj = Json.createObjectBuilder()
					.add("msj", "no existe follow a eliminar").build();
			return Response.status(Response.Status.NOT_FOUND).entity(msj.toString()).build();
		}
	}
	
	@GET
	@Path("/following/{pageParam}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getFollowingUsers(@HeaderParam("authorization") String authStringe, @PathParam("pageParam") int pageParam){
		if(!Util.parseToken(authStringe)){
			JsonObject msj = Json.createObjectBuilder()
					.add("error", "-1").build();
			return Response.status(Response.Status.UNAUTHORIZED).entity(msj.toString()).build();
		}

		List<Follow> followingList = followService.getMyFollows(Util.parseTokenToUser(authStringe).getId());
				
		if(followingList.isEmpty()){
			JsonObject msj = Json.createObjectBuilder()
					.add("msj", "No se sigue a ningún usuario").build();
			return Response.status(Response.Status.NOT_FOUND).entity(msj.toString()).build();
		}
		
		int page = 1;
		int itemsPerPage = 4;
		
		if(pageParam!=0) {
			page = pageParam;
		}
		
		List<Follow> list = new ArrayList(Util.partition(followingList, itemsPerPage));
		boolean continuar = false;
		try {
			list = (List<Follow>) list.get(page-1);
			continuar = true;
        } catch (IndexOutOfBoundsException e) {
        	continuar = false;
        }

		if(continuar){
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
			String json = null;
			try {


				final ObjectWriter writer = mapper.writer().withoutRootName();

				Pagination p = new Pagination();
				p.setItem(list);
				p.setPages((followingList.size()+itemsPerPage-1)/itemsPerPage);
				p.setTotal(followingList.size());

				json = writer.writeValueAsString(p);

			
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return Response.status(Response.Status.OK).entity(json).build();
		}else{
			JsonObject msj = Json.createObjectBuilder()
					.add("msj", "no existen registros para pagina: "+page).build();
			return Response.status(Response.Status.NOT_FOUND).entity(msj.toString()).build();
		}
	}
	
	@GET
	@Path("/followed/{pageParam}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getFollowedUsers(@HeaderParam("authorization") String authStringe, @PathParam("pageParam") int pageParam){
		if(!Util.parseToken(authStringe)){
			JsonObject msj = Json.createObjectBuilder()
					.add("error", "-1").build();
			return Response.status(Response.Status.UNAUTHORIZED).entity(msj.toString()).build();
		}

		List<Follow> followingList = followService.getMyFollowed(Util.parseTokenToUser(authStringe).getId());
				
		if(followingList.isEmpty()){
			JsonObject msj = Json.createObjectBuilder()
					.add("msj", "No se sigue a ningún usuario").build();
			return Response.status(Response.Status.NOT_FOUND).entity(msj.toString()).build();
		}
		
		int page = 1;
		int itemsPerPage = 4;
		
		if(pageParam!=0) {
			page = pageParam;
		}
		
		List<Follow> list = new ArrayList(Util.partition(followingList, itemsPerPage));
		boolean continuar = false;
		try {
			list = (List<Follow>) list.get(page-1);
			continuar = true;
        } catch (IndexOutOfBoundsException e) {
        	continuar = false;
        }

		if(continuar){
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
			String json = null;
			try {


				final ObjectWriter writer = mapper.writer().withoutRootName();

				Pagination p = new Pagination();
				p.setItem(list);
				p.setPages((followingList.size()+itemsPerPage-1)/itemsPerPage);
				p.setTotal(followingList.size());

				json = writer.writeValueAsString(p);

			
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return Response.status(Response.Status.OK).entity(json).build();
		}else{
			JsonObject msj = Json.createObjectBuilder()
					.add("msj", "no existen registros para pagina: "+page).build();
			return Response.status(Response.Status.NOT_FOUND).entity(msj.toString()).build();
		}
	}
}