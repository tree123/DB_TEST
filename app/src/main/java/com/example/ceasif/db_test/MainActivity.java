package com.example.ceasif.db_test;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MyDatabaseHelper myDB;
    EditText editName,editPaid,editExpense;
    Button insertBtn;
    Button dispBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDB = new MyDatabaseHelper(this);
        editName = (EditText)findViewById(R.id.editText_name);
        editPaid = (EditText)findViewById(R.id.editText_paid);
        editExpense = (EditText)findViewById(R.id.editText_expense);
        insertBtn = (Button)findViewById(R.id.insert_Btn);
        dispBtn = (Button)findViewById(R.id.displayBtn);
        insertToDB();
        getDatafromDB();
    }


    public void insertToDB() {

       insertBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               boolean errInsert = myDB.insertData(editName.getText().toString(),
                                                    editPaid.getText().toString(),
                                                    editExpense.getText().toString());

               if (errInsert == true) {
                   Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
               } else {
                   Toast.makeText(MainActivity.this, "Data Insertion Failed", Toast.LENGTH_LONG).show();
               }
           }
       });
    }

    public void getDatafromDB(){

        dispBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDB.getData();
                if(res.getCount() == 0)
                {
                    DisplayData("Error", "No Data Available");
                }
                else
                {
                    StringBuffer buf = new StringBuffer();
                    buf.append("ID  Name  Paid  Expense \n\n");
                    while(res.moveToNext())
                    {
                        buf.append(String.valueOf(res.getInt(0))+"  ");
                        buf.append(res.getString(1)+"   ");
                        buf.append(res.getString(2)+"   ");
                        buf.append(res.getString(3)+"\n\n");
                    }
                    DisplayData("Data",buf.toString());
                }

            }
        });
    }

    public void DisplayData(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();


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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
