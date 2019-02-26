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
import javax.ws.rs.PUT;
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
import com.mx.nj.pet.model.Appointment;
import com.mx.nj.pet.model.Pagination;
import com.mx.nj.pet.service.AppointmentService;
import com.mx.nj.pet.service.PetService;
import com.mx.nj.pet.util.Util;

@Component
@Path("/appointmentController")
public class AppointmentController {

	@Autowired(required=true) AppointmentService appointmentService;
	@Autowired(required=true) PetService petService;
	
	@POST
	@Path("/addAppointment")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveAppointment(@HeaderParam("authorization") String authStringe, Appointment appointment){
		if(!Util.parseToken(authStringe)){
			JsonObject msj = Json.createObjectBuilder()
					.add("error", "No tienes permiso para obtener informacion").build();
			return Response.status(Response.Status.UNAUTHORIZED).entity(msj.toString()).build();
		}
		if(appointment.getService()==0 || appointment.getFromDate()==null || appointment.getToDate()==null 
				|| appointment.getFromUser()==null || appointment.getToUser()==null
				|| appointment.getPet().getOwner()==null || appointment.getPet().getGender()==0
				|| appointment.getPet().getType()==0 || appointment.getPet().getSize()==0 
				|| appointment.getPet().getYears()==0) {
			JsonObject msj = Json.createObjectBuilder()
					.add("msj", "0").build();
			return Response.status(Response.Status.OK).entity(msj.toString()).build();
		}else {
			appointment.setStatus(1);
			appointment.getPet().setId(petService.addPet(appointment.getPet()).getId());
			appointmentService.addAppointment(appointment);
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
			String result = null;
			try {
				result = mapper.writeValueAsString(appointment);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return Response.status(Response.Status.OK).entity(result).build();
		}
	}

	@GET
	@Path("/myAppointments/{pageParam}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getReceivedAppointments(@HeaderParam("authorization") String authStringe, @PathParam("pageParam") int pageParam){
		if(!Util.parseToken(authStringe)){
			JsonObject msj = Json.createObjectBuilder()
					.add("error", "No tienes permiso para obtener informacion").build();
			return Response.status(Response.Status.UNAUTHORIZED).entity(msj.toString()).build();
		}

		List<Appointment> appointmentList = appointmentService.getMyAppointments(Util.parseTokenToUser(authStringe).getId());

		int page = 1;
		int itemsPerPage = 4;
		
		if(pageParam!=0) {
			page = pageParam;
		}

		List<Appointment> list = new ArrayList(Util.partition(appointmentList, itemsPerPage));
		boolean continuar = false;
		try {
			list = (List<Appointment>) list.get(page-1);
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
				p.setPages((appointmentList.size()+itemsPerPage-1)/itemsPerPage);
				p.setItemsPerPage(itemsPerPage);
				p.setTotal(appointmentList.size());

				json = writer.writeValueAsString(p);

			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return Response.status(Response.Status.OK).entity(json.toString()).build();
		}else{
			JsonObject msj = Json.createObjectBuilder()
					.add("msj", "0").build();
			return Response.status(Response.Status.OK).entity(msj.toString()).build();
		}
	}

	@GET
	@Path("/appointments/{pageParam}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getAppointments(@HeaderParam("authorization") String authStringe, @PathParam("pageParam") int pageParam){
		if(!Util.parseToken(authStringe)){
			JsonObject msj = Json.createObjectBuilder()
					.add("error", "No tienes permiso para obtener informacion").build();
			return Response.status(Response.Status.UNAUTHORIZED).entity(msj.toString()).build();
		}

		List<Appointment> appointmentList = appointmentService.getAppointments(Util.parseTokenToUser(authStringe).getId());

		int page = 1;
		int itemsPerPage = 4;

		if(pageParam!=0) {
			page = pageParam;
		}

		List<Appointment> list = new ArrayList(Util.partition(appointmentList, itemsPerPage));
		boolean continuar = false;
		try {
			list = (List<Appointment>) list.get(page-1);
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
				p.setPages((appointmentList.size()+itemsPerPage-1)/itemsPerPage);
				p.setItemsPerPage(itemsPerPage);
				p.setTotal(appointmentList.size());

				json = writer.writeValueAsString(p);

			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return Response.status(Response.Status.OK).entity(json.toString()).build();
		}else{
			JsonObject msj = Json.createObjectBuilder()
					.add("msj", "0").build();
			return Response.status(Response.Status.OK).entity(msj.toString()).build();
		}
	}

	@GET
	@Path("/appointment/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getAppointment(@HeaderParam("authorization") String authStringe, @PathParam("id") int id){
		if(!Util.parseToken(authStringe)){
			JsonObject msj = Json.createObjectBuilder()
					.add("error", "-1").build();
			return Response.status(Response.Status.UNAUTHORIZED).entity(msj.toString()).build();
		}

		if(id!=0){

			Appointment appointment = appointmentService.getAppointment(id);

			if(appointment!=null){
				ObjectMapper mapper = new ObjectMapper();
				mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
				String result = null;
				try {
					final ObjectWriter writer = mapper.writer().withRootName("message");
					result = writer.writeValueAsString(appointment);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				return Response.status(Response.Status.OK).entity(result).build();
			}else{
				JsonObject msj = Json.createObjectBuilder()
						.add("msj", "no existen cita").build();
				return Response.status(Response.Status.NOT_FOUND).entity(msj.toString()).build();
			}

		}else{
			JsonObject msj = Json.createObjectBuilder()
					.add("msj", "no existe cita para id 0").build();
			return Response.status(Response.Status.NOT_FOUND).entity(msj.toString()).build();
		}
	}

	@DELETE
	@Path("/appointment/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteAppointment(@HeaderParam("authorization") String authStringe, @PathParam("id") int id){
		if(!Util.parseToken(authStringe)){
			JsonObject msj = Json.createObjectBuilder()
					.add("error", "-1").build();
			return Response.status(Response.Status.UNAUTHORIZED).entity(msj.toString()).build();
		}

		appointmentService.changeAppointmentStatus(id, 0);

		JsonObject msj = Json.createObjectBuilder()
				.add("msj", "0").build();
		return Response.status(Response.Status.OK).entity(msj.toString()).build();
	}
	
	@PUT
	@Path("/appointment/{id}/{status}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response changeAppointmentStatus(@HeaderParam("authorization") String authStringe, @PathParam("id") int id, @PathParam("status") int status){
		if(!Util.parseToken(authStringe)){
			JsonObject msj = Json.createObjectBuilder()
					.add("error", "-1").build();
			return Response.status(Response.Status.UNAUTHORIZED).entity(msj.toString()).build();
		}

		appointmentService.changeAppointmentStatus(id, status);

		JsonObject msj = Json.createObjectBuilder()
				.add("msj", "0").build();
		return Response.status(Response.Status.OK).entity(msj.toString()).build();
	}
	
	@PUT
	@Path("/appointment/{id}/{rating}/{review}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response changeAppointmentReview(@HeaderParam("authorization") String authStringe, @PathParam("id") int id, @PathParam("rating") int rating, @PathParam("review") String review ){
		if(!Util.parseToken(authStringe)){
			JsonObject msj = Json.createObjectBuilder()
					.add("error", "-1").build();
			return Response.status(Response.Status.UNAUTHORIZED).entity(msj.toString()).build();
		}

		appointmentService.changeAppointmentReview(id, rating, review);

		JsonObject msj = Json.createObjectBuilder()
				.add("msj", "0").build();
		return Response.status(Response.Status.OK).entity(msj.toString()).build();
	}

}