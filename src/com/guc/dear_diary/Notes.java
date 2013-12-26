package com.guc.dear_diary;

import com.guc.dear_diary.SaverSQL.FeedEntry;

import java.util.ArrayList;
import java.util.Date;



import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Notes extends Activity {
	
	public static int id ;
	public static ArrayList<String> name=new ArrayList<String>();
	public static ArrayList<String> description=new ArrayList<String>();
	public static ArrayList<String> date=new ArrayList<String>();
	ArrayAdapter<String> adapter;
	boolean firstTime=true;
	static int count =-1;
	String dateSelected;
	SaverSQL mDbHelper = new SaverSQL(this); //Getbase Context di el mafrood teb2a get context bas eshta
	ContentValues values = new ContentValues();
	
	//TODO The Third Column (DATE) has the Date of the day the entry was written on. The dateSelected string is set to the date the user selected. Check both are equal before dispalying.

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notes);
		Intent i = getIntent();
	    dateSelected = i.getExtras().get("dateLine").toString();
		
		readFromDatabase();
		adapter=new ArrayAdapter<String>(this,
		            android.R.layout.simple_list_item_1,
		            name);
		
		 
		ListView list= (ListView)findViewById(R.id.listView1);
		list.setAdapter(adapter);
		adapter.notifyDataSetChanged();
			

		 ListView l1 = (ListView)findViewById(R.id.listView1);
		 l1.setOnItemClickListener(new OnItemClickListener(){
			
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Context context = getApplicationContext();
			CharSequence text = "Hello toast! "+name.get(arg2);
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			 Intent intent = new Intent(arg1.getContext(), NoteItself.class);
			Notes.id=arg2;
			 intent.putExtra("id",arg2);
			 intent.putExtra("dateLine", dateSelected);
			 arg1.getContext().startActivity(intent);
				
		}
	      

	   });   
		
	count++;
	}
	
	public void onStart(){
		
		super.onStart();
		if(count>0){
			
			count++;
			//Log.e("Message",name.get(name.size()-1));
			//List list= (List)findViewById(R.id.listView1);
			ListView list= (ListView)findViewById(R.id.listView1);
			list.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			//list.addView(view1);
		}
			
			
	}
	
	public void readFromDatabase(){
		
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
	
		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = {
				mDbHelper.COLUMN_NAME_TITLE,
				mDbHelper.COLUMN_NAME_DESC,
				mDbHelper.COLUMN_NAME_DATE,
		   /* FeedEntry.COLUMN_NAME_TITLE,
		    FeedEntry.COLUMN_NAME_DESC,
		    FeedEntry.COLUMN_NAME_DATE*/
		      };
	
		
		// How you want the results sorted in the resulting Cursor
		
		Cursor c = db.query(
		  //  FeedEntry.TABLE_NAME,
			mDbHelper.TABLE_NAME,// The table to query
		    projection,                               // The columns to return
		    null,                                // The columns for the WHERE clause
		    null,                            // The values for the WHERE clause
		    null,                                     // don't group the rows
		    null,                                     // don't filter by row groups
		    null                                 // The sort order
		    );
		
		name.clear();
		description.clear();
		date.clear();
		 c.moveToFirst();
		for(int i=0;i<c.getCount();i++){
		
		if(dateSelected.equals(c.getString(c.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_DATE)))){
		name.add(c.getString(c.getColumnIndexOrThrow(mDbHelper.COLUMN_NAME_TITLE)));
		description.add(c.getString(c.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_DESC)));
		date.add(c.getString(c.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_DATE)));
		}
		c.moveToNext();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notes, menu);
		return true;
	}

}
