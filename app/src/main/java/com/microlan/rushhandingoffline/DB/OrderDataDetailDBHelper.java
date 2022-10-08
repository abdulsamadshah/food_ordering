package com.microlan.rushhandingoffline.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.microlan.rushhandingoffline.OfflineModel.OrderDataDetailsModel;

import java.util.ArrayList;

public class OrderDataDetailDBHelper extends SQLiteOpenHelper

{

    private Context context;
    public static final String DATABASE_NAME = "orderdeatlis.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "orderdatadetails_table";


    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ORDERID = "order_id";
    public static final String COLUMN_ORDER_NUMBER = "order_number";
    public static final String COLUMN_PRODUCTID = "productid";
    public static final String COLUMN_PRODUCTNAME = "productname";
    public static final String COLUMN_IMAGEPATH = "imagepath";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_QTY = "qty";
    public static final String COLUMN_TOTAL = "unit_total";
    public static final String COLUMN_SIZE = "size";
    public static final String COLUMN_PRODUCTSIZE = "product_size";
    public static final String COLUMN_UNIT= "unit";
    public static final String COLUMN_COLOR = "product_color";
    public static final String COLUMN_USERID = "user_id";
    public static final String COLUMN_FLAG = "flag";

    private static OnDatabaseChangedListener mOnDatabaseChangedListener;


    private static final String COMA_SEP = ",";

    int value;
    private static final String SQLITE_CREATE_TABLE  = "CREATE TABLE "+TABLE_NAME + " ("+COLUMN_ID+" INTEGER PRIMARY KEY" +
            " AUTOINCREMENT"+COMA_SEP+
            COLUMN_ORDERID+ " TEXT"+COMA_SEP+
            COLUMN_ORDER_NUMBER + " TEXT"+COMA_SEP+
            COLUMN_PRODUCTID+ " INTEGER"+COMA_SEP+
            COLUMN_PRODUCTNAME+ " TEXT"+COMA_SEP+
            COLUMN_IMAGEPATH+ " TEXT"+COMA_SEP+
            COLUMN_PRICE+ " INTEGER"+COMA_SEP+
            COLUMN_QTY+ " INTEGER"+COMA_SEP+
            COLUMN_TOTAL+ " TEXT"+COMA_SEP+
            COLUMN_SIZE+ " TEXT"+COMA_SEP+
            COLUMN_PRODUCTSIZE+ " TEXT"+COMA_SEP+
            COLUMN_UNIT+ " TEXT"+COMA_SEP+
            COLUMN_USERID+ " TEXT"+COMA_SEP+
            COLUMN_COLOR+ " TEXT"+COMA_SEP+
            COLUMN_FLAG+ " TEXT"+ ")";


    public OrderDataDetailDBHelper(Context context)
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
        db.execSQL(SQLITE_CREATE_TABLE);

