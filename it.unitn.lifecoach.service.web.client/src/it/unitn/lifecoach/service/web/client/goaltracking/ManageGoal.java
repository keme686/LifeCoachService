package it.unitn.lifecoach.service.web.client.goaltracking;

import it.unitn.lifecoach.Constants.GoalStatus;
import it.unitn.lifecoach.model.Datapoint;
import it.unitn.lifecoach.model.Goal;
import it.unitn.lifecoach.model.Measure;
import it.unitn.lifecoach.service.process.goaltracking.adapter.GoalTrackingProcessAdapter;
import it.unitn.lifecoach.service.process.selfmonitoring.adapter.SelfMonitoringProcessAdapter;

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
 * Servlet implementation class ManageGoal
 * @author Kemele Muhammed Endris
 */
@WebServlet("/ManageGoal")
public class ManageGoal extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private final int REMOVE = -1;
	private final int PROGRESS = 0;
	private final int ADD = 1;
	private final int GET = 2;
	private final int GETALL = 3;
	private final int UPDATE = 4;
	
	private final int HISTOTY = 5;
	private final int EXPECTED = 6;
	private final int FEEDBACK = 7;
	
	GoalTrackingProcessAdapter adapter = new GoalTrackingProcessAdapter();
	SelfMonitoringProcessAdapter measureAdapter = new SelfMonitoringProcessAdapter();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageGoal() {
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
		if(session == null) return;
		String username = (String) session.getAttribute("username");
		String token = (String)session.getAttribute("token");
		if(q == null) return;
		int query = 0;
		
		try {
			query = Integer.parseInt(q);
		} catch (NumberFormatException e) {		
			e.printStackTrace();
		}
		
		if(query == PROGRESS){
			String mid = request.getParameter("gid");
			long gId =0;
			try{
				if(mid !=null)
					gId = Long.parseLong(mid);
			}catch(Exception e){
				
			}
			String progress = adapter.getProgress(new Date().getTime(), gId, token, username);
			System.out.println("progress: "+progress);
			out.print(progress);
			out.flush();
		}else if (query == GET){
			
		}else if(query == GETALL){
			List<Goal> measures =adapter.getActiveGoals(username, token);			
			out.print(new Gson().toJson(measures));
			out.flush();
		}else if(query == HISTOTY){
			String measureid = request.getParameter("gid");
			if(measureid == null) return;
			long mid = 0;
			try {
				mid = Integer.parseInt(measureid);
			} catch (NumberFormatException e) {		
				e.printStackTrace();
			}
			if(mid == 0) return;
			List<Datapoint> hists = adapter.getDatapoints(mid, token, username);
			out.print(new Gson().toJson(hists));
			out.flush();			
		}else if(query == EXPECTED){
			String mid = request.getParameter("gid");
			long gId =0;
			try{
				if(mid !=null)
					gId = Long.parseLong(mid);
			}catch(Exception e){
				
			}
			String progress = adapter.getExpectedGoalValue(new Date().getTime(), gId, token, username);
			System.out.println("Expected: "+progress);
			out.print(progress);
			out.flush();
		}else if(query == FEEDBACK){
			String mid = request.getParameter("gid");
			long gId =0;
			try{
				if(mid !=null)
					gId = Long.parseLong(mid);
			}catch(Exception e){
				
			}
			String progress = adapter.getFeedback(new Date().getTime(), gId, token, username);
			System.out.println("feedback: "+progress);
			out.print(progress);
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
		if(session == null) return;
		String username = (String) session.getAttribute("username");
		String token = (String)session.getAttribute("token");
		if(q == null) return;
		int query = 0;
		
		try {
			query = Integer.parseInt(q);
		} catch (NumberFormatException e) {		
			e.printStackTrace();
		}
		if(query == ADD){
			
			String title = request.getParameter("title");
			String mid = request.getParameter("mid");
			String initialValue = request.getParameter("initialValue");
			String initialValueUnit = request.getParameter("initialValueUnit");
			String goalValue = request.getParameter("goalValue");
			String goalValueUnit = request.getParameter("goalValueUnit");
			String goalDate = request.getParameter("goalDate");
			//String goalAmount = request.getParameter("goalAmount");
			//String goalAmountUnit = request.getParameter("goalAmountUnit");
			String planRate = request.getParameter("planRate");
			String planRateUnit = request.getParameter("planRateUnit");
			String planRateType = request.getParameter("planRateType");
			String goalRateType = request.getParameter("goalRateType");			
			
			double gv = 0.0,iv=0, rate=0.0;
			long mId=0;
			long date=new Date().getTime();
			try{
				if(goalValue!=null){
					gv = Double.parseDouble(goalValue);
				}
			}catch(Exception e){
				
			}
			try{
				if(initialValue != null){
					iv = Double.parseDouble(initialValue);
				}
			}catch(Exception e){
				
			}
			try{
				if(planRate !=null){
					rate = Double.parseDouble(planRate);
				}
			}catch(Exception e){
				
			}
			try{
				if(mid!=null){
					mId = Long.parseLong(mid);
				}
			}catch(Exception e){
				
			}
			try{
				if(goalDate!=null)
					date = Long.parseLong(goalDate);
			
			}catch(Exception e){
				
			}
			
			if(gv==0)return;
			
			Goal g = new Goal();
			g.setTitle(title);
			g.setGoalDate(date);
			g.setGoalType(planRateType);
			if(title != null)
				g.setGoalTag(title.substring(0, title.lastIndexOf(' ')));
			g.setRateType(goalRateType);
			g.setGoalValue(gv);
			g.setGoalValueUnit(goalValueUnit);
			g.setInitialValue(iv);
			g.setInitialValueUnit(initialValueUnit);
			g.setUpdatedTime(new Date().getTime());
			if(gv>iv)
				g.setPlannedDirection(1);
			else 
				g.setPlannedDirection(-1);
			g.setRate(rate);
			g.setRateUnit(planRateUnit);
			
			g.setStatus(GoalStatus.ACTIVE);
			
			if(mId > 0){
				Measure m = measureAdapter.getMeasure(mId, token, username);
				g.setMeasure(m);
			}
			
			Goal goal = adapter.addGoal(g, username, token);
			out.print(new Gson().toJson(goal));
			out.flush();
		}else if(query == REMOVE){
			
		}else if(query == UPDATE){
			String value = request.getParameter("value");
			String valueUnit = request.getParameter("unit");
			String timestamp = request.getParameter("timestamp");
			String id = request.getParameter("id");
			long mid = 0, time=new Date().getTime();
			double v = 0.0;
			try{
				if(value!=null){
					v = Double.parseDouble(value);
				}
				if(id!=null){
					mid = Long.parseLong(id);
				}
				if(timestamp!=null)
					time = Long.parseLong(timestamp);
			}catch(Exception e){
				
			}
			if(v==0 || mid==0){
				
				return;
			}
			Datapoint d = new Datapoint();
			d.setComment("Goal datapoint updated on "+ new Date().getTime());
			Goal g = adapter.getGoal(username, mid, token);
			if(g==null) {
				System.out.println("goal is null " + username +" id: " + mid);
				return;
			}
			d.setGoal(g);
			Measure measure = g.getMeasure();
			if(measure == null){
				System.out.println("measure null");
			}
			d.setMeasure(measure);
			d.setTimestamp(time);
			d.setUpdatedTime(new Date().getTime());
			d.setValue(v);
			d.setValueUnit(valueUnit);
			
			Datapoint dp = adapter.addDatapoint(username, mid, d, token );
			System.out.println("update: " + (dp!=null));
			List<Datapoint> hists = adapter.getDatapoints(mid, token, username);			
			out.print(new Gson().toJson(hists));
			out.flush();
		}
	}

}
