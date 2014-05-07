/**
 * 
 */
package it.unitn.lifecoach.storage.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import it.unitn.lifecoach.model.MealLog;
import it.unitn.lifecoach.storage.dao.MealLogDAO;

/**
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
@Path("/meallogdata")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class MealLogDataResource {

	private MealLogDAO mlogdao = new MealLogDAO();
	/**
	 * 
	 * @param log
	 * @return
	 */
	@POST
	@Path("add")
	public MealLog addMeal(MealLog log){
		try{
			return mlogdao.addMealLog(log);
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * 
	 * @param mid
	 * @param log
	 * @return
	 */
	@PUT
	@Path("{mid}/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public MealLog updateMeal(@PathParam("mid")long mid, MealLog log){
		try{
			return mlogdao.update(log);
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * 
	 * @param mid
	 * @return
	 */
	@DELETE
	@Path("{mid}/del")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteMeal(@PathParam("mid")long mid){
		try{
			boolean st = mlogdao.delete(mid);
			return "{\"deletion\":"+ st + "}";
		}catch(Exception e){
			return "{\"Error\":\"Error while deleting Meal Log\"}";
		}
	}
	/**
	 * 
	 * @param username
	 * @return
	 */
	@GET
	@Path("{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<MealLog> getMealLogs(@PathParam("username")String username){
		try{
			return mlogdao.getAll(username);
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * 
	 * @return
	 */
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<MealLog> getAllMealLogs(){
		try{
			return mlogdao.getAll();
		}catch(Exception e){
			return null;
		}
	}
}
