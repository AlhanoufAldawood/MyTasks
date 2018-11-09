package com.example.alhanoufaldawood.swe444;

import android.content.Intent;
import android.drm.DrmStore;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
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

public class parentHome extends AppCompatActivity {

    ListView listViewChild;
    DatabaseReference ref;

    List<Child> childrenList;

    public static  String childName="";
    public static  String childId="";
    public static  String childId1="";

    private ActionMode actionMode;
    private ActionMode.Callback callback;
    private static View selectedView ;
    private  static int selectedPosition;





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

                        for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {
                            childId = childSnapShot.getKey();


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
                        NavUtils.navigateUpFromSameTask(parentHome.this);
                        break;

                    case R.id.edit_child:






                        Intent EditChild = new Intent(parentHome.this, UpdateChild.class);
                        EditChild.putExtra(childId, childId);
                        EditChild.putExtra(childName, childName);
                        startActivity(EditChild);
                              actionMode.finish();
                        break;

                    case R.id.delete_child:
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("children").child(childId);
                        ref.removeValue();
                        actionMode.finish();
                        break;
                }






                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {



            }
        };


        listViewChild.setLongClickable(true);
        listViewChild.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DatabaseReference ref1 =FirebaseDatabase.getInstance().getReference("children");
                Child view1 = ((Child) listViewChild.getItemAtPosition(position));


                ref1.orderByChild("name").equalTo(view1.getName()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {
                            childId = childSnapShot.getKey();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

                actionMode = parentHome.this.startActionMode(callback);
                view.setSelected(true);
                selectedView = view;
                selectedPosition=position;

                return true;
            }
        });



    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.child_tasks, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.edit_child){

            Intent intent = new Intent(parentHome.this, Log_in.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
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


