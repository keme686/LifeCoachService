package it.unitn.lifecoach.service.web.client.account;

import it.unitn.lifecoach.model.User;
import it.unitn.lifecoach.service.process.authentication.adapter.UserAuthenticationAdapter;

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
 * Servlet implementation class UserLogin
 * @author Kemele Muhammed Endris
 */
@WebServlet(description = "Authenticates a user and set get access token from the lifecoach authentication sevice then set the access token to the user session object", urlPatterns = { "/UserLogin" })
public class UserLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * adapter used to access lifecoach authentication service
	 */
	UserAuthenticationAdapter adapter;
    /**
     * Default constructor. 
     */
    public UserLogin() {
       adapter = new UserAuthenticationAdapter();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		if(username != null && !username.equals("") && password != null && !password.equals("")){
			User u = new User();
			u.setPassword(password);
			u.setUsername(username);
			User user = adapter.login(u);
			
			if(user!=null && user.getAccessToken()!=null){
				HttpSession s = request.getSession(true);
				s.setAttribute("username", username);
				s.setAttribute("token", user.getAccessToken());
				response.sendRedirect(getServletContext().getContextPath() +"/index.jsp");			
				out.write(new Gson().toJson(user));
				out.flush();
			}
		}else {
			Map<String, String> data = new HashMap<>();
			
			String reason = "login error";
			String status = "status";
			data.put(status, reason);
			out.print(new Gson().toJson(data));
			out.flush();
		}
			
	}

}
