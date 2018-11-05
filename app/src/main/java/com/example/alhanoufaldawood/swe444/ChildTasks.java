package com.example.alhanoufaldawood.swe444;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
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

public class ChildTasks extends AppCompatActivity implements OnClickListener{

    ListView listViewTasks;
    DatabaseReference ref;
    List<Task> TasksList;

    public static String childName="";
    public static String childId="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_tasks);


        TasksList = new ArrayList<>();
        listViewTasks = (ListView) findViewById(R.id.listViewID);






        Intent intent = getIntent();



       // String className = intent.getComponent().getShortClassName();
       String className = intent.getStringExtra("class");

        //Toast.makeText(this,className,Toast.LENGTH_LONG).show();


        if(className.equals("parent")) {

               childId = intent.getStringExtra(parentHome.childId);
               childName = intent.getStringExtra(parentHome.childName);

            //Toast.makeText(this,childId ,Toast.LENGTH_LONG).show();


        }

             else{

               childId = intent.getStringExtra(AddTaskActivity.childId);
               childName = intent.getStringExtra(AddTaskActivity.childName);

           }



        ref= FirebaseDatabase.getInstance().getReference("tasks/"+childName);



         findViewById(R.id.fab). setOnClickListener(this);


    }

    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.child_tasks,menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){


        switch (item.getItemId()){

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

            case R.id.edit_child:

                //Toast.makeText(this,childId ,Toast.LENGTH_LONG).show();

               Intent EditChild = new Intent(ChildTasks.this, UpdateChild.class);
                        EditChild.putExtra(childId, childId);
                       EditChild.putExtra(childName, childName);
                        startActivity(EditChild);
                          return true;




        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                Toast.makeText(this,childId ,Toast.LENGTH_LONG).show(); // طلع الاسم
                Intent AddChild = new Intent(ChildTasks.this, AddTaskActivity.class);
                      AddChild.putExtra(childId, childId);
                      AddChild.putExtra(childName, childName);
                startActivity(AddChild);}
        }


    @Override
    protected void onStart() {
        super.onStart();

       // FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
       // String parentId =currentFirebaseUser.getUid();




        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                TasksList.clear();


                for (DataSnapshot childSnapShot :dataSnapshot.getChildren()){

                    Task task=childSnapShot.getValue(Task.class);
                    TasksList.add(task);

                }

                TaskList adapter = new TaskList(ChildTasks.this , TasksList);

                listViewTasks.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
