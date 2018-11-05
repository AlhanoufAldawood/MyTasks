package com.example.alhanoufaldawood.swe444;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class parentHome extends AppCompatActivity {

    ListView listViewChild;
    DatabaseReference ref;
    List<Child> childrenList;

    public static  String childName="";
    public static  String childId="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_home);

        getSupportActionBar().setTitle("Parent Home");


        childrenList = new ArrayList<>();
        listViewChild = (ListView) findViewById(R.id.listViewID);

        ref = FirebaseDatabase.getInstance().getReference("children");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              // pass
                Intent AddChild = new Intent(parentHome.this, MainActivity.class);
                startActivity(AddChild);
            }
        });

        listViewChild.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Child child = childrenList.get(position);


               ref.orderByChild("name").equalTo(child.getName()).addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {

                       for (DataSnapshot childSnapShot :dataSnapshot.getChildren()){
                          childId= childSnapShot.getKey();


                       }

                       //Toast.makeText(parentHome.this,childId ,Toast.LENGTH_LONG).show();

                       Intent intent = new Intent(parentHome.this, ChildTasks.class);


                     intent.putExtra("class", "parent");
                       intent.putExtra(childId, childId);

                       // intent.putExtra(childName, child.getName());

                      startActivity(intent);
                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }

               });
            }
        });


    }




    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String parentId =currentFirebaseUser.getUid();

        ref.orderByChild("parentId").equalTo(parentId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                childrenList.clear();

                for (DataSnapshot childSnapShot :dataSnapshot.getChildren()){

                    Child child=childSnapShot.getValue(Child.class);

                    childrenList.add(child);

                }

                Children adapter = new Children(parentHome.this , childrenList);

                listViewChild.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    }


