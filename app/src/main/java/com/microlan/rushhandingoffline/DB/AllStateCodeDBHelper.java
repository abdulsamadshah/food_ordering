package com.microlan.rushhandingoffline.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.microlan.rushhandingoffline.OfflineModel.AllGstModel;
import com.microlan.rushhandingoffline.OfflineModel.StateModel;

import java.util.ArrayList;

public class AllStateCodeDBHelper extends SQLiteOpenHelper
{

    private Context context;
    public static final String DATABASE_NAME = "rush_handings.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "state_table";


    public static final String COLUMN_STATENAME = "statename";
    public static final String COLUMN_STATECODE = "statecode";

    private static OnDatabaseChangedListener mOnDatabaseChangedListener;


    private static final String COMA_SEP = ",";
    private static final String SQLITE_CREATE_TABLE  = "CREATE TABLE "+TABLE_NAME + " ("+"id INTEGER PRIMARY KEY" +
            " AUTOINCREMENT"+COMA_SEP+
            COLUMN_STATENAME + " TEXT" +COMA_SEP+
            COLUMN_STATECODE + " INTEGER"+ ")";


    public AllStateCodeDBHelper(Context context)
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
            db.execSQL("ALTER TABLE state_table ADD COLUMN inquiry_id INTEGER DEFAULT 0");
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

    public boolean addstate(StateModel recordingItem)
    {
        try
        {
            SQLiteDatabase db = getWritableDatabase();



            ContentValues contentValues = new ContentValues();
            //contentValues.put(COLUMN_Id,value);


            contentValues.put(COLUMN_STATENAME,recordingItem.getState());
            contentValues.put(COLUMN_STATECODE,recordingItem.getStatecode());

            Log.d("","customer add"+contentValues);
            db.insert(TABLE_NAME,null,contentValues);
            //   $insertId = db.insert(TABLE_NAME,null,contentValues);

            if (mOnDatabaseChangedListener != null) {
                mOnDatabaseChangedListener.onNewDatabaseEntryallstate(recordingItem);
            }


            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }


    public ArrayList<StateModel> getallstae()
    {
        ArrayList<StateModel> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();



        Cursor cursor =  db.rawQuery( "select * from state_table ", null );
        if(cursor!=null)
        {
            while (cursor.moveToNext())
            {
                // arrayList.id = cursor.getInt(cursor.getColumnIndex(ItemTable.COL_ID));

                //  String id = cursor.getString(0);
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String code = cursor.getString(2);

                // Log.d("","Col ID "+id);
                StateModel recordingItem  = new StateModel(name,code);
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