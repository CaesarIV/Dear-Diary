package com.guc.dear_diary;

import java.io.File;
import java.util.ArrayList;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class Videos extends Activity {
        ArrayAdapter<String> adapter;
        // set constants for MediaStore to query, and show videos
        Uri uri;
        File folder;
        File[] listOfFiles;
        ArrayList <String> namesOfFiles=new ArrayList<String>();
        protected Context _context;
        String dateSelected;
        ListView l1;
        ListView list;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                
     
        	super.onCreate(savedInstanceState);
        	setContentView(R.layout.activity_videos);
                
                Intent i = getIntent();
        	    dateSelected = i.getExtras().get("dateLine").toString();
        	    Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
                        + "/Dear Diary/"+dateSelected+"/Video/");
        	    
        	    
        	    folder =new File(uri.toString());
        	    if(folder.exists()){
                listOfFiles=folder.listFiles();
        	    
                
                
                
                System.out.println("uri is "+uri);
                System.out.println("number of files in the folder "+listOfFiles.length);
                
                
        	    
                
                
                
                
                for (File file : listOfFiles) {
                        if (file.isFile()) {
                                System.out.println("file name : "+file.getName());
                                namesOfFiles.add(file.getName());
                        }
                }
                System.out.println("size of file names list "+namesOfFiles.size());
                adapter=new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1,
                    namesOfFiles);
        
         
         list= (ListView)findViewById(R.id.listView1);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
         
         
         l1 = (ListView)findViewById(R.id.listView1);
         l1.setOnItemClickListener(new OnItemClickListener(){

                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {
                                Context context = getApplicationContext();
                                CharSequence text = "Hello toast! "+namesOfFiles.get(arg2);
                                int duration = Toast.LENGTH_SHORT;
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);                                                                                            
                                Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
                                        + "/Dear Diary/"+dateSelected+"/Video/"+l1.getItemAtPosition(arg2));                                   
                                intent.setDataAndType(uri , "video/*");   
                                startActivity(Intent.createChooser(intent, "Open Video"));
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                                // Intent intent = new Intent(arg1.getContext(), Sub2.class);
                                //MainActivity.id=arg2;
                                 //intent.putExtra("id",arg2);
                                 //arg1.getContext().startActivity(intent);
                        //        System.out.println("the video to open "+uri+namesOfFiles.get(index));
                                
                        }
              

            });
         
                }
        }

        
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.videos, menu);
                
                return true;
        }
        
        /*public void openFolder()
        {
                
                
                
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/DearDiary/Camera/");              
        intent.setDataAndType(uri , "file/*");   
        startActivity(Intent.createChooser(intent, "Open Video"));
        }
*/
        
        
        
        
}