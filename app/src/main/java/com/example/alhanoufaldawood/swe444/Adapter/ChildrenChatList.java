package com.example.alhanoufaldawood.swe444.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.alhanoufaldawood.swe444.Model.Child;
import com.example.alhanoufaldawood.swe444.R;

import java.util.List;

public class ChildrenChatList extends ArrayAdapter<Child> {


            private Activity context;
            private List<Child> childListChat;

            public ChildrenChatList( Activity context, List<Child> childChat) {

                super(context, R.layout.activity_children_chat_list, childChat);

                this.context = context;
                this.childListChat = childChat;
            }


            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                LayoutInflater inflater = context.getLayoutInflater();

                View listViewItem = inflater.inflate(R.layout.activity_children_chat_list, null, false);



                TextView name = (TextView) listViewItem.findViewById(R.id.nameText);

                Child child = childListChat.get(position);

                name.setText(child.getName());

                name.setTextSize(30);
                name.setTextColor(Color.parseColor("#707070"));


                if(position %4 == 0){
                    listViewItem.setBackgroundResource(R.drawable.green);

                }if(position %4 == 1){
                    listViewItem.setBackgroundResource(R.drawable.yello);

                }if(position %4 == 2){
                    listViewItem.setBackgroundResource(R.drawable.red);

                }if(position %4 == 3){
                    listViewItem.setBackgroundResource(R.drawable.blue);

                }

                return listViewItem;

            }
        }


