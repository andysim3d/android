package com.example.andy.mytodo;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.andy.mytodo.db.TaskContract;
import com.example.andy.mytodo.db.TaskDBHelper;


public class MainActivity extends ActionBarActivity {
    private TaskDBHelper _helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase sqlDB = new TaskDBHelper(this).getWritableDatabase();
        Cursor cursor = sqlDB.query(TaskContract.TABLE, new String[]{TaskContract.Columns.TASK},
                null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            Log.d("job  ",
                    cursor.getString(cursor.getColumnIndexOrThrow(
                            TaskContract.Columns.TASK))
            );

            Log.d("123", "haha");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add_task) {
            AlertDialog.Builder bd = new AlertDialog.Builder(this);
            bd.setTitle("Add a task");
            bd.setMessage("What you want to do?");
            final EditText inputField = new EditText(this);
            bd.setView(inputField);
            bd.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String task = inputField.getText().toString();
                    Log.d("MainActivity", task);
                    _helper = new TaskDBHelper(MainActivity.this);
                    SQLiteDatabase db = _helper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.clear();
                    //create value
                    values.put(TaskContract.Columns.TASK, task);
                    //insert into table, sth, value, if conflict do what.
                    db.insertWithOnConflict(TaskContract.TABLE, null, values,
                            SQLiteDatabase.CONFLICT_IGNORE);
                }
            });
            bd.setNegativeButton("Cancel", null);
            bd.create().show();
            return true;
            //      Log.d("MainActivity", "Add a new task");
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
