package com.microlan.rushhandingoffline.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.microlan.rushhandingoffline.OfflineModel.AllProductModel;

import java.util.ArrayList;

public class AllProductDBHelper extends SQLiteOpenHelper
{

    private Context context;
    public static final String DATABASE_NAME = "productdetails.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "productsdetails_table";


    public static final String COLUMN_Id = "id";
    public static final String COLUMN_CATOGERYID = "catogeryid";
    public static final String COLUMN_PRODUCTID = "productid";
    public static final String COLUMN_PRODUCTNAME = "productname";
    public static final String COLUMN_IMAGEPATH = "imagepath";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_PRICES = "prices";
    public static final String COLUMN_SIZE = "size";
    public static final String COLUMN_UNIT= "unit";
    public static final String COLUMN_DESC = "descp";
    public static final String COLUMN_TERM = "terms";
    public static final String COLUMN_HASHCODE = "hsn_code";
    public static final String COLUMN_STOCK = "stock_qty";

    private static OnDatabaseChangedListener mOnDatabaseChangedListener;


    private static final String COMA_SEP = ",";

    int value;




    private static final String SQLITE_CREATE_TABLE  = "CREATE TABLE "+TABLE_NAME + " ("+"id INTEGER PRIMARY KEY" +
            " AUTOINCREMENT"+COMA_SEP+
            COLUMN_CATOGERYID + " INTEGER" +COMA_SEP+
            COLUMN_PRODUCTID + " INTEGER" +COMA_SEP+
            COLUMN_PRODUCTNAME + " TEXT" +COMA_SEP+
            COLUMN_IMAGEPATH + " TEXT"+COMA_SEP+
            COLUMN_PRICE + " INTEGER"+COMA_SEP+
            COLUMN_PRICES + " TEXT"+COMA_SEP+
            COLUMN_SIZE + " TEXT"+COMA_SEP+
            COLUMN_UNIT + " TEXT"+COMA_SEP+
            COLUMN_TERM + " TEXT"+COMA_SEP+
            COLUMN_DESC + " TEXT"+COMA_SEP+
            COLUMN_HASHCODE + " INTEGER DEFAULT 0"+COMA_SEP+
            COLUMN_STOCK + " TEXT"+ ")";


    public AllProductDBHelper(Context context)
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

    public ArrayList<AllProductModel> deleteItem(String item) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "productid=?";
        String whereArgs[] = {item};
        db.delete(TABLE_NAME, whereClause, whereArgs);

