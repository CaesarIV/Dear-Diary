package com.guc.dear_diary;





import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

public class NoteTaking extends Activity {
	public final static String MESSAGE1 = "com.example.myfirstapp.MESSAGE";
	public final static String MESSAGE2 = "com.example.myfirstapp.MESSAGE";
	public final static String MESSAGE3 = "com.example.myfirstapp.MESSAGE";
	public final static int number = 0;
	SaverSQL mDbHelper = new SaverSQL(this);
	String dateSelected;
	ContentValues values = new ContentValues();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_taking);
		
		Intent i = getIntent();
	     dateSelected = i.getExtras().get("dateLine").toString();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.note_taking, menu);
		return true;
	}
	

	public void saveclicked(View v) {
		Intent intent = new Intent(this, MainActivity.class);
		EditText editText = (EditText) findViewById(R.id.editText1);
		String message = editText.getText().toString();
		EditText editText2 = (EditText) findViewById(R.id.editText2);
		String message2 = editText2.getText().toString();
	
	
	
		intent.putExtra(MESSAGE1, message);
		intent.putExtra(MESSAGE2, message2);
		intent.putExtra(MESSAGE3, dateSelected);
		Notes.name.add(message);
		Notes.description.add(message2);
		Notes.date.add(dateSelected);
		values.put(mDbHelper.COLUMN_NAME_TITLE, message);
		values.put(mDbHelper.COLUMN_NAME_DESC, message2);
		values.put(mDbHelper.COLUMN_NAME_DATE, dateSelected);
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		long newRowId; 
		newRowId = db.insert( mDbHelper.TABLE_NAME,null, values);
		//values.put(FeedEntry.COLUMN_NAME_DESC, MESSAGE2);
		//values.put(FeedEntry.COLUMN_NAME_DATE, MESSAGE3);


		/*
		 * EditText editText2 = (EditText) findViewById(R.id.editText2); String
		 * message2 = editText2.getText().toString(); intent.putExtra(MESSAGE2,
		 * message2);
		 * 
		 * // EditText editText3 = (EditText) findViewById(R.id.datePicker1); //
		 * String message3 = editText3.getText().toString(); //
		 * intent.putExtra(EXTRA_MESSAGE, message3);
		 * 
		 * Log.e("Message 1",message); Log.e("Message 2",message2); //
		 * Log.e("Message 3",message);
		 */
		intent.putExtra("dateLine", dateSelected);
		startActivity(intent);

	}
	
	public void Gsync (View v){
		
		
		EditText editText = (EditText) findViewById(R.id.editText1);
		String message = editText.getText().toString();
		
		Intent i = new Intent(this,Sync.class);
		
		EditText editText2 = (EditText) findViewById(R.id.editText2);
		String message2 = editText2.getText().toString();
	
	
	
		i.putExtra(MESSAGE1, message);
		i.putExtra(MESSAGE2, message2);
		i.putExtra(MESSAGE3, dateSelected);
		Notes.name.add(message);
		Notes.description.add(message2);
		Notes.date.add(dateSelected);
		values.put(mDbHelper.COLUMN_NAME_TITLE, message);
		values.put(mDbHelper.COLUMN_NAME_DESC, message2);
		values.put(mDbHelper.COLUMN_NAME_DATE, dateSelected);
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		long newRowId; 
		newRowId = db.insert( mDbHelper.TABLE_NAME,null, values);
		
		i.putExtra("dateLine", dateSelected);
		i.putExtra("message", message);
		startActivity(i);
	}

}
