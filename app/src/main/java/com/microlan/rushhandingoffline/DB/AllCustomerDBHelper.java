package com.microlan.rushhandingoffline.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.microlan.rushhandingoffline.OfflineModel.ALLCustomerModel;

import java.util.ArrayList;

public class AllCustomerDBHelper extends SQLiteOpenHelper
{

    private Context context;
    public static final String DATABASE_NAME = "customer.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "customers_table";


    public static final String COLUMN_CUSTOMERID = "customer_id";
    public static final String COLUMN_CUSTOMERNAME = "customername";
    public static final String COLUMN_ADDRESS1 = "address1";
    public static final String COLUMN_ADDRESS2 = "address2";
    public static final String COLUMN_CITY = "town_city";
    public static final String COLUMN_MOBILE = "mobile";
    public static final String COLUMN_STATE = "state";
    public static final String COLUMN_PINCODE = "pincode";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_CODE = "code";
    public static final String COLUMN_UNIQEID = "uniqu";
    public static final String COLUMN_FLAG = "flag";

    private static OnDatabaseChangedListener mOnDatabaseChangedListener;


    private static final String COMA_SEP = ",";
    private static final String SQLITE_CREATE_TABLE  = "CREATE TABLE "+TABLE_NAME + " ("+"id INTEGER PRIMARY KEY" +
            " AUTOINCREMENT"+COMA_SEP+
            COLUMN_CUSTOMERNAME + " TEXT" +COMA_SEP+
            COLUMN_ADDRESS1 + " TEXT"+COMA_SEP+
            COLUMN_ADDRESS2 + " TEXT"+COMA_SEP+
            COLUMN_CITY + " TEXT"+COMA_SEP+
            COLUMN_MOBILE + " TEXT"+COMA_SEP+
            COLUMN_EMAIL + " TEXT"+COMA_SEP+
            COLUMN_STATE + " TEXT"+COMA_SEP+
            COLUMN_PINCODE + " TEXT"+COMA_SEP+
            COLUMN_FLAG + " TEXT"+COMA_SEP+
            COLUMN_UNIQEID + " TEXT"+COMA_SEP+
            COLUMN_CODE + " INTEGER"+ ")";


