package com.guc.dear_diary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class SaverSQL extends SQLiteOpenHelper {
	
	  // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TODO.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    public static final String TABLE_NAME = "todo";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_DESC = "desc";
    public static final String COLUMN_NAME_DATE = "date";
   /* private static final String SQL_CREATE_ENTRIES =
    	    "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
    	    FeedEntry._ID + " INTEGER PRIMARY KEY," +
    	    FeedEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
    	    FeedEntry.COLUMN_NAME_DESC + TEXT_TYPE + COMMA_SEP +
    	    FeedEntry.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +" )";
    */
    private static final String SQL_CREATE_ENTRIES = "create table "
    	      + TABLE_NAME + "(" + COLUMN_ID
    	      + " integer primary key autoincrement, "  
    	      +COLUMN_NAME_TITLE  + " text,"
    	      + COLUMN_NAME_DESC + " text,"
    	      + COLUMN_NAME_DATE + " text);";
    
    private static final String SQL_DELETE_ENTRIES =
    	    "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
    
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "todo";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESC = "desc";
        public static final String COLUMN_NAME_DATE = "date";
        
    }
    
    
    public SaverSQL(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
