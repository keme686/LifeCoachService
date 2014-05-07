/**
 * 
 */
package prova;

import java.util.Date;
import java.util.List;

import it.unitn.lifecoach.model.Datapoint;
import it.unitn.lifecoach.storage.dao.DatapointDAO;
import it.unitn.lifecoach.storage.dao.MeasureDAO;

/**
 * @author kemele
 *
 */
public class TestDatapointDAO {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Datapoint d = testAdd();
		if(d == null){
			System.out.println("cant add datapoint");
			return;
		}
		testGet(d.getId());
		d.setComment("normal sleep");
		testUpdate(d);
		testGetAll();
		testGetByMeasure(9);
		testGetByGoal(1);
		testDelete(1);
		testDelete(d.getId());
		System.out.println("After delete!");
		testGetAll();
	}

	public static Datapoint testAdd(){
		try{
			DatapointDAO dao = DatapointDAO.newInstance();
			Datapoint dp = new Datapoint();
			dp.setTimestamp(new Date().getTime());
			dp.setComment("sleep log 1");
			dp.setUpdatedTime(new Date().getTime());
			dp.setValue(8);
			dp.setValueUnit("hour");
			dp.setMeasure(MeasureDAO.newInstance().get(9));
			
			Datapoint d = dao.add(dp);
			System.out.println("the follwing is added:");
			printData(d);
			return d;
		}catch(Exception e){
			System.out.println("testAdd()");
			e.printStackTrace();
		}
		return null;
	}
	public static void testGet(long id){
		try{
			DatapointDAO dao = DatapointDAO.newInstance();
			Datapoint d = dao.get(id);
			System.out.println("The follwing is found:");
			printData(d);
		}catch(Exception e){
			System.out.println("testGet()");
			e.printStackTrace();
		}
		
	}
	public static void testUpdate(Datapoint m){
		try{
			DatapointDAO dao = DatapointDAO.newInstance();
			Datapoint d = dao.update(m);
			System.out.println("the follwing is updated:");
			printData(d);
		}catch(Exception e){
			System.out.println("testUpdate()");
			e.printStackTrace();
		}
		
	}
	public static void testDelete(long id){
		try{
			DatapointDAO dao = DatapointDAO.newInstance();
			Datapoint d = dao.get(id);
			boolean status = dao.delete(id);
			System.out.println("the following datapoint is " + (status?"":"not ") + "deleted!");
			printData(d);
		}catch(Exception e){
			System.out.println("testDelete()");
			e.printStackTrace();
		}
		
	}
	public static void testGetAll(){
		try{
			DatapointDAO dao = DatapointDAO.newInstance();
			List<Datapoint> dps = dao.getAll();
			System.out.println("the follwing list of data found:");
			for(Datapoint d: dps){
				printData(d);
			}
		}catch(Exception e){
			System.out.println("testGetAll()");
			e.printStackTrace();
		}
		
	}
	public static void testGetByMeasure(long mid){
		try{
			DatapointDAO dao = DatapointDAO.newInstance();
			List<Datapoint> dps = dao.getAllMeasureDatapoints(mid);
			System.out.println("the follwing list of data found: measure id: " + mid);
			for(Datapoint d: dps){
 				printData(d);
			}
		}catch(Exception e){
			System.out.println("testGetAll()");
			e.printStackTrace();
		}
	}
	public static void testGetByGoal(long gid){
		try{
			DatapointDAO dao = DatapointDAO.newInstance();
			List<Datapoint> dps = dao.getAllGoalDatapoints(gid);
			System.out.println("the follwing list of data found: goal id: " + gid);
			for(Datapoint d: dps){
				printData(d);
			}
		}catch(Exception e){
			System.out.println("testGetAll()");
			e.printStackTrace();
		}
	}
	public static void printData(Datapoint d){
		if(d == null){
			System.out.println("Null value found");
			return;
		}
		System.out.println("______________________________________________________");
		System.out.println("ID: " + d.getId());
		System.out.println("User: " + ((d.getMeasure()==null?(d.getGoal()==null?"Null":d.getGoal().getUser().getName()):d.getMeasure().getUser().getName())));
		System.out.println("Measure: " + ((d.getMeasure()==null)?"Null":d.getMeasure().getName()));
		System.out.println("Goal: " + ((d.getGoal()==null)?"NUll":d.getGoal().getTitle()));		
		System.out.println("Timestamp: " + new Date(d.getTimestamp()));
		System.out.println("value: " + d.getValue());
		System.out.println("Value unit: " + d.getValueUnit());
		System.out.println("Comment: " + d.getComment());
		System.out.println("Update time: " + new Date(d.getUpdatedTime()));		
		System.out.println("-------------------------------------------------------");
	}
}
