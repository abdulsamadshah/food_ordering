package com.microlan.rushhandingoffline.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.microlan.rushhandingoffline.OfflineModel.AllGstModel;
import com.microlan.rushhandingoffline.OfflineModel.CompanySettingModel;

import java.util.ArrayList;

public class CompanySettingDBHelper extends SQLiteOpenHelper
{

    private Context context;
    public static final String DATABASE_NAME = "rush_handings.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "companysetting_table";


    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_GSTNO= "gst";
    public static final String COLUMN_STATE = "state";
    public static final String COLUMN_PANNO = "panno";
    public static final String COLUMN_CONTACT = "contact";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_WEBSITE = "website";
    public static final String COLUMN_CONDITION = "condition";

    private static OnDatabaseChangedListener mOnDatabaseChangedListener;


    private static final String COMA_SEP = ",";
    private static final String SQLITE_CREATE_TABLE  = "CREATE TABLE "+TABLE_NAME + " ("+"id INTEGER PRIMARY KEY" +
            " AUTOINCREMENT"+COMA_SEP+
            COLUMN_NAME + " TEXT" +COMA_SEP+
            COLUMN_ADDRESS + " TEXT"+COMA_SEP+
            COLUMN_GSTNO + " TEXT"+COMA_SEP+
            COLUMN_STATE + " VARCHAR"+COMA_SEP+
            COLUMN_PANNO + " TEXT"+COMA_SEP+
            COLUMN_CONTACT + " TEXT"+COMA_SEP+
            COLUMN_EMAIL + " TEXT"+COMA_SEP+
            COLUMN_WEBSITE + " TEXT"+COMA_SEP+
            COLUMN_CONDITION + " INTEGER"+ ")";


    public CompanySettingDBHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLITE_CREATE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (newVersion > oldVersion) {
            db.execSQL("ALTER TABLE companysetting_table ADD COLUMN inquiry_id INTEGER DEFAULT 0");
        }
        else {
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);

        }
    }

    public void deleteItem(String item) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "id=?";
        String whereArgs[] = {item};
        db.delete(TABLE_NAME, whereClause, whereArgs);

        Log.d("","deleteItem v"+item);
    }

    public boolean addSetting(CompanySettingModel recordingItem)
    {
        try
        {
            SQLiteDatabase db = getWritableDatabase();



            ContentValues contentValues = new ContentValues();
            //contentValues.put(COLUMN_Id,value);

            contentValues.put(COLUMN_NAME,recordingItem.getName());
            contentValues.put(COLUMN_ADDRESS,recordingItem.getAddress());
            contentValues.put(COLUMN_GSTNO,recordingItem.getGst());
            contentValues.put(COLUMN_STATE,recordingItem.getState());
            contentValues.put(COLUMN_PANNO,recordingItem.getPan());
            contentValues.put(COLUMN_CONTACT,recordingItem.getCaontac());
            contentValues.put(COLUMN_EMAIL,recordingItem.getEmail());
            contentValues.put(COLUMN_WEBSITE,recordingItem.getWebsite());
            contentValues.put(COLUMN_CONDITION,recordingItem.getCaontac());

            Log.d("","arraycompanyitem add"+contentValues);
            db.insert(TABLE_NAME,null,contentValues);
           // db.update(TABLE_NAME, contentValues, "", null);
            //   $insertId = db.insert(TABLE_NAME,null,contentValues);

            if (mOnDatabaseChangedListener != null) {
                mOnDatabaseChangedListener.onNewDatabaseEntryallsetting(recordingItem);
            }


            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateSetting(CompanySettingModel recordingItem)
    {
        try
        {
            SQLiteDatabase db = getWritableDatabase();



            ContentValues contentValues = new ContentValues();
            //contentValues.put(COLUMN_Id,value);

            contentValues.put(COLUMN_NAME,recordingItem.getName());
            contentValues.put(COLUMN_ADDRESS,recordingItem.getAddress());
            contentValues.put(COLUMN_GSTNO,recordingItem.getGst());
            contentValues.put(COLUMN_STATE,recordingItem.getState());
            contentValues.put(COLUMN_PANNO,recordingItem.getPan());
            contentValues.put(COLUMN_CONTACT,recordingItem.getCaontac());
            contentValues.put(COLUMN_EMAIL,recordingItem.getEmail());
            contentValues.put(COLUMN_WEBSITE,recordingItem.getWebsite());
            contentValues.put(COLUMN_CONDITION,recordingItem.getCaontac());

            Log.d("","arraycompanyitem add"+contentValues);
            db.update(TABLE_NAME, contentValues, " id = ?"+recordingItem.getId(), null);

            if (mOnDatabaseChangedListener != null) {
                mOnDatabaseChangedListener.onNewDatabaseEntryallsetting(recordingItem);
            }


            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }


    public ArrayList<CompanySettingModel> getsettingdetails()
    {
        ArrayList<CompanySettingModel> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();



       //  Cursor cursor =  db.rawQuery( "select * from companysetting_table", null );

        Cursor cursor =  db.rawQuery( "select * from companysetting_table ", null );

        if(cursor!=null)
        {
            while (cursor.moveToNext())
            {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String address = cursor.getString(2);
                String gst = cursor.getString(3);
                String state  = cursor.getString(4);
                String pen = cursor.getString(5);
                String contact = cursor.getString(6);
                String email = cursor.getString(7);
                String  web= cursor.getString(8);
                String  condition= cursor.getString(9);


                // Log.d("","Col ID "+id);
                CompanySettingModel recordingItem  = new CompanySettingModel(name,address,gst,state,pen,contact, email, web,condition);
                arrayList.add(recordingItem);
            }
            cursor.close();
            return arrayList;
        }
        else
        {
            return null;
        }

    }

    public static void setOnDatabaseChangedListener(OnDatabaseChangedListener listener) {
        mOnDatabaseChangedListener = listener;
    }


}