        Log.d("","deleteItem v"+item);
        return null;
    }

    public boolean addproduct(AllProductModel recordingItem)
    {
        try
        {
            SQLiteDatabase db = getWritableDatabase();



            ContentValues contentValues = new ContentValues();
            //contentValues.put(COLUMN_Id,value);


            contentValues.put(COLUMN_CATOGERYID,recordingItem.getCatogeryid());
            contentValues.put(COLUMN_PRODUCTID,recordingItem.getProduct_id());
            contentValues.put(COLUMN_PRODUCTNAME,recordingItem.getProduct_name());
            contentValues.put(COLUMN_IMAGEPATH,recordingItem.getImage_name());
            contentValues.put(COLUMN_PRICE,recordingItem.getPrice());
            contentValues.put(COLUMN_SIZE,recordingItem.getPack_size1());
            contentValues.put(COLUMN_PRICES,recordingItem.getPrice1());
            contentValues.put(COLUMN_UNIT,recordingItem.getUnit_name());
            contentValues.put(COLUMN_TERM,recordingItem.getTerms_conditions());
            contentValues.put(COLUMN_DESC,recordingItem.getDescription());
            contentValues.put(COLUMN_HASHCODE,recordingItem.getHsn_code());
            contentValues.put(COLUMN_STOCK,recordingItem.getStock_qty());

            db.insert(TABLE_NAME,null,contentValues);
         //   $insertId = db.insert(TABLE_NAME,null,contentValues);

            if (mOnDatabaseChangedListener != null) {
                mOnDatabaseChangedListener.onNewDatabaseEntryAdded(recordingItem);
            }


            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public boolean getupdateexitproduct(AllProductModel recordingItem)
    {
        try
        {
            SQLiteDatabase db = getWritableDatabase();



            ContentValues contentValues = new ContentValues();
            //contentValues.put(COLUMN_Id,value);


            contentValues.put(COLUMN_CATOGERYID,recordingItem.getCatogeryid());
            contentValues.put(COLUMN_PRODUCTID,recordingItem.getProduct_id());
            contentValues.put(COLUMN_PRODUCTNAME,recordingItem.getProduct_name());
            contentValues.put(COLUMN_IMAGEPATH,recordingItem.getImage_name());
            contentValues.put(COLUMN_PRICE,recordingItem.getPrice());
            contentValues.put(COLUMN_SIZE,recordingItem.getPack_size1());
            contentValues.put(COLUMN_PRICES,recordingItem.getPrice1());
            contentValues.put(COLUMN_UNIT,recordingItem.getUnit_name());
            contentValues.put(COLUMN_TERM,recordingItem.getTerms_conditions());
            contentValues.put(COLUMN_DESC,recordingItem.getDescription());
            contentValues.put(COLUMN_HASHCODE,recordingItem.getHsn_code());
            contentValues.put(COLUMN_STOCK,recordingItem.getStock_qty());

            db.update(AllProductDBHelper.TABLE_NAME, contentValues, " productid = ?"+recordingItem.getProduct_id(), null);
           // db.update(TABLE_NAME, contentValues, "productid = ?", new String[]{recordingItem.getProduct_id()});

                Log.d("","contentValues update "+contentValues);
            if (mOnDatabaseChangedListener != null) {
                mOnDatabaseChangedListener.onNewDatabaseEntryAdded(recordingItem);
            }


            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }


    public ArrayList<AllProductModel> getupdateproduct(String productIDString)
    {
        ArrayList<AllProductModel> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();



        Cursor cursor =  db.rawQuery( "select * from productsdetails_table where productid ="+productIDString+"", null );
        if(cursor!=null)
        {
            while (cursor.moveToNext())
            {
               // arrayList.id = cursor.getInt(cursor.getColumnIndex(ItemTable.COL_ID));

              //  String id = cursor.getString(0);
                String catogery_id = cursor.getString(1);
                String product_id = cursor.getString(2);
                String product_name = cursor.getString(3);
                String path = cursor.getString(4);
                String price = cursor.getString(5);
                String size  = cursor.getString(6);
                String prices = cursor.getString(7);
                String unit = cursor.getString(8);
                String  term= cursor.getString(9);
                String  descp= cursor.getString(10);
                String  code= cursor.getString(11);
                String  stock= cursor.getString(12);


               // Log.d("","Col ID "+id);
                AllProductModel recordingItem  = new AllProductModel(catogery_id,product_id,product_name,path,price, size, prices, unit,term,descp,code,stock);
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
    public ArrayList<AllProductModel> getallproduct()
    {
        ArrayList<AllProductModel> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();



        Cursor cursor =  db.rawQuery( "select * from productsdetails_table ", null );
        if(cursor!=null)
        {
            while (cursor.moveToNext())
            {
               // arrayList.id = cursor.getInt(cursor.getColumnIndex(ItemTable.COL_ID));

              //  String id = cursor.getString(0);
                String catogery_id = cursor.getString(1);
                String product_id = cursor.getString(2);
                String product_name = cursor.getString(3);
                String path = cursor.getString(4);
                String price = cursor.getString(5);
                String size  = cursor.getString(6);
                String prices = cursor.getString(7);
                String unit = cursor.getString(8);
                String  term= cursor.getString(9);
                String  descp= cursor.getString(10);
                String  code= cursor.getString(11);
                String  stock= cursor.getString(12);


               // Log.d("","Col ID "+id);
                AllProductModel recordingItem  = new AllProductModel(catogery_id,product_id,product_name,path,price, size, prices, unit,term,descp,code,stock);
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
    public ArrayList<AllProductModel> getscanproduct(String stockval)
    {
        ArrayList<AllProductModel> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();



        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_STOCK + " like ?", new String[] { "%" + stockval + "%" });
        if(cursor!=null)
        {
            while (cursor.moveToNext())
            {
               // arrayList.id = cursor.getInt(cursor.getColumnIndex(ItemTable.COL_ID));

              //  String id = cursor.getString(0);
                String catogery_id = cursor.getString(1);
                String product_id = cursor.getString(2);
                String product_name = cursor.getString(3);
                String path = cursor.getString(4);
                String price = cursor.getString(5);
                String size  = cursor.getString(6);
                String prices = cursor.getString(7);
                String unit = cursor.getString(8);
                String  term= cursor.getString(9);
                String  descp= cursor.getString(10);
                String  code= cursor.getString(11);
                String  stock= cursor.getString(12);


               // Log.d("","Col ID "+id);
                AllProductModel recordingItem  = new AllProductModel(catogery_id,product_id,product_name,path,price, size, prices, unit,term,descp,code,stock);
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
    public ArrayList<AllProductModel> getsearchproduct(String name)
    {
        ArrayList<AllProductModel> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

       String searchString = "%" + name + "%";


        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_PRODUCTNAME + " like ?", new String[] { "%" + name + "%" });
        if(cursor!=null)
        {
            while (cursor.moveToNext())
            {
               // arrayList.id = cursor.getInt(cursor.getColumnIndex(ItemTable.COL_ID));

              //  String id = cursor.getString(0);
                String catogery_id = cursor.getString(1);
                String product_id = cursor.getString(2);
                String product_name = cursor.getString(3);
                String path = cursor.getString(4);
                String price = cursor.getString(5);
                String size  = cursor.getString(6);
                String prices = cursor.getString(7);
                String unit = cursor.getString(8);
                String  term= cursor.getString(9);
                String  descp= cursor.getString(10);
                String  code= cursor.getString(11);
                String  stock= cursor.getString(12);


               // Log.d("","Col ID "+id);
                AllProductModel recordingItem  = new AllProductModel(catogery_id,product_id,product_name,path,price, size, prices, unit,term,descp,code,stock);
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
    public ArrayList<AllProductModel> getcatproduct(String id)
    {
        ArrayList<AllProductModel> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor =  db.rawQuery( "select * from productsdetails_table where catogeryid ="+id+"", null );

        if(cursor!=null)
        {
            while (cursor.moveToNext())
            {
               // arrayList.id = cursor.getInt(cursor.getColumnIndex(ItemTable.COL_ID));

              //  String id = cursor.getString(0);
                String catogery_id = cursor.getString(1);
                String product_id = cursor.getString(2);
                String product_name = cursor.getString(3);
                String path = cursor.getString(4);
                String price = cursor.getString(5);
                String size  = cursor.getString(6);
                String prices = cursor.getString(7);
                String unit = cursor.getString(8);
                String  term= cursor.getString(9);
                String  descp= cursor.getString(10);
                String  code= cursor.getString(11);
                String  stock= cursor.getString(12);


               // Log.d("","Col ID "+id);
                AllProductModel recordingItem  = new AllProductModel(catogery_id,product_id,product_name,path,price, size, prices, unit,term,descp,code,stock);
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
