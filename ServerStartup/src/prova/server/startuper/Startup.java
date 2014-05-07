/**
 * 
 */
package prova.server.startuper;


import it.unitn.lifecoach.service.logic.schedule.endpoint.ScheduleLogicPublisher;
import it.unitn.lifecoach.service.storage.out.food.endpoint.FoodInfoPublisher;

import java.io.IOException;

import prova.QuotesStandaloneServer;
import prova.StandaloneServerDataStorage;
import prova.server.MealLogLogicStandaloneServer;
import prova.server.MealLogProcessStandaloneServer;
import prova.server.QuotesProcessStandaloneServer;
import prova.server.ScheduleProcessStandaloneServer;
import prova.server.StandaloneServerAuthentication;
import prova.server.StandaloneServerLogicSelfMonitoring;
import prova.server.StandaloneServerLogicGoalTracking;
import prova.server.StandaloneServerProcessGoalTracking;
import prova.server.StandaloneServerProcessSelfMonitoring;

/**
 * @author kemele
 *
 */
public class Startup {

	public static void main(String a[]) throws IllegalArgumentException, IOException{		
					
		/**
		 * Authentication server
		 */
		StandaloneServerAuthentication.main(null);   				
		
		
		
		/** 
		 * Internal/local storage service
		 */
		StandaloneServerDataStorage.main(null);
		
		
		
		/**
		 * Self monitoring logic layer server
		 */
		StandaloneServerLogicSelfMonitoring.main(null);
		/**
		 * Self monitoring proess layer server
		 */
		StandaloneServerProcessSelfMonitoring.main(null);
		
		
		/**
		 * Goal tracking logic layer server
		 */
		StandaloneServerLogicGoalTracking.main(null);
		/**
		 * Goal tracking process centric service server
		 */
	    StandaloneServerProcessGoalTracking.main(null);
	    
	    
	    /**
	     * Meal information external storage adapter service server
	     */
	    FoodInfoPublisher.main(null);
	    /**
	     * Meal log logic layer server
	     */
	    MealLogLogicStandaloneServer.main(null);
	    /**
	     * Meal log process centric service server
	     */
	    MealLogProcessStandaloneServer.main(null);

	    
	   	    
	    /**
	     * Schedule logic layer server
	     */
	    ScheduleLogicPublisher.main(null);
	    /**
	     * Schedule process centric service server
	     */
	    ScheduleProcessStandaloneServer.main(null);
	    
	    
	    
	    /**
	     * Motivational quotes external storage adapter serveice server
	     */
	    QuotesStandaloneServer.main(null);
	    /**
	     * Motivational quotes process centric service server
	     */
	    QuotesProcessStandaloneServer.main(null);
	}
}
