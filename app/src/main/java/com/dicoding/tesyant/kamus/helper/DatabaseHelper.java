package com.dicoding.tesyant.kamus.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tesyant on 10/4/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    public static String DATABASE_NAME = "dbkamus";

    //table name
    public static String TABLE_ENG = "englishindo";
    public static String TABLE_IND = "tbl_ind";

    //column name
    public static String ENG_ID = "eng_id";
    public static String IND_ID = "ind_id";
    public static String ENG_VOCAB = "eng_vocab";
    public static String IND_VOCAB = "ind_vocab";
    public static String IND_WORD = "ind_word";
    public static String ENG_MEANS = "eng_means";
    public static String IND_MEANS = "ind_means";

    public static final String CREATE_TABLE_ENGLISHIND = "CREATE TABLE " + TABLE_ENG
            + "("
            + ENG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + ENG_VOCAB + " TEXT NOT NULL , "
            + ENG_MEANS + " TEXT NOT NULL );" ;


    public static String CREATE_TABLE_INDOENG = "create table" + TABLE_IND + " ("
            + IND_ID + " integer primary key autoincrement, "
            + IND_VOCAB + " text not null, "
            + IND_WORD + " text not null, "
            + IND_MEANS + " text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ENGLISHIND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENG);
       // db.execSQL("DROP TABLE IF EXISTS " + TABLE_IND);
        onCreate(db);
    }
}
