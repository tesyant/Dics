package com.dicoding.tesyant.kamus.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.dicoding.tesyant.kamus.model.IndonesiaModel;

import java.util.ArrayList;

/**
 * Created by tesyant on 10/5/17.
 */

public class IndonesiaHelper {

    private static String DATABASE_TABLE = DatabaseHelper.TABLE_IND;
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public IndonesiaHelper(Context context) {
        this.context = context;
    }

    public IndonesiaHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public Cursor searchQueryByName (String query) {
        return database.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE " +
                DatabaseHelper.IND_VOCAB + " LIKE '%" + query + "%'", null);
    }

    public final ArrayList<String> getData(String search) {
        Cursor cursor = searchQueryByName(search);
        ArrayList<String> result = new ArrayList<String>();
        cursor.moveToFirst();
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            for (int i=0; i<cursor.getCount(); i++) {
                result.add(cursor.getString(1));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return result;
    }

    public Cursor queryAllData() {
        return database.rawQuery("SELECT * FROM " + DATABASE_TABLE + " ORDER BY " +
                DatabaseHelper.IND_ID + " ASC", null);
    }

    public ArrayList<IndonesiaModel> getAllData() {
        ArrayList<IndonesiaModel> arrayList = new ArrayList<>();
        Cursor cursor = queryAllData();
        cursor.moveToFirst();
        IndonesiaModel indonesiaModel;
        if (cursor.getCount()>0) {
            do {
                indonesiaModel = new IndonesiaModel();
                indonesiaModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.IND_ID)));
                indonesiaModel.setVocab(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.IND_VOCAB)));
                indonesiaModel.setMeans(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.IND_MEANS)));

                arrayList.add(indonesiaModel);
                cursor.moveToNext();
            }
            while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(IndonesiaModel indonesiaModel) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(DatabaseHelper.IND_VOCAB, indonesiaModel.getVocab());
        initialValues.put(DatabaseHelper.IND_MEANS, indonesiaModel.getMeans());
        Log.d("insert", "check");
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public void insertTransaction(ArrayList<IndonesiaModel> indonesiaModels) {
        String sql = "INSERT INTO " + DATABASE_TABLE + " ("
                + DatabaseHelper.IND_VOCAB + ", "
                + DatabaseHelper.IND_MEANS + ", " + ") VALUES (?, ?, ?, ?);";
        database.beginTransaction();

        SQLiteStatement statement = database.compileStatement(sql);
        for (int i = 0; i < indonesiaModels.size(); i++) {
            statement.bindString(1, indonesiaModels.get(i).getVocab());
            statement.bindString(3, indonesiaModels.get(i).getMeans());
            statement.execute();
            statement.clearBindings();
        }

        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public void update(IndonesiaModel indonesiaModel) {
        ContentValues args = new ContentValues();
        args.put(DatabaseHelper.IND_VOCAB, indonesiaModel.getVocab());
        args.put(DatabaseHelper.IND_MEANS, indonesiaModel.getMeans());
        database.update(DATABASE_TABLE, args, DatabaseHelper.IND_ID + "= '"
                + indonesiaModel.getId() + "'", null);
    }

    public void delete(int id) {
        database.delete(DatabaseHelper.TABLE_IND, DatabaseHelper.IND_ID + " = '" + id
                + "'", null);
    }


}
