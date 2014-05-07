/**
 * 
 */
package prova;

import java.util.Date;
import java.util.List;
import java.util.Random;

import it.unitn.lifecoach.model.User;
import it.unitn.lifecoach.storage.dao.UserDAO;

/**
 * @author kemele
 *
 */
public class TestUserDAO {

	public static void main(String arg[]){
		String username = testAdd();
		User u = testGet(username);
		if(u==null)
			return;
		boolean a = new Random().nextBoolean(); 
		u.setName("User "+a);
		testUpdate(u);
		testGetAll();
		testDelete(username);
	
	}
	
	@SuppressWarnings("deprecation")
	private static String testAdd(){
		UserDAO uda = UserDAO.newInstance();
		
		User u= new User();
		int num = new Random().nextInt();
		u.setUsername("user"+num);
		u.setPassword("123");
		u.setName("User "+num);
		u.setGender("M");
		u.setDateOfBirth(new Date(86, 10, 3).getTime());
		u.setEmail("user@example.com");
		u.setLocation("Trento");
		u.setMobileNum("998279729279");
		u.setStatus(true);
		try{
		User user = uda.addUser(u);
		System.out.println("User " + user.getUsername() +" added!");
		printUserInfo(user);
		return user.getUsername();
		}catch(Exception e){
			System.out.println("TestUserDAO.testAdd() says:");
			e.printStackTrace();
			return null;
		}
	}
	private static User  testGet(String username){
		UserDAO uda = UserDAO.newInstance();
		try{
			User u = uda.get(username);
			System.out.println("User from testGet(String username): ");
			printUserInfo(u);
			return u;
		}catch(Exception e){
			System.out.println("TestDAO.testGet(String username) syays: ");
			e.printStackTrace();
			return null;
		}
	}
	private static void testUpdate(User user){
		UserDAO uda = UserDAO.newInstance();
		try{
			User u = uda.update(user);
			System.out.println("User from testUpdate(User user): ");
			printUserInfo(u);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private static void testDelete(String username){
		UserDAO uda = UserDAO.newInstance();
		try{
			User u = uda.get(username);
			boolean status = uda.delete(username);
			System.out.println("the following user is " + (status?"":"not ") + "deleted!");
			printUserInfo(u);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private static void testGetAll(){
		UserDAO uda = UserDAO.newInstance();
		try{
			List<User> users = uda.getAll();
			System.out.println("User from testGetAll(): ");
			for(User u: users){
				printUserInfo(u);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static void printUserInfo(User u){
		if(u == null){
			System.out.println("User info is Null");
			return;
		}
		System.out.println("____________________________________________________");		
		System.out.println("Username: " + u.getUsername());
		System.out.println("Password: " + u.getPassword());
		System.out.println("Name: " + u.getName());
		System.out.println("Gender: " + u.getGender());
		System.out.println("Date of Birth: " + new Date(u.getDateOfBirth()));
		System.out.println("Email: " + u.getEmail());
		System.out.println("Location: " + u.getLocation());
		System.out.println("Mobile Num: " + u.getMobileNum());
		System.out.println("Profile photo: " + u.getProfilePhoto());
		System.out.println("Status: " + u.getStatus());		
		System.out.println("----------------------------------------------------");
	}
}
