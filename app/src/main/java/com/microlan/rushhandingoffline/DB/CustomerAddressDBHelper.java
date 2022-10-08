package com.microlan.rushhandingoffline.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.microlan.rushhandingoffline.OfflineModel.AllCustomerAddressModel;

import java.util.ArrayList;

public class CustomerAddressDBHelper extends SQLiteOpenHelper
{

    private Context context;
    public static final String DATABASE_NAME = "address.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "address_table";


    public static final String COLUMN_ADDRESSID = "address_id";
    public static final String COLUMN_CUSTOMERID = "customerid";
    public static final String COLUMN_ADDRESS1 = "address1";
    public static final String COLUMN_ADDRESS2 = "address2";
    public static final String COLUMN_CITY = "town_city";
    public static final String COLUMN_AREA = "area";
    public static final String COLUMN_TYPE = "addressType";
    public static final String COLUMN_STATECODE = "stateCode";
    public static final String COLUMN_MOBILE = "mobile";
    public static final String COLUMN_STATE = "state";
    public static final String COLUMN_PINCODE = "pincode";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_CUSTOMERNAME = "customername";
    public static final String COLUMN_FLAG = "flag";
    public static final String COLUMN_UNIQEID = "uniq";

    private static OnDatabaseChangedListener mOnDatabaseChangedListener;


    private static final String COMA_SEP = ",";
    private static final String SQLITE_CREATE_TABLE  = "CREATE TABLE "+TABLE_NAME + " ("+COLUMN_ADDRESSID+" INTEGER PRIMARY KEY" +
            " AUTOINCREMENT"+COMA_SEP+
            COLUMN_CUSTOMERID + " TEXT" +COMA_SEP+
            COLUMN_CUSTOMERNAME + " TEXT" +COMA_SEP+
            COLUMN_TYPE + " TEXT"+COMA_SEP+
            COLUMN_ADDRESS1 + " TEXT"+COMA_SEP+
            COLUMN_ADDRESS2 + " TEXT"+COMA_SEP+
            COLUMN_AREA + " TEXT"+COMA_SEP+
            COLUMN_CITY + " TEXT"+COMA_SEP+
            COLUMN_MOBILE + " TEXT"+COMA_SEP+
            COLUMN_EMAIL + " TEXT"+COMA_SEP+
            COLUMN_STATE + " TEXT"+COMA_SEP+
            COLUMN_STATECODE + " TEXT"+COMA_SEP+
            COLUMN_PINCODE + " TEXT"+COMA_SEP+
            COLUMN_UNIQEID + " VARCHAR"+COMA_SEP+
            COLUMN_FLAG + " INTEGER"+ ")";


