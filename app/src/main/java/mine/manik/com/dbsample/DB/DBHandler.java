package mine.manik.com.dbsample.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by manikkam on 1/2/18.
 */

public class DBHandler extends SQLiteOpenHelper {

    private final static String TAG="ConnectDB";
    // All Static variables

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "emp";

    // Contacts table name
    private static final String TABLE_NAME= "profile";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE="create table profile (id INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT,mobile TEXT,dep TEXT)";
        db.execSQL(CREATE_CONTACTS_TABLE);
        Log.d(TAG,"Table Create Successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(db);

    }

    // Adding new contact
    public void insertNewData(String name,String mobile,String dep) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("username", name);
        values.put("mobile", mobile);
        values.put("dep", dep);

        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
        Log.d(TAG,"Insert Success," + "Name:"+name);
    }

    // Getting contacts Count
    public boolean getNumberExists(String mobile) {

        String countQuery = "SELECT * FROM profile where mobile='" + mobile +"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count=cursor.getCount();
        cursor.close();
        db.close();
        return count>0;
    }

    public void updateProfile(String name,String dep,String mobile) {

        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("Update " + TABLE_NAME + " Set username='" + name + "',dep='" + dep+ "' Where mobile='" + mobile +"'");
        db.close();
        Log.d(TAG,"Updated:"+"Record Updated");

    }

    public void removeProfile(String mobile) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_NAME + " where mobile='" + mobile +"'");
        db.close();
        Log.d(TAG,"Saved Form Deleted");
    }

    public Cursor getEmpDetails() {
        String selectQuery = "SELECT id,username,mobile,dep FROM profile order by username";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        return cursor;
    }

}
