package it.unitn.lifecoach.service.web.client.account;

import it.unitn.lifecoach.model.User;
import it.unitn.lifecoach.service.process.authentication.adapter.UserAuthenticationAdapter;
import it.unitn.lifecoach.service.process.selfmonitoring.adapter.SelfMonitoringProcessAdapter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

/**
 * Servlet implementation class Signup
 * @author Kemele Muhammed Endris
 */
@WebServlet("/Signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	SelfMonitoringProcessAdapter adapter = new SelfMonitoringProcessAdapter();
	UserAuthenticationAdapter authAdapter = new UserAuthenticationAdapter();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Signup() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String gender = request.getParameter("gender");
		String mobile = request.getParameter("mobile");
		String location = request.getParameter("location");
		
	//	String dateofbirth = request.getParameter("dateofbirth");
		
		User u = new User();
		u.setUsername(username);
		u.setPassword(password);
		u.setEmail(email);
		u.setGender(gender);
		u.setMobileNum(mobile);
		u.setName(name);
		u.setLocation(location);
		
		
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		User user = adapter.addUser(u);
		
		if(user!=null ){
			user = authAdapter.login(user);
			if(user != null  && user.getAccessToken()!=null){
			HttpSession s = request.getSession(true);
			s.setAttribute("username", username);
			s.setAttribute("token", user.getAccessToken());
			
			//response.sendRedirect(getServletContext().getContextPath() +"/account/home.jsp");			
			out.write(new Gson().toJson(user));
			out.flush();
			}else{
				Map<String, String> data = new HashMap<>();
				
				String reason = "login error";
				String status = "status";
				data.put(status, reason);
				out.print(new Gson().toJson(data));
				out.flush();
			}
	}else {
		Map<String, String> data = new HashMap<>();
		
		String reason = "Sign up error";
		String status = "status";
		data.put(status, reason);
		out.print(new Gson().toJson(data));
		out.flush();
	}
		
	}

}
