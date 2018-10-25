package com.example.alhanoufaldawood.swe444;

import android.os.Bundle;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



        EditText name;
        EditText gender;
        EditText age;
        EditText user;
        EditText password;

        Button AddChild;

        DatabaseReference databaseTasks ;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


            databaseTasks = FirebaseDatabase.getInstance().getReference("children"); // i should pass the node name

            name = (EditText) findViewById(R.id.childName);
            gender = (EditText) findViewById(R.id.ChildGender);
            age=(EditText)findViewById(R.id.childAge);
            user=(EditText)findViewById(R.id.childUser);
            password=(EditText)findViewById(R.id.childPass);







           findViewById(R.id.addChild).setOnClickListener(this);
        }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addChild:
                addChild();

        }
    }


    private void addChild (){

        String Name = name.getText().toString().trim();
        String Gender = gender.getText().toString().trim();
        String Age =age.getText().toString().trim();
        String User =user.getText().toString().trim();
        String Password =password.getText().toString().trim();



        if (!TextUtils.isEmpty(Name) &&!TextUtils.isEmpty(User) && !TextUtils.isEmpty(Password)) {

            String id = databaseTasks.push().getKey();

            Child child = new Child(id,Name,Gender,Age ,User, Password);

            databaseTasks.child(id).setValue(child);

            Toast.makeText(this,"Child added" ,Toast.LENGTH_LONG).show();
            Intent AddChild = new Intent(MainActivity.this, parentHome.class);
            startActivity(AddChild);

        } else if (TextUtils.isEmpty(Name) && TextUtils.isEmpty(User) && TextUtils.isEmpty(Password)){
            name.setError("Name is required");
            user.setError("User is required");
            password.setError("Password is required");
            return;
        }else if (TextUtils.isEmpty(Name)  && TextUtils.isEmpty(Password)){
            name.setError("Name is required");
            password.setError("Password is required");
            return;
        }else if (TextUtils.isEmpty(Name)  && TextUtils.isEmpty(User)){
            name.setError("Name is required");
            user.setError("User is required");
            return;
        }else if (TextUtils.isEmpty(User)  && TextUtils.isEmpty(Password)){
            user.setError("User is required");
            password.setError("Password is required");
            return;
        }


    }


}
