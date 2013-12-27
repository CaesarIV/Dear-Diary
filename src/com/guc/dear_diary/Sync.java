package com.guc.dear_diary;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class Sync extends Activity {
	
	String dateLine;
	String entry;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sync);
		Intent i = getIntent();
		dateLine = i.getExtras().get("dateLine").toString();
		entry = i.getExtras().get("message").toString();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sync, menu);
		return true;
	}
	
	public void login(View v){
		
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("dateLine", dateLine);
		startActivity(intent);
	}

}
