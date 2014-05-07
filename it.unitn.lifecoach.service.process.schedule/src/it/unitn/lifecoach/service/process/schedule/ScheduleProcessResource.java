package it.unitn.lifecoach.service.process.schedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import it.unitn.lifecoach.model.Schedule;
import it.unitn.lifecoach.model.ScheduleSOAP;
import it.unitn.lifecoach.service.logic.schedule.adapter.ScheduleLogicAdapter;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

/**
 * Process Service handling the schedule data requests from the UI
 * 
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
@Path("/schedule/process")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class ScheduleProcessResource {
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	/**
	 * Adapter to the Schedule Logic SOAP service
	 */
	private ScheduleLogicAdapter adapter = new ScheduleLogicAdapter();
	
	/**
	 * 
	 * @param schedule
	 * @param username
	 * @return
	 */
	@POST
	@Path("add")
	public Schedule addSchedule(Schedule schedule, @QueryParam("username") String username){
		
		if(schedule==null){
			return null;
		}
		ScheduleSOAP scheduleSOAP = toScheduleSOAP(schedule);
		scheduleSOAP = adapter.addSchedule(scheduleSOAP, username);
		if(scheduleSOAP==null){
			return null;	
		}
		return toScheduleModel(scheduleSOAP);
	}
	
	/**
	 * 
	 * @param schedule
	 * @param sid
	 * @param username
	 * @return
	 */
	@PUT
	@Path("update")
	public Schedule updateSchedule(Schedule schedule, @QueryParam("sid") long sid,
												 			@QueryParam("username") String username){
		if(schedule==null){
			return null;
		}
		ScheduleSOAP scheduleSOAP = toScheduleSOAP(schedule);
		scheduleSOAP=adapter.updateSchedule(scheduleSOAP, sid, username);
		if(scheduleSOAP==null){
			return null;
		}
		return toScheduleModel(scheduleSOAP);
	}
	
	/**
	 * 
	 * @param sid
	 * @return
	 */
	@GET
	@Path("get")
	public Schedule getSchedule(@QueryParam("sid") long sid){
		ScheduleSOAP scheduleSOAP= adapter.getSchedule(sid);
		return toScheduleModel(scheduleSOAP);
	}
	
	/**
	 * 
	 * @param sid
	 * @param username
	 * @return
	 */
	@DELETE
	@Path("del")
	public String delSchedule(@QueryParam("sid") long sid, @QueryParam("username") String username){
		return adapter.deleteSchedule(sid, username);
	}
	
	/**
	 * 
	 * @param from
	 * @param to
	 * @param username
	 * @return
	 */
	@GET
	@Path("interval")
	public List<Schedule> getWithinInterval(@QueryParam("from") long from , @QueryParam("to") long to ,
			@QueryParam("username") String username){
		
		ScheduleSOAP[] schedules = adapter.getSchedulesWithinInterval(username, from, to);
		if(schedules==null){
			return null;
		}
		
		List<ScheduleSOAP> soap_list = Arrays.asList(schedules);
		List<Schedule> response = new ArrayList<Schedule>();
		
		Iterator<ScheduleSOAP> itr= soap_list.iterator();
		while(itr.hasNext()){
			ScheduleSOAP scheduleSOAP = new ScheduleSOAP();
			scheduleSOAP = itr.next();
			response.add(toScheduleModel(scheduleSOAP));
		}
		
		return response;
	}
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	@GET
	@Path("today")
	public List<Schedule> getAllForToday(@QueryParam("username") String username){
		
		ScheduleSOAP[] schedules = adapter.getAllForToday(username);
		if(schedules==null){
			return null;
		}
		
		List<ScheduleSOAP> soap_list = Arrays.asList(schedules);
		List<Schedule> response = new ArrayList<Schedule>();
		
		Iterator<ScheduleSOAP> itr= soap_list.iterator();
		while(itr.hasNext()){
			ScheduleSOAP scheduleSOAP = new ScheduleSOAP();
			scheduleSOAP = itr.next();
			response.add(toScheduleModel(scheduleSOAP));
		}
		
		return response;
	}
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	@GET
	@Path("remind")
	public List<Schedule> getWithinNextHour(@QueryParam("username") String username){
		
		ScheduleSOAP[] schedules = adapter.needToBeRemind(username);
		if(schedules==null){
			return null;
		}
		List<ScheduleSOAP> soap_list = Arrays.asList(schedules);
		List<Schedule> response = new ArrayList<Schedule>();
		
		Iterator<ScheduleSOAP> itr= soap_list.iterator();
		while(itr.hasNext()){
			ScheduleSOAP scheduleSOAP = new ScheduleSOAP();
			scheduleSOAP = itr.next();
			response.add(toScheduleModel(scheduleSOAP));
		}
		
		return response;
	}
	
	//There was the need to create a new class which share the same fields as the schedule class in the model
	//except the field user. This was need to be done because the SOAP was conflicting with 
	//annotation on the original Schedule class in the model project. There were cast exception thrown during 
	//the calls from client to this SOAP service. The two functions bellow do the transformation from an
	//object of one class to an object of the to other class.
	private ScheduleSOAP toScheduleSOAP(Schedule mdschd){
		
		try{
			ScheduleSOAP scheduleSOAP = new ScheduleSOAP();
			scheduleSOAP.setId(mdschd.getId());
			scheduleSOAP.setDesc(mdschd.getDesc());
			scheduleSOAP.setTitle(mdschd.getTitle());
			scheduleSOAP.setStartTime(mdschd.getStartTime());
			scheduleSOAP.setEndTime(mdschd.getEndTime());
			scheduleSOAP.setType(mdschd.getType());
			scheduleSOAP.setUpdatedTime(mdschd.getUpdatedTime());
			return scheduleSOAP;
		}catch(NullPointerException e){
			return new ScheduleSOAP();
		}
	
	}
	
	private Schedule toScheduleModel(ScheduleSOAP scheduleSOAP){
		try{
			Schedule mdschd = new Schedule();
			mdschd.setId(scheduleSOAP.getId());
			mdschd.setDesc(scheduleSOAP.getDesc());
			mdschd.setTitle(scheduleSOAP.getTitle());
			mdschd.setStartTime(scheduleSOAP.getStartTime());
			mdschd.setEndTime(scheduleSOAP.getEndTime());
			mdschd.setType(scheduleSOAP.getType());
			mdschd.setUpdatedTime(scheduleSOAP.getUpdatedTime());
			return mdschd;
		}catch(NullPointerException e){
			return new Schedule();
		}
	}
}
