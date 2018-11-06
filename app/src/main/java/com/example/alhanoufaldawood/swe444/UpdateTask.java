package com.example.alhanoufaldawood.swe444;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateTask extends AppCompatActivity {

    EditText taskTitle;
    EditText taskDescription;
    EditText txtDate, txtTime;

    Button updateTask;
    Button btnDatePicker, btnTimePicker;
    DatabaseReference databaseTasks ;


    private int mYear, mMonth, mDay, mHour, mMinute;
    static String taskId;
    static String childId;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_task);

        Intent intent = getIntent();

        taskId = intent.getStringExtra(ChildTasks.taskId);
        childId = intent.getStringExtra(ChildTasks.childId);


        taskTitle = (EditText) findViewById(R.id.tasktitle);
        taskDescription = (EditText) findViewById(R.id.taskdescription);
        txtDate=(EditText)findViewById(R.id.in_date);
        txtDate.setKeyListener(null);
        txtDate.setBackground(null);
        txtTime=(EditText)findViewById(R.id.in_time);
        txtTime.setKeyListener(null);
        txtTime.setBackground(null);

        btnTimePicker =(Button)findViewById(R.id.btn_time);
        btnDatePicker =(Button)findViewById(R.id.btn_date);
        updateTask = (Button)findViewById(R.id.addtask);


        updateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = taskTitle.getText().toString().trim();
                String description = taskDescription.getText().toString().trim();
                String date = txtDate.getText().toString().trim();
                String time = txtTime.getText().toString().trim();

                update(title,description,date,time);

                Intent updateChild = new Intent(UpdateTask.this, ChildTasks.class);
                startActivity(updateChild);

            }
        });


    }

  private boolean update(String title, String description, String date, String time){


      Task task = new Task(taskId,title,description,date,time);

      FirebaseDatabase database = FirebaseDatabase.getInstance();
      DatabaseReference mDatabaseRef = database.getReference();

      mDatabaseRef.child("children").child(childId).setValue(task);


      //Toast.makeText(this,childId ,Toast.LENGTH_LONG).show();

      //Toast.makeText(this,"Child updated",Toast.LENGTH_LONG).show();


      return true;

  }


}
