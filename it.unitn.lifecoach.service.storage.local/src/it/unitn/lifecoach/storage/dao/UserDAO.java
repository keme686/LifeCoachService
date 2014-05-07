/**
 * 
 */
package it.unitn.lifecoach.storage.dao;

import it.unitn.lifecoach.model.User;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
public class UserDAO {
	/**
	 *  entity manager object
	 */
	private EntityManager em;
	/**
	 * UserDAO singleton object 
	 */
	private static UserDAO userdao = new UserDAO();
	/**
	 * constructor: initializes entity manager
	 */
	public UserDAO() {
		em = Persistence.createEntityManagerFactory("it.unitn.lifecoach.model").createEntityManager();
	}
	
	public EntityManager getEntityManger(){
		return em;
	}
	/**
	 *  Creates a new Singleton instance
	 * @return a singleton {@link UserDAO} object
	 */
	public static UserDAO newInstance(){
		return userdao;
	}
	/**
	 * closes a connection to database set to entity manager {@link em}
	 */
	public void closeConnection(){
		  em.close();
	}
	/**
	 * saves a new user account
	 * 
	 * @param user
	 * @return 
	 * @throws Exception
	 */
	public User addUser(User user) throws Exception{
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			em.persist(user);
			tx.commit();
			return user;
	}
	/**
	 * updates an existing {@link User}
	 * 
	 * @param user
	 * @return 
	 * @throws Exception
	 */
	public User update(User user) throws Exception{
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.merge(user);
		tx.commit();
		return user;
	}
	/**
	 * deletes an existing user identified by username
	 * 
	 * @param username
	 * @return true if deleted
	 * @throws Exception
	 */
	public boolean delete(String username) throws Exception {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		User u = em.find(User.class, username);
		em.remove(u);
		tx.commit();
		return true;
	}
	
	/**
	 * 
	 * @param username
	 * @return a {@link User} identified by username, null otherwise
	 * @throws Exception
	 */
	public User get(String username) throws Exception{
		User u = em.find(User.class, username);
		return u;
	}
	
	/**
	 * 
	 * @return list of all {@link User} stored in the database
	 * @throws Exception
	 */
	public List<User> getAll() throws Exception{
		List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
		return users;
	}	
}
