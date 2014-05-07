/**
 * 
 */
package prova;

import it.unitn.lifecoach.model.Schedule;
import it.unitn.lifecoach.model.User;
import it.unitn.lifecoach.storage.dao.ScheduleDAO;
import it.unitn.lifecoach.storage.dao.UserDAO;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author Elvis Koci <elvkoci@gmail.com>
 *
 */
public class TestScheduleDAO {

	private static ScheduleDAO dao = ScheduleDAO.newInstance();
	
	public static void main(String args[]){
		
		Schedule sch= testAdd();
		if(sch == null){
			System.out.println("cant add schedule");
			return;
		}
		sch.setDesc("Appointment with Doctor John, at Via Roma 55");
		testUpdate(sch);
		testGet(sch.getId());
		testGetAll();
		testGetByUser("elvis");
		testDelete(sch.getId());
		testGetAll();
	}
	
	public static Schedule testAdd(){
		try{
			Schedule sch = new Schedule();
			sch.setTitle("Appointment Doctor");
			sch.setDesc("Appointment with Doctor J.Brao, at Via Roma 55");
			sch.setStartTime(new Date().getTime());
			sch.setEndTime(new Date().getTime());
			sch.setType("Appointment");
			sch.setUpdatedTime(new Date().getTime());
			User u = new UserDAO().get("elvis");
			sch.setUser(u);
			sch =dao.addSchedule(sch);
			System.out.println( "the following schedule is added:");
			printSchedule(sch);
			return sch;
		}catch(Exception e){
			System.out.println("testAdd()");
			e.printStackTrace();
		}
		return null;
		
	}
	public static void testGet(long id){
		
		Schedule schd;
		try {
			schd = dao.get(id);
			System.out.println("\nGET:");
			printSchedule(schd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	public static void testUpdate(Schedule schd){
		
		try {
			dao.update(schd);
			System.out.println("\nUPDATE:");
			printSchedule(schd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void testDelete(long id){
		
		try {
			boolean status=dao.delete(id);
			System.out.println("\nthe following measure is " + (status?"":"not ") + "deleted!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void testGetAll(){
		
		try {
			List<Schedule> list=dao.getAll();
			Iterator<Schedule> itr= list.iterator();
			System.out.println("\nLIST OF ALL SCHEDULES: ");
			while (itr.hasNext()){
				Schedule schd = itr.next();
				printSchedule(schd);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void testGetByUser(String username){
		
		try {
			List<Schedule> list=dao.getAll(username);
			Iterator<Schedule> itr= list.iterator();
			System.out.println("\nLIST OF ALL SCHEDULES FOR USER "+username+": ");
			while (itr.hasNext()){
				Schedule schd = itr.next();
				printSchedule(schd);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private static void printSchedule(Schedule s){
		
		if(s == null){
			System.out.println("Schedule value Null");
			return;
		}
		System.out.println("________________________________________________________");
		System.out.println("User: " + s.getUser().getName());
		System.out.println("Title: " + s.getTitle());
		System.out.println("Desc: " + s.getDesc());
		System.out.println("End: "+ s.getEndTime());
		System.out.println("Type: "+ s.getType());
		System.out.println("----------------------------------------------------------");
	}
}
