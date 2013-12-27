package com.guc.dear_diary;


import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class NoteItself extends Activity {
	 public final static String MESSAGE1 = "com.example.myfirstapp.MESSAGE";
	  private TextToSpeech tts;
	  SaverSQL mDbHelper = new SaverSQL(this);
	  ContentValues values = new ContentValues();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_itself);
		
		 int id=Notes.id;
		 
			TextView textView1 = (TextView) findViewById(R.id.textView1);
			TextView textView2 = (TextView) findViewById(R.id.textView2);
			TextView textView3 = (TextView) findViewById(R.id.textView3);
			
			System.out.println("Name: "+Notes.name.get(id));
			
			textView1.setText(Notes.name.get(id));
			textView2.setText(Notes.description.get(id));
			textView3.setText(Notes.date.get(id));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.note_itself, menu);
		

		
	
		
		return true;
	}
	
public void deletetask(View v){
		
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		
		int id=Notes.id;
	

		String selection = mDbHelper.COLUMN_NAME_TITLE + "=? " + " AND " + mDbHelper.COLUMN_NAME_DESC + "=?" + " AND " + mDbHelper.COLUMN_NAME_DATE + "=?";
		String[] selectionArgs = { String.valueOf(Notes.name.get(id)),String.valueOf(Notes.description.get(id)),String.valueOf(Notes.date.get(id)) };
		db.delete(mDbHelper.TABLE_NAME, selection, selectionArgs);
	
		Intent intent = new Intent(this, MainActivity.class);
		 startActivity(intent);
		
	}
	
	public void onInit(int code) {
		// TODO Auto-generated method stub
		
	    if (code==TextToSpeech.SUCCESS) {
	    	  tts.setLanguage(Locale.getDefault());


	    	  } else {
	    	  tts = null;
	    	  Toast.makeText(this, "Failed to initialize TTS engine.",
	    	  Toast.LENGTH_SHORT).show();
	    	  }
		
	}
	
	 public void readIt(View v){
	    	
    	 if (tts!=null) {
   		  String text =
   		  ((TextView)findViewById(R.id.textView1)).getText().toString();
   		                 if (text!=null) {
   		                        if (!tts.isSpeaking()) {
   		  tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
   		  }


   		  }


   		  }
   
    }

}
