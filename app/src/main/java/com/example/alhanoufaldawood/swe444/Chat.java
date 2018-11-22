package com.example.alhanoufaldawood.swe444;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.alhanoufaldawood.swe444.Adapter.ChildrenChatList;
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


public class Chat extends Fragment {

    ListView listViewChildChat;
    DatabaseReference ref;

    List<Child> childrenListChat;

    public static  String childName="";
    public static  String childId="";







    @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myFragmentView = inflater.inflate(R.layout.chat, container, false);
        setHasOptionsMenu(true);




        childrenListChat = new ArrayList<>();
        listViewChildChat = (ListView) myFragmentView.findViewById(R.id.listViewChat);

        ref = FirebaseDatabase.getInstance().getReference("children");

        listViewChildChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Child child = childrenListChat.get(position);


                ref.orderByChild("name").equalTo(child.getName()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {
                            childId = childSnapShot.getKey();
                        }

                        Toast.makeText(getActivity(), childId, Toast.LENGTH_LONG).show();


                      //  Intent intent = new Intent(getActivity(), ChattingActivity.class);
                       // intent.putExtra(childId, childId);
                       // startActivity(intent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) { }

                });
            }
        });



        return myFragmentView;

        }

    public void onResume(){
        super.onResume();

        // Set title bar
        ((ParentFragment) getActivity())
                .setActionBarTitle("Messages");

    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String parentId =currentFirebaseUser.getUid();

        ref.orderByChild("parentId").equalTo(parentId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                childrenListChat.clear();

                for (DataSnapshot childSnapShot :dataSnapshot.getChildren()){

                    Child child=childSnapShot.getValue(Child.class);

                    childrenListChat.add(child);

                }

                ChildrenChatList adapter = new ChildrenChatList(getActivity(), childrenListChat);

                listViewChildChat.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}

