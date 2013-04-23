package my.projects.ds.groupmessenger;


//import com.example.android.notepad.NotePad;

import android.content.ClipDescription;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.ContentProvider.PipeDataWriter;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.LiveFolders;
import android.text.TextUtils;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/* 
 * 
 *   Parts of the below code based on the code from the following websites :
 *   http://www.vogella.de/articles/AndroidSQLite/article.html#databasetutorial 
 *   http://thinkandroid.wordpress.com/2010/01/13/writing-your-own-contentprovider/
 *   http://developer.android.com/resources/samples/NotePad/src/com/example/android/notepad/NotePadProvider.html
 * 
 */

public class Messenger_ContentProvider extends ContentProvider{
	private static final String TAG = "MyActivity";
	public static final String TABLE_NAME = "MessengerDB";
	public static final String COLUMN_KEY = "provider_key";
	public static final String COLUMN_VALUE = "provider_value";
    public static final Uri DB_URI = Uri.parse("content://edu.buffalo.cse.cse486_586.provider"); 
	private static final String DATABASE_NAME = "GroupMessenger.db";
	private static final int DATABASE_VERSION = 2;
	public static final String AUTHORITY = "my.projects.ds.groupmessenger";	
	
	private static HashMap<String, String> sLiveFolderProjectionMap;
	private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	private static final String BASE_PATH = "provider";
	static {
		sUriMatcher.addURI(AUTHORITY, BASE_PATH, 10);
		sUriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", 20);
	}
	private DatabaseHelper MessengerHelper;

	private static final String DATABASE_CREATE = "create table "
			+ TABLE_NAME + "( " + COLUMN_KEY
			+ " integer primary key autoincrement, " + COLUMN_VALUE
			+ " text not null);";
	
	static class DatabaseHelper extends SQLiteOpenHelper {
	       DatabaseHelper(Context context) {
	           // calls the super constructor, requesting the default cursor factory.
	           super(context, DATABASE_NAME, null, DATABASE_VERSION);
	       }
           @Override
    	   public void onCreate(SQLiteDatabase db) {
       		// TODO Auto-generated method stub
       	     db.execSQL(DATABASE_CREATE);  	
       	   }
		   @Override
		   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
				db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
				onCreate(db);			
	     }
	   }


	   @Override
	   public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	   }
	   
	   @Override
	   public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	   }
	   
	   @Override
	   public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = MessengerHelper.getWritableDatabase();
		MessengerHelper = new DatabaseHelper(getContext());
		long rowId = db.insert(
                TABLE_NAME,        // The table to insert into.
                null,  // A hack, SQLite sets this column value to null
                values                           // A map of column names, and the values to insert
                                                 // into the columns.
            );
        if (rowId > 0) {
        	return Uri.parse(BASE_PATH + "/" + rowId);
        }
        // If the insert didn't succeed, then the rowID is <= 0. Throws an exception.
        throw new SQLException("Failed to insert row into " + uri);
	   }
	   
	   @Override
	   public boolean onCreate() {
		// TODO Auto-generated method stub
		   
		   
		return true;
	   }
	   
	   @Override
	   public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		   SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		   qb.setTables(TABLE_NAME);
		   SQLiteDatabase db = MessengerHelper.getReadableDatabase();
		   String orderBy = sortOrder;
		   Cursor c = qb.query(
		           db,            // The database to query
		           projection,    // The columns to return from the query
		           selection,     // The columns for the where clause
		           selectionArgs, // The values for the where clause
		           null,          // don't group the rows
		           null,          // don't filter by row groups
		           orderBy        // The sort order
		       );
		   c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	   }
	   @Override
	   public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		   return 0;
       }
}
