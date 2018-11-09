package com.example.alhanoufaldawood.swe444;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateChild extends AppCompatActivity {

    EditText name;
    RadioGroup gender;
    EditText age;
    EditText user;
    EditText password;
    RadioButton girl;
    RadioButton boy;


    Button UpdateChild;

    DatabaseReference databaseTasks ;
    static String childId;
    static String childName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_child);

        getSupportActionBar().setTitle("Update Child information");

        Intent intent = getIntent();

         childId = intent.getStringExtra(parentHome.childId);
         childName = intent.getStringExtra(parentHome.childName);

        Toast.makeText(this,childId ,Toast.LENGTH_LONG).show();


        databaseTasks = FirebaseDatabase.getInstance().getReference("children").child(childId); // i should pass the node name

        name = (EditText) findViewById(R.id.childName);
        gender = (RadioGroup)findViewById(R.id.radioGroup);
        boy = (RadioButton)findViewById(R.id.boyRadio);
        girl = (RadioButton)findViewById(R.id.girlRadio);
        age=(EditText)findViewById(R.id.childAge);
        user=(EditText)findViewById(R.id.childUser);
        password=(EditText)findViewById(R.id.childPass);
        UpdateChild = (Button)findViewById(R.id.updateChild);



        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("children");

        ref.orderByChild("id").equalTo(childId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childe :dataSnapshot.getChildren()){
                   Child child  = childe.getValue(Child.class);

                    name.setText(child.getName());
                    user.setText(child.getUser());
                    age.setText(child.getAge());
                    password.setText(child.getPassword());


                    String gender = child.getGender();

                      if(gender.equals("boy")){
                          boy.setChecked(true);

                      }
                      else{
                         girl.setChecked(true);

                      }




                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


        UpdateChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newName = name.getText().toString().trim();
                String Gender = "";
                String newAge = age.getText().toString().trim();
                String newUser = user.getText().toString().trim();
                String newPass = password.getText().toString().trim();
                int selectedId = gender.getCheckedRadioButtonId();
                RadioButton selectedGender = (RadioButton) findViewById(selectedId);
                Gender = selectedGender.getText().toString();

                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                String parentId =currentFirebaseUser.getUid();

                update(newName,Gender,newAge,newUser,newPass,parentId);

                Intent updateChild = new Intent(UpdateChild.this, parentHome.class);
                startActivity(updateChild);

            }
        });


    }







    private boolean update(String name , String gender, String age, String user, String pass, String parentId){

           // Toast.makeText(this,id ,Toast.LENGTH_LONG).show();

            //DatabaseReference databaseUpdate = FirebaseDatabase.getInstance().getReference("children").child(id);

            Child child = new Child(childId,name,gender,age,user,pass,parentId);

            FirebaseDatabase  database = FirebaseDatabase.getInstance();
            DatabaseReference mDatabaseRef = database.getReference();

            mDatabaseRef.child("children").child(childId).setValue(child);


          // databaseUpdate.setValue(child);
            Toast.makeText(this,childId ,Toast.LENGTH_LONG).show();

            //Toast.makeText(this,"Child updated",Toast.LENGTH_LONG).show();


        return true;
        }


    }


