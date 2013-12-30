package com.guc.dear_diary;



import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.calendar.CalendarEntry;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarFeed;
import com.google.gdata.data.calendar.ColorProperty;
import com.google.gdata.data.calendar.HiddenProperty;
import com.google.gdata.data.extensions.When;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class GoogleCalendar {
	void post(){
		 try {
		        System.out.println("Hi cruel world");
		        // Create a new Calendar service
		        CalendarService myService = new CalendarService("My Application");
		        myService.setUserCredentials("alimoussasaid@gmail.com","mariam2007");
		        
		        // Get a list of all entries
		        URL metafeedUrl = new URL("http://www.google.com/calendar/feeds/default/allcalendars/full");
		        System.out.println("Getting Calendar entries...\n");
		        CalendarFeed resultFeed = myService.getFeed(metafeedUrl, CalendarFeed.class);
		       
		        List<CalendarEntry> entries = resultFeed.getEntries();
		       System.out.println("entires size is "+entries.size());
		    	//////////////////////////////////////////////////////////////
		        		CalendarEventEntry myEntry = new CalendarEventEntry();
		        		URL postUrl = new URL("https://www.google.com/calendar/feeds/default/owncalendars/full");
		        	//	myEntry.setId("ta093vbkmpbe0ddh4q54vs2iv0@group.calendar.google.com");
		        	//	URL postUrl = new URL("https://www.google.com/calendar/feeds/default/Private/full");//adds new entry to the default calendar 
		        	//	URL postUrl = new URL("https://www.google.com/calendar/feeds/default/owncalendars/full");//adds new calendar
		        		
		        		myEntry.setTitle(new PlainTextConstruct("at last "));
		        		myEntry.setContent(new PlainTextConstruct("today i was trying to use google api and it wasn't that bad "));
		        		
		        	
		        		com.google.gdata.data.DateTime startTime = com.google.gdata.data.DateTime.parseDateTime("2013-12-27T01:00:00-08:00");
		        		com.google.gdata.data.DateTime endTime = com.google.gdata.data.DateTime.parseDateTime("2013-12-27T17:00:00-08:00");
		        		When eventTimes = new When();
		        		eventTimes.setStartTime(startTime);
		        		eventTimes.setEndTime(endTime);
		        		myEntry.addTime(eventTimes);
		        		System.out.println("my id that will be added is "+entries.get(2).getId());
		        		System.out.println("the name of the caleder we r trying to use is "+entries.get(2).getTitle());
		        		// Send the request and receive the response:
		        		CalendarEventEntry insertedEntry = myService.insert(
		        				 new URL(entries.get(0).getLink("http://schemas.google.com/gCal/2005#eventFeed", null).getHref()), myEntry);
		        		
		        		
		        		//////////////////////////////////////////////////////////////
		        
		  boolean alreadyExists=false;
		        for(int i=0; i<entries.size(); i++) {
		          CalendarEntry entry = entries.get(i);
		          System.out.println("\t" + "title of the only entry "+entry.getTitle().getPlainText());
		          if(entry.getTitle().getPlainText().equals("DEAR DIARY")){
		        	  alreadyExists=true;
		          }
		          
		         
		        }
		        System.out.println("\nTotal Entries: "+entries.size());
		       System.out.println("the already exists flag is : "+alreadyExists);
		       if(!alreadyExists){
		    	// Create the calendar
		    	   CalendarEntry calendar = new CalendarEntry();
		    	   calendar.setTitle(new PlainTextConstruct("DEAR DIARY"));
		    	   calendar.setSummary(new PlainTextConstruct("This calendar contains the online synced events that you added using your DEAR DIARY app"));
		    	  // calendar.setTimeZone(new TimeZoneProperty("America/Los_Angeles"));
		    	   calendar.setHidden(HiddenProperty.FALSE);
		    	   calendar.setColor(new ColorProperty("#2952A3"));
		    	 //  calendar.addLocation(new Where("","","Oakland"));

		    	   // Insert the calendar
		    	   URL postUrl2 = new URL("https://www.google.com/calendar/feeds/default/owncalendars/full");

		    	   CalendarEntry returnedCalendar = myService.insert(postUrl2, calendar);
		       }
		     
		        
		      }
		      catch(AuthenticationException e) {
		        e.printStackTrace();
		      }
		      catch(MalformedURLException e) {
		        e.printStackTrace();
		      }
		      catch(ServiceException e) {
		        e.printStackTrace();
		      }
		      catch(IOException e) {
		        e.printStackTrace();
		      }
		
	}
}
