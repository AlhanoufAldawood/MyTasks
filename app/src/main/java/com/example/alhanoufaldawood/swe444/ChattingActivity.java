package com.example.alhanoufaldawood.swe444;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.alhanoufaldawood.swe444.Adapter.MessageAdapter;
import com.example.alhanoufaldawood.swe444.Model.ChatMessage;
import com.example.alhanoufaldawood.swe444.Model.Child;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ChattingActivity extends AppCompatActivity {

    MessageAdapter messageAdapter;
    List<ChatMessage> messages;
    RecyclerView recyclerView;

     FirebaseUser user;
     String userId ;
    static String childId;



    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        ImageButton sendMsg;
        final EditText message;





        getSupportActionBar().setTitle("Chatting with my child");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);


        sendMsg = findViewById(R.id.btn_send);
        message=  findViewById(R.id.text_send);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        try {
            Intent intent = getIntent();
            childId = intent.getStringExtra("childId");

        } catch(Exception e) {
            e.printStackTrace();
        }



        user = FirebaseAuth.getInstance().getCurrentUser();
        userId= user.getUid();


        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg;
                msg = message.getText().toString();

                if (!msg.equals("")) {
                    sendMessage(userId, childId, msg);
                } else{

                   // Toast.makeText(ChattingActivity.this, "empty meassge", Toast.LENGTH_LONG).show();
                    Toast.makeText(ChattingActivity.this, childId, Toast.LENGTH_LONG).show();


                }
                message.setText("");

            }
        });

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user").child("childId");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                Child child = dataSnapshot.getValue(Child.class);

                //readMsg(user.getUid(),childId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void sendMessage (String sender , String resever, String message){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        HashMap <String, Object> hashMap = new HashMap<>();
        hashMap.put("sender" , sender);
        hashMap.put("resever" , resever);
        hashMap.put("message" , message);

        ref.child("Chats").push().setValue(hashMap);


    }

    private void readMsg (final String sender , final String resever){

        messages = new ArrayList<>() ;

        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("Chats");

       /* ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messages.clear();

                for (DataSnapshot msgSnapShot : dataSnapshot.getChildren()) {
                    ChatMessage msg = msgSnapShot.getValue(ChatMessage.class);

                    if (msg.getMessageRecever().equals(sender)&& msg.getMessageSender().equals(resever)||
                            msg.getMessageRecever().equals(resever)&&msg.getMessageSender().equals(sender)){
                        messages.add(msg);
                    }

                    messageAdapter = new MessageAdapter(ChattingActivity.this ,messages);
                    recyclerView.setAdapter(messageAdapter);

                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }

        });*/
    }





}
