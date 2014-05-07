/**
 * 
 */
package prova;

import java.util.Date;
import java.util.List;

import it.unitn.lifecoach.model.MealLog;
import it.unitn.lifecoach.model.User;
import it.unitn.lifecoach.storage.dao.MealLogDAO;
import it.unitn.lifecoach.storage.dao.UserDAO;

/**
 * @author kemele
 *
 */
public class TestMealLogDAO {

	public static void main(String arg[]){
		MealLog m = testAdd();
		if(m == null){
			System.out.println("Cannot add meal");
			return;
		}
		testGet(m.getId());
		m.setCalories(900);
		m.setName("Baked Beans");
		testUpdate(m);
		testGetAll();
		testGetByUser("elvis");
		testGetByUser("keme");
		testDelete(m.getId());
		System.out.println("After delete:");
		testGetAll();
	}
	public static MealLog testAdd(){
		try{
			MealLogDAO dao = MealLogDAO.newInstance();
			User u = UserDAO.newInstance().get("elvis");
			MealLog m = new MealLog();
			m.setName("Pasta");
			m.setCalories(800);
			m.setServingSize(1.0);
			m.setServingSizeUnit("oz");
			m.setServingTime("Lunch");
			m.setTypeOfMeal("Red");
			m.setUpdatedTime(new Date().getTime());
			m.setUser(u);
			MealLog log = dao.addMealLog(m);
			System.out.println("The following Meal logged:");
			printMeallog(log);
			return log;
		}catch(Exception e){
			System.out.println("testAdd()");
			e.printStackTrace();
		}
		return null;
	}
	public static void testGet(long id){
		try{
			MealLogDAO dao = MealLogDAO.newInstance();
			MealLog log = dao.get(id);
			System.out.println("The following log found:");
			printMeallog(log);
			
		}catch(Exception e){
			System.out.println("testGet()");
			e.printStackTrace();
		}
		
	}
	public static void testUpdate(MealLog m){
		try{
			MealLogDAO dao = MealLogDAO.newInstance();
			MealLog log = dao.update(m);
			System.out.println("the following meal log updated:");
			printMeallog(log);
		}catch(Exception e){
			System.out.println("testUpdate()");
			e.printStackTrace();
		}
		
	}
	public static void testDelete(long id){
		try{
			MealLogDAO dao = MealLogDAO.newInstance();
			MealLog log = dao.get(id);
			boolean status  = dao.delete(id);
			System.out.println("the following meal log is " + (status?"":"not ") + "deleted!");
			printMeallog(log);
		}catch(Exception e){
			System.out.println("testDelete()");
			e.printStackTrace();
		}
		
	}
	public static void testGetAll(){
		try{
			MealLogDAO dao = MealLogDAO.newInstance();
			List<MealLog> logs = dao.getAll();
			System.out.println("The following list of Meal logs found:");
			if(logs == null) {
				System.out.println("Null value found");
				return;
			}
			for(MealLog m: logs){
				printMeallog(m);
			}
		}catch(Exception e){
			System.out.println("testGetAll()");
			e.printStackTrace();
		}
		
	}
	public static void testGetByUser(String username){
		try{
			MealLogDAO dao = MealLogDAO.newInstance();
			List<MealLog> logs = dao.getAll(username);
			System.out.println("The following list of Meal logs found for a user:" + username);
			if(logs == null) {
				System.out.println("Null value found");
				return;
			}
			for(MealLog m: logs){
				printMeallog(m);
			}
		}catch(Exception e){
			System.out.println("testGetByUser()");
			e.printStackTrace();
		}		
	}
	
	public static void printMeallog(MealLog log){
		if(log == null){
			System.out.println("Null value found:");
			return;
		}
		System.out.println("_____________________________________________________________");
		System.out.println("User: " + log.getUser().getName());
		System.out.println("Name: " + log.getName());
		System.out.println("Serving Time: " + log.getServingTime());
		System.out.println("Serving size: " + log.getServingSize());
		System.out.println("Serving unit: " + log.getServingSizeUnit());
		System.out.println("Calories: " + log.getCalories());
		System.out.println("Update time: " + log.getUpdatedTime());
		System.out.println("Type of meal: " + log.getTypeOfMeal());
		System.out.println("-------------------------------------------------------------");
	}
}
