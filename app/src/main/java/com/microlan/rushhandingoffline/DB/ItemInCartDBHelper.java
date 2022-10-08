package com.microlan.rushhandingoffline.DB;

import static org.apache.poi.ss.formula.functions.BooleanFunction.AND;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.microlan.rushhandingoffline.OfflineModel.ItemInCardModel;

import java.util.ArrayList;

public class ItemInCartDBHelper extends SQLiteOpenHelper
{

    private Context context;
    public static final String DATABASE_NAME = "temincart.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "itemincart_table";


    public static final String COLUMN_Id = "id";
    public static final String COLUMN_CARTID = "cart_id";
    public static final String COLUMN_PRODUCTID = "productid";
    public static final String COLUMN_PRODUCTNAME = "productname";
    public static final String COLUMN_IMAGEPATH = "imagepath";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_QTY = "qty";
    public static final String COLUMN_TOTAL = "unit_total";
    public static final String COLUMN_SIZE = "size";
    public static final String COLUMN_PRICES = "prices";
    public static final String COLUMN_PRODUCTSIZE = "product_size";
    public static final String COLUMN_UNIT= "unit";
    public static final String COLUMN_COLOR = "product_color";
    public static final String COLUMN_USERID = "user_id";
    public static final String COLUMN_CODE = "code";

    private static OnDatabaseChangedListener mOnDatabaseChangedListener;


    private static final String COMA_SEP = ",";

    int value;
    private static final String SQLITE_CREATE_TABLE  = "CREATE TABLE "+TABLE_NAME + " ("+"id INTEGER PRIMARY KEY" +
            " AUTOINCREMENT"+COMA_SEP+
            COLUMN_PRODUCTID + " INTEGER" +COMA_SEP+
            COLUMN_PRODUCTNAME + " TEXT" +COMA_SEP+
            COLUMN_IMAGEPATH + " TEXT"+COMA_SEP+
            COLUMN_PRICE + " INTEGER"+COMA_SEP+
            COLUMN_QTY + " INTEGER"+COMA_SEP+
            COLUMN_TOTAL + " TEXT"+COMA_SEP+
            COLUMN_SIZE + " TEXT"+COMA_SEP+
            COLUMN_PRICES + " TEXT"+COMA_SEP+
            COLUMN_PRODUCTSIZE + " TEXT"+COMA_SEP+
            COLUMN_UNIT + " TEXT"+COMA_SEP+
            COLUMN_USERID + " TEXT"+COMA_SEP+
            COLUMN_COLOR + " TEXT"+COMA_SEP+
            COLUMN_CODE + " TEXT"+ ")";


    public ItemInCartDBHelper(Context context)
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
            db.execSQL("ALTER TABLE itemincart_table ADD COLUMN inquiry_id INTEGER DEFAULT 0");
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
    public void deleteItemtable(String item) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "user_id=?";
        String whereArgs[] = {item};
        db.delete(TABLE_NAME, whereClause, whereArgs);

