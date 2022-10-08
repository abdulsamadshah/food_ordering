package com.microlan.rushhandingoffline.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.microlan.rushhandingoffline.OfflineModel.ALLCustomerModel;
import com.microlan.rushhandingoffline.OfflineModel.AllProductModel;
import com.microlan.rushhandingoffline.OfflineModel.USerListLoginModel;

import java.util.ArrayList;

public class AllUserDBHelper extends SQLiteOpenHelper
{

    private Context context;
    public static final String DATABASE_NAME = "userlist.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "userlistq_table";


    public static final String COLUMN_OPUSERID = "op_user_id";
    public static final String COLUMN_USERID = "user_id";
    public static final String COLUMN_FULLNAME = "full_name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_MOBILE = "mobile";
    public static final String COLUMN_ROLEID = "role_id";
    public static final String COLUMN_MAPWITH = "map_with";
    public static final String COLUMN_MAPID = "map_with_id";
    public static final String COLUMN_PROFILE = "profile_pic";
    public static final String COLUMN_AADHAR = "aadhar_no";
    public static final String COLUMN_PANNO = "pan_no";
    public static final String COLUMN_PHONENO = "phone_no";
    public static final String COLUMN_ADDRESS = "address";

    private static OnDatabaseChangedListener mOnDatabaseChangedListener;


    private static final String COMA_SEP = ",";
    private static final String SQLITE_CREATE_TABLE  = "CREATE TABLE "+TABLE_NAME + " ("+"id INTEGER PRIMARY KEY" +
            " AUTOINCREMENT"+COMA_SEP+
            COLUMN_OPUSERID + " TEXT" +COMA_SEP+
            COLUMN_USERID + " TEXT" +COMA_SEP+
            COLUMN_FULLNAME + " VARCHAR" +COMA_SEP+
            COLUMN_EMAIL + " VARCHAR "+COMA_SEP+
            COLUMN_PASSWORD + " TEXT"+COMA_SEP+
            COLUMN_MOBILE + " TEXT"+COMA_SEP+
            COLUMN_ROLEID + " TEXT"+COMA_SEP+
            COLUMN_MAPWITH + " TEXT"+COMA_SEP+
            COLUMN_MAPID + " TEXT"+COMA_SEP+
            COLUMN_PROFILE + " TEXT"+COMA_SEP+
            COLUMN_AADHAR + " TEXT"+COMA_SEP+
            COLUMN_PANNO + " TEXT"+COMA_SEP+
            COLUMN_PHONENO + " TEXT"+COMA_SEP+
            COLUMN_ADDRESS + " INTEGER"+ ")";


