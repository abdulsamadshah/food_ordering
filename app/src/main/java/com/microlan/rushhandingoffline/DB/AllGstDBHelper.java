package com.microlan.rushhandingoffline.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.microlan.rushhandingoffline.OfflineModel.AllGstModel;

import java.util.ArrayList;

public class AllGstDBHelper extends SQLiteOpenHelper
{

    private Context context;
    public static final String DATABASE_NAME = "rush_handing.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "gst_table";


    public static final String COLUMN_GSTID = "gstid";
    public static final String COLUMN_TYPE = "gst_type";
    public static final String COLUMN_HSN_CODE = "hsn_code";
    public static final String COLUMN_DESC = "description";
    public static final String COLUMN_SGST = "SGST";
    public static final String COLUMN_CGST = "CGST";
    public static final String COLUMN_IGST = "pincode";
    public static final String COLUMN_CESS = "CESS";

    private static OnDatabaseChangedListener mOnDatabaseChangedListener;


    private static final String COMA_SEP = ",";
    private static final String SQLITE_CREATE_TABLE  = "CREATE TABLE "+TABLE_NAME + " ("+"id INTEGER PRIMARY KEY" +
            " AUTOINCREMENT"+COMA_SEP+
            COLUMN_GSTID + " TEXT" +COMA_SEP+
            COLUMN_TYPE + " TEXT"+COMA_SEP+
            COLUMN_HSN_CODE + " TEXT"+COMA_SEP+
            COLUMN_DESC + " VARCHAR"+COMA_SEP+
            COLUMN_SGST + " TEXT"+COMA_SEP+
            COLUMN_CGST + " TEXT"+COMA_SEP+
            COLUMN_IGST + " TEXT"+COMA_SEP+
            COLUMN_CESS + " INTEGER"+ ")";


