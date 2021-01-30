package com.fashion.amai.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.fashion.amai.model.AddToCartProduct;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ronsoft on 9/16/2017.
 */

public class AddToCartDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "product.db";
    private static final int DATABASE_VERSION = 3 ;
    public static final String TABLE_NAME = Constants.ADD_TO_CART_TABLE;
    public static final String COLUMN_ID = "_id";
    public static final String CART_PRODUCT_NAME = Constants.CART_PRODUCT_NAME;
    public static final String CART_PRODUCT_PRICE = Constants.CART_PRODUCT_PRICE;
    public static final String CART_PRODUCT_QUANTITY = Constants.CART_PRODUCT_QUANTITY;
    public static final String COLUMN_PERSON_IMAGE = Constants.CART_PRODUCT_IMAGE;
    public Context mContext;
    private int count;


    protected SharedPreferences preferences;



    public AddToCartDBHelper(Context context, SharedPreferences preferences) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
        mContext = context;
        this.preferences = preferences;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      //  mContext.deleteDatabase("people.db");

        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CART_PRODUCT_NAME + " TEXT NOT NULL, " +
                CART_PRODUCT_PRICE + " NUMBER NOT NULL, " +
                CART_PRODUCT_QUANTITY + " TEXT NOT NULL, " +
                COLUMN_PERSON_IMAGE + " BLOB NOT NULL);"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }
    /**create record**/
    public void saveNewPerson(AddToCartProduct cartProduct) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CART_PRODUCT_NAME, cartProduct.getProduct_name());
        values.put(CART_PRODUCT_PRICE, cartProduct.getPrice());
        values.put(CART_PRODUCT_QUANTITY, cartProduct.getQuantity());
        values.put(COLUMN_PERSON_IMAGE, cartProduct.getProduct_url());

        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();


    }

    /**Query records, give options to filter results**/
    public List<AddToCartProduct> peopleList(String filter) {
        String query;
        if(filter.equals("")){
            //regular query
            query = "SELECT  * FROM " + TABLE_NAME;
        }else{
            //filter results by filter option provided
            query = "SELECT  * FROM " + TABLE_NAME + " ORDER BY "+ filter;
        }

        String query02 = "select count(*) from " + TABLE_NAME;



        List<AddToCartProduct> personLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Cursor cursor02 = db.rawQuery(query02, null);
        cursor02.moveToFirst();
        count= cursor02.getInt(0);
        MySharedPreferences.registerCartItem(preferences, String.valueOf(count));


        //cursor02.close();


        AddToCartProduct person;

        if (cursor.moveToFirst()) {
            do {
                person = new AddToCartProduct();
                person.setProduct_id(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                person.setProduct_name(cursor.getString(cursor.getColumnIndex(CART_PRODUCT_NAME)));
                person.setQuantity(cursor.getString(cursor.getColumnIndex(CART_PRODUCT_PRICE)));
                person.setSize(cursor.getString(cursor.getColumnIndex(CART_PRODUCT_QUANTITY)));
                person.setProduct_url(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_IMAGE)));
                personLinkedList.add(person);
            } while (cursor.moveToNext());
        }


        return personLinkedList;
    }

    /**Query only 1 record**/
    public AddToCartProduct getPerson(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE _id="+ id;
        Cursor cursor = db.rawQuery(query, null);

        AddToCartProduct receivedPerson = new AddToCartProduct();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            receivedPerson.setProduct_name(cursor.getString(cursor.getColumnIndex(CART_PRODUCT_NAME)));
            receivedPerson.setQuantity(cursor.getString(cursor.getColumnIndex(CART_PRODUCT_PRICE)));
            receivedPerson.setSize(cursor.getString(cursor.getColumnIndex(CART_PRODUCT_QUANTITY)));
            receivedPerson.setProduct_url(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_IMAGE)));
        }



        return receivedPerson;


    }




    /**delete record**/
    public void deleteCartProduct(long id, Context context) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE _id='"+id+"'");
        Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();

        if (count>0){
            count--;
            MySharedPreferences.registerCartItem(preferences, String.valueOf(count));
        }


    }


    /**update record */
    public void updatePersonRecord(long personId, Context context, AddToCartProduct updatedperson) {
        SQLiteDatabase db = this.getWritableDatabase();
        //you can use the constants above instead of typing the column names
        db.execSQL("UPDATE  "+TABLE_NAME+" SET name ='"+ updatedperson.getProduct_name() + "', age ='" + updatedperson.getQuantity()+ "', occupation ='"+ updatedperson.getPrice() + "', image ='"+ updatedperson.getProduct_url() + "'  WHERE _id='" + personId + "'");
        Toast.makeText(context, "Updated successfully.", Toast.LENGTH_SHORT).show();


    }

}
