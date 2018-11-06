package com.example.alhanoufaldawood.swe444;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

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

       //Toast.makeText(UpdateTask.this, childId, Toast.LENGTH_LONG).show();




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


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("tasks/"+childId);


        ref.orderByChild("taskId").equalTo(taskId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot task1 :dataSnapshot.getChildren()){
                    Task task  = task1.getValue(Task.class);

                    Toast.makeText(UpdateTask.this, "inside for", Toast.LENGTH_LONG).show();


                    taskTitle.setText(task.getTitle());
                    taskDescription.setText(task.getDescription());
                    txtDate.setText(task.getDate());
                    txtTime.setText(task.getTime());





                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDeadline(v);
            }
        });

        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDeadline(v);
            }
        });


        updateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = taskTitle.getText().toString().trim();
                String description = taskDescription.getText().toString().trim();
                String date = txtDate.getText().toString().trim();
                String time = txtTime.getText().toString().trim();

                update(title,description,date,time);

                Intent updateChild = new Intent(UpdateTask.this, ChildTasks.class);
                updateChild.putExtra("class", "update");
                updateChild.putExtra(childId, childId);
                startActivity(updateChild);

            }
        });


    }

    public void selectDeadline(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
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

  private boolean update(String title, String description, String date, String time){


      Task task = new Task(taskId,title,description,date,time);



      databaseTasks = FirebaseDatabase.getInstance().getReference("tasks").child(childId).child(taskId);
      databaseTasks.setValue(task);



      //Toast.makeText(this,childId ,Toast.LENGTH_LONG).show();

      //Toast.makeText(this,"Child updated",Toast.LENGTH_LONG).show();


      return true;

  }


}
