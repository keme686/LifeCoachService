/**
 * 
 */
package prova;

import java.util.Date;
import java.util.List;

import it.unitn.lifecoach.model.Goal;
import it.unitn.lifecoach.model.Measure;
import it.unitn.lifecoach.model.User;
import it.unitn.lifecoach.storage.dao.GoalDAO;
import it.unitn.lifecoach.storage.dao.MeasureDAO;
import it.unitn.lifecoach.storage.dao.UserDAO;

/**
 * @author kemele
 *
 */
public class TestGoalDAO {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Goal g = testAdd();
		if(g == null){
			System.out.println("Cant set goal");
			return;
		}
		testGet(g.getId());
		g.setTitle("Weight loss");
		g.setGoalValue(81.0);
		testUpdate(g);
		testGetAll();
		testGetByUser("keme");
		testGetByUser("elvis");
		testGetByUserMeasure("keme", 1);
		testDelete(g.getId());
		System.out.println("After delete!");
		testGetAll();
	}
	
	@SuppressWarnings("deprecation")
	public static Goal testAdd(){
		try{
			GoalDAO dao = GoalDAO.newInstance();
			Goal g = new Goal();
			g.setTitle("Loss Weight");
			g.setGoalTag("weight");
			g.setGoalType("Intensive");
			g.setInitialValue(89.0);
			g.setInitialValueUnit("kg");
			g.setGoalDate(new Date(114, 2, 10).getTime());
			g.setGoalValue(84.0);
			g.setGoalValueUnit("kg");
			g.setRate(0.07);
			g.setRateType("Daily");
			g.setRateUnit("kg");
			g.setUpdatedTime(new Date().getTime());
			g.setPlannedDirection(-1);
			g.setStatus("Active");
			User u = UserDAO.newInstance().get("elvis");
			Measure m = MeasureDAO.newInstance().get(1);
			g.setUser(u);
			g.setMeasure(m);
			
			Goal goal = dao.addGoal(g);
			System.out.println("The follwing goal added:");
			printGoal(goal);
			return goal;
		}catch(Exception e){
			System.out.println("testAdd()");
			e.printStackTrace();
		}
		return null;
	}
	public static void testGet(long id){
		try{
			GoalDAO dao = GoalDAO.newInstance();
			Goal g = dao.get(id);
			System.out.println("the following goal found:");
			printGoal(g);
		}catch(Exception e){
			System.out.println("testGet()");
			e.printStackTrace();
		}
		
	}
	public static void testUpdate(Goal m){
		try{
			GoalDAO dao = GoalDAO.newInstance();
			Goal g = dao.update(m);
			System.out.println("the following is updated:");
			printGoal(g);
		}catch(Exception e){
			System.out.println("testUpdate()");
			e.printStackTrace();
		}
		
	}
	public static void testDelete(long id){
		try{
			GoalDAO dao = GoalDAO.newInstance();
			Goal g = dao.get(id);
			boolean status = dao.delete(id);
			System.out.println("the following goal is " + (status?"":"not ") + "deleted!");
			printGoal(g);
		}catch(Exception e){
			System.out.println("testDelete()");
			e.printStackTrace();
		}
		
	}
	public static void testGetAll(){
		try{
			GoalDAO dao = GoalDAO.newInstance();
			List<Goal> goals = dao.getAll();
			if(goals == null){
				System.out.println("Null value found");
				return;
			}
			System.out.println("The following list of goals found");
			for(Goal g: goals){
				printGoal(g);
			}
		}catch(Exception e){
			System.out.println("testGetAll()");
			e.printStackTrace();
		}
		
	}
	public static void testGetByUser(String username){
		try{
			GoalDAO dao = GoalDAO.newInstance();
			List<Goal> goals = dao.getAll(username);
			if(goals == null){
				System.out.println("Null value found");
				return;
			}
			System.out.println("The following list of goals found for user: " + username);
			for(Goal g: goals){
				printGoal(g);
			}
		}catch(Exception e){
			System.out.println("testGetByUser()");
			e.printStackTrace();
		}		
	}
	public static void testGetByUserMeasure(String username, long mid){
		try{
			GoalDAO dao = GoalDAO.newInstance();
			List<Goal> goals = dao.getAll(username, mid);
			if(goals == null){
				System.out.println("Null value found");
				return;
			}
			System.out.println("The following list of goals found for user: " + username + " Measure id: " + mid);
			for(Goal g: goals){
				printGoal(g);
			}
		}catch(Exception e){
			System.out.println("testGetByUser()");
			e.printStackTrace();
		}		
	}
	
	
	
	public static void printGoal(Goal g){
		if(g==null){
			System.out.println("Null value sent to print:");
			return;
		}
		System.out.println("_________________________________________________________");
		System.out.println("ID: " + g.getId());
		System.out.println("User: " + g.getUser().getName());
		System.out.println("Title: " + g.getTitle());
		System.out.println("Goal tag: " + g.getGoalTag());
		
		System.out.println("Measure:(if any) :" + ((g.getMeasure()==null)?"-":g.getMeasure().getName()));
		System.out.println("Goal type: " + g.getGoalType());
		System.out.println("Initial Value: " + g.getInitialValue());
		System.out.println("Initial value Unit: " + g.getInitialValueUnit());
		System.out.println("Goal Value: " + g.getGoalValue());
		System.out.println("Goal value Unit: " + g.getGoalValueUnit());
		System.out.println("Goal date: " + g.getGoalDate());
		System.out.println("Goal rate: " + g.getRate());
		System.out.println("Goal rate unit: " + g.getRateUnit());
		System.out.println("Goal rate type: " + g.getRateType());
		System.out.println("Planned direction: " + g.getPlannedDirection());
		System.out.println("Update time: " + g.getUpdatedTime());
		System.out.println("Status: " + g.getStatus());
		
		System.out.println("------------------------------------------------------------");
		
	}
	
}
