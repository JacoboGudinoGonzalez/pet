package com.mx.nj.pet.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mx.nj.pet.model.Follow;
import com.mx.nj.pet.model.FollowList;
import com.mx.nj.pet.model.ServicePet;
import com.mx.nj.pet.model.Usuario;
import com.mx.nj.pet.service.FollowService;
import com.mx.nj.pet.service.PublicationService;
import com.mx.nj.pet.service.UsuarioService;
import com.mx.nj.pet.util.Util;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@Component
@Path("/controller")
public class LoginController {

	@Autowired(required=true) UsuarioService usuarioService;
	@Autowired(required=true) FollowService followService;
	@Autowired(required=true) PublicationService publicationService;

	private String createToken(Usuario user){
		if((user.getName()!=null && user.getName()!="") && (user.getPassword()!=null && user.getPassword()!="") 
				&& (user.getType()!=null && user.getType()!="") && (user.getEmail()!=null && user.getEmail()!="")){
			String KEY = "token_petNJ";
			long tiempo = System.currentTimeMillis();
			String jwt = Jwts.builder()
					.signWith(SignatureAlgorithm.HS256, KEY)
					.setSubject(user.getName())
					.setIssuedAt(new Date(tiempo))
					.setExpiration(new Date(tiempo+1800000))
					.claim("email", user.getEmail())
					.claim("id", user.getId())
					.compact();
			return jwt;
		}
		return null;
	}

