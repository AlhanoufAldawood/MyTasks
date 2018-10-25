package com.example.alhanoufaldawood.swe444;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class Children extends ArrayAdapter<Child> {



    private Activity context;
    private List<Child> childList;

    public Children( Activity context, List<Child> childList) {

        super(context, R.layout.child, childList);

        this.context = context;
        this.childList = childList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.child, null, false);



        TextView name = (TextView) listViewItem.findViewById(R.id.nameText);

        Child child = childList.get(position);

        name.setText(child.getName());

        return listViewItem;

    }
}