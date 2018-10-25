package com.example.alhanoufaldawood.swe444;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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

    public static final String childName="";
    public static final String childId="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_home);


        childrenList = new ArrayList<>();
        listViewChild = (ListView) findViewById(R.id.listViewID);
        ref = FirebaseDatabase.getInstance().getReference("children");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AddChild = new Intent(parentHome.this, MainActivity.class);
                startActivity(AddChild);
            }
        });

        listViewChild.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Child child = childrenList.get(position);
                Intent intent = new Intent(parentHome.this, AddTaskActivity.class);

                intent.putExtra(childId, child.getId());
                intent.putExtra(childName, child.getName());

                startActivity(intent);


            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();


        ref.addValueEventListener(new ValueEventListener() {
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


