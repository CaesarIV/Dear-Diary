package com.guc.dear_diary;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

public class Selector extends Activity {

	 // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
 
    // directory name to store captured images and videos
    
    
    private static  String IMAGE_DIRECTORY_NAME = "/Dear Diary/Camera/";
    private static  String VIDEO_DIRECTORY_NAME = "/Dear Diary/Camera/";
    private static  String AUDIO_DIRECTORY_NAME = "/Dear Diary/Camera/";
    
    
 
    private Uri fileUri; // file url to store image/video
 
    boolean pressed = false;
    String dateSelected;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selector);
		Intent i = getIntent();
	     dateSelected = i.getExtras().get("dateLine").toString();
	    
	    IMAGE_DIRECTORY_NAME = "/Dear Diary/"+dateSelected+"/Images/";
	    VIDEO_DIRECTORY_NAME = "/Dear Diary/"+dateSelected+"/Video/";
	    AUDIO_DIRECTORY_NAME = "/Dear Diary/"+dateSelected+"/Audio/";
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.selector, menu);
		return true;
	}

	
	public void snap(View v){
		
		captureImage();
	}
	
	public void snapRec(View v){
		
		recordVideo();
	}
	
	public void notak(View v){
			
		Intent intent = new Intent(this,NoteTaking.class);
		intent.putExtra("dateLine",dateSelected);
		startActivity(intent);
	}
	
	public void memo(View v){
		Button b = (Button)findViewById(R.id.button3);
		b.setText("Recording!");
		MediaRecorder recorder = new MediaRecorder(); 
		  File AUDmediaStorageDir = new File(Environment.getExternalStorageDirectory().getPath()+AUDIO_DIRECTORY_NAME);
		//fileAud.mkdir();
		
		
		System.out.println("Can we write? " + AUDmediaStorageDir.canWrite());
		if(!pressed){
			
			
			if (!AUDmediaStorageDir.exists()) {
	            if (!AUDmediaStorageDir.mkdirs()) {
	                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
	                        + IMAGE_DIRECTORY_NAME + " directory");
	                
	            }
	        }
			
			int aVal = (int) ((float) (Math.random())*1000);
			System.out.println("Random: " + aVal);
			
			recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
			recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
			recorder.setOutputFile(Environment.getExternalStorageDirectory().getPath()+AUDIO_DIRECTORY_NAME+"AudioFile#"+aVal+".mp3");
			System.out.println("Audio Storage Location: "+Environment.getExternalStorageDirectory().getPath()+AUDIO_DIRECTORY_NAME+"AduioFile#"+aVal);
			pressed = true;
		

		

		try {
			recorder.prepare();
			recorder.start();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		}else{
			b.setText("Ready!");
			recorder.reset();
			recorder.release();
			pressed = false;
		}
	}
	
	 private boolean isDeviceSupportCamera() {
	        if (getApplicationContext().getPackageManager().hasSystemFeature(
	                PackageManager.FEATURE_CAMERA)) {
	            // this device has a camera
	            return true;
	        } else {
	            // no camera on this device
	            return false;
	        }
	    }
	 
	    /**
	     * Capturing Camera Image will lauch camera app requrest image capture
	     */
	    private void captureImage() {
	        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	 
	        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
	 
	        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
	 
	        // start the image capture Intent
	        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
	    }
	 
	    /**
	     * Here we store the file url as it will be null after returning from camera
	     * app
	     */
	    @Override
	    protected void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	 
	        // save file url in bundle as it will be null on scren orientation
	        // changes
	        outState.putParcelable("file_uri", fileUri);
	    }
	 
	    @Override
	    protected void onRestoreInstanceState(Bundle savedInstanceState) {
	        super.onRestoreInstanceState(savedInstanceState);
	 
	        // get the file url
	        fileUri = savedInstanceState.getParcelable("file_uri");
	    }
	 
	    /**
	     * Recording video
	     */
	    private void recordVideo() {
	        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
	 
	        fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
	 
	        // set video quality
	        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
	 
	        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file
	                                                            // name
	 
	        // start the video capture Intent
	        startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
	    }
	 
	    /**
	     * Receiving activity result method will be called after closing the camera
	     * */
	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        // if the result is capturing Image
	        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
	            if (resultCode == RESULT_OK) {
	                // successfully captured the image
	                // display it in image view
	        //        previewCapturedImage();
	            } else if (resultCode == RESULT_CANCELED) {
	                // user cancelled Image capture
	                Toast.makeText(getApplicationContext(),
	                        "User cancelled image capture", Toast.LENGTH_SHORT)
	                        .show();
	            } else {
	                // failed to capture image
	                Toast.makeText(getApplicationContext(),
	                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
	                        .show();
	            }
	        } else if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
	            if (resultCode == RESULT_OK) {
	                // video successfully recorded
	                // preview the recorded video
	       //         previewVideo();
	            } else if (resultCode == RESULT_CANCELED) {
	                // user cancelled recording
	                Toast.makeText(getApplicationContext(),
	                        "User cancelled video recording", Toast.LENGTH_SHORT)
	                        .show();
	            } else {
	                // failed to record video
	                Toast.makeText(getApplicationContext(),
	                        "Sorry! Failed to record video", Toast.LENGTH_SHORT)
	                        .show();
	            }
	        }
	    }
	 
	    /*
	     * Display image from a path to ImageView
	     
	    private void previewCapturedImage() {
	        try {
	            // hide video preview
	            videoPreview.setVisibility(View.GONE);
	 
	            imgPreview.setVisibility(View.VISIBLE);
	 
	            // bimatp factory
	            BitmapFactory.Options options = new BitmapFactory.Options();
	 
	            // downsizing image as it throws OutOfMemory Exception for larger
	            // images
	            options.inSampleSize = 8;
	 
	            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
	                    options);
	 
	            imgPreview.setImageBitmap(bitmap);
	        } catch (NullPointerException e) {
	            e.printStackTrace();
	        }
	    }
	 */
	    /*
	     * Previewing recorded video
	     
	    private void previewVideo() {
	        try {
	            // hide image preview
	            imgPreview.setVisibility(View.GONE);
	 
	            videoPreview.setVisibility(View.VISIBLE);
	            videoPreview.setVideoPath(fileUri.getPath());
	            // start playing
	            videoPreview.start();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	     */
	    /**
	     * ------------ Helper Methods ---------------------- 
	     * */
	 
	    /**
	     * Creating file uri to store image/video
	     */
	    public Uri getOutputMediaFileUri(int type) {
	        return Uri.fromFile(getOutputMediaFile(type));
	    }
	 
	    /**
	     * returning image / video
	     */
	    private static File getOutputMediaFile(int type) {
	 
	        // External sdcard location
	        File IMGmediaStorageDir = new File(Environment.getExternalStorageDirectory().getPath()+IMAGE_DIRECTORY_NAME);
	        File VIDmediaStorageDir = new File(Environment.getExternalStorageDirectory().getPath()+VIDEO_DIRECTORY_NAME);
	   //   File AUDmediaStorageDir = new File(Environment.getExternalStorageDirectory().getPath()+AUDIO_DIRECTORY_NAME);
	 
	        // Create the storage directory if it does not exist
	        if (!VIDmediaStorageDir.exists()) {
	            if (!VIDmediaStorageDir.mkdirs()) {
	                Log.d(VIDEO_DIRECTORY_NAME, "Oops! Failed create "
	                        + VIDEO_DIRECTORY_NAME + " directory");
	                return null;
	            }
	        }
	        
	        if (!IMGmediaStorageDir.exists()) {
	            if (!IMGmediaStorageDir.mkdirs()) {
	                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
	                        + IMAGE_DIRECTORY_NAME + " directory");
	                return null;
	            }
	        }
	 
	        // Create a media file name
	        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
	                Locale.getDefault()).format(new Date());
	        File mediaFile;
	        if (type == MEDIA_TYPE_IMAGE) {
	            mediaFile = new File(IMGmediaStorageDir.getPath() + File.separator
	                    + "IMG_" + timeStamp + ".jpg");
	        } else if (type == MEDIA_TYPE_VIDEO) {
	            mediaFile = new File(VIDmediaStorageDir.getPath() + File.separator
	                    + "VID_" + timeStamp + ".mp4");
	        } else {
	            return null;
	        }
	 
	        return mediaFile;
	    }
}