        Log.d("","deleteItem v"+item);
    }


    public boolean updateItemIncart(ItemInCardModel recordingItem)
    {
        try
        {
            SQLiteDatabase db = getWritableDatabase();



            ContentValues contentValues = new ContentValues();
            //contentValues.put(COLUMN_Id,value);


            contentValues.put(COLUMN_QTY,recordingItem.getQty());
            contentValues.put(COLUMN_TOTAL,recordingItem.getUnit_total());

            db.update(TABLE_NAME, contentValues, "id="+recordingItem.getCart_id(), null);


            Log.d("","update contentValues"+contentValues);
          //  db.insert(TABLE_NAME,null,contentValues);
            //   $insertId = db.insert(TABLE_NAME,null,contentValues);

            if (mOnDatabaseChangedListener != null) {
                mOnDatabaseChangedListener.onNewDatabaseEntryAddedCart(recordingItem);
            }


            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatesameItemIncart(ItemInCardModel recordingItem)
    {
        try
        {
            SQLiteDatabase db = getWritableDatabase();



            ContentValues contentValues = new ContentValues();
            //contentValues.put(COLUMN_Id,value);


            // contentValues.put(COLUMN_CARTID,recordingItem.getCart_id());
            contentValues.put(COLUMN_PRODUCTID,recordingItem.getProduct_id());
            contentValues.put(COLUMN_PRODUCTNAME,recordingItem.getProduct_name());
            contentValues.put(COLUMN_IMAGEPATH,recordingItem.getImage_name());
            contentValues.put(COLUMN_PRICE,recordingItem.getPrice());
            contentValues.put(COLUMN_SIZE,recordingItem.getPack_size());
            contentValues.put(COLUMN_PRICES,recordingItem.getPrices());
            contentValues.put(COLUMN_UNIT,recordingItem.getPack_unit());
            contentValues.put(COLUMN_QTY,recordingItem.getQty());
            contentValues.put(COLUMN_TOTAL,recordingItem.getUnit_total());
            contentValues.put(COLUMN_PRODUCTSIZE,recordingItem.getProduct_size());
            contentValues.put(COLUMN_USERID,recordingItem.getUser_id());
            contentValues.put(COLUMN_COLOR,recordingItem.getProduct_color());
            contentValues.put(COLUMN_CODE,recordingItem.getCode());

            Log.d("","contentValues update "+contentValues);
            db.update(ItemInCartDBHelper.TABLE_NAME, contentValues, COLUMN_PRODUCTID+" = "+recordingItem.getProduct_id(), null);

            if (mOnDatabaseChangedListener != null) {
                mOnDatabaseChangedListener.onNewDatabaseEntryAddedCart(recordingItem);
            }


            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addItemIncart(ItemInCardModel recordingItem)
    {
        try
        {
            SQLiteDatabase db = getWritableDatabase();



            ContentValues contentValues = new ContentValues();
            //contentValues.put(COLUMN_Id,value);


           // contentValues.put(COLUMN_CARTID,recordingItem.getCart_id());
            contentValues.put(COLUMN_PRODUCTID,recordingItem.getProduct_id());
            contentValues.put(COLUMN_PRODUCTNAME,recordingItem.getProduct_name());
            contentValues.put(COLUMN_IMAGEPATH,recordingItem.getImage_name());
            contentValues.put(COLUMN_PRICE,recordingItem.getPrice());
            contentValues.put(COLUMN_SIZE,recordingItem.getPack_size());
            contentValues.put(COLUMN_PRICES,recordingItem.getPrices());
            contentValues.put(COLUMN_UNIT,recordingItem.getPack_unit());
            contentValues.put(COLUMN_QTY,recordingItem.getQty());
            contentValues.put(COLUMN_TOTAL,recordingItem.getUnit_total());
            contentValues.put(COLUMN_PRODUCTSIZE,recordingItem.getProduct_size());
            contentValues.put(COLUMN_USERID,recordingItem.getUser_id());
            contentValues.put(COLUMN_COLOR,recordingItem.getProduct_color());
            contentValues.put(COLUMN_CODE,recordingItem.getCode());

            Log.d("","contentValues"+contentValues);
            db.insert(TABLE_NAME,null,contentValues);
            //   $insertId = db.insert(TABLE_NAME,null,contentValues);

            if (mOnDatabaseChangedListener != null) {
                mOnDatabaseChangedListener.onNewDatabaseEntryAddedCart(recordingItem);
            }


            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }


    public ArrayList<ItemInCardModel> getaddedproduct(String userID)
    {
        ArrayList<ItemInCardModel> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

            Log.d("","userID"+userID);
        Cursor cursor =  db.rawQuery( "select * from itemincart_table where user_id ="+userID+"", null );

       // Cursor cursor =  db.rawQuery( "select * from productsdetails_table ", null );
        if(cursor!=null)
        {
            while (cursor.moveToNext())
            {
                // arrayList.id = cursor.getInt(cursor.getColumnIndex(ItemTable.COL_ID));

                //  String id = cursor.getString(0);
                String cart_id = cursor.getString(0);
                String product_id = cursor.getString(1);
                String product_name = cursor.getString(2);
                String path = cursor.getString(3);
                String price = cursor.getString(4);
                int qty  = Integer.parseInt(cursor.getString(5));
                Float total = Float.parseFloat(cursor.getString(6));
                String size = cursor.getString(7);
                String  prices= cursor.getString(8);
                String  productsize= cursor.getString(9);
                String  unit= cursor.getString(10);
                String  userid= cursor.getString(11);
                String  color= cursor.getString(12);
                String  code= cursor.getString(13);

                Log.d("","product_name "+product_name);

                // Log.d("","Col ID "+id);
                ItemInCardModel recordingItem  = new ItemInCardModel(cart_id,product_id,product_name,path,price, qty,total,size, prices, productsize, unit,color, userid,code);

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
    public ArrayList<ItemInCardModel> extesprodutcs(String products, String PAcksize)
    {
        ArrayList<ItemInCardModel> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Log.d("","products"+products);
        Log.d("","PAcksize"+PAcksize);
      //  Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " +COLUMN_PRODUCTID + AND +COLUMN_PRODUCTNAME + " like ?", new String[] { products+"&"+"%" + PAcksize + "%" });

       // Cursor cursor =  db.rawQuery( "select * from itemincart_table where productid ="+products+""+" AND " +"size ="+PAcksize+"" , null );
        Cursor cursor =  db.rawQuery( "select * from itemincart_table where productid ="+products+""+" AND " +COLUMN_PRODUCTNAME + " like ?", new String[] { "%" + PAcksize + "%" } );
        if(cursor!=null)
        {
            while (cursor.moveToNext())
            {

                String cart_id = cursor.getString(0);
                String product_id = cursor.getString(1);
                String product_name = cursor.getString(2);
                String path = cursor.getString(3);
                String price = cursor.getString(4);
                int qty  = Integer.parseInt(cursor.getString(5));
                Float total = Float.parseFloat(cursor.getString(6));
                String size = cursor.getString(7);
                String  prices= cursor.getString(8);
                String  productsize= cursor.getString(9);
                String  unit= cursor.getString(10);
                String  userid= cursor.getString(11);
                String  color= cursor.getString(12);
                String  code= cursor.getString(13);

                Log.d("","product_name "+product_name);

                // Log.d("","Col ID "+id);
                ItemInCardModel recordingItem  = new ItemInCardModel(cart_id,product_id,product_name,path,price, qty,total,size, prices, productsize, unit,color, userid,code);

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