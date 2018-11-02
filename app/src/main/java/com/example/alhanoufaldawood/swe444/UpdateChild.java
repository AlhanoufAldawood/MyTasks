package com.example.alhanoufaldawood.swe444;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateChild extends AppCompatActivity {

    EditText name;
    EditText gender;
    EditText age;
    EditText user;
    EditText password;

    Button UpdateChild;

    DatabaseReference databaseTasks ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_child);

        databaseTasks = FirebaseDatabase.getInstance().getReference("children"); // i should pass the node name

        name = (EditText) findViewById(R.id.newchildName);
        gender = (EditText) findViewById(R.id.newChildGender);
        age=(EditText)findViewById(R.id.newchildAge);
        user=(EditText)findViewById(R.id.newchildUser);
        password=(EditText)findViewById(R.id.newchildPass);
        UpdateChild = (Button)findViewById(R.id.updateChild);

        Intent intent = getIntent();

        final String childId = intent.getStringExtra(parentHome.childId);

        UpdateChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newName = name.getText().toString().trim();
                String newGender = gender.getText().toString().trim();
                String newAge = age.getText().toString().trim();
                String newUser = user.getText().toString().trim();
                String newPass = password.getText().toString().trim();


                update(childId,newName,newGender,newAge,newUser,newPass);
            }
        });


    }



        private boolean update(String id,String name , String gender, String age, String user, String pass){

            databaseTasks = FirebaseDatabase.getInstance().getReference("children").child(id);

           // Child child = new Child(id,name,gender,age,user,pass,"gg");

           // databaseTasks.setValue(child);


            Toast.makeText(this,"Child updated",Toast.LENGTH_LONG).show();


        return true;
        }


    }


