package com.guc.dear_diary;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Sounds extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sounds);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sounds, menu);
		return true;
	}

}
