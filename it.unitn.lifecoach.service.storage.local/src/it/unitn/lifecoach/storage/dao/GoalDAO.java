/**
 * 
 */
package it.unitn.lifecoach.storage.dao;

import it.unitn.lifecoach.model.Goal;
import it.unitn.lifecoach.model.Measure;
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
public class GoalDAO {

	/**
	 *  entity manager object
	 */
	private EntityManager em;
	/**
	 * GoalDAO singleton object 
	 */
	private static GoalDAO goaldao = new GoalDAO();
	/**
	 * Constructor: initializes entity manager
	 */
	public GoalDAO() {
		em = Persistence.createEntityManagerFactory("it.unitn.lifecoach.model").createEntityManager();
	}
	
	public EntityManager getEntityManger(){
		return em;
	}
	/**
	 *  Creates a new Singleton instance
	 * @return a singleton {@link GoalDAO} object
	 */
	public static GoalDAO newInstance(){
		return goaldao;
	}
	/**
	 * closes a connection to database set to entity manager {@link em}
	 */
	public void closeConnection(){
		  em.close();
	}
	/**
	 * saves a new Goal
	 * 
	 * @param goal
	 * @return 
	 * @throws Exception
	 */
	public Goal addGoal(Goal goal) throws Exception{
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(goal);
		tx.commit();
		return goal;
	}
	/**
	 *  updates an existing Goal
	 *  
	 * @param goal
	 * @return
	 * @throws Exception
	 */
	public Goal update(Goal goal) throws Exception{
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.merge(goal);
		tx.commit();
		return goal;
	}
	/**
	 * delete an existing Goal
	 * 
	 * @param id
	 * @return 
	 * @throws Exception
	 */
	public boolean delete(long id) throws Exception {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.remove(this.get(id));
		tx.commit();
		return true;
	}
	/**
	 *  a Goal with specified id
	 *  
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Goal get(long id) throws Exception{
		Goal u = em.find(Goal.class, id);
		return u;
	}
	/**
	 * 
	 * @param username
	 * @return list of Goal set by a user, identified by Username
	 * @throws Exception
	 */
	public List<Goal> getAll(String username) throws Exception{
		User u = em.find(User.class, username);
		if(u == null)
			return new ArrayList<>();
		List<Goal> goals = em.createQuery("SELECT g FROM Goal g WHERE g.user=:username", Goal.class).setParameter("username", u).getResultList();
		return goals;
	}
	/**
	 *  
	 * @param username
	 * @param measureId
	 * @return list of Goal for a user identified by username and a measure identified by measureId otherwise null
	 * @throws Exception
	 */
	public List<Goal> getAll(String username, long measureId) throws Exception{
		User u = em.find(User.class, username);
		Measure m = em.find(Measure.class, measureId);
		if(m == null || u == null)
			return new ArrayList<>();
			
		List<Goal> goal = em.createQuery("SELECT g FROM Goal g WHERE g.user=:username AND g.measure=:id", Goal.class).setParameter("username", u).setParameter("id", m).getResultList();
		return goal;
	}
	/**
	 *  
	 * @return list of all goals in the database
	 * @throws Exception
	 */
	public List<Goal> getAll() throws Exception{
		List<Goal> goals = em.createQuery("SELECT g FROM Goal g", Goal.class).getResultList();
		return goals;
	}
	

	
}