    public AllGstDBHelper(Context context)
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
            db.execSQL("ALTER TABLE gst_table ADD COLUMN inquiry_id INTEGER DEFAULT 0");
        }
        else {
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);

        }
    }

    public ArrayList<AllGstModel> deleteItem(String item) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "gstid=?";
        String whereArgs[] = {item};
        db.delete(TABLE_NAME, whereClause, whereArgs);

        Log.d("","deleteItem v"+item);
        return null;
    }

    public ArrayList<AllGstModel> getallgstss()
    {
        ArrayList<AllGstModel> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();



        // Cursor cursor =  db.rawQuery( "select * from gst_table ", null );

        Cursor cursor =  db.rawQuery( "select * from gst_table ", null );

        if(cursor!=null)
        {
            while (cursor.moveToNext())
            {
                // arrayList.id = cursor.getInt(cursor.getColumnIndex(ItemTable.COL_ID));

                //  String id = cursor.getString(0);
                String id = cursor.getString(0);
                String gstsetid = cursor.getString(1);
                String type = cursor.getString(2);
                String code = cursor.getString(3);
                String desc  = cursor.getString(4);
                String sgst = cursor.getString(5);
                String cgst = cursor.getString(6);
                String igst = cursor.getString(7);
                String  cess= cursor.getString(8);


                // Log.d("","Col ID "+id);
                AllGstModel recordingItem  = new AllGstModel(id,gstsetid,type,code,desc,sgst,cgst, igst, cess);
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

    public boolean addgst(AllGstModel recordingItem)
    {
        try
        {
            SQLiteDatabase db = getWritableDatabase();



            ContentValues contentValues = new ContentValues();
            //contentValues.put(COLUMN_Id,value);


            contentValues.put(COLUMN_GSTID,recordingItem.getGstSetId());
            contentValues.put(COLUMN_TYPE,recordingItem.getGstType());
            contentValues.put(COLUMN_HSN_CODE,recordingItem.getHsnCode());
            contentValues.put(COLUMN_DESC,recordingItem.getDescription());
            contentValues.put(COLUMN_SGST,recordingItem.getsGST());
            contentValues.put(COLUMN_CGST,recordingItem.getcGST());
            contentValues.put(COLUMN_IGST,recordingItem.getiGST());
            contentValues.put(COLUMN_CESS,recordingItem.getcESS());

            Log.d("","GST add"+contentValues);
            db.insert(TABLE_NAME,null,contentValues);
            //   $insertId = db.insert(TABLE_NAME,null,contentValues);

            if (mOnDatabaseChangedListener != null) {
                mOnDatabaseChangedListener.onNewDatabaseEntryallgst(recordingItem);
            }


            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateexitsgst(AllGstModel recordingItem)
    {
        try
        {
            SQLiteDatabase db = getWritableDatabase();



            ContentValues contentValues = new ContentValues();
            //contentValues.put(COLUMN_Id,value);


            contentValues.put(COLUMN_GSTID,recordingItem.getGstSetId());
            contentValues.put(COLUMN_TYPE,recordingItem.getGstType());
            contentValues.put(COLUMN_HSN_CODE,recordingItem.getHsnCode());
            contentValues.put(COLUMN_DESC,recordingItem.getDescription());
            contentValues.put(COLUMN_SGST,recordingItem.getsGST());
            contentValues.put(COLUMN_CGST,recordingItem.getcGST());
            contentValues.put(COLUMN_IGST,recordingItem.getiGST());
            contentValues.put(COLUMN_CESS,recordingItem.getcESS());

            Log.d("","GST add"+contentValues);
            db.update(TABLE_NAME, contentValues, " gstid = ?"+recordingItem.getGstSetId(), null);

            // db.insert(TABLE_NAME,null,contentValues);
            //   $insertId = db.insert(TABLE_NAME,null,contentValues);

            if (mOnDatabaseChangedListener != null) {
                mOnDatabaseChangedListener.onNewDatabaseEntryallgst(recordingItem);
            }


            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<AllGstModel> updateallgst(String gstid)
    {
        ArrayList<AllGstModel> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();


        Log.d("","productid hsn "+gstid);

       // Cursor cursor =  db.rawQuery( "select * from gst_table ", null );

        Cursor cursor =  db.rawQuery( "select * from gst_table where gstid ="+gstid+"", null );

        if(cursor!=null)
        {
            while (cursor.moveToNext())
            {
                // arrayList.id = cursor.getInt(cursor.getColumnIndex(ItemTable.COL_ID));

                //  String id = cursor.getString(0);
                String id = cursor.getString(0);
                String gstsetid = cursor.getString(1);
                String type = cursor.getString(2);
                String code = cursor.getString(3);
                String desc  = cursor.getString(4);
                String sgst = cursor.getString(5);
                String cgst = cursor.getString(6);
                String igst = cursor.getString(7);
                String  cess= cursor.getString(8);


                // Log.d("","Col ID "+id);
                AllGstModel recordingItem  = new AllGstModel(gstsetid,type,code,desc,sgst,cgst, igst, cess);
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
    public ArrayList<AllGstModel> getallgst(int productid)
    {
        ArrayList<AllGstModel> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();


        Log.d("","productid hsn "+productid);

       // Cursor cursor =  db.rawQuery( "select * from gst_table ", null );

        Cursor cursor =  db.rawQuery( "select * from gst_table where hsn_code ="+productid+"", null );

        if(cursor!=null)
        {
            while (cursor.moveToNext())
            {
                // arrayList.id = cursor.getInt(cursor.getColumnIndex(ItemTable.COL_ID));

                //  String id = cursor.getString(0);
                String id = cursor.getString(0);
                String gstsetid = cursor.getString(1);
                String type = cursor.getString(2);
                String code = cursor.getString(3);
                String desc  = cursor.getString(4);
                String sgst = cursor.getString(5);
                String cgst = cursor.getString(6);
                String igst = cursor.getString(7);
                String  cess= cursor.getString(8);


                // Log.d("","Col ID "+id);
                AllGstModel recordingItem  = new AllGstModel(gstsetid,type,code,desc,sgst,cgst, igst, cess);
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