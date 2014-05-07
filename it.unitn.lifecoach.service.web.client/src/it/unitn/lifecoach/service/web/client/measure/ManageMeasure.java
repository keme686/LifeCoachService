package it.unitn.lifecoach.service.web.client.measure;

import it.unitn.lifecoach.model.Datapoint;
import it.unitn.lifecoach.model.Measure;
import it.unitn.lifecoach.service.process.authentication.adapter.UserAuthenticationAdapter;
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
 * Servlet implementation class ManageMeasure
 * @author Kemele Muhammed Endris
 */
@WebServlet("/ManageMeasure")
public class ManageMeasure extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	UserAuthenticationAdapter authAdapter = new UserAuthenticationAdapter();
	SelfMonitoringProcessAdapter adapter = new SelfMonitoringProcessAdapter();
	
	private final int REMOVE = -1;
	private final int SUGGESTED = 0;
	private final int ADD = 1;
	private final int GET = 2;
	private final int GETALL = 3;
	private final int UPDATE = 4;
	
	private final int HISTOTY = 5;
	private final int BMI = 6;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageMeasure() {
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
		String token  = (String)session.getAttribute("token");
		if(q == null) return;
		int query = 0;
		
		try {
			query = Integer.parseInt(q);
		} catch (NumberFormatException e) {		
			e.printStackTrace();
		}
		
		if(query == SUGGESTED){
			List<String> measures = adapter.getSuggestedMeasures();
			
			out.print(measures);
			out.flush();
		}else if (query == GET){
			
		}else if(query == GETALL){
			List<Measure> measures =adapter.getAll(username, token);
			out.print(new Gson().toJson(measures));
			out.flush();
		}else if(query == HISTOTY){
			String measureid = request.getParameter("mid");
			if(measureid == null) return;
			long mid = 0;
			try {
				mid = Integer.parseInt(measureid);
			} catch (NumberFormatException e) {		
				e.printStackTrace();
			}
			if(mid == 0) return;
			List<Datapoint> hists = adapter.getHistory(mid, token, username);
			out.print(new Gson().toJson(hists));
			out.flush();			
		}else if(query == BMI){
			String bmi = adapter.getBmi(username, token);
			out.println(new Gson().toJson(bmi));
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
			String name = request.getParameter("name");
			String value = request.getParameter("value");
			String valueUnit = request.getParameter("unit");
			String timestamp = request.getParameter("timestamp");
			
			double v = 0.0;
			long time=new Date().getTime();
			try{
				if(value!=null){
					v = Double.parseDouble(value);
				}
				if(timestamp!=null)
					time = Long.parseLong(timestamp);
			
			}catch(Exception e){
				
			}
			
			if(v==0)return;
			Measure m = new Measure();
			m.setName(name);
			m.setValue(v);
			m.setValueUnit(valueUnit);
			m.setUpdateTime(time);
			Measure ms = adapter.addMease(username, m, token);
			out.print(new Gson().toJson(ms));
			out.flush();
		}else if(query == REMOVE){
			
		}else if(query == UPDATE){
			String name = request.getParameter("name");
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
			if(v==0 || mid==0)return;
			Measure m = new Measure();
			m.setId(mid);
			m.setName(name);
			m.setValue(v);
			m.setValueUnit(valueUnit);
			m.setUpdateTime(time);
			Measure ms = adapter.updateMeasure(m, mid, username, token);
			out.print(new Gson().toJson(ms));
			out.flush();
		}
	}

}
