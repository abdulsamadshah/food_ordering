package com.microlan.rushhandingoffline.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.microlan.rushhandingoffline.OfflineModel.OrderDataModel;

import java.util.ArrayList;

import static com.microlan.rushhandingoffline.DB.OrderDataDetailDBHelper.COLUMN_ORDER_NUMBER;

public class OrderDataDBHelper extends SQLiteOpenHelper
{

    private Context context;
    public static final String DATABASE_NAME = "orderdata.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "orderdata_table";


    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ORDER_NUM = "order_number";
    public static final String COLUMN_USERID = "userid";
    public static final String COLUMN_PAYMENTMODE = "payment_method";
    public static final String COLUMN_DELIVERYADDRESS = "deliver_address";
    public static final String COLUMN_TOTAL = "total_amount";
    public static final String COLUMN_SUBTOTAL = "sub_total_amount";
    public static final String COLUMN_CUSTNAME = "customer_name";
    public static final String COLUMN_CUSTID = "customer_id";
    public static final String COLUMN_WALLET= "wallet_amount";
    public static final String COLUMN_DISCOUNT = "discount";
    public static final String COLUMN_DISCOUNTTYPE= "discount_type";
    public static final String COLUMN_CASH= "cash_amount";
    public static final String COLUMN_SOURCE= "order_source";
    public static final String COLUMN_DATETIME= "order_date";
    public static final String COLUMN_FLAG= "order_flag";
    public static final String COLUMN_IGST= "igst";
    public static final String COLUMN_CGST= "cgst";
    public static final String COLUMN_SGST= "sgst";

    private static OnDatabaseChangedListener mOnDatabaseChangedListener;


    private static final String COMA_SEP = ",";

    int value;


    private static final String SQLITE_CREATE_TABLE  = "CREATE TABLE "+TABLE_NAME + " ("+COLUMN_ID+" INTEGER PRIMARY KEY" +
            " AUTOINCREMENT"+COMA_SEP+
            COLUMN_ORDER_NUM + " TEXT"+COMA_SEP+
            COLUMN_USERID + " INTEGER"+COMA_SEP+
            COLUMN_PAYMENTMODE + " TEXT"+COMA_SEP+
            COLUMN_DELIVERYADDRESS + " TEXT"+COMA_SEP+
            COLUMN_TOTAL + " TEXT"+COMA_SEP+
            COLUMN_SUBTOTAL + " TEXT"+COMA_SEP+
            COLUMN_CUSTNAME + " TEXT"+COMA_SEP+
            COLUMN_CUSTID + " INTEGER"+COMA_SEP+
            COLUMN_WALLET + " TEXT"+COMA_SEP+
            COLUMN_DISCOUNT + " TEXT"+COMA_SEP+
            COLUMN_DISCOUNTTYPE + " TEXT"+COMA_SEP+
            COLUMN_CASH + " TEXT"+COMA_SEP+
            COLUMN_SOURCE + " TEXT"+COMA_SEP+
            COLUMN_DATETIME + " TEXT"+COMA_SEP+
            COLUMN_FLAG + " TEXT"+COMA_SEP+
            COLUMN_IGST + " TEXT"+COMA_SEP+
            COLUMN_CGST + " TEXT"+COMA_SEP+
            COLUMN_SGST + " TEXT"+")";




    public OrderDataDBHelper(Context context)
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
            db.execSQL("ALTER TABLE orderdata_table ADD COLUMN inquiry_id INTEGER DEFAULT 0");
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

