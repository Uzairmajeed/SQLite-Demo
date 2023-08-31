package com.facebook.data_store_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ContanctDb";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_Contact = "contacts";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String kEY_PHONE = "phoneNo";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create your tables here
        // You can use the db.execSQL() method to execute SQL statements
       // String createTableQuery = "CREATE TABLE IF NOT EXISTS Contacts ( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone_no INTEGER)";
         db.execSQL("CREATE TABLE "+TABLE_Contact+"("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ KEY_NAME +" TEXT,"+ kEY_PHONE +" TEXT"+ " )");
        //SQLiteDatabase database=this.getWritableDatabase();
        //database.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades here
       //db.execSQL("DROP TABLE IF EXISTS "+ TABLE_Contact);
       //onCreate(db);
    }

    public  void addcontact(String name,String phoneNo){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_NAME,name);
        values.put(kEY_PHONE,phoneNo);
        db.insert(TABLE_Contact,null,values);
       /* Log.d("Dataname",name);
        Log.d("Dataphone",phoneNo);*/
    }
    public ArrayList<ContactModel> fetchcontact(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+ TABLE_Contact , null);
        ArrayList<ContactModel> arraycontacts = new ArrayList<>();
        while (cursor.moveToNext()){
            ContactModel model=new ContactModel();
            model.id=cursor.getInt(0);
            model.name=cursor.getString(1);
            model.phoneNo =cursor.getString(2);
            arraycontacts.add(model);
        }
        return arraycontacts;
    }

    public  void updateContact(ContactModel contactModel){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(kEY_PHONE,contactModel.phoneNo);
        db.update(TABLE_Contact,cv,KEY_ID +" = "+contactModel.id,null);
        db.close();

    }

    public  void deletecontact(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_Contact,KEY_ID +" = ?",new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteAllData() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_Contact, null, null);
        // Add more delete statements if you have multiple tables
        // db.delete("AnotherTableName", null, null);
        db.close();
    }
}
