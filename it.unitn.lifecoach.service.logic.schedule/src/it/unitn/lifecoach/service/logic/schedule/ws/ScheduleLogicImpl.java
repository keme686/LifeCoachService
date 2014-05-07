package it.unitn.lifecoach.service.logic.schedule.ws;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import it.unitn.lifecoach.model.Schedule;
import it.unitn.lifecoach.model.User;
import it.unitn.lifecoach.model.ScheduleSOAP;
import it.unitn.lifecoach.service.storage.local.adapter.ScheduleDataAdapter;
import it.unitn.lifecoach.service.storage.local.adapter.UserDataAdapter;

import javax.jws.WebService;

import org.joda.time.DateTime;

/**
 * Implementation Schedule Logic as SOAP Service
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
@WebService(endpointInterface = "it.unitn.lifecoach.service.logic.schedule.ws.ScheduleLogic",
serviceName="ScheduleLogicService")
public class ScheduleLogicImpl implements ScheduleLogic {

	ScheduleDataAdapter schad = new ScheduleDataAdapter();
	UserDataAdapter  usrad = new UserDataAdapter();
	
	/**
	 * Add new Schedule 
	 */
	@Override
	public ScheduleSOAP addSchedule(ScheduleSOAP scheduleSOAP, String username) {
		User user= usrad.getUser(username);
		if(user==null){
			return new ScheduleSOAP();
		}
	
		Schedule mdschd = toScheduleModel(scheduleSOAP);
		mdschd.setUser(user);
		mdschd.setUpdatedTime(new Date().getTime());
		mdschd=schad.addSchedule(mdschd);
		if(mdschd==null){
			return new ScheduleSOAP();
		}
		scheduleSOAP = toScheduleSOAP(mdschd);			
		return scheduleSOAP;
	}
	
	/**
	 * Update existing schedule 
	 */
	@Override
	public ScheduleSOAP updateSchedule(ScheduleSOAP scheduleSOAP, long id,
			String username) {
		User user= usrad.getUser(username);
		if(user==null){
			return new ScheduleSOAP();
		}
		if(schad.getSchedule(id)==null){
			return new ScheduleSOAP();	
		}
		Schedule mdschd = toScheduleModel(scheduleSOAP);
		mdschd.setUser(user);
		mdschd.setUpdatedTime(new Date().getTime());
		mdschd= schad.updateSchedule(id,mdschd);
		if(mdschd==null){
			return new ScheduleSOAP();
		}
		scheduleSOAP= toScheduleSOAP(mdschd);
		return scheduleSOAP;
	}

	/**
	 * Get a schedule by id
	 */
	@Override
	public ScheduleSOAP getSchedule(long id) {
		Schedule mdschd = schad.getSchedule(id);
		if(mdschd==null){
			return new ScheduleSOAP();
		}
		return toScheduleSOAP(mdschd);
	}

	/**
	 * Delete a schedule providing the id
	 */
	@Override
	public String deleteSchedule(String username, long id) {
		User user= usrad.getUser(username);
		if(user==null){
			return "User not found";
		}
		String msg = schad.deleteSchedule(id);
		return msg;
	}

	/**
	 * Get all the schedules for the user  
	 */
	@Override
	public ScheduleSOAP[] getAllUser(String username) {
		
		User user= usrad.getUser(username);
		if(user==null){
			return null;
		}
		
		List<Schedule> list= schad.getAll(username);
		Iterator<Schedule> itr = list.iterator();
		List<ScheduleSOAP> listSOAP = new ArrayList<ScheduleSOAP>();
		while(itr.hasNext()){
			Schedule mdschd = new Schedule();
			mdschd= itr.next();
			listSOAP.add(toScheduleSOAP(mdschd));
		}
		ScheduleSOAP[] schedules_array = new  ScheduleSOAP[list.size()];
		schedules_array = listSOAP.toArray(schedules_array);
		return schedules_array;
	}

	/**
	 * Get all todays schedules for the user  
	 */
	@Override
	public ScheduleSOAP[] getTodays(String username) {
		User user= usrad.getUser(username);
		if(user==null){
			return null;
		}
		
		List<Schedule> list= schad.getAll(username);
		List<ScheduleSOAP> listSOAP = new ArrayList<ScheduleSOAP>();
		Iterator<Schedule> itr = list.iterator();
		
		while(itr.hasNext()){
			Schedule mdschd = new Schedule();
			mdschd= itr.next();
			listSOAP.add(toScheduleSOAP(mdschd));
		}
		
		// find the group of schedules that are due today
		DateTime now =  new DateTime(new Date().getTime());
		now= now.withTimeAtStartOfDay();
		DateTime tomorrow = now.withTimeAtStartOfDay().plus(1000*60*60*24);
		
//		System.out.print("\nFind all shchedule from now: "+now.toString()+" to end of the day: "+tomorrow.toString());
//		System.out.print("\nFind all shchedule from now: "+now.getMillis()+" to end of the day: "+tomorrow.getMillis());
		List<ScheduleSOAP> todaysSchedules = new ArrayList<ScheduleSOAP>();
		
		Iterator<ScheduleSOAP> sit = listSOAP.iterator();
		while(sit.hasNext()){
			ScheduleSOAP schedule = new ScheduleSOAP();
			schedule = sit.next();
//			System.out.println("Schedule start time:"+schedule.getStartTime());
			if(schedule.getStartTime()<tomorrow.getMillis() && schedule.getStartTime()>=now.getMillis()){
				todaysSchedules.add(schedule);
			}
		}
		System.out.println();
		ScheduleSOAP[] array_schedules = new  ScheduleSOAP[todaysSchedules.size()];
		array_schedules = todaysSchedules.toArray(array_schedules);
		return array_schedules;
	}

	/**
	 * Check if there is any scheduled event due to an hour from now
	 */
	@Override
	public ScheduleSOAP[] remindUser(String username) {
		User user= usrad.getUser(username);
		if(user==null){
			return null;
		}
		
		List<Schedule> list= schad.getAll(username);
		Iterator<Schedule> itr = list.iterator();
		List<ScheduleSOAP> listSOAP = new ArrayList<ScheduleSOAP>();
		while(itr.hasNext()){
			Schedule mdschd = new Schedule();
			mdschd= itr.next();
			listSOAP.add(toScheduleSOAP(mdschd));
		}
		// find the group of schedules that are due to an hour
		long now =  new Date().getTime();
		long afterAnHour = now+3600000;
		
		List<ScheduleSOAP> dueToAnHour = new ArrayList<ScheduleSOAP>();
		Iterator<ScheduleSOAP> sit = listSOAP.iterator();
		while(sit.hasNext()){
			ScheduleSOAP schedule = new ScheduleSOAP();
			schedule = sit.next();
			if(schedule.getStartTime()<=afterAnHour && schedule.getStartTime()>now){
				dueToAnHour.add(schedule);
			}
		}
		
		ScheduleSOAP[] array_schedules = new  ScheduleSOAP[dueToAnHour.size()];
		array_schedules = dueToAnHour.toArray(array_schedules);
		return array_schedules;
	}

	/**
	 * Get list of schedules within the specified interval 
	 */
	@Override
	public ScheduleSOAP[] withinInterval(String username, long from, long to ) {
		
		User user= usrad.getUser(username);
		if(user==null){
			return null;
		}
		
		if(from>to){
			return null;
		}
		
		List<Schedule> list= schad.getAll(username);
		Iterator<Schedule> itr = list.iterator();
		List<ScheduleSOAP> listSOAP = new ArrayList<ScheduleSOAP>();
		while(itr.hasNext()){
			Schedule mdschd = new Schedule();
			mdschd= itr.next();
			listSOAP.add(toScheduleSOAP(mdschd));
		}
		
		List<ScheduleSOAP> withinInterval = new ArrayList<ScheduleSOAP>();
		
		Iterator<ScheduleSOAP> sit = listSOAP.iterator();
		while(sit.hasNext()){
			ScheduleSOAP schedule = new ScheduleSOAP();
			schedule = sit.next();
			if(schedule.getStartTime()<to && schedule.getStartTime()>=from){
				withinInterval.add(schedule);
			}
		}
		
		ScheduleSOAP[] array_schedules = new  ScheduleSOAP[withinInterval.size()];
		array_schedules = withinInterval.toArray(array_schedules);
		return array_schedules;
	}
	
	//There was the need to create a new class which share the same fields as the schedule class in the model
	//except the field user. This was need to be done because the SOAP was conflicting with 
	//annotation on the original Schedule class in the model project. There were cast exception thrown during 
	//the calls from client to this SOAP service. The two functions bellow do the transformation from an
	//object of one class to an object of the to other class.
	public ScheduleSOAP toScheduleSOAP(Schedule mdschd){
		
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
	
	public Schedule toScheduleModel(ScheduleSOAP scheduleSOAP){
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