    public boolean addorderdata(OrderDataModel recordingItem)
    {
        try
        {
            SQLiteDatabase db = getWritableDatabase();



            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_ORDER_NUM,recordingItem.getOrder_number());
            contentValues.put(COLUMN_USERID,recordingItem.getUser_id());
            contentValues.put(COLUMN_PAYMENTMODE,recordingItem.getPayment_method());
            contentValues.put(COLUMN_DELIVERYADDRESS,recordingItem.getAddress_id());
            contentValues.put(COLUMN_TOTAL,recordingItem.getTotal_amount());
            contentValues.put(COLUMN_SUBTOTAL,recordingItem.getBalance());
            contentValues.put(COLUMN_CUSTNAME,recordingItem.getCc_user_name());
            contentValues.put(COLUMN_CUSTID,recordingItem.getCust_id());
            contentValues.put(COLUMN_WALLET,recordingItem.getWallet_amount());
            contentValues.put(COLUMN_DISCOUNT,recordingItem.getDiscount());
            contentValues.put(COLUMN_DISCOUNTTYPE,recordingItem.getDiscount_type());
            contentValues.put(COLUMN_CASH,recordingItem.getCash_amount());
            contentValues.put(COLUMN_SOURCE,recordingItem.getOrder_source());
            contentValues.put(COLUMN_DATETIME,recordingItem.getOrder_date_time());
            contentValues.put(COLUMN_FLAG,recordingItem.getFlag());
            contentValues.put(COLUMN_IGST,recordingItem.getIgst());
            contentValues.put(COLUMN_CGST,recordingItem.getCgst());
            contentValues.put(COLUMN_SGST,recordingItem.getSgst());

            Log.d("","contentValues..... "+contentValues);
            db.insert(TABLE_NAME,null,contentValues);

            if (mOnDatabaseChangedListener != null) {
                mOnDatabaseChangedListener.onNewDatabaseEntryOrderDeta(recordingItem);
            }


            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateorderdata(OrderDataModel recordingItem)
    {
        try
        {
            SQLiteDatabase db = getWritableDatabase();



            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_ORDER_NUM,recordingItem.getOrder_number());
            contentValues.put(COLUMN_USERID,recordingItem.getUser_id());
            contentValues.put(COLUMN_PAYMENTMODE,recordingItem.getPayment_method());
            contentValues.put(COLUMN_DELIVERYADDRESS,recordingItem.getAddress_id());
            contentValues.put(COLUMN_TOTAL,recordingItem.getTotal_amount());
            contentValues.put(COLUMN_SUBTOTAL,recordingItem.getBalance());
            contentValues.put(COLUMN_CUSTNAME,recordingItem.getCc_user_name());
            contentValues.put(COLUMN_CUSTID,recordingItem.getCust_id());
            contentValues.put(COLUMN_WALLET,recordingItem.getWallet_amount());
            contentValues.put(COLUMN_DISCOUNT,recordingItem.getDiscount());
            contentValues.put(COLUMN_DISCOUNTTYPE,recordingItem.getDiscount_type());
            contentValues.put(COLUMN_CASH,recordingItem.getCash_amount());
            contentValues.put(COLUMN_SOURCE,recordingItem.getOrder_source());
            contentValues.put(COLUMN_DATETIME,recordingItem.getOrder_date_time());
            contentValues.put(COLUMN_FLAG,recordingItem.getFlag());
            contentValues.put(COLUMN_IGST,recordingItem.getIgst());
            contentValues.put(COLUMN_CGST,recordingItem.getCgst());
            contentValues.put(COLUMN_SGST,recordingItem.getSgst());

            Log.d("","DetailsOrder update  "+contentValues);

            db.update(OrderDataDBHelper.TABLE_NAME, contentValues, COLUMN_FLAG+" = 0",null);

            if (mOnDatabaseChangedListener != null) {
                mOnDatabaseChangedListener.onNewDatabaseEntryOrderDeta(recordingItem);
            }


            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateorderdataflag(OrderDataModel recordingItem)
    {
        try
        {
            SQLiteDatabase db = getWritableDatabase();



            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_FLAG,recordingItem.getFlag());

            Log.d("","update..... "+contentValues);
            db.update(TABLE_NAME, contentValues, " order_flag = "+"0", null);


            if (mOnDatabaseChangedListener != null) {
                mOnDatabaseChangedListener.onNewDatabaseEntryOrderDeta(recordingItem);
            }
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public ArrayList<OrderDataModel> getdateorder(String dates)
    {
        ArrayList<OrderDataModel> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        //Log.d("",""+);
        //  Cursor cursor =  db.rawQuery( "select * from orderdata_table where userid ="+userID+""+"AND"+"order_flag = "+s, null );
        //  Cursor cursor =  db.rawQuery( "select * from Address_table where order_flag ="+s+"", null );

        Log.d("","dates"+dates);
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_DATETIME + " like ?",new String[]{"%" + dates + "%" });

        if(cursor!=null)
        {
            while (cursor.moveToNext())
            {
                String orderid = cursor.getString(0);
                String orderno = cursor.getString(1);
                String userid = cursor.getString(2);
                String payment = cursor.getString(3);
                String address = cursor.getString(4);
                String total  = cursor.getString(5);
                String subtotal  = cursor.getString(6);
                String custname = cursor.getString(7);
                String custid = cursor.getString(8);
                String  wallet= cursor.getString(9);
                String  discount= cursor.getString(10);
                String  discounttype= cursor.getString(11);
                String  cash= cursor.getString(12);
                String  source= cursor.getString(13);
                String  date= cursor.getString(14);
                String  flag= cursor.getString(15);
                String  igst= cursor.getString(16);
                String  cgst= cursor.getString(17);
                String  sgst= cursor.getString(18);


                // Log.d("","Col ID "+id);
                OrderDataModel recordingItem  = new OrderDataModel(orderid,userid,orderno,total,payment,address, source, custname, date,wallet,discount,discounttype,cash,custid,subtotal,flag,igst,cgst,sgst);
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


    public ArrayList<OrderDataModel> getorderdata()
    {
        ArrayList<OrderDataModel> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME,null);

        if(cursor!=null)
        {
            while (cursor.moveToNext())
            {
                 String orderid = cursor.getString(0);
                String orderno = cursor.getString(1);
                String userid = cursor.getString(2);
                String payment = cursor.getString(3);
                String address = cursor.getString(4);
                String total  = cursor.getString(5);
                String subtotal  = cursor.getString(6);
                String custname = cursor.getString(7);
                String custid = cursor.getString(8);
                String  wallet= cursor.getString(9);
                String  discount= cursor.getString(10);
                String  discounttype= cursor.getString(11);
                String  cash= cursor.getString(12);
                String  source= cursor.getString(13);
                String  date= cursor.getString(14);
                String  flag= cursor.getString(15);
                String  igst= cursor.getString(16);
                String  cgst= cursor.getString(17);
                String  sgst= cursor.getString(18);


                // Log.d("","Col ID "+id);
                OrderDataModel recordingItem  = new OrderDataModel(orderid,userid,orderno,total,payment,address, source, custname, date,wallet,discount,discounttype,cash,custid,subtotal,flag,igst,cgst,sgst);
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
    public ArrayList<OrderDataModel> getorderdataflag(String userID, String flags)
    {
        ArrayList<OrderDataModel> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();


        Log.d("","flags"+flags);
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_FLAG + " like ?",new String[]{"%" + flags + "%"});

        if(cursor!=null)
        {
            while (cursor.moveToNext())
            {
                 String orderid = cursor.getString(0);
                String orderno = cursor.getString(1);
                String userid = cursor.getString(2);
                String payment = cursor.getString(3);
                String address = cursor.getString(4);
                String total  = cursor.getString(5);
                String subtotal  = cursor.getString(6);
                String custname = cursor.getString(7);
                String custid = cursor.getString(8);
                String  wallet= cursor.getString(9);
                String  discount= cursor.getString(10);
                String  discounttype= cursor.getString(11);
                String  cash= cursor.getString(12);
                String  source= cursor.getString(13);
                String  date= cursor.getString(14);
                String  flag= cursor.getString(15);
                String  igst= cursor.getString(16);
                String  cgst= cursor.getString(17);
                String  sgst= cursor.getString(18);


                // Log.d("","Col ID "+id);
                OrderDataModel recordingItem  = new OrderDataModel(orderid,userid,orderno,total,payment,address, source, custname, date,wallet,discount,discounttype,cash,custid,subtotal,flag,igst,cgst,sgst);
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
    public ArrayList<OrderDataModel> getallupdateorderdata(String ordernum)
    {
        ArrayList<OrderDataModel> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        //Log.d("",""+);
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_ORDER_NUMBER + " like ?", new String[] {  ordernum  });


        if(cursor!=null)
        {
            while (cursor.moveToNext())
            {

                 String orderid = cursor.getString(0);
                String orderno = cursor.getString(1);
                String userid = cursor.getString(2);
                String payment = cursor.getString(3);
                String address = cursor.getString(4);
                String total  = cursor.getString(5);
                String subtotal  = cursor.getString(6);
                String custname = cursor.getString(7);
                String custid = cursor.getString(8);
                String  wallet= cursor.getString(9);
                String  discount= cursor.getString(10);
                String  discounttype= cursor.getString(11);
                String  cash= cursor.getString(12);
                String  source= cursor.getString(13);
                String  date= cursor.getString(14);
                String  flag= cursor.getString(15);
                String  igst= cursor.getString(16);
                String  cgst= cursor.getString(17);
                String  sgst= cursor.getString(18);


                // Log.d("","Col ID "+id);
                OrderDataModel recordingItem  = new OrderDataModel(orderid,userid,orderno,total,payment,address, source, custname, date,wallet,discount,discounttype,cash,custid,subtotal,flag,igst,cgst,sgst);
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