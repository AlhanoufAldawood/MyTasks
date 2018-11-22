package com.example.alhanoufaldawood.swe444;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;


public class Log_in extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth auth;
    EditText  editTextPassword, editTextEmail ;

    String email;
    String password;
    private DatabaseReference mDatabase;
    public Log_in(){
        email=null;
        password=null;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        findViewById(R.id.login).setOnClickListener(this);
        editTextEmail=(EditText)findViewById(R.id.email);
        editTextPassword=(EditText)findViewById(R.id.password);
        auth=FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        getSupportActionBar().hide();

    }

    public void login(){

        password=editTextPassword.getText().toString().trim();
        email=editTextEmail.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length()<6){
            editTextPassword.setError("Minimum length of password shoud be 6");
            editTextPassword.requestFocus();
            return;
        }


        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                                  @Override
                                                                                  public void onComplete(@NonNull Task<AuthResult> task) {

                                                                                      if(task.isSuccessful()){


                                                                                          FirebaseDatabase database =  FirebaseDatabase.getInstance();
                                                                                          FirebaseUser user =  auth.getCurrentUser();
                                                                                          String userId = user.getUid();
                                                                                          DatabaseReference mRef =  database.getReference().child("user").child(userId);
                                                                                          mRef.addValueEventListener(new ValueEventListener() {
                                                                                              @Override
                                                                                              public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                                  String Type=dataSnapshot.child("Type").getValue(String.class);
                                                                                                  if(Type.equals("parent")){

                                                                                                      Toast.makeText(Log_in.this, "Login Successfull...", Toast.LENGTH_LONG).show();
                                                                                                      Intent start = new Intent(Log_in.this, ParentFragment.class);
                                                                                                      startActivity(start);

                                                                                                  }
                                                                                                  else if(Type.equals("child")) {
                                                                                                      Toast.makeText(Log_in.this, "Login Successfull...", Toast.LENGTH_LONG).show();
                                                                                                      Intent start = new Intent(Log_in.this, ChildHome.class);
                                                                                                      startActivity(start);

                                                                                                  }

                                                                                              }

                                                                                              @Override
                                                                                              public void onCancelled(DatabaseError error) {

                                                                                              }
                                                                                          });

                                                                                      }
                                                                                      else {
                                                                                          FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                                                          Toast.makeText(getApplicationContext(), "faild to sign in", Toast.LENGTH_SHORT).show();

                                                                                      }

                                                                                  }
                                                                              }

        );

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                login();
                break;

        }
    }

    public void btnRegister_Click(View view) {
        Intent register = new Intent(Log_in.this, SignUp.class);
        startActivity(register);
    }

    public void btnForget_Click(View view) {
        Intent register = new Intent(Log_in.this, ForgetPass.class);
        startActivity(register);
    }


}