        if (newVersion > oldVersion) {
            db.execSQL("ALTER TABLE orderdatadetails_table ADD COLUMN inquiry_id INTEGER DEFAULT 0");
        }
        else {
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);

        }
    }

    public ArrayList<OrderDataDetailsModel> deleteItem(String item) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ORDERID + "='" + item+"'", null);

        Log.d("","deleteItem v"+item);
        return null;
    }


    public boolean addorderdetails(OrderDataDetailsModel recordingItem)
    {
        try
        {
            SQLiteDatabase db = getWritableDatabase();



            ContentValues contentValues = new ContentValues();
            //contentValues.put(COLUMN_Id,value);


            // contentValues.put(COLUMN_CARTID,recordingItem.getCart_id());
            contentValues.put(COLUMN_ORDERID,recordingItem.getOrder_id());
            contentValues.put(COLUMN_ORDER_NUMBER,recordingItem.getOrder_number());
            contentValues.put(COLUMN_PRODUCTID,recordingItem.getProduct_id());
            contentValues.put(COLUMN_PRODUCTNAME,recordingItem.getProduct_name());
            contentValues.put(COLUMN_IMAGEPATH,recordingItem.getImage_name());
            contentValues.put(COLUMN_PRICE,recordingItem.getPrice());
            contentValues.put(COLUMN_SIZE,recordingItem.getPack_size());
            contentValues.put(COLUMN_UNIT,recordingItem.getPack_unit());
            contentValues.put(COLUMN_QTY,recordingItem.getQty());
            contentValues.put(COLUMN_TOTAL,recordingItem.getUnit_total());
            contentValues.put(COLUMN_PRODUCTSIZE,recordingItem.getProduct_size());
            contentValues.put(COLUMN_USERID,recordingItem.getUser_id());
            contentValues.put(COLUMN_COLOR,recordingItem.getProduct_color());
            contentValues.put(COLUMN_FLAG,recordingItem.getFlag());

            Log.d("","Order details insert"+contentValues);
            db.insert(TABLE_NAME,null,contentValues);
            //   $insertId = db.insert(TABLE_NAME,null,contentValues);

            if (mOnDatabaseChangedListener != null) {
                mOnDatabaseChangedListener.onNewDatabaseEntryOrderDetais(recordingItem);
            }


            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<OrderDataDetailsModel> getuserorderdetails(String userID)
    {
        ArrayList<OrderDataDetailsModel> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        //  Cursor cursor =  db.rawQuery( "select * from orderdatadetails_table ", null );

        //Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_USERID + " like ?" + " AND " + COLUMN_FLAG + " like ?",new String[]{"%" + userID + "%","%" + s + "%" });
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_USERID + " like ?",new String[]{"%" + userID + "%"});

        if(cursor!=null)
        {
            while (cursor.moveToNext())
            {


                String cart_id = cursor.getString(0);
                String order_id = cursor.getString(1);
                String ordere_number= cursor.getString(2);
                String product_id = cursor.getString(3);
                String product_name = cursor.getString(4);
                String path = cursor.getString(5);
                String price = cursor.getString(6);
                String qty  = cursor.getString(7);
                String total = (cursor.getString(8));
                String size = cursor.getString(9);
                String  productsize= cursor.getString(10);
                String  unit= cursor.getString(11);
                String  userid= cursor.getString(12);
                String  color= cursor.getString(13);
                String  flag= cursor.getString(14);

                Log.d("","product_name "+product_name);
                Log.d("","order_id "+order_id);

                // Log.d("","Col ID "+id);
                OrderDataDetailsModel recordingItem  = new OrderDataDetailsModel(cart_id,product_id,product_name,path,price, qty,size, productsize, unit,color, total,userid,order_id,ordere_number,flag);

                Log.d("","recordingItem"+recordingItem);
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

    public boolean updateorderdataflag(OrderDataDetailsModel recordingItem)
    {
        try
        {
            SQLiteDatabase db = getWritableDatabase();



            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_FLAG,recordingItem.getFlag());

            Log.d("","contentValuesOrder Deatils..... "+contentValues);
            db.update(TABLE_NAME, contentValues, " flag = "+"0", null);
            String orderNo=recordingItem.getOrder_number();
            if (mOnDatabaseChangedListener != null) {
                mOnDatabaseChangedListener.onNewDatabaseEntryOrderDetais(recordingItem);
            }


            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateexitorderdetails(OrderDataDetailsModel recordingItem)
    {
        try
        {
            SQLiteDatabase db = getWritableDatabase();



            ContentValues contentValues = new ContentValues();
            //contentValues.put(COLUMN_Id,value);


            // contentValues.put(COLUMN_CARTID,recordingItem.getCart_id());
            contentValues.put(COLUMN_ORDERID,recordingItem.getOrder_id());
            contentValues.put(COLUMN_ORDER_NUMBER,recordingItem.getOrder_number());
            contentValues.put(COLUMN_PRODUCTID,recordingItem.getProduct_id());
            contentValues.put(COLUMN_PRODUCTNAME,recordingItem.getProduct_name());
            contentValues.put(COLUMN_IMAGEPATH,recordingItem.getImage_name());
            contentValues.put(COLUMN_PRICE,recordingItem.getPrice());
            contentValues.put(COLUMN_SIZE,recordingItem.getPack_size());
            contentValues.put(COLUMN_UNIT,recordingItem.getPack_unit());
            contentValues.put(COLUMN_QTY,recordingItem.getQty());
            contentValues.put(COLUMN_TOTAL,recordingItem.getUnit_total());
            contentValues.put(COLUMN_PRODUCTSIZE,recordingItem.getProduct_size());
            contentValues.put(COLUMN_USERID,recordingItem.getUser_id());
            contentValues.put(COLUMN_COLOR,recordingItem.getProduct_color());
            contentValues.put(COLUMN_FLAG,recordingItem.getFlag());

            Log.d("","Update order exti data  "+contentValues);
            Log.d("","Update order exti data  "+recordingItem.getFlag());

            db.update(OrderDataDetailDBHelper.TABLE_NAME, contentValues, COLUMN_FLAG+" = "+"0",null);

            if (mOnDatabaseChangedListener != null) {
                mOnDatabaseChangedListener.onNewDatabaseEntryOrderDetais(recordingItem);
            }


            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }


    public ArrayList<OrderDataDetailsModel> getorderdetails(String ordernum)
    {
        ArrayList<OrderDataDetailsModel> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
            String ab=ordernum;
        Log.d("","ordernum"+ordernum);
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_ORDER_NUMBER + " like ?", new String[] {  ordernum  });
        if(cursor!=null)
        {
            while (cursor.moveToNext())
            {


                String cart_id = cursor.getString(0);
                String order_id = cursor.getString(1);
                String ordere_number= cursor.getString(2);
                String product_id = cursor.getString(3);
                String product_name = cursor.getString(4);
                String path = cursor.getString(5);
                String price = cursor.getString(6);
                String qty  = cursor.getString(7);
                String total = (cursor.getString(8));
                String size = cursor.getString(9);
                String  productsize= cursor.getString(10);
                String  unit= cursor.getString(11);
                String  userid= cursor.getString(12);
                String  color= cursor.getString(13);
                String  flag= cursor.getString(14);

                Log.d("","product_name "+product_name);
                Log.d("","order_id "+order_id);

                // Log.d("","Col ID "+id);
                OrderDataDetailsModel recordingItem  = new OrderDataDetailsModel(cart_id,product_id,product_name,path,price, qty,size, productsize, unit,color, total,userid,order_id,ordere_number,flag);

                Log.d("","recordingItem"+recordingItem);
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
    public ArrayList<OrderDataDetailsModel> updategetorderdetails(String ordernum)
    {
        ArrayList<OrderDataDetailsModel> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
            String ab=ordernum;
        Log.d("","ordernum"+ordernum);
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_ORDER_NUMBER + " like ?", new String[] {  ordernum  });
     //   Cursor cursor =  db.rawQuery( "select * from orderdata_table where order_number ="+ordernum+"", null );

        if(cursor!=null)
        {
            while (cursor.moveToNext())
            {


                String cart_id = cursor.getString(0);
                String order_id = cursor.getString(1);
                String ordere_number= cursor.getString(2);
                String product_id = cursor.getString(3);
                String product_name = cursor.getString(4);
                String path = cursor.getString(5);
                String price = cursor.getString(6);
                String qty  = cursor.getString(7);
                String total = (cursor.getString(8));
                String size = cursor.getString(9);
                String  productsize= cursor.getString(10);
                String  unit= cursor.getString(11);
                String  userid= cursor.getString(12);
                String  color= cursor.getString(13);
                String  flag= cursor.getString(14);

                Log.d("","product_name "+product_name);
                Log.d("","order_id "+order_id);

                // Log.d("","Col ID "+id);
                OrderDataDetailsModel recordingItem  = new OrderDataDetailsModel(cart_id,product_id,product_name,path,price, qty,size, productsize, unit,color, total,userid,order_id,ordere_number,flag);

                Log.d("","recordingItem"+recordingItem);
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
    public ArrayList<OrderDataDetailsModel> getallorderdetails(String userID, String flags)
    {
        ArrayList<OrderDataDetailsModel> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        //  Cursor cursor =  db.rawQuery( "select * from orderdatadetails_table ", null );

        //Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_USERID + " like ?" + " AND " + COLUMN_FLAG + " like ?",new String[]{"%" + userID + "%","%" + s + "%" });
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_FLAG + " like ?",new String[]{"%" + flags + "%"});

        if(cursor!=null)
        {
            while (cursor.moveToNext())
            {


                String cart_id = cursor.getString(0);
                String order_id = cursor.getString(1);
                String ordere_number= cursor.getString(2);
                String product_id = cursor.getString(3);
                String product_name = cursor.getString(4);
                String path = cursor.getString(5);
                String price = cursor.getString(6);
                String qty  = cursor.getString(7);
                String total = (cursor.getString(8));
                String size = cursor.getString(9);
                String  productsize= cursor.getString(10);
                String  unit= cursor.getString(11);
                String  userid= cursor.getString(12);
                String  color= cursor.getString(13);
                String  flag= cursor.getString(14);

                Log.d("","product_name "+product_name);
                Log.d("","order_id "+order_id);

                // Log.d("","Col ID "+id);
                OrderDataDetailsModel recordingItem  = new OrderDataDetailsModel(cart_id,product_id,product_name,path,price, qty,size, productsize, unit,color, total,userid,order_id,ordere_number,flag);

                Log.d("","recordingItem"+recordingItem);
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