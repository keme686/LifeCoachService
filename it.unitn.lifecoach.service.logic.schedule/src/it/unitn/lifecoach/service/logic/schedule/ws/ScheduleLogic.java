package it.unitn.lifecoach.service.logic.schedule.ws;

import it.unitn.lifecoach.model.ScheduleSOAP;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
/**
 * 
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */

@WebService
@SOAPBinding(style = Style.RPC ,use=Use.LITERAL)
public interface ScheduleLogic {
	
	  @WebMethod(operationName="addSchedule")
	  @WebResult(name="Schedule")
	  public ScheduleSOAP addSchedule(@WebParam(name="schedule") ScheduleSOAP schedule, @WebParam(name="username") String username);
	  
	  @WebMethod(operationName="updateSchedule")
	  @WebResult(name="Schedule")
	  public ScheduleSOAP updateSchedule(@WebParam(name="schedule") ScheduleSOAP schedule, @WebParam(name="id") long id, @WebParam(name="username") String username);
	  
	  @WebMethod(operationName="getSchedule")
	  @WebResult(name="Schedule")
	  public ScheduleSOAP getSchedule(@WebParam(name="id") long id);
	  
	  @WebMethod(operationName="deleteSchedule")
	  @WebResult(name="message")
	  public String deleteSchedule(@WebParam(name="username") String username, @WebParam(name="id") long id);
	  
	  @WebMethod(operationName="getAllUser")
	  @WebResult(name="schedules")
	  public ScheduleSOAP[]getAllUser(@WebParam(name="username") String username);
	  
	  @WebMethod(operationName="getTodays")
	  @WebResult(name="schedules")
	  public ScheduleSOAP[] getTodays(@WebParam(name="username") String username);
	
	  @WebMethod(operationName="remindUser")
	  @WebResult(name="schedules")
	  public ScheduleSOAP[] remindUser(@WebParam(name="username") String username);
	 
	  @WebMethod(operationName="withinInterval")
	  @WebResult(name="schedules")
	  public ScheduleSOAP[] withinInterval(@WebParam(name="username") String username,
			  @WebParam(name="from") long from ,  @WebParam(name="to") long to );
}
