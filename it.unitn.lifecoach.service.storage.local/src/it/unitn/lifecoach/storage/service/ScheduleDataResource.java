/**
 * 
 */
package it.unitn.lifecoach.storage.service;

import java.util.List;

import it.unitn.lifecoach.model.Schedule;
import it.unitn.lifecoach.storage.dao.ScheduleDAO;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
@Path("/scheduledata")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class ScheduleDataResource {
	
	ScheduleDAO schdao = ScheduleDAO.newInstance();
	
	/**
	 * End point to that accepts requests to add new schedule to the database
	 * @param schedule
	 * @return
	 */
	@POST
	@Path("add")
	public Schedule addSchedule(Schedule schedule){
		
		try {
			schedule=schdao.addSchedule(schedule);
			return schedule;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * End point to that accepts requests to update an existing schedule to the database
	 * @param sid
	 * @param schedule
	 * @return
	 */
	@PUT
	@Path("{sid}/update")
    public Schedule updateSchedule(@PathParam("sid") long sid, Schedule schedule){
		try{
			schedule=schdao.update(schedule);
			return schedule;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@DELETE
	@Path("{sid}/del")
	public String deleteSchedule(@PathParam("sid") long sid){
		try{
			boolean st = schdao.delete(sid);
			return "{\"deletion\":"+ st + "}";
		}catch(Exception e){
			return "{\"Error\":\"Error while deleting measure\"}";
		}
	}

	/**
	 * 
	 * @param sid
	 * @return
	 */
	@GET
	@Path("{sid}")
	public Schedule getSchedule(@PathParam("sid") long sid){
		try{
			return schdao.get(sid);
		}catch(Exception e){
			return null;
		}
	}
	
	@GET
	@Path("all")
	public List<Schedule> getAllSchedules(){
		
		try {
			return schdao.getAll();
		} catch (Exception e) {
			return null;
		}	
	}
	
	@GET
	@Path("{username}/all")
	public List<Schedule> getAllSchedules(@PathParam("username") String username){
		try {
			return schdao.getAll(username);
		} catch (Exception e) {
			return null;
		}
	}
}
