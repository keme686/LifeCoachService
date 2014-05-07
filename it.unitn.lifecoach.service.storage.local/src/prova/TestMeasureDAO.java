/**
 * 
 */
package prova;

import java.util.Date;
import java.util.List;

import it.unitn.lifecoach.model.Measure;
import it.unitn.lifecoach.model.User;
import it.unitn.lifecoach.storage.dao.MeasureDAO;
import it.unitn.lifecoach.storage.dao.UserDAO;

/**
 * @author kemele
 *
 */
public class TestMeasureDAO {

	public static void main(String arg[]){
		Measure m = testAdd();
		if(m == null){
			System.out.println("cant add measure");
			return;
		}
		testGet(m.getId());
		m.setValue(89.0);
		testUpdate(m);
		testGetAll();
		testGetByUser("keme");
		testGetByUser("elvis");
		testDelete(m.getId());
		testGetAll();
	}
	public static Measure testAdd(){
		try{
			MeasureDAO dao = new MeasureDAO();
			Measure m = new Measure();
			User u = new UserDAO().get("elvis");
			m.setName("sleep");
			m.setUser(u);
			m.setValue(7);
			m.setValueUnit("hours");
			m.setUpdateTime(new Date().getTime());
			
			Measure measure = dao.addMeasure(m);
			System.out.println( "the following measure is added:");
			printMeasure(measure);
			return measure;
		}catch(Exception e){
			System.out.println("testAdd()");
			e.printStackTrace();
		}
		return null;
	}
	public static void testGet(long id){
		try{
			MeasureDAO dao = new MeasureDAO();
			Measure m = dao.get(id);
			System.out.println("The following measure is found:");
			printMeasure(m);
		}catch(Exception e){
			System.out.println("testGet()");
			e.printStackTrace();
		}
		
	}
	public static void testUpdate(Measure m){
		try{
			MeasureDAO dao = new MeasureDAO();
			Measure measure = dao.update(m);
			System.out.println("The follwing measure data updated:");
			printMeasure(measure);
		}catch(Exception e){
			System.out.println("testUpdate()");
			e.printStackTrace();
		}
		
	}
	public static void testDelete(long id){
		try{
			MeasureDAO dao = new MeasureDAO();
			Measure m = dao.get(id);
			boolean status = dao.delete(id);
			System.out.println("the following measure is " + (status?"":"not ") + "deleted!");
			printMeasure(m);
		}catch(Exception e){
			System.out.println("testDelete()");
			e.printStackTrace();
		}
		
	}
	public static void testGetAll(){
		try{
			MeasureDAO dao = new MeasureDAO();
			List<Measure> measures = dao.getAll();
			if(measures == null) return;
			System.out.println("The following list of measures found:");
			for(Measure m: measures){
				printMeasure(m);
			}
		}catch(Exception e){
			System.out.println("testGetAll()");
			e.printStackTrace();
		}
		
	}
	public static void testGetByUser(String username){
		try{
			MeasureDAO dao = new MeasureDAO();
			List<Measure> measures = dao.getAll(username);
			if(measures == null) return;
			System.out.println("The following list of measures for a " + username + " was found:");
			for(Measure m: measures){
				printMeasure(m);
			}
		}catch(Exception e){
			System.out.println("testGetByUser()");
			e.printStackTrace();
		}
		
	}
	private static void printMeasure(Measure m){
		
		if(m == null){
			System.out.println("Measure value Null");
			return;
		}
		System.out.println("________________________________________________________");
		System.out.println("User: " + m.getUser().getName());
		System.out.println("Measure Name: " + m.getName());
		System.out.println("Value: " + m.getValue());
		System.out.println("Unit of Measure: " + m.getValueUnit());
		System.out.println("Updated Time: " + new Date(m.getUpdateTime()));
		System.out.println("----------------------------------------------------------");
	}
}
