package com.example.employee;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText name,id,design,salary;
    Button load,upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name=findViewById(R.id.Name);
        id=findViewById(R.id.id);
        design=findViewById(R.id.designation);
        salary=findViewById(R.id.salary);
        load=findViewById(R.id.Load);
        upload=findViewById(R.id.Upload);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eName = name.getText().toString();
                String eId = id.getText().toString();
                String eDesign=design.getText().toString();
                String eSalary = salary.getText().toString();

                if(eName.isEmpty() || eId.isEmpty() || eDesign.isEmpty() || eSalary.isEmpty())
                {
                    android.widget.Toast.makeText(getApplicationContext(), "Please Enter All Fields", Toast.LENGTH_LONG).show();
                }
                else
                {
                    // class to add values in the database
                    ContentValues values = new ContentValues();

                    // fetching text from user
                    values.put(MyContentProvider.name,eName);
                    values.put(MyContentProvider.id,eId);
                    values.put(MyContentProvider.desig,eDesign);
                    values.put(MyContentProvider.salary,eSalary);

                    // inserting into database through content URI
                    getContentResolver().insert(MyContentProvider.CONTENT_URI, values);

                    // displaying a toast message
                    android.widget.Toast.makeText(getApplicationContext(), "New Record Inserted", Toast.LENGTH_LONG).show();
                }
            }
        });

        load.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View v) {

                TextView resultView= (TextView) findViewById(R.id.res);

                // creating a cursor object of the
                // content URI
                Cursor cursor = getContentResolver().query(MyContentProvider.CONTENT_URI,
                        null, null, null, null);

                // iteration of the cursor
                // to print whole table
                if(cursor.moveToFirst()) {
                    StringBuilder strBuild=new StringBuilder();
                    while (!cursor.isAfterLast()) {
                        strBuild.append("\n").
                                append(cursor.getString(cursor.getColumnIndex(MyContentProvider.name))).
                                append("-").append(cursor.getString(cursor.getColumnIndex(MyContentProvider.id))).
                                append("-").append(cursor.getString(cursor.getColumnIndex(MyContentProvider.desig))).
                                append("-").append(cursor.getString(cursor.getColumnIndex(MyContentProvider.salary)));
                        cursor.moveToNext();
                    }
                    resultView.setText(strBuild);
                }
                else {
                    resultView.setText("No Records Found");
                }
            }
        });
    }
}