	@POST
	@Path("/registerUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registerUser(Usuario user){
		if((user.getName()!=null && user.getName()!="") 
				&& (user.getPassword()!=null && user.getPassword()!="") 
				&& (user.getType()!=null && user.getType()!="") 
				&& (user.getEmail()!=null && user.getEmail()!="")
				&& (user.getTel()!=null && user.getTel()!="")){
			if(usuarioService.getUserByEmail(user.getEmail())!=null){
				JsonObject msj = Json.createObjectBuilder()
						.add("msj", "Usuario ya registrado anteriormente").build();
				return Response.status(Response.Status.OK).entity(msj.toString()).build();
			}else{
				Usuario newUser = new Usuario();
				newUser.setName(user.getName());
				newUser.setEmail(user.getEmail());
				newUser.setPassword(Util.hash(user.getPassword()));
				newUser.setType(user.getType());
				newUser.setTel(user.getTel());
				usuarioService.addUsuario(newUser);
				ObjectMapper mapper = new ObjectMapper();
				mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
				String result = null;
				try {
					result = mapper.writeValueAsString(user);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				return Response.status(Response.Status.OK).entity(result).build();
			}
		}else{
			JsonObject msj = Json.createObjectBuilder()
					.add("msj", "Introduce los datos correctamente").build();
			return Response.status(Response.Status.OK).entity(msj.toString()).build();
		}
	}

	@POST
	@Path("/loginUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(Usuario user){
		if((user.getEmail()!=null && user.getEmail()!="") && (user.getPassword()!=null && user.getPassword()!="")){
			Usuario issetUser = usuarioService.getUserByEmail(user.getEmail());
			if(issetUser!=null){
				if(Util.verifyAndUpdateHash(user.getPassword(), issetUser.getPassword(), Util.update)){
					ObjectMapper mapper = new ObjectMapper();
					mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
					String result = null;
					try {
						result = mapper.writeValueAsString(issetUser);
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}
					if(user.getGetToken()!=null){
						JsonObject msj = Json.createObjectBuilder()
								.add("token", createToken(issetUser)).build();
						return Response.status(Response.Status.OK).entity(msj.toString()).build();
					}else{
						return Response.status(Response.Status.OK).entity(result).build();
					}
				}else{
					JsonObject msj = Json.createObjectBuilder()
							.add("msj", "ContraseÃ±a incorrecta").build();
					return Response.status(Response.Status.NOT_FOUND).entity(msj.toString()).build();
				}
			}else{
				JsonObject msj = Json.createObjectBuilder()
						.add("msj", "Usuario no existe").build();
				return Response.status(Response.Status.NOT_FOUND).entity(msj.toString()).build();
			}
		}else{
			JsonObject msj = Json.createObjectBuilder()
					.add("msj", "Introduce los datos correctamente").build();
			return Response.status(Response.Status.OK).entity(msj.toString()).build();
		}
	}

	@PUT
	@Path("/updateUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser(@HeaderParam("authorization") String authStringe, Usuario user){
		if(!Util.parseToken(authStringe)){
			JsonObject msj = Json.createObjectBuilder()
					.add("error", "-1").build();
			return Response.status(Response.Status.UNAUTHORIZED).entity(msj.toString()).build();
		}
		if((user.getName()!=null && user.getName()!="") 
				&& (user.getType()!=null && user.getType()!="") && (user.getEmail()!=null && user.getEmail()!="")){
			Usuario issetUser = usuarioService.getUserById(user.getId());
			if(issetUser!=null){
				user.setPassword(issetUser.getPassword());
				usuarioService.updateUsuario(user);
				ObjectMapper mapper = new ObjectMapper();
				mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
				String result = null;
				try {
					result = mapper.writeValueAsString(user);
					return Response.status(Response.Status.OK).entity(result.toString()).build();
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			}else{
				JsonObject msj = Json.createObjectBuilder()
						.add("msj", "Usuario no existe").build();
				return Response.status(Response.Status.NOT_FOUND).entity(msj.toString()).build();
			}
		}else{
			JsonObject msj = Json.createObjectBuilder()
					.add("msj", "Introduce los datos correctamente").build();
			return Response.status(Response.Status.OK).entity(msj.toString()).build();
		}
		return null;
	}

	@POST
	@Path("/upload/{id}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
			@PathParam("id") int id,
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {

		String uploadedFileLocation = "C:/Users/jgudiño/Documents/workspace/petProject/backend/img/"
//		String uploadedFileLocation = "/Users/JACOBO/Documents/images/"
				+ fileDetail.getFileName();

		Usuario issetUser = usuarioService.getUserById(id);
		if(issetUser!=null){
			usuarioService.updateImageUser(id, fileDetail.getFileName());
		}
		// save it
		Util.writeToFile(uploadedInputStream, uploadedFileLocation);

		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
		issetUser = usuarioService.getUserById(id);
		try {
			String result = mapper.writeValueAsString(issetUser);
			return Response.status(Response.Status.OK).entity(result.toString()).build();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@GET
	@Path("/getImageFile/{imageFile}")
	@Produces("image/png")
	public Response getImageFile(@PathParam("imageFile") String imageFile){
		File file = new File("C:/Users/jgudiño/Documents/workspace/petProject/backend/img/"+imageFile);
//		File file = new File("/Users/JACOBO/Documents/images/"+imageFile);
		if(file.exists() && !file.isDirectory()) { 
			ResponseBuilder response = Response.ok((Object) file);
			response.header("Content-Disposition",
					"attachment; filename=image_from_server.png");
			return response.build();
		}else {
			imageFile = "default.jpg";
			file = new File("C:/Users/jgudiño/Documents/workspace/petProject/backend/img/"+imageFile);
//			file = new File("/Users/JACOBO/Documents/images/"+imageFile);
			ResponseBuilder response = Response.ok((Object) file);
			response.header("Content-Disposition",
					"attachment; filename=image_from_server.png");
			return response.build();
		}
		
	}

	@GET
	@Path("/users/{type}/{pageParam}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getUsers(@HeaderParam("authorization") String authStringe, @PathParam("type") String type, @PathParam("pageParam") int pageParam){
		if(!Util.parseToken(authStringe)){
			JsonObject msj = Json.createObjectBuilder()
					.add("error", "-1").build();
			return Response.status(Response.Status.UNAUTHORIZED).entity(msj.toString()).build();
		}
		if(type!=null){

			List<Usuario> userList = usuarioService.getAllUsers(type);
			int itemsPerPage = 5;
			int page = 1;
			if(pageParam!=0) {
				page = pageParam;
			}
			List<Usuario> list = new ArrayList(Util.partition(userList, itemsPerPage));
			boolean continuar = false;
			try {
				list = (List<Usuario>) list.get(page-1);
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

					FollowList f = new FollowList();
					f.setItem(list);
					f.setPages((userList.size()+itemsPerPage-1)/itemsPerPage);
					f.setTotal(userList.size());
					List<Follow> issetMyFollows = followService.getMyFollows(Util.parseTokenToUser(authStringe).getId());
					List<Follow> issetMyFollowed = followService.getMyFollowed(Util.parseTokenToUser(authStringe).getId());
					
					List<Usuario> myFollows = issetMyFollows.stream().map(Follow::getFollowed).collect(Collectors.toList());
					List<Integer> myFollowsIds = myFollows.stream().map(Usuario::getId).collect(Collectors.toList());
					f.setFollowing(myFollowsIds);
					List<Usuario> myFollowed = issetMyFollowed.stream().map(Follow::getFollowed).collect(Collectors.toList());
					List<Integer> myFollowedIds = myFollowed.stream().map(Usuario::getId).collect(Collectors.toList());
					f.setFollowed(myFollowedIds);
					json = writer.writeValueAsString(f);

				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				return Response.status(Response.Status.OK).entity(json.toString()).build();
			}else{
				JsonObject msj = Json.createObjectBuilder()
						.add("msj", "no existen usuarios para pagina: "+(page-1)).build();
				return Response.status(Response.Status.NOT_FOUND).entity(msj.toString()).build();
			}
		}
		JsonObject msj = Json.createObjectBuilder()
				.add("msj", "error al consultar lista de usuarios").build();
		return Response.status(Response.Status.OK).entity(msj.toString()).build();
	}

	@GET
	@Path("/getUsersService/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getAllUsers(@PathParam("id") int id){
		if(id!=0){
			List<ServicePet> servicePetList = usuarioService.getUsersService(id);
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
			String json = null;
			try {
				final ObjectWriter writer = mapper.writer().withRootName("service");
				json = writer.writeValueAsString(servicePetList);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return Response.status(Response.Status.OK).entity(json.toString()).build();
		}
		JsonObject msj = Json.createObjectBuilder()
				.add("msj", "error al consultar lista de usuarios").build();
		return Response.status(Response.Status.OK).entity(msj.toString()).build();
	}

	@GET
	@Path("/user/{followId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getUser(@HeaderParam("authorization") String authStringe, @PathParam("followId") int followId) throws JsonProcessingException, IOException{
		if(!Util.parseToken(authStringe)){
			JsonObject msj = Json.createObjectBuilder()
					.add("error", "-1").build();
			return Response.status(Response.Status.UNAUTHORIZED).entity(msj.toString()).build();
		}
		if(followId!=0){
			Usuario issetUser = usuarioService.getUserById(followId);
			Follow issetFollowing = followService.getFollow(Util.parseTokenToUser(authStringe).getId(), followId);
			Follow issetFollowed = followService.getFollow(followId, Util.parseTokenToUser(authStringe).getId());
			if(issetUser!=null){
				ObjectMapper mapper = new ObjectMapper();
				ArrayNode arrayNode = mapper.createArrayNode();
				ObjectNode usuario = mapper.createObjectNode();
					usuario.put("user", mapper.convertValue(issetUser, JsonNode.class));
				ObjectNode following = mapper.createObjectNode();
					following.put("following", mapper.convertValue(issetFollowing, JsonNode.class));
				ObjectNode followed = mapper.createObjectNode();
					followed.put("followed", mapper.convertValue(issetFollowed, JsonNode.class));
				arrayNode.add(usuario);
				arrayNode.add(following);
				arrayNode.add(followed);
				return Response.status(Response.Status.OK).entity(arrayNode.toString()).build();
			}else{
				JsonObject msj = Json.createObjectBuilder()
						.add("msj", "Usuario no existe").build();
				return Response.status(Response.Status.NOT_FOUND).entity(msj.toString()).build();
			}
		}else{
			JsonObject msj = Json.createObjectBuilder()
					.add("msj", "Introduce los datos correctamente").build();
			return Response.status(Response.Status.OK).entity(msj.toString()).build();
		}

	}

	@GET
	@Path("/counters/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getCounters(@HeaderParam("authorization") String authStringe, @PathParam("id") int id){
		if(!Util.parseToken(authStringe)){
			JsonObject msj = Json.createObjectBuilder()
					.add("error", "-1").build();
			return Response.status(Response.Status.UNAUTHORIZED).entity(msj.toString()).build();
		}
		if(id!=0){
			int[] followCounters = followService.getFollowCounters(id);
			int publicationCounters = publicationService.getPublicationCounters(id);
			if(followCounters!=null){
				ObjectMapper mapper = new ObjectMapper();
				ObjectNode counters = mapper.createObjectNode();
				counters.put("following", followCounters[0]);
				counters.put("followed", followCounters[1]);
				counters.put("publications", publicationCounters);
				String result = null;
				try {
					result = mapper.writeValueAsString(counters);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				return Response.status(Response.Status.OK).entity(result).build();
			}else{
				JsonObject msj = Json.createObjectBuilder()
						.add("msj", "Usuario no existe").build();
				return Response.status(Response.Status.NOT_FOUND).entity(msj.toString()).build();
			}
		}else{
			JsonObject msj = Json.createObjectBuilder()
					.add("msj", "Introduce los datos correctamente").build();
			return Response.status(Response.Status.OK).entity(msj.toString()).build();
		}

	}
}