    public CustomerAddressDBHelper(Context context)
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
            db.execSQL("ALTER TABLE Address_table ADD COLUMN inquiry_id INTEGER DEFAULT 0");
        }
        else {
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);

        }
    }

    public ArrayList<AllCustomerAddressModel> deleteItem(String item) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "uniq=?";
        String whereArgs[] = {item};
        db.delete(TABLE_NAME, whereClause, whereArgs);

        Log.d("","deleteItem v"+item);
        return null;
    }

    public boolean addaddress(AllCustomerAddressModel recordingItem)
    {
        try
        {
            SQLiteDatabase db = getWritableDatabase();



            ContentValues contentValues = new ContentValues();
            //contentValues.put(COLUMN_Id,value);


            contentValues.put(COLUMN_CUSTOMERID,recordingItem.getCustomerId());
            contentValues.put(COLUMN_ADDRESS1,recordingItem.getAddress1());
            contentValues.put(COLUMN_ADDRESS2,recordingItem.getAddress2());
            contentValues.put(COLUMN_CITY,recordingItem.getTownCity());
            contentValues.put(COLUMN_MOBILE,recordingItem.getMobileNumber());
            contentValues.put(COLUMN_EMAIL,recordingItem.getEmailAddress());
            contentValues.put(COLUMN_STATE,recordingItem.getState());
            contentValues.put(COLUMN_PINCODE,recordingItem.getPincode());
            contentValues.put(COLUMN_AREA,recordingItem.getLandmarkNearestArea());
            contentValues.put(COLUMN_STATECODE,recordingItem.getStateCode());
            contentValues.put(COLUMN_TYPE,recordingItem.getAddressType());
            contentValues.put(COLUMN_CUSTOMERNAME,recordingItem.getFullName());
            contentValues.put(COLUMN_FLAG,recordingItem.getFlag());
            contentValues.put(COLUMN_UNIQEID,recordingItem.getUniqeid());

            Log.d("","Address add"+contentValues);
            db.insert(TABLE_NAME,null,contentValues);
            //   $insertId = db.insert(TABLE_NAME,null,contentValues);

            if (mOnDatabaseChangedListener != null) {
                mOnDatabaseChangedListener.onNewDatabaseEntryAddress(recordingItem);
            }


            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateaddress(AllCustomerAddressModel recordingItem)
    {
        try
        {
            SQLiteDatabase db = getWritableDatabase();



            ContentValues contentValues = new ContentValues();
            //contentValues.put(COLUMN_Id,value);


            contentValues.put(COLUMN_CUSTOMERID,recordingItem.getCustomerId());
            contentValues.put(COLUMN_ADDRESS1,recordingItem.getAddress1());
            contentValues.put(COLUMN_ADDRESS2,recordingItem.getAddress2());
            contentValues.put(COLUMN_CITY,recordingItem.getTownCity());
            contentValues.put(COLUMN_MOBILE,recordingItem.getMobileNumber());
            contentValues.put(COLUMN_EMAIL,recordingItem.getEmailAddress());
            contentValues.put(COLUMN_STATE,recordingItem.getState());
            contentValues.put(COLUMN_PINCODE,recordingItem.getPincode());
            contentValues.put(COLUMN_AREA,recordingItem.getLandmarkNearestArea());
            contentValues.put(COLUMN_STATECODE,recordingItem.getStateCode());
            contentValues.put(COLUMN_TYPE,recordingItem.getAddressType());
            contentValues.put(COLUMN_CUSTOMERNAME,recordingItem.getFullName());
            contentValues.put(COLUMN_CUSTOMERNAME,recordingItem.getFullName());
            contentValues.put(COLUMN_FLAG,recordingItem.getFlag());
            contentValues.put(COLUMN_UNIQEID,recordingItem.getUniqeid());

            Log.d("","Address add"+contentValues);
            db.update(CustomerAddressDBHelper.TABLE_NAME, contentValues, COLUMN_UNIQEID+" = "+recordingItem.getUniqeid(),null);
            //   $insertId = db.insert(TABLE_NAME,null,contentValues);

            if (mOnDatabaseChangedListener != null) {
                mOnDatabaseChangedListener.onNewDatabaseEntryAddress(recordingItem);
            }


            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }


    public boolean updateorderdataflag(AllCustomerAddressModel recordingItem)
    {
        try
        {
            SQLiteDatabase db = getWritableDatabase();



            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_ADDRESSID,recordingItem.getAddressid());
            contentValues.put(COLUMN_CUSTOMERID,recordingItem.getCustomerId());
            contentValues.put(COLUMN_FLAG,recordingItem.getFlag());

            Log.d("","contentValues..... "+contentValues);
            //   db.insert(TABLE_NAME,null,contentValues);
            db.update(TABLE_NAME, contentValues, " order_flag = ?"+"1", null);

            if (mOnDatabaseChangedListener != null) {
                mOnDatabaseChangedListener.onNewDatabaseEntryAddress(recordingItem);
            }


            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<AllCustomerAddressModel> getaddress(String flags)
    {
        ArrayList<AllCustomerAddressModel> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_FLAG + " like ?",new String[]{"%" + flags + "%"});

    //    Cursor cursor =  db.rawQuery( "select * from Address_table ", null );


       // Cursor cursor =  db.rawQuery( "select * from Address_table ", null );
        if(cursor!=null)
        {
            while (cursor.moveToNext())
            {


                String address_id = cursor.getString(0);
                String customer_id = cursor.getString(1);
                String name = cursor.getString(2);
                String type = cursor.getString(3);
                String address1 = cursor.getString(4);
                String address2 = cursor.getString(5);
                String area = cursor.getString(6);
                String city = cursor.getString(7);
                String  mobile= cursor.getString(8);
                String email  = cursor.getString(9);
                String  state= cursor.getString(10);
                String pincode = cursor.getString(11);
                String  code= cursor.getString(12);
                String  uniqe= cursor.getString(13);
                String  flag= cursor.getString(14);


                // Log.d("","Col ID "+id);
                AllCustomerAddressModel recordingItem  = new AllCustomerAddressModel(city,customer_id,pincode,area,name,email, type, address2,address1,state,mobile,code,flag,uniqe);
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
    public ArrayList<AllCustomerAddressModel> geexitaddress(String uniqe)
    {
        ArrayList<AllCustomerAddressModel> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

       // Cursor cursor =  db.rawQuery( "select * from Address_table ", null );
        Cursor cursor =  db.rawQuery( "select * from Address_table where uniq ="+uniqe+"", null );


       // Cursor cursor =  db.rawQuery( "select * from Address_table ", null );
        if(cursor!=null)
        {
            while (cursor.moveToNext())
            {


                String address_id = cursor.getString(0);
                String customer_id = cursor.getString(1);
                String name = cursor.getString(2);
                String type = cursor.getString(3);
                String address1 = cursor.getString(4);
                String address2 = cursor.getString(5);
                String area = cursor.getString(6);
                String city = cursor.getString(7);
                String  mobile= cursor.getString(8);
                String email  = cursor.getString(9);
                String  state= cursor.getString(10);
                String pincode = cursor.getString(11);
                String  code= cursor.getString(12);
                String  uniqes= cursor.getString(13);
                String  flag= cursor.getString(14);


                // Log.d("","Col ID "+id);
                AllCustomerAddressModel recordingItem  = new AllCustomerAddressModel(city,customer_id,pincode,area,name,email, type, address2,address1,state,mobile,code,flag,uniqes);
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
    public ArrayList<AllCustomerAddressModel> getalladdress(String customerid)
    {
        ArrayList<AllCustomerAddressModel> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Log.d("","customerid"+customerid);

        Cursor cursor =  db.rawQuery( "select * from Address_table where customerid ="+customerid+"", null );


       // Cursor cursor =  db.rawQuery( "select * from Address_table ", null );
        if(cursor!=null)
        {
            while (cursor.moveToNext())
            {


                String address_id = cursor.getString(0);
                String customer_id = cursor.getString(1);
                String name = cursor.getString(2);
                String type = cursor.getString(3);
                String address1 = cursor.getString(4);
                String address2 = cursor.getString(5);
                String area = cursor.getString(6);
                String city = cursor.getString(7);
                String  mobile= cursor.getString(8);
                String email  = cursor.getString(9);
                String  state= cursor.getString(10);
                String pincode = cursor.getString(11);
                String  code= cursor.getString(12);
                String  uniqe= cursor.getString(13);
                String  flag= cursor.getString(14);


                // Log.d("","Col ID "+id);
                AllCustomerAddressModel recordingItem  = new AllCustomerAddressModel(city,customer_id,pincode,area,name,email, type, address2,address1,state,mobile,code,flag,uniqe);
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