package com.example.alhanoufaldawood.swe444;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    EditText editTextUsername , editTextPassword, editTextEmail,EditTextphone,EditTextpasswordCon ;

    private FirebaseAuth mAuth;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();
    String name;
    String email;
    boolean flag;
    String password;
    String password2;
    String phoneNo;

    public SignUp(){
        name=null;
        email=null;
        password=null;
        password2=null;
        flag=true;
    }
    public SignUp(String name, int i) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextEmail=(EditText)findViewById(R.id.email);
        editTextPassword=(EditText)findViewById(R.id.password);
        editTextUsername=(EditText)findViewById(R.id.name);
        EditTextpasswordCon=(EditText)findViewById(R.id.password2);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.Register).setOnClickListener(this);


        getSupportActionBar().hide();

    }

    private void regsterUser(){
        name=editTextUsername.getText().toString().trim();
        password=editTextPassword.getText().toString().trim();
        email=editTextEmail.getText().toString().trim();
        password2=EditTextpasswordCon.getText().toString().trim();


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

        if(password2.isEmpty()){
            editTextPassword.setError("Password confirmation required");
            editTextPassword.requestFocus();
            return;
        }

        if(name.isEmpty()){
            editTextUsername.setError("Username is required");
            editTextUsername.requestFocus();
            return;
        }

        if(password2.equals(password)==false)  {
            EditTextpasswordCon.setError("Password Not matching");
            EditTextpasswordCon.requestFocus();
            return;}

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        DatabaseReference Ref = rootRef.child("user").child("phoneNO");

        ValueEventListener eventListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        Ref.addListenerForSingleValueEvent(eventListener);





        Task<AuthResult> authResultTask =mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                                                                        @Override
                                                                                                                        public void onComplete(@NonNull Task<AuthResult> task) {

                                                                                                                            if (task.isSuccessful()) {

                                                                                                                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                                                                                                FirebaseUser user = mAuth.getCurrentUser();
                                                                                                                                String userId = user.getUid();
                                                                                                                                DatabaseReference mRef = database.getReference().child("user").child(userId);
                                                                                                                                /*mRef.child("name").setValue(name);
                                                                                                                                mRef.child("email").setValue(email);
                                                                                                                                mRef.child("phoneNO").setValue(phoneNo);
                                                                                                                                mRef.child("groupID").setValue(1);*/
                                                                                                                                mRef.child("userID").setValue(userId);
                                                                                                                                mRef.child("Type").setValue("parent");

                                                                                                                                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                    @Override
                                                                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                        Toast.makeText(SignUp.this, "Successfuly regist", Toast.LENGTH_SHORT);
                                                                                                                                        Intent start = new Intent(SignUp.this, ParentFragment.class);
                                                                                                                                        startActivity(start);
                                                                                                                                    }
                                                                                                                                });
                                                                                                                            } else {
                                                                                                                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                                                                                                                    Toast.makeText(getApplicationContext(), "You already registered", Toast.LENGTH_SHORT).show();
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }

        );

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Register:
                regsterUser();
                break;

        }
    }



    public void btnSignin_Click(View view) {
        Intent Login = new Intent(SignUp.this, Log_in.class);
        startActivity(Login);
    }

    // @Override
    // public void onBackPressed() {
    //    super.onBackPressed();
    //   this.finish();
    // }
}