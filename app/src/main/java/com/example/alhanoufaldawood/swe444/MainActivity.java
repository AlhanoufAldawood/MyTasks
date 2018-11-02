package com.example.alhanoufaldawood.swe444;

import android.annotation.SuppressLint;
import android.graphics.RadialGradient;
import android.os.Bundle;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



        EditText name;
        RadioGroup gender;
        EditText age;
        EditText user;
        EditText password;

        Button AddChild;

        DatabaseReference databaseTasks ;
        @SuppressLint("RestrictedApi")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


            getSupportActionBar().setTitle("Add New Child");
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);


            databaseTasks = FirebaseDatabase.getInstance().getReference("children"); // i should pass the node name

            name = (EditText) findViewById(R.id.childName);
            gender = (RadioGroup)findViewById(R.id.radioGroup);
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
        String Gender = "";
        String Age =age.getText().toString().trim();
        String User =user.getText().toString().trim();
        String Password =password.getText().toString().trim();
        //String task = new Task();


        if (!TextUtils.isEmpty(Name) &&!TextUtils.isEmpty(User) && !TextUtils.isEmpty(Password)) {
            int selectedId = gender.getCheckedRadioButtonId();
            RadioButton selectedGender = (RadioButton) findViewById(selectedId);
            Gender = selectedGender.getText().toString();


            String id = databaseTasks.push().getKey();
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            String parentId =currentFirebaseUser.getUid();

            Child child = new Child(id,Name,Gender,Age ,User, Password,parentId,"homework");

            databaseTasks.child(id).setValue(child);

            //DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            //Query chatIdRef = rootRef.child("children").orderByChild("name").equalTo("hh");




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
