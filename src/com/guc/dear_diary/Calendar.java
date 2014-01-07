package com.guc.dear_diary;

import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Calendar extends Activity {

	int month;
	int day;
	int year;
	String dateLine;
	Date today = new Date();
	Date selectedDate;
	CalendarView cv;
	
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);
		 cv =  (CalendarView)findViewById(R.id.calendarView1);
		
		System.out.println("DATE: "+ cv.getDate());
		selectedDate =new Date(cv.getDate());
		
		dateLine = selectedDate.toString();
	
		/*
		String [] yeararray=dateLine.split(" ");
		day = Integer.parseInt(yeararray[2]);
		//month = yeararray[1];
		year = Integer.parseInt(yeararray[5]);
		
		System.out.println("Day is: "+day);
		System.out.println("Month is: "+month);
		System.out.println("Year is: "+year);
		
		System.out.println("Today is: " + today.toString());
		
		//dateLine = day+month+year;
		
		*/
		
		cv.setOnDateChangeListener(new OnDateChangeListener() {
 
            @Override
            public void onSelectedDayChange(CalendarView view, int Inyear, int Inmonth,
                    int dayOfMonth)
            {
            	day = dayOfMonth;
            	month = Inmonth+1;
                year = Inyear;
                dateLine = day+"00"+month+"00"+year+"";
                selectedDate =new Date(cv.getDate());
                
                System.out.println("Day: " + day);
                System.out.println("Mon: " + month);
                System.out.println("Yea: " + year);
 
                
            	
				if(selectedDate.before(today)||selectedDate.equals(today)){
					//redirect to the new intent and pass the selected date
					Intent intent = new Intent(view.getContext(), MainActivity.class);
					System.out.println("The Date is (On this side): " + dateLine);
					intent.putExtra("dateLine",dateLine);
					intent.putExtra("date",selectedDate);
			    	startActivity(intent);
				}
				
				else if(selectedDate.after(today)){
					
				}
                                        
            }
        });
		
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calendar, menu);
		return true;
	}

}
