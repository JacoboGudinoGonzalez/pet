package com.mx.nj.pet.controller;

import java.io.InputStream;
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
import com.mx.nj.pet.model.Publication;
import com.mx.nj.pet.model.Usuario;
import com.mx.nj.pet.service.FollowService;
import com.mx.nj.pet.service.PublicationService;
import com.mx.nj.pet.util.Util;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Component
@Path("/publicationController")
public class PublicationController {

	@Autowired(required=true) PublicationService publicationService;

	@Autowired(required=true) FollowService followService;

	@POST
	@Path("/savePublication")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response savePublication(Publication publication){
		publicationService.addPublication(publication);
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
		String result = null;
		try {
			result = mapper.writeValueAsString(publication);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.OK).entity(result).build();
	}

	@GET
	@Path("/publications/{pageParam}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getPublications(@HeaderParam("authorization") String authStringe, @PathParam("pageParam") int pageParam){
		if(!Util.parseToken(authStringe)){
			JsonObject msj = Json.createObjectBuilder()
					.add("error", "-1").build();
			return Response.status(Response.Status.UNAUTHORIZED).entity(msj.toString()).build();
		}

		List<Follow> myFollows = followService.getMyFollows(Util.parseTokenToUser(authStringe).getId());
		List<Integer> userListId = new ArrayList<Integer>();

		for(Follow s : myFollows){
			userListId.add(s.getFollowed().getId());
		}
		
		userListId.add(Util.parseTokenToUser(authStringe).getId());
		
		if(userListId.isEmpty()){
			JsonObject msj = Json.createObjectBuilder()
					.add("msj", "0").build();
			return Response.status(Response.Status.OK).entity(msj.toString()).build();
		}
		
		List<Publication> publicationList = publicationService.getPublications(userListId);
		
		int page = 1;
		int itemsPerPage = 4;
		
		if(pageParam!=0) {
			page = pageParam;
		}
		
		List<Publication> list = new ArrayList(Util.partition(publicationList, itemsPerPage));
		boolean continuar = false;
		try {
			list = (List<Publication>) list.get(page-1);
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
				p.setPages((publicationList.size()+itemsPerPage-1)/itemsPerPage);
				p.setTotal(publicationList.size());
				p.setItemsPerPage(itemsPerPage);

				json = writer.writeValueAsString(p);

			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return Response.status(Response.Status.OK).entity(json).build();
		}else{
			JsonObject msj = Json.createObjectBuilder()
					.add("msj", "0").build();
			return Response.status(Response.Status.OK).entity(msj.toString()).build();
		}
	}
	
	@GET
	@Path("/publicationsUser/{userId}/{pageParam}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getMyPublications(@HeaderParam("authorization") String authStringe,@PathParam("userId") int userId,  @PathParam("pageParam") int pageParam){
		if(!Util.parseToken(authStringe)){
			JsonObject msj = Json.createObjectBuilder()
					.add("error", "-1").build();
			return Response.status(Response.Status.UNAUTHORIZED).entity(msj.toString()).build();
		}
		
		List<Publication> publicationList = publicationService.getPublicationsUser(userId);
		
		int page = 1;
		int itemsPerPage = 4;
		
		if(pageParam!=0) {
			page = pageParam;
		}
		
		List<Publication> list = new ArrayList(Util.partition(publicationList, itemsPerPage));
		boolean continuar = false;
		try {
			list = (List<Publication>) list.get(page-1);
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
				p.setPages((publicationList.size()+itemsPerPage-1)/itemsPerPage);
				p.setTotal(publicationList.size());
				p.setItemsPerPage(itemsPerPage);

				json = writer.writeValueAsString(p);

			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return Response.status(Response.Status.OK).entity(json).build();
		}else{
			JsonObject msj = Json.createObjectBuilder()
					.add("msj", "0").build();
			return Response.status(Response.Status.OK).entity(msj.toString()).build();
		}
	}
	
	@GET
	@Path("/publication/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getPublication(@HeaderParam("authorization") String authStringe, @PathParam("id") int id){
		if(!Util.parseToken(authStringe)){
			JsonObject msj = Json.createObjectBuilder()
					.add("error", "-1").build();
			return Response.status(Response.Status.UNAUTHORIZED).entity(msj.toString()).build();
		}

		if(id!=0){
			
			Publication publication = publicationService.getPublication(id);
			
			if(publication!=null){
				ObjectMapper mapper = new ObjectMapper();
				mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
				String result = null;
				try {
					final ObjectWriter writer = mapper.writer().withRootName("publication");
					result = writer.writeValueAsString(publication);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				return Response.status(Response.Status.OK).entity(result).build();
			}else{
				JsonObject msj = Json.createObjectBuilder()
						.add("msj", "no existen publicación").build();
				return Response.status(Response.Status.NOT_FOUND).entity(msj.toString()).build();
			}

		}else{
			JsonObject msj = Json.createObjectBuilder()
					.add("msj", "no existen publicación para id 0").build();
			return Response.status(Response.Status.NOT_FOUND).entity(msj.toString()).build();
		}
	}
	
	@DELETE
	@Path("/publication/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deletePublication(@HeaderParam("authorization") String authStringe, @PathParam("id") int id){
		if(!Util.parseToken(authStringe)){
			JsonObject msj = Json.createObjectBuilder()
					.add("error", "-1").build();
			return Response.status(Response.Status.UNAUTHORIZED).entity(msj.toString()).build();
		}

		publicationService.deletePublication(id, Util.parseTokenToUser(authStringe).getId());
		
		JsonObject msj = Json.createObjectBuilder()
				.add("msj", "no existen publicación").build();
		return Response.status(Response.Status.NOT_FOUND).entity(msj.toString()).build();
	}
	
	@POST
	@Path("/upload/{id}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
			@PathParam("id") int id,
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {

		String uploadedFileLocation = "C:/Users/jgudiño/Documents/workspace/petProject/backend/img/"
				+ fileDetail.getFileName();

		Publication issetPublication = publicationService.getPublication(id);
		if(issetPublication!=null){
			publicationService.updateFilePublication(id, fileDetail.getFileName());
		}
		// save it
		Util.writeToFile(uploadedInputStream, uploadedFileLocation);

		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
		issetPublication = publicationService.getPublication(id);
		try {
			String result = mapper.writeValueAsString(issetPublication);
			return Response.status(Response.Status.OK).entity(result.toString()).build();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
}