package com.guc.dear_diary;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.CalendarList;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import java.io.IOException;
import java.util.Collections;

public final class Sync extends Activity {

  private static final String PREF_ACCOUNT_NAME = "accountName";
  static final String TAG = "CalendarSampleActivity";
  static final int REQUEST_GOOGLE_PLAY_SERVICES = 0;
  static final int REQUEST_AUTHORIZATION = 1;
  static final int REQUEST_ACCOUNT_PICKER = 2;
  static final int CALENDAR_REQUEST = 3;
  final HttpTransport transport = AndroidHttp.newCompatibleTransport();
  final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
  GoogleAccountCredential credential;
  com.google.api.services.calendar.Calendar client;
  String dateLine;
  String entry;
  boolean there = false;
  GoogleCalendar goo = new GoogleCalendar();
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_sync);
	Intent i = getIntent();
	dateLine = i.getExtras().get("dateLine").toString();
	entry = i.getExtras().get("message").toString();
    credential =
        GoogleAccountCredential.usingOAuth2(this, Collections.singleton(CalendarScopes.CALENDAR));
    SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
    credential.setSelectedAccountName(settings.getString(PREF_ACCOUNT_NAME, null));
    // Calendar client
    client = new com.google.api.services.calendar.Calendar.Builder(
        transport, jsonFactory, credential).setApplicationName("Google-CalendarAndroidSample/1.0")
        .build();
  }

  public void login(View v){
	  there=true;
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("dateLine", dateLine);
		startActivity(intent);
		 ConnectivityManager connMgr = (ConnectivityManager) 
		         getSystemService(Context.CONNECTIVITY_SERVICE);
		     NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		     if (networkInfo != null && networkInfo.isConnected()) {
		        System.out.println("we have internet connections , now we will try to access google ");
		        	 AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
		                 @Override
		                 protected String doInBackground(Void... params) {
		                	 CalendarList feed;
							try {
								feed = client.calendarList().list().setFields("item(id,summary)").execute();
								System.out.println("no of calendars:"+feed.size());
								 final com.google.api.services.calendar.model.Calendar entry = new com.google.api.services.calendar.model.Calendar();
									
									
									entry.setDescription("this is just a mobile calendar");
									//  CalendarList feed = client.calendarList().list().setFields(CalendarInfo.FEED_FIELDS).execute();

									// System.out.println("size of the feed"+feed.size());

									client.calendars().insert(entry).setFields("mobile").execute();
									System.out.println("i entered the calendar in your calendars sir ");
								//	System.out.println("number of calendars you have are: "+client.();
								//	System.out.println("trying the calendar objet itself: "+client.);
		                	 return feed.toPrettyString();
							} catch (Exception e) {
								System.out.println("Thread Error!");
								e.printStackTrace();
								chooseAccount();
								return "";
							}
		                 }
		                 };
		                 task.execute();
		                 //goo.post();
					//startCalendarActivity();
					        
		     } else {
		        System.out.println("No network connection available.");
		     }
		}
  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sync, menu);
		return true;
	}
  
  void showGooglePlayServicesAvailabilityErrorDialog(final int connectionStatusCode) {
    runOnUiThread(new Runnable() {
      public void run() {
        Dialog dialog = GooglePlayServicesUtil.getErrorDialog(
            connectionStatusCode, Sync.this, REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
      }
    });
  }
  @Override
  protected void onResume() {
    super.onResume();
    if (checkGooglePlayServicesAvailable()) {
      haveGooglePlayServices();
    }
  }
  
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    System.out.println("on activity result called!"+ resultCode);
    switch (requestCode) {
      case REQUEST_GOOGLE_PLAY_SERVICES:
        if (resultCode == Activity.RESULT_OK) {
          haveGooglePlayServices();
        } else {
          checkGooglePlayServicesAvailable();
        }
        break;
      case REQUEST_AUTHORIZATION:
        if (resultCode == Activity.RESULT_OK) {
        	login(null);
        } else {
          chooseAccount();
        }
        break;
      case REQUEST_ACCOUNT_PICKER:
        if (resultCode == Activity.RESULT_OK && data != null && data.getExtras() != null) {
          String accountName = data.getExtras().getString(AccountManager.KEY_ACCOUNT_NAME);
          if (accountName != null) {
            credential.setSelectedAccountName(accountName);
            SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(PREF_ACCOUNT_NAME, accountName);
            editor.commit();
            login(null);
          }
        }
        break;
      case CALENDAR_REQUEST:
    	  if (resultCode == Activity.RESULT_OK) {
              login(null);
            }
        break;
    }
  }


  /** Check that Google Play services APK is installed and up to date. */
  private boolean checkGooglePlayServicesAvailable() {
    final int connectionStatusCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
    if (GooglePlayServicesUtil.isUserRecoverableError(connectionStatusCode)) {
      showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
      return false;
    }
    return true;
  }

  private void haveGooglePlayServices(){
    // check if there is already an account selected
    if (credential.getSelectedAccountName() == null) {
      // ask user to choose account
      chooseAccount();
    } else {
    	 if(!there)
    		 login(null);
    }
  }

  private void chooseAccount() {
    startActivityForResult(credential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
  }
 // private void startCalendarActivity() {
	//  login(null);  
	  //startActivityForResult(this.getIntent(), CALENDAR_REQUEST);
	 // }
}