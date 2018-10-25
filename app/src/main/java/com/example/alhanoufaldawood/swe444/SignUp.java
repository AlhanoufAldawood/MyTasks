package com.example.alhanoufaldawood.swe444;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {


    private FirebaseAuth auth;
    EditText email,pass,name;
    Button signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth = FirebaseAuth.getInstance();
        email= (EditText) findViewById(R.id.email);
        pass= (EditText) findViewById(R.id.password);
        name= (EditText) findViewById(R.id.name);
        signUp = (Button) findViewById(R.id.Register);

        signUp.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {

                btnRegister_Click(v);

            }
        });

    }



    public void btnRegister_Click(View view){
        auth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignUp.this , "User Created Successfully..",Toast.LENGTH_LONG).show();
                            Intent start = new Intent(SignUp.this, parentHome.class);
                            startActivity(start);
                        }else{
                            Toast.makeText(SignUp.this , "Faild to create..",Toast.LENGTH_LONG).show();

                        }
                    }
                });

    }

    // @Override
    // public void onBackPressed() {
    //    super.onBackPressed();
    //   this.finish();
    // }
}