    public AllCustomerDBHelper(Context context)
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
            db.execSQL("ALTER TABLE productsdetails_table ADD COLUMN inquiry_id INTEGER DEFAULT 0");
        }
        else {
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);

        }
    }

    public ArrayList<ALLCustomerModel> deleteItem(String item) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_UNIQEID+ "='" + item+"'", null);

        Log.d("","deleteItem v"+item);
        return null;
    }

    public boolean addcustomer(ALLCustomerModel recordingItem)
    {
        try
        {
            SQLiteDatabase db = getWritableDatabase();



            ContentValues contentValues = new ContentValues();
            //contentValues.put(COLUMN_Id,value);



            contentValues.put(COLUMN_CUSTOMERNAME,recordingItem.getCustomername());
            contentValues.put(COLUMN_ADDRESS1,recordingItem.getAddress1());
            contentValues.put(COLUMN_ADDRESS2,recordingItem.getAddress2());
            contentValues.put(COLUMN_CITY,recordingItem.getCity());
            contentValues.put(COLUMN_MOBILE,recordingItem.getCustomernumber());
            contentValues.put(COLUMN_EMAIL,recordingItem.getCustomeremail());
            contentValues.put(COLUMN_STATE,recordingItem.getState());
            contentValues.put(COLUMN_PINCODE,recordingItem.getPincode());
            contentValues.put(COLUMN_CODE,recordingItem.getCode());
            contentValues.put(COLUMN_FLAG,recordingItem.getFlag());
            contentValues.put(COLUMN_UNIQEID,recordingItem.getUniqeid());

            Log.d("","customer add"+contentValues);
            db.insert(TABLE_NAME,null,contentValues);
            //   $insertId = db.insert(TABLE_NAME,null,contentValues);

            if (mOnDatabaseChangedListener != null) {
                mOnDatabaseChangedListener.onNewDatabaseEntryAddedCustomer(recordingItem);
            }


            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updatecustomer(ALLCustomerModel recordingItem)
    {
        try
        {
            SQLiteDatabase db = getWritableDatabase();



            ContentValues contentValues = new ContentValues();
            //contentValues.put(COLUMN_Id,value);


            contentValues.put(COLUMN_CUSTOMERNAME,recordingItem.getCustomername());
            contentValues.put(COLUMN_ADDRESS1,recordingItem.getAddress1());
            contentValues.put(COLUMN_ADDRESS2,recordingItem.getAddress2());
            contentValues.put(COLUMN_CITY,recordingItem.getCity());
            contentValues.put(COLUMN_MOBILE,recordingItem.getCustomernumber());
            contentValues.put(COLUMN_EMAIL,recordingItem.getCustomeremail());
            contentValues.put(COLUMN_STATE,recordingItem.getState());
            contentValues.put(COLUMN_PINCODE,recordingItem.getPincode());
            contentValues.put(COLUMN_CODE,recordingItem.getCode());
            contentValues.put(COLUMN_UNIQEID,recordingItem.getUniqeid());
            contentValues.put(COLUMN_FLAG,recordingItem.getFlag());

            Log.d("","customer Update "+contentValues);
            db.update(AllCustomerDBHelper.TABLE_NAME, contentValues, COLUMN_UNIQEID+" = "+recordingItem.getUniqeid(),null);

            if (mOnDatabaseChangedListener != null) {
                mOnDatabaseChangedListener.onNewDatabaseEntryAddedCustomer(recordingItem);
            }


            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }



    public ArrayList<ALLCustomerModel> getallcustomer()
    {
        ArrayList<ALLCustomerModel> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();



        Cursor cursor =  db.rawQuery( "select * from customers_table ", null );
        if(cursor!=null)
        {
            while (cursor.moveToNext())
            {
                // arrayList.id = cursor.getInt(cursor.getColumnIndex(ItemTable.COL_ID));

                //  String id = cursor.getString(0);
                String customer_id = cursor.getString(0);
                String customer_name = cursor.getString(1);
                String address1 = cursor.getString(2);
                String address2 = cursor.getString(3);
                String city  = cursor.getString(4);
                String mobile = cursor.getString(5);
                String email = cursor.getString(6);
                String state = cursor.getString(7);
                String  pincode= cursor.getString(8);
                String  flag= cursor.getString(9);
                String  uniqe= cursor.getString(10);
                String  code= cursor.getString(11);


                // Log.d("","Col ID "+id);
                ALLCustomerModel recordingItem  = new ALLCustomerModel(customer_id,customer_name,mobile,email,address1,address2, city, state,pincode,code,flag,uniqe);
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
    public ArrayList<ALLCustomerModel> getallflagcustomers(String flags)
    {
        ArrayList<ALLCustomerModel> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();



       // Cursor cursor =  db.rawQuery( "select * from customers_table ", null );
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_FLAG + " like ?",new String[]{"%" + flags + "%"});
        if(cursor!=null)
        {
            while (cursor.moveToNext())
            {
                // arrayList.id = cursor.getInt(cursor.getColumnIndex(ItemTable.COL_ID));

                //  String id = cursor.getString(0);
                String customer_id = cursor.getString(0);
                String customer_name = cursor.getString(1);
                String address1 = cursor.getString(2);
                String address2 = cursor.getString(3);
                String city  = cursor.getString(4);
                String mobile = cursor.getString(5);
                String email = cursor.getString(6);
                String state = cursor.getString(7);
                String  pincode= cursor.getString(8);
                String  flag= cursor.getString(9);
                String  uniqe= cursor.getString(10);
                String  code= cursor.getString(11);


                // Log.d("","Col ID "+id);
                ALLCustomerModel recordingItem  = new ALLCustomerModel(customer_id,customer_name,mobile,email,address1,address2, city, state,pincode,code,flag,uniqe);
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
    public ArrayList<ALLCustomerModel> getupdatecustomer(String uqid)
    {
        ArrayList<ALLCustomerModel> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();


        Log.d("","customerIDString"+uqid);

      //  Cursor cursor =  db.rawQuery( "select * from customers_table ", null );
      //  Cursor cursor =  db.rawQuery( "select * from customers_table where uniqu ="+customerIDString+"", null );
        Cursor cursor =  db.rawQuery( "select * from customers_table where uniqu ="+uqid+"", null );

        if(cursor!=null)
        {
            while (cursor.moveToNext())
            {
                // arrayList.id = cursor.getInt(cursor.getColumnIndex(ItemTable.COL_ID));

                //  String id = cursor.getString(0);
                String customer_id = cursor.getString(0);
                String customer_name = cursor.getString(1);
                String address1 = cursor.getString(2);
                String address2 = cursor.getString(3);
                String city  = cursor.getString(4);
                String mobile = cursor.getString(5);
                String email = cursor.getString(6);
                String state = cursor.getString(7);
                String  pincode= cursor.getString(8);
                String  flags= cursor.getString(9);
                String  uniuqe= cursor.getString(10);
                String  code= cursor.getString(11);


                // Log.d("","Col ID "+id);
                ALLCustomerModel recordingItem  = new ALLCustomerModel(customer_id,customer_name,mobile,email,address1,address2, city, state,pincode,flags,code,uniuqe);
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