package com.microlan.rushhandingoffline.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.microlan.rushhandingoffline.OfflineModel.AllCatogeryModel;

import java.util.ArrayList;

public class AllCatogeryDBHelper extends SQLiteOpenHelper
{

    private Context context;
    public static final String DATABASE_NAME = "allcatogery.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "allcatogery_table";


    public static final String COLUMN_Id = "id";
    public static final String COLUMN_CATEGORYID = "catogeryid";
    public static final String COLUMN_CATEGORYNAME = "catogeryname";
    public static final String COLUMN_IMAGEPATH = "imagepath";
    public static final String COLUMN_IMAGEPATHOFF = "imagepathoff";

    private static OnDatabaseCatogery mOnDatabaseChangedListener;


    private static final String COMA_SEP = ",";

    int value;




    private static final String SQLITE_CREATE_TABLE  = "CREATE TABLE "+TABLE_NAME+ " ("+"id INTEGER PRIMARY KEY" +
            " AUTOINCREMENT"+COMA_SEP+
            COLUMN_CATEGORYID + " INTEGER" +COMA_SEP+
            COLUMN_CATEGORYNAME + " TEXT" +COMA_SEP+
            COLUMN_IMAGEPATH + " TEXT" + ")";


    public AllCatogeryDBHelper(Context context)
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
            db.execSQL("ALTER TABLE allcatogery_table ADD COLUMN inquiry_id INTEGER DEFAULT 0");
        }
        else {
            // db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);

        }
    }

    public ArrayList<AllCatogeryModel> deleteItem(String item) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "catogeryid=?";
        String whereArgs[] = {item};
        db.delete(TABLE_NAME, whereClause, whereArgs);

        Log.d("","deleteItem v"+item);
        return null;
    }

    public boolean updateexitscatogery(AllCatogeryModel recordingItem)
    {
        try
        {
            SQLiteDatabase db = getWritableDatabase();



            ContentValues contentValues = new ContentValues();
            //contentValues.put(COLUMN_Id,value);


            contentValues.put(COLUMN_CATEGORYID,recordingItem.getCatogerId());
            contentValues.put(COLUMN_CATEGORYNAME,recordingItem.getCatogeryname());
            contentValues.put(COLUMN_IMAGEPATH,recordingItem.getCatogeryimage());


            db.update(TABLE_NAME, contentValues, " catogeryid = ?"+recordingItem.getCatogerId(), null);
            //   $insertId = db.insert(TABLE_NAME,null,contentValues);

            if (mOnDatabaseChangedListener != null) {
                mOnDatabaseChangedListener.onNewDatabaseCatogeryAdded(recordingItem);
            }


            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public boolean addcatogery(AllCatogeryModel recordingItem)
    {
        try
        {
            SQLiteDatabase db = getWritableDatabase();



            ContentValues contentValues = new ContentValues();
            //contentValues.put(COLUMN_Id,value);


            contentValues.put(COLUMN_CATEGORYID,recordingItem.getCatogerId());
            contentValues.put(COLUMN_CATEGORYNAME,recordingItem.getCatogeryname());
            contentValues.put(COLUMN_IMAGEPATH,recordingItem.getCatogeryimage());

            Log.d("","contentValues"+contentValues);

            db.insert(TABLE_NAME,null,contentValues);
            //   $insertId = db.insert(TABLE_NAME,null,contentValues);

            if (mOnDatabaseChangedListener != null) {
                mOnDatabaseChangedListener.onNewDatabaseCatogeryAdded(recordingItem);
            }


            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }


    public ArrayList<AllCatogeryModel> getallcatogery()
    {
        ArrayList<AllCatogeryModel> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();



        Cursor cursor =  db.rawQuery( "select * from allcatogery_table ", null );
        if(cursor!=null)
        {
            while (cursor.moveToNext())
            {
                // arrayList.id = cursor.getInt(cursor.getColumnIndex(ItemTable.COL_ID));

                //  String id = cursor.getString(0);
                String product_id = cursor.getString(1);
                String product_name = cursor.getString(2);
                String path = cursor.getString(3);


                AllCatogeryModel recordingItem  = new AllCatogeryModel(product_id,product_name,path);
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
    public ArrayList<AllCatogeryModel> addexitscatogery(String categoryIDString)
    {
        ArrayList<AllCatogeryModel> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();



        Cursor cursor =  db.rawQuery( "select * from allcatogery_table where catogeryid ="+categoryIDString+"", null );

    //    Cursor cursor =  db.rawQuery( "select * from allcatogery_table ", null );
        if(cursor!=null)
        {
            while (cursor.moveToNext())
            {
                // arrayList.id = cursor.getInt(cursor.getColumnIndex(ItemTable.COL_ID));

                //  String id = cursor.getString(0);
                String product_id = cursor.getString(1);
                String product_name = cursor.getString(2);
                String path = cursor.getString(3);


                AllCatogeryModel recordingItem  = new AllCatogeryModel(product_id,product_name,path);
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

    public static void setOnDatabaseChangedListener(OnDatabaseCatogery listener) {
        mOnDatabaseChangedListener = listener;
    }


}