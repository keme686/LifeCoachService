package it.unitn.lifecoach.service.web.client.account;

import it.unitn.lifecoach.model.User;
import it.unitn.lifecoach.service.process.authentication.adapter.UserAuthenticationAdapter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SignOut
 * @author Kemele Muhammed Endris
 */
@WebServlet("/SignOut")
public class SignOut extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	UserAuthenticationAdapter authAdapter = new UserAuthenticationAdapter();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignOut() {
        super();
        // TODO Auto-generated constructor stub
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
		if(request.getSession(false)==null)
			return;
		String username = (String) request.getSession(false).getAttribute("username");
		String token = (String) request.getSession(false).getAttribute("token");
		PrintWriter out = response.getWriter();
		response.setContentType("text/xml;charset=UTF-8");
		if(username !=null && token !=null){
			User user = new User();
			user.setUsername(username);
			user.setAccessToken(token);
			boolean status = authAdapter.logout(user);		
			out.println(status);
		}		
		request.getSession().invalidate();
		response.sendRedirect(getServletContext().getContextPath() +"/index.jsp");	
		out.flush();
	}

}
