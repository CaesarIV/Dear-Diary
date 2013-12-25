package com.guc.dear_diary;


import java.util.Locale;


import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;

public class MainActivity extends Activity  /* implements OnInitListener */{
 //   private TextToSpeech tts;
    public static String URLL;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
  //      tts = new TextToSpeech(this, this);
     //   findViewById(R.id.button1).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void newEnt(View V){
    
    	Intent intent = new Intent(this, Selector.class);
    	startActivity(intent);
    	
    }
    
    public void toAud(View V){
        
    	Intent intent = new Intent(this, Sounds.class);
    	startActivity(intent);
    	
    }
    
    public void toVid(View V){
        
    	Intent intent = new Intent(this, Videos.class);
    	startActivity(intent);
    	
    }
    
    public void toImg(View V){
        
    	Intent intent = new Intent(this, Images.class);
    	startActivity(intent);
    	
    }
    
    public void toNot(View V){
        
    	Intent intent = new Intent(this, Notes.class);
    	startActivity(intent);
    	
    }
    

   /*
    public void readIt(View v){
    	
    	 if (tts!=null) {
   		  String text =
   		  ((EditText)findViewById(R.id.editText1)).getText().toString();
   		                 if (text!=null) {
   		                        if (!tts.isSpeaking()) {
   		  tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
   		  }


   		  }


   		  }
   
    }*/

	/*
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
  */
    
}
