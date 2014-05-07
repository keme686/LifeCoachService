/**
 * 
 */
package it.unitn.lifecoach.storage.dao;


import it.unitn.lifecoach.model.MealLog;
import it.unitn.lifecoach.model.User;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
public class MealLogDAO {
	/**
	 *  entity manager object
	 */
	private EntityManager em;
	/**
	 * MealLogDAO singleton object 
	 */
	private static MealLogDAO mealogdao = new MealLogDAO();
	/**
	 * constructor: initializes entity manager
	 */
	public MealLogDAO() {
		em = Persistence.createEntityManagerFactory("it.unitn.lifecoach.model").createEntityManager();
	}
	public EntityManager getEntityManger(){
		return em;
	}
	/**
	 *  Creates a new Singleton instance
	 * @return a singleton {@link MealLogDAO} object
	 */
	public static MealLogDAO newInstance(){
		return mealogdao;
	}
	/**
	 * closes a connection to database set to entity manager {@link em}
	 */
	public void closeConnection(){
		  em.close();
	}
	/**
	 * saves a new {@link MealLog} data
	 * 
	 * @param log
	 * @return just saved {@link MealLog} object  
	 * @throws Exception
	 */
	public MealLog addMealLog(MealLog log) throws Exception{
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			em.persist(log);
			tx.commit();
			return log;
	}
	/**
	 * updates an existing Meal log data
	 * 
	 * @param log
	 * @return just updated {@link MealLog} object
	 * @throws Exception
	 */
	public MealLog update(MealLog log) throws Exception{
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.merge(log);
		tx.commit();
		return log;
	}
	/**
	 * deletes an existing Meal log data 
	 * 
	 * @param id
	 * @return true if success, false otherwise
	 * @throws Exception
	 */
	public boolean delete(long id) throws Exception {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		MealLog u = em.find(MealLog.class, id);
		em.remove(u);
		tx.commit();
		return true;
	}
	/**
	 * 
	 * @param id
	 * @return {@link MealLog} object identified by id, null otherwise
	 * @throws Exception
	 */
	public MealLog get(long id) throws Exception{
		MealLog u = em.find(MealLog.class, id);
		return u;
	}
	/**
	 * list of {@link MealLog} objects for a specified User
	 * @param username
	 * @return list of {@link MealLog} objects for a specified User
	 * @throws Exception
	 */
	public List<MealLog> getAll(String username) throws Exception{
		User u = em.find(User.class, username);
		if(u == null)
			return new ArrayList<>();
		List<MealLog> logs = em.createQuery("select u from MealLog u where u.user=:userId", MealLog.class).setParameter("userId", u).getResultList();
		return logs;
	}
	/**
	 * 
	 * @return list of {@link MealLog} stored in a database
	 * @throws Exception
	 */
	public List<MealLog> getAll() throws Exception{
		List<MealLog> logs = em.createQuery("select u from MealLog u", MealLog.class).getResultList();
		return logs;
	}
}
