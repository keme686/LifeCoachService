package it.unitn.lifecoach.service.web.client.meallog;

import it.unitn.lifecoach.Constants.MealServingTime;
import it.unitn.lifecoach.model.GraphPoint;
import it.unitn.lifecoach.model.MealLog;
import it.unitn.lifecoach.model.Product;
import it.unitn.lifecoach.service.process.meallog.adapter.MealLogProcessAdapter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

/**
 * Servlet implementation class ManageMealLog
 * 
 * @author Kemele Muhammed Endris
 */
@WebServlet("/ManageMealLog")
public class ManageMealLog extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final int REMOVE = -1;
	private final int SUGGESTED = 0;
	private final int ADD = 1;
	private final int GET = 2;
	private final int GETALL = 3;
	private final int UPDATE = 4;
	
	private final int HISTOTY = 5;
	private final int BMI = 6;
	
		
	MealLogProcessAdapter adapter = new MealLogProcessAdapter();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageMealLog() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		String q = request.getParameter("q");
		HttpSession session = request.getSession(false);
		if(session == null){
			return;
		}
		String username = (String) session.getAttribute("username");
		String token  = (String)session.getAttribute("token");
		if(q == null) return;
		int query = 0;
		
		try {
			query = Integer.parseInt(q);
			
		} catch (NumberFormatException e) {		
			e.printStackTrace();
		}
		
		if(query == SUGGESTED){
			long time=new Date().getTime();
			String day = request.getParameter("time");
			try {
				time = Long.parseLong(day);
				
			} catch (NumberFormatException e) {		
				e.printStackTrace();
			}
			GraphPoint p = adapter.getCaloriesOfADay(time, username, token);
			
			out.print(p.getX());
			out.flush();
		}else if (query == GET){
			
		}else if(query == GETALL){
			List<MealLog> measures =adapter.getMealLogsWithinInterval(new Date().getTime()-7*24*3600*1000, new Date().getTime(), username, token);//adapter.getAll(username);
			out.print(new Gson().toJson(measures));
			out.flush();
		}else if(query == HISTOTY){
			
			String cmd = request.getParameter("cmd");
			
			if(cmd == null) {
				return;
			}
			
			int cmds =0;
			
			try {
				cmds = Integer.parseInt(cmd);
			} catch (NumberFormatException e) {		
				e.printStackTrace();
			}
			if(cmds == 0 ) return;
			
			if(cmds == 1){
				
				List<MealLog> logs = adapter.getMealLogsofDayPerSevingTime(new Date().getTime(), MealServingTime.BREAKFAST, username, token);
				System.out.println(logs.size());
				out.println(new Gson().toJson(logs));
			}else if(cmds == 2){
				List<MealLog> logs = adapter.getMealLogsofDayPerSevingTime(new Date().getTime(), MealServingTime.MORNING_SNACK, username, token);
				out.println(new Gson().toJson(logs));
			}else if(cmds == 3){
				List<MealLog> logs = adapter.getMealLogsofDayPerSevingTime(new Date().getTime(), MealServingTime.LUNCH, username, token);
				out.println(new Gson().toJson(logs));
			}else if(cmds ==4){
				List<MealLog> logs = adapter.getMealLogsofDayPerSevingTime(new Date().getTime(), MealServingTime.AFTERNOON_SNACK, username, token);
				out.println(new Gson().toJson(logs));
			}else if(cmds == 5){
				List<MealLog> logs = adapter.getMealLogsofDayPerSevingTime(new Date().getTime(), MealServingTime.DINNER, username, token);
				out.println(new Gson().toJson(logs));
			}else if(cmds == 6){
				List<MealLog> logs = adapter.getMealLogsofDayPerSevingTime(new Date().getTime(), MealServingTime.EVENING_SNACK, username, token);
				out.println(new Gson().toJson(logs));
			}
			
			out.flush();
			
		}else if(query == BMI){
			
			//List<Product> fs = adapter.getSearchFood("a", 10);
			GraphPoint fs = adapter.getCaloriesOfADay(new Date().getTime(), username, token);
			System.out.println(fs.getY());
			out.print(fs.getY());
			out.flush();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		String q = request.getParameter("q");
		HttpSession session = request.getSession(false);
		if(session == null){
			return;
		}
		String username = (String) session.getAttribute("username");
		String token  = (String)session.getAttribute("token");
		if(q == null) return;
		int query = 0;
		
		try {
			query = Integer.parseInt(q);
			
		} catch (NumberFormatException e) {		
			e.printStackTrace();
		}
		
		if(query == ADD){
			String name = request.getParameter("name");
			String servingTime = request.getParameter("servingTime");
			String servingSize = request.getParameter("servingSize");
			String servingSizeUnit = request.getParameter("servingUnit");
			
			String date = request.getParameter("date");
			String caloriestr = request.getParameter("calories");
			String typeOfMeal = request.getParameter("type");
			
			long timestamp = new Date().getTime();
			int calories = 0;
			double size = 0.0;
			
			try{
				
				calories = Integer.parseInt(caloriestr);
				
				size = Double.parseDouble(servingSize);				
				timestamp = Long.parseLong(date);				
			}catch(NumberFormatException ex){
				ex.printStackTrace();
			}
			if(calories == 0.0){
				return;
			}
			MealLog log = new MealLog();
			log.setName(name);
			log.setCalories(calories);
			log.setServingSize(size);
			log.setServingSizeUnit(servingSizeUnit);
			log.setServingTime(servingTime);
			log.setUpdatedTime(timestamp);
			
			log.setTypeOfMeal(typeOfMeal);
			
			MealLog m = adapter.addMealLog(log, username, token);
			System.out.println(m);
			out.print(m);
			out.flush();
			
		}else if(query == REMOVE){
			
		}else if(query == UPDATE){
			
		}
	}

}
