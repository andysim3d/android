package com.example.andy.mytodo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Andy on 14/07/2015.
 */
public class TaskDBHelper extends SQLiteOpenHelper {

    public TaskDBHelper(Context context) {
        super(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQuery = String.format("CREATE TABLE %s (" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT)", TaskContract.TABLE, TaskContract.Columns.TASK
        );
        Log.d("TaskDBHelper", "Query to form table: " + sqlQuery);
        db.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //How ever I should not doing like this;
        db.execSQL("DROP TABLE IF EXISTS" + TaskContract.TABLE);
        onCreate(db);

    }
}
