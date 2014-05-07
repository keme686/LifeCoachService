/**
 * 
 */
package it.unitn.lifecoach.storage.dao;

import it.unitn.lifecoach.model.Schedule;
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
public class ScheduleDAO {

	/**
	 *  entity manager object
	 */
	private EntityManager em;
	/**
	 * ScheduleDAO singleton object 
	 */
	private static ScheduleDAO schduledao = new ScheduleDAO();
	/**
	 * constructor: initializes entity manager
	 */
	private ScheduleDAO() {
		em = Persistence.createEntityManagerFactory("it.unitn.lifecoach.model").createEntityManager();
	}
	public EntityManager getEntityManger(){
		return em;
	}
	/**
	 *  Creates a new Singleton instance
	 * @return a singleton {@link ScheduleDAO} object
	 */
	public static ScheduleDAO newInstance(){
		return schduledao;
	}
	
	/**
	 * closes a connection to database set to entity manager {@link em}
	 */
	public void closeConnection(){
		  em.close();
	}
	
	/**
	 * saves a new {@link Schedule} data
	 * 
	 * @param schd
	 * @return just saved {@link Schedule} object  
	 * @throws Exception
	 */
	public Schedule addSchedule(Schedule schd) throws Exception{
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			em.persist(schd);
			tx.commit();
			return schd;
	}
	
	/**
	 * updates an existing Schedule
	 * 
	 * @param schd
	 * @return just updated {@link Schedule} object
	 * @throws Exception
	 */
	public Schedule update(Schedule schd) throws Exception{
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.merge(schd);
		tx.commit();
		return schd;
	}
	
	/**
	 * deletes an existing Schedule 
	 * 
	 * @param id
	 * @return true if success, false otherwise
	 * @throws Exception
	 */
	public boolean delete(long id) throws Exception {
		Schedule schd = em.find(Schedule.class, id);
		if(schd==null) return true;
		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.remove(schd);
		tx.commit();
		return true;
	}
	
	/**
	 * 
	 * @param id
	 * @return {@link Schedule} object identified by id, null otherwise
	 * @throws Exception
	 */
	public Schedule get(long id) throws Exception{
		Schedule u = em.find(Schedule.class, id);
		return u;
	}
	
	/**
	 * list of {@link Schedule} objects for a specified User
	 * @param username
	 * @return list of {@link Schedule} objects for a specified User
	 * @throws Exception
	 */
	public List<Schedule> getAll(String username) throws Exception{
		User u = em.find(User.class, username);
		if(u == null)
			return new ArrayList<>();
		List<Schedule> schedules = em.createQuery("select u from Schedule u where u.user=:userId", Schedule.class).setParameter("userId", u).getResultList();
		return schedules;
	}
	
	/**
	 * 
	 * @return list of {@link Schedule} stored in a database
	 * @throws Exception
	 */
	public List<Schedule> getAll() throws Exception{
		List< Schedule> schedules = em.createQuery("select u from Schedule u",  Schedule.class).getResultList();
		return schedules;
	}
}
