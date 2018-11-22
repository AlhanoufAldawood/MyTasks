package com.example.alhanoufaldawood.swe444;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.alhanoufaldawood.swe444.Model.Child;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

        private FirebaseAuth mAuth,CRef;


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

            mAuth = FirebaseAuth.getInstance();
            CRef=FirebaseAuth.getInstance();

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

            final Child child = new Child(id,Name,Gender,Age ,User, Password,parentId);

            databaseTasks.child(id).setValue(child);

            /////////////////

            com.google.android.gms.tasks.Task<AuthResult> authResultTask =mAuth.createUserWithEmailAndPassword(User,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        String Gender = "";
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        FirebaseUser user = mAuth.getCurrentUser();
                        String userId = user.getUid();
                        DatabaseReference mRef = database.getReference().child("user").child(userId);

                        mRef.child("userID").setValue(userId);
                        mRef.child("Type").setValue("child");



                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(MainActivity.this, "Successfuly add child", Toast.LENGTH_SHORT);
                                Intent start = new Intent(MainActivity.this, ParentFragment.class);
                            }
                        });
                    }
                }
            });

//////////////////////////




            //Toast.makeText(this,id ,Toast.LENGTH_LONG).show();


            Toast.makeText(this,"Child added" ,Toast.LENGTH_LONG).show();
            Intent AddChild = new Intent(MainActivity.this, ParentFragment.class);
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
