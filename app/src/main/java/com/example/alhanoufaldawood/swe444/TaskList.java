package com.example.alhanoufaldawood.swe444;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ArrayAdapter;


import java.util.List;

public class TaskList extends ArrayAdapter<Task>{


    private Activity context;
    private List<Task> taskList;

    public TaskList( Activity context, List<Task> taskList) {

        super(context, R.layout.task_list, taskList);

        this.context = context;
        this.taskList = taskList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.task_list, null, false);



        TextView name = (TextView) listViewItem.findViewById(R.id.titleText);

        Task task = taskList.get(position);

        name.setText(task.getTitle());

        return listViewItem;

    }
}
