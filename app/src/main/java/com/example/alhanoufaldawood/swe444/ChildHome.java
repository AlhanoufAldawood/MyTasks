package com.example.alhanoufaldawood.swe444;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChildHome extends AppCompatActivity {


    ListView listViewTask;
    DatabaseReference ref;
    List<Task> TasksList;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public static final String taskTitle="";
    public static final String taskId="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_home);
        getSupportActionBar().setTitle("MY Tasks");

        TasksList = new ArrayList<>();
        listViewTask = (ListView) findViewById(R.id.listViewID1);

        ////NNNNEEEEWWWW////
        // ref = FirebaseDatabase.getInstance().getReference("tasks").child(user.getUid());
        ref = FirebaseDatabase.getInstance().getReference("tasks").child(user.getUid());
///     ////NNNNEEEEWWWW////



        listViewTask.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Task task = TasksList.get(position);
                //Intent intent = new Intent(ChildHome.this, taskDetails.class);

              //  intent.putExtra(taskId, task.getTaskId());
               // intent.putExtra(taskTitle, task.getTitle());

               // startActivity(intent);


            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                TasksList.clear();

                for (DataSnapshot childSnapShot :dataSnapshot.getChildren()){

                    Task task=childSnapShot.getValue(Task.class);

                    TasksList.add(task);

                }

                TaskList adapter = new TaskList(ChildHome.this , TasksList);

                listViewTask.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }}
