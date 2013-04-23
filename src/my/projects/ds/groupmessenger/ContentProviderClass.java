package my.projects.ds.groupmessenger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.net.Uri;
import android.provider.BaseColumns;
import android.app.SearchManager;
import android.content.ContentUris;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
//import android.content.ContentValues;

/* 
 * 
 *   Parts of the below code based on the code from the following websites :
 *   http://www.vogella.de/articles/AndroidSQLite/article.html#databasetutorial 
 *   http://thinkandroid.wordpress.com/2010/01/13/writing-your-own-contentprovider/
 *   http://developer.android.com/resources/samples/NotePad/src/com/example/android/notepad/NotePadProvider.html
 * 
 */


public class ContentProviderClass extends SQLiteOpenHelper {

	public static final String TABLE_NAME = "MessengerDB";
	public static final String COLUMN_KEY = "provider_key";
	public static final String COLUMN_VALUE = "provider_value";
    public static final Uri DB_URI = Uri.parse("content://edu.buffalo.cse.cse486_586.provider"); 
	private static final String DATABASE_NAME = "GroupMessenger.db";
	private static final int DATABASE_VERSION = 1;
	public static final String AUTHORITY = "my.projects.ds.groupmessenger";
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_NAME + "( " + COLUMN_KEY
			+ " integer primary key autoincrement, " + COLUMN_VALUE
			+ " text not null);";
	
	public ContentProviderClass(Context context)
	{ super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
	  db.execSQL(DATABASE_CREATE);  	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);	
	}
}

class MessengerDBclass
{
   int key;
   String value;
   public int getKey()
   {
	 return key;  
   }
   public String getValue()
   {
	 return value;
   }
   public void setKey(int n)
   {
	 key = n;  
   }
   public void setValue(String val)
   {
	 value = val;  
   }
   public String toString() {
		return value;
	}
}


class ContentValues {

	// Database fields
	SQLiteDatabase database;
	ContentProviderClass cProvider;
	
	String[] columns = { ContentProviderClass.COLUMN_KEY, ContentProviderClass.COLUMN_VALUE};
	int key;
	String value;
	
	public ContentValues(Context context) {
		cProvider = new ContentProviderClass(context);
	}
	
	public void open() {
	  database = cProvider.getWritableDatabase();	
	}
	
	public void close() {
	  cProvider.close();
	}
	
	public MessengerDBclass createDB(String s) {
		ContentValues values = new ContentValues(null);
		
		
		return null;
		
	}
		
}

