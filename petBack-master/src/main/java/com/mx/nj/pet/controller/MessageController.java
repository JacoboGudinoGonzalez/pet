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
import com.mx.nj.pet.model.Message;
import com.mx.nj.pet.model.Pagination;
import com.mx.nj.pet.service.FollowService;
import com.mx.nj.pet.service.MessageService;
import com.mx.nj.pet.util.Util;

@Component
@Path("/messageController")
public class MessageController {

	@Autowired(required=true) MessageService messageService;

	@Autowired(required=true) FollowService followService;

	@POST
	@Path("/saveMessage")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveMessage(@HeaderParam("authorization") String authStringe, Message message){
		if(!Util.parseToken(authStringe)){
			JsonObject msj = Json.createObjectBuilder()
					.add("error", "No tienes permiso para obtener informacion").build();
			return Response.status(Response.Status.UNAUTHORIZED).entity(msj.toString()).build();
		}
		if(message.getText()==null || message.getReceiver()==null) {
			JsonObject msj = Json.createObjectBuilder()
					.add("error", "Envia los datos necesarios").build();
			return Response.status(Response.Status.UNAUTHORIZED).entity(msj.toString()).build();
		}else {
			message.setEmmiter(Util.parseTokenToUser(authStringe));
			message.setCreatedAt(Util.getDate());
			message.setViewed(false);
			messageService.addMessage(message);
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
			String result = null;
			try {
				result = mapper.writeValueAsString(message);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return Response.status(Response.Status.OK).entity(result).build();
		}
	}

	@GET
	@Path("/myMessages/{pageParam}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getReceivedMessages(@HeaderParam("authorization") String authStringe, @PathParam("pageParam") int pageParam){
		if(!Util.parseToken(authStringe)){
			JsonObject msj = Json.createObjectBuilder()
					.add("error", "No tienes permiso para obtener informacion").build();
			return Response.status(Response.Status.UNAUTHORIZED).entity(msj.toString()).build();
		}

		List<Message> messageList = messageService.getMyMessages(Util.parseTokenToUser(authStringe).getId());

		int page = 1;
		int itemsPerPage = 4;
		
		if(pageParam!=0) {
			page = pageParam;
		}

		List<Message> list = new ArrayList(Util.partition(messageList, itemsPerPage));
		boolean continuar = false;
		try {
			list = (List<Message>) list.get(page-1);
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
				p.setPages((list.size()+itemsPerPage-1)/itemsPerPage);
				p.setTotal(list.size());

				json = writer.writeValueAsString(p);

			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return Response.status(Response.Status.OK).entity(json.toString()).build();
		}else{
			JsonObject msj = Json.createObjectBuilder()
					.add("msj", "no existen mensajes para pagina: "+(page-1)).build();
			return Response.status(Response.Status.NOT_FOUND).entity(msj.toString()).build();
		}
	}

	@GET
	@Path("/messages/{pageParam}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getMessages(@HeaderParam("authorization") String authStringe, @PathParam("pageParam") int pageParam){
		if(!Util.parseToken(authStringe)){
			JsonObject msj = Json.createObjectBuilder()
					.add("error", "No tienes permiso para obtener informacion").build();
			return Response.status(Response.Status.UNAUTHORIZED).entity(msj.toString()).build();
		}

		List<Message> messageList = messageService.getMessages(Util.parseTokenToUser(authStringe).getId());

		int page = 1;
		int itemsPerPage = 4;

		if(pageParam!=0) {
			page = pageParam;
		}

		List<Message> list = new ArrayList(Util.partition(messageList, itemsPerPage));
		boolean continuar = false;
		try {
			list = (List<Message>) list.get(page-1);
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
				p.setPages((messageList.size()+itemsPerPage-1)/itemsPerPage);
				p.setTotal(messageList.size());

				json = writer.writeValueAsString(p);

			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return Response.status(Response.Status.OK).entity(json.toString()).build();
		}else{
			JsonObject msj = Json.createObjectBuilder()
					.add("msj", "no existen mensajes para pagina: "+(page-1)).build();
			return Response.status(Response.Status.NOT_FOUND).entity(msj.toString()).build();
		}
	}

	@GET
	@Path("/unviewedMessages")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getUnviewedMessages(@HeaderParam("authorization") String authStringe){
		if(!Util.parseToken(authStringe)){
			JsonObject msj = Json.createObjectBuilder()
					.add("error", "No tienes permiso para obtener informacion").build();
			return Response.status(Response.Status.UNAUTHORIZED).entity(msj.toString()).build();
		}
		long unviewed = messageService.getUnviewedMessages(Util.parseTokenToUser(authStringe).getId());

		if(unviewed==0){
			JsonObject msj = Json.createObjectBuilder()
					.add("error", "No existen mensajes sin ver").build();
			return Response.status(Response.Status.UNAUTHORIZED).entity(msj.toString()).build();
		}else {
			JsonObject msj = Json.createObjectBuilder()
					.add("unviewed", unviewed).build();
			return Response.status(Response.Status.UNAUTHORIZED).entity(msj.toString()).build();
		}
	}
	
	@GET
	@Path("/setViewedMessages")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setViewedMessages(@HeaderParam("authorization") String authStringe){
		if(!Util.parseToken(authStringe)){
			JsonObject msj = Json.createObjectBuilder()
					.add("error", "No tienes permiso para obtener informacion").build();
			return Response.status(Response.Status.UNAUTHORIZED).entity(msj.toString()).build();
		}
		int unviewed = messageService.setViewedMessages(Util.parseTokenToUser(authStringe).getId());

		if(unviewed==0){
			JsonObject msj = Json.createObjectBuilder()
					.add("msj", "No existen mensajes sin ver").build();
			return Response.status(Response.Status.UNAUTHORIZED).entity(msj.toString()).build();
		}else {
			JsonObject msj = Json.createObjectBuilder()
					.add("msj", "mensajes marcados como vistos: "+unviewed).build();
			return Response.status(Response.Status.UNAUTHORIZED).entity(msj.toString()).build();
		}
	}

	@GET
	@Path("/message/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getMessage(@HeaderParam("authorization") String authStringe, @PathParam("id") int id){
		if(!Util.parseToken(authStringe)){
			JsonObject msj = Json.createObjectBuilder()
					.add("error", "-1").build();
			return Response.status(Response.Status.UNAUTHORIZED).entity(msj.toString()).build();
		}

		if(id!=0){

			Message message = messageService.getMessageById(id);

			if(message!=null){
				ObjectMapper mapper = new ObjectMapper();
				mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
				String result = null;
				try {
					final ObjectWriter writer = mapper.writer().withRootName("message");
					result = writer.writeValueAsString(message);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				return Response.status(Response.Status.OK).entity(result).build();
			}else{
				JsonObject msj = Json.createObjectBuilder()
						.add("msj", "no existen mensaje").build();
				return Response.status(Response.Status.NOT_FOUND).entity(msj.toString()).build();
			}

		}else{
			JsonObject msj = Json.createObjectBuilder()
					.add("msj", "no existe mensaj para id 0").build();
			return Response.status(Response.Status.NOT_FOUND).entity(msj.toString()).build();
		}
	}

	@DELETE
	@Path("/message/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteMessage(@HeaderParam("authorization") String authStringe, @PathParam("id") int id){
		if(!Util.parseToken(authStringe)){
			JsonObject msj = Json.createObjectBuilder()
					.add("error", "-1").build();
			return Response.status(Response.Status.UNAUTHORIZED).entity(msj.toString()).build();
		}

		messageService.deleteMyMessageById(id, Util.parseTokenToUser(authStringe).getId());

		JsonObject msj = Json.createObjectBuilder()
				.add("msj", "no existen mensajes").build();
		return Response.status(Response.Status.NOT_FOUND).entity(msj.toString()).build();
	}

}