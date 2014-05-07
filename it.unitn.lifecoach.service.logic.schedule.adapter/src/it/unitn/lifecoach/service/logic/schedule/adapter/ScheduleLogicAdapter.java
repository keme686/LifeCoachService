package it.unitn.lifecoach.service.logic.schedule.adapter;

import it.unitn.lifecoach.model.ScheduleSOAP;
import it.unitn.lifecoach.service.logic.schedule.ws.ScheduleLogic;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

/**
 * Adapter for the Schedule Logic Service
 * 
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
public class ScheduleLogicAdapter {
	
	private static final ScheduleLogic schdLogic;

	static {
		URL url = null;
		try {
			url = new URL("http://localhost:6905/ws/schdlogic?wsdl");
		
		} catch (MalformedURLException e) {
			System.out.println("Error service url not correct");
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		QName qname = new QName("http://ws.schedule.logic.service.lifecoach.unitn.it/", "ScheduleLogicService");		
        Service service = Service.create(url, qname);
        schdLogic = service.getPort(ScheduleLogic.class); 
	}
	
	/**
	 * 
	 * @param schedule
	 * @param username
	 * @return
	 */
	public ScheduleSOAP addSchedule(ScheduleSOAP schedule , String username){
		try{
		schedule=schdLogic.addSchedule(schedule, username);
		return schedule;
		}catch(Exception e){
			System.out.println(e.getLocalizedMessage());
			return null;
		}
	}
	
	/**
	 * 
	 * @param schedule
	 * @param id
	 * @param username
	 * @return
	 */
	public ScheduleSOAP updateSchedule(ScheduleSOAP schedule, long id, String username ){
		ScheduleSOAP schd;
		try {
			schd = schdLogic.updateSchedule(schedule, id, username);
			return schd;
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}	
		
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public ScheduleSOAP getSchedule(long id){
	
		ScheduleSOAP schd;
		try {
			schd = schdLogic.getSchedule(id);
			return schd;
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}	
		
	}
	
	/**
	 * 
	 * @param id
	 * @param username
	 * @return
	 */
	public String deleteSchedule(long id , String username){
		
		try {
			String message = schdLogic.deleteSchedule(username, id);
			return message;
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}	
	}
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	public ScheduleSOAP[] getAllForUser(String username){

		try {
			ScheduleSOAP[] schedules = schdLogic.getAllUser(username);
			return schedules;
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}	
	}
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	public ScheduleSOAP[] getAllForToday(String username){
		
		try {
			ScheduleSOAP[] schedules = schdLogic.getTodays(username);
			return schedules;
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}	
	}
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	public ScheduleSOAP[] needToBeRemind(String username){
	
		try {
			ScheduleSOAP[] schedules = schdLogic.remindUser(username);
			return schedules;
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}	
	}
	
	/**
	 * 
	 * @param username
	 * @param from
	 * @param to
	 * @return
	 */
	public ScheduleSOAP[] getSchedulesWithinInterval(String username, long from , long to){

		try {
			ScheduleSOAP[] schedules = schdLogic.withinInterval(username, from, to);
			return schedules;
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}	
	}
	
}