    public AllUserDBHelper(Context context)
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
            db.execSQL("ALTER TABLE userlistq_table ADD COLUMN inquiry_id INTEGER DEFAULT 0");
        }
        else {
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);

        }
    }


    public boolean adduser(USerListLoginModel recordingItem)
    {
        try
        {
            SQLiteDatabase db = getWritableDatabase();



            ContentValues contentValues = new ContentValues();
            //contentValues.put(COLUMN_Id,value);
            contentValues.put(COLUMN_OPUSERID,recordingItem.getOp_user_id());
            contentValues.put(COLUMN_USERID,recordingItem.getUser_id());
            contentValues.put(COLUMN_FULLNAME,recordingItem.getFull_name());
            contentValues.put(COLUMN_MOBILE,recordingItem.getMobile_number());
            contentValues.put(COLUMN_EMAIL,recordingItem.getEmail_address());
            contentValues.put(COLUMN_PASSWORD,recordingItem.getPassword());
            contentValues.put(COLUMN_ROLEID,recordingItem.getRole_id());
            contentValues.put(COLUMN_MAPWITH,recordingItem.getMap_with());
            contentValues.put(COLUMN_MAPID,recordingItem.getMap_with_id());
            contentValues.put(COLUMN_PROFILE,recordingItem.getProfile_pic());
            contentValues.put(COLUMN_AADHAR,recordingItem.getAadhar_no());
            contentValues.put(COLUMN_PANNO,recordingItem.getPan_no());
            contentValues.put(COLUMN_PHONENO,recordingItem.getPhone_no());
            contentValues.put(COLUMN_ADDRESS,recordingItem.getAddress());

            Log.d("","User  add"+contentValues);
            db.insert(TABLE_NAME,null,contentValues);
            //   $insertId = db.insert(TABLE_NAME,null,contentValues);

            if (mOnDatabaseChangedListener != null) {
                mOnDatabaseChangedListener.onNewDatabaseEntryallUserlist(recordingItem);
            }


            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<USerListLoginModel> deleteItem(String item) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_OPUSERID + "='" + item+"'", null);

        Log.d("","deleteItem v"+item);
        return null;
    }

    public boolean updateuser(USerListLoginModel recordingItem)
    {
        try
        {
            SQLiteDatabase db = getWritableDatabase();



            ContentValues contentValues = new ContentValues();
            //contentValues.put(COLUMN_Id,value);

            contentValues.put(COLUMN_OPUSERID,recordingItem.getOp_user_id());
            contentValues.put(COLUMN_USERID,recordingItem.getUser_id());
            contentValues.put(COLUMN_FULLNAME,recordingItem.getFull_name());
            contentValues.put(COLUMN_MOBILE,recordingItem.getMobile_number());
            contentValues.put(COLUMN_EMAIL,recordingItem.getEmail_address());
            contentValues.put(COLUMN_PASSWORD,recordingItem.getPassword());
            contentValues.put(COLUMN_ROLEID,recordingItem.getRole_id());
            contentValues.put(COLUMN_MAPWITH,recordingItem.getMap_with());
            contentValues.put(COLUMN_MAPID,recordingItem.getMap_with_id());
            contentValues.put(COLUMN_PROFILE,recordingItem.getProfile_pic());
            contentValues.put(COLUMN_AADHAR,recordingItem.getAadhar_no());
            contentValues.put(COLUMN_PANNO,recordingItem.getPan_no());
            contentValues.put(COLUMN_PHONENO,recordingItem.getPhone_no());
            contentValues.put(COLUMN_ADDRESS,recordingItem.getAddress());

            Log.d("","customer add"+contentValues);
            db.update(TABLE_NAME, contentValues, " op_user_id = ?"+recordingItem.getUser_id(), null);
            //   $insertId = db.insert(TABLE_NAME,null,contentValues);

            if (mOnDatabaseChangedListener != null) {
                mOnDatabaseChangedListener.onNewDatabaseEntryallUserlist(recordingItem);
            }


            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<USerListLoginModel> getloginuser(String username, String password)
    {
        ArrayList<USerListLoginModel> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Log.d("","username"+username);
        Log.d("","passwords "+password);

       //    Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_EMAIL + " like ?", new String[] { "%" + username + "%" });
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_EMAIL + " like ?" + " AND " + COLUMN_PASSWORD + " like ?",new String[]{"%" + username + "%","%" + password + "%" });

        if(cursor!=null)
        {
            while (cursor.moveToNext())
            {
                String op_id = cursor.getString(1);
                String id = cursor.getString(2);
                Log.d("","User id"+id);
                String full_name = cursor.getString(3);
                String email = cursor.getString(4);
                String Password  = cursor.getString(5);
                String mobile_no = cursor.getString(6);
                String role_id = cursor.getString(7);
                String  map_with= cursor.getString(8);
                String map_with_id = cursor.getString(9);
                String  profile_pic= cursor.getString(10);
                String  aadhar_no= cursor.getString(11);
                String  pan_no= cursor.getString(12);
                String  phone_no= cursor.getString(13);
                String  address= cursor.getString(14);


                // Log.d("","Col ID "+id);
                USerListLoginModel recordingItem  = new USerListLoginModel(op_id,id,full_name,email,Password,mobile_no,role_id, map_with, map_with_id,profile_pic,aadhar_no,pan_no,phone_no,address);
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


    public ArrayList<USerListLoginModel> getalluser()
    {
        ArrayList<USerListLoginModel> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();



        Cursor cursor =  db.rawQuery( "select * from userlistq_table ", null );
        if(cursor!=null)
        {
            while (cursor.moveToNext())
            {
                String op_id = cursor.getString(1);
                String id = cursor.getString(2);
                String full_name = cursor.getString(3);
                String email = cursor.getString(4);
                String Password  = cursor.getString(5);
                String mobile_no = cursor.getString(6);
                String role_id = cursor.getString(7);
                String  map_with= cursor.getString(8);
                String map_with_id = cursor.getString(9);
                String  profile_pic= cursor.getString(10);
                String  aadhar_no= cursor.getString(11);
                String  pan_no= cursor.getString(12);
                String  phone_no= cursor.getString(13);
                String  address= cursor.getString(14);


                // Log.d("","Col ID "+id);
                USerListLoginModel recordingItem  = new USerListLoginModel(op_id,id,full_name,email,Password,mobile_no,role_id, map_with, map_with_id,profile_pic,aadhar_no,pan_no,phone_no,address);
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


    public ArrayList<USerListLoginModel> getupdateproduct(String user_id) {
        ArrayList<USerListLoginModel> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Log.d("","user_id"+user_id);

        Cursor cursor =  db.rawQuery( "select * from userlistq_table where user_id ="+user_id+"", null );

     //   Cursor cursor =  db.rawQuery( "select * from customers_table ", null );
        if(cursor!=null)
        {
            while (cursor.moveToNext())
            {
                // arrayList.id = cursor.getInt(cursor.getColumnIndex(ItemTable.COL_ID));
                //   USerListLoginModel recordingItem = new USerListLoginModel( op_user_id,user_id,user_name,email_address,password,mobile_number,role_id,map_with,map_with_id,profile_pic,aadhar_no,pan_no,phone_no,address);

                //  String id = cursor.getString(0);
                String op_id = cursor.getString(1);
                String id = cursor.getString(2);
                String full_name = cursor.getString(3);
                String email = cursor.getString(4);
                String Password  = cursor.getString(5);
                String mobile_no = cursor.getString(6);
                String role_id = cursor.getString(7);
                String  map_with= cursor.getString(8);
                String map_with_id = cursor.getString(9);
                String  profile_pic= cursor.getString(10);
                String  aadhar_no= cursor.getString(11);
                String  pan_no= cursor.getString(12);
                String  phone_no= cursor.getString(13);
                String  address= cursor.getString(14);


                // Log.d("","Col ID "+id);
                USerListLoginModel recordingItem  = new USerListLoginModel(op_id,id,full_name,email,Password,mobile_no,role_id, map_with, map_with_id,profile_pic,aadhar_no,pan_no,phone_no,address);
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
}