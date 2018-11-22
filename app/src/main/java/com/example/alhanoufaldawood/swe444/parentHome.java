package com.example.alhanoufaldawood.swe444;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.alhanoufaldawood.swe444.Adapter.Children;
import com.example.alhanoufaldawood.swe444.Model.Child;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class parentHome extends Fragment  {

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


    Activity context;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myFragmentView = inflater.inflate(R.layout.activity_parent_home, container, false);
        setHasOptionsMenu(true);


        context = getActivity();




        childrenList = new ArrayList<>();
        listViewChild = (ListView) myFragmentView.findViewById(R.id.listViewID);

        ref = FirebaseDatabase.getInstance().getReference("children");

     //  FloatingActionButton fab = (FloatingActionButton) myFragmentView.findViewById(R.id.fab);
       // fab.setOnClickListener(new View.OnClickListener() {
         //  @Override
          // public void onClick(View view) {

             //  Intent AddChild = new Intent(context, MainActivity.class);
             //  startActivity(AddChild);
          // }
      // });

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

                        Intent intent = new Intent(getActivity(), ChildTasks.class);


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
                        NavUtils.navigateUpFromSameTask(context);
                        break;

                    case R.id.edit_child:

                        Intent EditChild = new Intent(getActivity(), UpdateChild.class);
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

                actionMode = context.startActionMode(callback);
                view.setSelected(true);
                selectedView = view;
                selectedPosition=position;

                return true;
            }
        });


   return myFragmentView;
    } // on create





    public void onResume(){
        super.onResume();

        // Set title bar
        ((ParentFragment) getActivity()).setActionBarTitle("Children List");

    }


    @Override
    public void onStart() {
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

                Children adapter = new Children(context , childrenList);

                listViewChild.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    }


