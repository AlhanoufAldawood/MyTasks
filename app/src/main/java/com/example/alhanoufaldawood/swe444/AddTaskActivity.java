package com.example.alhanoufaldawood.swe444;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    EditText taskTitle;
    EditText taskDescription;
    EditText txtDate, txtTime;

    Button addTask;
    Button btnDatePicker, btnTimePicker;


    DatabaseReference databaseTasks ;


    private int mYear, mMonth, mDay, mHour, mMinute;

    static  String childId="";
    static String childName="";


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);

        Intent intent = getIntent();
        childId = intent.getStringExtra(ChildTasks.childId);
        childName = intent.getStringExtra(ChildTasks.childName);

        getSupportActionBar().setTitle("Add New Task for ");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);



        taskTitle = (EditText) findViewById(R.id.tasktitle);
        taskDescription = (EditText) findViewById(R.id.taskdescription);
        txtDate=(EditText)findViewById(R.id.in_date);
        txtDate.setKeyListener(null);
        txtTime=(EditText)findViewById(R.id.in_time);
        txtTime.setKeyListener(null);

        btnTimePicker =(Button)findViewById(R.id.btn_time);
        btnDatePicker =(Button)findViewById(R.id.btn_date);
        addTask = (Button)findViewById(R.id.addtask);









        //ref= FirebaseDatabase.getInstance().getReference("tasks/"+childName);

        databaseTasks = FirebaseDatabase.getInstance().getReference("tasks").child(childId);
               // databaseTasks.orderByChild("childId").equalTo(childId);


                btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDeadline(v);
            }
        });

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDeadline(v);
            }
        });

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();

            }
        });

       // findViewById(R.id.btn_date). setOnClickListener(this);
        //findViewById(R.id.btn_time). setOnClickListener(this);
        //findViewById(R.id.addtask). setOnClickListener(this);
    }

   // public void onClick(View view) {
        //switch (view.getId()) {
          //  case R.id.btn_date:
              //  selectDeadline(view);

           // case R.id.btn_time:
               // selectDeadline(view);

           // case R.id.addChild:
             //  addTask();
              //  Intent AddTask = new Intent(AddTaskActivity.this, ChildTasks.class);
               // startActivity(AddTask);



       // }
   // }

    private void addTask (){

        String title = taskTitle.getText().toString().trim();
        String description = taskDescription.getText().toString().trim();
        String time =txtTime.getText().toString().trim();
        String date =txtDate.getText().toString().trim();



        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(date) &&  !TextUtils.isEmpty(date)) {

            String id = databaseTasks.push().getKey();

            Task task = new Task(id,title,description,date ,time);
            Toast.makeText(AddTaskActivity.this, id, Toast.LENGTH_LONG).show();


            databaseTasks.push().setValue(task);

            Toast.makeText(this,"Task added" ,Toast.LENGTH_LONG).show();
            Intent AddTask = new Intent(AddTaskActivity.this, ChildTasks.class);
            AddTask.putExtra("class", "AddTask");
            AddTask.putExtra(childId,childId);
            AddTask.putExtra(childName,childName);
            startActivity(AddTask);


        } else if(TextUtils.isEmpty(title) && TextUtils.isEmpty(date) && TextUtils.isEmpty(time)){
            taskTitle.setError("Title is required");
            txtDate.setError("Date is required");
            txtTime.setError("Time is required");
            return;

        }else if(TextUtils.isEmpty(time)) {
            txtTime.setError("Time is required");
            return;}

        else if(TextUtils.isEmpty(date)) {
            txtDate.setError("Date is required");
            return;



        }else if(TextUtils.isEmpty(title) && TextUtils.isEmpty(date)){
            taskTitle.setError("Title is required");
            txtDate.setError("Date is required");
            return;

        }else if(TextUtils.isEmpty(date) && TextUtils.isEmpty(time)){
            taskTitle.setError("Title is required");
            txtTime.setError("Time is required");
            return;

        }else if(TextUtils.isEmpty(title) && TextUtils.isEmpty(time)){
            txtDate.setError("Date is required");
            txtTime.setError("Time is required");
            return;

        }else if(TextUtils.isEmpty(title) ){
            taskTitle.setError("Title is required");
            return;


        }

    }

    public void selectDeadline(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog cc = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {


                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            cc.getDatePicker().setMinDate(System.currentTimeMillis()-1000);

            cc.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }

}


