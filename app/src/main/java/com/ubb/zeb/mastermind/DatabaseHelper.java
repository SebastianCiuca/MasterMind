package com.ubb.zeb.mastermind;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by zeb on 12/17/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    //tag
    private static final String TAG = "Sebi - DatabaseHelper";

    //Database fields
    private static final String TABLE_NAME = "Memos";
    private static final String COL0_ID = "ID";
    private static final String COL1_title = "Title";
    private static final String COL2_memo = "Memo";

    public DatabaseHelper(Context ctx){
        super(ctx,TABLE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //check if DB already exists?
        String createTable = "CREATE TABLE   " + TABLE_NAME
                + " ( ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL1_title + " TEXT, "
                + COL2_memo + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        //onCreate(db);
    }

    /*
        EVERYTHING FROM HERE SHOULD BE IN REPO.
     */

    public boolean addData(Memo m){
        Log.d(TAG,"addData: setting content values... ");

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1_title,m.getMemoTitle());
        contentValues.put(COL2_memo,m.getMemoText());

        Log.d(TAG,"addData: Adding memo - " + m.toString() + " into the DB!");

        long res = db.insert(TABLE_NAME,null,contentValues);

        if (res == -1)
            return false;
        return true;
    }

    public Cursor getData(){
        Log.d(TAG,"getData... ");

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query,null);
        return data;
    }


    public Cursor getMemoID(Memo m){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  " + COL0_ID + " FROM " + TABLE_NAME +
                " WHERE " + COL1_title + " = '" + m.getMemoTitle() + "' AND " +
                COL2_memo + " = '" + m.getMemoText() + "'";
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public void updateMemo(int ID, Memo newMemo, Memo oldMemo){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " UPDATE " + TABLE_NAME +
                " SET " + COL1_title + " = '" + newMemo.getMemoTitle() +
                "', " + COL2_memo + " = '" + newMemo.getMemoText() +
                "' WHERE " + COL0_ID + " = '" + String.valueOf(ID) +
                "' AND " + COL1_title + " = '" + oldMemo.getMemoTitle() +
                "' AND " + COL2_memo + " ='" + oldMemo.getMemoText() + "'";
        Log.d(TAG,"updateMemo: query -> " + query);
        Log.d(TAG,"updateMemo: newMemo -> " + newMemo);

        db.execSQL(query);
    }

    public int extractMemoID(Memo oldMemo){
        Cursor data = getMemoID(oldMemo);

        int itemID = -1;

        while(data.moveToNext())
            itemID = data.getInt(0);

        return itemID;
    }

    public void deleteMemo(int memoID){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME +
                " WHERE " + COL0_ID + " = '" + String.valueOf(memoID) + "'";
        Log.d(TAG,"deleteMemo: query -> " + query);
        Log.d(TAG,"deleteMemo: memoID -> " + String.valueOf(memoID));
        db.execSQL(query);
    }
}




























