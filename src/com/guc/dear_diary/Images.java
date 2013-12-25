package com.guc.dear_diary;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.Menu;
import android.widget.GridView;

public class Images extends Activity {
	
	  //set constants for MediaStore to query, and show videos
    private final static Uri MEDIA_EXTERNAL_CONTENT_URI = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    private final static String _ID = MediaStore.Video.Media._ID;
    private final static String MEDIA_DATA = MediaStore.Video.Media.DATA;
    //flag for which one is used for images selection
    private GridView _gallery; 
    private Cursor _cursor;
    private int _columnIndex;
    private int[] _videosId;
    private Uri _contentUri;
    String filename;
    int flag = 0;



    protected Context _context;

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_images);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.images, menu);
		return true;
	}

}
