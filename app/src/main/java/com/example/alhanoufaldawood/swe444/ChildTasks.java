package com.example.alhanoufaldawood.swe444;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
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
    DatabaseReference ref1;

    List<Task> TasksList;

    public static String childName="";
    public static String childId="";
    public static String taskId="";
    private ActionMode actionMode;
    private ActionMode.Callback callback;
    private static View selectedView ;
    private  static int selectedPosition;



    @SuppressLint("RestrictedApi")
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


        if (className.equals("parent")) {

            childId = intent.getStringExtra(parentHome.childId);
            childName = intent.getStringExtra(parentHome.childName);

            //Toast.makeText(this,childId ,Toast.LENGTH_LONG).show();



        } else if(className.equals("update")){

            childId = intent.getStringExtra(UpdateTask.childId);

        }


        else {

            childId = intent.getStringExtra(AddTaskActivity.childId);
            childName = intent.getStringExtra(AddTaskActivity.childName);

        }
        getSupportActionBar().setTitle(childName + "'s page");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);


        ref = FirebaseDatabase.getInstance().getReference("tasks/" + childId);


        findViewById(R.id.fab).setOnClickListener(this);



        //////////

        callback = new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {

                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.child_context_menu,menu);

                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {

                    case android.R.id.home:
                        NavUtils.navigateUpFromSameTask(ChildTasks.this);
                        break;

                    case R.id.edit_child:

                        Intent EditTask = new Intent(ChildTasks.this, UpdateTask.class);
                        EditTask.putExtra(childId, childId);
                        EditTask.putExtra(taskId, taskId);
                        startActivity(EditTask);
                        actionMode.finish();
                        break;

                    case R.id.delete_child:
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("tasks").child(childId).child(taskId);
                        ref.removeValue();
                        actionMode.finish();
                        break;
                }






                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

                selectedView.setBackgroundColor(Color.WHITE);


            }
        };
        ////////

        listViewTasks.setLongClickable(true);
        listViewTasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Task view1 = ((Task) listViewTasks.getItemAtPosition(position));
                ref1 = FirebaseDatabase.getInstance().getReference("tasks/"+childId);


                Toast.makeText(ChildTasks.this, view1.getTitle(), Toast.LENGTH_LONG).show();

                //final Task task = TasksList.get(position);

                ////////
                ref1.orderByChild("title").equalTo(view1.getTitle()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {
                            taskId = childSnapShot.getKey();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                actionMode = ChildTasks.this.startActionMode(callback);
                view.setSelected(true);
                view.setBackgroundColor(Color.parseColor("#B1B5BB"));
                selectedView = view;
                selectedPosition=position;
               // Toast.makeText(ChildTasks.this, taskId, Toast.LENGTH_LONG).show();

                return true;

                /////////


            }

        });
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

               // Toast.makeText(ChildTasks.this, String.valueOf(listViewTasks.getAdapter().getCount()), Toast.LENGTH_LONG).show();


               // for(int i=0 ; i<listViewTasks.getAdapter().getCount() ; i++){


                   // if(i%2==0){
                       // TextView view = (TextView)getViewByPosition(i,listViewTasks);

                       // view.setBackgroundColor(Color.BLUE);

                 //   }

                  //  else{
                     //   getViewByPosition(i,listViewTasks).setBackgroundColor(Color.YELLOW);


                    //}


               // }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

}
